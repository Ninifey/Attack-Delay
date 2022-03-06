// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerBookshelf;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiBookshelf extends GuiContainer
{
    private static final ResourceLocation chestTexture;
    private IInventory playerInv;
    private IInventory shelfInv;
    private int inventoryRows;
    
    public LOTRGuiBookshelf(final IInventory player, final LOTRTileEntityBookshelf shelf) {
        super((Container)new LOTRContainerBookshelf(player, shelf));
        this.playerInv = player;
        this.shelfInv = (IInventory)shelf;
        ((GuiScreen)this).field_146291_p = false;
        final int i = 222;
        final int j = i - 108;
        this.inventoryRows = shelf.getSizeInventory() / 9;
        super.field_147000_g = j + this.inventoryRows * 18;
    }
    
    protected void func_146979_b(final int i, final int j) {
        ((GuiScreen)this).fontRendererObj.drawString(this.shelfInv.isInventoryNameLocalized() ? this.shelfInv.getInventoryName() : StatCollector.translateToLocal(this.shelfInv.getInventoryName()), 8, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(this.playerInv.isInventoryNameLocalized() ? this.playerInv.getInventoryName() : StatCollector.translateToLocal(this.playerInv.getInventoryName()), 8, super.field_147000_g - 96 + 2, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiBookshelf.chestTexture);
        final int k = (((GuiScreen)this).width - super.field_146999_f) / 2;
        final int l = (((GuiScreen)this).height - super.field_147000_g) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, super.field_146999_f, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, super.field_146999_f, 96);
    }
    
    static {
        chestTexture = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
