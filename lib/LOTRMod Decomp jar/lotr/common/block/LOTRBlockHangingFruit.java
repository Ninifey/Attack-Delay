// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public abstract class LOTRBlockHangingFruit extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] fruitIcons;
    private String[] fruitSides;
    
    public LOTRBlockHangingFruit() {
        super(Material.plants);
        this.fruitSides = new String[] { "top", "side", "bottom" };
        this.setTickRandomly(true);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 0) {
            return this.fruitIcons[2];
        }
        if (i == 1) {
            return this.fruitIcons[0];
        }
        return this.fruitIcons[1];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.fruitIcons = new IIcon[3];
        for (int i = 0; i < 3; ++i) {
            this.fruitIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.fruitSides[i]);
        }
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final int l = world.getBlockMetadata(i, j, k);
        final ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
        final Block block = world.getBlock(i + dir.offsetX, j, k + dir.offsetZ);
        return block.isWood((IBlockAccess)world, i + dir.offsetX, j, k + dir.offsetZ);
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
}
