// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class LOTRItemMattock extends LOTRItemPickaxe
{
    private float efficiencyOnProperMaterial;
    
    public LOTRItemMattock(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemMattock(final Item.ToolMaterial material) {
        super(material);
        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
        this.setHarvestLevel("axe", material.getHarvestLevel());
    }
    
    public float func_150893_a(final ItemStack itemstack, final Block block) {
        final float f = super.func_150893_a(itemstack, block);
        if (f == 1.0f && block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine)) {
            return this.efficiencyOnProperMaterial;
        }
        return f;
    }
}
