// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.DamageSource;

public abstract class LOTREnchantmentProtectionSpecial extends LOTREnchantment
{
    public final int protectLevel;
    
    public LOTREnchantmentProtectionSpecial(final String s, final int level) {
        this(s, LOTREnchantmentType.ARMOR, level);
    }
    
    public LOTREnchantmentProtectionSpecial(final String s, final LOTREnchantmentType type, final int level) {
        super(s, type);
        this.protectLevel = level;
        this.setValueModifier((2.0f + this.protectLevel) / 2.0f);
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
    
    @Override
    public boolean isCompatibleWith(final LOTREnchantment other) {
        return super.isCompatibleWith(other) && (!(other instanceof LOTREnchantmentProtectionSpecial) || this.isCompatibleWithOtherProtection() || ((LOTREnchantmentProtectionSpecial)other).isCompatibleWithOtherProtection());
    }
    
    protected boolean isCompatibleWithOtherProtection() {
        return false;
    }
    
    protected abstract boolean protectsAgainst(final DamageSource p0);
    
    public final int calcSpecialProtection(final DamageSource source) {
        if (source.canHarmInCreative()) {
            return 0;
        }
        if (this.protectsAgainst(source)) {
            return this.calcIntProtection();
        }
        return 0;
    }
    
    protected abstract int calcIntProtection();
}
