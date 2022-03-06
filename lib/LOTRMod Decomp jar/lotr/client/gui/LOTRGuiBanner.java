// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import java.util.UUID;
import lotr.common.network.LOTRPacketEditBanner;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBannerRequestInvalidName;
import org.lwjgl.input.Mouse;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fellowship.LOTRFellowshipProfile;
import org.lwjgl.opengl.GL11;
import com.mojang.authlib.GameProfile;
import java.util.Arrays;
import net.minecraft.util.StringUtils;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiBanner extends LOTRGuiScreenBase
{
    private static ResourceLocation guiTexture;
    public final LOTREntityBanner theBanner;
    public int xSize;
    public int ySize;
    private int guiLeft;
    private int guiTop;
    private boolean firstInit;
    private GuiButton modeButton;
    private LOTRGuiButtonOptions selfProtectionButton;
    private GuiButton buttonAddSlot;
    private GuiButton buttonRemoveSlot;
    private GuiTextField alignmentField;
    private static final int displayedPlayers = 5;
    private GuiTextField[] allowedPlayers;
    private boolean[] invalidUsernames;
    private boolean[] checkUsernames;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasMouseDown;
    private int scrollBarWidth;
    private int scrollBarHeight;
    private int scrollBarX;
    private int scrollBarY;
    private int scrollBarBorder;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    
    public LOTRGuiBanner(final LOTREntityBanner banner) {
        this.xSize = 200;
        this.ySize = 250;
        this.firstInit = true;
        this.allowedPlayers = new GuiTextField[0];
        this.invalidUsernames = new boolean[0];
        this.checkUsernames = new boolean[0];
        this.currentScroll = 0.0f;
        this.isScrolling = false;
        this.scrollBarWidth = 14;
        this.scrollBarHeight = 120;
        this.scrollBarX = 180;
        this.scrollBarY = 68;
        this.scrollBarBorder = 1;
        this.scrollWidgetWidth = 12;
        this.scrollWidgetHeight = 17;
        this.theBanner = banner;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(this.modeButton = new GuiButton(0, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 20, 160, 20, ""));
        super.buttonList.add(this.selfProtectionButton = new LOTRGuiButtonOptions(1, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 220, 160, 20, StatCollector.translateToLocal("lotr.gui.bannerEdit.selfProtection")));
        super.buttonList.add(this.buttonAddSlot = new LOTRGuiBannerButton(0, this.guiLeft + 179, this.guiTop + 190));
        super.buttonList.add(this.buttonRemoveSlot = new LOTRGuiBannerButton(1, this.guiLeft + 187, this.guiTop + 190));
        (this.alignmentField = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 - 70, this.guiTop + 100, 140, 20)).setText(String.valueOf(this.theBanner.getAlignmentProtection()));
        this.alignmentField.func_146184_c(false);
        this.refreshWhitelist();
        for (int i = 0; i < this.allowedPlayers.length; ++i) {
            final GuiTextField textBox = this.allowedPlayers[i];
            final GameProfile profile = this.theBanner.getWhitelistedPlayer(i);
            if (profile != null) {
                final String name = profile.getName();
                if (!StringUtils.isNullOrEmpty(name)) {
                    textBox.setText(name);
                }
            }
            this.allowedPlayers[i] = textBox;
        }
        this.allowedPlayers[0].func_146184_c(false);
        if (this.firstInit) {
            this.firstInit = false;
        }
        Arrays.fill(this.checkUsernames, false);
    }
    
    private void refreshWhitelist() {
        final int length = this.theBanner.getWhitelistLength();
        final GuiTextField[] allowedPlayers_new = new GuiTextField[length];
        final boolean[] invalidUsernames_new = new boolean[length];
        final boolean[] checkUsernames_new = new boolean[length];
        for (int i = 0; i < length; ++i) {
            if (i < this.allowedPlayers.length) {
                allowedPlayers_new[i] = this.allowedPlayers[i];
            }
            else {
                allowedPlayers_new[i] = new GuiTextField(super.fontRendererObj, 0, 0, 140, 20);
            }
            if (i < this.invalidUsernames.length) {
                invalidUsernames_new[i] = this.invalidUsernames[i];
            }
            if (i < this.checkUsernames.length) {
                checkUsernames_new[i] = this.checkUsernames[i];
            }
        }
        this.allowedPlayers = allowedPlayers_new;
        this.invalidUsernames = invalidUsernames_new;
        this.checkUsernames = checkUsernames_new;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.setupScrollBar(i, j);
        this.alignmentField.func_146189_e(false);
        this.alignmentField.func_146184_c(false);
        for (int l = 0; l < this.allowedPlayers.length; ++l) {
            final GuiTextField textBox = this.allowedPlayers[l];
            textBox.func_146189_e(false);
            textBox.func_146184_c(false);
        }
        this.drawDefaultBackground();
        super.mc.getTextureManager().bindTexture(LOTRGuiBanner.guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final String title = StatCollector.translateToLocal("lotr.gui.bannerEdit.title");
        super.fontRendererObj.drawString(title, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(title) / 2, this.guiTop + 6, 4210752);
        if (this.theBanner.isPlayerSpecificProtection()) {
            this.modeButton.displayString = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific");
            String s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.1");
            super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 46, 4210752);
            s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.2");
            super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 46 + super.fontRendererObj.FONT_HEIGHT, 4210752);
            s = LOTRFellowshipProfile.getFellowshipCodeHint();
            super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 200, 4210752);
            int start = 0 + Math.round(this.currentScroll * (this.allowedPlayers.length - 5));
            int end = start + 5 - 1;
            start = Math.max(start, 0);
            end = Math.min(end, this.allowedPlayers.length - 1);
            for (int index = start; index <= end; ++index) {
                final int displayIndex = index - start;
                final GuiTextField textBox2 = this.allowedPlayers[index];
                textBox2.func_146189_e(true);
                textBox2.func_146184_c(index != 0);
                textBox2.field_146209_f = this.guiLeft + this.xSize / 2 - 70;
                textBox2.field_146210_g = this.guiTop + 70 + displayIndex * (textBox2.field_146219_i + 4);
                textBox2.drawTextBox();
                final String number = index + 1 + ".";
                super.fontRendererObj.drawString(number, this.guiLeft + 24 - super.fontRendererObj.getStringWidth(number), textBox2.field_146210_g + 6, 4210752);
            }
            if (this.hasScrollBar()) {
                super.mc.getTextureManager().bindTexture(LOTRGuiBanner.guiTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 200, 0, this.scrollBarWidth, this.scrollBarHeight);
                if (this.canScroll()) {
                    final int scroll = (int)(this.currentScroll * (this.scrollBarHeight - this.scrollBarBorder * 2 - this.scrollWidgetHeight));
                    this.drawTexturedModalRect(this.guiLeft + this.scrollBarX + this.scrollBarBorder, this.guiTop + this.scrollBarY + this.scrollBarBorder + scroll, 214, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
                }
            }
        }
        else {
            this.modeButton.displayString = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction");
            String s = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.protectionMode.faction.desc.1", new Object[0]);
            super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 46, 4210752);
            s = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.protectionMode.faction.desc.2", new Object[] { this.theBanner.getAlignmentProtection(), this.theBanner.getBannerType().faction.factionName() });
            super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 46 + super.fontRendererObj.FONT_HEIGHT, 4210752);
            s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction.desc.3");
            super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 46 + super.fontRendererObj.FONT_HEIGHT * 2, 4210752);
            s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction.alignment");
            super.fontRendererObj.drawString(s, this.alignmentField.field_146209_f, this.alignmentField.field_146210_g - super.fontRendererObj.FONT_HEIGHT - 3, 4210752);
            this.alignmentField.func_146189_e(true);
            this.alignmentField.func_146184_c(true);
            this.alignmentField.drawTextBox();
        }
        super.drawScreen(i, j, f);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.selfProtectionButton.setState(this.theBanner.isSelfProtection());
        final GuiButton buttonAddSlot = this.buttonAddSlot;
        final GuiButton buttonRemoveSlot = this.buttonRemoveSlot;
        final boolean playerSpecificProtection = this.theBanner.isPlayerSpecificProtection();
        buttonRemoveSlot.field_146125_m = playerSpecificProtection;
        buttonAddSlot.field_146125_m = playerSpecificProtection;
        this.buttonAddSlot.enabled = (this.theBanner.getWhitelistLength() < LOTREntityBanner.WHITELIST_MAX);
        this.buttonRemoveSlot.enabled = (this.theBanner.getWhitelistLength() > LOTREntityBanner.WHITELIST_MIN);
        this.alignmentField.updateCursorCounter();
        this.alignmentField.func_146189_e(!this.theBanner.isPlayerSpecificProtection());
        this.alignmentField.func_146184_c(this.alignmentField.func_146176_q());
        if (this.alignmentField.func_146176_q() && !this.alignmentField.isFocused()) {
            final float prevAlignment = this.theBanner.getAlignmentProtection();
            float alignment;
            try {
                alignment = Float.parseFloat(this.alignmentField.getText());
            }
            catch (NumberFormatException e) {
                alignment = 0.0f;
            }
            alignment = Math.max(alignment, LOTREntityBanner.ALIGNMENT_PROTECTION_MIN);
            alignment = Math.min(alignment, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
            this.theBanner.setAlignmentProtection(alignment);
            this.alignmentField.setText(LOTRAlignmentValues.formatAlignForDisplay(alignment));
            if (alignment != prevAlignment) {
                this.sendBannerData(false);
            }
        }
        for (int l = 0; l < this.allowedPlayers.length; ++l) {
            final GuiTextField textBox = this.allowedPlayers[l];
            textBox.updateCursorCounter();
        }
    }
    
    private void setupScrollBar(final int i, final int j) {
        final boolean isMouseDown = Mouse.isButtonDown(0);
        final int i2 = this.guiLeft + this.scrollBarX;
        final int j2 = this.guiTop + this.scrollBarY;
        final int i3 = i2 + this.scrollBarWidth;
        final int j3 = j2 + this.scrollBarHeight;
        if (!this.wasMouseDown && isMouseDown && i >= i2 && j >= j2 && i < i3 && j < j3) {
            this.isScrolling = this.canScroll();
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - j2 - this.scrollWidgetHeight / 2.0f) / (j3 - j2 - (float)this.scrollWidgetHeight);
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            }
            if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
        }
    }
    
    private boolean hasScrollBar() {
        return this.theBanner.isPlayerSpecificProtection();
    }
    
    private boolean canScroll() {
        return true;
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (this.alignmentField.func_146176_q() && this.alignmentField.textboxKeyTyped(c, i)) {
            return;
        }
        for (int l = 1; l < this.allowedPlayers.length; ++l) {
            final GuiTextField textBox = this.allowedPlayers[l];
            if (textBox.func_146176_q() && textBox.textboxKeyTyped(c, i)) {
                this.checkUsernames[l] = true;
                return;
            }
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        if (this.alignmentField.func_146176_q()) {
            this.alignmentField.mouseClicked(i, j, k);
        }
        for (int l = 1; l < this.allowedPlayers.length; ++l) {
            final GuiTextField textBox = this.allowedPlayers[l];
            if (textBox.func_146176_q()) {
                textBox.mouseClicked(i, j, k);
                if (!textBox.isFocused() && this.checkUsernames[l]) {
                    this.checkUsernameValid(l);
                    this.checkUsernames[l] = false;
                }
                if (textBox.isFocused() && this.invalidUsernames[l]) {
                    this.invalidUsernames[l] = false;
                    textBox.func_146193_g(16777215);
                    textBox.setText("");
                }
            }
        }
    }
    
    private void checkUsernameValid(final int index) {
        final GuiTextField textBox = this.allowedPlayers[index];
        final String username = textBox.getText();
        if (!StringUtils.isNullOrEmpty(username) && !this.invalidUsernames[index]) {
            final LOTRPacketBannerRequestInvalidName packet = new LOTRPacketBannerRequestInvalidName(this.theBanner, index, username);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    public void setUsernameInvalid(final int index, final String prevText) {
        final GuiTextField textBox = this.allowedPlayers[index];
        final String text = textBox.getText();
        if (text.equals(prevText)) {
            this.invalidUsernames[index] = true;
            textBox.func_146193_g(16711680);
            textBox.setText(StatCollector.translateToLocal("lotr.gui.bannerEdit.invalidUsername"));
        }
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0 && this.canScroll()) {
            final int j = this.allowedPlayers.length - 5;
            if (i > 0) {
                i = 1;
            }
            if (i < 0) {
                i = -1;
            }
            this.currentScroll -= (float)(i / (double)j);
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            }
            if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.modeButton) {
                this.theBanner.setPlayerSpecificProtection(!this.theBanner.isPlayerSpecificProtection());
            }
            if (button == this.selfProtectionButton) {
                this.theBanner.setSelfProtection(!this.theBanner.isSelfProtection());
            }
            if (button == this.buttonAddSlot) {
                this.theBanner.resizeWhitelist(this.theBanner.getWhitelistLength() + 1);
                this.refreshWhitelist();
            }
            if (button == this.buttonRemoveSlot) {
                this.theBanner.resizeWhitelist(this.theBanner.getWhitelistLength() - 1);
                this.refreshWhitelist();
            }
        }
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        this.sendBannerData(true);
    }
    
    private void sendBannerData(final boolean sendNames) {
        final LOTRPacketEditBanner packet = new LOTRPacketEditBanner(this.theBanner);
        packet.playerSpecificProtection = this.theBanner.isPlayerSpecificProtection();
        packet.selfProtection = this.theBanner.isSelfProtection();
        packet.alignmentProtection = this.theBanner.getAlignmentProtection();
        packet.whitelistLength = this.theBanner.getWhitelistLength();
        if (sendNames) {
            final String[] whitelistSlots = new String[this.allowedPlayers.length];
            for (int index = 1; index < this.allowedPlayers.length; ++index) {
                final String text = this.allowedPlayers[index].getText();
                if (StringUtils.isNullOrEmpty(text) || this.invalidUsernames[index]) {
                    this.theBanner.whitelistPlayer(index, null);
                }
                else if (LOTRFellowshipProfile.hasFellowshipCode(text)) {
                    this.theBanner.whitelistPlayer(index, new LOTRFellowshipProfile(this.theBanner, null, LOTRFellowshipProfile.stripFellowshipCode(text)));
                }
                else {
                    this.theBanner.whitelistPlayer(index, new GameProfile((UUID)null, text));
                }
                final GameProfile profile = this.theBanner.getWhitelistedPlayer(index);
                if (profile == null) {
                    whitelistSlots[index] = null;
                }
                else {
                    final String username = profile.getName();
                    if (StringUtils.isNullOrEmpty(username)) {
                        whitelistSlots[index] = null;
                    }
                    else {
                        whitelistSlots[index] = username;
                    }
                }
            }
            packet.whitelistSlots = whitelistSlots;
        }
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    static {
        LOTRGuiBanner.guiTexture = new ResourceLocation("lotr:gui/banner_edit.png");
    }
}
