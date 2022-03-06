// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenShireWoodlands extends LOTRBiomeGenShire
{
    public LOTRBiomeGenShireWoodlands(final int i, final boolean major) {
        super(i, major);
        super.variantChance = 0.2f;
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        super.decorator.treesPerChunk = 9;
        super.decorator.flowersPerChunk = 6;
        super.decorator.doubleFlowersPerChunk = 2;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.enableFern = true;
        super.decorator.addTree(LOTRTreeType.BIRCH, 250);
        super.decorator.addTree(LOTRTreeType.SHIRE_PINE, 2500);
        super.decorator.addTree(LOTRTreeType.ASPEN, 300);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 100);
        this.addFlower(LOTRMod.shireHeather, 0, 20);
        super.biomeColors.resetGrass();
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SHIRE.getSubregion("woodland");
    }
    
    @Override
    public boolean hasDomesticAnimals() {
        return false;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
