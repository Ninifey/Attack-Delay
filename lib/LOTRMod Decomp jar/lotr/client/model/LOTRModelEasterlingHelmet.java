// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelEasterlingHelmet extends LOTRModelBiped
{
    public LOTRModelEasterlingHelmet() {
        this(0.0f, false);
    }
    
    public LOTRModelEasterlingHelmet(final float f, final boolean kineHorns) {
        super(f);
        if (kineHorns) {
            ((ModelBase)this).textureWidth = 64;
            ((ModelBase)this).textureHeight = 64;
        }
        else {
            ((ModelBase)this).textureWidth = 64;
            ((ModelBase)this).textureHeight = 32;
        }
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(0, 16).addBox(-5.5f, -8.5f - f, -5.5f, 11, 2, 11, 0.0f);
        super.bipedHead.setTextureOffset(32, 8).addBox(-3.5f, -9.5f - f, -3.5f, 7, 1, 7, 0.0f);
        super.bipedHead.setTextureOffset(50, 16).addBox(0.0f, -10.5f - f, -4.5f - f, 0, 3, 4, 0.0f);
        final ModelRenderer horn = new ModelRenderer((ModelBase)this, 44, 16);
        horn.setRotationPoint(0.0f, 0.0f, 0.0f);
        horn.addBox(-0.5f, -14.0f - f, -2.0f - f, 1, 8, 2, 0.0f);
        horn.rotateAngleX = (float)Math.toRadians(20.0);
        super.bipedHead.addChild(horn);
        super.bipedHead.setTextureOffset(24, 0).addBox(-1.0f, -8.0f - f, 4.0f + f, 2, 4, 1, 0.0f);
        super.bipedHead.setTextureOffset(32, 2).addBox(-6.0f, -12.0f - f, 5.0f + f, 12, 4, 0, 0.0f);
        final ModelRenderer crest = new ModelRenderer((ModelBase)this, 32, 0);
        crest.setRotationPoint(0.0f, -12.0f - f, 5.0f + f);
        crest.addBox(-6.0f, -2.0f, 0.0f, 12, 2, 0, 0.0f);
        crest.rotateAngleX = (float)Math.toRadians(30.0);
        super.bipedHead.addChild(crest);
        if (kineHorns) {
            final ModelRenderer kineHornRight = new ModelRenderer((ModelBase)this, 0, 32);
            kineHornRight.setRotationPoint(-1.0f - f, -8.0f - f, 0.0f);
            kineHornRight.addBox(-7.0f, -1.5f, -1.5f, 7, 3, 3, 0.0f);
            final ModelRenderer kineHornRight2 = new ModelRenderer((ModelBase)this, 0, 38);
            kineHornRight2.setRotationPoint(-7.0f, 0.0f, 0.0f);
            kineHornRight2.addBox(-5.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
            final ModelRenderer kineHornRight3 = new ModelRenderer((ModelBase)this, 0, 42);
            kineHornRight3.setRotationPoint(-5.0f, 0.0f, 0.0f);
            kineHornRight3.addBox(-3.0f, -0.5f, -0.5f, 4, 1, 1, 0.0f);
            final ModelRenderer kineHornLeft = new ModelRenderer((ModelBase)this, 0, 32);
            kineHornLeft.mirror = true;
            kineHornLeft.setRotationPoint(1.0f + f, -8.0f - f, 0.0f);
            kineHornLeft.addBox(0.0f, -1.5f, -1.5f, 7, 3, 3, 0.0f);
            final ModelRenderer kineHornLeft2 = new ModelRenderer((ModelBase)this, 0, 38);
            kineHornLeft2.mirror = true;
            kineHornLeft2.setRotationPoint(7.0f, 0.0f, 0.0f);
            kineHornLeft2.addBox(-1.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
            final ModelRenderer kineHornLeft3 = new ModelRenderer((ModelBase)this, 0, 42);
            kineHornLeft3.mirror = true;
            kineHornLeft3.setRotationPoint(5.0f, 0.0f, 0.0f);
            kineHornLeft3.addBox(-1.0f, -0.5f, -0.5f, 4, 1, 1, 0.0f);
            kineHornRight.rotateAngleZ = (float)Math.toRadians(40.0);
            kineHornLeft.rotateAngleZ = -kineHornRight.rotateAngleZ;
            kineHornRight2.rotateAngleZ = (float)Math.toRadians(-30.0);
            kineHornLeft2.rotateAngleZ = -kineHornRight2.rotateAngleZ;
            kineHornRight3.rotateAngleZ = (float)Math.toRadians(-30.0);
            kineHornLeft3.rotateAngleZ = -kineHornRight3.rotateAngleZ;
            super.bipedHead.addChild(kineHornRight);
            kineHornRight.addChild(kineHornRight2);
            kineHornRight2.addChild(kineHornRight3);
            super.bipedHead.addChild(kineHornLeft);
            kineHornLeft.addChild(kineHornLeft2);
            kineHornLeft2.addChild(kineHornLeft3);
        }
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
