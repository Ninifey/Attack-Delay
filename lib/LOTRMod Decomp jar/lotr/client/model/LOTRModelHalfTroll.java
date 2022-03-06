// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHalfTroll extends LOTRModelBiped
{
    private ModelRenderer mohawk;
    private ModelRenderer hornRight1;
    private ModelRenderer hornRight2;
    private ModelRenderer hornLeft1;
    private ModelRenderer hornLeft2;
    
    public LOTRModelHalfTroll() {
        this(0.0f);
    }
    
    public LOTRModelHalfTroll(final float f) {
        super(f);
        ((ModelBase)this).textureWidth = 64;
        ((ModelBase)this).textureHeight = 64;
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -8.0f, 0.0f);
        super.bipedHead.addBox(-5.0f, -10.0f, -5.0f, 10, 10, 10, f);
        super.bipedHead.setTextureOffset(40, 5).addBox(-4.0f, -3.0f, -7.0f, 8, 3, 2, f);
        final ModelRenderer nose = new ModelRenderer((ModelBase)this, 30, 0);
        nose.addBox(-1.0f, -4.5f, -8.0f, 2, 3, 3, f);
        nose.rotateAngleX = (float)Math.toRadians(-20.0);
        super.bipedHead.addChild(nose);
        final ModelRenderer teeth = new ModelRenderer((ModelBase)this, 60, 7);
        teeth.addBox(-3.5f, -7.5f, -5.0f, 1, 2, 1, f);
        teeth.mirror = true;
        teeth.addBox(2.5f, -7.5f, -5.0f, 1, 2, 1, f);
        teeth.rotateAngleX = (float)Math.toRadians(30.0);
        super.bipedHead.addChild(teeth);
        final ModelRenderer earRight = new ModelRenderer((ModelBase)this, 0, 0);
        earRight.addBox(-5.0f, -6.0f, -2.0f, 1, 3, 3, f);
        earRight.rotateAngleY = (float)Math.toRadians(-35.0);
        super.bipedHead.addChild(earRight);
        final ModelRenderer earLeft = new ModelRenderer((ModelBase)this, 0, 0);
        earLeft.mirror = true;
        earLeft.addBox(4.0f, -6.0f, -2.0f, 1, 3, 3, f);
        earLeft.rotateAngleY = (float)Math.toRadians(35.0);
        super.bipedHead.addChild(earLeft);
        (this.mohawk = new ModelRenderer((ModelBase)this, 40, 10)).addBox(-1.0f, -12.5f, -1.5f, 2, 10, 8, f);
        super.bipedHead.addChild(this.mohawk);
        (this.hornRight1 = new ModelRenderer((ModelBase)this, 40, 0)).addBox(-10.0f, -8.0f, 1.0f, 3, 2, 2, f);
        this.hornRight1.rotateAngleZ = (float)Math.toRadians(20.0);
        super.bipedHead.addChild(this.hornRight1);
        (this.hornRight2 = new ModelRenderer((ModelBase)this, 50, 2)).addBox(-14.5f, -4.0f, 1.5f, 3, 1, 1, f);
        this.hornRight2.rotateAngleZ = (float)Math.toRadians(40.0);
        super.bipedHead.addChild(this.hornRight2);
        this.hornLeft1 = new ModelRenderer((ModelBase)this, 40, 0);
        this.hornLeft1.mirror = true;
        this.hornLeft1.addBox(7.0f, -8.0f, 1.0f, 3, 2, 2, f);
        this.hornLeft1.rotateAngleZ = (float)Math.toRadians(-20.0);
        super.bipedHead.addChild(this.hornLeft1);
        this.hornLeft2 = new ModelRenderer((ModelBase)this, 50, 2);
        this.hornLeft2.mirror = true;
        this.hornLeft2.addBox(11.5f, -4.0f, 1.5f, 3, 1, 1, f);
        this.hornLeft2.rotateAngleZ = (float)Math.toRadians(-40.0);
        super.bipedHead.addChild(this.hornLeft2);
        (super.bipedBody = new ModelRenderer((ModelBase)this, 0, 20)).setRotationPoint(0.0f, -8.0f, 0.0f);
        super.bipedBody.addBox(-6.0f, 0.0f, -4.0f, 12, 16, 8, f);
        (super.bipedRightArm = new ModelRenderer((ModelBase)this, 20, 50)).setRotationPoint(-8.5f, -6.0f, 0.0f);
        super.bipedRightArm.addBox(-3.5f, -2.0f, -3.0f, 6, 8, 6, f);
        super.bipedRightArm.setTextureOffset(0, 49).addBox(-3.0f, 6.0f, -2.5f, 5, 10, 5, f);
        (super.bipedLeftArm = new ModelRenderer((ModelBase)this, 20, 50)).setRotationPoint(8.5f, -6.0f, 0.0f);
        super.bipedLeftArm.mirror = true;
        super.bipedLeftArm.addBox(-2.5f, -2.0f, -3.0f, 6, 8, 6, f);
        super.bipedLeftArm.setTextureOffset(0, 49).addBox(-2.0f, 6.0f, -2.5f, 5, 10, 5, f);
        (super.bipedRightLeg = new ModelRenderer((ModelBase)this, 40, 28)).setRotationPoint(-3.2f, 8.0f, 0.0f);
        super.bipedRightLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 16, 6, f);
        (super.bipedLeftLeg = new ModelRenderer((ModelBase)this, 40, 28)).setRotationPoint(3.2f, 8.0f, 0.0f);
        super.bipedLeftLeg.mirror = true;
        super.bipedLeftLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 16, 6, f);
        super.bipedHeadwear.isHidden = true;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll)entity;
        this.mohawk.showModel = halfTroll.hasMohawk();
        final ModelRenderer hornRight1 = this.hornRight1;
        final ModelRenderer hornLeft1 = this.hornLeft1;
        final boolean hasHorns = halfTroll.hasHorns();
        hornLeft1.showModel = hasHorns;
        hornRight1.showModel = hasHorns;
        final ModelRenderer hornRight2 = this.hornRight2;
        final ModelRenderer hornLeft2 = this.hornLeft2;
        final boolean hasFullHorns = halfTroll.hasFullHorns();
        hornLeft2.showModel = hasFullHorns;
        hornRight2.showModel = hasFullHorns;
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
}
