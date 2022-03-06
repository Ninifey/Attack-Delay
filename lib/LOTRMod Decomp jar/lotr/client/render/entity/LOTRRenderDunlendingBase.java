// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.item.ItemStack;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityDunlendingBerserker;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDunlending;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;

public class LOTRRenderDunlendingBase extends LOTRRenderBiped
{
    private static LOTRRandomSkins dunlendingSkinsMale;
    private static LOTRRandomSkins dunlendingSkinsFemale;
    private static LOTRRandomSkins dunlendingSkinsBerserker;
    
    public LOTRRenderDunlendingBase() {
        super(new LOTRModelHuman(), 0.5f);
        LOTRRenderDunlendingBase.dunlendingSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/dunlending_male");
        LOTRRenderDunlendingBase.dunlendingSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/dunlending_female");
        LOTRRenderDunlendingBase.dunlendingSkinsBerserker = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/berserker");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDunlending dunlending = (LOTREntityDunlending)entity;
        if (!dunlending.familyInfo.isMale()) {
            return LOTRRenderDunlendingBase.dunlendingSkinsFemale.getRandomSkin(dunlending);
        }
        if (dunlending instanceof LOTREntityDunlendingBerserker) {
            return LOTRRenderDunlendingBase.dunlendingSkinsBerserker.getRandomSkin(dunlending);
        }
        return LOTRRenderDunlendingBase.dunlendingSkinsMale.getRandomSkin(dunlending);
    }
    
    @Override
    public void doRender(final EntityLiving entity, final double d, double d1, final double d2, final float f, final float f1) {
        final LOTREntityDunlending dunlending = (LOTREntityDunlending)entity;
        final ItemStack helmet = dunlending.getEquipmentInSlot(4);
        if (helmet != null && helmet.getItem() == Item.getItemFromBlock(LOTRMod.orcBomb)) {
            d1 += 0.5;
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + 2.5f, (float)d2);
            GL11.glRotatef(-f, 0.0f, 1.0f, 0.0f);
            final int i = entity.getBrightnessForRender(f1);
            final int j = i % 65536;
            final int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0f, k / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final LOTRRenderOrcBomb bombRenderer = (LOTRRenderOrcBomb)RenderManager.instance.getEntityClassRenderObject((Class)LOTREntityOrcBomb.class);
            bombRenderer.renderBomb((Entity)entity, 0.0, 0.0, 0.0, f1, 5, 0, 0.75f, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        super.doRender(entity, d, d1, d2, f, f1);
    }
}
