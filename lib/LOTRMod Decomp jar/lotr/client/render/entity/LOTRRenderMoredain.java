// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityMoredain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;

public class LOTRRenderMoredain extends LOTRRenderBiped
{
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins outfits;
    private ModelBiped outfitModel;
    
    public LOTRRenderMoredain() {
        super(new LOTRModelHuman(), 0.5f);
        this.outfitModel = new LOTRModelHuman(0.6f, false);
        LOTRRenderMoredain.skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/moredain_male");
        LOTRRenderMoredain.skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/moredain_female");
        LOTRRenderMoredain.outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/outfit");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityMoredain moredain = (LOTREntityMoredain)entity;
        if (moredain.familyInfo.isMale()) {
            return LOTRRenderMoredain.skinsMale.getRandomSkin(moredain);
        }
        return LOTRRenderMoredain.skinsFemale.getRandomSkin(moredain);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityMoredain moredain = (LOTREntityMoredain)entity;
        if (pass == 1 && moredain.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(moredain, 3) == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderMoredain.outfits.getRandomSkin(moredain));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)moredain, pass, f);
    }
}
