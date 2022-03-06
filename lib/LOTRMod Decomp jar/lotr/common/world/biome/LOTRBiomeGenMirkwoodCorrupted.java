// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityGorcrow;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityButterfly;

public class LOTRBiomeGenMirkwoodCorrupted extends LOTRBiomeGenMirkwood
{
    public LOTRBiomeGenMirkwoodCorrupted(final int i, final boolean major) {
        super(i, major);
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGorcrow.class, 6, 4, 4));
        super.variantChance = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        super.decorator.treesPerChunk = 8;
        super.decorator.willowPerChunk = 1;
        super.decorator.vinesPerChunk = 20;
        super.decorator.logsPerChunk = 3;
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 6;
        super.decorator.enableFern = true;
        super.decorator.mushroomsPerChunk = 4;
        super.decorator.generateCobwebs = false;
        super.decorator.addTree(LOTRTreeType.MIRK_OAK_LARGE, 1000);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 400);
        super.biomeColors.setGrass(2841381);
        super.biomeColors.setFoliage(2503461);
        super.biomeColors.setFog(3302525);
        super.biomeColors.setFoggy(true);
        super.biomeColors.setWater(1708838);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MIRKWOOD.getSubregion("mirkwood");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (super.decorator.treesPerChunk > 2) {
            for (int l = 0; l < super.decorator.treesPerChunk / 2; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
                LOTRTreeType.MIRK_OAK.create(false, random).generate(world, random, i2, j1, k2);
            }
        }
        for (int l = 0; l < 6; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j2 = random.nextInt(128);
            final int k3 = k + random.nextInt(16) + 8;
            new LOTRWorldGenWebOfUngoliant(false, 64).generate(world, random, i2, j2, k3);
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
