// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockRotatedPillar;

public abstract class LOTRBlockWoodBeam extends BlockRotatedPillar
{
    @SideOnly(Side.CLIENT)
    private IIcon[] sideIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] topIcons;
    private String[] woodNames;
    
    public LOTRBlockWoodBeam() {
        super(Material.wood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    protected void setWoodNames(final String... s) {
        this.woodNames = s;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.sideIcons = new IIcon[this.woodNames.length];
        this.topIcons = new IIcon[this.woodNames.length];
        for (int i = 0; i < this.woodNames.length; ++i) {
            this.topIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_top");
            this.sideIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_side");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < this.woodNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    @SideOnly(Side.CLIENT)
    protected IIcon func_150163_b(int i) {
        if (i < 0 || i >= this.woodNames.length) {
            i = 0;
        }
        return this.sideIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    protected IIcon func_150161_d(int i) {
        if (i < 0 || i >= this.woodNames.length) {
            i = 0;
        }
        return this.topIcons[i];
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getBeamRenderID();
    }
}
