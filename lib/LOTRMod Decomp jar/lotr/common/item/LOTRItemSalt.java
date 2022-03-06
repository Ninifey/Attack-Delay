// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemSalt extends Item
{
    public LOTRItemSalt() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            boolean changedAny = false;
            final int range = 1 + world.rand.nextInt(3);
            final int yRange = range / 2;
            for (int i2 = -range; i2 <= range; ++i2) {
                for (int j2 = -yRange; j2 <= yRange; ++j2) {
                    for (int k2 = -range; k2 <= range; ++k2) {
                        final int i3 = i + i2;
                        final int j3 = j + j2;
                        final int k3 = k + k2;
                        if (i2 * i2 + k2 * k2 <= range * range) {
                            final Block block = world.getBlock(i3, j3, k3);
                            final int meta = world.getBlockMetadata(i3, j3, k3);
                            Block newBlock = null;
                            int newMeta = 0;
                            if (block == Blocks.grass || (block == Blocks.dirt && meta == 0) || block == Blocks.farmland) {
                                newBlock = Blocks.dirt;
                                newMeta = 1;
                            }
                            else if (block == LOTRMod.mudGrass || (block == LOTRMod.mud && meta == 0) || block == LOTRMod.mudFarmland) {
                                newBlock = LOTRMod.mud;
                                newMeta = 1;
                            }
                            if (newBlock != null) {
                                if ((i2 == 0 && j2 == 0 && k2 == 0) || world.rand.nextInt(3) != 0) {
                                    world.setBlock(i3, j3, k3, newBlock, newMeta, 3);
                                }
                                changedAny = true;
                            }
                        }
                    }
                }
            }
            if (changedAny) {
                --itemstack.stackSize;
                return true;
            }
        }
        return true;
    }
}
