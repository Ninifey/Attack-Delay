// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityMoredainHutmaker extends LOTREntityMoredainVillageTrader
{
    public LOTREntityMoredainHutmaker(final World world) {
        super(world);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.MOREDAIN_HUTMAKER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.MOREDAIN_HUTMAKER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(new ItemStack(Blocks.hardened_clay));
        return data;
    }
}
