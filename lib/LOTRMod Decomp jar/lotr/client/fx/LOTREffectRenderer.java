// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;

public class LOTREffectRenderer
{
    private Minecraft mc;
    private World worldObj;
    private List particles;
    
    public LOTREffectRenderer(final Minecraft minecraft) {
        this.particles = new ArrayList();
        this.mc = minecraft;
    }
    
    public void addEffect(final EntityFX entityfx) {
        if (this.particles.size() >= 4000) {
            this.particles.remove(0);
        }
        this.particles.add(entityfx);
    }
    
    public void updateEffects() {
        for (int i = 0; i < this.particles.size(); ++i) {
            final EntityFX entityfx = this.particles.get(i);
            if (entityfx != null) {
                entityfx.onUpdate();
            }
            if (entityfx == null || ((Entity)entityfx).isDead) {
                this.particles.remove(i--);
            }
        }
    }
    
    public void renderParticles(final Entity entity, final float f) {
        final float f2 = ActiveRenderInfo.rotationX;
        final float f3 = ActiveRenderInfo.rotationZ;
        final float f4 = ActiveRenderInfo.rotationYZ;
        final float f5 = ActiveRenderInfo.rotationXY;
        final float f6 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
        EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
        EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
        if (!this.particles.isEmpty()) {
            this.mc.getTextureManager().bindTexture(LOTRClientProxy.particlesTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glAlphaFunc(516, 0.003921569f);
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            for (int i = 0; i < this.particles.size(); ++i) {
                final EntityFX entityfx = this.particles.get(i);
                if (entityfx != null) {
                    tessellator.setBrightness(entityfx.getBrightnessForRender(f));
                    entityfx.renderParticle(tessellator, f, f2, f6, f3, f4, f5);
                }
            }
            tessellator.draw();
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glAlphaFunc(516, 0.1f);
        }
    }
    
    public void clearEffectsAndSetWorld(final World world) {
        this.worldObj = world;
        this.particles.clear();
    }
}
