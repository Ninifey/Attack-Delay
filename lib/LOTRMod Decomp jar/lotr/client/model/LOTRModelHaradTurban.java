// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.common.item.LOTRItemHaradTurban;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHaradTurban extends LOTRModelHaradRobes
{
    private ModelRenderer ornament;
    
    public LOTRModelHaradTurban() {
        this(0.0f);
    }
    
    public LOTRModelHaradTurban(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-5.0f, -10.0f, -5.0f, 10, 5, 10, 0.0f);
        final ModelRenderer shawl = new ModelRenderer((ModelBase)this, 0, 15);
        shawl.addBox(-4.5f, -5.0f, 1.5f, 9, 6, 4, 0.25f);
        shawl.rotateAngleX = (float)Math.toRadians(13.0);
        super.bipedHead.addChild(shawl);
        (this.ornament = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-1.0f, -9.0f, -6.0f, 2, 2, 1, 0.0f);
        super.bipedHead.addChild(this.ornament);
        super.bipedHeadwear.cubeList.clear();
    }
    
    @Override
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.ornament.showModel = false;
        super.render(entity, f, f1, f2, f3, f4, f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        LOTRArmorModels.INSTANCE.copyBoxRotations(this.ornament, super.bipedHead);
        this.ornament.showModel = (super.bipedHead.showModel && LOTRItemHaradTurban.hasOrnament(super.robeItem));
        this.ornament.render(f5);
    }
}
