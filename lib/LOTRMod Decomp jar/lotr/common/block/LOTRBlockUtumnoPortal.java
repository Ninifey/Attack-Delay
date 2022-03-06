// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import lotr.common.LOTRDimension;
import java.util.Random;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.tileentity.LOTRTileEntityUtumnoPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockUtumnoPortal extends BlockContainer
{
    public LOTRBlockUtumnoPortal() {
        super(Material.Portal);
        this.setHardness(-1.0f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityUtumnoPortal();
    }
    
    public void addCollisionBoxesToList(final World world, final int i, final int j, final int k, final AxisAlignedBB aabb, final List list, final Entity entity) {
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        entity.setInWeb();
        final TileEntity te = world.getTileEntity(i, j, k);
        if (te instanceof LOTRTileEntityUtumnoPortal) {
            ((LOTRTileEntityUtumnoPortal)te).transferEntity(entity);
        }
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        if (world.provider.dimensionId != LOTRDimension.MIDDLE_EARTH.dimensionID) {
            world.setBlockToAir(i, j, k);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return Item.getItemById(0);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.portal.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
