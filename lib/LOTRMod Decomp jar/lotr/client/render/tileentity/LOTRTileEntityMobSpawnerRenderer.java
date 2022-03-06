// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityList;
import lotr.common.entity.LOTREntities;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import java.util.HashMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRTileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    private int tempID;
    private HashMap initialisedItemEntities;
    private static double itemYaw;
    private static double prevItemYaw;
    
    public LOTRTileEntityMobSpawnerRenderer() {
        this.initialisedItemEntities = new HashMap();
    }
    
    public static void onClientTick() {
        LOTRTileEntityMobSpawnerRenderer.itemYaw = MathHelper.wrapAngleTo180_double(LOTRTileEntityMobSpawnerRenderer.itemYaw);
        LOTRTileEntityMobSpawnerRenderer.prevItemYaw = LOTRTileEntityMobSpawnerRenderer.itemYaw;
        LOTRTileEntityMobSpawnerRenderer.itemYaw += 1.5;
    }
    
    public void func_147496_a(final World world) {
        this.loadEntities(world);
    }
    
    private void loadEntities(final World world) {
        this.unloadEntities();
        if (world != null) {
            for (final LOTREntities.SpawnEggInfo info : LOTREntities.spawnEggs.values()) {
                final String entityName = LOTREntities.getStringFromID(info.spawnedID);
                final Entity entity = EntityList.createEntityByName(entityName, world);
                if (entity instanceof EntityLiving) {
                    ((EntityLiving)entity).onSpawnWithEgg((IEntityLivingData)null);
                }
                this.initialisedItemEntities.put(info.spawnedID, entity);
            }
        }
    }
    
    private void unloadEntities() {
        this.initialisedItemEntities.clear();
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final World world = (World)Minecraft.getMinecraft().theWorld;
        final LOTRTileEntityMobSpawner mobSpawner = (LOTRTileEntityMobSpawner)tileentity;
        if (mobSpawner != null && !mobSpawner.isActive()) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5f, (float)d1, (float)d2 + 0.5f);
        Entity entity = null;
        double yaw = 0.0;
        double prevYaw = 0.0;
        if (mobSpawner != null) {
            entity = mobSpawner.getMobEntity(world);
            yaw = mobSpawner.yaw;
            prevYaw = mobSpawner.prevYaw;
        }
        else {
            entity = this.initialisedItemEntities.get(this.tempID);
            yaw = LOTRTileEntityMobSpawnerRenderer.itemYaw;
            prevYaw = LOTRTileEntityMobSpawnerRenderer.prevItemYaw;
        }
        if (entity != null) {
            final float f2 = 0.4375f;
            GL11.glTranslatef(0.0f, 0.4f, 0.0f);
            GL11.glRotatef((float)(prevYaw + (yaw - prevYaw) * f) * 10.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.4f, 0.0f);
            GL11.glScalef(f2, f2, f2);
            entity.setLocationAndAngles(d, d1, d2, 0.0f, 0.0f);
            RenderManager.instance.func_147940_a(entity, 0.0, 0.0, 0.0, 0.0f, f);
        }
        GL11.glPopMatrix();
    }
    
    public void renderInvMobSpawner(final int i) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.tempID = i;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushAttrib(1048575);
        this.renderTileEntityAt(null, 0.0, 0.0, 0.0, LOTRTickHandlerClient.renderTick);
        GL11.glPopAttrib();
        this.tempID = 0;
        GL11.glPopMatrix();
    }
}
