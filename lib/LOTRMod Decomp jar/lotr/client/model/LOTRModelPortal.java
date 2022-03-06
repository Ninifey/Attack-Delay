// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelPortal extends ModelBase
{
    private ModelRenderer[] ringParts;
    
    public LOTRModelPortal(final int i) {
        this.ringParts = new ModelRenderer[60];
        for (int j = 0; j < 60; ++j) {
            if (i == 0) {
                (this.ringParts[j] = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(64, 32)).addBox(-2.0f, -3.5f, -38.0f, 4, 7, 3);
            }
            else {
                this.ringParts[j] = new ModelRenderer((ModelBase)this, j % 30 * 8, 0).setTextureSize(240, 5);
                this.ringParts[j].mirror = true;
                this.ringParts[j].addBox(-2.0f, -2.5f, -38.0f, 4, 5, 0);
            }
            this.ringParts[j].setRotationPoint(0.0f, 0.0f, 0.0f);
            this.ringParts[j].rotateAngleY = j * 6.0f / 180.0f * 3.1415927f;
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        for (int i = 0; i < 60; ++i) {
            this.ringParts[i].render(f5);
        }
    }
}
