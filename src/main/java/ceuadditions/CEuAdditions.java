package ceuadditions;

import ceuadditions.api.CEuAdLogger;
import ceuadditions.api.ConfigHandler;
import ceuadditions.api.pollution.PollutionUtil;
import ceuadditions.api.world.SaveWorldPollution;
import ceuadditions.common.TileEntities.CEuAMetaTileEntities;
import gregtech.api.GTValues;
import gregtech.api.util.CapesRegistry;
import gregtech.api.util.VirtualTankRegistry;
import gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinSaveData;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Locale;

@Mod(modid = CEuAdditions.MOD_ID, name = CEuAdditions.MOD_NAME, version = CEuAdditions.VERSION, dependencies = GTValues.MOD_VERSION_DEP + "required:mixinbooter@[4.2,)")
public class CEuAdditions {

    public static final String MOD_ID = "ceuadditions";
    public static final String MOD_NAME = "CEuAdditions";
    public static final String VERSION = "1.0-alpha";

    @SidedProxy(modId = MOD_ID, clientSide = "ceuadditions.ClientProxy", serverSide = "ceuadditions.CommonProxy")
    public static CommonProxy proxy;

    public static ConfigHandler ConfigManager;
    public static PollutionUtil polutil = new PollutionUtil();

    @Mod.Instance(MOD_ID)
    public static CEuAdditions INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CEuAdLogger.logger.info("Mod Pre-Loaded!");
        Locale.setDefault(Locale.ENGLISH);
        polutil.initFilterList();
        // NetworkHandler.init();
        CEuAMetaTileEntities.init();
        // RecipeHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CEuAdLogger.logger.info("Mod Loaded!");
        proxy.onLoad();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CEuAdLogger.logger.info("Mod Post-Loaded!");
        proxy.onPostLoad();

    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
            if (!world.isRemote) {
                SaveWorldPollution saveData = (SaveWorldPollution) world.loadData(SaveWorldPollution.class, SaveWorldPollution.dataName);
                if (saveData == null) {
                    saveData = new SaveWorldPollution(SaveWorldPollution.dataName);
                    world.setData(SaveWorldPollution.dataName, saveData);
                }
                SaveWorldPollution.setInstance(saveData);
            }
        }
    }

    @Mod.EventHandler
    public static void onServerStopped(FMLServerStoppedEvent event) {
        CEuAdditions.proxy.loadedChunks.clear();
        CEuAdditions.proxy.dimensionWisePollution.clear();
    }
}
