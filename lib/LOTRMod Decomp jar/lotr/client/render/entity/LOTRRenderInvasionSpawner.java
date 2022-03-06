// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.LOTREntityInvasionSpawner;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderInvasionSpawner extends Render
{
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationItemsTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityInvasionSpawner spawner = (LOTREntityInvasionSpawner)entity;
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        final float rotation = this.interpolateRotation(spawner.prevSpawnerSpin, spawner.spawnerSpin, f1);
        final float scale = 1.5f;
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(scale, scale, scale);
        final ItemStack item = spawner.getInvasionItem();
        super.renderManager.itemRenderer.renderItem(super.renderManager.livingPlayer, item, 0, IItemRenderer.ItemRenderType.EQUIPPED);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    private float interpolateRotation(final float prevRotation, final float newRotation, final float tick) {
        float interval;
        for (interval = newRotation - prevRotation; interval < -180.0f; interval += 360.0f) {}
        while (interval >= 180.0f) {
            interval -= 360.0f;
        }
        return prevRotation + tick * interval;
    }
}
