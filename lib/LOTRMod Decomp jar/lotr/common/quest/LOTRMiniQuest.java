// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import java.util.Comparator;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRAlignmentBonusMap;
import java.util.Random;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.LOTRLore;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRDate;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTRSpeech;
import java.awt.Color;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRDimension;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.entity.npc.LOTREntityNPC;
import java.util.ArrayList;
import net.minecraft.util.ChunkCoordinates;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.item.ItemStack;
import java.util.List;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.fac.LOTRFaction;
import java.util.UUID;
import lotr.common.LOTRPlayerData;
import java.util.Map;

public abstract class LOTRMiniQuest
{
    private static Map<String, Class<? extends LOTRMiniQuest>> nameToQuestMapping;
    private static Map<Class<? extends LOTRMiniQuest>, String> questToNameMapping;
    public static int MAX_MINIQUESTS_PER_FACTION;
    public static double RENDER_HEAD_DISTANCE;
    public LOTRMiniQuestFactory questGroup;
    private LOTRPlayerData playerData;
    public UUID questUUID;
    public UUID entityUUID;
    public String entityName;
    public LOTRFaction entityFaction;
    public int dateGiven;
    public LOTRBiome biomeGiven;
    public float rewardFactor;
    public static final float defaultRewardFactor = 1.0f;
    public boolean willHire;
    public float hiringAlignment;
    public List<ItemStack> rewardItemTable;
    private boolean completed;
    public int dateCompleted;
    public int coinsRewarded;
    public float alignmentRewarded;
    public boolean wasHired;
    public List<ItemStack> itemsRewarded;
    private boolean entityDead;
    private Pair<ChunkCoordinates, Integer> lastLocation;
    public String speechBankStart;
    public String speechBankProgress;
    public String speechBankComplete;
    public String speechBankTooMany;
    public String quoteStart;
    public String quoteComplete;
    public List<String> quotesStages;
    
    private static void registerQuestType(final String name, final Class<? extends LOTRMiniQuest> questType) {
        LOTRMiniQuest.nameToQuestMapping.put(name, questType);
        LOTRMiniQuest.questToNameMapping.put(questType, name);
    }
    
    public LOTRMiniQuest(final LOTRPlayerData pd) {
        this.rewardFactor = 1.0f;
        this.willHire = false;
        this.rewardItemTable = new ArrayList<ItemStack>();
        this.itemsRewarded = new ArrayList<ItemStack>();
        this.quotesStages = new ArrayList<String>();
        this.playerData = pd;
        this.questUUID = UUID.randomUUID();
    }
    
    public void setPlayerData(final LOTRPlayerData pd) {
        this.playerData = pd;
    }
    
    public LOTRPlayerData getPlayerData() {
        return this.playerData;
    }
    
