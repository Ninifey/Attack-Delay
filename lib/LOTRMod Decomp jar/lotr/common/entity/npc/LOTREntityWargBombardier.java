// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.entity.ai.LOTREntityAIWargBombardierAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public abstract class LOTREntityWargBombardier extends LOTREntityWarg
{
    public LOTREntityWargBombardier(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIWargBombardierAttack(this, 1.7);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(21, (Object)35);
        ((Entity)this).dataWatcher.addObject(22, (Object)1);
    }
    
    public int getBombFuse() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(21);
    }
    
    public void setBombFuse(final int i) {
        ((Entity)this).dataWatcher.updateObject(21, (Object)(byte)i);
    }
    
    public int getBombStrengthLevel() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(22);
    }
    
    public void setBombStrengthLevel(final int i) {
        ((Entity)this).dataWatcher.updateObject(22, (Object)(byte)i);
    }
    
    @Override
    public LOTREntityNPC createWargRider() {
        return null;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("BombFuse", (byte)this.getBombFuse());
        nbt.setByte("BombStrengthLevel", (byte)this.getBombStrengthLevel());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBombFuse(nbt.getByte("BombFuse"));
        this.setBombStrengthLevel(nbt.getByte("BombStrengthLevel"));
    }
    
    @Override
    public boolean canWargBeRidden() {
        return false;
    }
    
    @Override
    public boolean isMountSaddled() {
        return false;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getBombFuse() < 35) {
            ((Entity)this).worldObj.spawnParticle("smoke", ((Entity)this).posX, ((Entity)this).posY + 2.2, ((Entity)this).posZ, 0.0, 0.0, 0.0);
        }
    }
    
    public void setAttackTarget(final EntityLivingBase target) {
        super.setAttackTarget(target);
        if (target != null) {
            ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
}
