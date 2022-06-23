package ceuadditions.common.TileEntities.Pollution.multi;

import ceuadditions.CEuAdditions;
import ceuadditions.CommonProxy;
import ceuadditions.api.ConfigHandler;
import ceuadditions.api.values.GregTechDataValues;
import ceuadditions.api.values.MultiblockAbilities;
import ceuadditions.common.TileEntities.Pollution.multi.parts.MetaTileEntityHugeFan;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.unification.ore.OrePrefix.dust;

public class MetaTileEntityLargePollutionScrubber extends RecipeMapMultiblockController implements ITieredMetaTileEntity {

    private static final int BaseEuConsumptionPerScrub = 50;
    private boolean isWorking;


    public final IBlockState casingState;
    public final IBlockState gearboxState;
    public final ICubeRenderer casingRenderer;
    public final boolean hasMufflerHatch;
    public final ICubeRenderer frontOverlay;
    public final int tier;

    private IEnergyContainer energyContainer;

    private boolean isWorkingEnabled = true;

    private MetaTileEntityHugeFan fan = new MetaTileEntityHugeFan(null, 1);

    public MetaTileEntityLargePollutionScrubber(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, int tier, IBlockState casingState, IBlockState gearboxState, ICubeRenderer casingRenderer, boolean hasMufflerHatch, ICubeRenderer frontOverlay) {
        super(metaTileEntityId, recipeMap);
        this.casingState = casingState;
        this.gearboxState = gearboxState;
        this.casingRenderer = casingRenderer;
        this.hasMufflerHatch = hasMufflerHatch;
        this.frontOverlay = frontOverlay;
        this.tier = tier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargePollutionScrubber(metaTileEntityId, recipeMap, tier, casingState, gearboxState, casingRenderer, hasMufflerHatch, frontOverlay);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("HHHHH", "CCCCC", "CCCCC", "CCCCC", "HHHHH")
                .aisle("CCCCC", "C#G#C", "C###C", "C###C", "CCCCC")
                .aisle("CCCCC", "CG#GC", "C###C", "C###C", "CCRCC")
                .aisle("CCCCC", "C#G#C", "C###C", "C###C", "CCCCC")
                .aisle("HHHHH", "CCCCC", "CCSCC", "CCCCC", "HHHHH")
                .where('S', selfPredicate())
                .where('G', states(getGearBoxState()))
                .where('C', states(getCasingState()))
                .where('R', metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbilities.HUGE_FAN).stream()
                        .filter(mte -> (mte instanceof ITieredMetaTileEntity) && (((ITieredMetaTileEntity) mte).getTier() >= tier))
                        .toArray(MetaTileEntity[]::new))
                        .addTooltips("gregtech.multiblock.pattern.clear_amount_3")
                        .addTooltip("gregtech.multiblock.pattern.error.limited.1", GTValues.VN[tier])
                        .setExactLimit(1))
                .where('H', states(getCasingState()).or(autoAbilities(true, true, false, true, false, true, false)))
                .where('#', states(Blocks.AIR.getDefaultState()))
                .build();
    }

    public IBlockState getCasingState() {
        return casingState;
    }

    public IBlockState getGearBoxState() {
        return gearboxState;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return casingRenderer;
    }


    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }


    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (this.energyContainer == null) return;
        if (getWorld().isRemote) return;
        if (getOffsetTimer() % 80 != 0) return;
        Integer currentPollution = CommonProxy.pollution.getPollution(new ChunkPosDimension(getWorld().provider.getDimension(), getWorld().getChunk(getPos()).x, getWorld().getChunk(getPos()).z));
        boolean isWorkingNow = isAbleToWork(currentPollution);
        if (isWorkingNow != isWorking) {
            this.isWorking = isWorkingNow;
            setActive(true);
        }
        if (isWorking(currentPollution)) {
            this.energyContainer.removeEnergy(getEnergyConsumedPerScrub());
            this.isWorking = true;
            Integer pollutiontoremove = ConfigHandler.polOptions.MultiblockScrubberScrubamount * (1 << (tier + 3) * 2) * (fan.getTier() - 3);

            currentPollution -= pollutiontoremove;
            if (currentPollution <= 0) currentPollution = 0;
            Chunk chunk = getWorld().getChunk(getPos());
            CEuAdditions.proxy.dimensionWisePollution.replace(new ChunkPosDimension(getWorld().provider.getDimension(), chunk.x, chunk.z), currentPollution);
            tryAndInsertItem(createStackList(pollutiontoremove / 1400));
            tryAndInsertFluid(createFluidStackList(pollutiontoremove / 1300));
            scrubRadius(getPos(), currentPollution, getTier(), pollutiontoremove);
        }
        else {
            setActive(false);
            this.isWorking = false;
        }


    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregTechDataValues.IsRunning) {
            this.isWorking = buf.readBoolean();
            getHolder().scheduleRenderUpdate();
        }
        if (dataId == GregTechDataValues.IsPaused) {
            this.isWorkingEnabled = buf.readBoolean();
            getHolder().scheduleRenderUpdate();
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        setActive(buf.readBoolean());
        setWorkingEnabled(buf.readBoolean());

    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isWorking);
        buf.writeBoolean(isWorkingEnabled);
    }
    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    @Override
    public void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("isActive", this.isWorking);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        return data;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound data) {
        super.readFromNBT(data);
        this.isWorking = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
    }

    private void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new ItemStackHandler(0);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(), this.isWorkingEnabled);
    }

    private void tryAndInsertItem(NonNullList<ItemStack> stack) {
        if(GTTransferUtils.addItemsToItemHandler(this.outputInventory, true, stack)) {
            GTTransferUtils.addItemsToItemHandler(this.outputInventory, false, stack);
        }
    }

    private void tryAndInsertFluid(NonNullList<FluidStack> stack) {
        if(GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, true, stack)) {
            GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, stack);
        }
    }

    private NonNullList<ItemStack> createStackList(int amountofitems) {
        NonNullList<ItemStack> stack = NonNullList.create();
        int newamount = amountofitems / 3;
        stack.add(OreDictUnifier.get(dust, Materials.Sulfur, newamount + 15));
        stack.add(OreDictUnifier.get(dust, Materials.Carbon, newamount + 25));
        stack.add(OreDictUnifier.get(dust, Materials.DarkAsh, newamount + 35));
        return stack;
    }
    private NonNullList<FluidStack> createFluidStackList(int amount) {
        NonNullList<FluidStack> stack = NonNullList.create();
        stack.add(Materials.CarbonMonoxide.getFluid(amount));
        return stack;
    }
    private void scrubRadius(BlockPos pos, Integer newvalue, int radius, int amount) {
        ChunkPos chunkpos = getWorld().getChunk(pos).getPos();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (this.energyContainer.getEnergyStored() > getEnergyConsumedPerScrub()) {
                    this.energyContainer.removeEnergy(getEnergyConsumedPerScrub());
                    CEuAdditions.proxy.dimensionWisePollution.replace(new ChunkPosDimension(getWorld().provider.getDimension(), chunkpos.x + x, chunkpos.z + z), newvalue);
                    tryAndInsertItem(createStackList(amount / 25));
                    tryAndInsertFluid(createFluidStackList(amount / 15));
                }
                else break;
            }
        }
    }
    public void setActive(boolean active) {
        if (this.isWorking != active) {
            this.isWorking = false;
            this.markDirty();
            if (getWorld() != null && !getWorld().isRemote) {
                this.writeCustomData(GregTechDataValues.IsRunning, buf -> buf.writeBoolean(active));
            }
        }
    }

    public boolean isWorking(int pollution) {
        return isWorking && this.energyContainer.getEnergyStored() > getEnergyConsumedPerScrub() && isWorkingEnabled && pollution > 0;
    }
    public boolean isAbleToWork(int pollution) {
        return this.energyContainer.getEnergyStored() > getEnergyConsumedPerScrub() && isWorkingEnabled && pollution > 0;
    }


    public void setWorkingEnabled(boolean isWorkingEnabled) {
        if (this.isWorkingEnabled != isWorkingEnabled) {
            this.isWorkingEnabled = isWorkingEnabled;
            markDirty();
            if (getWorld() != null && !getWorld().isRemote) {
                this.writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingEnabled));
            }
        }
    }

    protected int getEnergyConsumedPerScrub() {return BaseEuConsumptionPerScrub * (1 << (getTier() + 3) * 2);}

    @Override
    public boolean canVoidRecipeItemOutputs() {
        return true;
    }

    @Override
    public boolean canVoidRecipeFluidOutputs() {
        return true;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("ceuadditions.machine.large_scrubber.tooltip.radius", getTier() + 2, getTier() + 2));
        tooltip.add(I18n.format("ceuadditions.machine.scrubber.tooltip.consumption", getEnergyConsumedPerScrub()));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
    }



}
