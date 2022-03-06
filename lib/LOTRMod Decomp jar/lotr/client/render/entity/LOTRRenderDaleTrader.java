// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDaleTrader extends LOTRRenderDaleMan
{
    private ResourceLocation traderOutfit;
    
    public LOTRRenderDaleTrader(final String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/dale/" + s + ".png");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDaleMan man = (LOTREntityDaleMan)entity;
        if (pass == 1 && man.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)man, pass, f);
    }
}
