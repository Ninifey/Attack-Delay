// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityEasterlingLumberman extends LOTREntityEasterlingMarketTrader
{
    public LOTREntityEasterlingLumberman(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.RHUN_LUMBERMAN_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.RHUN_LUMBERMAN_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        final int robeColor = 8538153;
        final ItemStack body = new ItemStack(LOTRMod.bodyKaftan);
        final ItemStack legs = new ItemStack(LOTRMod.legsKaftan);
        LOTRItemHaradRobes.setRobesColor(body, robeColor);
        LOTRItemHaradRobes.setRobesColor(legs, robeColor);
        this.setCurrentItemOrArmor(3, body);
        this.setCurrentItemOrArmor(2, legs);
        return data;
    }
}
