// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.LOTRMod;

public class LOTRBiomeGenDorwinionHills extends LOTRBiomeGenDorwinion
{
    public LOTRBiomeGenDorwinionHills(final int i, final boolean major) {
        super(i, major);
        super.fillerBlock = LOTRMod.rock;
        super.fillerBlockMeta = 5;
        super.biomeTerrain.setXZScale(50.0);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        super.decorator.flowersPerChunk = 3;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 5;
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DORWINION(1, 4), 800);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDorwinionHills;
    }
    
    @Override
    public boolean hasDomesticAnimals() {
        return false;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}
