// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import lotr.common.network.LOTRPacketAnvilReforge;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketAnvilRename;
import net.minecraft.inventory.Slot;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import lotr.common.enchant.LOTREnchantmentHelper;
import org.lwjgl.input.Keyboard;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import lotr.common.inventory.LOTRContainerAnvil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiAnvil extends GuiContainer
{
    public static final ResourceLocation anvilTexture;
    private LOTRContainerAnvil theAnvil;
    private ItemStack prevItemStack;
    private GuiButton buttonReforge;
    private GuiTextField textFieldRename;
    
    public LOTRGuiAnvil(final EntityPlayer entityplayer, final int i, final int j, final int k) {
        super((Container)new LOTRContainerAnvil(entityplayer, i, j, k));
        this.theAnvil = (LOTRContainerAnvil)super.field_147002_h;
        super.field_146999_f = 176;
        super.field_147000_g = 190;
    }
    
    public LOTRGuiAnvil(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        super((Container)new LOTRContainerAnvil(entityplayer, npc));
        this.theAnvil = (LOTRContainerAnvil)super.field_147002_h;
        super.field_146999_f = 176;
        super.field_147000_g = 190;
    }
    
    public void initGui() {
        super.initGui();
        this.buttonReforge = new LOTRGuiButtonReforge(0, super.field_147003_i + 25, super.field_147009_r + 78);
        ((GuiScreen)this).buttonList.add(this.buttonReforge);
        Keyboard.enableRepeatEvents(true);
        (this.textFieldRename = new GuiTextField(((GuiScreen)this).fontRendererObj, super.field_147003_i + 62, super.field_147009_r + 24, 103, 12)).func_146193_g(-1);
        this.textFieldRename.func_146204_h(-1);
        this.textFieldRename.func_146185_a(false);
        this.textFieldRename.func_146203_f(40);
        this.prevItemStack = null;
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
    
    public void updateScreen() {
        super.updateScreen();
        if (this.theAnvil.clientReforgeTime > 0) {
            final LOTRContainerAnvil theAnvil = this.theAnvil;
            --theAnvil.clientReforgeTime;
        }
        final ItemStack itemstack = this.theAnvil.invInput.getStackInSlot(0);
        if (itemstack != this.prevItemStack) {
            this.prevItemStack = itemstack;
            this.textFieldRename.setText((itemstack == null) ? "" : itemstack.getDisplayName());
            this.textFieldRename.func_146184_c(itemstack != null);
            if (itemstack != null) {
                this.renameItem();
            }
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        final ItemStack inputItem = this.theAnvil.invInput.getStackInSlot(0);
        final GuiButton buttonReforge = this.buttonReforge;
        final GuiButton buttonReforge2 = this.buttonReforge;
        final boolean b = inputItem != null && LOTREnchantmentHelper.isReforgeable(inputItem) && this.theAnvil.reforgeCost > 0;
        buttonReforge2.enabled = b;
        buttonReforge.field_146125_m = b;
        super.drawScreen(i, j, f);
        if (this.buttonReforge.field_146125_m && this.buttonReforge.func_146115_a()) {
            final float z = ((Gui)this).zLevel;
            final String s = StatCollector.translateToLocal("container.lotr.anvil.reforge");
            this.func_146279_a(s, i - 12, j + 24);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((Gui)this).zLevel = z;
        }
        GL11.glDisable(2896);
        GL11.glDisable(3042);
        this.textFieldRename.drawTextBox();
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiAnvil.anvilTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theAnvil.isTrader) {
            this.drawTexturedModalRect(super.field_147003_i + 75, super.field_147009_r + 69, 176, 21, 18, 18);
        }
        this.drawTexturedModalRect(super.field_147003_i + 59, super.field_147009_r + 20, 0, super.field_147000_g + ((this.theAnvil.invInput.getStackInSlot(0) != null) ? 0 : 16), 110, 16);
        if (this.theAnvil.invOutput.getStackInSlot(0) == null) {
            boolean flag = false;
            for (int l = 0; l < this.theAnvil.invInput.getSizeInventory(); ++l) {
                if (this.theAnvil.invInput.getStackInSlot(l) != null) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                this.drawTexturedModalRect(super.field_147003_i + 99, super.field_147009_r + 56, super.field_146999_f, 0, 28, 21);
            }
        }
    }
    
    protected void func_146979_b(final int i, final int j) {
        GL11.glDisable(2896);
        GL11.glDisable(3042);
        final String s = this.theAnvil.isTrader ? StatCollector.translateToLocal("container.lotr.smith") : StatCollector.translateToLocal("container.lotr.anvil");
        ((GuiScreen)this).fontRendererObj.drawString(s, 60, 6, 4210752);
        final boolean reforge = this.buttonReforge.enabled && this.buttonReforge.func_146115_a();
        String costText = null;
        int color = 8453920;
        final ItemStack inputItem = this.theAnvil.invInput.getStackInSlot(0);
        final ItemStack outputItem = this.theAnvil.invOutput.getStackInSlot(0);
        if (inputItem != null) {
            if (reforge && this.theAnvil.reforgeCost > 0) {
                costText = StatCollector.translateToLocalFormatted("container.lotr.anvil.reforgeCost", new Object[] { this.theAnvil.reforgeCost });
                if (!this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.reforgeCost)) {
                    color = 16736352;
                }
            }
            else if (this.theAnvil.materialCost > 0 && outputItem != null) {
                if (this.theAnvil.isTrader) {
                    costText = StatCollector.translateToLocalFormatted("container.lotr.smith.cost", new Object[] { this.theAnvil.materialCost });
                }
                else {
                    costText = StatCollector.translateToLocalFormatted("container.lotr.anvil.cost", new Object[] { this.theAnvil.materialCost });
                }
                if (!this.theAnvil.getSlotFromInventory(this.theAnvil.invOutput, 0).canTakeStack((EntityPlayer)((GuiScreen)this).mc.thePlayer)) {
                    color = 16736352;
                }
            }
        }
        if (costText != null) {
            final int colorF = 0xFF000000 | (color & 0xFCFCFC) >> 2 | (color & 0xFF000000);
            final int x = super.field_146999_f - 8 - ((GuiScreen)this).fontRendererObj.getStringWidth(costText);
            final int y = 90;
            if (((GuiScreen)this).fontRendererObj.getUnicodeFlag()) {
                drawRect(x - 3, y - 2, super.field_146999_f - 7, y + 10, -16777216);
                drawRect(x - 2, y - 1, super.field_146999_f - 8, y + 9, -12895429);
            }
            else {
                ((GuiScreen)this).fontRendererObj.drawString(costText, x, y + 1, colorF);
                ((GuiScreen)this).fontRendererObj.drawString(costText, x + 1, y, colorF);
                ((GuiScreen)this).fontRendererObj.drawString(costText, x + 1, y + 1, colorF);
            }
            ((GuiScreen)this).fontRendererObj.drawString(costText, x, y, color);
        }
        GL11.glEnable(2896);
        if (this.theAnvil.clientReforgeTime > 0) {
            final float n = (float)this.theAnvil.clientReforgeTime;
            final LOTRContainerAnvil theAnvil = this.theAnvil;
            final float f = n / 40.0f;
            int alpha = (int)(f * 255.0f);
            alpha = MathHelper.clamp_int(alpha, 0, 255);
            final int overlayColor = 0xFFFFFF | alpha << 24;
            final Slot slot = this.theAnvil.getSlotFromInventory(this.theAnvil.invInput, 0);
            drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, overlayColor);
        }
    }
    
    protected void keyTyped(final char c, final int i) {
        if (this.textFieldRename.textboxKeyTyped(c, i)) {
            this.renameItem();
        }
        else {
            super.keyTyped(c, i);
        }
    }
    
    private void renameItem() {
        String rename = this.textFieldRename.getText();
        final ItemStack itemstack = this.theAnvil.invInput.getStackInSlot(0);
        if (itemstack != null && !itemstack.hasDisplayName() && rename.equals(itemstack.getDisplayName())) {
            rename = "";
        }
        this.theAnvil.updateItemName(rename);
        final LOTRPacketAnvilRename packet = new LOTRPacketAnvilRename(rename);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.textFieldRename.mouseClicked(i, j, k);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button == this.buttonReforge) {
            final ItemStack inputItem = this.theAnvil.invInput.getStackInSlot(0);
            if (inputItem != null && this.theAnvil.reforgeCost > 0 && this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.reforgeCost)) {
                final LOTRPacketAnvilReforge packet = new LOTRPacketAnvilReforge();
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
        }
    }
    
    static {
        anvilTexture = new ResourceLocation("lotr:gui/anvil.png");
    }
}
