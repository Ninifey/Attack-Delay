// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
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

public abstract class LOTRBlockOreStorageBase extends Block
{
    @SideOnly(Side.CLIENT)
    protected IIcon[] oreStorageIcons;
    protected String[] oreStorageNames;
    
    public LOTRBlockOreStorageBase() {
        super(Material.iron);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    protected void setOreStorageNames(final String... names) {
        this.oreStorageNames = names;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.oreStorageIcons = new IIcon[this.oreStorageNames.length];
        for (int i = 0; i < this.oreStorageNames.length; ++i) {
            this.oreStorageIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.oreStorageNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.oreStorageNames.length) {
            j = 0;
        }
        return this.oreStorageIcons[j];
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.oreStorageNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public boolean isBeaconBase(final IBlockAccess world, final int i, final int j, final int k, final int beaconX, final int beaconY, final int beaconZ) {
        return true;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
}
