// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenFarHaradSwamp extends LOTRBiomeGenFarHarad
{
    public LOTRBiomeGenFarHaradSwamp(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 1;
        super.decorator.treesPerChunk = 0;
        super.decorator.vinesPerChunk = 20;
        super.decorator.logsPerChunk = 3;
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 8;
        super.decorator.enableFern = true;
        super.decorator.mushroomsPerChunk = 3;
        super.decorator.waterlilyPerChunk = 3;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 3;
        super.decorator.addTree(LOTRTreeType.OAK_SWAMP, 1000);
        this.registerSwampFlowers();
        super.biomeColors.setWater(5607038);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("swamp");
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
}
