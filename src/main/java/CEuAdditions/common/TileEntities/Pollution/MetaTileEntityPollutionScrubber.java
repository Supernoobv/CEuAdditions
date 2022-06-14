package CEuAdditions.common.TileEntities.Pollution;

import CEuAdditions.CEuAdditions;
import CEuAdditions.CommonProxy;
import CEuAdditions.api.ConfigHandler;
import CEuAdditions.api.values.GregTechDataValues;
import CEuAdditions.common.client.ClientHandler;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.sound.GTSounds;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

import static gregtech.api.unification.ore.OrePrefix.*;
public class MetaTileEntityPollutionScrubber extends SimpleMachineMetaTileEntity {

    private boolean isWorking;
    private static final int BaseEuConsumptionPerScrub = 25;
    public MetaTileEntityPollutionScrubber(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier) {
        super(metaTileEntityId, recipeMap, renderer, tier, true);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPollutionScrubber(this.metaTileEntityId, this.workable.getRecipeMap(), this.renderer, this.getTier());
    }

    @Override
    public void update() {
        super.update();

        boolean isWorkingNow = energyContainer.getEnergyStored() >= getEnergyConsumedPerScrub() && isBlockRedstonePowered();
        int currentPollution = CommonProxy.pollution.getPollution(getWorld().getChunk(getPos()).getPos());

        if (getWorld().isRemote) return;
        if (!(currentPollution > 0)) return;
        if (getOffsetTimer() % 20 != 0) return;
        if (isWorkingNow != isWorking) {
            this.isWorking = isWorkingNow;
            writeCustomData(GregTechDataValues.IsRunning, buffer -> buffer.writeBoolean(isWorkingNow));
        }
        if (getOffsetTimer() % 20 != 0) return;
        if (isWorkingNow) {
            int pollutiontoremove = (ConfigHandler.polOptions.PollutionScrubberScrubAmount * (1 << getTier() - 1) * 2);
            int amountofitems = pollutiontoremove / 20; int amountoffluids = pollutiontoremove / 10;
            currentPollution -= pollutiontoremove;
            if (currentPollution <= 0) currentPollution = 0;
            CEuAdditions.proxy.dimensionWisePollution.put(getWorld().getChunk(getPos()).getPos(), currentPollution);
            tryAndInsertItem(createStackList(amountofitems));
            tryAndInsertFluid(createFluidStackList(amountoffluids));
            energyContainer.removeEnergy(getEnergyConsumedPerScrub());
        }



    }


    public void tryAndInsertItem(NonNullList<ItemStack> stack) {
        if(GTTransferUtils.addItemsToItemHandler(getExportItems(), true, stack)) {
            GTTransferUtils.addItemsToItemHandler(getExportItems(), false, stack);
        }
    }

    public void tryAndInsertFluid(NonNullList<FluidStack> stack) {
        if(GTTransferUtils.addFluidsToFluidHandler(getExportFluids(), true, stack)) {
            GTTransferUtils.addFluidsToFluidHandler(getExportFluids(), false, stack);
        }
    }

    public NonNullList<ItemStack> createStackList(int amountofitems) {
        NonNullList<ItemStack> stack = NonNullList.create();
        stack.add(OreDictUnifier.get(dust, Materials.Sulfur, amountofitems + 2));
        stack.add(OreDictUnifier.get(dust, Materials.Carbon, amountofitems + 5));
        stack.add(OreDictUnifier.get(dust, Materials.DarkAsh, amountofitems + 3));
        return stack;
    }
    public NonNullList<FluidStack> createFluidStackList(int amount) {
        NonNullList<FluidStack> stack = NonNullList.create();
        stack.add(Materials.CarbonMonoxide.getFluid(amount));
        return stack;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        OrientedOverlayRenderer renderer = ClientHandler.SCRUBBER_OVERLAY;
        renderer.renderOrientedState(renderState, translation, pipeline, Cuboid6.full, this.getFrontFacing(), isWorking, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> toolTip, boolean advanced) {
        toolTip.add(I18n.format("ceuadditions.machine.scrubber.tooltip", ConfigHandler.polOptions.PollutionScrubberScrubAmount));
        toolTip.add(I18n.format("gregtech.universal.tooltip.max_voltage_in", energyContainer.getInputVoltage(), GTValues.VNF[getTier()]));
        toolTip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        toolTip.add(I18n.format("gregtech.machine.item_controller.tooltip.redstone"));
        toolTip.add(I18n.format("ceuadditions.machine.scrubber.tooltip.consumption", getEnergyConsumedPerScrub()));

    }

    @Override
    public SoundEvent getSound() {return GTSounds.TURBINE;}

    public class RecipeLogicScrubber extends RecipeLogicEnergy {
        public RecipeLogicScrubber(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, Supplier<IEnergyContainer> energyContainer) {
            super(tileEntity, recipeMap, energyContainer);
        }
    }
    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isWorking = buf.readBoolean();
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isWorking);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregTechDataValues.IsRunning) {
            this.isWorking = buf.readBoolean();
            getHolder().scheduleRenderUpdate();
        }
    }


    @Override
    public boolean isActive() {
        return isWorking;
    }

    protected int getEnergyConsumedPerScrub() {return BaseEuConsumptionPerScrub * (1 << (getTier() - 1) * 2);}
}
