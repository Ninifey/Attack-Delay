// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelLionOld extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer headwear;
    private ModelRenderer mane;
    private ModelRenderer body;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    
    public LOTRModelLionOld() {
        super.textureWidth = 64;
        super.textureHeight = 96;
        (this.head = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 4.0f, -9.0f);
        this.head.addBox(-4.0f, -4.0f, -7.0f, 8, 8, 8, 0.0f);
        this.head.setTextureOffset(52, 34).addBox(-2.0f, 0.0f, -9.0f, 4, 4, 2);
        (this.headwear = new ModelRenderer((ModelBase)this, 32, 0)).setRotationPoint(0.0f, 4.0f, -9.0f);
        this.headwear.addBox(-4.0f, -4.0f, -7.0f, 8, 8, 8, 0.5f);
        (this.mane = new ModelRenderer((ModelBase)this, 0, 36)).setRotationPoint(0.0f, 4.0f, -9.0f);
        this.mane.addBox(-7.0f, -7.0f, -5.0f, 14, 14, 9, 0.0f);
        (this.body = new ModelRenderer((ModelBase)this, 0, 68)).addBox(-6.0f, -10.0f, -7.0f, 12, 18, 10, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        (this.leg1 = new ModelRenderer((ModelBase)this, 0, 19)).setRotationPoint(-4.0f, 12.0f, 7.0f);
        this.leg1.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        this.leg2 = new ModelRenderer((ModelBase)this, 0, 19);
        this.leg2.mirror = true;
        this.leg2.setRotationPoint(4.0f, 12.0f, 7.0f);
        this.leg2.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        (this.leg3 = new ModelRenderer((ModelBase)this, 0, 19)).setRotationPoint(-4.0f, 12.0f, -5.0f);
        this.leg3.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        this.leg4 = new ModelRenderer((ModelBase)this, 0, 19);
        this.leg4.mirror = true;
        this.leg4.setRotationPoint(4.0f, 12.0f, -5.0f);
        this.leg4.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (super.isChild) {
            final float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
            this.head.render(f5);
            this.headwear.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.headwear.render(f5);
            this.mane.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
        }
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        this.head.rotateAngleX = (float)Math.toRadians(f4);
        this.head.rotateAngleY = (float)Math.toRadians(f3);
        this.headwear.rotateAngleX = this.head.rotateAngleX;
        this.headwear.rotateAngleY = this.head.rotateAngleY;
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
    }
}
