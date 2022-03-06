// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.world.Teleporter;
import net.minecraft.server.MinecraftServer;
import lotr.common.world.LOTRTeleporterUtumno;
import lotr.common.LOTRDimension;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketUtumnoReturn;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityUtumnoReturnPortal extends TileEntity
{
    public static int PORTAL_TOP;
    private int beamCheck;
    public int ticksExisted;
    
    public LOTRTileEntityUtumnoReturnPortal() {
        this.beamCheck = 0;
    }
    
    public void updateEntity() {
        ++this.ticksExisted;
        if (!super.worldObj.isClient) {
            if (this.beamCheck % 20 == 0) {
                final int i = super.xCoord;
                final int k = super.zCoord;
                for (int j = super.yCoord + 1; j <= LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP; ++j) {
                    super.worldObj.setBlock(i, j, k, LOTRMod.utumnoReturnLight, 0, 3);
                }
            }
            ++this.beamCheck;
        }
        final List nearbyEntities = super.worldObj.getEntitiesWithinAABB((Class)Entity.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP, (double)(super.zCoord + 1)));
        for (final Object obj : nearbyEntities) {
            final Entity entity = (Entity)obj;
            if (LOTRMod.getNPCFaction(entity) == LOTRFaction.UTUMNO) {
                continue;
            }
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)entity;
                if (entityplayer.capabilities.isFlying) {
                    continue;
                }
            }
            if (!super.worldObj.isClient) {
                final float accel = 0.2f;
                final Entity entity2 = entity;
                entity2.motionY += accel;
                entity.motionY = Math.max(entity.motionY, 0.0);
                entity.setPosition(super.xCoord + 0.5, entity.boundingBox.minY, super.zCoord + 0.5);
                entity.fallDistance = 0.0f;
            }
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)entity;
                LOTRMod.proxy.setInUtumnoReturnPortal(entityplayer);
                if (entityplayer instanceof EntityPlayerMP) {
                    final EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
                    final LOTRPacketUtumnoReturn packet = new LOTRPacketUtumnoReturn(((Entity)entityplayer).posX, ((Entity)entityplayer).posZ);
                    LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayermp);
                    entityplayermp.playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity((Entity)entityplayer));
                }
            }
            if (super.worldObj.isClient || entity.posY < LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP - 5.0) {
                continue;
            }
            final int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
            final LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
            if (entity instanceof EntityPlayerMP) {
                final EntityPlayerMP entityplayer2 = (EntityPlayerMP)entity;
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer2, dimension, (Teleporter)teleporter);
                LOTRLevelData.getData((EntityPlayer)entityplayer2).addAchievement(LOTRAchievement.leaveUtumno);
            }
            else {
                LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return LOTRTileEntityUtumnoReturnPortal.INFINITE_EXTENT_AABB;
    }
    
    static {
        LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP = 250;
    }
}
