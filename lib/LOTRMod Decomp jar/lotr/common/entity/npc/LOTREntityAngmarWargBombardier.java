// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityAngmarWargBombardier extends LOTREntityWargBombardier
{
    public LOTREntityAngmarWargBombardier(final World world) {
        super(world);
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }
    
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
