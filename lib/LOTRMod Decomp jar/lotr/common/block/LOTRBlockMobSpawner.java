// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockMobSpawner;

public class LOTRBlockMobSpawner extends BlockMobSpawner
{
    public LOTRBlockMobSpawner() {
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabSpawn);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (entityplayer.capabilities.isCreativeMode) {
            entityplayer.openGui((Object)LOTRMod.instance, 6, world, i, j, k);
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.mob_spawner.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityMobSpawner();
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof LOTRTileEntityMobSpawner) {
            final LOTRTileEntityMobSpawner spawner = (LOTRTileEntityMobSpawner)tileentity;
            return new ItemStack((Block)this, 1, LOTREntities.getIDFromString(spawner.getEntityClassName()));
        }
        return null;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getMobSpawnerRenderID();
    }
}
