// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockQuenditeGrass extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon grassSideIcon;
    
    public LOTRBlockQuenditeGrass() {
        super(Material.grass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 0) {
            return Blocks.dirt.getIcon(i, j);
        }
        if (i == 1) {
            return super.blockIcon;
        }
        return this.grassSideIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.blockIcon = iconregister.registerIcon("lotr:quenditeGrass_top");
        this.grassSideIcon = iconregister.registerIcon("lotr:quenditeGrass_side");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(Blocks.dirt);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(8) == 0) {
            final double d = i + random.nextFloat();
            final double d2 = j + 1.0;
            final double d3 = k + random.nextFloat();
            LOTRMod.proxy.spawnParticle("quenditeSmoke", d, d2, d3, 0.0, 0.0, 0.0);
        }
    }
}
