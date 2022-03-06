// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityOrcForge extends LOTRTileEntityAlloyForgeBase
{
    @Override
    public String getForgeName() {
        return StatCollector.translateToLocal("container.lotr.orcForge");
    }
    
    @Override
    public ItemStack getSmeltingResult(final ItemStack itemstack) {
        if (this.isWood(itemstack)) {
            final boolean isCharred = itemstack.getItem() == Item.getItemFromBlock(LOTRMod.wood) && itemstack.getItemDamage() == 3;
            if (!isCharred) {
                return new ItemStack(LOTRMod.wood, 1, 3);
            }
        }
        if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMorgulIron)) {
            return new ItemStack(LOTRMod.orcSteel);
        }
        if (itemstack.getItem() instanceof ItemFood) {
            final ItemFood food = (ItemFood)itemstack.getItem();
            if (food.isWolfsFavoriteMeat()) {
                return new ItemStack(Items.rotten_flesh);
            }
        }
        return super.getSmeltingResult(itemstack);
    }
    
    @Override
    protected ItemStack getAlloySmeltingResult(final ItemStack itemstack, final ItemStack alloyItem) {
        if (this.isIron(itemstack) && this.isCoal(alloyItem)) {
            return new ItemStack(LOTRMod.urukSteel);
        }
        if (this.isOrcSteel(itemstack) && alloyItem.getItem() == LOTRMod.guldurilCrystal) {
            return new ItemStack(LOTRMod.morgulSteel);
        }
        if (this.isOrcSteel(itemstack) && alloyItem.getItem() == LOTRMod.nauriteGem) {
            return new ItemStack(LOTRMod.blackUrukSteel);
        }
        return super.getAlloySmeltingResult(itemstack, alloyItem);
    }
}
