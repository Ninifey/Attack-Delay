// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;

public class LOTRFoods
{
    public static LOTRFoods HOBBIT;
    public static LOTRFoods HOBBIT_DRINK;
    public static LOTRFoods RANGER;
    public static LOTRFoods RANGER_DRINK;
    public static LOTRFoods ROHAN;
    public static LOTRFoods ROHAN_DRINK;
    public static LOTRFoods GONDOR;
    public static LOTRFoods GONDOR_DRINK;
    public static LOTRFoods DALE;
    public static LOTRFoods DALE_DRINK;
    public static LOTRFoods DWARF;
    public static LOTRFoods BLUE_DWARF;
    public static LOTRFoods DWARF_DRINK;
    public static LOTRFoods DUNLENDING;
    public static LOTRFoods DUNLENDING_DRINK;
    public static LOTRFoods RHUDAUR;
    public static LOTRFoods RHUDAUR_DRINK;
    public static LOTRFoods ELF;
    public static LOTRFoods ELF_DRINK;
    public static LOTRFoods WOOD_ELF_DRINK;
    public static LOTRFoods DORWINION;
    public static LOTRFoods DORWINION_DRINK;
    public static LOTRFoods RHUN;
    public static LOTRFoods RHUN_DRINK;
    public static LOTRFoods SOUTHRON;
    public static LOTRFoods SOUTHRON_DRINK;
    public static LOTRFoods HARNEDOR;
    public static LOTRFoods HARNEDOR_DRINK;
    public static LOTRFoods CORSAIR;
    public static LOTRFoods CORSAIR_DRINK;
    public static LOTRFoods NOMAD;
    public static LOTRFoods NOMAD_DRINK;
    public static LOTRFoods GULF_HARAD;
    public static LOTRFoods GULF_HARAD_DRINK;
    public static LOTRFoods HARAD_SLAVE;
    public static LOTRFoods HARAD_SLAVE_DRINK;
    public static LOTRFoods MOREDAIN;
    public static LOTRFoods MOREDAIN_DRINK;
    public static LOTRFoods TAUREDAIN;
    public static LOTRFoods TAUREDAIN_DRINK;
    public static LOTRFoods ORC;
    public static LOTRFoods ORC_DRINK;
    public static LOTRFoods NURN_SLAVE;
    public static LOTRFoods NURN_SLAVE_DRINK;
    public static LOTRFoods HALF_TROLL;
    public static LOTRFoods HALF_TROLL_DRINK;
    private ItemStack[] foodList;
    private LOTRItemMug.Vessel[] drinkVessels;
    private LOTRItemMug.Vessel[] drinkVesselsPlaceable;
    
    public LOTRFoods(final ItemStack[] items) {
        this.foodList = items;
    }
    
    public ItemStack getRandomFood(final Random random) {
        final ItemStack food = this.foodList[random.nextInt(this.foodList.length)].copy();
        this.setDrinkVessel(food, random, false);
        return food;
    }
    
    public ItemStack getRandomPlaceableDrink(final Random random) {
        final ItemStack food = this.foodList[random.nextInt(this.foodList.length)].copy();
        this.setDrinkVessel(food, random, true);
        return food;
    }
    
    public ItemStack getRandomFoodForPlate(final Random random) {
        final List<ItemStack> foodsNoContainer = new ArrayList<ItemStack>();
        for (final ItemStack itemstack : this.foodList) {
            final Item item = itemstack.getItem();
            if (!item.hasContainerItem(itemstack)) {
                foodsNoContainer.add(itemstack.copy());
            }
        }
        final ItemStack food = foodsNoContainer.get(random.nextInt(foodsNoContainer.size()));
        return food;
    }
    
    public LOTRFoods setDrinkVessels(final LOTRItemMug.Vessel... vessels) {
        this.drinkVessels = vessels;
        final List<LOTRItemMug.Vessel> placeable = new ArrayList<LOTRItemMug.Vessel>();
        for (final LOTRItemMug.Vessel v : this.drinkVessels) {
            if (v.canPlace) {
                placeable.add(v);
            }
        }
        if (!placeable.isEmpty()) {
            this.drinkVesselsPlaceable = placeable.toArray(new LOTRItemMug.Vessel[0]);
        }
        else {
            this.drinkVesselsPlaceable = new LOTRItemMug.Vessel[] { LOTRItemMug.Vessel.MUG };
        }
        return this;
    }
    
