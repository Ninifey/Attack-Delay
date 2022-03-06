// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import net.minecraft.world.World;
import lotr.common.LOTRDate;
import net.minecraft.util.Vec3;
import com.google.common.math.IntMath;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GLContext;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.util.glu.Project;
import lotr.client.LOTRReflectionClient;
import org.lwjgl.opengl.GL11;
import lotr.common.LOTRConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.client.multiplayer.WorldClient;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

public class LOTRCloudRenderer extends IRenderHandler
{
    public static final ResourceLocation cloudTexture;
    private static int cloudRange;
    private static Random cloudRand;
    private static CloudProperty cloudOpacity;
    private static CloudProperty cloudSpeed;
    private static CloudProperty cloudAngle;
    private static double cloudPosXPre;
    private static double cloudPosX;
    private static double cloudPosZPre;
    private static double cloudPosZ;
    
    public static void updateClouds(final WorldClient world) {
        LOTRCloudRenderer.cloudOpacity.update(world);
        LOTRCloudRenderer.cloudSpeed.update(world);
        LOTRCloudRenderer.cloudAngle.update(world);
        final float angle = LOTRCloudRenderer.cloudAngle.getValue(1.0f);
        final float speed = LOTRCloudRenderer.cloudSpeed.getValue(1.0f);
        LOTRCloudRenderer.cloudPosXPre = LOTRCloudRenderer.cloudPosX;
        LOTRCloudRenderer.cloudPosX += MathHelper.cos(angle) * speed;
        LOTRCloudRenderer.cloudPosZPre = LOTRCloudRenderer.cloudPosZ;
        LOTRCloudRenderer.cloudPosZ += MathHelper.sin(angle) * speed;
    }
    
    public static void resetClouds() {
        LOTRCloudRenderer.cloudOpacity.reset();
        LOTRCloudRenderer.cloudSpeed.reset();
        LOTRCloudRenderer.cloudAngle.reset();
    }
    
