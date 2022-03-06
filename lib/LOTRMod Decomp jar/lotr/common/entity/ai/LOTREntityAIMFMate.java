// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.Random;
import net.minecraft.entity.item.EntityXPOrb;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityAgeable;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.animal.LOTREntityAnimalMF;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIMFMate extends EntityAIBase
{
    private LOTREntityAnimalMF theAnimal;
    World theWorld;
    public LOTREntityAnimalMF targetMate;
    int breeding;
    double moveSpeed;
    
    public LOTREntityAIMFMate(final LOTREntityAnimalMF animal, final double d) {
        this.breeding = 0;
        this.theAnimal = animal;
        this.theWorld = ((Entity)animal).worldObj;
        this.moveSpeed = d;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (!this.theAnimal.isInLove()) {
            return false;
        }
        this.targetMate = this.findMate();
        return this.targetMate != null;
    }
    
    public boolean continueExecuting() {
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.breeding < 60;
    }
    
    public void resetTask() {
        this.targetMate = null;
        this.breeding = 0;
    }
    
    public void updateTask() {
        this.theAnimal.getLookHelper().setLookPositionWithEntity((Entity)this.targetMate, 10.0f, (float)this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.targetMate, this.moveSpeed);
        ++this.breeding;
        if (this.breeding == 60 && this.theAnimal.getDistanceSqToEntity((Entity)this.targetMate) < 9.0) {
            this.procreate();
        }
    }
    
    private LOTREntityAnimalMF findMate() {
        final float f = 8.0f;
        final Class mateClass = this.theAnimal.getAnimalMFBaseClass();
        final List list = this.theWorld.getEntitiesWithinAABB(mateClass, ((Entity)this.theAnimal).boundingBox.expand((double)f, (double)f, (double)f));
        for (final LOTREntityAnimalMF mate : list) {
            if (this.theAnimal.canMateWith(mate)) {
                return mate;
            }
        }
        return null;
    }
    
    private void procreate() {
        final EntityAgeable babyAnimal = this.theAnimal.createChild((EntityAgeable)this.targetMate);
        if (babyAnimal != null) {
            this.theAnimal.setGrowingAge(6000);
            this.targetMate.setGrowingAge(6000);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            babyAnimal.setGrowingAge(-24000);
            babyAnimal.setLocationAndAngles(((Entity)this.theAnimal).posX, ((Entity)this.theAnimal).posY, ((Entity)this.theAnimal).posZ, 0.0f, 0.0f);
            this.theWorld.spawnEntityInWorld((Entity)babyAnimal);
            final Random rand = this.theAnimal.getRNG();
            for (int i = 0; i < 7; ++i) {
                final double d = ((Entity)this.theAnimal).posX + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * ((Entity)this.theAnimal).width;
                final double d2 = ((Entity)this.theAnimal).posY + 0.5 + rand.nextFloat() * ((Entity)this.theAnimal).height;
                final double d3 = ((Entity)this.theAnimal).posZ + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * ((Entity)this.theAnimal).width;
                final double d4 = rand.nextGaussian() * 0.02;
                final double d5 = rand.nextGaussian() * 0.02;
                final double d6 = rand.nextGaussian() * 0.02;
                this.theWorld.spawnParticle("heart", d, d2, d3, d4, d5, d6);
            }
            if (LOTRMod.canDropLoot(this.theWorld)) {
                this.theWorld.spawnEntityInWorld((Entity)new EntityXPOrb(this.theWorld, ((Entity)this.theAnimal).posX, ((Entity)this.theAnimal).posY, ((Entity)this.theAnimal).posZ, rand.nextInt(4) + 1));
            }
        }
    }
}
