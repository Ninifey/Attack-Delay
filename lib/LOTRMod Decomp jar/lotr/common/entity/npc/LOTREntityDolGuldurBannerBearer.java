// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityDolGuldurBannerBearer extends LOTREntityDolGuldurOrc implements LOTRBannerBearer
{
    public LOTREntityDolGuldurBannerBearer(final World world) {
        super(world);
    }
    
    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.DOL_GULDUR;
    }
}
