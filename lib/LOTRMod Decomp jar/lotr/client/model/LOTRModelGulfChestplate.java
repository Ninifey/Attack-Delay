// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGulfChestplate extends LOTRModelBiped
{
    public LOTRModelGulfChestplate() {
        this(0.0f);
    }
    
    public LOTRModelGulfChestplate(final float f) {
        super(f);
        (super.bipedBody = new ModelRenderer((ModelBase)this, 16, 16)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        super.bipedBody.setTextureOffset(16, 0);
        super.bipedBody.addBox(-4.0f, 0.0f, -3.0f - f, 8, 3, 1, 0.0f);
        final ModelRenderer chestHorn1 = new ModelRenderer((ModelBase)this, 0, 0);
        chestHorn1.setRotationPoint(-2.5f - f, 2.5f, -3.0f - f);
        chestHorn1.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
        chestHorn1.rotateAngleX = (float)Math.toRadians(-25.0);
        chestHorn1.rotateAngleZ = (float)Math.toRadians(25.0);
        super.bipedBody.addChild(chestHorn1);
        final ModelRenderer chestHorn2 = new ModelRenderer((ModelBase)this, 0, 0);
        chestHorn2.setRotationPoint(0.0f, 3.0f, -3.0f - f);
        chestHorn2.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
        chestHorn2.rotateAngleX = (float)Math.toRadians(-25.0);
        chestHorn2.rotateAngleZ = (float)Math.toRadians(0.0);
        super.bipedBody.addChild(chestHorn2);
        final ModelRenderer chestHorn3 = new ModelRenderer((ModelBase)this, 0, 0);
        chestHorn3.setRotationPoint(2.5f + f, 2.5f, -3.0f - f);
        chestHorn3.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
        chestHorn3.rotateAngleX = (float)Math.toRadians(-25.0);
        chestHorn3.rotateAngleZ = (float)Math.toRadians(-25.0);
        super.bipedBody.addChild(chestHorn3);
        (super.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).setRotationPoint(-5.0f, 2.0f, 0.0f);
        super.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        super.bipedRightArm.setTextureOffset(40, 0);
        super.bipedRightArm.addBox(-4.0f, -2.0f - f, -2.5f, 5, 1, 5, 0.0f);
        final ModelRenderer rightHorn1 = new ModelRenderer((ModelBase)this, 4, 0);
        rightHorn1.setRotationPoint(-2.5f, -2.0f - f, 0.0f);
        rightHorn1.addBox(-0.5f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
        rightHorn1.rotateAngleZ = (float)Math.toRadians(-10.0);
        super.bipedRightArm.addChild(rightHorn1);
        final ModelRenderer rightHorn2 = new ModelRenderer((ModelBase)this, 8, 0);
        rightHorn2.setRotationPoint(-0.5f, -2.0f - f, 0.0f);
        rightHorn2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
        rightHorn2.rotateAngleZ = (float)Math.toRadians(-10.0);
        super.bipedRightArm.addChild(rightHorn2);
        (super.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16)).setRotationPoint(5.0f, 2.0f, 0.0f);
        super.bipedLeftArm.mirror = true;
        super.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        super.bipedLeftArm.setTextureOffset(40, 0);
        super.bipedLeftArm.addBox(-1.0f, -2.0f - f, -2.5f, 5, 1, 5, 0.0f);
        final ModelRenderer leftHorn1 = new ModelRenderer((ModelBase)this, 4, 0);
        leftHorn1.setRotationPoint(2.5f, -2.0f - f, 0.0f);
        leftHorn1.mirror = true;
        leftHorn1.addBox(-0.5f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
        leftHorn1.rotateAngleZ = (float)Math.toRadians(10.0);
        super.bipedLeftArm.addChild(leftHorn1);
        final ModelRenderer leftHorn2 = new ModelRenderer((ModelBase)this, 8, 0);
        leftHorn2.setRotationPoint(0.5f, -2.0f - f, 0.0f);
        leftHorn2.mirror = true;
        leftHorn2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
        leftHorn2.rotateAngleZ = (float)Math.toRadians(10.0);
        super.bipedLeftArm.addChild(leftHorn2);
        super.bipedHead.cubeList.clear();
        super.bipedHeadwear.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
