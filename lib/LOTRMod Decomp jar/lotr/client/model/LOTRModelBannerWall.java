// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelBannerWall extends ModelBase
{
    private ModelRenderer post;
    private ModelRenderer banner;
    
    public LOTRModelBannerWall() {
        super.textureWidth = 64;
        super.textureHeight = 64;
        (this.post = new ModelRenderer((ModelBase)this, 4, 18)).setRotationPoint(0.0f, -8.0f, 0.0f);
        this.post.addBox(-8.0f, 0.0f, -0.5f, 16, 1, 1);
        (this.banner = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.banner.addBox(-8.0f, 0.0f, 0.0f, 16, 32, 0);
    }
    
    public void renderPost(final float f) {
        this.post.render(f);
    }
    
    public void renderBanner(final float f) {
        this.banner.render(f);
    }
}
