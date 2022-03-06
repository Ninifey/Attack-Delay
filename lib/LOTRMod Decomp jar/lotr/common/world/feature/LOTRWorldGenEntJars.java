// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.tileentity.TileEntity;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenEntJars extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 16; ++l) {
            final int i2 = i - random.nextInt(6) + random.nextInt(6);
            final int j2 = j - random.nextInt(2) + random.nextInt(2);
            final int k2 = k - random.nextInt(6) + random.nextInt(6);
            if (world.getBlock(i2, j2 - 1, k2) == Blocks.grass && !world.getBlock(i2, j2, k2).isNormalCube() && world.getPrecipitationHeight(i2, k2) == j2 && world.getBiomeGenForCoords(i2, k2) instanceof LOTRBiomeGenFangorn) {
                world.setBlock(i2, j2, k2, LOTRMod.entJar, 0, 2);
                final TileEntity tileentity = world.getTileEntity(i2, j2, k2);
                if (tileentity instanceof LOTRTileEntityEntJar) {
                    for (int amount = random.nextInt(LOTRTileEntityEntJar.MAX_CAPACITY + 1), l2 = 0; l2 < amount; ++l2) {
                        ((LOTRTileEntityEntJar)tileentity).fillWithWater();
                    }
                }
            }
        }
        return true;
    }
}
