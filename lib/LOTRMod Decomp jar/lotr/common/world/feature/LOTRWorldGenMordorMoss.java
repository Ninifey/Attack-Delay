// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMordorMoss extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int numberOfMoss = 32 + random.nextInt(80);
        final float f = random.nextFloat() * 3.1415927f;
        final double d = i + 8 + MathHelper.sin(f) * numberOfMoss / 8.0f;
        final double d2 = i + 8 - MathHelper.sin(f) * numberOfMoss / 8.0f;
        final double d3 = k + 8 + MathHelper.cos(f) * numberOfMoss / 8.0f;
        final double d4 = k + 8 - MathHelper.cos(f) * numberOfMoss / 8.0f;
        for (int l = 0; l <= numberOfMoss; ++l) {
            final double d5 = d + (d2 - d) * l / numberOfMoss;
            final double d6 = d3 + (d4 - d3) * l / numberOfMoss;
            final double d7 = random.nextDouble() * numberOfMoss / 16.0;
            final double d8 = (MathHelper.sin(l * 3.1415927f / numberOfMoss) + 1.0f) * d7 + 1.0;
            final int i2 = MathHelper.floor_double(d5 - d8 / 2.0);
            final int k2 = MathHelper.floor_double(d6 - d8 / 2.0);
            final int i3 = MathHelper.floor_double(d5 + d8 / 2.0);
            final int k3 = MathHelper.floor_double(d6 + d8 / 2.0);
            for (int i4 = i2; i4 <= i3; ++i4) {
                final double d9 = (i4 + 0.5 - d5) / (d8 / 2.0);
                if (d9 * d9 < 1.0) {
                    for (int k4 = k2; k4 <= k3; ++k4) {
                        final int j2 = world.getHeightValue(i4, k4);
                        if (j2 == j) {
                            final double d10 = (k4 + 0.5 - d6) / (d8 / 2.0);
                            if (d9 * d9 + d10 * d10 < 1.0 && LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i4, j2 - 1, k4) && world.getBlockMetadata(i4, j2 - 1, k4) == 0 && world.isAirBlock(i4, j2, k4)) {
                                world.setBlock(i4, j2, k4, LOTRMod.mordorMoss, 0, 2);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
