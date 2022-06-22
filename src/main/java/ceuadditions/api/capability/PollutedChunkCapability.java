/* package ceuadditions.api.capability;

import ceuadditions.CEuAdditions;
import ceuadditions.api.ConfigHandler;
import ceuadditions.api.pollution.PollutionUtil;
import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class PollutedChunkCapability implements IPollutedChunk{

    private int pollutionAmount = 0;
    @Override
    public boolean isPolluted() {
        return pollutionAmount > 0;
    }

    @Override
    public int getPollutionAmount() {
        return pollutionAmount;
    }

    @Override
    public void setPollutionAmount(int amount) {
        pollutionAmount = amount;
    }

    @Override
    public void tickPollutionInWorld(int tickid) {
        if (!(tickid == 0)) return;
        ArrayList<ChunkPosDimension> chunks = CEuAdditions.proxy.loadedChunks;
        if (CEuAdditions.proxy.loadedChunks.size() == 0)
            return;
        for (ChunkPosDimension chunk : CEuAdditions.proxy.loadedChunks) {
            int tPollution = getPollutionAmount();
            if (tPollution <= 0) tPollution = 0;
            tPollution = (int)(0.99f*tPollution);
            tPollution -= 2000;
            if (tPollution <= 0) tPollution = 0;
            if (tPollution > 50000) {

                ChunkPosDimension[] tNeighbors = new ChunkPosDimension[4];
                tNeighbors[0] = (new ChunkPosDimension(chunk.dimension , chunk.x + 1, chunk.z));
                tNeighbors[1] = (new ChunkPosDimension(chunk.dimension, chunk.x - 1, chunk.z));
                tNeighbors[2] = (new ChunkPosDimension(chunk.dimension, chunk.x, chunk.z + 1));
                tNeighbors[3] = (new ChunkPosDimension(chunk.dimension, chunk.x, chunk.z - 1));
                for (ChunkPosDimension neighborPosition : tNeighbors) {
                    if (!chunks.contains(neighborPosition))
                        CEuAdditions.proxy.dimensionWisePollution.put(neighborPosition, tPollution);
                    Integer neighborPollution = CEuAdditions.proxy.dimensionWisePollution.get(neighborPosition);
                    if (neighborPollution * 6 < tPollution * 5) {
                        int tDiff = tPollution - neighborPollution;
                        tDiff = tDiff / 10;
                        neighborPollution += tDiff;
                        CEuAdditions.proxy.dimensionWisePollution.put(neighborPosition, tPollution);

                    }
                }

                if (tPollution > ConfigHandler.polOptions.mPollutionSmogLimit) {
                    AxisAlignedBB axisalignedbb = new AxisAlignedBB(chunk.x, 0, chunk.z << 4, (chunk.x) + 16, 256, (chunk.z << 4) + 16);
                    List<EntityLivingBase> entitys = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                    for (EntityLivingBase entity : entitys) {
                        switch (tRan.nextInt((3))) {
                            default:
                                entity.addPotionEffect(new PotionEffect((PollutionUtil.hunger), tPollution / 500000));
                            case 1:
                                entity.addPotionEffect(new PotionEffect(PollutionUtil.nausea, Math.min(tPollution / 2000, 1000), 1));
                            case 2:
                                entity.addPotionEffect(new PotionEffect(PollutionUtil.poison, Math.min(tPollution / 4000, 1000), tPollution / 500000));
                            case 3:
                                entity.addPotionEffect(new PotionEffect(PollutionUtil.blindness, Math.min(tPollution / 2000, 1000), 1));
                        }
                    }


                    if (tPollution > ConfigHandler.polOptions.mPollutionPoisonLimit) {
                        for (EntityLivingBase entity : entitys) {
                            switch (tRan.nextInt(4)) {
                                default:
                                    entity.addPotionEffect(new PotionEffect(PollutionUtil.hunger, tPollution / 500000));
                                case 1:
                                    entity.addPotionEffect(new PotionEffect(PollutionUtil.nausea, Math.min(tPollution / 2000, 1000), 1));
                                case 2:
                                    entity.addPotionEffect(new PotionEffect(PollutionUtil.poison, Math.min(tPollution / 4000, 1000), tPollution / 500000));
                                case 3:
                                        entity.addPotionEffect(new PotionEffect(PollutionUtil.blindness, Math.min(tPollution / 2000, 1000), 1));
                                }
                            }
                        }


                        if (tPollution > ConfigHandler.polOptions.mPollutionVegetationLimit) {
                            int f = 20;
                            for (; f < (tPollution / 25000); f++) {
                                int x = (chunk.x << 4) + tRan.nextInt(16);
                                int y = 60 + (-f + tRan.nextInt(f * 2 + 1));
                                int z = (chunk.z << 4) + tRan.nextInt(16);
                                damageBlock(world, x, y, z, tPollution > ConfigHandler.polOptions.mPollutionSourRainLimit);
                            }
                        }

                    }
                }

            }

        }
    }
    }

}


 */