// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRTreeType;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockFruitSapling extends LOTRBlockSaplingBase
{
    public LOTRBlockFruitSapling() {
        this.setSaplingNames("apple", "pear", "cherry", "mango");
    }
    
    @Override
    public void growTree(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        WorldGenerator treeGen = null;
        if (meta == 0) {
            treeGen = (WorldGenerator)LOTRTreeType.APPLE.create(true, random);
        }
        else if (meta == 1) {
            treeGen = (WorldGenerator)LOTRTreeType.PEAR.create(true, random);
        }
        else if (meta == 2) {
            treeGen = (WorldGenerator)LOTRTreeType.CHERRY.create(true, random);
        }
        else if (meta == 3) {
            treeGen = (WorldGenerator)LOTRTreeType.MANGO.create(true, random);
        }
        world.setBlock(i, j, k, Blocks.air, 0, 4);
        if (treeGen != null && !treeGen.generate(world, random, i, j, k)) {
            world.setBlock(i, j, k, (Block)this, meta, 4);
        }
    }
}
