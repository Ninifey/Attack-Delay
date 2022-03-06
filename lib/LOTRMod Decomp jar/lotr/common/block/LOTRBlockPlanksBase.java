// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public abstract class LOTRBlockPlanksBase extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] plankIcons;
    private String[] plankTypes;
    
    public LOTRBlockPlanksBase() {
        super(Material.wood);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    protected void setPlankTypes(final String... types) {
        this.plankTypes = types;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.plankTypes.length) {
            j = 0;
        }
        return this.plankIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.plankIcons = new IIcon[this.plankTypes.length];
        for (int i = 0; i < this.plankTypes.length; ++i) {
            this.plankIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.plankTypes[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < this.plankTypes.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
