// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.Item;
import net.minecraft.block.BlockStem;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.IGrowable;
import net.minecraft.block.Block;

public class LOTRBlockMudGrass extends Block implements IGrowable
{
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    
    public LOTRBlockMudGrass() {
        super(Material.grass);
        this.setHardness(0.6f);
        this.setStepSound(Block.soundTypeGrass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setTickRandomly(true);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return this.iconTop;
        }
        if (i == 0) {
            return LOTRMod.mud.getBlockTextureFromSide(i);
        }
        return super.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.blockIcon = iconregister.registerIcon(this.getTextureName() + "_side");
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        Blocks.grass.updateTick(world, i, j, k, random);
    }
    
    public boolean canSustainPlant(final IBlockAccess world, final int i, final int j, final int k, final ForgeDirection direction, final IPlantable plantable) {
        return Blocks.grass.canSustainPlant(world, i, j, k, direction, plantable) || plantable instanceof BlockStem;
    }
    
    public void onPlantGrow(final World world, final int i, final int j, final int k, final int sourceX, final int sourceY, final int sourceZ) {
        world.setBlock(i, j, k, LOTRMod.mud, 0, 2);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return LOTRMod.mud.getItemDropped(0, random, j);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int i) {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return 16777215;
    }
    
    public boolean func_149851_a(final World world, final int i, final int j, final int k, final boolean flag) {
        return Blocks.grass.func_149851_a(world, i, j, k, flag);
    }
    
    public boolean func_149852_a(final World world, final Random random, final int i, final int j, final int k) {
        return Blocks.grass.func_149852_a(world, random, i, j, k);
    }
    
    public void func_149853_b(final World world, final Random random, final int i, final int j, final int k) {
        Blocks.grass.func_149853_b(world, random, i, j, k);
    }
}
