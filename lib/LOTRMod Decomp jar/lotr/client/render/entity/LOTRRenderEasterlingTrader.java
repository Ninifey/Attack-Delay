// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderEasterlingTrader extends LOTRRenderEasterling
{
    private ResourceLocation traderOutfit;
    
    public LOTRRenderEasterlingTrader(final String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/rhun/" + s + ".png");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityEasterling easterling = (LOTREntityEasterling)entity;
        if (pass == 1 && easterling.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)easterling, pass, f);
    }
}
