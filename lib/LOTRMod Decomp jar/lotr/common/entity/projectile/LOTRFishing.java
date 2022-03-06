// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.init.Items;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.FishingHooks;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.MathHelper;
import java.util.Random;
import java.util.List;

public class LOTRFishing
{
    private static List<FishingItem> fish;
    private static List<FishingItem> junk;
    private static List<FishingItem> treasure;
    
    public static FishResult getFishResult(final Random rand, float chance, final int luck, final int speed, final boolean allowJunkTreasure) {
        float junkChance = 0.1f - luck * 0.025f - speed * 0.01f;
        float treasureChance = 0.2f + luck * 0.01f - speed * 0.01f;
        junkChance = MathHelper.clamp_float(junkChance, 0.0f, 1.0f);
        treasureChance = MathHelper.clamp_float(treasureChance, 0.0f, 1.0f);
        if (allowJunkTreasure) {
            if (chance < junkChance) {
                final ItemStack result = ((FishingItem)WeightedRandom.getRandomItem(rand, (Collection)LOTRFishing.junk)).getRandomResult(rand);
                return new FishResult(FishingHooks.FishableCategory.JUNK, result);
            }
            chance -= junkChance;
            if (chance < treasureChance) {
                final ItemStack result = ((FishingItem)WeightedRandom.getRandomItem(rand, (Collection)LOTRFishing.treasure)).getRandomResult(rand);
                return new FishResult(FishingHooks.FishableCategory.TREASURE, result);
            }
        }
        final ItemStack result = ((FishingItem)WeightedRandom.getRandomItem(rand, (Collection)LOTRFishing.fish)).getRandomResult(rand);
        return new FishResult(FishingHooks.FishableCategory.FISH, result);
    }
    
    static {
        LOTRFishing.fish = new ArrayList<FishingItem>();
        LOTRFishing.junk = new ArrayList<FishingItem>();
        LOTRFishing.treasure = new ArrayList<FishingItem>();
        LOTRFishing.fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60));
        LOTRFishing.fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25));
        LOTRFishing.fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2));
        LOTRFishing.fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13));
        LOTRFishing.junk.add(new FishingItem(new ItemStack((Item)Items.fishing_rod), 5).setMaxDurability(0.1f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.wooden_sword), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.wooden_axe), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.wooden_pickaxe), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.wooden_shovel), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.wooden_hoe), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.leatherHat), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack((Item)Items.leather_helmet), 5).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack((Item)Items.leather_boots), 5).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.helmetBone), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.bootsBone), 2).setMaxDurability(0.5f));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.skull, 1, 0), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.bone), 20));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.orcBone), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.elfBone), 2));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.dwarfBone), 2));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.hobbitBone), 1));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.rottenLog, 1, 0), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.leather), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.string), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.bowl), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.mug), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.book), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.stick), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.feather), 10));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.dye, 1, 0), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Items.rotten_flesh), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.saltedFlesh), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.maggotyBread), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(LOTRMod.manFlesh), 5));
        LOTRFishing.junk.add(new FishingItem(new ItemStack(Blocks.waterlily), 15));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.pearl), 200));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack((Item)Items.bow), 20).setMaxDurability(0.75f));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack((Item)Items.fishing_rod), 20).setMaxDurability(0.75f));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.daggerIron), 20).setMaxDurability(0.75f));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.daggerBronze), 20).setMaxDurability(0.75f));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.silverCoin, 1, 0), 100));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.silverCoin, 1, 1), 10));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.silverCoin, 1, 2), 1));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.pouch, 1, 0), 20));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.pouch, 1, 1), 10));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.pouch, 1, 2), 5));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(Items.iron_ingot), 20));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.bronze), 10));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.copper), 10));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.tin), 10));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(Items.gold_nugget), 50));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(Items.gold_ingot), 5));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.silverNugget), 50));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.silver), 5));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.mithrilNugget), 5));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.silverRing), 10));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.goldRing), 5));
        LOTRFishing.treasure.add(new FishingItem(new ItemStack(LOTRMod.mithrilRing), 1));
    }
    
    private static class FishingItem extends WeightedRandom.Item
    {
        private final ItemStack theItem;
        private float maxDurability;
        
        private FishingItem(final ItemStack item, final int weight) {
            super(weight);
            this.maxDurability = 0.0f;
            this.theItem = item;
        }
        
        public FishingItem setMaxDurability(final float f) {
            this.maxDurability = f;
            return this;
        }
        
        public ItemStack getRandomResult(final Random rand) {
            final ItemStack result = this.theItem.copy();
            if (this.maxDurability > 0.0f) {
                final float damageF = 1.0f - rand.nextFloat() * this.maxDurability;
                int damage = (int)(damageF * result.getMaxDamage());
                damage = Math.min(damage, result.getMaxDamage());
                damage = Math.max(damage, 1);
                result.setItemDamage(damage);
            }
            return result;
        }
    }
    
    public static class FishResult
    {
        public final FishingHooks.FishableCategory category;
        public final ItemStack fishedItem;
        
        public FishResult(final FishingHooks.FishableCategory c, final ItemStack item) {
            this.category = c;
            this.fishedItem = item;
        }
    }
}
