// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBlackUrukHelmet extends LOTRModelBiped
{
    private ModelRenderer crest;
    
    public LOTRModelBlackUrukHelmet() {
        this(0.0f);
    }
    
    public LOTRModelBlackUrukHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.crest = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-8.0f, -16.0f, -3.0f, 16, 10, 0, 0.0f);
        this.crest.rotateAngleX = (float)Math.toRadians(-20.0);
        super.bipedHead.addChild(this.crest);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
