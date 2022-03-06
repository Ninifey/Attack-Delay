// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import lotr.common.quest.LOTRMiniQuestWelcome;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;
import net.minecraft.world.World;
import lotr.common.LOTRDimension;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.util.StatCollector;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiMenu extends LOTRGuiScreenBase
{
    public static final ResourceLocation menuIconsTexture;
    public static Class<? extends LOTRGuiMenuBase> lastMenuScreen;
    
    public void initGui() {
        super.initGui();
        resetLastMenuScreen();
        final int midX = super.width / 2;
        final int midY = super.height / 2;
        final int buttonGap = 10;
        final int buttonSize = 32;
        super.buttonList.add(new LOTRGuiButtonMenu(this, 2, 0, 0, LOTRGuiAchievements.class, StatCollector.translateToLocal("lotr.gui.achievements"), 30));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 3, 0, 0, LOTRGuiMap.class, StatCollector.translateToLocal("lotr.gui.map"), 50));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 4, 0, 0, LOTRGuiFactions.class, StatCollector.translateToLocal("lotr.gui.factions"), 33));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 0, 0, 0, null, "?", -1));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 6, 0, 0, LOTRGuiFellowships.class, StatCollector.translateToLocal("lotr.gui.fellowships"), 25));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 7, 0, 0, LOTRGuiTitles.class, StatCollector.translateToLocal("lotr.gui.titles"), 20));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 5, 0, 0, LOTRGuiShields.class, StatCollector.translateToLocal("lotr.gui.shields"), 31));
        super.buttonList.add(new LOTRGuiButtonMenu(this, 1, 0, 0, LOTRGuiOptions.class, StatCollector.translateToLocal("lotr.gui.options"), 24));
        final List<LOTRGuiButtonMenu> menuButtons = new ArrayList<LOTRGuiButtonMenu>();
        for (final Object obj : super.buttonList) {
            if (obj instanceof LOTRGuiButtonMenu) {
                final LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)obj;
                button.enabled = button.canDisplayMenu();
                menuButtons.add(button);
            }
        }
        final int numButtons = menuButtons.size();
        final int numTopRowButtons = (numButtons - 1) / 2 + 1;
        final int numBtmRowButtons = numButtons - numTopRowButtons;
        final int topRowLeft = midX - (numTopRowButtons * buttonSize + (numTopRowButtons - 1) * buttonGap) / 2;
        final int btmRowLeft = midX - (numBtmRowButtons * buttonSize + (numBtmRowButtons - 1) * buttonGap) / 2;
        for (int l = 0; l < numButtons; ++l) {
            final LOTRGuiButtonMenu button2 = menuButtons.get(l);
            if (l < numTopRowButtons) {
                button2.field_146128_h = topRowLeft + l * (buttonSize + buttonGap);
                button2.field_146129_i = midY - buttonGap / 2 - buttonSize;
            }
            else {
                button2.field_146128_h = btmRowLeft + (l - numTopRowButtons) * (buttonSize + buttonGap);
                button2.field_146129_i = midY + buttonGap / 2;
            }
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        final String title = StatCollector.translateToLocalFormatted("lotr.gui.menu", new Object[] { LOTRDimension.getCurrentDimension((World)super.mc.theWorld).getDimensionName() });
        super.fontRendererObj.drawStringWithShadow(title, super.width / 2 - super.fontRendererObj.getStringWidth(title) / 2, super.height / 2 - 80, 16777215);
        super.drawScreen(i, j, f);
        for (final Object obj : super.buttonList) {
            if (obj instanceof LOTRGuiButtonMenu) {
                final LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)obj;
                if (!button.func_146115_a() || button.displayString == null) {
                    continue;
                }
                final float z = ((Gui)this).zLevel;
                final int stringWidth = 200;
                this.func_146279_a(button.displayString, i, j);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                ((Gui)this).zLevel = z;
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        for (final Object obj : super.buttonList) {
            if (obj instanceof LOTRGuiButtonMenu) {
                final LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)obj;
                if (button.field_146125_m && button.enabled && button.menuKeyCode >= 0 && i == button.menuKeyCode) {
                    this.actionPerformed(button);
                    return;
                }
                continue;
            }
        }
        super.keyTyped(c, i);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button instanceof LOTRGuiButtonMenu) {
            final LOTRGuiButtonMenu buttonMenu = (LOTRGuiButtonMenu)button;
            final LOTRGuiMenuBase screen = buttonMenu.openMenu();
            if (screen != null) {
                super.mc.displayGuiScreen((GuiScreen)screen);
                LOTRGuiMenu.lastMenuScreen = screen.getClass();
                return;
            }
        }
        super.actionPerformed(button);
    }
    
    public static void resetLastMenuScreen() {
        LOTRGuiMenu.lastMenuScreen = null;
    }
    
    public static GuiScreen openMenu(final EntityPlayer entityplayer) {
        final boolean[] map_factions = LOTRMiniQuestWelcome.forceMenu_Map_Factions(entityplayer);
        if (map_factions[0]) {
            return new LOTRGuiMap();
        }
        if (map_factions[1]) {
            return new LOTRGuiFactions();
        }
        if (LOTRGuiMenu.lastMenuScreen != null) {
            try {
                return (GuiScreen)LOTRGuiMenu.lastMenuScreen.newInstance();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new LOTRGuiMenu();
    }
    
    static {
        menuIconsTexture = new ResourceLocation("lotr:gui/menu_icons.png");
        LOTRGuiMenu.lastMenuScreen = null;
    }
}
