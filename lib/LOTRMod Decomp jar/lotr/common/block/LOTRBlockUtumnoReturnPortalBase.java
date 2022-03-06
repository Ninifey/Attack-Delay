// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockUtumnoReturnPortalBase extends Block
{
    public static int MAX_SACRIFICE;
    public static int RANGE;
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    
    public LOTRBlockUtumnoReturnPortalBase() {
        super(Material.circuits);
        this.setHardness(-1.0f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return this.topIcon;
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.topIcon = iconregister.registerIcon(this.getTextureName() + "_top");
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        this.setBlockBoundsMeta(meta);
    }
    
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsMeta(0);
    }
    
    private void setBlockBoundsMeta(final int meta) {
        final float f = meta / (float)LOTRBlockUtumnoReturnPortalBase.MAX_SACRIFICE;
        final float f2 = 0.0625f;
        final float f3 = f2 + (1.0f - f2) * f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f3, 1.0f);
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        final float f = meta / (float)LOTRBlockUtumnoReturnPortalBase.MAX_SACRIFICE;
        final float f2 = 0.5f;
        float f3 = f2 + (1.0f - f2) * f;
        f3 *= 16.0f;
        return (int)f3;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    static {
        LOTRBlockUtumnoReturnPortalBase.MAX_SACRIFICE = 15;
        LOTRBlockUtumnoReturnPortalBase.RANGE = 5;
    }
}
