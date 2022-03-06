// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockGateDwarven extends LOTRBlockGate
{
    public LOTRBlockGateDwarven() {
        super(Material.rock, false);
        this.setHardness(4.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
        this.setFullBlock();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        LOTRConnectedTextures.registerNonConnectedGateIcons(iconregister, this, 0, Blocks.stone.getIcon(0, 0).getIconName());
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final boolean open = LOTRBlockGate.isGateOpen(world, i, j, k);
        if (open) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
        }
        return Blocks.stone.getIcon(side, 0);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        return Blocks.stone.getIcon(i, 0);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final boolean flag = super.onBlockActivated(world, i, j, k, entityplayer, side, f, f1, f2);
        if (flag && !world.isClient) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenDoor);
        }
        return flag;
    }
}
