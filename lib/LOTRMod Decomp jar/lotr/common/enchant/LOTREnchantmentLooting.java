// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentLooting extends LOTREnchantment
{
    public final int lootLevel;
    
    public LOTREnchantmentLooting(final String s, final int level) {
        super(s, new LOTREnchantmentType[] { LOTREnchantmentType.TOOL, LOTREnchantmentType.MELEE });
        this.lootLevel = level;
        this.setValueModifier(1.0f + this.lootLevel);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.looting.desc", new Object[] { this.formatAdditiveInt(this.lootLevel) });
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
    
    @Override
    public boolean isCompatibleWith(final LOTREnchantment other) {
        return super.isCompatibleWith(other) && !(other instanceof LOTREnchantmentSilkTouch);
    }
}
