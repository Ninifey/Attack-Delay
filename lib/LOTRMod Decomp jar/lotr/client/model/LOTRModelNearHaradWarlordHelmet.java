// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelNearHaradWarlordHelmet extends LOTRModelBiped
{
    private ModelRenderer stickRight;
    private ModelRenderer stickCentre;
    private ModelRenderer stickLeft;
    
    public LOTRModelNearHaradWarlordHelmet() {
        this(0.0f);
    }
    
    public LOTRModelNearHaradWarlordHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.setTextureOffset(6, 24).addBox(-2.5f, -3.0f, 4.1f, 5, 3, 2, 0.0f);
        super.bipedHead.setTextureOffset(0, 16).addBox(-9.0f, -16.0f, 5.5f, 18, 8, 0, 0.0f);
        (this.stickRight = new ModelRenderer((ModelBase)this, 36, 0)).addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
        this.stickRight.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
        this.stickRight.rotateAngleZ = (float)Math.toRadians(-28.0);
        super.bipedHead.addChild(this.stickRight);
        (this.stickCentre = new ModelRenderer((ModelBase)this, 36, 0)).addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
        this.stickCentre.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
        this.stickCentre.rotateAngleZ = (float)Math.toRadians(0.0);
        super.bipedHead.addChild(this.stickCentre);
        (this.stickLeft = new ModelRenderer((ModelBase)this, 36, 0)).addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
        this.stickLeft.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
        this.stickLeft.rotateAngleZ = (float)Math.toRadians(28.0);
        super.bipedHead.addChild(this.stickLeft);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
