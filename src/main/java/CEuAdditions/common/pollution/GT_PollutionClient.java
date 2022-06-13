package CEuAdditions.common.pollution;

import CEuAdditions.api.CEuAdLogger;
import CEuAdditions.api.ConfigHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GT_PollutionClient {

    @SubscribeEvent
    public void manipulateWaterColor(BiomeEvent.GetWaterColor event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit) {
            event.setNewColor(0x123013);
        }

    }

    @SubscribeEvent
    public void manipulateFoliageColor(BiomeEvent.GetFoliageColor event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit) {
            event.setNewColor(0xf0ff6e);
        }
    }

    @SubscribeEvent
    public void manipulateGrassColor(BiomeEvent.GetGrassColor event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit) {
            event.setNewColor(0xf0ff6e);
        }
    }

    @SubscribeEvent
    public void manipulateDensity(EntityViewRenderEvent.FogDensity event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit){
            event.setDensity((0.15f*(Math.min(GT_Pollution.mPlayerPollution/((float)ConfigHandler.polOptions.mPollutionSourRainLimit),1.0f)))+0.1f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void manipulateColor(EntityViewRenderEvent.FogColors event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit){
            event.setRed(112/255f);
            event.setGreen(112/255f);
            event.setBlue(112/255f);
        }
    }

}
