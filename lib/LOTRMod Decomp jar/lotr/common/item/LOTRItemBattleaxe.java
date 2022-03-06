// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class LOTRItemBattleaxe extends LOTRItemSword
{
    private float efficiencyOnProperMaterial;
    
    public LOTRItemBattleaxe(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemBattleaxe(final Item.ToolMaterial material) {
        super(material);
        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
        this.setHarvestLevel("axe", material.getHarvestLevel());
        super.lotrWeaponDamage += 2.0f;
    }
    
    public float func_150893_a(final ItemStack itemstack, final Block block) {
        final float f = super.func_150893_a(itemstack, block);
        if (f == 1.0f && block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine)) {
            return this.efficiencyOnProperMaterial;
        }
        return f;
    }
    
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final Block block, final int i, final int j, final int k, final EntityLivingBase entity) {
        if (block.getBlockHardness(world, i, j, k) != 0.0) {
            itemstack.damageItem(1, entity);
        }
        return true;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.none;
    }
}
