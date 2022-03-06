// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTRFamilyInfo;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityList;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCMate extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    private World theWorld;
    private LOTREntityNPC theSpouse;
    private int spawnBabyDelay;
    private double moveSpeed;
    
    public LOTREntityAINPCMate(final LOTREntityNPC npc, final double d) {
        this.spawnBabyDelay = 0;
        this.theNPC = npc;
        this.theWorld = ((Entity)npc).worldObj;
        this.moveSpeed = d;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theNPC.getClass() != this.theNPC.familyInfo.marriageEntityClass || this.theNPC.familyInfo.spouseUniqueID == null || this.theNPC.familyInfo.children >= this.theNPC.familyInfo.maxChildren || this.theNPC.familyInfo.getAge() != 0) {
            return false;
        }
        this.theSpouse = this.theNPC.familyInfo.getSpouse();
        return this.theSpouse != null && this.theNPC.getDistanceToEntity((Entity)this.theSpouse) < 16.0 && this.theSpouse.familyInfo.children < this.theSpouse.familyInfo.maxChildren && this.theSpouse.familyInfo.getAge() == 0;
    }
    
    public boolean continueExecuting() {
        return this.theSpouse.isEntityAlive() && this.spawnBabyDelay < 60 && this.theNPC.familyInfo.getAge() == 0 && this.theSpouse.familyInfo.getAge() == 0;
    }
    
    public void resetTask() {
        this.theSpouse = null;
        this.spawnBabyDelay = 0;
    }
    
    public void updateTask() {
        this.theNPC.getLookHelper().setLookPositionWithEntity((Entity)this.theSpouse, 10.0f, (float)this.theNPC.getVerticalFaceSpeed());
        this.theNPC.getNavigator().tryMoveToEntityLiving((Entity)this.theSpouse, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay % 20 == 0) {
            this.theNPC.spawnHearts();
        }
        if (this.spawnBabyDelay >= 60 && this.theNPC.getDistanceSqToEntity((Entity)this.theSpouse) < 9.0) {
            this.spawnBaby();
        }
    }
    
    private void spawnBaby() {
        final LOTREntityNPC baby = (LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.theNPC.familyInfo.marriageEntityClass), this.theWorld);
        final LOTREntityNPC maleParent = this.theNPC.familyInfo.isMale() ? this.theNPC : this.theSpouse;
        final LOTREntityNPC femaleParent = this.theNPC.familyInfo.isMale() ? this.theSpouse : this.theNPC;
        baby.familyInfo.setChild();
        baby.familyInfo.setMale(baby.getRNG().nextBoolean());
        baby.familyInfo.maleParentID = maleParent.getUniqueID();
        baby.familyInfo.femaleParentID = femaleParent.getUniqueID();
        baby.createNPCChildName(maleParent, femaleParent);
        baby.onSpawnWithEgg(null);
        baby.setLocationAndAngles(((Entity)this.theNPC).posX, ((Entity)this.theNPC).posY, ((Entity)this.theNPC).posZ, 0.0f, 0.0f);
        baby.isNPCPersistent = true;
        this.theWorld.spawnEntityInWorld((Entity)baby);
        this.theNPC.familyInfo.setMaxBreedingDelay();
        this.theSpouse.familyInfo.setMaxBreedingDelay();
        final LOTRFamilyInfo familyInfo = this.theNPC.familyInfo;
        ++familyInfo.children;
        final LOTRFamilyInfo familyInfo2 = this.theSpouse.familyInfo;
        ++familyInfo2.children;
        this.theNPC.spawnHearts();
        this.theSpouse.spawnHearts();
    }
}
