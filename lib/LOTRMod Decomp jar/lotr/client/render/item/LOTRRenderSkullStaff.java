// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderSkullStaff implements IItemRenderer
{
    private static ModelBase staffModel;
    private static ResourceLocation staffTexture;
    
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(LOTRRenderSkullStaff.staffTexture);
        if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glRotatef(-70.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
        }
        LOTRRenderSkullStaff.staffModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }
    
    static {
        LOTRRenderSkullStaff.staffModel = new ModelBase() {
            private ModelRenderer staff;
            
            {
                (this.staff = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-0.5f, 8.0f, -6.0f, 1, 1, 28, 0.0f);
                this.staff.addBox(-2.5f, 6.0f, -11.0f, 5, 5, 5, 0.0f);
                this.staff.rotateAngleY = (float)Math.toRadians(90.0);
                this.staff.rotateAngleZ = (float)Math.toRadians(-20.0);
            }
            
            public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
                this.staff.render(f5);
            }
        };
        LOTRRenderSkullStaff.staffTexture = new ResourceLocation("lotr:item/skullStaff.png");
    }
}
