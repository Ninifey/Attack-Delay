// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import net.minecraft.tileentity.TileEntity;
import lotr.client.model.LOTRModelBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderBeacon extends TileEntitySpecialRenderer
{
    private ModelBase beaconModel;
    private ResourceLocation beaconTexture;
    
    public LOTRRenderBeacon() {
        this.beaconModel = new LOTRModelBeacon();
        this.beaconTexture = new ResourceLocation("lotr:item/beacon.png");
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        this.bindTexture(this.beaconTexture);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 1.5f, (float)d2 + 0.5f);
        GL11.glScalef(1.0f, -1.0f, 1.0f);
        this.beaconModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public void renderInvBeacon() {
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.renderTileEntityAt(null, 0.0, 0.0, 0.0, 0.0f);
        GL11.glEnable(32826);
    }
}
