// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.item.Item;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentProtection extends LOTREnchantment
{
    public final int protectLevel;
    
    public LOTREnchantmentProtection(final String s, final int level) {
        this(s, LOTREnchantmentType.ARMOR, level);
    }
    
    public LOTREnchantmentProtection(final String s, final LOTREnchantmentType type, final int level) {
        super(s, type);
        this.protectLevel = level;
        if (this.protectLevel >= 0) {
            this.setValueModifier((2.0f + this.protectLevel) / 2.0f);
        }
        else {
            this.setValueModifier((4.0f + this.protectLevel) / 4.0f);
        }
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protect.desc", new Object[] { this.formatAdditiveInt(this.protectLevel) });
    }
    
    @Override
    public boolean isBeneficial() {
        return this.protectLevel >= 0;
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (!super.canApply(itemstack, considering)) {
            return false;
        }
        final Item item = itemstack.getItem();
        if (!(item instanceof ItemArmor)) {
            return true;
        }
        final ItemArmor armor = (ItemArmor)item;
        if (armor.getArmorMaterial() == LOTRMaterial.GALVORN.toArmorMaterial()) {
            return false;
        }
        final int prot = armor.damageReduceAmount;
        final int total = prot + this.protectLevel;
        return total > 0 && (considering || total <= LOTRMaterial.MITHRIL.toArmorMaterial().getDamageReductionAmount(armor.armorType));
    }
}
