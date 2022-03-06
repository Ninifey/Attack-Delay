// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenStalactites extends WorldGenerator
{
    private Block stalactiteBlock;
    
    public LOTRWorldGenStalactites() {
        this(LOTRMod.stalactite);
    }
    
    public LOTRWorldGenStalactites(final Block block) {
        this.stalactiteBlock = block;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 64; ++l) {
            final int i2 = i - random.nextInt(8) + random.nextInt(8);
            final int j2 = j - random.nextInt(4) + random.nextInt(4);
            final int k2 = k - random.nextInt(8) + random.nextInt(8);
            if (world.isAirBlock(i2, j2, k2)) {
                final Block above = world.getBlock(i2, j2 + 1, k2);
                final Block below = world.getBlock(i2, j2 - 1, k2);
                if (above.isOpaqueCube() && above.getMaterial() == Material.rock) {
                    world.setBlock(i2, j2, k2, this.stalactiteBlock, 0, 2);
                }
                else if (below.isOpaqueCube() && below.getMaterial() == Material.rock) {
                    world.setBlock(i2, j2, k2, this.stalactiteBlock, 1, 2);
                }
            }
        }
        return true;
    }
}
