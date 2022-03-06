// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderNearHaradrim extends LOTRRenderBiped
{
    private static LOTRRandomSkins haradrimSkinsMale;
    private static LOTRRandomSkins haradrimSkinsFemale;
    private static LOTRRandomSkins warriorSkins;
    private static LOTRRandomSkins harnedorSkinsMale;
    private static LOTRRandomSkins harnedorSkinsFemale;
    private static LOTRRandomSkins harnedorWarriorSkins;
    private static LOTRRandomSkins harnedorOutfits;
    private static LOTRRandomSkins nomadSkinsMale;
    private static LOTRRandomSkins nomadSkinsFemale;
    private static LOTRRandomSkins nomadHats;
    protected ModelBiped outfitModel;
    
    public LOTRRenderNearHaradrim() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderNearHaradrim.haradrimSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/haradrim_male");
        LOTRRenderNearHaradrim.haradrimSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/haradrim_female");
        LOTRRenderNearHaradrim.warriorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/warrior");
        LOTRRenderNearHaradrim.harnedorSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedor_male");
        LOTRRenderNearHaradrim.harnedorSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedor_female");
        LOTRRenderNearHaradrim.harnedorWarriorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedorWarrior");
        LOTRRenderNearHaradrim.harnedorOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedor_outfit");
        LOTRRenderNearHaradrim.nomadSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/nomad_male");
        LOTRRenderNearHaradrim.nomadSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/nomad_female");
        LOTRRenderNearHaradrim.nomadHats = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/nomad_hat");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityNearHaradrimBase haradrim = (LOTREntityNearHaradrimBase)entity;
        if (haradrim instanceof LOTREntityHarnedhrim || haradrim instanceof LOTREntityGulfHaradrim || haradrim instanceof LOTREntityCorsair) {
            if (haradrim instanceof LOTREntityHarnedorWarrior || haradrim instanceof LOTREntityGulfHaradWarrior) {
                return LOTRRenderNearHaradrim.harnedorWarriorSkins.getRandomSkin(haradrim);
            }
            if (haradrim.familyInfo.isMale()) {
                return LOTRRenderNearHaradrim.harnedorSkinsMale.getRandomSkin(haradrim);
            }
            return LOTRRenderNearHaradrim.harnedorSkinsFemale.getRandomSkin(haradrim);
        }
        else if (haradrim instanceof LOTREntityNomad) {
            if (haradrim.familyInfo.isMale()) {
                return LOTRRenderNearHaradrim.nomadSkinsMale.getRandomSkin(haradrim);
            }
            return LOTRRenderNearHaradrim.nomadSkinsFemale.getRandomSkin(haradrim);
        }
        else {
            if (haradrim instanceof LOTREntityNearHaradrimWarrior || haradrim instanceof LOTREntityUmbarWarrior) {
                return LOTRRenderNearHaradrim.warriorSkins.getRandomSkin(haradrim);
            }
            if (haradrim.familyInfo.isMale()) {
                return LOTRRenderNearHaradrim.haradrimSkinsMale.getRandomSkin(haradrim);
            }
            return LOTRRenderNearHaradrim.haradrimSkinsFemale.getRandomSkin(haradrim);
        }
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityNearHaradrimBase haradrim = (LOTREntityNearHaradrimBase)entity;
        if ((haradrim instanceof LOTREntityHarnedhrim || haradrim instanceof LOTREntityGulfHaradrim) && pass == 1 && haradrim.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(haradrim, 2) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderNearHaradrim.harnedorOutfits.getRandomSkin(haradrim));
            return 1;
        }
        if (haradrim instanceof LOTREntityNomad && pass == 0 && haradrim.getEquipmentInSlot(4) == null && LOTRRandomSkins.nextInt(haradrim, 2) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderNearHaradrim.nomadHats.getRandomSkin(haradrim));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)haradrim, pass, f);
    }
}
