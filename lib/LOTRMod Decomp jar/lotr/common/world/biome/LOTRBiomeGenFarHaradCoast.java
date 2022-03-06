// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenCorsairCove;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.entity.animal.LOTREntityLioness;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityLion;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenFarHaradCoast extends LOTRBiomeGenFarHaradSavannah
{
    protected static NoiseGeneratorPerlin noiseGrass;
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseSand;
    protected static NoiseGeneratorPerlin noiseSandstone;
    
    public LOTRBiomeGenFarHaradCoast(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityLion.class, 4, 2, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityLioness.class, 4, 2, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityZebra.class, 8, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityRhino.class, 8, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGemsbok.class, 8, 4, 8));
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(5000);
        factionList.add(lists);
        super.populatedSpawnList.clear();
        super.topBlock = Blocks.stone;
        super.topBlockMeta = 0;
        super.fillerBlock = super.topBlock;
        super.fillerBlockMeta = super.topBlockMeta;
        super.biomeTerrain.setXZScale(30.0);
        this.clearBiomeVariants();
        super.decorator.addTree(LOTRTreeType.PALM, 4000);
        super.decorator.treesPerChunk = 1;
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenCorsairCove(false), 10);
        super.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 100);
        this.clearTravellingTraders();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
        super.invasionSpawns.clearInvasions();
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.COMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterCorsairCoasts;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenFarHaradCoast.noiseGrass.func_151601_a(i * 0.06, k * 0.06);
        final double d2 = LOTRBiomeGenFarHaradCoast.noiseGrass.func_151601_a(i * 0.47, k * 0.47);
        final double d3 = LOTRBiomeGenFarHaradCoast.noiseDirt.func_151601_a(i * 0.06, k * 0.06);
        final double d4 = LOTRBiomeGenFarHaradCoast.noiseDirt.func_151601_a(i * 0.47, k * 0.47);
        final double d5 = LOTRBiomeGenFarHaradCoast.noiseSand.func_151601_a(i * 0.06, k * 0.06);
        final double d6 = LOTRBiomeGenFarHaradCoast.noiseSand.func_151601_a(i * 0.47, k * 0.47);
        final double d7 = LOTRBiomeGenFarHaradCoast.noiseSandstone.func_151601_a(i * 0.06, k * 0.06);
        final double d8 = LOTRBiomeGenFarHaradCoast.noiseSandstone.func_151601_a(i * 0.47, k * 0.47);
        if (d7 + d8 > 0.8) {
            super.topBlock = Blocks.sandstone;
            super.topBlockMeta = 0;
            super.fillerBlock = (Block)Blocks.sand;
            super.fillerBlockMeta = 0;
        }
        else if (d5 + d6 > 0.6) {
            super.topBlock = (Block)Blocks.sand;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d3 + d4 > 0.5) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.4) {
            super.topBlock = (Block)Blocks.grass;
            super.topBlockMeta = 0;
            super.fillerBlock = Blocks.dirt;
            super.fillerBlockMeta = 0;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
    
    static {
        LOTRBiomeGenFarHaradCoast.noiseGrass = new NoiseGeneratorPerlin(new Random(75796728360672L), 1);
        LOTRBiomeGenFarHaradCoast.noiseDirt = new NoiseGeneratorPerlin(new Random(63275968906L), 1);
        LOTRBiomeGenFarHaradCoast.noiseSand = new NoiseGeneratorPerlin(new Random(127425276902L), 1);
        LOTRBiomeGenFarHaradCoast.noiseSandstone = new NoiseGeneratorPerlin(new Random(267215026920L), 1);
    }
}
