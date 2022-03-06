// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;

public class LOTRNPCSelectByFaction implements IEntitySelector
{
    protected LOTRFaction faction;
    
    public LOTRNPCSelectByFaction(final LOTRFaction f) {
        this.faction = f;
    }
    
    public boolean isEntityApplicable(final Entity entity) {
        return entity.isEntityAlive() && LOTRMod.getNPCFaction(entity) == this.faction;
    }
}
