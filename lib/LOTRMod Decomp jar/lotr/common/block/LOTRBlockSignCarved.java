// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.block.BlockSign;

public class LOTRBlockSignCarved extends BlockSign
{
    public LOTRBlockSignCarved(final Class<? extends LOTRTileEntitySign> cls) {
        super((Class)cls, false);
        this.setStepSound(Block.soundTypeStone);
        this.setHardness(0.5f);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        super.setBlockBoundsBasedOnState(world, i, j, k);
        this.setBlockBounds((float)((Block)this).minX, 0.0f, (float)((Block)this).minZ, (float)((Block)this).maxX, 1.0f, (float)((Block)this).maxZ);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return LOTRCommonIcons.iconEmptyBlock;
    }
    
    public IIcon getOnBlockIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final int onX = i - Facing.offsetsXForSide[side];
        final int onY = j - Facing.offsetsYForSide[side];
        final int onZ = k - Facing.offsetsZForSide[side];
        final Block onBlock = world.getBlock(onX, onY, onZ);
        IIcon icon = onBlock.getIcon(world, onX, onY, onZ, side);
        if (icon == null) {
            icon = Blocks.stone.getIcon(0, 0);
        }
        return icon;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        if (this == LOTRMod.signCarvedIthildin) {
            return LOTRMod.chiselIthildin;
        }
        return LOTRMod.chisel;
    }
}
