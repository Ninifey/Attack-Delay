// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.common.item.LOTRItemPartyHat;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class LOTRModelPartyHat extends LOTRModelBiped
{
    private ItemStack hatItem;
    
    public LOTRModelPartyHat() {
        this(0.0f);
    }
    
    public LOTRModelPartyHat(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-4.0f, -14.0f, -4.0f, 8, 8, 8, f);
        super.bipedHeadwear.cubeList.clear();
        super.bipedBody.cubeList.clear();
        super.bipedRightArm.cubeList.clear();
        super.bipedLeftArm.cubeList.clear();
        super.bipedRightLeg.cubeList.clear();
        super.bipedLeftLeg.cubeList.clear();
    }
    
    public void setHatItem(final ItemStack itemstack) {
        this.hatItem = itemstack;
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glPushMatrix();
        final int hatColor = LOTRItemPartyHat.getHatColor(this.hatItem);
        final float r = (hatColor >> 16 & 0xFF) / 255.0f;
        final float g = (hatColor >> 8 & 0xFF) / 255.0f;
        final float b = (hatColor & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);
        super.bipedHead.render(f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
