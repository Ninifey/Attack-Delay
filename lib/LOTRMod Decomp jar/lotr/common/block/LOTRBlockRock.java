// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockRock extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] rockIcons;
    private String[] rockNames;
    
    public LOTRBlockRock() {
        super(Material.rock);
        this.rockNames = new String[] { "mordor", "gondor", "rohan", "blue", "red", "chalk" };
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(1.5f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public boolean isReplaceableOreGen(final World world, final int i, final int j, final int k, final Block target) {
        return target == this && world.getBlockMetadata(i, j, k) == 0;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.rockNames.length) {
            j = 0;
        }
        return this.rockIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.rockIcons = new IIcon[this.rockNames.length];
        for (int i = 0; i < this.rockNames.length; ++i) {
            this.rockIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.rockNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.rockNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == 0 && random.nextInt(10) == 0) {
            world.spawnParticle("smoke", (double)(i + random.nextFloat()), (double)(j + 1.1f), (double)(k + random.nextFloat()), 0.0, 0.0, 0.0);
        }
    }
}
