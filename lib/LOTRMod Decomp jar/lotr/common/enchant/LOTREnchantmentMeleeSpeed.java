// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentMeleeSpeed extends LOTREnchantment
{
    public final float speedFactor;
    
    public LOTREnchantmentMeleeSpeed(final String s, final float speed) {
        super(s, LOTREnchantmentType.MELEE);
        this.setValueModifier(this.speedFactor = speed);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.meleeSpeed.desc", new Object[] { this.formatMultiplicative(this.speedFactor) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.speedFactor >= 1.0f;
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (super.canApply(itemstack, considering)) {
            float speed = LOTRWeaponStats.getMeleeSpeed(itemstack);
            speed *= this.speedFactor;
            return speed <= LOTRWeaponStats.MAX_MODIFIABLE_SPEED;
        }
        return false;
    }
}
