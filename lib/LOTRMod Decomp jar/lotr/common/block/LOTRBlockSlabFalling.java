// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.block.BlockFalling;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;

public abstract class LOTRBlockSlabFalling extends LOTRBlockSlabBase
{
    public LOTRBlockSlabFalling(final boolean flag, final Material material, final int n) {
        super(flag, material, n);
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        world.scheduleBlockUpdate(i, j, k, (Block)this, this.func_149738_a(world));
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        world.scheduleBlockUpdate(i, j, k, (Block)this, this.func_149738_a(world));
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isClient) {
            this.tryBlockFall(world, i, j, k);
        }
    }
    
    private void tryBlockFall(final World world, final int i, int j, final int k) {
        if (BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0) {
            final int range = 32;
            if (!BlockFalling.fallInstantly && world.checkChunksExist(i - range, j - range, k - range, i + range, j + range, k + range)) {
                if (!world.isClient) {
                    final EntityFallingBlock fallingBlock = new EntityFallingBlock(world, (double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), (Block)this, world.getBlockMetadata(i, j, k) & 0x7);
                    world.spawnEntityInWorld((Entity)fallingBlock);
                }
            }
            else {
                world.setBlockToAir(i, j, k);
                while (BlockFalling.func_149831_e(world, i, j - 1, k) && j > 0) {
                    --j;
                }
                if (j > 0) {
                    world.setBlock(i, j, k, (Block)this);
                }
            }
        }
    }
    
    public int func_149738_a(final World world) {
        return 2;
    }
}
