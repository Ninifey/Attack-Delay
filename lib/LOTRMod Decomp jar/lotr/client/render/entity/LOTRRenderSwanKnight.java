// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;

public class LOTRRenderSwanKnight extends LOTRRenderBiped
{
    private static LOTRRandomSkins dolAmrothSkins;
    
    public LOTRRenderSwanKnight() {
        super(new LOTRModelHuman(), 0.5f);
        LOTRRenderSwanKnight.dolAmrothSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/swanKnight");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderSwanKnight.dolAmrothSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
}
