// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelUnsmeltery extends ModelBase
{
    private ModelRenderer base;
    private ModelRenderer body;
    private ModelRenderer standRight;
    private ModelRenderer standLeft;
    
    public LOTRModelUnsmeltery() {
        super.textureWidth = 64;
        super.textureHeight = 64;
        (this.base = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 21.0f, 0.0f);
        this.base.addBox(-7.0f, 0.0f, -7.0f, 14, 3, 14);
        (this.body = new ModelRenderer((ModelBase)this, 0, 17)).setRotationPoint(0.0f, 12.0f, 0.0f);
        this.body.addBox(-7.0f, -2.0f, -7.0f, 14, 10, 14);
        this.body.setTextureOffset(0, 41).addBox(-7.0f, -4.0f, -7.0f, 14, 2, 1);
        this.body.addBox(-7.0f, -4.0f, 6.0f, 14, 2, 1);
        this.body.setTextureOffset(0, 44).addBox(-7.0f, -4.0f, -6.0f, 1, 2, 12);
        this.body.addBox(6.0f, -4.0f, -6.0f, 1, 2, 12);
        (this.standRight = new ModelRenderer((ModelBase)this, 56, 6)).setRotationPoint(-7.0f, 23.0f, 0.0f);
        this.standRight.addBox(-0.9f, -12.0f, -1.0f, 1, 12, 2);
        final ModelRenderer panelRight = new ModelRenderer((ModelBase)this, 56, 0);
        panelRight.setRotationPoint(0.0f, -11.0f, 0.0f);
        panelRight.addBox(-1.0f, -2.0f, -1.0f, 1, 3, 3);
        panelRight.rotateAngleX = (float)Math.toRadians(45.0);
        this.standRight.addChild(panelRight);
        (this.standLeft = new ModelRenderer((ModelBase)this, 56, 6)).setRotationPoint(7.0f, 23.0f, 0.0f);
        this.standLeft.mirror = true;
        this.standLeft.addBox(-0.1f, -12.0f, -1.0f, 1, 12, 2);
        final ModelRenderer panelLeft = new ModelRenderer((ModelBase)this, 56, 0);
        panelLeft.setRotationPoint(0.0f, -11.0f, 0.0f);
        panelLeft.mirror = true;
        panelLeft.addBox(0.0f, -2.0f, -1.0f, 1, 3, 3);
        panelLeft.rotateAngleX = (float)Math.toRadians(45.0);
        this.standLeft.addChild(panelLeft);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.body.rotateAngleX = f * (float)Math.toRadians(20.0);
        this.base.render(f5);
        this.body.render(f5);
        this.standRight.render(f5);
        this.standLeft.render(f5);
    }
}
