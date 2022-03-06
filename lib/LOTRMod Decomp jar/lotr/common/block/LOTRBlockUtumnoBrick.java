// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;

public class LOTRBlockUtumnoBrick extends Block implements LOTRWorldProviderUtumno.UtumnoBlock
{
    @SideOnly(Side.CLIENT)
    private IIcon[] brickIcons;
    private String[] brickNames;
    
    public LOTRBlockUtumnoBrick() {
        super(Material.rock);
        this.brickNames = new String[] { "fire", "burning", "ice", "iceGlowing", "obsidian", "obsidianFire", "iceTile", "obsidianTile", "fireTile" };
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(1.5f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.brickNames.length) {
            j = 0;
        }
        return this.brickIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.brickIcons = new IIcon[this.brickNames.length];
        for (int i = 0; i < this.brickNames.length; ++i) {
            this.brickIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.brickNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 1 || meta == 3 || meta == 5) {
            return 12;
        }
        return super.getLightValue(world, i, j, k);
    }
    
    public boolean isFireSource(final World world, final int i, final int j, final int k, final ForgeDirection side) {
        return this.isFlammable((IBlockAccess)world, i, j, k, side);
    }
    
    public boolean isFlammable(final IBlockAccess world, final int i, final int j, final int k, final ForgeDirection side) {
        final int meta = world.getBlockMetadata(i, j, k);
        return meta == 0 || meta == 1 || super.isFlammable(world, i, j, k, side);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.brickNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
