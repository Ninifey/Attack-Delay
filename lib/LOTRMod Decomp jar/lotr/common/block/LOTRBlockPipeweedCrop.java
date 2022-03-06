// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockCrops;

public class LOTRBlockPipeweedCrop extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] pipeweedIcons;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j < 7) {
            if (j == 6) {
                j = 5;
            }
            return this.pipeweedIcons[j >> 1];
        }
        return this.pipeweedIcons[3];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.pipeweedIcons = new IIcon[4];
        for (int i = 0; i < this.pipeweedIcons.length; ++i) {
            this.pipeweedIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }
    
    public Item func_149866_i() {
        return LOTRMod.pipeweedSeeds;
    }
    
    public Item func_149865_P() {
        return LOTRMod.pipeweedLeaf;
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (world.getBlockMetadata(i, j, k) == 7) {
            LOTRMod.pipeweedPlant.randomDisplayTick(world, i, j, k, random);
        }
    }
}
