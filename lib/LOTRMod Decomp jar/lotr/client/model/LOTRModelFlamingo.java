// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import net.minecraft.util.MathHelper;
import lotr.common.entity.animal.LOTREntityFlamingo;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelFlamingo extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer body;
    private ModelRenderer tail;
    private ModelRenderer wingLeft;
    private ModelRenderer wingRight;
    private ModelRenderer legLeft;
    private ModelRenderer legRight;
    private ModelRenderer head_child;
    private ModelRenderer body_child;
    private ModelRenderer tail_child;
    private ModelRenderer wingLeft_child;
    private ModelRenderer wingRight_child;
    private ModelRenderer legLeft_child;
    private ModelRenderer legRight_child;
    
    public LOTRModelFlamingo() {
        (this.head = new ModelRenderer((ModelBase)this, 8, 24)).addBox(-2.0f, -17.0f, -2.0f, 4, 4, 4);
        this.head.setRotationPoint(0.0f, 5.0f, -2.0f);
        this.head.setTextureOffset(24, 27).addBox(-1.5f, -16.0f, -5.0f, 3, 2, 3);
        this.head.setTextureOffset(36, 30).addBox(-1.0f, -14.0f, -5.0f, 2, 1, 1);
        this.head.setTextureOffset(0, 16).addBox(-1.0f, -15.0f, -1.0f, 2, 14, 2);
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.0f, 0.0f, -4.0f, 6, 7, 8);
        this.body.setRotationPoint(0.0f, 3.0f, 0.0f);
        (this.tail = new ModelRenderer((ModelBase)this, 42, 23)).addBox(-2.5f, 0.0f, 0.0f, 5, 3, 6);
        this.tail.setRotationPoint(0.0f, 4.0f, 3.0f);
        (this.wingLeft = new ModelRenderer((ModelBase)this, 36, 0)).addBox(-1.0f, 0.0f, -3.0f, 1, 8, 6);
        this.wingLeft.setRotationPoint(-3.0f, 3.0f, 0.0f);
        (this.wingRight = new ModelRenderer((ModelBase)this, 50, 0)).addBox(0.0f, 0.0f, -3.0f, 1, 8, 6);
        this.wingRight.setRotationPoint(3.0f, 3.0f, 0.0f);
        (this.legLeft = new ModelRenderer((ModelBase)this, 30, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 16, 1);
        this.legLeft.setRotationPoint(-2.0f, 8.0f, 0.0f);
        this.legLeft.setTextureOffset(30, 17).addBox(-1.5f, 14.9f, -3.5f, 3, 1, 3);
        (this.legRight = new ModelRenderer((ModelBase)this, 30, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 16, 1);
        this.legRight.setRotationPoint(2.0f, 8.0f, 0.0f);
        this.legRight.setTextureOffset(30, 17).addBox(-1.5f, 14.9f, -3.5f, 3, 1, 3);
        (this.head_child = new ModelRenderer((ModelBase)this, 0, 24)).addBox(-2.0f, -4.0f, -4.0f, 4, 4, 4);
        this.head_child.setRotationPoint(0.0f, 15.0f, -3.0f);
        this.head_child.setTextureOffset(16, 28).addBox(-1.0f, -2.0f, -6.0f, 2, 2, 2);
        (this.body_child = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-3.0f, 0.0f, -4.0f, 6, 5, 7);
        this.body_child.setRotationPoint(0.0f, 14.0f, 0.0f);
        (this.tail_child = new ModelRenderer((ModelBase)this, 0, 14)).addBox(-2.0f, 0.0f, 0.0f, 4, 2, 3);
        this.tail_child.setRotationPoint(0.0f, 14.5f, 3.0f);
        (this.wingLeft_child = new ModelRenderer((ModelBase)this, 40, 0)).addBox(-1.0f, 0.0f, -3.0f, 1, 4, 5);
        this.wingLeft_child.setRotationPoint(-3.0f, 14.0f, 0.0f);
        (this.wingRight_child = new ModelRenderer((ModelBase)this, 52, 0)).addBox(0.0f, 0.0f, -3.0f, 1, 4, 5);
        this.wingRight_child.setRotationPoint(3.0f, 14.0f, 0.0f);
        (this.legLeft_child = new ModelRenderer((ModelBase)this, 27, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 5, 1);
        this.legLeft_child.setRotationPoint(-2.0f, 19.0f, 0.0f);
        this.legLeft_child.setTextureOffset(27, 7).addBox(-1.5f, 3.9f, -3.5f, 3, 1, 3);
        (this.legRight_child = new ModelRenderer((ModelBase)this, 27, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 5, 1);
        this.legRight_child.setRotationPoint(2.0f, 19.0f, 0.0f);
        this.legRight_child.setTextureOffset(27, 7).addBox(-1.5f, 3.9f, -3.5f, 3, 1, 3);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (super.isChild) {
            this.head_child.render(f5);
            this.body_child.render(f5);
            this.tail_child.render(f5);
            this.wingLeft_child.render(f5);
            this.wingRight_child.render(f5);
            this.legLeft_child.render(f5);
            this.legRight_child.render(f5);
        }
        else {
            this.head.render(f5);
            this.body.render(f5);
            this.tail.render(f5);
            this.wingLeft.render(f5);
            this.wingRight.render(f5);
            this.legLeft.render(f5);
            this.legRight.render(f5);
        }
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        final LOTREntityFlamingo flamingo = (LOTREntityFlamingo)entity;
        if (super.isChild) {
            this.head_child.rotateAngleX = f4 / 57.29578f;
            this.head_child.rotateAngleY = f3 / 57.29578f;
            this.legLeft_child.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
            this.legRight_child.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
            this.wingLeft_child.rotateAngleZ = f2 * 0.4f;
            this.wingRight_child.rotateAngleZ = -f2 * 0.4f;
            this.tail_child.rotateAngleX = -0.25f;
        }
        else {
            this.head.rotateAngleX = f4 / 57.29578f;
            this.head.rotateAngleY = f3 / 57.29578f;
            this.legLeft.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
            this.legRight.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
            this.wingLeft.rotateAngleZ = f2 * 0.4f;
            this.wingRight.rotateAngleZ = -f2 * 0.4f;
            this.tail.rotateAngleX = -0.25f;
            final int cur = flamingo.getFishingTickCur();
            final int pre = flamingo.getFishingTickPre();
            final float fishing = pre + (cur - pre) * LOTRTickHandlerClient.renderTick;
            if (cur > 160 + 20) {
                this.head.rotateAngleX = 3.1415927f * (200.0f - fishing) / 20.0f;
            }
            else if (cur > 20) {
                this.head.rotateAngleX = 3.1415927f;
            }
            else if (cur > 0) {
                this.head.rotateAngleX = 3.1415927f * fishing / 20.0f;
            }
        }
    }
}
