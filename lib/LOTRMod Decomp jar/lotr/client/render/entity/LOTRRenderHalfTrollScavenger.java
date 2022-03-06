// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHalfTroll;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHalfTrollScavenger extends LOTRRenderHalfTroll
{
    private static ResourceLocation outfitTexture;
    private ModelBiped outfitModel;
    
    public LOTRRenderHalfTrollScavenger() {
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHalfTroll(0.5f)));
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        if (pass == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(LOTRRenderHalfTrollScavenger.outfitTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderHalfTrollScavenger.outfitTexture = new ResourceLocation("lotr:mob/halfTroll/scavenger.png");
    }
}
