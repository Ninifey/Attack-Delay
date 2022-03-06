// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

public class LOTRBiomeVariantDeadForest extends LOTRBiomeVariant
{
    public LOTRBiomeVariantDeadForest(final int i, final String s) {
        super(i, s, VariantScale.ALL);
        this.setTemperatureRainfall(0.0f, -0.3f);
        this.setTrees(3.0f);
        this.setGrass(0.5f);
    }
}
