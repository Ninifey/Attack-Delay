// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import net.minecraft.world.World;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import lotr.client.render.entity.LOTRRandomSkins;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

public class LOTRSkyRenderer extends IRenderHandler
{
    private static final ResourceLocation moonTexture;
    private static final ResourceLocation sunTexture;
    private static final ResourceLocation earendilTexture;
    private LOTRWorldProvider worldProvider;
    private LOTRRandomSkins skyTextures;
    private ResourceLocation currentSkyTexture;
    private int glSkyList;
    private int glSkyList2;
    
    public LOTRSkyRenderer(final LOTRWorldProvider provider) {
        this.worldProvider = provider;
        this.skyTextures = LOTRRandomSkins.loadSkinsList("lotr:sky/night");
        final Tessellator tessellator = Tessellator.instance;
        GL11.glNewList(this.glSkyList = GLAllocation.generateDisplayLists(3), 4864);
        final byte b2 = 64;
        final int i = 256 / b2 + 2;
        float f = 16.0f;
        for (int j = -b2 * i; j <= b2 * i; j += b2) {
            for (int k = -b2 * i; k <= b2 * i; k += b2) {
                tessellator.startDrawingQuads();
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + b2));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + b2));
                tessellator.draw();
            }
        }
        GL11.glEndList();
        GL11.glNewList(this.glSkyList2 = this.glSkyList + 1, 4864);
        f = -16.0f;
        tessellator.startDrawingQuads();
        for (int j = -b2 * i; j <= b2 * i; j += b2) {
            for (int k = -b2 * i; k <= b2 * i; k += b2) {
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + b2));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + b2));
            }
        }
        tessellator.draw();
        GL11.glEndList();
    }
    
    public void render(final float partialTicks, final WorldClient world, final Minecraft mc) {
        ((World)world).theProfiler.startSection("lotrSky");
        boolean renderSkyFeatures = ((World)world).provider.isSurfaceWorld();
        final int i = MathHelper.floor_double(((Entity)mc.renderViewEntity).posX);
        final int k = MathHelper.floor_double(((Entity)mc.renderViewEntity).posZ);
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome && renderSkyFeatures) {
            renderSkyFeatures = ((LOTRBiome)biome).hasSky();
        }
        GL11.glDisable(3553);
        final Vec3 skyColor = world.getSkyColor((Entity)mc.renderViewEntity, partialTicks);
        float skyR = (float)skyColor.xCoord;
        float skyG = (float)skyColor.yCoord;
        float skyB = (float)skyColor.zCoord;
        if (mc.gameSettings.anaglyph) {
            final float newSkyR = (skyR * 30.0f + skyG * 59.0f + skyB * 11.0f) / 100.0f;
            final float newSkyG = (skyR * 30.0f + skyG * 70.0f) / 100.0f;
            final float newSkyB = (skyR * 30.0f + skyB * 70.0f) / 100.0f;
            skyR = newSkyR;
            skyG = newSkyG;
            skyB = newSkyB;
        }
        GL11.glColor3f(skyR, skyG, skyB);
        final Tessellator tessellator = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(2912);
        GL11.glColor3f(skyR, skyG, skyB);
        GL11.glCallList(this.glSkyList);
        GL11.glDisable(2912);
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        RenderHelper.disableStandardItemLighting();
        final float[] sunrise = ((World)world).provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
        if (sunrise != null) {
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef((MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0f) ? 180.0f : 0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            float r = sunrise[0];
            float g = sunrise[1];
            g *= 1.2f;
            float b = sunrise[2];
            if (mc.gameSettings.anaglyph) {
                final float r2 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
                final float g2 = (r * 30.0f + g * 70.0f) / 100.0f;
                final float b2 = (r * 30.0f + b * 70.0f) / 100.0f;
                r = r2;
                g = g2;
                b = b2;
            }
            tessellator.startDrawing(6);
            tessellator.setColorRGBA_F(r, g, b, sunrise[3]);
            tessellator.addVertex(0.0, 100.0, 0.0);
            tessellator.setColorRGBA_F(sunrise[0], sunrise[1], sunrise[2], 0.0f);
            for (int passes = 16, l = 0; l <= passes; ++l) {
                final float angle = l * 3.1415927f * 2.0f / passes;
                final float sin = MathHelper.sin(angle);
                final float cos = MathHelper.cos(angle);
                tessellator.addVertex((double)(sin * 120.0f), (double)(cos * 120.0f), (double)(-cos * 40.0f * sunrise[3]));
            }
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
        }
        GL11.glPushMatrix();
        if (renderSkyFeatures) {
            GL11.glEnable(3553);
            GL11.glBlendFunc(770, 1);
            final float rainBrightness = 1.0f - world.getRainStrength(partialTicks);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
            final float x = 0.0f;
            final float y = 0.0f;
            final float z = 0.0f;
            GL11.glTranslatef(x, y, z);
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            final float starBrightness = world.getStarBrightness(partialTicks) * rainBrightness;
            if (starBrightness > 0.0f) {
                if (this.currentSkyTexture == null) {
                    this.currentSkyTexture = this.skyTextures.getRandomSkin();
                }
                mc.renderEngine.bindTexture(this.currentSkyTexture);
                GL11.glPushMatrix();
                GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, starBrightness);
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                this.renderSkyboxSide(tessellator, 4);
                GL11.glPushMatrix();
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                this.renderSkyboxSide(tessellator, 1);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                this.renderSkyboxSide(tessellator, 0);
                GL11.glPopMatrix();
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                this.renderSkyboxSide(tessellator, 5);
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                this.renderSkyboxSide(tessellator, 2);
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                this.renderSkyboxSide(tessellator, 3);
                GL11.glPopMatrix();
            }
            else {
                this.currentSkyTexture = null;
            }
            GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
            GL11.glBlendFunc(770, 771);
            mc.renderEngine.bindTexture(LOTRSkyRenderer.sunTexture);
            final float rSun = 10.0f;
            for (int pass = 0; pass <= 1; ++pass) {
                if (pass == 0) {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
                }
                else if (pass == 1) {
                    if (sunrise == null) {
                        continue;
                    }
                    float sunriseBlend = sunrise[3];
                    sunriseBlend *= 0.5f;
                    GL11.glColor4f(1.0f, 0.9f, 0.2f, sunriseBlend * rainBrightness);
                }
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV((double)(-rSun), 100.0, (double)(-rSun), 0.0, 0.0);
                tessellator.addVertexWithUV((double)rSun, 100.0, (double)(-rSun), 1.0, 0.0);
                tessellator.addVertexWithUV((double)rSun, 100.0, (double)rSun, 1.0, 1.0);
                tessellator.addVertexWithUV((double)(-rSun), 100.0, (double)rSun, 0.0, 1.0);
                tessellator.draw();
            }
            GL11.glBlendFunc(770, 1);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
            final LOTRWorldProvider worldProvider = this.worldProvider;
            final int phases = LOTRWorldProvider.MOON_PHASES;
            final LOTRWorldProvider worldProvider2 = this.worldProvider;
            final int moonPhase = LOTRWorldProvider.getLOTRMoonPhase();
            final LOTRWorldProvider worldProvider3 = this.worldProvider;
            final boolean lunarEclipse = LOTRWorldProvider.isLunarEclipse();
            if (lunarEclipse) {
                GL11.glColor3f(1.0f, 0.6f, 0.4f);
            }
            mc.renderEngine.bindTexture(LOTRSkyRenderer.moonTexture);
            final float rMoon = 10.0f;
            final float f14 = moonPhase / (float)phases;
            final float f15 = 0.0f;
            final float f16 = (moonPhase + 1) / (float)phases;
            final float f17 = 1.0f;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)(-rMoon), -100.0, (double)rMoon, (double)f16, (double)f17);
            tessellator.addVertexWithUV((double)rMoon, -100.0, (double)rMoon, (double)f14, (double)f17);
            tessellator.addVertexWithUV((double)rMoon, -100.0, (double)(-rMoon), (double)f14, (double)f15);
            tessellator.addVertexWithUV((double)(-rMoon), -100.0, (double)(-rMoon), (double)f16, (double)f15);
            tessellator.draw();
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            final float celestialAngle = world.getCelestialAngle(partialTicks);
            final float f18 = celestialAngle - 0.5f;
            final float f19 = Math.abs(f18);
            final float eMin = 0.15f;
            final float eMax = 0.3f;
            if (f19 >= eMin && f19 <= eMax) {
                final float eMid = (eMin + eMax) / 2.0f;
                final float eHalfWidth = eMax - eMid;
                float eBright = MathHelper.cos((f19 - eMid) / eHalfWidth * 3.1415927f / 2.0f);
                eBright *= eBright;
                final float eAngle = Math.signum(f18) * 18.0f;
                GL11.glPushMatrix();
                GL11.glRotatef(eAngle, 1.0f, 0.0f, 0.0f);
                GL11.glBlendFunc(770, 1);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, eBright * rainBrightness);
                mc.renderEngine.bindTexture(LOTRSkyRenderer.earendilTexture);
                final float rEarendil = 1.5f;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV((double)(-rEarendil), 100.0, (double)(-rEarendil), 0.0, 0.0);
                tessellator.addVertexWithUV((double)rEarendil, 100.0, (double)(-rEarendil), 1.0, 0.0);
                tessellator.addVertexWithUV((double)rEarendil, 100.0, (double)rEarendil, 1.0, 1.0);
                tessellator.addVertexWithUV((double)(-rEarendil), 100.0, (double)rEarendil, 0.0, 1.0);
                tessellator.draw();
                GL11.glPopMatrix();
            }
            GL11.glDisable(3553);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(2912);
        GL11.glPopMatrix();
        GL11.glDisable(3553);
        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        final double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();
        if (d0 < 0.0) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 12.0f, 0.0f);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            final float f20 = 1.0f;
            final float f21 = -(float)(d0 + 65.0);
            final float f22 = -f20;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0, 255);
            tessellator.addVertex((double)(-f20), (double)f21, (double)f20);
            tessellator.addVertex((double)f20, (double)f21, (double)f20);
            tessellator.addVertex((double)f20, (double)f22, (double)f20);
            tessellator.addVertex((double)(-f20), (double)f22, (double)f20);
            tessellator.addVertex((double)(-f20), (double)f22, (double)(-f20));
            tessellator.addVertex((double)f20, (double)f22, (double)(-f20));
            tessellator.addVertex((double)f20, (double)f21, (double)(-f20));
            tessellator.addVertex((double)(-f20), (double)f21, (double)(-f20));
            tessellator.addVertex((double)f20, (double)f22, (double)(-f20));
            tessellator.addVertex((double)f20, (double)f22, (double)f20);
            tessellator.addVertex((double)f20, (double)f21, (double)f20);
            tessellator.addVertex((double)f20, (double)f21, (double)(-f20));
            tessellator.addVertex((double)(-f20), (double)f21, (double)(-f20));
            tessellator.addVertex((double)(-f20), (double)f21, (double)f20);
            tessellator.addVertex((double)(-f20), (double)f22, (double)f20);
            tessellator.addVertex((double)(-f20), (double)f22, (double)(-f20));
            tessellator.addVertex((double)(-f20), (double)f22, (double)(-f20));
            tessellator.addVertex((double)(-f20), (double)f22, (double)f20);
            tessellator.addVertex((double)f20, (double)f22, (double)f20);
            tessellator.addVertex((double)f20, (double)f22, (double)(-f20));
            tessellator.draw();
        }
        if (((World)world).provider.isSkyColored()) {
            GL11.glColor3f(skyR * 0.2f + 0.04f, skyG * 0.2f + 0.04f, skyB * 0.6f + 0.1f);
        }
        else {
            GL11.glColor3f(skyR, skyG, skyB);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -(float)(d0 - 16.0), 0.0f);
        GL11.glCallList(this.glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDepthMask(true);
        ((World)world).theProfiler.endSection();
    }
    
    private void renderSkyboxSide(final Tessellator tessellator, final int side) {
        final double u = side % 3 / 3.0;
        final double v = side / 3 / 2.0;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-100.0, -100.0, -100.0, u, v);
        tessellator.addVertexWithUV(-100.0, -100.0, 100.0, u, v + 0.5);
        tessellator.addVertexWithUV(100.0, -100.0, 100.0, u + 0.3333333333333333, v + 0.5);
        tessellator.addVertexWithUV(100.0, -100.0, -100.0, u + 0.3333333333333333, v);
        tessellator.draw();
    }
    
    static {
        moonTexture = new ResourceLocation("lotr:sky/moon.png");
        sunTexture = new ResourceLocation("lotr:sky/sun.png");
        earendilTexture = new ResourceLocation("lotr:sky/earendil.png");
    }
}
