// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSeaBlock extends WorldGenerator
{
    private Block theBlock;
    private int theMeta;
    private int tries;
    
    public LOTRWorldGenSeaBlock(final Block block, final int i, final int t) {
        this.theBlock = block;
        this.theMeta = i;
        this.tries = t;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < this.tries; ++l) {
            final int i2 = i - random.nextInt(6) + random.nextInt(6);
            final int j2 = j - random.nextInt(4) + random.nextInt(4);
            final int k2 = k - random.nextInt(6) + random.nextInt(6);
            final Block below = world.getBlock(i2, j2 - 1, k2);
            final Block block = world.getBlock(i2, j2, k2);
            final Material belowMaterial = below.getMaterial();
            if ((belowMaterial == Material.sand || belowMaterial == Material.ground) && block.getMaterial() == Material.water) {
                world.setBlock(i2, j2, k2, this.theBlock, this.theMeta, 2);
            }
        }
        return true;
    }
}
