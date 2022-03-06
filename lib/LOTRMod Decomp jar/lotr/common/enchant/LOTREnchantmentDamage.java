// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.util.StatCollector;
import lotr.common.item.LOTRItemThrowingAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

public class LOTREnchantmentDamage extends LOTREnchantment
{
    private final float baseDamageBoost;
    
    public LOTREnchantmentDamage(final String s, final float boost) {
        super(s, new LOTREnchantmentType[] { LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE });
        this.baseDamageBoost = boost;
        if (this.baseDamageBoost >= 0.0f) {
            this.setValueModifier((7.0f + this.baseDamageBoost * 5.0f) / 7.0f);
        }
        else {
            this.setValueModifier((7.0f + this.baseDamageBoost) / 7.0f);
        }
    }
    
    public float getBaseDamageBoost() {
        return this.baseDamageBoost;
    }
    
    public float getEntitySpecificDamage(final EntityLivingBase entity) {
        return 0.0f;
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemThrowingAxe) {
            return StatCollector.translateToLocalFormatted("lotr.enchant.damage.desc.throw", new Object[] { this.formatAdditive(this.baseDamageBoost) });
        }
        return StatCollector.translateToLocalFormatted("lotr.enchant.damage.desc", new Object[] { this.formatAdditive(this.baseDamageBoost) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.baseDamageBoost >= 0.0f;
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (super.canApply(itemstack, considering)) {
            float dmg = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
            dmg += this.baseDamageBoost;
            return dmg > 0.0f;
        }
        return false;
    }
}
