// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRReflection;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRAchievement;
import net.minecraft.potion.Potion;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.item.EnumAction;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockMug;
import net.minecraft.util.MathHelper;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import java.util.Collection;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import lotr.client.render.LOTRDrinkIcons;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import java.util.ArrayList;
import net.minecraft.potion.PotionEffect;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemMug extends Item
{
    private static String[] strengthNames;
    private static float[] strengths;
    private static float[] foodStrengths;
    public static int vesselMeta;
    @SideOnly(Side.CLIENT)
    private IIcon[] drinkIcons;
    @SideOnly(Side.CLIENT)
    private IIcon liquidIcon;
    @SideOnly(Side.CLIENT)
    public static IIcon barrelGui_emptyBucketSlotIcon;
    @SideOnly(Side.CLIENT)
    public static IIcon barrelGui_emptyMugSlotIcon;
    public final boolean isFullMug;
    public final boolean isFoodDrink;
    public final boolean isBrewable;
    public final float alcoholicity;
    protected int foodHealAmount;
    protected float foodSaturationAmount;
    protected List<PotionEffect> potionEffects;
    protected int damageAmount;
    protected boolean curesEffects;
    
    public LOTRItemMug(final boolean full, final boolean food, final boolean brew, final float alc) {
        this.potionEffects = new ArrayList<PotionEffect>();
        if (full) {
            this.setMaxStackSize(1);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }
        else {
            this.setMaxStackSize(64);
        }
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
        this.isFullMug = full;
        this.isFoodDrink = food;
        this.isBrewable = brew;
        this.alcoholicity = alc;
    }
    
    public LOTRItemMug(final boolean full, final boolean food) {
        this(full, food, false, 0.0f);
    }
    
    public LOTRItemMug(final float alc) {
        this(true, false, true, alc);
    }
    
    public LOTRItemMug setDrinkStats(final int i, final float f) {
        this.foodHealAmount = i;
        this.foodSaturationAmount = f;
        return this;
    }
    
    public LOTRItemMug addPotionEffect(final int i, final int j) {
        this.potionEffects.add(new PotionEffect(i, j * 20));
        return this;
    }
    
    public LOTRItemMug setDamageAmount(final int i) {
        this.damageAmount = i;
        return this;
    }
    
    public LOTRItemMug setCuresEffects() {
        this.curesEffects = true;
        return this;
    }
    
    public static boolean isItemFullDrink(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemMug) {
                return ((LOTRItemMug)item).isFullMug;
            }
            if (item == Items.potionitem && itemstack.getItemDamage() == 0) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isItemEmptyDrink(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemMug) {
                return !((LOTRItemMug)item).isFullMug;
            }
            if (item == Items.glass_bottle) {
                return true;
            }
        }
        return false;
    }
    
    public static ItemStack getEquivalentDrink(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemMug) {
                return itemstack;
            }
            if (item == Items.potionitem && itemstack.getItemDamage() == 0) {
                final ItemStack water = itemstack.copy();
                water.func_150996_a(LOTRMod.mugWater);
                setVessel(water, Vessel.BOTTLE, false);
                return water;
            }
        }
        return itemstack;
    }
    
    public static ItemStack getRealDrink(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item == LOTRMod.mugWater && getVessel(itemstack) == Vessel.BOTTLE) {
                final ItemStack water = itemstack.copy();
                water.func_150996_a((Item)Items.potionitem);
                water.setItemDamage(0);
                return water;
            }
        }
        return itemstack;
    }
    
    private static int getStrengthMeta(final int damage) {
        int i = damage % LOTRItemMug.vesselMeta;
        if (i < 0 || i >= LOTRItemMug.strengths.length) {
            i = 0;
        }
        return i;
    }
    
    public static int getStrengthMeta(final ItemStack itemstack) {
        return getStrengthMeta(itemstack.getItemDamage());
    }
    
    public static void setStrengthMeta(final ItemStack itemstack, final int i) {
        final Vessel v = getVessel(itemstack);
        itemstack.setItemDamage(i);
        setVessel(itemstack, v, true);
    }
    
    public static float getStrength(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
            final int i = getStrengthMeta(itemstack);
            return LOTRItemMug.strengths[i];
        }
        return 1.0f;
    }
    
    public static float getFoodStrength(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
            final int i = getStrengthMeta(itemstack);
            return LOTRItemMug.foodStrengths[i];
        }
        return 1.0f;
    }
    
    public static Vessel getVessel(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemMug) {
            final LOTRItemMug itemMug = (LOTRItemMug)item;
            if (itemMug.isFullMug) {
                return getVessel(itemstack.getItemDamage());
            }
            return itemMug.getEmptyVesselType();
        }
        else {
            if (item == Items.glass_bottle) {
                return Vessel.BOTTLE;
            }
            if (item == Items.potionitem && itemstack.getItemDamage() == 0) {
                return Vessel.BOTTLE;
            }
            return null;
        }
    }
    
    private static Vessel getVessel(final int damage) {
        final int i = damage / LOTRItemMug.vesselMeta;
        return Vessel.forMeta(i);
    }
    
    public static void setVessel(final ItemStack itemstack, final Vessel v, final boolean correctItem) {
        if (correctItem && itemstack.getItem() == Items.potionitem && itemstack.getItemDamage() == 0) {
            itemstack.func_150996_a(LOTRMod.mugWater);
            itemstack.setItemDamage(0);
        }
        int i = itemstack.getItemDamage();
        i %= LOTRItemMug.vesselMeta;
        itemstack.setItemDamage(v.id * LOTRItemMug.vesselMeta + i);
        if (correctItem && itemstack.getItem() == LOTRMod.mugWater && v == Vessel.BOTTLE) {
            itemstack.func_150996_a((Item)Items.potionitem);
            itemstack.setItemDamage(0);
        }
    }
    
    public IIcon getIconFromDamage(final int i) {
        if (!this.isFullMug) {
            return super.getIconFromDamage(i);
        }
        if (i == -1) {
            return this.liquidIcon;
        }
        final int vessel = getVessel(i).id;
        return this.drinkIcons[vessel];
    }
    
    public void registerIcons(final IIconRegister iconregister) {
        if (this.isFullMug) {
            this.drinkIcons = new IIcon[Vessel.values().length];
            for (int i = 0; i < Vessel.values().length; ++i) {
                this.drinkIcons[i] = LOTRDrinkIcons.registerDrinkIcon(iconregister, this, this.getIconString(), Vessel.values()[i].name);
            }
            this.liquidIcon = LOTRDrinkIcons.registerLiquidIcon(iconregister, this, this.getIconString());
            LOTRItemMug.barrelGui_emptyBucketSlotIcon = iconregister.registerIcon("lotr:barrel_emptyBucketSlot");
            LOTRItemMug.barrelGui_emptyMugSlotIcon = iconregister.registerIcon("lotr:barrel_emptyMugSlot");
        }
        else {
            super.registerIcons(iconregister);
        }
    }
    
    private List<PotionEffect> convertPotionEffectsForStrength(final float strength) {
        final List<PotionEffect> list = new ArrayList<PotionEffect>();
        for (int i = 0; i < this.potionEffects.size(); ++i) {
            final PotionEffect base = this.potionEffects.get(i);
            final PotionEffect modified = new PotionEffect(base.getPotionID(), (int)(base.getDuration() * strength));
            list.add(modified);
        }
        return list;
    }
    
    public static String getStrengthSubtitle(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
                final int i = getStrengthMeta(itemstack);
                return StatCollector.translateToLocal("item.lotr.drink." + LOTRItemMug.strengthNames[i]);
            }
        }
        return null;
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        if (LOTRMod.isAprilFools() && this.isFullMug) {
            return "Hooch";
        }
        if (itemstack.getItem() == LOTRMod.mugWater && getVessel(itemstack) == Vessel.BOTTLE) {
            return "\u00c2§cMY DUDE YOU DONE MESSED UP";
        }
        return super.getItemStackDisplayName(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        if (this.isBrewable) {
            final float strength = getStrength(itemstack);
            list.add(getStrengthSubtitle(itemstack));
            if (this.alcoholicity > 0.0f) {
                EnumChatFormatting c = EnumChatFormatting.GREEN;
                final float f = this.alcoholicity * strength * 10.0f;
                if (f < 2.0f) {
                    c = EnumChatFormatting.GREEN;
                }
                else if (f < 5.0f) {
                    c = EnumChatFormatting.YELLOW;
                }
                else if (f < 10.0f) {
                    c = EnumChatFormatting.GOLD;
                }
                else if (f < 20.0f) {
                    c = EnumChatFormatting.RED;
                }
                else {
                    c = EnumChatFormatting.DARK_RED;
                }
                list.add(c + StatCollector.translateToLocal("item.lotr.drink.alcoholicity") + ": " + String.format("%.2f", f) + "%");
            }
            addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, this.convertPotionEffectsForStrength(strength));
        }
    }
    
    public static void addPotionEffectsToTooltip(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag, final List itemEffects) {
        if (!itemEffects.isEmpty()) {
            final ItemStack potionEquivalent = new ItemStack((Item)Items.potionitem);
            potionEquivalent.setItemDamage(69);
            final NBTTagList effectsData = new NBTTagList();
            for (int l = 0; l < itemEffects.size(); ++l) {
                final PotionEffect effect = itemEffects.get(l);
                final NBTTagCompound nbt = new NBTTagCompound();
                effect.writeCustomPotionEffectToNBT(nbt);
                effectsData.appendTag((NBTBase)nbt);
            }
            potionEquivalent.setTagCompound(new NBTTagCompound());
            potionEquivalent.getTagCompound().setTag("CustomPotionEffects", (NBTBase)effectsData);
            final List effectTooltips = new ArrayList();
            potionEquivalent.getItem().addInformation(potionEquivalent, entityplayer, effectTooltips, flag);
            list.addAll(effectTooltips);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        if (this.isFullMug) {
            Vessel[] vesselTypes = { Vessel.MUG };
            if (tab == null || tab.hasSearchBar()) {
                vesselTypes = Vessel.values();
            }
            for (final Vessel v : vesselTypes) {
                if (this.isBrewable) {
                    for (int str = 0; str < LOTRItemMug.strengths.length; ++str) {
                        final ItemStack drink = new ItemStack(item);
                        setStrengthMeta(drink, str);
                        setVessel(drink, v, true);
                        if (drink.getItem() == item) {
                            list.add(drink);
                        }
                    }
                }
                else {
                    final ItemStack drink2 = new ItemStack(item);
                    setVessel(drink2, v, true);
                    if (drink2.getItem() == item) {
                        list.add(drink2);
                    }
                }
            }
        }
        else {
            super.getSubItems(item, tab, list);
        }
    }
    
    protected final Vessel getEmptyVesselType() {
        for (final Vessel v : Vessel.values()) {
            if (v.getEmptyVesselItem() == this) {
                return v;
            }
        }
        return Vessel.MUG;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        return tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
    }
    
    public static boolean tryPlaceMug(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, final int side) {
        final Vessel vessel = getVessel(itemstack);
        if (vessel == null || !vessel.canPlace) {
            return false;
        }
        final Block mugBlock = vessel.getBlock();
        i += Facing.offsetsXForSide[side];
        j += Facing.offsetsYForSide[side];
        k += Facing.offsetsZForSide[side];
        final Block block = world.getBlock(i, j, k);
        if (block != null && !block.isReplaceable((IBlockAccess)world, i, j, k)) {
            return false;
        }
        if (block.getMaterial() == Material.water) {
            return false;
        }
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            return false;
        }
        if (!mugBlock.canPlaceBlockAt(world, i, j, k)) {
            return false;
        }
        final int l = MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        world.setBlock(i, j, k, mugBlock, l, 3);
        final ItemStack mugFill = itemstack.copy();
        mugFill.stackSize = 1;
        LOTRBlockMug.setMugItem(world, i, j, k, mugFill, vessel);
        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, mugBlock.stepSound.func_150496_b(), (mugBlock.stepSound.getVolume() + 1.0f) / 2.0f, mugBlock.stepSound.getFrequency() * 0.8f);
        --itemstack.stackSize;
        return true;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 32;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.drink;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (this.isFullMug) {
            if (this.canPlayerDrink(entityplayer)) {
                entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
            }
            return itemstack;
        }
        final ItemStack filled = new ItemStack(LOTRMod.mugWater);
        setVessel(filled, this.getEmptyVesselType(), true);
        final MovingObjectPosition m = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);
        if (m == null) {
            return itemstack;
        }
        if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int i = m.blockX;
            final int j = m.blockY;
            final int k = m.blockZ;
            if (!world.canMineBlock(entityplayer, i, j, k)) {
                return itemstack;
            }
            if (!entityplayer.canPlayerEdit(i, j, k, m.sideHit, itemstack)) {
                return itemstack;
            }
            if (world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0) {
                --itemstack.stackSize;
                if (itemstack.stackSize <= 0) {
                    world.playSoundAtEntity((Entity)entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                    return filled.copy();
                }
                if (!entityplayer.inventory.addItemStackToInventory(filled.copy())) {
                    entityplayer.dropPlayerItemWithRandomChoice(filled.copy(), false);
                }
                world.playSoundAtEntity((Entity)entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                return itemstack;
            }
        }
        return itemstack;
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final Vessel vessel = getVessel(itemstack);
        final float strength = getStrength(itemstack);
        final float foodStrength = getFoodStrength(itemstack);
        if (entityplayer.canEat(false)) {
            entityplayer.getFoodStats().addStats(Math.round(this.foodHealAmount * foodStrength), this.foodSaturationAmount * foodStrength);
        }
        if (this.alcoholicity > 0.0f) {
            float alcoholPower = this.alcoholicity * strength;
            int tolerance = LOTRLevelData.getData(entityplayer).getAlcoholTolerance();
            if (tolerance > 0) {
                final float f = (float)Math.pow(0.99, tolerance);
                alcoholPower *= f;
            }
            if (!world.isClient && Item.itemRand.nextFloat() < alcoholPower) {
                final int duration = (int)(60.0f * (1.0f + Item.itemRand.nextFloat() * 0.5f) * alcoholPower);
                if (duration >= 1) {
                    final int durationTicks = duration * 20;
                    entityplayer.addPotionEffect(new PotionEffect(Potion.confusion.id, durationTicks));
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getDrunk);
                    final int toleranceAdd = Math.round(duration / 20.0f);
                    tolerance += toleranceAdd;
                    LOTRLevelData.getData(entityplayer).setAlcoholTolerance(tolerance);
                }
            }
        }
        if (!world.isClient && this.shouldApplyPotionEffects(itemstack, entityplayer)) {
            final List<PotionEffect> effects = this.convertPotionEffectsForStrength(strength);
            for (int i = 0; i < effects.size(); ++i) {
                final PotionEffect effect = effects.get(i);
                entityplayer.addPotionEffect(effect);
            }
        }
        if (this.damageAmount > 0) {
            entityplayer.attackEntityFrom(DamageSource.magic, this.damageAmount * strength);
        }
        if (!world.isClient && this.curesEffects) {
            entityplayer.curePotionEffects(new ItemStack(Items.milk_bucket));
        }
        if (!world.isClient) {
            if (vessel == Vessel.SKULL) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkSkull);
            }
            if (this == LOTRMod.mugMangoJuice) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkMangoJuice);
            }
            if (this == LOTRMod.mugOrcDraught) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkOrcDraught);
            }
            if (this == LOTRMod.mugAthelasBrew) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkAthelasBrew);
                for (int j = 0; j < Potion.potionTypes.length; ++j) {
                    final Potion potion = Potion.potionTypes[j];
                    if (potion != null && LOTRReflection.isBadEffect(potion)) {
                        entityplayer.removePotionEffect(potion.id);
                    }
                }
            }
            if (this == LOTRMod.mugRedWine || this == LOTRMod.mugWhiteWine) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkWine);
            }
            if (this == LOTRMod.mugDwarvenTonic) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkDwarvenTonic);
            }
        }
        return entityplayer.capabilities.isCreativeMode ? itemstack : new ItemStack(vessel.getEmptyVesselItem());
    }
    
    protected boolean shouldApplyPotionEffects(final ItemStack itemstack, final EntityPlayer entityplayer) {
        return true;
    }
    
    public void applyToNPC(final LOTREntityNPC npc, final ItemStack itemstack) {
        final float strength = getStrength(itemstack);
        npc.heal(this.foodHealAmount * strength);
        final List<PotionEffect> effects = this.convertPotionEffectsForStrength(strength);
        for (int i = 0; i < effects.size(); ++i) {
            final PotionEffect effect = effects.get(i);
            npc.addPotionEffect(effect);
        }
        if (this.damageAmount > 0) {
            npc.attackEntityFrom(DamageSource.magic, this.damageAmount * strength);
        }
        if (this.curesEffects) {
            npc.curePotionEffects(new ItemStack(Items.milk_bucket));
        }
    }
    
    public boolean canPlayerDrink(final EntityPlayer entityplayer) {
        return this.isFullMug && (!this.isFoodDrink || entityplayer.canEat(false));
    }
    
    static {
        LOTRItemMug.strengthNames = new String[] { "weak", "light", "moderate", "strong", "potent" };
        LOTRItemMug.strengths = new float[] { 0.25f, 0.5f, 1.0f, 2.0f, 3.0f };
        LOTRItemMug.foodStrengths = new float[] { 0.5f, 0.75f, 1.0f, 1.25f, 1.5f };
        LOTRItemMug.vesselMeta = 100;
    }
    
    public enum Vessel
    {
        MUG(0, "mug", true, 0), 
        MUG_CLAY(1, "clay", true, 1), 
        GOBLET_GOLD(2, "gobletGold", true, 10), 
        GOBLET_SILVER(3, "gobletSilver", true, 8), 
        GOBLET_COPPER(4, "gobletCopper", true, 5), 
        GOBLET_WOOD(5, "gobletWood", true, 0), 
        SKULL(6, "skull", true, 3), 
        GLASS(7, "glass", true, 3), 
        BOTTLE(8, "bottle", true, 2), 
        SKIN(9, "skin", false, 0), 
        HORN(10, "horn", true, 5), 
        HORN_GOLD(11, "hornGold", true, 8);
        
        public final String name;
        public final int id;
        public final boolean canPlace;
        public final int extraPrice;
        
        private Vessel(final int i, final String s, final boolean flag, final int p) {
            this.id = i;
            this.name = s;
            this.canPlace = flag;
            this.extraPrice = p;
        }
        
        public static Vessel forMeta(final int i) {
            for (final Vessel v : values()) {
                if (v.id == i) {
                    return v;
                }
            }
            return Vessel.MUG;
        }
        
        public Item getEmptyVesselItem() {
            if (this == Vessel.MUG) {
                return LOTRMod.mug;
            }
            if (this == Vessel.MUG_CLAY) {
                return LOTRMod.ceramicMug;
            }
            if (this == Vessel.GOBLET_GOLD) {
                return LOTRMod.gobletGold;
            }
            if (this == Vessel.GOBLET_SILVER) {
                return LOTRMod.gobletSilver;
            }
            if (this == Vessel.GOBLET_COPPER) {
                return LOTRMod.gobletCopper;
            }
            if (this == Vessel.GOBLET_WOOD) {
                return LOTRMod.gobletWood;
            }
            if (this == Vessel.SKULL) {
                return LOTRMod.skullCup;
            }
            if (this == Vessel.GLASS) {
                return LOTRMod.wineGlass;
            }
            if (this == Vessel.BOTTLE) {
                return Items.glass_bottle;
            }
            if (this == Vessel.SKIN) {
                return LOTRMod.waterskin;
            }
            if (this == Vessel.HORN) {
                return LOTRMod.aleHorn;
            }
            if (this == Vessel.HORN_GOLD) {
                return LOTRMod.aleHornGold;
            }
            return LOTRMod.mug;
        }
        
        public ItemStack getEmptyVessel() {
            return new ItemStack(this.getEmptyVesselItem());
        }
        
        public Block getBlock() {
            if (this == Vessel.MUG) {
                return LOTRMod.mugBlock;
            }
            if (this == Vessel.MUG_CLAY) {
                return LOTRMod.ceramicMugBlock;
            }
            if (this == Vessel.GOBLET_GOLD) {
                return LOTRMod.gobletGoldBlock;
            }
            if (this == Vessel.GOBLET_SILVER) {
                return LOTRMod.gobletSilverBlock;
            }
            if (this == Vessel.GOBLET_COPPER) {
                return LOTRMod.gobletCopperBlock;
            }
            if (this == Vessel.GOBLET_WOOD) {
                return LOTRMod.gobletWoodBlock;
            }
            if (this == Vessel.SKULL) {
                return LOTRMod.skullCupBlock;
            }
            if (this == Vessel.GLASS) {
                return LOTRMod.wineGlassBlock;
            }
            if (this == Vessel.BOTTLE) {
                return LOTRMod.glassBottleBlock;
            }
            if (this == Vessel.SKIN) {
                return null;
            }
            if (this == Vessel.HORN) {
                return LOTRMod.aleHornBlock;
            }
            if (this == Vessel.HORN_GOLD) {
                return LOTRMod.aleHornGoldBlock;
            }
            return LOTRMod.mugBlock;
        }
    }
}
