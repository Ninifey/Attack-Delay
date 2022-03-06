// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityGulfHunter extends LOTREntityGulfTrader
{
    public LOTREntityGulfHunter(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.GULF_HUNTER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.GULF_HUNTER_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.lionFur));
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGemsbok));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGemsbok));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGemsbok));
        return data;
    }
}
