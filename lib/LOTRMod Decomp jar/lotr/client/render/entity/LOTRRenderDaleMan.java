// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityDaleLevyman;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderDaleMan extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins skinsSoldier;
    protected ModelBiped outfitModel;
    
    public LOTRRenderDaleMan() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderDaleMan.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_male");
        LOTRRenderDaleMan.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_female");
        LOTRRenderDaleMan.skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_soldier");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDaleMan man = (LOTREntityDaleMan)entity;
        if (!man.familyInfo.isMale()) {
            return LOTRRenderDaleMan.skinsFemale.getRandomSkin(man);
        }
        if (man instanceof LOTREntityDaleLevyman) {
            return LOTRRenderDaleMan.skinsSoldier.getRandomSkin(man);
        }
        return LOTRRenderDaleMan.skinsMale.getRandomSkin(man);
    }
}
