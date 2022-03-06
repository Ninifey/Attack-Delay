// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;

public class LOTREntityAINearestAttackableTargetOrc extends LOTREntityAINearestAttackableTargetBasic
{
    public LOTREntityAINearestAttackableTargetOrc(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag) {
        super(entity, targetClass, chance, flag);
    }
    
    public LOTREntityAINearestAttackableTargetOrc(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag, final IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
    }
    
    @Override
    protected boolean isPlayerSuitableAlignmentTarget(final EntityPlayer entityplayer) {
        final LOTRFaction faction = LOTRMod.getNPCFaction((Entity)super.taskOwner);
        if (faction != LOTRFaction.MORDOR) {
            return super.isPlayerSuitableAlignmentTarget(entityplayer);
        }
        final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(faction);
        if (alignment >= 100.0f) {
            return false;
        }
        if (alignment >= 0.0f) {
            int chance = Math.round(alignment * 5.0f);
            chance = Math.max(chance, 1);
            return super.taskOwner.getRNG().nextInt(chance) == 0;
        }
        return super.isPlayerSuitableAlignmentTarget(entityplayer);
    }
}
