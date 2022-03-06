// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBarrowWight extends LOTRModelBiped
{
    public LOTRModelBarrowWight() {
        this(0.0f);
    }
    
    public LOTRModelBarrowWight(final float f) {
        super(f);
        ((ModelBase)this).textureWidth = 64;
        ((ModelBase)this).textureHeight = 64;
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -8.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 1.0f);
        (super.bipedBody = new ModelRenderer((ModelBase)this, 0, 16)).setRotationPoint(0.0f, -8.0f, 0.0f);
        super.bipedBody.addBox(-4.0f, 0.0f, -3.0f, 8, 32, 6, f);
        (super.bipedRightArm = new ModelRenderer((ModelBase)this, 28, 16)).setRotationPoint(-6.0f, -5.0f, 0.0f);
        super.bipedRightArm.addBox(-3.0f, -2.0f, -2.5f, 5, 9, 5, f);
        super.bipedRightArm.setTextureOffset(28, 30).addBox(-2.0f, 7.0f, -1.5f, 3, 10, 3, f);
        (super.bipedLeftArm = new ModelRenderer((ModelBase)this, 28, 16)).setRotationPoint(6.0f, -5.0f, 0.0f);
        super.bipedLeftArm.mirror = true;
        super.bipedLeftArm.addBox(-2.0f, -2.0f, -2.5f, 5, 9, 5, f);
        super.bipedLeftArm.setTextureOffset(28, 30).addBox(-1.0f, 7.0f, -1.5f, 3, 10, 3, f);
        super.bipedHeadwear.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
    
    @Override
    public void setRotationAngles(final float f, float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        f1 = 0.0f;
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        super.bipedLeftArm.rotateAngleX = super.bipedRightArm.rotateAngleX;
        super.bipedLeftArm.rotateAngleY = -super.bipedRightArm.rotateAngleY;
        super.bipedLeftArm.rotateAngleZ = -super.bipedRightArm.rotateAngleZ;
    }
}
