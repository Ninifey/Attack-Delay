// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRohanTrader extends LOTRRenderRohirrim
{
    private ResourceLocation traderOutfit;
    
    public LOTRRenderRohanTrader(final String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/rohan/" + s + ".png");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)entity;
        if (pass == 1 && rohirrim.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)rohirrim, pass, f);
    }
}
