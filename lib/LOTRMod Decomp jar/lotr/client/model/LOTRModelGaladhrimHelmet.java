// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGaladhrimHelmet extends LOTRModelBiped
{
    public LOTRModelGaladhrimHelmet() {
        this(0.0f);
    }
    
    public LOTRModelGaladhrimHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        final ModelRenderer horn = new ModelRenderer((ModelBase)this, 32, 0);
        horn.addBox(-0.5f, -9.0f - f, 2.0f - f, 1, 3, 3, 0.0f);
        horn.setTextureOffset(32, 6).addBox(-0.5f, -10.0f - f, 3.5f - f, 1, 1, 3, 0.0f);
        horn.setTextureOffset(32, 10).addBox(-0.5f, -11.0f - f, 5.5f - f, 1, 1, 4, 0.0f);
        horn.rotateAngleX = (float)Math.toRadians(45.0);
        super.bipedHead.addChild(horn);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
