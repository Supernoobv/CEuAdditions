package ceuadditions.api.values;

import ceuadditions.api.capability.IHugeFan;
import ceuadditions.common.TileEntities.Pollution.multi.parts.MetaTileEntityHugeFan;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class MultiblockAbilities {
    public static final MultiblockAbility<IHugeFan> HUGE_FAN = new MultiblockAbility<>("huge_fan");

}
