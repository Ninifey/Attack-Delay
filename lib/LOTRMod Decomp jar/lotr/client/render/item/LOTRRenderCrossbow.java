// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.IIcon;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderCrossbow implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        RotationMode rotationMode = null;
        final EntityLivingBase holder = (EntityLivingBase)data[1];
        final boolean loaded = LOTRItemCrossbow.isLoaded(itemstack);
        boolean using = false;
        if (holder instanceof EntityPlayer) {
            using = (((EntityPlayer)holder).getItemInUse() == itemstack);
        }
        else if (holder instanceof EntityLiving) {
            using = (((EntityLiving)holder).getHeldItem() == itemstack);
            if (using && holder instanceof LOTREntityNPC) {
                using = ((LOTREntityNPC)holder).clientCombatStance;
            }
        }
        if (LOTRRenderBow.renderingWeaponRack) {
            rotationMode = RotationMode.FIRST_PERSON_HOLDING;
        }
        else if (holder == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
            if (using || loaded) {
                rotationMode = RotationMode.FIRST_PERSON_LOADED;
            }
            else {
                rotationMode = RotationMode.FIRST_PERSON_HOLDING;
            }
        }
        else {
            if (using || loaded) {
                rotationMode = RotationMode.ENTITY_LOADED;
            }
            else {
                rotationMode = RotationMode.ENTITY_HOLDING;
            }
            GL11.glTranslatef(0.9375f, 0.0625f, 0.0f);
            GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.6666667f, 0.6666667f, 0.6666667f);
            GL11.glTranslatef(0.0f, 0.3f, 0.0f);
            GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-60.0f, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(2.6666667f, 2.6666667f, 2.6666667f);
            GL11.glTranslatef(-0.25f, -0.1875f, 0.1875f);
        }
        if (rotationMode == RotationMode.FIRST_PERSON_LOADED) {
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-60.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-25.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.0f, -0.5f);
        }
        else if (rotationMode == RotationMode.ENTITY_HOLDING) {
            GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
            GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.625f, -0.625f, 0.625f);
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3f, 0.0f);
            GL11.glScalef(1.625f, 1.625f, 1.625f);
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
        }
        else if (rotationMode == RotationMode.ENTITY_LOADED) {
            GL11.glRotatef(50.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.0f, -0.15f);
            GL11.glTranslatef(0.0f, -0.5f, 0.0f);
        }
        final IIcon icon = ((EntityLivingBase)data[1]).getItemIcon(itemstack, 0);
        if (icon == null) {
            GL11.glPopMatrix();
            return;
        }
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        final Tessellator tessellator = Tessellator.instance;
        final float f = icon.getMinU();
        final float f2 = icon.getMaxU();
        final float f3 = icon.getMinV();
        final float f4 = icon.getMaxV();
        ItemRenderer.renderItemIn2D(tessellator, f2, f3, f, f4, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        if (itemstack != null && itemstack.hasEffect(0)) {
            LOTRClientProxy.renderEnchantmentEffect();
        }
        GL11.glDisable(32826);
    }
    
    private enum RotationMode
    {
        FIRST_PERSON_HOLDING, 
        FIRST_PERSON_LOADED, 
        ENTITY_HOLDING, 
        ENTITY_LOADED;
    }
}
