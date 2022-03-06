// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRInventoryNPC extends LOTREntityInventory
{
    protected LOTREntityNPC theNPC;
    
    public LOTRInventoryNPC(final String s, final LOTREntityNPC npc, final int i) {
        super(s, (EntityLivingBase)npc, i);
        this.theNPC = npc;
    }
    
    @Override
    protected void dropItem(final ItemStack itemstack) {
        this.theNPC.npcDropItem(itemstack, 0.0f, false);
    }
}
