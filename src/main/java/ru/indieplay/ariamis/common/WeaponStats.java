package ru.indieplay.ariamis.common;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.common.ISpecialArmor;

import java.util.HashMap;
import java.util.Map;

public class WeaponStats {
    public static float MAX_MODIFIABLE_REACH;
    public static float MAX_MODIFIABLE_SPEED;
    public static int MAX_MODIFIABLE_KNOCKBACK;
    public static int basePlayerMeleeTime;
    private static int baseMobMeleeTime;
    private static Map meleeSpeed;
    private static Map meleeReach;
    private static Map meleeExtraKnockback;
    static {

        WeaponStats.baseMobMeleeTime = 20;
        WeaponStats.basePlayerMeleeTime = 20;
        WeaponStats.meleeSpeed = new HashMap();
        WeaponStats.meleeReach = new HashMap();
        WeaponStats.meleeExtraKnockback = new HashMap();

//        registerMeleeSpeed(LOTRItemSpear.class, 0.833f);
//        registerMeleeSpeed(LOTRItemPolearm.class, 0.667f);
//        registerMeleeSpeed(LOTRItemPolearmLong.class, 0.5f);
//        registerMeleeSpeed(LOTRItemLance.class, 0.5f);
//        registerMeleeSpeed(LOTRItemBattleaxe.class, 0.75f);
//        registerMeleeSpeed(LOTRItemHammer.class, 0.667f);
//        registerMeleeReach(LOTRItemDagger.class, 0.75f);
//        registerMeleeReach(LOTRItemSpear.class, 1.5f);
//        registerMeleeReach(LOTRItemPolearm.class, 1.5f);
//        registerMeleeReach(LOTRItemPolearmLong.class, 2.0f);
//        registerMeleeReach(LOTRItemLance.class, 2.0f);
//        registerMeleeReach(LOTRItemBalrogWhip.class, 1.5f);
//        registerMeleeExtraKnockback(LOTRItemHammer.class, 1);
//        registerMeleeExtraKnockback(LOTRItemLance.class, 1);
        WeaponStats.MAX_MODIFIABLE_REACH = 20.0f;
        WeaponStats.MAX_MODIFIABLE_SPEED = 1.6f;
        WeaponStats.MAX_MODIFIABLE_KNOCKBACK = 2;
    }

    public static boolean isMeleeWeapon(final ItemStack itemstack) {
        if (itemstack != null) {
            final Multimap weaponAttributes = itemstack.getAttributeModifiers();
            if (weaponAttributes != null) {
                for (final Object obj : weaponAttributes.entries()) {
                    final Map.Entry e = (Map.Entry) obj;

                }
            }
        }
        return false;
    }

    public static float getMeleeDamageBonus(final ItemStack itemstack) {
        float damage = 0.0f;
        if (itemstack != null) {
            final Multimap weaponAttributes = itemstack.getAttributeModifiers();
        }
        return damage;
    }
    public static void registerMeleeSpeed(final Object obj, final double f) {
        registerMeleeSpeed(obj,new Double(f).floatValue());
    }
    public static void registerMeleeReach(final Object obj, final double f) {
        registerMeleeReach(obj,new Double(f).floatValue());
    }
    public static void registerMeleeSpeed(final Object obj, final float f) {
        System.out.printf("Registered melee speed for %s with %f\n",obj.toString(),f);
        WeaponStats.meleeSpeed.put(obj, f);
    }

    public static void registerMeleeReach(final Object obj, final float f){
        System.out.printf("Registered melee reach for %s with %f\n",obj.toString(),f);
        WeaponStats.meleeReach.put(obj, f);
    }

    public static void registerMeleeExtraKnockback(final Object obj, final int i) {
        WeaponStats.meleeExtraKnockback.put(obj, i);
    }

    private static Object getClassOrItemProperty(final ItemStack itemstack, final Map propertyMap) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (propertyMap.containsKey(item)) {
                return propertyMap.get(item);
            }
            Class<? extends Item> itemClass;
            for (itemClass = item.getClass(); !propertyMap.containsKey(itemClass); itemClass = (Class<? extends Item>) itemClass.getSuperclass()) {
                if (itemClass == Item.class) {
                    return null;
                }
            }
            return propertyMap.get(itemClass);
        }
        return null;
    }

    public static int getAttackTimePlayer(final ItemStack itemstack) {
        return getAttackTimeWithBase(itemstack, WeaponStats.basePlayerMeleeTime);
    }

    public static int getAttackTimeMob(final ItemStack itemstack) {
        return getAttackTimeWithBase(itemstack, WeaponStats.baseMobMeleeTime);
    }

    public static int getAttackTimeWithBase(final ItemStack itemstack, final int baseTime) {
        float time = (float) baseTime;
        final Float factor = (Float) getClassOrItemProperty(itemstack, WeaponStats.meleeSpeed);
        if (factor != null) {
            time /= factor;
        }
        time = Math.max(time, 1.0f);
        return Math.round(time);
    }

    public static float getMeleeSpeed(final ItemStack itemstack) {
        final int base = WeaponStats.basePlayerMeleeTime;
        return 1.0f / (getAttackTimeWithBase(itemstack, base) / (float) base);
    }

    public static float getMeleeReachFactor(final ItemStack itemstack) {
        float reach = 1.0f;
        final Float factor = (Float) getClassOrItemProperty(itemstack, WeaponStats.meleeReach);
        if (factor != null) {
            reach *= factor;
        }
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

    public static float getMeleeExtraLookWidth(ItemStack is) {
        return 1.0f + getMeleeReachFactor(is);
    }

    public static int getBaseExtraKnockback(final ItemStack itemstack) {
        int kb = 0;
        final Integer extra = (Integer) getClassOrItemProperty(itemstack, WeaponStats.meleeExtraKnockback);
        if (extra != null) {
            kb = extra;
        }
        return kb;
    }

    public static int getTotalKnockback(final ItemStack itemstack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack) + getBaseExtraKnockback(itemstack) ;
    }

    public static boolean isPoisoned(final ItemStack itemstack) {
        return false;
    }

    public static boolean isRangedWeapon(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            return item instanceof ItemBow;
        }
        return false;
    }

    public static float getRangedSpeed(final ItemStack itemstack) {
        final int base = 20;
        int time = 0;
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item == Items.bow) {
                time = 20;
            }
        }
        if (time > 0) {
            return 1.0f / (time / (float) base);
        }
        return 0.0f;
    }

    public static float getRangedDamageFactor(final ItemStack itemstack, final boolean launchSpeedOnly) {
        final float baseArrowFactor = 2.0f;
        float weaponFactor = 0.0f;
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof ItemBow) {
                weaponFactor = baseArrowFactor;
                if (!launchSpeedOnly) {
                    final int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
                    if (power > 0) {
                        weaponFactor += (float) (power * 0.5 + 0.5);
                    }
                }
            }
        }
        if (weaponFactor > 0.0f) {
            return weaponFactor / baseArrowFactor;
        }
        return 0.0f;
    }

    public static int getRangedKnockback(final ItemStack itemstack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
    }

    public static int getArmorProtection(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor) item;
                int i = armor.damageReduceAmount;
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
                protection += ((ISpecialArmor) stack.getItem()).getArmorDisplay(entityplayer, stack, i);
            } else if (stack != null && stack.getItem() instanceof ItemArmor) {
                protection += getArmorProtection(stack);
            }
        }
        return protection;
    }

}
