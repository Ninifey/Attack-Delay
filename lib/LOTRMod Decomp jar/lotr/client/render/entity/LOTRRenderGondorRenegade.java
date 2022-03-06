// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityGondorRenegade;
import net.minecraft.entity.EntityLiving;

public class LOTRRenderGondorRenegade extends LOTRRenderGondorMan
{
    private static LOTRRandomSkins hoodSkins;
    
    public LOTRRenderGondorRenegade() {
        LOTRRenderGondorRenegade.hoodSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/renegade_hood");
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityGondorRenegade renegade = (LOTREntityGondorRenegade)entity;
        if (pass == 0 && renegade.getEquipmentInSlot(4) == null) {
            this.setRenderPassModel((ModelBase)super.outfitModel);
            this.bindTexture(LOTRRenderGondorRenegade.hoodSkins.getRandomSkin(renegade));
            return 1;
        }
        return super.shouldRenderPass((EntityLiving)renegade, pass, f);
    }
}
