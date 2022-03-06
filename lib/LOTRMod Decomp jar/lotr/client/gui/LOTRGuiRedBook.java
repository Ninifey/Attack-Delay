// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import lotr.common.network.LOTRPacketMiniquestTrack;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketDeleteMiniquest;
import org.lwjgl.input.Mouse;
import net.minecraft.client.renderer.RenderHelper;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.client.LOTRTextBody;
import lotr.common.LOTRDate;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import java.util.HashMap;
import net.minecraft.client.gui.GuiButton;
import org.apache.commons.lang3.tuple.Pair;
import lotr.common.quest.LOTRMiniQuest;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiRedBook extends LOTRGuiScreenBase
{
    public static ResourceLocation guiTexture;
    public static ResourceLocation guiTexture_miniquests;
    private static RenderItem renderItem;
    public int xSize;
    public int ySize;
    private int guiLeft;
    private int guiTop;
    private int pageWidth;
    private int pageTop;
    private int pageBorder;
    private boolean wasMouseDown;
    private int lastMouseX;
    private int lastMouseY;
    private int scrollBarWidth;
    private int scrollBarHeight;
    private int scrollBarX;
    private int scrollBarY;
    private int scrollBarBorder;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    private boolean mouseInScrollBar;
    private boolean isScrolling;
    private float currentScroll;
    private Map<LOTRMiniQuest, Pair<Integer, Integer>> displayedMiniQuests;
    private int maxDisplayedMiniQuests;
    private int qPanelWidth;
    private int qPanelHeight;
    private int qPanelBorder;
    private int qDelX;
    private int qDelY;
    private int qTrackX;
    private int qTrackY;
    private int qWidgetSize;
    private int diaryWidth;
    private int diaryHeight;
    private int diaryX;
    private int diaryY;
    private int diaryBorder;
    private boolean mouseInDiary;
    private boolean isDiaryScrolling;
    private float diaryScroll;
    private static boolean viewCompleted;
    private LOTRMiniQuest selectedMiniquest;
    private LOTRMiniQuest deletingMiniquest;
    private int trackTicks;
    private static final int trackTicksMax = 40;
    private GuiButton buttonViewActive;
    private GuiButton buttonViewCompleted;
    private GuiButton buttonQuestDelete;
    private GuiButton buttonQuestDeleteCancel;
    public static final int textColor = 8019267;
    public static final int textColorDark = 5521198;
    public static final int textColorFaded = 9666921;
    public static final int textColorRed = 16711680;
    private static Page page;
    
    public LOTRGuiRedBook() {
        this.xSize = 420;
        this.ySize = 256;
        this.pageWidth = 186;
        this.pageTop = 18;
        this.pageBorder = 10;
        this.scrollBarWidth = 12;
        this.scrollBarHeight = 216;
        this.scrollBarX = this.xSize / 2 + this.pageWidth;
        this.scrollBarY = 18;
        this.scrollBarBorder = 1;
        this.scrollWidgetWidth = 10;
        this.scrollWidgetHeight = 17;
        this.mouseInScrollBar = false;
        this.isScrolling = false;
        this.currentScroll = 0.0f;
        this.displayedMiniQuests = new HashMap<LOTRMiniQuest, Pair<Integer, Integer>>();
        this.maxDisplayedMiniQuests = 4;
        this.qPanelWidth = 170;
        this.qPanelHeight = 45;
        this.qPanelBorder = 4;
        this.qDelX = 158;
        this.qDelY = 4;
        this.qTrackX = 148;
        this.qTrackY = 4;
        this.qWidgetSize = 8;
        this.diaryWidth = 170;
        this.diaryHeight = 218;
        this.diaryX = this.xSize / 2 - this.pageBorder - this.pageWidth / 2 - this.diaryWidth / 2;
        this.diaryY = this.ySize / 2 - this.diaryHeight / 2 - 1;
        this.diaryBorder = 6;
        this.mouseInDiary = false;
        this.isDiaryScrolling = false;
    }
    
    public void initGui() {
        if (LOTRGuiRedBook.page == null) {
            LOTRGuiRedBook.page = Page.values()[0];
        }
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        int buttonX = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
        int buttonY = this.guiTop + 80;
        super.buttonList.add(this.buttonViewActive = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, StatCollector.translateToLocal("lotr.gui.redBook.mq.viewActive")));
        super.buttonList.add(this.buttonViewCompleted = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, StatCollector.translateToLocal("lotr.gui.redBook.mq.viewComplete")));
        buttonX = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2;
        buttonY = this.guiTop + this.ySize - 60;
        super.buttonList.add(this.buttonQuestDelete = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, ""));
        super.buttonList.add(this.buttonQuestDeleteCancel = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, ""));
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.trackTicks > 0) {
            --this.trackTicks;
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.displayedMiniQuests.clear();
        this.setupScrollBar(i, j);
        this.drawDefaultBackground();
        super.mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512);
        int x = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
        int y = this.guiTop + 30;
        if (LOTRGuiRedBook.page == Page.MINIQUESTS && this.selectedMiniquest == null) {
            final float scale = 2.0f;
            final float invScale = 1.0f / scale;
            x *= (int)invScale;
            y *= (int)invScale;
            GL11.glScalef(scale, scale, scale);
            this.drawCenteredString(LOTRGuiRedBook.page.getTitle(), x, y, 8019267);
            GL11.glScalef(invScale, invScale, invScale);
            x = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
            y = this.guiTop + 50;
            if (LOTRGuiRedBook.viewCompleted) {
                this.drawCenteredString(StatCollector.translateToLocal("lotr.gui.redBook.mq.viewComplete"), x, y, 8019267);
            }
            else {
                this.drawCenteredString(StatCollector.translateToLocal("lotr.gui.redBook.mq.viewActive"), x, y, 8019267);
            }
        }
        if (LOTRGuiRedBook.page == Page.MINIQUESTS) {
            if (this.selectedMiniquest == null) {
                this.drawCenteredString(LOTRDate.ShireReckoning.getShireDate().getDateName(false), this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2, this.guiTop + this.ySize - 30, 8019267);
                this.drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.numActive", new Object[] { this.getPlayerData().getActiveMiniQuests().size() }), x, this.guiTop + 120, 8019267);
                this.drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.numComplete", new Object[] { this.getPlayerData().getCompletedMiniQuestsTotal() }), x, this.guiTop + 140, 8019267);
            }
            else {
                final LOTRMiniQuest quest = this.selectedMiniquest;
                super.mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture);
                final float[] questRGB = quest.getQuestColorComponents();
                GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
                x = this.guiLeft + this.diaryX;
                y = this.guiTop + this.diaryY;
                this.drawTexturedModalRect(x, y, 0, 256, this.diaryWidth, this.diaryHeight, 512);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final int textW = this.diaryWidth - this.diaryBorder * 2;
                final int textBottom = y + this.diaryHeight - this.diaryBorder;
                x += this.diaryBorder;
                y += this.diaryBorder;
                final boolean completed = quest.isCompleted();
                final boolean failed = !completed && quest.isFailed();
                final String entityName = quest.entityName;
                final String factionName = quest.getFactionSubtitle();
                final LOTRTextBody pageText = new LOTRTextBody(8019267);
                pageText.setTextWidth(textW);
                String[] dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateGiven).getDayAndYearNames(false);
                pageText.add(dayYear[0]);
                pageText.add(dayYear[1]);
                if (quest.biomeGiven != null) {
                    pageText.add(quest.biomeGiven.getBiomeDisplayName());
                }
                pageText.add("");
                String startQuote = LOTRSpeech.formatSpeech(quest.quoteStart, (EntityPlayer)super.mc.thePlayer, null, quest.getObjectiveInSpeech());
                startQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", new Object[] { startQuote });
                pageText.add(startQuote);
                pageText.add("");
                final List<String> quotesStages = quest.quotesStages;
                if (!quotesStages.isEmpty()) {
                    for (final String s : quotesStages) {
                        String stageQuote = LOTRSpeech.formatSpeech(s, (EntityPlayer)super.mc.thePlayer, null, quest.getObjectiveInSpeech());
                        stageQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", new Object[] { stageQuote });
                        pageText.add(stageQuote);
                        pageText.add("");
                    }
                }
                final String asked = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.asked", new Object[] { entityName, quest.getQuestObjective() });
                pageText.add(asked);
                pageText.add("");
                final String progress = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.progress", new Object[] { quest.getQuestProgress() });
                pageText.add(progress);
                if (quest.willHire) {
                    pageText.add("");
                    final String willHire = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.willHire", new Object[] { entityName });
                    pageText.add(willHire);
                }
                if (failed) {
                    for (int l = 0; l < pageText.size(); ++l) {
                        String line = pageText.getText(l);
                        line = EnumChatFormatting.STRIKETHROUGH + line;
                        pageText.set(l, line);
                    }
                    final String failureText = quest.getQuestFailure();
                    pageText.add(failureText, 16711680);
                }
                if (completed) {
                    pageText.add("");
                    pageText.addLinebreak();
                    pageText.add("");
                    dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateCompleted).getDayAndYearNames(false);
                    pageText.add(dayYear[0]);
                    pageText.add(dayYear[1]);
                    pageText.add("");
                    String completeQuote = LOTRSpeech.formatSpeech(quest.quoteComplete, (EntityPlayer)super.mc.thePlayer, null, quest.getObjectiveInSpeech());
                    completeQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", new Object[] { completeQuote });
                    pageText.add(completeQuote);
                    pageText.add("");
                    final String completedText = StatCollector.translateToLocal("lotr.gui.redBook.mq.diary.complete");
                    pageText.add(completedText);
                    if (quest.anyRewardsGiven()) {
                        pageText.add("");
                        final String rewardText = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward", new Object[] { entityName });
                        pageText.add(rewardText);
                        if (quest.alignmentRewarded != 0.0f) {
                            final String alignS = LOTRAlignmentValues.formatAlignForDisplay(quest.alignmentRewarded);
                            final String rewardAlign = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.align", new Object[] { alignS, factionName });
                            pageText.add(rewardAlign);
                        }
                        if (quest.coinsRewarded != 0.0f) {
                            final String rewardCoins = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.coins", new Object[] { quest.coinsRewarded });
                            pageText.add(rewardCoins);
                        }
                        if (!quest.itemsRewarded.isEmpty()) {
                            for (final ItemStack item : quest.itemsRewarded) {
                                String rewardItem;
                                if (item.getItem() instanceof ItemEditableBook) {
                                    rewardItem = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.book", new Object[] { item.getDisplayName() });
                                }
                                else {
                                    rewardItem = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.item", new Object[] { item.getDisplayName(), item.stackSize });
                                }
                                pageText.add(rewardItem);
                            }
                        }
                    }
                    if (quest.wasHired) {
                        pageText.add("");
                        final String rewardHired = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.hired", new Object[] { entityName });
                        pageText.add(rewardHired);
                    }
                }
                this.diaryScroll = pageText.renderAndReturnScroll(super.fontRendererObj, x, y, textBottom, this.diaryScroll);
            }
            if (this.deletingMiniquest == null) {
                List<LOTRMiniQuest> miniquests = this.getMiniQuests();
                miniquests = new ArrayList<LOTRMiniQuest>(miniquests);
                if (!miniquests.isEmpty()) {
                    if (LOTRGuiRedBook.viewCompleted) {
                        miniquests = (List<LOTRMiniQuest>)Lists.reverse((List)miniquests);
                    }
                    else {
                        Collections.sort(miniquests, new LOTRMiniQuest.SorterAlphabetical());
                    }
                    final int size = miniquests.size();
                    int min = 0 + Math.round(this.currentScroll * (size - this.maxDisplayedMiniQuests));
                    int max = this.maxDisplayedMiniQuests - 1 + Math.round(this.currentScroll * (size - this.maxDisplayedMiniQuests));
                    min = Math.max(min, 0);
                    max = Math.min(max, size - 1);
                    for (int index = min; index <= max; ++index) {
                        final LOTRMiniQuest quest2 = miniquests.get(index);
                        final int displayIndex = index - min;
                        final int questX = this.guiLeft + this.xSize / 2 + this.pageBorder;
                        final int questY = this.guiTop + this.pageTop + displayIndex * (4 + this.qPanelHeight);
                        this.renderMiniQuestPanel(quest2, questX, questY, i, j);
                        this.displayedMiniQuests.put(quest2, (Pair<Integer, Integer>)Pair.of((Object)questX, (Object)questY));
                    }
                }
            }
            else {
                String deleteText;
                if (LOTRGuiRedBook.viewCompleted) {
                    deleteText = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmp");
                }
                else {
                    deleteText = StatCollector.translateToLocal("lotr.gui.redBook.mq.delete");
                }
                final List deleteTextLines = super.fontRendererObj.listFormattedStringToWidth(deleteText, this.pageWidth);
                final int lineX = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2;
                int lineY = this.guiTop + 50;
                for (final Object obj : deleteTextLines) {
                    final String line2 = (String)obj;
                    this.drawCenteredString(line2, lineX, lineY, 8019267);
                    lineY += super.fontRendererObj.FONT_HEIGHT;
                }
                final int questX2 = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2 - this.qPanelWidth / 2;
                final int questY2 = this.guiTop + this.pageTop + 80;
                this.renderMiniQuestPanel(this.deletingMiniquest, questX2, questY2, i, j);
            }
        }
        if (this.hasScrollBar()) {
            super.mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture_miniquests);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 244, 0, this.scrollBarWidth, this.scrollBarHeight);
            if (this.canScroll()) {
                final int scroll = (int)(this.currentScroll * (this.scrollBarHeight - this.scrollBarBorder * 2 - this.scrollWidgetHeight));
                this.drawTexturedModalRect(this.guiLeft + this.scrollBarX + this.scrollBarBorder, this.guiTop + this.scrollBarY + this.scrollBarBorder + scroll, 224, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
            }
            else {
                this.drawTexturedModalRect(this.guiLeft + this.scrollBarX + this.scrollBarBorder, this.guiTop + this.scrollBarY + this.scrollBarBorder, 234, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
            }
        }
        final boolean hasQuestViewButtons = LOTRGuiRedBook.page == Page.MINIQUESTS && this.selectedMiniquest == null;
        final GuiButton buttonViewActive = this.buttonViewActive;
        final GuiButton buttonViewActive2 = this.buttonViewActive;
        final boolean b = hasQuestViewButtons;
        buttonViewActive2.field_146125_m = b;
        buttonViewActive.enabled = b;
        final GuiButton buttonViewCompleted = this.buttonViewCompleted;
        final GuiButton buttonViewCompleted2 = this.buttonViewCompleted;
        final boolean b2 = hasQuestViewButtons;
        buttonViewCompleted2.field_146125_m = b2;
        buttonViewCompleted.enabled = b2;
        final boolean hasQuestDeleteButtons = LOTRGuiRedBook.page == Page.MINIQUESTS && this.deletingMiniquest != null;
        final GuiButton buttonQuestDelete = this.buttonQuestDelete;
        final GuiButton buttonQuestDelete2 = this.buttonQuestDelete;
        final boolean b3 = hasQuestDeleteButtons;
        buttonQuestDelete2.field_146125_m = b3;
        buttonQuestDelete.enabled = b3;
        final GuiButton buttonQuestDeleteCancel = this.buttonQuestDeleteCancel;
        final GuiButton buttonQuestDeleteCancel2 = this.buttonQuestDeleteCancel;
        final boolean b4 = hasQuestDeleteButtons;
        buttonQuestDeleteCancel2.field_146125_m = b4;
        buttonQuestDeleteCancel.enabled = b4;
        if (LOTRGuiRedBook.viewCompleted) {
            this.buttonQuestDelete.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmpYes");
            this.buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmpNo");
        }
        else {
            this.buttonQuestDelete.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteYes");
            this.buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteNo");
        }
        super.drawScreen(i, j, f);
    }
    
    private void renderMiniQuestPanel(final LOTRMiniQuest quest, final int questX, final int questY, final int mouseX, final int mouseY) {
        GL11.glPushMatrix();
        final boolean mouseInPanel = mouseX >= questX && mouseX < questX + this.qPanelWidth && mouseY >= questY && mouseY < questY + this.qPanelHeight;
        final boolean mouseInDelete = mouseX >= questX + this.qDelX && mouseX < questX + this.qDelX + this.qWidgetSize && mouseY >= questY + this.qDelY && mouseY < questY + this.qDelY + this.qWidgetSize;
        final boolean mouseInTrack = mouseX >= questX + this.qTrackX && mouseX < questX + this.qTrackX + this.qWidgetSize && mouseY >= questY + this.qTrackY && mouseY < questY + this.qTrackY + this.qWidgetSize;
        final boolean isTracking = quest == this.getPlayerData().getTrackingMiniQuest();
        super.mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture_miniquests);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (mouseInPanel || quest == this.selectedMiniquest) {
            this.drawTexturedModalRect(questX, questY, 0, this.qPanelHeight, this.qPanelWidth, this.qPanelHeight);
        }
        else {
            this.drawTexturedModalRect(questX, questY, 0, 0, this.qPanelWidth, this.qPanelHeight);
        }
        final float[] questRGB = quest.getQuestColorComponents();
        GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
        GL11.glEnable(3008);
        this.drawTexturedModalRect(questX, questY, 0, this.qPanelHeight * 2, this.qPanelWidth, this.qPanelHeight);
        GL11.glDisable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String questName = quest.entityName;
        String factionName = quest.getFactionSubtitle();
        if (quest.isFailed()) {
            questName = EnumChatFormatting.STRIKETHROUGH + questName;
            factionName = EnumChatFormatting.STRIKETHROUGH + factionName;
        }
        super.fontRendererObj.drawString(questName, questX + this.qPanelBorder, questY + this.qPanelBorder, 8019267);
        super.fontRendererObj.drawString(factionName, questX + this.qPanelBorder, questY + this.qPanelBorder + super.fontRendererObj.FONT_HEIGHT, 8019267);
        if (quest.isFailed()) {
            super.fontRendererObj.drawString(quest.getQuestFailureShorthand(), questX + this.qPanelBorder, questY + 25, 16711680);
        }
        else if (isTracking && this.trackTicks > 0) {
            super.fontRendererObj.drawString(StatCollector.translateToLocal("lotr.gui.redBook.mq.tracking"), questX + this.qPanelBorder, questY + 25, 8019267);
        }
        else {
            String objective = quest.getQuestObjective();
            final int maxObjLength = this.qPanelWidth - this.qPanelBorder * 2 - 18;
            if (super.fontRendererObj.getStringWidth(objective) >= maxObjLength) {
                final String ellipsis = "...";
                while (super.fontRendererObj.getStringWidth(objective + ellipsis) >= maxObjLength) {
                    for (objective = objective.substring(0, objective.length() - 1); Character.isWhitespace(objective.charAt(objective.length() - 1)); objective = objective.substring(0, objective.length() - 1)) {}
                }
                objective += ellipsis;
            }
            super.fontRendererObj.drawString(objective, questX + this.qPanelBorder, questY + 25, 8019267);
            String progress = quest.getQuestProgress();
            if (quest.isCompleted()) {
                progress = StatCollector.translateToLocal("lotr.gui.redBook.mq.complete");
            }
            super.fontRendererObj.drawString(progress, questX + this.qPanelBorder, questY + 25 + super.fontRendererObj.FONT_HEIGHT, 8019267);
        }
        if (this.deletingMiniquest == null) {
            super.mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture_miniquests);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final int delU = this.qPanelWidth;
            int delV = 0;
            if (mouseInDelete) {
                delV += this.qWidgetSize;
            }
            this.drawTexturedModalRect(questX + this.qDelX, questY + this.qDelY, delU, delV, this.qWidgetSize, this.qWidgetSize);
            if (!LOTRGuiRedBook.viewCompleted) {
                int trackU = this.qPanelWidth + this.qWidgetSize;
                int trackV = 0;
                if (mouseInTrack) {
                    trackV += this.qWidgetSize;
                }
                if (isTracking) {
                    trackU += this.qWidgetSize;
                }
                this.drawTexturedModalRect(questX + this.qTrackX, questY + this.qTrackY, trackU, trackV, this.qWidgetSize, this.qWidgetSize);
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        LOTRGuiRedBook.renderItem.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.getTextureManager(), quest.getQuestIcon(), questX + 149, questY + 24);
        GL11.glDisable(2896);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void setupScrollBar(final int i, final int j) {
        final boolean isMouseDown = Mouse.isButtonDown(0);
        final int i2 = i - this.guiLeft;
        final int j2 = j - this.guiTop;
        if (this.selectedMiniquest != null) {
            this.mouseInDiary = (i2 >= this.diaryX && i2 < this.diaryX + this.diaryWidth && j2 >= this.diaryY && j2 < this.diaryY + this.diaryHeight);
        }
        else {
            this.mouseInDiary = false;
        }
        this.mouseInScrollBar = (i2 >= this.scrollBarX && i2 < this.scrollBarX + this.scrollBarWidth && j2 >= this.scrollBarY && j2 < this.scrollBarY + this.scrollBarHeight);
        if (!this.wasMouseDown && isMouseDown) {
            if (this.mouseInScrollBar) {
                this.isScrolling = this.canScroll();
            }
            else if (this.mouseInDiary) {
                this.isDiaryScrolling = true;
            }
        }
        if (!isMouseDown) {
            this.isScrolling = false;
            this.isDiaryScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - (this.guiTop + this.scrollBarY) - this.scrollWidgetHeight / 2.0f) / (this.scrollBarHeight - (float)this.scrollWidgetHeight);
            this.currentScroll = Math.max(this.currentScroll, 0.0f);
            this.currentScroll = Math.min(this.currentScroll, 1.0f);
        }
        else if (this.isDiaryScrolling) {
            final float d = (this.lastMouseY - j) / (float)super.fontRendererObj.FONT_HEIGHT;
            this.diaryScroll -= d;
        }
        this.lastMouseX = i;
        this.lastMouseY = j;
    }
    
    private boolean hasScrollBar() {
        return LOTRGuiRedBook.page == Page.MINIQUESTS && this.deletingMiniquest == null;
    }
    
    private boolean canScroll() {
        return this.hasScrollBar() && this.getMiniQuests().size() > this.maxDisplayedMiniQuests;
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonViewActive) {
                LOTRGuiRedBook.viewCompleted = false;
            }
            if (button == this.buttonViewCompleted) {
                LOTRGuiRedBook.viewCompleted = true;
            }
            if (button == this.buttonQuestDelete && this.deletingMiniquest != null) {
                final LOTRPacketDeleteMiniquest packet = new LOTRPacketDeleteMiniquest(this.deletingMiniquest);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                this.deletingMiniquest = null;
                this.selectedMiniquest = null;
                this.diaryScroll = 0.0f;
            }
            if (button == this.buttonQuestDeleteCancel && this.deletingMiniquest != null) {
                this.deletingMiniquest = null;
            }
        }
    }
    
    protected void mouseClicked(final int i, final int j, final int mouse) {
        if (mouse == 0) {
            if (LOTRGuiRedBook.page == Page.MINIQUESTS && this.deletingMiniquest == null) {
                for (final Map.Entry<LOTRMiniQuest, Pair<Integer, Integer>> entry : this.displayedMiniQuests.entrySet()) {
                    final LOTRMiniQuest quest = entry.getKey();
                    final int questX = (int)entry.getValue().getLeft();
                    final int questY = (int)entry.getValue().getRight();
                    int i2 = questX + this.qDelX;
                    int j2 = questY + this.qDelY;
                    int i3 = i2 + this.qWidgetSize;
                    int j3 = j2 + this.qWidgetSize;
                    if (i >= i2 && j >= j2 && i < i3 && j < j3) {
                        this.deletingMiniquest = quest;
                        this.selectedMiniquest = this.deletingMiniquest;
                        this.diaryScroll = 0.0f;
                        return;
                    }
                    if (LOTRGuiRedBook.viewCompleted) {
                        continue;
                    }
                    i2 = questX + this.qTrackX;
                    j2 = questY + this.qTrackY;
                    i3 = i2 + this.qWidgetSize;
                    j3 = j2 + this.qWidgetSize;
                    if (i >= i2 && j >= j2 && i < i3 && j < j3) {
                        this.trackOrUntrack(quest);
                        return;
                    }
                }
            }
            if (LOTRGuiRedBook.page == Page.MINIQUESTS && this.deletingMiniquest == null) {
                for (final Map.Entry<LOTRMiniQuest, Pair<Integer, Integer>> entry : this.displayedMiniQuests.entrySet()) {
                    final LOTRMiniQuest quest = entry.getKey();
                    final int questX = (int)entry.getValue().getLeft();
                    final int questY = (int)entry.getValue().getRight();
                    final int i2 = questX;
                    final int j2 = questY;
                    final int i3 = i2 + this.qPanelWidth;
                    final int j3 = j2 + this.qPanelHeight;
                    if (i >= i2 && j >= j2 && i < i3 && j < j3) {
                        this.selectedMiniquest = quest;
                        this.diaryScroll = 0.0f;
                        return;
                    }
                }
                if (!this.mouseInDiary && !this.isScrolling) {
                    this.selectedMiniquest = null;
                    this.diaryScroll = 0.0f;
                }
            }
        }
        super.mouseClicked(i, j, mouse);
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode()) {
            if (this.deletingMiniquest != null) {
                this.deletingMiniquest = null;
                return;
            }
            if (this.selectedMiniquest != null) {
                this.selectedMiniquest = null;
                return;
            }
        }
        super.keyTyped(c, i);
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0 && (this.canScroll() || this.mouseInDiary)) {
            if (i > 0) {
                i = 1;
            }
            if (i < 0) {
                i = -1;
            }
            if (this.mouseInDiary) {
                this.diaryScroll += i;
            }
            else {
                final int j = this.getMiniQuests().size() - this.maxDisplayedMiniQuests;
                this.currentScroll -= i / (float)j;
                this.currentScroll = Math.max(this.currentScroll, 0.0f);
                this.currentScroll = Math.min(this.currentScroll, 1.0f);
            }
        }
    }
    
    private LOTRPlayerData getPlayerData() {
        return LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
    }
    
    private List<LOTRMiniQuest> getMiniQuests() {
        if (LOTRGuiRedBook.viewCompleted) {
            return this.getPlayerData().getMiniQuestsCompleted();
        }
        return this.getPlayerData().getMiniQuests();
    }
    
    private void trackOrUntrack(final LOTRMiniQuest quest) {
        final LOTRMiniQuest tracking = this.getPlayerData().getTrackingMiniQuest();
        LOTRMiniQuest newTracking = null;
        if (quest == tracking) {
            newTracking = null;
        }
        else {
            newTracking = quest;
        }
        final LOTRPacketMiniquestTrack packet = new LOTRPacketMiniquestTrack(newTracking);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        this.getPlayerData().setTrackingMiniQuest(newTracking);
        this.trackTicks = 40;
    }
    
    static {
        LOTRGuiRedBook.guiTexture = new ResourceLocation("lotr:gui/quest/redBook.png");
        LOTRGuiRedBook.guiTexture_miniquests = new ResourceLocation("lotr:gui/quest/redBook_miniquests.png");
        LOTRGuiRedBook.renderItem = new RenderItem();
        LOTRGuiRedBook.viewCompleted = false;
    }
    
    private enum Page
    {
        MINIQUESTS("miniquests");
        
        private String name;
        
        private Page(final String s) {
            this.name = s;
        }
        
        public String getTitle() {
            return StatCollector.translateToLocal("lotr.gui.redBook.page." + this.name);
        }
    }
}
