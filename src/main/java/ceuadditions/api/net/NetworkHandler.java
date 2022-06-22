/* package CEuAdditions.api.net;

import CEuAdditions.api.pollution.SPacketPollution;
import gregtech.api.net.PacketHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.api.net.PacketHandler.registerClientExecutor;

public class NetworkHandler {
    public static void init() {
        PacketHandler.registerPacket(SPacketPollution.class);

        if (FMLCommonHandler.instance().getSide().isClient()) {
            clientInit();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        registerClientExecutor(SPacketPollution.class);
    }
}


 */