// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityDwarfBannerBearer extends LOTREntityDwarfWarrior implements LOTRBannerBearer
{
    public LOTREntityDwarfBannerBearer(final World world) {
        super(world);
    }
    
    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.DWARF;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarvenSilver));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarvenSilver));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarvenSilver));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDwarvenSilver));
        return data;
    }
}
