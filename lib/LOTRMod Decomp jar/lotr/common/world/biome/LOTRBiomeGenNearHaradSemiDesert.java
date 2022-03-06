// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import lotr.common.world.structure2.LOTRWorldGenHaradRuinedFort;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenHaradPyramid;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenNearHaradSemiDesert extends LOTRBiomeGenNearHarad
{
    public LOTRBiomeGenNearHaradSemiDesert(final int i, final boolean major) {
        super(i, major);
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 20).setSpawnChance(500);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 15).setSpawnChance(500);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS);
        factionList3.add(lists3);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        super.decorator.grassPerChunk = 5;
        super.decorator.doubleGrassPerChunk = 0;
        super.decorator.cactiPerChunk = 1;
        super.decorator.deadBushPerChunk = 1;
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradPyramid(false), 4000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 4), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
        super.decorator.clearVillages();
        super.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.5f));
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.08, k * 0.08);
        final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3);
        if (d1 + d2 > 0.3) {
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
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2) - 1;
            if (world.getBlock(i2, j1, k2) == Blocks.sand) {
                world.setBlock(i2, j1, k2, Blocks.dirt);
                final LOTRTreeType treeType = LOTRTreeType.OAK_DESERT;
                final WorldGenerator tree = (WorldGenerator)treeType.create(false, random);
                if (!tree.generate(world, random, i2, j1 + 1, k2)) {
                    world.setBlock(i2, j1, k2, (Block)Blocks.sand);
                }
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        return new GrassBlockAndMeta(LOTRMod.aridGrass, 0);
    }
}
