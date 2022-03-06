// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRMercenaryTradeEntry extends LOTRUnitTradeEntry
{
    private LOTRMercenary theMerc;
    
    private LOTRMercenaryTradeEntry(final LOTRMercenary merc) {
        super(merc.getClass(), merc.getMercBaseCost(), merc.getMercAlignmentRequired());
        this.theMerc = merc;
    }
    
    public static LOTRMercenaryTradeEntry createFor(final LOTRMercenary merc) {
        return new LOTRMercenaryTradeEntry(merc);
    }
    
    @Override
    public LOTREntityNPC getOrCreateHiredNPC(final World world) {
        return (LOTREntityNPC)this.theMerc;
    }
    
    @Override
    public boolean hasRequiredCostAndAlignment(final EntityPlayer entityplayer, final LOTRHireableBase trader) {
        return !((LOTREntityNPC)this.theMerc).hiredNPCInfo.isActive && super.hasRequiredCostAndAlignment(entityplayer, trader);
    }
}
