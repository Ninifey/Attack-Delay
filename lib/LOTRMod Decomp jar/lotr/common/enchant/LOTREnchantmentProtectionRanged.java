// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentProtectionRanged extends LOTREnchantmentProtectionSpecial
{
    public LOTREnchantmentProtectionRanged(final String s, final int level) {
        super(s, level);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectRanged.desc", new Object[] { this.formatAdditiveInt(this.calcIntProtection()) });
    }
    
    @Override
    public boolean canApply(final ItemStack itemstack, final boolean considering) {
        if (super.canApply(itemstack, considering)) {
            final Item item = itemstack.getItem();
            return !(item instanceof ItemArmor) || ((ItemArmor)item).getArmorMaterial() != LOTRMaterial.GALVORN.toArmorMaterial();
        }
        return false;
    }
    
    @Override
    protected boolean protectsAgainst(final DamageSource source) {
        return source.isProjectile();
    }
    
    @Override
    protected int calcIntProtection() {
        return super.protectLevel;
    }
}
