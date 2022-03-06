// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.Item;

public class LOTRItemPike extends LOTRItemPolearmLong
{
    public LOTRItemPike(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemPike(final Item.ToolMaterial material) {
        super(material);
    }
}
