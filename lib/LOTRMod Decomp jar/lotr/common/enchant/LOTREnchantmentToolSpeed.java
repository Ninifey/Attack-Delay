// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentToolSpeed extends LOTREnchantment
{
    public final float speedFactor;
    
    public LOTREnchantmentToolSpeed(final String s, final float speed) {
        super(s, new LOTREnchantmentType[] { LOTREnchantmentType.TOOL, LOTREnchantmentType.SHEARS });
        this.setValueModifier(this.speedFactor = speed);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.toolSpeed.desc", new Object[] { this.formatMultiplicative(this.speedFactor) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.speedFactor >= 1.0f;
    }
}
