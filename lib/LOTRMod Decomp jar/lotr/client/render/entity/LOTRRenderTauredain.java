// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityTauredain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderTauredain extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins outfits;
    protected ModelBiped outfitModel;
    
    public LOTRRenderTauredain() {
        super(new LOTRModelHuman(), 0.5f);
        this.outfitModel = new LOTRModelHuman(0.6f, false);
        LOTRRenderTauredain.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/tauredain_male");
        LOTRRenderTauredain.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/tauredain_female");
        LOTRRenderTauredain.outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/outfit");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityTauredain tauredain = (LOTREntityTauredain)entity;
        if (tauredain.familyInfo.isMale()) {
            return LOTRRenderTauredain.skinsMale.getRandomSkin(tauredain);
        }
        return LOTRRenderTauredain.skinsFemale.getRandomSkin(tauredain);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityTauredain tauredain = (LOTREntityTauredain)entity;
        if (pass == 1 && tauredain.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(tauredain, 3) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderTauredain.outfits.getRandomSkin(tauredain));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)tauredain, pass, f);
    }
}
