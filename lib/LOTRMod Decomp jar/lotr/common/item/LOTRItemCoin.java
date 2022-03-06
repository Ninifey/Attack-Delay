// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemCoin extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] coinIcons;
    public static int[] values;
    
    public LOTRItemCoin() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
    
    public static int getValue(final ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemCoin) {
            int i = itemstack.getItemDamage();
            if (i >= LOTRItemCoin.values.length) {
                i = 0;
            }
            return LOTRItemCoin.values[i];
        }
        return 0;
    }
    
    public static int getStackValue(final ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        return getValue(itemstack) * itemstack.stackSize;
    }
    
    public static int getInventoryValue(final EntityPlayer entityplayer) {
        int coins = 0;
        for (final ItemStack itemstack : entityplayer.inventory.mainInventory) {
            coins += getStackValue(itemstack);
        }
        coins += getStackValue(entityplayer.inventory.getItemStack());
        return coins;
    }
    
    public static int getContainerValue(final IInventory inv) {
        if (inv instanceof InventoryPlayer) {
            return getInventoryValue(((InventoryPlayer)inv).player);
        }
        int coins = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            coins += getStackValue(itemstack);
        }
        return coins;
    }
    
    public static void takeCoins(int coins, final EntityPlayer entityplayer) {
        final InventoryPlayer inv = entityplayer.inventory;
        final int invValue = getInventoryValue(entityplayer);
        if (invValue < coins) {
            FMLLog.warning("Attempted to take " + coins + " coins from player " + entityplayer.getCommandSenderName() + " who has only " + invValue, new Object[0]);
        }
        final int initCoins = coins;
    Label_0229:
        for (int i = LOTRItemCoin.values.length - 1; i >= 0; --i) {
            final int value = LOTRItemCoin.values[i];
            if (value <= initCoins) {
                final ItemStack coin = new ItemStack(LOTRMod.silverCoin, 1, i);
                for (int slot = -1; slot < inv.mainInventory.length; ++slot) {
                    ItemStack itemstack;
                    while ((itemstack = ((slot == -1) ? inv.getItemStack() : inv.mainInventory[slot])) != null && itemstack.isItemEqual(coin)) {
                        if (slot == -1) {
                            final ItemStack is = inv.getItemStack();
                            if (is != null) {
                                final ItemStack itemStack = is;
                                --itemStack.stackSize;
                                if (is.stackSize <= 0) {
                                    inv.setItemStack((ItemStack)null);
                                }
                            }
                        }
                        else {
                            inv.decrStackSize(slot, 1);
                        }
                        coins -= value;
                        if (coins < value) {
                            continue Label_0229;
                        }
                    }
                }
            }
        }
        if (coins > 0) {
            for (int i = 0; i < LOTRItemCoin.values.length; ++i) {
                if (i != 0) {
                    final int value = LOTRItemCoin.values[i];
                    final ItemStack coin = new ItemStack(LOTRMod.silverCoin, 1, i);
                Label_0401:
                    for (int slot = -1; slot < inv.mainInventory.length; ++slot) {
                        ItemStack itemstack;
                        while ((itemstack = ((slot == -1) ? inv.getItemStack() : inv.mainInventory[slot])) != null && itemstack.isItemEqual(coin)) {
                            if (slot == -1) {
                                final ItemStack is = inv.getItemStack();
                                if (is != null) {
                                    final ItemStack itemStack2 = is;
                                    --itemStack2.stackSize;
                                    if (is.stackSize <= 0) {
                                        inv.setItemStack((ItemStack)null);
                                    }
                                }
                            }
                            else {
                                inv.decrStackSize(slot, 1);
                            }
                            coins -= value;
                            if (coins < 0) {
                                break Label_0401;
                            }
                        }
                    }
                    if (coins < 0) {
                        break;
                    }
                }
            }
        }
        if (coins < 0) {
            giveCoins(-coins, entityplayer);
        }
    }
    
    public static void giveCoins(int coins, final EntityPlayer entityplayer) {
        final InventoryPlayer inv = entityplayer.inventory;
        if (coins <= 0) {
            FMLLog.warning("Attempted to give a non-positive value of coins " + coins + " to player " + entityplayer.getCommandSenderName(), new Object[0]);
        }
        for (int i = LOTRItemCoin.values.length - 1; i >= 0; --i) {
            final int value = LOTRItemCoin.values[i];
            for (ItemStack coin = new ItemStack(LOTRMod.silverCoin, 1, i); coins >= value && inv.addItemStackToInventory(coin.copy()); coins -= value) {}
        }
        if (coins > 0) {
            for (int i = LOTRItemCoin.values.length - 1; i >= 0; --i) {
                final int value = LOTRItemCoin.values[i];
                final ItemStack coin = new ItemStack(LOTRMod.silverCoin, 1, i);
                while (coins >= value) {
                    entityplayer.dropPlayerItemWithRandomChoice(coin.copy(), false);
                    coins -= value;
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.coinIcons.length) {
            i = 0;
        }
        return this.coinIcons[i];
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if (i >= LOTRItemCoin.values.length) {
            i = 0;
        }
        return super.getUnlocalizedName() + "." + LOTRItemCoin.values[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.coinIcons = new IIcon[LOTRItemCoin.values.length];
        for (int i = 0; i < LOTRItemCoin.values.length; ++i) {
            this.coinIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemCoin.values[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < LOTRItemCoin.values.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    static {
        LOTRItemCoin.values = new int[] { 1, 10, 100 };
    }
}
