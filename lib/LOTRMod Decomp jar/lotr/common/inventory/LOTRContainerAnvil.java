// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemCoin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemFishingRod;
import lotr.common.LOTRMod;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import net.minecraft.item.Item;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import java.util.Collection;
import lotr.common.enchant.LOTREnchantment;
import java.util.ArrayList;
import net.minecraft.enchantment.Enchantment;
import java.util.HashMap;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.item.LOTRItemEnchantment;
import lotr.common.LOTRConfig;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import org.apache.commons.lang3.StringUtils;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Container;

public class LOTRContainerAnvil extends Container
{
    public IInventory invOutput;
    public IInventory invInput;
    public final EntityPlayer thePlayer;
    public final World theWorld;
    public final boolean isTrader;
    private int xCoord;
    private int yCoord;
    private int zCoord;
    public LOTREntityNPC theNPC;
    public LOTRTradeable theTrader;
    public int materialCost;
    public int reforgeCost;
    private String repairedItemName;
    private long lastReforgeTime;
    public static final int maxReforgeTime = 40;
    public int clientReforgeTime;
    private boolean doneMischief;
    
    private LOTRContainerAnvil(final EntityPlayer entityplayer, final boolean trader) {
        this.lastReforgeTime = -1L;
        this.thePlayer = entityplayer;
        this.theWorld = ((Entity)entityplayer).worldObj;
        this.isTrader = trader;
        this.invOutput = (IInventory)new InventoryCraftResult();
        this.invInput = (IInventory)new InventoryBasic("Repair", true, this.isTrader ? 2 : 3) {
            public void onInventoryChanged() {
                super.onInventoryChanged();
                LOTRContainerAnvil.this.onCraftMatrixChanged((IInventory)this);
            }
        };
        this.addSlotToContainer(new Slot(this.invInput, 0, 27, 58));
        this.addSlotToContainer(new Slot(this.invInput, 1, 76, 47));
        if (!this.isTrader) {
            this.addSlotToContainer(new Slot(this.invInput, 2, 76, 70));
        }
        this.addSlotToContainer((Slot)new LOTRSlotAnvilOutput(this, this.invOutput, 0, 134, 58));
        for (int j1 = 0; j1 < 3; ++j1) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, i1 + j1 * 9 + 9, 8 + i1 * 18, 108 + j1 * 18));
            }
        }
        for (int i2 = 0; i2 < 9; ++i2) {
            this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, i2, 8 + i2 * 18, 166));
        }
    }
    
    public LOTRContainerAnvil(final EntityPlayer entityplayer, final int i, final int j, final int k) {
        this(entityplayer, false);
        this.xCoord = i;
        this.yCoord = j;
        this.zCoord = k;
    }
    
    public LOTRContainerAnvil(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        this(entityplayer, true);
        this.theNPC = npc;
        this.theTrader = (LOTRTradeable)npc;
    }
    
    public void onCraftMatrixChanged(final IInventory inv) {
        super.onCraftMatrixChanged(inv);
        if (inv == this.invInput) {
            this.updateRepairOutput();
        }
    }
    
    private void updateRepairOutput() {
        final ItemStack inputItem = this.invInput.getStackInSlot(0);
        this.materialCost = 0;
        this.reforgeCost = 0;
        int baseAnvilCost = 0;
        int repairCost = 0;
        int combineCost = 0;
        int renameCost = 0;
        if (inputItem == null) {
            this.invOutput.setInventorySlotContents(0, (ItemStack)null);
            this.materialCost = 0;
        }
        else {
            ItemStack inputCopy = inputItem.copy();
            final ItemStack combinerItem = this.invInput.getStackInSlot(1);
            final ItemStack materialItem = this.isTrader ? null : this.invInput.getStackInSlot(2);
            final Map inputEnchants = EnchantmentHelper.getEnchantments(inputCopy);
            boolean enchantingWithBook = false;
            final List<LOTREnchantment> inputModifiers = LOTREnchantmentHelper.getEnchantList(inputCopy);
            baseAnvilCost = LOTREnchantmentHelper.getAnvilCost(inputItem) + ((combinerItem == null) ? 0 : LOTREnchantmentHelper.getAnvilCost(combinerItem));
            this.materialCost = 0;
            boolean nameChange = false;
            final String defaultItemName = inputItem.getItem().getItemStackDisplayName(inputItem);
            if (StringUtils.isBlank((CharSequence)this.repairedItemName) || this.repairedItemName.equals(defaultItemName)) {
                if (inputItem.hasDisplayName()) {
                    inputCopy.func_135074_t();
                    nameChange = true;
                }
            }
            else if (!this.repairedItemName.equals(inputItem.getDisplayName())) {
                inputCopy.setStackDisplayName(this.repairedItemName);
                nameChange = true;
            }
            if (nameChange) {
                boolean costRename = false;
                final Item item = inputItem.getItem();
                if (item instanceof ItemSword || item instanceof ItemTool) {
                    costRename = true;
                }
                if (item instanceof ItemArmor && ((ItemArmor)item).damageReduceAmount > 0) {
                    costRename = true;
                }
                if (item instanceof ItemBow || item instanceof LOTRItemCrossbow || item instanceof LOTRItemThrowingAxe || item instanceof LOTRItemBlowgun) {
                    costRename = true;
                }
                if (costRename) {
                    renameCost = 1;
                }
            }
            boolean combining = false;
            if (combinerItem != null) {
                enchantingWithBook = (combinerItem.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(combinerItem).tagCount() > 0);
                if (enchantingWithBook && !LOTRConfig.enchantingVanilla) {
                    this.invOutput.setInventorySlotContents(0, (ItemStack)null);
                    this.materialCost = 0;
                    return;
                }
                LOTREnchantment combinerItemEnchant = null;
                if (combinerItem.getItem() instanceof LOTRItemEnchantment) {
                    combinerItemEnchant = ((LOTRItemEnchantment)combinerItem.getItem()).theEnchant;
                }
                else if (combinerItem.getItem() instanceof LOTRItemModifierTemplate) {
                    combinerItemEnchant = LOTRItemModifierTemplate.getModifier(combinerItem);
                }
                if (!enchantingWithBook && combinerItemEnchant == null) {
                    if (!inputCopy.isItemStackDamageable() || inputCopy.getItem() != combinerItem.getItem()) {
                        this.invOutput.setInventorySlotContents(0, (ItemStack)null);
                        this.materialCost = 0;
                        return;
                    }
                    final int inputUseLeft = inputItem.getMaxDamage() - inputItem.getItemDamageForDisplay();
                    final int combinerUseLeft = combinerItem.getMaxDamage() - combinerItem.getItemDamageForDisplay();
                    final int restoredUses = combinerUseLeft + inputCopy.getMaxDamage() * 12 / 100;
                    final int newUsesLeft = inputUseLeft + restoredUses;
                    int newDamage = inputCopy.getMaxDamage() - newUsesLeft;
                    newDamage = Math.max(newDamage, 0);
                    if (newDamage < inputCopy.getItemDamage()) {
                        inputCopy.setItemDamage(newDamage);
                        final int restoredUses2 = inputCopy.getMaxDamage() - inputUseLeft;
                        final int restoredUses3 = inputCopy.getMaxDamage() - combinerUseLeft;
                        combineCost += Math.max(0, Math.min(restoredUses2, restoredUses3) / 100);
                    }
                    combining = true;
                }
                final Map outputEnchants = new HashMap(inputEnchants);
                if (LOTRConfig.enchantingVanilla) {
                    final Map combinerEnchants = EnchantmentHelper.getEnchantments(combinerItem);
                    for (final Object obj : combinerEnchants.keySet()) {
                        final int combinerEnchID = (int)obj;
                        final Enchantment combinerEnch = Enchantment.enchantmentsList[combinerEnchID];
                        int inputEnchLevel = 0;
                        if (outputEnchants.containsKey(combinerEnchID)) {
                            inputEnchLevel = outputEnchants.get(combinerEnchID);
                        }
                        int combinerEnchLevel = combinerEnchants.get(combinerEnchID);
                        int combinedEnchLevel;
                        if (inputEnchLevel == combinerEnchLevel) {
                            combinedEnchLevel = ++combinerEnchLevel;
                        }
                        else {
                            combinedEnchLevel = Math.max(combinerEnchLevel, inputEnchLevel);
                        }
                        combinerEnchLevel = combinedEnchLevel;
                        final int levelsAdded = combinerEnchLevel - inputEnchLevel;
                        boolean canApply = combinerEnch.canApply(inputItem);
                        if (this.thePlayer.capabilities.isCreativeMode || inputItem.getItem() == Items.enchanted_book) {
                            canApply = true;
                        }
                        for (final Object objIn : outputEnchants.keySet()) {
                            final int inputEnchID = (int)objIn;
                            final Enchantment inputEnch = Enchantment.enchantmentsList[inputEnchID];
                            if (inputEnchID != combinerEnchID && (!combinerEnch.canApplyTogether(inputEnch) || !inputEnch.canApplyTogether(combinerEnch))) {
                                canApply = false;
                                combineCost += levelsAdded;
                            }
                        }
                        if (canApply) {
                            combinerEnchLevel = Math.min(combinerEnchLevel, combinerEnch.getMaxLevel());
                            outputEnchants.put(combinerEnchID, combinerEnchLevel);
                            int costPerLevel = 0;
                            final int enchWeight = combinerEnch.getWeight();
                            if (enchWeight == 1) {
                                costPerLevel = 8;
                            }
                            else if (enchWeight == 2) {
                                costPerLevel = 4;
                            }
                            else if (enchWeight == 5) {
                                costPerLevel = 2;
                            }
                            else if (enchWeight == 10) {
                                costPerLevel = 1;
                            }
                            combineCost += costPerLevel * levelsAdded;
                        }
                    }
                }
                else {
                    outputEnchants.clear();
                }
                EnchantmentHelper.setEnchantments(outputEnchants, inputCopy);
                final int maxMods = 3;
                final List<LOTREnchantment> outputMods = new ArrayList<LOTREnchantment>();
                outputMods.addAll(inputModifiers);
                final List<LOTREnchantment> combinerMods = LOTREnchantmentHelper.getEnchantList(combinerItem);
                if (combinerItemEnchant != null) {
                    combinerMods.add(combinerItemEnchant);
                    if (combinerItemEnchant == LOTREnchantment.fire) {
                        final Item item2 = inputCopy.getItem();
                        if (LOTRRecipePoisonWeapon.poisonedToInput.containsKey(item2)) {
                            final Item unpoisoned = LOTRRecipePoisonWeapon.poisonedToInput.get(item2);
                            inputCopy.func_150996_a(unpoisoned);
                        }
                    }
                }
                for (final LOTREnchantment combinerMod : combinerMods) {
                    boolean canApply2 = combinerMod.canApply(inputItem, false);
                    if (canApply2) {
                        for (final LOTREnchantment mod : outputMods) {
                            if (!mod.isCompatibleWith(combinerMod) || !combinerMod.isCompatibleWith(mod)) {
                                canApply2 = false;
                            }
                        }
                    }
                    int numOutputMods = 0;
                    for (final LOTREnchantment mod2 : outputMods) {
                        if (!mod2.bypassAnvilLimit()) {
                            ++numOutputMods;
                        }
                    }
                    if (!combinerMod.bypassAnvilLimit() && numOutputMods >= maxMods) {
                        canApply2 = false;
                    }
                    if (canApply2) {
                        outputMods.add(combinerMod);
                        if (!combinerMod.isBeneficial()) {
                            continue;
                        }
                        combineCost += Math.max(1, (int)combinerMod.getValueModifier());
                    }
                }
                LOTREnchantmentHelper.setEnchantList(inputCopy, outputMods);
            }
            if (combineCost > 0) {
                combining = true;
            }
            int numEnchants = 0;
            for (final Object obj2 : inputEnchants.keySet()) {
                final int enchID = (int)obj2;
                final Enchantment ench = Enchantment.enchantmentsList[enchID];
                final int enchLevel = inputEnchants.get(enchID);
                ++numEnchants;
                int costPerLevel2 = 0;
                final int enchWeight2 = ench.getWeight();
                if (enchWeight2 == 1) {
                    costPerLevel2 = 8;
                }
                else if (enchWeight2 == 2) {
                    costPerLevel2 = 4;
                }
                else if (enchWeight2 == 5) {
                    costPerLevel2 = 2;
                }
                else if (enchWeight2 == 10) {
                    costPerLevel2 = 1;
                }
                baseAnvilCost += numEnchants + enchLevel * costPerLevel2;
            }
            if (enchantingWithBook && !inputCopy.getItem().isBookEnchantable(inputCopy, combinerItem)) {
                inputCopy = null;
            }
            for (final LOTREnchantment mod3 : inputModifiers) {
                if (mod3.isBeneficial()) {
                    baseAnvilCost += Math.max(1, (int)mod3.getValueModifier());
                }
            }
            if (inputCopy.isItemStackDamageable()) {
                boolean canRepair = false;
                int availableMaterials = 0;
                if (this.isTrader) {
                    canRepair = (this.getTraderMaterialPrice(inputItem) > 0.0f);
                    availableMaterials = Integer.MAX_VALUE;
                }
                else {
                    canRepair = (materialItem != null && this.isRepairMaterial(inputItem, materialItem));
                    if (materialItem != null) {
                        availableMaterials = materialItem.stackSize - combineCost - renameCost;
                    }
                }
                int oneItemRepair = Math.min(inputCopy.getItemDamageForDisplay(), inputCopy.getMaxDamage() / 4);
                if (canRepair && availableMaterials > 0 && oneItemRepair > 0) {
                    availableMaterials -= baseAnvilCost;
                    if (availableMaterials > 0) {
                        int usedMaterials;
                        for (usedMaterials = 0; oneItemRepair > 0 && usedMaterials < availableMaterials; oneItemRepair = Math.min(inputCopy.getItemDamageForDisplay(), inputCopy.getMaxDamage() / 4), ++usedMaterials) {
                            final int newDamage = inputCopy.getItemDamageForDisplay() - oneItemRepair;
                            inputCopy.setItemDamage(newDamage);
                        }
                        repairCost += usedMaterials;
                    }
                    else if (!nameChange && !combining) {
                        repairCost = 1;
                        final int newDamage2 = inputCopy.getItemDamageForDisplay() - oneItemRepair;
                        inputCopy.setItemDamage(newDamage2);
                    }
                }
            }
            final boolean repairing = repairCost > 0;
            if (combining || repairing) {
                this.materialCost = baseAnvilCost;
                this.materialCost += combineCost + repairCost;
            }
            else {
                this.materialCost = 0;
            }
            this.materialCost += renameCost;
            if (inputCopy != null) {
                int nextAnvilCost = LOTREnchantmentHelper.getAnvilCost(inputItem);
                if (combinerItem != null) {
                    final int combinerAnvilCost = LOTREnchantmentHelper.getAnvilCost(combinerItem);
                    nextAnvilCost = Math.max(nextAnvilCost, combinerAnvilCost);
                }
                if (combining) {
                    nextAnvilCost += 2;
                }
                else if (repairing) {
                    ++nextAnvilCost;
                }
                nextAnvilCost = Math.max(nextAnvilCost, 0);
                if (nextAnvilCost > 0) {
                    LOTREnchantmentHelper.setAnvilCost(inputCopy, nextAnvilCost);
                }
            }
            if (LOTREnchantmentHelper.isReforgeable(inputItem)) {
                this.reforgeCost = 2;
                if (inputItem.getItem() instanceof ItemArmor) {
                    this.reforgeCost = 3;
                }
                if (inputItem.isItemStackDamageable()) {
                    final ItemStack reforgeCopy = inputItem.copy();
                    int oneItemRepair = Math.min(reforgeCopy.getItemDamageForDisplay(), reforgeCopy.getMaxDamage() / 4);
                    if (oneItemRepair > 0) {
                        int usedMaterials;
                        for (usedMaterials = 0; oneItemRepair > 0; oneItemRepair = Math.min(reforgeCopy.getItemDamageForDisplay(), reforgeCopy.getMaxDamage() / 4), ++usedMaterials) {
                            final int newDamage = reforgeCopy.getItemDamageForDisplay() - oneItemRepair;
                            reforgeCopy.setItemDamage(newDamage);
                        }
                        this.reforgeCost += usedMaterials;
                    }
                }
            }
            else {
                this.reforgeCost = 0;
            }
            if (this.isRepairMaterial(inputItem, new ItemStack(Items.string))) {
                final int stringFactor = 3;
                this.materialCost *= stringFactor;
                this.reforgeCost *= stringFactor;
            }
            if (this.isTrader) {
                final boolean isCommonRenameOnly = nameChange && this.materialCost == 0;
                final float materialPrice = this.getTraderMaterialPrice(inputItem);
                if (materialPrice > 0.0f) {
                    this.materialCost = Math.round(this.materialCost * materialPrice);
                    this.materialCost = Math.max(this.materialCost, 1);
                    this.reforgeCost = Math.round(this.reforgeCost * materialPrice);
                    this.reforgeCost = Math.max(this.reforgeCost, 1);
                    if (this.theTrader instanceof LOTREntityScrapTrader) {
                        this.materialCost = MathHelper.ceiling_float_int(this.materialCost * 0.5f);
                        this.materialCost = Math.max(this.materialCost, 1);
                        this.reforgeCost = MathHelper.ceiling_float_int(this.reforgeCost * 0.5f);
                        this.reforgeCost = Math.max(this.reforgeCost, 1);
                    }
                }
                else if (!isCommonRenameOnly) {
                    this.invOutput.setInventorySlotContents(0, (ItemStack)null);
                    this.materialCost = 0;
                    this.reforgeCost = 0;
                    return;
                }
            }
            if (combining || repairing || nameChange) {
                this.invOutput.setInventorySlotContents(0, inputCopy);
            }
            else {
                this.invOutput.setInventorySlotContents(0, (ItemStack)null);
                this.materialCost = 0;
            }
            this.detectAndSendChanges();
        }
    }
    
    public boolean isRepairMaterial(final ItemStack inputItem, final ItemStack materialItem) {
        if (inputItem.getItem().getIsRepairable(inputItem, materialItem)) {
            return true;
        }
        final Item item = inputItem.getItem();
        if (item == Items.bow && LOTRMod.rohanBow.getIsRepairable(inputItem, materialItem)) {
            return true;
        }
        if (item instanceof ItemFishingRod && materialItem.getItem() == Items.string) {
            return true;
        }
        if (item instanceof ItemShears && materialItem.getItem() == Items.iron_ingot) {
            return true;
        }
        if (item instanceof ItemEnchantedBook && materialItem.getItem() == Items.paper) {
            return true;
        }
        Item.ToolMaterial material = null;
        if (item instanceof ItemTool) {
            material = Item.ToolMaterial.valueOf(((ItemTool)item).getToolMaterialName());
        }
        else if (item instanceof ItemSword) {
            material = Item.ToolMaterial.valueOf(((ItemSword)item).func_150932_j());
        }
        if (material == Item.ToolMaterial.WOOD || material == LOTRMaterial.MOREDAIN_WOOD.toToolMaterial()) {
            return LOTRMod.isOreNameEqual(materialItem, "plankWood");
        }
        if (material == LOTRMaterial.MALLORN.toToolMaterial()) {
            return materialItem.getItem() == Item.getItemFromBlock(LOTRMod.planks) && materialItem.getItemDamage() == 1;
        }
        if (material == LOTRMaterial.MALLORN_MACE.toToolMaterial()) {
            return materialItem.getItem() == Item.getItemFromBlock(LOTRMod.wood) && materialItem.getItemDamage() == 1;
        }
        if (item instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)item;
            final ItemArmor.ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if (armorMaterial == LOTRMaterial.BONE.toArmorMaterial()) {
                return LOTRMod.isOreNameEqual(materialItem, "bone");
            }
        }
        return false;
    }
    
    private float getTraderMaterialPrice(final ItemStack inputItem) {
        float materialPrice = 0.0f;
        final LOTRTradeEntry[] sellTrades = this.theNPC.traderNPCInfo.getSellTrades();
        if (sellTrades != null) {
            for (final LOTRTradeEntry trade : sellTrades) {
                final ItemStack tradeItem = trade.createTradeItem();
                if (this.isRepairMaterial(inputItem, tradeItem)) {
                    materialPrice = trade.getCost() / (float)trade.createTradeItem().stackSize;
                    break;
                }
            }
        }
        if (materialPrice <= 0.0f) {
            final LOTRTradeEntries sellPool = this.theTrader.getSellPool();
            for (final LOTRTradeEntry trade2 : sellPool.tradeEntries) {
                final ItemStack tradeItem2 = trade2.createTradeItem();
                if (this.isRepairMaterial(inputItem, tradeItem2)) {
                    materialPrice = trade2.getCost() / (float)trade2.createTradeItem().stackSize;
                    break;
                }
            }
        }
        if (materialPrice <= 0.0f && (this.isRepairMaterial(inputItem, new ItemStack(LOTRMod.mithril)) || this.isRepairMaterial(inputItem, new ItemStack(LOTRMod.mithrilMail))) && this.theTrader instanceof LOTREntityDwarf) {
            materialPrice = 200.0f;
        }
        return materialPrice;
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            crafting.sendProgressBarUpdate((Container)this, 0, this.materialCost);
            crafting.sendProgressBarUpdate((Container)this, 1, this.reforgeCost);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.materialCost = j;
        }
        if (i == 1) {
            this.reforgeCost = j;
        }
        if (i == 2) {
            this.clientReforgeTime = 40;
        }
    }
    
    public boolean hasMaterialOrCoinAmount(final int cost) {
        if (this.isTrader) {
            return LOTRItemCoin.getInventoryValue(this.thePlayer) >= cost;
        }
        final ItemStack inputItem = this.invInput.getStackInSlot(0);
        final ItemStack materialItem = this.invInput.getStackInSlot(2);
        return materialItem != null && this.isRepairMaterial(inputItem, materialItem) && materialItem.stackSize >= cost;
    }
    
    public void takeMaterialOrCoinAmount(final int cost) {
        if (this.isTrader) {
            if (!this.theWorld.isClient) {
                LOTRItemCoin.takeCoins(cost, this.thePlayer);
                this.detectAndSendChanges();
                this.theNPC.playTradeSound();
            }
        }
        else {
            final ItemStack materialItem = this.invInput.getStackInSlot(2);
            if (materialItem != null) {
                final ItemStack itemStack = materialItem;
                itemStack.stackSize -= cost;
                if (materialItem.stackSize <= 0) {
                    this.invInput.setInventorySlotContents(2, (ItemStack)null);
                }
                else {
                    this.invInput.setInventorySlotContents(2, materialItem);
                }
            }
        }
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!this.theWorld.isClient) {
            for (int i = 0; i < this.invInput.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.invInput.getStackInSlotOnClosing(i);
                if (itemstack != null) {
                    entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
            if (this.doneMischief && this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
                this.theNPC.sendSpeechBank(entityplayer, ((LOTREntityScrapTrader)this.theNPC).getSmithSpeechBank());
            }
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        if (this.isTrader) {
            return this.theNPC != null && entityplayer.getDistanceToEntity((Entity)this.theNPC) <= 12.0 && this.theNPC.isEntityAlive() && this.theNPC.getAttackTarget() == null && this.theTrader.canTradeWith(entityplayer);
        }
        return this.theWorld.getBlock(this.xCoord, this.yCoord, this.zCoord) == Blocks.anvil && entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
    
    public ItemStack slotClick(final int slotNo, final int j, final int k, final EntityPlayer entityplayer) {
        ItemStack resultItem = this.invOutput.getStackInSlot(0);
        resultItem = ItemStack.copyItemStack(resultItem);
        boolean changed = false;
        if (resultItem != null && slotNo == this.getSlotFromInventory(this.invOutput, 0).slotNumber && !this.theWorld.isClient && this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
            final ItemStack resultCopy = resultItem.copy();
            changed = this.applyMischief(resultCopy);
            if (changed) {
                this.invOutput.setInventorySlotContents(0, resultCopy);
            }
        }
        final ItemStack slotClickResult = super.slotClick(slotNo, j, k, entityplayer);
        if (changed) {
            this.doneMischief = true;
            if (this.invOutput.getStackInSlot(0) != null) {
                this.invOutput.setInventorySlotContents(0, resultItem.copy());
            }
        }
        return slotClickResult;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            final int inputSize = this.invInput.getSizeInventory();
            if (i == inputSize) {
                if (!this.mergeItemStack(itemstack2, inputSize + 1, inputSize + 37, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack2, itemstack);
            }
            else if (i >= inputSize + 1) {
                if (i >= inputSize + 1 && i < inputSize + 37 && !this.mergeItemStack(itemstack2, 0, inputSize, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, inputSize + 1, inputSize + 37, false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack2);
        }
        return itemstack;
    }
    
    public void updateItemName(final String name) {
        this.repairedItemName = name;
        final ItemStack itemstack = this.invOutput.getStackInSlot(0);
        if (itemstack != null) {
            if (StringUtils.isBlank((CharSequence)name)) {
                itemstack.func_135074_t();
            }
            else {
                itemstack.setStackDisplayName(this.repairedItemName);
            }
        }
        this.updateRepairOutput();
    }
    
    public void reforgeItem() {
        final long curTime = System.currentTimeMillis();
        if (this.lastReforgeTime < 0L || curTime - this.lastReforgeTime >= 2000L) {
            final ItemStack inputItem = this.invInput.getStackInSlot(0);
            if (inputItem != null && this.reforgeCost > 0 && this.hasMaterialOrCoinAmount(this.reforgeCost)) {
                final int cost = this.reforgeCost;
                if (inputItem.isItemStackDamageable()) {
                    inputItem.setItemDamage(0);
                }
                LOTREnchantmentHelper.applyRandomEnchantments(inputItem, this.theWorld.rand, true, true);
                LOTREnchantmentHelper.setAnvilCost(inputItem, 0);
                if (this.isTrader && this.theNPC instanceof LOTREntityScrapTrader) {
                    final boolean changed = this.applyMischief(inputItem);
                    if (changed) {
                        this.doneMischief = true;
                    }
                }
                this.invInput.setInventorySlotContents(0, inputItem);
                this.takeMaterialOrCoinAmount(cost);
                this.playAnvilSound();
                this.lastReforgeTime = curTime;
                ((EntityPlayerMP)this.thePlayer).sendProgressBarUpdate((Container)this, 2, 0);
                if (!this.isTrader) {
                    LOTRLevelData.getData(this.thePlayer).addAchievement(LOTRAchievement.reforge);
                }
            }
        }
    }
    
    private boolean applyMischief(final ItemStack itemstack) {
        boolean changed = false;
        final Random rand = this.theWorld.rand;
        if (rand.nextFloat() < 0.8f) {
            String name = itemstack.getDisplayName();
            final int deletes = rand.nextInt(3);
            for (int l = 0; l < deletes; ++l) {
                if (name.length() > 3) {
                    final int x = rand.nextInt(name.length());
                    final char c = name.charAt(x);
                    name = name.substring(0, x) + name.substring(x + 1);
                }
            }
            final int replaces = rand.nextInt(3);
            final String vowels = "aeiou";
            final String consonants = "bcdfghjklmnopqrstvwxyz";
            for (int i = 0; i < deletes; ++i) {
                final int x2 = rand.nextInt(name.length());
                char c2 = name.charAt(x2);
                if (vowels.indexOf(Character.toLowerCase(c2)) >= 0) {
                    char cNew = vowels.charAt(rand.nextInt(vowels.length()));
                    if (Character.isUpperCase(c2)) {
                        cNew = Character.toUpperCase(cNew);
                    }
                    c2 = cNew;
                }
                else if (consonants.indexOf(Character.toLowerCase(c2)) >= 0) {
                    char cNew = consonants.charAt(rand.nextInt(vowels.length()));
                    if (Character.isUpperCase(c2)) {
                        cNew = Character.toUpperCase(cNew);
                    }
                    c2 = cNew;
                }
                name = name.substring(0, x2) + c2 + name.substring(x2 + 1);
            }
            for (int dupes = rand.nextInt(2), j = 0; j < dupes; ++j) {
                final int x3 = rand.nextInt(name.length());
                final char c3 = name.charAt(x3);
                if (Character.isAlphabetic(c3)) {
                    name = name.substring(0, x3) + c3 + c3 + name.substring(x3 + 1);
                }
            }
            if (name.equals(itemstack.getItem().getItemStackDisplayName(itemstack))) {
                itemstack.func_135074_t();
            }
            else {
                itemstack.setStackDisplayName(name);
            }
            changed = true;
        }
        if (rand.nextFloat() < 0.2f) {
            LOTREnchantmentHelper.applyRandomEnchantments(itemstack, rand, false, true);
            changed = true;
        }
        return changed;
    }
    
    public void playAnvilSound() {
        if (!this.theWorld.isClient) {
            int i;
            int j;
            int k;
            if (this.isTrader) {
                i = MathHelper.floor_double(((Entity)this.theNPC).posX);
                j = MathHelper.floor_double(((Entity)this.theNPC).posY);
                k = MathHelper.floor_double(((Entity)this.theNPC).posZ);
            }
            else {
                i = this.xCoord;
                j = this.yCoord;
                k = this.zCoord;
            }
            this.theWorld.playAuxSFX(1021, i, j, k, 0);
        }
    }
}
