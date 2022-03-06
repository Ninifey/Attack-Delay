// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import java.util.HashMap;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketCancelItemHighlight;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.server.MinecraftServer;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import net.minecraft.item.ItemTool;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.player.InventoryPlayer;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.StatCollector;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import java.util.UUID;
import java.util.Map;

public class LOTREnchantmentHelper
{
    private static Map<UUID, ItemStack[]> lastKnownPlayerInventories;
    
    private static NBTTagList getItemEnchantTags(final ItemStack itemstack, final boolean create) {
        NBTTagCompound itemData = itemstack.getTagCompound();
        NBTTagList tags = null;
        if (itemData != null && itemData.hasKey("LOTREnch")) {
            tags = itemData.getTagList("LOTREnch", 8);
        }
        else if (create) {
            if (itemData == null) {
                itemData = new NBTTagCompound();
                itemstack.setTagCompound(itemData);
            }
            tags = new NBTTagList();
            itemData.setTag("LOTREnch", (NBTBase)tags);
        }
        return tags;
    }
    
    private static NBTTagCompound getItemEnchantProgress(final ItemStack itemstack, final LOTREnchantment ench, final boolean create) {
        NBTTagCompound itemData = itemstack.getTagCompound();
        if (itemData != null && itemData.hasKey("LOTREnchProgress")) {
            final NBTTagList tags = itemData.getTagList("LOTREnchProgress", 10);
            for (int i = 0; i < tags.tagCount(); ++i) {
                final NBTTagCompound enchData = tags.getCompoundTagAt(i);
                if (enchData.getString("Name").equals(ench.enchantName)) {
                    return enchData;
                }
            }
            if (create) {
                final NBTTagCompound enchData2 = new NBTTagCompound();
                enchData2.setString("Name", ench.enchantName);
                tags.appendTag((NBTBase)enchData2);
                return enchData2;
            }
        }
        else if (create) {
            if (itemData == null) {
                itemData = new NBTTagCompound();
                itemstack.setTagCompound(itemData);
            }
            final NBTTagList tags = new NBTTagList();
            itemData.setTag("LOTREnchProgress", (NBTBase)tags);
            final NBTTagCompound enchData2 = new NBTTagCompound();
            enchData2.setString("Name", ench.enchantName);
            tags.appendTag((NBTBase)enchData2);
            return enchData2;
        }
        return null;
    }
    