    public void render(final float partialTicks, final WorldClient world, final Minecraft mc) {
        if (((World)world).provider.isSurfaceWorld()) {
            ((World)world).theProfiler.startSection("lotrClouds");
            LOTRCloudRenderer.cloudRange = LOTRConfig.cloudRange;
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            final float fov = LOTRReflectionClient.getFOVModifier(mc.entityRenderer, partialTicks, true);
            Project.gluPerspective(fov, mc.displayWidth / (float)mc.displayHeight, 0.05f, (float)LOTRCloudRenderer.cloudRange);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GL11.glEnable(3008);
            final float alphaFunc = GL11.glGetFloat(3010);
            GL11.glAlphaFunc(516, 0.01f);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, LOTRCloudRenderer.cloudRange * 0.9f);
            GL11.glFogf(2916, (float)LOTRCloudRenderer.cloudRange);
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            final Tessellator tessellator = Tessellator.instance;
            mc.renderEngine.bindTexture(LOTRCloudRenderer.cloudTexture);
            final Vec3 cloudColor = world.getCloudColour(partialTicks);
            float r = (float)cloudColor.xCoord;
            float g = (float)cloudColor.yCoord;
            float b = (float)cloudColor.zCoord;
            if (mc.gameSettings.anaglyph) {
                final float r2 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
                final float g2 = (r * 30.0f + g * 70.0f) / 100.0f;
                final float b2 = (r * 30.0f + b * 70.0f) / 100.0f;
                r = r2;
                g = g2;
                b = b2;
            }
            final Vec3 pos = mc.renderViewEntity.getPosition(partialTicks);
            for (int pass = 0; pass < 2; ++pass) {
                final int scale = 4096 * IntMath.pow(2, pass);
                final double invScaleD = 1.0 / scale;
                double posX = pos.xCoord;
                double posZ = pos.zCoord;
                final double posY = pos.yCoord;
                double cloudPosXAdd = LOTRCloudRenderer.cloudPosXPre + (LOTRCloudRenderer.cloudPosX - LOTRCloudRenderer.cloudPosXPre) * partialTicks;
                double cloudPosZAdd = LOTRCloudRenderer.cloudPosZPre + (LOTRCloudRenderer.cloudPosZ - LOTRCloudRenderer.cloudPosZPre) * partialTicks;
                cloudPosXAdd /= pass + 1;
                cloudPosZAdd /= pass + 1;
                posX += cloudPosXAdd;
                posZ += cloudPosZAdd;
                final int x = MathHelper.floor_double(posX / scale);
                final int z = MathHelper.floor_double(posZ / scale);
                final double cloudX = posX - x * scale;
                final double cloudZ = posZ - z * scale;
                final double cloudY = ((World)world).provider.getCloudHeight() - posY + 0.33000001311302185 + pass * 50.0f;
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(r, g, b, (0.8f - pass * 0.5f) * LOTRCloudRenderer.cloudOpacity.getValue(partialTicks));
                for (int interval = LOTRCloudRenderer.cloudRange, i = -LOTRCloudRenderer.cloudRange; i < LOTRCloudRenderer.cloudRange; i += interval) {
                    for (int k = -LOTRCloudRenderer.cloudRange; k < LOTRCloudRenderer.cloudRange; k += interval) {
                        final int xMin = i + 0;
                        final int xMax = i + interval;
                        final int zMin = k + 0;
                        final int zMax = k + interval;
                        final double uMin = (xMin + cloudX) * invScaleD;
                        final double uMax = (xMax + cloudX) * invScaleD;
                        final double vMin = (zMin + cloudZ) * invScaleD;
                        final double vMax = (zMax + cloudZ) * invScaleD;
                        tessellator.addVertexWithUV((double)xMin, cloudY, (double)zMax, uMin, vMax);
                        tessellator.addVertexWithUV((double)xMax, cloudY, (double)zMax, uMax, vMax);
                        tessellator.addVertexWithUV((double)xMax, cloudY, (double)zMin, uMax, vMin);
                        tessellator.addVertexWithUV((double)xMin, cloudY, (double)zMin, uMin, vMin);
                    }
                }
                tessellator.draw();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2884);
            GL11.glDepthMask(true);
            GL11.glAlphaFunc(516, alphaFunc);
            GL11.glDisable(3042);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            ((World)world).theProfiler.endSection();
        }
    }
    
    static {
        cloudTexture = new ResourceLocation("lotr:sky/clouds.png");
        LOTRCloudRenderer.cloudRand = new Random();
        LOTRCloudRenderer.cloudOpacity = new CloudProperty(233591206262L, 0.1f, 1.0f, 0.001f);
        LOTRCloudRenderer.cloudSpeed = new CloudProperty(6283905602629L, 0.0f, 0.5f, 0.001f);
        LOTRCloudRenderer.cloudAngle = new CloudProperty(360360635650636L, 0.0f, 6.2831855f, 0.01f);
    }
    
    private static class CloudProperty
    {
        private final long baseSeed;
        private float currentDayValue;
        private float value;
        private float prevValue;
        private final float minValue;
        private final float maxValue;
        private final float interval;
        
        public CloudProperty(final long l, final float min, final float max, final float i) {
            this.baseSeed = l;
            this.value = -1.0f;
            this.minValue = min;
            this.maxValue = max;
            this.interval = i;
        }
        
        public void reset() {
            this.value = -1.0f;
        }
        
        public float getValue(final float f) {
            return this.prevValue + (this.value - this.prevValue) * f;
        }
        
        public void update(final WorldClient world) {
            this.currentDayValue = this.getCurrentDayValue(world);
            if (this.value == -1.0f) {
                final float currentDayValue = this.currentDayValue;
                this.value = currentDayValue;
                this.prevValue = currentDayValue;
            }
            else {
                this.prevValue = this.value;
                if (this.value > this.currentDayValue) {
                    this.value -= this.interval;
                    this.value = Math.max(this.value, this.currentDayValue);
                }
                else if (this.value < this.currentDayValue) {
                    this.value += this.interval;
                    this.value = Math.min(this.value, this.currentDayValue);
                }
            }
        }
        
        private float getCurrentDayValue(final WorldClient world) {
            final int day = LOTRDate.ShireReckoning.currentDay;
            final long seed = day * this.baseSeed + day + 83025820626792L;
            LOTRCloudRenderer.cloudRand.setSeed(seed);
            final float f = MathHelper.randomFloatClamp(LOTRCloudRenderer.cloudRand, this.minValue, this.maxValue);
            return f;
        }
    }
}
