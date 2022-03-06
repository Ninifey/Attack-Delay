// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelMug extends ModelBase
{
    private ModelRenderer[] mugParts;
    private ModelRenderer[] handleParts;
    
    public LOTRModelMug() {
        this.mugParts = new ModelRenderer[5];
        this.handleParts = new ModelRenderer[3];
        (this.mugParts[0] = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.0f, -8.0f, -2.0f, 1, 8, 4);
        (this.mugParts[1] = new ModelRenderer((ModelBase)this, 10, 3)).addBox(-3.0f, -8.0f, -3.0f, 6, 8, 1);
        (this.mugParts[2] = new ModelRenderer((ModelBase)this, 24, 0)).addBox(2.0f, -8.0f, -2.0f, 1, 8, 4);
        (this.mugParts[3] = new ModelRenderer((ModelBase)this, 34, 3)).addBox(-3.0f, -8.0f, 2.0f, 6, 8, 1);
        (this.mugParts[4] = new ModelRenderer((ModelBase)this, 0, 12)).addBox(-2.0f, -1.0f, -2.0f, 4, 1, 4);
        (this.handleParts[0] = new ModelRenderer((ModelBase)this, 0, 17)).addBox(3.0f, -7.0f, -0.5f, 2, 1, 1);
        (this.handleParts[1] = new ModelRenderer((ModelBase)this, 0, 19)).addBox(4.0f, -6.0f, -0.5f, 1, 4, 1);
        (this.handleParts[2] = new ModelRenderer((ModelBase)this, 0, 24)).addBox(3.0f, -2.0f, -0.5f, 2, 1, 1);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        for (final ModelRenderer part : this.mugParts) {
            part.render(f5);
        }
        for (final ModelRenderer part : this.handleParts) {
            part.render(f5);
        }
    }
}
