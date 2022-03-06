// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.util.IIcon;
import lotr.common.entity.LOTRPlateFallingInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.tileentity.TileEntity;
import java.util.Random;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderPlateFood extends TileEntitySpecialRenderer
{
    private Random rand;
    
    public LOTRRenderPlateFood() {
        this.rand = new Random();
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
        final ItemStack plateItem = plate.getFoodItem();
        final LOTRPlateFallingInfo fallInfo = plate.plateFallInfo;
        final float plateFallOffset = (fallInfo == null) ? 0.0f : fallInfo.getPlateOffsetY(f);
        if (plateItem != null) {
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glEnable(32826);
            GL11.glTranslatef((float)d + 0.5f, (float)d1, (float)d2 + 0.5f);
            this.bindTexture(TextureMap.locationItemsTexture);
            final IIcon icon = plateItem.getIconIndex();
            final Tessellator tessellator = Tessellator.instance;
            final float f2 = icon.getMinU();
            final float f3 = icon.getMaxU();
            final float f4 = icon.getMinV();
            final float f5 = icon.getMaxV();
            final int foods = plateItem.stackSize;
            float lowerOffset = 0.125f;
            for (int l = 0; l < foods; ++l) {
                GL11.glPushMatrix();
                float offset = 0.0f;
                if (fallInfo != null) {
                    offset = fallInfo.getFoodOffsetY(l, f);
                }
                offset = Math.max(offset, lowerOffset);
                GL11.glTranslatef(0.0f, offset, 0.0f);
                lowerOffset = offset + 0.03125f;
                this.rand.setSeed((long)(plate.xCoord * 3129871) ^ plate.zCoord * 116129781L ^ plate.yCoord + l * 5930563L);
                final float rotation = this.rand.nextFloat() * 360.0f;
                GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(-0.25f, -0.25f, 0.0f);
                GL11.glScalef(0.5625f, 0.5625f, 0.5625f);
                ItemRenderer.renderItemIn2D(tessellator, f3, f4, f2, f5, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
                GL11.glPopMatrix();
            }
            GL11.glDisable(32826);
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }
    }
}
