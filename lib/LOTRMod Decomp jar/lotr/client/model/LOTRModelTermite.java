// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelTermite extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer head;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg5;
    private ModelRenderer leg4;
    private ModelRenderer leg6;
    private ModelRenderer rightfeeler;
    private ModelRenderer leftfeeler;
    
    public LOTRModelTermite() {
        (this.body = new ModelRenderer((ModelBase)this, 10, 5)).addBox(0.0f, 0.0f, 0.0f, 6, 6, 21);
        this.body.setRotationPoint(-3.0f, 17.0f, -5.0f);
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 8, 8, 7);
        this.head.setRotationPoint(-4.0f, 14.0f, -10.0f);
        (this.leg1 = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg1.setRotationPoint(-2.0f, 19.0f, 1.0f);
        (this.leg2 = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg2.setRotationPoint(2.0f, 19.0f, 1.0f);
        (this.leg3 = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg3.setRotationPoint(-2.0f, 19.0f, 0.0f);
        (this.leg4 = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg4.setRotationPoint(2.0f, 19.0f, 0.0f);
        (this.leg5 = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg5.setRotationPoint(-2.0f, 19.0f, -1.0f);
        (this.leg6 = new ModelRenderer((ModelBase)this, 34, 0)).addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg6.setRotationPoint(2.0f, 19.0f, -1.0f);
        (this.rightfeeler = new ModelRenderer((ModelBase)this, 50, 18)).addBox(0.0f, 0.0f, -8.0f, 1, 1, 6);
        this.rightfeeler.setRotationPoint(-3.0f, 15.0f, -8.0f);
        this.rightfeeler.rotateAngleY = -0.1f;
        (this.leftfeeler = new ModelRenderer((ModelBase)this, 50, 18)).addBox(0.0f, 0.0f, -8.0f, 1, 1, 6);
        this.leftfeeler.setRotationPoint(2.0f, 15.0f, -8.0f);
        this.leftfeeler.rotateAngleY = -0.1f;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.head.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
        this.leg5.render(f5);
        this.leg6.render(f5);
        this.rightfeeler.render(f5);
        this.leftfeeler.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        final float f6 = -0.51460177f;
        this.leg1.rotateAngleZ = f6;
        this.leg2.rotateAngleZ = -f6;
        this.leg3.rotateAngleZ = f6 * 0.74f;
        this.leg4.rotateAngleZ = -f6 * 0.74f;
        this.leg5.rotateAngleZ = f6 * 0.74f;
        this.leg6.rotateAngleZ = -f6 * 0.74f;
        final float f7 = -0.0f;
        final float f8 = 0.3926991f;
        this.leg1.rotateAngleY = f8 * 2.0f + f7;
        this.leg2.rotateAngleY = -f8 * 2.0f - f7;
        this.leg3.rotateAngleY = f8 * 1.0f + f7;
        this.leg4.rotateAngleY = -f8 * 1.0f - f7;
        this.leg5.rotateAngleY = -f8 * 1.0f + f7;
        this.leg6.rotateAngleY = f8 * 1.0f - f7;
        final float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 0.0f) * 0.4f) * f1;
        final float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * f1;
        final float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f1;
        final float f12 = -(MathHelper.cos(f * 0.6662f * 2.0f + 4.712389f) * 0.4f) * f1;
        final float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f1;
        final float f14 = Math.abs(MathHelper.sin(f * 0.6662f + 3.1415927f) * 0.4f) * f1;
        final float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f1;
        final ModelRenderer leg1 = this.leg1;
        leg1.rotateAngleY += f9;
        final ModelRenderer leg2 = this.leg2;
        leg2.rotateAngleY += -f9;
        final ModelRenderer leg3 = this.leg3;
        leg3.rotateAngleY += f10;
        final ModelRenderer leg4 = this.leg4;
        leg4.rotateAngleY += -f10;
        final ModelRenderer leg5 = this.leg5;
        leg5.rotateAngleY += f11;
        final ModelRenderer leg6 = this.leg6;
        leg6.rotateAngleY += -f11;
        final ModelRenderer leg7 = this.leg1;
        leg7.rotateAngleZ += f13;
        final ModelRenderer leg8 = this.leg2;
        leg8.rotateAngleZ += -f13;
        final ModelRenderer leg9 = this.leg3;
        leg9.rotateAngleZ += f14;
        final ModelRenderer leg10 = this.leg4;
        leg10.rotateAngleZ += -f14;
        final ModelRenderer leg11 = this.leg5;
        leg11.rotateAngleZ += f15;
        final ModelRenderer leg12 = this.leg6;
        leg12.rotateAngleZ += -f15;
    }
}
