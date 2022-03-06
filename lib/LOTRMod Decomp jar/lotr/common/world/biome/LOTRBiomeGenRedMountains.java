// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenRedMountains extends LOTRBiome
{
    public LOTRBiomeGenRedMountains(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
        super.decorator.biomeOreFactor = 2.0f;
        super.decorator.biomeGemFactor = 1.5f;
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.rock, 4, 60, Blocks.stone), 12.0f, 0, 96);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0f, 0, 48);
        super.decorator.treesPerChunk = 1;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.OAK, 300);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 500);
        super.decorator.addTree(LOTRTreeType.LARCH, 300);
        super.decorator.addTree(LOTRTreeType.MAPLE, 300);
        super.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.FIR, 500);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        this.registerMountainsFlowers();
        this.addFlower(LOTRMod.dwarfHerb, 0, 1);
        super.biomeColors.setSky(13541522);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRedMountains;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RED_MOUNTAINS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DWARVEN.getSubregion("redMountains");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.DWARVEN;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        final int stoneHeight = 110 - rockDepth;
        for (int sandHeight = stoneHeight - 6, j = ySize - 1; j >= sandHeight; --j) {
            final int index = xzIndex * ySize + j;
            final Block block = blocks[index];
            if (block == super.topBlock || block == super.fillerBlock) {
                if (j >= stoneHeight) {
                    blocks[index] = LOTRMod.rock;
                    meta[index] = 4;
                }
                else {
                    blocks[index] = (Block)Blocks.sand;
                    meta[index] = 1;
                }
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.2f;
    }
}
