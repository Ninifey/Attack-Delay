// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerAlloyForge;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiAlloyForge extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTRTileEntityAlloyForgeBase theForge;
    
    public LOTRGuiAlloyForge(final InventoryPlayer inv, final LOTRTileEntityAlloyForgeBase forge) {
        super((Container)new LOTRContainerAlloyForge(inv, forge));
        this.theForge = forge;
        super.field_147000_g = 233;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = this.theForge.getInventoryName();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 139, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiAlloyForge.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theForge.isSmelting()) {
            final int k = this.theForge.getSmeltTimeRemainingScaled(12);
            this.drawTexturedModalRect(super.field_147003_i + 80, super.field_147009_r + 112 + 12 - k, 176, 12 - k, 14, k + 2);
        }
        final int l = this.theForge.getSmeltProgressScaled(24);
        this.drawTexturedModalRect(super.field_147003_i + 80, super.field_147009_r + 58, 176, 14, 16, l + 1);
    }
    
    static {
        LOTRGuiAlloyForge.guiTexture = new ResourceLocation("lotr:gui/forge.png");
    }
}
