// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import lotr.common.entity.npc.LOTREntityRanger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderRanger extends LOTRRenderDunedain
{
    private static LOTRRandomSkins ithilienSkins;
    
    public LOTRRenderRanger() {
        LOTRRenderRanger.ithilienSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/ranger");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityRanger ranger = (LOTREntityRanger)entity;
        if (ranger instanceof LOTREntityRangerIthilien) {
            return LOTRRenderRanger.ithilienSkins.getRandomSkin(ranger);
        }
        return super.getEntityTexture(entity);
    }
    
    private void doRangerInvisibility() {
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.001f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
    }
    
    private void undoRangerInvisibility() {
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        if (((LOTREntityRanger)entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final int i = super.shouldRenderPass(entity, pass, f);
        if (i > 0 && ((LOTREntityRanger)entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
        return i;
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        if (((LOTREntityRanger)entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
        super.renderEquippedItems(entity, f);
        if (((LOTREntityRanger)entity).isRangerSneaking()) {
            this.undoRangerInvisibility();
        }
    }
    
    @Override
    protected void renderNPCCape(final LOTREntityNPC entity) {
        if (((LOTREntityRanger)entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
        super.renderNPCCape(entity);
        if (((LOTREntityRanger)entity).isRangerSneaking()) {
            this.undoRangerInvisibility();
        }
    }
}
