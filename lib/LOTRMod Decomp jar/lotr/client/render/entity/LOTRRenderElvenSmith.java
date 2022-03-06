// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelElf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElvenSmith extends LOTRRenderElf
{
    private ResourceLocation outfitTexture;
    private ResourceLocation capeTexture;
    private ModelBiped outfitModel;
    
    public LOTRRenderElvenSmith(final String s, final String s1) {
        this.outfitModel = new LOTRModelElf(0.5f);
        this.outfitTexture = new ResourceLocation("lotr:mob/elf/" + s + ".png");
        this.capeTexture = new ResourceLocation("lotr:mob/elf/" + s1 + ".png");
        this.setRenderPassModel((ModelBase)this.outfitModel);
    }
    
    @Override
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        ((LOTREntityNPC)entity).npcCape = this.capeTexture;
        super.doRender(entity, d, d1, d2, f, f1);
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
