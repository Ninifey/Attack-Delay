// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderDorwinionMan extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins outfits;
    private ModelBiped outfitModel;
    
    public LOTRRenderDorwinionMan() {
        super(new LOTRModelHuman(), 0.5f);
        this.outfitModel = new LOTRModelHuman(0.6f, false);
        LOTRRenderDorwinionMan.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/dorwinion_male");
        LOTRRenderDorwinionMan.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/dorwinion_female");
        LOTRRenderDorwinionMan.outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/outfit");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDorwinionMan man = (LOTREntityDorwinionMan)entity;
        if (man.familyInfo.isMale()) {
            return LOTRRenderDorwinionMan.skinsMale.getRandomSkin(man);
        }
        return LOTRRenderDorwinionMan.skinsFemale.getRandomSkin(man);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDorwinionMan man = (LOTREntityDorwinionMan)entity;
        if (pass == 1 && man.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(man, 2) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderDorwinionMan.outfits.getRandomSkin(man));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)man, pass, f);
    }
}
