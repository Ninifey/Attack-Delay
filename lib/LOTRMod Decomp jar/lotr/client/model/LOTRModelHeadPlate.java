// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import lotr.client.render.LOTRRenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.LOTRPlateFallingInfo;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemPlate;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.client.renderer.RenderBlocks;

public class LOTRModelHeadPlate extends LOTRModelHuman
{
    private RenderBlocks blockRenderer;
    private LOTRTileEntityPlate plateTE;
    private Block plateBlock;
    
    public LOTRModelHeadPlate() {
        this.blockRenderer = new RenderBlocks();
        this.plateTE = new LOTRTileEntityPlate();
    }
    
    public void setPlateItem(final ItemStack itemstack) {
        if (itemstack.getItem() instanceof LOTRItemPlate) {
            this.plateBlock = ((LOTRItemPlate)itemstack.getItem()).plateBlock;
        }
        else {
            this.plateBlock = LOTRMod.plateBlock;
        }
    }
    
    @Override
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float tick = LOTRTickHandlerClient.renderTick;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        final float headRotateY = f3;
        final LOTRPlateFallingInfo fallingInfo = (entity == null) ? null : LOTRPlateFallingInfo.getOrCreateFor(entity, false);
        final float fallOffset = (fallingInfo == null) ? 0.0f : fallingInfo.getPlateOffsetY(tick);
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, -1.0f, 1.0f);
        GL11.glRotatef(headRotateY, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 1.0f - super.bipedHead.rotationPointY / 16.0f, 0.0f);
        GL11.glTranslatef(0.0f, fallOffset * 0.5f, 0.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final World world = (entity == null) ? LOTRMod.proxy.getClientWorld() : entity.worldObj;
        LOTRRenderBlocks.renderEntityPlate((IBlockAccess)world, 0, 0, 0, this.plateBlock, this.blockRenderer);
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase living = (EntityLivingBase)entity;
            final ItemStack heldItem = living.getHeldItem();
            if (heldItem != null && LOTRTileEntityPlate.isValidFoodItem(heldItem)) {
                final ItemStack copy;
                final ItemStack heldItemMinusOne = copy = heldItem.copy();
                --copy.stackSize;
                if (heldItemMinusOne.stackSize > 0) {
                    this.plateTE.setFoodItem(heldItemMinusOne);
                    this.plateTE.plateFallInfo = fallingInfo;
                    TileEntityRendererDispatcher.instance.func_147549_a((TileEntity)this.plateTE, -0.5, -0.5, -0.5, tick);
                    this.plateTE.plateFallInfo = null;
                    GL11.glDisable(2884);
                }
            }
        }
        GL11.glPopMatrix();
        GL11.glDisable(32826);
    }
}