    public void setNPCInfo(final LOTREntityNPC npc) {
        this.entityUUID = npc.getUniqueID();
        this.entityName = npc.getNPCName();
        this.entityFaction = npc.getFaction();
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setString("QuestType", (String)LOTRMiniQuest.questToNameMapping.get(this.getClass()));
        if (this.questGroup != null) {
            nbt.setString("QuestGroup", this.questGroup.getBaseName());
        }
        nbt.setString("QuestUUID", this.questUUID.toString());
        nbt.setString("EntityUUID", this.entityUUID.toString());
        nbt.setString("Owner", this.entityName);
        nbt.setString("Faction", this.entityFaction.codeName());
        nbt.setInteger("DateGiven", this.dateGiven);
        if (this.biomeGiven != null) {
            nbt.setByte("BiomeID", (byte)this.biomeGiven.biomeID);
            nbt.setString("BiomeDim", this.biomeGiven.biomeDimension.dimensionName);
        }
        nbt.setFloat("RewardFactor", this.rewardFactor);
        nbt.setBoolean("WillHire", this.willHire);
        nbt.setFloat("HiringAlignF", this.hiringAlignment);
        if (!this.rewardItemTable.isEmpty()) {
            final NBTTagList itemTags = new NBTTagList();
            for (final ItemStack item : this.rewardItemTable) {
                final NBTTagCompound itemData = new NBTTagCompound();
                item.writeToNBT(itemData);
                itemTags.appendTag((NBTBase)itemData);
            }
            nbt.setTag("RewardItemTable", (NBTBase)itemTags);
        }
        nbt.setBoolean("Completed", this.completed);
        nbt.setInteger("DateCompleted", this.dateCompleted);
        nbt.setShort("CoinReward", (short)this.coinsRewarded);
        nbt.setFloat("AlignRewardF", this.alignmentRewarded);
        nbt.setBoolean("WasHired", this.wasHired);
        if (!this.itemsRewarded.isEmpty()) {
            final NBTTagList itemTags = new NBTTagList();
            for (final ItemStack item : this.itemsRewarded) {
                final NBTTagCompound itemData = new NBTTagCompound();
                item.writeToNBT(itemData);
                itemTags.appendTag((NBTBase)itemData);
            }
            nbt.setTag("ItemRewards", (NBTBase)itemTags);
        }
        nbt.setBoolean("OwnerDead", this.entityDead);
        if (this.lastLocation != null) {
            final ChunkCoordinates coords = (ChunkCoordinates)this.lastLocation.getLeft();
            nbt.setInteger("XPos", coords.posX);
            nbt.setInteger("YPos", coords.posY);
            nbt.setInteger("ZPos", coords.posZ);
            nbt.setInteger("Dimension", (int)this.lastLocation.getRight());
        }
        nbt.setString("SpeechStart", this.speechBankStart);
        nbt.setString("SpeechProgress", this.speechBankProgress);
        nbt.setString("SpeechComplete", this.speechBankComplete);
        nbt.setString("SpeechTooMany", this.speechBankTooMany);
        nbt.setString("QuoteStart", this.quoteStart);
        nbt.setString("QuoteComplete", this.quoteComplete);
        if (!this.quotesStages.isEmpty()) {
            final NBTTagList stageTags = new NBTTagList();
            for (final String s : this.quotesStages) {
                stageTags.appendTag((NBTBase)new NBTTagString(s));
            }
            nbt.setTag("QuotesStages", (NBTBase)stageTags);
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey("QuestGroup")) {
            final String groupName = nbt.getString("QuestGroup");
            final LOTRMiniQuestFactory factory = LOTRMiniQuestFactory.forName(groupName);
            if (factory != null) {
                this.questGroup = factory;
            }
        }
        if (nbt.hasKey("QuestUUID")) {
            final UUID u = UUID.fromString(nbt.getString("QuestUUID"));
            if (u != null) {
                this.questUUID = u;
            }
        }
        if (nbt.hasKey("UUIDMost") && nbt.hasKey("UUIDLeast")) {
            this.entityUUID = new UUID(nbt.getLong("UUIDMost"), nbt.getLong("UUIDLeast"));
        }
        else {
            this.entityUUID = UUID.fromString(nbt.getString("EntityUUID"));
        }
        this.entityName = nbt.getString("Owner");
        this.entityFaction = LOTRFaction.forName(nbt.getString("Faction"));
        this.dateGiven = nbt.getInteger("DateGiven");
        if (nbt.hasKey("BiomeID")) {
            final int biomeID = nbt.getByte("BiomeID") & 0xFF;
            final String biomeDimName = nbt.getString("BiomeDim");
            final LOTRDimension biomeDim = LOTRDimension.forName(biomeDimName);
            if (biomeDim != null) {
                this.biomeGiven = biomeDim.biomeList[biomeID];
            }
        }
        if (nbt.hasKey("RewardFactor")) {
            this.rewardFactor = nbt.getFloat("RewardFactor");
        }
        else {
            this.rewardFactor = 1.0f;
        }
        this.willHire = nbt.getBoolean("WillHire");
        if (nbt.hasKey("HiringAlignment")) {
            this.hiringAlignment = (float)nbt.getInteger("HiringAlignment");
        }
        else {
            this.hiringAlignment = nbt.getFloat("HiringAlignF");
        }
        this.rewardItemTable.clear();
        if (nbt.hasKey("RewardItemTable")) {
            final NBTTagList itemTags = nbt.getTagList("RewardItemTable", 10);
            for (int l = 0; l < itemTags.tagCount(); ++l) {
                final NBTTagCompound itemData = itemTags.getCompoundTagAt(l);
                final ItemStack item = ItemStack.loadItemStackFromNBT(itemData);
                if (item != null) {
                    this.rewardItemTable.add(item);
                }
            }
        }
        this.completed = nbt.getBoolean("Completed");
        this.dateCompleted = nbt.getInteger("DateCompleted");
        this.coinsRewarded = nbt.getShort("CoinReward");
        if (nbt.hasKey("AlignmentReward")) {
            this.alignmentRewarded = nbt.getShort("AlignmentReward");
        }
        else {
            this.alignmentRewarded = nbt.getFloat("AlignRewardF");
        }
        this.wasHired = nbt.getBoolean("WasHired");
        this.itemsRewarded.clear();
        if (nbt.hasKey("ItemRewards")) {
            final NBTTagList itemTags = nbt.getTagList("ItemRewards", 10);
            for (int l = 0; l < itemTags.tagCount(); ++l) {
                final NBTTagCompound itemData = itemTags.getCompoundTagAt(l);
                final ItemStack item = ItemStack.loadItemStackFromNBT(itemData);
                if (item != null) {
                    this.itemsRewarded.add(item);
                }
            }
        }
        this.entityDead = nbt.getBoolean("OwnerDead");
        if (nbt.hasKey("Dimension")) {
            final ChunkCoordinates coords = new ChunkCoordinates(nbt.getInteger("XPos"), nbt.getInteger("YPos"), nbt.getInteger("ZPos"));
            final int dimension = nbt.getInteger("Dimension");
            this.lastLocation = (Pair<ChunkCoordinates, Integer>)Pair.of((Object)coords, (Object)dimension);
        }
        this.speechBankStart = nbt.getString("SpeechStart");
        this.speechBankProgress = nbt.getString("SpeechProgress");
        this.speechBankComplete = nbt.getString("SpeechComplete");
        this.speechBankTooMany = nbt.getString("SpeechTooMany");
        this.quoteStart = nbt.getString("QuoteStart");
        this.quoteComplete = nbt.getString("QuoteComplete");
        this.quotesStages.clear();
        if (nbt.hasKey("QuotesStages")) {
            final NBTTagList stageTags = nbt.getTagList("QuotesStages", 8);
            for (int l = 0; l < stageTags.tagCount(); ++l) {
                final String s = stageTags.getStringTagAt(l);
                this.quotesStages.add(s);
            }
        }
        if (this.questGroup == null) {
            String recovery = this.speechBankStart;
            if (recovery != null) {
                final int i1 = recovery.indexOf("/", 0);
                final int i2 = recovery.indexOf("/", i1 + 1);
                if (i1 >= 0 && i2 >= 0) {
                    recovery = recovery.substring(i1 + 1, i2);
                    final LOTRMiniQuestFactory factory2 = LOTRMiniQuestFactory.forName(recovery);
                    if (factory2 != null) {
                        this.questGroup = factory2;
                    }
                }
            }
        }
    }
    
