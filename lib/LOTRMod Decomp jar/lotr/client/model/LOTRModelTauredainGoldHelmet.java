// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainGoldHelmet extends LOTRModelBiped
{
    public LOTRModelTauredainGoldHelmet() {
        this(0.0f);
    }
    
    public LOTRModelTauredainGoldHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        final ModelRenderer crest = new ModelRenderer((ModelBase)this, 32, 0);
        crest.setRotationPoint(0.0f, -f, 0.0f);
        crest.addBox(-7.0f, -20.0f, 0.0f, 14, 12, 0, 0.0f);
        crest.rotateAngleX = (float)Math.toRadians(-4.0);
        final ModelRenderer tusks1 = new ModelRenderer((ModelBase)this, 0, 16);
        tusks1.setRotationPoint(-3.5f - f, 0.0f + f, -4.0f - f);
        tusks1.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks1.rotateAngleX = (float)Math.toRadians(20.0);
        tusks1.rotateAngleY = (float)Math.toRadians(30.0);
        final ModelRenderer tusks2 = new ModelRenderer((ModelBase)this, 0, 16);
        tusks2.setRotationPoint(-3.5f - f, 0.0f + f, -4.0f - f);
        tusks2.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks2.rotateAngleX = (float)Math.toRadians(20.0);
        tusks2.rotateAngleY = (float)Math.toRadians(-20.0);
        final ModelRenderer tusks3 = new ModelRenderer((ModelBase)this, 0, 16);
        tusks3.setRotationPoint(3.5f + f, 0.0f + f, -4.0f - f);
        tusks3.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks3.rotateAngleX = (float)Math.toRadians(20.0);
        tusks3.rotateAngleY = (float)Math.toRadians(20.0);
        final ModelRenderer tusks4 = new ModelRenderer((ModelBase)this, 0, 16);
        tusks4.setRotationPoint(3.5f + f, 0.0f + f, -4.0f - f);
        tusks4.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks4.rotateAngleX = (float)Math.toRadians(20.0);
        tusks4.rotateAngleY = (float)Math.toRadians(-30.0);
        super.bipedHead.addChild(crest);
        super.bipedHead.addChild(tusks1);
        super.bipedHead.addChild(tusks2);
        super.bipedHead.addChild(tusks3);
        super.bipedHead.addChild(tusks4);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
