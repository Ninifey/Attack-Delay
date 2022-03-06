// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGaladhrimWarden extends LOTRRenderElf
{
    private void doElfInvisibility() {
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.001f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.05f);
    }
    
    private void undoElfInvisibility() {
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.doElfInvisibility();
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final int j = super.shouldRenderPass(entity, pass, f);
        if (j > 0 && ((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.doElfInvisibility();
        }
        return j;
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.doElfInvisibility();
            return;
        }
        super.renderEquippedItems(entity, f);
        if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.undoElfInvisibility();
        }
    }
    
    @Override
    protected void renderNPCCape(final LOTREntityNPC entity) {
        if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.doElfInvisibility();
        }
        super.renderNPCCape(entity);
        if (((LOTREntityGaladhrimWarden)entity).isElfSneaking()) {
            this.undoElfInvisibility();
        }
    }
}
