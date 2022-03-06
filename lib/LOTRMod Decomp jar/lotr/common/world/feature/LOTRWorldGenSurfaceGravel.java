// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSurfaceGravel extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int r = MathHelper.getRandomIntegerInRange(random, 2, 8);
        final int chance = MathHelper.getRandomIntegerInRange(random, 3, 9);
        Block surfBlock;
        int surfMeta;
        if (random.nextBoolean()) {
            surfBlock = Blocks.gravel;
            surfMeta = 0;
        }
        else {
            surfBlock = Blocks.dirt;
            surfMeta = 1;
        }
        for (int i2 = -r; i2 <= r; ++i2) {
            for (int k2 = -r; k2 <= r; ++k2) {
                final int i3 = i + i2;
                final int k3 = k + k2;
                final int d = i2 * i2 + k2 * k2;
                if (d < r * r && random.nextInt(chance) == 0) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i3, k3) - 1;
                    final Block block = world.getBlock(i3, j2, k3);
                    final Material mt = block.getMaterial();
                    if (block.isOpaqueCube() && (mt == Material.ground || mt == Material.grass)) {
                        world.setBlock(i3, j2, k3, surfBlock, surfMeta, 2);
                    }
                }
            }
        }
        return true;
    }
}
