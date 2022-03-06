// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelPig;

public class LOTRModelBoar extends ModelPig
{
    private ModelRenderer tusks;
    
    public LOTRModelBoar() {
        this(0.0f);
    }
    
    public LOTRModelBoar(final float f) {
        super(f);
        ((ModelQuadruped)this).head.setTextureOffset(24, 0).addBox(-3.0f, 0.0f, -10.0f, 6, 4, 2, f);
        ((ModelQuadruped)this).head.setTextureOffset(40, 0).addBox(-5.0f, -5.0f, -6.0f, 1, 2, 2, f);
        ((ModelQuadruped)this).head.mirror = true;
        ((ModelQuadruped)this).head.addBox(4.0f, -5.0f, -6.0f, 1, 2, 2, f);
        (this.tusks = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, 2.0f, -11.0f, 1, 1, 2, f);
        this.tusks.setTextureOffset(1, 1).addBox(-4.0f, 1.0f, -11.5f, 1, 1, 1, f);
        this.tusks.mirror = true;
        this.tusks.setTextureOffset(0, 0).addBox(3.0f, 2.0f, -11.0f, 1, 1, 2, f);
        this.tusks.setTextureOffset(1, 1).addBox(3.0f, 1.0f, -11.5f, 1, 1, 1, f);
        ((ModelQuadruped)this).head.addChild(this.tusks);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.tusks.showModel = !((ModelBase)this).isChild;
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
}
