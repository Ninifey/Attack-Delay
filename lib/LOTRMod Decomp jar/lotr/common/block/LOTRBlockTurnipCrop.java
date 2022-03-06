// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockCrops;

public class LOTRBlockTurnipCrop extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] turnipIcons;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j < 7) {
            if (j == 6) {
                j = 5;
            }
            return this.turnipIcons[j >> 1];
        }
        return this.turnipIcons[3];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.turnipIcons = new IIcon[4];
        for (int i = 0; i < this.turnipIcons.length; ++i) {
            this.turnipIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }
    
    public Item func_149866_i() {
        return LOTRMod.turnip;
    }
    
    public Item func_149865_P() {
        return LOTRMod.turnip;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
}
