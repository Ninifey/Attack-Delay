// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerMillstone;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiMillstone extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTRTileEntityMillstone theMillstone;
    
    public LOTRGuiMillstone(final InventoryPlayer inv, final LOTRTileEntityMillstone millstone) {
        super((Container)new LOTRContainerMillstone(inv, millstone));
        this.theMillstone = millstone;
        super.field_147000_g = 182;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = this.theMillstone.getInventoryName();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 88, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiMillstone.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theMillstone.isMilling()) {
            final int k = this.theMillstone.getMillProgressScaled(14);
            this.drawTexturedModalRect(super.field_147003_i + 85, super.field_147009_r + 47, 176, 0, 14, k);
        }
    }
    
    static {
        LOTRGuiMillstone.guiTexture = new ResourceLocation("lotr:gui/millstone.png");
    }
}
