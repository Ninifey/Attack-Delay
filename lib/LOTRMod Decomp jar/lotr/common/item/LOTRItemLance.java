// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import java.util.UUID;

public class LOTRItemLance extends LOTRItemPolearmLong
{
    public static final UUID lanceSpeedBoost_id;
    public static final AttributeModifier lanceSpeedBoost;
    
    public LOTRItemLance(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemLance(final Item.ToolMaterial material) {
        super(material);
    }
    
    static {
        lanceSpeedBoost_id = UUID.fromString("4da96302-7457-42ed-9709-f1be0c465ec3");
        lanceSpeedBoost = new AttributeModifier(LOTRItemLance.lanceSpeedBoost_id, "Lance speed boost", -0.2, 2).setSaved(false);
    }
}
