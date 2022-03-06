// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRItemGlamdring extends LOTRItemSword implements LOTRStoryItem
{
    public LOTRItemGlamdring() {
        super(LOTRMaterial.GONDOLIN);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
        this.setIsElvenBlade();
    }
}
