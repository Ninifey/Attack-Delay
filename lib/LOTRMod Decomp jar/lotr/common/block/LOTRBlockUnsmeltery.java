// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockUnsmeltery extends LOTRBlockForgeBase
{
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        return this.getIcon(side, world.getBlockMetadata(i, j, k));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        return Blocks.cobblestone.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityUnsmeltery();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getUnsmelteryRenderID();
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 38, world, i, j, k);
        }
        return true;
    }
    
    @Override
    protected boolean useLargeSmoke() {
        return false;
    }
    
    @Override
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        super.randomDisplayTick(world, i, j, k, random);
        if (LOTRBlockForgeBase.isForgeActive((IBlockAccess)world, i, j, k)) {
            for (int l = 0; l < 3; ++l) {
                final float f = i + 0.25f + random.nextFloat() * 0.5f;
                final float f2 = j + 0.5f + random.nextFloat() * 0.5f;
                final float f3 = k + 0.25f + random.nextFloat() * 0.5f;
                world.spawnParticle("largesmoke", (double)f, (double)f2, (double)f3, 0.0, 0.0, 0.0);
            }
        }
    }
}
