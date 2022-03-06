// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import lotr.client.render.entity.LOTRGlowingEyes;

public class LOTRModelOrc extends LOTRModelBiped implements LOTRGlowingEyes.Model
{
    private ModelRenderer nose;
    private ModelRenderer earRight;
    private ModelRenderer earLeft;
    
    public LOTRModelOrc() {
        this(0.0f);
    }
    
    public LOTRModelOrc(final float f) {
        super(f);
        (this.nose = new ModelRenderer((ModelBase)this, 14, 17)).addBox(-0.5f, -4.0f, -4.8f, 1, 2, 1, f);
        this.nose.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.earRight = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.5f, -5.5f, 2.0f, 1, 2, 3, f);
        this.earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earRight.rotateAngleX = 0.2617994f;
        this.earRight.rotateAngleY = -0.5235988f;
        this.earRight.rotateAngleZ = -0.22689281f;
        (this.earLeft = new ModelRenderer((ModelBase)this, 24, 0)).addBox(2.5f, -5.5f, 2.0f, 1, 2, 3, f);
        this.earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earLeft.rotateAngleX = 0.2617994f;
        this.earLeft.rotateAngleY = 0.5235988f;
        this.earLeft.rotateAngleZ = 0.22689281f;
        super.bipedHead.addChild(this.nose);
        super.bipedHead.addChild(this.earRight);
        super.bipedHead.addChild(this.earLeft);
    }
    
    @Override
    public void renderGlowingEyes(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        super.bipedHead.render(f5);
    }
}
