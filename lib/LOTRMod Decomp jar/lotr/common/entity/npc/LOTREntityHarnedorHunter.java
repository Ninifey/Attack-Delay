// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityHarnedorHunter extends LOTREntityHarnedorTrader
{
    public LOTREntityHarnedorHunter(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HARAD_HUNTER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HARAD_HUNTER_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.fur));
        this.setCurrentItemOrArmor(1, new ItemStack((Item)Items.leather_boots));
        this.setCurrentItemOrArmor(2, new ItemStack((Item)Items.leather_leggings));
        this.setCurrentItemOrArmor(3, new ItemStack((Item)Items.leather_chestplate));
        return data;
    }
}
