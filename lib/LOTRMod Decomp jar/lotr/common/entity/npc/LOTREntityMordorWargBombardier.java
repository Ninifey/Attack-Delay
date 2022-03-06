// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMordorWargBombardier extends LOTREntityWargBombardier
{
    public LOTREntityMordorWargBombardier(final World world) {
        super(world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.setWargType(WargType.BLACK);
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
