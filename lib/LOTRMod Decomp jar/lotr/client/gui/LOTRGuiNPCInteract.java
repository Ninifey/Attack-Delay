// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;

public abstract class LOTRGuiNPCInteract extends LOTRGuiScreenBase
{
    protected LOTREntityNPC theEntity;
    
    public LOTRGuiNPCInteract(final LOTREntityNPC entity) {
        this.theEntity = entity;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        final String s = this.theEntity.getCommandSenderName();
        super.fontRendererObj.drawString(s, (super.width - super.fontRendererObj.getStringWidth(s)) / 2, super.height / 5 * 3 - 20, 16777215);
        super.drawScreen(i, j, f);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.theEntity == null || !this.theEntity.isEntityAlive() || this.theEntity.getDistanceSqToEntity((Entity)super.mc.thePlayer) > 100.0) {
            super.mc.thePlayer.closeScreen();
        }
    }
}
