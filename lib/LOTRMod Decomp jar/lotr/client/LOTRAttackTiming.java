// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.ScaledResolution;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;

public class LOTRAttackTiming
{
    private static Minecraft mc;
    private static ResourceLocation meterTexture;
    private static RenderItem itemRenderer;
    public static int attackTime;
    public static int prevAttackTime;
    public static int fullAttackTime;
    private static ItemStack attackItem;
    private static int lastCheckTick;
    
    public static void doAttackTiming() {
        final int currentTick = LOTRTickHandlerClient.clientTick;
        if (LOTRAttackTiming.lastCheckTick == -1) {
            LOTRAttackTiming.lastCheckTick = currentTick;
        }
        else if (LOTRAttackTiming.lastCheckTick == currentTick) {
            return;
        }
        if (LOTRAttackTiming.mc.thePlayer == null) {
            reset();
        }
        else {
            final KeyBinding attackKey = LOTRAttackTiming.mc.gameSettings.keyBindAttack;
            final boolean pressed = attackKey.isPressed();
            if (pressed) {
                KeyBinding.onTick(attackKey.getKeyCode());
            }
            if (pressed && LOTRAttackTiming.mc.objectMouseOver != null && LOTRAttackTiming.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && LOTRAttackTiming.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                if (LOTRAttackTiming.attackTime > 0) {
                    while (attackKey.isPressed()) {}
                }
                else {
                    final ItemStack itemstack = LOTRAttackTiming.mc.thePlayer.getHeldItem();
                    LOTRAttackTiming.fullAttackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
                    LOTRAttackTiming.attackTime = LOTRAttackTiming.fullAttackTime;
                    LOTRAttackTiming.attackItem = itemstack;
                }
                LOTRAttackTiming.lastCheckTick = currentTick;
            }
        }
    }
    
    public static void update() {
        LOTRAttackTiming.prevAttackTime = LOTRAttackTiming.attackTime;
        if (LOTRAttackTiming.attackTime > 0) {
            --LOTRAttackTiming.attackTime;
        }
        else {
            reset();
        }
    }
    
    public static void reset() {
        LOTRAttackTiming.attackTime = 0;
        LOTRAttackTiming.prevAttackTime = 0;
        LOTRAttackTiming.fullAttackTime = 0;
        LOTRAttackTiming.attackItem = null;
    }
    
    public static void renderAttackMeter(final ScaledResolution resolution, final float partialTicks) {
        if (LOTRAttackTiming.fullAttackTime > 0) {
            float attackTimeF = LOTRAttackTiming.prevAttackTime + (LOTRAttackTiming.attackTime - LOTRAttackTiming.prevAttackTime) * partialTicks;
            attackTimeF /= LOTRAttackTiming.fullAttackTime;
            final float meterAmount = 1.0f - attackTimeF;
            final int minX = resolution.getScaledWidth() / 2 + 120;
            final int maxX = resolution.getScaledWidth() - 20;
            final int maxY = resolution.getScaledHeight() - 10;
            final int minY = maxY - 10;
            final double lerpX = minX + (maxX - minX) * meterAmount;
            final Tessellator tessellator = Tessellator.instance;
            LOTRAttackTiming.mc.getTextureManager().bindTexture(LOTRAttackTiming.meterTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final double minU = 0.0;
            final double maxU = 1.0;
            final double minV = 0.0;
            final double maxV = 0.0625;
            final double lerpU = minU + (maxU - minU) * meterAmount;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)minX, (double)minY, 0.0, minU, minV);
            tessellator.addVertexWithUV((double)minX, (double)maxY, 0.0, minU, maxV);
            tessellator.addVertexWithUV((double)maxX, (double)maxY, 0.0, maxU, maxV);
            tessellator.addVertexWithUV((double)maxX, (double)minY, 0.0, maxU, minV);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(lerpX, (double)minY, 0.0, lerpU, minV + maxV);
            tessellator.addVertexWithUV(lerpX, (double)maxY, 0.0, lerpU, maxV + maxV);
            tessellator.addVertexWithUV((double)maxX, (double)maxY, 0.0, maxU, maxV + maxV);
            tessellator.addVertexWithUV((double)maxX, (double)minY, 0.0, maxU, minV + maxV);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)minX, (double)minY, 0.0, minU, minV + maxV * 2.0);
            tessellator.addVertexWithUV((double)minX, (double)maxY, 0.0, minU, maxV + maxV * 2.0);
            tessellator.addVertexWithUV((double)maxX, (double)maxY, 0.0, maxU, maxV + maxV * 2.0);
            tessellator.addVertexWithUV((double)maxX, (double)minY, 0.0, maxU, minV + maxV * 2.0);
            tessellator.draw();
            if (LOTRAttackTiming.attackItem != null) {
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(32826);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final int iconX = (minX + maxX) / 2 - 8;
                final int iconY = (minY + maxY) / 2 - 8;
                LOTRAttackTiming.itemRenderer.renderItemAndEffectIntoGUI(LOTRAttackTiming.mc.fontRenderer, LOTRAttackTiming.mc.getTextureManager(), LOTRAttackTiming.attackItem, iconX, iconY);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }
    
    static {
        LOTRAttackTiming.mc = Minecraft.getMinecraft();
        LOTRAttackTiming.meterTexture = new ResourceLocation("lotr:gui/attackMeter.png");
        LOTRAttackTiming.itemRenderer = new RenderItem();
        LOTRAttackTiming.lastCheckTick = -1;
    }
}
