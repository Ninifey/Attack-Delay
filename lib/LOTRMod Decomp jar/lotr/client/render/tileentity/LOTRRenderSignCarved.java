// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.Direction;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import java.util.Map;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderSignCarved extends TileEntitySpecialRenderer
{
    private static Map<IIcon, Integer> iconAverageColors;
    private static Map<IIcon, Integer> iconContrastColors;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntitySignCarved sign = (LOTRTileEntitySignCarved)tileentity;
        final int meta = tileentity.getBlockMetadata();
        final float rotation = Direction.facingToDirection[meta] * 90.0f;
        final float f2 = 0.6666667f;
        final float f3 = 0.016666668f * f2;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 0.75f * f2, (float)d2 + 0.5f);
        GL11.glRotatef(-rotation, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
        GL11.glTranslatef(0.0f, 0.5f * f2, -0.09f * f2);
        GL11.glScalef(f3, -f3, f3);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * f3);
        GL11.glDepthMask(false);
        final FontRenderer fontrenderer = this.func_147498_b();
        final int color = this.getTextColor(sign, f);
        final int signLines = sign.signText.length;
        final int lineHeight = fontrenderer.FONT_HEIGHT + 1;
        int lineBase = -signLines * 5;
        if (signLines > 4) {
            lineBase = -((signLines - 1) * lineHeight) / 2;
        }
        for (int l = 0; l < signLines; ++l) {
            String s = sign.signText[l];
            if (l == sign.lineBeingEdited) {
                s = "> " + s + " <";
            }
            final int lineX = -fontrenderer.getStringWidth(s) / 2;
            final int lineY = lineBase + l * lineHeight;
            fontrenderer.drawString(s, lineX, lineY, color);
        }
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    protected int getTextColor(final LOTRTileEntitySignCarved sign, final float f) {
        return this.getContrastingColor(sign.getOnBlockIcon());
    }
    
    private int getContrastingColor(final IIcon icon) {
        if (LOTRRenderSignCarved.iconContrastColors.containsKey(icon)) {
            return LOTRRenderSignCarved.iconContrastColors.get(icon);
        }
        int color = this.averageIconColor(icon);
        color = this.calculateContrast(color);
        LOTRRenderSignCarved.iconContrastColors.put(icon, color);
        return color;
    }
    
    private int calculateContrast(final int color) {
        final Color c = new Color(color);
        final float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        final float h = hsb[0];
        float s = hsb[1];
        float b = hsb[2];
        s *= 0.5f;
        if (b > 0.5f) {
            b -= 0.5f;
        }
        else {
            b += 0.5f;
        }
        b = MathHelper.clamp_float(b, 0.0f, 1.0f);
        return Color.HSBtoRGB(h, s, b);
    }
    
    private int averageIconColor(final IIcon icon) {
        if (LOTRRenderSignCarved.iconAverageColors.containsKey(icon)) {
            return LOTRRenderSignCarved.iconAverageColors.get(icon);
        }
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            final String iconName = icon.getIconName();
            String iconPath = "";
            String iconBaseName = iconName;
            final int indexP = iconName.indexOf(":");
            if (indexP > 0) {
                iconPath = iconName.substring(0, indexP);
                iconBaseName = iconName.substring(indexP + 1);
            }
            final ResourceLocation res = new ResourceLocation(iconPath, "textures/blocks/" + iconBaseName + ".png");
            final BufferedImage iconImage = ImageIO.read(mc.getResourceManager().getResource(res).getInputStream());
            final int width = iconImage.getWidth();
            final int height = iconImage.getHeight();
            int totalR = 0;
            int totalG = 0;
            int totalB = 0;
            int count = 0;
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    final int rgb = iconImage.getRGB(x, y);
                    final int r = rgb >> 16 & 0xFF;
                    final int g = rgb >> 8 & 0xFF;
                    final int b = rgb >> 0 & 0xFF;
                    totalR += r;
                    totalG += g;
                    totalB += b;
                    ++count;
                }
            }
            final int avgR = totalR / count & 0xFF;
            final int avgG = totalG / count & 0xFF;
            final int avgB = totalB / count & 0xFF;
            final int avgColor = 0xFF000000 | avgR << 16 | avgG << 8 | avgB << 0;
            LOTRRenderSignCarved.iconAverageColors.put(icon, avgColor);
            return avgColor;
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    static {
        LOTRRenderSignCarved.iconAverageColors = new HashMap<IIcon, Integer>();
        LOTRRenderSignCarved.iconContrastColors = new HashMap<IIcon, Integer>();
    }
}
