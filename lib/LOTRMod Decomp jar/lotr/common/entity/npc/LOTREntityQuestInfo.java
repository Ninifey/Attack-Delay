// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import net.minecraft.util.MathHelper;
import lotr.common.network.LOTRPacketNPCIsOfferingQuest;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMiniquestOffer;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuestBounty;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRConfig;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import lotr.common.quest.LOTRMiniQuest;

public class LOTREntityQuestInfo
{
    private LOTREntityNPC theNPC;
    private LOTRMiniQuest miniquestOffer;
    private int offerTime;
    public static final int maxOfferTime = 24000;
    private int offerChance;
    private static final int offerChance_default = 20000;
    private float minAlignment;
    private Map<UUID, LOTRMiniQuest> playerSpecificOffers;
    private List<EntityPlayer> openOfferPlayers;
    public boolean clientIsOffering;
    public int clientOfferColor;
    private List<UUID> activeQuestPlayers;
    
    public LOTREntityQuestInfo(final LOTREntityNPC npc) {
        this.offerTime = 0;
        this.playerSpecificOffers = new HashMap<UUID, LOTRMiniQuest>();
        this.openOfferPlayers = new ArrayList<EntityPlayer>();
        this.activeQuestPlayers = new ArrayList<UUID>();
        this.theNPC = npc;
        this.offerChance = 20000;
        this.minAlignment = 0.0f;
    }
    
    public void setOfferChance(final int i) {
        this.offerChance = i;
    }
    
    public void setMinAlignment(final float f) {
        this.minAlignment = f;
    }
    
    private boolean canGenerateQuests() {
        return LOTRConfig.allowMiniquests && !this.theNPC.isChild() && !this.theNPC.isDrunkard() && !this.theNPC.isTrader() && !this.theNPC.isTraderEscort && !this.theNPC.hiredNPCInfo.isActive;
    }
    
