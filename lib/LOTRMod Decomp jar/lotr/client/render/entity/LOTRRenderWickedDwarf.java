// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWickedDwarf extends LOTRRenderDwarf
{
    private static LOTRRandomSkins wickedSkinsMale;
    private static final ResourceLocation apronTexture;
    
    public LOTRRenderWickedDwarf() {
        LOTRRenderWickedDwarf.wickedSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/wicked_male");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
        return LOTRRenderWickedDwarf.wickedSkinsMale.getRandomSkin(dwarf);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
        if (pass == 1 && dwarf.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.standardRenderPassModel);
            this.bindTexture(LOTRRenderWickedDwarf.apronTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        apronTexture = new ResourceLocation("lotr:mob/dwarf/wicked_apron.png");
    }
}
