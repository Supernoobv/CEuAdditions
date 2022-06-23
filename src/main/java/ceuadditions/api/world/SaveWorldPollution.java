package ceuadditions.api.world;

import ceuadditions.CEuAdditions;
import ceuadditions.api.CEuAdLogger;
import gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinHandler;
import gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinSaveData;
import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveWorldPollution extends WorldSavedData {

    private static SaveWorldPollution INSTANCE;
    public static final String dataName = CEuAdditions.MOD_ID + ".SaveWorldPollution";

    public SaveWorldPollution(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList pollutionList = nbt.getTagList("pollutionTotal", 10);
        CEuAdditions.proxy.dimensionWisePollution.clear();
        for (int i = 0; i < pollutionList.tagCount(); i++) {
            NBTTagCompound tag = pollutionList.getCompoundTagAt(i);
            ChunkPosDimension coords = new ChunkPosDimension(tag.getInteger("dimension"), tag.getInteger("x"), tag.getInteger("z"));
            if (coords != null) {
                Integer pollution = tag.getInteger("pollutionAmount");
                CEuAdditions.proxy.dimensionWisePollution.put(coords, pollution);
            }
        }
    }

    @Override
    public @Nonnull NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
        NBTTagList pollutionList = new NBTTagList();
        for (Map.Entry<ChunkPosDimension, Integer> e : CEuAdditions.proxy.dimensionWisePollution.entrySet()) {
            if (e.getKey() != null && e.getValue() != null) {
                NBTTagCompound tag = e.getKey().writeToNBT();
                tag.setInteger("pollutionAmount", e.getValue());
                pollutionList.appendTag(tag);
            }
        }
        nbt.setTag("pollutionTotal", pollutionList);

        return nbt;
    }

    public static void setDirty() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && INSTANCE != null)
            INSTANCE.markDirty();
    }

    public static void setInstance(SaveWorldPollution in) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            INSTANCE = in;
    }

}
