// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityUrukHaiBerserker;
import org.lwjgl.opengl.GL11;
import lotr.client.LOTRTextures;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityBlackUruk;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelOrc;

public class LOTRRenderOrc extends LOTRRenderBiped
{
    private static LOTRRandomSkins orcSkins;
    private static LOTRRandomSkins urukSkins;
    private static LOTRRandomSkins blackUrukSkins;
    private LOTRModelOrc eyesModel;
    
    public LOTRRenderOrc() {
        super(new LOTRModelOrc(), 0.5f);
        this.eyesModel = new LOTRModelOrc(0.05f);
        LOTRRenderOrc.orcSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/orc");
        LOTRRenderOrc.urukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/urukHai");
        LOTRRenderOrc.blackUrukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/blackUruk");
    }
    
    @Override
    protected void func_82421_b() {
        super.field_82423_g = new LOTRModelOrc(1.0f);
        super.field_82425_h = new LOTRModelOrc(0.5f);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityOrc orc = (LOTREntityOrc)entity;
        if (orc instanceof LOTREntityUrukHai) {
            return LOTRRenderOrc.urukSkins.getRandomSkin(orc);
        }
        if (orc instanceof LOTREntityBlackUruk) {
            return LOTRRenderOrc.blackUrukSkins.getRandomSkin(orc);
        }
        return LOTRRenderOrc.orcSkins.getRandomSkin(orc);
    }
    
    protected void renderModel(final EntityLivingBase entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.renderModel(entity, f, f1, f2, f3, f4, f5);
        final ResourceLocation eyes = LOTRTextures.getEyesTexture(this.getEntityTexture((Entity)entity), new int[][] { { 9, 11 }, { 13, 11 } }, 2, 1);
        LOTRGlowingEyes.renderGlowingEyes((Entity)entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        final LOTREntityOrc orc = (LOTREntityOrc)entity;
        if (orc.isWeakOrc) {
            GL11.glScalef(0.85f, 0.85f, 0.85f);
        }
        else if (orc instanceof LOTREntityUrukHaiBerserker) {
            final float scale = LOTREntityUrukHaiBerserker.BERSERKER_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
    }
}
