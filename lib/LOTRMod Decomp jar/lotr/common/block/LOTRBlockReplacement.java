// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.RegistrySimple;
import java.util.Map;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.lang.reflect.Field;
import lotr.common.LOTRReflection;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import java.util.List;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IChatComponent;
import net.minecraft.stats.StatCrafting;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.stats.StatList;
import java.lang.reflect.Constructor;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;

public class LOTRBlockReplacement
{
    private static boolean initForgeHooks;
    
    public static void replaceVanillaBlock(final Block oldBlock, final Block newBlock, final Class<? extends ItemBlock> itemClass) {
        try {
            final Item oldItem = Item.getItemFromBlock(oldBlock);
            final int id = Block.blockRegistry.getIDForObject((Object)oldBlock);
            final String blockName = getBlockName(oldBlock);
            final String registryName = Block.blockRegistry.getNameForObject((Object)oldBlock);
            String itemblockName = blockName;
            if (oldItem != null) {
                itemblockName = getItemName(oldItem);
            }
            newBlock.setUnlocalizedName(blockName);
            overwriteBlockList(oldBlock, newBlock);
            getUnderlyingIntMap(Block.blockRegistry).func_148746_a((Object)newBlock, id);
            getUnderlyingObjMap(Block.blockRegistry).put(registryName, newBlock);
            if (!LOTRBlockReplacement.initForgeHooks) {
                ForgeHooks.isToolEffective(new ItemStack(Items.iron_shovel), Blocks.dirt, 0);
                LOTRBlockReplacement.initForgeHooks = true;
            }
            for (int meta = 0; meta <= 15; ++meta) {
                newBlock.setHarvestLevel(oldBlock.getHarvestTool(meta), oldBlock.getHarvestLevel(meta), meta);
            }
            if (itemClass != null) {
                Constructor<ItemBlock> itemCtor = null;
                final Constructor<?>[] constructors;
                final Constructor[] itemCtors = constructors = itemClass.getConstructors();
                for (final Constructor ct : constructors) {
                    final Class[] params = ct.getParameterTypes();
                    if (params.length == 1 && Block.class.isAssignableFrom(params[0])) {
                        itemCtor = (Constructor<ItemBlock>)ct;
                        break;
                    }
                }
                final ItemBlock itemblock = itemCtor.newInstance(newBlock).setUnlocalizedName(itemblockName);
                getUnderlyingIntMap(Item.itemRegistry).func_148746_a((Object)itemblock, id);
                getUnderlyingObjMap(Item.itemRegistry).put(registryName, itemblock);
                replaceBlockStats(id, newBlock, itemblock);
                replaceRecipesEtc((Item)itemblock);
            }
        }
        catch (Exception e) {
            FMLLog.severe("Failed to replace vanilla block %s", new Object[] { oldBlock.getUnlocalizedName() });
            throw new RuntimeException(e);
        }
    }
    
    private static void replaceBlockStats(final int id, final Block newBlock, final ItemBlock itemblock) {
        replaceStat(id, StatList.mineBlockStatArray, (StatBase)new StatCrafting("stat.mineBlock." + id, (IChatComponent)new ChatComponentTranslation("stat.mineBlock", new Object[] { new ItemStack(newBlock).func_151000_E() }), (Item)itemblock));
        replaceStat(id, StatList.objectUseStats, (StatBase)new StatCrafting("stat.useItem." + id, (IChatComponent)new ChatComponentTranslation("stat.useItem", new Object[] { new ItemStack((Item)itemblock).func_151000_E() }), (Item)itemblock));
        replaceStat(id, StatList.objectCraftStats, (StatBase)new StatCrafting("stat.craftItem." + id, (IChatComponent)new ChatComponentTranslation("stat.craftItem", new Object[] { new ItemStack((Item)itemblock).func_151000_E() }), (Item)itemblock));
    }
    
    public static void replaceVanillaItem(final Item oldItem, final Item newItem) {
        try {
            final int id = Item.itemRegistry.getIDForObject((Object)oldItem);
            final String itemName = getItemName(oldItem);
            final String registryName = Item.itemRegistry.getNameForObject((Object)oldItem);
            newItem.setUnlocalizedName(itemName);
            overwriteItemList(oldItem, newItem);
            getUnderlyingIntMap(Item.itemRegistry).func_148746_a((Object)newItem, id);
            getUnderlyingObjMap(Item.itemRegistry).put(registryName, newItem);
            replaceItemStats(id, newItem);
            replaceRecipesEtc(newItem);
        }
        catch (Exception e) {
            FMLLog.severe("Failed to replace vanilla item %s", new Object[] { oldItem.getUnlocalizedName() });
            throw new RuntimeException(e);
        }
    }
    
    private static void replaceItemStats(final int id, final Item newItem) {
        replaceStat(id, StatList.objectUseStats, (StatBase)new StatCrafting("stat.useItem." + id, (IChatComponent)new ChatComponentTranslation("stat.useItem", new Object[] { new ItemStack(newItem).func_151000_E() }), newItem));
        replaceStat(id, StatList.objectCraftStats, (StatBase)new StatCrafting("stat.craftItem." + id, (IChatComponent)new ChatComponentTranslation("stat.craftItem", new Object[] { new ItemStack(newItem).func_151000_E() }), newItem));
        if (newItem.isDamageable()) {
            replaceStat(id, StatList.objectBreakStats, (StatBase)new StatCrafting("stat.breakItem." + id, (IChatComponent)new ChatComponentTranslation("stat.breakItem", new Object[] { new ItemStack(newItem).func_151000_E() }), newItem));
        }
    }
    
