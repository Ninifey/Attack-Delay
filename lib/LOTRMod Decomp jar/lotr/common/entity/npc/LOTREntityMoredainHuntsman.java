// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityMoredainHuntsman extends LOTREntityMoredainVillageTrader
{
    public LOTREntityMoredainHuntsman(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.MOREDAIN_HUNTSMAN_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.MOREDAIN_HUNTSMAN_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setSpearBackup(null);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredain));
        return data;
    }
}
