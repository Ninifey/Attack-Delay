// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import lotr.common.item.LOTRMaterial;
import lotr.common.item.LOTRItemDaleCracker;
import lotr.common.item.LOTRItemBanner;
import java.lang.reflect.Field;
import java.util.HashMap;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.RecipesArmorDyes;
import lotr.common.LOTRConfig;
import java.util.Arrays;
import java.util.ArrayList;
import lotr.common.block.LOTRBlockFallenLeaves;
import lotr.common.block.LOTRBlockTreasurePile;
import net.minecraft.block.BlockColored;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import lotr.common.item.LOTRItemBerry;
import lotr.common.item.LOTRItemBone;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockSaplingBase;
import lotr.common.block.LOTRBlockLeavesBase;
import lotr.common.block.LOTRBlockWoodBase;
import lotr.common.block.LOTRBlockStairs;
import net.minecraft.block.material.Material;
import lotr.common.block.LOTRBlockSlabBase;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.item.ItemStack;
import lotr.common.block.LOTRBlockPlanksBase;
import net.minecraft.block.Block;
import java.util.Collection;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraft.item.crafting.IRecipe;
import java.util.List;

public class LOTRRecipes
{
    public static List<IRecipe> woodenSlabRecipes;
    public static List<IRecipe> morgulRecipes;
    public static List<IRecipe> elvenRecipes;
    public static List<IRecipe> dwarvenRecipes;
    public static List<IRecipe> urukRecipes;
    public static List<IRecipe> woodElvenRecipes;
    public static List<IRecipe> gondorianRecipes;
    public static List<IRecipe> rohirricRecipes;
    public static List<IRecipe> dunlendingRecipes;
    public static List<IRecipe> angmarRecipes;
    public static List<IRecipe> nearHaradRecipes;
    public static List<IRecipe> highElvenRecipes;
    public static List<IRecipe> blueMountainsRecipes;
    public static List<IRecipe> rangerRecipes;
    public static List<IRecipe> dolGuldurRecipes;
    public static List<IRecipe> gundabadRecipes;
    public static List<IRecipe> halfTrollRecipes;
    public static List<IRecipe> dolAmrothRecipes;
    public static List<IRecipe> moredainRecipes;
    public static List<IRecipe> tauredainRecipes;
    public static List<IRecipe> daleRecipes;
    public static List<IRecipe> dorwinionRecipes;
    public static List<IRecipe> hobbitRecipes;
    public static List<IRecipe> rhunRecipes;
    public static List<IRecipe> rivendellRecipes;
    public static List<IRecipe> umbarRecipes;
    public static List<IRecipe> gulfRecipes;
    public static List<IRecipe> uncraftableUnsmeltingRecipes;
    private static List[] commonOrcRecipes;
    private static List[] commonMorgulRecipes;
    private static List[] commonElfRecipes;
    private static List[] commonHighElfRecipes;
    private static List[] commonDwarfRecipes;
    private static List[] commonDunedainRecipes;
    private static List[] commonNumenoreanRecipes;
    private static List[] commonNearHaradRecipes;
    private static final String[] dyeOreNames;
    
