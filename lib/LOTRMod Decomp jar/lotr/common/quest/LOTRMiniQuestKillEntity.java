// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTREntities;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRPlayerData;

public class LOTRMiniQuestKillEntity extends LOTRMiniQuestKill
{
    public Class entityType;
    
    public LOTRMiniQuestKillEntity(final LOTRPlayerData pd) {
        super(pd);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("KillClass", LOTREntities.getStringFromClass(this.entityType));
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.entityType = LOTREntities.getClassFromString(nbt.getString("KillClass"));
    }
    
    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.entityType != null && EntityLivingBase.class.isAssignableFrom(this.entityType);
    }
    
    @Override
    protected String getKillTargetName() {
        final String entityName = LOTREntities.getStringFromClass(this.entityType);
        return StatCollector.translateToLocal("entity." + entityName + ".name");
    }
    
    @Override
    public void onKill(final EntityPlayer entityplayer, final EntityLivingBase entity) {
        if (super.killCount < super.killTarget && this.entityType.isAssignableFrom(entity.getClass())) {
            ++super.killCount;
            this.updateQuest();
        }
    }
    
    public static class QFKillEntity extends QFKill<LOTRMiniQuestKillEntity>
    {
        private Class entityType;
        
        public QFKillEntity(final String name) {
            super(name);
        }
        
        public QFKillEntity setKillEntity(final Class entityClass, final int min, final int max) {
            this.entityType = entityClass;
            this.setKillTarget(min, max);
            return this;
        }
        
        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestKillEntity.class;
        }
        
        @Override
        public LOTRMiniQuestKillEntity createQuest(final LOTREntityNPC npc, final Random rand) {
            final LOTRMiniQuestKillEntity quest = super.createQuest(npc, rand);
            quest.entityType = this.entityType;
            return quest;
        }
    }
}
