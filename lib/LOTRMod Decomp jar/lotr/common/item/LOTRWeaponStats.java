// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.HashMap;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import java.util.Iterator;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import java.util.Map;

public class LOTRWeaponStats
{
    private static int basePlayerMeleeTime;
    private static int baseMobMeleeTime;
    private static Map meleeSpeed;
    private static Map meleeReach;
    private static Map meleeExtraKnockback;
    public static float MAX_MODIFIABLE_REACH;
    public static float MAX_MODIFIABLE_SPEED;
    public static int MAX_MODIFIABLE_KNOCKBACK;
    
    public static boolean isMeleeWeapon(final ItemStack itemstack) {
        if (itemstack != null) {
            final Multimap weaponAttributes = itemstack.getAttributeModifiers();
            if (weaponAttributes != null) {
                for (final Object obj : weaponAttributes.entries()) {
                    final Map.Entry e = (Map.Entry)obj;
                    final AttributeModifier mod = e.getValue();
                    if (mod.getID() == LOTRItemSword.accessWeaponDamageModifier()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static float getMeleeDamageBonus(final ItemStack itemstack) {
        float damage = 0.0f;
        if (itemstack != null) {
            final Multimap weaponAttributes = itemstack.getAttributeModifiers();
            if (weaponAttributes != null) {
                for (final Object obj : weaponAttributes.entries()) {
                    final Map.Entry e = (Map.Entry)obj;
                    final AttributeModifier mod = e.getValue();
                    if (mod.getID() == LOTRItemSword.accessWeaponDamageModifier()) {
                        damage += (float)mod.getAmount();
                        damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
                    }
                }
            }
        }
        return damage;
    }
    
    public static void registerMeleeSpeed(final Object obj, final float f) {
        LOTRWeaponStats.meleeSpeed.put(obj, f);
    }
    
    public static void registerMeleeReach(final Object obj, final float f) {
        LOTRWeaponStats.meleeReach.put(obj, f);
    }
    
    public static void registerMeleeExtraKnockback(final Object obj, final int i) {
        LOTRWeaponStats.meleeExtraKnockback.put(obj, i);
    }
    
    private static Object getClassOrItemProperty(final ItemStack itemstack, final Map propertyMap) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (propertyMap.containsKey(item)) {
                return propertyMap.get(item);
            }
            Class<? extends Item> itemClass;
            for (itemClass = item.getClass(); !propertyMap.containsKey(itemClass); itemClass = (Class<? extends Item>)itemClass.getSuperclass()) {
                if (itemClass == Item.class) {
                    return null;
                }
            }
            return propertyMap.get(itemClass);
        }
        return null;
    }
    
    public static int getAttackTimePlayer(final ItemStack itemstack) {
        return getAttackTimeWithBase(itemstack, LOTRWeaponStats.basePlayerMeleeTime);
    }
    
    public static int getAttackTimeMob(final ItemStack itemstack) {
        return getAttackTimeWithBase(itemstack, LOTRWeaponStats.baseMobMeleeTime);
    }
    
    public static int getAttackTimeWithBase(final ItemStack itemstack, final int baseTime) {
        float time = (float)baseTime;
        final Float factor = (Float)getClassOrItemProperty(itemstack, LOTRWeaponStats.meleeSpeed);
        if (factor != null) {
            time /= factor;
        }
        time /= LOTREnchantmentHelper.calcMeleeSpeedFactor(itemstack);
        time = Math.max(time, 1.0f);
        return Math.round(time);
    }
    
    public static float getMeleeSpeed(final ItemStack itemstack) {
        final int base = LOTRWeaponStats.basePlayerMeleeTime;
        return 1.0f / (getAttackTimeWithBase(itemstack, base) / (float)base);
    }
    
    public static float getMeleeReachFactor(final ItemStack itemstack) {
        float reach = 1.0f;
        final Float factor = (Float)getClassOrItemProperty(itemstack, LOTRWeaponStats.meleeReach);
        if (factor != null) {
            reach *= factor;
        }
        reach *= LOTREnchantmentHelper.calcMeleeReachFactor(itemstack);
        return reach;
    }
    
    public static float getMeleeReachDistance(final EntityPlayer entityplayer) {
        float reach = 3.0f;
        reach *= getMeleeReachFactor(entityplayer.getHeldItem());
        if (entityplayer.capabilities.isCreativeMode) {
            reach += 3.0;
        }
        return reach;
    }
    
    public static float getMeleeExtraLookWidth() {
        return 1.0f;
    }
    
    public static int getBaseExtraKnockback(final ItemStack itemstack) {
        int kb = 0;
        final Integer extra = (Integer)getClassOrItemProperty(itemstack, LOTRWeaponStats.meleeExtraKnockback);
        if (extra != null) {
            kb = extra;
        }
        return kb;
    }
    
    public static int getTotalKnockback(final ItemStack itemstack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack) + getBaseExtraKnockback(itemstack) + LOTREnchantmentHelper.calcExtraKnockback(itemstack);
    }
    
    public static boolean isPoisoned(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            return item instanceof LOTRItemDagger && ((LOTRItemDagger)item).getDaggerEffect() == LOTRItemDagger.DaggerEffect.POISON;
        }
        return false;
    }
    
    public static boolean isRangedWeapon(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            return item instanceof ItemBow || item instanceof LOTRItemSpear || item instanceof LOTRItemBlowgun || item instanceof LOTRItemThrowingAxe;
        }
        return false;
    }
    
