// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.EnumPlantType;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.common.IPlantable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockCrops;

public class LOTRBlockYamCrop extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] yamIcons;
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        if (world.getBlockMetadata(i, j, k) == 8) {
            return world.getBlock(i, j - 1, k).canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.tallgrass);
        }
        return super.canBlockStay(world, i, j, k);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j < 7) {
            if (j == 6) {
                j = 5;
            }
            return this.yamIcons[j >> 1];
        }
        return this.yamIcons[3];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.yamIcons = new IIcon[4];
        for (int i = 0; i < this.yamIcons.length; ++i) {
            this.yamIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }
    
    public Item func_149866_i() {
        return LOTRMod.yam;
    }
    
    public Item func_149865_P() {
        return LOTRMod.yam;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
}
