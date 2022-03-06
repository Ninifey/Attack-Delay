// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import net.minecraft.util.IIcon;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.inventory.Slot;

public class LOTRSlotBarrel extends Slot
{
    private LOTRTileEntityBarrel theBarrel;
    private boolean isWater;
    
    public LOTRSlotBarrel(final LOTRTileEntityBarrel inv, final int i, final int j, final int k) {
        super((IInventory)inv, i, j, k);
        this.theBarrel = inv;
    }
    
    public LOTRSlotBarrel setWaterSource() {
        this.isWater = true;
        return this;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return this.theBarrel.barrelMode == 0 && (!this.isWater || LOTRBrewingRecipes.isWaterSource(itemstack));
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        IIcon barrelGui_emptyBucketSlotIcon;
        if (this.getSlotIndex() > 5) {
            final LOTRItemMug lotrItemMug = (LOTRItemMug)LOTRMod.mugAle;
            barrelGui_emptyBucketSlotIcon = LOTRItemMug.barrelGui_emptyBucketSlotIcon;
        }
        else {
            barrelGui_emptyBucketSlotIcon = null;
        }
        return barrelGui_emptyBucketSlotIcon;
    }
}
