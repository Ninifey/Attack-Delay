// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityBossTrophy;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelEnt;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.util.ResourceLocation;
import lotr.common.item.LOTRItemBossTrophy;
import java.util.Map;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderBossTrophy extends Render
{
    private static Map<LOTRItemBossTrophy.TrophyType, ResourceLocation> trophyTextures;
    private static LOTRModelTroll trollModel;
    private static LOTRModelEnt entModel;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityBossTrophy trophy = (LOTREntityBossTrophy)entity;
        final LOTRItemBossTrophy.TrophyType type = trophy.getTrophyType();
        ResourceLocation r = LOTRRenderBossTrophy.trophyTextures.get(type);
        if (r == null) {
            r = new ResourceLocation("lotr:item/bossTrophy/" + type.trophyName + ".png");
            LOTRRenderBossTrophy.trophyTextures.put(type, r);
        }
        return r;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityBossTrophy trophy = (LOTREntityBossTrophy)entity;
        final LOTRItemBossTrophy.TrophyType type = trophy.getTrophyType();
        final float modelscale = 0.0625f;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        float rotation = 0.0f;
        if (trophy.isTrophyHanging()) {
            rotation = 180.0f + trophy.getTrophyFacing() * 90.0f;
        }
        else {
            rotation = 180.0f - entity.rotationYaw;
        }
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        this.bindEntityTexture(entity);
        if (type == LOTRItemBossTrophy.TrophyType.MOUNTAIN_TROLL_CHIEFTAIN) {
            final ModelRenderer head = LOTRRenderBossTrophy.trollModel.head;
            head.setRotationPoint(0.0f, -6.0f, 6.0f);
            GL11.glTranslatef(0.0f, -0.05f, 0.1f);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.25f, 0.0f, 0.0f);
            GL11.glRotatef(-10.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(15.0f, 0.0f, 1.0f, 0.0f);
            head.render(modelscale);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.25f, 0.0f, 0.0f);
            GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-15.0f, 0.0f, 1.0f, 0.0f);
            head.render(modelscale);
            GL11.glPopMatrix();
        }
        if (type == LOTRItemBossTrophy.TrophyType.MALLORN_ENT) {
            final ModelRenderer trunk = LOTRRenderBossTrophy.entModel.trunk;
            LOTRRenderBossTrophy.entModel.rightArm.showModel = false;
            LOTRRenderBossTrophy.entModel.leftArm.showModel = false;
            LOTRRenderBossTrophy.entModel.trophyBottomPanel.showModel = true;
            final float scale = 0.6f;
            GL11.glTranslatef(0.0f, 34.0f * modelscale * scale, 0.0f);
            if (trophy.isTrophyHanging()) {
                GL11.glTranslatef(0.0f, 0.0f, 3.0f * modelscale / scale);
            }
            GL11.glScalef(scale, scale, scale);
            trunk.render(modelscale);
        }
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderBossTrophy.trophyTextures = new HashMap<LOTRItemBossTrophy.TrophyType, ResourceLocation>();
        LOTRRenderBossTrophy.trollModel = new LOTRModelTroll();
        LOTRRenderBossTrophy.entModel = new LOTRModelEnt();
    }
}
