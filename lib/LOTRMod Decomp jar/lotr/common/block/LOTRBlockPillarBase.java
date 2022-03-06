// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public abstract class LOTRBlockPillarBase extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] pillarFaceIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] pillarSideIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] pillarSideTopIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] pillarSideMiddleIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] pillarSideBottomIcons;
    private String[] pillarNames;
    
    public LOTRBlockPillarBase() {
        this(Material.rock);
        this.setHardness(1.5f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public LOTRBlockPillarBase(final Material material) {
        super(material);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    protected void setPillarNames(final String... names) {
        this.pillarNames = names;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final boolean pillarAbove = this.isPillarAt(world, i, j + 1, k);
        final boolean pillarBelow = this.isPillarAt(world, i, j - 1, k);
        int meta = world.getBlockMetadata(i, j, k);
        if (meta >= this.pillarNames.length) {
            meta = 0;
        }
        if (side == 0 || side == 1) {
            return this.pillarFaceIcons[meta];
        }
        if (pillarAbove && pillarBelow) {
            return this.pillarSideMiddleIcons[meta];
        }
        if (pillarAbove) {
            return this.pillarSideBottomIcons[meta];
        }
        if (pillarBelow) {
            return this.pillarSideTopIcons[meta];
        }
        return this.pillarSideIcons[meta];
    }
    
    private boolean isPillarAt(final IBlockAccess world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        return block instanceof LOTRBlockPillarBase;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.pillarNames.length) {
            j = 0;
        }
        if (i == 0 || i == 1) {
            return this.pillarFaceIcons[j];
        }
        return this.pillarSideIcons[j];
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.pillarNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.pillarFaceIcons = new IIcon[this.pillarNames.length];
        this.pillarSideIcons = new IIcon[this.pillarNames.length];
        this.pillarSideTopIcons = new IIcon[this.pillarNames.length];
        this.pillarSideMiddleIcons = new IIcon[this.pillarNames.length];
        this.pillarSideBottomIcons = new IIcon[this.pillarNames.length];
        for (int i = 0; i < this.pillarNames.length; ++i) {
            final String s = this.getTextureName() + "_" + this.pillarNames[i];
            this.pillarFaceIcons[i] = iconregister.registerIcon(s + "_face");
            this.pillarSideIcons[i] = iconregister.registerIcon(s + "_side");
            this.pillarSideTopIcons[i] = iconregister.registerIcon(s + "_sideTop");
            this.pillarSideMiddleIcons[i] = iconregister.registerIcon(s + "_sideMiddle");
            this.pillarSideBottomIcons[i] = iconregister.registerIcon(s + "_sideBottom");
        }
    }
}
