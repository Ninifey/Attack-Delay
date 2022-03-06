// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelButterfly extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer rightWing;
    private ModelRenderer leftWing;
    
    public LOTRModelButterfly() {
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-1.0f, -6.0f, -1.0f, 2, 12, 2);
        (this.rightWing = new ModelRenderer((ModelBase)this, 10, 0)).addBox(-12.0f, -10.5f, 0.0f, 12, 21, 0);
        this.leftWing = new ModelRenderer((ModelBase)this, 10, 0);
        this.leftWing.mirror = true;
        this.leftWing.addBox(0.0f, -10.5f, 0.0f, 12, 21, 0);
        this.body.addChild(this.rightWing);
        this.body.addChild(this.leftWing);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
        if (butterfly.isButterflyStill()) {
            this.body.setRotationPoint(0.0f, 24.0f, 0.0f);
            this.body.rotateAngleX = 1.5707964f;
            if (butterfly.flapTime > 0) {
                this.rightWing.rotateAngleY = MathHelper.cos(f2 * 1.3f) * 3.1415927f * 0.25f;
            }
            else {
                this.rightWing.rotateAngleY = 0.31415927f;
            }
            this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
        }
        else {
            this.body.setRotationPoint(0.0f, 8.0f, 0.0f);
            this.body.rotateAngleX = 0.7853982f + MathHelper.cos(f2 * 0.1f) * 0.15f;
            this.rightWing.rotateAngleY = MathHelper.cos(f2 * 1.3f) * 3.1415927f * 0.25f;
            this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
        }
        this.body.render(f5);
    }
}
