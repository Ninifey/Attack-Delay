// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHobbit extends LOTRModelBiped
{
    public ModelRenderer bipedChest;
    private static final float F_10_12 = 0.8333333f;
    
    public LOTRModelHobbit() {
        this(0.0f);
    }
    
    public LOTRModelHobbit(final float f) {
        this(f, 64, (f == 0.0f) ? 64 : 32);
    }
    
    public LOTRModelHobbit(final float f, final int width, final int height) {
        super(f, 0.0f, width, height);
        final boolean isArmor = height == 32;
        (this.bipedChest = new ModelRenderer((ModelBase)this, 24, 0)).addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
        this.bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedBody.addChild(this.bipedChest);
        if (!isArmor) {
            (super.bipedHeadwear = new ModelRenderer((ModelBase)this, 0, 32)).addBox(-4.0f, -8.0f, -4.0f, 8, 12, 8, 0.5f + f);
            super.bipedHeadwear.setRotationPoint(0.0f, 2.0f, 0.0f);
        }
        if (!isArmor) {
            final ModelRenderer rightFoot = new ModelRenderer((ModelBase)this, 40, 32);
            rightFoot.addBox(-2.0f, 10.0f, -5.0f, 4, 2, 3, f);
            rightFoot.rotateAngleY = (float)Math.toRadians(10.0);
            super.bipedRightLeg.addChild(rightFoot);
            final ModelRenderer leftFoot = new ModelRenderer((ModelBase)this, 40, 32);
            leftFoot.addBox(-2.0f, 10.0f, -5.0f, 4, 2, 3, f);
            leftFoot.rotateAngleY = (float)Math.toRadians(-10.0);
            super.bipedLeftLeg.addChild(leftFoot);
        }
        final ModelRenderer bipedHead = super.bipedHead;
        bipedHead.rotationPointY += 4.0f;
        final ModelRenderer bipedHeadwear = super.bipedHeadwear;
        bipedHeadwear.rotationPointY += 4.0f;
        final ModelRenderer bipedBody = super.bipedBody;
        bipedBody.rotationPointY += 4.8f;
        final ModelRenderer bipedRightArm = super.bipedRightArm;
        bipedRightArm.rotationPointY += 4.8f;
        final ModelRenderer bipedLeftArm = super.bipedLeftArm;
        bipedLeftArm.rotationPointY += 4.8f;
        final ModelRenderer bipedRightLeg = super.bipedRightLeg;
        bipedRightLeg.rotationPointY += 4.8f;
        final ModelRenderer bipedLeftLeg = super.bipedLeftLeg;
        bipedLeftLeg.rotationPointY += 4.8f;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedChest.showModel = (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest());
        final float f6 = 2.0f;
        if (((ModelBase)this).isChild) {
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            GL11.glTranslatef(0.0f, -1.0f * f5, 0.0f);
        }
        else {
            GL11.glPushMatrix();
        }
        super.bipedHead.render(f5);
        super.bipedHeadwear.render(f5);
        if (((ModelBase)this).isChild) {
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
        }
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, 0.8333333f, 1.0f);
        super.bipedBody.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, 0.8333333f, 1.0f);
        super.bipedRightArm.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, 0.8333333f, 1.0f);
        super.bipedLeftArm.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, 0.8333333f, 1.0f);
        super.bipedRightLeg.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, 0.8333333f, 1.0f);
        super.bipedLeftLeg.render(f5);
        GL11.glPopMatrix();
        if (((ModelBase)this).isChild) {
            GL11.glPopMatrix();
        }
        else {
            GL11.glPopMatrix();
        }
    }
}
