// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelSwan extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer tail;
    private ModelRenderer neck;
    private ModelRenderer head;
    private ModelRenderer wingRight;
    private ModelRenderer wingLeft;
    private ModelRenderer legRight;
    private ModelRenderer legLeft;
    
    public LOTRModelSwan() {
        super.textureWidth = 64;
        super.textureHeight = 64;
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 18.0f, 0.0f);
        this.body.addBox(-4.0f, -3.0f, -7.0f, 8, 6, 14);
        (this.tail = new ModelRenderer((ModelBase)this, 24, 20)).setRotationPoint(0.0f, -2.0f, 7.0f);
        this.tail.addBox(-3.0f, -1.5f, -1.0f, 6, 4, 4);
        this.tail.setTextureOffset(24, 28).addBox(-2.0f, -1.0f, 3.0f, 4, 2, 3);
        this.body.addChild(this.tail);
        (this.neck = new ModelRenderer((ModelBase)this, 44, 11)).setRotationPoint(0.0f, 0.0f, -5.5f);
        this.neck.addBox(-1.0f, -11.0f, -3.0f, 2, 13, 2);
        this.body.addChild(this.neck);
        (this.head = new ModelRenderer((ModelBase)this, 44, 0)).setRotationPoint(0.0f, -10.0f, -2.0f);
        this.head.addBox(-1.5f, -2.0f, -4.0f, 3, 3, 4);
        this.head.setTextureOffset(44, 7).addBox(-1.0f, -0.5f, -7.0f, 2, 1, 3);
        this.neck.addChild(this.head);
        (this.wingRight = new ModelRenderer((ModelBase)this, 0, 20)).setRotationPoint(-4.0f, 18.0f, -5.0f);
        this.wingRight.addBox(-1.0f, -3.5f, -1.0f, 1, 7, 8);
        this.wingRight.setTextureOffset(0, 35).addBox(-1.0f, -4.5f, 7.0f, 1, 6, 3);
        this.wingRight.setTextureOffset(8, 35).addBox(-1.0f, -5.5f, 10.0f, 1, 5, 3);
        this.wingLeft = new ModelRenderer((ModelBase)this, 0, 20);
        this.wingLeft.mirror = true;
        this.wingLeft.setRotationPoint(4.0f, 18.0f, -5.0f);
        this.wingLeft.addBox(0.0f, -3.5f, -1.0f, 1, 7, 8);
        this.wingLeft.setTextureOffset(0, 35).addBox(0.0f, -4.5f, 7.0f, 1, 6, 3);
        this.wingLeft.setTextureOffset(8, 35).addBox(0.0f, -5.5f, 10.0f, 1, 5, 3);
        (this.legRight = new ModelRenderer((ModelBase)this, 24, 33)).setRotationPoint(-2.0f, 21.0f, 1.0f);
        this.legRight.addBox(-1.5f, 0.0f, -3.0f, 3, 3, 3);
        this.legLeft = new ModelRenderer((ModelBase)this, 24, 33);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(2.0f, 21.0f, 1.0f);
        this.legLeft.addBox(-1.5f, 0.0f, -3.0f, 3, 3, 3);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.wingRight.render(f5);
        this.wingLeft.render(f5);
        this.legRight.render(f5);
        this.legLeft.render(f5);
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        final float tick = LOTRTickHandlerClient.renderTick;
        final LOTREntitySwan swan = (LOTREntitySwan)entity;
        final float f6 = swan.prevFlapPhase + (swan.flapPhase - swan.prevFlapPhase) * tick;
        final float f7 = swan.prevFlapPower + (swan.flapPower - swan.prevFlapPower) * tick;
        final float flapping = (MathHelper.sin(f6) + 1.0f) * f7;
        this.neck.rotateAngleX = (float)Math.toRadians(-12.0);
        final ModelRenderer neck = this.neck;
        neck.rotateAngleX += f4 / 57.295776f * 0.4f;
        final ModelRenderer neck2 = this.neck;
        neck2.rotateAngleX += swan.getPeckAngle(tick) * 1.0f;
        this.neck.rotateAngleY = f3 / 57.295776f;
        this.head.rotateAngleX = -this.neck.rotateAngleX;
        this.tail.rotateAngleX = (float)Math.toRadians(20.0);
        final ModelRenderer tail = this.tail;
        tail.rotateAngleX += MathHelper.cos(f * 0.4f) * f1 * 0.5f;
        final ModelRenderer tail2 = this.tail;
        tail2.rotateAngleX += MathHelper.cos(f2 * 0.1f) * 0.1f;
        final float wingX = (float)Math.toRadians(10.0);
        float wingY = (1.0f + MathHelper.cos(f * 0.4f + 3.1415927f)) * f1 * 0.5f;
        wingY += (1.0f + MathHelper.cos(f2 * 0.15f)) * 0.1f;
        wingY += flapping * 0.2f;
        float wingZ = MathHelper.cos(f * 0.4f + 3.1415927f) * f1 * 0.2f;
        wingZ += flapping * 0.5f;
        this.wingRight.rotateAngleX = wingX;
        this.wingLeft.rotateAngleX = wingX;
        this.wingRight.rotateAngleY = -wingY;
        this.wingLeft.rotateAngleY = wingY;
        this.wingRight.rotateAngleZ = wingZ;
        this.wingLeft.rotateAngleZ = -wingZ;
        this.legRight.rotateAngleX = MathHelper.cos(f * 0.7f + 3.1415927f) * f1 * 1.0f;
        this.legLeft.rotateAngleX = MathHelper.cos(f * 0.7f) * f1 * 1.0f;
    }
}
