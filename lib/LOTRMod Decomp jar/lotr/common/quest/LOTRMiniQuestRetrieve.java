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
import java.util.UUID;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRPlayerData;

public class LOTRMiniQuestRetrieve extends LOTRMiniQuestCollect
{
    public Class killEntityType;
    public boolean hasDropped;
    
    public LOTRMiniQuestRetrieve(final LOTRPlayerData pd) {
        super(pd);
        this.hasDropped = false;
    }
    
    public static UUID getRetrieveQuestID(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRRetrieveID")) {
            final String id = itemstack.getTagCompound().getString("LOTRRetrieveID");
            return UUID.fromString(id);
        }
        return null;
    }
    
    public static void setRetrieveQuest(final ItemStack itemstack, final LOTRMiniQuest quest) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setString("LOTRRetrieveID", quest.questUUID.toString());
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("KillClass", LOTREntities.getStringFromClass(this.killEntityType));
        nbt.setBoolean("HasDropped", this.hasDropped);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.killEntityType = LOTREntities.getClassFromString(nbt.getString("KillClass"));
        this.hasDropped = nbt.getBoolean("HasDropped");
    }
    
    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.killEntityType != null && EntityLivingBase.class.isAssignableFrom(this.killEntityType);
    }
    
    @Override
    public String getQuestObjective() {
        if (super.collectTarget == 1) {
            return StatCollector.translateToLocalFormatted("lotr.miniquest.retrieve1", new Object[] { super.collectItem.getDisplayName() });
        }
        return StatCollector.translateToLocalFormatted("lotr.miniquest.retrieveMany", new Object[] { super.collectTarget, super.collectItem.getDisplayName() });
    }
    
    @Override
    public String getProgressedObjectiveInSpeech() {
        if (super.collectTarget == 1) {
            return super.collectItem.getDisplayName();
        }
        return super.collectTarget + " " + super.collectItem.getDisplayName();
    }
    
    @Override
    protected boolean isQuestItem(final ItemStack itemstack) {
        if (super.isQuestItem(itemstack)) {
            final UUID retrieveQuestID = getRetrieveQuestID(itemstack);
            return retrieveQuestID.equals(super.questUUID);
        }
        return false;
    }
    
    @Override
    public void onKill(final EntityPlayer entityplayer, final EntityLivingBase entity) {
        if (!this.hasDropped && this.killEntityType.isAssignableFrom(entity.getClass())) {
            final ItemStack itemstack = super.collectItem.copy();
            setRetrieveQuest(itemstack, this);
            this.hasDropped = true;
            this.updateQuest();
        }
    }
    
    public static class QFRetrieve extends QFCollect<LOTRMiniQuestRetrieve>
    {
        private Class entityType;
        
        public QFRetrieve(final String name) {
            super(name);
        }
        
        public QFRetrieve setKillEntity(final Class entityClass) {
            this.entityType = entityClass;
            return this;
        }
        
        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestRetrieve.class;
        }
        
        @Override
        public LOTRMiniQuestRetrieve createQuest(final LOTREntityNPC npc, final Random rand) {
            final LOTRMiniQuestRetrieve quest = super.createQuest(npc, rand);
            quest.killEntityType = this.entityType;
            return quest;
        }
    }
}
