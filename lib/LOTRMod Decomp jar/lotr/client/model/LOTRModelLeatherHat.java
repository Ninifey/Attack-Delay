// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.init.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.item.LOTRItemLeatherHat;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class LOTRModelLeatherHat extends LOTRModelBiped
{
    private static ItemStack feather;
    private ItemStack hatItem;
    
    public LOTRModelLeatherHat() {
        this(0.0f);
    }
    
    public LOTRModelLeatherHat(final float f) {
        super(f);
        (super.bipedHead = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        super.bipedHead.addBox(-6.0f, -9.0f, -6.0f, 12, 2, 12, f);
        super.bipedHead.setTextureOffset(0, 14).addBox(-4.0f, -13.0f, -4.0f, 8, 4, 8, f);
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
        final int hatColor = LOTRItemLeatherHat.getHatColor(this.hatItem);
        float r = (hatColor >> 16 & 0xFF) / 255.0f;
        float g = (hatColor >> 8 & 0xFF) / 255.0f;
        float b = (hatColor & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);
        super.bipedHead.render(f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        if (LOTRItemLeatherHat.hasFeather(this.hatItem)) {
            super.bipedHead.postRender(f5);
            GL11.glScalef(0.375f, 0.375f, 0.375f);
            GL11.glRotatef(130.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.25f, 1.5f, 0.75f);
            GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
            final int featherColor = LOTRItemLeatherHat.getFeatherColor(this.hatItem);
            r = (featherColor >> 16 & 0xFF) / 255.0f;
            g = (featherColor >> 8 & 0xFF) / 255.0f;
            b = (featherColor & 0xFF) / 255.0f;
            GL11.glColor3f(r, g, b);
            if (entity instanceof EntityLivingBase) {
                RenderManager.instance.itemRenderer.renderItem((EntityLivingBase)entity, LOTRModelLeatherHat.feather, 0);
            }
            else {
                RenderManager.instance.itemRenderer.renderItem((EntityLivingBase)Minecraft.getMinecraft().thePlayer, LOTRModelLeatherHat.feather, 0);
            }
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
        GL11.glPopMatrix();
    }
    
    static {
        LOTRModelLeatherHat.feather = new ItemStack(Items.feather);
    }
}
