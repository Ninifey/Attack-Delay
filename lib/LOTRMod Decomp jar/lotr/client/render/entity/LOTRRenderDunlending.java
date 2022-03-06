// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDunlendingBartender;
import lotr.common.entity.npc.LOTREntityDunlending;
import net.minecraft.entity.EntityLiving;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunlending extends LOTRRenderDunlendingBase
{
    private static LOTRRandomSkins dunlendingOutfits;
    private static ResourceLocation outfitApron;
    private ModelBiped outfitModel;
    
    public LOTRRenderDunlending() {
        this.setRenderPassModel((ModelBase)(this.outfitModel = new LOTRModelHuman(0.6f, false)));
        LOTRRenderDunlending.dunlendingOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/outfit");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDunlending dunlending = (LOTREntityDunlending)entity;
        if (pass == 1 && dunlending.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)this.outfitModel);
            if (dunlending instanceof LOTREntityDunlendingBartender) {
                this.bindTexture(LOTRRenderDunlending.outfitApron);
            }
            else {
                this.bindTexture(LOTRRenderDunlending.dunlendingOutfits.getRandomSkin(dunlending));
            }
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)dunlending, pass, f);
    }
    
    static {
        LOTRRenderDunlending.outfitApron = new ResourceLocation("lotr:mob/dunland/bartender_apron.png");
    }
}
