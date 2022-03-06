// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipePoisonWeapon implements IRecipe
{
    private Item inputItem;
    private Item resultItem;
    private Object catalystObj;
    public static Map<Item, Item> inputToPoisoned;
    public static Map<Item, Item> poisonedToInput;
    
    public LOTRRecipePoisonWeapon(final Item item1, final Item item2) {
        this(item1, item2, "poison");
        LOTRRecipePoisonWeapon.inputToPoisoned.put(item1, item2);
        LOTRRecipePoisonWeapon.poisonedToInput.put(item2, item1);
    }
    
    public LOTRRecipePoisonWeapon(final Item item1, final Item item2, final Object cat) {
        this.inputItem = item1;
        this.resultItem = item2;
        this.catalystObj = cat;
    }
    
    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack weapon = null;
        ItemStack catalyst = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == this.inputItem) {
                    if (weapon != null) {
                        return false;
                    }
                    weapon = itemstack;
                }
                else {
                    if (!this.matchesCatalyst(itemstack)) {
                        return false;
                    }
                    if (catalyst != null) {
                        return false;
                    }
                    catalyst = itemstack;
                }
            }
        }
        return weapon != null && catalyst != null;
    }
    
    private boolean matchesCatalyst(final ItemStack itemstack) {
        if (this.catalystObj instanceof String) {
            return LOTRMod.isOreNameEqual(itemstack, (String)this.catalystObj);
        }
        if (this.catalystObj instanceof Item) {
            return itemstack.getItem() == this.catalystObj;
        }
        return this.catalystObj instanceof ItemStack && LOTRRecipes.checkItemEquals((ItemStack)this.catalystObj, itemstack);
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack weapon = null;
        ItemStack catalyst = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == this.inputItem) {
                    if (weapon != null) {
                        return null;
                    }
                    weapon = itemstack.copy();
                }
                else {
                    if (!this.matchesCatalyst(itemstack)) {
                        return null;
                    }
                    if (catalyst != null) {
                        return null;
                    }
                    catalyst = itemstack.copy();
                }
            }
        }
        if (weapon == null || catalyst == null) {
            return null;
        }
        final ItemStack result = new ItemStack(this.resultItem);
        result.setItemDamage(weapon.getItemDamage());
        if (weapon.hasTagCompound()) {
            final NBTTagCompound nbt = (NBTTagCompound)weapon.getTagCompound().copy();
            result.setTagCompound(nbt);
        }
        return result;
    }
    
    public int getRecipeSize() {
        return 2;
    }
    
    public ItemStack getRecipeOutput() {
        return new ItemStack(this.resultItem);
    }
    
    public ItemStack getInputItem() {
        return new ItemStack(this.inputItem);
    }
    
    static {
        LOTRRecipePoisonWeapon.inputToPoisoned = new HashMap<Item, Item>();
        LOTRRecipePoisonWeapon.poisonedToInput = new HashMap<Item, Item>();
    }
}
