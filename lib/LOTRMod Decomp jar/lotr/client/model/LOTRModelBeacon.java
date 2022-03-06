// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelBeacon extends ModelBase
{
    private ModelRenderer base;
    private ModelRenderer[][] logs;
    
    public LOTRModelBeacon() {
        this.logs = new ModelRenderer[3][4];
        (this.base = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-8.0f, -8.0f, -2.0f, 16, 16, 4);
        this.base.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.base.rotateAngleX = 1.5707964f;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                (this.logs[i][j] = new ModelRenderer((ModelBase)this, 30, 15)).addBox(-1.5f, 0.0f, -7.0f, 3, 3, 14);
                this.logs[i][j].setRotationPoint((i != 1) ? (-6.0f + j * 4.0f) : 0.0f, 17.0f - i * 3.0f, (i == 1) ? (-6.0f + j * 4.0f) : 0.0f);
                if (i == 1) {
                    this.logs[i][j].rotateAngleY = 1.5707964f;
                }
            }
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.base.render(f5);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.logs[i][j].render(f5);
            }
        }
    }
}
