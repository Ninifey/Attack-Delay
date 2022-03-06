// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityNomadBrewer extends LOTREntityNomadTrader
{
    public LOTREntityNomadBrewer(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HARAD_BREWER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HARAD_BREWER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final ItemStack drink = new ItemStack(LOTRMod.mugAraq);
        LOTRItemMug.setVessel(drink, this.getHaradrimDrinks().getRandomVessel(((Entity)this).rand), true);
        super.npcItemsInv.setIdleItem(drink);
        return data;
    }
}
