// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityEsgarothBannerBearer extends LOTREntityDaleSoldier implements LOTRBannerBearer
{
    public LOTREntityEsgarothBannerBearer(final World world) {
        super(world);
        super.npcShield = LOTRShields.ALIGNMENT_ESGAROTH;
    }
    
    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.ESGAROTH;
    }
}
