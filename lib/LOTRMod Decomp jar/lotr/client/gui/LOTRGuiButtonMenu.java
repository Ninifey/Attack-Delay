// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonMenu extends GuiButton
{
    private LOTRGuiMenu parentGUI;
    private Class<? extends LOTRGuiMenuBase> menuScreenClass;
    public final int menuKeyCode;
    
    public LOTRGuiButtonMenu(final LOTRGuiMenu gui, final int i, final int x, final int y, final Class<? extends LOTRGuiMenuBase> cls, final String s, final int key) {
        super(i, x, y, 32, 32, s);
        this.parentGUI = gui;
        this.menuScreenClass = cls;
        this.menuKeyCode = key;
    }
    
    public LOTRGuiMenuBase openMenu() {
        try {
            return (LOTRGuiMenuBase)this.menuScreenClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean canDisplayMenu() {
        if (this.menuScreenClass == LOTRGuiMap.class) {
            final World world = (World)Minecraft.getMinecraft().theWorld;
            return world != null && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
        }
        return true;
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiMenu.menuIconsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, 0 + (super.enabled ? 0 : (super.field_146120_f * 2)) + (super.field_146123_n ? super.field_146120_f : 0), super.id * super.field_146121_g, super.field_146120_f, super.field_146121_g);
            this.mouseDragged(mc, i, j);
        }
    }
}
