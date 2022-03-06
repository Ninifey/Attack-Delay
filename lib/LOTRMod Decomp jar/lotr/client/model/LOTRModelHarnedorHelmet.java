// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHarnedorHelmet extends LOTRModelBiped
{
    public LOTRModelHarnedorHelmet() {
        this(0.0f);
    }
    
    public LOTRModelHarnedorHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(0, 5).addBox(0.0f, -11.0f, -7.0f, 0, 10, 14, 0.0f);
        super.bipedHead.setTextureOffset(16, 19).addBox(-6.0f, -2.0f, -6.0f, 12, 0, 12, 0.0f);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
