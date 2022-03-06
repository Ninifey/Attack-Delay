// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import lotr.common.network.LOTRPacketFellowshipToggle;
import lotr.common.network.LOTRPacketFellowshipRename;
import lotr.common.network.LOTRPacketFellowshipSetIcon;
import lotr.common.network.LOTRPacketFellowshipLeave;
import lotr.common.network.LOTRPacketFellowshipDisband;
import lotr.common.network.LOTRPacketFellowshipDoPlayer;
import lotr.common.network.LOTRPacketFellowshipCreate;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketFellowshipRespondInvite;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderHelper;
import lotr.common.LOTRTitle;
import lotr.common.LOTRPlayerData;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.util.StatCollector;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import lotr.common.fellowship.LOTRFellowshipClient;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiFellowships extends LOTRGuiMenuBase
{
    public static final ResourceLocation iconsTextures;
    private Page page;
    private List<LOTRFellowshipClient> allFellowshipsLeading;
    private List<LOTRFellowshipClient> allFellowshipsOther;
    private List<LOTRFellowshipClient> allFellowshipInvites;
    private LOTRFellowshipClient mouseOverFellowship;
    private LOTRFellowshipClient viewingFellowship;
    private String mouseOverPlayer;
    private boolean mouseOverPlayerRemove;
    private boolean mouseOverPlayerOp;
    private boolean mouseOverPlayerDeop;
    private boolean mouseOverPlayerTransfer;
    private String removingPlayer;
    private String oppingPlayer;
    private String deoppingPlayer;
    private String transferringPlayer;
    private boolean mouseOverInviteAccept;
    private boolean mouseOverInviteReject;
    private GuiButton buttonCreate;
    private GuiButton buttonCreateThis;
    private LOTRGuiButtonFsOption buttonInvitePlayer;
    private GuiButton buttonInviteThis;
    private LOTRGuiButtonFsOption buttonDisband;
    private GuiButton buttonDisbandThis;
    private GuiButton buttonLeave;
    private GuiButton buttonLeaveThis;
    private LOTRGuiButtonFsOption buttonSetIcon;
    private GuiButton buttonRemove;
    private GuiButton buttonTransfer;
    private LOTRGuiButtonFsOption buttonRename;
    private GuiButton buttonRenameThis;
    private GuiButton buttonBack;
    private GuiButton buttonInvites;
    private LOTRGuiButtonFsOption buttonPVP;
    private LOTRGuiButtonFsOption buttonHiredFF;
    private LOTRGuiButtonFsOption buttonMapShow;
    private GuiButton buttonOp;
    private GuiButton buttonDeop;
    private List<LOTRGuiButtonFsOption> orderedFsOptionButtons;
    private GuiTextField textFieldName;
    private GuiTextField textFieldPlayer;
    private GuiTextField textFieldRename;
    private static final int MAX_NAME_LENGTH = 40;
    public static final int entrySplit = 5;
    public static final int entryBorder = 10;
    public static final int selectBorder = 2;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    private int scrollBarX;
    private LOTRGuiScrollPane scrollPaneLeading;
    private LOTRGuiScrollPane scrollPaneOther;
    private LOTRGuiScrollPane scrollPaneMembers;
    private LOTRGuiScrollPane scrollPaneInvites;
    private static final int maxDisplayedFellowships = 12;
    private int displayedFellowshipsLeading;
    private int displayedFellowshipsOther;
    private static final int maxDisplayedMembers = 11;
    private int displayedMembers;
    private static final int maxDisplayedInvites = 15;
    private int displayedInvites;
    
    public LOTRGuiFellowships() {
        this.page = Page.LIST;
        this.allFellowshipsLeading = new ArrayList<LOTRFellowshipClient>();
        this.allFellowshipsOther = new ArrayList<LOTRFellowshipClient>();
        this.allFellowshipInvites = new ArrayList<LOTRFellowshipClient>();
        this.orderedFsOptionButtons = new ArrayList<LOTRGuiButtonFsOption>();
        super.xSize = 256;
        this.scrollWidgetWidth = 9;
        this.scrollWidgetHeight = 8;
        this.scrollBarX = super.xSize + 2 + 1;
        this.scrollPaneLeading = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
        this.scrollPaneOther = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
        this.scrollPaneMembers = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
        this.scrollPaneInvites = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (super.mc.thePlayer != null) {
            this.refreshFellowshipList();
        }
        final int midX = super.guiLeft + super.xSize / 2;
        super.buttonList.add(this.buttonCreate = new GuiButton(0, midX - 100, super.guiTop + 230, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.create")));
        super.buttonList.add(this.buttonCreateThis = new GuiButton(1, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.createThis")));
        super.buttonList.add(this.buttonInvitePlayer = new LOTRGuiButtonFsOption(2, midX, super.guiTop + 232, 0, 48, StatCollector.translateToLocal("lotr.gui.fellowships.invite")));
        super.buttonList.add(this.buttonInviteThis = new GuiButton(3, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.inviteThis")));
        super.buttonList.add(this.buttonDisband = new LOTRGuiButtonFsOption(4, midX, super.guiTop + 232, 16, 48, StatCollector.translateToLocal("lotr.gui.fellowships.disband")));
        super.buttonList.add(this.buttonDisbandThis = new GuiButton(5, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.disbandThis")));
        super.buttonList.add(this.buttonLeave = new GuiButton(6, midX - 60, super.guiTop + 230, 120, 20, StatCollector.translateToLocal("lotr.gui.fellowships.leave")));
        super.buttonList.add(this.buttonLeaveThis = new GuiButton(7, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.leaveThis")));
        super.buttonList.add(this.buttonSetIcon = new LOTRGuiButtonFsOption(8, midX, super.guiTop + 232, 48, 48, StatCollector.translateToLocal("lotr.gui.fellowships.setIcon")));
        super.buttonList.add(this.buttonRemove = new GuiButton(9, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.remove")));
        super.buttonList.add(this.buttonTransfer = new GuiButton(10, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.transfer")));
        super.buttonList.add(this.buttonRename = new LOTRGuiButtonFsOption(11, midX, super.guiTop + 232, 32, 48, StatCollector.translateToLocal("lotr.gui.fellowships.rename")));
        super.buttonList.add(this.buttonRenameThis = new GuiButton(12, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.renameThis")));
        super.buttonList.add(this.buttonBack = new GuiButton(13, super.guiLeft - 10, super.guiTop, 20, 20, "<"));
        super.buttonList.add(this.buttonInvites = new LOTRGuiButtonFsInvites(14, super.guiLeft + super.xSize - 16, super.guiTop, ""));
        super.buttonList.add(this.buttonPVP = new LOTRGuiButtonFsOption(15, midX, super.guiTop + 232, 64, 48, StatCollector.translateToLocal("lotr.gui.fellowships.togglePVP")));
        super.buttonList.add(this.buttonHiredFF = new LOTRGuiButtonFsOption(16, midX, super.guiTop + 232, 80, 48, StatCollector.translateToLocal("lotr.gui.fellowships.toggleHiredFF")));
        super.buttonList.add(this.buttonMapShow = new LOTRGuiButtonFsOption(17, midX, super.guiTop + 232, 96, 48, StatCollector.translateToLocal("lotr.gui.fellowships.toggleMapShow")));
        super.buttonList.add(this.buttonOp = new GuiButton(18, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.op")));
        super.buttonList.add(this.buttonDeop = new GuiButton(19, midX - 100, super.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.deop")));
        this.orderedFsOptionButtons.clear();
        this.orderedFsOptionButtons.add(this.buttonInvitePlayer);
        this.orderedFsOptionButtons.add(this.buttonDisband);
        this.orderedFsOptionButtons.add(this.buttonRename);
        this.orderedFsOptionButtons.add(this.buttonSetIcon);
        this.orderedFsOptionButtons.add(this.buttonMapShow);
        this.orderedFsOptionButtons.add(this.buttonPVP);
        this.orderedFsOptionButtons.add(this.buttonHiredFF);
        (this.textFieldName = new GuiTextField(super.fontRendererObj, midX - 80, super.guiTop + 40, 160, 20)).func_146203_f(40);
        this.textFieldPlayer = new GuiTextField(super.fontRendererObj, midX - 80, super.guiTop + 40, 160, 20);
        (this.textFieldRename = new GuiTextField(super.fontRendererObj, midX - 80, super.guiTop + 40, 160, 20)).func_146203_f(40);
    }
    
    private void refreshFellowshipList() {
        this.allFellowshipsLeading.clear();
        this.allFellowshipsOther.clear();
        final List<LOTRFellowshipClient> fellowships = new ArrayList<LOTRFellowshipClient>(LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getClientFellowships());
        for (final LOTRFellowshipClient fs : fellowships) {
            if (fs.isOwned()) {
                this.allFellowshipsLeading.add(fs);
            }
            else {
                this.allFellowshipsOther.add(fs);
            }
        }
        this.allFellowshipInvites.clear();
        this.allFellowshipInvites.addAll(LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getClientFellowshipInvites());
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.refreshFellowshipList();
        this.textFieldName.updateCursorCounter();
        if (this.page != Page.CREATE) {
            this.textFieldName.setText("");
        }
        this.textFieldPlayer.updateCursorCounter();
        if (this.page != Page.INVITE) {
            this.textFieldPlayer.setText("");
        }
        this.textFieldRename.updateCursorCounter();
        if (this.page != Page.RENAME) {
            this.textFieldRename.setText("");
        }
    }
    
    private void alignOptionButtons() {
        final List<GuiButton> activeOptionButtons = new ArrayList<GuiButton>();
        for (final GuiButton button : this.orderedFsOptionButtons) {
            if (button.field_146125_m) {
                activeOptionButtons.add(button);
            }
        }
        if (this.buttonLeave.field_146125_m) {
            activeOptionButtons.add(this.buttonLeave);
        }
        final int midX = super.guiLeft + super.xSize / 2;
        final int numActive = activeOptionButtons.size();
        if (numActive > 0) {
            final int gap = 8;
            int allWidth = 0;
            for (final GuiButton button2 : activeOptionButtons) {
                if (allWidth > 0) {
                    allWidth += gap;
                }
                allWidth += button2.field_146120_f;
            }
            int x = midX - allWidth / 2;
            for (int i = 0; i < activeOptionButtons.size(); ++i) {
                final GuiButton button3 = activeOptionButtons.get(i);
                button3.field_146128_h = x;
                x += button3.field_146120_f;
                x += gap;
            }
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
        final boolean viewingOwned = this.viewingFellowship != null && this.viewingFellowship.isOwned();
        final boolean viewingAdminned = this.viewingFellowship != null && this.viewingFellowship.isAdminned();
        this.mouseOverFellowship = null;
        this.mouseOverPlayer = null;
        this.mouseOverPlayerRemove = false;
        this.mouseOverPlayerOp = false;
        this.mouseOverPlayerDeop = false;
        this.mouseOverPlayerTransfer = false;
        if (this.page != Page.REMOVE) {
            this.removingPlayer = null;
        }
        if (this.page != Page.OP) {
            this.oppingPlayer = null;
        }
        if (this.page != Page.DEOP) {
            this.deoppingPlayer = null;
        }
        if (this.page != Page.TRANSFER) {
            this.transferringPlayer = null;
        }
        this.mouseOverInviteAccept = false;
        this.mouseOverInviteReject = false;
        final boolean canCreateNew = playerData.canCreateFellowships(true);
        this.buttonCreate.field_146125_m = (this.page == Page.LIST);
        this.buttonCreate.enabled = (this.buttonCreate.field_146125_m && canCreateNew);
        this.buttonCreateThis.field_146125_m = (this.page == Page.CREATE);
        final String checkValidName = this.checkValidFellowshipName(this.textFieldName.getText());
        this.buttonCreateThis.enabled = (this.buttonCreateThis.field_146125_m && checkValidName == null);
        final LOTRGuiButtonFsOption buttonInvitePlayer = this.buttonInvitePlayer;
        final LOTRGuiButtonFsOption buttonInvitePlayer2 = this.buttonInvitePlayer;
        final boolean b = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        buttonInvitePlayer2.enabled = b;
        buttonInvitePlayer.field_146125_m = b;
        this.buttonInviteThis.field_146125_m = (this.page == Page.INVITE);
        final String checkValidPlayer = this.checkValidPlayerName(this.textFieldPlayer.getText());
        this.buttonInviteThis.enabled = (this.buttonInviteThis.field_146125_m && checkValidPlayer == null);
        final LOTRGuiButtonFsOption buttonDisband = this.buttonDisband;
        final LOTRGuiButtonFsOption buttonDisband2 = this.buttonDisband;
        final boolean b2 = this.page == Page.FELLOWSHIP && viewingOwned;
        buttonDisband2.enabled = b2;
        buttonDisband.field_146125_m = b2;
        final GuiButton buttonDisbandThis = this.buttonDisbandThis;
        final GuiButton buttonDisbandThis2 = this.buttonDisbandThis;
        final boolean b3 = this.page == Page.DISBAND;
        buttonDisbandThis2.enabled = b3;
        buttonDisbandThis.field_146125_m = b3;
        final GuiButton buttonLeave = this.buttonLeave;
        final GuiButton buttonLeave2 = this.buttonLeave;
        final boolean b4 = this.page == Page.FELLOWSHIP && !viewingOwned;
        buttonLeave2.enabled = b4;
        buttonLeave.field_146125_m = b4;
        final GuiButton buttonLeaveThis = this.buttonLeaveThis;
        final GuiButton buttonLeaveThis2 = this.buttonLeaveThis;
        final boolean b5 = this.page == Page.LEAVE;
        buttonLeaveThis2.enabled = b5;
        buttonLeaveThis.field_146125_m = b5;
        final LOTRGuiButtonFsOption buttonSetIcon = this.buttonSetIcon;
        final LOTRGuiButtonFsOption buttonSetIcon2 = this.buttonSetIcon;
        final boolean b6 = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        buttonSetIcon2.enabled = b6;
        buttonSetIcon.field_146125_m = b6;
        final GuiButton buttonRemove = this.buttonRemove;
        final GuiButton buttonRemove2 = this.buttonRemove;
        final boolean b7 = this.page == Page.REMOVE;
        buttonRemove2.enabled = b7;
        buttonRemove.field_146125_m = b7;
        final GuiButton buttonTransfer = this.buttonTransfer;
        final GuiButton buttonTransfer2 = this.buttonTransfer;
        final boolean b8 = this.page == Page.TRANSFER;
        buttonTransfer2.enabled = b8;
        buttonTransfer.field_146125_m = b8;
        final LOTRGuiButtonFsOption buttonRename = this.buttonRename;
        final LOTRGuiButtonFsOption buttonRename2 = this.buttonRename;
        final boolean b9 = this.page == Page.FELLOWSHIP && viewingOwned;
        buttonRename2.enabled = b9;
        buttonRename.field_146125_m = b9;
        this.buttonRenameThis.field_146125_m = (this.page == Page.RENAME);
        final String checkValidRename = this.checkValidFellowshipName(this.textFieldRename.getText());
        this.buttonRenameThis.enabled = (this.buttonRenameThis.field_146125_m && checkValidRename == null);
        final GuiButton buttonBack = this.buttonBack;
        final GuiButton buttonBack2 = this.buttonBack;
        final boolean b10 = this.page != Page.LIST;
        buttonBack2.enabled = b10;
        buttonBack.field_146125_m = b10;
        final GuiButton buttonInvites = this.buttonInvites;
        final GuiButton buttonInvites2 = this.buttonInvites;
        final boolean b11 = this.page == Page.LIST;
        buttonInvites2.enabled = b11;
        buttonInvites.field_146125_m = b11;
        final LOTRGuiButtonFsOption buttonPVP = this.buttonPVP;
        final LOTRGuiButtonFsOption buttonPVP2 = this.buttonPVP;
        final boolean b12 = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        buttonPVP2.enabled = b12;
        buttonPVP.field_146125_m = b12;
        if (this.buttonPVP.enabled) {
            this.buttonPVP.setIconUV(64, this.viewingFellowship.getPreventPVP() ? 80 : 48);
        }
        final LOTRGuiButtonFsOption buttonHiredFF = this.buttonHiredFF;
        final LOTRGuiButtonFsOption buttonHiredFF2 = this.buttonHiredFF;
        final boolean b13 = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        buttonHiredFF2.enabled = b13;
        buttonHiredFF.field_146125_m = b13;
        if (this.buttonHiredFF.enabled) {
            this.buttonHiredFF.setIconUV(80, this.viewingFellowship.getPreventHiredFriendlyFire() ? 80 : 48);
        }
        final LOTRGuiButtonFsOption buttonMapShow = this.buttonMapShow;
        final LOTRGuiButtonFsOption buttonMapShow2 = this.buttonMapShow;
        final boolean b14 = this.page == Page.FELLOWSHIP && viewingOwned;
        buttonMapShow2.enabled = b14;
        buttonMapShow.field_146125_m = b14;
        if (this.buttonMapShow.enabled) {
            this.buttonMapShow.setIconUV(96, this.viewingFellowship.getShowMapLocations() ? 48 : 80);
        }
        final GuiButton buttonOp = this.buttonOp;
        final GuiButton buttonOp2 = this.buttonOp;
        final boolean b15 = this.page == Page.OP;
        buttonOp2.enabled = b15;
        buttonOp.field_146125_m = b15;
        final GuiButton buttonDeop = this.buttonDeop;
        final GuiButton buttonDeop2 = this.buttonDeop;
        final boolean b16 = this.page == Page.DEOP;
        buttonDeop2.enabled = b16;
        buttonDeop.field_146125_m = b16;
        this.alignOptionButtons();
        this.setupScrollBars(i, j);
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawScreen(i, j, f);
        String s = StatCollector.translateToLocal("lotr.gui.fellowships.title");
        this.drawCenteredString(s, super.guiLeft + super.xSize / 2, super.guiTop - 30, 16777215);
        if (this.page == Page.LIST) {
            final int x = super.guiLeft;
            int y = this.scrollPaneLeading.paneY0;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.leading");
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT + 10;
            final List<LOTRFellowshipClient> sortedLeading = this.sortFellowshipsForDisplay(this.allFellowshipsLeading);
            final int[] leadingMinMax = this.scrollPaneLeading.getMinMaxIndices(sortedLeading, this.displayedFellowshipsLeading);
            for (int index = leadingMinMax[0]; index <= leadingMinMax[1]; ++index) {
                final LOTRFellowshipClient fs = sortedLeading.get(index);
                this.drawFellowshipEntry(fs, x, y, i, j, false);
                y += super.fontRendererObj.FONT_HEIGHT + 5;
            }
            y = this.scrollPaneOther.paneY0;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.member");
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT + 10;
            final List<LOTRFellowshipClient> sortedOther = this.sortFellowshipsForDisplay(this.allFellowshipsOther);
            final int[] otherMinMax = this.scrollPaneOther.getMinMaxIndices(sortedOther, this.displayedFellowshipsOther);
            for (int index2 = otherMinMax[0]; index2 <= otherMinMax[1]; ++index2) {
                final LOTRFellowshipClient fs2 = sortedOther.get(index2);
                this.drawFellowshipEntry(fs2, x, y, i, j, false);
                y += super.fontRendererObj.FONT_HEIGHT + 5;
            }
            final String invites = String.valueOf(playerData.getClientFellowshipInvites().size());
            final int invitesX = this.buttonInvites.field_146128_h - 2 - super.fontRendererObj.getStringWidth(invites);
            final int invitesY = this.buttonInvites.field_146129_i + this.buttonInvites.field_146121_g / 2 - super.fontRendererObj.FONT_HEIGHT / 2;
            super.fontRendererObj.drawString(invites, invitesX, invitesY, 16777215);
            if (this.buttonInvites.func_146115_a()) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal("lotr.gui.fellowships.invitesTooltip"));
            }
            if (!canCreateNew && this.buttonCreate.func_146115_a()) {
                s = StatCollector.translateToLocal("lotr.gui.fellowships.createLimit");
                this.drawCenteredString(s, super.guiLeft + super.xSize / 2, this.buttonCreate.field_146129_i + this.buttonCreate.field_146121_g + 4, 16777215);
            }
            if (this.scrollPaneLeading.hasScrollBar) {
                this.scrollPaneLeading.drawScrollBar();
            }
            if (this.scrollPaneOther.hasScrollBar) {
                this.scrollPaneOther.drawScrollBar();
            }
        }
        else if (this.page == Page.CREATE) {
            s = StatCollector.translateToLocal("lotr.gui.fellowships.createName");
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, this.textFieldName.field_146210_g - 4 - super.fontRendererObj.FONT_HEIGHT, 16777215);
            this.textFieldName.drawTextBox();
            if (checkValidName != null) {
                this.drawCenteredString(checkValidName, super.guiLeft + super.xSize / 2, this.textFieldName.field_146210_g + this.textFieldName.field_146219_i + super.fontRendererObj.FONT_HEIGHT, 16711680);
            }
        }
        else if (this.page == Page.FELLOWSHIP) {
            final int x = super.guiLeft;
            int y = super.guiTop + 10;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.nameAndPlayers", new Object[] { this.viewingFellowship.getName(), this.viewingFellowship.getMemberCount() });
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT;
            y += 5;
            if (this.viewingFellowship.getIcon() != null) {
                this.drawFellowshipIcon(this.viewingFellowship, super.guiLeft + super.xSize / 2 - 8, y, 1.0f);
            }
            final boolean preventPVP = this.viewingFellowship.getPreventPVP();
            final boolean preventHiredFF = this.viewingFellowship.getPreventHiredFriendlyFire();
            final boolean mapShow = this.viewingFellowship.getShowMapLocations();
            final int iconPVPX = super.guiLeft + super.xSize - 36;
            final int iconHFFX = super.guiLeft + super.xSize - 16;
            final int iconMapX = super.guiLeft + super.xSize - 56;
            final int iconY = y;
            final int iconSize = 16;
            super.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(iconPVPX, iconY, 64, preventPVP ? 80 : 48, iconSize, iconSize);
            this.drawTexturedModalRect(iconHFFX, iconY, 80, preventHiredFF ? 80 : 48, iconSize, iconSize);
            this.drawTexturedModalRect(iconMapX, iconY, 96, mapShow ? 48 : 80, iconSize, iconSize);
            if (i >= iconPVPX && i < iconPVPX + iconSize && j >= iconY && j < iconY + iconSize) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal(preventPVP ? "lotr.gui.fellowships.pvp.prevent" : "lotr.gui.fellowships.pvp.allow"));
            }
            if (i >= iconHFFX && i < iconHFFX + iconSize && j >= iconY && j < iconY + iconSize) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal(preventHiredFF ? "lotr.gui.fellowships.hiredFF.prevent" : "lotr.gui.fellowships.hiredFF.allow"));
            }
            if (i >= iconMapX && i < iconMapX + iconSize && j >= iconY && j < iconY + iconSize) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal(mapShow ? "lotr.gui.fellowships.mapShow.on" : "lotr.gui.fellowships.mapShow.off"));
            }
            y += iconSize;
            y += 10;
            int titleOffset = 0;
            for (final String name : this.viewingFellowship.getAllPlayerNames()) {
                final LOTRTitle.PlayerTitle title = this.viewingFellowship.getTitleFor(name);
                if (title != null) {
                    final String titleName = title.getFormattedTitle();
                    final int thisTitleWidth = super.fontRendererObj.getStringWidth(titleName + " ");
                    titleOffset = Math.max(titleOffset, thisTitleWidth);
                }
            }
            this.drawPlayerEntry(this.viewingFellowship.getOwnerName(), x, y, titleOffset, i, j);
            y += super.fontRendererObj.FONT_HEIGHT + 10;
            final List<String> membersSorted = this.sortMemberNamesForDisplay(this.viewingFellowship);
            final int[] membersMinMax = this.scrollPaneMembers.getMinMaxIndices(membersSorted, this.displayedMembers);
            for (int index3 = membersMinMax[0]; index3 <= membersMinMax[1]; ++index3) {
                final String name2 = membersSorted.get(index3);
                this.drawPlayerEntry(name2, x, y, titleOffset, i, j);
                y += super.fontRendererObj.FONT_HEIGHT + 5;
            }
            for (final Object bObj : super.buttonList) {
                final GuiButton button = (GuiButton)bObj;
                if (button instanceof LOTRGuiButtonFsOption && button.field_146125_m && button.func_146115_a()) {
                    s = button.displayString;
                    this.drawCenteredString(s, super.guiLeft + super.xSize / 2, button.field_146129_i + button.field_146121_g + 4, 16777215);
                }
            }
            if (this.scrollPaneMembers.hasScrollBar) {
                this.scrollPaneMembers.drawScrollBar();
            }
        }
        else if (this.page == Page.INVITE) {
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.inviteName", new Object[] { this.viewingFellowship.getName() });
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, this.textFieldPlayer.field_146210_g - 4 - super.fontRendererObj.FONT_HEIGHT, 16777215);
            this.textFieldPlayer.drawTextBox();
            if (checkValidPlayer != null) {
                this.drawCenteredString(checkValidPlayer, super.guiLeft + super.xSize / 2, this.textFieldPlayer.field_146210_g + this.textFieldPlayer.field_146219_i + super.fontRendererObj.FONT_HEIGHT, 16711680);
            }
        }
        else if (this.page == Page.DISBAND) {
            final int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.disbandCheck1", new Object[] { this.viewingFellowship.getName() });
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.disbandCheck2");
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT * 2;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.disbandCheck3");
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT;
        }
        else if (this.page == Page.LEAVE) {
            final int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.leaveCheck1", new Object[] { this.viewingFellowship.getName() });
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.leaveCheck2");
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT * 2;
        }
        else if (this.page == Page.REMOVE) {
            final int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.removeCheck", new Object[] { this.viewingFellowship.getName(), this.removingPlayer });
            final List<String> lines = (List<String>)super.fontRendererObj.listFormattedStringToWidth(s, super.xSize);
            for (final String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += super.fontRendererObj.FONT_HEIGHT;
            }
        }
        else if (this.page == Page.OP) {
            final int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.opCheck1", new Object[] { this.viewingFellowship.getName(), this.oppingPlayer });
            List<String> lines = (List<String>)super.fontRendererObj.listFormattedStringToWidth(s, super.xSize);
            for (final String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += super.fontRendererObj.FONT_HEIGHT;
            }
            y += super.fontRendererObj.FONT_HEIGHT;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.opCheck2", new Object[] { this.viewingFellowship.getName(), this.oppingPlayer });
            lines = (List<String>)super.fontRendererObj.listFormattedStringToWidth(s, super.xSize);
            for (final String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += super.fontRendererObj.FONT_HEIGHT;
            }
        }
        else if (this.page == Page.DEOP) {
            final int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.deopCheck", new Object[] { this.viewingFellowship.getName(), this.deoppingPlayer });
            final List<String> lines = (List<String>)super.fontRendererObj.listFormattedStringToWidth(s, super.xSize);
            for (final String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += super.fontRendererObj.FONT_HEIGHT;
            }
        }
        else if (this.page == Page.TRANSFER) {
            final int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.transferCheck1", new Object[] { this.viewingFellowship.getName(), this.transferringPlayer });
            final List<String> lines = (List<String>)super.fontRendererObj.listFormattedStringToWidth(s, super.xSize);
            for (final String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += super.fontRendererObj.FONT_HEIGHT;
            }
            y += super.fontRendererObj.FONT_HEIGHT;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.transferCheck2");
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT;
        }
        else if (this.page == Page.RENAME) {
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.renameName", new Object[] { this.viewingFellowship.getName() });
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, this.textFieldRename.field_146210_g - 4 - super.fontRendererObj.FONT_HEIGHT, 16777215);
            this.textFieldRename.drawTextBox();
            if (checkValidRename != null) {
                this.drawCenteredString(checkValidRename, super.guiLeft + super.xSize / 2, this.textFieldRename.field_146210_g + this.textFieldRename.field_146219_i + super.fontRendererObj.FONT_HEIGHT, 16711680);
            }
        }
        else if (this.page == Page.INVITATIONS) {
            final int x = super.guiLeft;
            int y = super.guiTop + 10;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.invites");
            this.drawCenteredString(s, super.guiLeft + super.xSize / 2, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT + 10;
            if (this.allFellowshipInvites.isEmpty()) {
                y += super.fontRendererObj.FONT_HEIGHT;
                s = StatCollector.translateToLocal("lotr.gui.fellowships.invitesNone");
                this.drawCenteredString(s, super.guiLeft + super.xSize / 2, y, 16777215);
            }
            else {
                final int[] invitesMinMax = this.scrollPaneInvites.getMinMaxIndices(this.allFellowshipInvites, this.displayedInvites);
                for (int index4 = invitesMinMax[0]; index4 <= invitesMinMax[1]; ++index4) {
                    final LOTRFellowshipClient fs3 = this.allFellowshipInvites.get(index4);
                    this.drawFellowshipEntry(fs3, x, y, i, j, true);
                    y += super.fontRendererObj.FONT_HEIGHT + 5;
                }
            }
            if (this.scrollPaneInvites.hasScrollBar) {
                this.scrollPaneInvites.drawScrollBar();
            }
        }
    }
    
    private void drawFellowshipEntry(final LOTRFellowshipClient fs, final int x, final int y, final int mouseX, final int mouseY, final boolean isInvite) {
        this.drawFellowshipEntry(fs, x, y, mouseX, mouseY, isInvite, super.xSize);
    }
    
    public void drawFellowshipEntry(final LOTRFellowshipClient fs, final int x, final int y, final int mouseX, final int mouseY, final boolean isInvite, final int selectWidth) {
        final int selectX0 = x - 2;
        final int selectX2 = x + selectWidth + 2;
        final int selectY0 = y - 2;
        final int selectY2 = y + super.fontRendererObj.FONT_HEIGHT + 2;
        if (mouseX >= selectX0 && mouseX <= selectX2 && mouseY >= selectY0 && mouseY <= selectY2) {
            drawRect(selectX0, selectY0, selectX2, selectY2, 1442840575);
            this.mouseOverFellowship = fs;
        }
        final boolean isMouseOver = this.mouseOverFellowship == fs;
        this.drawFellowshipIcon(fs, x, y, 0.5f);
        String fsName = fs.getName();
        final int maxLength = 110;
        if (super.fontRendererObj.getStringWidth(fsName) > maxLength) {
            String ellipsis;
            for (ellipsis = "..."; super.fontRendererObj.getStringWidth(fsName + ellipsis) > maxLength; fsName = fsName.substring(0, fsName.length() - 1)) {}
            fsName += ellipsis;
        }
        final String ownerName = fs.getOwnerName();
        final boolean ownerOnline = isPlayerOnline(ownerName);
        super.fontRendererObj.drawString(fsName, x + 15, y, 16777215);
        super.fontRendererObj.drawString(ownerName, x + 130, y, ownerOnline ? 16777215 : (isMouseOver ? 12303291 : 7829367));
        if (isInvite) {
            final int iconWidth = 8;
            final int iconAcceptX = x + super.xSize - 18;
            final int iconRejectX = x + super.xSize - 8;
            boolean accept = false;
            boolean reject = false;
            if (isMouseOver) {
                this.mouseOverInviteAccept = (mouseX >= iconAcceptX && mouseX <= iconAcceptX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                accept = this.mouseOverInviteAccept;
                this.mouseOverInviteReject = (mouseX >= iconRejectX && mouseX <= iconRejectX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                reject = this.mouseOverInviteReject;
            }
            super.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(iconAcceptX, y, 16, 16 + (accept ? 0 : iconWidth), iconWidth, iconWidth);
            this.drawTexturedModalRect(iconRejectX, y, 8, 16 + (reject ? 0 : iconWidth), iconWidth, iconWidth);
        }
        else {
            final String memberCount = String.valueOf(fs.getMemberCount());
            final String onlineMemberCount = String.valueOf(this.countOnlineMembers(fs)) + " | ";
            super.fontRendererObj.drawString(memberCount, x + super.xSize - super.fontRendererObj.getStringWidth(memberCount), y, isMouseOver ? 12303291 : 7829367);
            super.fontRendererObj.drawString(onlineMemberCount, x + super.xSize - super.fontRendererObj.getStringWidth(memberCount) - super.fontRendererObj.getStringWidth(onlineMemberCount), y, 16777215);
        }
    }
    
    private void drawPlayerEntry(final String player, final int x, final int y, final int titleOffset, final int mouseX, final int mouseY) {
        final int selectX0 = x - 2;
        final int selectX2 = x + super.xSize + 2;
        final int selectY0 = y - 2;
        final int selectY2 = y + super.fontRendererObj.FONT_HEIGHT + 2;
        if (mouseX >= selectX0 && mouseX <= selectX2 && mouseY >= selectY0 && mouseY <= selectY2) {
            drawRect(selectX0, selectY0, selectX2, selectY2, 1442840575);
            this.mouseOverPlayer = player;
        }
        final boolean isMouseOver = this.mouseOverPlayer == player;
        String titleName = null;
        final LOTRTitle.PlayerTitle title = this.viewingFellowship.getTitleFor(player);
        if (title != null) {
            titleName = title.getFormattedTitle();
        }
        if (titleName != null) {
            super.fontRendererObj.drawString(titleName, x, y, 16777215);
        }
        final boolean online = isPlayerOnline(player);
        super.fontRendererObj.drawString(player, x + titleOffset, y, online ? 16777215 : (isMouseOver ? 12303291 : 7829367));
        final boolean isOwner = this.viewingFellowship.getOwnerName().equals(player);
        final boolean isAdmin = this.viewingFellowship.isAdmin(player);
        if (isOwner) {
            super.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x + titleOffset + super.fontRendererObj.getStringWidth(player + " "), y, 0, 0, 8, 8);
        }
        else if (isAdmin) {
            super.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x + titleOffset + super.fontRendererObj.getStringWidth(player + " "), y, 8, 0, 8, 8);
        }
        final boolean owned = this.viewingFellowship.isOwned();
        final boolean adminned = this.viewingFellowship.isAdminned();
        if (!isOwner && (owned || adminned)) {
            final int iconWidth = 8;
            int iconRemoveX = x + super.xSize - 28;
            final int iconOpDeopX = x + super.xSize - 18;
            final int iconTransferX = x + super.xSize - 8;
            if (adminned) {
                iconRemoveX = x + super.xSize - 8;
            }
            boolean remove = false;
            boolean opDeop = false;
            boolean transfer = false;
            if (isMouseOver) {
                this.mouseOverPlayerRemove = (mouseX >= iconRemoveX && mouseX <= iconRemoveX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                remove = this.mouseOverPlayerRemove;
                if (owned) {
                    if (isAdmin) {
                        this.mouseOverPlayerDeop = (mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                        opDeop = this.mouseOverPlayerDeop;
                    }
                    else {
                        this.mouseOverPlayerOp = (mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                        opDeop = this.mouseOverPlayerOp;
                    }
                    this.mouseOverPlayerTransfer = (mouseX >= iconTransferX && mouseX <= iconTransferX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                    transfer = this.mouseOverPlayerTransfer;
                }
            }
            super.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(iconRemoveX, y, 8, 16 + (remove ? 0 : iconWidth), iconWidth, iconWidth);
            if (owned) {
                if (isAdmin) {
                    this.drawTexturedModalRect(iconOpDeopX, y, 32, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
                }
                else {
                    this.drawTexturedModalRect(iconOpDeopX, y, 24, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
                }
                this.drawTexturedModalRect(iconTransferX, y, 0, 16 + (transfer ? 0 : iconWidth), iconWidth, iconWidth);
            }
        }
    }
    
    private void drawFellowshipIcon(final LOTRFellowshipClient fsClient, final int x, final int y, final float scale) {
        final ItemStack fsIcon = fsClient.getIcon();
        if (fsIcon != null) {
            GL11.glDisable(3042);
            GL11.glDisable(3008);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GL11.glScalef(scale, scale, 1.0f);
            LOTRGuiMenuBase.renderItem.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.getTextureManager(), fsIcon, Math.round(x / scale), Math.round(y / scale));
            GL11.glPopMatrix();
            GL11.glDisable(2896);
        }
    }
    
    private void renderIconTooltip(final int x, final int y, final String s) {
        final float z = ((Gui)this).zLevel;
        final int stringWidth = 200;
        final List desc = super.fontRendererObj.listFormattedStringToWidth(s, stringWidth);
        this.func_146283_a(desc, x, y);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((Gui)this).zLevel = z;
    }
    
    public static boolean isPlayerOnline(final String player) {
        final EntityClientPlayerMP mcPlayer = Minecraft.getMinecraft().thePlayer;
        final List list = mcPlayer.sendQueue.playerInfoList;
        for (final Object obj : list) {
            final GuiPlayerInfo info = (GuiPlayerInfo)obj;
            if (info.name.equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }
    
    private int countOnlineMembers(final LOTRFellowshipClient fs) {
        int i = 0;
        final List<String> allPlayers = new ArrayList<String>(fs.getAllPlayerNames());
        for (final String player : allPlayers) {
            if (isPlayerOnline(player)) {
                ++i;
            }
        }
        return i;
    }
    
    private List<LOTRFellowshipClient> sortFellowshipsForDisplay(final List<LOTRFellowshipClient> list) {
        final List<LOTRFellowshipClient> sorted = new ArrayList<LOTRFellowshipClient>(list);
        Collections.sort(sorted, new Comparator<LOTRFellowshipClient>() {
            @Override
            public int compare(final LOTRFellowshipClient fs1, final LOTRFellowshipClient fs2) {
                final int count1 = fs1.getMemberCount();
                final int count2 = fs2.getMemberCount();
                if (count1 == count2) {
                    return fs1.getName().toLowerCase().compareTo(fs2.getName().toLowerCase());
                }
                return -Integer.valueOf(count1).compareTo(count2);
            }
        });
        return sorted;
    }
    
    private List<String> sortMemberNamesForDisplay(final LOTRFellowshipClient fs) {
        final List<String> members = new ArrayList<String>(fs.getMemberNames());
        Collections.sort(members, new Comparator<String>() {
            @Override
            public int compare(final String player1, final String player2) {
                final boolean admin1 = fs.isAdmin(player1);
                final boolean admin2 = fs.isAdmin(player2);
                final boolean online1 = LOTRGuiFellowships.isPlayerOnline(player1);
                final boolean online2 = LOTRGuiFellowships.isPlayerOnline(player2);
                if (online1 == online2) {
                    if (admin1 == admin2) {
                        return player1.toLowerCase().compareTo(player2.toLowerCase());
                    }
                    if (admin1 && !admin2) {
                        return -1;
                    }
                    if (!admin1 && admin2) {
                        return 1;
                    }
                }
                else {
                    if (online1 && !online2) {
                        return -1;
                    }
                    if (!online1 && online2) {
                        return 1;
                    }
                }
                return 0;
            }
        });
        return members;
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (this.page == Page.CREATE && this.textFieldName.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.page == Page.INVITE && this.textFieldPlayer.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.page == Page.RENAME && this.textFieldRename.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.page != Page.LIST) {
            if (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode()) {
                if (this.page == Page.INVITE || this.page == Page.DISBAND || this.page == Page.LEAVE || this.page == Page.REMOVE || this.page == Page.OP || this.page == Page.DEOP || this.page == Page.TRANSFER || this.page == Page.RENAME) {
                    this.page = Page.FELLOWSHIP;
                }
                else {
                    this.page = Page.LIST;
                }
            }
        }
        else {
            super.keyTyped(c, i);
        }
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        if (this.page == Page.LIST && this.mouseOverFellowship != null) {
            this.buttonSound();
            this.page = Page.FELLOWSHIP;
            this.viewingFellowship = this.mouseOverFellowship;
        }
        if (this.page == Page.CREATE) {
            this.textFieldName.mouseClicked(i, j, k);
        }
        if (this.page == Page.INVITE) {
            this.textFieldPlayer.mouseClicked(i, j, k);
        }
        if (this.page == Page.RENAME) {
            this.textFieldRename.mouseClicked(i, j, k);
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerRemove) {
            this.buttonSound();
            this.page = Page.REMOVE;
            this.removingPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerOp) {
            this.buttonSound();
            this.page = Page.OP;
            this.oppingPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerDeop) {
            this.buttonSound();
            this.page = Page.DEOP;
            this.deoppingPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerTransfer) {
            this.buttonSound();
            this.page = Page.TRANSFER;
            this.transferringPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.INVITATIONS && this.mouseOverFellowship != null && this.mouseOverInviteAccept) {
            this.buttonSound();
            final LOTRPacketFellowshipRespondInvite packet = new LOTRPacketFellowshipRespondInvite(this.mouseOverFellowship, true);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            this.mouseOverFellowship = null;
        }
        if (this.page == Page.INVITATIONS && this.mouseOverFellowship != null && this.mouseOverInviteReject) {
            this.buttonSound();
            final LOTRPacketFellowshipRespondInvite packet = new LOTRPacketFellowshipRespondInvite(this.mouseOverFellowship, false);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            this.mouseOverFellowship = null;
        }
    }
    
    private void buttonSound() {
        this.buttonBack.func_146113_a(super.mc.getSoundHandler());
    }
    
    private void setupScrollBars(final int i, final int j) {
        if (this.page == Page.LIST) {
            this.displayedFellowshipsLeading = this.allFellowshipsLeading.size();
            this.displayedFellowshipsOther = this.allFellowshipsOther.size();
            this.scrollPaneLeading.hasScrollBar = false;
            this.scrollPaneOther.hasScrollBar = false;
            while (this.displayedFellowshipsLeading + this.displayedFellowshipsOther > 12) {
                if (this.displayedFellowshipsOther >= this.displayedFellowshipsLeading) {
                    --this.displayedFellowshipsOther;
                    this.scrollPaneOther.hasScrollBar = true;
                }
                else {
                    --this.displayedFellowshipsLeading;
                    this.scrollPaneLeading.hasScrollBar = true;
                }
            }
            this.scrollPaneLeading.paneX0 = super.guiLeft;
            this.scrollPaneLeading.scrollBarX0 = super.guiLeft + this.scrollBarX;
            this.scrollPaneLeading.paneY0 = super.guiTop + 10;
            this.scrollPaneLeading.paneY1 = this.scrollPaneLeading.paneY0 + super.fontRendererObj.FONT_HEIGHT + 10 + (super.fontRendererObj.FONT_HEIGHT + 5) * this.displayedFellowshipsLeading;
            this.scrollPaneLeading.mouseDragScroll(i, j);
            this.scrollPaneOther.paneX0 = super.guiLeft;
            this.scrollPaneOther.scrollBarX0 = super.guiLeft + this.scrollBarX;
            this.scrollPaneOther.paneY0 = this.scrollPaneLeading.paneY1 + 5;
            this.scrollPaneOther.paneY1 = this.scrollPaneOther.paneY0 + super.fontRendererObj.FONT_HEIGHT + 10 + (super.fontRendererObj.FONT_HEIGHT + 5) * this.displayedFellowshipsOther;
            this.scrollPaneOther.mouseDragScroll(i, j);
        }
        if (this.page == Page.FELLOWSHIP) {
            this.displayedMembers = this.viewingFellowship.getMemberNames().size();
            this.scrollPaneMembers.hasScrollBar = false;
            if (this.displayedMembers > 11) {
                this.displayedMembers = 11;
                this.scrollPaneMembers.hasScrollBar = true;
            }
            this.scrollPaneMembers.paneX0 = super.guiLeft;
            this.scrollPaneMembers.scrollBarX0 = super.guiLeft + this.scrollBarX;
            this.scrollPaneMembers.paneY0 = super.guiTop + 10 + super.fontRendererObj.FONT_HEIGHT + 5 + 16 + 10 + super.fontRendererObj.FONT_HEIGHT + 10;
            this.scrollPaneMembers.paneY1 = this.scrollPaneMembers.paneY0 + (super.fontRendererObj.FONT_HEIGHT + 5) * this.displayedMembers;
            this.scrollPaneMembers.mouseDragScroll(i, j);
        }
        else {
            this.scrollPaneMembers.hasScrollBar = false;
            this.scrollPaneMembers.mouseDragScroll(i, j);
        }
        if (this.page == Page.INVITATIONS) {
            this.displayedInvites = this.allFellowshipInvites.size();
            this.scrollPaneInvites.hasScrollBar = false;
            if (this.displayedInvites > 15) {
                this.displayedInvites = 15;
                this.scrollPaneInvites.hasScrollBar = true;
            }
            this.scrollPaneInvites.paneX0 = super.guiLeft;
            this.scrollPaneInvites.scrollBarX0 = super.guiLeft + this.scrollBarX;
            this.scrollPaneInvites.paneY0 = super.guiTop + 10 + super.fontRendererObj.FONT_HEIGHT + 10;
            this.scrollPaneInvites.paneY1 = this.scrollPaneInvites.paneY0 + (super.fontRendererObj.FONT_HEIGHT + 5) * this.displayedInvites;
            this.scrollPaneInvites.mouseDragScroll(i, j);
        }
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int k = Mouse.getEventDWheel();
        if (k != 0) {
            k = Integer.signum(k);
            if (this.page == Page.LIST) {
                if (this.scrollPaneLeading.hasScrollBar && this.scrollPaneLeading.mouseOver) {
                    final int l = this.allFellowshipsLeading.size() - this.displayedFellowshipsLeading;
                    this.scrollPaneLeading.mouseWheelScroll(k, l);
                }
                if (this.scrollPaneOther.hasScrollBar && this.scrollPaneOther.mouseOver) {
                    final int l = this.allFellowshipsOther.size() - this.displayedFellowshipsOther;
                    this.scrollPaneOther.mouseWheelScroll(k, l);
                }
            }
            if (this.page == Page.FELLOWSHIP && this.scrollPaneMembers.hasScrollBar && this.scrollPaneMembers.mouseOver) {
                final int l = this.viewingFellowship.getMemberNames().size() - this.displayedMembers;
                this.scrollPaneMembers.mouseWheelScroll(k, l);
            }
            if (this.page == Page.INVITATIONS && this.scrollPaneInvites.hasScrollBar && this.scrollPaneInvites.mouseOver) {
                final int l = this.allFellowshipInvites.size() - this.displayedInvites;
                this.scrollPaneInvites.mouseWheelScroll(k, l);
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonCreate) {
                this.page = Page.CREATE;
            }
            else if (button == this.buttonCreateThis) {
                String name = this.textFieldName.getText();
                if (this.checkValidFellowshipName(name) == null) {
                    name = StringUtils.trim(name);
                    final LOTRPacketFellowshipCreate packet = new LOTRPacketFellowshipCreate(name);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                }
                this.page = Page.LIST;
            }
            else if (button == this.buttonInvitePlayer) {
                this.page = Page.INVITE;
            }
            else if (button == this.buttonInviteThis) {
                String name = this.textFieldPlayer.getText();
                if (this.checkValidPlayerName(name) == null) {
                    name = StringUtils.trim(name);
                    final LOTRPacketFellowshipDoPlayer packet2 = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, name, LOTRPacketFellowshipDoPlayer.PlayerFunction.INVITE);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet2);
                }
                this.page = Page.FELLOWSHIP;
            }
            else if (button == this.buttonDisband) {
                this.page = Page.DISBAND;
            }
            else if (button == this.buttonDisbandThis) {
                final LOTRPacketFellowshipDisband packet3 = new LOTRPacketFellowshipDisband(this.viewingFellowship);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet3);
                this.page = Page.LIST;
            }
            else if (button == this.buttonLeave) {
                this.page = Page.LEAVE;
            }
            else if (button == this.buttonLeaveThis) {
                final LOTRPacketFellowshipLeave packet4 = new LOTRPacketFellowshipLeave(this.viewingFellowship);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet4);
                this.page = Page.LIST;
            }
            else if (button == this.buttonSetIcon) {
                final LOTRPacketFellowshipSetIcon packet5 = new LOTRPacketFellowshipSetIcon(this.viewingFellowship);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet5);
            }
            else if (button == this.buttonRemove) {
                final LOTRPacketFellowshipDoPlayer packet6 = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.removingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.REMOVE);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet6);
                this.page = Page.FELLOWSHIP;
            }
            else if (button == this.buttonOp) {
                final LOTRPacketFellowshipDoPlayer packet6 = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.oppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.OP);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet6);
                this.page = Page.FELLOWSHIP;
            }
            else if (button == this.buttonDeop) {
                final LOTRPacketFellowshipDoPlayer packet6 = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.deoppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.DEOP);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet6);
                this.page = Page.FELLOWSHIP;
            }
            else if (button == this.buttonTransfer) {
                final LOTRPacketFellowshipDoPlayer packet6 = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.transferringPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.TRANSFER);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet6);
                this.page = Page.FELLOWSHIP;
            }
            else if (button == this.buttonRename) {
                this.page = Page.RENAME;
            }
            else if (button == this.buttonRenameThis) {
                String name = this.textFieldRename.getText();
                if (this.checkValidFellowshipName(name) == null) {
                    name = StringUtils.trim(name);
                    final LOTRPacketFellowshipRename packet7 = new LOTRPacketFellowshipRename(this.viewingFellowship, name);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet7);
                }
                this.page = Page.FELLOWSHIP;
            }
            else if (button == this.buttonBack) {
                this.keyTyped('E', 1);
            }
            else if (button == this.buttonInvites) {
                this.page = Page.INVITATIONS;
            }
            else if (button == this.buttonPVP) {
                final LOTRPacketFellowshipToggle packet8 = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.PVP);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet8);
            }
            else if (button == this.buttonHiredFF) {
                final LOTRPacketFellowshipToggle packet8 = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.HIRED_FF);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet8);
            }
            else if (button == this.buttonMapShow) {
                final LOTRPacketFellowshipToggle packet8 = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.MAP_SHOW);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet8);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    private String checkValidFellowshipName(final String name) {
        if (StringUtils.isWhitespace((CharSequence)name)) {
            return "";
        }
        if (LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).anyMatchingFellowshipNames(name, true)) {
            return StatCollector.translateToLocal("lotr.gui.fellowships.nameExists");
        }
        return null;
    }
    
    private String checkValidPlayerName(final String name) {
        if (StringUtils.isWhitespace((CharSequence)name)) {
            return "";
        }
        if (this.viewingFellowship.isPlayerIn(name)) {
            return StatCollector.translateToLocalFormatted("lotr.gui.fellowships.playerExists", new Object[] { name });
        }
        return null;
    }
    
    public LOTRFellowshipClient getMouseOverFellowship() {
        return this.mouseOverFellowship;
    }
    
    public void clearMouseOverFellowship() {
        this.mouseOverFellowship = null;
    }
    
    static {
        iconsTextures = new ResourceLocation("lotr:gui/fellowships.png");
    }
    
    public enum Page
    {
        LIST, 
        CREATE, 
        FELLOWSHIP, 
        INVITE, 
        DISBAND, 
        LEAVE, 
        REMOVE, 
        OP, 
        DEOP, 
        TRANSFER, 
        RENAME, 
        INVITATIONS;
    }
}
