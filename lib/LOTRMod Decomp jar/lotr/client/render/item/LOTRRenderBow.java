// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemBow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderBow implements IItemRenderer
{
    private LOTRRenderLargeItem largeItemRenderer;
    public static boolean renderingWeaponRack;
    
    public LOTRRenderBow(final LOTRRenderLargeItem large) {
        this.largeItemRenderer = large;
    }
    
    public boolean isLargeBow() {
        return this.largeItemRenderer != null;
    }
    
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        GL11.glPushMatrix();
        final EntityLivingBase entity = (EntityLivingBase)data[1];
        if (!LOTRRenderBow.renderingWeaponRack && (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 || entity != Minecraft.getMinecraft().thePlayer)) {
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
            GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
            GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.625f, -0.625f, 0.625f);
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3f, 0.0f);
            GL11.glScalef(1.5f, 1.5f, 1.5f);
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
        }
        if (this.largeItemRenderer != null) {
            final Item item = itemstack.getItem();
            if (!(item instanceof LOTRItemBow)) {
                throw new RuntimeException("Attempting to render a large bow which is not a bow");
            }
            final LOTRItemBow bow = (LOTRItemBow)item;
            LOTRItemBow.BowState bowState = LOTRItemBow.BowState.HELD;
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)entity;
                final ItemStack usingItem = entityplayer.getItemInUse();
                final int useCount = entityplayer.getItemInUseCount();
                bowState = bow.getBowState((EntityLivingBase)entityplayer, usingItem, useCount);
            }
            else {
                bowState = bow.getBowState(entity, itemstack, 0);
            }
            if (bowState == LOTRItemBow.BowState.HELD) {
                this.largeItemRenderer.renderLargeItem();
            }
            else {
                this.largeItemRenderer.renderLargeItem(bowState.iconName);
            }
        }
        else {
            IIcon icon = ((EntityLivingBase)data[1]).getItemIcon(itemstack, 0);
            icon = RenderBlocks.getInstance().getIconSafe(icon);
            final float minU = icon.getMinU();
            final float maxU = icon.getMaxU();
            final float minV = icon.getMinV();
            final float maxV = icon.getMaxV();
            final int width = icon.getIconWidth();
            final int height = icon.getIconWidth();
            final Tessellator tessellator = Tessellator.instance;
            ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
        }
        if (itemstack != null && itemstack.hasEffect(0)) {
            LOTRClientProxy.renderEnchantmentEffect();
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderBow.renderingWeaponRack = false;
    }
}
