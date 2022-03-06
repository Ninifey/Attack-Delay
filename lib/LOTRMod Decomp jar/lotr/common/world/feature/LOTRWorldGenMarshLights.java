// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMarshLights extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j;
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            if (world.isAirBlock(i2, j2, k2) && LOTRMod.marshLights.canPlaceBlockAt(world, i2, j2, k2)) {
                world.setBlock(i2, j2, k2, LOTRMod.marshLights, 0, 2);
            }
        }
        return true;
    }
}
