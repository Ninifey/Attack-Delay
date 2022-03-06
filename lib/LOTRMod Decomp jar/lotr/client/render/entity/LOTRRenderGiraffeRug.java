// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelGiraffeRug;

public class LOTRRenderGiraffeRug extends LOTRRenderRugBase
{
    public LOTRRenderGiraffeRug() {
        super(new LOTRModelGiraffeRug());
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderGiraffe.texture;
    }
}
