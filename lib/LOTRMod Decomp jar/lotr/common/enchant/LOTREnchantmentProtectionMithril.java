// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentProtectionMithril extends LOTREnchantmentProtectionSpecial
{
    public LOTREnchantmentProtectionMithril(final String s) {
        super(s, 1);
        this.setValueModifier(2.0f);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectMithril.desc", new Object[] { this.formatAdditiveInt(this.calcIntProtection()) });
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (super.canApply(itemstack, considering)) {
            final Item item = itemstack.getItem();
            return item instanceof ItemArmor && ((ItemArmor)item).getArmorMaterial() == LOTRMaterial.MITHRIL.toArmorMaterial();
        }
        return false;
    }
    
    @Override
    protected boolean protectsAgainst(final DamageSource source) {
        final Entity attacker = source.getEntity();
        final Entity entity = source.getSourceOfDamage();
        if (attacker instanceof EntityLivingBase && attacker == entity) {
            final ItemStack weapon = ((EntityLivingBase)attacker).getHeldItem();
            if (weapon != null) {
                final ItemStack weaponBase = weapon.copy();
                LOTREnchantmentHelper.clearEnchants(weaponBase);
                final float range = LOTRWeaponStats.getMeleeReachFactor(weaponBase);
                if (range >= 1.3f) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected int calcIntProtection() {
        return 4;
    }
}
