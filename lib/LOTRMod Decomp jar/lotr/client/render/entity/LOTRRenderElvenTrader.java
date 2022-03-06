// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelElf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElvenTrader extends LOTRRenderElf
{
    private ResourceLocation outfitTexture;
    private ModelBiped outfitModel;
    
    public LOTRRenderElvenTrader(final String s) {
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelElf(0.5f)));
        this.outfitTexture = new ResourceLocation("lotr:mob/elf/" + s + ".png");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        if (pass == 0) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            this.bindTexture(this.outfitTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
