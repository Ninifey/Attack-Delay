// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelGlassBottle;
import lotr.client.model.LOTRModelWineGlass;
import lotr.client.model.LOTRModelSkullCup;
import lotr.client.model.LOTRModelGoblet;
import lotr.client.model.LOTRModelMug;
import lotr.client.render.LOTRRenderBlocks;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.texture.TextureMap;
import lotr.common.item.LOTRItemMug;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.RenderBlocks;
import lotr.client.model.LOTRModelAleHorn;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderMug extends TileEntitySpecialRenderer
{
    private static ResourceLocation mugTexture;
    private static ResourceLocation mugClayTexture;
    private static ResourceLocation gobletGoldTexture;
    private static ResourceLocation gobletSilverTexture;
    private static ResourceLocation gobletCopperTexture;
    private static ResourceLocation gobletWoodTexture;
    private static ResourceLocation skullTexture;
    private static ResourceLocation glassTexture;
    private static ResourceLocation bottleTexture;
    private static ResourceLocation hornTexture;
    private static ResourceLocation hornGoldTexture;
    private static ModelBase mugModel;
    private static ModelBase gobletModel;
    private static ModelBase skullModel;
    private static ModelBase glassModel;
    private static ModelBase bottleModel;
    private static LOTRModelAleHorn hornModel;
    private static RenderBlocks renderBlocks;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityMug mug = (LOTRTileEntityMug)tileentity;
        final ItemStack mugItemstack = mug.getMugItemForRender();
        final Item mugItem = mugItemstack.getItem();
        final boolean full = !mug.isEmpty();
        final LOTRItemMug.Vessel vessel = mug.getVessel();
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5f, (float)d1, (float)d2 + 0.5f);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        final float mugScale = 0.75f;
        GL11.glScalef(mugScale, mugScale, mugScale);
        final float scale = 0.0625f;
        switch (mug.getBlockMetadata()) {
            case 0: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 1: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 2: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 3: {
                GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
        }
        if (vessel == LOTRItemMug.Vessel.SKULL || vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        }
        if (full) {
            GL11.glDisable(2896);
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationItemsTexture);
            final IIcon liquidIcon = mugItem.getIconFromDamage(-1);
            if (vessel == LOTRItemMug.Vessel.MUG || vessel == LOTRItemMug.Vessel.MUG_CLAY) {
                this.renderMeniscus(liquidIcon, 6, 10, 2.0, 7.0, scale);
            }
            else if (vessel == LOTRItemMug.Vessel.GOBLET_GOLD || vessel == LOTRItemMug.Vessel.GOBLET_SILVER || vessel == LOTRItemMug.Vessel.GOBLET_COPPER || vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
                this.renderMeniscus(liquidIcon, 6, 9, 1.5, 8.0, scale);
            }
            else if (vessel == LOTRItemMug.Vessel.SKULL) {
                this.renderMeniscus(liquidIcon, 5, 11, 3.0, 9.0, scale);
            }
            else if (vessel == LOTRItemMug.Vessel.GLASS) {
                this.renderLiquid(liquidIcon, 6, 9, 6.0, 9.0, scale);
            }
            else if (vessel == LOTRItemMug.Vessel.BOTTLE) {
                this.renderLiquid(liquidIcon, 6, 10, 1.0, 5.0, scale);
            }
            else if (vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
                LOTRRenderMug.hornModel.prepareLiquid(scale);
                this.renderMeniscus(liquidIcon, 6, 9, -1.5, 5.0, scale);
            }
            GL11.glPopMatrix();
            GL11.glEnable(2896);
        }
        GL11.glPushMatrix();
        ModelBase model = null;
        if (vessel == LOTRItemMug.Vessel.MUG) {
            this.bindTexture(LOTRRenderMug.mugTexture);
            model = LOTRRenderMug.mugModel;
        }
        else if (vessel == LOTRItemMug.Vessel.MUG_CLAY) {
            this.bindTexture(LOTRRenderMug.mugClayTexture);
            model = LOTRRenderMug.mugModel;
        }
        else if (vessel == LOTRItemMug.Vessel.GOBLET_GOLD) {
            this.bindTexture(LOTRRenderMug.gobletGoldTexture);
            model = LOTRRenderMug.gobletModel;
        }
        else if (vessel == LOTRItemMug.Vessel.GOBLET_SILVER) {
            this.bindTexture(LOTRRenderMug.gobletSilverTexture);
            model = LOTRRenderMug.gobletModel;
        }
        else if (vessel == LOTRItemMug.Vessel.GOBLET_COPPER) {
            this.bindTexture(LOTRRenderMug.gobletCopperTexture);
            model = LOTRRenderMug.gobletModel;
        }
        else if (vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
            this.bindTexture(LOTRRenderMug.gobletWoodTexture);
            model = LOTRRenderMug.gobletModel;
        }
        else if (vessel == LOTRItemMug.Vessel.SKULL) {
            this.bindTexture(LOTRRenderMug.skullTexture);
            model = LOTRRenderMug.skullModel;
        }
        else if (vessel == LOTRItemMug.Vessel.GLASS) {
            this.bindTexture(LOTRRenderMug.glassTexture);
            model = LOTRRenderMug.glassModel;
            GL11.glEnable(2884);
        }
        else if (vessel == LOTRItemMug.Vessel.BOTTLE) {
            this.bindTexture(LOTRRenderMug.bottleTexture);
            model = LOTRRenderMug.bottleModel;
            GL11.glEnable(2884);
        }
        else if (vessel == LOTRItemMug.Vessel.HORN) {
            this.bindTexture(LOTRRenderMug.hornTexture);
            model = LOTRRenderMug.hornModel;
        }
        else if (vessel == LOTRItemMug.Vessel.HORN_GOLD) {
            this.bindTexture(LOTRRenderMug.hornGoldTexture);
            model = LOTRRenderMug.hornModel;
        }
        if (model != null) {
            model.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glDisable(32826);
    }
    
    private void renderMeniscus(final IIcon icon, final int uvMin, final int uvMax, double width, double height, final float scale) {
        final float minU = icon.getInterpolatedU((double)uvMin);
        final float maxU = icon.getInterpolatedU((double)uvMax);
        final float minV = icon.getInterpolatedV((double)uvMin);
        final float maxV = icon.getInterpolatedV((double)uvMax);
        width *= scale;
        height *= scale;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-width, -height, width, (double)minU, (double)maxV);
        tessellator.addVertexWithUV(width, -height, width, (double)maxU, (double)maxV);
        tessellator.addVertexWithUV(width, -height, -width, (double)maxU, (double)minV);
        tessellator.addVertexWithUV(-width, -height, -width, (double)minU, (double)minV);
        tessellator.draw();
    }
    
    private void renderLiquid(final IIcon icon, final int uvMin, final int uvMax, double yMin, double yMax, final float scale) {
        final double edge = 0.001;
        double xzMin = uvMin * (double)scale;
        double xzMax = uvMax * (double)scale;
        xzMin += edge;
        xzMax -= edge;
        final float dxz = 0.5f - (uvMin + uvMax) / 2.0f * scale;
        yMin = 16.0 - yMin;
        yMax = 16.0 - yMax;
        yMin *= scale;
        yMax *= scale;
        yMin += edge;
        yMax -= edge;
        GL11.glPushMatrix();
        GL11.glTranslatef(dxz, -0.5f, dxz);
        LOTRRenderMug.renderBlocks.setOverrideBlockTexture(icon);
        LOTRRenderBlocks.renderStandardInvBlock(LOTRRenderMug.renderBlocks, LOTRMod.mugBlock, xzMin, yMax, xzMin, xzMax, yMin, xzMax);
        LOTRRenderMug.renderBlocks.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderMug.mugTexture = new ResourceLocation("lotr:item/mug.png");
        LOTRRenderMug.mugClayTexture = new ResourceLocation("lotr:item/mugClay.png");
        LOTRRenderMug.gobletGoldTexture = new ResourceLocation("lotr:item/gobletGold.png");
        LOTRRenderMug.gobletSilverTexture = new ResourceLocation("lotr:item/gobletSilver.png");
        LOTRRenderMug.gobletCopperTexture = new ResourceLocation("lotr:item/gobletCopper.png");
        LOTRRenderMug.gobletWoodTexture = new ResourceLocation("lotr:item/gobletWood.png");
        LOTRRenderMug.skullTexture = new ResourceLocation("lotr:item/skullCup.png");
        LOTRRenderMug.glassTexture = new ResourceLocation("lotr:item/wineGlass.png");
        LOTRRenderMug.bottleTexture = new ResourceLocation("lotr:item/glassBottle.png");
        LOTRRenderMug.hornTexture = new ResourceLocation("lotr:item/aleHorn.png");
        LOTRRenderMug.hornGoldTexture = new ResourceLocation("lotr:item/aleHornGold.png");
        LOTRRenderMug.mugModel = new LOTRModelMug();
        LOTRRenderMug.gobletModel = new LOTRModelGoblet();
        LOTRRenderMug.skullModel = new LOTRModelSkullCup();
        LOTRRenderMug.glassModel = new LOTRModelWineGlass();
        LOTRRenderMug.bottleModel = new LOTRModelGlassBottle();
        LOTRRenderMug.hornModel = new LOTRModelAleHorn();
        LOTRRenderMug.renderBlocks = new RenderBlocks();
    }
}
