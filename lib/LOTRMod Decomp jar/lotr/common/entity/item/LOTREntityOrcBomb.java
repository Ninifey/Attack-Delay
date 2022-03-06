// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.block.LOTRBlockOrcBomb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.item.EntityTNTPrimed;

public class LOTREntityOrcBomb extends EntityTNTPrimed
{
    public int orcBombFuse;
    public boolean droppedByPlayer;
    public boolean droppedByHiredUnit;
    public boolean droppedTargetingPlayer;
    
    public LOTREntityOrcBomb(final World world) {
        super(world);
    }
    
    public LOTREntityOrcBomb(final World world, final double d, final double d1, final double d2, final EntityLivingBase entity) {
        super(world, d, d1, d2, entity);
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    public int getBombStrengthLevel() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setBombStrengthLevel(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)i);
        this.orcBombFuse = 40 + LOTRBlockOrcBomb.getBombStrengthLevel(i) * 20;
    }
    
    public void setFuseFromExplosion() {
        this.orcBombFuse = ((Entity)this).worldObj.rand.nextInt(this.orcBombFuse / 4) + this.orcBombFuse / 8;
    }
    
    public void setFuseFromHiredUnit() {
        final int strength = LOTRBlockOrcBomb.getBombStrengthLevel(this.getBombStrengthLevel());
    }
    
    public void onUpdate() {
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        ((Entity)this).motionY -= 0.04;
        this.moveEntity(((Entity)this).motionX, ((Entity)this).motionY, ((Entity)this).motionZ);
        ((Entity)this).motionX *= 0.98;
        ((Entity)this).motionY *= 0.98;
        ((Entity)this).motionZ *= 0.98;
        if (((Entity)this).onGround) {
            ((Entity)this).motionX *= 0.7;
            ((Entity)this).motionZ *= 0.7;
            ((Entity)this).motionY *= -0.5;
        }
        --this.orcBombFuse;
        if (this.orcBombFuse <= 0 && !((Entity)this).worldObj.isClient) {
            this.setDead();
            this.explodeOrcBomb();
        }
        else {
            ((Entity)this).worldObj.spawnParticle("smoke", ((Entity)this).posX, ((Entity)this).posY + 0.7, ((Entity)this).posZ, 0.0, 0.0, 0.0);
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("DroppedByPlayer", this.droppedByPlayer);
        nbt.setBoolean("DroppedByHiredUnit", this.droppedByHiredUnit);
        nbt.setBoolean("DroppedTargetingPlayer", this.droppedTargetingPlayer);
        nbt.setInteger("BombStrengthLevel", this.getBombStrengthLevel());
        nbt.setInteger("OrcBombFuse", this.orcBombFuse);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.droppedByPlayer = nbt.getBoolean("DroppedByPlayer");
        this.droppedByHiredUnit = nbt.getBoolean("DroppedByHiredUnit");
        this.droppedTargetingPlayer = nbt.getBoolean("DroppedTargetingPlayer");
        this.setBombStrengthLevel(nbt.getInteger("BombStrengthLevel"));
        this.orcBombFuse = nbt.getInteger("OrcBombFuse");
    }
    
    private void explodeOrcBomb() {
        boolean doTerrainDamage = false;
        if (this.droppedByPlayer) {
            doTerrainDamage = true;
        }
        else if (this.droppedByHiredUnit) {
            doTerrainDamage = LOTRMod.canGrief(((Entity)this).worldObj);
        }
        else if (this.droppedTargetingPlayer) {
            doTerrainDamage = LOTRMod.canGrief(((Entity)this).worldObj);
        }
        final int meta = this.getBombStrengthLevel();
        final int strength = LOTRBlockOrcBomb.getBombStrengthLevel(meta);
        final boolean fire = LOTRBlockOrcBomb.isFireBomb(meta);
        ((Entity)this).worldObj.newExplosion((Entity)this, ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, (strength + 1) * 4.0f, fire, doTerrainDamage);
    }
}
