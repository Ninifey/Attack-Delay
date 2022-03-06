// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRRoadType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;

public class LOTRBiomeGenEasternDesolation extends LOTRBiomeGenMordor
{
    public LOTRBiomeGenEasternDesolation(final int i, final boolean major) {
        super(i, major);
        super.topBlock = LOTRMod.mordorDirt;
        super.fillerBlock = LOTRMod.mordorDirt;
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.mordorGravel, 0, 60, LOTRMod.mordorDirt), 6.0f, 60, 100);
        super.decorator.grassPerChunk = 3;
        super.biomeColors.setSky(9538431);
        super.biomeColors.resetClouds();
        super.biomeColors.resetFog();
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.DIRT;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("east");
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
