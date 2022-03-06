// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHighElvenHelmet extends LOTRModelBiped
{
    private ModelRenderer crest;
    
    public LOTRModelHighElvenHelmet() {
        this(0.0f);
    }
    
    public LOTRModelHighElvenHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-0.5f, -11.0f, -2.0f, 1, 3, 1, 0.0f);
        super.bipedHead.setTextureOffset(0, 4).addBox(-0.5f, -10.0f, 2.0f, 1, 2, 1, 0.0f);
        (this.crest = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-1.0f, -11.0f, -8.0f, 2, 1, 11, 0.0f);
        this.crest.setTextureOffset(32, 12).addBox(-1.0f, -10.0f, -8.0f, 2, 1, 1, 0.0f);
        this.crest.rotateAngleX = (float)Math.toRadians(-16.0);
        super.bipedHead.addChild(this.crest);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
