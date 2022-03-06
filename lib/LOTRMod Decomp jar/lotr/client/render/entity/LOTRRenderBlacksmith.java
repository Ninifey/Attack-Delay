// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBlacksmith extends LOTRRenderBiped
{
    private LOTRRandomSkins skins;
    private ResourceLocation apron;
    private ModelBiped standardRenderPassModel;
    
    public LOTRRenderBlacksmith(final String s, final String s1) {
        super(new LOTRModelHuman(), 0.5f);
        this.standardRenderPassModel = new LOTRModelHuman(0.5f, false);
        this.skins = LOTRRandomSkins.loadSkinsList("lotr:mob/" + s);
        this.apron = new ResourceLocation("lotr:mob/" + s1 + ".png");
        this.setRenderPassModel((ModelBase)this.standardRenderPassModel);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return this.skins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        if (pass == 1) {
            this.setRenderPassModel((ModelBase)this.standardRenderPassModel);
            this.bindTexture(this.apron);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
