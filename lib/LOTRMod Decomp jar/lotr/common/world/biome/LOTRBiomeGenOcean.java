// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.world.structure.LOTRWorldGenUnderwaterElvenRuin;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenNumenorRuin;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.entity.animal.LOTREntitySeagull;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntitySquid;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenSeaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import java.util.Random;

public class LOTRBiomeGenOcean extends LOTRBiome
{
    private static Random iceRand;
    private static final int iceLimitSouth = -30000;
    private static final int iceLimitNorth = -60000;
    private static final int palmStartZ = 64000;
    private static final int palmFullZ = 130000;
    private WorldGenerator spongeGen;
    private WorldGenerator coralGen;
    
    public LOTRBiomeGenOcean(final int i, final boolean major) {
        super(i, major);
        this.spongeGen = new LOTRWorldGenSeaBlock(Blocks.sponge, 0, 24);
        this.coralGen = new LOTRWorldGenSeaBlock(LOTRMod.coralReef, 0, 64);
        super.spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySquid.class, 4, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntitySeagull.class, 20, 4, 4));
        super.npcSpawnList.clear();
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSalt, 8), 4.0f, 0, 64);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSalt, 8, (Block)Blocks.sand), 0.5f, 56, 80);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSalt, 8, LOTRMod.whiteSand), 0.5f, 56, 80);
        super.decorator.treesPerChunk = 1;
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 2;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.OAK, 1000);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.BEECH, 50);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 5);
        super.decorator.addTree(LOTRTreeType.APPLE, 3);
        super.decorator.addTree(LOTRTreeType.PEAR, 3);
        super.decorator.addRandomStructure(new LOTRWorldGenNumenorRuin(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.OCEAN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SEA.getSubregion("sea");
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterOcean;
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (i < LOTRWaypoint.MITHLOND_SOUTH.getXCoord() && k > LOTRWaypoint.SOUTH_FOROCHEL.getZCoord() && k < LOTRWaypoint.ERYN_VORN.getZCoord() && random.nextInt(200) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
            new LOTRWorldGenUnderwaterElvenRuin(false).generate(world, random, i2, j1, k2);
        }
        if (k > -30000) {
            if (random.nextInt(12) == 0) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
                if (j1 < 60 || random.nextBoolean()) {
                    this.spongeGen.generate(world, random, i2, j1, k2);
                }
            }
            if (random.nextInt(4) == 0) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
                if (j1 < 60 || random.nextBoolean()) {
                    this.coralGen.generate(world, random, i2, j1, k2);
                }
            }
        }
        if (k >= 64000) {
            float chance = 0.0f;
            if (k >= 130000) {
                chance = 1.0f;
            }
            else {
                chance = (k - 64000) / 66000.0f;
            }
            if (random.nextFloat() < chance && random.nextInt(6) == 0) {
                int palms = 1 + random.nextInt(2);
                if (random.nextInt(3) == 0) {
                    ++palms;
                }
                for (int l = 0; l < palms; ++l) {
                    final int i3 = i + random.nextInt(16) + 8;
                    final int k3 = k + random.nextInt(16) + 8;
                    final int j2 = world.getTopSolidOrLiquidBlock(i3, k3) - 1;
                    if (world.getBlock(i3, j2, k3).isNormalCube() && LOTRWorldGenStructureBase2.isSurfaceStatic(world, i3, j2, k3)) {
                        final Block prevBlock = world.getBlock(i3, j2, k3);
                        final int prevMeta = world.getBlockMetadata(i3, j2, k3);
                        world.setBlock(i3, j2, k3, Blocks.dirt, 0, 2);
                        final WorldGenerator palmGen = (WorldGenerator)LOTRTreeType.PALM.create(false, random);
                        if (!palmGen.generate(world, random, i3, j2 + 1, k3)) {
                            world.setBlock(i3, j2, k3, prevBlock, prevMeta, 2);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    public static boolean isFrozen(final int i, final int k) {
        if (k > -30000) {
            return false;
        }
        int l = -60000 - k;
        l *= -1;
        if (l < 1) {
            return true;
        }
        LOTRBiomeGenOcean.iceRand.setSeed(i * 341873128712L + k * 132897987541L);
        l -= Math.abs(-30000) / 2;
        if (l < 0) {
            l *= -1;
            l = (int)Math.sqrt(l);
            if (l < 2) {
                l = 2;
            }
            return LOTRBiomeGenOcean.iceRand.nextInt(l) != 0;
        }
        l = (int)Math.sqrt(l);
        if (l < 2) {
            l = 2;
        }
        return LOTRBiomeGenOcean.iceRand.nextInt(l) == 0;
    }
    
    static {
        LOTRBiomeGenOcean.iceRand = new Random();
    }
}
