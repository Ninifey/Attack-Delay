// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.ItemRenderer;
import lotr.common.item.LOTRItemRing;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.texture.TextureMap;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import lotr.common.LOTRMod;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import lotr.client.LOTRTextures;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityDorwinionElf;
import lotr.common.entity.npc.LOTREntityTormentedElf;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelElf;

public class LOTRRenderElf extends LOTRRenderBiped
{
    private static LOTRRandomSkins galadhrimSkinsMale;
    private static LOTRRandomSkins galadhrimSkinsFemale;
    private static LOTRRandomSkins woodElfSkinsMale;
    private static LOTRRandomSkins woodElfSkinsFemale;
    private static LOTRRandomSkins highElfSkinsMale;
    private static LOTRRandomSkins highElfSkinsFemale;
    private static LOTRRandomSkins dorwinionSkinsMale;
    private static LOTRRandomSkins dorwinionSkinsFemale;
    private static LOTRRandomSkins tormentedElfSkins;
    private static LOTRRandomSkins jazzSkinsMale;
    private static LOTRRandomSkins jazzSkinsFemale;
    private static LOTRRandomSkins jazzOutfits;
    private LOTRModelElf eyesModel;
    private LOTRModelElf outfitModel;
    
    public LOTRRenderElf() {
        super(new LOTRModelElf(), 0.5f);
        this.eyesModel = new LOTRModelElf(0.05f, 64, 64);
        this.outfitModel = new LOTRModelElf(0.6f, 64, 64);
        LOTRRenderElf.galadhrimSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/galadhrim_male");
        LOTRRenderElf.galadhrimSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/galadhrim_female");
        LOTRRenderElf.woodElfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/woodElf_male");
        LOTRRenderElf.woodElfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/woodElf_female");
        LOTRRenderElf.highElfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/highElf_male");
        LOTRRenderElf.highElfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/highElf_female");
        LOTRRenderElf.dorwinionSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/dorwinion_male");
        LOTRRenderElf.dorwinionSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/dorwinion_female");
        LOTRRenderElf.tormentedElfSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/tormented");
        LOTRRenderElf.jazzSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_male");
        LOTRRenderElf.jazzSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_female");
        LOTRRenderElf.jazzOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_outfit");
    }
    
    @Override
    protected void func_82421_b() {
        super.field_82423_g = new LOTRModelElf(1.0f);
        super.field_82425_h = new LOTRModelElf(0.5f);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityElf elf = (LOTREntityElf)entity;
        final boolean male = elf.familyInfo.isMale();
        if (elf.isJazz()) {
            if (male) {
                return LOTRRenderElf.jazzSkinsMale.getRandomSkin(elf);
            }
            return LOTRRenderElf.jazzSkinsFemale.getRandomSkin(elf);
        }
        else {
            if (elf instanceof LOTREntityTormentedElf) {
                return LOTRRenderElf.tormentedElfSkins.getRandomSkin(elf);
            }
            if (elf instanceof LOTREntityDorwinionElf) {
                if (male) {
                    return LOTRRenderElf.dorwinionSkinsMale.getRandomSkin(elf);
                }
                return LOTRRenderElf.dorwinionSkinsFemale.getRandomSkin(elf);
            }
            else if (elf instanceof LOTREntityHighElf || elf instanceof LOTREntityRivendellElf) {
                if (male) {
                    return LOTRRenderElf.highElfSkinsMale.getRandomSkin(elf);
                }
                return LOTRRenderElf.highElfSkinsFemale.getRandomSkin(elf);
            }
            else if (elf instanceof LOTREntityWoodElf) {
                if (male) {
                    return LOTRRenderElf.woodElfSkinsMale.getRandomSkin(elf);
                }
                return LOTRRenderElf.woodElfSkinsFemale.getRandomSkin(elf);
            }
            else {
                if (male) {
                    return LOTRRenderElf.galadhrimSkinsMale.getRandomSkin(elf);
                }
                return LOTRRenderElf.galadhrimSkinsFemale.getRandomSkin(elf);
            }
        }
    }
    
    protected void renderModel(final EntityLivingBase entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.renderModel(entity, f, f1, f2, f3, f4, f5);
        if (entity instanceof LOTREntityTormentedElf) {
            final ResourceLocation eyes = LOTRTextures.getEyesTexture(this.getEntityTexture((Entity)entity), new int[][] { { 9, 12 }, { 13, 12 } }, 2, 1);
            LOTRGlowingEyes.renderGlowingEyes((Entity)entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityElf elf = (LOTREntityElf)entity;
        if (elf.isJazz() && pass == 0 && elf.getEquipmentInSlot(4) == null && LOTRRandomSkins.nextInt(elf, 2) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderElf.jazzOutfits.getRandomSkin(elf));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)elf, pass, f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        final LOTREntityElf elf = (LOTREntityElf)entity;
        if (LOTRMod.isAprilFools()) {
            GL11.glScalef(0.25f, 0.25f, 0.25f);
        }
        if (elf.isJazz() && elf.isSolo()) {
            float hue = (((Entity)elf).ticksExisted + f) / 20.0f;
            hue %= 360.0f;
            final float sat = 0.5f;
            final Color color = Color.getHSBColor(hue, sat, 1.0f);
            final float r = color.getRed() / 255.0f;
            final float g = color.getGreen() / 255.0f;
            final float b = color.getBlue() / 255.0f;
            GL11.glColor3f(r, g, b);
            final float soloSpin = elf.getSoloSpin(f);
            GL11.glRotatef(soloSpin, 0.0f, 1.0f, 0.0f);
        }
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        super.renderEquippedItems(entity, f);
        final LOTREntityElf elf = (LOTREntityElf)entity;
        if (elf.isJazz() && elf.isSolo()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.75f, 0.1f);
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
            final TextureManager texturemanager = ((Render)this).renderManager.renderEngine;
            texturemanager.bindTexture(TextureMap.locationItemsTexture);
            TextureUtil.func_152777_a(false, false, 1.0f);
            final Tessellator tessellator = Tessellator.instance;
            final IIcon icon = LOTRItemRing.saxIcon;
            final float minU = icon.getMinU();
            final float maxU = icon.getMaxU();
            final float minV = icon.getMinV();
            final float maxV = icon.getMaxV();
            GL11.glEnable(32826);
            ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
            GL11.glDisable(32826);
            texturemanager.bindTexture(TextureMap.locationItemsTexture);
            TextureUtil.func_147945_b();
            GL11.glPopMatrix();
        }
    }
}
