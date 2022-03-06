// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import org.apache.commons.lang3.tuple.Pair;
import lotr.common.network.LOTRPacketTitle;
import lotr.common.network.LOTRPacketMessage;
import lotr.common.network.LOTRPacketMiniquestTrackClient;
import lotr.common.quest.LOTRMiniQuestEvent;
import lotr.common.quest.LOTRMiniQuestWelcome;
import lotr.common.network.LOTRPacketMiniquest;
import lotr.common.network.LOTRPacketMiniquestRemove;
import lotr.common.network.LOTRPacketUpdateViewingFaction;
import org.apache.commons.lang3.StringUtils;
import lotr.common.network.LOTRPacketFellowshipRemove;
import lotr.common.network.LOTRPacketFellowship;
import lotr.common.network.LOTRPacketWaypointCooldownFraction;
import lotr.common.network.LOTRPacketCWPSharedHideClient;
import lotr.common.network.LOTRPacketCWPSharedUnlockClient;
import cpw.mods.fml.common.FMLLog;
import lotr.common.network.LOTRPacketShareCWPClient;
import lotr.common.network.LOTRPacketRenameCWPClient;
import lotr.common.network.LOTRPacketDeleteCWPClient;
import lotr.common.network.LOTRPacketWaypointRegion;
import lotr.common.network.LOTRPacketFTTimer;
import net.minecraft.entity.EntityList;
import lotr.common.network.LOTRPacketFTScreen;
import java.util.Collection;
import net.minecraft.entity.passive.EntityTameable;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.EntityLiving;
import lotr.common.network.LOTRPacketFTBounceClient;
import lotr.common.network.LOTRPacketOptions;
import lotr.common.network.LOTRPacketAchievementRemove;
import lotr.common.network.LOTRPacketAchievement;
import lotr.common.item.LOTRItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.item.LOTRMaterial;
import lotr.common.world.biome.LOTRBiomeGenMistyMountains;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemCrossbowBolt;
import lotr.common.block.LOTRBlockCraftingTable;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import lotr.common.world.LOTRUtumnoLevel;
import net.minecraft.util.StatCollector;
import lotr.common.network.LOTRPacketBrokenPledge;
import lotr.common.fac.LOTRFactionRank;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import lotr.common.network.LOTRPacketPledge;
import lotr.common.network.LOTRPacketFactionData;
import lotr.common.network.LOTRPacketAlignDrain;
import lotr.common.network.LOTRPacketAlignmentBonus;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.command.LOTRCommandAdminHideMap;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.network.LOTRPacketAlignmentChoiceOffer;
import java.io.IOException;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.network.LOTRPacketCreateCWPClient;
import lotr.common.fellowship.LOTRFellowshipData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketLoginPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import lotr.common.fellowship.LOTRFellowshipInvite;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.util.ChunkCoordinates;
import java.util.List;
import java.util.Set;
import lotr.common.fac.LOTRFactionData;
import lotr.common.fac.LOTRFaction;
import java.util.Map;
import java.util.UUID;

public class LOTRPlayerData
{
    private UUID playerUUID;
    private boolean needsSave;
    private int pdTick;
    private Map<LOTRFaction, Float> alignments;
    private Map<LOTRFaction, LOTRFactionData> factionDataMap;
    private LOTRFaction viewingFaction;
    private Map<LOTRDimension.DimensionRegion, LOTRFaction> prevRegionFactions;
    private boolean hideAlignment;
    private Set<LOTRFaction> takenAlignmentRewards;
    private LOTRFaction pledgeFaction;
    private int pledgeKillCooldown;
    public static final int PLEDGE_KILL_COOLDOWN_MAX = 24000;
    private int pledgeBreakCooldown;
    private int pledgeBreakCooldownStart;
    private LOTRFaction brokenPledgeFaction;
    private boolean hasPre35Alignments;
    private boolean chosenUnwantedAlignments;
    private boolean hideOnMap;
    private boolean adminHideMap;
    private boolean showWaypoints;
    private boolean showCustomWaypoints;
    private boolean showHiddenSharedWaypoints;
    private boolean conquestKills;
    private List<LOTRAchievement> achievements;
    private LOTRShields shield;
    private boolean friendlyFire;
    private boolean hiredDeathMessages;
    private ChunkCoordinates deathPoint;
    private int deathDim;
    private int alcoholTolerance;
    private List<LOTRMiniQuest> miniQuests;
    private List<LOTRMiniQuest> miniQuestsCompleted;
    private int completedMiniquestCount;
    private int completedBountyQuests;
    private UUID trackingMiniQuestID;
    private List<LOTRFaction> bountiesPlaced;
    private LOTRWaypoint lastWaypoint;
    private LOTRBiome lastBiome;
    private Map<LOTRGuiMessageTypes, Boolean> sentMessageTypes;
    private LOTRTitle.PlayerTitle playerTitle;
    private boolean femRankOverride;
    private int fastTravelTimer;
    private LOTRAbstractWaypoint targetFTWaypoint;
    private int ticksUntilFT;
    private static int ticksUntilFT_max;
    private UUID uuidToMount;
    private int uuidToMountTime;
    private Set<LOTRWaypoint.Region> unlockedFTRegions;
    private List<LOTRCustomWaypoint> customWaypoints;
    private List<LOTRCustomWaypoint> customWaypointsShared;
    private Set<CWPSharedKey> cwpSharedUnlocked;
    private Set<CWPSharedKey> cwpSharedHidden;
    private Map<LOTRWaypoint, Float> wpCooldowns;
    private Map<Integer, Float> cwpCooldowns;
    private Map<CWPSharedKey, Float> cwpSharedCooldowns;
    private static int startCwpID;
    private int nextCwpID;
    private List<UUID> fellowshipIDs;
    private List<LOTRFellowshipClient> fellowshipsClient;
    private List<LOTRFellowshipInvite> fellowshipInvites;
    private List<LOTRFellowshipClient> fellowshipInvitesClient;
    private UUID chatBoundFellowshipID;
    private boolean structuresBanned;
    private boolean askedForGandalf;
    private boolean teleportedME;
    private LOTRPlayerQuestData questData;
    private int siegeActiveTime;
    
    public LOTRPlayerData(final UUID uuid) {
        this.needsSave = false;
        this.pdTick = 0;
        this.alignments = new HashMap<LOTRFaction, Float>();
        this.factionDataMap = new HashMap<LOTRFaction, LOTRFactionData>();
        this.prevRegionFactions = new HashMap<LOTRDimension.DimensionRegion, LOTRFaction>();
        this.hideAlignment = false;
        this.takenAlignmentRewards = new HashSet<LOTRFaction>();
        this.pledgeKillCooldown = 0;
        this.brokenPledgeFaction = null;
        this.hasPre35Alignments = false;
        this.chosenUnwantedAlignments = false;
        this.hideOnMap = false;
        this.adminHideMap = false;
        this.showWaypoints = true;
        this.showCustomWaypoints = true;
        this.showHiddenSharedWaypoints = true;
        this.conquestKills = true;
        this.achievements = new ArrayList<LOTRAchievement>();
        this.friendlyFire = false;
        this.hiredDeathMessages = true;
        this.miniQuests = new ArrayList<LOTRMiniQuest>();
        this.miniQuestsCompleted = new ArrayList<LOTRMiniQuest>();
        this.bountiesPlaced = new ArrayList<LOTRFaction>();
        this.sentMessageTypes = new HashMap<LOTRGuiMessageTypes, Boolean>();
        this.femRankOverride = false;
        this.unlockedFTRegions = new HashSet<LOTRWaypoint.Region>();
        this.customWaypoints = new ArrayList<LOTRCustomWaypoint>();
        this.customWaypointsShared = new ArrayList<LOTRCustomWaypoint>();
        this.cwpSharedUnlocked = new HashSet<CWPSharedKey>();
        this.cwpSharedHidden = new HashSet<CWPSharedKey>();
        this.wpCooldowns = new HashMap<LOTRWaypoint, Float>();
        this.cwpCooldowns = new HashMap<Integer, Float>();
        this.cwpSharedCooldowns = new HashMap<CWPSharedKey, Float>();
        this.nextCwpID = LOTRPlayerData.startCwpID;
        this.fellowshipIDs = new ArrayList<UUID>();
        this.fellowshipsClient = new ArrayList<LOTRFellowshipClient>();
        this.fellowshipInvites = new ArrayList<LOTRFellowshipInvite>();
        this.fellowshipInvitesClient = new ArrayList<LOTRFellowshipClient>();
        this.structuresBanned = false;
        this.askedForGandalf = false;
        this.teleportedME = false;
        this.questData = new LOTRPlayerQuestData(this);
        this.playerUUID = uuid;
        this.viewingFaction = LOTRFaction.HOBBIT;
    }
    
    public UUID getPlayerUUID() {
        return this.playerUUID;
    }
    
    private EntityPlayer getPlayer() {
        World[] searchWorlds = null;
        if (LOTRMod.proxy.isClient()) {
            searchWorlds = new World[] { LOTRMod.proxy.getClientWorld() };
        }
        else {
            searchWorlds = (World[])MinecraftServer.getServer().worldServers;
        }
        for (final World world : searchWorlds) {
            final EntityPlayer entityplayer = world.func_152378_a(this.playerUUID);
            if (entityplayer != null) {
                return entityplayer;
            }
        }
        return null;
    }
    
    private EntityPlayer getOtherPlayer(final UUID uuid) {
        for (final World world : MinecraftServer.getServer().worldServers) {
            final EntityPlayer entityplayer = world.func_152378_a(uuid);
            if (entityplayer != null) {
                return entityplayer;
            }
        }
        return null;
    }
    
    public void markDirty() {
        this.needsSave = true;
    }
    
    public boolean needsSave() {
        return this.needsSave;
    }
    
