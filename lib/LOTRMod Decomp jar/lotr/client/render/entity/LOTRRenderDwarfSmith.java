// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarfSmith extends LOTRRenderDwarf
{
    private static ResourceLocation apronTexture;
    
    @Override
    public int shouldRenderPass(final EntityLiving entity, final int pass, final float f) {
        final LOTREntityDwarf dwarf = (LOTREntityDwarf)entity;
        if (pass == 1 && dwarf.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel((ModelBase)super.standardRenderPassModel);
            this.bindTexture(LOTRRenderDwarfSmith.apronTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderDwarfSmith.apronTexture = new ResourceLocation("lotr:mob/dwarf/blacksmith_apron.png");
    }
}
