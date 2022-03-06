// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityFish;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelFish;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderFish extends RenderLiving
{
    private static Map<String, LOTRRandomSkins> fishTypeSkins;
    
    public LOTRRenderFish() {
        super((ModelBase)new LOTRModelFish(), 0.0f);
    }
    
    private LOTRRandomSkins getFishSkins(final String s) {
        LOTRRandomSkins skins = LOTRRenderFish.fishTypeSkins.get(s);
        if (skins == null) {
            skins = LOTRRandomSkins.loadSkinsList("lotr:mob/fish/" + s);
            LOTRRenderFish.fishTypeSkins.put(s, skins);
        }
        return skins;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityFish fish = (LOTREntityFish)entity;
        final String type = fish.getFishTextureDir();
        final LOTRRandomSkins skins = this.getFishSkins(type);
        return skins.getRandomSkin(fish);
    }
    
    public void preRenderCallback(final EntityLivingBase entity, final float f) {
        if (!entity.isInWater()) {
            GL11.glTranslatef(0.0f, -0.05f, 0.0f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected float handleRotationFloat(final EntityLivingBase entity, final float f) {
        return super.handleRotationFloat(entity, f);
    }
    
    static {
        LOTRRenderFish.fishTypeSkins = new HashMap<String, LOTRRandomSkins>();
    }
}
