package CEuAdditions;

import CEuAdditions.api.ConfigHandler;
// import CEuAdditions.api.net.NetworkHandler;
import CEuAdditions.api.pollution.PollutionUtil;
import CEuAdditions.common.TileEntities.CEuAMetaTileEntities;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Locale;

@Mod(modid = CEuAdditions.MOD_ID, name = CEuAdditions.MOD_NAME, version = CEuAdditions.VERSION, dependencies = GTValues.MOD_VERSION_DEP + "required:mixinbooter@[4.2,)")
public class CEuAdditions {

    public static final String MOD_ID = "ceuadditions";
    public static final String MOD_NAME = "CEuAdditions";
    public static final String VERSION = "1.0A";

    @SidedProxy(modId = MOD_ID, clientSide = "CEuAdditions.ClientProxy", serverSide = "CEuAdditions.CommonProxy")
    public static CommonProxy proxy;

    public static ConfigHandler ConfigManager;
    public static PollutionUtil polutil = new PollutionUtil();

    @Mod.Instance(MOD_ID)
    public static CEuAdditions INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Locale.setDefault(Locale.ENGLISH);
        polutil.initFilterList();
        // NetworkHandler.init();
        CEuAMetaTileEntities.init();
    }


}
