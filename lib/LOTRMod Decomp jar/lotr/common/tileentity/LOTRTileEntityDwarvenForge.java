// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityDwarvenForge extends LOTRTileEntityAlloyForgeBase
{
    @Override
    public String getForgeName() {
        return StatCollector.translateToLocal("container.lotr.dwarvenForge");
    }
    
    @Override
    public ItemStack getSmeltingResult(final ItemStack itemstack) {
        if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMithril)) {
            return new ItemStack(LOTRMod.mithril);
        }
        return super.getSmeltingResult(itemstack);
    }
    
    @Override
    protected ItemStack getAlloySmeltingResult(final ItemStack itemstack, final ItemStack alloyItem) {
        if (this.isIron(itemstack) && this.isCoal(alloyItem)) {
            return new ItemStack(LOTRMod.dwarfSteel);
        }
        if (this.isIron(itemstack) && alloyItem.getItem() == LOTRMod.quenditeCrystal) {
            return new ItemStack(LOTRMod.galvorn);
        }
        if (this.isIron(itemstack) && alloyItem.getItem() == Item.getItemFromBlock(LOTRMod.rock) && alloyItem.getItemDamage() == 3) {
            return new ItemStack(LOTRMod.blueDwarfSteel);
        }
        return super.getAlloySmeltingResult(itemstack, alloyItem);
    }
}
