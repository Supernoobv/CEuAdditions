package CEuAdditions.common.pollution;

import CEuAdditions.CEuAdditions;
import CEuAdditions.api.ConfigHandler;
import CEuAdditions.api.pollution.PollutionUtil;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.XSTR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import java.util.ArrayList;
import java.util.List;

public class GT_Pollution {

    // ALL RIGHTS AND CREDITS GO TO GT5U FOR THIS CLASS, ONLY CHANGES MADE WERE SO IT WOULD WORK WITH 1.12.2!!
    // And also minor changes
    // (Don't judge me i just used this as a starting point, will be changed in the future!!)
    private static XSTR tRan = new XSTR();
    private static final short cycleLen=1200;
    public static int mPlayerPollution;
    private PollutionUtil util = new PollutionUtil();
    public World aWorld;
    public GT_Pollution() {}

    public GT_Pollution(World world) {
        aWorld = world;
    }


    public void onWorldTick(TickEvent.WorldTickEvent event){
        CEuAdditions.proxy.pollution.tickPollutionInWorld((int)(event.world.getWorldTime()%cycleLen), event.world);
    }

    private void tickPollutionInWorld(int tickID, World world) {
        if (!(tickID == 0)) return;
        ArrayList<ChunkPos> chunks = CEuAdditions.proxy.loadedChunks;
        if (CEuAdditions.proxy.loadedChunks.size() == 0)
            return;
        for (ChunkPos chunk : CEuAdditions.proxy.loadedChunks) {
            int tPollution = getPollution(chunk);
            if (tPollution <= 0) tPollution = 0;
            tPollution = (int)(0.99f*tPollution);
            tPollution -= 2000;
            if (tPollution <= 0) tPollution = 0;
            if (tPollution > 50000) {

                ChunkPos[] tNeighbors = new ChunkPos[4];
                tNeighbors[0] = (new ChunkPos(chunk.x + 1, chunk.z));
                tNeighbors[1] = (new ChunkPos(chunk.x - 1, chunk.z));
                tNeighbors[2] = (new ChunkPos(chunk.x, chunk.z + 1));
                tNeighbors[3] = (new ChunkPos(chunk.x, chunk.z - 1));
                for (ChunkPos neighborPosition : tNeighbors) {
                    if (!CEuAdditions.proxy.loadedChunks.contains(neighborPosition))
                        CEuAdditions.proxy.dimensionWisePollution.put(neighborPosition, tPollution);

                    Integer neighborPollution = CEuAdditions.proxy.dimensionWisePollution.get(neighborPosition);
                    if (neighborPollution * 6 < tPollution * 5) {
                        int tDiff = tPollution - neighborPollution;
                        tDiff = tDiff / 10;
                        neighborPollution += tDiff;
                        tPollution -= tDiff;
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
                            if (!util.checkIfPlayerHasNano(entity) || (!util.checkIfPlayerHasQuarkTech(entity))) {
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
    private static void damageBlock(World world, int x, int y, int z, boolean sourRain) {
        if (world.isRemote) return;
        IBlockState blockstate = world.getBlockState(new BlockPos(x, y, z));
        Block block = blockstate.getBlock();
        int meta = block.getMetaFromState(blockstate);
        BlockPos pos = new BlockPos(x, y, z);
        if (block == Blocks.AIR || block == Blocks.STONE || block == Blocks.SAND || block == Blocks.DEADBUSH) return;


        if (block == Blocks.LEAVES || block == Blocks.LEAVES2 || block.getMaterial(blockstate) == Material.LEAVES)
            world.setBlockToAir(pos);
        if (block == Blocks.REEDS) {
            block.dropBlockAsItem(world, pos, blockstate, 0);
            world.setBlockToAir(pos);
        }
        if (block == Blocks.TALLGRASS)
            world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
        if (block == Blocks.VINE) {
            block.dropBlockAsItem(world, pos, blockstate, 0);
            world.setBlockToAir(pos);
        }
        if (block == Blocks.WATERLILY || block == Blocks.WHEAT || block == Blocks.CACTUS ||
                block.getMaterial(blockstate) == Material.CACTUS || block == Blocks.MELON_BLOCK || block == Blocks.MELON_STEM) {
            block.dropBlockAsItem(world, pos, blockstate, 0);
            world.setBlockToAir(pos);
        }
        if (block == Blocks.RED_FLOWER || block == Blocks.YELLOW_FLOWER || block == Blocks.CARROTS ||
                block == Blocks.POTATOES || block == Blocks.PUMPKIN || block == Blocks.PUMPKIN_STEM) {
            block.dropBlockAsItem(world, pos, blockstate, 0);
            world.setBlockToAir(pos);
        }
        if (block == Blocks.SAPLING || block.getMaterial(blockstate) == Material.PLANTS)
            world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
        if (block == Blocks.COCOA) {
            block.dropBlockAsItem(world, pos, blockstate, 0);
            world.setBlockToAir(pos);
        }
        if (block == Blocks.MOSSY_COBBLESTONE)
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
        if (block == Blocks.GRASS || block.getMaterial(blockstate) == Material.GRASS)
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        if (block == Blocks.FARMLAND || block == Blocks.DIRT)
            world.setBlockState(pos, Blocks.SAND.getDefaultState());
        if (sourRain && world.isRaining() && (block == Blocks.STONE || block == Blocks.GRAVEL || block == Blocks.COBBLESTONE) &&
                world.getBlockState(new BlockPos(x, y+1, z)).getBlock() == Blocks.AIR && world.canBlockSeeSky(pos)) {
            if(block == Blocks.STONE){world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState()); }
            else if (block == Blocks.COBBLESTONE){world.setBlockState(pos, Blocks.GRAVEL.getDefaultState()); }
            else if (block == Blocks.GRAVEL) {world.setBlockState(pos, Blocks.SAND.getDefaultState()); }

        }


    }
    private void addPollution(IGregTechTileEntity te, int pollution){
        addPollution(te.getMetaTileEntity().getWorld().getChunk(te.getMetaTileEntity().getPos()).getPos(), pollution);
    }

    public void addPollution(ChunkPos chunk, int pollution) {
        CEuAdditions.proxy.dimensionWisePollution.put(chunk, CEuAdditions.proxy.dimensionWisePollution.get(chunk) + pollution);
    }



    public int getPollution(ChunkPos chunk) {
        if(CEuAdditions.proxy.dimensionWisePollution.isEmpty() || CEuAdditions.proxy.dimensionWisePollution.get(chunk) == null) return 0;
        return CEuAdditions.proxy.dimensionWisePollution.get(chunk);
    }

}
