// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelRabbit extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer body;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    
    public LOTRModelRabbit() {
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4);
        this.head.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.head.setTextureOffset(0, 8).addBox(-1.5f, 0.0f, -3.0f, 3, 2, 2);
        final ModelRenderer rightEar = new ModelRenderer((ModelBase)this, 16, 0);
        rightEar.addBox(-1.2f, -4.5f, -0.5f, 2, 5, 1);
        rightEar.setRotationPoint(-1.0f, -1.5f, 0.0f);
        rightEar.rotateAngleX = (float)Math.toRadians(-20.0);
        final ModelRenderer leftEar = new ModelRenderer((ModelBase)this, 16, 0);
        leftEar.mirror = true;
        leftEar.addBox(-0.8f, -4.5f, -0.5f, 2, 5, 1);
        leftEar.setRotationPoint(1.0f, -1.5f, 0.0f);
        leftEar.rotateAngleX = (float)Math.toRadians(-20.0);
        this.head.addChild(rightEar);
        this.head.addChild(leftEar);
        (this.body = new ModelRenderer((ModelBase)this, 0, 19)).addBox(-2.5f, -4.0f, -2.5f, 5, 8, 5);
        this.body.setRotationPoint(0.0f, 18.5f, 0.0f);
        this.body.setTextureOffset(0, 14).addBox(-1.5f, -6.0f, -1.5f, 3, 2, 3);
        final ModelRenderer tail = new ModelRenderer((ModelBase)this, 32, 30);
        tail.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1);
        tail.setRotationPoint(0.0f, 4.5f, 2.5f);
        tail.rotateAngleX = (float)Math.toRadians(-45.0);
        this.body.addChild(this.head);
        this.body.addChild(tail);
        (this.rightArm = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.rightArm.setRotationPoint(-1.5f, -2.0f, -2.5f);
        this.leftArm = new ModelRenderer((ModelBase)this, 32, 0);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.leftArm.setRotationPoint(1.5f, -2.0f, -2.5f);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.leftArm);
        (this.rightLeg = new ModelRenderer((ModelBase)this, 32, 8)).addBox(-1.0f, -2.0f, -2.0f, 2, 4, 4);
        this.rightLeg.setRotationPoint(-3.0f, 21.5f, 1.0f);
        final ModelRenderer rightFoot = new ModelRenderer((ModelBase)this, 32, 16);
        rightFoot.addBox(-1.0f, -0.5f, -2.5f, 2, 1, 3);
        rightFoot.setRotationPoint(0.0f, 2.0f, -1.0f);
        rightFoot.rotateAngleX = (float)Math.toRadians(-15.0);
        this.rightLeg.addChild(rightFoot);
        this.leftLeg = new ModelRenderer((ModelBase)this, 32, 8);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-1.0f, -2.0f, -2.0f, 2, 4, 4);
        this.leftLeg.setRotationPoint(3.0f, 21.5f, 1.0f);
        final ModelRenderer leftFoot = new ModelRenderer((ModelBase)this, 32, 16);
        leftFoot.mirror = true;
        leftFoot.addBox(-1.0f, -0.5f, -2.5f, 2, 1, 3);
        leftFoot.setRotationPoint(0.0f, 2.0f, -1.0f);
        leftFoot.rotateAngleX = (float)Math.toRadians(-15.0);
        this.leftLeg.addChild(leftFoot);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        this.body.rotateAngleX = (float)Math.toRadians(45.0);
        this.head.rotateAngleX = (float)Math.toRadians(-45.0);
        this.rightArm.rotateAngleX = (float)Math.toRadians(-55.0);
        this.leftArm.rotateAngleX = (float)Math.toRadians(-55.0);
        float f6 = (float)Math.toRadians(45.0);
        if (LOTRMod.isAprilFools()) {
            f6 *= f2;
        }
        else {
            f6 *= f1;
        }
        final ModelRenderer body = this.body;
        body.rotateAngleX += f6;
        final ModelRenderer head = this.head;
        head.rotateAngleX -= f6;
        final ModelRenderer rightArm = this.rightArm;
        rightArm.rotateAngleX -= f6;
        final ModelRenderer leftArm = this.leftArm;
        leftArm.rotateAngleX -= f6;
        if (((LOTREntityRabbit)entity).isRabbitEating()) {
            final float f7 = (float)Math.toRadians(30.0);
            final ModelRenderer body2 = this.body;
            body2.rotateAngleX += f7;
            final ModelRenderer rightArm2 = this.rightArm;
            rightArm2.rotateAngleX += f7;
            final ModelRenderer leftArm2 = this.leftArm;
            leftArm2.rotateAngleX += f7;
            final ModelRenderer head2 = this.head;
            head2.rotateAngleX += f7 * 2.0f;
        }
        else {
            final ModelRenderer head3 = this.head;
            head3.rotateAngleX += f4 / 57.295776f;
            this.head.rotateAngleY = MathHelper.cos(this.head.rotateAngleX) * f3 / 57.295776f;
            this.head.rotateAngleZ = MathHelper.sin(this.head.rotateAngleX) * f3 / 57.295776f;
        }
        final ModelRenderer rightArm3 = this.rightArm;
        rightArm3.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.1415927f) * f1;
        final ModelRenderer leftArm3 = this.leftArm;
        leftArm3.rotateAngleX += MathHelper.cos(f * 0.6662f) * f1;
        this.body.rotateAngleZ = MathHelper.cos(f * 0.6662f) * f1 * 0.3f;
        this.rightLeg.rotateAngleX = (float)Math.toRadians(15.0);
        this.leftLeg.rotateAngleX = (float)Math.toRadians(15.0);
        final ModelRenderer rightLeg = this.rightLeg;
        rightLeg.rotateAngleX += MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        final ModelRenderer leftLeg = this.leftLeg;
        leftLeg.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
    }
}
