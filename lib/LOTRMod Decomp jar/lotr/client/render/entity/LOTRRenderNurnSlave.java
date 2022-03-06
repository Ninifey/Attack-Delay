// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;

public class LOTRRenderNurnSlave extends LOTRRenderBiped
{
    private static LOTRRandomSkins slaveSkinsMale;
    private static LOTRRandomSkins slaveSkinsFemale;
    
    public LOTRRenderNurnSlave() {
        super(new LOTRModelHuman(), 0.5f);
        LOTRRenderNurnSlave.slaveSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nurn/slave_male");
        LOTRRenderNurnSlave.slaveSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nurn/slave_female");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityNurnSlave slave = (LOTREntityNurnSlave)entity;
        if (slave.familyInfo.isMale()) {
            return LOTRRenderNurnSlave.slaveSkinsMale.getRandomSkin(slave);
        }
        return LOTRRenderNurnSlave.slaveSkinsFemale.getRandomSkin(slave);
    }
}
