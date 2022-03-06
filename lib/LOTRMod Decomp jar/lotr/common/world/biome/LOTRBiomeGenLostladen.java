// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenLostladen extends LOTRBiome
{
    private static NoiseGeneratorPerlin noiseDirt;
    private static NoiseGeneratorPerlin noiseSand;
    private static NoiseGeneratorPerlin noiseStone;
    private WorldGenerator boulderGen;
    private WorldGenerator boulderGenSandstone;
    
    public LOTRBiomeGenLostladen(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.sandstone, 0, 1, 3);
        super.spawnableCreatureList.clear();
        super.npcSpawnList.clear();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK, 3.0f);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        super.decorator.treesPerChunk = 0;
        super.decorator.grassPerChunk = 3;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.flowersPerChunk = 1;
        super.decorator.cactiPerChunk = 1;
        super.decorator.deadBushPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 200);
        this.registerHaradFlowers();
        super.biomeColors.setSky(15592678);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLostladen;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.LOSTLADEN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("lostladen");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HARAD.setRepair(0.3f);
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenLostladen.noiseDirt.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = LOTRBiomeGenLostladen.noiseDirt.func_151601_a(i * 0.4, k * 0.4);
        final double d3 = LOTRBiomeGenLostladen.noiseSand.func_151601_a(i * 0.09, k * 0.09);
        final double d4 = LOTRBiomeGenLostladen.noiseSand.func_151601_a(i * 0.4, k * 0.4);
        final double d5 = LOTRBiomeGenLostladen.noiseStone.func_151601_a(i * 0.09, k * 0.09);
        final double d6 = LOTRBiomeGenLostladen.noiseStone.func_151601_a(i * 0.4, k * 0.4);
        if (d5 + d6 > 0.3) {
            if (random.nextInt(5) == 0) {
                super.topBlock = Blocks.gravel;
                super.topBlockMeta = 0;
            }
            else {
                super.topBlock = Blocks.stone;
                super.topBlockMeta = 0;
            }
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d3 + d4 > 0.1) {
            if (random.nextInt(5) == 0) {
                super.topBlock = Blocks.sandstone;
                super.topBlockMeta = 0;
            }
            else {
                super.topBlock = (Block)Blocks.sand;
                super.topBlockMeta = 0;
            }
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > -0.2) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(20) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i2, k2);
                if (random.nextBoolean()) {
                    this.boulderGen.generate(world, random, i2, j1, k2);
                }
                else {
                    this.boulderGenSandstone.generate(world, random, i2, j1, k2);
                }
            }
        }
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        if (random.nextBoolean()) {
            return new GrassBlockAndMeta(LOTRMod.aridGrass, 0);
        }
        return super.getRandomGrass(random);
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.01f;
    }
    
    static {
        LOTRBiomeGenLostladen.noiseDirt = new NoiseGeneratorPerlin(new Random(486938207230702L), 1);
        LOTRBiomeGenLostladen.noiseSand = new NoiseGeneratorPerlin(new Random(28507830789060732L), 1);
        LOTRBiomeGenLostladen.noiseStone = new NoiseGeneratorPerlin(new Random(275928960292060726L), 1);
    }
}
