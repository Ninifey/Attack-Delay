// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTREntJarRecipes;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemEntDraught;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public class LOTRBlockEntJar extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon[] jarIcons;
    
    public LOTRBlockEntJar() {
        super(Material.field_151571_B);
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.875f, 0.75f);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityEntJar();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getEntJarRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return (i == 0 || i == 1) ? this.jarIcons[0] : this.jarIcons[1];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.jarIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.jarIcons[1] = iconregister.registerIcon(this.getTextureName() + "_side");
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return this.canBlockStay(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getHeldItem();
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityEntJar) {
            final LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar)tileentity;
            if (itemstack != null && itemstack.getItem() instanceof LOTRItemEntDraught && jar.fillFromBowl(itemstack)) {
                if (!entityplayer.capabilities.isCreativeMode) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bowl));
                }
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                return true;
            }
            if (jar.drinkMeta >= 0) {
                final ItemStack drink = new ItemStack(LOTRMod.entDraught, 1, jar.drinkMeta);
                if (itemstack != null && itemstack.getItem() == Items.bowl) {
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = itemstack;
                        --itemStack.stackSize;
                    }
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, drink.copy());
                    }
                    else if (!entityplayer.inventory.addItemStackToInventory(drink.copy())) {
                        entityplayer.dropPlayerItemWithRandomChoice(drink.copy(), false);
                    }
                    world.playSoundAtEntity((Entity)entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                    jar.consume();
                    return true;
                }
            }
            else if (itemstack != null) {
                final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                if (biome instanceof LOTRBiomeGenFangorn && jar.drinkAmount > 0) {
                    final ItemStack draught = LOTREntJarRecipes.findMatchingRecipe(itemstack);
                    if (draught != null) {
                        jar.drinkMeta = draught.getItemDamage();
                        if (!entityplayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack2 = itemstack;
                            --itemStack2.stackSize;
                        }
                        if (!world.isClient) {
                            world.playAuxSFX(2005, i, j, k, 0);
                        }
                        return true;
                    }
                }
                if (jar.drinkAmount > 0) {
                    if (this.tryTakeWaterFromJar(jar, world, entityplayer, new ItemStack(Items.bucket), new ItemStack(Items.water_bucket), LOTRTileEntityEntJar.MAX_CAPACITY)) {
                        return true;
                    }
                    for (final LOTRItemMug.Vessel vessel : LOTRItemMug.Vessel.values()) {
                        final ItemStack vesselEmpty = vessel.getEmptyVessel();
                        final ItemStack vesselFull = new ItemStack(LOTRMod.mugWater);
                        LOTRItemMug.setVessel(vesselFull, vessel, true);
                        if (this.tryTakeWaterFromJar(jar, world, entityplayer, vesselEmpty, vesselFull, 1)) {
                            return true;
                        }
                    }
                }
                if (jar.drinkAmount < LOTRTileEntityEntJar.MAX_CAPACITY) {
                    if (this.tryAddWaterToJar(jar, world, entityplayer, new ItemStack(Items.water_bucket), new ItemStack(Items.bucket), LOTRTileEntityEntJar.MAX_CAPACITY)) {
                        return true;
                    }
                    for (final LOTRItemMug.Vessel vessel : LOTRItemMug.Vessel.values()) {
                        final ItemStack vesselEmpty = vessel.getEmptyVessel();
                        final ItemStack vesselFull = new ItemStack(LOTRMod.mugWater);
                        LOTRItemMug.setVessel(vesselFull, vessel, true);
                        if (this.tryAddWaterToJar(jar, world, entityplayer, vesselFull, vesselEmpty, 1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean tryTakeWaterFromJar(final LOTRTileEntityEntJar jar, final World world, final EntityPlayer entityplayer, final ItemStack container, final ItemStack filled, final int amount) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack.getItem() != container.getItem() || itemstack.getItemDamage() != container.getItemDamage()) {
            return false;
        }
        for (int i = 0; i < amount; ++i) {
            jar.consume();
        }
        world.playSoundAtEntity((Entity)entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
        if (!entityplayer.capabilities.isCreativeMode) {
            final ItemStack itemStack = itemstack;
            --itemStack.stackSize;
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, filled.copy());
            }
            else if (!entityplayer.inventory.addItemStackToInventory(filled.copy())) {
                entityplayer.dropPlayerItemWithRandomChoice(filled.copy(), false);
            }
        }
        return true;
    }
    
    private boolean tryAddWaterToJar(final LOTRTileEntityEntJar jar, final World world, final EntityPlayer entityplayer, final ItemStack filled, final ItemStack container, final int amount) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack.getItem() != filled.getItem() || itemstack.getItemDamage() != filled.getItemDamage()) {
            return false;
        }
        for (int i = 0; i < amount; ++i) {
            jar.fillWithWater();
        }
        world.playSoundAtEntity((Entity)entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
        if (!entityplayer.capabilities.isCreativeMode) {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, container.copy());
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(4) == 0) {
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity instanceof LOTRTileEntityEntJar) {
                final LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar)tileentity;
                if (jar.drinkMeta >= 0) {
                    final double d = i + 0.25 + random.nextFloat() * 0.5f;
                    final double d2 = j + 1.0;
                    final double d3 = k + 0.25 + random.nextFloat() * 0.5f;
                    world.spawnParticle("happyVillager", d, d2, d3, 0.0, 0.2, 0.0);
                }
            }
        }
    }
}