    public LOTRItemMug.Vessel[] getDrinkVessels() {
        return this.drinkVessels;
    }
    
    public LOTRItemMug.Vessel[] getPlaceableDrinkVessels() {
        return this.drinkVesselsPlaceable;
    }
    
    public LOTRItemMug.Vessel getRandomVessel(final Random random) {
        return this.drinkVessels[random.nextInt(this.drinkVessels.length)];
    }
    
    public LOTRItemMug.Vessel getRandomPlaceableVessel(final Random random) {
        return this.drinkVesselsPlaceable[random.nextInt(this.drinkVesselsPlaceable.length)];
    }
    
    public ItemStack getRandomBrewableDrink(final Random random) {
        final List<ItemStack> alcohols = new ArrayList<ItemStack>();
        for (final ItemStack itemstack : this.foodList) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
                alcohols.add(itemstack.copy());
            }
        }
        final ItemStack drink = alcohols.get(random.nextInt(alcohols.size()));
        this.setDrinkVessel(drink, random, false);
        return drink;
    }
    
    private void setDrinkVessel(final ItemStack itemstack, final Random random, final boolean requirePlaceable) {
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isFullMug) {
            LOTRItemMug.Vessel v;
            if (requirePlaceable) {
                v = this.getRandomPlaceableVessel(random);
            }
            else {
                v = this.getRandomVessel(random);
            }
            LOTRItemMug.setVessel(itemstack, v, true);
        }
    }
    
    static {
        LOTRFoods.HOBBIT = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(Items.bread), new ItemStack(LOTRMod.cornBread), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.leek), new ItemStack(LOTRMod.leekSoup), new ItemStack(Items.mushroom_stew), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(Items.pumpkin_pie), new ItemStack(LOTRMod.mushroomPie), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.cherry), new ItemStack(LOTRMod.plum), new ItemStack(Items.cookie), new ItemStack(LOTRMod.hobbitPancake), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.elderberry), new ItemStack(LOTRMod.chestnutRoast), new ItemStack(LOTRMod.cornCooked), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate) });
        LOTRFoods.HOBBIT_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugCherryLiqueur), new ItemStack(LOTRMod.mugAppleJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE);
        LOTRFoods.RANGER = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.bread), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.elderberry) });
        LOTRFoods.RANGER_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
        LOTRFoods.ROHAN = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry) });
        LOTRFoods.ROHAN_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.HORN, LOTRItemMug.Vessel.HORN_GOLD);
        LOTRFoods.GONDOR = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry) });
        LOTRFoods.GONDOR_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.HORN);
        LOTRFoods.DALE = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate) });
        LOTRFoods.DALE_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugVodka), new ItemStack(LOTRMod.mugPlumKvass), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugAppleJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.HORN);
        LOTRFoods.DWARF = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.bread), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram) });
        LOTRFoods.BLUE_DWARF = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.bread) });
        LOTRFoods.DWARF_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenTonic) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.HORN);
        LOTRFoods.DUNLENDING = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.chestnutRoast) });
        LOTRFoods.DUNLENDING_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugRum) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
        LOTRFoods.RHUDAUR = new LOTRFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(Items.rotten_flesh) });
        LOTRFoods.RHUDAUR_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugOrcDraught) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
        LOTRFoods.ELF = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.chestnutRoast) });
        LOTRFoods.ELF_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugAppleJuice), new ItemStack(LOTRMod.mugBlueberryJuice), new ItemStack(LOTRMod.mugBlackberryJuice), new ItemStack(LOTRMod.mugElderberryJuice), new ItemStack(LOTRMod.mugRaspberryJuice), new ItemStack(LOTRMod.mugCranberryJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE);
        LOTRFoods.WOOD_ELF_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugAppleJuice), new ItemStack(LOTRMod.mugBlueberryJuice), new ItemStack(LOTRMod.mugBlackberryJuice), new ItemStack(LOTRMod.mugElderberryJuice), new ItemStack(LOTRMod.mugRaspberryJuice), new ItemStack(LOTRMod.mugCranberryJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE);
        LOTRFoods.DORWINION = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.almond), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.grapeRed), new ItemStack(LOTRMod.grapeWhite), new ItemStack(LOTRMod.raisins) });
        LOTRFoods.DORWINION_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugRedGrapeJuice), new ItemStack(LOTRMod.mugWhiteGrapeJuice) }).setDrinkVessels(LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE);
        LOTRFoods.RHUN = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.almond), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_porkchop), new ItemStack(LOTRMod.grapeRed), new ItemStack(LOTRMod.grapeWhite), new ItemStack(LOTRMod.raisins), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.pomegranate) });
        LOTRFoods.RHUN_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugRedGrapeJuice), new ItemStack(LOTRMod.mugWhiteGrapeJuice), new ItemStack(LOTRMod.mugPomegranateWine), new ItemStack(LOTRMod.mugPomegranateJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN);
        LOTRFoods.SOUTHRON = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.lemon), new ItemStack(LOTRMod.orange), new ItemStack(LOTRMod.lime), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.almond), new ItemStack(LOTRMod.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked) });
        LOTRFoods.SOUTHRON_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugLemonLiqueur), new ItemStack(LOTRMod.mugLemonade), new ItemStack(LOTRMod.mugLimeLiqueur), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN);
        LOTRFoods.HARNEDOR = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked) });
        LOTRFoods.HARNEDOR_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN);
        LOTRFoods.CORSAIR = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.date), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab) });
        LOTRFoods.CORSAIR_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugRum) }).setDrinkVessels(LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.SKULL);
        LOTRFoods.NOMAD = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.date), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.saltedFlesh) });
        LOTRFoods.NOMAD_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugAle) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN);
        LOTRFoods.GULF_HARAD = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.orange), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.saltedFlesh), new ItemStack(LOTRMod.saltedFlesh), new ItemStack(LOTRMod.saltedFlesh) });
        LOTRFoods.GULF_HARAD_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMangoJuice) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKULL);
        LOTRFoods.HARAD_SLAVE = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.kebab) });
        LOTRFoods.HARAD_SLAVE_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugWater) }).setDrinkVessels(LOTRItemMug.Vessel.SKIN);
        LOTRFoods.MOREDAIN = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.bananaBread), new ItemStack(LOTRMod.banana), new ItemStack(LOTRMod.mango), new ItemStack(Items.melon), new ItemStack(LOTRMod.lionCooked), new ItemStack(LOTRMod.zebraCooked), new ItemStack(LOTRMod.rhinoCooked), new ItemStack(LOTRMod.yamRoast) });
        LOTRFoods.MOREDAIN_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugMangoJuice), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMelonLiqueur) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.SKIN);
        LOTRFoods.TAUREDAIN = new LOTRFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(LOTRMod.bananaBread), new ItemStack(LOTRMod.cornBread), new ItemStack(LOTRMod.corn), new ItemStack(LOTRMod.cornCooked), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.banana), new ItemStack(LOTRMod.mango), new ItemStack(Items.melon), new ItemStack(LOTRMod.melonSoup), new ItemStack(Items.cooked_fished) });
        LOTRFoods.TAUREDAIN_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugChocolate), new ItemStack(LOTRMod.mugMangoJuice), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMelonLiqueur), new ItemStack(LOTRMod.mugCornLiquor) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD);
        LOTRFoods.ORC = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.maggotyBread) });
        LOTRFoods.ORC_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugOrcDraught) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN);
        LOTRFoods.NURN_SLAVE = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.maggotyBread) });
        LOTRFoods.NURN_SLAVE_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugOrcDraught) }).setDrinkVessels(LOTRItemMug.Vessel.SKIN);
        LOTRFoods.HALF_TROLL = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.maggotyBread), new ItemStack(Items.rotten_flesh), new ItemStack(LOTRMod.lionRaw), new ItemStack(LOTRMod.zebraRaw), new ItemStack(LOTRMod.rhinoRaw), new ItemStack(LOTRMod.torogStew) });
        LOTRFoods.HALF_TROLL_DRINK = new LOTRFoods(new ItemStack[] { new ItemStack(LOTRMod.mugTorogDraught) }).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN);
    }
}
