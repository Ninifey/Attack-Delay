// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHarnedorChestplate extends LOTRModelBiped
{
    public LOTRModelHarnedorChestplate() {
        this(0.0f);
    }
    
    public LOTRModelHarnedorChestplate(final float f) {
        super(f);
        (super.bipedBody = new ModelRenderer((ModelBase)this, 16, 16)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        (super.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).setRotationPoint(-5.0f, 2.0f, 0.0f);
        super.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        super.bipedRightArm.setTextureOffset(46, 0);
        super.bipedRightArm.addBox(-4.0f - f, -3.0f - f, -2.0f, 5, 1, 4, 0.0f);
        final ModelRenderer rightBarbs1 = new ModelRenderer((ModelBase)this, 29, 0);
        rightBarbs1.setRotationPoint(-1.5f, -2.5f - f, -2.0f);
        rightBarbs1.addBox(-2.5f, 0.0f, -2.0f, 5, 0, 2, 0.0f);
        rightBarbs1.rotateAngleX = (float)Math.toRadians(30.0);
        super.bipedRightArm.addChild(rightBarbs1);
        final ModelRenderer rightBarbs2 = new ModelRenderer((ModelBase)this, 29, 3);
        rightBarbs2.setRotationPoint(-1.5f, -2.5f - f, 2.0f);
        rightBarbs2.addBox(-2.5f, 0.0f, 0.0f, 5, 0, 2, 0.0f);
        rightBarbs2.rotateAngleX = (float)Math.toRadians(-30.0);
        super.bipedRightArm.addChild(rightBarbs2);
        (super.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16)).setRotationPoint(5.0f, 2.0f, 0.0f);
        super.bipedLeftArm.mirror = true;
        super.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        super.bipedLeftArm.setTextureOffset(46, 0);
        super.bipedLeftArm.addBox(-1.0f + f, -3.0f - f, -2.0f, 5, 1, 4, 0.0f);
        final ModelRenderer leftBarbs1 = new ModelRenderer((ModelBase)this, 29, 0);
        leftBarbs1.setRotationPoint(1.5f, -2.5f - f, -2.0f);
        leftBarbs1.mirror = true;
        leftBarbs1.addBox(-2.5f, 0.0f, -2.0f, 5, 0, 2, 0.0f);
        leftBarbs1.rotateAngleX = (float)Math.toRadians(30.0);
        super.bipedLeftArm.addChild(leftBarbs1);
        final ModelRenderer leftBarbs2 = new ModelRenderer((ModelBase)this, 29, 3);
        leftBarbs2.setRotationPoint(1.5f, -2.5f - f, 2.0f);
        leftBarbs2.mirror = true;
        leftBarbs2.addBox(-2.5f, 0.0f, 0.0f, 5, 0, 2, 0.0f);
        leftBarbs2.rotateAngleX = (float)Math.toRadians(-30.0);
        super.bipedLeftArm.addChild(leftBarbs2);
        super.bipedHead.cubeList.clear();
        super.bipedHeadwear.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
