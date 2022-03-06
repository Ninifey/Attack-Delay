// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerHiredFarmerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiHiredFarmerInventory extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTREntityNPC theNPC;
    
    public LOTRGuiHiredFarmerInventory(final InventoryPlayer inv, final LOTREntityNPC entity) {
        super((Container)new LOTRContainerHiredFarmerInventory(inv, entity));
        this.theNPC = entity;
        super.field_147000_g = 161;
    }
    
    protected void func_146979_b(final int i, final int j) {
        String s = this.theNPC.getNPCName();
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 67, 4210752);
        final ItemStack seeds = super.field_147002_h.getSlot(0).getStack();
        if (seeds != null && seeds.stackSize == 1) {
            s = StatCollector.translateToLocal("lotr.gui.farmer.oneSeed");
            s = EnumChatFormatting.RED + s;
            ((GuiScreen)this).fontRendererObj.drawSplitString(s, super.field_146999_f + 10, 20, 120, 16777215);
        }
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiHiredFarmerInventory.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        final ItemStack seeds = super.field_147002_h.getSlot(0).getStack();
        if (seeds == null) {
            this.drawTexturedModalRect(super.field_147003_i + 80, super.field_147009_r + 21, 176, 0, 16, 16);
        }
        final ItemStack bonemeal = super.field_147002_h.getSlot(3).getStack();
        if (bonemeal == null) {
            this.drawTexturedModalRect(super.field_147003_i + 123, super.field_147009_r + 34, 176, 16, 16, 16);
        }
    }
    
    static {
        LOTRGuiHiredFarmerInventory.guiTexture = new ResourceLocation("lotr:gui/npc/hiredFarmer.png");
    }
}
