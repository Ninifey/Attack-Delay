// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMountainsideBush extends WorldGenerator
{
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenMountainsideBush(final Block block, final int meta) {
        this.leafBlock = block;
        this.leafMeta = meta;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 64; ++l) {
            final int i2 = i + MathHelper.getRandomIntegerInRange(random, -2, 2);
            final int j2 = j + MathHelper.getRandomIntegerInRange(random, -2, 2);
            final int k2 = k + MathHelper.getRandomIntegerInRange(random, -2, 2);
            if (world.isAirBlock(i2, j2, k2) && (this.isStone(world, i2 - 1, j2, k2) || this.isStone(world, i2 + 1, j2, k2) || this.isStone(world, i2, j2, k2 - 1) || this.isStone(world, i2, j2, k2 + 1))) {
                world.setBlock(i2, j2, k2, this.leafBlock, this.leafMeta | 0x4, 2);
            }
        }
        return true;
    }
    
    private boolean isStone(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j, k).getMaterial() == Material.rock;
    }
}
