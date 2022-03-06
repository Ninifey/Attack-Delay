// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHornSelect;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiHornSelect extends LOTRGuiScreenBase
{
    private static final ResourceLocation guiTexture;
    private static final RenderItem itemRenderer;
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;
    
    public LOTRGuiHornSelect() {
        this.xSize = 176;
        this.ySize = 256;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(new GuiButton(1, this.guiLeft + 40, this.guiTop + 40, 120, 20, StatCollector.translateToLocal("lotr.gui.hornSelect.haltReady")));
        super.buttonList.add(new GuiButton(3, this.guiLeft + 40, this.guiTop + 75, 120, 20, StatCollector.translateToLocal("lotr.gui.hornSelect.summon")));
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            final LOTRPacketHornSelect packet = new LOTRPacketHornSelect(button.id);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            super.mc.thePlayer.closeScreen();
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiHornSelect.guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final String s = StatCollector.translateToLocal("lotr.gui.hornSelect.title");
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        super.drawScreen(i, j, f);
        for (int k = 0; k < super.buttonList.size(); ++k) {
            final GuiButton button = super.buttonList.get(k);
            LOTRGuiHornSelect.itemRenderer.renderItemIntoGUI(super.fontRendererObj, super.mc.getTextureManager(), new ItemStack(LOTRMod.commandHorn, 1, button.id), button.field_146128_h - 22, button.field_146129_i + 2);
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final ItemStack itemstack = ((EntityPlayer)super.mc.thePlayer).inventory.getCurrentItem();
        if (itemstack == null || itemstack.getItem() != LOTRMod.commandHorn || itemstack.getItemDamage() != 0) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    static {
        guiTexture = new ResourceLocation("lotr:gui/horn_select.png");
        itemRenderer = new RenderItem();
    }
}
