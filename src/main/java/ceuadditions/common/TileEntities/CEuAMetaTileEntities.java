package ceuadditions.common.TileEntities;

import ceuadditions.CEuAdditions;
import ceuadditions.common.TileEntities.Pollution.MetaTileEntityPollutionScrubber;
import ceuadditions.common.TileEntities.Pollution.multi.MetaTileEntityLargePollutionScrubber;
import ceuadditions.common.TileEntities.Pollution.multi.parts.MetaTileEntityHugeFan;
import ceuadditions.common.client.ClientHandler;
import ceuadditions.common.recipe.CEuARecipeMaps;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.util.ResourceLocation;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class CEuAMetaTileEntities {

    public static SimpleMachineMetaTileEntity[] SCRUBBER = new MetaTileEntityPollutionScrubber[GTValues.V.length];

    public static MetaTileEntityLargePollutionScrubber LARGE_SCRUBBER;
    // Multiblock Parts
    public static MetaTileEntityHugeFan[] HUGE_FAN = new MetaTileEntityHugeFan[GTValues.V.length];
    public static void init() {
        SCRUBBER[1] = registerMetaTileEntity(21000, new MetaTileEntityPollutionScrubber(location("scrubber.lv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 1));
        SCRUBBER[2] = registerMetaTileEntity(21001, new MetaTileEntityPollutionScrubber(location("scrubber.mv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 2));
        SCRUBBER[3] = registerMetaTileEntity(21002, new MetaTileEntityPollutionScrubber(location("scrubber.hv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 3));
        SCRUBBER[4] = registerMetaTileEntity(21003, new MetaTileEntityPollutionScrubber(location("scrubber.ev"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 4));
        SCRUBBER[5] = registerMetaTileEntity(21004, new MetaTileEntityPollutionScrubber(location("scrubber.iv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 5));
        if(getMidTier("scrubber")) {
            SCRUBBER[6] = registerMetaTileEntity(21005, new MetaTileEntityPollutionScrubber(location("scrubber.luv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 6));
            SCRUBBER[7] = registerMetaTileEntity(21006, new MetaTileEntityPollutionScrubber(location("scrubber.zpm"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 7));
            SCRUBBER[8] = registerMetaTileEntity(21007, new MetaTileEntityPollutionScrubber(location("scrubber.uv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 8));
        }
        if(getHighTier("scrubber")) {
            SCRUBBER[9] = registerMetaTileEntity(21008, new MetaTileEntityPollutionScrubber(location("scrubber.uhv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 9));
            SCRUBBER[10] = registerMetaTileEntity(21009, new MetaTileEntityPollutionScrubber(location("scrubber.uev"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 10));
            SCRUBBER[11] = registerMetaTileEntity(21010, new MetaTileEntityPollutionScrubber(location("scrubber.uiv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 11));
            SCRUBBER[12] = registerMetaTileEntity(21011, new MetaTileEntityPollutionScrubber(location("scrubber.umv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 12));
            SCRUBBER[13] = registerMetaTileEntity(21012, new MetaTileEntityPollutionScrubber(location("scrubber.uxv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 13));

        }

        LARGE_SCRUBBER = registerMetaTileEntity(21013, new MetaTileEntityLargePollutionScrubber(location("large_scrubber.hv"), CEuARecipeMaps.LARGE_SCRUBBING_RECIPES, 1, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_STEEL_GEARBOX), Textures.CLEAN_STAINLESS_STEEL_CASING, false, Textures.LARGE_STEAM_TURBINE_OVERLAY));
        LARGE_SCRUBBER = registerMetaTileEntity(21014, new MetaTileEntityLargePollutionScrubber(location("large_scrubber.ev"), CEuARecipeMaps.LARGE_SCRUBBING_RECIPES, 2, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_GEARBOX), Textures.STABLE_TITANIUM_CASING, false, Textures.LARGE_STEAM_TURBINE_OVERLAY));
        LARGE_SCRUBBER = registerMetaTileEntity(21015, new MetaTileEntityLargePollutionScrubber(location("large_scrubber.iv"), CEuARecipeMaps.LARGE_SCRUBBING_RECIPES, 3, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), Textures.ROBUST_TUNGSTENSTEEL_CASING, false, Textures.LARGE_STEAM_TURBINE_OVERLAY));

        for(int i = 0; i < 8; i++) {
            String hugefanid = "huge_fan." + GTValues.VN[i].toLowerCase();
            HUGE_FAN[i] = new MetaTileEntityHugeFan(location(hugefanid), i);
            registerMetaTileEntity(21016 + i, HUGE_FAN[i]);
        }

        COMBUSTION_GENERATOR[3] = registerMetaTileEntity(21024, new SimpleGeneratorMetaTileEntity(location("combustion_generator.ev"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        STEAM_TURBINE[3] = registerMetaTileEntity(21025, new SimpleGeneratorMetaTileEntity(location("steam_turbine.ev"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 4, GTUtility.steamGeneratorTankSizeFunction));
        GAS_TURBINE[3] = registerMetaTileEntity(21026, new SimpleGeneratorMetaTileEntity(location("gas_turbine.ev"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));

    }

    private static ResourceLocation location(String name) {
        return new ResourceLocation(CEuAdditions.MOD_ID, name);
    }
}
