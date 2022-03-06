// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenCorn extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 20; ++l) {
            final int i2 = i + random.nextInt(4) - random.nextInt(4);
            final int j2 = j;
            final int k2 = k + random.nextInt(4) - random.nextInt(4);
            final Block replace = world.getBlock(i2, j2, k2);
            if (replace.isReplaceable((IBlockAccess)world, i2, j2, k2) && !replace.getMaterial().isLiquid()) {
                boolean adjWater = false;
            Label_0164:
                for (int i3 = -1; i3 <= 1; ++i3) {
                    for (int k3 = -1; k3 <= 1; ++k3) {
                        if (Math.abs(i3) + Math.abs(k3) == 1 && world.getBlock(i2 + i3, j - 1, k2 + k3).getMaterial() == Material.water) {
                            adjWater = true;
                            break Label_0164;
                        }
                    }
                }
                if (adjWater) {
                    for (int cornHeight = 2 + random.nextInt(2), j3 = 0; j3 < cornHeight; ++j3) {
                        if (LOTRMod.cornStalk.canBlockStay(world, i2, j2 + j3, k2)) {
                            world.setBlock(i2, j2 + j3, k2, LOTRMod.cornStalk, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
