// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBrewingButton;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerBarrel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.gui.GuiButton;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiBarrel extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private static ResourceLocation brewingTexture;
    private LOTRTileEntityBarrel theBarrel;
    private GuiButton brewingButton;
    private float prevBrewAnim;
    private float brewAnim;
    
    public LOTRGuiBarrel(final InventoryPlayer inv, final LOTRTileEntityBarrel barrel) {
        super((Container)new LOTRContainerBarrel(inv, barrel));
        this.prevBrewAnim = -1.0f;
        this.brewAnim = -1.0f;
        this.theBarrel = barrel;
        super.field_146999_f = 210;
        super.field_147000_g = 221;
    }
    
    public void initGui() {
        super.initGui();
        ((GuiScreen)this).buttonList.add(this.brewingButton = new GuiButton(0, super.field_147003_i + 25, super.field_147009_r + 97, 100, 20, StatCollector.translateToLocal("container.lotr.barrel.startBrewing")));
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        if (this.theBarrel.barrelMode == 0) {
            this.brewingButton.enabled = (this.theBarrel.getStackInSlot(9) != null);
            this.brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.startBrewing");
        }
        if (this.theBarrel.barrelMode == 1) {
            this.brewingButton.enabled = (this.theBarrel.getStackInSlot(9) != null && this.theBarrel.getStackInSlot(9).getItemDamage() > 0);
            this.brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.stopBrewing");
        }
        if (this.theBarrel.barrelMode == 2) {
            this.brewingButton.enabled = false;
            this.brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.startBrewing");
        }
        super.drawScreen(i, j, f);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button.id == 0) {
            final LOTRPacketBrewingButton packet = new LOTRPacketBrewingButton();
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = this.theBarrel.getInventoryName();
        final String s2 = this.theBarrel.getInvSubtitle();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(s2, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s2) / 2, 17, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 25, 127, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiBarrel.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        final int brewMode = this.theBarrel.barrelMode;
        int fullAmount = this.theBarrel.getBarrelFullAmountScaled(96);
        if (brewMode == 1) {
            fullAmount = this.theBarrel.getBrewProgressScaled(96);
        }
        this.prevBrewAnim = this.brewAnim;
        this.brewAnim = this.theBarrel.getBrewAnimationProgressScaledF(97, f);
        final float brewAnimF = this.prevBrewAnim + (this.brewAnim - this.prevBrewAnim) * f;
        final float brewAnimPc = this.theBarrel.getBrewAnimationProgressScaledF(1, f);
        if (brewMode == 1 || brewMode == 2) {
            final int x0 = super.field_147003_i + 148;
            final int x2 = super.field_147003_i + 196;
            final int y0 = super.field_147009_r + 34;
            final int y2 = super.field_147009_r + 130;
            final int yFull = y2 - fullAmount;
            final float yAnim = y2 - brewAnimF;
            final ItemStack itemstack = this.theBarrel.getStackInSlot(9);
            if (itemstack != null) {
                final IIcon liquidIcon = itemstack.getItem().getIconFromDamage(-1);
                if (liquidIcon != null) {
                    ((GuiScreen)this).mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
                    final float minU = liquidIcon.getInterpolatedU(7.0);
                    final float maxU = liquidIcon.getInterpolatedU(8.0);
                    final float minV = liquidIcon.getInterpolatedV(7.0);
                    final float maxV = liquidIcon.getInterpolatedV(8.0);
                    final Tessellator tessellator = Tessellator.instance;
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV((double)x0, (double)y2, (double)((Gui)this).zLevel, (double)minU, (double)maxV);
                    tessellator.addVertexWithUV((double)x2, (double)y2, (double)((Gui)this).zLevel, (double)maxU, (double)maxV);
                    tessellator.addVertexWithUV((double)x2, (double)yFull, (double)((Gui)this).zLevel, (double)maxU, (double)minV);
                    tessellator.addVertexWithUV((double)x0, (double)yFull, (double)((Gui)this).zLevel, (double)minU, (double)minV);
                    tessellator.draw();
                    final int fullColor = 2167561;
                    this.drawGradientRect(x0, yFull, x2, y2, 0, 0xFF000000 | fullColor);
                }
            }
            if (brewMode == 1) {
                ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiBarrel.brewingTexture);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glDisable(3008);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, brewAnimPc);
                LOTRGuiScreenBase.drawTexturedModalRectFloat((float)x0, yAnim, 51.0, 0.0, x2 - x0, y2 - yAnim, 256, ((Gui)this).zLevel);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(3008);
                GL11.glDisable(3042);
            }
            ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiBarrel.brewingTexture);
            this.drawTexturedModalRect(x0, y0, 1, 0, x2 - x0, y2 - y0);
        }
    }
    
    static {
        LOTRGuiBarrel.guiTexture = new ResourceLocation("lotr:gui/barrel/barrel.png");
        LOTRGuiBarrel.brewingTexture = new ResourceLocation("lotr:gui/barrel/brewing.png");
    }
}
