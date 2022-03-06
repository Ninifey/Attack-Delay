// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelDorwinionElfHelmet extends LOTRModelBiped
{
    public LOTRModelDorwinionElfHelmet() {
        this(0.0f);
    }
    
    public LOTRModelDorwinionElfHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(20, 16).addBox(0.0f, -10.0f, 4.0f, 0, 10, 4, 0.0f);
        (super.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        final ModelRenderer crest = new ModelRenderer((ModelBase)this, 0, 16);
        crest.setRotationPoint(0.0f, -f, 0.0f);
        crest.addBox(-1.0f, -11.0f, -6.0f, 2, 5, 8, 0.0f);
        crest.rotateAngleX = (float)Math.toRadians(-15.0);
        super.bipedHead.addChild(crest);
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
