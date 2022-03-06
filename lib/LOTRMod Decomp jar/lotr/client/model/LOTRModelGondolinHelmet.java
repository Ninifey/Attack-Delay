// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGondolinHelmet extends LOTRModelBiped
{
    public LOTRModelGondolinHelmet() {
        this(0.0f);
    }
    
    public LOTRModelGondolinHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(46, 0).addBox(-0.5f, -14.0f - f, -4.5f, 1, 6, 1, 0.0f);
        super.bipedHead.setTextureOffset(50, 0).addBox(-0.5f, -12.0f - f, -0.5f, 1, 4, 1, 0.0f);
        super.bipedHead.setTextureOffset(54, 0).addBox(-0.5f, -10.0f - f, 3.5f, 1, 2, 1, 0.0f);
        super.bipedHead.setTextureOffset(32, -7).addBox(0.0f, -13.5f - f, -3.5f, 0, 6, 7, 0.0f);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
