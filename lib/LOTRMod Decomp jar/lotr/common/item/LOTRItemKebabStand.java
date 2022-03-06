// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemKebabStand extends ItemBlock
{
    public LOTRItemKebabStand(final Block block) {
        super(block);
    }
    
    public static void setKebabData(final ItemStack itemstack, final NBTTagCompound kebabData) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setTag("LOTRKebabData", (NBTBase)kebabData);
    }
    
    public static void setKebabData(final ItemStack itemstack, final LOTRTileEntityKebabStand kebabStand) {
        if (kebabStand.shouldSaveBlockData()) {
            final NBTTagCompound kebabData = new NBTTagCompound();
            kebabStand.writeKebabStandToNBT(kebabData);
            setKebabData(itemstack, kebabData);
        }
    }
    
    public static NBTTagCompound getKebabData(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRKebabData")) {
            final NBTTagCompound kebabData = itemstack.getTagCompound().getCompoundTag("LOTRKebabData");
            return kebabData;
        }
        return null;
    }
    
    public static void loadKebabData(final ItemStack itemstack, final LOTRTileEntityKebabStand kebabStand) {
        final NBTTagCompound kebabData = getKebabData(itemstack);
        if (kebabData != null) {
            kebabStand.readKebabStandFromNBT(kebabData);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final NBTTagCompound kebabData = getKebabData(itemstack);
        if (kebabData != null) {
            final LOTRTileEntityKebabStand kebabStand = new LOTRTileEntityKebabStand();
            kebabStand.readKebabStandFromNBT(kebabData);
            final int meats = kebabStand.getMeatAmount();
            list.add(StatCollector.translateToLocalFormatted("tile.lotr.kebabStand.meats", new Object[] { meats }));
            if (kebabStand.isCooked()) {
                list.add(StatCollector.translateToLocal("tile.lotr.kebabStand.cooked"));
            }
        }
    }
}
