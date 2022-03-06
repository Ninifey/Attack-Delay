// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGundabadUrukHelmet extends LOTRModelBiped
{
    public LOTRModelGundabadUrukHelmet() {
        this(0.0f);
    }
    
    public LOTRModelGundabadUrukHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        final ModelRenderer hornRight = new ModelRenderer((ModelBase)this, 32, 0);
        hornRight.setRotationPoint(-f, -f, -f);
        hornRight.addBox(-7.0f, -12.0f, 0.5f, 3, 8, 0, 0.0f);
        hornRight.rotateAngleZ = (float)Math.toRadians(6.0);
        final ModelRenderer hornLeft = new ModelRenderer((ModelBase)this, 32, 0);
        hornLeft.setRotationPoint(f, -f, -f);
        hornLeft.mirror = true;
        hornLeft.addBox(4.0f, -12.0f, 0.5f, 3, 8, 0, 0.0f);
        hornLeft.rotateAngleZ = (float)Math.toRadians(-6.0);
        super.bipedHead.addChild(hornRight);
        super.bipedHead.addChild(hornLeft);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
