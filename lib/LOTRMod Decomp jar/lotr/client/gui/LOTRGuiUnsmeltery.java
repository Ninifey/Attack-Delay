// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerUnsmeltery;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiUnsmeltery extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTRTileEntityUnsmeltery theUnsmeltery;
    
    public LOTRGuiUnsmeltery(final InventoryPlayer inv, final LOTRTileEntityUnsmeltery unsmeltery) {
        super((Container)new LOTRContainerUnsmeltery(inv, unsmeltery));
        this.theUnsmeltery = unsmeltery;
        super.field_147000_g = 176;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = this.theUnsmeltery.getInventoryName();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 72, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiUnsmeltery.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theUnsmeltery.isSmelting()) {
            final int k = this.theUnsmeltery.getSmeltTimeRemainingScaled(13);
            this.drawTexturedModalRect(super.field_147003_i + 56, super.field_147009_r + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        final int l = this.theUnsmeltery.getSmeltProgressScaled(24);
        this.drawTexturedModalRect(super.field_147003_i + 79, super.field_147009_r + 34, 176, 14, l + 1, 16);
    }
    
    static {
        LOTRGuiUnsmeltery.guiTexture = new ResourceLocation("lotr:gui/unsmelter.png");
    }
}
