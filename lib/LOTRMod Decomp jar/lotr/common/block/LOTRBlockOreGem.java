// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockOreGem extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] oreIcons;
    private String[] oreNames;
    
    public LOTRBlockOreGem() {
        super(Material.rock);
        this.oreNames = new String[] { "topaz", "amethyst", "sapphire", "ruby", "amber", "diamond", "opal", "emerald" };
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.oreIcons = new IIcon[this.oreNames.length];
        for (int i = 0; i < this.oreNames.length; ++i) {
            this.oreIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.oreNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.oreNames.length) {
            j = 0;
        }
        return this.oreIcons[j];
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        if (i == 0) {
            return LOTRMod.topaz;
        }
        if (i == 1) {
            return LOTRMod.amethyst;
        }
        if (i == 2) {
            return LOTRMod.sapphire;
        }
        if (i == 3) {
            return LOTRMod.ruby;
        }
        if (i == 4) {
            return LOTRMod.amber;
        }
        if (i == 5) {
            return LOTRMod.diamond;
        }
        if (i == 6) {
            return LOTRMod.opal;
        }
        if (i == 7) {
            return LOTRMod.emerald;
        }
        return Item.getItemFromBlock((Block)this);
    }
    
    public int quantityDropped(final Random random) {
        return 1 + random.nextInt(2);
    }
    
    public int quantityDroppedWithBonus(final int i, final Random random) {
        if (i > 0 && Item.getItemFromBlock((Block)this) != this.getItemDropped(0, random, i)) {
            int drops = this.quantityDropped(random);
            drops += random.nextInt(i + 1);
            return drops;
        }
        return this.quantityDropped(random);
    }
    
    public void dropBlockAsItemWithChance(final World world, final int i, final int j, final int k, final int meta, final float f, final int fortune) {
        super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
        if (this.getItemDropped(meta, world.rand, fortune) != Item.getItemFromBlock((Block)this)) {
            final int amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            this.dropXpOnBlockBreak(world, i, j, k, amountXp);
        }
    }
    
    public int getDamageValue(final World world, final int i, final int j, final int k) {
        return world.getBlockMetadata(i, j, k);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.oreNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
