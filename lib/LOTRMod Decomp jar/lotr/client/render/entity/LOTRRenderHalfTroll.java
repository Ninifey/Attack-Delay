// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHalfTroll;

public class LOTRRenderHalfTroll extends LOTRRenderBiped
{
    private static LOTRRandomSkins halfTrollSkins;
    
    public LOTRRenderHalfTroll() {
        super(new LOTRModelHalfTroll(), 0.5f);
        LOTRRenderHalfTroll.halfTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/halfTroll/halfTroll");
    }
    
    @Override
    protected void func_82421_b() {
        super.field_82423_g = new LOTRModelHalfTroll(1.0f);
        super.field_82425_h = new LOTRModelHalfTroll(0.5f);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll)entity;
        return LOTRRenderHalfTroll.halfTrollSkins.getRandomSkin(halfTroll);
    }
    
    @Override
    public float getHeldItemYTranslation() {
        return 0.45f;
    }
}
