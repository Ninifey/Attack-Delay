// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelMidge extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer rightWing;
    private ModelRenderer leftWing;
    
    public LOTRModelMidge() {
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-0.5f, -1.5f, -0.5f, 1, 5, 1);
        (this.rightWing = new ModelRenderer((ModelBase)this, 0, 6)).addBox(-5.0f, -2.5f, 0.0f, 5, 5, 1);
        this.leftWing = new ModelRenderer((ModelBase)this, 0, 6);
        this.leftWing.mirror = true;
        this.leftWing.addBox(0.0f, -2.5f, 0.0f, 5, 5, 1);
        this.body.addChild(this.rightWing);
        this.body.addChild(this.leftWing);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.body.setRotationPoint(0.0f, 8.0f, 0.0f);
        this.body.rotateAngleX = 0.7853982f + MathHelper.cos(f2 * 0.1f) * 0.15f;
        this.rightWing.rotateAngleY = MathHelper.cos(f2 * 4.0f) * 3.1415927f * 0.25f;
        this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
        this.body.render(f5);
    }
}
