// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

public class LOTRBiomeVariantForest extends LOTRBiomeVariant
{
    public LOTRBiomeVariantForest(final int i, final String s) {
        super(i, s, VariantScale.ALL);
        this.setTemperatureRainfall(0.0f, 0.3f);
        this.setTrees(8.0f);
        this.setGrass(2.0f);
    }
}
