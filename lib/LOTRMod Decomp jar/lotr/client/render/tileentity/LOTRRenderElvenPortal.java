// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.ResourceLocation;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderElvenPortal extends TileEntitySpecialRenderer
{
    private FloatBuffer floatBuffer;
    private ResourceLocation portalTexture0;
    private ResourceLocation portalTexture1;
    
    public LOTRRenderElvenPortal() {
        this.floatBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.portalTexture0 = new ResourceLocation("lotr:misc/elvenportal_0.png");
        this.portalTexture1 = new ResourceLocation("lotr:misc/elvenportal_1.png");
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final float f2 = tileentity.getWorldObj().getTotalWorldTime() % 16L + f;
        final TileEntityRendererDispatcher field_147501_a = super.field_147501_a;
        final float f3 = (float)TileEntityRendererDispatcher.staticPlayerX + f2 * 0.25f;
        final TileEntityRendererDispatcher field_147501_a2 = super.field_147501_a;
        final float f4 = (float)TileEntityRendererDispatcher.staticPlayerY;
        final TileEntityRendererDispatcher field_147501_a3 = super.field_147501_a;
        final float f5 = (float)TileEntityRendererDispatcher.staticPlayerZ + f2 * 0.25f;
        GL11.glDisable(2896);
        GL11.glColor3f(0.2f, 0.6f, 1.0f);
        final Random random = new Random(31100L);
        final float f6 = 0.75f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f7 = (float)(16 - i);
            float f8 = 0.0625f;
            float f9 = 1.0f / (f7 + 1.0f);
            if (i == 0) {
                this.bindTexture(this.portalTexture0);
                f9 = 0.1f;
                f7 = 65.0f;
                f8 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.bindTexture(this.portalTexture1);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f8 = 0.5f;
            }
            final float f10 = (float)(-(d1 + f6));
            float f11 = f10 + ActiveRenderInfo.objectY;
            final float f12 = f10 + f7 + ActiveRenderInfo.objectY;
            float f13 = f11 / f12;
            f13 += (float)(d1 + f6);
            GL11.glTranslatef(f3, f13, f5);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.getFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.getFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.getFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.getFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
            GL11.glScalef(f8, f8, f8);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-f3, -f5, -f4);
            f11 = f10 + ActiveRenderInfo.objectY;
            GL11.glTranslatef(ActiveRenderInfo.objectX * f7 / f11, ActiveRenderInfo.objectZ * f7 / f11, -f4);
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            f13 = random.nextFloat() * 0.5f + 0.1f;
            float f14 = random.nextFloat() * 0.5f + 0.4f;
            float f15 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f15 = 1.0f;
                f14 = 1.0f;
                f13 = 1.0f;
            }
            tessellator.setColorRGBA_F(f13 * f9, f14 * f9, f15 * f9, 1.0f);
            tessellator.addVertex(d, d1 + f6, d2);
            tessellator.addVertex(d, d1 + f6, d2 + 1.0);
            tessellator.addVertex(d + 1.0, d1 + f6, d2 + 1.0);
            tessellator.addVertex(d + 1.0, d1 + f6, d2);
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnable(2896);
    }
    
    private FloatBuffer getFloatBuffer(final float f, final float f1, final float f2, final float f3) {
        this.floatBuffer.clear();
        this.floatBuffer.put(f).put(f1).put(f2).put(f3);
        this.floatBuffer.flip();
        return this.floatBuffer;
    }
}
