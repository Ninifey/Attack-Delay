// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentDurability extends LOTREnchantment
{
    public final float durabilityFactor;
    
    public LOTREnchantmentDurability(final String s, final float f) {
        super(s, LOTREnchantmentType.BREAKABLE);
        this.setValueModifier(this.durabilityFactor = f);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.durable.desc", new Object[] { this.formatMultiplicative(this.durabilityFactor) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.durabilityFactor >= 1.0f;
    }
}
