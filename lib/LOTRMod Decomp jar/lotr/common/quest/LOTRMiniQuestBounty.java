// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import org.apache.commons.lang3.StringUtils;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import java.util.Random;
import java.util.ArrayList;
import lotr.common.LOTRAchievement;
import net.minecraft.util.IChatComponent;
import lotr.common.fac.LOTRFaction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFactionBounties;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRPlayerData;
import java.util.UUID;

public class LOTRMiniQuestBounty extends LOTRMiniQuest
{
    private static final int KILL_THRESHOLD = 25;
    private static final float MIN_ALIGNMENT_LOWERING = 100.0f;
    public UUID targetID;
    public String targetName;
    public boolean killed;
    public float alignmentBonus;
    public int coinBonus;
    private boolean bountyClaimedByOther;
    private boolean killedByBounty;
    
    public LOTRMiniQuestBounty(final LOTRPlayerData pd) {
        super(pd);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (this.targetID != null) {
            nbt.setString("TargetID", this.targetID.toString());
        }
        if (this.targetName != null) {
            nbt.setString("TargetName", this.targetName);
        }
        nbt.setBoolean("Killed", this.killed);
        nbt.setFloat("AlignF", this.alignmentBonus);
        nbt.setInteger("Coins", this.coinBonus);
        nbt.setBoolean("BountyClaimed", this.bountyClaimedByOther);
        nbt.setBoolean("KilledBy", this.killedByBounty);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("TargetID")) {
            final String s = nbt.getString("TargetID");
            this.targetID = UUID.fromString(s);
        }
        if (nbt.hasKey("TargetName")) {
            this.targetName = nbt.getString("TargetName");
        }
        this.killed = nbt.getBoolean("Killed");
        if (nbt.hasKey("Alignment")) {
            this.alignmentBonus = (float)nbt.getInteger("Alignment");
        }
        else {
            this.alignmentBonus = nbt.getFloat("AlignF");
        }
        this.coinBonus = nbt.getInteger("Coins");
        this.bountyClaimedByOther = nbt.getBoolean("BountyClaimed");
        this.killedByBounty = nbt.getBoolean("KilledBy");
    }
    
    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.targetID != null;
    }
    
    @Override
    public boolean canPlayerAccept(final EntityPlayer entityplayer) {
        if (super.canPlayerAccept(entityplayer) && !this.targetID.equals(entityplayer.getUniqueID()) && LOTRLevelData.getData(entityplayer).getAlignment(super.entityFaction) >= 100.0f) {
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            final List<LOTRMiniQuest> active = (List<LOTRMiniQuest>)pd.getActiveMiniQuests();
            for (final LOTRMiniQuest quest : active) {
                if (quest instanceof LOTRMiniQuestBounty && ((LOTRMiniQuestBounty)quest).targetID.equals(this.targetID)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getQuestObjective() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.bounty", new Object[] { this.targetName });
    }
    
    @Override
    public String getObjectiveInSpeech() {
        return this.targetName;
    }
    
    @Override
    public String getProgressedObjectiveInSpeech() {
        return this.targetName;
    }
    
    @Override
    public String getQuestProgress() {
        if (this.killed) {
            return StatCollector.translateToLocal("lotr.miniquest.bounty.progress.slain");
        }
        return StatCollector.translateToLocal("lotr.miniquest.bounty.progress.notSlain");
    }
    
    @Override
    public String getQuestProgressShorthand() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", new Object[] { this.killed ? 1 : 0, 1 });
    }
    
    @Override
    public float getCompletionFactor() {
        return this.killed ? 1.0f : 0.0f;
    }
    
    @Override
    public ItemStack getQuestIcon() {
        return new ItemStack(Items.iron_sword);
    }
    
    @Override
    public void onInteract(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        if (this.killed) {
            this.complete(entityplayer, npc);
        }
        else {
            this.sendProgressSpeechbank(entityplayer, npc);
        }
    }
    
    @Override
    public void onKill(final EntityPlayer entityplayer, final EntityLivingBase entity) {
        if (!this.killed && !this.isFailed() && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().equals(this.targetID)) {
            final EntityPlayer slainPlayer = (EntityPlayer)entity;
            final LOTRPlayerData slainPlayerData = LOTRLevelData.getData(slainPlayer);
            this.killed = true;
            LOTRFactionBounties.forFaction(super.entityFaction).forPlayer(slainPlayer).recordBountyKilled();
            this.updateQuest();
            final LOTRFaction highestFaction = this.getPledgeOrHighestAlignmentFaction(slainPlayer, 100.0f);
            if (highestFaction != null) {
                final float curAlignment = slainPlayerData.getAlignment(highestFaction);
                float alignmentLoss = this.getKilledAlignmentPenalty();
                if (curAlignment + alignmentLoss < 100.0f) {
                    alignmentLoss = -(curAlignment - 100.0f);
                }
                final LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(alignmentLoss, "lotr.alignment.bountyKill");
                slainPlayerData.addAlignment(slainPlayer, source, highestFaction, (Entity)entityplayer);
                final IChatComponent slainMsg1 = (IChatComponent)new ChatComponentTranslation("chat.lotr.bountyKilled1", new Object[] { entityplayer.getCommandSenderName(), super.entityFaction.factionName() });
                final IChatComponent slainMsg2 = (IChatComponent)new ChatComponentTranslation("chat.lotr.bountyKilled2", new Object[] { highestFaction.factionName() });
                slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                slainPlayer.addChatMessage(slainMsg1);
                slainPlayer.addChatMessage(slainMsg2);
            }
            final IChatComponent announceMsg = (IChatComponent)new ChatComponentTranslation("chat.lotr.bountyKill", new Object[] { entityplayer.getCommandSenderName(), slainPlayer.getCommandSenderName(), super.entityFaction.factionName() });
            announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            for (final Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                final EntityPlayer otherPlayer = (EntityPlayer)obj;
                if (otherPlayer != slainPlayer) {
                    otherPlayer.addChatMessage(announceMsg);
                }
            }
        }
    }
    
    @Override
    public void onKilledByPlayer(final EntityPlayer entityplayer, final EntityPlayer killer) {
        if (!this.killed && !this.isFailed() && killer.getUniqueID().equals(this.targetID)) {
            final LOTRPlayerData killerData = LOTRLevelData.getData(killer);
            final LOTRFaction killerHighestFaction = this.getPledgeOrHighestAlignmentFaction(killer, 0.0f);
            if (killerHighestFaction != null) {
                final float killerBonus = this.getAlignmentBonus();
                final LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(killerBonus, "lotr.alignment.killedHunter");
                killerData.addAlignment(killer, source, killerHighestFaction, (Entity)entityplayer);
            }
            final LOTRPlayerData pd = this.getPlayerData();
            final float curAlignment = pd.getAlignment(super.entityFaction);
            if (curAlignment > 100.0f) {
                float alignmentLoss = this.getKilledAlignmentPenalty();
                if (curAlignment + alignmentLoss < 100.0f) {
                    alignmentLoss = -(curAlignment - 100.0f);
                }
                final LOTRAlignmentValues.AlignmentBonus source2 = new LOTRAlignmentValues.AlignmentBonus(alignmentLoss, "lotr.alignment.killedByBounty");
                pd.addAlignment(entityplayer, source2, super.entityFaction, (Entity)killer);
                final IChatComponent slainMsg1 = (IChatComponent)new ChatComponentTranslation("chat.lotr.killedByBounty1", new Object[] { killer.getCommandSenderName() });
                final IChatComponent slainMsg2 = (IChatComponent)new ChatComponentTranslation("chat.lotr.killedByBounty2", new Object[] { super.entityFaction.factionName() });
                slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                entityplayer.addChatMessage(slainMsg1);
                entityplayer.addChatMessage(slainMsg2);
            }
            this.killedByBounty = true;
            this.updateQuest();
            killerData.addAchievement(LOTRAchievement.killHuntingPlayer);
            final IChatComponent announceMsg = (IChatComponent)new ChatComponentTranslation("chat.lotr.killedByBounty", new Object[] { entityplayer.getCommandSenderName(), killer.getCommandSenderName() });
            announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            for (final Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                final EntityPlayer otherPlayer = (EntityPlayer)obj;
                if (otherPlayer != entityplayer) {
                    otherPlayer.addChatMessage(announceMsg);
                }
            }
        }
    }
    
    private LOTRFaction getPledgeOrHighestAlignmentFaction(final EntityPlayer entityplayer, final float min) {
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if (pd.getPledgeFaction() != null) {
            return pd.getPledgeFaction();
        }
        final List<LOTRFaction> highestFactions = new ArrayList<LOTRFaction>();
        float highestAlignment = min;
        for (final LOTRFaction f : LOTRFaction.getPlayableAlignmentFactions()) {
            final float alignment = pd.getAlignment(f);
            if (alignment > min) {
                if (alignment > highestAlignment) {
                    highestFactions.clear();
                    highestFactions.add(f);
                    highestAlignment = alignment;
                }
                else {
                    if (alignment != highestAlignment) {
                        continue;
                    }
                    highestFactions.add(f);
                }
            }
        }
        if (!highestFactions.isEmpty()) {
            final Random rand = entityplayer.getRNG();
            final LOTRFaction highestFaction = highestFactions.get(rand.nextInt(highestFactions.size()));
            return highestFaction;
        }
        return null;
    }
    
    @Override
    public void onPlayerTick(final EntityPlayer entityplayer) {
        super.onPlayerTick(entityplayer);
        if (this.isActive() && !this.killed && !this.bountyClaimedByOther && LOTRFactionBounties.forFaction(super.entityFaction).forPlayer(this.targetID).recentlyBountyKilled()) {
            this.bountyClaimedByOther = true;
            this.updateQuest();
        }
    }
    
    @Override
    public boolean isFailed() {
        return super.isFailed() || this.bountyClaimedByOther || this.killedByBounty;
    }
    
    @Override
    public String getQuestFailure() {
        if (this.killedByBounty) {
            return StatCollector.translateToLocalFormatted("lotr.miniquest.bounty.killedBy", new Object[] { this.targetName });
        }
        if (this.bountyClaimedByOther) {
            return StatCollector.translateToLocalFormatted("lotr.miniquest.bounty.claimed", new Object[] { this.targetName });
        }
        return super.getQuestFailure();
    }
    
    @Override
    public String getQuestFailureShorthand() {
        if (this.killedByBounty) {
            return StatCollector.translateToLocal("lotr.miniquest.bounty.killedBy.short");
        }
        if (this.bountyClaimedByOther) {
            return StatCollector.translateToLocal("lotr.miniquest.bounty.claimed.short");
        }
        return super.getQuestFailureShorthand();
    }
    
    @Override
    public void start(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        super.start(entityplayer, npc);
        LOTRLevelData.getData(this.targetID).placeBountyFor(npc.getFaction());
    }
    
    @Override
    protected void complete(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        final LOTRPlayerData pd = this.getPlayerData();
        pd.addCompletedBountyQuest();
        final int bComplete = pd.getCompletedBountyQuests();
        final boolean specialReward = bComplete > 0 && bComplete % 5 == 0;
        if (specialReward) {
            final ItemStack trophy = new ItemStack(LOTRMod.bountyTrophy);
            super.rewardItemTable.add(trophy);
        }
        super.complete(entityplayer, npc);
        pd.addAchievement(LOTRAchievement.doMiniquestHunter);
        if (specialReward) {
            pd.addAchievement(LOTRAchievement.doMiniquestHunter5);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return this.alignmentBonus;
    }
    
    public float getKilledAlignmentPenalty() {
        return -this.getAlignmentBonus() * 2.0f;
    }
    
    @Override
    public int getCoinBonus() {
        return this.coinBonus;
    }
    
    @Override
    protected boolean shouldRandomiseCoinReward() {
        return false;
    }
    
    public static class QFBounty<Q extends LOTRMiniQuestBounty> extends QuestFactoryBase<Q>
    {
        public QFBounty(final String name) {
            super(name);
        }
        
        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestBounty.class;
        }
        
        @Override
        public Q createQuest(final LOTREntityNPC npc, final Random rand) {
            if (!LOTRConfig.allowBountyQuests) {
                return null;
            }
            final Q quest = super.createQuest(npc, rand);
            final LOTRFaction faction = npc.getFaction();
            final LOTRFactionBounties bounties = LOTRFactionBounties.forFaction(faction);
            final List<LOTRFactionBounties.PlayerData> players = bounties.findBountyTargets(25);
            if (players.isEmpty()) {
                return null;
            }
            final LOTRFactionBounties.PlayerData targetData = players.get(rand.nextInt(players.size()));
            final int kills = targetData.getNumKills();
            final float f = (float)kills;
            int alignment = (int)f;
            alignment = MathHelper.clamp_int(alignment, 1, 50);
            int coins = (int)(f * 10.0f * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
            coins = MathHelper.clamp_int(coins, 1, 1000);
            quest.targetID = targetData.playerID;
            String username = targetData.findUsername();
            if (StringUtils.isBlank((CharSequence)username)) {
                username = quest.targetID.toString();
            }
            quest.targetName = username;
            quest.alignmentBonus = (float)alignment;
            quest.coinBonus = coins;
            return quest;
        }
    }
    
    public enum BountyHelp
    {
        BIOME("biome"), 
        WAYPOINT("wp");
        
        public final String speechName;
        
        private BountyHelp(final String s) {
            this.speechName = s;
        }
        
        public static BountyHelp getRandomHelpType(final Random random) {
            return values()[random.nextInt(values().length)];
        }
    }
}
