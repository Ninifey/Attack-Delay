// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import java.util.Iterator;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemBlowgun extends Item
{
    public LOTRItemBlowgun(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemBlowgun(final Item.ToolMaterial material) {
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.setFull3D();
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final int i) {
        ItemStack dartItem = null;
        int dartSlot = -1;
        for (int l = 0; l < entityplayer.inventory.mainInventory.length; ++l) {
            final ItemStack invItem = entityplayer.inventory.mainInventory[l];
            if (invItem != null && invItem.getItem() instanceof LOTRItemDart) {
                dartItem = invItem;
                dartSlot = l;
                break;
            }
        }
        if (dartItem == null && entityplayer.capabilities.isCreativeMode) {
            dartItem = new ItemStack(LOTRMod.tauredainDart);
        }
        if (dartItem != null) {
            final int useTick = this.getMaxItemUseDuration(itemstack) - i;
            float charge = useTick / (float)this.getMaxDrawTime();
            if (charge < 0.65f) {
                return;
            }
            charge = (charge * charge + charge * 2.0f) / 3.0f;
            charge = Math.min(charge, 1.0f);
            itemstack.damageItem(1, (EntityLivingBase)entityplayer);
            if (!entityplayer.capabilities.isCreativeMode && dartSlot >= 0) {
                final ItemStack itemStack = dartItem;
                --itemStack.stackSize;
                if (dartItem.stackSize <= 0) {
                    entityplayer.inventory.mainInventory[dartSlot] = null;
                }
            }
            world.playSoundAtEntity((Entity)entityplayer, "lotr:item.dart", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
            if (!world.isClient) {
                final ItemStack shotDart = dartItem.copy();
                shotDart.stackSize = 1;
                final LOTREntityDart dart = ((LOTRItemDart)shotDart.getItem()).createDart(world, (EntityLivingBase)entityplayer, shotDart, charge * 2.0f * getBlowgunLaunchSpeedFactor(itemstack));
                if (dart.dartDamageFactor < 1.0f) {
                    dart.dartDamageFactor = 1.0f;
                }
                if (charge >= 1.0f) {
                    dart.setIsCritical(true);
                }
                applyBlowgunModifiers(dart, itemstack);
                if (entityplayer.capabilities.isCreativeMode) {
                    dart.canBePickedUp = 2;
                }
                world.spawnEntityInWorld((Entity)dart);
            }
        }
    }
    
    public static float getBlowgunLaunchSpeedFactor(final ItemStack itemstack) {
        float f = 1.0f;
        if (itemstack != null) {
            f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
        }
        return f;
    }
    
    public static void applyBlowgunModifiers(final LOTREntityDart dart, final ItemStack itemstack) {
        final int punch = LOTREnchantmentHelper.calcRangedKnockback(itemstack);
        if (punch > 0) {
            dart.knockbackStrength = punch;
        }
        final int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
        if (fireAspect > 0) {
            dart.setFire(100);
        }
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                LOTREnchantmentHelper.setProjectileEnchantment(dart, ench);
            }
        }
    }
    
    public int getMaxDrawTime() {
        return 5;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        boolean anyDart = false;
        for (final ItemStack invItem : entityplayer.inventory.mainInventory) {
            if (invItem != null && invItem.getItem() instanceof LOTRItemDart) {
                anyDart = true;
                break;
            }
        }
        if (anyDart || entityplayer.capabilities.isCreativeMode) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return repairItem.getItem() == Item.getItemFromBlock(LOTRMod.reeds);
    }
}
