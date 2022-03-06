// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityGondorGreengrocer extends LOTREntityGondorMarketTrader
{
    public LOTREntityGondorGreengrocer(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.GONDOR_GREENGROCER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.GONDOR_GREENGROCER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(3);
        if (i == 0) {
            super.npcItemsInv.setIdleItem(new ItemStack(Items.apple));
        }
        else if (i == 1) {
            super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.appleGreen));
        }
        else if (i == 2) {
            super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pear));
        }
        return data;
    }
}
