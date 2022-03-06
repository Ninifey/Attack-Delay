// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.util.StatCollector;
import lotr.common.inventory.LOTRInventoryPouch;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerPouch;
import net.minecraft.client.renderer.texture.IIconRegister;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemPouch extends Item
{
    private static int POUCH_COLOR;
    @SideOnly(Side.CLIENT)
    private IIcon[] pouchIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] pouchIconsOpen;
    @SideOnly(Side.CLIENT)
    private IIcon[] overlayIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] overlayIconsOpen;
    private static String[] pouchTypes;
    
    public LOTRItemPouch() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.openGui((Object)LOTRMod.instance, 15, world, 0, 0, 0);
        return itemstack;
    }
    
    public static int getCapacity(final ItemStack itemstack) {
        return getCapacityForMeta(itemstack.getItemDamage());
    }
    
    public static int getCapacityForMeta(final int i) {
        return (i + 1) * 9;
    }
    
    public static int getMaxPouchCapacity() {
        return getCapacityForMeta(LOTRItemPouch.pouchTypes.length - 1);
    }
    
    public static int getRandomPouchSize(final Random random) {
        final float f = random.nextFloat();
        if (f < 0.6f) {
            return 0;
        }
        if (f < 0.9f) {
            return 1;
        }
        return 2;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.pouchIcons = new IIcon[LOTRItemPouch.pouchTypes.length];
        this.pouchIconsOpen = new IIcon[LOTRItemPouch.pouchTypes.length];
        this.overlayIcons = new IIcon[LOTRItemPouch.pouchTypes.length];
        this.overlayIconsOpen = new IIcon[LOTRItemPouch.pouchTypes.length];
        for (int i = 0; i < LOTRItemPouch.pouchTypes.length; ++i) {
            this.pouchIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemPouch.pouchTypes[i]);
            this.pouchIconsOpen[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemPouch.pouchTypes[i] + "_open");
            this.overlayIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemPouch.pouchTypes[i] + "_overlay");
            this.overlayIconsOpen[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemPouch.pouchTypes[i] + "_open_overlay");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public int getRenderPasses(final int meta) {
        return 2;
    }
    
    public IIcon getIcon(final ItemStack itemstack, final int pass) {
        boolean open = false;
        final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
        if (entityplayer != null) {
            final Container container = entityplayer.openContainer;
            if (container instanceof LOTRContainerPouch && itemstack == entityplayer.getHeldItem()) {
                open = true;
            }
        }
        int meta = itemstack.getItemDamage();
        if (meta >= this.pouchIcons.length) {
            meta = 0;
        }
        if (open) {
            return (pass > 0) ? this.overlayIconsOpen[meta] : this.pouchIconsOpen[meta];
        }
        return (pass > 0) ? this.overlayIcons[meta] : this.pouchIcons[meta];
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        if (pass == 0) {
            return getPouchColor(itemstack);
        }
        return 16777215;
    }
    
    public static int getPouchColor(final ItemStack itemstack) {
        final int dye = getSavedDyeColor(itemstack);
        if (dye != -1) {
            return dye;
        }
        return LOTRItemPouch.POUCH_COLOR;
    }
    
    private static int getSavedDyeColor(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PouchColor")) {
            return itemstack.getTagCompound().getInteger("PouchColor");
        }
        return -1;
    }
    
    public static boolean isPouchDyed(final ItemStack itemstack) {
        return getSavedDyeColor(itemstack) != -1;
    }
    
    public static void setPouchColor(final ItemStack itemstack, final int i) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("PouchColor", i);
    }
    
    public static void removePouchDye(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null) {
            itemstack.getTagCompound().removeTag("PouchColor");
        }
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final int slots = getCapacity(itemstack);
        int slotsFull = 0;
        final LOTRInventoryPouch pouchInv = new LOTRInventoryPouch(itemstack);
        for (int i = 0; i < pouchInv.getSizeInventory(); ++i) {
            final ItemStack slotItem = pouchInv.getStackInSlot(i);
            if (slotItem != null) {
                ++slotsFull;
            }
        }
        list.add(StatCollector.translateToLocalFormatted("item.lotr.pouch.slots", new Object[] { slotsFull, slots }));
        if (isPouchDyed(itemstack)) {
            list.add(StatCollector.translateToLocal("item.lotr.pouch.dyed"));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < LOTRItemPouch.pouchTypes.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public static boolean tryAddItemToPouch(final ItemStack pouch, final ItemStack itemstack, final boolean requireMatchInPouch) {
        if (itemstack != null && itemstack.stackSize > 0) {
            final LOTRInventoryPouch pouchInv = new LOTRInventoryPouch(pouch);
            for (int i = 0; i < pouchInv.getSizeInventory() && itemstack.stackSize > 0; ++i) {
                final ItemStack itemInSlot = pouchInv.getStackInSlot(i);
                if (itemInSlot == null) {
                    if (requireMatchInPouch) {
                        continue;
                    }
                }
                else {
                    if (itemInSlot.stackSize >= itemInSlot.getMaxStackSize()) {
                        continue;
                    }
                    if (itemInSlot.getItem() != itemstack.getItem()) {
                        continue;
                    }
                    if (!itemInSlot.isStackable()) {
                        continue;
                    }
                    if (itemInSlot.getHasSubtypes() && itemInSlot.getItemDamage() != itemstack.getItemDamage()) {
                        continue;
                    }
                    if (!ItemStack.areItemStackTagsEqual(itemInSlot, itemstack)) {
                        continue;
                    }
                }
                if (itemInSlot == null) {
                    pouchInv.setInventorySlotContents(i, itemstack);
                    return true;
                }
                int maxStackSize = itemInSlot.getMaxStackSize();
                if (pouchInv.getInventoryStackLimit() < maxStackSize) {
                    maxStackSize = pouchInv.getInventoryStackLimit();
                }
                int difference = maxStackSize - itemInSlot.stackSize;
                if (difference > itemstack.stackSize) {
                    difference = itemstack.stackSize;
                }
                itemstack.stackSize -= difference;
                final ItemStack itemStack = itemInSlot;
                itemStack.stackSize += difference;
                pouchInv.setInventorySlotContents(i, itemInSlot);
                if (itemstack.stackSize <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        LOTRItemPouch.POUCH_COLOR = 10841676;
        LOTRItemPouch.pouchTypes = new String[] { "small", "medium", "large" };
    }
}
