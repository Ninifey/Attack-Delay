// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.FurnaceRecipes;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public abstract class LOTRTileEntityAlloyForgeBase extends LOTRTileEntityForgeBase
{
    @Override
    public int getForgeInvSize() {
        return 13;
    }
    
    @Override
    public void setupForgeSlots() {
        super.inputSlots = new int[] { 4, 5, 6, 7 };
        super.outputSlots = new int[] { 8, 9, 10, 11 };
        super.fuelSlot = 12;
    }
    
    @Override
    protected boolean canMachineInsertInput(final ItemStack itemstack) {
        return itemstack != null && this.getSmeltingResult(itemstack) != null;
    }
    
    @Override
    public int getSmeltingDuration() {
        return 200;
    }
    
    @Override
    protected boolean canDoSmelting() {
        for (int i = 4; i < 8; ++i) {
            if (this.canSmelt(i)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected void doSmelt() {
        for (int i = 4; i < 8; ++i) {
            this.smeltItemInSlot(i);
        }
    }
    
    private boolean canSmelt(final int i) {
        if (super.inventory[i] == null) {
            return false;
        }
        if (super.inventory[i - 4] != null) {
            final ItemStack alloyResult = this.getAlloySmeltingResult(super.inventory[i], super.inventory[i - 4]);
            if (alloyResult != null) {
                if (super.inventory[i + 4] == null) {
                    return true;
                }
                final int resultSize = super.inventory[i + 4].stackSize + alloyResult.stackSize;
                if (super.inventory[i + 4].isItemEqual(alloyResult) && resultSize <= this.getInventoryStackLimit() && resultSize <= alloyResult.getMaxStackSize()) {
                    return true;
                }
            }
        }
        final ItemStack result = this.getSmeltingResult(super.inventory[i]);
        if (result == null) {
            return false;
        }
        if (super.inventory[i + 4] == null) {
            return true;
        }
        if (!super.inventory[i + 4].isItemEqual(result)) {
            return false;
        }
        final int resultSize = super.inventory[i + 4].stackSize + result.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
    }
    
    private void smeltItemInSlot(final int i) {
        if (this.canSmelt(i)) {
            boolean smeltedAlloyItem = false;
            if (super.inventory[i - 4] != null) {
                final ItemStack alloyResult = this.getAlloySmeltingResult(super.inventory[i], super.inventory[i - 4]);
                if (alloyResult != null && (super.inventory[i + 4] == null || super.inventory[i + 4].isItemEqual(alloyResult))) {
                    if (super.inventory[i + 4] == null) {
                        super.inventory[i + 4] = alloyResult.copy();
                    }
                    else if (super.inventory[i + 4].isItemEqual(alloyResult)) {
                        final ItemStack itemStack = super.inventory[i + 4];
                        itemStack.stackSize += alloyResult.stackSize;
                    }
                    final ItemStack itemStack2 = super.inventory[i];
                    --itemStack2.stackSize;
                    if (super.inventory[i].stackSize <= 0) {
                        super.inventory[i] = null;
                    }
                    final ItemStack itemStack3 = super.inventory[i - 4];
                    --itemStack3.stackSize;
                    if (super.inventory[i - 4].stackSize <= 0) {
                        super.inventory[i - 4] = null;
                    }
                    smeltedAlloyItem = true;
                }
            }
            if (!smeltedAlloyItem) {
                final ItemStack result = this.getSmeltingResult(super.inventory[i]);
                if (super.inventory[i + 4] == null) {
                    super.inventory[i + 4] = result.copy();
                }
                else if (super.inventory[i + 4].isItemEqual(result)) {
                    final ItemStack itemStack4 = super.inventory[i + 4];
                    itemStack4.stackSize += result.stackSize;
                }
                final ItemStack itemStack5 = super.inventory[i];
                --itemStack5.stackSize;
                if (super.inventory[i].stackSize <= 0) {
                    super.inventory[i] = null;
                }
            }
        }
    }
    
    public ItemStack getSmeltingResult(final ItemStack itemstack) {
        boolean isStoneMaterial = false;
        final Item item = itemstack.getItem();
        final Block block = Block.getBlockFromItem(item);
        if (block != null && block != Blocks.air) {
            final Material material = block.getMaterial();
            if (material == Material.rock || material == Material.sand || material == Material.field_151571_B) {
                isStoneMaterial = true;
            }
        }
        else if (item == Items.clay_ball || item == LOTRMod.clayMug || item == LOTRMod.clayPlate || item == LOTRMod.ceramicPlate) {
            isStoneMaterial = true;
        }
        if (isStoneMaterial || this.isWood(itemstack)) {
            return FurnaceRecipes.smelting().func_151395_a(itemstack);
        }
        return null;
    }
    
    protected ItemStack getAlloySmeltingResult(final ItemStack itemstack, final ItemStack alloyItem) {
        if ((this.isCopper(itemstack) && this.isTin(alloyItem)) || (this.isTin(itemstack) && this.isCopper(alloyItem))) {
            return new ItemStack(LOTRMod.bronze, 2);
        }
        return null;
    }
    
    protected boolean isCopper(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreCopper") || LOTRMod.isOreNameEqual(itemstack, "ingotCopper");
    }
    
    protected boolean isTin(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreTin") || LOTRMod.isOreNameEqual(itemstack, "ingotTin");
    }
    
    protected boolean isIron(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreIron") || LOTRMod.isOreNameEqual(itemstack, "ingotIron");
    }
    
    protected boolean isGold(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreGold") || LOTRMod.isOreNameEqual(itemstack, "ingotGold");
    }
    
    protected boolean isGoldNugget(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "nuggetGold");
    }
    
    protected boolean isSilver(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreSilver") || LOTRMod.isOreNameEqual(itemstack, "ingotSilver");
    }
    
    protected boolean isSilverNugget(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "nuggetSilver");
    }
    
    protected boolean isMithril(final ItemStack itemstack) {
        return itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMithril) || itemstack.getItem() == LOTRMod.mithril;
    }
    
    protected boolean isMithrilNugget(final ItemStack itemstack) {
        return itemstack.getItem() == LOTRMod.mithrilNugget;
    }
    
    protected boolean isOrcSteel(final ItemStack itemstack) {
        return itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMorgulIron) || itemstack.getItem() == LOTRMod.orcSteel;
    }
    
    protected boolean isWood(final ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "logWood");
    }
    
    protected boolean isCoal(final ItemStack itemstack) {
        return itemstack.getItem() == Items.coal;
    }
}
