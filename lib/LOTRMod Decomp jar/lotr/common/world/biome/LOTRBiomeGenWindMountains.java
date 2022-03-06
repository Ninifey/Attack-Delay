// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenMountainsideBush;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenWindMountains extends LOTRBiome
{
    public LOTRBiomeGenWindMountains(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.npcSpawnList.clear();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.3f);
        super.decorator.biomeGemFactor = 1.0f;
        super.decorator.flowersPerChunk = 1;
        super.decorator.doubleFlowersPerChunk = 0;
        super.decorator.grassPerChunk = 4;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.SPRUCE, 400);
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 50);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 10);
        super.decorator.addTree(LOTRTreeType.LARCH, 500);
        super.decorator.addTree(LOTRTreeType.FIR, 500);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        super.decorator.addTree(LOTRTreeType.MAPLE, 300);
        this.registerMountainsFlowers();
        super.biomeColors.setSky(11653858);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMountainsWind;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("windMountains");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        final int snowHeight = 150 - rockDepth;
        for (int stoneHeight = snowHeight - 40, j = ySize - 1; j >= stoneHeight; --j) {
            final int index = xzIndex * ySize + j;
            Block block = blocks[index];
            if (j >= snowHeight && block == super.topBlock) {
                blocks[index] = Blocks.snow;
                meta[index] = 0;
            }
            else if (block == super.topBlock || block == super.fillerBlock) {
                blocks[index] = Blocks.stone;
                meta[index] = 0;
            }
            block = blocks[index];
            if (block == Blocks.stone) {
                if (random.nextInt(6) == 0) {
                    for (int h = 1 + random.nextInt(6), j2 = j; j2 > j - h && j2 > 0; --j2) {
                        final int indexH = xzIndex * ySize + j2;
                        if (blocks[indexH] == Blocks.stone) {
                            blocks[indexH] = Blocks.stained_hardened_clay;
                            meta[indexH] = 9;
                        }
                    }
                }
                else if (random.nextInt(16) == 0) {
                    blocks[index] = Blocks.clay;
                    meta[index] = 0;
                }
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 3; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = MathHelper.getRandomIntegerInRange(random, 70, 160);
            final int k2 = k + random.nextInt(16) + 8;
            new LOTRWorldGenMountainsideBush(LOTRMod.leaves5, 0).generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}
