package ceuadditions;

// import CEuAdditions.common.item.CEuAMetaItems;

import ceuadditions.api.world.SaveWorldPollution;
import ceuadditions.common.pollution.GT_Pollution;
import ceuadditions.common.pollution.GT_PollutionClient;
import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = CEuAdditions.MOD_ID)
    public class CommonProxy {

        public ArrayList<ChunkPosDimension> loadedChunks = new ArrayList<>(1024);
        public HashMap<ChunkPosDimension, Integer> dimensionWisePollution = new HashMap<>(2048);
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
            if (!dimensionWisePollution.containsKey(new ChunkPosDimension(event.getWorld().provider.getDimension(), event.getChunk().x, event.getChunk().z))) {
                dimensionWisePollution.put(new ChunkPosDimension(event.getChunk().getWorld().provider.getDimension(), event.getChunk().x, event.getChunk().z), 0);
            }
            Chunk chunk = event.getChunk();
            loadedChunks.add(new ChunkPosDimension(chunk.getWorld().provider.getDimension(), chunk.x, chunk.z));
        }

        @SubscribeEvent
        public void onChunkUnLoadEvent(ChunkDataEvent.Unload event) {
            loadedChunks.remove(new ChunkPosDimension(event.getWorld().provider.getDimension(), event.getChunk().x, event.getChunk().z));

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
                tpollution = pollution.getPollution(new ChunkPosDimension(event.player.getEntityWorld().provider.getDimension(), chunk.x, chunk.z));
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
    @SubscribeEvent
    public static void onWorldUnloadEvent(WorldEvent.Unload event) {
        SaveWorldPollution.setDirty();
    }

    @SubscribeEvent
    public static void onWorldSaveEvent(WorldEvent.Save event) {
        SaveWorldPollution.setDirty();
    }




    }

