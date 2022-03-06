// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRReflection;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityShirePony extends LOTREntityHorse
{
    public static float PONY_SCALE;
    public boolean breedingFlag;
    
    public LOTREntityShirePony(final World world) {
        super(world);
        this.breedingFlag = false;
        this.setSize(((Entity)this).width * LOTREntityShirePony.PONY_SCALE, ((Entity)this).height * LOTREntityShirePony.PONY_SCALE);
    }
    
    public int getHorseType() {
        return (!this.breedingFlag && !((Entity)this).worldObj.isClient) ? 1 : 0;
    }
    
    public boolean func_110259_cr() {
        return false;
    }
    
    @Override
    protected void onLOTRHorseSpawn() {
        double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
        maxHealth *= 0.75;
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        jumpStrength *= 0.5;
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
        double moveSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
        moveSpeed *= 0.8;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
    }
    
    @Override
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 10.0, 28.0);
    }
    
    @Override
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.2, 1.0);
    }
    
    @Override
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.3);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable other) {
        final LOTREntityShirePony otherPony = (LOTREntityShirePony)other;
        this.breedingFlag = true;
        otherPony.breedingFlag = true;
        final EntityAgeable child = super.createChild((EntityAgeable)otherPony);
        this.breedingFlag = false;
        otherPony.breedingFlag = false;
        return child;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && ((Entity)this).riddenByEntity != null && ((Entity)this).riddenByEntity instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)((Entity)this).riddenByEntity;
            if (this.isHorseSaddled() && this.isChested()) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideShirePony);
            }
        }
    }
    
    protected String getLivingSound() {
        return "mob.horse.idle";
    }
    
    protected String getHurtSound() {
        return "mob.horse.hit";
    }
    
    protected String getDeathSound() {
        return "mob.horse.death";
    }
    
    protected String getAngrySoundName() {
        return "mob.horse.angry";
    }
    
    static {
        LOTREntityShirePony.PONY_SCALE = 0.8f;
    }
}
