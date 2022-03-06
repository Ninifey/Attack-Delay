// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentProtectionFire extends LOTREnchantmentProtectionSpecial
{
    public LOTREnchantmentProtectionFire(final String s, final int level) {
        super(s, level);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectFire.desc", new Object[] { this.formatAdditiveInt(this.calcIntProtection()) });
    }
    
    @Override
    protected boolean protectsAgainst(final DamageSource source) {
        return source.isFireDamage();
    }
    
    @Override
    protected int calcIntProtection() {
        return 1 + super.protectLevel;
    }
}
