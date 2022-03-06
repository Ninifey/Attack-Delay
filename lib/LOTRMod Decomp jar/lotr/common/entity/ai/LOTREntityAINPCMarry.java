// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityXPOrb;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.LOTRLevelData;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCMarry extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    private World theWorld;
    private LOTREntityNPC theSpouse;
    private int marryDelay;
    private double moveSpeed;
    
    public LOTREntityAINPCMarry(final LOTREntityNPC npc, final double d) {
        this.marryDelay = 0;
        this.theNPC = npc;
        this.theWorld = ((Entity)npc).worldObj;
        this.moveSpeed = d;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theNPC.getClass() != this.theNPC.familyInfo.marriageEntityClass || this.theNPC.familyInfo.spouseUniqueID != null || this.theNPC.familyInfo.getAge() != 0 || this.theNPC.getEquipmentInSlot(4) != null || this.theNPC.getEquipmentInSlot(0) == null) {
            return false;
        }
        final List list = ((Entity)this.theNPC).worldObj.getEntitiesWithinAABB(this.theNPC.familyInfo.marriageEntityClass, ((Entity)this.theNPC).boundingBox.expand(16.0, 4.0, 16.0));
        LOTREntityNPC spouse = null;
        double distanceSq = Double.MAX_VALUE;
        for (final LOTREntityNPC candidate : list) {
            if (this.theNPC.familyInfo.canMarryNPC(candidate) && candidate.familyInfo.canMarryNPC(this.theNPC)) {
                final double d = this.theNPC.getDistanceSqToEntity((Entity)candidate);
                if (d > distanceSq) {
                    continue;
                }
                distanceSq = d;
                spouse = candidate;
            }
        }
        if (spouse == null) {
            return false;
        }
        this.theSpouse = spouse;
        return true;
    }
    
    public boolean continueExecuting() {
        return this.theSpouse != null && this.theSpouse.isEntityAlive() && this.theNPC.familyInfo.canMarryNPC(this.theSpouse) && this.theSpouse.familyInfo.canMarryNPC(this.theNPC);
    }
    
    public void resetTask() {
        this.theSpouse = null;
        this.marryDelay = 0;
    }
    
    public void updateTask() {
        this.theNPC.getLookHelper().setLookPositionWithEntity((Entity)this.theSpouse, 10.0f, (float)this.theNPC.getVerticalFaceSpeed());
        this.theNPC.getNavigator().tryMoveToEntityLiving((Entity)this.theSpouse, this.moveSpeed);
        ++this.marryDelay;
        if (this.marryDelay % 20 == 0) {
            this.theNPC.spawnHearts();
        }
        if (this.marryDelay >= 60 && this.theNPC.getDistanceSqToEntity((Entity)this.theSpouse) < 9.0) {
            this.marry();
        }
    }
    
    private void marry() {
        this.theNPC.familyInfo.spouseUniqueID = this.theSpouse.getUniqueID();
        this.theSpouse.familyInfo.spouseUniqueID = this.theNPC.getUniqueID();
        this.theNPC.setCurrentItemOrArmor(0, (ItemStack)null);
        this.theNPC.setCurrentItemOrArmor(4, new ItemStack(this.theNPC.familyInfo.marriageRing));
        this.theSpouse.setCurrentItemOrArmor(0, (ItemStack)null);
        this.theSpouse.setCurrentItemOrArmor(4, new ItemStack(this.theNPC.familyInfo.marriageRing));
        this.theNPC.changeNPCNameForMarriage(this.theSpouse);
        this.theSpouse.changeNPCNameForMarriage(this.theNPC);
        final int maxChildren = this.theNPC.familyInfo.getRandomMaxChildren();
        this.theNPC.familyInfo.maxChildren = maxChildren;
        this.theSpouse.familyInfo.maxChildren = maxChildren;
        this.theNPC.familyInfo.setMaxBreedingDelay();
        this.theSpouse.familyInfo.setMaxBreedingDelay();
        this.theNPC.spawnHearts();
        this.theSpouse.spawnHearts();
        final EntityPlayer ringPlayer = this.theNPC.familyInfo.getRingGivingPlayer();
        if (ringPlayer != null) {
            LOTRLevelData.getData(ringPlayer).addAlignment(ringPlayer, LOTRAlignmentValues.MARRIAGE_BONUS, this.theNPC.getFaction(), (Entity)this.theNPC);
            if (this.theNPC.familyInfo.marriageAchievement != null) {
                LOTRLevelData.getData(ringPlayer).addAchievement(this.theNPC.familyInfo.marriageAchievement);
            }
        }
        final EntityPlayer ringPlayerSpouse = this.theSpouse.familyInfo.getRingGivingPlayer();
        if (ringPlayerSpouse != null) {
            LOTRLevelData.getData(ringPlayerSpouse).addAlignment(ringPlayerSpouse, LOTRAlignmentValues.MARRIAGE_BONUS, this.theSpouse.getFaction(), (Entity)this.theSpouse);
            if (this.theSpouse.familyInfo.marriageAchievement != null) {
                LOTRLevelData.getData(ringPlayerSpouse).addAchievement(this.theSpouse.familyInfo.marriageAchievement);
            }
        }
        this.theWorld.spawnEntityInWorld((Entity)new EntityXPOrb(this.theWorld, ((Entity)this.theNPC).posX, ((Entity)this.theNPC).posY, ((Entity)this.theNPC).posZ, this.theNPC.getRNG().nextInt(8) + 2));
    }
}
