// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockCrops;

public class LOTRBlockFlaxCrop extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] flaxIcons;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j < 7) {
            if (j == 6) {
                j = 5;
            }
            return this.flaxIcons[j >> 1];
        }
        return LOTRMod.flaxPlant.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.flaxIcons = new IIcon[3];
        for (int i = 0; i < this.flaxIcons.length; ++i) {
            this.flaxIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }
    
    public Item func_149866_i() {
        return LOTRMod.flaxSeeds;
    }
    
    public Item func_149865_P() {
        return LOTRMod.flax;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
}
