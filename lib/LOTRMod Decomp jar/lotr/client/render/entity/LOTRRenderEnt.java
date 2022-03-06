// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.client.LOTRTextures;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.entity.npc.LOTREntityEnt;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelEnt;
import java.util.HashMap;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderEnt extends RenderLiving
{
    private static HashMap entTextures;
    private LOTRModelEnt eyesModel;
    
    public LOTRRenderEnt() {
        super((ModelBase)new LOTRModelEnt(), 0.5f);
        this.eyesModel = new LOTRModelEnt(0.05f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final int treeType = ((LOTREntityEnt)entity).getTreeType();
        final String s = "lotr:mob/ent/" + LOTREntityTree.TYPES[treeType] + ".png";
        ResourceLocation r = LOTRRenderEnt.entTextures.get(treeType);
        if (r == null) {
            r = new ResourceLocation(s);
            LOTRRenderEnt.entTextures.put(treeType, r);
        }
        return r;
    }
    
    protected void renderModel(final EntityLivingBase entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.renderModel(entity, f, f1, f2, f3, f4, f5);
        final ResourceLocation eyes = LOTRTextures.getEyesTexture(this.getEntityTexture((Entity)entity), new int[][] { { 15, 23 }, { 22, 23 } }, 3, 2);
        LOTRGlowingEyes.renderGlowingEyes((Entity)entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
    }
    
    static {
        LOTRRenderEnt.entTextures = new HashMap();
    }
}
