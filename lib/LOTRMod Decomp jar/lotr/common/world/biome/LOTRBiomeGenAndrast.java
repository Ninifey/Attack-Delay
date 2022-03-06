// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenAndrast extends LOTRBiomeGenGondor
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseStone;
    private WorldGenerator boulderGen;
    private WorldGenerator boulderGenGondor;
    
    public LOTRBiomeGenAndrast(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
        this.boulderGenGondor = new LOTRWorldGenBoulder(LOTRMod.rock, 1, 1, 4);
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        super.variantChance = 0.5f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND, 3.0f);
        super.decorator.setTreeCluster(10, 30);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 4;
        super.decorator.addTree(LOTRTreeType.OAK, 400);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.BIRCH, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.BEECH, 50);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.DARK_OAK, 500);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        super.decorator.addTree(LOTRTreeType.LARCH, 200);
        super.decorator.addTree(LOTRTreeType.APPLE, 5);
        super.decorator.addTree(LOTRTreeType.PEAR, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        super.decorator.addTree(LOTRTreeType.OAK_SHRUB, 1500);
        this.registerPlainsFlowers();
        super.biomeColors.setGrass(10202470);
        super.decorator.clearVillages();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1500);
        this.clearTravellingTraders();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterAndrast;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.PUKEL.getSubregion("andrast");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenAndrast.noiseDirt.func_151601_a(i * 0.07, k * 0.07);
        final double d2 = LOTRBiomeGenAndrast.noiseDirt.func_151601_a(i * 0.3, k * 0.3);
        final double d3 = LOTRBiomeGenAndrast.noiseStone.func_151601_a(i * 0.07, k * 0.07);
        final double d4 = LOTRBiomeGenAndrast.noiseStone.func_151601_a(i * 0.3, k * 0.3);
        if (d3 + d4 > 1.1) {
            super.topBlock = Blocks.stone;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.6) {
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
        if (random.nextInt(5) == 0) {
            for (int l = 0; l < 4; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                if (random.nextBoolean()) {
                    this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
                }
                else {
                    this.boulderGenGondor.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
                }
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 1;
    }
    
    static {
        LOTRBiomeGenAndrast.noiseDirt = new NoiseGeneratorPerlin(new Random(285939985023633003L), 1);
        LOTRBiomeGenAndrast.noiseStone = new NoiseGeneratorPerlin(new Random(4148990259960304L), 1);
    }
}
