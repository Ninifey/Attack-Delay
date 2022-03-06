// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class LOTRItemPolearm extends LOTRItemSword
{
    public LOTRItemPolearm(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemPolearm(final Item.ToolMaterial material) {
        super(material);
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.none;
    }
}
