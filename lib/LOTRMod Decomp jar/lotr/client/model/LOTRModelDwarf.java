// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelDwarf extends LOTRModelBiped
{
    public ModelRenderer bipedChest;
    
    public LOTRModelDwarf() {
        this(0.0f);
    }
    
    public LOTRModelDwarf(final float f) {
        this(f, 64, (f == 0.0f) ? 64 : 32);
    }
    
    public LOTRModelDwarf(final float f, final int width, final int height) {
        super(f, 0.0f, width, height);
        (this.bipedChest = new ModelRenderer((ModelBase)this, 24, 0)).addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
        this.bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedBody.addChild(this.bipedChest);
        if (height == 64) {
            (super.bipedHeadwear = new ModelRenderer((ModelBase)this, 0, 32)).addBox(-4.0f, -8.0f, -4.0f, 8, 12, 8, 0.5f + f);
            super.bipedHeadwear.setRotationPoint(0.0f, 2.0f, 0.0f);
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedChest.showModel = (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest());
        if (((ModelBase)this).isChild) {
            final float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            super.bipedHead.render(f5);
            super.bipedHeadwear.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            super.bipedBody.render(f5);
            super.bipedRightArm.render(f5);
            super.bipedLeftArm.render(f5);
            super.bipedRightLeg.render(f5);
            super.bipedLeftLeg.render(f5);
            GL11.glPopMatrix();
        }
        else {
            super.bipedHead.render(f5);
            super.bipedHeadwear.render(f5);
            GL11.glPushMatrix();
            GL11.glScalef(1.25f, 1.0f, 1.0f);
            super.bipedBody.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(-1.0f * f5, 0.0f, 0.0f);
            super.bipedRightArm.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(1.0f * f5, 0.0f, 0.0f);
            super.bipedLeftArm.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.25f * f5, 0.0f, 0.0f);
            GL11.glScalef(1.25f, 1.0f, 1.0f);
            super.bipedRightLeg.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.25f * f5, 0.0f, 0.0f);
            GL11.glScalef(1.25f, 1.0f, 1.0f);
            super.bipedLeftLeg.render(f5);
            GL11.glPopMatrix();
        }
    }
}
