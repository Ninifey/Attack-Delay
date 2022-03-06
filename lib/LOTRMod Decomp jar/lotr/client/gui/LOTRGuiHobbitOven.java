// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerHobbitOven;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiHobbitOven extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTRTileEntityHobbitOven theOven;
    
    public LOTRGuiHobbitOven(final InventoryPlayer inv, final LOTRTileEntityHobbitOven oven) {
        super((Container)new LOTRContainerHobbitOven(inv, oven));
        this.theOven = oven;
        super.field_147000_g = 215;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = this.theOven.getInventoryName();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 121, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiHobbitOven.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theOven.isCooking()) {
            final int k = this.theOven.getCookTimeRemainingScaled(12);
            this.drawTexturedModalRect(super.field_147003_i + 80, super.field_147009_r + 94 + 12 - k, 176, 12 - k, 14, k + 2);
        }
        final int l = this.theOven.getCookProgressScaled(24);
        this.drawTexturedModalRect(super.field_147003_i + 80, super.field_147009_r + 40, 176, 14, 16, l + 1);
    }
    
    static {
        LOTRGuiHobbitOven.guiTexture = new ResourceLocation("lotr:gui/oven.png");
    }
}
