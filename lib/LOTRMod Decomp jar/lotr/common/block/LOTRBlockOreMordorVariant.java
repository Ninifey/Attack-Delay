// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockOreMordorVariant extends LOTRBlockOre
{
    @SideOnly(Side.CLIENT)
    private IIcon mordorIcon;
    private boolean dropsBlock;
    
    public LOTRBlockOreMordorVariant(final boolean flag) {
        this.dropsBlock = flag;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (j == 1) {
            return this.mordorIcon;
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.mordorIcon = iconregister.registerIcon(this.getTextureName() + "_mordor");
    }
    
    public int damageDropped(final int i) {
        if (this.dropsBlock) {
            return i;
        }
        return super.damageDropped(i);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j <= 1; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        return new ItemStack((Block)this, 1, world.getBlockMetadata(i, j, k));
    }
}