    private static void replaceStat(final int id, final StatBase[] stats, final StatBase newStat) {
        final StatBase oldStat = stats[id];
        if (oldStat != null && oldStat.statId.equals(newStat.statId)) {
            for (int i = 0; i < stats.length; ++i) {
                final StatBase otherOldStat = stats[i];
                if (otherOldStat != null && otherOldStat.statId.equals(oldStat.statId)) {
                    StatList.allStats.remove(otherOldStat);
                    StatList.objectMineStats.remove(otherOldStat);
                    StatList.itemStats.remove(otherOldStat);
                    StatList.generalStats.remove(otherOldStat);
                    getOneShotStats().remove(otherOldStat.statId);
                    stats[i] = newStat;
                }
            }
            newStat.registerStat();
        }
    }
    
    private static void replaceRecipesEtc(final Item newItem) {
        final String newItemName = newItem.getUnlocalizedName();
        final List craftingRecipes = CraftingManager.getInstance().getRecipeList();
        for (final Object obj : craftingRecipes) {
            if (obj instanceof ShapedRecipes) {
                final ShapedRecipes recipe = (ShapedRecipes)obj;
                final ItemStack output = recipe.getRecipeOutput();
                if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
                    injectReplacementItem(output, newItem);
                }
            }
            if (obj instanceof ShapelessRecipes) {
                final ShapelessRecipes recipe2 = (ShapelessRecipes)obj;
                final ItemStack output = recipe2.getRecipeOutput();
                if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
                    injectReplacementItem(output, newItem);
                }
            }
            if (obj instanceof ShapedOreRecipe) {
                final ShapedOreRecipe recipe3 = (ShapedOreRecipe)obj;
                final ItemStack output = recipe3.func_77571_b();
                if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
                    injectReplacementItem(output, newItem);
                }
            }
            if (obj instanceof ShapelessOreRecipe) {
                final ShapelessOreRecipe recipe4 = (ShapelessOreRecipe)obj;
                final ItemStack output = recipe4.func_77571_b();
                if (output == null || output.getItem() == null || !output.getItem().getUnlocalizedName().equals(newItemName)) {
                    continue;
                }
                injectReplacementItem(output, newItem);
            }
        }
        for (final Object obj : AchievementList.achievementList) {
            final Achievement a = (Achievement)obj;
            final ItemStack icon = a.theItemStack;
            if (icon.getItem().getUnlocalizedName().equals(newItem.getUnlocalizedName())) {
                injectReplacementItem(icon, newItem);
            }
        }
    }
    
    private static void injectReplacementItem(final ItemStack itemstack, final Item newItem) {
        final NBTTagCompound nbt = new NBTTagCompound();
        itemstack.writeToNBT(nbt);
        itemstack.readFromNBT(nbt);
    }
    
    static {
        LOTRBlockReplacement.initForgeHooks = false;
    }
    
    private static class Reflect
    {
        private static void overwriteBlockList(final Block oldBlock, final Block newBlock) {
            try {
                Field field = null;
                final Field[] declaredFields2;
                final Field[] declaredFields = declaredFields2 = Blocks.class.getDeclaredFields();
                for (final Field f : declaredFields2) {
                    LOTRReflection.unlockFinalField(f);
                    if (f.get(null) == oldBlock) {
                        field = f;
                        break;
                    }
                }
                LOTRReflection.setFinalField((Class<? super Object>)Blocks.class, (Object)null, newBlock, field);
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
            }
        }
        
        private static void overwriteItemList(final Item oldItem, final Item newItem) {
            try {
                Field field = null;
                final Field[] declaredFields2;
                final Field[] declaredFields = declaredFields2 = Items.class.getDeclaredFields();
                for (final Field f : declaredFields2) {
                    LOTRReflection.unlockFinalField(f);
                    if (f.get(null) == oldItem) {
                        field = f;
                        break;
                    }
                }
                LOTRReflection.setFinalField((Class<? super Object>)Items.class, (Object)null, newItem, field);
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
            }
        }
        
        private static String getBlockName(final Block block) {
            try {
                return (String)ObfuscationReflectionHelper.getPrivateValue((Class)Block.class, (Object)block, new String[] { "unlocalizedName", "field_149770_b" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
        
        private static String getItemName(final Item item) {
            try {
                return (String)ObfuscationReflectionHelper.getPrivateValue((Class)Item.class, (Object)item, new String[] { "unlocalizedName", "field_77774_bZ" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
        
        private static ObjectIntIdentityMap getUnderlyingIntMap(final RegistryNamespaced registry) {
            try {
                return (ObjectIntIdentityMap)ObfuscationReflectionHelper.getPrivateValue((Class)RegistryNamespaced.class, (Object)registry, new String[] { "underlyingIntegerMap", "field_148759_a" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
        
        private static Map getUnderlyingObjMap(final RegistryNamespaced registry) {
            try {
                return (Map)ObfuscationReflectionHelper.getPrivateValue((Class)RegistrySimple.class, (Object)registry, new String[] { "registryObjects", "field_82596_a" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
        
        private static Map getOneShotStats() {
            try {
                return (Map)ObfuscationReflectionHelper.getPrivateValue((Class)StatList.class, (Object)null, new String[] { "oneShotStats", "field_75942_a" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
    }
}
