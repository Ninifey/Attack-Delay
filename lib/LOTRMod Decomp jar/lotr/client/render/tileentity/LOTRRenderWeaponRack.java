// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import lotr.client.render.item.LOTRRenderBow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import net.minecraft.tileentity.TileEntity;
import lotr.client.model.LOTRModelWeaponRack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderWeaponRack extends TileEntitySpecialRenderer
{
    private static ResourceLocation rackTexture;
    private static LOTRModelWeaponRack rackModel;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityWeaponRack weaponRack = (LOTRTileEntityWeaponRack)tileentity;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 1.5f, (float)d2 + 0.5f);
        final int meta = weaponRack.getBlockMetadata();
        final int dir = meta & 0x3;
        final boolean wall = (meta & 0x4) != 0x0;
        switch (dir) {
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
        if (wall) {
            GL11.glTranslatef(0.0f, 0.375f, -0.5f);
        }
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        final float scale = 0.0625f;
        this.bindTexture(LOTRRenderWeaponRack.rackTexture);
        LOTRRenderWeaponRack.rackModel.onWall = wall;
        LOTRRenderWeaponRack.rackModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        final ItemStack weaponItem = weaponRack.getWeaponItem();
        if (weaponItem != null) {
            final float weaponScale = 0.625f;
            GL11.glScalef(weaponScale, weaponScale, weaponScale);
            GL11.glScalef(-1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.52f, 0.0f);
            if (wall) {
                GL11.glTranslatef(0.0f, 1.1f, 0.51f);
            }
            GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.9375f, 0.0625f, 0.0f);
            GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.6666667f, 0.6666667f, 0.6666667f);
            GL11.glTranslatef(0.0f, 0.3f, 0.0f);
            final RenderManager renderManager = RenderManager.instance;
            int passes = 1;
            if (weaponItem.getItem().requiresMultipleRenderPasses()) {
                passes = weaponItem.getItem().getRenderPasses(weaponItem.getItemDamage());
            }
            LOTRRenderBow.renderingWeaponRack = true;
            for (int pass = 0; pass < passes; ++pass) {
                final int color = weaponItem.getItem().getColorFromItemStack(weaponItem, pass);
                final float r = (color >> 16 & 0xFF) / 255.0f;
                final float g = (color >> 8 & 0xFF) / 255.0f;
                final float b = (color & 0xFF) / 255.0f;
                GL11.glColor4f(r, g, b, 1.0f);
                renderManager.itemRenderer.renderItem(weaponRack.getEntityForRender(), weaponItem, 0, IItemRenderer.ItemRenderType.EQUIPPED);
            }
            LOTRRenderBow.renderingWeaponRack = false;
        }
        GL11.glEnable(2884);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        this.renderWeaponName(weaponRack, d + 0.5, d1 + 0.75, d2 + 0.5);
    }
    
    private void renderWeaponName(final LOTRTileEntityWeaponRack rack, final double d, final double d1, final double d2) {
        final MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
        if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mop.blockX == rack.xCoord && mop.blockY == rack.yCoord && mop.blockZ == rack.zCoord) {
            final ItemStack weaponItem = rack.getWeaponItem();
            if (Minecraft.isGuiEnabled() && weaponItem != null && weaponItem.hasDisplayName()) {
                final RenderManager renderManager = RenderManager.instance;
                final FontRenderer fontRenderer = this.func_147498_b();
                final float f = 1.6f;
                final float f2 = 0.016666668f * f;
                final double dSq = renderManager.livingPlayer.getDistanceSq(rack.xCoord + 0.5, rack.yCoord + 0.5, (double)rack.zCoord);
                final float f3 = 64.0f;
                if (dSq < f3 * f3) {
                    final String name = weaponItem.getDisplayName();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)d, (float)d1 + 0.5f, (float)d2);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GL11.glScalef(-f2, -f2, f2);
                    GL11.glDisable(2896);
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929);
                    GL11.glEnable(3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    final Tessellator tessellator = Tessellator.instance;
                    final byte b0 = 0;
                    GL11.glDisable(3553);
                    tessellator.startDrawingQuads();
                    final int j = fontRenderer.getStringWidth(name) / 2;
                    tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                    tessellator.addVertex((double)(-j - 1), (double)(-1 + b0), 0.0);
                    tessellator.addVertex((double)(-j - 1), (double)(8 + b0), 0.0);
                    tessellator.addVertex((double)(j + 1), (double)(8 + b0), 0.0);
                    tessellator.addVertex((double)(j + 1), (double)(-1 + b0), 0.0);
                    tessellator.draw();
                    GL11.glEnable(3553);
                    fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, (int)b0, 553648127);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, (int)b0, -1);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                }
            }
        }
    }
    
    static {
        LOTRRenderWeaponRack.rackTexture = new ResourceLocation("lotr:item/weaponRack.png");
        LOTRRenderWeaponRack.rackModel = new LOTRModelWeaponRack();
    }
}
