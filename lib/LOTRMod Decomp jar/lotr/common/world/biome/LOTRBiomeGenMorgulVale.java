// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import java.util.Random;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenMorgulVale extends LOTRBiomeGenMordor
{
    private NoiseGeneratorPerlin noiseDirt;
    private NoiseGeneratorPerlin noiseGravel;
    private NoiseGeneratorPerlin noiseRock;
    
    public LOTRBiomeGenMorgulVale(final int i, final boolean major) {
        super(i, major);
        this.noiseDirt = new NoiseGeneratorPerlin(new Random(1860286702860L), 1);
        this.noiseGravel = new NoiseGeneratorPerlin(new Random(8903486028509023054L), 1);
        this.noiseRock = new NoiseGeneratorPerlin(new Random(769385178389572607L), 1);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[5];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 15).setSpawnChance(30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2).setSpawnChance(30);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n4 = 3;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
        final int n5 = 4;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 2).setConquestThreshold(200.0f);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        factionList3.add(lists3);
        super.npcSpawnList.conquestGainRate = 0.5f;
        super.topBlock = (Block)Blocks.grass;
        super.fillerBlock = Blocks.dirt;
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 10.0f, 0, 60);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 3;
        super.decorator.dryReedChance = 1.0f;
        super.decorator.addTree(LOTRTreeType.OAK, 200);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
        super.decorator.addTree(LOTRTreeType.CHARRED, 500);
        this.flowers.clear();
        this.addFlower(LOTRMod.morgulFlower, 0, 20);
        super.biomeColors.setGrass(6054733);
        super.biomeColors.setFoliage(4475954);
        super.biomeColors.setSky(7835270);
        super.biomeColors.setClouds(5860197);
        super.biomeColors.setFog(6318950);
        super.biomeColors.setWater(3563598);
    }
    
    @Override
    public boolean isGorgoroth() {
        return false;
    }
    
    @Override
    protected boolean hasMordorSoils() {
        return false;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMorgulVale;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("morgulVale");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = this.noiseDirt.func_151601_a(i * 0.06, k * 0.06);
        final double d2 = this.noiseDirt.func_151601_a(i * 0.3, k * 0.3);
        final double d3 = this.noiseGravel.func_151601_a(i * 0.06, k * 0.06);
        final double d4 = this.noiseGravel.func_151601_a(i * 0.3, k * 0.3);
        final double d5 = this.noiseRock.func_151601_a(i * 0.06, k * 0.06);
        final double d6 = this.noiseRock.func_151601_a(i * 0.3, k * 0.3);
        if (d5 + d6 > 1.1) {
            super.topBlock = LOTRMod.rock;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d3 + d4 > 0.7) {
            super.topBlock = LOTRMod.mordorGravel;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.7) {
            super.topBlock = LOTRMod.mordorDirt;
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
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            boolean foundWater = false;
            for (int a = 0; a < 20; ++a) {
                final int range = 8;
                final int i3 = i2 + MathHelper.getRandomIntegerInRange(random, -range, range);
                final int j2 = j1 + MathHelper.getRandomIntegerInRange(random, -range, range);
                final int k3 = k2 + MathHelper.getRandomIntegerInRange(random, -range, range);
                final Block block = world.getBlock(i3, j2, k3);
                if (block.getMaterial() == Material.water) {
                    foundWater = true;
                    break;
                }
            }
            if (foundWater) {
                final WorldGenFlowers flowerGen = new WorldGenFlowers(LOTRMod.morgulFlower);
                flowerGen.generate(world, random, i2, j1, k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}
