// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelWizardHat extends LOTRModelBiped
{
    private ModelRenderer hatBrim;
    private ModelRenderer hat1;
    private ModelRenderer hat2;
    private ModelRenderer hat3;
    
    public LOTRModelWizardHat() {
        this(0.0f);
    }
    
    public LOTRModelWizardHat(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.hatBrim = new ModelRenderer((ModelBase)this, 0, 17)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.hatBrim.addBox(-7.0f, -8.0f - f, -7.0f, 14, 1, 14);
        (this.hat1 = new ModelRenderer((ModelBase)this, 32, 3)).setRotationPoint(0.0f, -8.0f - f, 0.0f);
        this.hat1.addBox(-4.0f, -5.0f, -4.0f, 8, 5, 8);
        (this.hat2 = new ModelRenderer((ModelBase)this, 11, 7)).setRotationPoint(0.0f, -4.0f, 0.0f);
        this.hat2.addBox(-2.5f, -4.0f, -2.5f, 5, 4, 5);
        (this.hat3 = new ModelRenderer((ModelBase)this, 0, 22)).setRotationPoint(0.0f, -3.5f, 0.0f);
        this.hat3.addBox(-1.5f, -3.0f, -1.0f, 3, 3, 3);
        super.bipedHead.addChild(this.hatBrim);
        this.hatBrim.addChild(this.hat1);
        this.hat1.addChild(this.hat2);
        this.hat2.addChild(this.hat3);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        super.bipedHead.render(f5);
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.hat2.rotateAngleX = (float)Math.toRadians(-(10.0 + f1 * 10.0));
        this.hat3.rotateAngleX = (float)Math.toRadians(-(10.0 + f1 * 10.0));
    }
}
