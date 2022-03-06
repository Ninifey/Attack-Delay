// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShears;
import lotr.common.item.LOTRItemCommandSword;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public enum LOTREnchantmentType
{
    BREAKABLE, 
    ARMOR, 
    ARMOR_FEET, 
    ARMOR_LEGS, 
    ARMOR_BODY, 
    ARMOR_HEAD, 
    MELEE, 
    TOOL, 
    SHEARS, 
    RANGED, 
    RANGED_LAUNCHER, 
    THROWING_AXE, 
    FISHING;
    
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        final Item item = itemstack.getItem();
        if (this == LOTREnchantmentType.BREAKABLE && item.isDamageable()) {
            return true;
        }
        if (item instanceof ItemArmor && ((ItemArmor)item).damageReduceAmount > 0) {
            if (this == LOTREnchantmentType.ARMOR) {
                return true;
            }
            final ItemArmor itemarmor = (ItemArmor)item;
            final int armorType = itemarmor.armorType;
            if (armorType == 0) {
                return this == LOTREnchantmentType.ARMOR_HEAD;
            }
            if (armorType == 1) {
                return this == LOTREnchantmentType.ARMOR_BODY;
            }
            if (armorType == 1) {
                return this == LOTREnchantmentType.ARMOR_BODY;
            }
            if (armorType == 2) {
                return this == LOTREnchantmentType.ARMOR_LEGS;
            }
            if (armorType == 3) {
                return this == LOTREnchantmentType.ARMOR_FEET;
            }
        }
        return (this == LOTREnchantmentType.MELEE && LOTRWeaponStats.isMeleeWeapon(itemstack) && !(item instanceof LOTRItemCommandSword)) || (this == LOTREnchantmentType.TOOL && !item.getToolClasses(itemstack).isEmpty()) || (this == LOTREnchantmentType.SHEARS && item instanceof ItemShears) || (this == LOTREnchantmentType.RANGED && LOTRWeaponStats.isRangedWeapon(itemstack)) || (this == LOTREnchantmentType.RANGED_LAUNCHER && (item instanceof ItemBow || item instanceof LOTRItemCrossbow || item instanceof LOTRItemBlowgun)) || (this == LOTREnchantmentType.THROWING_AXE && item instanceof LOTRItemThrowingAxe) || (this == LOTREnchantmentType.FISHING && item instanceof ItemFishingRod);
    }
}
