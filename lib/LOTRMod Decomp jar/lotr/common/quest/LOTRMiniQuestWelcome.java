// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import java.util.Iterator;
import lotr.common.LOTRAchievement;
import java.util.List;
import java.util.ArrayList;
import lotr.common.LOTRGreyWandererTracker;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.GameSettings;
import lotr.client.LOTRKeyHandler;
import net.minecraft.util.StatCollector;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityGandalf;
import lotr.common.LOTRPlayerData;

public class LOTRMiniQuestWelcome extends LOTRMiniQuest
{
    private static final String SPEECHBANK = "char/gandalf/quest";
    public int stage;
    public static final int STAGE_GET_ITEMS = 1;
    public static final int STAGE_READ_BOOK = 2;
    public static final int STAGE_EXPLAIN_BOOK = 3;
    public static final int STAGE_EXPLAIN_MAP = 4;
    public static final int STAGE_OPEN_MAP = 5;
    public static final int STAGE_EXPLAIN_FACTIONS = 6;
    public static final int STAGE_EXPLAIN_ALIGNMENT = 7;
    public static final int STAGE_CYCLE_ALIGNMENT = 8;
    public static final int STAGE_CYCLE_REGIONS = 9;
    public static final int STAGE_EXPLAIN_FACTION_GUIDE = 10;
    public static final int STAGE_OPEN_FACTIONS = 11;
    public static final int STAGE_TALK_ADVENTURES = 12;
    public static final int STAGE_GET_POUCHES = 13;
    public static final int STAGE_TALK_FINAL = 14;
    public static final int STAGE_COMPLETE = 15;
    public static final int NUM_STAGES = 15;
    private boolean movedOn;
    
    public LOTRMiniQuestWelcome(final LOTRPlayerData pd) {
        super(pd);
        this.stage = 0;
    }
    
