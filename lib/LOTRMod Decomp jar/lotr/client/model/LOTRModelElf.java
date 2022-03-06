// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.common.entity.npc.LOTREntityElf;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import lotr.client.render.entity.LOTRGlowingEyes;

public class LOTRModelElf extends LOTRModelBiped implements LOTRGlowingEyes.Model
{
    private ModelRenderer earRight;
    private ModelRenderer earLeft;
    public ModelRenderer bipedChest;
    
    public LOTRModelElf() {
        this(0.0f);
    }
    
    public LOTRModelElf(final float f) {
        this(f, 64, (f == 0.0f) ? 64 : 32);
    }
    
    public LOTRModelElf(final float f, final int width, final int height) {
        super(f, 0.0f, width, height);
        (this.earRight = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -6.5f, -1.0f, 1, 4, 2);
        this.earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earRight.rotateAngleZ = -0.2617994f;
        this.earLeft = new ModelRenderer((ModelBase)this, 0, 0);
        this.earLeft.mirror = true;
        this.earLeft.addBox(3.0f, -6.5f, -1.0f, 1, 4, 2);
        this.earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earLeft.rotateAngleZ = 0.2617994f;
        super.bipedHead.addChild(this.earRight);
        super.bipedHead.addChild(this.earLeft);
        (this.bipedChest = new ModelRenderer((ModelBase)this, 24, 0)).addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
        this.bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedBody.addChild(this.bipedChest);
        if (height == 64) {
            (super.bipedHeadwear = new ModelRenderer((ModelBase)this, 0, 32)).addBox(-4.0f, -8.0f, -4.0f, 8, 16, 8, 0.5f + f);
            super.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedChest.showModel = (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest());
        if (((ModelBase)this).isChild) {
            final float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            super.bipedHead.render(f5);
            super.bipedHeadwear.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            super.bipedBody.render(f5);
            super.bipedRightArm.render(f5);
            super.bipedLeftArm.render(f5);
            super.bipedRightLeg.render(f5);
            super.bipedLeftLeg.render(f5);
            GL11.glPopMatrix();
        }
        else {
            super.bipedHead.render(f5);
            super.bipedHeadwear.render(f5);
            super.bipedBody.render(f5);
            super.bipedRightArm.render(f5);
            super.bipedLeftArm.render(f5);
            super.bipedRightLeg.render(f5);
            super.bipedLeftLeg.render(f5);
        }
    }
    
    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (entity instanceof LOTREntityElf) {
            final LOTREntityElf elf = (LOTREntityElf)entity;
            if (elf.isJazz() && elf.isSolo()) {
                super.bipedRightArm.rotateAngleY = (float)Math.toRadians(-45.0);
                super.bipedLeftArm.rotateAngleY = -super.bipedRightArm.rotateAngleY;
                super.bipedRightArm.rotateAngleX = (float)Math.toRadians(-50.0);
                super.bipedLeftArm.rotateAngleX = super.bipedRightArm.rotateAngleX;
            }
        }
    }
    
    @Override
    public void renderGlowingEyes(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        super.bipedHead.render(f5);
    }
}
