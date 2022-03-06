// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;

public class LOTRModelSkeleton extends ModelZombie
{
    public LOTRModelSkeleton() {
        this(0.0f);
    }
    
    public LOTRModelSkeleton(final float f) {
        super(f, 0.0f, 64, 32);
        if (f == 0.0f) {
            (((ModelBiped)this).bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, f);
            ((ModelBiped)this).bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
            ((ModelBiped)this).bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
            ((ModelBiped)this).bipedLeftArm.mirror = true;
            ((ModelBiped)this).bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, f);
            ((ModelBiped)this).bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (((ModelBiped)this).bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, f);
            ((ModelBiped)this).bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            ((ModelBiped)this).bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 16);
            ((ModelBiped)this).bipedLeftLeg.mirror = true;
            ((ModelBiped)this).bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, f);
            ((ModelBiped)this).bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        }
    }
}
