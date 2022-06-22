/* package ceuadditions.api.capability;

import gregtech.api.GTValues;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GT_Pollution implements Capability.IStorage<IPollutedChunk>, ICapabilityProvider {

    private static gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinSaveData INSTANCE;
    public static final String dataName = GTValues.MODID + ".bedrockFluidVeinData";




 /*   @Override
    public void readFromNBT(NBTTagCompound nbt) {
        Integer pollution = nbt.getInteger("pollutionAmount");
        ChunkPosDimension coords = new ChunkPosDimension(nbt.getInteger("dimension"), nbt.getInteger("x"), nbt.getInteger("z"));
        if (coords != null) {
            CEuAdditions.proxy.dimensionWisePollution.put(coords, pollution);
        }
    }

    @Override
    public @Nonnull
    NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
        NBTTagList pollutionList = new NBTTagList();
        HashMap<ChunkPosDimension, Integer> chunks = CEuAdditions.proxy.dimensionWisePollution;
        for (ChunkPosDimension chunkpos : chunks.keySet()) {
            NBTTagCompound tag = chunkpos.writeToNBT();
            tag.setTag("polinfo");
        }
        nbt.setInteger("pollutionAmount", CEuAdditions.proxy.dimensionWisePollution.(1));

        return nbt;
    }





    public static void setDirty() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && INSTANCE != null)
            INSTANCE.markDirty();
    }

    public static void setInstance(gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinSaveData in) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            INSTANCE = in;
    }
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IPollutedChunk> capability, IPollutedChunk instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();

        if(instance.isPolluted()) {
            nbt.setInteger("pollutionAmount", instance.getPollutionAmount());
        }

        return nbt;
    }

    @Override
    public void readNBT(Capability<IPollutedChunk> capability, IPollutedChunk instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound nbtc = (NBTTagCompound) nbt;
        if (nbtc.getInteger("pollutionAmount") >= 0) {
            instance.setPollutionAmount(nbtc.getInteger("pollutionAmount"));
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return true;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability.getDefaultInstance();
    }
}
*/