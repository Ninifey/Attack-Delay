// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGemsbokHelmet extends LOTRModelBiped
{
    private ModelRenderer hornRight;
    private ModelRenderer hornLeft;
    
    public LOTRModelGemsbokHelmet() {
        this(0.0f);
    }
    
    public LOTRModelGemsbokHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.hornRight = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-4.9f, -7.0f, 7.5f, 1, 1, 13);
        this.hornLeft = new ModelRenderer((ModelBase)this, 32, 0);
        this.hornLeft.mirror = true;
        this.hornLeft.addBox(3.9f, -7.0f, 7.5f, 1, 1, 13);
        final ModelRenderer hornRight = this.hornRight;
        final ModelRenderer hornLeft = this.hornLeft;
        final float n = (float)Math.toRadians(20.0);
        hornLeft.rotateAngleX = n;
        hornRight.rotateAngleX = n;
        super.bipedHead.addChild(this.hornRight);
        super.bipedHead.addChild(this.hornLeft);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
