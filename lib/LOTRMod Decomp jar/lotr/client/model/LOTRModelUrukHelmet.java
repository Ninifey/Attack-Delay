// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUrukHelmet extends LOTRModelBiped
{
    private ModelRenderer crest;
    private ModelRenderer jaw;
    
    public LOTRModelUrukHelmet() {
        this(0.0f);
    }
    
    public LOTRModelUrukHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        (this.crest = new ModelRenderer((ModelBase)this, 0, 22)).addBox(-10.0f, -16.0f, -1.0f, 20, 10, 0, 0.0f);
        this.crest.rotateAngleX = (float)Math.toRadians(-10.0);
        super.bipedHead.addChild(this.crest);
        (this.jaw = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-6.0f, 2.0f, -4.0f, 12, 6, 0, 0.0f);
        this.jaw.rotateAngleX = (float)Math.toRadians(-60.0);
        super.bipedHead.addChild(this.jaw);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
