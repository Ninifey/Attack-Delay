// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelFish extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer finTop;
    private ModelRenderer finRight;
    private ModelRenderer finLeft;
    private ModelRenderer finBack;
    
    public LOTRModelFish() {
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 22.0f, -1.0f);
        this.body.addBox(-0.5f, -2.0f, -3.0f, 1, 3, 6);
        (this.finTop = new ModelRenderer((ModelBase)this, 14, 0)).setRotationPoint(0.0f, 0.0f, -1.5f);
        this.finTop.addBox(0.0f, -2.0f, 0.0f, 0, 2, 4);
        this.body.addChild(this.finTop);
        (this.finRight = new ModelRenderer((ModelBase)this, 22, 0)).setRotationPoint(0.0f, 0.0f, -1.0f);
        this.finRight.addBox(-0.5f, -1.0f, 0.0f, 0, 2, 3);
        this.body.addChild(this.finRight);
        (this.finLeft = new ModelRenderer((ModelBase)this, 22, 0)).setRotationPoint(0.0f, 0.0f, -1.0f);
        this.finLeft.addBox(0.5f, -1.0f, 0.0f, 0, 2, 3);
        this.body.addChild(this.finLeft);
        (this.finBack = new ModelRenderer((ModelBase)this, 0, 9)).setRotationPoint(0.0f, -0.5f, 1.5f);
        this.finBack.addBox(0.0f, -5.0f, 0.0f, 0, 5, 5);
        this.body.addChild(this.finBack);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        this.finTop.rotateAngleX = (float)Math.toRadians(27.0);
        this.finRight.rotateAngleX = (float)Math.toRadians(-15.0);
        this.finRight.rotateAngleY = (float)Math.toRadians(-30.0);
        final ModelRenderer finRight = this.finRight;
        finRight.rotateAngleY += MathHelper.cos(f2 * 0.5f + 3.1415927f) * (float)Math.toRadians(10.0);
        this.finLeft.rotateAngleX = this.finRight.rotateAngleX;
        this.finLeft.rotateAngleY = -this.finRight.rotateAngleY;
        this.finBack.rotateAngleX = (float)Math.toRadians(-45.0);
    }
}
