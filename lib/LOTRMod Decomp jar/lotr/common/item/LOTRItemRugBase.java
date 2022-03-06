// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public abstract class LOTRItemRugBase extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] rugIcons;
    private String[] rugNames;
    
    public LOTRItemRugBase(final String... names) {
        this.rugNames = names;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.rugIcons.length) {
            i = 0;
        }
        return this.rugIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.rugIcons = new IIcon[this.rugNames.length];
        for (int i = 0; i < this.rugIcons.length; ++i) {
            this.rugIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.rugNames[i]);
        }
    }
    
    protected abstract LOTREntityRugBase createRug(final World p0, final ItemStack p1);
    
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
            final LOTREntityRugBase rug = this.createRug(world, itemstack);
            rug.setLocationAndAngles((double)(i + f), (double)j, (double)(k + f2), 180.0f - ((Entity)entityplayer).rotationYaw % 360.0f, 0.0f);
            if (world.checkNoEntityCollision(rug.boundingBox) && world.getCollidingBoundingBoxes((Entity)rug, rug.boundingBox).size() == 0 && !world.isAnyLiquid(rug.boundingBox)) {
                world.spawnEntityInWorld((Entity)rug);
                world.playSoundAtEntity((Entity)rug, Blocks.wool.stepSound.func_150496_b(), (Blocks.wool.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.wool.stepSound.getFrequency() * 0.8f);
                --itemstack.stackSize;
                return true;
            }
            rug.setDead();
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.rugNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
