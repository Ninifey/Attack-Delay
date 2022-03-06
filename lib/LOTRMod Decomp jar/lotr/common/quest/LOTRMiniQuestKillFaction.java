// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;

public class LOTRMiniQuestKillFaction extends LOTRMiniQuestKill
{
    public LOTRFaction killFaction;
    
    public LOTRMiniQuestKillFaction(final LOTRPlayerData pd) {
        super(pd);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("KillFaction", this.killFaction.codeName());
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.killFaction = LOTRFaction.forName(nbt.getString("KillFaction"));
    }
    
    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.killFaction != null;
    }
    
    @Override
    protected String getKillTargetName() {
        return this.killFaction.factionEntityName();
    }
    
    @Override
    public void onKill(final EntityPlayer entityplayer, final EntityLivingBase entity) {
        if (super.killCount < super.killTarget && LOTRMod.getNPCFaction((Entity)entity) == this.killFaction) {
            ++super.killCount;
            this.updateQuest();
        }
    }
    
    public static class QFKillFaction extends QFKill<LOTRMiniQuestKillFaction>
    {
        private LOTRFaction killFaction;
        
        public QFKillFaction(final String name) {
            super(name);
        }
        
        public QFKillFaction setKillFaction(final LOTRFaction faction, final int min, final int max) {
            this.killFaction = faction;
            this.setKillTarget(min, max);
            return this;
        }
        
        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestKillFaction.class;
        }
        
        @Override
        public LOTRMiniQuestKillFaction createQuest(final LOTREntityNPC npc, final Random rand) {
            final LOTRMiniQuestKillFaction quest = super.createQuest(npc, rand);
            quest.killFaction = this.killFaction;
            return quest;
        }
    }
}
