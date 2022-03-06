// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelArnorHelmet extends LOTRModelBiped
{
    public LOTRModelArnorHelmet() {
        this(0.0f);
    }
    
    public LOTRModelArnorHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(32, 0).addBox(-4.5f - f, -13.0f - f, -1.0f, 1, 8, 1, 0.0f);
        super.bipedHead.setTextureOffset(36, 0).addBox(-4.5f - f, -12.0f - f, 0.0f, 1, 7, 1, 0.0f);
        super.bipedHead.setTextureOffset(40, 0).addBox(-4.5f - f, -11.0f - f, 1.0f, 1, 5, 1, 0.0f);
        super.bipedHead.mirror = true;
        super.bipedHead.setTextureOffset(32, 0).addBox(3.5f + f, -13.0f - f, -1.0f, 1, 8, 1, 0.0f);
        super.bipedHead.setTextureOffset(36, 0).addBox(3.5f + f, -12.0f - f, 0.0f, 1, 7, 1, 0.0f);
        super.bipedHead.setTextureOffset(40, 0).addBox(3.5f + f, -11.0f - f, 1.0f, 1, 5, 1, 0.0f);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
