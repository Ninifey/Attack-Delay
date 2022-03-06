// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.item.LOTRItemLionRug;
import net.minecraft.world.World;

public class LOTREntityLioness extends LOTREntityLionBase
{
    public LOTREntityLioness(final World world) {
        super(world);
    }
    
    @Override
    public boolean isMale() {
        return false;
    }
    
    @Override
    protected LOTRItemLionRug.LionRugType getLionRugType() {
        return LOTRItemLionRug.LionRugType.LIONESS;
    }
}
