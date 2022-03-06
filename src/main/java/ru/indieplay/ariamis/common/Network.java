package ru.indieplay.ariamis.common;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Network extends NetHandlerPlayServer {

    private MinecraftServer theServer;
    private double defaultReach;
    private int lastAttackTime;
    private double lastX;
    private double lastY;
    private double lastZ;
    private int floatingMountTick;

    public Network(final MinecraftServer server, final NetworkManager nm, final EntityPlayerMP entityplayer) {
        super(server, nm, entityplayer);
        this.defaultReach = -1.0;
        this.lastAttackTime = 0;
        this.theServer = server;
    }

    public void update() {
        this.updateAttackTime();
    }

    public void processInput(final C0CPacketInput packet) {
        super.processInput(packet);
        final float forward = packet.func_149616_d();
        final float strafing = packet.func_149620_c();
        final boolean jump = packet.func_149618_e();
    }

    public void processPlayer(final C03PacketPlayer packet) {
        super.processPlayer(packet);
        if (!super.playerEntity.isRiding() && packet.func_149466_j()) {
            final double newX = packet.func_149464_c();
            final double newY = packet.func_149467_d();
            final double newZ = packet.func_149472_e();
        }
        this.lastX = ((Entity) super.playerEntity).posX;
        this.lastY = ((Entity) super.playerEntity).posY;
        this.lastZ = ((Entity) super.playerEntity).posZ;
    }


    public void processUseEntity(final C02PacketUseEntity packet) {
        final WorldServer world = this.theServer.worldServerForDimension(((Entity) super.playerEntity).dimension);
        final Entity target = packet.func_149564_a((World) world);
        super.playerEntity.func_143004_u();
        if (target != null) {
            final ItemStack itemstack = super.playerEntity.getHeldItem();
            double reach = WeaponStats.getMeleeReachDistance((EntityPlayer) super.playerEntity);
            reach += WeaponStats.getMeleeExtraLookWidth(itemstack);
            reach += target.getCollisionBorderSize();
            final int attackTime = WeaponStats.getAttackTimePlayer(itemstack);
            if (super.playerEntity.getDistanceSqToEntity(target) < reach * reach) {
                if (packet.func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
                    super.playerEntity.interactWith(target);
                } else if (packet.func_149565_c() == C02PacketUseEntity.Action.ATTACK && (this.lastAttackTime <= 0 || !(target instanceof EntityLivingBase))) {
                    if (target instanceof EntityItem || target instanceof EntityXPOrb || target instanceof EntityArrow || target == super.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.theServer.logWarning("Player " + super.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
                        return;
                    }
                    attackTargetEntityWithCurrentItem(target,playerEntity);
                    this.lastAttackTime = attackTime;
                }
            }
        }
    }

    static public void attackTargetEntityWithCurrentItem(Entity attackedEntity, EntityPlayer ep) {
        ItemStack stack = ep.getCurrentEquippedItem();
        if (!(stack != null && stack.getItem().onLeftClickEntity(stack, ep, attackedEntity) ) && attackedEntity.canAttackWithItem()) {

            if (!attackedEntity.hitByEntity(ep)) {
                float damage = (float) ep.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();

                float enchant_modyfy  = attackedEntity instanceof EntityLivingBase ? EnchantmentHelper.getEnchantmentModifierLiving(ep, (EntityLivingBase) attackedEntity):0F;
                int i = attackedEntity instanceof EntityLivingBase ? EnchantmentHelper.getKnockbackModifier(ep, (EntityLivingBase) attackedEntity):0;
                i+=ep.isSprinting()?1:0;
                if (damage > 0.0F || enchant_modyfy > 0.0F) {
                    boolean isCrit = ep.fallDistance > 0.0F && !ep.onGround && !ep.isOnLadder() && !ep.isInWater() && !ep.isPotionActive(Potion.blindness) && ep.ridingEntity == null && attackedEntity instanceof EntityLivingBase;

                    if (isCrit && damage > 0.0F) damage *= 1.5F;
                    
                    damage += enchant_modyfy;
                    boolean isEntityBurning = false;
                    int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(ep);

                    if (attackedEntity instanceof EntityLivingBase && fireAspectModifier > 0 && !attackedEntity.isBurning()) {
                        isEntityBurning = true;
                        attackedEntity.setFire(1);
                    }

                    boolean isDamageDealed=damage>0;
                    if (isDamageDealed) {
                        if (i > 0) {
                            attackedEntity.addVelocity((double) (-MathHelper.sin(ep.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(ep.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
                            ep.motionX *= 0.6D;
                            ep.motionZ *= 0.6D;
                            ep.setSprinting(false);
                        }

                        if (isCrit) ep.onCriticalHit(attackedEntity);

                        if (enchant_modyfy > 0.0F) ep.onEnchantmentCritical(attackedEntity);


                        if (damage >= 18.0F) ep.triggerAchievement(AchievementList.overkill);

                        ep.setLastAttacker(attackedEntity);

                        if (attackedEntity instanceof EntityLivingBase) EnchantmentHelper.func_151384_a((EntityLivingBase) attackedEntity, ep);


                        EnchantmentHelper.func_151385_b(ep, attackedEntity);
                        ItemStack itemstack = ep.getCurrentEquippedItem();
                        Object object = attackedEntity;

                        if (attackedEntity instanceof EntityDragonPart) {
                            IEntityMultiPart ientitymultipart = ((EntityDragonPart) attackedEntity).entityDragonObj;
                            if ( ientitymultipart instanceof EntityLivingBase) object = ientitymultipart;
                        }
                        if (itemstack != null && object instanceof EntityLivingBase) {
                            if( itemstack.getItem().hitEntity(itemstack, (EntityLivingBase) object, ep))
                                ep.addStat(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())], 1);
                            if (itemstack.stackSize <= 0) ep.destroyCurrentEquippedItem();
                        }
                        if (attackedEntity instanceof EntityLivingBase) {
                            ep.addStat(StatList.damageDealtStat, Math.round(damage * 10.0F));
                            if (fireAspectModifier > 0) attackedEntity.setFire(fireAspectModifier * 4);
                        }
                        ep.addExhaustion(0.3F);
                    } else if (isEntityBurning) {
                        attackedEntity.extinguish();
                    }
                }
            }
        }
    }


    public void updateAttackTime() {
        if (this.lastAttackTime > 0) {
            --this.lastAttackTime;
        }
    }

    public void processPlayerDigging(final C07PacketPlayerDigging packet) {
        this.setBlockReach();
        super.processPlayerDigging(packet);
    }

    public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement packet) {
        this.setBlockReach();
        super.processPlayerBlockPlacement(packet);
    }

    private void setBlockReach() {
        if (this.defaultReach == -1.0) {
            this.defaultReach = super.playerEntity.theItemInWorldManager.getBlockReachDistance();
        }
        double reach = this.defaultReach;
        reach *= WeaponStats.getMeleeReachFactor(super.playerEntity.getHeldItem());
        super.playerEntity.theItemInWorldManager.setBlockReachDistance(reach);
    }


}
