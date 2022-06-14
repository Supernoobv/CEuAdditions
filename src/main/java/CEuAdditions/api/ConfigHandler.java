package CEuAdditions.api;

import CEuAdditions.CEuAdditions;
import net.minecraftforge.common.config.Config;

@Config(modid = CEuAdditions.MOD_ID)
public class ConfigHandler {

    @Config.Name("Pollution")
    @Config.Comment("Config options for pollution-related features")
    public static PollutionOptions polOptions = new PollutionOptions();



    public static class PollutionOptions {

        @Config.RangeInt(min = 1000)
        @Config.Comment({"The Amount of Pollution a chunk can have before smog effects take place."})
        public int mPollutionSmogLimit = 500000;

        @Config.RangeInt(min = 1000)
        @Config.Comment({"The amount of pollution a chunk can have before poison effects take place."})
        public int mPollutionPoisonLimit = 750000;

        @Config.RangeInt(min = 1000)
        @Config.Comment({"The amount of pollution a chunk can have before vegetation is damaged."})
        public int mPollutionVegetationLimit = 1000000;

        @Config.RangeInt(min = 1000)
        @Config.Comment({"The amount of pollution a chunk can have before sour rain damages the surroundings."})
        public int mPollutionSourRainLimit = 2000000;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution will be scrubbed by a pollution scrubber.", "Each tier multiplies this value by 2."})
        public int PollutionScrubberScrubAmount = 200;


        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution Brozne Boilers will produce per tick."})
        public int BronzeBoilerEmissionRate = 2;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution Steel Boilers will produce per tick."})
        public int SteelBoilerEmissionRate = 3;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution Gas Turbines will produce per tick.", "Note: EACH TIER IS MULTIPLIED BY 2"})
        public int GasTurbineEmissionRate = 2;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution Combustion Generators will produce per tick."})
        public int CombustionEmissionRate = 4;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Electric Blast Furnace will produce per tick."})
        public int EBFEmissionRate = 6;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Electric Blast Furnace will produce per tick."})
        public int PBFEmissionRate = 9;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Implosion Compressor will produce per tick."})
        public int ImplosionCompressorEmissionRate = 100;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Multi Smelter will produce per tick."})
        public int MultiSmelterEmissionRate = 6;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Pyrolyse Oven will produce per tick."})
        public int PyrolyseOvenEmissionRate = 12;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Large Boiler will produce per tick.", "Tiered multiblocks produce x2 pollution per tier."})
        public int LargeBoilerEmissionRate = 16;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Large Gas Turbine will produce per tick."})
        public int LargeGasTurbineEmissionRate = 15;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution a Large Combustion Engine will produce per tick."})
        public int LargeCombustionEmissionRate = 19;

        @Config.RangeInt(min = 1)
        @Config.Comment({"How much pollution an explosion will emit", "This is the base value, it will be multiplied depending on the explosion power."})
        public int ExplosionEmissionAmount = 50000;

    }

}
