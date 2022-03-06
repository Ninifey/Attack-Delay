// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelRohanMarshalHelmet extends LOTRModelBiped
{
    private ModelRenderer[] manes;
    
    public LOTRModelRohanMarshalHelmet() {
        this(0.0f);
    }
    
    public LOTRModelRohanMarshalHelmet(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        super.bipedHead.setTextureOffset(0, 16).addBox(-1.0f, -11.5f - f, -4.5f - f, 2, 7, 6, 0.0f);
        this.manes = new ModelRenderer[3];
        for (int i = 0; i < this.manes.length; ++i) {
            final ModelRenderer mane = new ModelRenderer((ModelBase)this, 32, 0);
            (this.manes[i] = mane).setRotationPoint(0.0f, -f, f);
            mane.addBox(0.0f, -11.0f, -1.0f, 0, 14, 12, 0.0f);
            super.bipedHead.addChild(mane);
        }
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        final float mid = this.manes.length / 2.0f - 0.5f;
        for (int i = 0; i < this.manes.length; ++i) {
            final ModelRenderer mane = this.manes[i];
            mane.rotateAngleX = (mid - Math.abs(i - mid)) / mid * 0.22f;
            mane.rotateAngleY = (i - mid) / mid * 0.17f;
            final ModelRenderer modelRenderer = mane;
            modelRenderer.rotateAngleX += MathHelper.sin(f * 0.4f) * f1 * 0.2f;
        }
    }
}