    public void save(final NBTTagCompound playerData) {
        final NBTTagList alignmentTags = new NBTTagList();
        for (final Map.Entry<LOTRFaction, Float> entry : this.alignments.entrySet()) {
            final LOTRFaction faction = entry.getKey();
            final float alignment = entry.getValue();
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Faction", faction.codeName());
            nbt.setFloat("AlignF", alignment);
            alignmentTags.appendTag((NBTBase)nbt);
        }
        playerData.setTag("AlignmentMap", (NBTBase)alignmentTags);
        final NBTTagList factionDataTags = new NBTTagList();
        for (final Map.Entry<LOTRFaction, LOTRFactionData> entry2 : this.factionDataMap.entrySet()) {
            final LOTRFaction faction2 = entry2.getKey();
            final LOTRFactionData data = entry2.getValue();
            final NBTTagCompound nbt2 = new NBTTagCompound();
            nbt2.setString("Faction", faction2.codeName());
            data.save(nbt2);
            factionDataTags.appendTag((NBTBase)nbt2);
        }
        playerData.setTag("FactionData", (NBTBase)factionDataTags);
        playerData.setString("CurrentFaction", this.viewingFaction.codeName());
        final NBTTagList prevRegionFactionTags = new NBTTagList();
        for (final Map.Entry<LOTRDimension.DimensionRegion, LOTRFaction> entry3 : this.prevRegionFactions.entrySet()) {
            final LOTRDimension.DimensionRegion region = entry3.getKey();
            final LOTRFaction faction3 = entry3.getValue();
            final NBTTagCompound nbt3 = new NBTTagCompound();
            nbt3.setString("Region", region.codeName());
            nbt3.setString("Faction", faction3.codeName());
            prevRegionFactionTags.appendTag((NBTBase)nbt3);
        }
        playerData.setTag("PrevRegionFactions", (NBTBase)prevRegionFactionTags);
        playerData.setBoolean("HideAlignment", this.hideAlignment);
        final NBTTagList takenRewardsTags = new NBTTagList();
        for (final LOTRFaction faction4 : this.takenAlignmentRewards) {
            final NBTTagCompound nbt2 = new NBTTagCompound();
            nbt2.setString("Faction", faction4.codeName());
            takenRewardsTags.appendTag((NBTBase)nbt2);
        }
        playerData.setTag("TakenAlignmentRewards", (NBTBase)takenRewardsTags);
        if (this.pledgeFaction != null) {
            playerData.setString("PledgeFac", this.pledgeFaction.codeName());
        }
        playerData.setInteger("PledgeKillCD", this.pledgeKillCooldown);
        playerData.setInteger("PledgeBreakCD", this.pledgeBreakCooldown);
        playerData.setInteger("PledgeBreakCDStart", this.pledgeBreakCooldownStart);
        if (this.brokenPledgeFaction != null) {
            playerData.setString("BrokenPledgeFac", this.brokenPledgeFaction.codeName());
        }
        playerData.setBoolean("Pre35Align", this.hasPre35Alignments);
        playerData.setBoolean("Chosen35Align", this.chosenUnwantedAlignments);
        playerData.setBoolean("HideOnMap", this.hideOnMap);
        playerData.setBoolean("AdminHideMap", this.adminHideMap);
        playerData.setBoolean("ShowWP", this.showWaypoints);
        playerData.setBoolean("ShowCWP", this.showCustomWaypoints);
        playerData.setBoolean("ShowHiddenSWP", this.showHiddenSharedWaypoints);
        playerData.setBoolean("ConquestKills", this.conquestKills);
        final NBTTagList achievementTags = new NBTTagList();
        for (final LOTRAchievement achievement : this.achievements) {
            final NBTTagCompound nbt3 = new NBTTagCompound();
            nbt3.setString("Category", achievement.category.name());
            nbt3.setInteger("ID", achievement.ID);
            achievementTags.appendTag((NBTBase)nbt3);
        }
        playerData.setTag("Achievements", (NBTBase)achievementTags);
        if (this.shield != null) {
            playerData.setString("Shield", this.shield.name());
        }
        playerData.setBoolean("FriendlyFire", this.friendlyFire);
        playerData.setBoolean("HiredDeathMessages", this.hiredDeathMessages);
        if (this.deathPoint != null) {
            playerData.setInteger("DeathX", this.deathPoint.posX);
            playerData.setInteger("DeathY", this.deathPoint.posY);
            playerData.setInteger("DeathZ", this.deathPoint.posZ);
            playerData.setInteger("DeathDim", this.deathDim);
        }
        playerData.setInteger("Alcohol", this.alcoholTolerance);
        final NBTTagList miniquestTags = new NBTTagList();
        for (final LOTRMiniQuest quest : this.miniQuests) {
            final NBTTagCompound nbt4 = new NBTTagCompound();
            quest.writeToNBT(nbt4);
            miniquestTags.appendTag((NBTBase)nbt4);
        }
        playerData.setTag("MiniQuests", (NBTBase)miniquestTags);
        final NBTTagList miniquestCompletedTags = new NBTTagList();
        for (final LOTRMiniQuest quest2 : this.miniQuestsCompleted) {
            final NBTTagCompound nbt5 = new NBTTagCompound();
            quest2.writeToNBT(nbt5);
            miniquestCompletedTags.appendTag((NBTBase)nbt5);
        }
        playerData.setTag("MiniQuestsCompleted", (NBTBase)miniquestCompletedTags);
        playerData.setInteger("MQCompleteCount", this.completedMiniquestCount);
        playerData.setInteger("MQCompletedBounties", this.completedBountyQuests);
        if (this.trackingMiniQuestID != null) {
            playerData.setString("MiniQuestTrack", this.trackingMiniQuestID.toString());
        }
        final NBTTagList bountyTags = new NBTTagList();
        for (final LOTRFaction fac : this.bountiesPlaced) {
            final String fName = fac.codeName();
            bountyTags.appendTag((NBTBase)new NBTTagString(fName));
        }
        playerData.setTag("BountiesPlaced", (NBTBase)bountyTags);
        if (this.lastWaypoint != null) {
            final String lastWPName = this.lastWaypoint.getCodeName();
            playerData.setString("LastWP", lastWPName);
        }
        if (this.lastBiome != null) {
            final int lastBiomeID = this.lastBiome.biomeID;
            playerData.setShort("LastBiome", (short)lastBiomeID);
        }
        final NBTTagList sentMessageTags = new NBTTagList();
        for (final Map.Entry<LOTRGuiMessageTypes, Boolean> entry4 : this.sentMessageTypes.entrySet()) {
            final LOTRGuiMessageTypes message = entry4.getKey();
            final boolean sent = entry4.getValue();
            final NBTTagCompound nbt6 = new NBTTagCompound();
            nbt6.setString("Message", message.getSaveName());
            nbt6.setBoolean("Sent", sent);
            sentMessageTags.appendTag((NBTBase)nbt6);
        }
        playerData.setTag("SentMessageTypes", (NBTBase)sentMessageTags);
        if (this.playerTitle != null) {
            playerData.setString("PlayerTitle", this.playerTitle.getTitle().getTitleName());
            playerData.setInteger("PlayerTitleColor", (int)this.playerTitle.getColor().getFormattingCode());
        }
        playerData.setBoolean("FemRankOverride", this.femRankOverride);
        playerData.setInteger("FTTimer", this.fastTravelTimer);
        if (this.uuidToMount != null) {
            playerData.setString("MountUUID", this.uuidToMount.toString());
        }
        playerData.setInteger("MountUUIDTime", this.uuidToMountTime);
        final NBTTagList unlockedFTRegionTags = new NBTTagList();
        for (final LOTRWaypoint.Region region2 : this.unlockedFTRegions) {
            final NBTTagCompound nbt7 = new NBTTagCompound();
            nbt7.setString("Name", region2.name());
            unlockedFTRegionTags.appendTag((NBTBase)nbt7);
        }
        playerData.setTag("UnlockedFTRegions", (NBTBase)unlockedFTRegionTags);
        final NBTTagList customWaypointTags = new NBTTagList();
        for (final LOTRCustomWaypoint waypoint : this.customWaypoints) {
            final NBTTagCompound nbt6 = new NBTTagCompound();
            waypoint.writeToNBT(nbt6, this);
            customWaypointTags.appendTag((NBTBase)nbt6);
        }
        playerData.setTag("CustomWaypoints", (NBTBase)customWaypointTags);
        final NBTTagList cwpSharedUnlockedTags = new NBTTagList();
        for (final CWPSharedKey key : this.cwpSharedUnlocked) {
            final NBTTagCompound nbt8 = new NBTTagCompound();
            nbt8.setString("SharingPlayer", key.sharingPlayer.toString());
            nbt8.setInteger("CustomID", key.waypointID);
            cwpSharedUnlockedTags.appendTag((NBTBase)nbt8);
        }
        playerData.setTag("CWPSharedUnlocked", (NBTBase)cwpSharedUnlockedTags);
        final NBTTagList cwpSharedHiddenTags = new NBTTagList();
        for (final CWPSharedKey key2 : this.cwpSharedHidden) {
            final NBTTagCompound nbt9 = new NBTTagCompound();
            nbt9.setString("SharingPlayer", key2.sharingPlayer.toString());
            nbt9.setInteger("CustomID", key2.waypointID);
            cwpSharedHiddenTags.appendTag((NBTBase)nbt9);
        }
        playerData.setTag("CWPSharedHidden", (NBTBase)cwpSharedHiddenTags);
        final NBTTagList wpCooldownTags = new NBTTagList();
        for (final Map.Entry<LOTRWaypoint, Float> e : this.wpCooldowns.entrySet()) {
            final LOTRAbstractWaypoint wp = e.getKey();
            final float fraction = e.getValue();
            final NBTTagCompound nbt10 = new NBTTagCompound();
            nbt10.setString("WPName", wp.getCodeName());
            nbt10.setFloat("Fraction", fraction);
            wpCooldownTags.appendTag((NBTBase)nbt10);
        }
        playerData.setTag("WPCooldowns", (NBTBase)wpCooldownTags);
        final NBTTagList cwpCooldownTags = new NBTTagList();
        for (final Map.Entry<Integer, Float> e2 : this.cwpCooldowns.entrySet()) {
            final int ID = e2.getKey();
            final float fraction2 = e2.getValue();
            final NBTTagCompound nbt11 = new NBTTagCompound();
            nbt11.setInteger("CustomID", ID);
            nbt11.setFloat("Fraction", fraction2);
            cwpCooldownTags.appendTag((NBTBase)nbt11);
        }
        playerData.setTag("CWPCooldowns", (NBTBase)cwpCooldownTags);
        final NBTTagList cwpSharedCooldownTags = new NBTTagList();
        for (final Map.Entry<CWPSharedKey, Float> e3 : this.cwpSharedCooldowns.entrySet()) {
            final CWPSharedKey key3 = e3.getKey();
            final float fraction3 = e3.getValue();
            final NBTTagCompound nbt12 = new NBTTagCompound();
            nbt12.setString("SharingPlayer", key3.sharingPlayer.toString());
            nbt12.setInteger("CustomID", key3.waypointID);
            nbt12.setFloat("Fraction", fraction3);
            cwpSharedCooldownTags.appendTag((NBTBase)nbt12);
        }
        playerData.setTag("CWPSharedCooldowns", (NBTBase)cwpSharedCooldownTags);
        playerData.setInteger("NextCWPID", this.nextCwpID);
        final NBTTagList fellowshipTags = new NBTTagList();
        for (final UUID fsID : this.fellowshipIDs) {
            final NBTTagCompound nbt11 = new NBTTagCompound();
            nbt11.setString("ID", fsID.toString());
            fellowshipTags.appendTag((NBTBase)nbt11);
        }
        playerData.setTag("Fellowships", (NBTBase)fellowshipTags);
        final NBTTagList fellowshipInviteTags = new NBTTagList();
        for (final LOTRFellowshipInvite invite : this.fellowshipInvites) {
            final NBTTagCompound nbt12 = new NBTTagCompound();
            nbt12.setString("ID", invite.fellowshipID.toString());
            if (invite.inviterID != null) {
                nbt12.setString("InviterID", invite.inviterID.toString());
            }
            fellowshipInviteTags.appendTag((NBTBase)nbt12);
        }
        playerData.setTag("FellowshipInvites", (NBTBase)fellowshipInviteTags);
        if (this.chatBoundFellowshipID != null) {
            playerData.setString("ChatBoundFellowship", this.chatBoundFellowshipID.toString());
        }
        playerData.setBoolean("StructuresBanned", this.structuresBanned);
        playerData.setBoolean("AskedForGandalf", this.askedForGandalf);
        playerData.setBoolean("TeleportedME", this.teleportedME);
        final NBTTagCompound questNBT = new NBTTagCompound();
        this.questData.save(questNBT);
        playerData.setTag("QuestData", (NBTBase)questNBT);
        this.needsSave = false;
    }
    
