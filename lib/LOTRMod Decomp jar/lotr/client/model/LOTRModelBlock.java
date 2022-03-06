// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelBlock extends ModelBase
{
    private ModelRenderer block;
    
    public LOTRModelBlock(final float f) {
        (this.block = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16, f);
        this.block.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.block.render(f5);
    }
}
