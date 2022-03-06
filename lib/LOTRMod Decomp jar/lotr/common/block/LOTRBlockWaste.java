// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRBlockWaste extends Block
{
    private static Random wasteRand;
    @SideOnly(Side.CLIENT)
    private IIcon[] randomIcons;
    
    public LOTRBlockWaste() {
        super(Material.ground);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeSand);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.randomIcons = new IIcon[4];
        for (int l = 0; l < this.randomIcons.length; ++l) {
            this.randomIcons[l] = iconregister.registerIcon(this.getTextureName() + "_var" + l);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        int hash = i * 25799626 ^ k * 6879038 ^ j;
        hash += side;
        LOTRBlockWaste.wasteRand.setSeed(hash);
        LOTRBlockWaste.wasteRand.setSeed(LOTRBlockWaste.wasteRand.nextLong());
        return this.randomIcons[LOTRBlockWaste.wasteRand.nextInt(this.randomIcons.length)];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        int hash = i * 334224425 ^ i;
        hash = hash * hash * 245256 + hash * 113549945;
        LOTRBlockWaste.wasteRand.setSeed(hash);
        LOTRBlockWaste.wasteRand.setSeed(LOTRBlockWaste.wasteRand.nextLong());
        return this.randomIcons[LOTRBlockWaste.wasteRand.nextInt(this.randomIcons.length)];
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getWasteRenderID();
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        final float f = 0.125f;
        return AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1 - f), (double)(k + 1));
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        final double slow = 0.4;
        entity.motionX *= slow;
        entity.motionZ *= slow;
    }
    
    public boolean isFireSource(final World world, final int i, final int j, final int k, final ForgeDirection side) {
        return side == ForgeDirection.UP;
    }
    
    static {
        LOTRBlockWaste.wasteRand = new Random();
    }
}
