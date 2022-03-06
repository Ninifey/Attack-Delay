// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Slot;
import lotr.common.entity.npc.LOTRInventoryHiredReplacedItems;
import net.minecraft.inventory.IInventory;
import lotr.common.entity.npc.LOTREntityOrc;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.inventory.LOTRContainerHiredWarriorInventory;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiHiredWarriorInventory extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTREntityNPC theNPC;
    private LOTRContainerHiredWarriorInventory containerInv;
    
    public LOTRGuiHiredWarriorInventory(final InventoryPlayer inv, final LOTREntityNPC entity) {
        super((Container)new LOTRContainerHiredWarriorInventory(inv, entity));
        this.theNPC = entity;
        this.containerInv = (LOTRContainerHiredWarriorInventory)super.field_147002_h;
        super.field_147000_g = 188;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = StatCollector.translateToLocal("lotr.gui.warrior.openInv");
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 95, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiHiredWarriorInventory.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theNPC instanceof LOTREntityOrc && ((LOTREntityOrc)this.theNPC).isOrcBombardier()) {
            final LOTRContainerHiredWarriorInventory containerInv = this.containerInv;
            final IInventory proxyInv = this.containerInv.proxyInv;
            final LOTRInventoryHiredReplacedItems npcInv = this.containerInv.npcInv;
            final Slot slotBomb = containerInv.getSlotFromInventory(proxyInv, 5);
            final LOTRContainerHiredWarriorInventory containerInv2 = this.containerInv;
            final IInventory proxyInv2 = this.containerInv.proxyInv;
            final LOTRInventoryHiredReplacedItems npcInv2 = this.containerInv.npcInv;
            final Slot slotMelee = containerInv2.getSlotFromInventory(proxyInv2, 4);
            this.drawTexturedModalRect(super.field_147003_i + slotBomb.xDisplayPosition - 1, super.field_147009_r + slotBomb.yDisplayPosition - 1, slotMelee.xDisplayPosition - 1, slotMelee.yDisplayPosition - 1, 18, 18);
        }
    }
    
    static {
        LOTRGuiHiredWarriorInventory.guiTexture = new ResourceLocation("lotr:gui/npc/hiredWarrior.png");
    }
}
