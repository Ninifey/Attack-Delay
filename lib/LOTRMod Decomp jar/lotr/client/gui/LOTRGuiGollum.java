// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerGollum;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiGollum extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTREntityGollum theGollum;
    
    public LOTRGuiGollum(final InventoryPlayer inv, final LOTREntityGollum gollum) {
        super((Container)new LOTRContainerGollum(inv, gollum));
        this.theGollum = gollum;
        super.field_147000_g = 168;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = this.theGollum.getCommandSenderName();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 74, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiGollum.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
    }
    
    static {
        LOTRGuiGollum.guiTexture = new ResourceLocation("lotr:gui/npc/gollum.png");
    }
}
