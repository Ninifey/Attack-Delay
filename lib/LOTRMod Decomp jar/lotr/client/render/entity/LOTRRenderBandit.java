// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBandit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;

public class LOTRRenderBandit extends LOTRRenderBiped
{
    private LOTRRandomSkins banditSkins;
    
    public LOTRRenderBandit(final String s) {
        super(new LOTRModelHuman(), 0.5f);
        this.banditSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/bandit/" + s);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return this.banditSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
}
