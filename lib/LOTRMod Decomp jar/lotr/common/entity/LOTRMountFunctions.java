// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountControl;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.entity.passive.EntityHorse;
import lotr.common.network.LOTRPacketMountControlServerEnforce;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;

public class LOTRMountFunctions
{
    public static void setNavigatorRangeFromNPC(final LOTRNPCMount mount, final LOTREntityNPC npc) {
        final EntityLiving entity = (EntityLiving)mount;
        final double d = npc.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
        entity.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(d);
    }
    
    public static void update(final LOTRNPCMount mount) {
        final EntityLiving entity = (EntityLiving)mount;
        final World world = ((Entity)entity).worldObj;
        final Random rand = entity.getRNG();
        if (!world.isClient) {
            if (rand.nextInt(900) == 0 && entity.isEntityAlive()) {
                entity.heal(1.0f);
            }
            if (!(entity instanceof LOTREntityNPC)) {
                if (entity.getAttackTarget() != null) {
                    final EntityLivingBase target = entity.getAttackTarget();
                    if (!target.isEntityAlive() || (target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode)) {
                        entity.setAttackTarget((EntityLivingBase)null);
                    }
                }
                if (((Entity)entity).riddenByEntity instanceof EntityLiving) {
                    final EntityLivingBase target = ((EntityLiving)((Entity)entity).riddenByEntity).getAttackTarget();
                    entity.setAttackTarget(target);
                }
                else if (((Entity)entity).riddenByEntity instanceof EntityPlayer) {
                    entity.setAttackTarget((EntityLivingBase)null);
                }
            }
        }
    }
    
    public static boolean interact(final LOTRNPCMount mount, final EntityPlayer entityplayer) {
        final EntityLiving entity = (EntityLiving)mount;
        if (mount.getBelongsToNPC() && ((Entity)entity).riddenByEntity == null) {
            if (!((Entity)entity).worldObj.isClient) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.mountOwnedByNPC", new Object[0]));
            }
            return true;
        }
        return false;
    }
    
    public static void move(final LOTRNPCMount mount, float strafe, float forward) {
        final EntityLiving entity = (EntityLiving)mount;
        final Entity rider = ((Entity)entity).riddenByEntity;
        if (rider != null && rider instanceof EntityPlayer && mount.isMountSaddled()) {
            final EntityLiving entityLiving = entity;
            final EntityLiving entityLiving2 = entity;
            final float rotationYaw = rider.rotationYaw;
            ((Entity)entityLiving2).rotationYaw = rotationYaw;
            ((Entity)entityLiving).prevRotationYaw = rotationYaw;
            ((Entity)entity).rotationPitch = rider.rotationPitch * 0.5f;
            ((Entity)entity).rotationYaw %= 360.0f;
            ((Entity)entity).rotationPitch %= 360.0f;
            final EntityLiving entityLiving3 = entity;
            final EntityLiving entityLiving4 = entity;
            final float rotationYaw2 = ((Entity)entity).rotationYaw;
            ((EntityLivingBase)entityLiving4).renderYawOffset = rotationYaw2;
            ((EntityLivingBase)entityLiving3).rotationYawHead = rotationYaw2;
            strafe = ((EntityLivingBase)rider).moveStrafing * 0.5f;
            forward = ((EntityLivingBase)rider).moveForward;
            if (forward <= 0.0f) {
                forward *= 0.25f;
            }
            ((Entity)entity).stepHeight = 1.0f;
            ((EntityLivingBase)entity).jumpMovementFactor = entity.getAIMoveSpeed() * 0.1f;
            if (canRiderControl_elseNoMotion(entity)) {
                entity.setAIMoveSpeed((float)entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                mount.super_moveEntityWithHeading(strafe, forward);
            }
            ((EntityLivingBase)entity).prevLimbSwingAmount = ((EntityLivingBase)entity).limbSwingAmount;
            final double d0 = ((Entity)entity).posX - ((Entity)entity).prevPosX;
            final double d2 = ((Entity)entity).posZ - ((Entity)entity).prevPosZ;
            float f4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 4.0f;
            if (f4 > 1.0f) {
                f4 = 1.0f;
            }
            final EntityLiving entityLiving5 = entity;
            ((EntityLivingBase)entityLiving5).limbSwingAmount += (f4 - ((EntityLivingBase)entity).limbSwingAmount) * 0.4f;
            final EntityLiving entityLiving6 = entity;
            ((EntityLivingBase)entityLiving6).limbSwing += ((EntityLivingBase)entity).limbSwingAmount;
        }
        else {
            ((Entity)entity).stepHeight = 0.5f;
            ((EntityLivingBase)entity).jumpMovementFactor = 0.02f;
            mount.super_moveEntityWithHeading(strafe, forward);
        }
    }
    
    public static boolean sendControlToServer(final EntityPlayer clientPlayer) {
        return sendControlToServer(clientPlayer, null);
    }
    
    public static boolean isPlayerControlledMount(final Entity mount) {
        return mount != null && mount.riddenByEntity instanceof EntityPlayer && isMountControllable(mount) && canRiderControl(mount);
    }
    
    public static boolean isMountControllable(final Entity mount) {
        return (mount instanceof EntityHorse && ((EntityHorse)mount).isTame()) || (mount instanceof LOTREntityNPCRideable && ((LOTREntityNPCRideable)mount).isNPCTamed());
    }
    
    public static boolean sendControlToServer(final EntityPlayer clientPlayer, final LOTRPacketMountControlServerEnforce pktSet) {
        final Entity mount = ((Entity)clientPlayer).ridingEntity;
        if (isPlayerControlledMount(mount)) {
            if (pktSet != null) {
                mount.setPositionAndRotation(pktSet.posX, pktSet.posY, pktSet.posZ, pktSet.rotationYaw, pktSet.rotationPitch);
                mount.updateRiderPosition();
            }
            final LOTRPacketMountControl pkt = new LOTRPacketMountControl(mount);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)pkt);
            return true;
        }
        return false;
    }
    
    public static boolean canRiderControl(final Entity entity) {
        final Entity rider = entity.riddenByEntity;
        if (rider instanceof EntityPlayer) {
            return ((EntityPlayer)rider).isClientWorld();
        }
        return !entity.worldObj.isClient;
    }
    
    public static boolean canRiderControl_elseNoMotion(final EntityLiving entity) {
        final boolean flag = canRiderControl((Entity)entity);
        if (!flag && ((Entity)entity).riddenByEntity instanceof EntityPlayer && isMountControllable((Entity)entity)) {
            ((Entity)entity).motionX = 0.0;
            ((Entity)entity).motionY = 0.0;
            ((Entity)entity).motionZ = 0.0;
        }
        return flag;
    }
}
