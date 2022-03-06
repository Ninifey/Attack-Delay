// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentSilkTouch extends LOTREnchantment
{
    public LOTREnchantmentSilkTouch(final String s) {
        super(s, LOTREnchantmentType.TOOL);
        this.setValueModifier(3.0f);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant." + super.enchantName + ".desc", new Object[0]);
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
    
    @Override
    public boolean isCompatibleWith(final LOTREnchantment other) {
        return super.isCompatibleWith(other) && !(other instanceof LOTREnchantmentLooting);
    }
}
