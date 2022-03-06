// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import java.util.HashMap;
import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.block.LOTRBlockChest;
import net.minecraft.block.Block;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import lotr.common.tileentity.LOTRTileEntityChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderChest extends TileEntitySpecialRenderer
{
    private static Map<String, ResourceLocation> chestTextures;
    private static ModelChest chestModel;
    private LOTRTileEntityChest itemEntity;
    
    public LOTRRenderChest() {
        this.itemEntity = new LOTRTileEntityChest();
    }
    
    public static ResourceLocation getChestTexture(final String s) {
        ResourceLocation r = LOTRRenderChest.chestTextures.get(s);
        if (r == null) {
            r = new ResourceLocation("lotr:item/chest/" + s + ".png");
            LOTRRenderChest.chestTextures.put(s, r);
        }
        return r;
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityChest chest = (LOTRTileEntityChest)tileentity;
        int meta;
        if (!chest.hasWorldObj()) {
            meta = 0;
        }
        else {
            final Block block = tileentity.getBlockType();
            meta = tileentity.getBlockMetadata();
            if (block instanceof BlockChest && meta == 0) {
                try {
                    ((BlockChest)block).func_149954_e(tileentity.getWorldObj(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
                }
                catch (ClassCastException e) {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", new Object[] { tileentity.xCoord, tileentity.yCoord, tileentity.zCoord });
                }
                meta = tileentity.getBlockMetadata();
            }
        }
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float)d, (float)d1 + 1.0f, (float)d2 + 1.0f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        float rot = 0.0f;
        if (meta == 2) {
            rot = 180.0f;
        }
        if (meta == 3) {
            rot = 0.0f;
        }
        if (meta == 4) {
            rot = 90.0f;
        }
        if (meta == 5) {
            rot = -90.0f;
        }
        GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        float lid = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * f;
        lid = 1.0f - lid;
        lid = 1.0f - lid * lid * lid;
        LOTRRenderChest.chestModel.chestLid.rotateAngleX = -(lid * 3.1415927f / 2.0f);
        this.bindTexture(getChestTexture(chest.textureName));
        LOTRRenderChest.chestModel.renderAll();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void renderInvChest(final Block block, final int meta) {
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.itemEntity.textureName = "";
        if (block instanceof LOTRBlockChest) {
            this.itemEntity.textureName = ((LOTRBlockChest)block).getChestTextureName();
        }
        else if (block instanceof LOTRBlockSpawnerChest) {
            final Block c = ((LOTRBlockSpawnerChest)block).chestModel;
            if (c instanceof LOTRBlockChest) {
                this.itemEntity.textureName = ((LOTRBlockChest)c).getChestTextureName();
            }
        }
        this.renderTileEntityAt(this.itemEntity, 0.0, 0.0, 0.0, 0.0f);
        GL11.glEnable(32826);
    }
    
    static {
        LOTRRenderChest.chestTextures = new HashMap<String, ResourceLocation>();
        LOTRRenderChest.chestModel = new ModelChest();
    }
}