    public boolean canOfferQuestsTo(final EntityPlayer entityplayer) {
        if (this.canGenerateQuests() && this.theNPC.isFriendly(entityplayer) && this.theNPC.getAttackTarget() == null) {
            final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction());
            return alignment >= this.minAlignment;
        }
        return false;
    }
    
    private LOTRMiniQuest generateRandomMiniQuest() {
        for (int tries = 8, l = 0; l < tries; ++l) {
            final LOTRMiniQuest quest = this.theNPC.createMiniQuest();
            if (quest != null) {
                if (quest.isValidQuest()) {
                    return quest;
                }
                FMLLog.severe("Created an invalid LOTR miniquest " + quest.speechBankStart, new Object[0]);
            }
        }
        return null;
    }
    
    public LOTRMiniQuest getOfferFor(final EntityPlayer entityplayer) {
        return this.getOfferFor(entityplayer, null);
    }
    
    private LOTRMiniQuest getOfferFor(final EntityPlayer entityplayer, final boolean[] isSpecific) {
        final UUID id = entityplayer.getUniqueID();
        if (this.playerSpecificOffers.containsKey(id)) {
            if (isSpecific != null) {
                isSpecific[0] = true;
            }
            return this.playerSpecificOffers.get(id);
        }
        if (isSpecific != null) {
            isSpecific[0] = false;
        }
        return this.miniquestOffer;
    }
    
    public void clearMiniQuestOffer() {
        this.setMiniQuestOffer(null, 0);
    }
    
    public void setMiniQuestOffer(final LOTRMiniQuest quest, final int time) {
        this.miniquestOffer = quest;
        this.offerTime = time;
    }
    
    public void setPlayerSpecificOffer(final EntityPlayer entityplayer, final LOTRMiniQuest quest) {
        this.playerSpecificOffers.put(entityplayer.getUniqueID(), quest);
    }
    
    public void clearPlayerSpecificOffer(final EntityPlayer entityplayer) {
        this.playerSpecificOffers.remove(entityplayer.getUniqueID());
    }
    
    public void addOpenOfferPlayer(final EntityPlayer entityplayer) {
        this.openOfferPlayers.add(entityplayer);
    }
    
    public void removeOpenOfferPlayer(final EntityPlayer entityplayer) {
        this.openOfferPlayers.remove(entityplayer);
    }
    
    public boolean anyOpenOfferPlayers() {
        return !this.openOfferPlayers.isEmpty();
    }
    
    public void onUpdate() {
        if (!((Entity)this.theNPC).worldObj.isClient) {
            if (this.miniquestOffer == null) {
                if (this.canGenerateQuests() && this.theNPC.getRNG().nextInt(this.offerChance) == 0) {
                    this.miniquestOffer = this.generateRandomMiniQuest();
                    if (this.miniquestOffer != null) {
                        this.offerTime = 24000;
                    }
                }
            }
            else if (!this.miniquestOffer.isValidQuest() || !this.canGenerateQuests()) {
                this.clearMiniQuestOffer();
            }
            else if (!this.anyOpenOfferPlayers()) {
                if (this.offerTime > 0) {
                    --this.offerTime;
                }
                else {
                    this.clearMiniQuestOffer();
                }
            }
            if (!this.activeQuestPlayers.isEmpty()) {
                final Set<UUID> removes = new HashSet<UUID>();
                for (final UUID player : this.activeQuestPlayers) {
                    final List<LOTRMiniQuest> playerQuests = LOTRLevelData.getData(player).getMiniQuestsForEntity(this.theNPC, true);
                    if (playerQuests.isEmpty()) {
                        removes.add(player);
                    }
                    else {
                        for (final LOTRMiniQuest quest : playerQuests) {
                            quest.updateLocation(this.theNPC);
                        }
                    }
                }
                this.activeQuestPlayers.removeAll(removes);
            }
            if (((Entity)this.theNPC).ticksExisted % 10 == 0) {
                this.sendDataToAllWatchers();
            }
        }
    }
    
    public boolean anyActiveQuestPlayers() {
        return !this.activeQuestPlayers.isEmpty();
    }
    
    public void addActiveQuestPlayer(final EntityPlayer entityplayer) {
        this.activeQuestPlayers.add(entityplayer.getUniqueID());
    }
    
    private void removeActiveQuestPlayer(final EntityPlayer entityplayer) {
        this.activeQuestPlayers.remove(entityplayer.getUniqueID());
    }
    
    private boolean doesPlayerHaveActiveQuest(final EntityPlayer entityplayer) {
        return this.activeQuestPlayers.contains(entityplayer.getUniqueID());
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        if (this.canOfferQuestsTo(entityplayer)) {
            final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
            final List<LOTRMiniQuest> questsInProgress = playerData.getMiniQuestsForEntity(this.theNPC, true);
            if (!questsInProgress.isEmpty()) {
                final LOTRMiniQuest activeQuest = questsInProgress.get(0);
                activeQuest.onInteract(entityplayer, this.theNPC);
                if (activeQuest.isCompleted()) {
                    this.removeActiveQuestPlayer(entityplayer);
                }
                else {
                    playerData.setTrackingMiniQuest(activeQuest);
                }
                return true;
            }
            final LOTRMiniQuest offer = this.getOfferFor(entityplayer);
            if (offer != null && offer.isValidQuest() && offer.canPlayerAccept(entityplayer)) {
                final List<LOTRMiniQuest> questsForFaction = playerData.getMiniQuestsForFaction(this.theNPC.getFaction(), true);
                if (questsForFaction.size() < LOTRMiniQuest.MAX_MINIQUESTS_PER_FACTION) {
                    this.sendMiniquestOffer(entityplayer, offer);
                    return true;
                }
                this.theNPC.sendSpeechBank(entityplayer, offer.speechBankTooMany, offer);
                return true;
            }
            else {
                final LOTRMiniQuestFactory bountyHelpSpeechDir = this.theNPC.getBountyHelpSpeechDir();
                if (bountyHelpSpeechDir != null && this.theNPC.getRNG().nextInt(3) == 0) {
                    final List<LOTRMiniQuest> factionQuests = playerData.getMiniQuestsForFaction(this.theNPC.getFaction(), true);
                    final List<LOTRMiniQuestBounty> bountyQuests = new ArrayList<LOTRMiniQuestBounty>();
                    for (final LOTRMiniQuest quest : factionQuests) {
                        if (quest instanceof LOTRMiniQuestBounty) {
                            final LOTRMiniQuestBounty bQuest = (LOTRMiniQuestBounty)quest;
                            if (bQuest.killed) {
                                continue;
                            }
                            bountyQuests.add(bQuest);
                        }
                    }
                    if (!bountyQuests.isEmpty()) {
                        final LOTRMiniQuestBounty bQuest2 = bountyQuests.get(this.theNPC.getRNG().nextInt(bountyQuests.size()));
                        final UUID targetID = bQuest2.targetID;
                        final String objective = bQuest2.targetName;
                        final LOTRPlayerData targetData = LOTRLevelData.getData(targetID);
                        final LOTRMiniQuestBounty.BountyHelp helpType = LOTRMiniQuestBounty.BountyHelp.getRandomHelpType(this.theNPC.getRNG());
                        String location = null;
                        if (helpType == LOTRMiniQuestBounty.BountyHelp.BIOME) {
                            final LOTRBiome lastBiome = targetData.getLastKnownBiome();
                            if (lastBiome != null) {
                                location = lastBiome.getBiomeDisplayName();
                            }
                        }
                        else if (helpType == LOTRMiniQuestBounty.BountyHelp.WAYPOINT) {
                            final LOTRWaypoint lastWP = targetData.getLastKnownWaypoint();
                            if (lastWP != null) {
                                location = lastWP.getDisplayName();
                            }
                        }
                        if (location != null) {
                            final String speechBank = "miniquest/" + bountyHelpSpeechDir.getBaseName() + "/_bountyHelp_" + helpType.speechName;
                            this.theNPC.sendSpeechBank(entityplayer, speechBank, location, objective);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private void sendMiniquestOffer(final EntityPlayer entityplayer, final LOTRMiniQuest quest) {
        final NBTTagCompound nbt = new NBTTagCompound();
        quest.writeToNBT(nbt);
        final LOTRPacketMiniquestOffer packet = new LOTRPacketMiniquestOffer(this.theNPC.getEntityId(), nbt);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        this.addOpenOfferPlayer(entityplayer);
    }
    
    public void receiveOfferResponse(final EntityPlayer entityplayer, final boolean accept) {
        this.removeOpenOfferPlayer(entityplayer);
        if (accept) {
            final boolean[] container = { false };
            final LOTRMiniQuest quest = this.getOfferFor(entityplayer, container);
            final boolean isSpecific = container[0];
            if (quest != null && quest.isValidQuest() && this.canOfferQuestsTo(entityplayer)) {
                quest.setPlayerData(LOTRLevelData.getData(entityplayer));
                quest.start(entityplayer, this.theNPC);
                if (isSpecific) {
                    this.clearPlayerSpecificOffer(entityplayer);
                }
                else {
                    this.clearMiniQuestOffer();
                }
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        if (this.miniquestOffer != null) {
            final NBTTagCompound questData = new NBTTagCompound();
            this.miniquestOffer.writeToNBT(questData);
            nbt.setTag("MQOffer", (NBTBase)questData);
        }
        nbt.setInteger("MQOfferTime", this.offerTime);
        final NBTTagList specificTags = new NBTTagList();
        for (final Map.Entry<UUID, LOTRMiniQuest> e : this.playerSpecificOffers.entrySet()) {
            final UUID playerID = e.getKey();
            final LOTRMiniQuest offer = e.getValue();
            final NBTTagCompound offerData = new NBTTagCompound();
            offerData.setString("OfferPlayerID", playerID.toString());
            offer.writeToNBT(offerData);
            specificTags.appendTag((NBTBase)offerData);
        }
        nbt.setTag("MQSpecificOffers", (NBTBase)specificTags);
        final NBTTagList activeQuestTags = new NBTTagList();
        for (final UUID player : this.activeQuestPlayers) {
            final String s = player.toString();
            activeQuestTags.appendTag((NBTBase)new NBTTagString(s));
        }
        nbt.setTag("ActiveQuestPlayers", (NBTBase)activeQuestTags);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        if (nbt.func_150297_b("MQOffer", 10)) {
            final NBTTagCompound questData = nbt.getCompoundTag("MQOffer");
            this.miniquestOffer = LOTRMiniQuest.loadQuestFromNBT(questData, null);
        }
        this.offerTime = nbt.getInteger("MQOfferTime");
        this.playerSpecificOffers.clear();
        if (nbt.hasKey("MQSpecificOffers")) {
            final NBTTagList specificTags = nbt.getTagList("MQSpecificOffers", 10);
            for (int i = 0; i < specificTags.tagCount(); ++i) {
                final NBTTagCompound offerData = specificTags.getCompoundTagAt(i);
                try {
                    final UUID playerID = UUID.fromString(offerData.getString("OfferPlayerID"));
                    final LOTRMiniQuest offer = LOTRMiniQuest.loadQuestFromNBT(offerData, null);
                    if (offer != null && offer.isValidQuest()) {
                        this.playerSpecificOffers.put(playerID, offer);
                    }
                }
                catch (Exception e) {
                    FMLLog.warning("Error loading NPC player-specific miniquest offer", new Object[0]);
                    e.printStackTrace();
                }
            }
        }
        this.activeQuestPlayers.clear();
        final NBTTagList activeQuestTags = nbt.getTagList("ActiveQuestPlayers", 8);
        for (int i = 0; i < activeQuestTags.tagCount(); ++i) {
            final String s = activeQuestTags.getStringTagAt(i);
            final UUID player = UUID.fromString(s);
            if (player != null) {
                this.activeQuestPlayers.add(player);
            }
        }
        if (nbt.hasKey("NPCMiniQuestPlayer")) {
            final UUID player2 = UUID.fromString(nbt.getString("NPCMiniQuestPlayer"));
            if (player2 != null) {
                this.activeQuestPlayers.add(player2);
            }
        }
    }
    
    public void sendData(final EntityPlayerMP entityplayer) {
        final LOTRMiniQuest questOffer = this.getOfferFor((EntityPlayer)entityplayer);
        final boolean offering = questOffer != null && this.canOfferQuestsTo((EntityPlayer)entityplayer);
        final int color = (questOffer != null) ? questOffer.getQuestColor() : 0;
        final LOTRPacketNPCIsOfferingQuest packet = new LOTRPacketNPCIsOfferingQuest(this.theNPC.getEntityId(), offering, color);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    private void sendDataToAllWatchers() {
        final int x = MathHelper.floor_double(((Entity)this.theNPC).posX) >> 4;
        final int z = MathHelper.floor_double(((Entity)this.theNPC).posZ) >> 4;
        final PlayerManager playermanager = ((WorldServer)((Entity)this.theNPC).worldObj).getPlayerManager();
        final List players = ((Entity)this.theNPC).worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                this.sendData(entityplayer);
            }
        }
    }
    
    public void receiveData(final LOTRPacketNPCIsOfferingQuest packet) {
        this.clientIsOffering = packet.offering;
        this.clientOfferColor = packet.offerColor;
    }
    
    public void onDeath() {
        if (!((Entity)this.theNPC).worldObj.isClient && !this.activeQuestPlayers.isEmpty()) {
            for (final UUID player : this.activeQuestPlayers) {
                final List<LOTRMiniQuest> playerQuests = LOTRLevelData.getData(player).getMiniQuestsForEntity(this.theNPC, true);
                for (final LOTRMiniQuest quest : playerQuests) {
                    if (quest.isActive()) {
                        quest.setEntityDead();
                    }
                }
            }
        }
    }
}
