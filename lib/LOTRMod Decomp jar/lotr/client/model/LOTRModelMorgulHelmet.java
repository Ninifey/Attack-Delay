// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelMorgulHelmet extends LOTRModelBiped
{
    private ModelRenderer[] spikes;
    
    public LOTRModelMorgulHelmet() {
        this(0.0f);
    }
    
    public LOTRModelMorgulHelmet(final float f) {
        super(f);
        this.spikes = new ModelRenderer[8];
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 12, 8, f);
        super.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.setTextureOffset(0, 20).addBox(-3.5f, -18.0f, -3.5f, 7, 10, 1, f);
        for (int i = 0; i < this.spikes.length; ++i) {
            (this.spikes[i] = new ModelRenderer((ModelBase)this, 16, 20)).setRotationPoint(0.0f, 0.0f, 0.0f);
        }
        this.spikes[0].addBox(-1.0f, -5.5f, -10.0f, 1, 1, 4);
        this.spikes[0].rotateAngleX = (float)Math.toRadians(-20.0);
        this.spikes[0].rotateAngleY = (float)Math.toRadians(20.0);
        this.spikes[1].addBox(0.0f, -5.5f, -10.0f, 1, 1, 4);
        this.spikes[1].rotateAngleX = (float)Math.toRadians(-20.0);
        this.spikes[1].rotateAngleY = (float)Math.toRadians(-20.0);
        this.spikes[2].addBox(6.0f, -5.5f, -1.0f, 4, 1, 1);
        this.spikes[2].rotateAngleZ = (float)Math.toRadians(-20.0);
        this.spikes[2].rotateAngleY = (float)Math.toRadians(20.0);
        this.spikes[3].addBox(6.0f, -5.5f, 0.0f, 4, 1, 1);
        this.spikes[3].rotateAngleZ = (float)Math.toRadians(-20.0);
        this.spikes[3].rotateAngleY = (float)Math.toRadians(-20.0);
        this.spikes[4].addBox(0.0f, -5.5f, 6.0f, 1, 1, 4);
        this.spikes[4].rotateAngleX = (float)Math.toRadians(20.0);
        this.spikes[4].rotateAngleY = (float)Math.toRadians(20.0);
        this.spikes[5].addBox(-1.0f, -5.5f, 6.0f, 1, 1, 4);
        this.spikes[5].rotateAngleX = (float)Math.toRadians(20.0);
        this.spikes[5].rotateAngleY = (float)Math.toRadians(-20.0);
        this.spikes[6].addBox(-10.0f, -5.5f, 0.0f, 4, 1, 1);
        this.spikes[6].rotateAngleZ = (float)Math.toRadians(20.0);
        this.spikes[6].rotateAngleY = (float)Math.toRadians(20.0);
        this.spikes[7].addBox(-10.0f, -5.5f, -1.0f, 4, 1, 1);
        this.spikes[7].rotateAngleZ = (float)Math.toRadians(20.0);
        this.spikes[7].rotateAngleY = (float)Math.toRadians(-20.0);
        for (int i = 0; i < this.spikes.length; ++i) {
            super.bipedHead.addChild(this.spikes[i]);
        }
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
}
