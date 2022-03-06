// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderZebra extends LOTRRenderHorse
{
    private static ResourceLocation zebraTexture;
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderZebra.zebraTexture;
    }
    
    static {
        LOTRRenderZebra.zebraTexture = new ResourceLocation("lotr:mob/zebra.png");
    }
}
