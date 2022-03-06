// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenTundra extends LOTRBiome
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseStone;
    protected static NoiseGeneratorPerlin noiseSnow;
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenTundra(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.setEnableSnow();
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 10, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 10, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityElk.class, 10, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 10, 1, 4));
        super.spawnableLOTRAmbientList.clear();
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10).setSpawnChance(1000);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 5).setSpawnChance(1000);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(10);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(5000);
        factionList2.add(lists2);
        super.variantChance = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_SPRUCE);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK_SPRUCE);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 2;
        super.decorator.grassPerChunk = 4;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.generateOrcDungeon = true;
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 100);
        super.decorator.addTree(LOTRTreeType.PINE, 100);
        super.decorator.addTree(LOTRTreeType.FIR, 100);
        super.decorator.addTree(LOTRTreeType.MAPLE, 10);
        super.decorator.addTree(LOTRTreeType.BEECH, 10);
        this.registerTaigaFlowers();
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1500);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 500);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FORODWAITH;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FORODWAITH.getSubregion("tundra");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenTundra.noiseDirt.func_151601_a(i * 0.07, k * 0.07);
        final double d2 = LOTRBiomeGenTundra.noiseDirt.func_151601_a(i * 0.3, k * 0.3);
        final double d3 = LOTRBiomeGenTundra.noiseStone.func_151601_a(i * 0.07, k * 0.07);
        final double d4 = LOTRBiomeGenTundra.noiseStone.func_151601_a(i * 0.3, k * 0.3);
        if (d3 + d4 > 1.2) {
            super.topBlock = Blocks.stone;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.8) {
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
        if (random.nextInt(2) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            for (int bushes = 4 + random.nextInt(20), l = 0; l < bushes; ++l) {
                final int i3 = i2 + MathHelper.getRandomIntegerInRange(random, -4, 4);
                final int k3 = k2 + MathHelper.getRandomIntegerInRange(random, -4, 4);
                final int j2 = j1 + MathHelper.getRandomIntegerInRange(random, -1, 1);
                final Block below = world.getBlock(i3, j2 - 1, k3);
                final Block block = world.getBlock(i3, j2, k3);
                if (below.canSustainPlant((IBlockAccess)world, i3, j2 - 1, k3, ForgeDirection.UP, (IPlantable)Blocks.sapling) && !block.getMaterial().isLiquid() && block.isReplaceable((IBlockAccess)world, i3, j2, k3)) {
                    Block leafBlock = (Block)Blocks.leaves;
                    int leafMeta = 1;
                    if (random.nextInt(3) == 0) {
                        leafBlock = LOTRMod.leaves3;
                        leafMeta = 0;
                    }
                    else if (random.nextInt(3) == 0) {
                        leafBlock = LOTRMod.leaves2;
                        leafMeta = 1;
                    }
                    world.setBlock(i3, j2, k3, leafBlock, leafMeta | 0x4, 2);
                }
            }
        }
        if (random.nextInt(40) == 0) {
            for (int boulders = 1 + random.nextInt(4), m = 0; m < boulders; ++m) {
                final int i4 = i + random.nextInt(16) + 8;
                final int k4 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i4, world.getHeightValue(i4, k4), k4);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.04f;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getBiomeGrassColor(final int i, final int j, final int k) {
        final int color1 = 10708034;
        final int color2 = 13747522;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.002, k * 0.002);
        double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.04, k * 0.04);
        d2 *= 0.4;
        float noise = (float)MathHelper.clamp_double(d1 + d2, -2.0, 2.0);
        noise += 2.0f;
        noise /= 4.0f;
        final float[] rgb1 = new Color(color1).getColorComponents(null);
        final float[] rgb2 = new Color(color2).getColorComponents(null);
        final float[] rgbNoise = new float[rgb1.length];
        for (int l = 0; l < rgbNoise.length; ++l) {
            rgbNoise[l] = rgb1[l] + (rgb2[l] - rgb1[l]) * noise;
        }
        return new Color(rgbNoise[0], rgbNoise[1], rgbNoise[2]).getRGB();
    }
    
    public static boolean isTundraSnowy(final int i, final int k) {
        final double d1 = LOTRBiomeGenTundra.noiseSnow.func_151601_a(i * 0.002, k * 0.002);
        double d2 = LOTRBiomeGenTundra.noiseSnow.func_151601_a(i * 0.05, k * 0.05);
        double d3 = LOTRBiomeGenTundra.noiseSnow.func_151601_a(i * 0.3, k * 0.3);
        d2 *= 0.3;
        d3 *= 0.3;
        return d1 + d2 + d3 > 0.8;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.02f;
    }
    
    static {
        LOTRBiomeGenTundra.noiseDirt = new NoiseGeneratorPerlin(new Random(47684796930956L), 1);
        LOTRBiomeGenTundra.noiseStone = new NoiseGeneratorPerlin(new Random(8894086030764L), 1);
        LOTRBiomeGenTundra.noiseSnow = new NoiseGeneratorPerlin(new Random(2490309256000602L), 1);
    }
}
