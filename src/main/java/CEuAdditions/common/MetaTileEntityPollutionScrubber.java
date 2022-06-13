package CEuAdditions.common;

import CEuAdditions.CommonProxy;
import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityPollutionScrubber extends SimpleMachineMetaTileEntity {

    public int pollutionReduction = 0;
    protected int baseEfficiency = 3000;
    protected int SLOT_ROTOR = 0;
    protected int SLOT_FILTER = 1;

    public MetaTileEntityPollutionScrubber(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier) {
        super(metaTileEntityId, recipeMap, renderer, tier, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPollutionScrubber(this.metaTileEntityId, this.workable.getRecipeMap(), this.renderer, this.getTier());
    }

    @Override
    public void update() {
        if (this.energyContainer.getEnergyStored() > GTValues.V[this.getTier()]) {

            int currentPollution = CommonProxy.pollution.getPollution(getWorld().getChunk(getPos()).getPos());

            ItemStack Rotor = this.itemInventory.getStackInSlot(SLOT_ROTOR);
        }
    }
}