    public LOTRMiniQuestWelcome(final LOTRPlayerData pd, final LOTREntityGandalf gandalf) {
        this(pd);
        this.setNPCInfo(gandalf);
        super.speechBankStart = "";
        super.speechBankProgress = "";
        super.speechBankComplete = "";
        super.speechBankTooMany = "";
        super.quoteStart = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 2);
        super.quoteComplete = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 12);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("WStage", (byte)this.stage);
        nbt.setBoolean("WMovedOn", this.movedOn);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.stage = nbt.getByte("WStage");
        this.movedOn = nbt.getBoolean("WMovedOn");
    }
    
    @Override
    public String getFactionSubtitle() {
        return "";
    }
    
    @Override
    public int getQuestColor() {
        return 10526880;
    }
    
    @Override
    public String getQuestObjective() {
        if (this.stage == 2) {
            return StatCollector.translateToLocal("lotr.miniquest.welcome.book");
        }
        if (this.stage == 5) {
            final KeyBinding keyMenu = LOTRKeyHandler.keyBindingMenu;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.map", new Object[] { GameSettings.getKeyDisplayString(keyMenu.getKeyCode()) });
        }
        if (this.stage == 8) {
            final KeyBinding keyLeft = LOTRKeyHandler.keyBindingAlignmentCycleLeft;
            final KeyBinding keyRight = LOTRKeyHandler.keyBindingAlignmentCycleRight;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.align", new Object[] { GameSettings.getKeyDisplayString(keyLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyRight.getKeyCode()) });
        }
        if (this.stage == 9) {
            final KeyBinding keyUp = LOTRKeyHandler.keyBindingAlignmentGroupPrev;
            final KeyBinding keyDown = LOTRKeyHandler.keyBindingAlignmentGroupNext;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.alignRegions", new Object[] { GameSettings.getKeyDisplayString(keyUp.getKeyCode()), GameSettings.getKeyDisplayString(keyDown.getKeyCode()) });
        }
        if (this.stage == 11) {
            final KeyBinding keyMenu = LOTRKeyHandler.keyBindingMenu;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.factions", new Object[] { GameSettings.getKeyDisplayString(keyMenu.getKeyCode()) });
        }
        return StatCollector.translateToLocal("lotr.miniquest.welcome.speak");
    }
    
    @Override
    public String getObjectiveInSpeech() {
        return "OBJECTIVE_SPEECH";
    }
    
    @Override
    public String getProgressedObjectiveInSpeech() {
        return "OBJECTIVE_SPEECH_PROGRESSED";
    }
    
    @Override
    public String getQuestProgress() {
        return this.getQuestProgressShorthand();
    }
    
    @Override
    public String getQuestProgressShorthand() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", new Object[] { this.stage, 15 });
    }
    
    @Override
    public float getCompletionFactor() {
        return this.stage / 15.0f;
    }
    
    @Override
    public ItemStack getQuestIcon() {
        return new ItemStack(LOTRMod.redBook);
    }
    
    @Override
    public boolean canPlayerAccept(final EntityPlayer entityplayer) {
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        return !pd.hasAnyGWQuest();
    }
    
    private void updateGreyWanderer() {
        LOTRGreyWandererTracker.setWandererActive(super.entityUUID);
    }
    
    @Override
    public void start(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        super.start(entityplayer, npc);
        final String line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 3);
        this.sendQuoteSpeech(entityplayer, npc, line);
        super.quotesStages.add(line);
        this.stage = 1;
        this.updateQuest();
        this.updateGreyWanderer();
    }
    
    @Override
    public void onInteract(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        this.updateGreyWanderer();
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if (this.stage == 1) {
            final List<ItemStack> dropItems = new ArrayList<ItemStack>();
            dropItems.add(new ItemStack(LOTRMod.redBook));
            npc.dropItemList(dropItems);
            final String line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 4);
            this.sendQuoteSpeech(entityplayer, npc, line);
            super.quotesStages.add(line);
            this.stage = 2;
            this.updateQuest();
        }
        else if (this.stage == 2) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 4);
            this.sendQuoteSpeech(entityplayer, npc, line2);
        }
        else if (this.stage == 3) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 5);
            this.sendQuoteSpeech(entityplayer, npc, line2);
            super.quotesStages.add(line2);
            this.stage = 4;
            this.updateQuest();
        }
        else if (this.stage == 4) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 6);
            this.sendQuoteSpeech(entityplayer, npc, line2);
            super.quotesStages.add(line2);
            this.stage = 5;
            this.updateQuest();
        }
        else if (this.stage == 5) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 6);
            this.sendQuoteSpeech(entityplayer, npc, line2);
        }
        else if (this.stage == 6) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 7);
            this.sendQuoteSpeech(entityplayer, npc, line2);
            super.quotesStages.add(line2);
            this.stage = 7;
            this.updateQuest();
        }
        else if (this.stage == 7) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 8);
            this.sendQuoteSpeech(entityplayer, npc, line2);
            super.quotesStages.add(line2);
            this.stage = 8;
            this.updateQuest();
        }
        else if (this.stage == 8) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 8);
            this.sendQuoteSpeech(entityplayer, npc, line2);
        }
        else if (this.stage == 9) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 8);
            this.sendQuoteSpeech(entityplayer, npc, line2);
        }
        else if (this.stage == 10) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 9);
            this.sendQuoteSpeech(entityplayer, npc, line2);
            super.quotesStages.add(line2);
            this.stage = 11;
            this.updateQuest();
        }
        else if (this.stage == 11) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 9);
            this.sendQuoteSpeech(entityplayer, npc, line2);
        }
        else if (this.stage == 12) {
            final String line2 = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 10);
            this.sendQuoteSpeech(entityplayer, npc, line2);
            super.quotesStages.add(line2);
            this.stage = 13;
            this.updateQuest();
        }
        else if (this.stage == 13) {
            final List<ItemStack> dropItems = new ArrayList<ItemStack>();
            if (!pd.getQuestData().getGivenFirstPouches()) {
                dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
                dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
                dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
                pd.getQuestData().setGivenFirstPouches(true);
            }
            npc.dropItemList(dropItems);
            final String line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 11);
            this.sendQuoteSpeech(entityplayer, npc, line);
            super.quotesStages.add(line);
            this.stage = 14;
            this.updateQuest();
        }
        else if (this.stage == 14) {
            this.stage = 15;
            this.updateQuest();
            this.complete(entityplayer, npc);
        }
    }
    
    @Override
    public void handleEvent(final LOTRMiniQuestEvent event) {
        if (event instanceof LOTRMiniQuestEvent.OpenRedBook && this.stage == 2) {
            this.stage = 3;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.ViewMap && this.stage == 5) {
            this.stage = 6;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.CycleAlignment && this.stage == 8) {
            this.stage = 9;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.CycleAlignmentRegion && this.stage == 9) {
            this.stage = 10;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.ViewFactions && this.stage == 11) {
            this.stage = 12;
            this.updateQuest();
            this.updateGreyWanderer();
        }
    }
    
    @Override
    protected void complete(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        super.complete(entityplayer, npc);
        this.updateGreyWanderer();
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.doGreyQuest);
    }
    
    @Override
    public void onPlayerTick(final EntityPlayer entityplayer) {
        if (!LOTRGreyWandererTracker.isWandererActive(super.entityUUID)) {
            this.movedOn = true;
            this.updateQuest();
        }
    }
    
    @Override
    public boolean isFailed() {
        return super.isFailed() || this.movedOn;
    }
    
    @Override
    public String getQuestFailure() {
        if (this.movedOn) {
            return StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.movedOn", new Object[] { super.entityName });
        }
        return super.getQuestFailure();
    }
    
    @Override
    public String getQuestFailureShorthand() {
        if (this.movedOn) {
            return StatCollector.translateToLocal("lotr.gui.redBook.mq.movedOn");
        }
        return super.getQuestFailureShorthand();
    }
    
    @Override
    public float getAlignmentBonus() {
        return 0.0f;
    }
    
    @Override
    public int getCoinBonus() {
        return 0;
    }
    
    public static boolean[] forceMenu_Map_Factions(final EntityPlayer entityplayer) {
        final boolean[] flags = { false, false };
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        final List<LOTRMiniQuest> activeQuests = (List<LOTRMiniQuest>)pd.getActiveMiniQuests();
        for (final LOTRMiniQuest quest : activeQuests) {
            if (quest instanceof LOTRMiniQuestWelcome) {
                final LOTRMiniQuestWelcome qw = (LOTRMiniQuestWelcome)quest;
                if (qw.stage == 5) {
                    flags[0] = true;
                    break;
                }
                if (qw.stage == 11) {
                    flags[1] = true;
                    break;
                }
                continue;
            }
        }
        return flags;
    }
}
