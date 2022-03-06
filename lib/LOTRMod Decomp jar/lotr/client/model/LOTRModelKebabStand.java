// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelKebabStand extends ModelBase
{
    private ModelRenderer stand;
    private ModelRenderer[] kebab;
    
    public LOTRModelKebabStand() {
        super.textureWidth = 64;
        super.textureHeight = 64;
        (this.stand = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 24.0f, 0.0f);
        this.stand.addBox(-7.0f, -1.0f, -7.0f, 14, 1, 14);
        this.stand.setTextureOffset(0, 15).addBox(-4.0f, -16.0f, 6.0f, 8, 15, 1);
        this.stand.setTextureOffset(0, 31).addBox(-4.0f, -16.0f, -2.0f, 8, 1, 8);
        this.stand.setTextureOffset(0, 40).addBox(-0.5f, -15.0f, -0.5f, 1, 14, 1);
        final ModelRenderer panelRight = new ModelRenderer((ModelBase)this, 18, 15);
        panelRight.setRotationPoint(-4.0f, 0.0f, 6.0f);
        panelRight.addBox(-4.0f, -16.0f, 0.0f, 4, 15, 1);
        panelRight.rotateAngleY = (float)Math.toRadians(-45.0);
        this.stand.addChild(panelRight);
        final ModelRenderer panelLeft = new ModelRenderer((ModelBase)this, 18, 15);
        panelLeft.setRotationPoint(4.0f, 0.0f, 6.0f);
        panelLeft.addBox(0.0f, -16.0f, 0.0f, 4, 15, 1);
        panelLeft.rotateAngleY = (float)Math.toRadians(45.0);
        this.stand.addChild(panelLeft);
        super.textureWidth = 32;
        super.textureHeight = 32;
        this.kebab = new ModelRenderer[9];
        for (int i = 0; i < this.kebab.length; ++i) {
            final ModelRenderer kb = new ModelRenderer((ModelBase)this, 0, 0);
            kb.setRotationPoint(0.0f, 10.0f, 0.0f);
            if (i > 0) {
                final int width = i + 1;
                kb.addBox(-(float)width / 2.0f, 0.0f, -(float)width / 2.0f, width, 11, width);
            }
            this.kebab[i] = kb;
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.stand.render(f5);
    }
    
    public void renderKebab(final float scale, int size, final float spin) {
        if (size < 0 || size >= this.kebab.length) {
            size = 0;
        }
        this.kebab[size].rotateAngleY = (float)Math.toRadians(spin);
        this.kebab[size].render(scale);
    }
}
