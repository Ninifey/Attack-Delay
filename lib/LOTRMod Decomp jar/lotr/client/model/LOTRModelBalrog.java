// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelBalrog extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer head;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer tail;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    private boolean isFireModel;
    public int heldItemRight;
    
    public LOTRModelBalrog() {
        this(0.0f);
    }
    
    public LOTRModelBalrog(final float f) {
        super.textureWidth = 128;
        super.textureHeight = 256;
        (this.body = new ModelRenderer((ModelBase)this, 0, 38)).setRotationPoint(0.0f, 7.0f, 3.0f);
        this.body.addBox(-8.0f, -15.0f, -6.0f, 16, 18, 12, f);
        this.body.setTextureOffset(0, 207);
        this.body.addBox(-9.0f, -6.5f, -7.0f, 7, 1, 14, f);
        this.body.addBox(-9.0f, -9.5f, -7.0f, 7, 1, 14, f);
        this.body.addBox(-9.0f, -12.5f, -7.0f, 7, 1, 14, f);
        this.body.mirror = true;
        this.body.addBox(2.0f, -6.5f, -7.0f, 7, 1, 14, f);
        this.body.addBox(2.0f, -9.5f, -7.0f, 7, 1, 14, f);
        this.body.addBox(2.0f, -12.5f, -7.0f, 7, 1, 14, f);
        this.body.mirror = false;
        this.body.setTextureOffset(0, 0).addBox(-9.0f, -29.0f, -7.0f, 18, 14, 15, f);
        this.body.setTextureOffset(81, 163).addBox(-2.0f, -21.0f, 5.5f, 4, 25, 2, f);
        (this.neck = new ModelRenderer((ModelBase)this, 76, 0)).setRotationPoint(0.0f, -25.0f, -3.0f);
        this.neck.addBox(-6.0f, -5.0f, -10.0f, 12, 12, 14, f);
        this.body.addChild(this.neck);
        (this.head = new ModelRenderer((ModelBase)this, 92, 48)).setRotationPoint(0.0f, 0.0f, -10.0f);
        this.head.addBox(-4.0f, -6.0f, -6.0f, 8, 10, 7, f);
        this.head.setTextureOffset(57, 58).addBox(-6.0f, -7.0f, -4.0f, 12, 4, 4, f);
        this.head.rotateAngleX = (float)Math.toRadians(10.0);
        this.neck.addChild(this.head);
        final ModelRenderer rightHorn1 = new ModelRenderer((ModelBase)this, 57, 47);
        rightHorn1.setRotationPoint(-6.0f, -5.0f, -2.0f);
        rightHorn1.addBox(-7.0f, -1.5f, -1.5f, 8, 3, 3, f);
        rightHorn1.rotateAngleY = (float)Math.toRadians(-35.0);
        this.head.addChild(rightHorn1);
        final ModelRenderer rightHorn2 = new ModelRenderer((ModelBase)this, 57, 35);
        rightHorn2.setRotationPoint(-7.0f, 0.0f, 0.0f);
        rightHorn2.addBox(-1.0f, -1.0f, -6.0f, 2, 2, 6, f);
        rightHorn2.rotateAngleY = (float)Math.toRadians(45.0);
        rightHorn1.addChild(rightHorn2);
        final ModelRenderer leftHorn1 = new ModelRenderer((ModelBase)this, 57, 47);
        leftHorn1.setRotationPoint(6.0f, -5.0f, -2.0f);
        leftHorn1.mirror = true;
        leftHorn1.addBox(-1.0f, -1.5f, -1.5f, 8, 3, 3, f);
        leftHorn1.rotateAngleY = (float)Math.toRadians(35.0);
        this.head.addChild(leftHorn1);
        final ModelRenderer leftHorn2 = new ModelRenderer((ModelBase)this, 57, 35);
        leftHorn2.setRotationPoint(7.0f, 0.0f, 0.0f);
        leftHorn2.mirror = true;
        leftHorn2.addBox(-1.0f, -1.0f, -6.0f, 2, 2, 6, f);
        leftHorn2.rotateAngleY = (float)Math.toRadians(-45.0);
        leftHorn1.addChild(leftHorn2);
        (this.rightArm = new ModelRenderer((ModelBase)this, 59, 136)).setRotationPoint(-9.0f, -25.0f, 0.0f);
        this.rightArm.addBox(-7.0f, -2.0f, -4.0f, 7, 10, 8, f);
        this.rightArm.setTextureOffset(93, 136).addBox(-6.5f, 8.0f, -3.0f, 6, 16, 6, f);
        this.body.addChild(this.rightArm);
        (this.leftArm = new ModelRenderer((ModelBase)this, 59, 136)).setRotationPoint(9.0f, -25.0f, 0.0f);
        this.leftArm.mirror = true;
        this.leftArm.addBox(0.0f, -2.0f, -4.0f, 7, 10, 8, f);
        this.leftArm.setTextureOffset(93, 136).addBox(0.5f, 8.0f, -3.0f, 6, 16, 6, f);
        this.body.addChild(this.leftArm);
        (this.rightLeg = new ModelRenderer((ModelBase)this, 46, 230)).setRotationPoint(-6.0f, 6.0f, 3.0f);
        this.rightLeg.addBox(-7.0f, -2.0f, -4.0f, 7, 9, 8, f);
        this.rightLeg.setTextureOffset(46, 208).addBox(-6.5f, 2.0f, 4.0f, 6, 13, 5, f);
        final ModelRenderer rightFoot = new ModelRenderer((ModelBase)this, 0, 243);
        rightFoot.setRotationPoint(0.0f, 0.0f, 0.0f);
        rightFoot.addBox(-7.0f, 15.0f, -6.0f, 7, 3, 9, f);
        rightFoot.rotateAngleX = (float)Math.toRadians(25.0);
        this.rightLeg.addChild(rightFoot);
        (this.leftLeg = new ModelRenderer((ModelBase)this, 46, 230)).setRotationPoint(6.0f, 6.0f, 3.0f);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(0.0f, -2.0f, -4.0f, 7, 9, 8, f);
        this.leftLeg.setTextureOffset(46, 208).addBox(0.5f, 2.0f, 4.0f, 6, 13, 5, f);
        final ModelRenderer leftFoot = new ModelRenderer((ModelBase)this, 0, 243);
        leftFoot.setRotationPoint(0.0f, 0.0f, 0.0f);
        leftFoot.mirror = true;
        leftFoot.addBox(0.0f, 15.0f, -6.0f, 7, 3, 9, f);
        leftFoot.rotateAngleX = (float)Math.toRadians(25.0);
        this.leftLeg.addChild(leftFoot);
        (this.tail = new ModelRenderer((ModelBase)this, 79, 200)).setRotationPoint(0.0f, -3.0f, 3.0f);
        this.tail.addBox(-3.5f, -3.0f, 2.0f, 7, 7, 10, f);
        this.tail.setTextureOffset(80, 225).addBox(-2.5f, -2.5f, 11.0f, 5, 5, 14, f);
        this.tail.setTextureOffset(96, 175).addBox(-1.5f, -2.0f, 24.0f, 3, 3, 12, f);
        this.body.addChild(this.tail);
        (this.rightWing = new ModelRenderer((ModelBase)this, 0, 137)).setRotationPoint(-6.0f, -27.0f, 4.0f);
        this.rightWing.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 25, f);
        this.rightWing.setTextureOffset(0, 167).addBox(-1.0f, -2.0f, 25.0f, 2, 24, 2, f);
        this.rightWing.setTextureOffset(0, 30).addBox(-0.5f, -7.0f, 25.5f, 1, 5, 1, f);
        this.rightWing.setTextureOffset(0, 69).addBox(0.0f, 0.0f, 0.0f, 0, 35, 25, f);
        this.body.addChild(this.rightWing);
        (this.leftWing = new ModelRenderer((ModelBase)this, 0, 137)).setRotationPoint(6.0f, -27.0f, 4.0f);
        this.leftWing.mirror = true;
        this.leftWing.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 25, f);
        this.leftWing.setTextureOffset(0, 167).addBox(-1.0f, -2.0f, 25.0f, 2, 24, 2, f);
        this.leftWing.setTextureOffset(0, 30).addBox(-0.5f, -7.0f, 25.5f, 1, 5, 1, f);
        this.leftWing.setTextureOffset(0, 69).addBox(0.0f, 0.0f, 0.0f, 0, 35, 25, f);
        this.body.addChild(this.leftWing);
    }
    
    public void setFireModel() {
        this.isFireModel = true;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        if (this.isFireModel) {
            final ModelRenderer leftWing = this.leftWing;
            final ModelRenderer rightWing = this.rightWing;
            final boolean b = false;
            rightWing.showModel = b;
            leftWing.showModel = b;
        }
        else {
            final ModelRenderer leftWing2 = this.leftWing;
            final ModelRenderer rightWing2 = this.rightWing;
            final boolean b2 = LOTRConfig.balrogWings && balrog.isWreathedInFlame();
            rightWing2.showModel = b2;
            leftWing2.showModel = b2;
        }
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        final LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        this.neck.rotateAngleX = (float)Math.toRadians(-10.0);
        this.neck.rotateAngleY = 0.0f;
        final ModelRenderer neck = this.neck;
        neck.rotateAngleX += f4 / (float)Math.toDegrees(1.0);
        final ModelRenderer neck2 = this.neck;
        neck2.rotateAngleY += f3 / (float)Math.toDegrees(1.0);
        this.body.rotateAngleX = (float)Math.toRadians(10.0);
        final ModelRenderer body = this.body;
        body.rotateAngleX += MathHelper.cos(f2 * 0.03f) * 0.15f;
        this.rightArm.rotateAngleX = 0.0f;
        this.leftArm.rotateAngleX = 0.0f;
        this.rightArm.rotateAngleZ = 0.0f;
        this.leftArm.rotateAngleZ = 0.0f;
        final ModelRenderer rightArm = this.rightArm;
        rightArm.rotateAngleX += MathHelper.cos(f * 0.4f + 3.1415927f) * 0.8f * f1;
        final ModelRenderer leftArm = this.leftArm;
        leftArm.rotateAngleX += MathHelper.cos(f * 0.4f) * 0.8f * f1;
        final ModelRenderer rightArm2 = this.rightArm;
        rightArm2.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer leftArm2 = this.leftArm;
        leftArm2.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        if (super.onGround > -9990.0f) {
            float f6 = super.onGround;
            final ModelRenderer rightArm3 = this.rightArm;
            rightArm3.rotateAngleY += this.body.rotateAngleY;
            final ModelRenderer leftArm3 = this.leftArm;
            leftArm3.rotateAngleY += this.body.rotateAngleY;
            final ModelRenderer leftArm4 = this.leftArm;
            leftArm4.rotateAngleX += this.body.rotateAngleY;
            f6 = 1.0f - super.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0f - f6;
            final float f7 = MathHelper.sin(f6 * 3.1415927f);
            final float f8 = MathHelper.sin(super.onGround * 3.1415927f) * -(this.head.rotateAngleX - 0.7f) * 0.75f;
            this.rightArm.rotateAngleX -= (float)(f7 * 1.2 + f8);
            final ModelRenderer rightArm4 = this.rightArm;
            rightArm4.rotateAngleY += this.body.rotateAngleY * 2.0f;
            this.rightArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927f) * -0.4f;
        }
        if (this.heldItemRight != 0) {
            this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
        }
        this.rightLeg.rotateAngleX = (float)Math.toRadians(-25.0);
        this.leftLeg.rotateAngleX = (float)Math.toRadians(-25.0);
        final ModelRenderer rightLeg = this.rightLeg;
        rightLeg.rotateAngleX += MathHelper.sin(f * 0.4f) * 1.2f * f1;
        final ModelRenderer leftLeg = this.leftLeg;
        leftLeg.rotateAngleX += MathHelper.sin(f * 0.4f + 3.1415927f) * 1.2f * f1;
        this.rightWing.rotateAngleX = (float)Math.toRadians(40.0);
        this.leftWing.rotateAngleX = (float)Math.toRadians(40.0);
        this.rightWing.rotateAngleY = (float)Math.toRadians(-40.0);
        this.leftWing.rotateAngleY = (float)Math.toRadians(40.0);
        final ModelRenderer rightWing = this.rightWing;
        rightWing.rotateAngleY += MathHelper.cos(f2 * 0.04f) * 0.5f;
        final ModelRenderer leftWing = this.leftWing;
        leftWing.rotateAngleY -= MathHelper.cos(f2 * 0.04f) * 0.5f;
        this.tail.rotateAngleX = (float)Math.toRadians(-40.0);
        this.tail.rotateAngleY = 0.0f;
        final ModelRenderer tail = this.tail;
        tail.rotateAngleY += MathHelper.cos(f2 * 0.05f) * 0.15f;
        final ModelRenderer tail2 = this.tail;
        tail2.rotateAngleY += MathHelper.sin(f * 0.1f) * 0.6f * f1;
    }
}
