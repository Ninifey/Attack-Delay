// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainChieftainHelmet extends LOTRModelBiped
{
    public LOTRModelTauredainChieftainHelmet() {
        this(0.0f);
    }
    
    public LOTRModelTauredainChieftainHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(32, 0).addBox(-5.0f, -9.0f, 0.0f, 10, 6, 3, f);
        final ModelRenderer crest = new ModelRenderer((ModelBase)this, 0, 16);
        crest.setRotationPoint(0.0f, -f, 0.0f);
        crest.addBox(-8.0f, -23.0f, 0.0f, 16, 14, 0, 0.0f);
        crest.rotateAngleX = (float)Math.toRadians(-10.0);
        super.bipedHead.addChild(crest);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
