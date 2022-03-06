// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUmbarHelmet extends LOTRModelBiped
{
    public LOTRModelUmbarHelmet() {
        this(0.0f);
    }
    
    public LOTRModelUmbarHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        (super.bipedHeadwear = new ModelRenderer((ModelBase)this, 32, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        super.bipedHead.setTextureOffset(0, 0);
        super.bipedHead.addBox(-0.5f, -11.0f - f, -3.0f, 1, 3, 1, 0.0f);
        super.bipedHead.addBox(-0.5f, -10.0f - f, 2.0f, 1, 2, 1, 0.0f);
        super.bipedHead.setTextureOffset(0, 16).addBox(0.0f, -13.0f - f, -6.0f, 0, 4, 12, 0.0f);
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
