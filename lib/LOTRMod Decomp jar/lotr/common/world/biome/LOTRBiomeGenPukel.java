// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.structure2.LOTRWorldGenBurntHouse;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenPukel extends LOTRBiome
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseStone;
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenPukel(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 6, 1, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 4, 1, 4));
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        super.variantChance = 0.6f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 4.0f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST, 4.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_OAK, 6.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_DARK_OAK, 6.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
        super.decorator.setTreeCluster(10, 24);
        super.decorator.treesPerChunk = 1;
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 14;
        super.decorator.doubleGrassPerChunk = 6;
        super.decorator.addTree(LOTRTreeType.OAK, 400);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.OAK_PARTY, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.BEECH, 50);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 200);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.DARK_OAK, 500);
        super.decorator.addTree(LOTRTreeType.DARK_OAK_PARTY, 100);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        super.decorator.addTree(LOTRTreeType.LARCH, 200);
        super.decorator.addTree(LOTRTreeType.APPLE, 5);
        super.decorator.addTree(LOTRTreeType.PEAR, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        super.decorator.addTree(LOTRTreeType.OAK_SHRUB, 300);
        this.registerPlainsFlowers();
        super.biomeColors.setGrass(6715192);
        super.biomeColors.setSky(10927288);
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.clearTravellingTraders();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterPukel;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.PUKEL;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.PUKEL.getSubregion("pukel");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenPukel.noiseDirt.func_151601_a(i * 0.06, k * 0.06);
        final double d2 = LOTRBiomeGenPukel.noiseDirt.func_151601_a(i * 0.4, k * 0.4);
        final double d3 = LOTRBiomeGenPukel.noiseStone.func_151601_a(i * 0.06, k * 0.06);
        final double d4 = LOTRBiomeGenPukel.noiseStone.func_151601_a(i * 0.4, k * 0.4);
        if (d3 + d4 > 1.3) {
            super.topBlock = Blocks.stone;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.7) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
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
        if (random.nextInt(24) == 0) {
            for (int l = 0; l < 4; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    static {
        LOTRBiomeGenPukel.noiseDirt = new NoiseGeneratorPerlin(new Random(285939985023633003L), 1);
        LOTRBiomeGenPukel.noiseStone = new NoiseGeneratorPerlin(new Random(4148990259960304L), 1);
    }
}
