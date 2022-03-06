// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import lotr.common.LOTRMod;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.init.Items;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityAnimal;

public class LOTREntityFlamingo extends EntityAnimal
{
    public boolean field_753_a;
    public float field_752_b;
    public float destPos;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h;
    public static final int NECK_TIME = 20;
    public static final int FISHING_TIME = 160;
    public static final int FISHING_TIME_TOTAL = 200;
    
    public LOTREntityFlamingo(final World world) {
        super(world);
        this.field_753_a = false;
        this.field_755_h = 5.0f;
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(false);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.3));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2, Items.fish, false));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.2));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 6.0f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    private int getFishingTick() {
        final int i = ((Entity)this).dataWatcher.getWatchableObjectInt(16);
        return i;
    }
    
    public int getFishingTickPre() {
        return this.getFishingTick() >> 16;
    }
    
    public int getFishingTickCur() {
        return this.getFishingTick() & 0xFFFF;
    }
    
    public void setFishingTick(final int pre, final int cur) {
        final int i = pre << 16 | (cur & 0xFFFF);
        ((Entity)this).dataWatcher.updateObject(16, (Object)i);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_756_e = this.field_752_b;
        this.field_757_d = this.destPos;
        this.destPos += (float)(((((Entity)this).onGround || ((Entity)this).inWater) ? -1 : 4) * 0.3);
        if (this.destPos < 0.0f) {
            this.destPos = 0.0f;
        }
        if (this.destPos > 1.0f) {
            this.destPos = 1.0f;
        }
        if (!((Entity)this).onGround && !((Entity)this).inWater && this.field_755_h < 1.0f) {
            this.field_755_h = 1.0f;
        }
        this.field_755_h *= (float)0.9;
        if (!((Entity)this).onGround && !((Entity)this).inWater && ((Entity)this).motionY < 0.0) {
            ((Entity)this).motionY *= 0.6;
        }
        this.field_752_b += this.field_755_h * 2.0f;
        if (!((Entity)this).worldObj.isClient && !this.isChild() && !this.isInLove() && this.getFishingTickCur() == 0 && ((Entity)this).rand.nextInt(600) == 0) {
            final Block block = ((Entity)this).worldObj.getBlock(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).boundingBox.minY), MathHelper.floor_double(((Entity)this).posZ));
            if (block == Blocks.water) {
                this.setFishingTick(200, 200);
            }
        }
        if (this.getFishingTickCur() > 0) {
            if (!((Entity)this).worldObj.isClient) {
                final int cur = this.getFishingTickCur();
                this.setFishingTick(cur, cur - 1);
            }
            else {
                for (int i = 0; i < 3; ++i) {
                    final double d = ((Entity)this).posX + MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.3, 0.3);
                    final double d2 = ((Entity)this).boundingBox.minY + MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.3, 0.3);
                    final double d3 = ((Entity)this).posZ + MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.3, 0.3);
                    ((Entity)this).worldObj.spawnParticle("bubble", d, d2, d3, 0.0, 0.0, 0.0);
                }
            }
        }
        if (!((Entity)this).worldObj.isClient && this.isInLove() && this.getFishingTickCur() > 20) {
            this.setFishingTick(20, 20);
        }
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float f) {
        final boolean flag = super.attackEntityFrom(source, f);
        if (flag && !((Entity)this).worldObj.isClient && this.getFishingTickCur() > 20) {
            this.setFishingTick(20, 20);
        }
        return flag;
    }
    
    protected void fall(final float f) {
    }
    
    protected String getLivingSound() {
        return "lotr:flamingo.say";
    }
    
    protected String getHurtSound() {
        return "lotr:flamingo.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:flamingo.death";
    }
    
    protected Item func_146068_u() {
        return Items.feather;
    }
    
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack.getItem() == Items.fish;
    }
    
    public EntityAgeable createChild(final EntityAgeable entity) {
        return (EntityAgeable)new LOTREntityFlamingo(((Entity)this).worldObj);
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
