// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityRohanLumberman extends LOTREntityRohanMarketTrader
{
    public LOTREntityRohanLumberman(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.ROHAN_LUMBERMAN_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.ROHAN_LUMBERMAN_SELL;
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
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 6834742);
        LOTRItemLeatherHat.setFeatherColor(hat, 3916082);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }
}
