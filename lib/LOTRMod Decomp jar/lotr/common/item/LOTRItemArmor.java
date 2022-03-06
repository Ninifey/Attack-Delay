// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.util.StringUtils;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemArmor;

public class LOTRItemArmor extends ItemArmor
{
    private LOTRMaterial lotrMaterial;
    private String extraName;
    
    public LOTRItemArmor(final LOTRMaterial material, final int slotType) {
        this(material, slotType, "");
    }
    
    public LOTRItemArmor(final LOTRMaterial material, final int slotType, final String s) {
        super(material.toArmorMaterial(), 0, slotType);
        this.lotrMaterial = material;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        this.extraName = s;
    }
    
    public LOTRMaterial getLOTRArmorMaterial() {
        return this.lotrMaterial;
    }
    
    public String getArmorTexture(final ItemStack itemstack, final Entity entity, final int slot, final String type) {
        String path = "lotr:armor/";
        if (entity instanceof LOTREntityHalfTroll) {
            path = "lotr:mob/halfTroll/";
        }
        final String armorName = this.getArmorName();
        String texture = path + armorName;
        if (type != null) {
            texture = texture + "_" + type;
        }
        return texture + ".png";
    }
    
    private String getArmorName() {
        final String prefix = this.getArmorMaterial().name().substring("lotr".length() + 1).toLowerCase();
        String suffix = (super.armorType == 2) ? "2" : "1";
        if (!StringUtils.isNullOrEmpty(this.extraName)) {
            suffix = this.extraName;
        }
        return prefix + "_" + suffix;
    }
    
    public boolean isDamageable() {
        return this.lotrMaterial.isDamageable();
    }
}
