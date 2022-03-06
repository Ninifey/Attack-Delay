// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import lotr.common.block.LOTRBlockGrapevine;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.Item;

public class LOTRItemGrapeSeeds extends Item implements IPlantable
{
    private Block grapevineBlock;
    
    public LOTRItemGrapeSeeds(final Block block) {
        this.grapevineBlock = block;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            final Block block = world.getBlock(i, j, k);
            if (block == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(world, i, j, k, (IPlantable)this)) {
                world.setBlock(i, j, k, this.grapevineBlock, 0, 3);
                --itemstack.stackSize;
                return true;
            }
        }
        return false;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
    
    public Block getPlant(final IBlockAccess world, final int i, final int j, final int k) {
        return this.grapevineBlock;
    }
    
    public int getPlantMetadata(final IBlockAccess world, final int i, final int j, final int k) {
        return 0;
    }
}
