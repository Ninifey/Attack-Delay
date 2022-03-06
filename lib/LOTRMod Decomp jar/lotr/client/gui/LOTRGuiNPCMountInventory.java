// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerNPCMountInventory;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiNPCMountInventory extends GuiContainer
{
    private static final ResourceLocation guiTexture;
    private IInventory thePlayerInv;
    private IInventory theMountInv;
    private LOTREntityNPCRideable theMount;
    private float mouseX;
    private float mouseY;
    
    public LOTRGuiNPCMountInventory(final IInventory playerInv, final IInventory mountInv, final LOTREntityNPCRideable mount) {
        super((Container)new LOTRContainerNPCMountInventory(playerInv, mountInv, mount));
        this.thePlayerInv = playerInv;
        this.theMountInv = mountInv;
        this.theMount = mount;
        ((GuiScreen)this).field_146291_p = false;
    }
    
    protected void func_146979_b(final int i, final int j) {
        ((GuiScreen)this).fontRendererObj.drawString(this.theMountInv.isInventoryNameLocalized() ? this.theMountInv.getInventoryName() : I18n.format(this.theMountInv.getInventoryName(), new Object[0]), 8, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(this.thePlayerInv.isInventoryNameLocalized() ? this.thePlayerInv.getInventoryName() : I18n.format(this.thePlayerInv.getInventoryName(), new Object[0]), 8, super.field_147000_g - 96 + 2, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiNPCMountInventory.guiTexture);
        final int k = (((GuiScreen)this).width - super.field_146999_f) / 2;
        final int l = (((GuiScreen)this).height - super.field_147000_g) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, super.field_146999_f, super.field_147000_g);
        this.drawTexturedModalRect(k + 7, l + 35, 0, super.field_147000_g + 54, 18, 18);
        GuiInventory.func_147046_a(k + 51, l + 60, 17, k + 51 - this.mouseX, l + 75 - 50 - this.mouseY, (EntityLivingBase)this.theMount);
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.mouseX = (float)i;
        this.mouseY = (float)j;
        super.drawScreen(i, j, f);
    }
    
    static {
        guiTexture = new ResourceLocation("textures/gui/container/horse.png");
    }
}
