// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderGondorMan extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins skinsSoldier;
    private static LOTRRandomSkins outfits;
    private static LOTRRandomSkins headwearFemale;
    protected ModelBiped outfitModel;
    
    public LOTRRenderGondorMan() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderGondorMan.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondor_male");
        LOTRRenderGondorMan.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondor_female");
        LOTRRenderGondorMan.skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondorSoldier");
        LOTRRenderGondorMan.outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/outfit");
        LOTRRenderGondorMan.headwearFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/headwear_female");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityGondorMan gondorian = (LOTREntityGondorMan)entity;
        if (!gondorian.familyInfo.isMale()) {
            return LOTRRenderGondorMan.skinsFemale.getRandomSkin(gondorian);
        }
        if (gondorian instanceof LOTREntityGondorSoldier) {
            return LOTRRenderGondorMan.skinsSoldier.getRandomSkin(gondorian);
        }
        return LOTRRenderGondorMan.skinsMale.getRandomSkin(gondorian);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityGondorMan gondorian = (LOTREntityGondorMan)entity;
        if (pass == 1 && gondorian.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(gondorian, 4) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderGondorMan.outfits.getRandomSkin(gondorian));
            return 1;
        }
        if (pass == 0 && gondorian.getEquipmentInSlot(4) == null && !gondorian.familyInfo.isMale() && LOTRRandomSkins.nextInt(gondorian, 4) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderGondorMan.headwearFemale.getRandomSkin(gondorian));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)gondorian, pass, f);
    }
}
