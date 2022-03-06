// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.lwjgl.opengl.GL11;
import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntityThrownRock;

public class LOTRRenderMountainTroll extends LOTRRenderTroll
{
    private static LOTRRandomSkins mountainTrollSkins;
    private LOTREntityThrownRock heldRock;
    
    public LOTRRenderMountainTroll() {
        LOTRRenderMountainTroll.mountainTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mountainTroll");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderMountainTroll.mountainTrollSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    protected void renderTrollWeapon(final EntityLivingBase entity, final float f) {
        final LOTREntityMountainTroll troll = (LOTREntityMountainTroll)entity;
        if (troll.isThrowingRocks()) {
            if (((LOTRModelTroll)((RendererLivingEntity)this).mainModel).onGround <= 0.0f) {
                if (this.heldRock == null) {
                    this.heldRock = new LOTREntityThrownRock(((Entity)troll).worldObj);
                }
                this.heldRock.setWorld(((Entity)troll).worldObj);
                this.heldRock.setPosition(((Entity)troll).posX, ((Entity)troll).posY, ((Entity)troll).posZ);
                ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).rightArm.postRender(0.0625f);
                GL11.glTranslatef(0.375f, 1.5f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                this.scaleTroll(troll, true);
                ((Render)this).renderManager.func_147940_a((Entity)this.heldRock, 0.0, 0.0, 0.0, 0.0f, f);
            }
        }
        else {
            ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).renderWoodenClubWithSpikes(0.0625f);
        }
    }
}
