// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelGoblet extends ModelBase
{
    private ModelRenderer base;
    private ModelRenderer cup;
    
    public LOTRModelGoblet() {
        (this.base = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.base.addBox(-2.5f, 0.0f, -2.5f, 5, 1, 5);
        this.base.setTextureOffset(0, 6).addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1);
        (this.cup = new ModelRenderer((ModelBase)this, 0, 12)).setRotationPoint(0.0f, -5.0f, 0.0f);
        this.cup.addBox(-2.5f, 0.0f, -2.5f, 5, 1, 5);
        this.cup.setTextureOffset(0, 18).addBox(-2.5f, -4.0f, -2.5f, 1, 4, 5);
        this.cup.setTextureOffset(12, 22).addBox(-1.5f, -4.0f, -2.5f, 3, 4, 1);
        this.cup.setTextureOffset(20, 18).addBox(1.5f, -4.0f, -2.5f, 1, 4, 5);
        this.cup.setTextureOffset(32, 22).addBox(-1.5f, -4.0f, 1.5f, 3, 4, 1);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.base.render(f5);
        this.cup.render(f5);
    }
}
