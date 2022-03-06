// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityUmbarFarmer;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarFarm extends LOTRWorldGenSouthronFarm
{
    public LOTRWorldGenUmbarFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean isUmbar() {
        return true;
    }
    
    @Override
    protected LOTREntityNearHaradrimBase createFarmer(final World world) {
        return new LOTREntityUmbarFarmer(world);
    }
}
