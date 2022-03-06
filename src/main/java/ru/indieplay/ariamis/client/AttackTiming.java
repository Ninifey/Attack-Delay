package ru.indieplay.ariamis.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.indieplay.ariamis.common.PacketDamage;
import ru.indieplay.ariamis.common.PacketHandler;
import ru.indieplay.ariamis.common.WeaponStats;

public class AttackTiming {
        private static Minecraft mc;
        private static ResourceLocation meterTexture;
        private static RenderItem itemRenderer;
        public static int attackTime;
        public static int prevAttackTime;
        public static int fullAttackTime;
        private static ItemStack attackItem;
        private static int lastCheckTick;

        public static void doAttackTiming() {
            final int currentTick = TickHandler.clientTick;
            if (AttackTiming.lastCheckTick == -1) {
                AttackTiming.lastCheckTick = currentTick;
            }
            else if (AttackTiming.lastCheckTick == currentTick) {
                return;
            }
            if (AttackTiming.mc.thePlayer == null) {
                reset();
            }
            else {
                final KeyBinding attackKey = AttackTiming.mc.gameSettings.keyBindAttack;
                final boolean pressed = attackKey.isPressed();
                if (pressed) {
                    KeyBinding.onTick(attackKey.getKeyCode());
                }
                if (pressed//){
                        && AttackTiming.mc.objectMouseOver != null
                        && AttackTiming.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
                        && AttackTiming.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {

                    if (AttackTiming.attackTime > 0) {
                        while (attackKey.isPressed()) {}
                    }
                    else {
                        final ItemStack itemstack = AttackTiming.mc.thePlayer.getHeldItem();
                        if(AttackTiming.mc.thePlayer.capabilities.isCreativeMode)
                            AttackTiming.fullAttackTime = 4;
                            else
                        AttackTiming.fullAttackTime = WeaponStats.getAttackTimePlayer(itemstack);
                        AttackTiming.attackTime = AttackTiming.fullAttackTime;
                        AttackTiming.attackItem = itemstack;
                    }
                    AttackTiming.lastCheckTick = currentTick;
                }
                else if(pressed){
                    float baseReach = 3.0f;
                    final ItemStack itemstack = AttackTiming.mc.thePlayer.getHeldItem();
                    float reach = WeaponStats.getMeleeReachFactor(itemstack)*baseReach;
                    MovingObjectPosition op = SwingHandler.getMouseOver(0, reach);

                    if(op!=null && op.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
                    Entity target = op.entityHit;
                    EntityPlayer entityPlayer = mc.thePlayer;
                    if (entityPlayer!=null && target instanceof EntityLiving && target != entityPlayer) {
                        if (target.hurtResistantTime != ((EntityLiving) target).maxHurtResistantTime) {
                            String player = entityPlayer.getDisplayName();
                            int id = target.getEntityId();
                            if(id > 0 && player != null) {


                                if (AttackTiming.attackTime > 0) {
                                    while (attackKey.isPressed()) { }
                                } else {
                                    PacketHandler.INSTANCE.sendToServer(new PacketDamage(id, player));
                                    if (AttackTiming.mc.thePlayer.capabilities.isCreativeMode) AttackTiming.fullAttackTime = 4;
                                    else AttackTiming.fullAttackTime = WeaponStats.getAttackTimePlayer(AttackTiming.mc.thePlayer.getHeldItem());
                                    AttackTiming.attackTime = AttackTiming.fullAttackTime;
                                    AttackTiming.attackItem = AttackTiming.mc.thePlayer.getHeldItem();
                                }
                                AttackTiming.lastCheckTick = currentTick;
                            }
                        }
                    }
                    }
                }
            }
        }

        public static void update() {
            AttackTiming.prevAttackTime = AttackTiming.attackTime;
            if (AttackTiming.attackTime > 0) {
                --AttackTiming.attackTime;
            }
            else {
                reset();
            }
        }

        public static void reset() {
            AttackTiming.attackTime = 0;
            AttackTiming.prevAttackTime = 0;
            AttackTiming.fullAttackTime = 0;
            AttackTiming.attackItem = null;
        }

        public static void renderAttackMeter(final ScaledResolution resolution, final float partialTicks) {
            if (AttackTiming.fullAttackTime > 0) {
                float attackTimeF = AttackTiming.prevAttackTime + (AttackTiming.attackTime - AttackTiming.prevAttackTime) * partialTicks;
                attackTimeF /= AttackTiming.fullAttackTime;
                final float meterAmount = 1.0f - attackTimeF;
                final int minX = resolution.getScaledWidth() / 2 - 80;
                final int maxX = resolution.getScaledWidth()/2 + 80;
                final int maxY = resolution.getScaledHeight() - 45;
                final int minY = maxY - 10;
                final double lerpX = minX + (maxX - minX) * meterAmount;
                final Tessellator tessellator = Tessellator.instance;
                AttackTiming.mc.getTextureManager().bindTexture(AttackTiming.meterTexture);
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
                if (AttackTiming.attackItem != null) {
                    RenderHelper.enableGUIStandardItemLighting();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glEnable(32826);
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    final int iconX = (minX + maxX) / 2 - 8;
                    final int iconY = (minY + maxY) / 2 - 8;
                    AttackTiming.itemRenderer.renderItemAndEffectIntoGUI(AttackTiming.mc.fontRenderer, AttackTiming.mc.getTextureManager(), AttackTiming.attackItem, iconX, iconY);
                    RenderHelper.disableStandardItemLighting();
                }
            }
        }

        static {
            AttackTiming.mc = Minecraft.getMinecraft();
            AttackTiming.meterTexture = new ResourceLocation("attackdelay","textures/gui/attackMeter.png");
            AttackTiming.itemRenderer = new RenderItem();
            AttackTiming.lastCheckTick = -1;
        }
    

}
