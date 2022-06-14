package CEuAdditions;

// import CEuAdditions.common.item.CEuAMetaItems;
import CEuAdditions.common.pollution.GT_Pollution;
import CEuAdditions.common.pollution.GT_PollutionClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = CEuAdditions.MOD_ID)
    public class CommonProxy {

        public ArrayList<ChunkPos> loadedChunks = new ArrayList<>(1024);
        public HashMap<ChunkPos, Integer> dimensionWisePollution = new HashMap<>(128);
        public static GT_Pollution pollution = new GT_Pollution();

        public CommonProxy() {
            MinecraftForge.EVENT_BUS.register(this);
            MinecraftForge.EVENT_BUS.register(new GT_PollutionClient());
        }
        public void preLoad() {
          //  CEuAMetaItems.init();
        }

        public void onLoad() {

        }

        public void onPostLoad() {

        }

        @SubscribeEvent
        public static void syncConfigChanges(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(CEuAdditions.MOD_ID)) {
                ConfigManager.sync(CEuAdditions.MOD_ID, Config.Type.INSTANCE);
            }
        }

/*        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            CEuAdLogger.logger.info("Registering Recipes...");
        }
 */
        @SubscribeEvent
        public void onChunkLoadEvent(ChunkDataEvent.Load event) {
            if (!dimensionWisePollution.containsKey(event.getChunk())) {
                dimensionWisePollution.put(event.getChunk().getPos(), 0);
            }
            loadedChunks.add(event.getChunk().getPos());
        }

        @SubscribeEvent
        public void onChunkUnLoadEvent(ChunkDataEvent.Unload event) {
            loadedChunks.remove(event.getChunk());

        }

        @SubscribeEvent
        public void onWorldTickEvent(TickEvent.WorldTickEvent event) {
            if(event.side.isServer()) {
                if (loadedChunks != null && dimensionWisePollution != null) pollution.onWorldTick(event);
            }

        }
        @SubscribeEvent
        public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
            if((event.side.isServer()) && (event.phase == TickEvent.Phase.END) && (!event.player.isDead)) {
                Integer tpollution =0;
                Chunk chunk = new Chunk(event.player.world, event.player.chunkCoordX, event.player.chunkCoordZ);
                tpollution = pollution.getPollution(chunk.getPos());
                if (tpollution == null)
                    return;

                if(event.player instanceof EntityPlayerMP) {
                    GT_Pollution.mPlayerPollution = tpollution;
                  //  EntityPlayerMP playermp = (EntityPlayerMP) event.player;
                  //  SPacketPollution packet = new SPacketPollution(pollution.getPollution(chunk.getPos()));
                  //  NetworkHandler.channel.sendTo(packet.toFMLPacket(), playermp);
                }
            }
        }
        @Mod.EventHandler
        public void onServerStart(FMLServerStartedEvent event) {
            loadedChunks.clear();

        }





    }

