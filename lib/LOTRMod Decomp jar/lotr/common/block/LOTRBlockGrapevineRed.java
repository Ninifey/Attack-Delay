// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockGrapevineRed extends LOTRBlockGrapevine
{
    public LOTRBlockGrapevineRed() {
        super(true);
    }
    
    @Override
    public Item getGrapeItem() {
        return LOTRMod.grapeRed;
    }
    
    @Override
    public Item getGrapeSeedsItem() {
        return LOTRMod.seedsGrapeRed;
    }
}
