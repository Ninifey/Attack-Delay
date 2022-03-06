// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderDunedain extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    protected ModelBiped outfitModel;
    
    public LOTRRenderDunedain() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderDunedain.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger_male");
        LOTRRenderDunedain.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger_female");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDunedain dunedain = (LOTREntityDunedain)entity;
        if (dunedain.familyInfo.isMale()) {
            return LOTRRenderDunedain.skinsMale.getRandomSkin(dunedain);
        }
        return LOTRRenderDunedain.skinsFemale.getRandomSkin(dunedain);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDunedain dunedain = (LOTREntityDunedain)entity;
        return super.shouldRenderPass((EntityLiving)dunedain, pass, f);
    }
}
