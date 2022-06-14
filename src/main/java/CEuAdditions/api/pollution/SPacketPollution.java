/* package CEuAdditions.api.pollution;

import CEuAdditions.common.pollution.GT_Pollution;
import gregtech.api.net.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NoArgsConstructor
public class SPacketPollution implements IPacket {

    private Integer pollution;

    public SPacketPollution(Integer pollution) {this.pollution = pollution;}

    @Override
    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(pollution);
    }

    @Override
    public void decode(PacketBuffer packetBuffer) {
        this.pollution = packetBuffer.readInt();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void executeClient(NetHandlerPlayClient handler) {
        GT_Pollution.mPlayerPollution = pollution;
    }
}

 */