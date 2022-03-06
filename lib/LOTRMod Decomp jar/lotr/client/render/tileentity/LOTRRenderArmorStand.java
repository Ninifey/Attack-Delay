// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelArmorStand;
import net.minecraft.item.ItemStack;
import lotr.client.LOTRClientProxy;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.client.renderer.entity.RenderBiped;
import lotr.common.item.LOTRItemPlate;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.EntityLivingBase;
import lotr.client.model.LOTRArmorModels;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderArmorStand extends TileEntitySpecialRenderer
{
    private static ResourceLocation standTexture;
    private static ModelBase standModel;
    private static ModelBiped modelBipedMain;
    private static ModelBiped modelBiped1;
    private static ModelBiped modelBiped2;
    private static float BIPED_ARM_ROTATION;
    private static float BIPED_TICKS_EXISTED;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand)tileentity;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 1.5f, (float)d2 + 0.5f);
        switch (armorStand.getBlockMetadata() & 0x3) {
            case 0: {
                GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 1: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 2: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 3: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
        }
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        final float scale = 0.0625f;
        this.bindTexture(LOTRRenderArmorStand.standTexture);
        LOTRRenderArmorStand.standModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        LOTRArmorModels.INSTANCE.setupModelForRender(LOTRRenderArmorStand.modelBipedMain, null, null);
        GL11.glTranslatef(0.0f, -0.1875f, 0.0f);
        for (int slot = 0; slot < 4; ++slot) {
            final ItemStack itemstack = armorStand.getStackInSlot(slot);
            if (itemstack != null && (itemstack.getItem() instanceof ItemArmor || itemstack.getItem() instanceof LOTRItemPlate)) {
                final boolean isArmor = itemstack.getItem() instanceof ItemArmor;
                if (isArmor) {
                    this.bindTexture(RenderBiped.getArmorResource((Entity)null, itemstack, slot, (String)null));
                }
                ModelBiped armorModel = (slot == 2) ? LOTRRenderArmorStand.modelBiped2 : LOTRRenderArmorStand.modelBiped1;
                LOTRArmorModels.INSTANCE.setupArmorForSlot(armorModel, slot);
                armorModel = ForgeHooksClient.getArmorModel((EntityLivingBase)null, itemstack, slot, armorModel);
                final ModelBiped specialModel = LOTRArmorModels.INSTANCE.getSpecialArmorModel(itemstack, slot, null, LOTRRenderArmorStand.modelBipedMain);
                if (specialModel != null) {
                    armorModel = specialModel;
                }
                LOTRArmorModels.INSTANCE.setupModelForRender(armorModel, null, null);
                float f2 = 1.0f;
                boolean isColoredArmor = false;
                if (isArmor) {
                    final int j = ((ItemArmor)itemstack.getItem()).getColor(itemstack);
                    if (j != -1) {
                        final float f3 = (j >> 16 & 0xFF) / 255.0f;
                        final float f4 = (j >> 8 & 0xFF) / 255.0f;
                        final float f5 = (j & 0xFF) / 255.0f;
                        GL11.glColor3f(f2 * f3, f2 * f4, f2 * f5);
                        isColoredArmor = true;
                    }
                    else {
                        GL11.glColor3f(f2, f2, f2);
                    }
                }
                else {
                    GL11.glColor3f(f2, f2, f2);
                }
                armorModel.render((Entity)null, LOTRRenderArmorStand.BIPED_ARM_ROTATION, 0.0f, LOTRRenderArmorStand.BIPED_TICKS_EXISTED, 0.0f, 0.0f, scale);
                if (isColoredArmor) {
                    this.bindTexture(RenderBiped.getArmorResource((Entity)null, itemstack, slot, "overlay"));
                    f2 = 1.0f;
                    GL11.glColor3f(f2, f2, f2);
                    armorModel.render((Entity)null, LOTRRenderArmorStand.BIPED_ARM_ROTATION, 0.0f, LOTRRenderArmorStand.BIPED_TICKS_EXISTED, 0.0f, 0.0f, scale);
                }
                if (itemstack.isItemEnchanted()) {
                    final float f6 = armorStand.ticksExisted + f;
                    this.bindTexture(LOTRClientProxy.enchantmentTexture);
                    GL11.glEnable(3042);
                    final float f7 = 0.5f;
                    GL11.glColor4f(f7, f7, f7, 1.0f);
                    GL11.glDepthFunc(514);
                    GL11.glDepthMask(false);
                    for (int k = 0; k < 2; ++k) {
                        GL11.glDisable(2896);
                        final float f5 = 0.76f;
                        GL11.glColor4f(0.5f * f5, 0.25f * f5, 0.8f * f5, 1.0f);
                        GL11.glBlendFunc(768, 1);
                        GL11.glMatrixMode(5890);
                        GL11.glLoadIdentity();
                        final float f8 = 0.33333334f;
                        GL11.glScalef(f8, f8, f8);
                        GL11.glRotatef(30.0f - k * 60.0f, 0.0f, 0.0f, 1.0f);
                        final float f9 = f6 * (0.001f + k * 0.003f) * 20.0f;
                        GL11.glTranslatef(0.0f, f9, 0.0f);
                        GL11.glMatrixMode(5888);
                        armorModel.render((Entity)null, LOTRRenderArmorStand.BIPED_ARM_ROTATION, 0.0f, LOTRRenderArmorStand.BIPED_TICKS_EXISTED, 0.0f, 0.0f, scale);
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glMatrixMode(5890);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(5888);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glDepthFunc(515);
                }
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderArmorStand.standTexture = new ResourceLocation("lotr:item/armorStand.png");
        LOTRRenderArmorStand.standModel = new LOTRModelArmorStand();
        LOTRRenderArmorStand.modelBipedMain = new ModelBiped(0.0f);
        LOTRRenderArmorStand.modelBiped1 = new ModelBiped(1.0f);
        LOTRRenderArmorStand.modelBiped2 = new ModelBiped(0.5f);
        LOTRRenderArmorStand.BIPED_ARM_ROTATION = -7.07353f;
        LOTRRenderArmorStand.BIPED_TICKS_EXISTED = 46.88954f;
    }
}