    public static float getRangedSpeed(final ItemStack itemstack) {
        final int base = 20;
        int time = 0;
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemCrossbow) {
                time = ((LOTRItemCrossbow)item).getMaxDrawTime();
            }
            else if (item instanceof LOTRItemBow) {
                time = ((LOTRItemBow)item).getMaxDrawTime();
            }
            else if (item == Items.bow) {
                time = 20;
            }
            if (item instanceof LOTRItemSpear) {
                time = ((LOTRItemSpear)item).getMaxDrawTime();
            }
            if (item instanceof LOTRItemBlowgun) {
                time = ((LOTRItemBlowgun)item).getMaxDrawTime();
            }
        }
        if (time > 0) {
            return 1.0f / (time / (float)base);
        }
        return 0.0f;
    }
    
    public static float getRangedDamageFactor(final ItemStack itemstack, final boolean launchSpeedOnly) {
        final float baseArrowFactor = 2.0f;
        float weaponFactor = 0.0f;
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemCrossbow) {
                weaponFactor = baseArrowFactor * (float)((LOTRItemCrossbow)item).boltDamageFactor;
                weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
                if (!launchSpeedOnly) {
                    final int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
                    if (power > 0) {
                        weaponFactor += (float)(power * 0.5 + 0.5);
                    }
                    weaponFactor *= 2.0f;
                }
            }
            else if (item instanceof ItemBow) {
                weaponFactor = baseArrowFactor;
                if (item instanceof LOTRItemBow) {
                    weaponFactor *= (float)((LOTRItemBow)item).arrowDamageFactor;
                }
                weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
                if (!launchSpeedOnly) {
                    final int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
                    if (power > 0) {
                        weaponFactor += (float)(power * 0.5 + 0.5);
                    }
                }
            }
            else if (item instanceof LOTRItemBlowgun) {
                weaponFactor = baseArrowFactor;
                if (!launchSpeedOnly) {
                    weaponFactor *= 1.0f / baseArrowFactor;
                }
                weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
            }
            else if (item instanceof LOTRItemSpear) {
                weaponFactor = ((LOTRItemSpear)item).getRangedDamageMultiplier(itemstack, null, null);
            }
            else if (item instanceof LOTRItemThrowingAxe) {
                weaponFactor = ((LOTRItemThrowingAxe)item).getRangedDamageMultiplier(itemstack, null, null);
            }
        }
        if (weaponFactor > 0.0f) {
            return weaponFactor / baseArrowFactor;
        }
        return 0.0f;
    }
    
    public static int getRangedKnockback(final ItemStack itemstack) {
        if (isMeleeWeapon(itemstack) || (itemstack != null && itemstack.getItem() instanceof LOTRItemThrowingAxe)) {
            return getTotalKnockback(itemstack);
        }
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack) + LOTREnchantmentHelper.calcRangedKnockback(itemstack);
    }
    
    public static int getArmorProtection(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)item;
                int i = armor.damageReduceAmount;
                i += LOTREnchantmentHelper.calcCommonArmorProtection(itemstack);
                return i;
            }
        }
        return 0;
    }
    
    public static int getTotalArmorValue(final EntityPlayer entityplayer) {
        int protection = 0;
        for (int i = 0; i < entityplayer.inventory.armorInventory.length; ++i) {
            final ItemStack stack = entityplayer.inventory.armorInventory[i];
            if (stack != null && stack.getItem() instanceof ISpecialArmor) {
                protection += ((ISpecialArmor)stack.getItem()).getArmorDisplay(entityplayer, stack, i);
            }
            else if (stack != null && stack.getItem() instanceof ItemArmor) {
                protection += getArmorProtection(stack);
            }
        }
        return protection;
    }
    
    static {
        LOTRWeaponStats.basePlayerMeleeTime = 15;
        LOTRWeaponStats.baseMobMeleeTime = 20;
        LOTRWeaponStats.meleeSpeed = new HashMap();
        LOTRWeaponStats.meleeReach = new HashMap();
        LOTRWeaponStats.meleeExtraKnockback = new HashMap();
        registerMeleeSpeed(LOTRItemDagger.class, 1.5f);
        registerMeleeSpeed(LOTRItemSpear.class, 0.833f);
        registerMeleeSpeed(LOTRItemPolearm.class, 0.667f);
        registerMeleeSpeed(LOTRItemPolearmLong.class, 0.5f);
        registerMeleeSpeed(LOTRItemLance.class, 0.5f);
        registerMeleeSpeed(LOTRItemBattleaxe.class, 0.75f);
        registerMeleeSpeed(LOTRItemHammer.class, 0.667f);
        registerMeleeReach(LOTRItemDagger.class, 0.75f);
        registerMeleeReach(LOTRItemSpear.class, 1.5f);
        registerMeleeReach(LOTRItemPolearm.class, 1.5f);
        registerMeleeReach(LOTRItemPolearmLong.class, 2.0f);
        registerMeleeReach(LOTRItemLance.class, 2.0f);
        registerMeleeReach(LOTRItemBalrogWhip.class, 1.5f);
        registerMeleeExtraKnockback(LOTRItemHammer.class, 1);
        registerMeleeExtraKnockback(LOTRItemLance.class, 1);
        LOTRWeaponStats.MAX_MODIFIABLE_REACH = 2.0f;
        LOTRWeaponStats.MAX_MODIFIABLE_SPEED = 1.6f;
        LOTRWeaponStats.MAX_MODIFIABLE_KNOCKBACK = 2;
    }
}
