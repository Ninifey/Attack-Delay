// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;

public class LOTRBiomeGenFarHaradMangrove extends LOTRBiomeGenFarHarad
{
    public LOTRBiomeGenFarHaradMangrove(final int i, final boolean major) {
        super(i, major);
        super.topBlock = (Block)Blocks.sand;
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(Blocks.dirt, 1, 60, (Block)Blocks.sand), 12.0f, 60, 90);
        super.decorator.quagmirePerChunk = 1;
        super.decorator.treesPerChunk = 5;
        super.decorator.vinesPerChunk = 20;
        super.decorator.grassPerChunk = 8;
        super.decorator.enableFern = true;
        super.decorator.waterlilyPerChunk = 3;
        super.decorator.addTree(LOTRTreeType.MANGROVE, 1000);
        super.decorator.addTree(LOTRTreeType.ACACIA, 10);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 5);
        this.registerSwampFlowers();
        super.biomeColors.setGrass(10466679);
        super.biomeColors.setFoliage(6715206);
        super.biomeColors.setWater(5985085);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("mangrove");
    }
    
    @Override
    public boolean getEnableRiver() {
        return true;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 2; ++l) {
            final int i2 = i + random.nextInt(16);
            final int k2 = k + random.nextInt(16);
            final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
            if (world.getBlock(i2, j1 - 1, k2).isOpaqueCube() && world.getBlock(i2, j1, k2).getMaterial() == Material.water) {
                super.decorator.genTree(world, random, i2, j1, k2);
            }
        }
        for (int l = 0; l < 20; ++l) {
            final int i2 = i + random.nextInt(16);
            final int j2 = 50 + random.nextInt(15);
            final int k3 = k + random.nextInt(16);
            if (world.getBlock(i2, j2, k3).isOpaqueCube() && world.getBlock(i2, j2 + 1, k3).getMaterial() == Material.water) {
                for (int height = 2 + random.nextInt(3), j3 = j2; j3 <= j2 + height; ++j3) {
                    world.setBlock(i2, j3, k3, LOTRMod.wood3, 3, 2);
                }
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.15f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.4f;
    }
}
