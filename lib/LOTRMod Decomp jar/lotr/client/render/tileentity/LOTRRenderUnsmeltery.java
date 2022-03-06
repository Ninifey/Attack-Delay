// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import lotr.common.block.LOTRBlockForgeBase;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.tileentity.TileEntity;
import lotr.client.model.LOTRModelUnsmeltery;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderUnsmeltery extends TileEntitySpecialRenderer
{
    private ModelBase unsmelteryModel;
    private ResourceLocation idleTexture;
    private ResourceLocation activeTexture;
    
    public LOTRRenderUnsmeltery() {
        this.unsmelteryModel = new LOTRModelUnsmeltery();
        this.idleTexture = new ResourceLocation("lotr:item/unsmeltery/idle.png");
        this.activeTexture = new ResourceLocation("lotr:item/unsmeltery/active.png");
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityUnsmeltery unsmeltery = (LOTRTileEntityUnsmeltery)tileentity;
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 1.5f, (float)d2 + 0.5f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        float rotation = 0.0f;
        float rocking = 0.0f;
        if (unsmeltery != null) {
            switch (unsmeltery.getBlockMetadata() & 0x7) {
                case 2: {
                    rotation = 180.0f;
                    break;
                }
                case 3: {
                    rotation = 0.0f;
                    break;
                }
                case 4: {
                    rotation = 90.0f;
                    break;
                }
                case 5: {
                    rotation = 270.0f;
                    break;
                }
            }
            rocking = unsmeltery.getRockingAmount(f);
        }
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        final boolean useActiveTexture = unsmeltery != null && LOTRBlockForgeBase.isForgeActive((IBlockAccess)unsmeltery.getWorldObj(), unsmeltery.xCoord, unsmeltery.yCoord, unsmeltery.zCoord);
        if (useActiveTexture) {
            this.bindTexture(this.activeTexture);
        }
        else {
            this.bindTexture(this.idleTexture);
        }
        this.unsmelteryModel.render((Entity)null, rocking, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public void renderInvUnsmeltery() {
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.renderTileEntityAt(null, 0.0, 0.0, 0.0, 0.0f);
        GL11.glEnable(32826);
    }
}
