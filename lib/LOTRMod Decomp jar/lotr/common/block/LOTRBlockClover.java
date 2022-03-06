// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.world.ColorizerGrass;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockClover extends LOTRBlockFlower
{
    @SideOnly(Side.CLIENT)
    public static IIcon stemIcon;
    @SideOnly(Side.CLIENT)
    public static IIcon petalIcon;
    
    public LOTRBlockClover() {
        this.setBlockBounds(0.2f, 0.0f, 0.2f, 0.8f, 0.4f, 0.8f);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        double posX = i;
        final double posY = j;
        double posZ = k;
        long seed = (long)(i * 3129871) ^ k * 116129781L ^ (long)j;
        seed = seed * seed * 42317861L + seed * 11L;
        posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
        posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
        return AxisAlignedBB.getBoundingBox(posX + ((Block)this).minX, posY + ((Block)this).minY, posZ + ((Block)this).minZ, posX + ((Block)this).maxX, posY + ((Block)this).maxY, posZ + ((Block)this).maxZ);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return LOTRBlockClover.petalIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        LOTRBlockClover.stemIcon = iconregister.registerIcon(this.getTextureName() + "_stem");
        LOTRBlockClover.petalIcon = iconregister.registerIcon(this.getTextureName() + "_petal");
    }
    
    public boolean isReplaceable(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return meta != 1;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j <= 1; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getCloverRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(1.0, 1.0);
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int i) {
        return this.getBlockColor();
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return world.getBiomeGenForCoords(i, k).getBiomeGrassColor(i, j, k);
    }
}