    public static void createAllRecipes() {
        registerOres();
        RecipeSorter.register("lotr:armorDyes", (Class)LOTRRecipesArmorDyes.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:hobbitPipe", (Class)LOTRRecipeHobbitPipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:pouch", (Class)LOTRRecipesPouch.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:leatherHatDye", (Class)LOTRRecipeLeatherHatDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:featherDye", (Class)LOTRRecipeFeatherDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:leatherHatFeather", (Class)LOTRRecipeLeatherHatFeather.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:haradRobesDye", (Class)LOTRRecipeHaradRobesDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:poisonWeapon", (Class)LOTRRecipePoisonWeapon.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:banners", (Class)LOTRRecipesBanners.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:poisonDrink", (Class)LOTRRecipesPoisonDrinks.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:treasurePile", (Class)LOTRRecipesTreasurePile.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:partyHatDye", (Class)LOTRRecipePartyHatDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        removeStandardRecipes();
        createStandardRecipes();
        createPoisonedDaggerRecipes();
        createPoisonedArrowRecipes();
        createWoodenSlabRecipes();
        CraftingManager.getInstance().getRecipeList().addAll(0, LOTRRecipes.woodenSlabRecipes);
        createSmeltingRecipes();
        createCommonOrcRecipes();
        createCommonMorgulRecipes();
        createCommonElfRecipes();
        createCommonHighElfRecipes();
        createCommonDwarfRecipes();
        createCommonDunedainRecipes();
        createCommonNumenoreanRecipes();
        createCommonNearHaradRecipes();
        createMorgulRecipes();
        createElvenRecipes();
        createDwarvenRecipes();
        createUrukRecipes();
        createWoodElvenRecipes();
        createGondorianRecipes();
        createRohirricRecipes();
        createDunlendingRecipes();
        createAngmarRecipes();
        createNearHaradRecipes();
        createHighElvenRecipes();
        createBlueMountainsRecipes();
        createRangerRecipes();
        createDolGuldurRecipes();
        createGundabadRecipes();
        createHalfTrollRecipes();
        createDolAmrothRecipes();
        createMoredainRecipes();
        createTauredainRecipes();
        createDaleRecipes();
        createDorwinionRecipes();
        createHobbitRecipes();
        createRhunRecipes();
        createRivendellRecipes();
        createUmbarRecipes();
        createGulfRecipes();
        createUnsmeltingRecipes();
    }
    
    private static void registerOres() {
        for (final Object obj : Block.blockRegistry) {
            final Block block = (Block)obj;
            if (block instanceof LOTRBlockPlanksBase) {
                OreDictionary.registerOre("plankWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockSlabBase && block.getMaterial() == Material.wood) {
                OreDictionary.registerOre("slabWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockStairs && block.getMaterial() == Material.wood) {
                OreDictionary.registerOre("stairWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockWoodBase) {
                OreDictionary.registerOre("logWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockLeavesBase) {
                OreDictionary.registerOre("treeLeaves", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockSaplingBase) {
                OreDictionary.registerOre("treeSapling", new ItemStack(block, 1, 32767));
            }
        }
        OreDictionary.registerOre("stickWood", LOTRMod.mallornStick);
        OreDictionary.registerOre("stickWood", LOTRMod.blackrootStick);
        for (final Object obj : Item.itemRegistry) {
            final Item item = (Item)obj;
            if (item == Items.bone || item instanceof LOTRItemBone) {
                OreDictionary.registerOre("bone", item);
            }
            if (item instanceof LOTRItemBerry) {
                OreDictionary.registerOre("berry", item);
            }
        }
        OreDictionary.registerOre("oreCopper", LOTRMod.oreCopper);
        OreDictionary.registerOre("oreTin", LOTRMod.oreTin);
        OreDictionary.registerOre("oreSilver", LOTRMod.oreSilver);
        OreDictionary.registerOre("oreSulfur", LOTRMod.oreSulfur);
        OreDictionary.registerOre("oreSaltpeter", LOTRMod.oreSaltpeter);
        OreDictionary.registerOre("oreSalt", LOTRMod.oreSalt);
        OreDictionary.registerOre("ingotCopper", LOTRMod.copper);
        OreDictionary.registerOre("ingotTin", LOTRMod.tin);
        OreDictionary.registerOre("ingotBronze", LOTRMod.bronze);
        OreDictionary.registerOre("ingotSilver", LOTRMod.silver);
        OreDictionary.registerOre("nuggetSilver", LOTRMod.silverNugget);
        OreDictionary.registerOre("sulfur", LOTRMod.sulfur);
        OreDictionary.registerOre("saltpeter", LOTRMod.saltpeter);
        OreDictionary.registerOre("salt", LOTRMod.salt);
        OreDictionary.registerOre("clayBall", Items.clay_ball);
        OreDictionary.registerOre("clayBall", LOTRMod.redClayBall);
        OreDictionary.registerOre("dyeYellow", new ItemStack(LOTRMod.dye, 1, 0));
        OreDictionary.registerOre("dyeWhite", new ItemStack(LOTRMod.dye, 1, 1));
        OreDictionary.registerOre("dyeBlue", new ItemStack(LOTRMod.dye, 1, 2));
        OreDictionary.registerOre("dyeGreen", new ItemStack(LOTRMod.dye, 1, 3));
        OreDictionary.registerOre("dyeBlack", new ItemStack(LOTRMod.dye, 1, 4));
        OreDictionary.registerOre("dyeBrown", new ItemStack(LOTRMod.dye, 1, 5));
        OreDictionary.registerOre("sand", new ItemStack(LOTRMod.whiteSand, 1, 32767));
        OreDictionary.registerOre("sandstone", new ItemStack(LOTRMod.redSandstone, 1, 32767));
        OreDictionary.registerOre("sandstone", new ItemStack(LOTRMod.whiteSandstone, 1, 32767));
        OreDictionary.registerOre("apple", Items.apple);
        OreDictionary.registerOre("apple", LOTRMod.appleGreen);
        OreDictionary.registerOre("feather", Items.feather);
        OreDictionary.registerOre("feather", LOTRMod.swanFeather);
        OreDictionary.registerOre("horn", LOTRMod.rhinoHorn);
        OreDictionary.registerOre("horn", LOTRMod.kineArawHorn);
        OreDictionary.registerOre("horn", LOTRMod.horn);
        OreDictionary.registerOre("arrowTip", Items.flint);
        OreDictionary.registerOre("arrowTip", LOTRMod.rhinoHorn);
        OreDictionary.registerOre("arrowTip", LOTRMod.kineArawHorn);
        OreDictionary.registerOre("arrowTip", LOTRMod.horn);
        OreDictionary.registerOre("poison", LOTRMod.bottlePoison);
        OreDictionary.registerOre("vine", Blocks.vine);
        OreDictionary.registerOre("vine", LOTRMod.willowVines);
        OreDictionary.registerOre("vine", LOTRMod.mirkVines);
    }
    
    private static void createStandardRecipes() {
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.goldRing), new Object[] { "XXX", "X X", "XXX", 'X', "nuggetGold" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.saddle), new Object[] { "XXX", "Y Y", 'X', Items.leather, 'Y', "ingotIron" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.bronze, 1), new Object[] { LOTRMod.copper, LOTRMod.tin });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelBronze), new Object[] { "X", "Y", "Y", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeBronze), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeBronze), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordBronze), new Object[] { "X", "X", "Y", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeBronze), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetBronze), new Object[] { "XXX", "X X", 'X', LOTRMod.bronze });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyBronze), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.bronze });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsBronze), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.bronze });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsBronze), new Object[] { "X X", "X X", 'X', LOTRMod.bronze });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 0), new Object[] { new ItemStack(LOTRMod.wood, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsShirePine, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.silverNugget, 9), new Object[] { LOTRMod.silver });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.silver), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.silverNugget });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.silverRing), new Object[] { "XXX", "X X", "XXX", 'X', "nuggetSilver" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mithrilNugget, 9), new Object[] { LOTRMod.mithril });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithril), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.mithrilNugget });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithrilRing), new Object[] { "XXX", "X X", "XXX", 'X', LOTRMod.mithrilNugget });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new Object[] { LOTRMod.shireHeather });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mug, 2), new Object[] { "X", "Y", "X", 'X', "ingotTin", 'Y', "plankWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.clayMug, 2), new Object[] { "X", "Y", "X", 'X', "ingotTin", 'Y', "clayBall" }));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugChocolate), LOTRMod.mugMilk, new Object[] { Items.sugar, new ItemStack(Items.dye, 1, 3) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.appleCrumbleItem), new Object[] { "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', "apple", 'C', Items.sugar, 'D', Items.wheat }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.copper, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 0), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.copper });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.tin, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 1), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.tin });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.bronze, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 2), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.bronze });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.silver, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 3), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.silver });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mithril, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 4), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.mithril });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 0), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotBronze" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 1), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotIron" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 2), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotSilver" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 3), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotGold" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pipeweedSeeds, 2), new Object[] { LOTRMod.pipeweedPlant });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pipeweedSeeds), new Object[] { LOTRMod.pipeweedLeaf });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelMithril), new Object[] { "X", "Y", "Y", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeMithril), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeMithril), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordMithril), new Object[] { "X", "X", "Y", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeMithril), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetMithril), new Object[] { "XXX", "X X", 'X', LOTRMod.mithrilMail });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyMithril), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.mithrilMail });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsMithril), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.mithrilMail });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsMithril), new Object[] { "X X", "X X", 'X', LOTRMod.mithrilMail });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearBronze), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearIron), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearMithril), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.silverCoin, 4), new Object[] { "XX", "XX", 'X', "nuggetSilver" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.clayPlate, 2), new Object[] { "XX", 'X', "clayBall" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetFur), new Object[] { "XXX", "X X", 'X', LOTRMod.fur });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyFur), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.fur });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsFur), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.fur });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsFur), new Object[] { "X X", "X X", 'X', LOTRMod.fur });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 4), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', LOTRMod.mithril }));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipeHobbitPipe());
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 4, 15), new Object[] { LOTRMod.wargBone });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 4), new Object[] { new ItemStack(LOTRMod.fruitWood, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsApple, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 4) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 5), new Object[] { new ItemStack(LOTRMod.fruitWood, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPear, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 5) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 6), new Object[] { new ItemStack(LOTRMod.fruitWood, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCherry, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 6) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 2), new Object[] { LOTRMod.bluebell });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.hearth, 3), new Object[] { "XXX", "YYY", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', Items.brick });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBronze), new Object[] { "X", "Y", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerIron), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerMithril), new Object[] { "X", "Y", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeMithril), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerMithril), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 3, 15), new Object[] { LOTRMod.orcBone });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 3, 15), new Object[] { LOTRMod.elfBone });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), new Object[] { LOTRMod.dwarfBone });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), new Object[] { LOTRMod.hobbitBone });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.commandHorn), new Object[] { "XYX", 'X', "ingotBronze", 'Y', "horn" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.arrow, 4), new Object[] { "X", "Y", "Z", 'X', "arrowTip", 'Y', "stickWood", 'Z', "feather" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.crossbowBolt, 4), new Object[] { "X", "Y", "Z", 'X', "ingotIron", 'Y', "stickWood", 'Z', "feather" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.crossbowBolt, 4), new Object[] { "X", "Y", "Z", 'X', "ingotBronze", 'Y', "stickWood", 'Z', "feather" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.cherryPieItem), new Object[] { "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', LOTRMod.cherry, 'C', Items.sugar, 'D', Items.wheat });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 6, 15), new Object[] { LOTRMod.trollBone });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.ironCrossbow), new Object[] { "XXY", "ZYX", "YZX", 'X', "ingotIron", 'Y', "stickWood", 'Z', Items.string }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mithrilCrossbow), new Object[] { "XXY", "ZYX", "YZX", 'X', LOTRMod.mithril, 'Y', "stickWood", 'Z', Items.string }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMirkOak, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 2), new Object[] { new ItemStack(LOTRMod.wood, 1, 2) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorIron), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorGold), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotGold", 'Y', Items.leather }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.horseArmorDiamond), new Object[] { "X  ", "XYX", "XXX", 'X', Items.diamond, 'Y', Items.leather });
        GameRegistry.addRecipe((IRecipe)new LOTRRecipesPouch());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 0), new Object[] { "X", "Y", "Z", 'X', new ItemStack(LOTRMod.ancientItemParts, 1, 0), 'Y', new ItemStack(LOTRMod.ancientItemParts, 1, 1), 'Z', new ItemStack(LOTRMod.ancientItemParts, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 1), new Object[] { "X", "Y", 'X', new ItemStack(LOTRMod.ancientItemParts, 1, 0), 'Y', new ItemStack(LOTRMod.ancientItemParts, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 2), new Object[] { "XXX", "X X", 'X', new ItemStack(LOTRMod.ancientItemParts, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 3), new Object[] { "X X", "XXX", "XXX", 'X', new ItemStack(LOTRMod.ancientItemParts, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 4), new Object[] { "XXX", "X X", "X X", 'X', new ItemStack(LOTRMod.ancientItemParts, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 5), new Object[] { "X X", "X X", 'X', new ItemStack(LOTRMod.ancientItemParts, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCharred, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 3), new Object[] { new ItemStack(LOTRMod.wood, 1, 3) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.barrel), new Object[] { "XXX", "YZY", "XXX", 'X', "plankWood", 'Y', "ingotIron", 'Z', Items.bucket }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.armorStandItem), new Object[] { " X ", " X ", "YYY", 'X', "stickWood", 'Y', Blocks.stone }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pebble, 4), new Object[] { Blocks.gravel });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.sling), new Object[] { "XYX", "XZX", " X ", 'X', "stickWood", 'Y', Items.leather, 'Z', Items.string }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 8), new Object[] { new ItemStack(LOTRMod.wood2, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLebethron, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 8) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), new Object[] { LOTRMod.asphodel });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.orcSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 5), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.orcSteel });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateMordorRock), new Object[] { "XX", 'X', new ItemStack(LOTRMod.rock, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonMordorRock), new Object[] { new ItemStack(LOTRMod.rock, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.nauriteGem, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 10) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 10), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.nauriteGem });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.guldurilCrystal, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 11) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 11), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.guldurilCrystal });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new Object[] { LOTRMod.elanor });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), new Object[] { LOTRMod.niphredil });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.quenditeCrystal, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 6) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 6), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.quenditeCrystal });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.galvorn, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 8) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 8), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.galvorn });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dwarfSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 7) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 7), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.dwarfSteel });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.urukSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 9) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 9), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.urukSteel });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateGondorRock), new Object[] { "XX", 'X', new ItemStack(LOTRMod.rock, 1, 1) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonGondorRock), new Object[] { new ItemStack(LOTRMod.rock, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateRohanRock), new Object[] { "XX", 'X', new ItemStack(LOTRMod.rock, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonRohanRock), new Object[] { new ItemStack(LOTRMod.rock, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 9), new Object[] { new ItemStack(LOTRMod.wood2, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBeech, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 9) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.morgulSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 12) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 12), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.morgulSteel });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.leatherHat), new Object[] { " X ", "XXX", 'X', Items.leather });
        GameRegistry.addRecipe((IRecipe)new LOTRRecipeLeatherHatDye());
        GameRegistry.addRecipe((IRecipe)new LOTRRecipeFeatherDye());
        GameRegistry.addRecipe((IRecipe)new LOTRRecipeLeatherHatFeather());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateBlueRock), new Object[] { "XX", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonBlueRock), new Object[] { new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.smoothStone, 2, 3), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStone, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick, 4, 14), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall, 6, 13), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 14) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBlueRockBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 14) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall, 6, 14), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 14) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 10), new Object[] { new ItemStack(LOTRMod.wood2, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsHolly, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 10) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.rabbitStew), new Object[] { Items.bowl, LOTRMod.rabbitCooked, Items.potato, Items.potato });
        for (int i = 0; i <= 5; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Blocks.fence, 3, i), new Object[] { "XYX", "XYX", 'X', new ItemStack(Blocks.planks, 1, i), 'Y', "stickWood" }));
        }
        for (int i = 0; i <= 15; ++i) {
            if (i != 1) {
                GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fence, 3, i), new Object[] { "XYX", "XYX", 'X', new ItemStack(LOTRMod.planks, 1, i), 'Y', "stickWood" }));
            }
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 2, 0), new Object[] { new ItemStack(LOTRMod.doubleFlower, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar, 3, 3), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 2), new Object[] { LOTRMod.sulfur, LOTRMod.saltpeter, new ItemStack(Items.coal, 1, 1) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), new Object[] { LOTRMod.saltpeter, Blocks.dirt });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.commandSword), new Object[] { "X", "Y", "Z", 'X', "ingotIron", 'Y', "ingotBronze", 'Z', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.sulfurMatch, 4), new Object[] { "X", "Y", 'X', "sulfur", 'Y', "stickWood" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 13), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.sulfur });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 14), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.saltpeter });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.sulfur, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 13) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.saltpeter, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 14) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 7), new Object[] { new ItemStack(LOTRMod.fruitWood, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMango, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 7) });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugMangoJuice), new Object[] { LOTRMod.mango, LOTRMod.mango });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 11), new Object[] { new ItemStack(LOTRMod.wood2, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBanana, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 11) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bananaCakeItem), new Object[] { "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', LOTRMod.banana, 'C', Items.egg, 'D', Items.wheat });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bananaBread), new Object[] { "XYX", 'X', Items.wheat, 'Y', LOTRMod.banana });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.lionBedItem), new Object[] { "XXX", "YYY", 'X', LOTRMod.lionFur, 'Y', "plankWood" }));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new Object[] { new ItemStack(LOTRMod.doubleFlower, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new Object[] { new ItemStack(LOTRMod.doubleFlower, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 12), new Object[] { new ItemStack(LOTRMod.wood3, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMaple, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 12) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mapleSyrup), new Object[] { new ItemStack(LOTRMod.wood3, 1, 0), Items.bowl });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 13), new Object[] { new ItemStack(LOTRMod.wood3, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLarch, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 13) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetGemsbok), new Object[] { "Y Y", "XXX", "X X", 'X', LOTRMod.gemsbokHide, 'Y', LOTRMod.gemsbokHorn });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyGemsbok), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.gemsbokHide });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsGemsbok), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.gemsbokHide });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsGemsbok), new Object[] { "X X", "X X", 'X', LOTRMod.gemsbokHide });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateRedRock), new Object[] { "XX", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonRedRock), new Object[] { new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.smoothStone, 2, 4), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStone, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick2, 4, 2), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall2, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedRockBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall2, 6, 3), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar, 3, 4), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 4) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 14), new Object[] { new ItemStack(LOTRMod.wood3, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsDatePalm, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 14) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.blueDwarfSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage, 1, 15) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 15), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.blueDwarfSteel });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.thatch, 6, 0), new Object[] { "XYX", "YXY", "XYX", 'X', Items.wheat, 'Y', Blocks.dirt });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleThatch, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.thatch, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsThatch, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.thatch, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.horseArmorMithril), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.mithrilMail, 'Y', Items.leather });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.strawBedItem), new Object[] { "XXX", "YYY", 'X', Items.wheat, 'Y', "plankWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.thatchFloor, 3), new Object[] { "XX", 'X', new ItemStack(LOTRMod.thatch, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.redBook), new Object[] { Items.book, new ItemStack(Items.dye, 1, 1), "nuggetGold" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.termiteMound, 1, 1), new Object[] { " X ", "XYX", " X ", 'X', LOTRMod.termite, 'Y', Blocks.stone }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 2), new Object[] { LOTRMod.termite }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 15), new Object[] { new ItemStack(LOTRMod.wood3, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMangrove, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 15) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new Object[] { new ItemStack(LOTRMod.haradFlower, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new Object[] { new ItemStack(LOTRMod.haradFlower, 1, 1) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 5), new Object[] { new ItemStack(LOTRMod.haradFlower, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new Object[] { new ItemStack(LOTRMod.haradFlower, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.flaxSeeds, 2), new Object[] { LOTRMod.flaxPlant });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.string), new Object[] { LOTRMod.flax });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 0), new Object[] { new ItemStack(LOTRMod.wood4, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsChestnut, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 1), new Object[] { new ItemStack(LOTRMod.wood4, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBaobab, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 1) });
        for (int i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fence2, 3, i), new Object[] { "XYX", "XYX", 'X', new ItemStack(LOTRMod.planks2, 1, i), 'Y', "stickWood" }));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 2), new Object[] { new ItemStack(LOTRMod.wood4, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCedar, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.blackUrukSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage2, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 0), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.blackUrukSteel });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.utumnoKey, 1, 0), new Object[] { new ItemStack(LOTRMod.utumnoKey, 1, 2), new ItemStack(LOTRMod.utumnoKey, 1, 3), new ItemStack(LOTRMod.utumnoKey, 1, 4) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.utumnoKey, 1, 1), new Object[] { new ItemStack(LOTRMod.utumnoKey, 1, 5), new ItemStack(LOTRMod.utumnoKey, 1, 6), new ItemStack(LOTRMod.utumnoKey, 1, 7) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeIron), new Object[] { "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeBronze), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.bronze, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bronzeCrossbow), new Object[] { "XXY", "ZYX", "YZX", 'X', LOTRMod.bronze, 'Y', "stickWood", 'Z', Items.string }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.commandTable), new Object[] { "XXX", "YYY", "ZZZ", 'X', Items.paper, 'Y', "plankWood", 'Z', "ingotBronze" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.butterflyJar), new Object[] { "X", "Y", 'X', "plankWood", 'Y', Blocks.glass }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.berryPieItem), new Object[] { "AAA", "BBB", "CCC", 'A', Items.milk_bucket, 'B', "berry", 'C', Items.wheat }));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugBlueberryJuice), new Object[] { LOTRMod.blueberry, LOTRMod.blueberry, LOTRMod.blueberry });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugBlackberryJuice), new Object[] { LOTRMod.blackberry, LOTRMod.blackberry, LOTRMod.blackberry });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugRaspberryJuice), new Object[] { LOTRMod.raspberry, LOTRMod.raspberry, LOTRMod.raspberry });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugCranberryJuice), new Object[] { LOTRMod.cranberry, LOTRMod.cranberry, LOTRMod.cranberry });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugElderberryJuice), new Object[] { LOTRMod.elderberry, LOTRMod.elderberry, LOTRMod.elderberry });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick3, 1, 0), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 14) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick3, 1, 1), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick2, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.elfSteel, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage2, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 1), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.elfSteel });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 3), new Object[] { new ItemStack(LOTRMod.wood4, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsFir, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 4), new Object[] { new ItemStack(LOTRMod.wood5, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPine, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 4) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBone), new Object[] { "XXX", "X X", 'X', "bone" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBone), new Object[] { "X X", "XXX", "XXX", 'X', "bone" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsBone), new Object[] { "XXX", "X X", "X X", 'X', "bone" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBone), new Object[] { "X X", "X X", 'X', "bone" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.unsmeltery), new Object[] { "X X", "YXY", "ZZZ", 'X', "ingotIron", 'Y', "stickWood", 'Z', Blocks.cobblestone }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeBronze), new Object[] { " X ", " YX", "Y  ", 'X', "ingotBronze", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeIron), new Object[] { " X ", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bronzeBars, 16), new Object[] { "XXX", "XXX", 'X', "ingotBronze" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.goldBars, 16), new Object[] { "XXX", "XXX", 'X', "ingotGold" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.silverBars, 16), new Object[] { "XXX", "XXX", 'X', "ingotSilver" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mithrilBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.mithril }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planksRotten, 4, 0), new Object[] { new ItemStack(LOTRMod.rottenLog, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRotten, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planksRotten, 1, 0) });
        for (int i = 0; i <= 0; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceRotten, 3, i), new Object[] { "XYX", "XYX", 'X', new ItemStack(LOTRMod.planksRotten, 1, i), 'Y', "stickWood" }));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.scorchedSlabSingle, 6, 0), new Object[] { "XXX", 'X', LOTRMod.scorchedStone });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsScorchedStone, 4), new Object[] { "X  ", "XX ", "XXX", 'X', LOTRMod.scorchedStone });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.scorchedWall, 6), new Object[] { "XXX", "XXX", 'X', LOTRMod.scorchedStone });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 5), new Object[] { new ItemStack(LOTRMod.wood5, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLemon, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.lemonCakeItem), new Object[] { "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', LOTRMod.lemon, 'C', Items.sugar, 'D', Items.wheat });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 6), new Object[] { new ItemStack(LOTRMod.wood5, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsOrange, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 6) });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugOrangeJuice), new Object[] { LOTRMod.orange, LOTRMod.orange });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugLemonade), LOTRMod.mugWater, new Object[] { LOTRMod.lemon, Items.sugar });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.alloyForge), new Object[] { "XXX", "X X", "XXX", 'X', Blocks.stonebrick });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 7), new Object[] { new ItemStack(LOTRMod.wood5, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLime, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 7) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.compass), new Object[] { " X ", "XYX", " X ", 'X', "ingotIron", 'Y', "ingotCopper" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.clock), new Object[] { " X ", "XYX", " X ", 'X', "ingotGold", 'Y', "ingotCopper" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.obsidianShard, 9), new Object[] { Blocks.obsidian });
        GameRegistry.addRecipe(new ItemStack(Blocks.obsidian, 1), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.obsidianShard });
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(Blocks.mossy_cobblestone, 1, 0), new Object[] { new ItemStack(Blocks.cobblestone, 1, 0), "vine" }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new Object[] { new ItemStack(Blocks.stonebrick, 1, 0), "vine" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 8), new Object[] { new ItemStack(LOTRMod.wood6, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMahogany, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 8) });
        GameRegistry.addRecipe((IRecipe)new LOTRRecipesBanners());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.redSandstone, 1, 0), new Object[] { "XX", "XX", 'X', new ItemStack((Block)Blocks.sand, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.redSandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedSandstone, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.redSandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.thatch, 4, 1), new Object[] { "XX", "XX", 'X', LOTRMod.driedReeds });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleThatch, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.thatch, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsReed, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.thatch, 1, 1) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeIron), new Object[] { "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamV1, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(Blocks.log, 1, i) }));
        }
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.reedBars, 16), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.thatch, 1, 1) }));
        for (int i = 0; i <= 1; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamV2, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(Blocks.log2, 1, i) }));
        }
        for (int i = 0; i <= 3; ++i) {
            if (i != 1) {
                GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam1, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood, 1, i) }));
            }
        }
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.paper, 3), new Object[] { "XXX", 'X', LOTRMod.reeds }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.paper, 3), new Object[] { "XXX", 'X', LOTRMod.cornStalk }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.weaponRack), new Object[] { "X X", "YYY", 'X', "stickWood", 'Y', "plankWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wasteBlock, 4), new Object[] { "XY", "YZ", 'X', Items.rotten_flesh, 'Y', Blocks.dirt, 'Z', "bone" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.dirtPath, 2, 0), new Object[] { "XX", 'X', Blocks.dirt });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 9), new Object[] { new ItemStack(LOTRMod.wood6, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsWillow, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 9) });
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam2, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood2, 1, i) }));
        }
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamFruit, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.fruitWood, 1, i) }));
        }
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam3, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood3, 1, i) }));
        }
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam4, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood4, 1, i) }));
        }
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam5, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood5, 1, i) }));
        }
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam6, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood6, 1, i) }));
        }
        for (int i = 0; i <= 0; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamRotten, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rottenLog, 1, i) }));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick4, 4, 15), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsChalkBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 15) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 6), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 7), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 15) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.smoothStone, 2, 5), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStone, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 15) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar2, 3, 1), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateChalk), new Object[] { "XX", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonChalk), new Object[] { new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(Blocks.stone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 3), new Object[] { "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 4), new Object[] { "XXX", "XXX", 'X', new ItemStack(Blocks.sandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 5), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.redSandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 6), new Object[] { "XXX", "XXX", 'X', new ItemStack(Blocks.brick_block, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsStoneBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsStoneBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2) });
        GameRegistry.addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, 5), new Object[] { "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 0), new Object[] { "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 1), new Object[] { "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsStone, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBlueRock, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedRock, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsChalk, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 5) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 3), new Object[] { new ItemStack(LOTRMod.clover, 1, 32767) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.clayTile, 4, 0), new Object[] { "XX", "XX", 'X', new ItemStack(Blocks.hardened_clay, 1, 0) });
        for (int i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.clayTileDyed, 8, i), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(LOTRMod.clayTile, 1, 0), 'Y', LOTRRecipes.dyeOreNames[BlockColored.func_150032_b(i)] }));
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.clayTileDyed, 4, i), new Object[] { "XX", "XX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, i) }));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabClayTileSingle, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.clayTile, 1, 0) });
        for (int i = 0; i <= 7; ++i) {
            GameRegistry.addRecipe(new ItemStack(LOTRMod.slabClayTileDyedSingle, 6, i), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, i) });
            GameRegistry.addRecipe(new ItemStack(LOTRMod.slabClayTileDyedSingle2, 6, i), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, i + 8) });
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTile, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTile, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedWhite, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedOrange, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedMagenta, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedLightBlue, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedYellow, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedLime, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedPink, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 6) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedGray, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 7) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedLightGray, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 8) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedCyan, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 9) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedPurple, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 10) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedBlue, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 11) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedBrown, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 12) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedGreen, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 13) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedRed, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 14) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedBlack, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, 15) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar2, 3, 2), new Object[] { "X", "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 4), new Object[] { new ItemStack(Items.coal, 1, 1) });
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.dye, 3, 5), new Object[] { "dyeRed", "dyeYellow", "dyeBlack" }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.dye, 2, 5), new Object[] { "dyeOrange", "dyeBlack" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), new Object[] { LOTRMod.simbelmyne });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 12), new Object[] { LOTRMod.dwarfHerb });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 10), new Object[] { new ItemStack(LOTRMod.fangornPlant, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 5), new Object[] { new ItemStack(LOTRMod.fangornPlant, 1, 1) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new Object[] { new ItemStack(LOTRMod.fangornPlant, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new Object[] { new ItemStack(LOTRMod.fangornPlant, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new Object[] { new ItemStack(LOTRMod.fangornPlant, 1, 4) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 7), new Object[] { new ItemStack(LOTRMod.fangornPlant, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar2, 3, 3), new Object[] { "X", "X", "X", 'X', Blocks.brick_block });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 3) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateGear, 4), new Object[] { " X ", "XYX", " X ", 'X', "ingotIron", 'Y', "plankWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateWooden, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "ingotIron" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateIronBars, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', "ingotIron" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateBronzeBars, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', "ingotBronze" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateWoodenCross, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.saddle), new Object[] { "XXX", "Y Y", 'X', LOTRMod.fur, 'Y', "ingotIron" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.furBedItem), new Object[] { "XXX", "YYY", 'X', LOTRMod.fur, 'Y', "plankWood" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoPillar, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoPillar, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoPillar, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoBrickFire, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoBrickIce, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoBrickObsidian, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 4) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.halberdMithril), new Object[] { " XX", " YX", "Y  ", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateSilver, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', "ingotSilver" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateGold, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', "ingotGold" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateMithril, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', LOTRMod.mithril }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick5, 4, 0), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.mud, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMudBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 8), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.leekSoup), new Object[] { Items.bowl, LOTRMod.leek, LOTRMod.leek, Items.potato });
        GameRegistry.addRecipe((IRecipe)new LOTRRecipesPoisonDrinks());
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 10), new Object[] { new ItemStack(LOTRMod.wood6, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCypress, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 10) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 11), new Object[] { new ItemStack(LOTRMod.wood6, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsOlive, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 11) });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugAppleJuice), new Object[] { "apple", "apple" });
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.redBrick, 1, 0), new Object[] { new ItemStack(Blocks.brick_block, 1, 0), "vine" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.redBrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.redBrick, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.redBrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.redBrick, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 7), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.redBrick, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 8), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.redBrick, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(Blocks.dirt, 4, 1), new Object[] { "XY", "YX", 'X', new ItemStack(Blocks.dirt, 1, 0), 'Y', Blocks.gravel });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.oliveBread), new Object[] { "XYX", 'X', Items.wheat, 'Y', LOTRMod.olive });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rollingPin), new Object[] { "XYX", 'X', "stickWood", 'Y', "plankWood" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.seedsGrapeRed), new Object[] { LOTRMod.grapeRed });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.seedsGrapeWhite), new Object[] { LOTRMod.grapeWhite });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugRedGrapeJuice), new Object[] { LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugWhiteGrapeJuice), new Object[] { LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.grapevine), new Object[] { "X", "X", "X", 'X', "stickWood" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.melonSoup), new Object[] { Items.bowl, Items.melon, Items.melon, Items.sugar });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 12), new Object[] { new ItemStack(LOTRMod.wood7, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsAspen, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 12) });
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam7, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood7, 1, i) }));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 13), new Object[] { new ItemStack(LOTRMod.wood7, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsGreenOak, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 13) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 14), new Object[] { new ItemStack(LOTRMod.wood7, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLairelosse, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 14) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 15), new Object[] { new ItemStack(LOTRMod.wood7, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsAlmond, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 15) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.bottlePoison), new Object[] { Items.glass_bottle, LOTRMod.wildberry });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 0), new Object[] { "XXX", 'X', new ItemStack(Blocks.dirt, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.dirtPath, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.mud, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.mordorDirt, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleSand, 6, 0), new Object[] { "XXX", 'X', new ItemStack((Block)Blocks.sand, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleSand, 6, 1), new Object[] { "XXX", 'X', new ItemStack((Block)Blocks.sand, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleGravel, 6, 0), new Object[] { "XXX", 'X', new ItemStack(Blocks.gravel, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleGravel, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.mordorGravel, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleGravel, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.obsidianGravel, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.blackrootStick, 2), new Object[] { LOTRMod.blackroot });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 0), new Object[] { new ItemStack(LOTRMod.wood8, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPlum, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 0) });
        for (int i = 0; i <= 5; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fence3, 3, i), new Object[] { "XYX", "XYX", 'X', new ItemStack(LOTRMod.planks3, 1, i), 'Y', "stickWood" }));
        }
        for (int i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam8, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood8, 1, i) }));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.whiteSandstone, 1, 0), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.whiteSand, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.whiteSandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsWhiteSandstone, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.whiteSandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 14), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.whiteSandstone, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleSand, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.whiteSand, 1, 0) });
        LOTRBlockTreasurePile.generateTreasureRecipes(LOTRMod.treasureCopper, LOTRMod.copper);
        LOTRBlockTreasurePile.generateTreasureRecipes(LOTRMod.treasureSilver, LOTRMod.silver);
        LOTRBlockTreasurePile.generateTreasureRecipes(LOTRMod.treasureGold, Items.gold_ingot);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.chestBasket), new Object[] { "XXX", "X X", "XXX", 'X', LOTRMod.driedReeds });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new Object[] { LOTRMod.marigold });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 2), new Object[] { new ItemStack(LOTRMod.rhunFlower, 1, 0) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new Object[] { new ItemStack(LOTRMod.rhunFlower, 1, 1) });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new Object[] { new ItemStack(LOTRMod.rhunFlower, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new Object[] { new ItemStack(LOTRMod.rhunFlower, 1, 3) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), new Object[] { new ItemStack(LOTRMod.rhunFlower, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 4), new Object[] { "XXX", 'X', Blocks.mossy_cobblestone });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCobblestoneMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', Blocks.mossy_cobblestone });
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 5), new Object[] { new ItemStack(LOTRMod.doubleFlower, 1, 0) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearStone), new Object[] { "  X", " Y ", "Y  ", 'X', "cobblestone", 'Y', "stickWood" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.marzipan), new Object[] { LOTRMod.almond, LOTRMod.almond, Items.sugar });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.marzipanChocolate), new Object[] { LOTRMod.marzipan, new ItemStack(Items.dye, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.marzipanBlock), new Object[] { "XXX", 'X', LOTRMod.marzipan });
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePartyHatDye());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallClayTile, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.clayTile, 1, 0) });
        for (int i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe(new ItemStack(LOTRMod.wallClayTileDyed, 6, i), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.clayTileDyed, 1, i) });
        }
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gobletGold, 2), new Object[] { "X X", " X ", " X ", 'X', "ingotGold" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gobletSilver, 2), new Object[] { "X X", " X ", " X ", 'X', "ingotSilver" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gobletCopper, 2), new Object[] { "X X", " X ", " X ", 'X', "ingotCopper" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gobletWood, 2), new Object[] { "X X", " X ", " X ", 'X', "plankWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.skullCup), new Object[] { "X", "Y", 'X', new ItemStack(Items.skull, 1, 0), 'Y', "ingotTin" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wineGlass, 2), new Object[] { "X X", " X ", " X ", 'X', Blocks.glass }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), new Object[] { " Y ", "X X", " X ", 'X', Items.leather, 'Y', Items.string }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), new Object[] { " Y ", "X X", " X ", 'X', LOTRMod.gemsbokHide, 'Y', Items.string }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), new Object[] { " Y ", "X X", " X ", 'X', LOTRMod.fur, 'Y', Items.string }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), new Object[] { " Y ", "X X", " X ", 'X', LOTRMod.lionFur, 'Y', Items.string }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.aleHorn), new Object[] { "X", "Y", 'X', "horn", 'Y', "ingotTin" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.aleHornGold), new Object[] { "X", "Y", 'X', "horn", 'Y', "ingotGold" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.oreGlowstone), new Object[] { "XXX", "XYX", "XXX", 'X', Items.glowstone_dust, 'Y', new ItemStack(Blocks.stone, 1, 0) }));
        GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 1, 3), new Object[] { "XX", "XX", 'X', new ItemStack(Blocks.stonebrick, 1, 0) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 0), new Object[] { "YYY", "Y Y", "XXX", 'X', "ingotBronze", 'Y', LOTRMod.bronzeBars }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 1), new Object[] { "YYY", "Y Y", "XXX", 'X', "ingotIron", 'Y', Blocks.iron_bars }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 2), new Object[] { "YYY", "Y Y", "XXX", 'X', "ingotSilver", 'Y', LOTRMod.silverBars }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 3), new Object[] { "YYY", "Y Y", "XXX", 'X', "ingotGold", 'Y', LOTRMod.goldBars }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.birdCageWood, 1, 0), new Object[] { "YYY", "Y Y", "XXX", 'X', "plankWood", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chisel), new Object[] { "XY", 'X', "ingotIron", 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 5), new Object[] { "XXX", 'X', new ItemStack(Blocks.stone, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStoneV, 2, 0), new Object[] { "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack((Block)Blocks.stone_slab, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStoneV, 1, 0) }));
        for (final LOTRBlockFallenLeaves fallenLeafBlock : LOTRBlockFallenLeaves.allFallenLeaves) {
            for (final Block leafBlock : fallenLeafBlock.getLeafBlocks()) {
                if (leafBlock instanceof LOTRBlockLeavesBase) {
                    final String[] leafNames = ((LOTRBlockLeavesBase)leafBlock).getAllLeafNames();
                    for (int leafMeta = 0; leafMeta < leafNames.length; ++leafMeta) {
                        final Object[] fallenBlockMeta = LOTRBlockFallenLeaves.fallenBlockMetaFromLeafBlockMeta(leafBlock, leafMeta);
                        if (fallenBlockMeta != null) {
                            final Block fallenBlock = (Block)fallenBlockMeta[0];
                            final int fallenMeta = (int)fallenBlockMeta[1];
                            if (fallenBlock != null) {
                                GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(fallenBlock, 6, fallenMeta), new Object[] { "XXX", 'X', new ItemStack(leafBlock, 1, leafMeta) }));
                            }
                        }
                    }
                }
            }
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 1), new Object[] { new ItemStack(LOTRMod.wood8, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedwood, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 1) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daub, 4), new Object[] { "XYX", "YXY", "XYX", 'X', "stickWood", 'Y', Blocks.dirt }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daub, 4), new Object[] { "XYX", "YXY", "XYX", 'X', LOTRMod.driedReeds, 'Y', Blocks.dirt }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.kebabBlock, 1, 0), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.kebab });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.kebab, 9), new Object[] { new ItemStack(LOTRMod.kebabBlock, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 2), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.gildedIron });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.gildedIron, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage2, 1, 2) });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 2), new Object[] { new ItemStack(LOTRMod.wood8, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPomegranate, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 2) });
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugPomegranateJuice), new Object[] { LOTRMod.pomegranate, LOTRMod.pomegranate });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.item_frame), new Object[] { "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', LOTRMod.fur }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mattockMithril), new Object[] { "XXX", "XY ", " Y ", 'X', LOTRMod.mithril, 'Y', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.gammon), new Object[] { Items.cooked_porkchop, "salt" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 3), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.salt });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.salt, 9), new Object[] { new ItemStack(LOTRMod.blockOreStorage2, 1, 3) });
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.saltedFlesh), new Object[] { Items.rotten_flesh, "salt" }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.cornBread), new Object[] { "XXX", 'X', LOTRMod.corn });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Blocks.fence_gate, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateSpruce, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 1) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBirch, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateJungle, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateAcacia, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateDarkOak, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateShirePine, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMirkOak, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCharred, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateApple, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePear, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCherry, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 6) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMango, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 7) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLebethron, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 8) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBeech, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 9) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateHolly, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 10) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBanana, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 11) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMaple, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 12) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLarch, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 13) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateDatePalm, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 14) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMangrove, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks, 1, 15) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateChestnut, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBaobab, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 1) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCedar, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateFir, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePine, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLemon, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateOrange, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 6) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLime, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 7) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMahogany, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 8) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateWillow, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 9) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCypress, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 10) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateOlive, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 11) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateAspen, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 12) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateGreenOak, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 13) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLairelosse, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 14) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateAlmond, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks2, 1, 15) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateRotten, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planksRotten, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePlum, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks3, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateRedwood, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks3, 1, 1) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePomegranate, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks3, 1, 2) }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle2, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 6) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle2, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 7) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle2, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 8) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoTileIce, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 6) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoTileObsidian, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 7) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoTileFire, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 8) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 3), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 6) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 4), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 7) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 5), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.utumnoBrick, 1, 8) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.millstone), new Object[] { "XYX", "XZX", "XXX", 'X', Blocks.cobblestone, 'Y', "ingotIron", 'Z', "stickWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.millstone), new Object[] { "XYX", "XZX", "XXX", 'X', Blocks.cobblestone, 'Y', "ingotBronze", 'Z', "stickWood" }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.topaz, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 0), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.topaz });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.amethyst, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 1), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.amethyst });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.sapphire, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 2), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.sapphire });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.ruby, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 3), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.ruby });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.amber, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 4) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 4), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.amber });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.diamond, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 5) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 5), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.diamond });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pearl, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 6) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 6), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.pearl });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.opal, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 7) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 7), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.opal });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mushroomPie), new Object[] { Items.egg, Blocks.red_mushroom, Blocks.brown_mushroom });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.coral, 4), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 8) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 8), new Object[] { "XX", "XX", 'X', LOTRMod.coral });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.emerald, 9), new Object[] { new ItemStack(LOTRMod.blockGem, 1, 9) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 9), new Object[] { "XXX", "XXX", "XXX", 'X', LOTRMod.emerald });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.glass, 4), new Object[] { "XX", "XX", 'X', Blocks.glass });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.glassPane, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.glass });
        for (int i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe(new ItemStack(LOTRMod.stainedGlass, 4, i), new Object[] { "XX", "XX", 'X', new ItemStack((Block)Blocks.stained_glass, 1, i) });
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stainedGlass, 8, i), new Object[] { "XXX", "XYX", "XXX", 'X', LOTRMod.glass, 'Y', LOTRRecipes.dyeOreNames[BlockColored.func_150031_c(i)] }));
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stainedGlassPane, 16, i), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.stainedGlass, 1, i) }));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.rope, 3), new Object[] { "X", "X", "X", 'X', Items.string });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithrilMail, 8), new Object[] { "XX", "XX", 'X', LOTRMod.mithril });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithril, 2), new Object[] { "XX", "XX", 'X', LOTRMod.mithrilMail });
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 3), new Object[] { new ItemStack(LOTRMod.wood8, 1, 3) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPalm, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 3) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePalm, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks3, 1, 3) }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 4), new Object[] { new ItemStack(LOTRMod.wood9, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsDragon, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 4) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateDragon, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks3, 1, 4) }));
        for (int i = 0; i <= 1; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam9, 3, i), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood9, 1, i) }));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 4, 1), new Object[] { new ItemStack(LOTRMod.wood9, 1, 0), new ItemStack(LOTRMod.wood9, 1, 0) });
        GameRegistry.addRecipe(new ItemStack(Blocks.packed_ice), new Object[] { "XX", "XX", 'X', Blocks.ice });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodPlate, 2), new Object[] { "XX", 'X', "logWood" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.item_frame), new Object[] { "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', LOTRMod.gemsbokHide }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.wooden_door), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorSpruce), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 1) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorBirch), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorJungle), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorAcacia), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorDarkOak), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorShirePine), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorMirkOak), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorCharred), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorApple), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorPear), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorCherry), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 6) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorMango), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 7) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorLebethron), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 8) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorBeech), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 9) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorHolly), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 10) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorBanana), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 11) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorMaple), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 12) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorLarch), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 13) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorDatePalm), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 14) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorMangrove), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 15) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorChestnut), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorBaobab), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 1) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorCedar), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorFir), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorPine), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorLemon), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorOrange), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 6) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorLime), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 7) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorMahogany), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 8) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorWillow), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 9) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorCypress), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 10) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorOlive), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 11) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorAspen), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 12) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorGreenOak), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 13) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorLairelosse), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 14) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorAlmond), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks2, 1, 15) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorPlum), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks3, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorRedwood), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks3, 1, 1) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorPomegranate), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks3, 1, 2) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorPalm), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks3, 1, 3) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorDragon), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks3, 1, 4) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorRotten), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planksRotten, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(Blocks.dirt, 1, 0), new Object[] { new ItemStack(Blocks.dirt, 1, 1), Items.wheat_seeds }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.boneBlock, 1, 0), new Object[] { "XX", "XX", 'X', "bone" }));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 8, 15), new Object[] { LOTRMod.boneBlock });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabBoneSingle, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.boneBlock, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsBone, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.boneBlock, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wallBone, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.boneBlock, 1, 0) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.redClay), new Object[] { "XX", "XX", 'X', LOTRMod.redClayBall }));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 5), new Object[] { new ItemStack(LOTRMod.wood9, 1, 1) });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsKanuka, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 5) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateKanuka, 1), new Object[] { "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(LOTRMod.planks3, 1, 5) }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorKanuka), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks3, 1, 5) }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mud, 4, 1), new Object[] { "XY", "YX", 'X', new ItemStack(LOTRMod.mud, 1, 0), 'Y', Blocks.gravel });
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.mud, 1, 0), new Object[] { new ItemStack(LOTRMod.mud, 1, 1), Items.wheat_seeds }));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.dirtPath, 2, 1), new Object[] { "XX", 'X', LOTRMod.mud });
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.dirtPath, 1, 1) });
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Items.saddle), new Object[] { "XXX", "Y Y", 'X', LOTRMod.gemsbokHide, 'Y', "ingotIron" }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brandingIron), new Object[] { "  X", " Y ", "X  ", 'X', Items.iron_ingot, 'Y', Items.leather }));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brandingIron), new Object[] { "  X", " Y ", "X  ", 'X', Items.iron_ingot, 'Y', LOTRMod.gemsbokHide }));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brandingIron), new Object[] { new ItemStack(LOTRMod.brandingIron, 1, 32767), Items.iron_ingot }));
    }
    
    private static void createPoisonedDaggerRecipes() {
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerOrc, LOTRMod.daggerOrcPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerUruk, LOTRMod.daggerUrukPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerBronze, LOTRMod.daggerBronzePoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerIron, LOTRMod.daggerIronPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerMithril, LOTRMod.daggerMithrilPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerGondor, LOTRMod.daggerGondorPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerElven, LOTRMod.daggerElvenPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerDwarven, LOTRMod.daggerDwarvenPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerRohan, LOTRMod.daggerRohanPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerWoodElven, LOTRMod.daggerWoodElvenPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerAngmar, LOTRMod.daggerAngmarPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerHighElven, LOTRMod.daggerHighElvenPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerNearHarad, LOTRMod.daggerNearHaradPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerBlueDwarven, LOTRMod.daggerBlueDwarvenPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerDolGuldur, LOTRMod.daggerDolGuldurPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerBlackUruk, LOTRMod.daggerBlackUrukPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerUtumno, LOTRMod.daggerUtumnoPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerHalfTroll, LOTRMod.daggerHalfTrollPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerMoredain, LOTRMod.daggerMoredainPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerTauredain, LOTRMod.daggerTauredainPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerBarrow, LOTRMod.daggerBarrowPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerDolAmroth, LOTRMod.daggerDolAmrothPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerDale, LOTRMod.daggerDalePoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerGundabadUruk, LOTRMod.daggerGundabadUrukPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerDorwinionElf, LOTRMod.daggerDorwinionElfPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerRhun, LOTRMod.daggerRhunPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerRivendell, LOTRMod.daggerRivendellPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerArnor, LOTRMod.daggerArnorPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerCorsair, LOTRMod.daggerCorsairPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerHarad, LOTRMod.daggerHaradPoisoned));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.daggerBlackNumenorean, LOTRMod.daggerBlackNumenoreanPoisoned));
    }
    
    private static void createPoisonedArrowRecipes() {
        final List recipeLists = new ArrayList();
        recipeLists.addAll(Arrays.asList((List[])LOTRRecipes.commonOrcRecipes));
        recipeLists.addAll(Arrays.asList((List[])LOTRRecipes.commonNearHaradRecipes));
        for (final Object obj : recipeLists) {
            final List recipes = (List)obj;
            recipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.arrowPoisoned, 4), new Object[] { " X ", "XYX", " X ", 'X', Items.arrow, 'Y', "poison" }));
            recipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.crossbowBoltPoisoned, 4), new Object[] { " X ", "XYX", " X ", 'X', LOTRMod.crossbowBolt, 'Y', "poison" }));
        }
    }
    
    private static void createWoodenSlabRecipes() {
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 0) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 2) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 3) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 4) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 5) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 6) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 7) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 8) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 9) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 10) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 11) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 12) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 13) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 14) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 15) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 0) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 1) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 2) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 3) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 4) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 5) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 6) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 7) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 8) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 9) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 10) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 11) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 12) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 13) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 14) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks2, 1, 15) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 0) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 1) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 2) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 3) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 4) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks3, 1, 5) }));
        LOTRRecipes.woodenSlabRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rottenSlabSingle, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planksRotten, 1, 0) }));
    }
    
    private static void removeStandardRecipes() {
        final List recipeList = CraftingManager.getInstance().getRecipeList();
        removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence));
        removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence_gate));
        removeRecipesItem(recipeList, Items.wooden_door);
        if (LOTRConfig.removeGoldenAppleRecipes) {
            removeRecipesItem(recipeList, Items.golden_apple);
        }
        if (LOTRConfig.removeDiamondArmorRecipes) {
            removeRecipesItem(recipeList, (Item)Items.diamond_helmet);
            removeRecipesItem(recipeList, (Item)Items.diamond_chestplate);
            removeRecipesItem(recipeList, (Item)Items.diamond_leggings);
            removeRecipesItem(recipeList, (Item)Items.diamond_boots);
        }
        removeRecipesItemStack(recipeList, new ItemStack((Block)Blocks.stone_slab, 1, 0));
        removeRecipesItemStack(recipeList, new ItemStack((Block)Blocks.stone_slab, 1, 5));
        removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.stone_brick_stairs));
        removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.anvil));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(Blocks.anvil), new Object[] { "XXX", " Y ", "XXX", 'X', "ingotIron", 'Y', "blockIron" }));
        removeRecipesClass(recipeList, (Class<? extends IRecipe>)RecipesArmorDyes.class);
        GameRegistry.addRecipe((IRecipe)new LOTRRecipesArmorDyes());
    }
    
    private static void removeRecipesItem(final List recipeList, final Item outputItem) {
        final List recipesToRemove = new ArrayList();
        for (final Object obj : recipeList) {
            final IRecipe recipe = (IRecipe)obj;
            final ItemStack output = recipe.getRecipeOutput();
            if (output != null && output.getItem() == outputItem) {
                recipesToRemove.add(recipe);
            }
        }
        recipeList.removeAll(recipesToRemove);
    }
    
    private static void removeRecipesItemStack(final List recipeList, final ItemStack outputItemStack) {
        final List recipesToRemove = new ArrayList();
        for (final Object obj : recipeList) {
            final IRecipe recipe = (IRecipe)obj;
            final ItemStack output = recipe.getRecipeOutput();
            if (output != null && output.isItemEqual(outputItemStack)) {
                recipesToRemove.add(recipe);
            }
        }
        recipeList.removeAll(recipesToRemove);
    }
    
    private static void removeRecipesClass(final List recipeList, final Class<? extends IRecipe> recipeClass) {
        final List recipesToRemove = new ArrayList();
        for (final Object obj : recipeList) {
            final IRecipe recipe = (IRecipe)obj;
            if (recipeClass.isAssignableFrom(recipe.getClass())) {
                recipesToRemove.add(recipe);
            }
        }
        recipeList.removeAll(recipesToRemove);
    }
    
    private static void createSmeltingRecipes() {
        for (final Object obj : Block.blockRegistry) {
            final Block block = (Block)obj;
            if (block instanceof LOTRBlockWoodBase) {
                GameRegistry.addSmelting(block, new ItemStack(Items.coal, 1, 1), 0.15f);
            }
        }
        GameRegistry.addSmelting(Blocks.stone, new ItemStack(LOTRMod.scorchedStone), 0.1f);
        GameRegistry.addSmelting(LOTRMod.rock, new ItemStack(LOTRMod.scorchedStone), 0.1f);
        GameRegistry.addSmelting(LOTRMod.whiteSand, new ItemStack(Blocks.glass), 0.1f);
        GameRegistry.addSmelting(LOTRMod.oreCopper, new ItemStack(LOTRMod.copper), 0.35f);
        GameRegistry.addSmelting(LOTRMod.oreTin, new ItemStack(LOTRMod.tin), 0.35f);
        GameRegistry.addSmelting(LOTRMod.oreSilver, new ItemStack(LOTRMod.silver), 0.8f);
        GameRegistry.addSmelting(LOTRMod.oreNaurite, new ItemStack(LOTRMod.nauriteGem), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreQuendite, new ItemStack(LOTRMod.quenditeCrystal), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreGlowstone, new ItemStack(Items.glowstone_dust), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreGulduril, new ItemStack(LOTRMod.guldurilCrystal), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreSulfur, new ItemStack(LOTRMod.sulfur), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreSaltpeter, new ItemStack(LOTRMod.saltpeter), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreSalt, new ItemStack(LOTRMod.salt), 1.0f);
        GameRegistry.addSmelting(LOTRMod.clayMug, new ItemStack(LOTRMod.ceramicMug), 0.3f);
        GameRegistry.addSmelting(LOTRMod.clayPlate, new ItemStack(LOTRMod.ceramicPlate), 0.3f);
        GameRegistry.addSmelting(LOTRMod.ceramicPlate, new ItemStack(LOTRMod.plate), 0.3f);
        GameRegistry.addSmelting(LOTRMod.redClayBall, new ItemStack(Items.brick), 0.3f);
        GameRegistry.addSmelting(LOTRMod.redClay, new ItemStack(Blocks.hardened_clay), 0.35f);
        GameRegistry.addSmelting(LOTRMod.rabbitRaw, new ItemStack(LOTRMod.rabbitCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.lionRaw, new ItemStack(LOTRMod.lionCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.zebraRaw, new ItemStack(LOTRMod.zebraCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.rhinoRaw, new ItemStack(LOTRMod.rhinoCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.muttonRaw, new ItemStack(LOTRMod.muttonCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.deerRaw, new ItemStack(LOTRMod.deerCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.camelRaw, new ItemStack(LOTRMod.camelCooked), 0.35f);
        GameRegistry.addSmelting(new ItemStack(LOTRMod.reeds, 1, 0), new ItemStack(LOTRMod.driedReeds), 0.25f);
        GameRegistry.addSmelting(LOTRMod.pipeweedLeaf, new ItemStack(LOTRMod.pipeweed), 0.25f);
        GameRegistry.addSmelting(LOTRMod.chestnut, new ItemStack(LOTRMod.chestnutRoast), 0.3f);
        GameRegistry.addSmelting(LOTRMod.corn, new ItemStack(LOTRMod.cornCooked), 0.3f);
        GameRegistry.addSmelting(LOTRMod.turnip, new ItemStack(LOTRMod.turnipCooked), 0.3f);
        GameRegistry.addSmelting(LOTRMod.yam, new ItemStack(LOTRMod.yamRoast), 0.3f);
        GameRegistry.addSmelting(LOTRMod.grapeRed, new ItemStack(LOTRMod.raisins), 0.3f);
        GameRegistry.addSmelting(LOTRMod.grapeWhite, new ItemStack(LOTRMod.raisins), 0.3f);
        addSmeltingXPForItem(LOTRMod.bronze, 0.7f);
        addSmeltingXPForItem(LOTRMod.mithril, 1.0f);
        addSmeltingXPForItem(LOTRMod.orcSteel, 0.7f);
        addSmeltingXPForItem(LOTRMod.dwarfSteel, 0.7f);
        addSmeltingXPForItem(LOTRMod.galvorn, 0.8f);
        addSmeltingXPForItem(LOTRMod.urukSteel, 0.7f);
        addSmeltingXPForItem(LOTRMod.morgulSteel, 0.8f);
        addSmeltingXPForItem(LOTRMod.blueDwarfSteel, 0.7f);
        addSmeltingXPForItem(LOTRMod.blackUrukSteel, 0.7f);
        addSmeltingXPForItem(LOTRMod.elfSteel, 0.7f);
        addSmeltingXPForItem(LOTRMod.ithildin, 0.8f);
        addSmeltingXPForItem(LOTRMod.gildedIron, 0.7f);
    }
    
    private static void addSmeltingXPForItem(final Item item, final float xp) {
        try {
            final Field field = FurnaceRecipes.class.getDeclaredFields()[2];
            field.setAccessible(true);
            final HashMap map = (HashMap)field.get(FurnaceRecipes.smelting());
            map.put(new ItemStack(item, 1, 32767), xp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void addRecipeTo(final List[] recipeLists, final IRecipe recipe) {
        for (final List list : recipeLists) {
            list.add(recipe);
        }
    }
    
    private static void createCommonOrcRecipes() {
        addRecipeTo(LOTRRecipes.commonOrcRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcBedItem), new Object[] { "XXX", "YYY", 'X', Blocks.wool, 'Y', "plankWood" }));
        addRecipeTo(LOTRRecipes.commonOrcRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.maggotyBread), new Object[] { "XXX", 'X', Items.wheat }));
        for (int i = 0; i <= 2; ++i) {
            addRecipeTo(LOTRRecipes.commonOrcRecipes, (IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, i + 8), new Object[] { new ItemStack(LOTRMod.orcBomb, 1, i), Items.lava_bucket }));
        }
        addRecipeTo(LOTRRecipes.commonOrcRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcSkullStaff), new Object[] { "X", "Y", "Y", 'X', Items.skull, 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonOrcRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcChain, 8), new Object[] { "X", "X", "X", 'X', LOTRMod.orcSteel }));
    }
    
    private static void createCommonMorgulRecipes() {
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.morgulBlade), new Object[] { "X", "X", "Y", 'X', LOTRMod.morgulSteel, 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetMorgul), new Object[] { "XXX", "X X", 'X', LOTRMod.morgulSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyMorgul), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.morgulSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsMorgul), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.morgulSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsMorgul), new Object[] { "X X", "X X", 'X', LOTRMod.morgulSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.morgulTorch, 4), new Object[] { "X", "Y", 'X', LOTRMod.guldurilCrystal, 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorMorgul), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.morgulSteel, 'Y', Items.leather }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcSteelBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.orcSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.orcSteel, 'Y', Items.string }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcBomb, 4), new Object[] { "XYX", "YXY", "XYX", 'X', Items.gunpowder, 'Y', LOTRMod.orcSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 1), new Object[] { new ItemStack(LOTRMod.orcBomb, 1, 0), Items.gunpowder, LOTRMod.orcSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 2), new Object[] { new ItemStack(LOTRMod.orcBomb, 1, 1), Items.gunpowder, LOTRMod.orcSteel }));
        addRecipeTo(LOTRRecipes.commonMorgulRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 12), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.morgulTorch, 'Z', LOTRMod.morgulSteel }));
    }
    
    private static void createCommonElfRecipes() {
        addRecipeTo(LOTRRecipes.commonElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGalvorn), new Object[] { "XXX", "X X", 'X', LOTRMod.galvorn }));
        addRecipeTo(LOTRRecipes.commonElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGalvorn), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.galvorn }));
        addRecipeTo(LOTRRecipes.commonElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsGalvorn), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.galvorn }));
        addRecipeTo(LOTRRecipes.commonElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGalvorn), new Object[] { "X X", "X X", 'X', LOTRMod.galvorn }));
        addRecipeTo(LOTRRecipes.commonElfRecipes, (IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.chisel, LOTRMod.chiselIthildin, new ItemStack(LOTRMod.ithildin, 1, 0)));
    }
    
    private static void createCommonHighElfRecipes() {
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenTorch, 4), new Object[] { "X", "Y", 'X', LOTRMod.quenditeCrystal, 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 10), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.highElvenTorch, 'Z', LOTRMod.elfSteel }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenBedItem), new Object[] { "XXX", "YYY", 'X', Blocks.wool, 'Y', "plankWood" }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 2), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick3, 1, 3), new Object[] { new ItemStack(LOTRMod.brick3, 1, 2), "vine" }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 3) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 4) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsHighElvenBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsHighElvenBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 3) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsHighElvenBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 4) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 11), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 12), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 3) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 13), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 4) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 10), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 10) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 11) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 13), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick3, 1, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.highElfBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.highElfWoodBars, 16), new Object[] { "XXX", "XXX", 'X', "plankWood" }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 8), new Object[] { " X ", "XYX", " X ", 'X', "nuggetSilver", 'Y', new ItemStack(LOTRMod.brick3, 1, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 11), new Object[] { " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(LOTRMod.brick3, 1, 2) }));
        addRecipeTo(LOTRRecipes.commonHighElfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateHighElven, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', LOTRMod.elfSteel }));
    }
    
    private static void createCommonDwarfRecipes() {
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 6), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDwarvenBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 7), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 0), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenDoor), new Object[] { "XX", "XX", "XX", 'X', Blocks.stone }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenDoorIthildin), new Object[] { "XX", "XY", "XX", 'X', Blocks.stone, 'Y', new ItemStack(LOTRMod.ithildin, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenBedItem), new Object[] { "XXX", "YYY", 'X', Blocks.wool, 'Y', "plankWood" }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 8), new Object[] { " X ", "XYX", " X ", 'X', "nuggetSilver", 'Y', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 9), new Object[] { " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 10), new Object[] { " X ", "XYX", " X ", 'X', LOTRMod.mithrilNugget, 'Y', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 12), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 1, 12), new Object[] { " X ", "XYX", " X ", 'X', Items.glowstone_dust, 'Y', new ItemStack(LOTRMod.brick, 1, 6) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDwarvenBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 5) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 5) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 5), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 5) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 14), new Object[] { "XX", "XX", 'X', LOTRMod.obsidianShard }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDwarvenBrickObsidian, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 14) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 14) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 6), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 14) }));
        addRecipeTo(LOTRRecipes.commonDwarfRecipes, (IRecipe)new LOTRRecipePoisonWeapon(LOTRMod.chisel, LOTRMod.chiselIthildin, new ItemStack(LOTRMod.ithildin, 1, 0)));
    }
    
    private static void createCommonDunedainRecipes() {
    }
    
    private static void createCommonNumenoreanRecipes() {
        addRecipeTo(LOTRRecipes.commonNumenoreanRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 11), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonNumenoreanRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 11) }));
        addRecipeTo(LOTRRecipes.commonNumenoreanRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsBlackGondorBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 11) }));
        addRecipeTo(LOTRRecipes.commonNumenoreanRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 10), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 11) }));
        addRecipeTo(LOTRRecipes.commonNumenoreanRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 9), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonNumenoreanRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 9) }));
    }
    
    private static void createCommonNearHaradRecipes() {
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 15), new Object[] { "XX", "XX", 'X', new ItemStack(Blocks.sandstone, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 15) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 15) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 15), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 15) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 5), new Object[] { "X", "X", "X", 'X', new ItemStack(Blocks.sandstone, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 5) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 1, 8), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 15) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 11) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 11) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 3), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 11) }));
        addDyeableWoolRobeRecipes(LOTRRecipes.commonNearHaradRecipes, new ItemStack(LOTRMod.helmetHaradRobes), "XXX", "X X", 'X', Blocks.wool);
        addDyeableWoolRobeRecipes(LOTRRecipes.commonNearHaradRecipes, new ItemStack(LOTRMod.bodyHaradRobes), "X X", "XXX", "XXX", 'X', Blocks.wool);
        addDyeableWoolRobeRecipes(LOTRRecipes.commonNearHaradRecipes, new ItemStack(LOTRMod.legsHaradRobes), "XXX", "X X", "X X", 'X', Blocks.wool);
        addDyeableWoolRobeRecipes(LOTRRecipes.commonNearHaradRecipes, new ItemStack(LOTRMod.bootsHaradRobes), "X X", "X X", 'X', Blocks.wool);
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new LOTRRecipeHaradRobesDye());
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new LOTRRecipeHaradTurbanOrnament());
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 13), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.redSandstone, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 13) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrickRed, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 13) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 4), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 13) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 1, 15), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick3, 1, 13) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 14) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrickRedCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 14) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 5), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 14) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 15), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.redSandstone, 1, 0) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 15) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 7), new Object[] { " X ", "XYX", " X ", 'X', "gemLapis", 'Y', new ItemStack(LOTRMod.brick, 1, 15) }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateNearHarad, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "ingotBronze" }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.kebabStand), new Object[] { " X ", " Y ", "ZZZ", 'X', "plankWood", 'Y', "stickWood", 'Z', Blocks.cobblestone }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shishKebab, 2), new Object[] { "  X", " X ", "Y  ", 'X', LOTRMod.kebab, 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.nearHaradBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetNomad), new Object[] { "XXX", "X X", 'X', LOTRMod.driedReeds }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyNomad), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.driedReeds }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsNomad), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.driedReeds }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsNomad), new Object[] { "X X", "X X", 'X', LOTRMod.driedReeds }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordHarad), new Object[] { "X", "X", "Y", 'X', "ingotBronze", 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerHarad), new Object[] { "X", "Y", 'X', "ingotBronze", 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearHarad), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotBronze", 'Y', "stickWood" }));
        addRecipeTo(LOTRRecipes.commonNearHaradRecipes, (IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeHarad), new Object[] { "  X", " YX", "Y  ", 'X', "ingotBronze", 'Y', "stickWood" }));
    }
    
    private static void createMorgulRecipes() {
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 0), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.morgulTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStone, 2, 0), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStone, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMordorBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), new Object[] { "X", "Y", "Y", 'X', LOTRMod.nauriteGem, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 7) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMordorBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 7) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 9), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 7) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.MORDOR.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wargArmorMordor), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.orcSteel, 'Y', Items.leather }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarOrc), new Object[] { "X", "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeOrc), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerOrc), new Object[] { "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetOrc), new Object[] { "XXX", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyOrc), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsOrc), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsOrc), new Object[] { "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearOrc), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelOrc), new Object[] { "X", "Y", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeOrc), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeOrc), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeOrc), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerOrc), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 7), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.orcTorchItem, 'Z', LOTRMod.orcSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 7), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 7) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 10), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarBlackUruk), new Object[] { "X", "X", "Y", 'X', LOTRMod.blackUrukSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBlackUruk), new Object[] { "X", "Y", 'X', LOTRMod.blackUrukSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearBlackUruk), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.blackUrukSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeBlackUruk), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.blackUrukSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerBlackUruk), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.blackUrukSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlackUruk), new Object[] { "XXX", "X X", 'X', LOTRMod.blackUrukSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlackUruk), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.blackUrukSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlackUruk), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.blackUrukSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlackUruk), new Object[] { "X X", "X X", 'X', LOTRMod.blackUrukSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.blackUrukBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.blackUrukSteel, 'Y', Items.string }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmOrc), new Object[] { " XX", " YX", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMordorRock, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', LOTRMod.orcSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.MINAS_MORGUL.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', new ItemStack(Items.skull, 1, 0) }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BLACK_URUK.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', LOTRMod.blackUrukSteel }));
        LOTRRecipes.morgulRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.NAN_UNGOL.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', Items.string }));
    }
    
    private static void createElvenRecipes() {
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.planks, 4, 1), new Object[] { new ItemStack(LOTRMod.wood, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMallorn, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenTable), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornStick, 4), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelMallorn), new Object[] { "X", "Y", "Y", 'X', new ItemStack(LOTRMod.planks, 1, 1), 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeMallorn), new Object[] { "XXX", " Y ", " Y ", 'X', new ItemStack(LOTRMod.planks, 1, 1), 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeMallorn), new Object[] { "XX", "XY", " Y", 'X', new ItemStack(LOTRMod.planks, 1, 1), 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordMallorn), new Object[] { "X", "X", "Y", 'X', new ItemStack(LOTRMod.planks, 1, 1), 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeMallorn), new Object[] { "XX", " Y", " Y", 'X', new ItemStack(LOTRMod.planks, 1, 1), 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelElven), new Object[] { "X", "Y", "Y", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeElven), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeElven), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordElven), new Object[] { "X", "X", "Y", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeElven), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearElven), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.mallornStick, 'Y', Items.string }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetElven), new Object[] { "XXX", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyElven), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsElven), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsElven), new Object[] { "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornLadder, 3), new Object[] { "X X", "XXX", "X X", 'X', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.elfSteel, 'Y', Items.string }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerElven), new Object[] { "X", "Y", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenBedItem), new Object[] { "XXX", "YYY", 'X', LOTRMod.hithlain, 'Y', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fence, 3, 1), new Object[] { "XYX", "XYX", 'X', new ItemStack(LOTRMod.planks, 1, 1), 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GALADHRIM.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', LOTRMod.mallornStick, 'Z', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorGaladhrim), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.elfSteel, 'Y', Items.leather }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 11), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick, 1, 12), new Object[] { new ItemStack(LOTRMod.brick, 1, 11), "vine" }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 4, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 4, 12) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 4, 13) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsElvenBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsElvenBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 12) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsElvenBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 13) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 10), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 11), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 12) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 12), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 13) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 1), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 2) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 15), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.galadhrimBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.galadhrimWoodBars, 16), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmElven), new Object[] { "  X", " Y ", "X  ", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hithlain, 3), new Object[] { "XXX", 'X', Items.string }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHithlain), new Object[] { "XXX", "X X", 'X', LOTRMod.hithlain }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHithlain), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.hithlain }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsHithlain), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.hithlain }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHithlain), new Object[] { "X X", "X X", 'X', LOTRMod.hithlain }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hithlainLadder, 3), new Object[] { "X", "X", "X", 'X', LOTRMod.hithlain }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam1, 3, 1), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.wood, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 9), new Object[] { " X ", "XYX", " X ", 'X', "nuggetSilver", 'Y', new ItemStack(LOTRMod.brick, 1, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 12), new Object[] { " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(LOTRMod.brick, 1, 11) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateElven, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', new ItemStack(LOTRMod.planks, 1, 1), 'Z', LOTRMod.elfSteel }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.longspearElven), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.elfSteel, 'Y', LOTRMod.mallornStick }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chestMallorn), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchSilver, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', new ItemStack(LOTRMod.leaves7, 1, 2) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchSilver, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', new ItemStack(LOTRMod.niphredil, 1, 0) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 5), new Object[] { " X ", "YZY", 'X', LOTRMod.mallornStick, 'Y', LOTRMod.mallornTorchSilver, 'Z', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchBlue, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', LOTRMod.quenditeCrystal }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchBlue, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', new ItemStack(LOTRMod.bluebell, 1, 0) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 13), new Object[] { " X ", "YZY", 'X', LOTRMod.mallornStick, 'Y', LOTRMod.mallornTorchBlue, 'Z', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGold, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', new ItemStack(LOTRMod.leaves, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGold, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', new ItemStack(LOTRMod.elanor, 1, 0) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 14), new Object[] { " X ", "YZY", 'X', LOTRMod.mallornStick, 'Y', LOTRMod.mallornTorchGold, 'Z', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGreen, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', new ItemStack((Block)Blocks.leaves, 1, 0) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGreen, 4), new Object[] { "Z", "X", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', LOTRMod.mallornStick, 'Z', LOTRMod.clover }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 15), new Object[] { " X ", "YZY", 'X', LOTRMod.mallornStick, 'Y', LOTRMod.mallornTorchGreen, 'Z', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMallorn, 1), new Object[] { "XYX", "XYX", 'X', LOTRMod.mallornStick, 'Y', new ItemStack(LOTRMod.planks, 1, 1) }));
        LOTRRecipes.elvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.doorMallorn), new Object[] { "XX", "XX", "XX", 'X', new ItemStack(LOTRMod.planks, 1, 1) }));
    }
    
    private static void createDwarvenRecipes() {
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick, 1, 6) }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelDwarven), new Object[] { "X", "Y", "Y", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeDwarven), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeDwarven), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordDwarven), new Object[] { "X", "X", "Y", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeDwarven), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDwarven), new Object[] { "X", "Y", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeDwarven), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerDwarven), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarven), new Object[] { "XXX", "X X", 'X', LOTRMod.dwarfSteel }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarven), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.dwarfSteel }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarven), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.dwarfSteel }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarven), new Object[] { "X X", "X X", 'X', LOTRMod.dwarfSteel }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeDwarven), new Object[] { " X ", " YX", "Y  ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 8), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', LOTRMod.dwarfSteel }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mattockDwarven), new Object[] { "XXX", "XY ", " Y ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DWARF.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearDwarven), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarvenSilver), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetSilver", 'Y', LOTRMod.helmetDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarvenSilver), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetSilver", 'Y', LOTRMod.bodyDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarvenSilver), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetSilver", 'Y', LOTRMod.legsDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarvenSilver), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetSilver", 'Y', LOTRMod.bootsDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarvenGold), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetGold", 'Y', LOTRMod.helmetDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarvenGold), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetGold", 'Y', LOTRMod.bodyDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarvenGold), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetGold", 'Y', LOTRMod.legsDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarvenGold), new Object[] { "XXX", "XYX", "XXX", 'X', "nuggetGold", 'Y', LOTRMod.bootsDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarvenMithril), new Object[] { "XXX", "XYX", "XXX", 'X', LOTRMod.mithrilNugget, 'Y', LOTRMod.helmetDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarvenMithril), new Object[] { "XXX", "XYX", "XXX", 'X', LOTRMod.mithrilNugget, 'Y', LOTRMod.bodyDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarvenMithril), new Object[] { "XXX", "XYX", "XXX", 'X', LOTRMod.mithrilNugget, 'Y', LOTRMod.legsDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarvenMithril), new Object[] { "XXX", "XYX", "XXX", 'X', LOTRMod.mithrilNugget, 'Y', LOTRMod.bootsDwarven }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dwarfBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.dwarfSteel }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.boarArmorDwarven), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.dwarfSteel, 'Y', Items.leather }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeDwarven), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dwarvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateDwarven, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', new ItemStack(LOTRMod.brick, 1, 6), 'Z', LOTRMod.dwarfSteel }));
    }
    
    private static void createUrukRecipes() {
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.urukTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick2, 1, 7) }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), new Object[] { "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelUruk), new Object[] { "X", "Y", "Y", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeUruk), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeUruk), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarUruk), new Object[] { "X", "X", "Y", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeUruk), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerUruk), new Object[] { "X", "Y", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeUruk), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerUruk), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearUruk), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUruk), new Object[] { "XXX", "X X", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyUruk), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsUruk), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsUruk), new Object[] { "X X", "X X", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.urukCrossbow), new Object[] { "XXY", "ZYX", "YZX", 'X', LOTRMod.urukSteel, 'Y', "stickWood", 'Z', Items.string }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 9), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.orcTorchItem, 'Z', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 7) }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcBomb, 4), new Object[] { "XYX", "YXY", "XYX", 'X', Items.gunpowder, 'Y', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 1), new Object[] { new ItemStack(LOTRMod.orcBomb, 1, 0), Items.gunpowder, LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 2), new Object[] { new ItemStack(LOTRMod.orcBomb, 1, 1), Items.gunpowder, LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ISENGARD.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wargArmorUruk), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.urukSteel, 'Y', Items.leather }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 7), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 7) }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsUrukBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 7) }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 7), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 7) }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.urukBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeUruk), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarUrukBerserker), new Object[] { "XXX", " X ", " Y ", 'X', LOTRMod.urukSteel, 'Y', "stickWood" }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUrukBerserker), new Object[] { "XYX", " Z ", 'X', LOTRMod.urukSteel, 'Y', "dyeWhite", 'Z', new ItemStack(LOTRMod.helmetUruk, 1, 0) }));
        LOTRRecipes.urukRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateUruk, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', LOTRMod.urukSteel }));
    }
    
    private static void createWoodElvenRecipes() {
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodElvenTable), new Object[] { "XX", "XX", 'X', "plankWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodElvenBedItem), new Object[] { "XXX", "YYY", 'X', Blocks.wool, 'Y', "plankWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetWoodElvenScout), new Object[] { "XXX", "X X", 'X', Items.leather }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyWoodElvenScout), new Object[] { "X X", "XXX", "XXX", 'X', Items.leather }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsWoodElvenScout), new Object[] { "XXX", "X X", "X X", 'X', Items.leather }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsWoodElvenScout), new Object[] { "X X", "X X", 'X', Items.leather }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mirkwoodBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodElvenTorch, 4), new Object[] { "X", "Y", "Z", 'X', new ItemStack(LOTRMod.leaves, 1, 3), 'Y', new ItemStack(Items.coal, 1, 32767), 'Z', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 6), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.woodElvenTorch, 'Z', "plankWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelWoodElven), new Object[] { "X", "Y", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeWoodElven), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeWoodElven), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordWoodElven), new Object[] { "X", "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeWoodElven), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerWoodElven), new Object[] { "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearWoodElven), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetWoodElven), new Object[] { "XXX", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyWoodElven), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsWoodElven), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsWoodElven), new Object[] { "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.WOOD_ELF.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elkArmorWoodElven), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.elfSteel, 'Y', Items.leather }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 5), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick3, 1, 6), new Object[] { new ItemStack(LOTRMod.brick3, 1, 5), "vine" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 6) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 7) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsWoodElvenBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsWoodElvenBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 6) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsWoodElvenBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 7) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 6) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 7) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 12), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 12) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 13) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 14), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick3, 1, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick3, 1, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodElfBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodElfWoodBars, 16), new Object[] { "XXX", "XXX", 'X', "plankWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmWoodElven), new Object[] { "  X", " Y ", "X  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 10), new Object[] { " X ", "XYX", " X ", 'X', "nuggetSilver", 'Y', new ItemStack(LOTRMod.brick3, 1, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 13), new Object[] { " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(LOTRMod.brick3, 1, 5) }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateWoodElven, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', LOTRMod.elfSteel }));
        LOTRRecipes.woodElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.longspearWoodElven), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
    }
    
    private static void createGondorianRecipes() {
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gondorianTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.beacon), new Object[] { "XXX", "XXX", "YYY", 'X', "logWood", 'Y', Blocks.cobblestone }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStone, 2, 1), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStone, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 1), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 3), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick, 1, 2), new Object[] { new ItemStack(LOTRMod.brick, 1, 1), "vine" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 2) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 2) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 4), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 2) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 3) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 3) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 5), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 3) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 5), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGondor), new Object[] { "XXX", "X X", 'X', "ingotIron" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGondor), new Object[] { "X X", "XXX", "XXX", 'X', "ingotIron" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsGondor), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGondor), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordGondor), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearGondor), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerGondor), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerGondor), new Object[] { "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gondorBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGondorWinged), new Object[] { "XYX", 'X', "feather", 'Y', new ItemStack(LOTRMod.helmetGondor, 1, 0) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GONDOR.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorGondor), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 6), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 6) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.lanceGondor), new Object[] { "  X", " X ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorRock, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateGondor, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "ingotIron" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRangerIthilien), new Object[] { "XXX", "X X", 'X', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRangerIthilien), new Object[] { "X X", "XXX", "XXX", 'X', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRangerIthilien), new Object[] { "XXX", "X X", "X X", 'X', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRangerIthilien), new Object[] { "X X", "X X", 'X', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ANORIEN.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "nuggetGold" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ITHILIEN.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "nuggetSilver" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.LOSSARNACH.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', new ItemStack((Block)Blocks.double_plant, 1, 4) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.PINNATH_GELIN.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "dyeGreen" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.LEBENNIN.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "dyeLightBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.PELARGIR.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "dyeCyan" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BLACKROOT_VALE.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', LOTRMod.blackroot }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.LAMEDON.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetLossarnach), new Object[] { "XXX", "Y Y", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLossarnach), new Object[] { "X X", "YYY", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsLossarnach), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsLossarnach), new Object[] { "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetPinnathGelin), new Object[] { "XXX", "YZY", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeGreen" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyPinnathGelin), new Object[] { "XZX", "YYY", "XXX", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeGreen" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsPinnathGelin), new Object[] { "XXX", "YZY", "X X", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeGreen" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsPinnathGelin), new Object[] { "YZY", "X X", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeGreen" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeLossarnach), new Object[] { "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeLossarnach), new Object[] { " X ", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetPelargir), new Object[] { "XXX", "XYX", 'X', "ingotIron", 'Y', "dyeCyan" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyPelargir), new Object[] { "XYX", "XXX", "XXX", 'X', "ingotIron", 'Y', "dyeCyan" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsPelargir), new Object[] { "XXX", "XYX", "X X", 'X', "ingotIron", 'Y', "dyeCyan" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsPelargir), new Object[] { "XYX", "X X", 'X', "ingotIron", 'Y', "dyeCyan" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tridentPelargir), new Object[] { " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordPelargir), new Object[] { " X", "X ", "Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 8), new Object[] { "XY", "YX", 'X', new ItemStack(LOTRMod.rock, 1, 1), 'Y', Blocks.cobblestone }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 8) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickRustic, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 8) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 7), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 8) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 9), new Object[] { new ItemStack(LOTRMod.brick5, 1, 8), "vine" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 9) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickRusticMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 9) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 8), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 9) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 10) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickRusticCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 10) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 9), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 10) }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GONDOR_STEWARD.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "dyeWhite" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlackroot), new Object[] { "XXX", "XYX", 'X', "ingotIron", 'Y', "dyeBlack" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlackroot), new Object[] { "XYX", "XXX", "XXX", 'X', "ingotIron", 'Y', "dyeBlack" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlackroot), new Object[] { "XXX", "XYX", "X X", 'X', "ingotIron", 'Y', "dyeBlack" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlackroot), new Object[] { "XYX", "X X", 'X', "ingotIron", 'Y', "dyeBlack" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.blackrootBow), new Object[] { " XY", "Z Y", " XY", 'X', LOTRMod.blackrootStick, 'Y', Items.string, 'Z', LOTRMod.blackroot }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeGondor), new Object[] { "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chestLebethron), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(LOTRMod.planks, 1, 8), 'Y', "nuggetSilver" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGondorGambeson), new Object[] { "X X", "XXX", "XXX", 'X', Blocks.wool }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLebenninGambeson), new Object[] { "XYX", "XXX", "XXX", 'X', Blocks.wool, 'Y', "dyeLightBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetLamedon), new Object[] { "XXX", "YZY", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLamedon), new Object[] { "XZX", "YYY", "XXX", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsLamedon), new Object[] { "XXX", "YZY", "X X", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsLamedon), new Object[] { "YZY", "X X", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorLamedon), new Object[] { "XZ ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather, 'Z', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLamedonJacket), new Object[] { "XYX", "XXX", "XXX", 'X', Items.leather, 'Y', "dyeBlue" }));
        LOTRRecipes.gondorianRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 6), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick2, 1, 11) }));
    }
    
    private static void createRohirricRecipes() {
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rohirricTable), new Object[] { "XX", "XX", 'X', "plankWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStone, 2, 2), new Object[] { "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.smoothStone, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 4), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 4) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRohanBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 4) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 8), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 6), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick, 1, 4) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordRohan), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerRohan), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearRohan), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRohan), new Object[] { "XXX", "Y Y", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRohan), new Object[] { "X X", "YYY", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRohan), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRohan), new Object[] { "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ROHAN.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeRohan), new Object[] { "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorRohan), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 8), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 8) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rohanBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRohanMarshal), new Object[] { " X ", "YAY", " X ", 'X', "nuggetGold", 'Y', Items.leather, 'A', new ItemStack(LOTRMod.helmetRohan, 1, 0) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRohanMarshal), new Object[] { " X ", "YAY", " X ", 'X', "nuggetGold", 'Y', Items.leather, 'A', new ItemStack(LOTRMod.bodyRohan, 1, 0) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRohanMarshal), new Object[] { " X ", "YAY", " X ", 'X', "nuggetGold", 'Y', Items.leather, 'A', new ItemStack(LOTRMod.legsRohan, 1, 0) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRohanMarshal), new Object[] { " X ", "YAY", " X ", 'X', "nuggetGold", 'Y', Items.leather, 'A', new ItemStack(LOTRMod.bootsRohan, 1, 0) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRohanRock, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.rock, 1, 2) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.lanceRohan), new Object[] { "  X", " X ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 1, 3), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick, 1, 4) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamS, 3, 0), new Object[] { "X", "X", "X", 'X', "logWood" }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamS, 1, 1), new Object[] { " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(LOTRMod.woodBeamS, 1, 0) }));
        LOTRRecipes.rohirricRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateRohan, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "ingotIron" }));
    }
    
    private static void createDunlendingRecipes() {
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dunlendingTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', Blocks.cobblestone }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDunlending), new Object[] { "XXX", "Y Y", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDunlending), new Object[] { "X X", "YYY", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDunlending), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDunlending), new Object[] { "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dunlendingClub), new Object[] { "X", "X", "X", 'X', "plankWood" }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dunlendingTrident), new Object[] { " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.dunlendingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DUNLAND.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
    }
    
    private static void createAngmarRecipes() {
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 0), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.angmarTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick2, 1, 0) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordAngmar), new Object[] { "X", "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeAngmar), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerAngmar), new Object[] { "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetAngmar), new Object[] { "XXX", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyAngmar), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsAngmar), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsAngmar), new Object[] { "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 0) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsAngmarBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 0) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 0) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), new Object[] { "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearAngmar), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelAngmar), new Object[] { "X", "Y", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeAngmar), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeAngmar), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeAngmar), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerAngmar), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 1) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsAngmarBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 1) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 1) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 0) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ANGMAR.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wargArmorAngmar), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.orcSteel, 'Y', Items.leather }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', LOTRMod.orcSteel }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 4), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 4) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmAngmar), new Object[] { " XX", " YX", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RHUDAUR.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "stickWood" }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick6, 1, 10), new Object[] { new ItemStack(LOTRMod.brick2, 4, 0), Items.snowball }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 10) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsAngmarBrickSnow, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 10) }));
        LOTRRecipes.angmarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 10) }));
    }
    
    private static void createNearHaradRecipes() {
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.nearHaradTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(Blocks.sandstone, 1, 0) }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.NEAR_HARAD.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HARAD_NOMAD.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', Blocks.sand }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetNearHarad), new Object[] { "XXX", "X X", 'X', "ingotBronze" }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyNearHarad), new Object[] { "X X", "XXX", "XXX", 'X', "ingotBronze" }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsNearHarad), new Object[] { "XXX", "X X", "X X", 'X', "ingotBronze" }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsNearHarad), new Object[] { "X X", "X X", 'X', "ingotBronze" }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetNearHaradWarlord), new Object[] { "XYX", " Z ", 'X', "stickWood", 'Y', Items.leather, 'Z', new ItemStack(LOTRMod.helmetNearHarad, 1, 0) }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorNearHarad), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHarnedor), new Object[] { "XXX", "Y Y", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHarnedor), new Object[] { "X X", "YYY", "XXX", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsHarnedor), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.nearHaradRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHarnedor), new Object[] { "Y Y", "X X", 'X', "ingotBronze", 'Y', Items.leather }));
    }
    
    private static void createHighElvenRecipes() {
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenTable), new Object[] { "XX", "XX", 'X', "plankWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelHighElven), new Object[] { "X", "Y", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeHighElven), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeHighElven), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeHighElven), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordHighElven), new Object[] { "X", "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerHighElven), new Object[] { "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearHighElven), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHighElven), new Object[] { "XXX", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHighElven), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsHighElven), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHighElven), new Object[] { "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HIGH_ELF.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorHighElven), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.elfSteel, 'Y', Items.leather }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmHighElven), new Object[] { "  X", " Y ", "X  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.longspearHighElven), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.highElvenRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.elfSteel, 'Y', Items.string }));
    }
    
    private static void createBlueMountainsRecipes() {
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.blueDwarvenTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick, 1, 14) }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelBlueDwarven), new Object[] { "X", "Y", "Y", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeBlueDwarven), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeBlueDwarven), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordBlueDwarven), new Object[] { "X", "X", "Y", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeBlueDwarven), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBlueDwarven), new Object[] { "X", "Y", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeBlueDwarven), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerBlueDwarven), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlueDwarven), new Object[] { "XXX", "X X", 'X', LOTRMod.blueDwarfSteel }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlueDwarven), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.blueDwarfSteel }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlueDwarven), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.blueDwarfSteel }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlueDwarven), new Object[] { "X X", "X X", 'X', LOTRMod.blueDwarfSteel }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeBlueDwarven), new Object[] { " X ", " YX", "Y  ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 11), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', LOTRMod.blueDwarfSteel }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.mattockBlueDwarven), new Object[] { "XXX", "XY ", " Y ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BLUE_MOUNTAINS.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearBlueDwarven), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.blueDwarfBars, 16), new Object[] { "XXX", "XXX", 'X', LOTRMod.blueDwarfSteel }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.boarArmorBlueDwarven), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.blueDwarfSteel, 'Y', Items.leather }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeBlueDwarven), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.blueDwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.blueMountainsRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateDwarven, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', new ItemStack(LOTRMod.brick, 1, 6), 'Z', LOTRMod.blueDwarfSteel }));
    }
    
    private static void createRangerRecipes() {
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rangerTable), new Object[] { "XX", "XX", 'X', "plankWood" }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RANGER_NORTH.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 3), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 3) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsArnorBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 3) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 4), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 3) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 6), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick2, 1, 3) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick2, 1, 4), new Object[] { new ItemStack(LOTRMod.brick2, 1, 3), "vine" }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 4) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsArnorBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 4) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 5), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 4) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 5) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsArnorBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 5) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 6), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 5) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRanger), new Object[] { "XXX", "X X", 'X', Items.leather }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRanger), new Object[] { "X X", "YYY", "XXX", 'X', Items.leather, 'Y', "ingotIron" }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRanger), new Object[] { "XXX", "Y Y", "X X", 'X', Items.leather, 'Y', "ingotIron" }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRanger), new Object[] { "X X", "X X", 'X', Items.leather }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rangerBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 13), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 13) }));
        LOTRRecipes.rangerRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle14, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 14) }));
    }
    
    private static void createDolGuldurRecipes() {
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 8), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dolGuldurTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick2, 1, 8) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 8) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolGuldurBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 8) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 8), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 8) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), new Object[] { "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 9) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolGuldurBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 9) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 9), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 9) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick2, 1, 8) }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DOL_GULDUR.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordDolGuldur), new Object[] { "X", "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeDolGuldur), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDolGuldur), new Object[] { "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDolGuldur), new Object[] { "XXX", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDolGuldur), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDolGuldur), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDolGuldur), new Object[] { "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearDolGuldur), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelDolGuldur), new Object[] { "X", "Y", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeDolGuldur), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeDolGuldur), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeDolGuldur), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerDolGuldur), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 7), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.orcTorchItem, 'Z', LOTRMod.orcSteel }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', LOTRMod.orcSteel }));
        LOTRRecipes.dolGuldurRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeDolGuldur), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
    }
    
    private static void createGundabadRecipes() {
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gundabadTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', Blocks.cobblestone }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), new Object[] { "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), new Object[] { "XXX", "X X", "XXX", 'X', Blocks.cobblestone }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GUNDABAD.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 7), new Object[] { " X ", "YZY", 'X', "stickWood", 'Y', LOTRMod.orcTorchItem, 'Z', LOTRMod.orcSteel }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGundabadUruk), new Object[] { "XXX", "X X", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGundabadUruk), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsGundabadUruk), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGundabadUruk), new Object[] { "X X", "X X", 'X', LOTRMod.urukSteel }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordGundabadUruk), new Object[] { "X", "X", "Y", 'X', LOTRMod.urukSteel, 'Y', "bone" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeGundabadUruk), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.urukSteel, 'Y', "bone" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerGundabadUruk), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.urukSteel, 'Y', "bone" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), new Object[] { "YYY", "YXY", "YYY", 'X', LOTRMod.gateGear, 'Y', LOTRMod.orcSteel }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerGundabadUruk), new Object[] { "X", "Y", 'X', LOTRMod.urukSteel, 'Y', "bone" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearGundabadUruk), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.urukSteel, 'Y', "bone" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeGundabadUruk), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.urukSteel, 'Y', "bone" }));
        LOTRRecipes.gundabadRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gundabadUrukBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.urukSteel, 'Y', Items.string }));
    }
    
    private static void createHalfTrollRecipes() {
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.halfTrollTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', Blocks.cobblestone }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HALF_TROLL.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHalfTroll), new Object[] { "XXX", "Y Y", 'X', LOTRMod.gemsbokHide, 'Y', Items.string }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHalfTroll), new Object[] { "X X", "XYX", "XYX", 'X', LOTRMod.gemsbokHide, 'Y', Items.string }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsHalfTroll), new Object[] { "XXX", "Y Y", "X X", 'X', LOTRMod.gemsbokHide, 'Y', Items.string }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHalfTroll), new Object[] { "Y Y", "X X", 'X', LOTRMod.gemsbokHide, 'Y', Items.string }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarHalfTroll), new Object[] { "X", "X", "Y", 'X', Items.flint, 'Y', "stickWood" }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeHalfTroll), new Object[] { "XXX", "XYX", " Y ", 'X', Items.flint, 'Y', "stickWood" }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerHalfTroll), new Object[] { "X", "Y", 'X', Items.flint, 'Y', "stickWood" }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerHalfTroll), new Object[] { "XYX", "XYX", " Y ", 'X', Items.flint, 'Y', "stickWood" }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.maceHalfTroll), new Object[] { " XX", " XX", "Y  ", 'X', Items.flint, 'Y', "stickWood" }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rhinoArmorHalfTroll), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.gemsbokHide, 'Y', Items.string }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.torogStew), new Object[] { Items.bowl, Items.rotten_flesh, "bone", Blocks.dirt }));
        LOTRRecipes.halfTrollRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeHalfTroll), new Object[] { "  X", " YX", "Y  ", 'X', Items.flint, 'Y', "stickWood" }));
    }
    
    private static void createDolAmrothRecipes() {
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dolAmrothTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DOL_AMROTH.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDolAmroth), new Object[] { "Y Y", "XXX", "X X", 'X', "ingotIron", 'Y', LOTRMod.swanFeather }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDolAmroth), new Object[] { "YXY", "XXX", "XXX", 'X', "ingotIron", 'Y', LOTRMod.swanFeather }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDolAmroth), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDolAmroth), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordDolAmroth), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorDolAmroth), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 9), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 1) }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 9) }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolAmrothBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 9) }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 14), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 9) }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.lanceDolAmroth), new Object[] { "  X", " X ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDolAmroth), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateDolAmroth, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "ingotIron" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.longspearDolAmroth), new Object[] { "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDolAmrothGambeson), new Object[] { "X X", "XXX", "XXX", 'X', Blocks.wool }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDolAmrothGambeson), new Object[] { "XXX", "X X", "X X", 'X', Blocks.wool }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gondorBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.dolAmrothRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 6), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick2, 1, 11) }));
    }
    
    private static void createMoredainRecipes() {
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.moredainTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', LOTRMod.redClay }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.MOREDAIN.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetMoredain), new Object[] { "XXX", "X X", 'X', LOTRMod.gemsbokHide }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyMoredain), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.gemsbokHide }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsMoredain), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.gemsbokHide }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsMoredain), new Object[] { "X X", "X X", 'X', LOTRMod.gemsbokHide }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 10), new Object[] { "XX", "XX", 'X', LOTRMod.redClay }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 10) }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMoredainBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 10) }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 15), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick3, 4, 10) }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeMoredain), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.rhinoHorn, 'Y', "stickWood" }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerMoredain), new Object[] { "X", "Y", 'X', LOTRMod.rhinoHorn, 'Y', "stickWood" }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearMoredain), new Object[] { "  X", " X ", "X  ", 'X', LOTRMod.gemsbokHorn }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetMoredainLion), new Object[] { "XXX", "X X", 'X', LOTRMod.lionFur }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyMoredainLion), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.lionFur }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsMoredainLion), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.lionFur }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsMoredainLion), new Object[] { "X X", "X X", 'X', LOTRMod.lionFur }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.clubMoredain), new Object[] { "X", "X", "X", 'X', "plankWood" }));
        LOTRRecipes.moredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordMoredain), new Object[] { "X", "X", "Y", 'X', "ingotBronze", 'Y', "stickWood" }));
    }
    
    private static void createTauredainRecipes() {
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick4, 1, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.TAUREDAIN.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 0), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick4, 1, 1), new Object[] { new ItemStack(LOTRMod.brick4, 1, 0), "vine" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 1) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 1) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 1) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 2) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 2) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 2), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 2) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 3), new Object[] { "XX", "XX", 'X', "ingotGold" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 3) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickGold, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 3) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 3), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 3) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 4), new Object[] { "XX", "XX", 'X', LOTRMod.obsidianShard }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 4) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickObsidian, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 4) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 4), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 4, 4) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelTauredain), new Object[] { "X", "Y", "Y", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeTauredain), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeTauredain), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeTauredain), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerTauredain), new Object[] { "X", "Y", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearTauredain), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordTauredain), new Object[] { "XZX", "XZX", " Y ", 'X', LOTRMod.obsidianShard, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetTauredain), new Object[] { "XXX", "X X", 'X', "ingotBronze" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyTauredain), new Object[] { "X X", "XXX", "XXX", 'X', "ingotBronze" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsTauredain), new Object[] { "XXX", "X X", "X X", 'X', "ingotBronze" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsTauredain), new Object[] { "X X", "X X", 'X', "ingotBronze" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetTauredainChieftain), new Object[] { "X", "Y", 'X', new ItemStack(LOTRMod.doubleFlower, 1, 3), 'Y', new ItemStack(LOTRMod.helmetTauredain, 1, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainBlowgun), new Object[] { "XYY", 'X', "stickWood", 'Y', LOTRMod.reeds }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDart, 4), new Object[] { "X", "Y", "Z", 'X', LOTRMod.obsidianShard, 'Y', "stickWood", 'Z', "feather" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartPoisoned, 4), new Object[] { " X ", "XYX", " X ", 'X', LOTRMod.tauredainDart, 'Y', "poison" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartTrap), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 0), 'Y', new ItemStack(LOTRMod.tauredainBlowgun, 1, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 14), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar, 1, 14) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDoubleTorchItem, 2), new Object[] { "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartTrapGold), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 3), 'Y', new ItemStack(LOTRMod.tauredainBlowgun, 1, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateTauredain, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "ingotGold" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerTauredain), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeTauredain), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeTauredain), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.obsidianShard, 'Y', "stickWood" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartTrapObsidian), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(LOTRMod.brick4, 1, 4), 'Y', new ItemStack(LOTRMod.tauredainBlowgun, 1, 0) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 11), new Object[] { "X", "X", "X", 'X', "ingotGold" }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 12), new Object[] { "X", "X", "X", 'X', LOTRMod.obsidianShard }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 11) }));
        LOTRRecipes.tauredainRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 12) }));
    }
    
    private static void createDaleRecipes() {
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daleTable), new Object[] { "XX", "XX", 'X', "plankWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DALE.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 1), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 1) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDaleBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 1) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 9), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 1) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dalishPastryItem), new Object[] { "ABA", "CDC", "EEE", 'A', LOTRMod.mapleSyrup, 'B', Items.milk_bucket, 'C', LOTRMod.raisins, 'D', Items.egg, 'E', Items.wheat }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.cram), new Object[] { "XYX", 'X', Items.wheat, 'Y', LOTRMod.salt }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordDale), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDale), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearDale), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeDale), new Object[] { "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDale), new Object[] { "XXX", "X X", 'X', "ingotIron" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDale), new Object[] { "X X", "XXX", "XXX", 'X', "ingotIron" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDale), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDale), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daleBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 5), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 5) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorDale), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeDale), new Object[] { "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDaleGambeson), new Object[] { "X X", "XXX", "XXX", 'X', Blocks.wool }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 0), true), new Object[] { " Z ", "XYX", 'X', Items.paper, 'Y', Items.gunpowder, 'Z', "dyeRed" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 1), true), new Object[] { " Z ", "XYX", 'X', Items.paper, 'Y', Items.gunpowder, 'Z', "dyeBlue" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 2), true), new Object[] { " Z ", "XYX", 'X', Items.paper, 'Y', Items.gunpowder, 'Z', "dyeGreen" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 3), true), new Object[] { " Z ", "XYX", 'X', Items.paper, 'Y', Items.gunpowder, 'Z', "dyeWhite" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 4), true), new Object[] { " Z ", "XYX", 'X', Items.paper, 'Y', Items.gunpowder, 'Z', "dyeYellow" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ESGAROTH.bannerID), new Object[] { "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', Items.fish }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick6, 1, 3), new Object[] { new ItemStack(LOTRMod.brick5, 1, 1), "vine" }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 3) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDaleBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 3) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 14), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 3) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 4) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDaleBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 4) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 15), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 4) }));
        LOTRRecipes.daleRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 5), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick5, 1, 1) }));
    }
    
    private static void createDorwinionRecipes() {
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dorwinionTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.rock, 1, 5) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DORWINION.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 2), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 5) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 7), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 2) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 2) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 10), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 2) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDorwinion), new Object[] { "XXX", "X X", 'X', "ingotIron" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDorwinion), new Object[] { "X X", "XXX", "XXX", 'X', "ingotIron" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDorwinion), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDorwinion), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDorwinionElf), new Object[] { "XXX", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDorwinionElf), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsDorwinionElf), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDorwinionElf), new Object[] { "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearBladorthin), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.dwarfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordDorwinionElf), new Object[] { "X", "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDorwinionElf), new Object[] { "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), new Object[] { "XXX", "X X", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 2) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 4), new Object[] { new ItemStack(LOTRMod.brick5, 1, 2), "vine" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 4) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 4) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 11), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 4) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 5) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 5) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 12), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 5) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 7), new Object[] { new ItemStack(LOTRMod.brick5, 1, 2), new ItemStack(LOTRMod.rhunFlower, 1, 2) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 7) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrickFlowers, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 7) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 13), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 7) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 1, 6), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick5, 1, 2) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 6), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 5) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 6) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.pillar2, 1, 7), new Object[] { new ItemStack(LOTRMod.pillar2, 1, 6), "vine" }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 7) }));
        LOTRRecipes.dorwinionRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.dorwinionElfBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.elfSteel, 'Y', Items.string }));
    }
    
    private static void createHobbitRecipes() {
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hobbitTable), new Object[] { "XX", "YY", 'X', Blocks.wool, 'Y', "plankWood" }));
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HOBBIT.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.hobbitPancake), new Object[] { Items.wheat, Items.egg, Items.milk_bucket }));
        for (int i = 1; i <= 8; ++i) {
            final Object[] ingredients = new Object[i + 1];
            ingredients[0] = LOTRMod.mapleSyrup;
            for (int j = 1; j < ingredients.length; ++j) {
                ingredients[j] = LOTRMod.hobbitPancake;
            }
            LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.hobbitPancakeMapleSyrup, i), ingredients));
        }
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hobbitOven), new Object[] { "XXX", "X X", "XXX", 'X', Blocks.brick_block }));
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitGreen, 4), new Object[] { "YYY", "ZXZ", "YYY", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "dyeGreen" }));
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitBlue, 4), new Object[] { "YYY", "ZXZ", "YYY", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "dyeBlue" }));
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitRed, 4), new Object[] { "YYY", "ZXZ", "YYY", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "dyeRed" }));
        LOTRRecipes.hobbitRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitYellow, 4), new Object[] { "YYY", "ZXZ", "YYY", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', "dyeYellow" }));
    }
    
    private static void createRhunRecipes() {
        OreDictionary.registerOre("rhunStone", new ItemStack(Blocks.stone, 1, 0));
        OreDictionary.registerOre("rhunStone", new ItemStack(Blocks.sandstone, 1, 0));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rhunTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(LOTRMod.brick5, 1, 11) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RHUN.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 11), new Object[] { "XX", "XX", 'X', "rhunStone" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 0), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 11) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 11) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 15), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 11) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 1, 12), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick5, 1, 11) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 8), new Object[] { "X", "X", "X", 'X', "rhunStone" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 8) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 13), new Object[] { new ItemStack(LOTRMod.brick5, 1, 11), "vine" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 1), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 13) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickMossy, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 13) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 10), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 13) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 14) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 14) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 11), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 14) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 15), new Object[] { new ItemStack(LOTRMod.brick5, 1, 11), new ItemStack(LOTRMod.rhunFlower, 1, 1) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 15) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickFlowers, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 15) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 12), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick5, 1, 15) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 0), new Object[] { " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(LOTRMod.brick5, 1, 11) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 4, 1), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.rock, 1, 4) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 5), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 1) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickRed, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 1) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 13), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 1) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 2), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick6, 1, 1) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 9), new Object[] { "X", "X", "X", 'X', new ItemStack(LOTRMod.rock, 1, 4) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 6), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 9) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordRhun), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerRhun), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearRhun), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmRhun), new Object[] { " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeRhun), new Object[] { "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRhun), new Object[] { "XXX", "Y Y", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRhun), new Object[] { "X X", "YYY", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRhun), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRhun), new Object[] { "Y Y", "X X", 'X', "ingotIron", 'Y', Items.leather }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rhunBow), new Object[] { " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorRhunGold), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.gildedIron, 'Y', Items.leather }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rhunFireJar), new Object[] { "XYX", "YZY", "XYX", 'X', LOTRMod.gildedIron, 'Y', Items.gunpowder, 'Z', LOTRMod.nauriteGem }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rhunFirePot, 4), new Object[] { "Z", "Y", "X", 'X', LOTRMod.gildedIron, 'Y', Items.gunpowder, 'Z', LOTRMod.nauriteGem }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gateRhun, 4), new Object[] { "ZYZ", "YXY", "ZYZ", 'X', LOTRMod.gateGear, 'Y', "plankWood", 'Z', LOTRMod.gildedIron }));
        addDyeableWoolRobeRecipes(LOTRRecipes.rhunRecipes, new ItemStack(LOTRMod.bodyKaftan), "X X", "XXX", "XXX", 'X', Blocks.wool);
        addDyeableWoolRobeRecipes(LOTRRecipes.rhunRecipes, new ItemStack(LOTRMod.legsKaftan), "XXX", "X X", "X X", 'X', Blocks.wool);
        LOTRRecipes.rhunRecipes.add((IRecipe)new LOTRRecipeHaradRobesDye(LOTRMaterial.KAFTAN));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRhunGold), new Object[] { "XXX", "X X", 'X', LOTRMod.gildedIron }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRhunGold), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.gildedIron }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRhunGold), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.gildedIron }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRhunGold), new Object[] { "X X", "X X", 'X', LOTRMod.gildedIron }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRhunWarlord), new Object[] { "XYX", 'X', LOTRMod.kineArawHorn, 'Y', new ItemStack(LOTRMod.helmetRhunGold, 1, 0) }));
        LOTRRecipes.rhunRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeRhun), new Object[] { "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
    }
    
    private static void createRivendellRecipes() {
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rivendellTable), new Object[] { "XX", "XX", 'X', "plankWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.shovelRivendell), new Object[] { "X", "Y", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeRivendell), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.axeRivendell), new Object[] { "XX", "XY", " Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hoeRivendell), new Object[] { "XX", " Y", " Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordRivendell), new Object[] { "X", "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerRivendell), new Object[] { "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearRivendell), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRivendell), new Object[] { "XXX", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRivendell), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsRivendell), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRivendell), new Object[] { "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RIVENDELL.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorRivendell), new Object[] { "X  ", "XYX", "XXX", 'X', LOTRMod.elfSteel, 'Y', Items.leather }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.polearmRivendell), new Object[] { "  X", " Y ", "X  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.longspearRivendell), new Object[] { "  X", " YX", "Y  ", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
        LOTRRecipes.rivendellRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.rivendellBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.elfSteel, 'Y', Items.string }));
    }
    
    private static void createUmbarRecipes() {
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.umbarTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(Blocks.sandstone, 1, 0) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.UMBAR.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetCorsair), new Object[] { "XXX", "Y Y", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyCorsair), new Object[] { "X X", "YYY", "XXX", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsCorsair), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsCorsair), new Object[] { "Y Y", "X X", 'X', "ingotBronze", 'Y', Items.leather }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordCorsair), new Object[] { "X ", "X ", "YA", 'X', "ingotIron", 'Y', "stickWood", 'A', Items.string }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerCorsair), new Object[] { "X ", "YA", 'X', "ingotIron", 'Y', "stickWood", 'A', Items.string }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearCorsair), new Object[] { "  X", " Y ", "YA ", 'X', "ingotIron", 'Y', "stickWood", 'A', Items.string }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeCorsair), new Object[] { "XXX", "XYX", " YA", 'X', "ingotIron", 'Y', "stickWood", 'A', Items.string }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarNearHarad), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerNearHarad), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearNearHarad), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.maceNearHarad), new Object[] { " XX", " XX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pikeNearHarad), new Object[] { "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.poleaxeNearHarad), new Object[] { " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUmbar), new Object[] { "XXX", "X X", 'X', "ingotIron" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyUmbar), new Object[] { "X X", "XXX", "XXX", 'X', "ingotIron" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsUmbar), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsUmbar), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 4, 6), new Object[] { "XX", "XX", 'X', Blocks.stone }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 2), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 6) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsUmbarBrick, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 6) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 0), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 6) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 3), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 7) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.stairsUmbarBrickCracked, 4), new Object[] { "X  ", "XX ", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 7) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 1), new Object[] { "XXX", "XXX", 'X', new ItemStack(LOTRMod.brick6, 1, 7) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 8), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick6, 1, 6) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 9), new Object[] { "XX", "XX", 'X', new ItemStack(LOTRMod.brick2, 1, 11) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 10), new Object[] { "X", "X", "X", 'X', Blocks.stone }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 4), new Object[] { "XXX", 'X', new ItemStack(LOTRMod.pillar2, 1, 10) }));
        LOTRRecipes.umbarRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorUmbar), new Object[] { "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather }));
    }
    
    private static void createGulfRecipes() {
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.gulfTable), new Object[] { "XX", "YY", 'X', "plankWood", 'Y', new ItemStack(Blocks.sandstone, 1, 0) }));
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HARAD_GULF.bannerID), new Object[] { "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood" }));
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGulfHarad), new Object[] { "XXX", "Y Y", 'X', "ingotBronze", 'Y', LOTRMod.driedReeds }));
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGulfHarad), new Object[] { "X X", "YYY", "XXX", 'X', "ingotBronze", 'Y', LOTRMod.driedReeds }));
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsGulfHarad), new Object[] { "XXX", "Y Y", "X X", 'X', "ingotBronze", 'Y', LOTRMod.driedReeds }));
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGulfHarad), new Object[] { "Y Y", "X X", 'X', "ingotBronze", 'Y', LOTRMod.driedReeds }));
        LOTRRecipes.gulfRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordGulfHarad), new Object[] { " X", "X ", "Y ", 'X', "ingotBronze", 'Y', "stickWood" }));
    }
    
    private static void createUnsmeltingRecipes() {
        createUtumnoUnsmeltingRecipes();
        createGondolinUnsmeltingRecipes();
        createTauredainUnsmeltingRecipes();
        createArnorUnsmeltingRecipes();
        createBNUnsmeltingRecipes();
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.blacksmithHammer), new Object[] { "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerAncientHarad), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
    }
    
    private static void createUtumnoUnsmeltingRecipes() {
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUtumno), new Object[] { "XXX", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyUtumno), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsUtumno), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsUtumno), new Object[] { "X X", "X X", 'X', LOTRMod.orcSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordUtumno), new Object[] { "X", "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerUtumno), new Object[] { "X", "Y", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearUtumno), new Object[] { "  X", " Y ", "Y  ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeUtumno), new Object[] { "XXX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.hammerUtumno), new Object[] { "XYX", "XYX", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.utumnoBow), new Object[] { " XY", "X Y", " XY", 'X', LOTRMod.orcSteel, 'Y', Items.string }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.utumnoPickaxe), new Object[] { "XXX", " Y ", " Y ", 'X', LOTRMod.orcSteel, 'Y', "stickWood" }));
    }
    
    private static void createGondolinUnsmeltingRecipes() {
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGondolin), new Object[] { "XXX", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGondolin), new Object[] { "X X", "XXX", "XXX", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsGondolin), new Object[] { "XXX", "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGondolin), new Object[] { "X X", "X X", 'X', LOTRMod.elfSteel }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordGondolin), new Object[] { "X", "X", "Y", 'X', LOTRMod.elfSteel, 'Y', "stickWood" }));
    }
    
    private static void createTauredainUnsmeltingRecipes() {
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetTauredainGold), new Object[] { "XXX", "X X", 'X', "ingotGold" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyTauredainGold), new Object[] { "X X", "XXX", "XXX", 'X', "ingotGold" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsTauredainGold), new Object[] { "XXX", "X X", "X X", 'X', "ingotGold" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsTauredainGold), new Object[] { "X X", "X X", 'X', "ingotGold" }));
    }
    
    private static void createArnorUnsmeltingRecipes() {
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBarrow), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetArnor), new Object[] { "XXX", "X X", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyArnor), new Object[] { "X X", "XXX", "XXX", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsArnor), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsArnor), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordArnor), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerArnor), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearArnor), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
    }
    
    private static void createBNUnsmeltingRecipes() {
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlackNumenorean), new Object[] { "XXX", "X X", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlackNumenorean), new Object[] { "X X", "XXX", "XXX", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlackNumenorean), new Object[] { "XXX", "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlackNumenorean), new Object[] { "X X", "X X", 'X', "ingotIron" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.swordBlackNumenorean), new Object[] { "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBlackNumenorean), new Object[] { "X", "Y", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.spearBlackNumenorean), new Object[] { "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
        LOTRRecipes.uncraftableUnsmeltingRecipes.add((IRecipe)new ShapedOreRecipe(new ItemStack(LOTRMod.maceBlackNumenorean), new Object[] { " XX", " XX", "Y  ", 'X', "ingotIron", 'Y', "stickWood" }));
    }
    
    public static ItemStack findMatchingRecipe(final List recipeList, final InventoryCrafting inv, final World world) {
        for (int i = 0; i < recipeList.size(); ++i) {
            final IRecipe recipe = recipeList.get(i);
            if (recipe.matches(inv, world)) {
                return recipe.getCraftingResult(inv);
            }
        }
        return null;
    }
    
    public static boolean checkItemEquals(final ItemStack target, final ItemStack input) {
        return target.getItem().equals(input.getItem()) && (target.getItemDamage() == 32767 || target.getItemDamage() == input.getItemDamage());
    }
    
    private static void addDyeableWoolRobeRecipes(final List[] recipeLists, final ItemStack result, final Object... params) {
        for (final List list : recipeLists) {
            addDyeableWoolRobeRecipes(list, result, params);
        }
    }
    
    private static void addDyeableWoolRobeRecipes(final List recipeList, final ItemStack result, final Object... params) {
        for (int i = 0; i <= 15; ++i) {
            final Object[] paramsDyed = Arrays.copyOf(params, params.length);
            final ItemStack wool = new ItemStack(Blocks.wool, 1, i);
            for (int l = 0; l < paramsDyed.length; ++l) {
                final Object param = paramsDyed[l];
                if (param instanceof Block && param == Block.getBlockFromItem(wool.getItem())) {
                    paramsDyed[l] = wool.copy();
                }
                else if (param instanceof ItemStack && ((ItemStack)param).getItem() == wool.getItem()) {
                    paramsDyed[l] = wool.copy();
                }
            }
            final ItemStack resultDyed = result.copy();
            final float[] colors = EntitySheep.fleeceColorTable[i];
            final float r = colors[0];
            final float g = colors[1];
            final float b = colors[2];
            if (r != 1.0f || g != 1.0f || b != 1.0f) {
                final int rI = (int)(r * 255.0f);
                final int gI = (int)(g * 255.0f);
                final int bI = (int)(b * 255.0f);
                final int rgb = rI << 16 | gI << 8 | bI;
                LOTRItemHaradRobes.setRobesColor(resultDyed, rgb);
            }
            recipeList.add(new ShapedOreRecipe(resultDyed, paramsDyed));
        }
    }
    
    static {
        LOTRRecipes.woodenSlabRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.morgulRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.elvenRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.dwarvenRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.urukRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.woodElvenRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.gondorianRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.rohirricRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.dunlendingRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.angmarRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.nearHaradRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.highElvenRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.blueMountainsRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.rangerRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.dolGuldurRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.gundabadRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.halfTrollRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.dolAmrothRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.moredainRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.tauredainRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.daleRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.dorwinionRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.hobbitRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.rhunRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.rivendellRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.umbarRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.gulfRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.uncraftableUnsmeltingRecipes = new ArrayList<IRecipe>();
        LOTRRecipes.commonOrcRecipes = new List[] { LOTRRecipes.morgulRecipes, LOTRRecipes.urukRecipes, LOTRRecipes.angmarRecipes, LOTRRecipes.dolGuldurRecipes, LOTRRecipes.gundabadRecipes, LOTRRecipes.halfTrollRecipes };
        LOTRRecipes.commonMorgulRecipes = new List[] { LOTRRecipes.morgulRecipes, LOTRRecipes.angmarRecipes, LOTRRecipes.dolGuldurRecipes };
        LOTRRecipes.commonElfRecipes = new List[] { LOTRRecipes.elvenRecipes, LOTRRecipes.woodElvenRecipes, LOTRRecipes.highElvenRecipes, LOTRRecipes.rivendellRecipes };
        LOTRRecipes.commonHighElfRecipes = new List[] { LOTRRecipes.highElvenRecipes, LOTRRecipes.rivendellRecipes };
        LOTRRecipes.commonDwarfRecipes = new List[] { LOTRRecipes.dwarvenRecipes, LOTRRecipes.blueMountainsRecipes };
        LOTRRecipes.commonDunedainRecipes = new List[] { LOTRRecipes.gondorianRecipes, LOTRRecipes.rangerRecipes, LOTRRecipes.dolAmrothRecipes };
        LOTRRecipes.commonNumenoreanRecipes = new List[] { LOTRRecipes.gondorianRecipes, LOTRRecipes.dolAmrothRecipes, LOTRRecipes.umbarRecipes };
        LOTRRecipes.commonNearHaradRecipes = new List[] { LOTRRecipes.nearHaradRecipes, LOTRRecipes.umbarRecipes, LOTRRecipes.gulfRecipes };
        dyeOreNames = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
    }
}
