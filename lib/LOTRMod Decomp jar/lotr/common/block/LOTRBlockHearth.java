// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockHearth extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] blockIcons;
    
    public LOTRBlockHearth() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 0) {
            return this.blockIcons[0];
        }
        if (i == 1) {
            return this.blockIcons[1];
        }
        return this.blockIcons[2];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.blockIcons = new IIcon[3])[0] = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.blockIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.blockIcons[2] = iconregister.registerIcon(this.getTextureName() + "_side");
    }
    
    public boolean isFireSource(final World world, final int i, final int j, final int k, final ForgeDirection side) {
        return side == ForgeDirection.UP;
    }
    
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (world.getBlock(i, j + 1, k) == Blocks.fire) {
            for (int smokeHeight = 5, j2 = j + 1; j2 <= j + smokeHeight; ++j2) {
                if (world.getBlock(i, j2, k).getMaterial().isSolid()) {
                    break;
                }
                for (int l = 0; l < 3; ++l) {
                    final float f = i + random.nextFloat();
                    final float f2 = j2 + random.nextFloat();
                    final float f3 = k + random.nextFloat();
                    world.spawnParticle("largesmoke", (double)f, (double)f2, (double)f3, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
