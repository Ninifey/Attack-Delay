// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityRohanBuilder extends LOTREntityRohanMarketTrader
{
    public LOTREntityRohanBuilder(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.ROHAN_BUILDER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.ROHAN_BUILDER_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_pickaxe));
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.brick, 1, 4));
        return data;
    }
}
