// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemTrollStatue extends Item
{
    public LOTRItemTrollStatue() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, int l, final float f, final float f1, final float f2) {
        final Block block = world.getBlock(i, j, k);
        if (block == Blocks.snow_layer) {
            l = 1;
        }
        else if (!block.isReplaceable((IBlockAccess)world, i, j, k)) {
            if (l == 0) {
                --j;
            }
            if (l == 1) {
                ++j;
            }
            if (l == 2) {
                --k;
            }
            if (l == 3) {
                ++k;
            }
            if (l == 4) {
                --i;
            }
            if (l == 5) {
                ++i;
            }
        }
        if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
            return false;
        }
        if (world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) && !world.isClient) {
            final LOTREntityStoneTroll trollStatue = new LOTREntityStoneTroll(world);
            trollStatue.setLocationAndAngles(i + (double)f, (double)j, k + (double)f2, 180.0f - ((Entity)entityplayer).rotationYaw % 360.0f, 0.0f);
            if (world.checkNoEntityCollision(trollStatue.boundingBox) && world.getCollidingBoundingBoxes((Entity)trollStatue, trollStatue.boundingBox).size() == 0 && !world.isAnyLiquid(trollStatue.boundingBox)) {
                trollStatue.setTrollOutfit(itemstack.getItemDamage());
                if (itemstack.hasTagCompound()) {
                    trollStatue.setHasTwoHeads(itemstack.getTagCompound().getBoolean("TwoHeads"));
                }
                trollStatue.placedByPlayer = true;
                world.spawnEntityInWorld((Entity)trollStatue);
                world.playSoundAtEntity((Entity)trollStatue, Blocks.stone.stepSound.func_150496_b(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getFrequency() * 0.8f);
                --itemstack.stackSize;
                return true;
            }
            trollStatue.setDead();
        }
        return false;
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        if (itemstack.hasTagCompound()) {
            final boolean twoHeads = itemstack.getTagCompound().getBoolean("TwoHeads");
            if (twoHeads) {
                list.add(StatCollector.translateToLocal("item.lotr.trollStatue.twoHeads"));
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j <= 2; ++j) {
            ItemStack statue = new ItemStack(item, 1, j);
            list.add(statue);
            statue = statue.copy();
            statue.setTagCompound(new NBTTagCompound());
            statue.getTagCompound().setBoolean("TwoHeads", true);
            list.add(statue);
        }
    }
}
