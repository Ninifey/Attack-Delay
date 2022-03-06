// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHayBales extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenHayBales(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        final int width = 1 + random.nextInt(3);
        for (int size = 4 + width * width * (2 + random.nextInt(3)), l = 0; l < size; ++l) {
            final int r = MathHelper.getRandomIntegerInRange(random, 0, width * width);
            final int dist = (int)Math.round(Math.sqrt(r));
            final float angle = 6.2831855f * random.nextFloat();
            final int i2 = Math.round(MathHelper.cos(angle) * dist);
            final int k2 = Math.round(MathHelper.sin(angle) * dist);
            for (int j2 = 12; j2 >= -12; --j2) {
                if (this.isSurface(world, i2, j2 - 1, k2) || this.getBlock(world, i2, j2 - 1, k2) == Blocks.hay_block) {
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (this.isAir(world, i2, j2, k2) || this.isReplaceable(world, i2, j2, k2) || block.getMaterial() == Material.plants) {
                        this.setBlockAndMetadata(world, i2, j2, k2, Blocks.hay_block, 0);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                        break;
                    }
                }
            }
        }
        return true;
    }
}
