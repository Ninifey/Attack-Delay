// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;

public class LOTRNPCSelectForInfluence extends LOTRNPCSelectByFaction
{
    public LOTRNPCSelectForInfluence(final LOTRFaction f) {
        super(f);
    }
    
    @Override
    public boolean isEntityApplicable(final Entity entity) {
        final boolean flag = super.isEntityApplicable(entity);
        if (flag && entity instanceof LOTREntityNPC) {
            final LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (!npc.generatesControlZone()) {
                return false;
            }
        }
        return flag;
    }
}
