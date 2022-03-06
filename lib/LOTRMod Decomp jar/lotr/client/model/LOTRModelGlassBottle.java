// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelGlassBottle extends ModelBase
{
    private ModelRenderer bottle;
    
    public LOTRModelGlassBottle() {
        (this.bottle = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.bottle.addBox(-2.0f, 0.0f, -2.0f, 4, 1, 4);
        this.bottle.setTextureOffset(0, 6).addBox(-3.0f, -4.0f, -2.0f, 1, 4, 4);
        this.bottle.setTextureOffset(10, 9).addBox(-2.0f, -4.0f, -3.0f, 4, 4, 1);
        this.bottle.setTextureOffset(20, 6).addBox(2.0f, -4.0f, -2.0f, 1, 4, 4);
        this.bottle.setTextureOffset(30, 9).addBox(-2.0f, -4.0f, 2.0f, 4, 4, 1);
        this.bottle.setTextureOffset(16, 0).addBox(-2.0f, -5.0f, -2.0f, 4, 1, 4);
        this.bottle.setTextureOffset(0, 16).addBox(-1.0f, -6.0f, -1.0f, 2, 1, 2);
        this.bottle.setTextureOffset(0, 19).addBox(-1.5f, -7.0f, -1.5f, 3, 1, 3);
        this.bottle.setTextureOffset(12, 19).addBox(-1.0f, -8.5f, -1.0f, 2, 2, 2);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.bottle.render(f5);
    }
}
