// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelBird extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    
    public LOTRModelBird() {
        (this.body = new ModelRenderer((ModelBase)this, 0, 7)).addBox(-1.5f, -2.0f, -2.0f, 3, 3, 5);
        this.body.setTextureOffset(8, 0).addBox(-1.0f, -1.5f, 3.0f, 2, 1, 3);
        this.body.setTextureOffset(8, 4).addBox(-1.0f, -0.5f, 3.0f, 2, 1, 2);
        this.body.setRotationPoint(0.0f, 21.0f, 0.0f);
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-1.0f, -1.5f, -1.5f, 2, 2, 2);
        this.head.setTextureOffset(0, 4).addBox(-0.5f, -0.5f, -2.5f, 1, 1, 1);
        this.head.setTextureOffset(15, 0).addBox(-0.5f, -0.5f, -3.5f, 1, 1, 2);
        this.head.setRotationPoint(0.0f, -2.0f, -2.0f);
        this.body.addChild(this.head);
        (this.wingRight = new ModelRenderer((ModelBase)this, 16, 7)).addBox(0.0f, 0.0f, -2.0f, 0, 5, 4);
        this.wingRight.setRotationPoint(-1.5f, -1.5f, 0.5f);
        this.body.addChild(this.wingRight);
        this.wingLeft = new ModelRenderer((ModelBase)this, 16, 7);
        this.wingLeft.mirror = true;
        this.wingLeft.addBox(0.0f, 0.0f, -2.0f, 0, 5, 4);
        this.wingLeft.setRotationPoint(1.5f, -1.5f, 0.5f);
        this.body.addChild(this.wingLeft);
        (this.legRight = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-1.0f, 0.0f, -1.5f, 1, 2, 2);
        this.legRight.setRotationPoint(-0.3f, 1.0f, 0.5f);
        this.body.addChild(this.legRight);
        this.legLeft = new ModelRenderer((ModelBase)this, 0, 16);
        this.legLeft.mirror = true;
        this.legLeft.addBox(0.0f, 0.0f, -1.5f, 1, 2, 2);
        this.legLeft.setRotationPoint(0.3f, 1.0f, 0.5f);
        this.body.addChild(this.legLeft);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        final LOTREntityBird bird = (LOTREntityBird)entity;
        if (bird.isBirdStill()) {
            this.body.rotateAngleX = (float)Math.toRadians(-10.0);
            this.head.rotateAngleX = (float)Math.toRadians(20.0);
            if (bird.flapTime > 0) {
                this.wingRight.rotateAngleZ = (float)Math.toRadians(90.0) + MathHelper.cos(f2 * 1.5f) * (float)Math.toRadians(30.0);
            }
            else {
                this.wingRight.rotateAngleZ = (float)Math.toRadians(30.0);
            }
            this.wingLeft.rotateAngleZ = -this.wingRight.rotateAngleZ;
            final ModelRenderer legRight = this.legRight;
            final ModelRenderer legLeft = this.legLeft;
            final float n = -this.body.rotateAngleX;
            legLeft.rotateAngleX = n;
            legRight.rotateAngleX = n;
            final ModelRenderer legRight2 = this.legRight;
            legRight2.rotateAngleX += MathHelper.cos(f * 0.6662f) * f1;
            final ModelRenderer legLeft2 = this.legLeft;
            legLeft2.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.141593f) * f1;
            this.legRight.rotationPointY = 1.0f;
            this.legLeft.rotationPointY = 1.0f;
        }
        else {
            this.body.rotateAngleX = 0.0f;
            this.head.rotateAngleX = 0.0f;
            this.wingRight.rotateAngleZ = (float)Math.toRadians(90.0) + MathHelper.cos(f2 * 1.5f) * (float)Math.toRadians(30.0);
            this.wingLeft.rotateAngleZ = -this.wingRight.rotateAngleZ;
            final ModelRenderer legRight3 = this.legRight;
            final ModelRenderer legLeft3 = this.legLeft;
            final float n2 = 0.0f;
            legLeft3.rotateAngleX = n2;
            legRight3.rotateAngleX = n2;
            this.legRight.rotationPointY = 0.0f;
            this.legLeft.rotationPointY = 0.0f;
        }
    }
}
