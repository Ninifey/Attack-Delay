// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityTauredain;
import net.minecraft.entity.EntityLiving;

public class LOTRRenderTauredainShaman extends LOTRRenderTauredain
{
    private static LOTRRandomSkins outfits;
    
    public LOTRRenderTauredainShaman() {
        LOTRRenderTauredainShaman.outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/shaman_outfit");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityTauredain tauredain = (LOTREntityTauredain)entity;
        if (pass == 1 && tauredain.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(LOTRRenderTauredainShaman.outfits.getRandomSkin(tauredain));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)tauredain, pass, f);
    }
}
