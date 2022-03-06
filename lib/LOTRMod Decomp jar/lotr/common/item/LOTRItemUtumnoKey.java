// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.LOTRDimension;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemUtumnoKey extends Item
{
    @SideOnly(Side.CLIENT)
    private static IIcon[] keyIcons;
    private static String[] keyTypes;
    
    public LOTRItemUtumnoKey() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public static ItemStack getRandomKeyPart(final Random rand) {
        final ItemStack itemstack = new ItemStack(LOTRMod.utumnoKey);
        itemstack.setItemDamage(MathHelper.getRandomIntegerInRange(rand, 2, LOTRItemUtumnoKey.keyTypes.length - 1));
        return itemstack;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (LOTRDimension.getCurrentDimension(world) != LOTRDimension.UTUMNO || side != 1) {
            return false;
        }
        final Block block = world.getBlock(i, j, k);
        final int meta = world.getBlockMetadata(i, j, k);
        final LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
        LOTRUtumnoLevel keyUsageLevel = null;
        if (itemstack.getItemDamage() == 0) {
            keyUsageLevel = LOTRUtumnoLevel.ICE;
        }
        else if (itemstack.getItemDamage() == 1) {
            keyUsageLevel = LOTRUtumnoLevel.OBSIDIAN;
        }
        if (utumnoLevel == keyUsageLevel && j < utumnoLevel.corridorBaseLevels[0] && block.isOpaqueCube()) {
            if (!world.isClient) {
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.explode", 2.0f, 0.2f + world.rand.nextFloat() * 0.2f);
            }
            for (int l = 0; l < 60; ++l) {
                world.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + meta, i + 0.5 + world.rand.nextGaussian() * 1.0, j + 1.5, k + 0.5 + world.rand.nextGaussian() * 1.0, 0.0, 0.0, 0.0);
            }
            if (!world.isClient) {
                final LOTRUtumnoLevel targetLevel = LOTRUtumnoLevel.values()[keyUsageLevel.ordinal() + 1];
                int stair = 0;
                int stairX = i - 1;
                int stairZ = k - 1;
                int stairY = j;
                while (stairY >= targetLevel.corridorBaseLevels[0]) {
                    if (LOTRUtumnoLevel.forY(stairY) == targetLevel && world.isAirBlock(stairX, stairY + 1, stairZ) && World.doesBlockHaveSolidTopSurface((IBlockAccess)world, stairX, stairY, stairZ)) {
                        break;
                    }
                    if (stair == 0 || stair == 2 || stair == 4 || stair == 6) {
                        world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickBlock, keyUsageLevel.brickMeta, 3);
                    }
                    else if (stair == 1) {
                        world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 1, 3);
                    }
                    else if (stair == 3) {
                        world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 3, 3);
                    }
                    else if (stair == 5) {
                        world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 0, 3);
                    }
                    else if (stair == 7) {
                        world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 2, 3);
                    }
                    for (int j2 = 1; j2 <= 3; ++j2) {
                        world.setBlock(stairX, stairY + j2, stairZ, Blocks.air, 0, 3);
                    }
                    world.setBlock(i, stairY, k, keyUsageLevel.brickGlowBlock, keyUsageLevel.brickGlowMeta, 3);
                    if (stair <= 1) {
                        ++stairX;
                    }
                    else if (stair <= 3) {
                        ++stairZ;
                    }
                    else if (stair <= 5) {
                        --stairX;
                    }
                    else if (stair <= 7) {
                        --stairZ;
                    }
                    if (stair % 2 == 1) {
                        --stairY;
                    }
                    stair = ++stair % 8;
                }
            }
            --itemstack.stackSize;
            return true;
        }
        for (int l = 0; l < 8; ++l) {
            final double d = i + (double)world.rand.nextFloat();
            final double d2 = j + 1.0;
            final double d3 = k + (double)world.rand.nextFloat();
            world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= LOTRItemUtumnoKey.keyIcons.length) {
            i = 0;
        }
        return LOTRItemUtumnoKey.keyIcons[i];
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        LOTRItemUtumnoKey.keyIcons = new IIcon[LOTRItemUtumnoKey.keyTypes.length];
        for (int i = 0; i < LOTRItemUtumnoKey.keyTypes.length; ++i) {
            LOTRItemUtumnoKey.keyIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemUtumnoKey.keyTypes[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < LOTRItemUtumnoKey.keyTypes.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    static {
        LOTRItemUtumnoKey.keyTypes = new String[] { "ice", "obsidian", "ice0", "ice1", "ice2", "obsidian0", "obsidian1", "obsidian2" };
    }
}
