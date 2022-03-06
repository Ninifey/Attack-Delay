// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class LOTRItemHammer extends LOTRItemSword
{
    public LOTRItemHammer(final LOTRMaterial material) {
        this(material.toToolMaterial());
        super.lotrWeaponDamage += 2.0f;
    }
    
    public LOTRItemHammer(final Item.ToolMaterial material) {
        super(material);
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.none;
    }
}
