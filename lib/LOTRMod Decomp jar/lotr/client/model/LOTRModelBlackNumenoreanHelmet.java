// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBlackNumenoreanHelmet extends LOTRModelBiped
{
    public LOTRModelBlackNumenoreanHelmet() {
        this(0.0f);
    }
    
    public LOTRModelBlackNumenoreanHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        final ModelRenderer wingLeft = new ModelRenderer((ModelBase)this, 33, 0);
        wingLeft.setRotationPoint(-4.0f - f, -8.0f - f, 0.0f);
        wingLeft.addBox(-6.0f, -6.0f, 0.0f, 6, 16, 0, 0.0f);
        wingLeft.rotateAngleY = (float)Math.toRadians(25.0);
        super.bipedHead.addChild(wingLeft);
        final ModelRenderer wingRight = new ModelRenderer((ModelBase)this, 33, 0);
        wingRight.mirror = true;
        wingRight.setRotationPoint(4.0f + f, -8.0f - f, 0.0f);
        wingRight.addBox(0.0f, -6.0f, 0.0f, 6, 16, 0, 0.0f);
        wingRight.rotateAngleY = (float)Math.toRadians(-25.0);
        super.bipedHead.addChild(wingRight);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