    public static boolean hasEnchant(final ItemStack itemstack, final LOTREnchantment ench) {
        final NBTTagList tags = getItemEnchantTags(itemstack, false);
        if (tags != null) {
            for (int i = 0; i < tags.tagCount(); ++i) {
                final String s = tags.getStringTagAt(i);
                if (s.equals(ench.enchantName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void setHasEnchant(final ItemStack itemstack, final LOTREnchantment ench) {
        if (!hasEnchant(itemstack, ench)) {
            final NBTTagList tags = getItemEnchantTags(itemstack, true);
            if (tags != null) {
                final String enchName = ench.enchantName;
                tags.appendTag((NBTBase)new NBTTagString(enchName));
            }
        }
    }
    
    public static void removeEnchant(final ItemStack itemstack, final LOTREnchantment ench) {
        final NBTTagList tags = getItemEnchantTags(itemstack, true);
        if (tags != null) {
            final String enchName = ench.enchantName;
            for (int i = 0; i < tags.tagCount(); ++i) {
                final String s = tags.getStringTagAt(i);
                if (s.equals(enchName)) {
                    tags.removeTag(i);
                    break;
                }
            }
        }
    }
    
    public static List<LOTREnchantment> getEnchantList(final ItemStack itemstack) {
        final List<LOTREnchantment> enchants = new ArrayList<LOTREnchantment>();
        final NBTTagList tags = getItemEnchantTags(itemstack, false);
        if (tags != null) {
            for (int i = 0; i < tags.tagCount(); ++i) {
                final String s = tags.getStringTagAt(i);
                final LOTREnchantment ench = LOTREnchantment.getEnchantmentByName(s);
                if (ench != null) {
                    enchants.add(ench);
                }
            }
        }
        return enchants;
    }
    
    public static void setEnchantList(final ItemStack itemstack, final List<LOTREnchantment> enchants) {
        clearEnchants(itemstack);
        for (final LOTREnchantment ench : enchants) {
            setHasEnchant(itemstack, ench);
        }
    }
    
    public static void clearEnchants(final ItemStack itemstack) {
        final NBTTagCompound itemData = itemstack.getTagCompound();
        if (itemData != null && itemData.hasKey("LOTREnch")) {
            itemData.removeTag("LOTREnch");
        }
    }
    
    public static void clearEnchantsAndProgress(final ItemStack itemstack) {
        clearEnchants(itemstack);
        final NBTTagCompound itemData = itemstack.getTagCompound();
        if (itemData != null && itemData.hasKey("LOTREnchProgress")) {
            itemData.removeTag("LOTREnchProgress");
        }
    }
    
    public static boolean checkEnchantCompatible(final ItemStack itemstack, final LOTREnchantment ench) {
        final List<LOTREnchantment> enchants = getEnchantList(itemstack);
        for (final LOTREnchantment itemEnch : enchants) {
            if (!itemEnch.isCompatibleWith(ench) || !ench.isCompatibleWith(itemEnch)) {
                return false;
            }
        }
        return true;
    }
    
    public static String getFullEnchantedName(final ItemStack itemstack, String name) {
        List<LOTREnchantment> enchants = getEnchantList(itemstack);
        enchants = (List<LOTREnchantment>)Lists.reverse((List)enchants);
        for (final LOTREnchantment ench : enchants) {
            name = StatCollector.translateToLocalFormatted("lotr.enchant.nameFormat", new Object[] { ench.getDisplayName(), name });
        }
        return name;
    }
    
    private static boolean hasAppliedRandomEnchants(final ItemStack itemstack) {
        final NBTTagCompound nbt = itemstack.getTagCompound();
        return nbt != null && nbt.hasKey("LOTRRandomEnch") && nbt.getBoolean("LOTRRandomEnch");
    }
    
    private static void setAppliedRandomEnchants(final ItemStack itemstack) {
        if (!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("LOTRRandomEnch", true);
    }
    
    private static boolean canApplyAnyEnchant(final ItemStack itemstack) {
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.canApply(itemstack, true)) {
                return true;
            }
        }
        return false;
    }
    
    public static int getAnvilCost(final ItemStack itemstack) {
        final NBTTagCompound nbt = itemstack.getTagCompound();
        if (nbt != null && nbt.hasKey("LOTRRepairCost")) {
            return nbt.getInteger("LOTRRepairCost");
        }
        return 0;
    }
    
    public static void setAnvilCost(final ItemStack itemstack, final int cost) {
        if (!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("LOTRRepairCost", cost);
    }
    
    public static boolean isReforgeable(final ItemStack itemstack) {
        return !getEnchantList(itemstack).isEmpty() || canApplyAnyEnchant(itemstack);
    }
    
    public static void onEntityUpdate(final EntityLivingBase entity) {
        final Random rand = entity.getRNG();
        if (LOTRConfig.enchantingLOTR) {
            if (entity instanceof EntityLiving) {
                final boolean init = entity.getEntityData().getBoolean("LOTREnchantInit");
                if (!init) {
                    for (int i = 0; i < entity.getInventory().length; ++i) {
                        final ItemStack itemstack = entity.getEquipmentInSlot(i);
                        tryApplyRandomEnchantsForEntity(itemstack, rand);
                    }
                    entity.getEntityData().setBoolean("LOTREnchantInit", true);
                }
            }
            if (entity instanceof EntityPlayerMP) {
                final EntityPlayerMP entityplayer = (EntityPlayerMP)entity;
                final UUID playerID = entityplayer.getUniqueID();
                final InventoryPlayer inv = ((EntityPlayer)entityplayer).inventory;
                ItemStack[] lastKnownInv = LOTREnchantmentHelper.lastKnownPlayerInventories.get(playerID);
                if (lastKnownInv == null) {
                    lastKnownInv = new ItemStack[inv.getSizeInventory()];
                }
                for (int j = 0; j < inv.getSizeInventory(); ++j) {
                    final ItemStack itemstack2 = inv.getStackInSlot(j);
                    ItemStack lastKnownItem = lastKnownInv[j];
                    if (!ItemStack.areItemStacksEqual(itemstack2, lastKnownItem)) {
                        tryApplyRandomEnchantsForEntity(itemstack2, rand);
                        lastKnownItem = ((itemstack2 == null) ? null : itemstack2.copy());
                        lastKnownInv[j] = lastKnownItem;
                    }
                }
                if (tryApplyRandomEnchantsForEntity(inv.getItemStack(), rand)) {
                    entityplayer.updateHeldItem();
                }
                LOTREnchantmentHelper.lastKnownPlayerInventories.put(playerID, lastKnownInv);
                if (LOTREnchantmentHelper.lastKnownPlayerInventories.size() > 200) {
                    LOTREnchantmentHelper.lastKnownPlayerInventories.clear();
                }
            }
        }
    }
    
    private static boolean tryApplyRandomEnchantsForEntity(final ItemStack itemstack, final Random rand) {
        if (itemstack != null && !hasAppliedRandomEnchants(itemstack) && canApplyAnyEnchant(itemstack)) {
            applyRandomEnchantments(itemstack, rand, false, false);
            return true;
        }
        return false;
    }
    
    public static int getSkilfulWeight(final LOTREnchantment ench) {
        int weight = ench.getEnchantWeight();
        double wd = weight;
        if (ench.isBeneficial()) {
            wd = Math.pow(wd, 0.3);
        }
        wd *= 100.0;
        if (!ench.isBeneficial()) {
            wd *= 0.15;
        }
        weight = (int)Math.round(wd);
        weight = Math.max(weight, 1);
        return weight;
    }
    
    public static void applyRandomEnchantments(final ItemStack itemstack, final Random random, final boolean skilful, final boolean keepBanes) {
        if (!keepBanes) {
            clearEnchantsAndProgress(itemstack);
        }
        else {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (!ench.persistsReforge()) {
                    removeEnchant(itemstack, ench);
                }
            }
        }
        if (itemstack.getItem() instanceof ItemSword && LOTRMaterial.getToolMaterialByName(((ItemSword)itemstack.getItem()).func_150932_j()) == LOTRMaterial.BARROW.toToolMaterial()) {
            final LOTREnchantment ench2 = LOTREnchantment.baneWight;
            if (ench2.canApply(itemstack, false)) {
                setHasEnchant(itemstack, ench2);
            }
        }
        if (itemstack.getItem() == LOTRMod.sting) {
            final LOTREnchantment ench2 = LOTREnchantment.baneSpider;
            if (ench2.canApply(itemstack, false)) {
                setHasEnchant(itemstack, ench2);
            }
        }
        int enchants2 = 0;
        final float chance = random.nextFloat();
        if (skilful) {
            if (chance < 0.15f) {
                enchants2 = 2;
            }
            else if (chance < 0.8f) {
                enchants2 = 1;
            }
        }
        else if (chance < 0.1f) {
            enchants2 = 2;
        }
        else if (chance < 0.65f) {
            enchants2 = 1;
        }
        final List<WeightedRandomEnchant> applicable = new ArrayList<WeightedRandomEnchant>();
        for (final LOTREnchantment ench3 : LOTREnchantment.allEnchantments) {
            if (ench3.canApply(itemstack, true) && (!ench3.isSkilful() || skilful)) {
                int weight = ench3.getEnchantWeight();
                if (weight <= 0) {
                    continue;
                }
                if (skilful) {
                    weight = getSkilfulWeight(ench3);
                }
                else {
                    weight *= 100;
                }
                if (weight > 0 && itemstack.getItem() instanceof ItemTool && !ench3.itemTypes.contains(LOTREnchantmentType.TOOL) && !ench3.itemTypes.contains(LOTREnchantmentType.BREAKABLE)) {
                    weight /= 3;
                    weight = Math.max(weight, 1);
                }
                final WeightedRandomEnchant wre = new WeightedRandomEnchant(ench3, weight);
                applicable.add(wre);
            }
        }
        if (!applicable.isEmpty()) {
            final List<LOTREnchantment> chosenEnchants = new ArrayList<LOTREnchantment>();
            for (int l = 0; l < enchants2 && !applicable.isEmpty(); ++l) {
                final WeightedRandomEnchant chosenWre = (WeightedRandomEnchant)WeightedRandom.getRandomItem(random, (Collection)applicable);
                final LOTREnchantment chosenEnch = chosenWre.theEnchant;
                chosenEnchants.add(chosenEnch);
                applicable.remove(chosenWre);
                final List<WeightedRandomEnchant> nowIncompatibles = new ArrayList<WeightedRandomEnchant>();
                for (final WeightedRandomEnchant wre2 : applicable) {
                    final LOTREnchantment otherEnch = wre2.theEnchant;
                    if (!otherEnch.isCompatibleWith(chosenEnch)) {
                        nowIncompatibles.add(wre2);
                    }
                }
                applicable.removeAll(nowIncompatibles);
            }
            for (final LOTREnchantment ench4 : chosenEnchants) {
                if (ench4.canApply(itemstack, false)) {
                    setHasEnchant(itemstack, ench4);
                }
            }
        }
        if (!getEnchantList(itemstack).isEmpty() || canApplyAnyEnchant(itemstack)) {
            setAppliedRandomEnchants(itemstack);
        }
    }
    
    public static float calcTradeValueFactor(final ItemStack itemstack) {
        float value = 1.0f;
        final List<LOTREnchantment> enchants = getEnchantList(itemstack);
        for (final LOTREnchantment ench : enchants) {
            value *= ench.getValueModifier();
            if (ench.isSkilful()) {
                value *= 1.5f;
            }
        }
        return value;
    }
    
    public static float calcBaseMeleeDamageBoost(final ItemStack itemstack) {
        float damage = 0.0f;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentDamage) {
                    damage += ((LOTREnchantmentDamage)ench).getBaseDamageBoost();
                }
            }
        }
        return damage;
    }
    
    public static float calcEntitySpecificDamage(final ItemStack itemstack, final EntityLivingBase entity) {
        float damage = 0.0f;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentDamage) {
                    damage += ((LOTREnchantmentDamage)ench).getEntitySpecificDamage(entity);
                }
            }
        }
        return damage;
    }
    
    public static float calcMeleeSpeedFactor(final ItemStack itemstack) {
        float speed = 1.0f;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentMeleeSpeed) {
                    speed *= ((LOTREnchantmentMeleeSpeed)ench).speedFactor;
                }
            }
        }
        return speed;
    }
    
    public static float calcMeleeReachFactor(final ItemStack itemstack) {
        float reach = 1.0f;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentMeleeReach) {
                    reach *= ((LOTREnchantmentMeleeReach)ench).reachFactor;
                }
            }
        }
        return reach;
    }
    
    public static int calcExtraKnockback(final ItemStack itemstack) {
        int kb = 0;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentKnockback) {
                    kb += ((LOTREnchantmentKnockback)ench).knockback;
                }
            }
        }
        return kb;
    }
    
    public static boolean negateDamage(final ItemStack itemstack, final Random random) {
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentDurability) {
                    final float durability = ((LOTREnchantmentDurability)ench).durabilityFactor;
                    if (durability <= 1.0f) {
                        continue;
                    }
                    final float inv = 1.0f / durability;
                    if (random.nextFloat() > inv) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }
    
    public static float calcToolEfficiency(final ItemStack itemstack) {
        float speed = 1.0f;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentToolSpeed) {
                    speed *= ((LOTREnchantmentToolSpeed)ench).speedFactor;
                }
            }
        }
        return speed;
    }
    
    public static boolean isSilkTouch(final ItemStack itemstack) {
        return itemstack != null && hasEnchant(itemstack, LOTREnchantment.toolSilk);
    }
    
    public static int calcLootingLevel(final ItemStack itemstack) {
        int looting = 0;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentLooting) {
                    looting += ((LOTREnchantmentLooting)ench).lootLevel;
                }
            }
        }
        return looting;
    }
    
    public static int calcCommonArmorProtection(final ItemStack itemstack) {
        int protection = 0;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentProtection) {
                    protection += ((LOTREnchantmentProtection)ench).protectLevel;
                }
            }
        }
        return protection;
    }
    
    public static int calcSpecialArmorSetProtection(final ItemStack[] armor, final DamageSource source) {
        int protection = 0;
        if (armor != null) {
            for (final ItemStack itemstack : armor) {
                if (itemstack != null) {
                    final List<LOTREnchantment> enchants = getEnchantList(itemstack);
                    for (final LOTREnchantment ench : enchants) {
                        if (ench instanceof LOTREnchantmentProtectionSpecial) {
                            protection += ((LOTREnchantmentProtectionSpecial)ench).calcSpecialProtection(source);
                        }
                    }
                }
            }
        }
        return protection;
    }
    
    public static int getMaxFireProtectionLevel(final ItemStack[] armor) {
        int max = 0;
        if (armor != null) {
            for (final ItemStack itemstack : armor) {
                if (itemstack != null) {
                    final List<LOTREnchantment> enchants = getEnchantList(itemstack);
                    for (final LOTREnchantment ench : enchants) {
                        if (ench instanceof LOTREnchantmentProtectionFire) {
                            final int protection = ((LOTREnchantmentProtectionFire)ench).protectLevel;
                            if (protection <= max) {
                                continue;
                            }
                            max = protection;
                        }
                    }
                }
            }
        }
        return max;
    }
    
    public static float calcRangedDamageFactor(final ItemStack itemstack) {
        float damage = 1.0f;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentRangedDamage) {
                    damage *= ((LOTREnchantmentRangedDamage)ench).damageFactor;
                }
            }
        }
        return damage;
    }
    
    public static int calcRangedKnockback(final ItemStack itemstack) {
        int kb = 0;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench instanceof LOTREnchantmentRangedKnockback) {
                    kb += ((LOTREnchantmentRangedKnockback)ench).knockback;
                }
            }
        }
        return kb;
    }
    
    public static int calcFireAspect(final ItemStack itemstack) {
        int fire = 0;
        if (itemstack != null) {
            final List<LOTREnchantment> enchants = getEnchantList(itemstack);
            for (final LOTREnchantment ench : enchants) {
                if (ench == LOTREnchantment.fire) {
                    fire += LOTREnchantmentWeaponSpecial.getFireAmount();
                }
            }
        }
        return fire;
    }
    
    public static int calcFireAspectForMelee(final ItemStack itemstack) {
        if (itemstack != null && LOTREnchantmentType.MELEE.canApply(itemstack, false)) {
            return calcFireAspect(itemstack);
        }
        return 0;
    }
    
    public static void onKillEntity(final EntityPlayer entityplayer, final EntityLivingBase target, final DamageSource source) {
        if (source.getSourceOfDamage() == entityplayer) {
            final ItemStack weapon = entityplayer.getHeldItem();
            final Random rand = entityplayer.getRNG();
            if (weapon != null) {
                boolean progressChanged = false;
                boolean enchantsChanged = false;
                for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
                    if (ench.canApply(weapon, false) && ench instanceof LOTREnchantmentBane) {
                        final LOTREnchantmentBane enchBane = (LOTREnchantmentBane)ench;
                        if (!enchBane.isAchievable || !enchBane.isEntityType(target) || ((Entity)entityplayer).worldObj.provider instanceof LOTRWorldProviderUtumno) {
                            continue;
                        }
                        final NBTTagCompound nbt = getItemEnchantProgress(weapon, ench, true);
                        int killed = 0;
                        if (nbt.hasKey("Kills")) {
                            killed = nbt.getInteger("Kills");
                        }
                        ++killed;
                        nbt.setInteger("Kills", killed);
                        progressChanged = true;
                        int requiredKills = 0;
                        if (nbt.hasKey("KillsRequired")) {
                            requiredKills = nbt.getInteger("KillsRequired");
                        }
                        else {
                            requiredKills = enchBane.getRandomKillsRequired(rand);
                            nbt.setInteger("KillsRequired", requiredKills);
                        }
                        if (killed < requiredKills || hasEnchant(weapon, enchBane)) {
                            continue;
                        }
                        final boolean compatible = checkEnchantCompatible(weapon, enchBane);
                        if (!compatible) {
                            continue;
                        }
                        setHasEnchant(weapon, enchBane);
                        enchantsChanged = true;
                        entityplayer.addChatMessage(enchBane.getEarnMessage(weapon));
                        for (final Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                            final EntityPlayer otherPlayer = (EntityPlayer)obj;
                            if (otherPlayer != entityplayer) {
                                otherPlayer.addChatMessage(enchBane.getEarnMessageWithName(entityplayer, weapon));
                            }
                        }
                        if (enchBane == LOTREnchantment.baneElf) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneElf);
                        }
                        else if (enchBane == LOTREnchantment.baneOrc) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneOrc);
                        }
                        else if (enchBane == LOTREnchantment.baneDwarf) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneDwarf);
                        }
                        else if (enchBane == LOTREnchantment.baneWarg) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneWarg);
                        }
                        else if (enchBane == LOTREnchantment.baneTroll) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneTroll);
                        }
                        else if (enchBane == LOTREnchantment.baneSpider) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneSpider);
                        }
                        else {
                            if (enchBane != LOTREnchantment.baneWight) {
                                continue;
                            }
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.enchantBaneWight);
                        }
                    }
                }
                if (progressChanged && !enchantsChanged) {
                    final LOTRPacketCancelItemHighlight pkt = new LOTRPacketCancelItemHighlight();
                    LOTRPacketHandler.networkWrapper.sendTo((IMessage)pkt, (EntityPlayerMP)entityplayer);
                }
            }
        }
    }
    
    private static NBTTagList getEntityEnchantTags(final Entity entity, final boolean create) {
        final NBTTagCompound data = entity.getEntityData();
        NBTTagList tags = null;
        if (data != null && data.hasKey("LOTREnchEntity")) {
            tags = data.getTagList("LOTREnchEntity", 8);
        }
        else if (create) {
            tags = new NBTTagList();
            data.setTag("LOTREnchEntity", (NBTBase)tags);
        }
        return tags;
    }
    
    public static void setProjectileEnchantment(final Entity entity, final LOTREnchantment ench) {
        if (!hasProjectileEnchantment(entity, ench)) {
            final NBTTagList tags = getEntityEnchantTags(entity, true);
            if (tags != null) {
                final String enchName = ench.enchantName;
                tags.appendTag((NBTBase)new NBTTagString(enchName));
            }
        }
    }
    
    public static boolean hasProjectileEnchantment(final Entity entity, final LOTREnchantment ench) {
        final NBTTagList tags = getEntityEnchantTags(entity, false);
        if (tags != null) {
            for (int i = 0; i < tags.tagCount(); ++i) {
                final String s = tags.getStringTagAt(i);
                if (s.equals(ench.enchantName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean hasMeleeOrRangedEnchant(final DamageSource source, final LOTREnchantment ench) {
        final Entity attacker = source.getEntity();
        final Entity sourceEntity = source.getSourceOfDamage();
        if (attacker instanceof EntityLivingBase) {
            final EntityLivingBase attackerLiving = (EntityLivingBase)attacker;
            if (attackerLiving == sourceEntity) {
                final ItemStack weapon = attackerLiving.getHeldItem();
                if (weapon != null && LOTREnchantmentType.MELEE.canApply(weapon, false) && hasEnchant(weapon, ench)) {
                    return true;
                }
            }
        }
        return sourceEntity != null && hasProjectileEnchantment(sourceEntity, ench);
    }
    
    static {
        LOTREnchantmentHelper.lastKnownPlayerInventories = new HashMap<UUID, ItemStack[]>();
    }
    
    public static class WeightedRandomEnchant extends WeightedRandom.Item
    {
        public final LOTREnchantment theEnchant;
        
        public WeightedRandomEnchant(final LOTREnchantment e, final int weight) {
            super(weight);
            this.theEnchant = e;
        }
    }
}
