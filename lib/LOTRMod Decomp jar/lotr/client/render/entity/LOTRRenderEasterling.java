// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderEasterling extends LOTRRenderBiped
{
    private static LOTRRandomSkins easterlingSkinsMale;
    private static LOTRRandomSkins easterlingSkinsFemale;
    protected ModelBiped outfitModel;
    
    public LOTRRenderEasterling() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderEasterling.easterlingSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/rhun/easterling_male");
        LOTRRenderEasterling.easterlingSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/rhun/easterling_female");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityEasterling easterling = (LOTREntityEasterling)entity;
        if (easterling.familyInfo.isMale()) {
            return LOTRRenderEasterling.easterlingSkinsMale.getRandomSkin(easterling);
        }
        return LOTRRenderEasterling.easterlingSkinsFemale.getRandomSkin(easterling);
    }
}
