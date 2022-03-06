// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.LOTRMod;
import java.util.Iterator;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRBannerProtection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import java.util.ArrayList;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class LOTRItemBalrogWhip extends LOTRItemSword
{
    public LOTRItemBalrogWhip() {
        super(LOTRMaterial.UTUMNO);
        super.lotrWeaponDamage = 7.0f;
        this.setMaxDamage(1000);
    }
    
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase hitEntity, final EntityLivingBase user) {
        if (super.hitEntity(itemstack, hitEntity, user)) {
            this.checkIncompatibleModifiers(itemstack);
            if (!((Entity)user).worldObj.isClient && hitEntity.hurtTime == hitEntity.maxHurtTime) {
                this.launchWhip(user, hitEntity);
            }
            return true;
        }
        return false;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 20;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        this.checkIncompatibleModifiers(itemstack);
        entityplayer.swingItem();
        if (!world.isClient) {
            this.launchWhip((EntityLivingBase)entityplayer, null);
        }
        itemstack.damageItem(1, (EntityLivingBase)entityplayer);
        return itemstack;
    }
    
    private void launchWhip(final EntityLivingBase user, final EntityLivingBase hitEntity) {
        ((Entity)user).worldObj.playSoundAtEntity((Entity)user, "lotr:item.balrogWhip", 2.0f, 0.7f + Item.itemRand.nextFloat() * 0.6f);
        final double range = 16.0;
        final Vec3 position = Vec3.createVectorHelper(((Entity)user).posX, ((Entity)user).posY, ((Entity)user).posZ);
        final Vec3 look = user.getLookVec();
        final Vec3 sight = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        final float sightWidth = 1.0f;
        final List list = ((Entity)user).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)user, ((Entity)user).boundingBox.addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand((double)sightWidth, (double)sightWidth, (double)sightWidth));
        final List<EntityLivingBase> whipTargets = new ArrayList<EntityLivingBase>();
        for (int i = 0; i < list.size(); ++i) {
            final Entity obj = list.get(i);
            if (obj instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)obj;
                if (entity != ((Entity)user).ridingEntity || entity.canRiderInteract()) {
                    if (entity.canBeCollidedWith()) {
                        final float width = 1.0f;
                        final AxisAlignedBB axisalignedbb = ((Entity)entity).boundingBox.expand((double)width, (double)width, (double)width);
                        final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(position, sight);
                        if (axisalignedbb.isVecInside(position)) {
                            whipTargets.add(entity);
                        }
                        else if (movingobjectposition != null) {
                            whipTargets.add(entity);
                        }
                    }
                }
            }
        }
        for (final EntityLivingBase entity2 : whipTargets) {
            if (entity2 == hitEntity || entity2.attackEntityFrom(DamageSource.causeMobDamage(user), 1.0f)) {
                entity2.setFire(5);
            }
        }
        final Vec3 eyeHeight = position.addVector(0.0, (double)user.getEyeHeight(), 0.0);
        for (int l = 4; l < (int)range; ++l) {
            final double d = l / range;
            final double dx = sight.xCoord - eyeHeight.xCoord;
            final double dy = sight.yCoord - eyeHeight.yCoord;
            final double dz = sight.zCoord - eyeHeight.zCoord;
            final double x = eyeHeight.xCoord + dx * d;
            final double y = eyeHeight.yCoord + dy * d;
            final double z = eyeHeight.zCoord + dz * d;
            final int j = MathHelper.floor_double(x);
            final int k = MathHelper.floor_double(y);
            final int m = MathHelper.floor_double(z);
            for (int j2 = k - 3; j2 <= k + 3; ++j2) {
                if (World.doesBlockHaveSolidTopSurface((IBlockAccess)((Entity)user).worldObj, j, j2 - 1, m) && ((Entity)user).worldObj.getBlock(j, j2, m).isReplaceable((IBlockAccess)((Entity)user).worldObj, j, j2, m)) {
                    boolean protection = false;
                    if (user instanceof EntityPlayer) {
                        protection = LOTRBannerProtection.isProtectedByBanner(((Entity)user).worldObj, j, j2, m, LOTRBannerProtection.forPlayer((EntityPlayer)user), false);
                    }
                    else if (user instanceof EntityLiving) {
                        protection = LOTRBannerProtection.isProtectedByBanner(((Entity)user).worldObj, j, j2, m, LOTRBannerProtection.forNPC((EntityLiving)user), false);
                    }
                    if (!protection) {
                        ((Entity)user).worldObj.setBlock(j, j2, m, (Block)Blocks.fire, 0, 3);
                        break;
                    }
                }
            }
        }
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return repairItem.getItem() == LOTRMod.balrogFire;
    }
    
    private void checkIncompatibleModifiers(final ItemStack itemstack) {
        for (final LOTREnchantment ench : new LOTREnchantment[] { LOTREnchantment.fire, LOTREnchantment.chill }) {
            if (LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                LOTREnchantmentHelper.removeEnchant(itemstack, ench);
            }
        }
    }
}