    public void load(final NBTTagCompound playerData) {
        this.alignments.clear();
        final NBTTagList alignmentTags = playerData.getTagList("AlignmentMap", 10);
        for (int i = 0; i < alignmentTags.tagCount(); ++i) {
            final NBTTagCompound nbt = alignmentTags.getCompoundTagAt(i);
            final LOTRFaction faction = LOTRFaction.forName(nbt.getString("Faction"));
            if (faction != null) {
                float alignment;
                if (nbt.hasKey("Alignment")) {
                    alignment = (float)nbt.getInteger("Alignment");
                    this.hasPre35Alignments = true;
                }
                else {
                    alignment = nbt.getFloat("AlignF");
                }
                this.alignments.put(faction, alignment);
            }
        }
        this.factionDataMap.clear();
        final NBTTagList factionDataTags = playerData.getTagList("FactionData", 10);
        for (int j = 0; j < factionDataTags.tagCount(); ++j) {
            final NBTTagCompound nbt2 = factionDataTags.getCompoundTagAt(j);
            final LOTRFaction faction2 = LOTRFaction.forName(nbt2.getString("Faction"));
            if (faction2 != null) {
                final LOTRFactionData data = new LOTRFactionData(this, faction2);
                data.load(nbt2);
                this.factionDataMap.put(faction2, data);
            }
        }
        if (playerData.hasKey("CurrentFaction")) {
            final LOTRFaction cur = LOTRFaction.forName(playerData.getString("CurrentFaction"));
            if (cur != null) {
                this.viewingFaction = cur;
            }
        }
        this.prevRegionFactions.clear();
        final NBTTagList prevRegionFactionTags = playerData.getTagList("PrevRegionFactions", 10);
        for (int k = 0; k < prevRegionFactionTags.tagCount(); ++k) {
            final NBTTagCompound nbt3 = prevRegionFactionTags.getCompoundTagAt(k);
            final LOTRDimension.DimensionRegion region = LOTRDimension.DimensionRegion.forName(nbt3.getString("Region"));
            final LOTRFaction faction3 = LOTRFaction.forName(nbt3.getString("Faction"));
            if (region != null && faction3 != null) {
                this.prevRegionFactions.put(region, faction3);
            }
        }
        this.hideAlignment = playerData.getBoolean("HideAlignment");
        this.takenAlignmentRewards.clear();
        final NBTTagList takenRewardsTags = playerData.getTagList("TakenAlignmentRewards", 10);
        for (int l = 0; l < takenRewardsTags.tagCount(); ++l) {
            final NBTTagCompound nbt4 = takenRewardsTags.getCompoundTagAt(l);
            final LOTRFaction faction3 = LOTRFaction.forName(nbt4.getString("Faction"));
            if (faction3 != null) {
                this.takenAlignmentRewards.add(faction3);
            }
        }
        this.pledgeFaction = null;
        if (playerData.hasKey("PledgeFac")) {
            this.pledgeFaction = LOTRFaction.forName(playerData.getString("PledgeFac"));
        }
        this.pledgeKillCooldown = playerData.getInteger("PledgeKillCD");
        this.pledgeBreakCooldown = playerData.getInteger("PledgeBreakCD");
        this.pledgeBreakCooldownStart = playerData.getInteger("PledgeBreakCDStart");
        this.brokenPledgeFaction = null;
        if (playerData.hasKey("BrokenPledgeFac")) {
            this.brokenPledgeFaction = LOTRFaction.forName(playerData.getString("BrokenPledgeFac"));
        }
        if (!this.hasPre35Alignments && playerData.hasKey("Pre35Align")) {
            this.hasPre35Alignments = playerData.getBoolean("Pre35Align");
        }
        if (playerData.hasKey("Chosen35Align")) {
            this.chosenUnwantedAlignments = playerData.getBoolean("Chosen35Align");
        }
        this.hideOnMap = playerData.getBoolean("HideOnMap");
        this.adminHideMap = playerData.getBoolean("AdminHideMap");
        if (playerData.hasKey("ShowWP")) {
            this.showWaypoints = playerData.getBoolean("ShowWP");
        }
        if (playerData.hasKey("ShowCWP")) {
            this.showCustomWaypoints = playerData.getBoolean("ShowCWP");
        }
        if (playerData.hasKey("ShowHiddenSWP")) {
            this.showHiddenSharedWaypoints = playerData.getBoolean("ShowHiddenSWP");
        }
        if (playerData.hasKey("ConquestKills")) {
            this.conquestKills = playerData.getBoolean("ConquestKills");
        }
        this.achievements.clear();
        final NBTTagList achievementTags = playerData.getTagList("Achievements", 10);
        for (int m = 0; m < achievementTags.tagCount(); ++m) {
            final NBTTagCompound nbt5 = achievementTags.getCompoundTagAt(m);
            final String category = nbt5.getString("Category");
            final int ID = nbt5.getInteger("ID");
            final LOTRAchievement achievement = LOTRAchievement.achievementForCategoryAndID(LOTRAchievement.categoryForName(category), ID);
            if (achievement != null && !this.achievements.contains(achievement)) {
                this.achievements.add(achievement);
            }
        }
        this.shield = null;
        if (playerData.hasKey("Shield")) {
            final LOTRShields savedShield = LOTRShields.shieldForName(playerData.getString("Shield"));
            if (savedShield != null) {
                this.shield = savedShield;
            }
        }
        if (playerData.hasKey("FriendlyFire")) {
            this.friendlyFire = playerData.getBoolean("FriendlyFire");
        }
        if (playerData.hasKey("HiredDeathMessages")) {
            this.hiredDeathMessages = playerData.getBoolean("HiredDeathMessages");
        }
        this.deathPoint = null;
        if (playerData.hasKey("DeathX") && playerData.hasKey("DeathY") && playerData.hasKey("DeathZ")) {
            this.deathPoint = new ChunkCoordinates(playerData.getInteger("DeathX"), playerData.getInteger("DeathY"), playerData.getInteger("DeathZ"));
            if (playerData.hasKey("DeathDim")) {
                this.deathDim = playerData.getInteger("DeathDim");
            }
            else {
                this.deathDim = LOTRDimension.MIDDLE_EARTH.dimensionID;
            }
        }
        this.alcoholTolerance = playerData.getInteger("Alcohol");
        this.miniQuests.clear();
        final NBTTagList miniquestTags = playerData.getTagList("MiniQuests", 10);
        for (int i2 = 0; i2 < miniquestTags.tagCount(); ++i2) {
            final NBTTagCompound nbt6 = miniquestTags.getCompoundTagAt(i2);
            final LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(nbt6, this);
            if (quest != null) {
                this.miniQuests.add(quest);
            }
        }
        this.miniQuestsCompleted.clear();
        final NBTTagList miniquestCompletedTags = playerData.getTagList("MiniQuestsCompleted", 10);
        for (int i3 = 0; i3 < miniquestCompletedTags.tagCount(); ++i3) {
            final NBTTagCompound nbt7 = miniquestCompletedTags.getCompoundTagAt(i3);
            final LOTRMiniQuest quest2 = LOTRMiniQuest.loadQuestFromNBT(nbt7, this);
            if (quest2 != null) {
                this.miniQuestsCompleted.add(quest2);
            }
        }
        this.completedMiniquestCount = playerData.getInteger("MQCompleteCount");
        this.completedBountyQuests = playerData.getInteger("MQCompletedBounties");
        this.trackingMiniQuestID = null;
        if (playerData.hasKey("MiniQuestTrack")) {
            final String s = playerData.getString("MiniQuestTrack");
            this.trackingMiniQuestID = UUID.fromString(s);
        }
        this.bountiesPlaced.clear();
        final NBTTagList bountyTags = playerData.getTagList("BountiesPlaced", 8);
        for (int i4 = 0; i4 < bountyTags.tagCount(); ++i4) {
            final String fName = bountyTags.getStringTagAt(i4);
            final LOTRFaction fac = LOTRFaction.forName(fName);
            if (fac != null) {
                this.bountiesPlaced.add(fac);
            }
        }
        this.lastWaypoint = null;
        if (playerData.hasKey("LastWP")) {
            final String lastWPName = playerData.getString("LastWP");
            this.lastWaypoint = LOTRWaypoint.waypointForName(lastWPName);
        }
        this.lastBiome = null;
        if (playerData.hasKey("LastBiome")) {
            final int lastBiomeID = playerData.getShort("LastBiome");
            final LOTRBiome[] biomeList = LOTRDimension.MIDDLE_EARTH.biomeList;
            if (lastBiomeID >= 0 && lastBiomeID < biomeList.length) {
                this.lastBiome = biomeList[lastBiomeID];
            }
        }
        this.sentMessageTypes.clear();
        final NBTTagList sentMessageTags = playerData.getTagList("SentMessageTypes", 10);
        for (int i5 = 0; i5 < sentMessageTags.tagCount(); ++i5) {
            final NBTTagCompound nbt8 = sentMessageTags.getCompoundTagAt(i5);
            final LOTRGuiMessageTypes message = LOTRGuiMessageTypes.forSaveName(nbt8.getString("Message"));
            if (message != null) {
                final boolean sent = nbt8.getBoolean("Sent");
                this.sentMessageTypes.put(message, sent);
            }
        }
        this.playerTitle = null;
        if (playerData.hasKey("PlayerTitle")) {
            final LOTRTitle title = LOTRTitle.forName(playerData.getString("PlayerTitle"));
            if (title != null) {
                final int colorCode = playerData.getInteger("PlayerTitleColor");
                final EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorCode);
                this.playerTitle = new LOTRTitle.PlayerTitle(title, color);
            }
        }
        if (playerData.hasKey("FemRankOverride")) {
            this.femRankOverride = playerData.getBoolean("FemRankOverride");
        }
        this.fastTravelTimer = playerData.getInteger("FTTimer");
        this.targetFTWaypoint = null;
        this.uuidToMount = null;
        if (playerData.hasKey("MountUUID")) {
            this.uuidToMount = UUID.fromString(playerData.getString("MountUUID"));
        }
        this.uuidToMountTime = playerData.getInteger("MountUUIDTime");
        this.unlockedFTRegions.clear();
        final NBTTagList unlockedFTRegionTags = playerData.getTagList("UnlockedFTRegions", 10);
        for (int i6 = 0; i6 < unlockedFTRegionTags.tagCount(); ++i6) {
            final NBTTagCompound nbt9 = unlockedFTRegionTags.getCompoundTagAt(i6);
            final String regionName = nbt9.getString("Name");
            final LOTRWaypoint.Region region2 = LOTRWaypoint.regionForName(regionName);
            if (region2 != null) {
                this.unlockedFTRegions.add(region2);
            }
        }
        this.customWaypoints.clear();
        final NBTTagList customWaypointTags = playerData.getTagList("CustomWaypoints", 10);
        for (int i7 = 0; i7 < customWaypointTags.tagCount(); ++i7) {
            final NBTTagCompound nbt10 = customWaypointTags.getCompoundTagAt(i7);
            final LOTRCustomWaypoint waypoint = LOTRCustomWaypoint.readFromNBT(nbt10, this);
            this.customWaypoints.add(waypoint);
        }
        this.cwpSharedUnlocked.clear();
        final NBTTagList cwpSharedUnlockedTags = playerData.getTagList("CWPSharedUnlocked", 10);
        for (int i8 = 0; i8 < cwpSharedUnlockedTags.tagCount(); ++i8) {
            final NBTTagCompound nbt11 = cwpSharedUnlockedTags.getCompoundTagAt(i8);
            final UUID sharingPlayer = UUID.fromString(nbt11.getString("SharingPlayer"));
            if (sharingPlayer != null) {
                final int ID2 = nbt11.getInteger("CustomID");
                final CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID2);
                this.cwpSharedUnlocked.add(key);
            }
        }
        this.cwpSharedHidden.clear();
        final NBTTagList cwpSharedHiddenTags = playerData.getTagList("CWPSharedHidden", 10);
        for (int i9 = 0; i9 < cwpSharedHiddenTags.tagCount(); ++i9) {
            final NBTTagCompound nbt12 = cwpSharedHiddenTags.getCompoundTagAt(i9);
            final UUID sharingPlayer2 = UUID.fromString(nbt12.getString("SharingPlayer"));
            if (sharingPlayer2 != null) {
                final int ID3 = nbt12.getInteger("CustomID");
                final CWPSharedKey key2 = CWPSharedKey.keyFor(sharingPlayer2, ID3);
                this.cwpSharedHidden.add(key2);
            }
        }
        this.wpCooldowns.clear();
        final NBTTagList wpCooldownTags = playerData.getTagList("WPCooldowns", 10);
        for (int i10 = 0; i10 < wpCooldownTags.tagCount(); ++i10) {
            final NBTTagCompound nbt13 = wpCooldownTags.getCompoundTagAt(i10);
            final String name = nbt13.getString("WPName");
            final float fraction = nbt13.getFloat("Fraction");
            final LOTRWaypoint wp = LOTRWaypoint.waypointForName(name);
            if (wp != null) {
                this.wpCooldowns.put(wp, fraction);
            }
        }
        this.cwpCooldowns.clear();
        final NBTTagList cwpCooldownTags = playerData.getTagList("CWPCooldowns", 10);
        for (int i11 = 0; i11 < cwpCooldownTags.tagCount(); ++i11) {
            final NBTTagCompound nbt14 = cwpCooldownTags.getCompoundTagAt(i11);
            final int ID4 = nbt14.getInteger("CustomID");
            final float fraction2 = nbt14.getFloat("Fraction");
            this.cwpCooldowns.put(ID4, fraction2);
        }
        this.cwpSharedCooldowns.clear();
        final NBTTagList cwpSharedCooldownTags = playerData.getTagList("CWPSharedCooldowns", 10);
        for (int i12 = 0; i12 < cwpSharedCooldownTags.tagCount(); ++i12) {
            final NBTTagCompound nbt15 = cwpSharedCooldownTags.getCompoundTagAt(i12);
            final UUID sharingPlayer3 = UUID.fromString(nbt15.getString("SharingPlayer"));
            if (sharingPlayer3 != null) {
                final int ID5 = nbt15.getInteger("CustomID");
                final CWPSharedKey key3 = CWPSharedKey.keyFor(sharingPlayer3, ID5);
                final float fraction3 = nbt15.getFloat("Fraction");
                this.cwpSharedCooldowns.put(key3, fraction3);
            }
        }
        this.nextCwpID = LOTRPlayerData.startCwpID;
        if (playerData.hasKey("NextCWPID")) {
            this.nextCwpID = playerData.getInteger("NextCWPID");
        }
        this.fellowshipIDs.clear();
        final NBTTagList fellowshipTags = playerData.getTagList("Fellowships", 10);
        for (int i13 = 0; i13 < fellowshipTags.tagCount(); ++i13) {
            final NBTTagCompound nbt16 = fellowshipTags.getCompoundTagAt(i13);
            final UUID fsID = UUID.fromString(nbt16.getString("ID"));
            if (fsID != null) {
                this.fellowshipIDs.add(fsID);
            }
        }
        this.fellowshipInvites.clear();
        final NBTTagList fellowshipInviteTags = playerData.getTagList("FellowshipInvites", 10);
        for (int i14 = 0; i14 < fellowshipInviteTags.tagCount(); ++i14) {
            final NBTTagCompound nbt17 = fellowshipInviteTags.getCompoundTagAt(i14);
            final UUID fsID2 = UUID.fromString(nbt17.getString("ID"));
            if (fsID2 != null) {
                UUID inviterID = null;
                if (nbt17.hasKey("InviterID")) {
                    inviterID = UUID.fromString(nbt17.getString("InviterID"));
                }
                this.fellowshipInvites.add(new LOTRFellowshipInvite(fsID2, inviterID));
            }
        }
        this.chatBoundFellowshipID = null;
        if (playerData.hasKey("ChatBoundFellowship")) {
            final UUID fsID3 = UUID.fromString(playerData.getString("ChatBoundFellowship"));
            if (fsID3 != null) {
                this.chatBoundFellowshipID = fsID3;
            }
        }
        this.structuresBanned = playerData.getBoolean("StructuresBanned");
        this.askedForGandalf = playerData.getBoolean("AskedForGandalf");
        this.teleportedME = playerData.getBoolean("TeleportedME");
        if (playerData.hasKey("QuestData")) {
            final NBTTagCompound questNBT = playerData.getCompoundTag("QuestData");
            this.questData.load(questNBT);
        }
    }
    
    public void sendPlayerData(final EntityPlayerMP entityplayer) throws IOException {
        final NBTTagCompound nbt = new NBTTagCompound();
        this.save(nbt);
        nbt.removeTag("Achievements");
        nbt.removeTag("MiniQuests");
        nbt.removeTag("MiniQuestsCompleted");
        nbt.removeTag("CustomWaypoints");
        nbt.removeTag("Fellowships");
        nbt.removeTag("FellowshipInvites");
        final LOTRPacketLoginPlayerData packet = new LOTRPacketLoginPlayerData(nbt);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
        for (final LOTRAchievement achievement : this.achievements) {
            this.sendAchievementPacket(entityplayer, achievement, false);
        }
        for (final LOTRMiniQuest quest : this.miniQuests) {
            this.sendMiniQuestPacket(entityplayer, quest, false);
        }
        for (final LOTRMiniQuest quest : this.miniQuestsCompleted) {
            this.sendMiniQuestPacket(entityplayer, quest, true);
        }
        for (final LOTRCustomWaypoint waypoint : this.customWaypoints) {
            final LOTRPacketCreateCWPClient cwpPacket = waypoint.getClientPacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)cwpPacket, entityplayer);
        }
        for (final UUID fsID : this.fellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null) {
                this.sendFellowshipPacket(fs);
            }
        }
        for (final LOTRFellowshipInvite invite : this.fellowshipInvites) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(invite.fellowshipID);
            if (fs != null) {
                this.sendFellowshipInvitePacket(fs);
            }
        }
        this.addSharedCustomWaypoints(null);
    }
    
    public void send35AlignmentChoice(final EntityPlayerMP entityplayer, final World world) {
        if (LOTRConfig.alignmentDrain && this.hasPre35Alignments && !this.chosenUnwantedAlignments) {
            final LOTRPacketAlignmentChoiceOffer pkt = new LOTRPacketAlignmentChoiceOffer();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)pkt, entityplayer);
        }
    }
    
    public void chooseUnwantedAlignments(final EntityPlayerMP entityplayer, final Set<LOTRFaction> setZeroFacs) {
        if (LOTRConfig.alignmentDrain && this.hasPre35Alignments && !this.chosenUnwantedAlignments) {
            final Set<LOTRFaction> validSelections = new HashSet<LOTRFaction>();
            for (final LOTRFaction fac : setZeroFacs) {
                boolean valid = false;
                if (this.getAlignment(fac) > 0.0f) {
                    for (final LOTRFaction otherFac : LOTRFaction.getPlayableAlignmentFactions()) {
                        if (fac != otherFac && this.doFactionsDrain(fac, otherFac) && this.getAlignment(otherFac) > 0.0f) {
                            valid = true;
                            break;
                        }
                    }
                }
                if (valid) {
                    validSelections.add(fac);
                }
            }
            for (final LOTRFaction fac : validSelections) {
                this.setAlignment(fac, 0.0f);
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("Set %s alignment to zero", new Object[] { fac.factionName() }));
            }
            this.hasPre35Alignments = false;
            this.chosenUnwantedAlignments = true;
            this.markDirty();
        }
    }
    
    private static boolean isTimerAutosaveTick() {
        return MinecraftServer.getServer() != null && MinecraftServer.getServer().getTickCounter() % 200 == 0;
    }
    
    public void onUpdate(final EntityPlayerMP entityplayer, final WorldServer world) {
        ++this.pdTick;
        LOTRDimension.DimensionRegion currentRegion = this.viewingFaction.factionRegion;
        final LOTRDimension currentDim = LOTRDimension.getCurrentDimension((World)world);
        if (currentRegion.getDimension() != currentDim) {
            currentRegion = currentDim.dimensionRegions.get(0);
            this.setViewingFaction(this.getRegionLastViewedFaction(currentRegion));
        }
        this.runAlignmentDraining(entityplayer);
        this.questData.onUpdate(entityplayer, world);
        if (!this.isSiegeActive()) {
            this.runAchievementChecks((EntityPlayer)entityplayer, (World)world);
        }
        if (this.playerTitle != null && !this.playerTitle.getTitle().canPlayerUse((EntityPlayer)entityplayer)) {
            final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("chat.lotr.loseTitle", new Object[] { this.playerTitle.getFullTitleComponent() });
            entityplayer.addChatMessage(msg);
            this.setPlayerTitle(null);
        }
        if (this.pledgeKillCooldown > 0) {
            --this.pledgeKillCooldown;
            if (this.pledgeKillCooldown == 0 || isTimerAutosaveTick()) {
                this.markDirty();
            }
        }
        if (this.pledgeBreakCooldown > 0) {
            this.setPledgeBreakCooldown(this.pledgeBreakCooldown - 1);
        }
        if (this.trackingMiniQuestID != null && this.getTrackingMiniQuest() == null) {
            this.setTrackingMiniQuest(null);
        }
        final List<LOTRMiniQuest> activeMiniquests = (List<LOTRMiniQuest>)this.getActiveMiniQuests();
        for (final LOTRMiniQuest quest : activeMiniquests) {
            quest.onPlayerTick((EntityPlayer)entityplayer);
        }
        if (!this.bountiesPlaced.isEmpty()) {
            for (final LOTRFaction fac : this.bountiesPlaced) {
                final IChatComponent msg2 = (IChatComponent)new ChatComponentTranslation("chat.lotr.bountyPlaced", new Object[] { fac.factionName() });
                msg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                entityplayer.addChatMessage(msg2);
            }
            this.bountiesPlaced.clear();
            this.markDirty();
        }
        if (this.fastTravelTimer > 0) {
            this.setFTTimer(this.fastTravelTimer - 1);
        }
        if (this.targetFTWaypoint != null) {
            if (this.ticksUntilFT > 0) {
                final int seconds = this.ticksUntilFT / 20;
                if (this.ticksUntilFT == LOTRPlayerData.ticksUntilFT_max) {
                    entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.fastTravel.travelTicksStart", new Object[] { seconds }));
                }
                else if (this.ticksUntilFT % 20 == 0 && seconds <= 5) {
                    entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.fastTravel.travelTicks", new Object[] { seconds }));
                }
                this.setTicksUntilFT(--this.ticksUntilFT);
            }
            else {
                this.sendFTBouncePacket(entityplayer);
            }
        }
        else {
            this.setTicksUntilFT(0);
        }
        if (this.uuidToMount != null) {
            if (this.uuidToMountTime > 0) {
                --this.uuidToMountTime;
            }
            else {
                final double range = 32.0;
                final List entities = world.getEntitiesWithinAABB((Class)EntityLivingBase.class, ((Entity)entityplayer).boundingBox.expand(range, range, range));
                for (final Object obj : entities) {
                    final Entity entity = (Entity)obj;
                    if (entity.getUniqueID().equals(this.uuidToMount)) {
                        entityplayer.mountEntity(entity);
                        break;
                    }
                }
                this.setUUIDToMount(null);
            }
        }
        if (this.pdTick % 24000 == 0 && this.alcoholTolerance > 0) {
            this.setAlcoholTolerance(--this.alcoholTolerance);
        }
        this.unlockSharedCustomWaypoints((EntityPlayer)entityplayer);
        if (this.pdTick % 100 == 0 && ((World)world).provider instanceof LOTRWorldProvider) {
            final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
            final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
            final LOTRBiome biome = (LOTRBiome)((World)world).provider.getBiomeGenForCoords(i, k);
            if (biome.biomeDimension == LOTRDimension.MIDDLE_EARTH) {
                final LOTRBiome prevLastBiome = this.lastBiome;
                if (prevLastBiome != (this.lastBiome = biome)) {
                    this.markDirty();
                }
            }
        }
        if (this.adminHideMap) {
            final boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
            if (!((EntityPlayer)entityplayer).capabilities.isCreativeMode || !isOp) {
                this.setAdminHideMap(false);
                LOTRCommandAdminHideMap.notifyUnhidden((EntityPlayer)entityplayer);
            }
        }
        if (this.siegeActiveTime > 0) {
            --this.siegeActiveTime;
        }
    }
    
    public float getAlignment(final LOTRFaction faction) {
        if (faction.hasFixedAlignment) {
            return (float)faction.fixedAlignment;
        }
        final Float alignment = this.alignments.get(faction);
        return (alignment != null) ? alignment : 0.0f;
    }
    
    public void setAlignment(final LOTRFaction faction, final float alignment) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (faction.isPlayableAlignmentFaction()) {
            final float prevAlignment = this.getAlignment(faction);
            this.alignments.put(faction, alignment);
            this.markDirty();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, ((Entity)entityplayer).worldObj);
            }
            this.checkAlignmentAchievements(faction, prevAlignment);
        }
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient && this.pledgeFaction != null && !this.canPledgeTo(this.pledgeFaction)) {
            this.revokePledgeFaction(entityplayer, false);
        }
    }
    
    public LOTRAlignmentBonusMap addAlignment(final EntityPlayer entityplayer, final LOTRAlignmentValues.AlignmentBonus source, final LOTRFaction faction, final Entity entity) {
        return this.addAlignment(entityplayer, source, faction, null, entity);
    }
    
    public LOTRAlignmentBonusMap addAlignment(final EntityPlayer entityplayer, final LOTRAlignmentValues.AlignmentBonus source, final LOTRFaction faction, final List<LOTRFaction> forcedBonusFactions, final Entity entity) {
        return this.addAlignment(entityplayer, source, faction, forcedBonusFactions, entity.posX, entity.boundingBox.minY + entity.height / 1.5, entity.posZ);
    }
    
    public LOTRAlignmentBonusMap addAlignment(final EntityPlayer entityplayer, final LOTRAlignmentValues.AlignmentBonus source, final LOTRFaction faction, final double posX, final double posY, final double posZ) {
        return this.addAlignment(entityplayer, source, faction, null, posX, posY, posZ);
    }
    
    public LOTRAlignmentBonusMap addAlignment(final EntityPlayer entityplayer, final LOTRAlignmentValues.AlignmentBonus source, final LOTRFaction faction, final List<LOTRFaction> forcedBonusFactions, final double posX, final double posY, final double posZ) {
        final float bonus = source.bonus;
        final LOTRAlignmentBonusMap factionBonusMap = new LOTRAlignmentBonusMap();
        final float prevMainAlignment = this.getAlignment(faction);
        float conquestBonus = 0.0f;
        final float notPledgedMultiplier = 0.5f;
        if (source.isKill) {
            final List<LOTRFaction> killBonuses = faction.getBonusesForKilling();
            for (final LOTRFaction bonusFaction : killBonuses) {
                if (bonusFaction.isPlayableAlignmentFaction() && (bonusFaction.approvesWarCrimes || !source.isCivilianKill)) {
                    if (!source.killByHiredUnit) {
                        float mplier = 0.0f;
                        if (forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction)) {
                            mplier = 1.0f;
                        }
                        else {
                            mplier = bonusFaction.getControlZoneAlignmentMultiplier(entityplayer);
                        }
                        if (mplier > 0.0f) {
                            float alignment = this.getAlignment(bonusFaction);
                            float factionBonus = Math.abs(bonus);
                            factionBonus *= mplier;
                            if (alignment >= bonusFaction.getPledgeAlignment() && !this.isPledgedTo(bonusFaction)) {
                                factionBonus *= 0.5f;
                            }
                            factionBonus = this.checkBonusForPledgeEnemyLimit(bonusFaction, factionBonus);
                            alignment += factionBonus;
                            this.setAlignment(bonusFaction, alignment);
                            factionBonusMap.put(bonusFaction, factionBonus);
                        }
                    }
                    if (bonusFaction != this.getPledgeFaction()) {
                        continue;
                    }
                    float conq = bonus;
                    if (source.killByHiredUnit) {
                        conq *= 0.25f;
                    }
                    conquestBonus = LOTRConquestGrid.onConquestKill(entityplayer, bonusFaction, faction, conq);
                    this.getFactionData(bonusFaction).addConquest(Math.abs(conquestBonus));
                }
            }
            final List<LOTRFaction> killPenalties = faction.getPenaltiesForKilling();
            for (final LOTRFaction penaltyFaction : killPenalties) {
                if (penaltyFaction.isPlayableAlignmentFaction() && !source.killByHiredUnit) {
                    float mplier2 = 0.0f;
                    if (penaltyFaction == faction) {
                        mplier2 = 1.0f;
                    }
                    else {
                        mplier2 = penaltyFaction.getControlZoneAlignmentMultiplier(entityplayer);
                    }
                    if (mplier2 <= 0.0f) {
                        continue;
                    }
                    float alignment2 = this.getAlignment(penaltyFaction);
                    float factionPenalty = -Math.abs(bonus);
                    factionPenalty *= mplier2;
                    factionPenalty = LOTRAlignmentValues.AlignmentBonus.scalePenalty(factionPenalty, alignment2);
                    alignment2 += factionPenalty;
                    this.setAlignment(penaltyFaction, alignment2);
                    factionBonusMap.put(penaltyFaction, factionPenalty);
                }
            }
        }
        else if (faction.isPlayableAlignmentFaction()) {
            float alignment3 = this.getAlignment(faction);
            float factionBonus2 = bonus;
            if (factionBonus2 > 0.0f && alignment3 >= faction.getPledgeAlignment() && !this.isPledgedTo(faction)) {
                factionBonus2 *= 0.5f;
            }
            factionBonus2 = this.checkBonusForPledgeEnemyLimit(faction, factionBonus2);
            alignment3 += factionBonus2;
            this.setAlignment(faction, alignment3);
            factionBonusMap.put(faction, factionBonus2);
        }
        if (!factionBonusMap.isEmpty() || conquestBonus != 0.0f) {
            this.sendAlignmentBonusPacket(source, faction, prevMainAlignment, factionBonusMap, conquestBonus, posX, posY, posZ);
        }
        return factionBonusMap;
    }
    
    private void sendAlignmentBonusPacket(final LOTRAlignmentValues.AlignmentBonus source, final LOTRFaction faction, final float prevMainAlignment, final LOTRAlignmentBonusMap factionMap, final float conqBonus, final double posX, final double posY, final double posZ) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null) {
            final LOTRPacketAlignmentBonus packet = new LOTRPacketAlignmentBonus(faction, prevMainAlignment, factionMap, conqBonus, posX, posY, posZ, source);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public void setAlignmentFromCommand(final LOTRFaction faction, final float set) {
        this.setAlignment(faction, set);
    }
    
    public void addAlignmentFromCommand(final LOTRFaction faction, final float add) {
        float alignment = this.getAlignment(faction);
        alignment += add;
        this.setAlignment(faction, alignment);
    }
    
    private float checkBonusForPledgeEnemyLimit(final LOTRFaction fac, float bonus) {
        if (this.isPledgeEnemyAlignmentLimited(fac)) {
            final float alignment = this.getAlignment(fac);
            final float limit = this.getPledgeEnemyAlignmentLimit(fac);
            if (alignment > limit) {
                bonus = 0.0f;
            }
            else if (alignment + bonus > limit) {
                bonus = limit - alignment;
            }
        }
        return bonus;
    }
    
    public boolean isPledgeEnemyAlignmentLimited(final LOTRFaction fac) {
        return this.pledgeFaction != null && this.doesFactionPreventPledge(this.pledgeFaction, fac);
    }
    
    public float getPledgeEnemyAlignmentLimit(final LOTRFaction fac) {
        return 0.0f;
    }
    
    private void checkAlignmentAchievements(final LOTRFaction faction, final float prevAlignment) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final float alignment = this.getAlignment(faction);
            faction.checkAlignmentAchievements(entityplayer, alignment);
        }
    }
    
    private void runAlignmentDraining(final EntityPlayerMP entityplayer) {
        if (LOTRConfig.alignmentDrain && this.pdTick % 1000 == 0) {
            final float drainLimit = 0.0f;
            final List<LOTRFaction> drainFactions = new ArrayList<LOTRFaction>();
            final List<LOTRFaction> allFacs = LOTRFaction.getPlayableAlignmentFactions();
            for (final LOTRFaction fac1 : allFacs) {
                for (final LOTRFaction fac2 : allFacs) {
                    if (this.doFactionsDrain(fac1, fac2)) {
                        final float align1 = this.getAlignment(fac1);
                        final float align2 = this.getAlignment(fac2);
                        if (align1 <= 0.0f || align2 <= 0.0f) {
                            continue;
                        }
                        if (!drainFactions.contains(fac1)) {
                            drainFactions.add(fac1);
                        }
                        if (drainFactions.contains(fac2)) {
                            continue;
                        }
                        drainFactions.add(fac2);
                    }
                }
            }
            if (!drainFactions.isEmpty()) {
                for (final LOTRFaction fac3 : drainFactions) {
                    float align3 = this.getAlignment(fac3);
                    float alignLoss = 5.0f;
                    alignLoss = Math.min(alignLoss, align3 - 0.0f);
                    align3 -= alignLoss;
                    this.setAlignment(fac3, align3);
                }
                this.sendMessageIfNotReceived(LOTRGuiMessageTypes.ALIGN_DRAIN);
                final LOTRPacketAlignDrain packet = new LOTRPacketAlignDrain(drainFactions.size());
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
            }
        }
    }
    
    public boolean doFactionsDrain(final LOTRFaction fac1, final LOTRFaction fac2) {
        return fac1.isMortalEnemy(fac2);
    }
    
    public LOTRFactionData getFactionData(final LOTRFaction faction) {
        LOTRFactionData data = this.factionDataMap.get(faction);
        if (data == null) {
            data = new LOTRFactionData(this, faction);
            this.factionDataMap.put(faction, data);
        }
        return data;
    }
    
    public void updateFactionData(final LOTRFaction faction, final LOTRFactionData factionData) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            this.markDirty();
            final NBTTagCompound nbt = new NBTTagCompound();
            factionData.save(nbt);
            final LOTRPacketFactionData packet = new LOTRPacketFactionData(faction, nbt);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public LOTRFaction getPledgeFaction() {
        return this.pledgeFaction;
    }
    
    public boolean isPledgedTo(final LOTRFaction fac) {
        return this.pledgeFaction == fac;
    }
    
    public void setPledgeFaction(final LOTRFaction fac) {
        this.pledgeFaction = fac;
        this.pledgeKillCooldown = 0;
        this.markDirty();
        if (fac != null) {
            this.checkAlignmentAchievements(fac, this.getAlignment(fac));
            this.addAchievement(LOTRAchievement.pledgeService);
        }
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            if (fac != null) {
                final World world = ((Entity)entityplayer).worldObj;
                world.playSoundAtEntity((Entity)entityplayer, "lotr:event.pledge", 1.0f, 1.0f);
            }
            final LOTRPacketPledge packet = new LOTRPacketPledge(fac);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public boolean canPledgeTo(final LOTRFaction fac) {
        return fac.isPlayableAlignmentFaction() && this.hasPledgeAlignment(fac) && this.getFactionsPreventingPledgeTo(fac).isEmpty();
    }
    
    public boolean hasPledgeAlignment(final LOTRFaction fac) {
        final float alignment = this.getAlignment(fac);
        return alignment >= fac.getPledgeAlignment();
    }
    
    public List<LOTRFaction> getFactionsPreventingPledgeTo(final LOTRFaction fac) {
        final List<LOTRFaction> enemies = new ArrayList<LOTRFaction>();
        for (final LOTRFaction otherFac : LOTRFaction.values()) {
            if (otherFac.isPlayableAlignmentFaction() && this.doesFactionPreventPledge(fac, otherFac)) {
                final float enemyAlign = this.getAlignment(otherFac);
                if (enemyAlign > 0.0f) {
                    enemies.add(otherFac);
                }
            }
        }
        return enemies;
    }
    
    private boolean doesFactionPreventPledge(final LOTRFaction pledgeFac, final LOTRFaction otherFac) {
        return pledgeFac.isMortalEnemy(otherFac);
    }
    
    public boolean canMakeNewPledge() {
        return this.pledgeBreakCooldown <= 0;
    }
    
    public void revokePledgeFaction(final EntityPlayer entityplayer, final boolean intentional) {
        final LOTRFaction wasPledge = this.pledgeFaction;
        final float pledgeLvl = wasPledge.getPledgeAlignment();
        final float prevAlign = this.getAlignment(wasPledge);
        final float diff = prevAlign - pledgeLvl;
        float cd = diff / 5000.0f;
        cd = MathHelper.clamp_float(cd, 0.0f, 1.0f);
        int cdTicks = 36000;
        cdTicks += Math.round(cd * 150.0f * 60.0f * 20.0f);
        this.setPledgeFaction(null);
        this.setBrokenPledgeFaction(wasPledge);
        this.setPledgeBreakCooldown(cdTicks);
        final World world = ((Entity)entityplayer).worldObj;
        if (!world.isClient) {
            final LOTRFactionRank rank = wasPledge.getRank(prevAlign);
            final LOTRFactionRank rankBelow = wasPledge.getRankBelow(rank);
            final LOTRFactionRank rankBelow2 = wasPledge.getRankBelow(rankBelow);
            float newAlign = rankBelow2.alignment;
            newAlign = Math.max(newAlign, pledgeLvl / 2.0f);
            final float alignPenalty = newAlign - prevAlign;
            if (alignPenalty < 0.0f) {
                final LOTRAlignmentValues.AlignmentBonus penalty = LOTRAlignmentValues.createPledgePenalty(alignPenalty);
                double alignX = 0.0;
                double alignY = 0.0;
                double alignZ = 0.0;
                final double lookRange = 2.0;
                final Vec3 posEye = Vec3.createVectorHelper(((Entity)entityplayer).posX, ((Entity)entityplayer).boundingBox.minY + entityplayer.getEyeHeight(), ((Entity)entityplayer).posZ);
                final Vec3 look = entityplayer.getLook(1.0f);
                final Vec3 posSight = posEye.addVector(look.xCoord * lookRange, look.yCoord * lookRange, look.zCoord * lookRange);
                final MovingObjectPosition mop = world.rayTraceBlocks(posEye, posSight);
                if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    alignX = mop.blockX + 0.5;
                    alignY = mop.blockY + 0.5;
                    alignZ = mop.blockZ + 0.5;
                }
                else {
                    alignX = posSight.xCoord;
                    alignY = posSight.yCoord;
                    alignZ = posSight.zCoord;
                }
                this.addAlignment(entityplayer, penalty, wasPledge, alignX, alignY, alignZ);
            }
            world.playSoundAtEntity((Entity)entityplayer, "lotr:event.unpledge", 1.0f, 1.0f);
            if (intentional) {
                final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("chat.lotr.unpledge", new Object[] { wasPledge.factionName() });
                entityplayer.addChatMessage(msg);
            }
            else {
                final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("chat.lotr.autoUnpledge", new Object[] { wasPledge.factionName() });
                entityplayer.addChatMessage(msg);
            }
            this.checkAlignmentAchievements(wasPledge, prevAlign);
        }
    }
    
    public void onPledgeKill(final EntityPlayer entityplayer) {
        this.pledgeKillCooldown += 24000;
        this.markDirty();
        if (this.pledgeKillCooldown > 24000) {
            this.revokePledgeFaction(entityplayer, false);
        }
        else if (this.pledgeFaction != null) {
            final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("chat.lotr.pledgeKillWarn", new Object[] { this.pledgeFaction.factionName() });
            entityplayer.addChatMessage(msg);
        }
    }
    
    public int getPledgeBreakCooldown() {
        return this.pledgeBreakCooldown;
    }
    
    public void setPledgeBreakCooldown(int i) {
        final int preCD = this.pledgeBreakCooldown;
        final LOTRFaction preBroken = this.brokenPledgeFaction;
        i = Math.max(0, i);
        this.pledgeBreakCooldown = i;
        boolean bigChange = (this.pledgeBreakCooldown == 0 || preCD == 0) && this.pledgeBreakCooldown != preCD;
        if (this.pledgeBreakCooldown > this.pledgeBreakCooldownStart) {
            this.setPledgeBreakCooldownStart(this.pledgeBreakCooldown);
            bigChange = true;
        }
        if (this.pledgeBreakCooldown <= 0 && preBroken != null) {
            this.setPledgeBreakCooldownStart(0);
            this.setBrokenPledgeFaction(null);
            bigChange = true;
        }
        if (bigChange || isTimerAutosaveTick()) {
            this.markDirty();
        }
        if (bigChange || this.pledgeBreakCooldown % 5 == 0) {
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketBrokenPledge packet = new LOTRPacketBrokenPledge(this.pledgeBreakCooldown, this.pledgeBreakCooldownStart, this.brokenPledgeFaction);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
        if (this.pledgeBreakCooldown == 0 && preCD != this.pledgeBreakCooldown) {
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final String brokenName = (preBroken == null) ? StatCollector.translateToLocal("lotr.gui.factions.pledgeUnknown") : preBroken.factionName();
                final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("chat.lotr.pledgeBreakCooldown", new Object[] { brokenName });
                entityplayer.addChatMessage(msg);
            }
        }
    }
    
    public int getPledgeBreakCooldownStart() {
        return this.pledgeBreakCooldownStart;
    }
    
    public void setPledgeBreakCooldownStart(final int i) {
        this.pledgeBreakCooldownStart = i;
        this.markDirty();
    }
    
    public LOTRFaction getBrokenPledgeFaction() {
        return this.brokenPledgeFaction;
    }
    
    public void setBrokenPledgeFaction(final LOTRFaction f) {
        this.brokenPledgeFaction = f;
        this.markDirty();
    }
    
    public List<LOTRAchievement> getAchievements() {
        return this.achievements;
    }
    
    public List<LOTRAchievement> getEarnedAchievements(final LOTRDimension dimension) {
        final List<LOTRAchievement> earnedAchievements = new ArrayList<LOTRAchievement>();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null) {
            for (final LOTRAchievement achievement : this.achievements) {
                if (achievement.getDimension() == dimension && achievement.canPlayerEarn(entityplayer)) {
                    earnedAchievements.add(achievement);
                }
            }
        }
        return earnedAchievements;
    }
    
    public boolean hasAchievement(final LOTRAchievement achievement) {
        for (final LOTRAchievement a : this.achievements) {
            if (a.category == achievement.category && a.ID == achievement.ID) {
                return true;
            }
        }
        return false;
    }
    
    public void addAchievement(final LOTRAchievement achievement) {
        if (this.hasAchievement(achievement)) {
            return;
        }
        if (this.isSiegeActive()) {
            return;
        }
        this.achievements.add(achievement);
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final boolean canEarn = achievement.canPlayerEarn(entityplayer);
            this.sendAchievementPacket((EntityPlayerMP)entityplayer, achievement, canEarn);
            if (canEarn) {
                achievement.broadcastEarning(entityplayer);
                final List earnedAchievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH);
                int biomes = 0;
                for (int i = 0; i < earnedAchievements.size(); ++i) {
                    final LOTRAchievement earnedAchievement = earnedAchievements.get(i);
                    if (earnedAchievement.isBiomeAchievement) {
                        ++biomes;
                    }
                }
                if (biomes >= 10) {
                    this.addAchievement(LOTRAchievement.travel10);
                }
                if (biomes >= 20) {
                    this.addAchievement(LOTRAchievement.travel20);
                }
                if (biomes >= 30) {
                    this.addAchievement(LOTRAchievement.travel30);
                }
                if (biomes >= 40) {
                    this.addAchievement(LOTRAchievement.travel40);
                }
                if (biomes >= 50) {
                    this.addAchievement(LOTRAchievement.travel50);
                }
            }
        }
    }
    
    public void removeAchievement(final LOTRAchievement achievement) {
        if (!this.hasAchievement(achievement)) {
            return;
        }
        if (this.achievements.remove(achievement)) {
            this.markDirty();
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                this.sendAchievementRemovePacket((EntityPlayerMP)entityplayer, achievement);
            }
        }
    }
    
    private void runAchievementChecks(final EntityPlayer entityplayer, final World world) {
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int j = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome) {
            final LOTRBiome lotrbiome = (LOTRBiome)biome;
            final LOTRAchievement ach = lotrbiome.getBiomeAchievement();
            if (ach != null) {
                this.addAchievement(ach);
            }
            final LOTRWaypoint.Region biomeRegion = lotrbiome.getBiomeWaypoints();
            if (biomeRegion != null) {
                this.unlockFTRegion(biomeRegion);
            }
        }
        if (((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            this.addAchievement(LOTRAchievement.enterMiddleEarth);
        }
        if (((Entity)entityplayer).dimension == LOTRDimension.UTUMNO.dimensionID) {
            this.addAchievement(LOTRAchievement.enterUtumnoIce);
            final int y = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
            final LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(y);
            if (level == LOTRUtumnoLevel.OBSIDIAN) {
                this.addAchievement(LOTRAchievement.enterUtumnoObsidian);
            }
            else if (level == LOTRUtumnoLevel.FIRE) {
                this.addAchievement(LOTRAchievement.enterUtumnoFire);
            }
        }
        if (entityplayer.inventory.hasItem(LOTRMod.pouch)) {
            this.addAchievement(LOTRAchievement.getPouch);
        }
        final Set tables = new HashSet();
        int crossbowBolts = 0;
        for (final ItemStack item : entityplayer.inventory.mainInventory) {
            if (item != null && item.getItem() instanceof ItemBlock) {
                final Block block = Block.getBlockFromItem(item.getItem());
                if (block instanceof LOTRBlockCraftingTable) {
                    tables.add(block);
                }
            }
            if (item != null && item.getItem() instanceof LOTRItemCrossbowBolt) {
                crossbowBolts += item.stackSize;
            }
        }
        if (tables.size() >= 10) {
            this.addAchievement(LOTRAchievement.collectCraftingTables);
        }
        if (crossbowBolts >= 128) {
            this.addAchievement(LOTRAchievement.collectCrossbowBolts);
        }
        if (!this.hasAchievement(LOTRAchievement.hundreds) && this.pdTick % 20 == 0) {
            int hiredUnits = 0;
            final List<LOTREntityNPC> nearbyNPCs = (List<LOTREntityNPC>)world.getEntitiesWithinAABB((Class)LOTREntityNPC.class, ((Entity)entityplayer).boundingBox.expand(64.0, 64.0, 64.0));
            for (final LOTREntityNPC npc : nearbyNPCs) {
                if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    ++hiredUnits;
                }
            }
            if (hiredUnits >= 100) {
                this.addAchievement(LOTRAchievement.hundreds);
            }
        }
        if (biome instanceof LOTRBiomeGenMistyMountains && ((Entity)entityplayer).posY > 192.0) {
            this.addAchievement(LOTRAchievement.climbMistyMountains);
        }
        final LOTRMaterial fullMaterial = this.isPlayerWearingFull(entityplayer);
        if (fullMaterial != null) {
            if (fullMaterial == LOTRMaterial.MITHRIL) {
                this.addAchievement(LOTRAchievement.wearFullMithril);
            }
            else if (fullMaterial == LOTRMaterial.FUR) {
                this.addAchievement(LOTRAchievement.wearFullFur);
            }
            else if (fullMaterial == LOTRMaterial.BLUE_DWARVEN) {
                this.addAchievement(LOTRAchievement.wearFullBlueDwarven);
            }
            else if (fullMaterial == LOTRMaterial.HIGH_ELVEN) {
                this.addAchievement(LOTRAchievement.wearFullHighElven);
            }
            else if (fullMaterial == LOTRMaterial.GONDOLIN) {
                this.addAchievement(LOTRAchievement.wearFullGondolin);
            }
            else if (fullMaterial == LOTRMaterial.GALVORN) {
                this.addAchievement(LOTRAchievement.wearFullGalvorn);
            }
            else if (fullMaterial == LOTRMaterial.RANGER) {
                this.addAchievement(LOTRAchievement.wearFullRanger);
            }
            else if (fullMaterial == LOTRMaterial.GUNDABAD_URUK) {
                this.addAchievement(LOTRAchievement.wearFullGundabadUruk);
            }
            else if (fullMaterial == LOTRMaterial.ARNOR) {
                this.addAchievement(LOTRAchievement.wearFullArnor);
            }
            else if (fullMaterial == LOTRMaterial.RIVENDELL) {
                this.addAchievement(LOTRAchievement.wearFullRivendell);
            }
            else if (fullMaterial == LOTRMaterial.ANGMAR) {
                this.addAchievement(LOTRAchievement.wearFullAngmar);
            }
            else if (fullMaterial == LOTRMaterial.WOOD_ELVEN_SCOUT) {
                this.addAchievement(LOTRAchievement.wearFullWoodElvenScout);
            }
            else if (fullMaterial == LOTRMaterial.WOOD_ELVEN) {
                this.addAchievement(LOTRAchievement.wearFullWoodElven);
            }
            else if (fullMaterial == LOTRMaterial.DOL_GULDUR) {
                this.addAchievement(LOTRAchievement.wearFullDolGuldur);
            }
            else if (fullMaterial == LOTRMaterial.DALE) {
                this.addAchievement(LOTRAchievement.wearFullDale);
            }
            else if (fullMaterial == LOTRMaterial.DWARVEN) {
                this.addAchievement(LOTRAchievement.wearFullDwarven);
            }
            else if (fullMaterial == LOTRMaterial.GALADHRIM) {
                this.addAchievement(LOTRAchievement.wearFullElven);
            }
            else if (fullMaterial == LOTRMaterial.HITHLAIN) {
                this.addAchievement(LOTRAchievement.wearFullHithlain);
            }
            else if (fullMaterial == LOTRMaterial.URUK) {
                this.addAchievement(LOTRAchievement.wearFullUruk);
            }
            else if (fullMaterial == LOTRMaterial.ROHAN) {
                this.addAchievement(LOTRAchievement.wearFullRohirric);
            }
            else if (fullMaterial == LOTRMaterial.ROHAN_MARSHAL) {
                this.addAchievement(LOTRAchievement.wearFullRohirricMarshal);
            }
            else if (fullMaterial == LOTRMaterial.DUNLENDING) {
                this.addAchievement(LOTRAchievement.wearFullDunlending);
            }
            else if (fullMaterial == LOTRMaterial.GONDOR) {
                this.addAchievement(LOTRAchievement.wearFullGondorian);
            }
            else if (fullMaterial == LOTRMaterial.DOL_AMROTH) {
                this.addAchievement(LOTRAchievement.wearFullDolAmroth);
            }
            else if (fullMaterial == LOTRMaterial.RANGER_ITHILIEN) {
                this.addAchievement(LOTRAchievement.wearFullRangerIthilien);
            }
            else if (fullMaterial == LOTRMaterial.LOSSARNACH) {
                this.addAchievement(LOTRAchievement.wearFullLossarnach);
            }
            else if (fullMaterial == LOTRMaterial.PELARGIR) {
                this.addAchievement(LOTRAchievement.wearFullPelargir);
            }
            else if (fullMaterial == LOTRMaterial.PINNATH_GELIN) {
                this.addAchievement(LOTRAchievement.wearFullPinnathGelin);
            }
            else if (fullMaterial == LOTRMaterial.BLACKROOT) {
                this.addAchievement(LOTRAchievement.wearFullBlackroot);
            }
            else if (fullMaterial == LOTRMaterial.LAMEDON) {
                this.addAchievement(LOTRAchievement.wearFullLamedon);
            }
            else if (fullMaterial == LOTRMaterial.MORDOR) {
                this.addAchievement(LOTRAchievement.wearFullOrc);
            }
            else if (fullMaterial == LOTRMaterial.MORGUL) {
                this.addAchievement(LOTRAchievement.wearFullMorgul);
            }
            else if (fullMaterial == LOTRMaterial.BLACK_URUK) {
                this.addAchievement(LOTRAchievement.wearFullBlackUruk);
            }
            else if (fullMaterial == LOTRMaterial.DORWINION) {
                this.addAchievement(LOTRAchievement.wearFullDorwinion);
            }
            else if (fullMaterial == LOTRMaterial.DORWINION_ELF) {
                this.addAchievement(LOTRAchievement.wearFullDorwinionElf);
            }
            else if (fullMaterial == LOTRMaterial.RHUN) {
                this.addAchievement(LOTRAchievement.wearFullRhun);
            }
            else if (fullMaterial == LOTRMaterial.RHUN_GOLD) {
                this.addAchievement(LOTRAchievement.wearFullRhunGold);
            }
            else if (fullMaterial == LOTRMaterial.NEAR_HARAD) {
                this.addAchievement(LOTRAchievement.wearFullNearHarad);
            }
            else if (fullMaterial == LOTRMaterial.GULF_HARAD) {
                this.addAchievement(LOTRAchievement.wearFullGulfHarad);
            }
            else if (fullMaterial == LOTRMaterial.CORSAIR) {
                this.addAchievement(LOTRAchievement.wearFullCorsair);
            }
            else if (fullMaterial == LOTRMaterial.UMBAR) {
                this.addAchievement(LOTRAchievement.wearFullUmbar);
            }
            else if (fullMaterial == LOTRMaterial.HARNEDOR) {
                this.addAchievement(LOTRAchievement.wearFullHarnedor);
            }
            else if (fullMaterial == LOTRMaterial.HARAD_NOMAD) {
                this.addAchievement(LOTRAchievement.wearFullNomad);
            }
            else if (fullMaterial == LOTRMaterial.BLACK_NUMENOREAN) {
                this.addAchievement(LOTRAchievement.wearFullBlackNumenorean);
            }
            else if (fullMaterial == LOTRMaterial.MOREDAIN) {
                this.addAchievement(LOTRAchievement.wearFullMoredain);
            }
            else if (fullMaterial == LOTRMaterial.TAUREDAIN) {
                this.addAchievement(LOTRAchievement.wearFullTauredain);
            }
            else if (fullMaterial == LOTRMaterial.HALF_TROLL) {
                this.addAchievement(LOTRAchievement.wearFullHalfTroll);
            }
            else if (fullMaterial == LOTRMaterial.UTUMNO) {
                this.addAchievement(LOTRAchievement.wearFullUtumno);
            }
        }
    }
    
    public LOTRMaterial isPlayerWearingFull(final EntityPlayer entityplayer) {
        LOTRMaterial fullMaterial = null;
        for (final ItemStack itemstack : entityplayer.inventory.armorInventory) {
            if (itemstack == null || !(itemstack.getItem() instanceof LOTRItemArmor)) {
                return null;
            }
            final LOTRItemArmor armor = (LOTRItemArmor)itemstack.getItem();
            final LOTRMaterial thisMaterial = armor.getLOTRArmorMaterial();
            if (fullMaterial == null) {
                fullMaterial = thisMaterial;
            }
            else if (fullMaterial != thisMaterial) {
                return null;
            }
        }
        return fullMaterial;
    }
    
    private void sendAchievementPacket(final EntityPlayerMP entityplayer, final LOTRAchievement achievement, final boolean display) {
        final LOTRPacketAchievement packet = new LOTRPacketAchievement(achievement, display);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    private void sendAchievementRemovePacket(final EntityPlayerMP entityplayer, final LOTRAchievement achievement) {
        final LOTRPacketAchievementRemove packet = new LOTRPacketAchievementRemove(achievement);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public void setShield(final LOTRShields lotrshield) {
        this.shield = lotrshield;
        this.markDirty();
    }
    
    public LOTRShields getShield() {
        return this.shield;
    }
    
    public void setStructuresBanned(final boolean flag) {
        this.structuresBanned = flag;
        this.markDirty();
    }
    
    public boolean getStructuresBanned() {
        return this.structuresBanned;
    }
    
    private void sendOptionsPacket(final int option, final boolean flag) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketOptions packet = new LOTRPacketOptions(option, flag);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public boolean getFriendlyFire() {
        return this.friendlyFire;
    }
    
    public void setFriendlyFire(final boolean flag) {
        this.friendlyFire = flag;
        this.markDirty();
        this.sendOptionsPacket(0, flag);
    }
    
    public boolean getEnableHiredDeathMessages() {
        return this.hiredDeathMessages;
    }
    
    public void setEnableHiredDeathMessages(final boolean flag) {
        this.hiredDeathMessages = flag;
        this.markDirty();
        this.sendOptionsPacket(1, flag);
    }
    
    public boolean getHideAlignment() {
        return this.hideAlignment;
    }
    
    public void setHideAlignment(final boolean flag) {
        this.hideAlignment = flag;
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, ((Entity)entityplayer).worldObj);
        }
    }
    
    private void sendFTBouncePacket(final EntityPlayerMP entityplayer) {
        final LOTRPacketFTBounceClient packet = new LOTRPacketFTBounceClient();
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public void receiveFTBouncePacket() {
        if (this.targetFTWaypoint != null && this.ticksUntilFT <= 0) {
            this.fastTravelTo(this.targetFTWaypoint);
            this.setTargetFTWaypoint(null);
        }
    }
    
    private void fastTravelTo(final LOTRAbstractWaypoint waypoint) {
        final EntityPlayer player = this.getPlayer();
        if (player instanceof EntityPlayerMP) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)player;
            final WorldServer world = (WorldServer)((Entity)entityplayer).worldObj;
            final int startX = MathHelper.floor_double(((Entity)entityplayer).posX);
            final int startZ = MathHelper.floor_double(((Entity)entityplayer).posZ);
            final double range = 256.0;
            final List<EntityLiving> entities = (List<EntityLiving>)world.getEntitiesWithinAABB((Class)EntityLiving.class, ((Entity)entityplayer).boundingBox.expand(range, range, range));
            final Set<EntityLiving> entitiesToTransport = new HashSet<EntityLiving>();
            for (final EntityLiving entity : entities) {
                if (entity instanceof LOTREntityNPC) {
                    final LOTREntityNPC npc = (LOTREntityNPC)entity;
                    if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.shouldFollowPlayer()) {
                        entitiesToTransport.add((EntityLiving)npc);
                        continue;
                    }
                    if (npc instanceof LOTREntityGollum) {
                        final LOTREntityGollum gollum = (LOTREntityGollum)npc;
                        if (gollum.getGollumOwner() == entityplayer && !gollum.isGollumSitting()) {
                            entitiesToTransport.add((EntityLiving)gollum);
                            continue;
                        }
                    }
                }
                if (entity instanceof EntityTameable) {
                    final EntityTameable pet = (EntityTameable)entity;
                    if (pet.getOwner() == entityplayer && !pet.isSitting()) {
                        entitiesToTransport.add((EntityLiving)pet);
                        continue;
                    }
                }
                if (entity.getLeashed() && entity.getLeashedToEntity() == entityplayer) {
                    entitiesToTransport.add(entity);
                }
            }
            final Set<EntityLiving> removes = new HashSet<EntityLiving>();
            for (final EntityLiving entity2 : entitiesToTransport) {
                final Entity rider = ((Entity)entity2).riddenByEntity;
                if (rider != null && entitiesToTransport.contains(rider)) {
                    removes.add(entity2);
                }
            }
            entitiesToTransport.removeAll(removes);
            final int i = waypoint.getXCoord();
            final int k = waypoint.getZCoord();
            world.theChunkProviderServer.provideChunk(i >> 4, k >> 4);
            final int j = waypoint.getYCoord((World)world, i, k);
            Entity playerMount = ((Entity)entityplayer).ridingEntity;
            entityplayer.mountEntity((Entity)null);
            entityplayer.setPositionAndUpdate(i + 0.5, (double)j, k + 0.5);
            ((Entity)entityplayer).fallDistance = 0.0f;
            if (playerMount instanceof EntityLiving) {
                playerMount = this.fastTravelEntity(world, playerMount, i, j, k);
            }
            if (playerMount != null) {
                this.setUUIDToMount(playerMount.getUniqueID());
            }
            for (EntityLiving entity3 : entitiesToTransport) {
                Entity mount = ((Entity)entity3).ridingEntity;
                entity3.mountEntity((Entity)null);
                entity3 = this.fastTravelEntity(world, entity3, i, j, k);
                if (mount instanceof EntityLiving) {
                    mount = this.fastTravelEntity(world, mount, i, j, k);
                    entity3.mountEntity(mount);
                }
            }
            this.sendFTPacket(entityplayer, waypoint, startX, startZ);
            if (!((EntityPlayer)entityplayer).capabilities.isCreativeMode) {
                final int cooldown = this.getFTCooldown(waypoint);
                this.setFTTimer(cooldown);
            }
            float fraction = this.getFTCooldownFraction(waypoint);
            final float growth = 0.85f;
            fraction *= 0.85f;
            this.setFTCooldownFraction(waypoint, fraction);
            if (waypoint instanceof LOTRWaypoint) {
                this.lastWaypoint = (LOTRWaypoint)waypoint;
                this.markDirty();
            }
        }
    }
    
    private void sendFTPacket(final EntityPlayerMP entityplayer, final LOTRAbstractWaypoint waypoint, final int startX, final int startZ) {
        final LOTRPacketFTScreen packet = new LOTRPacketFTScreen(waypoint, startX, startZ);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    private <T extends EntityLiving> T fastTravelEntity(final WorldServer world, final T entity, final int i, final int j, final int k) {
        final String entityID = EntityList.getEntityString((Entity)entity);
        final NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        entity.setDead();
        final T newEntity = (T)EntityList.createEntityByName(entityID, (World)world);
        newEntity.readFromNBT(nbt);
        newEntity.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, ((Entity)newEntity).rotationYaw, ((Entity)newEntity).rotationPitch);
        ((Entity)newEntity).fallDistance = 0.0f;
        newEntity.getNavigator().clearPathEntity();
        newEntity.setAttackTarget((EntityLivingBase)null);
        world.spawnEntityInWorld((Entity)newEntity);
        world.updateEntityWithOptionalForce((Entity)newEntity, false);
        return newEntity;
    }
    
    public boolean canFastTravel() {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null) {
            final World world = ((Entity)entityplayer).worldObj;
            if (!entityplayer.capabilities.isCreativeMode) {
                final double range = 16.0;
                final List entities = world.getEntitiesWithinAABB((Class)EntityLiving.class, ((Entity)entityplayer).boundingBox.expand(range, range, range));
                for (int l = 0; l < entities.size(); ++l) {
                    final EntityLiving entityliving = entities.get(l);
                    if (entityliving.getAttackTarget() == entityplayer) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public int getFTTimer() {
        return this.fastTravelTimer;
    }
    
    public void setFTTimer(int i) {
        final int preTimer = this.fastTravelTimer;
        i = Math.max(0, i);
        this.fastTravelTimer = i;
        final boolean bigChange = (this.fastTravelTimer == 0 || preTimer == 0) && this.fastTravelTimer != preTimer;
        if (bigChange || isTimerAutosaveTick()) {
            this.markDirty();
        }
        if (bigChange || this.fastTravelTimer % 5 == 0) {
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketFTTimer packet = new LOTRPacketFTTimer(this.fastTravelTimer);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
        if (this.fastTravelTimer == 0 && preTimer != this.fastTravelTimer) {
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.fastTravel.available", new Object[0]));
            }
        }
    }
    
    private void setUUIDToMount(final UUID uuid) {
        this.uuidToMount = uuid;
        if (this.uuidToMount != null) {
            this.uuidToMountTime = 10;
        }
        else {
            this.uuidToMountTime = 0;
        }
        this.markDirty();
    }
    
    public LOTRAbstractWaypoint getTargetFTWaypoint() {
        return this.targetFTWaypoint;
    }
    
    public void setTargetFTWaypoint(final LOTRAbstractWaypoint wp) {
        this.targetFTWaypoint = wp;
        this.markDirty();
        if (wp != null) {
            this.setTicksUntilFT(LOTRPlayerData.ticksUntilFT_max);
        }
        else {
            this.setTicksUntilFT(0);
        }
    }
    
    public int getTicksUntilFT() {
        return this.ticksUntilFT;
    }
    
    public void setTicksUntilFT(final int i) {
        if (this.ticksUntilFT != i) {
            this.ticksUntilFT = i;
            if (this.ticksUntilFT == LOTRPlayerData.ticksUntilFT_max || this.ticksUntilFT == 0) {
                this.markDirty();
            }
        }
    }
    
    public void cancelFastTravel() {
        if (this.targetFTWaypoint != null) {
            this.setTargetFTWaypoint(null);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.fastTravel.motion", new Object[0]));
            }
        }
    }
    
    public boolean isFTRegionUnlocked(final LOTRWaypoint.Region region) {
        return this.unlockedFTRegions.contains(region);
    }
    
    public void unlockFTRegion(final LOTRWaypoint.Region region) {
        if (this.isSiegeActive()) {
            return;
        }
        if (this.unlockedFTRegions.add(region)) {
            this.markDirty();
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketWaypointRegion packet = new LOTRPacketWaypointRegion(region, true);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }
    
    public void lockFTRegion(final LOTRWaypoint.Region region) {
        if (this.unlockedFTRegions.remove(region)) {
            this.markDirty();
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketWaypointRegion packet = new LOTRPacketWaypointRegion(region, false);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }
    
    public List<LOTRAbstractWaypoint> getAllAvailableWaypoints() {
        final List<LOTRAbstractWaypoint> waypoints = new ArrayList<LOTRAbstractWaypoint>();
        waypoints.addAll(LOTRWaypoint.listAllWaypoints());
        waypoints.addAll(this.getCustomWaypoints());
        waypoints.addAll(this.customWaypointsShared);
        return waypoints;
    }
    
    public List<LOTRCustomWaypoint> getCustomWaypoints() {
        return this.customWaypoints;
    }
    
    public LOTRCustomWaypoint getCustomWaypointByID(final int ID) {
        for (final LOTRCustomWaypoint waypoint : this.customWaypoints) {
            if (waypoint.getID() == ID) {
                return waypoint;
            }
        }
        return null;
    }
    
    public void addCustomWaypoint(final LOTRCustomWaypoint waypoint) {
        this.customWaypoints.add(waypoint);
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketCreateCWPClient packet = waypoint.getClientPacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            final LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
            final List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
            for (final UUID player : sharedPlayers) {
                LOTRLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
            }
        }
    }
    
    public void removeCustomWaypoint(final LOTRCustomWaypoint waypoint) {
        if (this.customWaypoints.remove(waypoint)) {
            this.markDirty();
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketDeleteCWPClient packet = waypoint.getClientDeletePacket();
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
                final LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
                final List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
                for (final UUID player : sharedPlayers) {
                    LOTRLevelData.getData(player).removeSharedCustomWaypoint(shareCopy);
                }
            }
        }
    }
    
    public void renameCustomWaypoint(final LOTRCustomWaypoint waypoint, final String newName) {
        waypoint.rename(newName);
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketRenameCWPClient packet = waypoint.getClientRenamePacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            final LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
            final List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
            for (final UUID player : sharedPlayers) {
                LOTRLevelData.getData(player).renameSharedCustomWaypoint(shareCopy, newName);
            }
        }
    }
    
    public void customWaypointAddSharedFellowship(final LOTRCustomWaypoint waypoint, final LOTRFellowship fs) {
        final UUID fsID = fs.getFellowshipID();
        waypoint.addSharedFellowship(fsID);
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketShareCWPClient packet = waypoint.getClientAddFellowshipPacket(fsID);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        final LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
        for (final UUID player : fs.getAllPlayerUUIDs()) {
            if (!player.equals(this.playerUUID)) {
                LOTRLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
            }
        }
    }
    
    public void customWaypointAddSharedFellowshipClient(final LOTRCustomWaypoint waypoint, final LOTRFellowshipClient fs) {
        waypoint.addSharedFellowship(fs.getFellowshipID());
    }
    
    public void customWaypointRemoveSharedFellowship(final LOTRCustomWaypoint waypoint, final LOTRFellowship fs) {
        final UUID fsID = fs.getFellowshipID();
        waypoint.removeSharedFellowship(fsID);
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketShareCWPClient packet = waypoint.getClientRemoveFellowshipPacket(fsID);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        final LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
        for (final UUID player : fs.getAllPlayerUUIDs()) {
            if (!player.equals(this.playerUUID)) {
                final LOTRPlayerData pd = LOTRLevelData.getData(player);
                pd.addOrUpdateSharedCustomWaypoint(shareCopy);
                pd.removeSharedCustomWaypoints();
            }
        }
    }
    
    public void customWaypointRemoveSharedFellowshipClient(final LOTRCustomWaypoint waypoint, final LOTRFellowshipClient fs) {
        waypoint.removeSharedFellowship(fs.getFellowshipID());
    }
    
    public int getMaxCustomWaypoints() {
        final int achievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size();
        return 5 + achievements / 5;
    }
    
    public LOTRCustomWaypoint getSharedCustomWaypointByID(final UUID owner, final int id) {
        for (final LOTRCustomWaypoint waypoint : this.customWaypointsShared) {
            if (waypoint.getSharingPlayerID().equals(owner) && waypoint.getID() == id) {
                return waypoint;
            }
        }
        return null;
    }
    
    public void addOrUpdateSharedCustomWaypoint(final LOTRCustomWaypoint waypoint) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to cache a shared custom waypoint with no owner!", new Object[0]);
            return;
        }
        final CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
        if (this.cwpSharedUnlocked.contains(key)) {
            waypoint.setSharedUnlocked();
        }
        waypoint.setSharedHidden(this.cwpSharedHidden.contains(key));
        final LOTRCustomWaypoint existing = this.getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
        if (existing == null) {
            this.customWaypointsShared.add(waypoint);
        }
        else {
            if (existing.isSharedUnlocked()) {
                waypoint.setSharedUnlocked();
            }
            waypoint.setSharedHidden(existing.isSharedHidden());
            final int i = this.customWaypointsShared.indexOf(existing);
            this.customWaypointsShared.set(i, waypoint);
        }
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketCreateCWPClient packet = waypoint.getClientPacketShared();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public void removeSharedCustomWaypoint(final LOTRCustomWaypoint waypoint) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to remove a shared custom waypoint with no owner!", new Object[0]);
            return;
        }
        LOTRCustomWaypoint existing = null;
        if (this.customWaypointsShared.contains(waypoint)) {
            existing = waypoint;
        }
        else {
            existing = this.getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
        }
        if (existing != null) {
            this.customWaypointsShared.remove(existing);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketDeleteCWPClient packet = waypoint.getClientDeletePacketShared();
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
        else {
            FMLLog.warning("LOTR: Warning! Tried to remove a shared custom waypoint that does not exist!", new Object[0]);
        }
    }
    
    public void renameSharedCustomWaypoint(final LOTRCustomWaypoint waypoint, final String newName) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to rename a shared custom waypoint with no owner!", new Object[0]);
            return;
        }
        waypoint.rename(newName);
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketRenameCWPClient packet = waypoint.getClientRenamePacketShared();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public void unlockSharedCustomWaypoint(final LOTRCustomWaypoint waypoint) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to unlock a shared custom waypoint with no owner!", new Object[0]);
            return;
        }
        waypoint.setSharedUnlocked();
        final CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
        this.cwpSharedUnlocked.add(key);
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketCWPSharedUnlockClient packet = waypoint.getClientSharedUnlockPacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public void hideOrUnhideSharedCustomWaypoint(final LOTRCustomWaypoint waypoint, final boolean hide) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to unlock a shared custom waypoint with no owner!", new Object[0]);
            return;
        }
        waypoint.setSharedHidden(hide);
        final CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
        if (hide) {
            this.cwpSharedHidden.add(key);
        }
        else {
            this.cwpSharedHidden.remove(key);
        }
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketCWPSharedHideClient packet = waypoint.getClientSharedHidePacket(hide);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public int getFTCooldown(final LOTRAbstractWaypoint wp) {
        final int cooldownMax = LOTRLevelData.getFTCooldownMax();
        final int cooldownMin = LOTRLevelData.getFTCooldownMin();
        final float f = this.getFTCooldownFraction(wp);
        int cooldown = cooldownMin + Math.round((cooldownMax - cooldownMin) * f);
        cooldown = Math.max(0, cooldown);
        return cooldown;
    }
    
    public float getFTCooldownFraction(final LOTRAbstractWaypoint wp) {
        if (wp instanceof LOTRCustomWaypoint) {
            final LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
            final int ID = cwp.getID();
            if (cwp.isShared()) {
                final UUID sharingPlayer = cwp.getSharingPlayerID();
                final CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
                if (this.cwpSharedCooldowns.containsKey(key)) {
                    return this.cwpSharedCooldowns.get(key);
                }
            }
            else if (this.cwpCooldowns.containsKey(ID)) {
                return this.cwpCooldowns.get(ID);
            }
        }
        else if (this.wpCooldowns.containsKey(wp)) {
            return this.wpCooldowns.get(wp);
        }
        return 1.0f;
    }
    
    public void setFTCooldownFraction(final LOTRAbstractWaypoint wp, final float f) {
        if (wp instanceof LOTRCustomWaypoint) {
            final LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
            final int ID = cwp.getID();
            if (cwp.isShared()) {
                final UUID sharingPlayer = cwp.getSharingPlayerID();
                final CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
                this.cwpSharedCooldowns.put(key, f);
            }
            else {
                this.cwpCooldowns.put(ID, f);
            }
        }
        else {
            this.wpCooldowns.put((LOTRWaypoint)wp, f);
        }
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketWaypointCooldownFraction packet = new LOTRPacketWaypointCooldownFraction(wp, f);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public int getNextCwpID() {
        return this.nextCwpID;
    }
    
    public void incrementNextCwpID() {
        ++this.nextCwpID;
        this.markDirty();
    }
    
    private void addSharedCustomWaypoints(final UUID oneFellowshipID) {
        final List<UUID> fellowPlayerIDs = new ArrayList<UUID>();
        List<UUID> checkFellowshipIDs;
        if (oneFellowshipID != null) {
            checkFellowshipIDs = new ArrayList<UUID>();
            checkFellowshipIDs.add(oneFellowshipID);
        }
        else {
            checkFellowshipIDs = this.fellowshipIDs;
        }
        for (final UUID fsID : checkFellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null) {
                final List<UUID> playerIDs = fs.getAllPlayerUUIDs();
                for (final UUID player : playerIDs) {
                    if (!player.equals(this.playerUUID) && !fellowPlayerIDs.contains(player)) {
                        fellowPlayerIDs.add(player);
                    }
                }
            }
        }
        for (final UUID player2 : fellowPlayerIDs) {
            final LOTRPlayerData pd = LOTRLevelData.getData(player2);
            final List<LOTRCustomWaypoint> cwps = pd.getCustomWaypoints();
            for (final LOTRCustomWaypoint waypoint : cwps) {
                boolean inSharedFellowship = false;
                for (final UUID fsID2 : checkFellowshipIDs) {
                    if (waypoint.hasSharedFellowship(fsID2)) {
                        inSharedFellowship = true;
                        break;
                    }
                }
                if (inSharedFellowship) {
                    this.addOrUpdateSharedCustomWaypoint(waypoint.createCopyOfShared(player2));
                }
            }
        }
    }
    
    private void removeSharedCustomWaypoints() {
        final List<LOTRCustomWaypoint> removes = new ArrayList<LOTRCustomWaypoint>();
        for (final LOTRCustomWaypoint waypoint : this.customWaypointsShared) {
            final LOTRCustomWaypoint wpOriginal = LOTRLevelData.getData(waypoint.getSharingPlayerID()).getCustomWaypointByID(waypoint.getID());
            if (wpOriginal != null) {
                final List<UUID> sharedFellowPlayers = wpOriginal.getPlayersInAllSharedFellowships();
                if (sharedFellowPlayers.contains(this.playerUUID)) {
                    continue;
                }
                removes.add(waypoint);
            }
        }
        for (final LOTRCustomWaypoint waypoint : removes) {
            this.removeSharedCustomWaypoint(waypoint);
        }
    }
    
    private void unlockSharedCustomWaypoints(final EntityPlayer entityplayer) {
        if (this.pdTick % 20 == 0 && ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            final List<LOTRCustomWaypoint> unlockWaypoints = new ArrayList<LOTRCustomWaypoint>();
            for (final LOTRCustomWaypoint waypoint : this.customWaypointsShared) {
                if (waypoint.isShared() && !waypoint.isSharedUnlocked() && waypoint.canUnlockShared(entityplayer)) {
                    unlockWaypoints.add(waypoint);
                }
            }
            for (final LOTRCustomWaypoint waypoint : unlockWaypoints) {
                this.unlockSharedCustomWaypoint(waypoint);
            }
        }
    }
    
    public List<UUID> getFellowshipIDs() {
        return this.fellowshipIDs;
    }
    
    public List<LOTRFellowship> getFellowships() {
        final List<LOTRFellowship> fellowships = new ArrayList<LOTRFellowship>();
        for (final UUID fsID : this.fellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null && !fs.isDisbanded()) {
                fellowships.add(fs);
            }
        }
        return fellowships;
    }
    
    public LOTRFellowship getFellowshipByName(final String fsName) {
        for (final UUID fsID : this.fellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null && !fs.isDisbanded() && fs.getName().equalsIgnoreCase(fsName)) {
                return fs;
            }
        }
        return null;
    }
    
    public List<String> listAllFellowshipNames() {
        final List<String> list = new ArrayList<String>();
        for (final UUID fsID : this.fellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null && !fs.isDisbanded() && fs.containsPlayer(this.playerUUID)) {
                list.add(fs.getName());
            }
        }
        return list;
    }
    
    public List<String> listAllLeadingFellowshipNames() {
        final List<String> list = new ArrayList<String>();
        for (final UUID fsID : this.fellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null && !fs.isDisbanded() && fs.isOwner(this.playerUUID)) {
                list.add(fs.getName());
            }
        }
        return list;
    }
    
    public void addFellowship(final LOTRFellowship fs) {
        if (fs.containsPlayer(this.playerUUID)) {
            final UUID fsID = fs.getFellowshipID();
            if (!this.fellowshipIDs.contains(fsID)) {
                this.fellowshipIDs.add(fsID);
                this.markDirty();
                this.sendFellowshipPacket(fs);
                this.addSharedCustomWaypoints(fs.getFellowshipID());
            }
        }
    }
    
    public void removeFellowship(final LOTRFellowship fs) {
        if (fs.isDisbanded() || !fs.containsPlayer(this.playerUUID)) {
            final UUID fsID = fs.getFellowshipID();
            if (this.fellowshipIDs.contains(fsID)) {
                this.fellowshipIDs.remove(fsID);
                this.markDirty();
                this.sendFellowshipRemovePacket(fs);
                this.removeSharedCustomWaypoints();
            }
        }
    }
    
    public void updateFellowship(final LOTRFellowship fs) {
        this.sendFellowshipPacket(fs);
        this.addSharedCustomWaypoints(fs.getFellowshipID());
        this.removeSharedCustomWaypoints();
    }
    
    public void createFellowship(final String name, final boolean restrict) {
        if (restrict && !this.canCreateFellowships(false)) {
            return;
        }
        if (!this.anyMatchingFellowshipNames(name, false)) {
            final LOTRFellowship fellowship = new LOTRFellowship(this.playerUUID, name);
            fellowship.createAndRegister();
        }
    }
    
    public boolean canCreateFellowships(final boolean client) {
        final int max = this.getMaxLeadingFellowships();
        int leading = 0;
        if (client) {
            for (final LOTRFellowshipClient fs : this.fellowshipsClient) {
                if (fs.isOwned() && ++leading >= max) {
                    return false;
                }
            }
        }
        else {
            for (final UUID fsID : this.fellowshipIDs) {
                final LOTRFellowship fs2 = LOTRFellowshipData.getFellowship(fsID);
                if (fs2 != null && !fs2.isDisbanded() && fs2.isOwner(this.playerUUID) && ++leading >= max) {
                    return false;
                }
            }
        }
        return leading < max;
    }
    
    private int getMaxLeadingFellowships() {
        final int achievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size();
        return 1 + achievements / 20;
    }
    
    public void disbandFellowship(final LOTRFellowship fs) {
        if (fs.isOwner(this.playerUUID)) {
            final List<UUID> memberUUIDs = new ArrayList<UUID>(fs.getMemberUUIDs());
            fs.disband();
            this.removeFellowship(fs);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                for (final UUID memberID : memberUUIDs) {
                    final EntityPlayer member = this.getOtherPlayer(memberID);
                    if (member != null) {
                        fs.sendNotification(member, "lotr.gui.fellowships.notifyDisband", entityplayer.getCommandSenderName());
                    }
                }
            }
        }
    }
    
    public void invitePlayerToFellowship(final LOTRFellowship fs, final UUID player) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            LOTRLevelData.getData(player).addFellowshipInvite(fs, this.playerUUID);
        }
    }
    
    public void removePlayerFromFellowship(final LOTRFellowship fs, final UUID player) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.removeMember(player);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final EntityPlayer removed = this.getOtherPlayer(player);
                if (removed != null) {
                    fs.sendNotification(removed, "lotr.gui.fellowships.notifyRemove", entityplayer.getCommandSenderName());
                }
            }
        }
    }
    
    public void transferFellowship(final LOTRFellowship fs, final UUID player) {
        if (fs.isOwner(this.playerUUID)) {
            fs.setOwner(player);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final EntityPlayer newOwner = this.getOtherPlayer(player);
                if (newOwner != null) {
                    fs.sendNotification(newOwner, "lotr.gui.fellowships.notifyTransfer", entityplayer.getCommandSenderName());
                }
            }
        }
    }
    
    public void setFellowshipAdmin(final LOTRFellowship fs, final UUID player, final boolean flag) {
        if (fs.isOwner(this.playerUUID)) {
            fs.setAdmin(player, flag);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final EntityPlayer otherPlayer = this.getOtherPlayer(player);
                if (otherPlayer != null) {
                    if (flag) {
                        fs.sendNotification(otherPlayer, "lotr.gui.fellowships.notifyOp", entityplayer.getCommandSenderName());
                    }
                    else {
                        fs.sendNotification(otherPlayer, "lotr.gui.fellowships.notifyDeop", entityplayer.getCommandSenderName());
                    }
                }
            }
        }
    }
    
    public void renameFellowship(final LOTRFellowship fs, final String name) {
        if (fs.isOwner(this.playerUUID)) {
            fs.setName(name);
        }
    }
    
    public void setFellowshipIcon(final LOTRFellowship fs, final ItemStack itemstack) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.setIcon(itemstack);
        }
    }
    
    public void setFellowshipPreventPVP(final LOTRFellowship fs, final boolean prevent) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.setPreventPVP(prevent);
        }
    }
    
    public void setFellowshipPreventHiredFF(final LOTRFellowship fs, final boolean prevent) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.setPreventHiredFriendlyFire(prevent);
        }
    }
    
    public void setFellowshipShowMapLocations(final LOTRFellowship fs, final boolean show) {
        if (fs.isOwner(this.playerUUID)) {
            fs.setShowMapLocations(show);
        }
    }
    
    public void leaveFellowship(final LOTRFellowship fs) {
        if (!fs.isOwner(this.playerUUID)) {
            fs.removeMember(this.playerUUID);
            if (this.fellowshipIDs.contains(fs.getFellowshipID())) {
                this.removeFellowship(fs);
            }
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final EntityPlayer owner = this.getOtherPlayer(fs.getOwner());
                if (owner != null) {
                    fs.sendNotification(owner, "lotr.gui.fellowships.notifyLeave", entityplayer.getCommandSenderName());
                }
            }
        }
    }
    
    private void sendFellowshipPacket(final LOTRFellowship fs) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketFellowship packet = new LOTRPacketFellowship(this, fs, false);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    private void sendFellowshipRemovePacket(final LOTRFellowship fs) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketFellowshipRemove packet = new LOTRPacketFellowshipRemove(fs, false);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public List<LOTRFellowshipClient> getClientFellowships() {
        return this.fellowshipsClient;
    }
    
    public boolean anyMatchingFellowshipNames(String name, final boolean client) {
        name = StringUtils.strip(name).toLowerCase();
        if (client) {
            for (final LOTRFellowshipClient fs : this.fellowshipsClient) {
                String otherName = fs.getName();
                otherName = StringUtils.strip(otherName).toLowerCase();
                if (name.equals(otherName)) {
                    return true;
                }
            }
        }
        else {
            for (final UUID fsID : this.fellowshipIDs) {
                final LOTRFellowship fs2 = LOTRFellowshipData.getFellowship(fsID);
                if (fs2 != null && !fs2.isDisbanded()) {
                    String otherName2 = fs2.getName();
                    otherName2 = StringUtils.strip(otherName2).toLowerCase();
                    if (name.equals(otherName2)) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }
    
    public void addOrUpdateClientFellowship(final LOTRFellowshipClient fs) {
        final UUID fsID = fs.getFellowshipID();
        LOTRFellowshipClient inList = null;
        for (final LOTRFellowshipClient fsInList : this.fellowshipsClient) {
            if (fsInList.getFellowshipID().equals(fsID)) {
                inList = fsInList;
                break;
            }
        }
        if (inList != null) {
            inList.updateDataFrom(fs);
        }
        else {
            this.fellowshipsClient.add(fs);
        }
    }
    
    public void removeClientFellowship(final UUID fsID) {
        LOTRFellowshipClient inList = null;
        for (final LOTRFellowshipClient fsInList : this.fellowshipsClient) {
            if (fsInList.getFellowshipID().equals(fsID)) {
                inList = fsInList;
                break;
            }
        }
        if (inList != null) {
            this.fellowshipsClient.remove(inList);
        }
    }
    
    public LOTRFellowshipClient getClientFellowshipByName(final String fsName) {
        for (final LOTRFellowshipClient fs : this.fellowshipsClient) {
            if (fs.getName().equalsIgnoreCase(fsName)) {
                return fs;
            }
        }
        return null;
    }
    
    public LOTRFellowshipClient getClientFellowshipByID(final UUID fsID) {
        for (final LOTRFellowshipClient fs : this.fellowshipsClient) {
            if (fs.getFellowshipID().equals(fsID)) {
                return fs;
            }
        }
        return null;
    }
    
    public void addFellowshipInvite(final LOTRFellowship fs, final UUID inviterUUID) {
        final UUID fsID = fs.getFellowshipID();
        boolean contains = false;
        for (final LOTRFellowshipInvite invite : this.fellowshipInvites) {
            if (invite.fellowshipID.equals(fsID)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            this.fellowshipInvites.add(new LOTRFellowshipInvite(fsID, inviterUUID));
            this.markDirty();
            this.sendFellowshipInvitePacket(fs);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final EntityPlayer inviter = this.getOtherPlayer(inviterUUID);
                if (inviter != null) {
                    fs.sendNotification(entityplayer, "lotr.gui.fellowships.notifyInvite", inviter.getCommandSenderName());
                }
            }
        }
    }
    
    public void acceptFellowshipInvite(final LOTRFellowship fs) {
        final UUID fsID = fs.getFellowshipID();
        LOTRFellowshipInvite existingInvite = null;
        for (final LOTRFellowshipInvite invite : this.fellowshipInvites) {
            if (invite.fellowshipID.equals(fsID)) {
                existingInvite = invite;
                break;
            }
        }
        if (existingInvite != null) {
            if (!fs.isDisbanded()) {
                fs.addMember(this.playerUUID);
            }
            this.fellowshipInvites.remove(existingInvite);
            this.markDirty();
            this.sendFellowshipInviteRemovePacket(fs);
            if (!fs.isDisbanded()) {
                final EntityPlayer entityplayer = this.getPlayer();
                if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                    UUID inviterID = existingInvite.inviterID;
                    if (inviterID == null) {
                        inviterID = fs.getOwner();
                    }
                    final EntityPlayer inviter = this.getOtherPlayer(inviterID);
                    if (inviter != null) {
                        fs.sendNotification(inviter, "lotr.gui.fellowships.notifyAccept", entityplayer.getCommandSenderName());
                    }
                }
            }
        }
    }
    
    public void rejectFellowshipInvite(final LOTRFellowship fs) {
        final UUID fsID = fs.getFellowshipID();
        LOTRFellowshipInvite existingInvite = null;
        for (final LOTRFellowshipInvite invite : this.fellowshipInvites) {
            if (invite.fellowshipID.equals(fsID)) {
                existingInvite = invite;
                break;
            }
        }
        if (existingInvite != null) {
            this.fellowshipInvites.remove(existingInvite);
            this.markDirty();
            this.sendFellowshipInviteRemovePacket(fs);
        }
    }
    
    private void sendFellowshipInvitePacket(final LOTRFellowship fs) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketFellowship packet = new LOTRPacketFellowship(this, fs, true);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    private void sendFellowshipInviteRemovePacket(final LOTRFellowship fs) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketFellowshipRemove packet = new LOTRPacketFellowshipRemove(fs, true);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public List<LOTRFellowshipClient> getClientFellowshipInvites() {
        return this.fellowshipInvitesClient;
    }
    
    public void addOrUpdateClientFellowshipInvite(final LOTRFellowshipClient fs) {
        final UUID fsID = fs.getFellowshipID();
        LOTRFellowshipClient inList = null;
        for (final LOTRFellowshipClient fsInList : this.fellowshipInvitesClient) {
            if (fsInList.getFellowshipID().equals(fsID)) {
                inList = fsInList;
                break;
            }
        }
        if (inList != null) {
            inList.updateDataFrom(fs);
        }
        else {
            this.fellowshipInvitesClient.add(fs);
        }
    }
    
    public void removeClientFellowshipInvite(final UUID fsID) {
        LOTRFellowshipClient inList = null;
        for (final LOTRFellowshipClient fsInList : this.fellowshipInvitesClient) {
            if (fsInList.getFellowshipID().equals(fsID)) {
                inList = fsInList;
                break;
            }
        }
        if (inList != null) {
            this.fellowshipInvitesClient.remove(inList);
        }
    }
    
    public UUID getChatBoundFellowshipID() {
        return this.chatBoundFellowshipID;
    }
    
    public LOTRFellowship getChatBoundFellowship() {
        if (this.chatBoundFellowshipID != null) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(this.chatBoundFellowshipID);
            if (fs != null) {
                return fs;
            }
        }
        return null;
    }
    
    public void setChatBoundFellowshipID(final UUID fsID) {
        this.chatBoundFellowshipID = fsID;
        this.markDirty();
    }
    
    public void setChatBoundFellowship(final LOTRFellowship fs) {
        this.setChatBoundFellowshipID(fs.getFellowshipID());
    }
    
    public void setSiegeActive(final int duration) {
        this.siegeActiveTime = Math.max(this.siegeActiveTime, duration);
    }
    
    public boolean isSiegeActive() {
        return this.siegeActiveTime > 0;
    }
    
    public LOTRFaction getViewingFaction() {
        return this.viewingFaction;
    }
    
    public void setViewingFaction(final LOTRFaction faction) {
        if (faction != null) {
            this.viewingFaction = faction;
            this.markDirty();
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketUpdateViewingFaction packet = new LOTRPacketUpdateViewingFaction(this.viewingFaction);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }
    
    public LOTRFaction getRegionLastViewedFaction(final LOTRDimension.DimensionRegion region) {
        LOTRFaction fac = this.prevRegionFactions.get(region);
        if (fac == null) {
            fac = region.factionList.get(0);
            this.prevRegionFactions.put(region, fac);
        }
        return fac;
    }
    
    public void setRegionLastViewedFaction(final LOTRDimension.DimensionRegion region, final LOTRFaction fac) {
        if (region.factionList.contains(fac)) {
            this.prevRegionFactions.put(region, fac);
            this.markDirty();
        }
    }
    
    public boolean showWaypoints() {
        return this.showWaypoints;
    }
    
    public void setShowWaypoints(final boolean flag) {
        this.showWaypoints = flag;
        this.markDirty();
    }
    
    public boolean showCustomWaypoints() {
        return this.showCustomWaypoints;
    }
    
    public void setShowCustomWaypoints(final boolean flag) {
        this.showCustomWaypoints = flag;
        this.markDirty();
    }
    
    public boolean showHiddenSharedWaypoints() {
        return this.showHiddenSharedWaypoints;
    }
    
    public void setShowHiddenSharedWaypoints(final boolean flag) {
        this.showHiddenSharedWaypoints = flag;
        this.markDirty();
    }
    
    public boolean getHideMapLocation() {
        return this.hideOnMap;
    }
    
    public void setHideMapLocation(final boolean flag) {
        this.hideOnMap = flag;
        this.markDirty();
        this.sendOptionsPacket(3, flag);
    }
    
    public boolean getAdminHideMap() {
        return this.adminHideMap;
    }
    
    public void setAdminHideMap(final boolean flag) {
        this.adminHideMap = flag;
        this.markDirty();
    }
    
    public boolean getEnableConquestKills() {
        return this.conquestKills;
    }
    
    public void setEnableConquestKills(final boolean flag) {
        this.conquestKills = flag;
        this.markDirty();
        this.sendOptionsPacket(5, flag);
    }
    
    public boolean getAskedForGandalf() {
        return this.askedForGandalf;
    }
    
    public void setAskedForGandalf(final boolean flag) {
        this.askedForGandalf = flag;
        this.markDirty();
    }
    
    public boolean getTeleportedME() {
        return this.teleportedME;
    }
    
    public void setTeleportedME(final boolean flag) {
        this.teleportedME = flag;
        this.markDirty();
    }
    
    public ChunkCoordinates getDeathPoint() {
        return this.deathPoint;
    }
    
    public void setDeathPoint(final int i, final int j, final int k) {
        this.deathPoint = new ChunkCoordinates(i, j, k);
        this.markDirty();
    }
    
    public int getDeathDimension() {
        return this.deathDim;
    }
    
    public void setDeathDimension(final int dim) {
        this.deathDim = dim;
        this.markDirty();
    }
    
    public int getAlcoholTolerance() {
        return this.alcoholTolerance;
    }
    
    public void setAlcoholTolerance(final int i) {
        this.alcoholTolerance = i;
        this.markDirty();
        if (this.alcoholTolerance >= 250) {
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                this.addAchievement(LOTRAchievement.gainHighAlcoholTolerance);
            }
        }
    }
    
    public List<LOTRMiniQuest> getMiniQuests() {
        return this.miniQuests;
    }
    
    public List<LOTRMiniQuest> getMiniQuestsCompleted() {
        return this.miniQuestsCompleted;
    }
    
    public void addMiniQuest(final LOTRMiniQuest quest) {
        this.miniQuests.add(quest);
        this.updateMiniQuest(quest);
    }
    
    public void addMiniQuestCompleted(final LOTRMiniQuest quest) {
        this.miniQuestsCompleted.add(quest);
        this.markDirty();
    }
    
    public void removeMiniQuest(final LOTRMiniQuest quest, final boolean completed) {
        List<LOTRMiniQuest> removeList;
        if (completed) {
            removeList = this.miniQuestsCompleted;
        }
        else {
            removeList = this.miniQuests;
        }
        if (removeList.remove(quest)) {
            this.markDirty();
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketMiniquestRemove packet = new LOTRPacketMiniquestRemove(quest, quest.isCompleted(), false);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
        else {
            FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data", new Object[0]);
        }
    }
    
    public void updateMiniQuest(final LOTRMiniQuest quest) {
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            try {
                this.sendMiniQuestPacket((EntityPlayerMP)entityplayer, quest, false);
            }
            catch (IOException e) {
                FMLLog.severe("Error sending miniquest packet to player " + entityplayer.getCommandSenderName(), new Object[0]);
                e.printStackTrace();
            }
        }
    }
    
    public void completeMiniQuest(final LOTRMiniQuest quest) {
        if (this.miniQuests.remove(quest)) {
            this.addMiniQuestCompleted(quest);
            ++this.completedMiniquestCount;
            this.getFactionData(quest.entityFaction).completeMiniQuest();
            this.markDirty();
            LOTRMod.proxy.setTrackedQuest(quest);
            final EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
                final LOTRPacketMiniquestRemove packet = new LOTRPacketMiniquestRemove(quest, false, true);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
        else {
            FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data", new Object[0]);
        }
    }
    
    private void sendMiniQuestPacket(final EntityPlayerMP entityplayer, final LOTRMiniQuest quest, final boolean completed) throws IOException {
        final NBTTagCompound nbt = new NBTTagCompound();
        quest.writeToNBT(nbt);
        final LOTRPacketMiniquest packet = new LOTRPacketMiniquest(nbt, completed);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public LOTRMiniQuest getMiniQuestForID(final UUID id, final boolean completed) {
        List<LOTRMiniQuest> threadSafe;
        if (completed) {
            threadSafe = new ArrayList<LOTRMiniQuest>(this.miniQuestsCompleted);
        }
        else {
            threadSafe = new ArrayList<LOTRMiniQuest>(this.miniQuests);
        }
        for (final LOTRMiniQuest quest : threadSafe) {
            if (quest.questUUID.equals(id)) {
                return quest;
            }
        }
        return null;
    }
    
    public List<LOTRMiniQuest> getMiniQuestsForEntity(final LOTREntityNPC npc, final boolean activeOnly) {
        return this.getMiniQuestsForEntityID(npc.getUniqueID(), activeOnly);
    }
    
    public List<LOTRMiniQuest> getMiniQuestsForEntityID(final UUID npcID, final boolean activeOnly) {
        final List<LOTRMiniQuest> forEntity = new ArrayList<LOTRMiniQuest>();
        final List<LOTRMiniQuest> threadSafe = new ArrayList<LOTRMiniQuest>(this.miniQuests);
        for (final LOTRMiniQuest quest : threadSafe) {
            if (quest.entityUUID.equals(npcID)) {
                if (activeOnly) {
                    if (!quest.isActive()) {
                        continue;
                    }
                    forEntity.add(quest);
                }
                else {
                    forEntity.add(quest);
                }
            }
        }
        return forEntity;
    }
    
    public List<LOTRMiniQuest> getMiniQuestsForFaction(final LOTRFaction f, final boolean activeOnly) {
        final List<LOTRMiniQuest> forFaction = new ArrayList<LOTRMiniQuest>();
        for (final LOTRMiniQuest quest : this.miniQuests) {
            if (quest.entityFaction == f) {
                if (activeOnly) {
                    if (!quest.isActive()) {
                        continue;
                    }
                    forFaction.add(quest);
                }
                else {
                    forFaction.add(quest);
                }
            }
        }
        return forFaction;
    }
    
    public List getActiveMiniQuests() {
        final List<LOTRMiniQuest> actives = new ArrayList<LOTRMiniQuest>();
        for (final LOTRMiniQuest quest : this.miniQuests) {
            if (quest.isActive()) {
                actives.add(quest);
            }
        }
        return actives;
    }
    
    public int getCompletedMiniQuestsTotal() {
        return this.completedMiniquestCount;
    }
    
    public int getCompletedBountyQuests() {
        return this.completedBountyQuests;
    }
    
    public void addCompletedBountyQuest() {
        ++this.completedBountyQuests;
        this.markDirty();
    }
    
    public boolean hasActiveOrCompleteMQType(final Class<? extends LOTRMiniQuest> type) {
        final List<LOTRMiniQuest> quests = this.getMiniQuests();
        final List<LOTRMiniQuest> questsComplete = this.getMiniQuestsCompleted();
        final List<LOTRMiniQuest> allQuests = new ArrayList<LOTRMiniQuest>();
        for (final LOTRMiniQuest q : quests) {
            if (q.isActive()) {
                allQuests.add(q);
            }
        }
        allQuests.addAll(questsComplete);
        for (final LOTRMiniQuest q : allQuests) {
            if (type.isAssignableFrom(q.getClass())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasAnyGWQuest() {
        return this.hasActiveOrCompleteMQType(LOTRMiniQuestWelcome.class);
    }
    
    public void distributeMQEvent(final LOTRMiniQuestEvent event) {
        final List<LOTRMiniQuest> actives = new ArrayList<LOTRMiniQuest>();
        for (final LOTRMiniQuest quest : this.miniQuests) {
            if (quest.isActive()) {
                quest.handleEvent(event);
            }
        }
    }
    
    public LOTRMiniQuest getTrackingMiniQuest() {
        if (this.trackingMiniQuestID == null) {
            return null;
        }
        return this.getMiniQuestForID(this.trackingMiniQuestID, false);
    }
    
    public void setTrackingMiniQuest(final LOTRMiniQuest quest) {
        this.setTrackingMiniQuestID((quest == null) ? null : quest.questUUID);
    }
    
    public void setTrackingMiniQuestID(final UUID npcID) {
        this.trackingMiniQuestID = npcID;
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketMiniquestTrackClient packet = new LOTRPacketMiniquestTrackClient(this.trackingMiniQuestID);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public void placeBountyFor(final LOTRFaction f) {
        this.bountiesPlaced.add(f);
        this.markDirty();
    }
    
    public LOTRWaypoint getLastKnownWaypoint() {
        return this.lastWaypoint;
    }
    
    public LOTRBiome getLastKnownBiome() {
        return this.lastBiome;
    }
    
    public void sendMessageIfNotReceived(final LOTRGuiMessageTypes message) {
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            Boolean sent = this.sentMessageTypes.get(message);
            if (sent == null) {
                sent = false;
                this.sentMessageTypes.put(message, sent);
            }
            if (!sent) {
                this.sentMessageTypes.put(message, true);
                this.markDirty();
                final LOTRPacketMessage packet = new LOTRPacketMessage(message);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }
    
    public LOTRTitle.PlayerTitle getPlayerTitle() {
        return this.playerTitle;
    }
    
    public void setPlayerTitle(final LOTRTitle.PlayerTitle title) {
        this.playerTitle = title;
        this.markDirty();
        final EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !((Entity)entityplayer).worldObj.isClient) {
            final LOTRPacketTitle packet = new LOTRPacketTitle(this.playerTitle);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        for (final UUID fsID : this.fellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null) {
                fs.updateForAllMembers();
            }
        }
    }
    
    public boolean getFemRankOverride() {
        return this.femRankOverride;
    }
    
    public void setFemRankOverride(final boolean flag) {
        this.femRankOverride = flag;
        this.markDirty();
        this.sendOptionsPacket(4, flag);
    }
    
    public boolean useFeminineRanks() {
        if (this.femRankOverride) {
            return true;
        }
        if (this.playerTitle != null) {
            final LOTRTitle title = this.playerTitle.getTitle();
            return title.isFeminineRank();
        }
        return false;
    }
    
    public LOTRPlayerQuestData getQuestData() {
        return this.questData;
    }
    
    static {
        LOTRPlayerData.ticksUntilFT_max = 200;
        LOTRPlayerData.startCwpID = 20000;
    }
    
    private static class CWPSharedKey extends Pair<UUID, Integer>
    {
        public final UUID sharingPlayer;
        public final int waypointID;
        
        private CWPSharedKey(final UUID player, final int id) {
            this.sharingPlayer = player;
            this.waypointID = id;
        }
        
        public static CWPSharedKey keyFor(final UUID player, final int id) {
            return new CWPSharedKey(player, id);
        }
        
        public Integer setValue(final Integer value) {
            throw new UnsupportedOperationException();
        }
        
        public UUID getLeft() {
            return this.sharingPlayer;
        }
        
        public Integer getRight() {
            return this.waypointID;
        }
    }
}
