// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import lotr.client.render.entity.LOTRRenderPortal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;

public class LOTRModelCompass extends ModelBase
{
    public static LOTRModelCompass compassModel;
    private static ResourceLocation compassTexture;
    private ModelRenderer compass;
    private ModelBase ringModel;
    private ModelBase writingModelOuter;
    private ModelBase writingModelInner;
    
    private LOTRModelCompass() {
        this.ringModel = new LOTRModelPortal(0);
        this.writingModelOuter = new LOTRModelPortal(1);
        this.writingModelInner = new LOTRModelPortal(1);
        super.textureWidth = 32;
        super.textureHeight = 32;
        (this.compass = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.compass.addBox(-16.0f, 0.0f, -16.0f, 32, 0, 32);
    }
    
    public void render(final float scale, final float rotation) {
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2884);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GL11.glEnable(32826);
        GL11.glScalef(1.0f, 1.0f, -1.0f);
        GL11.glRotatef(40.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        texturemanager.bindTexture(LOTRModelCompass.compassTexture);
        this.compass.render(scale * 2.0f);
        texturemanager.bindTexture(LOTRRenderPortal.ringTexture);
        this.ringModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        texturemanager.bindTexture(LOTRRenderPortal.writingTexture);
        this.writingModelOuter.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale * 1.05f);
        texturemanager.bindTexture(LOTRRenderPortal.writingTexture);
        this.writingModelInner.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale * 0.85f);
        GL11.glDisable(32826);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRModelCompass.compassModel = new LOTRModelCompass();
        LOTRModelCompass.compassTexture = new ResourceLocation("lotr:misc/compass.png");
    }
}
