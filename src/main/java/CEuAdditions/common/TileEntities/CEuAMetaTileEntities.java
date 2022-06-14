package CEuAdditions.common.TileEntities;

import CEuAdditions.CEuAdditions;
import CEuAdditions.common.TileEntities.Pollution.MetaTileEntityPollutionScrubber;
import CEuAdditions.common.client.ClientHandler;
import CEuAdditions.common.recipe.CEuARecipeMaps;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import net.minecraft.util.ResourceLocation;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class CEuAMetaTileEntities {

    public static SimpleMachineMetaTileEntity[] SCRUBBER = new MetaTileEntityPollutionScrubber[GTValues.V.length];

    public static void init() {
        SCRUBBER[1] = registerMetaTileEntity(10250, new MetaTileEntityPollutionScrubber(location("scrubber.lv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 1));
        SCRUBBER[2] = registerMetaTileEntity(10251, new MetaTileEntityPollutionScrubber(location("scrubber.mv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 2));
        SCRUBBER[3] = registerMetaTileEntity(10252, new MetaTileEntityPollutionScrubber(location("scrubber.hv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 3));
        SCRUBBER[4] = registerMetaTileEntity(10253, new MetaTileEntityPollutionScrubber(location("scrubber.ev"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 4));
        SCRUBBER[5] = registerMetaTileEntity(10254, new MetaTileEntityPollutionScrubber(location("scrubber.iv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 5));
        if(getMidTier("scrubber")) {
            SCRUBBER[6] = registerMetaTileEntity(10255, new MetaTileEntityPollutionScrubber(location("scrubber.luv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 6));
            SCRUBBER[7] = registerMetaTileEntity(10256, new MetaTileEntityPollutionScrubber(location("scrubber.zpm"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 7));
            SCRUBBER[8] = registerMetaTileEntity(10257, new MetaTileEntityPollutionScrubber(location("scrubber.uv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 8));
        }
        if(getHighTier("scrubber")) {
            SCRUBBER[9] = registerMetaTileEntity(10258, new MetaTileEntityPollutionScrubber(location("scrubber.uhv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 9));
            SCRUBBER[10] = registerMetaTileEntity(10259, new MetaTileEntityPollutionScrubber(location("scrubber.uev"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 10));
            SCRUBBER[11] = registerMetaTileEntity(10260, new MetaTileEntityPollutionScrubber(location("scrubber.uiv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 11));
            SCRUBBER[12] = registerMetaTileEntity(10261, new MetaTileEntityPollutionScrubber(location("scrubber.umv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 12));
            SCRUBBER[13] = registerMetaTileEntity(10262, new MetaTileEntityPollutionScrubber(location("scrubber.uxv"), CEuARecipeMaps.SCRUBBING_RECIPES, ClientHandler.SCRUBBER_OVERLAY, 13));

        }




    }

    private static ResourceLocation location(String name) {
        return new ResourceLocation(CEuAdditions.MOD_ID, name);
    }
}
