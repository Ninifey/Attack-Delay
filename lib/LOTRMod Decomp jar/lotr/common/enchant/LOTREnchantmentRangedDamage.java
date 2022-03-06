// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentRangedDamage extends LOTREnchantment
{
    public final float damageFactor;
    
    public LOTREnchantmentRangedDamage(final String s, final float damage) {
        super(s, LOTREnchantmentType.RANGED_LAUNCHER);
        this.damageFactor = damage;
        if (this.damageFactor > 1.0f) {
            this.setValueModifier(this.damageFactor * 2.0f);
        }
        else {
            this.setValueModifier(this.damageFactor);
        }
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.rangedDamage.desc", new Object[] { this.formatMultiplicative(this.damageFactor) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.damageFactor >= 1.0f;
    }
}
