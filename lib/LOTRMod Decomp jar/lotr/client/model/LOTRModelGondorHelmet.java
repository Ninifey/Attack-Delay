// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGondorHelmet extends LOTRModelBiped
{
    public LOTRModelGondorHelmet() {
        this(0.0f);
    }
    
    public LOTRModelGondorHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(0, 16).addBox(-1.5f, -9.0f, -3.5f, 3, 1, 7, f);
        super.bipedHead.setTextureOffset(20, 16).addBox(-0.5f, -10.0f, -3.5f, 1, 1, 7, f);
        super.bipedHead.setTextureOffset(24, 0).addBox(-1.5f, -10.5f - f, -4.5f - f, 3, 4, 1, 0.0f);
        super.bipedHead.setTextureOffset(24, 5).addBox(-0.5f, -11.5f - f, -4.5f - f, 1, 1, 1, 0.0f);
        super.bipedHead.setTextureOffset(28, 5).addBox(-0.5f, -6.5f - f, -4.5f - f, 1, 1, 1, 0.0f);
        super.bipedHead.setTextureOffset(32, 0).addBox(-1.5f, -9.5f - f, 3.5f + f, 3, 3, 1, 0.0f);
        super.bipedHead.setTextureOffset(32, 4).addBox(-0.5f, -10.5f - f, 3.5f + f, 1, 1, 1, 0.0f);
        super.bipedHead.setTextureOffset(36, 4).addBox(-0.5f, -6.5f - f, 3.5f + f, 1, 1, 1, 0.0f);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
