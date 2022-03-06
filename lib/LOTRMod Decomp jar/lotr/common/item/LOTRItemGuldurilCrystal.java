// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import lotr.common.block.LOTRBlockGuldurilBrick;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemGuldurilCrystal extends Item
{
    public LOTRItemGuldurilCrystal() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            return false;
        }
        final int guldurilBrickMeta = LOTRBlockGuldurilBrick.guldurilMetaForBlock(world.getBlock(i, j, k), world.getBlockMetadata(i, j, k));
        final boolean hasAlignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.ANGMAR) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DOL_GULDUR) >= 1.0f;
        if (guldurilBrickMeta >= 0) {
            if (hasAlignment) {
                world.setBlock(i, j, k, LOTRMod.guldurilBrick, guldurilBrickMeta, 3);
                --itemstack.stackSize;
                this.spawnCrystalParticles(world, i, j, k);
            }
            else {
                for (int l = 0; l < 8; ++l) {
                    final double d = i - 0.25 + world.rand.nextFloat() * 1.5;
                    final double d2 = j - 0.25 + world.rand.nextFloat() * 1.5;
                    final double d3 = k - 0.25 + world.rand.nextFloat() * 1.5;
                    world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
                }
                if (!world.isClient) {
                    LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR);
                }
            }
            return true;
        }
        if (world.getBlock(i, j, k) == LOTRMod.sapling && (world.getBlockMetadata(i, j, k) & 0x3) == 0x1 && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 0.0f) {
            world.setBlock(i, j, k, LOTRMod.corruptMallorn, 0, 3);
            --itemstack.stackSize;
            this.spawnCrystalParticles(world, i, j, k);
            return true;
        }
        return false;
    }
    
    private void spawnCrystalParticles(final World world, final int i, final int j, final int k) {
        for (int l = 0; l < 16; ++l) {
            final double d = i - 0.25 + world.rand.nextFloat() * 1.5;
            final double d2 = j - 0.25 + world.rand.nextFloat() * 1.5;
            final double d3 = k - 0.25 + world.rand.nextFloat() * 1.5;
            world.spawnParticle("iconcrack_" + Item.getIdFromItem((Item)this), d, d2, d3, 0.0, 0.0, 0.0);
        }
    }
}
