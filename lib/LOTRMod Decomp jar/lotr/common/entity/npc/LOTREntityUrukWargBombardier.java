// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityUrukWargBombardier extends LOTREntityWargBombardier
{
    public LOTREntityUrukWargBombardier(final World world) {
        super(world);
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.URUK_HAI;
    }
    
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
