// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.Item;

public class LOTRItemPolearmLong extends LOTRItemPolearm
{
    public LOTRItemPolearmLong(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemPolearmLong(final Item.ToolMaterial material) {
        super(material);
    }
}
