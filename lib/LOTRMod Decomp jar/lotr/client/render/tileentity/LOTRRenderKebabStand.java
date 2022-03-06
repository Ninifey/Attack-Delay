// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import java.util.HashMap;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import lotr.client.model.LOTRModelKebabStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderKebabStand extends TileEntitySpecialRenderer
{
    private static LOTRModelKebabStand standModel;
    private static Map<String, ResourceLocation> standTextures;
    private static ResourceLocation rawTexture;
    private static ResourceLocation cookedTexture;
    
    private static ResourceLocation getStandTexture(final LOTRTileEntityKebabStand kebabStand) {
        String s = kebabStand.getStandTextureName();
        if (!StringUtils.isNullOrEmpty(s)) {
            s = "_" + s;
        }
        s = "stand" + s;
        ResourceLocation r = LOTRRenderKebabStand.standTextures.get(s);
        if (r == null) {
            r = new ResourceLocation("lotr:item/kebab/" + s + ".png");
            LOTRRenderKebabStand.standTextures.put(s, r);
        }
        return r;
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)tileentity;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 1.5f, (float)d2 + 0.5f);
        final int meta = kebabStand.getBlockMetadata();
        switch (meta) {
            case 2: {
                GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 5: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 3: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 4: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
        }
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        final float scale = 0.0625f;
        this.bindTexture(getStandTexture(kebabStand));
        LOTRRenderKebabStand.standModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        final int meatAmount = kebabStand.getMeatAmount();
        if (meatAmount > 0) {
            final boolean cooked = kebabStand.isCooked();
            if (cooked) {
                this.bindTexture(LOTRRenderKebabStand.cookedTexture);
            }
            else {
                this.bindTexture(LOTRRenderKebabStand.rawTexture);
            }
            final float spin = kebabStand.getKebabSpin(f);
            LOTRRenderKebabStand.standModel.renderKebab(scale, meatAmount, spin);
        }
        GL11.glEnable(2884);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderKebabStand.standModel = new LOTRModelKebabStand();
        LOTRRenderKebabStand.standTextures = new HashMap<String, ResourceLocation>();
        LOTRRenderKebabStand.rawTexture = new ResourceLocation("lotr:item/kebab/raw.png");
        LOTRRenderKebabStand.cookedTexture = new ResourceLocation("lotr:item/kebab/cooked.png");
    }
}
