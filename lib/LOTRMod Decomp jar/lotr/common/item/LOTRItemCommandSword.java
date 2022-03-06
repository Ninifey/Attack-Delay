// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRMod;
import net.minecraft.command.IEntitySelector;
import java.util.ArrayList;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import lotr.common.LOTRSquadrons;

public class LOTRItemCommandSword extends LOTRItemSword implements LOTRSquadrons.SquadronItem
{
    private static final float COMMAND_RANGE = 12.0f;
    private static final float TARGET_RANGE = 64.0f;
    private static final float TARGET_SPREAD = 6.0f;
    
    public LOTRItemCommandSword() {
        super(Item.ToolMaterial.IRON);
        this.setMaxDamage(0);
        super.lotrWeaponDamage = 1.0f;
    }
    
    public boolean isDamageable() {
        return false;
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.swingItem();
        if (!world.isClient) {
            final Entity entity = this.getEntityTarget(entityplayer);
            if (entity != null) {
                final MovingObjectPosition entityHit = new MovingObjectPosition(entity, Vec3.createVectorHelper(entity.posX, entity.boundingBox.minY + entity.height / 2.0f, entity.posZ));
                this.command(entityplayer, world, itemstack, entityHit);
            }
            else {
                final double range = 64.0;
                final Vec3 eyePos = Vec3.createVectorHelper(((Entity)entityplayer).posX, ((Entity)entityplayer).posY + entityplayer.getEyeHeight(), ((Entity)entityplayer).posZ);
                final Vec3 look = entityplayer.getLookVec();
                final Vec3 sight = eyePos.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
                final MovingObjectPosition rayTrace = world.func_147447_a(eyePos, sight, false, false, true);
                if (rayTrace != null && rayTrace.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    this.command(entityplayer, world, itemstack, rayTrace);
                }
                else {
                    this.command(entityplayer, world, itemstack, null);
                }
            }
        }
        return itemstack;
    }
    
    private Entity getEntityTarget(final EntityPlayer entityplayer) {
        final double range = 64.0;
        final Vec3 eyePos = Vec3.createVectorHelper(((Entity)entityplayer).posX, ((Entity)entityplayer).posY + entityplayer.getEyeHeight(), ((Entity)entityplayer).posZ);
        final Vec3 look = entityplayer.getLookVec();
        final Vec3 sight = eyePos.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        final float sightWidth = 1.0f;
        final List list = ((Entity)entityplayer).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)entityplayer, ((Entity)entityplayer).boundingBox.addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand((double)sightWidth, (double)sightWidth, (double)sightWidth));
        Entity pointedEntity = null;
        double entityDist = range;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof EntityLivingBase) {
                if (entity.canBeCollidedWith()) {
                    final float width = 1.0f;
                    final AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)width, (double)width, (double)width);
                    final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyePos, sight);
                    if (axisalignedbb.isVecInside(eyePos)) {
                        if (entityDist >= 0.0) {
                            pointedEntity = entity;
                            entityDist = 0.0;
                        }
                    }
                    else if (movingobjectposition != null) {
                        final double d = eyePos.distanceTo(movingobjectposition.hitVec);
                        if (d < entityDist || entityDist == 0.0) {
                            if (entity == ((Entity)entityplayer).ridingEntity && !entity.canRiderInteract()) {
                                if (entityDist == 0.0) {
                                    pointedEntity = entity;
                                }
                            }
                            else {
                                pointedEntity = entity;
                                entityDist = d;
                            }
                        }
                    }
                }
            }
        }
        if (pointedEntity != null) {
            return pointedEntity;
        }
        return null;
    }
    
    private void command(final EntityPlayer entityplayer, final World world, final ItemStack itemstack, final MovingObjectPosition hitTarget) {
        entityplayer.setRevengeTarget((EntityLivingBase)null);
        List spreadTargets = new ArrayList();
        if (hitTarget != null) {
            final Vec3 vec = hitTarget.hitVec;
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(vec.xCoord, vec.yCoord, vec.zCoord, vec.xCoord, vec.yCoord, vec.zCoord);
            aabb = aabb.expand(6.0, 6.0, 6.0);
            spreadTargets = world.selectEntitiesWithinAABB((Class)EntityLivingBase.class, aabb, (IEntitySelector)new IEntitySelector() {
                public boolean isEntityApplicable(final Entity entity) {
                    return entity.isEntityAlive() && LOTRMod.canPlayerAttackEntity(entityplayer, (EntityLivingBase)entity, false);
                }
            });
        }
        final List nearbyHiredUnits = world.getEntitiesWithinAABB((Class)LOTREntityNPC.class, ((Entity)entityplayer).boundingBox.expand(12.0, 12.0, 12.0));
        for (int i = 0; i < nearbyHiredUnits.size(); ++i) {
            final LOTREntityNPC npc = nearbyHiredUnits.get(i);
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getObeyCommandSword() && LOTRSquadrons.areSquadronsCompatible(npc, itemstack)) {
                final List validTargets = new ArrayList();
                if (!spreadTargets.isEmpty()) {
                    for (final Object obj : spreadTargets) {
                        final EntityLivingBase entity = (EntityLivingBase)obj;
                        if (LOTRMod.canNPCAttackEntity(npc, entity, true)) {
                            validTargets.add(entity);
                        }
                    }
                }
                if (!validTargets.isEmpty()) {
                    final Comparator sorter = new LOTREntityAINearestAttackableTargetBasic.TargetSorter((EntityLivingBase)npc);
                    Collections.sort((List<Object>)validTargets, sorter);
                    final EntityLivingBase target = validTargets.get(0);
                    npc.hiredNPCInfo.commandSwordAttack(target);
                    npc.hiredNPCInfo.wasAttackCommanded = true;
                }
                else {
                    npc.hiredNPCInfo.commandSwordCancel();
                }
            }
        }
    }
}
