// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelGemsbok extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer tail;
    private ModelRenderer earLeft;
    private ModelRenderer earRight;
    private ModelRenderer neck;
    private ModelRenderer body;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    private ModelRenderer leftHorn;
    private ModelRenderer rightHorn;
    
    public LOTRModelGemsbok() {
        (this.head = new ModelRenderer((ModelBase)this, 28, 0).setTextureSize(128, 64)).addBox(-3.0f, -10.0f, -6.0f, 6, 7, 12);
        this.head.setRotationPoint(0.0f, 4.0f, -9.0f);
        (this.tail = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(128, 64)).addBox(0.0f, 0.0f, 0.0f, 2, 12, 2);
        this.tail.setRotationPoint(-1.0f, 3.0f, 11.0f);
        (this.earLeft = new ModelRenderer((ModelBase)this, 28, 19).setTextureSize(128, 64)).addBox(-3.8f, -12.0f, 3.0f, 1, 3, 2);
        this.earLeft.setRotationPoint(0.0f, 4.0f, -9.0f);
        (this.earRight = new ModelRenderer((ModelBase)this, 34, 19).setTextureSize(128, 64)).addBox(2.8f, -12.0f, 3.0f, 1, 3, 2);
        this.earRight.setRotationPoint(0.0f, 4.0f, -9.0f);
        (this.neck = new ModelRenderer((ModelBase)this, 0, 14).setTextureSize(128, 64)).addBox(-2.5f, -6.0f, -5.0f, 5, 8, 9);
        this.neck.setRotationPoint(0.0f, 4.0f, -9.0f);
        (this.body = new ModelRenderer((ModelBase)this, 0, 31).setTextureSize(128, 64)).addBox(-7.0f, -10.0f, -7.0f, 13, 10, 23);
        this.body.setRotationPoint(0.5f, 12.0f, -3.0f);
        (this.leg1 = new ModelRenderer((ModelBase)this, 0, 38).setTextureSize(128, 64)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4);
        this.leg1.setRotationPoint(-4.0f, 12.0f, 10.0f);
        (this.leg2 = new ModelRenderer((ModelBase)this, 0, 38).setTextureSize(128, 64)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4);
        this.leg2.setRotationPoint(4.0f, 12.0f, 10.0f);
        (this.leg3 = new ModelRenderer((ModelBase)this, 0, 38).setTextureSize(128, 64)).addBox(-2.0f, 0.0f, -3.0f, 4, 12, 4);
        this.leg3.setRotationPoint(-4.0f, 12.0f, -7.0f);
        (this.leg4 = new ModelRenderer((ModelBase)this, 0, 38).setTextureSize(128, 64)).addBox(-2.0f, 0.0f, -3.0f, 4, 12, 4);
        this.leg4.setRotationPoint(4.0f, 12.0f, -7.0f);
        (this.leftHorn = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(128, 64)).addBox(-2.8f, -9.5f, 5.8f, 1, 1, 13);
        this.leftHorn.setRotationPoint(0.0f, 4.0f, -9.0f);
        (this.rightHorn = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(128, 64)).addBox(1.8f, -9.5f, 5.8f, 1, 1, 13);
        this.rightHorn.setRotationPoint(0.0f, 4.0f, -9.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (super.isChild) {
            final float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.head.render(f5);
            this.earLeft.render(f5);
            this.earRight.render(f5);
            this.neck.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            this.tail.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.neck.render(f5);
            this.leftHorn.render(f5);
            this.rightHorn.render(f5);
            this.earLeft.render(f5);
            this.earRight.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            this.tail.render(f5);
        }
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        this.head.rotateAngleX = (float)Math.toRadians(f4) + 0.4014257f;
        this.head.rotateAngleY = (float)Math.toRadians(f3);
        this.neck.rotateAngleX = (float)Math.toRadians(-61.0);
        this.neck.rotateAngleY = this.head.rotateAngleY * 0.7f;
        this.rightHorn.rotateAngleX = this.head.rotateAngleX;
        this.rightHorn.rotateAngleY = this.head.rotateAngleY;
        this.leftHorn.rotateAngleX = this.head.rotateAngleX;
        this.leftHorn.rotateAngleY = this.head.rotateAngleY;
        this.earLeft.rotateAngleX = this.head.rotateAngleX;
        this.earLeft.rotateAngleY = this.head.rotateAngleY;
        this.earRight.rotateAngleX = this.head.rotateAngleX;
        this.earRight.rotateAngleY = this.head.rotateAngleY;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.tail.rotateAngleX = (float)Math.toRadians(17.0);
    }
}
