// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.network.Packet;
import java.util.List;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import lotr.common.entity.animal.LOTREntityRabbit;

public class LOTREntitySaruman extends LOTREntityNPC
{
    private LOTREntityRabbit targetingRabbit;
    private int ticksChasingRabbit;
    private String randomNameTag;
    
    public LOTREntitySaruman(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIEat(this, new LOTRFoods(new ItemStack[] { new ItemStack(Blocks.log) }), 200));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLivingBase.class, 20.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(0, new ItemStack(LOTRMod.gandalfStaffWhite));
        return data;
    }
    
    public String getCustomNameTag() {
        if (this.randomNameTag == null) {
            final StringBuilder tmp = new StringBuilder();
            for (int l = 0; l < 100; ++l) {
                tmp.append((char)((Entity)this).rand.nextInt(1000));
            }
            this.randomNameTag = tmp.toString();
        }
        return this.randomNameTag;
    }
    
    public boolean hasCustomNameTag() {
        return true;
    }
    
    public boolean getAlwaysRenderNameTag() {
        return true;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.URUK_HAI;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            if (((Entity)this).rand.nextInt(10) == 0) {
                this.playSound(this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch());
            }
            final List allMobsExcludingRabbits = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox.expand(24.0, 24.0, 24.0));
            for (int i = 0; i < allMobsExcludingRabbits.size(); ++i) {
                final Entity entity = allMobsExcludingRabbits.get(i);
                if (!(entity instanceof LOTREntityRabbit)) {
                    if (!(entity instanceof LOTREntityGandalf)) {
                        double dSq = this.getDistanceSqToEntity(entity);
                        if (dSq <= 0.0) {
                            dSq = 1.0E-5;
                        }
                        float strength = 1.0f;
                        if (entity instanceof EntityPlayer) {
                            strength /= 3.0f;
                        }
                        final double force = strength / dSq;
                        double x = entity.posX - ((Entity)this).posX;
                        double y = entity.posY - ((Entity)this).posY;
                        double z = entity.posZ - ((Entity)this).posZ;
                        x *= force;
                        y *= force;
                        z *= force;
                        if (entity instanceof EntityPlayerMP) {
                            ((EntityPlayerMP)entity).playerNetServerHandler.sendPacket((Packet)new S27PacketExplosion(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, 0.0f, (List)new ArrayList(), Vec3.createVectorHelper(x, y, z)));
                        }
                        else {
                            final Entity entity2 = entity;
                            entity2.motionX += x;
                            final Entity entity3 = entity;
                            entity3.motionY += y;
                            final Entity entity4 = entity;
                            entity4.motionZ += z;
                        }
                    }
                }
            }
            if (((Entity)this).rand.nextInt(40) == 0) {
                final LOTREntityRabbit rabbit = new LOTREntityRabbit(((Entity)this).worldObj);
                final int j = MathHelper.floor_double(((Entity)this).posX) - ((Entity)this).rand.nextInt(16) + ((Entity)this).rand.nextInt(16);
                final int k = MathHelper.floor_double(((Entity)this).boundingBox.minY) - ((Entity)this).rand.nextInt(8) + ((Entity)this).rand.nextInt(8);
                final int l = MathHelper.floor_double(((Entity)this).posZ) - ((Entity)this).rand.nextInt(16) + ((Entity)this).rand.nextInt(16);
                rabbit.setLocationAndAngles((double)j, (double)k, (double)l, 0.0f, 0.0f);
                final AxisAlignedBB aabb = ((Entity)rabbit).boundingBox;
                if (((Entity)this).worldObj.checkNoEntityCollision(aabb) && ((Entity)this).worldObj.getCollidingBoundingBoxes((Entity)rabbit, aabb).isEmpty() && !((Entity)this).worldObj.isAnyLiquid(aabb)) {
                    ((Entity)this).worldObj.spawnEntityInWorld((Entity)rabbit);
                }
            }
            if (this.targetingRabbit == null && ((Entity)this).rand.nextInt(20) == 0) {
                final List rabbits = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntityRabbit.class, ((Entity)this).boundingBox.expand(24.0, 24.0, 24.0));
                if (!rabbits.isEmpty()) {
                    final LOTREntityRabbit rabbit2 = rabbits.get(((Entity)this).rand.nextInt(rabbits.size()));
                    if (((Entity)rabbit2).ridingEntity == null) {
                        this.targetingRabbit = rabbit2;
                    }
                }
            }
            if (this.targetingRabbit != null) {
                if (!this.targetingRabbit.isEntityAlive()) {
                    this.targetingRabbit = null;
                }
                else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)this.targetingRabbit, 1.0);
                    if (this.getDistanceToEntity((Entity)this.targetingRabbit) < 1.0) {
                        Entity entityToMount;
                        for (entityToMount = (Entity)this; entityToMount.riddenByEntity != null; entityToMount = entityToMount.riddenByEntity) {}
                        this.targetingRabbit.mountEntity(entityToMount);
                        this.targetingRabbit = null;
                    }
                }
            }
        }
    }
    
    protected String getLivingSound() {
        return "lotr:orc.say";
    }
    
    @Override
    public int getTalkInterval() {
        return 10;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int j = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), k = 0; k < j; ++k) {
            this.func_145779_a(Items.bone, 1);
        }
    }
}
