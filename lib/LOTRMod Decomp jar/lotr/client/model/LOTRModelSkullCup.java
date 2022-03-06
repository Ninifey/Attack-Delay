// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelSkullCup extends ModelBase
{
    private ModelRenderer base;
    private ModelRenderer cup;
    
    public LOTRModelSkullCup() {
        (this.base = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.base.addBox(-3.0f, 0.0f, -3.0f, 6, 1, 6);
        this.base.setTextureOffset(0, 7).addBox(-1.0f, -3.0f, -1.0f, 2, 3, 2);
        (this.cup = new ModelRenderer((ModelBase)this, 32, 0)).setRotationPoint(0.0f, -5.0f, 0.0f);
        this.cup.addBox(-4.0f, 0.0f, -4.0f, 8, 1, 8);
        this.cup.setTextureOffset(0, 16).addBox(-4.0f, -5.0f, -4.0f, 1, 5, 8);
        this.cup.setTextureOffset(18, 23).addBox(-3.0f, -5.0f, -4.0f, 6, 5, 1);
        this.cup.setTextureOffset(32, 16).addBox(3.0f, -5.0f, -4.0f, 1, 5, 8);
        this.cup.setTextureOffset(50, 23).addBox(-3.0f, -5.0f, 3.0f, 6, 5, 1);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.base.render(f5);
        this.cup.render(f5);
    }
}
