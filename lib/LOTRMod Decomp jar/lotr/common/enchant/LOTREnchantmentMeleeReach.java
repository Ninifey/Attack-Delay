// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentMeleeReach extends LOTREnchantment
{
    public final float reachFactor;
    
    public LOTREnchantmentMeleeReach(final String s, final float reach) {
        super(s, LOTREnchantmentType.MELEE);
        this.setValueModifier(this.reachFactor = reach);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.meleeReach.desc", new Object[] { this.formatMultiplicative(this.reachFactor) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.reachFactor >= 1.0f;
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (super.canApply(itemstack, considering)) {
            float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
            reach *= this.reachFactor;
            return reach <= LOTRWeaponStats.MAX_MODIFIABLE_REACH;
        }
        return false;
    }
}
