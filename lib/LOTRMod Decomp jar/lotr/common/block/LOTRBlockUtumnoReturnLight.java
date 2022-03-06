// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockUtumnoReturnLight extends Block
{
    public LOTRBlockUtumnoReturnLight() {
        super(Material.circuits);
        this.setLightLevel(1.0f);
    }
    
    public boolean isReplaceable(final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean isCollidable() {
        return false;
    }
}
