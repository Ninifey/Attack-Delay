// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHaradrimTrader extends LOTRRenderNearHaradrim
{
    private ResourceLocation traderOutfit;
    
    public LOTRRenderHaradrimTrader(final String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/nearHarad/" + s + ".png");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityNearHaradrimBase haradrim = (LOTREntityNearHaradrimBase)entity;
        if (pass == 1 && haradrim.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)haradrim, pass, f);
    }
}