    public static LOTRMiniQuest loadQuestFromNBT(final NBTTagCompound nbt, final LOTRPlayerData playerData) {
        final String questTypeName = nbt.getString("QuestType");
        final Class questType = LOTRMiniQuest.nameToQuestMapping.get(questTypeName);
        if (questType == null) {
            FMLLog.severe("Could not instantiate miniquest of type " + questTypeName, new Object[0]);
            return null;
        }
        final LOTRMiniQuest quest = newQuestInstance((Class<LOTRMiniQuest>)questType, playerData);
        quest.readFromNBT(nbt);
        if (quest.isValidQuest()) {
            return quest;
        }
        FMLLog.severe("Loaded an invalid LOTR miniquest " + quest.speechBankStart, new Object[0]);
        return null;
    }
    
    private static <Q extends LOTRMiniQuest> Q newQuestInstance(final Class<Q> questType, final LOTRPlayerData playerData) {
        try {
            final Q quest = questType.getConstructor(LOTRPlayerData.class).newInstance(playerData);
            return quest;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean isValidQuest() {
        return this.entityUUID != null && this.entityFaction != null;
    }
    
    public boolean canPlayerAccept(final EntityPlayer entityplayer) {
        return true;
    }
    
    public String getFactionSubtitle() {
        return this.entityFaction.factionName();
    }
    
    public int getQuestColor() {
        return this.entityFaction.getFactionColor();
    }
    
    public final float[] getQuestColorComponents() {
        return new Color(this.getQuestColor()).getColorComponents(null);
    }
    
    public abstract String getQuestObjective();
    
    public abstract String getObjectiveInSpeech();
    
    public abstract String getProgressedObjectiveInSpeech();
    
    public abstract String getQuestProgress();
    
    public abstract String getQuestProgressShorthand();
    
    public abstract float getCompletionFactor();
    
    public abstract ItemStack getQuestIcon();
    
    public void onInteract(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
    }
    
    protected void sendProgressSpeechbank(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        npc.sendSpeechBank(entityplayer, this.speechBankProgress, this);
    }
    
    protected void sendCompletedSpeech(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        this.sendQuoteSpeech(entityplayer, npc, this.quoteComplete);
    }
    
    protected void sendQuoteSpeech(final EntityPlayer entityplayer, final LOTREntityNPC npc, final String quote) {
        LOTRSpeech.sendSpeech(entityplayer, npc, LOTRSpeech.formatSpeech(quote, entityplayer, null, this.getObjectiveInSpeech()));
    }
    
    public void onKill(final EntityPlayer entityplayer, final EntityLivingBase entity) {
    }
    
    public void onKilledByPlayer(final EntityPlayer entityplayer, final EntityPlayer killer) {
    }
    
    public void onPlayerTick(final EntityPlayer entityplayer) {
    }
    
    public void handleEvent(final LOTRMiniQuestEvent event) {
    }
    
    public final boolean isActive() {
        return !this.isCompleted() && !this.isFailed();
    }
    
    public boolean isFailed() {
        return this.entityDead;
    }
    
    public String getQuestFailure() {
        return StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.dead", new Object[] { this.entityName });
    }
    
    public String getQuestFailureShorthand() {
        return StatCollector.translateToLocal("lotr.gui.redBook.mq.dead");
    }
    
    public void setEntityDead() {
        this.entityDead = true;
        this.updateQuest();
    }
    
    public void start(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        this.entityUUID = npc.getUniqueID();
        this.entityName = npc.getNPCName();
        this.entityFaction = npc.getFaction();
        this.dateGiven = LOTRDate.ShireReckoning.currentDay;
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        final BiomeGenBase biome = ((Entity)entityplayer).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome) {
            this.biomeGiven = (LOTRBiome)biome;
        }
        this.playerData.addMiniQuest(this);
        npc.questInfo.addActiveQuestPlayer(entityplayer);
        this.playerData.setTrackingMiniQuest(this);
    }
    
    public boolean isCompleted() {
        return this.completed;
    }
    
    protected void complete(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        this.completed = true;
        this.dateCompleted = LOTRDate.ShireReckoning.currentDay;
        final Random rand = npc.getRNG();
        final List<ItemStack> dropItems = new ArrayList<ItemStack>();
        float alignment = this.getAlignmentBonus();
        if (alignment != 0.0f) {
            alignment *= MathHelper.randomFloatClamp(rand, 0.75f, 1.25f);
            alignment = Math.max(alignment, 1.0f);
            final LOTRAlignmentValues.AlignmentBonus bonus = LOTRAlignmentValues.createMiniquestBonus(alignment);
            final LOTRAlignmentBonusMap alignmentMap = this.playerData.addAlignment(entityplayer, bonus, this.entityFaction, (Entity)npc);
            this.alignmentRewarded = ((HashMap<K, Float>)alignmentMap).get(this.entityFaction);
        }
        int coins = this.getCoinBonus();
        if (coins != 0) {
            if (this.shouldRandomiseCoinReward()) {
                coins = Math.round(coins * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
                if (rand.nextInt(12) == 0) {
                    coins *= MathHelper.getRandomIntegerInRange(rand, 2, 5);
                }
            }
            coins = Math.max(coins, 1);
            this.coinsRewarded = coins;
            int coinsRemain = coins;
            for (int l = LOTRItemCoin.values.length - 1; l >= 0; --l) {
                final int coinValue = LOTRItemCoin.values[l];
                if (coinsRemain >= coinValue) {
                    int numCoins = coinsRemain / coinValue;
                    coinsRemain -= numCoins * coinValue;
                    while (numCoins > 64) {
                        numCoins -= 64;
                        dropItems.add(new ItemStack(LOTRMod.silverCoin, 64, l));
                    }
                    dropItems.add(new ItemStack(LOTRMod.silverCoin, numCoins, l));
                }
            }
        }
        if (!this.rewardItemTable.isEmpty()) {
            final ItemStack item = this.rewardItemTable.get(rand.nextInt(this.rewardItemTable.size()));
            dropItems.add(item.copy());
            this.itemsRewarded.add(item.copy());
        }
        if (rand.nextInt(10) == 0 && this.questGroup != null && !this.questGroup.getLoreCategories().isEmpty()) {
            final LOTRLore lore = LOTRLore.getMultiRandomLore(this.questGroup.getLoreCategories(), rand, true);
            if (lore != null) {
                final ItemStack loreBook = lore.createLoreBook(rand);
                dropItems.add(loreBook.copy());
                this.itemsRewarded.add(loreBook.copy());
            }
        }
        if (rand.nextInt(15) == 0) {
            final ItemStack modItem = LOTRItemModifierTemplate.getRandomCommonTemplate(rand);
            dropItems.add(modItem.copy());
            this.itemsRewarded.add(modItem.copy());
        }
        if (npc instanceof LOTREntityDwarf && rand.nextInt(10) == 0) {
            final ItemStack mithrilBook = new ItemStack(LOTRMod.mithrilBook);
            dropItems.add(mithrilBook.copy());
            this.itemsRewarded.add(mithrilBook.copy());
        }
        if (!dropItems.isEmpty()) {
            final boolean givePouch = rand.nextInt(10) == 0;
            if (givePouch) {
                final ItemStack pouch = npc.createNPCPouchDrop();
                npc.fillPouchFromListAndRetainUnfilled(pouch, dropItems);
                npc.entityDropItem(pouch, 0.0f);
                final ItemStack pouchCopy = pouch.copy();
                pouchCopy.setTagCompound((NBTTagCompound)null);
                this.itemsRewarded.add(pouchCopy);
            }
            npc.dropItemList(dropItems);
        }
        if (this.willHire) {
            final LOTRUnitTradeEntry tradeEntry = new LOTRUnitTradeEntry(npc.getClass(), 0, this.hiringAlignment);
            tradeEntry.setTask(LOTRHiredNPCInfo.Task.WARRIOR);
            npc.hiredNPCInfo.hireUnit(entityplayer, false, this.entityFaction, tradeEntry, null, ((Entity)npc).ridingEntity);
            this.wasHired = true;
        }
        this.updateQuest();
        this.playerData.completeMiniQuest(this);
        this.sendCompletedSpeech(entityplayer, npc);
        if (this.questGroup != null) {
            final LOTRAchievement achievement = this.questGroup.getAchievement();
            if (achievement != null) {
                this.playerData.addAchievement(achievement);
            }
        }
    }
    
    public boolean anyRewardsGiven() {
        return this.alignmentRewarded > 0.0f || this.coinsRewarded > 0 || !this.itemsRewarded.isEmpty();
    }
    
    public void updateLocation(final LOTREntityNPC npc) {
        final int i = MathHelper.floor_double(((Entity)npc).posX);
        final int j = MathHelper.floor_double(((Entity)npc).posY);
        final int k = MathHelper.floor_double(((Entity)npc).posZ);
        final ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
        final int dim = ((Entity)npc).dimension;
        ChunkCoordinates prevCoords = null;
        if (this.lastLocation != null) {
            prevCoords = (ChunkCoordinates)this.lastLocation.getLeft();
        }
        this.lastLocation = (Pair<ChunkCoordinates, Integer>)Pair.of((Object)coords, (Object)dim);
        boolean sendUpdate = false;
        sendUpdate = (prevCoords == null || coords.getDistanceSquaredToChunkCoordinates(prevCoords) > 256.0);
        if (sendUpdate) {
            this.updateQuest();
        }
    }
    
    public ChunkCoordinates getLastLocation() {
        return (this.lastLocation == null) ? null : ((ChunkCoordinates)this.lastLocation.getLeft());
    }
    
    protected void updateQuest() {
        this.playerData.updateMiniQuest(this);
    }
    
    public abstract float getAlignmentBonus();
    
    public abstract int getCoinBonus();
    
    protected boolean shouldRandomiseCoinReward() {
        return true;
    }
    
    static {
        LOTRMiniQuest.nameToQuestMapping = new HashMap<String, Class<? extends LOTRMiniQuest>>();
        LOTRMiniQuest.questToNameMapping = new HashMap<Class<? extends LOTRMiniQuest>, String>();
        registerQuestType("Collect", LOTRMiniQuestCollect.class);
        registerQuestType("KillFaction", LOTRMiniQuestKillFaction.class);
        registerQuestType("KillEntity", LOTRMiniQuestKillEntity.class);
        registerQuestType("Bounty", LOTRMiniQuestBounty.class);
        registerQuestType("Welcome", LOTRMiniQuestWelcome.class);
        LOTRMiniQuest.MAX_MINIQUESTS_PER_FACTION = 5;
        LOTRMiniQuest.RENDER_HEAD_DISTANCE = 12.0;
    }
    
    public abstract static class QuestFactoryBase<Q extends LOTRMiniQuest>
    {
        private LOTRMiniQuestFactory questFactoryGroup;
        private String questName;
        private float rewardFactor;
        private boolean willHire;
        private float hiringAlignment;
        private List<ItemStack> rewardItems;
        
        public QuestFactoryBase(final String name) {
            this.rewardFactor = 1.0f;
            this.willHire = false;
            this.questName = name;
        }
        
        public void setFactoryGroup(final LOTRMiniQuestFactory factory) {
            this.questFactoryGroup = factory;
        }
        
        public LOTRMiniQuestFactory getFactoryGroup() {
            return this.questFactoryGroup;
        }
        
        public QuestFactoryBase setRewardFactor(final float f) {
            this.rewardFactor = f;
            return this;
        }
        
        public QuestFactoryBase setHiring(final float f) {
            this.willHire = true;
            this.hiringAlignment = f;
            return this;
        }
        
        public QuestFactoryBase setRewardItems(final ItemStack[] items) {
            this.rewardItems = Arrays.asList(items);
            return this;
        }
        
        public abstract Class<Q> getQuestClass();
        
        public Q createQuest(final LOTREntityNPC npc, final Random rand) {
            final Q quest = (Q)newQuestInstance((Class<LOTRMiniQuest>)this.getQuestClass(), null);
            quest.questGroup = this.getFactoryGroup();
            final String pathName = "miniquest/" + this.getFactoryGroup().getBaseName() + "/";
            final String pathNameBaseSpeech = "miniquest/" + this.getFactoryGroup().getBaseSpeechGroup().getBaseName() + "/";
            final String questPathName = pathName + this.questName + "_";
            quest.speechBankStart = questPathName + "start";
            quest.speechBankProgress = questPathName + "progress";
            quest.speechBankComplete = questPathName + "complete";
            quest.speechBankTooMany = pathNameBaseSpeech + "_tooMany";
            quest.quoteStart = LOTRSpeech.getRandomSpeech(quest.speechBankStart);
            quest.quoteComplete = LOTRSpeech.getRandomSpeech(quest.speechBankComplete);
            quest.setNPCInfo(npc);
            quest.rewardFactor = this.rewardFactor;
            quest.willHire = this.willHire;
            quest.hiringAlignment = this.hiringAlignment;
            if (this.rewardItems != null) {
                quest.rewardItemTable.addAll(this.rewardItems);
            }
            return quest;
        }
    }
    
    public static class SorterAlphabetical implements Comparator<LOTRMiniQuest>
    {
        @Override
        public int compare(final LOTRMiniQuest q1, final LOTRMiniQuest q2) {
            if (!q2.isActive() && q1.isActive()) {
                return 1;
            }
            if (!q1.isActive() && q2.isActive()) {
                return -1;
            }
            if (q1.entityFaction == q2.entityFaction) {
                return q1.entityName.compareTo(q2.entityName);
            }
            return Integer.valueOf(q1.entityFaction.ordinal()).compareTo(q2.entityFaction.ordinal());
        }
    }
}
