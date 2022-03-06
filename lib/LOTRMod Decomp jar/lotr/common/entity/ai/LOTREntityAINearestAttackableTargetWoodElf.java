// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityWoodElf;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;

public class LOTREntityAINearestAttackableTargetWoodElf extends LOTREntityAINearestAttackableTargetBasic
{
    public LOTREntityAINearestAttackableTargetWoodElf(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag) {
        super(entity, targetClass, chance, flag);
    }
    
    public LOTREntityAINearestAttackableTargetWoodElf(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag, final IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
    }
    
    @Override
    protected boolean isPlayerSuitableAlignmentTarget(final EntityPlayer entityplayer) {
        final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction((Entity)super.taskOwner));
        if (alignment >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
            return false;
        }
        if (alignment >= 0.0f) {
            int chance = Math.round(alignment * 20.0f);
            chance = Math.max(chance, 1);
            return super.taskOwner.getRNG().nextInt(chance) == 0;
        }
        return super.isPlayerSuitableAlignmentTarget(entityplayer);
    }
}
