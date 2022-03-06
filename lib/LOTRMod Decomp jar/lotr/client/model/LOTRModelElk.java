// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.util.MathHelper;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRMod;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class LOTRModelElk extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    private ModelRenderer head;
    private ModelRenderer nose;
    private ModelRenderer antlersRight_1;
    private ModelRenderer antlersRight_2;
    private ModelRenderer antlersRight_3;
    private ModelRenderer antlersRight_4;
    private ModelRenderer antlersLeft_1;
    private ModelRenderer antlersLeft_2;
    private ModelRenderer antlersLeft_3;
    private ModelRenderer antlersLeft_4;
    
    public LOTRModelElk() {
        this(0.0f);
    }
    
    public LOTRModelElk(final float f) {
        super.textureWidth = 128;
        super.textureHeight = 64;
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 4.0f, 9.0f);
        this.body.addBox(-6.0f, -4.0f, -21.0f, 12, 11, 26, f);
        final ModelRenderer tail = new ModelRenderer((ModelBase)this, 0, 54);
        tail.addBox(-1.0f, -5.0f, 2.0f, 2, 2, 8, f);
        tail.rotateAngleX = (float)Math.toRadians(-60.0);
        this.body.addChild(tail);
        (this.leg1 = new ModelRenderer((ModelBase)this, 42, 37)).setRotationPoint(-4.0f, 3.0f, 8.0f);
        this.leg1.addBox(-5.5f, 0.0f, -3.0f, 7, 11, 8, f);
        this.leg1.setTextureOffset(26, 37).addBox(-4.0f, 11.0f, -1.0f, 4, 10, 4, f);
        this.leg2 = new ModelRenderer((ModelBase)this, 42, 37);
        this.leg2.mirror = true;
        this.leg2.setRotationPoint(4.0f, 3.0f, 8.0f);
        this.leg2.addBox(-1.5f, 0.0f, -3.0f, 7, 11, 8, f);
        this.leg2.setTextureOffset(26, 37).addBox(0.0f, 11.0f, -1.0f, 4, 10, 4, f);
        (this.leg3 = new ModelRenderer((ModelBase)this, 0, 37)).setRotationPoint(-4.0f, 4.0f, -6.0f);
        this.leg3.addBox(-4.5f, 0.0f, -3.0f, 6, 10, 7, f);
        this.leg3.setTextureOffset(26, 37).addBox(-3.5f, 10.0f, -2.0f, 4, 10, 4, f);
        this.leg4 = new ModelRenderer((ModelBase)this, 0, 37);
        this.leg4.mirror = true;
        this.leg4.setRotationPoint(4.0f, 4.0f, -6.0f);
        this.leg4.addBox(-1.5f, 0.0f, -3.0f, 6, 10, 7, f);
        this.leg4.setTextureOffset(26, 37).addBox(-0.5f, 10.0f, -2.0f, 4, 10, 4, f);
        (this.head = new ModelRenderer((ModelBase)this, 50, 0)).setRotationPoint(0.0f, 4.0f, -10.0f);
        this.head.addBox(-2.0f, -10.0f, -4.0f, 4, 12, 8, f);
        this.head.setTextureOffset(74, 0).addBox(-3.0f, -16.0f, -8.0f, 6, 6, 13, f);
        this.head.setTextureOffset(50, 20);
        this.head.addBox(-2.0f, -18.0f, 3.0f, 1, 2, 1, f);
        this.head.mirror = true;
        this.head.addBox(1.0f, -18.0f, 3.0f, 1, 2, 1, f);
        (this.nose = new ModelRenderer((ModelBase)this, 56, 20)).addBox(-1.0f, -14.5f, -9.0f, 2, 2, 1, f);
        (this.antlersRight_1 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(10.0f, -19.0f, 2.5f, 1, 12, 1, f);
        this.antlersRight_1.rotateAngleZ = (float)Math.toRadians(-65.0);
        (this.antlersRight_2 = new ModelRenderer((ModelBase)this, 4, 0)).addBox(-3.0f, -23.6f, 2.5f, 1, 8, 1, f);
        this.antlersRight_2.rotateAngleZ = (float)Math.toRadians(-15.0);
        (this.antlersRight_3 = new ModelRenderer((ModelBase)this, 8, 0)).addBox(-8.0f, -36.0f, 2.5f, 1, 16, 1, f);
        this.antlersRight_3.rotateAngleZ = (float)Math.toRadians(-15.0);
        (this.antlersRight_4 = new ModelRenderer((ModelBase)this, 12, 0)).addBox(7.5f, -35.0f, 2.5f, 1, 10, 1, f);
        this.antlersRight_4.rotateAngleZ = (float)Math.toRadians(-50.0);
        this.head.addChild(this.antlersRight_1);
        this.head.addChild(this.antlersRight_2);
        this.head.addChild(this.antlersRight_3);
        this.head.addChild(this.antlersRight_4);
        this.antlersLeft_1 = new ModelRenderer((ModelBase)this, 0, 0);
        this.antlersLeft_1.mirror = true;
        this.antlersLeft_1.addBox(-11.0f, -19.0f, 2.5f, 1, 12, 1, f);
        this.antlersLeft_1.rotateAngleZ = (float)Math.toRadians(65.0);
        this.antlersLeft_2 = new ModelRenderer((ModelBase)this, 4, 0);
        this.antlersLeft_2.mirror = true;
        this.antlersLeft_2.addBox(2.0f, -23.6f, 2.5f, 1, 8, 1, f);
        this.antlersLeft_2.rotateAngleZ = (float)Math.toRadians(15.0);
        this.antlersLeft_3 = new ModelRenderer((ModelBase)this, 8, 0);
        this.antlersLeft_3.mirror = true;
        this.antlersLeft_3.addBox(7.0f, -36.0f, 2.5f, 1, 16, 1, f);
        this.antlersLeft_3.rotateAngleZ = (float)Math.toRadians(15.0);
        this.antlersLeft_4 = new ModelRenderer((ModelBase)this, 12, 0);
        this.antlersLeft_4.mirror = true;
        this.antlersLeft_4.addBox(-8.5f, -35.0f, 2.5f, 1, 10, 1, f);
        this.antlersLeft_4.rotateAngleZ = (float)Math.toRadians(50.0);
        this.head.addChild(this.antlersLeft_1);
        this.head.addChild(this.antlersLeft_2);
        this.head.addChild(this.antlersLeft_3);
        this.head.addChild(this.antlersLeft_4);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final LOTREntityElk elk = (LOTREntityElk)entity;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, (Entity)elk);
        GL11.glPushMatrix();
        final float scale = elk.getHorseSize();
        GL11.glTranslatef(0.0f, 24.0f * (1.0f - scale) * f5, 0.0f);
        GL11.glScalef(scale, scale, scale);
        final boolean showAntlers = scale > 0.75f;
        this.antlersRight_1.showModel = showAntlers;
        this.antlersRight_2.showModel = showAntlers;
        this.antlersRight_3.showModel = showAntlers;
        this.antlersRight_4.showModel = showAntlers;
        this.antlersLeft_1.showModel = showAntlers;
        this.antlersLeft_2.showModel = showAntlers;
        this.antlersLeft_3.showModel = showAntlers;
        this.antlersLeft_4.showModel = showAntlers;
        this.body.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
        this.head.render(f5);
        if (LOTRMod.isChristmas()) {
            GL11.glColor3f(1.0f, 0.0f, 0.0f);
        }
        this.nose.render(f5);
        if (LOTRMod.isChristmas()) {
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
        GL11.glPopMatrix();
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        final LOTREntityElk elk = (LOTREntityElk)entity;
        final float renderTick = LOTRTickHandlerClient.renderTick;
        final float rearAmount = elk.getRearingAmount(renderTick);
        final float antiRearAmount = 1.0f - rearAmount;
        this.head.rotationPointY = 4.0f;
        this.head.rotationPointZ = -10.0f;
        this.head.rotateAngleX = (float)Math.toRadians(20.0);
        this.head.rotateAngleY = 0.0f;
        this.head.rotationPointY = rearAmount * -6.0f + antiRearAmount * this.head.rotationPointY;
        this.head.rotationPointZ = rearAmount * -1.0f + antiRearAmount * this.head.rotationPointZ;
        final ModelRenderer head = this.head;
        head.rotateAngleX += (float)Math.toRadians(f4);
        final ModelRenderer head2 = this.head;
        head2.rotateAngleY += (float)Math.toRadians(f3);
        this.head.rotateAngleX *= antiRearAmount;
        this.head.rotateAngleY *= antiRearAmount;
        if (f1 > 0.2f) {
            final ModelRenderer head3 = this.head;
            head3.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.1f * f1;
        }
        this.nose.setRotationPoint(this.head.rotationPointX, this.head.rotationPointY, this.head.rotationPointZ);
        this.nose.rotateAngleX = this.head.rotateAngleX;
        this.nose.rotateAngleY = this.head.rotateAngleY;
        this.nose.rotateAngleZ = this.head.rotateAngleZ;
        this.body.rotateAngleX = 0.0f;
        this.body.rotateAngleX = rearAmount * -0.7853982f + antiRearAmount * this.body.rotateAngleX;
        final float legRotation = MathHelper.cos(f * 0.4f + 3.1415927f) * f1;
        final float f6 = -1.0471976f;
        final float f7 = 0.2617994f * rearAmount;
        final float f8 = MathHelper.cos(f2 * 0.4f + 3.1415927f);
        this.leg4.rotationPointY = -2.0f * rearAmount + 4.0f * antiRearAmount;
        this.leg4.rotationPointZ = -2.0f * rearAmount + -6.0f * antiRearAmount;
        this.leg3.rotationPointY = this.leg4.rotationPointY;
        this.leg3.rotationPointZ = this.leg4.rotationPointZ;
        this.leg1.rotateAngleX = f7 + legRotation * antiRearAmount;
        this.leg2.rotateAngleX = f7 + -legRotation * antiRearAmount;
        this.leg3.rotateAngleX = (f6 + -f8) * rearAmount + -legRotation * 0.8f * antiRearAmount;
        this.leg4.rotateAngleX = (f6 + f8) * rearAmount + legRotation * 0.8f * antiRearAmount;
    }
}
