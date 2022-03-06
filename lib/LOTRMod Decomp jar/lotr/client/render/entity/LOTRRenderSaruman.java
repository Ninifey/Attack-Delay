// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSaruman extends LOTRRenderBiped
{
    private static ResourceLocation skin;
    private Random rand;
    private boolean twitch;
    
    public LOTRRenderSaruman() {
        super(new LOTRModelHuman(), 0.5f);
        this.rand = new Random();
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderSaruman.skin;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        if (((Entity)entity).ticksExisted % 60 == 0) {
            this.twitch = !this.twitch;
        }
        if (this.twitch) {
            GL11.glRotatef(this.rand.nextFloat() * 40.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(this.rand.nextFloat() * 40.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.rand.nextFloat() * 40.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(this.rand.nextFloat() * 0.5f, this.rand.nextFloat() * 0.5f, this.rand.nextFloat() * 0.5f);
        }
        final int i = ((Entity)entity).ticksExisted % 360;
        final float hue = i / 360.0f;
        final Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        GL11.glColor3f(r, g, b);
    }
    
    static {
        LOTRRenderSaruman.skin = new ResourceLocation("lotr:mob/char/saruman.png");
    }
}
