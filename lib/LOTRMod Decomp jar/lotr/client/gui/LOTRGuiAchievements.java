// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;
import java.util.List;
import com.google.common.math.IntMath;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import java.util.ArrayList;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDimension;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiAchievements extends LOTRGuiMenuBase
{
    public static ResourceLocation pageTexture;
    public static ResourceLocation iconsTexture;
    private static LOTRDimension currentDimension;
    private static LOTRDimension prevDimension;
    private static LOTRAchievement.Category currentCategory;
    private ArrayList currentCategoryTakenAchievements;
    private ArrayList currentCategoryUntakenAchievements;
    private int currentCategoryTakenCount;
    private int currentCategoryUntakenCount;
    private LOTRGuiButtonAchievements buttonCategoryPrev;
    private LOTRGuiButtonAchievements buttonCategoryNext;
    private int totalTakenCount;
    private int totalAvailableCount;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasMouseDown;
    private static final int scrollBarWidth = 12;
    private static final int scrollBarHeight = 200;
    private static final int scrollWidgetWidth = 10;
    private static final int scrollWidgetHeight = 17;
    private static final int catScrollWidth = 152;
    private static final int catScrollHeight = 10;
    private int catScrollAreaX0;
    private int catScrollAreaX1;
    private int catScrollAreaY0;
    private int catScrollAreaY1;
    private boolean wasInCategoryScrollBar;
    private int prevMouseX;
    private int prevMouseY;
    private int mouseX;
    private int mouseY;
    
    public LOTRGuiAchievements() {
        this.currentCategoryTakenAchievements = new ArrayList();
        this.currentCategoryUntakenAchievements = new ArrayList();
        this.currentScroll = 0.0f;
        this.isScrolling = false;
    }
    
    @Override
    public void initGui() {
        super.xSize = 220;
        super.initGui();
        super.buttonList.add(this.buttonCategoryPrev = new LOTRGuiButtonAchievements(0, true, super.guiLeft + 14, super.guiTop + 13));
        super.buttonList.add(this.buttonCategoryNext = new LOTRGuiButtonAchievements(1, false, super.guiLeft + 191, super.guiTop + 13));
        this.updateAchievementLists();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateAchievementLists();
        this.prevMouseX = this.mouseX;
        this.prevMouseY = this.mouseY;
        this.wasInCategoryScrollBar = this.isMouseInCategoryScrollBar();
    }
    
    private boolean isMouseInCategoryScrollBar() {
        return Mouse.isButtonDown(0) && this.mouseX >= this.catScrollAreaX0 && this.mouseX < this.catScrollAreaX1 && this.mouseY >= this.catScrollAreaY0 && this.mouseY < this.catScrollAreaY1;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.mouseX = i;
        this.mouseY = j;
        if (this.wasInCategoryScrollBar) {
            final int diff = this.mouseX - this.prevMouseX;
            boolean changed = false;
            if (diff >= 4) {
                this.prevCategory();
                changed = true;
            }
            else if (diff <= -4) {
                this.nextCategory();
                changed = true;
            }
            if (changed) {
                this.mouseX = this.prevMouseX;
                this.wasInCategoryScrollBar = false;
            }
        }
        final boolean isMouseDown = Mouse.isButtonDown(0);
        final int scrollBarX0 = super.guiLeft + 201;
        final int scrollBarX2 = scrollBarX0 + 12;
        final int scrollBarY0 = super.guiTop + 48;
        final int scrollBarY2 = scrollBarY0 + 200;
        if (!this.wasMouseDown && isMouseDown && i >= scrollBarX0 && i < scrollBarX2 && j >= scrollBarY0 && j < scrollBarY2) {
            this.isScrolling = this.hasScrollBar();
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - scrollBarY0 - 8.5f) / (scrollBarY2 - scrollBarY0 - 17.0f);
            this.currentScroll = Math.max(this.currentScroll, 0.0f);
            this.currentScroll = Math.min(this.currentScroll, 1.0f);
        }
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.pageTexture);
        this.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, super.xSize, super.ySize);
        final String title = StatCollector.translateToLocalFormatted("lotr.gui.achievements.title", new Object[] { LOTRGuiAchievements.currentDimension.getDimensionName(), this.totalTakenCount, this.totalAvailableCount });
        this.drawCenteredString(title, super.guiLeft + super.xSize / 2, super.guiTop - 30, 16777215);
        String categoryName = LOTRGuiAchievements.currentCategory.getDisplayName();
        categoryName = StatCollector.translateToLocalFormatted("lotr.gui.achievements.category", new Object[] { categoryName, this.currentCategoryTakenCount, this.currentCategoryTakenCount + this.currentCategoryUntakenCount });
        this.drawCenteredString(categoryName, super.guiLeft + super.xSize / 2, super.guiTop + 28, 8019267);
        this.buttonCategoryPrev.buttonCategory = this.getCategoryAtRelativeIndex(-1);
        this.buttonCategoryNext.buttonCategory = this.getCategoryAtRelativeIndex(1);
        super.drawScreen(i, j, f);
        final int catScrollCentre = super.guiLeft + super.xSize / 2;
        final int catScrollX = catScrollCentre - 76;
        final int catScrollY = super.guiTop + 13;
        super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
        this.drawTexturedModalRect(catScrollX, catScrollY, 0, 100, 152, 10);
        this.catScrollAreaX0 = catScrollX;
        this.catScrollAreaX1 = catScrollX + 152;
        this.catScrollAreaY0 = catScrollY;
        this.catScrollAreaY1 = catScrollY + 10;
        final int catWidth = 16;
        final int catCentreWidth = 50;
        for (int catsEitherSide = (this.catScrollAreaX1 - this.catScrollAreaX0) / catWidth + 1, l = -catsEitherSide; l <= catsEitherSide; ++l) {
            final int thisCatWidth = (l == 0) ? catCentreWidth : catWidth;
            int catX = catScrollCentre;
            if (l != 0) {
                final int signum = Integer.signum(l);
                catX += (catCentreWidth + catWidth) / 2 * signum;
                catX += (Math.abs(l) - 1) * signum * catWidth;
            }
            int catX2 = catX - thisCatWidth / 2;
            int catX3 = catX + thisCatWidth;
            if (catX2 < this.catScrollAreaX0) {
                catX2 = this.catScrollAreaX0;
            }
            if (catX3 > this.catScrollAreaX1) {
                catX3 = this.catScrollAreaX1;
            }
            final int catY0 = this.catScrollAreaY0;
            final int catY2 = this.catScrollAreaY1;
            final LOTRAchievement.Category thisCategory = this.getCategoryAtRelativeIndex(l);
            final float[] catColors = thisCategory.getCategoryRGB();
            super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
            GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
            this.drawTexturedModalRect(catX2, catY0, catX2 - this.catScrollAreaX0 + 0, 100, catX3 - catX2, catY2 - catY0);
        }
        super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
        this.drawTexturedModalRect(catScrollX, catScrollY, 0, 110, 152, 10);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
        if (this.hasScrollBar()) {
            final int offset = (int)(this.currentScroll * 181.0f);
            this.drawTexturedModalRect(scrollBarX0, scrollBarY0 + offset, 190, 0, 10, 17);
        }
        else {
            this.drawTexturedModalRect(scrollBarX0, scrollBarY0, 200, 0, 10, 17);
        }
        this.drawAchievements(i, j);
    }
    
    private void drawAchievements(final int mouseX, final int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        final int size = this.currentCategoryTakenCount + this.currentCategoryUntakenCount;
        final int min = 0 + Math.round(this.currentScroll * (size - 4));
        int max = 3 + Math.round(this.currentScroll * (size - 4));
        if (max > size - 1) {
            max = size - 1;
        }
        for (int i = min; i <= max; ++i) {
            LOTRAchievement achievement;
            boolean hasAchievement;
            if (i < this.currentCategoryTakenCount) {
                achievement = this.currentCategoryTakenAchievements.get(i);
                hasAchievement = true;
            }
            else {
                achievement = this.currentCategoryUntakenAchievements.get(i - this.currentCategoryTakenCount);
                hasAchievement = false;
            }
            final int offset = 47 + 50 * (i - min);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
            this.drawTexturedModalRect(super.guiLeft + 9, super.guiTop + offset, 0, hasAchievement ? 0 : 50, 190, 50);
            final int iconLeft = super.guiLeft + 12;
            final int iconTop = super.guiTop + offset + 3;
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            LOTRGuiMenuBase.renderItem.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.getTextureManager(), achievement.icon, iconLeft, iconTop);
            GL11.glDisable(2896);
            if (!hasAchievement) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 300.0f);
                drawRect(iconLeft, iconTop, iconLeft + 16, iconTop + 16, -2013265920);
                GL11.glPopMatrix();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final int textColour = hasAchievement ? 8019267 : 5652783;
            super.mc.fontRenderer.drawString(achievement.getTitle((EntityPlayer)super.mc.thePlayer), super.guiLeft + 33, super.guiTop + offset + 5, textColour);
            super.mc.fontRenderer.drawSplitString(achievement.getDescription((EntityPlayer)super.mc.thePlayer), super.guiLeft + 12, super.guiTop + offset + 24, 184, textColour);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (hasAchievement) {
                super.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
                this.drawTexturedModalRect(super.guiLeft + 179, super.guiTop + offset + 2, 190, 17, 16, 16);
            }
        }
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonCategoryPrev) {
                this.prevCategory();
            }
            else if (button == this.buttonCategoryNext) {
                this.nextCategory();
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    private LOTRAchievement.Category getCategoryAtRelativeIndex(final int i) {
        final List<LOTRAchievement.Category> categories = LOTRGuiAchievements.currentDimension.achievementCategories;
        int index = categories.indexOf(LOTRGuiAchievements.currentCategory);
        index += i;
        index = IntMath.mod(index, LOTRGuiAchievements.currentDimension.achievementCategories.size());
        return LOTRGuiAchievements.currentDimension.achievementCategories.get(index);
    }
    
    private void prevCategory() {
        LOTRGuiAchievements.currentCategory = this.getCategoryAtRelativeIndex(-1);
        this.currentScroll = 0.0f;
    }
    
    private void nextCategory() {
        LOTRGuiAchievements.currentCategory = this.getCategoryAtRelativeIndex(1);
        this.currentScroll = 0.0f;
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0 && this.hasScrollBar()) {
            final int j = this.currentCategoryTakenCount + this.currentCategoryUntakenCount - 4;
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
    
    private boolean hasScrollBar() {
        return this.currentCategoryTakenCount + this.currentCategoryUntakenCount > 4;
    }
    
    private void updateAchievementLists() {
        LOTRGuiAchievements.currentDimension = LOTRDimension.getCurrentDimension((World)super.mc.theWorld);
        if (LOTRGuiAchievements.currentDimension != LOTRGuiAchievements.prevDimension) {
            LOTRGuiAchievements.currentCategory = LOTRGuiAchievements.currentDimension.achievementCategories.get(0);
        }
        LOTRGuiAchievements.prevDimension = LOTRGuiAchievements.currentDimension;
        this.currentCategoryTakenAchievements.clear();
        this.currentCategoryUntakenAchievements.clear();
        for (final LOTRAchievement achievement : LOTRGuiAchievements.currentCategory.list) {
            if (achievement.canPlayerEarn((EntityPlayer)super.mc.thePlayer)) {
                if (LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).hasAchievement(achievement)) {
                    this.currentCategoryTakenAchievements.add(achievement);
                }
                else {
                    this.currentCategoryUntakenAchievements.add(achievement);
                }
            }
        }
        this.currentCategoryTakenCount = this.currentCategoryTakenAchievements.size();
        this.currentCategoryUntakenCount = this.currentCategoryUntakenAchievements.size();
        this.totalTakenCount = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getEarnedAchievements(LOTRGuiAchievements.currentDimension).size();
        this.totalAvailableCount = 0;
        for (final LOTRAchievement achievement : LOTRGuiAchievements.currentDimension.allAchievements) {
            if (achievement.canPlayerEarn((EntityPlayer)super.mc.thePlayer)) {
                ++this.totalAvailableCount;
            }
        }
        final Comparator<LOTRAchievement> sorter = LOTRAchievement.sortForDisplay((EntityPlayer)super.mc.thePlayer);
        Collections.sort((List<Object>)this.currentCategoryTakenAchievements, (Comparator<? super Object>)sorter);
        Collections.sort((List<Object>)this.currentCategoryUntakenAchievements, (Comparator<? super Object>)sorter);
    }
    
    static {
        LOTRGuiAchievements.pageTexture = new ResourceLocation("lotr:gui/achievements/page.png");
        LOTRGuiAchievements.iconsTexture = new ResourceLocation("lotr:gui/achievements/icons.png");
    }
}
