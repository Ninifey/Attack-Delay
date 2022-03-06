// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class LOTRMaterialRemains extends Material
{
    public static LOTRMaterialRemains remains;
    
    public LOTRMaterialRemains() {
        super(MapColor.field_151664_l);
        this.setRequiresTool();
    }
    
    static {
        LOTRMaterialRemains.remains = new LOTRMaterialRemains();
    }
}
