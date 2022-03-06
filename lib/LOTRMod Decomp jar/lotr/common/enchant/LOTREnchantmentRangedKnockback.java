// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentRangedKnockback extends LOTREnchantment
{
    public final int knockback;
    
    public LOTREnchantmentRangedKnockback(final String s, final int i) {
        super(s, LOTREnchantmentType.RANGED_LAUNCHER);
        this.knockback = i;
        this.setValueModifier((this.knockback + 2) / 2.0f);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.rangedKnockback.desc", new Object[] { this.formatAdditiveInt(this.knockback) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.knockback >= 0;
    }
}
