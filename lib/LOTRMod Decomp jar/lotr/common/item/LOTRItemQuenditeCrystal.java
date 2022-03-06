// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemQuenditeCrystal extends Item
{
    public LOTRItemQuenditeCrystal() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            return false;
        }
        if (world.getBlock(i, j, k) == Blocks.grass) {
            if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.GALADHRIM) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.HIGH_ELF) >= 1.0f) {
                world.setBlock(i, j, k, LOTRMod.quenditeGrass, 0, 3);
                --itemstack.stackSize;
                for (int l = 0; l < 8; ++l) {
                    world.spawnParticle("iconcrack_" + Item.getIdFromItem((Item)this), i + (double)world.rand.nextFloat(), j + 1.5, k + (double)world.rand.nextFloat(), 0.0, 0.0, 0.0);
                }
            }
            else {
                for (int l = 0; l < 8; ++l) {
                    final double d = i + (double)world.rand.nextFloat();
                    final double d2 = j + 1.0;
                    final double d3 = k + (double)world.rand.nextFloat();
                    world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
                }
                if (!world.isClient) {
                    LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, LOTRFaction.GALADHRIM, LOTRFaction.HIGH_ELF);
                }
            }
            return true;
        }
        return false;
    }
}
