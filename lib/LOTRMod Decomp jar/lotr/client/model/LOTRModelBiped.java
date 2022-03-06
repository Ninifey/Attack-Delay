// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;

public class LOTRModelBiped extends ModelBiped
{
    private boolean setup;
    private float base_bodyRotateX;
    private float base_armX;
    private float base_armY;
    private float base_armZ;
    private float base_legY;
    private float base_legZ;
    private float base_headY;
    private float base_headZ;
    private float base_bodyY;
    private float base_bodyZ;
    
    public LOTRModelBiped() {
        this.setup = false;
    }
    
    public LOTRModelBiped(final float f) {
        super(f);
        this.setup = false;
    }
    
    public LOTRModelBiped(final float f, final float f1, final int width, final int height) {
        super(f, f1, width, height);
        this.setup = false;
    }
    
    private void setupModelBiped() {
        this.base_bodyRotateX = super.bipedBody.rotateAngleX;
        this.base_armX = Math.abs(super.bipedRightArm.rotationPointX);
        this.base_armY = super.bipedRightArm.rotationPointY;
        this.base_armZ = super.bipedRightArm.rotationPointZ;
        this.base_legY = super.bipedRightLeg.rotationPointY;
        this.base_legZ = super.bipedRightLeg.rotationPointZ;
        this.base_headY = super.bipedHead.rotationPointY;
        this.base_headZ = super.bipedHead.rotationPointZ;
        this.base_bodyY = super.bipedBody.rotationPointY;
        this.base_bodyZ = super.bipedBody.rotationPointZ;
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        if (!this.setup) {
            this.setupModelBiped();
            this.setup = true;
        }
        super.bipedHead.rotateAngleY = f3 / 57.295776f;
        super.bipedHead.rotateAngleX = f4 / 57.295776f;
        super.bipedHeadwear.rotateAngleY = super.bipedHead.rotateAngleY;
        super.bipedHeadwear.rotateAngleX = super.bipedHead.rotateAngleX;
        super.bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
        super.bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
        super.bipedRightArm.rotateAngleZ = 0.0f;
        super.bipedLeftArm.rotateAngleZ = 0.0f;
        super.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        super.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        if (entity instanceof LOTREntityNPC) {
            super.bipedRightLeg.rotateAngleY = (float)Math.toRadians(5.0);
            super.bipedLeftLeg.rotateAngleY = (float)Math.toRadians(-5.0);
        }
        if (((ModelBase)this).isRiding) {
            final ModelRenderer bipedRightArm = super.bipedRightArm;
            bipedRightArm.rotateAngleX -= 0.62831855f;
            final ModelRenderer bipedLeftArm = super.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= 0.62831855f;
            super.bipedRightLeg.rotateAngleX = -1.2566371f;
            super.bipedLeftLeg.rotateAngleX = -1.2566371f;
            super.bipedRightLeg.rotateAngleY = 0.31415927f;
            super.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        if (super.heldItemLeft != 0) {
            super.bipedLeftArm.rotateAngleX = super.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f * super.heldItemLeft;
        }
        if (super.heldItemRight != 0) {
            super.bipedRightArm.rotateAngleX = super.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * super.heldItemRight;
        }
        super.bipedRightArm.rotateAngleY = 0.0f;
        super.bipedLeftArm.rotateAngleY = 0.0f;
        if (((ModelBase)this).onGround > -9990.0f) {
            float f6 = ((ModelBase)this).onGround;
            super.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927f * 2.0f) * 0.2f;
            super.bipedRightArm.rotationPointZ = MathHelper.sin(super.bipedBody.rotateAngleY) * this.base_armX;
            super.bipedRightArm.rotationPointX = -MathHelper.cos(super.bipedBody.rotateAngleY) * this.base_armX;
            super.bipedLeftArm.rotationPointZ = -MathHelper.sin(super.bipedBody.rotateAngleY) * this.base_armX;
            super.bipedLeftArm.rotationPointX = MathHelper.cos(super.bipedBody.rotateAngleY) * this.base_armX;
            final ModelRenderer bipedRightArm2 = super.bipedRightArm;
            bipedRightArm2.rotateAngleY += super.bipedBody.rotateAngleY;
            final ModelRenderer bipedLeftArm2 = super.bipedLeftArm;
            bipedLeftArm2.rotateAngleY += super.bipedBody.rotateAngleY;
            final ModelRenderer bipedLeftArm3 = super.bipedLeftArm;
            bipedLeftArm3.rotateAngleX += super.bipedBody.rotateAngleY;
            f6 = 1.0f - ((ModelBase)this).onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0f - f6;
            final float f7 = MathHelper.sin(f6 * 3.1415927f);
            final float f8 = MathHelper.sin(((ModelBase)this).onGround * 3.1415927f) * -(super.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            super.bipedRightArm.rotateAngleX -= (float)(f7 * 1.2 + f8);
            final ModelRenderer bipedRightArm3 = super.bipedRightArm;
            bipedRightArm3.rotateAngleY += super.bipedBody.rotateAngleY * 2.0f;
            super.bipedRightArm.rotateAngleZ = MathHelper.sin(((ModelBase)this).onGround * 3.1415927f) * -0.4f;
        }
        if (super.isSneak) {
            super.bipedBody.rotateAngleX = this.base_bodyRotateX + 0.5f;
            final ModelRenderer bipedRightArm4 = super.bipedRightArm;
            bipedRightArm4.rotateAngleX += 0.4f;
            final ModelRenderer bipedLeftArm4 = super.bipedLeftArm;
            bipedLeftArm4.rotateAngleX += 0.4f;
            super.bipedRightLeg.rotationPointZ = this.base_legZ + 4.0f;
            super.bipedLeftLeg.rotationPointZ = this.base_legZ + 4.0f;
            super.bipedRightLeg.rotationPointY = this.base_legY - 3.0f;
            super.bipedLeftLeg.rotationPointY = this.base_legY - 3.0f;
            super.bipedHead.rotationPointY = this.base_headY + 1.0f;
            super.bipedHeadwear.rotationPointY = this.base_headY + 1.0f;
        }
        else {
            super.bipedBody.rotateAngleX = this.base_bodyRotateX;
            super.bipedRightLeg.rotationPointZ = this.base_legZ + 0.1f;
            super.bipedLeftLeg.rotationPointZ = this.base_legZ + 0.1f;
            super.bipedRightLeg.rotationPointY = this.base_legY;
            super.bipedLeftLeg.rotationPointY = this.base_legY;
            super.bipedHead.rotationPointY = this.base_headY;
            super.bipedHeadwear.rotationPointY = this.base_headY;
        }
        final ModelRenderer bipedRightArm5 = super.bipedRightArm;
        bipedRightArm5.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm5 = super.bipedLeftArm;
        bipedLeftArm5.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm6 = super.bipedRightArm;
        bipedRightArm6.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm6 = super.bipedLeftArm;
        bipedLeftArm6.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
        if (super.aimedBow) {
            final float f6 = 0.0f;
            final float f7 = 0.0f;
            super.bipedRightArm.rotateAngleZ = 0.0f;
            super.bipedLeftArm.rotateAngleZ = 0.0f;
            super.bipedRightArm.rotateAngleY = -(0.1f - f6 * 0.6f) + super.bipedHead.rotateAngleY;
            super.bipedLeftArm.rotateAngleY = 0.1f - f6 * 0.6f + super.bipedHead.rotateAngleY + 0.4f;
            super.bipedRightArm.rotateAngleX = -1.5707964f + super.bipedHead.rotateAngleX;
            super.bipedLeftArm.rotateAngleX = -1.5707964f + super.bipedHead.rotateAngleX;
            final ModelRenderer bipedRightArm7 = super.bipedRightArm;
            bipedRightArm7.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
            final ModelRenderer bipedLeftArm7 = super.bipedLeftArm;
            bipedLeftArm7.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
            final ModelRenderer bipedRightArm8 = super.bipedRightArm;
            bipedRightArm8.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedLeftArm8 = super.bipedLeftArm;
            bipedLeftArm8.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedRightArm9 = super.bipedRightArm;
            bipedRightArm9.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
            final ModelRenderer bipedLeftArm9 = super.bipedLeftArm;
            bipedLeftArm9.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
        }
        if (entity instanceof LOTREntityNPC) {
            final LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (npc.isDrunkard()) {
                float f9 = f2 / 80.0f;
                float f10 = (f2 + 40.0f) / 80.0f;
                f9 *= 6.2831855f;
                f10 *= 6.2831855f;
                final float f11 = MathHelper.sin(f9) * 0.5f;
                final float f12 = MathHelper.sin(f10) * 0.5f;
                final ModelRenderer bipedHead = super.bipedHead;
                bipedHead.rotateAngleX += f11;
                final ModelRenderer bipedHead2 = super.bipedHead;
                bipedHead2.rotateAngleY += f12;
                final ModelRenderer bipedHeadwear = super.bipedHeadwear;
                bipedHeadwear.rotateAngleX += f11;
                final ModelRenderer bipedHeadwear2 = super.bipedHeadwear;
                bipedHeadwear2.rotateAngleY += f12;
                if (npc.getHeldItem() != null) {
                    super.bipedRightArm.rotateAngleX = -1.0471976f;
                }
            }
        }
        boolean bowing = false;
        float bowAmount = 0.0f;
        if (entity instanceof LOTREntityElf) {
            bowAmount = ((LOTREntityElf)entity).getBowingAmount(LOTRTickHandlerClient.renderTick);
            bowing = (bowAmount != 0.0f);
        }
        if (bowing) {
            bowAmount *= 30.0f;
            final float bowAmountRad = (float)Math.toRadians(bowAmount);
            final float bowCos = MathHelper.cos(bowAmountRad);
            final float bowSin = MathHelper.sin(bowAmountRad);
            super.bipedHead.rotationPointY = this.base_headY + 12.0f * (1.0f - bowCos);
            super.bipedHead.rotationPointZ = this.base_headY - 12.0f * bowSin;
            super.bipedHeadwear.rotationPointY = super.bipedHead.rotationPointY;
            super.bipedHeadwear.rotationPointZ = super.bipedHead.rotationPointZ;
            super.bipedBody.rotationPointY = this.base_bodyY + 12.0f * (1.0f - bowCos);
            super.bipedBody.rotationPointZ = this.base_bodyZ - 12.0f * bowSin;
            super.bipedRightArm.rotationPointY = this.base_armY + 10.0f * (1.0f - bowCos);
            super.bipedRightArm.rotationPointZ = this.base_armY - 12.0f * bowSin;
            super.bipedLeftArm.rotationPointY = super.bipedRightArm.rotationPointY;
            super.bipedLeftArm.rotationPointZ = super.bipedRightArm.rotationPointZ;
            super.bipedHead.rotateAngleX = bowAmountRad;
            super.bipedHeadwear.rotateAngleX = bowAmountRad;
            super.bipedBody.rotateAngleX = bowAmountRad;
            super.bipedRightArm.rotateAngleX = bowAmountRad;
            super.bipedLeftArm.rotateAngleX = bowAmountRad;
        }
        else {
            if (!super.isSneak) {
                super.bipedHead.rotationPointY = this.base_headY;
                super.bipedHead.rotationPointZ = this.base_headZ;
                super.bipedHeadwear.rotationPointY = this.base_headY;
                super.bipedHeadwear.rotationPointZ = this.base_headZ;
            }
            super.bipedBody.rotationPointY = this.base_bodyY;
            super.bipedBody.rotationPointZ = this.base_bodyZ;
            super.bipedRightArm.rotationPointY = this.base_armY;
            super.bipedRightArm.rotationPointZ = this.base_armZ;
            super.bipedLeftArm.rotationPointY = this.base_armY;
            super.bipedLeftArm.rotationPointZ = this.base_armZ;
        }
    }
}
