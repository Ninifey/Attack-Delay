// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelArmorStand extends ModelBase
{
    private ModelRenderer base;
    private ModelRenderer head;
    private ModelRenderer spine;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightHip;
    private ModelRenderer leftHip;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer rightFoot;
    private ModelRenderer leftFoot;
    
    public LOTRModelArmorStand() {
        (this.base = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-8.0f, -8.0f, -8.0f, 16, 16, 2);
        this.base.setRotationPoint(0.0f, 30.0f, 0.0f);
        this.base.rotateAngleX = -1.5707964f;
        (this.head = new ModelRenderer((ModelBase)this, 40, 0)).addBox(-3.0f, 0.0f, -2.0f, 6, 8, 4);
        this.head.setRotationPoint(0.0f, -11.0f, 0.0f);
        (this.spine = new ModelRenderer((ModelBase)this, 60, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 25, 1);
        this.spine.setRotationPoint(0.0f, -3.0f, 0.0f);
        (this.rightArm = new ModelRenderer((ModelBase)this, 44, 12)).addBox(-7.5f, 0.0f, -0.5f, 7, 1, 1);
        this.rightArm.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.leftArm = new ModelRenderer((ModelBase)this, 44, 12);
        this.leftArm.mirror = true;
        this.leftArm.addBox(0.5f, 0.0f, -0.5f, 7, 1, 1);
        this.leftArm.setRotationPoint(0.0f, -2.0f, 0.0f);
        (this.rightHip = new ModelRenderer((ModelBase)this, 42, 30)).addBox(-3.5f, 0.0f, -0.5f, 3, 1, 1);
        this.rightHip.setRotationPoint(0.0f, 9.0f, 0.0f);
        this.leftHip = new ModelRenderer((ModelBase)this, 42, 30);
        this.leftHip.mirror = true;
        this.leftHip.addBox(0.5f, 0.0f, -0.5f, 3, 1, 1);
        this.leftHip.setRotationPoint(0.0f, 9.0f, 0.0f);
        (this.rightLeg = new ModelRenderer((ModelBase)this, 50, 23)).addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1);
        this.rightLeg.setRotationPoint(-2.0f, 10.0f, 0.0f);
        this.leftLeg = new ModelRenderer((ModelBase)this, 50, 23);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1);
        this.leftLeg.setRotationPoint(2.0f, 10.0f, 0.0f);
        (this.rightFoot = new ModelRenderer((ModelBase)this, 54, 27)).addBox(-2.0f, 0.0f, -1.0f, 3, 3, 2);
        this.rightFoot.setRotationPoint(-2.0f, 18.0f, 0.0f);
        this.leftFoot = new ModelRenderer((ModelBase)this, 54, 27);
        this.leftFoot.mirror = true;
        this.leftFoot.addBox(-1.0f, 0.0f, -1.0f, 3, 3, 2);
        this.leftFoot.setRotationPoint(2.0f, 18.0f, 0.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.base.render(f5);
        this.head.render(f5);
        this.spine.render(f5);
        this.rightArm.render(f5);
        this.leftArm.render(f5);
        this.rightHip.render(f5);
        this.leftHip.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        this.rightFoot.render(f5);
        this.leftFoot.render(f5);
    }
}
