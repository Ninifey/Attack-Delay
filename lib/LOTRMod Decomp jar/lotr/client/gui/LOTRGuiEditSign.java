// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import net.minecraft.util.Direction;
import net.minecraft.util.ChatAllowedCharacters;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketEditSign;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.gui.GuiButton;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.client.gui.GuiScreen;

public class LOTRGuiEditSign extends GuiScreen
{
    private LOTRTileEntitySign tileSign;
    private int updateCounter;
    private int editLine;
    private GuiButton buttonDone;
    private static RenderItem itemRenderer;
    
    public LOTRGuiEditSign(final LOTRTileEntitySign sign) {
        this.tileSign = sign;
    }
    
    public void initGui() {
        super.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        super.buttonList.add(this.buttonDone = new GuiButton(0, super.width / 2 - 100, super.height / 4 + 120, StatCollector.translateToLocal("gui.done")));
        this.tileSign.setEditable(false);
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final LOTRPacketEditSign packet = new LOTRPacketEditSign(this.tileSign);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        this.tileSign.setEditable(true);
    }
    
    public void updateScreen() {
        ++this.updateCounter;
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonDone) {}
            this.tileSign.onInventoryChanged();
            super.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void keyTyped(final char c, final int i) {
        if (i == 200) {
            --this.editLine;
        }
        if (i == 208 || i == 28 || i == 156) {
            ++this.editLine;
        }
        this.editLine &= this.tileSign.getNumLines() - 1;
        if (i == 14 && this.tileSign.signText[this.editLine].length() > 0) {
            final String s = this.tileSign.signText[this.editLine];
            this.tileSign.signText[this.editLine] = s.substring(0, s.length() - 1);
        }
        if (ChatAllowedCharacters.isAllowedCharacter(c)) {
            final int length = this.tileSign.signText[this.editLine].length();
            final LOTRTileEntitySign tileSign = this.tileSign;
            if (length < 15) {
                final StringBuilder sb = new StringBuilder();
                final String[] signText = this.tileSign.signText;
                final int editLine = this.editLine;
                signText[editLine] = sb.append(signText[editLine]).append(c).toString();
            }
        }
        if (i == 1) {
            this.actionPerformed(this.buttonDone);
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        final Block block = this.tileSign.getBlockType();
        final int meta = this.tileSign.getBlockMetadata();
        final float rotation = Direction.facingToDirection[meta] * 90.0f;
        final IIcon onIcon = ((LOTRTileEntitySignCarved)this.tileSign).getOnBlockIcon();
        this.drawDefaultBackground();
        this.drawCenteredString(super.fontRendererObj, StatCollector.translateToLocal("sign.edit"), super.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(super.width / 2), 0.0f, 50.0f);
        final float f2 = 93.75f;
        GL11.glScalef(-f2, -f2, -f2);
        GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
        GL11.glDisable(2929);
        GL11.glPushMatrix();
        final float iconScale = 0.5f;
        GL11.glScalef(-iconScale, -iconScale, iconScale);
        GL11.glTranslatef(0.0f, 0.5f, 0.0f);
        super.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        LOTRGuiEditSign.itemRenderer.renderIcon(-1, -1, onIcon, 2, 2);
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        GL11.glRotatef(rotation + 180.0f, 0.0f, 1.0f, 0.0f);
        TileEntityRendererDispatcher.instance.func_147549_a((TileEntity)this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        GL11.glDisable(2896);
        this.tileSign.lineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(i, j, f);
    }
    
    static {
        LOTRGuiEditSign.itemRenderer = new RenderItem();
    }
}
