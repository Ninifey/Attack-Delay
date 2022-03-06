// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockGoran extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] goranIcons;
    private static final String[] goranNames;
    public static final String[] displayNames;
    
    public LOTRBlockGoran() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)null);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            if (!MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile())) {
                return false;
            }
            for (int i2 = i - 32; i2 <= i + 32; ++i2) {
                for (int j2 = j - 32; j2 <= j + 32; ++j2) {
                    for (int k2 = k - 32; k2 <= k + 32; ++k2) {
                        if (world.blockExists(i2, j2, k2) && world.isAirBlock(i2, j2, k2)) {
                            world.setBlock(i2, j2, k2, Blocks.water);
                        }
                    }
                }
            }
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= LOTRBlockGoran.goranNames.length) {
            j = 0;
        }
        return this.goranIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.goranIcons = new IIcon[LOTRBlockGoran.goranNames.length];
        for (int i = 0; i < LOTRBlockGoran.goranNames.length; ++i) {
            String iconName = this.getTextureName();
            if (!LOTRBlockGoran.goranNames[i].equals("")) {
                iconName = iconName + "_" + LOTRBlockGoran.goranNames[i];
            }
            this.goranIcons[i] = iconregister.registerIcon(iconName);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < LOTRBlockGoran.goranNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    static {
        goranNames = new String[] { "", "rock" };
        displayNames = new String[] { "Goran", "Sarngoran" };
    }
}
