// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class LOTRSlotUnsmeltResult extends LOTRSlotProtected
{
    public LOTRSlotUnsmeltResult(final IInventory inv, final int i, final int j, final int k) {
        super(inv, i, j, k);
    }
    
    public void onPickupFromSlot(final EntityPlayer entityplayer, final ItemStack itemstack) {
        super.onPickupFromSlot(entityplayer, itemstack);
        if (!((Entity)entityplayer).worldObj.isClient) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.unsmelt);
        }
    }
}
