// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelGollum extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightShoulder;
    public ModelRenderer rightArm;
    public ModelRenderer leftShoulder;
    public ModelRenderer leftArm;
    public ModelRenderer rightThigh;
    public ModelRenderer rightLeg;
    public ModelRenderer leftThigh;
    public ModelRenderer leftLeg;
    
    public LOTRModelGollum() {
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.5f, -6.5f, -6.5f, 7, 7, 7);
        this.head.setRotationPoint(0.0f, 5.0f, -5.5f);
        this.head.addBox(3.5f, -4.0f, -4.0f, 1, 2, 2);
        this.head.mirror = true;
        this.head.addBox(-4.5f, -4.0f, -4.0f, 1, 2, 2);
        (this.body = new ModelRenderer((ModelBase)this, 20, 17)).addBox(-5.0f, -12.0f, -2.0f, 10, 12, 3);
        this.body.setRotationPoint(0.0f, 11.0f, 5.0f);
        this.body.setTextureOffset(32, 0).addBox(-5.5f, -2.0f, -3.5f, 11, 4, 5);
        this.body.rotateAngleX = 1.0471976f;
        (this.rightShoulder = new ModelRenderer((ModelBase)this, 0, 23)).addBox(-0.5f, -1.0f, -2.0f, 3, 6, 3);
        this.rightShoulder.setRotationPoint(5.0f, 6.0f, -4.5f);
        this.rightShoulder.rotateAngleX = 0.5235988f;
        (this.rightArm = new ModelRenderer((ModelBase)this, 12, 22)).addBox(0.0f, 4.0f, 0.5f, 2, 8, 2);
        this.rightArm.setRotationPoint(5.0f, 6.0f, -4.5f);
        this.leftShoulder = new ModelRenderer((ModelBase)this, 0, 23);
        this.leftShoulder.mirror = true;
        this.leftShoulder.addBox(-1.5f, -1.0f, -2.0f, 3, 6, 3);
        this.leftShoulder.setRotationPoint(-5.0f, 6.0f, -4.5f);
        this.leftShoulder.rotateAngleX = 0.5235988f;
        this.leftArm = new ModelRenderer((ModelBase)this, 12, 22);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1.0f, 4.0f, 0.5f, 2, 8, 2);
        this.leftArm.setRotationPoint(-5.0f, 6.0f, -4.5f);
        (this.rightThigh = new ModelRenderer((ModelBase)this, 0, 23)).addBox(-0.5f, -1.0f, -1.0f, 3, 6, 3);
        this.rightThigh.setRotationPoint(2.0f, 12.0f, 4.0f);
        this.rightThigh.rotateAngleX = -0.43633232f;
        (this.rightLeg = new ModelRenderer((ModelBase)this, 12, 22)).addBox(0.0f, 4.0f, -2.5f, 2, 8, 2);
        this.rightLeg.setRotationPoint(2.0f, 12.0f, 4.0f);
        (this.leftThigh = new ModelRenderer((ModelBase)this, 0, 23)).addBox(-2.5f, -1.0f, -1.0f, 3, 6, 3);
        this.leftThigh.setRotationPoint(-2.0f, 12.0f, 4.0f);
        this.leftThigh.rotateAngleX = -0.43633232f;
        (this.leftLeg = new ModelRenderer((ModelBase)this, 12, 22)).addBox(-2.0f, 4.0f, -2.5f, 2, 8, 2);
        this.leftLeg.setRotationPoint(-2.0f, 12.0f, 4.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.render(f5);
        this.body.render(f5);
        this.rightShoulder.render(f5);
        this.rightArm.render(f5);
        this.leftShoulder.render(f5);
        this.leftArm.render(f5);
        this.rightThigh.render(f5);
        this.rightLeg.render(f5);
        this.leftThigh.render(f5);
        this.leftLeg.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        this.head.rotateAngleY = f3 / 57.295776f;
        this.head.rotateAngleX = f4 / 57.295776f;
        this.rightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
        this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
        this.rightArm.rotateAngleZ = 0.0f;
        this.leftArm.rotateAngleZ = 0.0f;
        this.rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.rightLeg.rotateAngleY = 0.0f;
        this.leftLeg.rotateAngleY = 0.0f;
        this.rightArm.rotateAngleY = 0.0f;
        this.leftArm.rotateAngleY = 0.0f;
        final ModelRenderer rightArm = this.rightArm;
        rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer leftArm = this.leftArm;
        leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer rightArm2 = this.rightArm;
        rightArm2.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
        final ModelRenderer leftArm2 = this.leftArm;
        leftArm2.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
        if (((LOTREntityGollum)entity).isGollumSitting()) {
            float f6 = f2 / 20.0f;
            f6 *= 6.2831855f;
            this.rightArm.rotateAngleX = MathHelper.sin(f6) * 3.0f;
            this.leftArm.rotateAngleX = MathHelper.sin(f6) * -3.0f;
            this.rightLeg.rotateAngleX = MathHelper.sin(f6) * 0.5f;
            this.leftLeg.rotateAngleX = MathHelper.sin(f6) * -0.5f;
        }
        else if (((LOTREntityGollum)entity).isGollumFleeing()) {
            final ModelRenderer rightArm3 = this.rightArm;
            rightArm3.rotateAngleX += 3.1415927f;
            final ModelRenderer leftArm3 = this.leftArm;
            leftArm3.rotateAngleX += 3.1415927f;
        }
        this.body.rotateAngleZ = MathHelper.cos(f * 0.6662f) * 0.25f * f1;
        this.syncRotationAngles(this.rightArm, this.rightShoulder, 30.0f);
        this.syncRotationAngles(this.leftArm, this.leftShoulder, 30.0f);
        this.syncRotationAngles(this.rightLeg, this.rightThigh, -25.0f);
        this.syncRotationAngles(this.leftLeg, this.leftThigh, -25.0f);
    }
    
    private void syncRotationAngles(final ModelRenderer source, final ModelRenderer target, final float additionalAngle) {
        target.rotationPointX = source.rotationPointX;
        target.rotationPointY = source.rotationPointY;
        target.rotationPointZ = source.rotationPointZ;
        target.rotateAngleX = source.rotateAngleX + 0.017453292f * additionalAngle;
        target.rotateAngleY = source.rotateAngleY;
        target.rotateAngleZ = source.rotateAngleZ;
    }
}
