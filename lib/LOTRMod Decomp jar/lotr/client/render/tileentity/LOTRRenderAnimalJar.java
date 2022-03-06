// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.entity.LOTRRenderBird;
import lotr.common.item.LOTRItemAnimalJar;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderAnimalJar extends TileEntitySpecialRenderer implements IItemRenderer
{
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)tileentity;
        final Entity jarEntity = jar.getOrCreateJarEntity();
        if (jarEntity != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, jar.getEntityBobbing(f), 0.0f);
            if (jarEntity instanceof EntityLivingBase) {
                final EntityLivingBase jarLiving = (EntityLivingBase)jarEntity;
                final EntityLivingBase viewer = RenderManager.instance.livingPlayer;
                if (jar.isEntityWatching()) {
                    final Vec3 viewerPos = viewer.getPosition(f);
                    final Vec3 entityPos = jarLiving.getPosition(f);
                    final double dx = entityPos.xCoord - viewerPos.xCoord;
                    final double dy = entityPos.yCoord - viewerPos.yCoord;
                    final double dz = entityPos.zCoord - viewerPos.zCoord;
                    float lookYaw = (float)Math.toDegrees(Math.atan2(dz, dx));
                    lookYaw += 90.0f;
                    final EntityLivingBase entityLivingBase = jarLiving;
                    final EntityLivingBase entityLivingBase2 = jarLiving;
                    final float n = lookYaw;
                    ((Entity)entityLivingBase2).prevRotationYaw = n;
                    ((Entity)entityLivingBase).rotationYaw = n;
                }
                jarLiving.renderYawOffset = ((Entity)jarLiving).rotationYaw;
                jarLiving.prevRenderYawOffset = ((Entity)jarLiving).prevRotationYaw;
            }
            RenderManager.instance.func_147936_a(jarEntity, f, false);
            GL11.glPopMatrix();
        }
    }
    
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return true;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return true;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        if (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
        final EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
        final Entity jarEntity = LOTRItemAnimalJar.getItemJarEntity(itemstack, ((Entity)viewer).worldObj);
        if (jarEntity != null) {
            jarEntity.setLocationAndAngles(0.0, 0.0, 0.0, 0.0f, 0.0f);
            jarEntity.ticksExisted = ((Entity)viewer).ticksExisted;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, -0.5f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushAttrib(1048575);
            if (type == IItemRenderer.ItemRenderType.ENTITY) {
                LOTRRenderBird.renderStolenItem = false;
            }
            RenderManager.instance.func_147940_a(jarEntity, 0.0, 0.0, 0.0, 0.0f, LOTRTickHandlerClient.renderTick);
            LOTRRenderBird.renderStolenItem = true;
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage(), 1.0f);
        GL11.glDisable(3042);
    }
}
