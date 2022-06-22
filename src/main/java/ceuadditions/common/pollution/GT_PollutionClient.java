package ceuadditions.common.pollution;

import ceuadditions.api.ConfigHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_PollutionClient {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void manipulateDensity(EntityViewRenderEvent.FogDensity event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit){
            event.setDensity((0.15f*(Math.min(GT_Pollution.mPlayerPollution/((float)ConfigHandler.polOptions.mPollutionSourRainLimit),1.0f)))+0.1f);
            event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void manipulateColor(EntityViewRenderEvent.FogColors event) {
        if (GT_Pollution.mPlayerPollution > ConfigHandler.polOptions.mPollutionSmogLimit){
            event.setRed(112/255f);
            event.setGreen(112/255f);
            event.setBlue(112/255f);
        }
    }

}
