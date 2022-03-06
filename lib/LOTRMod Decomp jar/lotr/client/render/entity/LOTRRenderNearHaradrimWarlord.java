// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderNearHaradrimWarlord extends LOTRRenderNearHaradrim
{
    private static ResourceLocation skin;
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderNearHaradrimWarlord.skin;
    }
    
    static {
        LOTRRenderNearHaradrimWarlord.skin = new ResourceLocation("lotr:mob/nearHarad/warlord.png");
    }
}
