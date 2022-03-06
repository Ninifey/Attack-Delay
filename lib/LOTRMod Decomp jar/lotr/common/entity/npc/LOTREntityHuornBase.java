// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.culling.Frustrum;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public abstract class LOTREntityHuornBase extends LOTREntityTree
{
    public boolean ignoringFrustumForRender;
    
    public LOTREntityHuornBase(final World world) {
        super(world);
        this.ignoringFrustumForRender = false;
        this.setSize(1.5f, 4.0f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.5, false));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    public boolean isHuornActive() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setHuornActive(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(4.0);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRender3d(final double d, final double d1, final double d2) {
        final EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
        final float f = LOTRTickHandlerClient.renderTick;
        final double viewX = ((Entity)viewer).lastTickPosX + (((Entity)viewer).posX - ((Entity)viewer).lastTickPosX) * f;
        final double viewY = ((Entity)viewer).lastTickPosY + (((Entity)viewer).posY - ((Entity)viewer).lastTickPosY) * f;
        final double viewZ = ((Entity)viewer).lastTickPosZ + (((Entity)viewer).posZ - ((Entity)viewer).lastTickPosZ) * f;
        final Frustrum camera = new Frustrum();
        camera.setPosition(viewX, viewY, viewZ);
        final AxisAlignedBB expandedBB = ((Entity)this).boundingBox.expand(2.0, 3.0, 2.0);
        if (camera.isBoundingBoxInFrustum(expandedBB)) {
            this.ignoringFrustumForRender = true;
            ((Entity)this).ignoreFrustumCheck = true;
        }
        return super.isInRangeToRender3d(d, d1, d2);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            final boolean active = !this.getNavigator().noPath() || (this.getAttackTarget() != null && this.getAttackTarget().isEntityAlive());
            this.setHuornActive(active);
        }
    }
    
    protected int decreaseAirSupply(final int i) {
        return i;
    }
    
    public void applyEntityCollision(final Entity entity) {
        if (this.isHuornActive()) {
            super.applyEntityCollision(entity);
        }
        else {
            final double x = ((Entity)this).motionX;
            final double y = ((Entity)this).motionY;
            final double z = ((Entity)this).motionZ;
            super.applyEntityCollision(entity);
            ((Entity)this).motionX = x;
            ((Entity)this).motionY = y;
            ((Entity)this).motionZ = z;
        }
    }
    
    public void collideWithEntity(final Entity entity) {
        if (this.isHuornActive()) {
            super.collideWithEntity(entity);
        }
        else {
            final double x = ((Entity)this).motionX;
            final double y = ((Entity)this).motionY;
            final double z = ((Entity)this).motionZ;
            super.collideWithEntity(entity);
            ((Entity)this).motionX = x;
            ((Entity)this).motionY = y;
            ((Entity)this).motionZ = z;
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && !((Entity)this).worldObj.isClient && !this.isHuornActive()) {
            this.setHuornActive(true);
        }
        return flag;
    }
    
    protected String getHurtSound() {
        return Blocks.log.stepSound.getDigResourcePath();
    }
    
    protected String getDeathSound() {
        return Blocks.log.stepSound.getDigResourcePath();
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
}
