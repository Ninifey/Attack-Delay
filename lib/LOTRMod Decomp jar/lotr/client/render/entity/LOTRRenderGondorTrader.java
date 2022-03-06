// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGondorTrader extends LOTRRenderGondorMan
{
    private ResourceLocation traderOutfit;
    
    public LOTRRenderGondorTrader(final String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/gondor/" + s + ".png");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityGondorMan gondorian = (LOTREntityGondorMan)entity;
        if (pass == 1 && gondorian.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)gondorian, pass, f);
    }
}
