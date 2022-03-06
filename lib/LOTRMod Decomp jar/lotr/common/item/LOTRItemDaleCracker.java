// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.Entity;
import lotr.common.LOTRCommonProxy;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.item.EnumAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemDaleCracker extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] crackerIcons;
    private String[] crackerNames;
    private static int emptyMeta;
    public static final int CUSTOM_CAPACITY = 3;
    
    public LOTRItemDaleCracker() {
        this.crackerNames = new String[] { "red", "blue", "green", "silver", "gold" };
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    public static boolean isEmpty(final ItemStack itemstack) {
        return (itemstack.getItemDamage() & LOTRItemDaleCracker.emptyMeta) == LOTRItemDaleCracker.emptyMeta;
    }
    
    public static ItemStack setEmpty(final ItemStack itemstack, final boolean flag) {
        int i = itemstack.getItemDamage();
        if (flag) {
            i |= LOTRItemDaleCracker.emptyMeta;
        }
        else {
            i &= ~LOTRItemDaleCracker.emptyMeta;
        }
        itemstack.setItemDamage(i);
        return itemstack;
    }
    
    private static int getBaseCrackerMetadata(final ItemStack itemstack) {
        return getBaseCrackerMetadata(itemstack.getItemDamage());
    }
    
    private static int getBaseCrackerMetadata(final int i) {
        return i & ~LOTRItemDaleCracker.emptyMeta;
    }
    
    public static String getSealingPlayerName(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SealingPlayer")) {
            return itemstack.getTagCompound().getString("SealingPlayer");
        }
        return null;
    }
    
    public static void setSealingPlayerName(final ItemStack itemstack, final String name) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        if (name == null) {
            itemstack.getTagCompound().removeTag("SealingPlayer");
        }
        else {
            itemstack.getTagCompound().setString("SealingPlayer", name);
        }
    }
    
    public static IInventory loadCustomCrackerContents(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("CustomCracker")) {
            final NBTTagCompound invData = itemstack.getTagCompound().getCompoundTag("CustomCracker");
            final int size = invData.getInteger("Size");
            final IInventory inv = (IInventory)new InventoryBasic("cracker", false, size);
            final NBTTagList items = invData.getTagList("Items", 10);
            for (int i = 0; i < items.tagCount(); ++i) {
                final NBTTagCompound itemData = items.getCompoundTagAt(i);
                final int slot = itemData.getByte("Slot");
                if (slot >= 0 && slot < inv.getSizeInventory()) {
                    inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
                }
            }
            return inv;
        }
        return null;
    }
    
    public static void setCustomCrackerContents(final ItemStack itemstack, final IInventory inv) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        if (inv == null) {
            itemstack.getTagCompound().removeTag("CustomCracker");
        }
        else {
            final NBTTagCompound invData = new NBTTagCompound();
            final int size = inv.getSizeInventory();
            invData.setInteger("Size", size);
            final NBTTagList items = new NBTTagList();
            for (int i = 0; i < inv.getSizeInventory(); ++i) {
                final ItemStack invItem = inv.getStackInSlot(i);
                if (invItem != null) {
                    final NBTTagCompound itemData = new NBTTagCompound();
                    itemData.setByte("Slot", (byte)i);
                    invItem.writeToNBT(itemData);
                    items.appendTag((NBTBase)itemData);
                }
            }
            invData.setTag("Items", (NBTBase)items);
            itemstack.getTagCompound().setTag("CustomCracker", (NBTBase)invData);
        }
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        if (isEmpty(itemstack)) {
            final String name = super.getItemStackDisplayName(itemstack);
            return StatCollector.translateToLocalFormatted("item.lotr.cracker.empty", new Object[] { name });
        }
        return super.getItemStackDisplayName(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        if (!isEmpty(itemstack)) {
            String name = getSealingPlayerName(itemstack);
            if (name == null) {
                name = StatCollector.translateToLocal("item.lotr.cracker.sealedByDale");
            }
            list.add(StatCollector.translateToLocalFormatted("item.lotr.cracker.sealedBy", new Object[] { name }));
        }
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 40;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!isEmpty(itemstack)) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        else {
            final LOTRMod instance = LOTRMod.instance;
            final LOTRCommonProxy proxy = LOTRMod.proxy;
            entityplayer.openGui((Object)instance, 48, world, 0, 0, 0);
        }
        return itemstack;
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (isEmpty(itemstack)) {
            return itemstack;
        }
        if (!entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
            }
        }
        world.playSoundAtEntity((Entity)entityplayer, "fireworks.blast", 1.0f, 0.9f + world.rand.nextFloat() * 0.1f);
        if (!world.isClient) {
            IInventory crackerItems = null;
            final IInventory customItems = loadCustomCrackerContents(itemstack);
            if (customItems != null) {
                crackerItems = customItems;
            }
            else {
                int amount = 1;
                if (world.rand.nextInt(3) == 0) {
                    ++amount;
                }
                if (LOTRMod.isChristmas()) {
                    amount += 1 + world.rand.nextInt(4);
                }
                crackerItems = (IInventory)new InventoryBasic("cracker", true, amount);
                LOTRChestContents.fillInventory(crackerItems, world.rand, LOTRChestContents.DALE_CRACKER, amount);
            }
            for (int l = 0; l < crackerItems.getSizeInventory(); ++l) {
                final ItemStack loot = crackerItems.getStackInSlot(l);
                if (!entityplayer.inventory.addItemStackToInventory(loot)) {
                    entityplayer.dropPlayerItemWithRandomChoice(loot, false);
                }
            }
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.openDaleCracker);
            return entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem);
        }
        return itemstack;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        i = getBaseCrackerMetadata(i);
        if (i >= this.crackerIcons.length) {
            i = 0;
        }
        return this.crackerIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.crackerIcons = new IIcon[this.crackerNames.length];
        for (int i = 0; i < this.crackerNames.length; ++i) {
            this.crackerIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.crackerNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.crackerNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
            list.add(setEmpty(new ItemStack(item, 1, i), true));
        }
    }
    
    static {
        LOTRItemDaleCracker.emptyMeta = 4096;
    }
}
