// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentProtectionFall extends LOTREnchantmentProtectionSpecial
{
    public LOTREnchantmentProtectionFall(final String s, final int level) {
        super(s, LOTREnchantmentType.ARMOR_FEET, level);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectFall.desc", new Object[] { this.formatAdditiveInt(this.calcIntProtection()) });
    }
    
    @Override
    protected boolean isCompatibleWithOtherProtection() {
        return true;
    }
    
    @Override
    protected boolean protectsAgainst(final DamageSource source) {
        return source == DamageSource.fall;
    }
    
    @Override
    protected int calcIntProtection() {
        final float f = super.protectLevel * (float)(super.protectLevel + 1) / 2.0f;
        return 3 + MathHelper.floor_float(f);
    }
}
