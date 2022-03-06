// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

import java.util.Random;
import lotr.common.util.LOTRFunctions;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeVariantDunes extends LOTRBiomeVariant
{
    private static NoiseGeneratorPerlin duneWaveNoise;
    private static NoiseGeneratorPerlin duneHeightNoise;
    
    public LOTRBiomeVariantDunes(final int i, final String s) {
        super(i, s, VariantScale.ALL);
        this.setTemperatureRainfall(0.1f, -0.1f);
    }
    
    @Override
    public float getHeightBoostAt(final int i, final int k) {
        return this.getDuneHeightAt(i, k) / 22.0f;
    }
    
    private int getDuneHeightAt(final int i, final int k) {
        final double d1 = LOTRBiomeVariantDunes.duneWaveNoise.func_151601_a(i * 0.02, k * 0.02);
        final double d2 = LOTRBiomeVariantDunes.duneWaveNoise.func_151601_a(i * 0.7, k * 0.7);
        double d3 = d1 * 0.9 + d2 * 0.1;
        d3 = MathHelper.clamp_double(d3, -1.0, 1.0);
        d3 *= 15.0;
        final int maxDuneHeight = 12;
        final int duneHeight = Math.round(LOTRFunctions.normalisedSin((i + (float)d3) * 0.09f) * maxDuneHeight);
        return duneHeight;
    }
    
    static {
        LOTRBiomeVariantDunes.duneWaveNoise = new NoiseGeneratorPerlin(new Random(305620668206968L), 1);
        LOTRBiomeVariantDunes.duneHeightNoise = new NoiseGeneratorPerlin(new Random(5729475867259682L), 1);
    }
}
