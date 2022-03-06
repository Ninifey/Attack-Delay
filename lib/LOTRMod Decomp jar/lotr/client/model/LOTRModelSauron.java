// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelSauron extends LOTRModelBiped
{
    private ModelRenderer bipedCape;
    
    public LOTRModelSauron() {
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(64, 64)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
        super.bipedHead.setRotationPoint(0.0f, -12.0f, 0.0f);
        super.bipedHead.setTextureOffset(32, 0).addBox(-0.5f, -15.0f, -3.5f, 1, 7, 1);
        super.bipedHead.setTextureOffset(32, 0).addBox(-2.5f, -13.0f, -3.5f, 1, 5, 1);
        super.bipedHead.setTextureOffset(32, 0).addBox(1.5f, -13.0f, -3.5f, 1, 5, 1);
        super.bipedHead.setTextureOffset(32, 0).addBox(-0.5f, -16.0f, 2.5f, 1, 8, 1);
        super.bipedHead.setTextureOffset(32, 0).addBox(-3.5f, -16.0f, -0.5f, 1, 8, 1);
        super.bipedHead.setTextureOffset(32, 0).addBox(2.5f, -16.0f, -0.5f, 1, 8, 1);
        (super.bipedBody = new ModelRenderer((ModelBase)this, 40, 42).setTextureSize(64, 64)).addBox(-4.0f, 0.0f, -2.0f, 8, 18, 4);
        super.bipedBody.setRotationPoint(0.0f, -12.0f, 0.0f);
        (super.bipedRightArm = new ModelRenderer((ModelBase)this, 0, 43).setTextureSize(64, 64)).addBox(-3.0f, -2.0f, -2.0f, 4, 17, 4);
        super.bipedRightArm.setRotationPoint(-5.0f, -8.0f, 0.0f);
        super.bipedRightArm.setTextureOffset(16, 52).addBox(-4.0f, -3.0f, -3.0f, 6, 6, 6);
        super.bipedLeftArm = new ModelRenderer((ModelBase)this, 0, 43).setTextureSize(64, 64);
        super.bipedLeftArm.mirror = true;
        super.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 17, 4);
        super.bipedLeftArm.setRotationPoint(5.0f, -8.0f, 0.0f);
        super.bipedLeftArm.setTextureOffset(16, 52).addBox(-2.0f, -3.0f, -3.0f, 6, 6, 6);
        (super.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 16).setTextureSize(64, 64)).addBox(-2.0f, 0.0f, -2.0f, 4, 18, 4);
        super.bipedRightLeg.setRotationPoint(-2.0f, 6.0f, 0.0f);
        super.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 16).setTextureSize(64, 64);
        super.bipedLeftLeg.mirror = true;
        super.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 18, 4);
        super.bipedLeftLeg.setRotationPoint(2.0f, 6.0f, 0.0f);
        (this.bipedCape = new ModelRenderer((ModelBase)this, 38, 0).setTextureSize(64, 64)).addBox(-6.0f, 1.0f, 1.0f, 12, 32, 1);
        this.bipedCape.setRotationPoint(0.0f, -12.0f, 0.0f);
        this.bipedCape.rotateAngleX = 0.15f;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        super.bipedHead.render(f5);
        super.bipedBody.render(f5);
        super.bipedRightArm.render(f5);
        super.bipedLeftArm.render(f5);
        super.bipedRightLeg.render(f5);
        super.bipedLeftLeg.render(f5);
        this.bipedCape.render(f5);
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (super.isSneak) {
            super.bipedRightLeg.rotationPointY = 3.0f;
            super.bipedLeftLeg.rotationPointY = 3.0f;
            super.bipedHead.rotationPointY = -11.0f;
        }
        else {
            super.bipedRightLeg.rotationPointY = 6.0f;
            super.bipedLeftLeg.rotationPointY = 6.0f;
            super.bipedHead.rotationPointY = -12.0f;
        }
    }
}
