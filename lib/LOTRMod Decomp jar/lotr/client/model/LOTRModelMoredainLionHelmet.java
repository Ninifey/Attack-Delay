// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelMoredainLionHelmet extends LOTRModelBiped
{
    private ModelRenderer panelRight;
    private ModelRenderer panelLeft;
    private ModelRenderer panelBack;
    private ModelRenderer panelTop;
    
    public LOTRModelMoredainLionHelmet() {
        this(0.0f);
    }
    
    public LOTRModelMoredainLionHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.setTextureOffset(34, 16).addBox(-4.5f, -9.0f, -2.5f, 9, 2, 5, f);
        super.bipedHead.setTextureOffset(0, 17).addBox(-2.5f, -10.0f, -7.0f, 5, 3, 12, f);
        super.bipedHead.setTextureOffset(34, 23).addBox(-1.0f, -10.4f, -7.2f, 2, 2, 7, f);
        super.bipedHead.setTextureOffset(0, 0).addBox(-2.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
        super.bipedHead.mirror = true;
        super.bipedHead.addBox(1.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
        (this.panelRight = new ModelRenderer((ModelBase)this, 32, 0)).addBox(-5.0f - f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
        this.panelRight.rotateAngleZ = (float)Math.toRadians(4.0);
        this.panelLeft = new ModelRenderer((ModelBase)this, 32, 0);
        this.panelLeft.mirror = true;
        this.panelLeft.addBox(5.0f + f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
        this.panelLeft.rotateAngleZ = (float)Math.toRadians(-4.0);
        (this.panelBack = new ModelRenderer((ModelBase)this, 44, 0)).addBox(-4.0f, -8.0f, 4.8f + f, 8, 10, 0, 0.0f);
        this.panelBack.rotateAngleX = (float)Math.toRadians(4.0);
        (this.panelTop = new ModelRenderer((ModelBase)this, 52, 25)).addBox(-2.5f, -16.0f - f, -2.0f, 5, 7, 0, 0.0f);
        this.panelTop.rotateAngleX = (float)Math.toRadians(-10.0);
        super.bipedHead.addChild(this.panelRight);
        super.bipedHead.addChild(this.panelLeft);
        super.bipedHead.addChild(this.panelBack);
        super.bipedHead.addChild(this.panelTop);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
