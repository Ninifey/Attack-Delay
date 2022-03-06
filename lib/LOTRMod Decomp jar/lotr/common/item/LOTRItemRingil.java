// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRItemRingil extends LOTRItemSword implements LOTRStoryItem
{
    public LOTRItemRingil() {
        super(LOTRMaterial.HIGH_ELVEN);
        this.setMaxDamage(1500);
        super.lotrWeaponDamage = 9.0f;
        this.setIsElvenBlade();
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
    }
}
