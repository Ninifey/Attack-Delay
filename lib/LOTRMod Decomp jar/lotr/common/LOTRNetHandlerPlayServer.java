// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.world.World;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountControlServerEnforce;
import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.LOTRMountFunctions;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Doubles;
import lotr.common.network.LOTRPacketMountControl;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.NetHandlerPlayServer;

public class LOTRNetHandlerPlayServer extends NetHandlerPlayServer
{
    private MinecraftServer theServer;
    private double defaultReach;
    private int lastAttackTime;
    private double lastX;
    private double lastY;
    private double lastZ;
    private int floatingMountTick;
    
    public LOTRNetHandlerPlayServer(final MinecraftServer server, final NetworkManager nm, final EntityPlayerMP entityplayer) {
        super(server, nm, entityplayer);
        this.defaultReach = -1.0;
        this.lastAttackTime = 0;
        this.theServer = server;
    }
    
    public void update() {
        this.updateAttackTime();
    }
    
    public void processInput(final C0CPacketInput packet) {
        super.processInput(packet);
        final float forward = packet.func_149616_d();
        final float strafing = packet.func_149620_c();
        final boolean jump = packet.func_149618_e();
        if (forward != 0.0f || strafing != 0.0f || jump) {
            LOTRLevelData.getData((EntityPlayer)super.playerEntity).cancelFastTravel();
        }
    }
    
    public void processPlayer(final C03PacketPlayer packet) {
        super.processPlayer(packet);
        if (!super.playerEntity.isRiding() && packet.func_149466_j()) {
            final double newX = packet.func_149464_c();
            final double newY = packet.func_149467_d();
            final double newZ = packet.func_149472_e();
            if (newX != this.lastX || newY != this.lastY || newZ != this.lastZ) {
                LOTRLevelData.getData((EntityPlayer)super.playerEntity).cancelFastTravel();
            }
        }
        this.lastX = ((Entity)super.playerEntity).posX;
        this.lastY = ((Entity)super.playerEntity).posY;
        this.lastZ = ((Entity)super.playerEntity).posZ;
    }
    
    public void processMountControl(final LOTRPacketMountControl packet) {
        final double x = packet.posX;
        final double y = packet.posY;
        final double z = packet.posZ;
        final float yaw = packet.rotationYaw;
        final float pitch = packet.rotationPitch;
        if (!Doubles.isFinite(x) || !Doubles.isFinite(y) || !Doubles.isFinite(z) || !Floats.isFinite(yaw) || !Floats.isFinite(pitch)) {
            super.playerEntity.playerNetServerHandler.kickPlayerFromServer("Invalid mount movement");
            return;
        }
        final Entity mount = ((Entity)super.playerEntity).ridingEntity;
        if (mount != null && mount != super.playerEntity && mount.riddenByEntity == super.playerEntity && LOTRMountFunctions.isMountControllable(mount)) {
            final WorldServer world = super.playerEntity.getServerForPlayer();
            final MinecraftServer server = world.func_73046_m();
            final double d0 = mount.posX;
            final double d2 = mount.posY;
            final double d3 = mount.posZ;
            double dx = x - d0;
            double dy = y - d2;
            double dz = z - d3;
            final double speedSq = mount.motionX * mount.motionX + mount.motionY * mount.motionY + mount.motionZ * mount.motionZ;
            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq - speedSq > 150.0 && (!server.isSinglePlayer() || !server.getServerOwner().equals(super.playerEntity.getCommandSenderName()))) {
                FMLLog.warning(mount.getCommandSenderName() + " (mount of " + super.playerEntity.getCommandSenderName() + ") moved too quickly! " + (distSq - speedSq), new Object[0]);
                final LOTRPacketMountControlServerEnforce pktClient = new LOTRPacketMountControlServerEnforce(mount);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)pktClient, super.playerEntity);
                return;
            }
            final double check = 0.0625;
            final boolean noCollideBeforeMove = world.getCollidingBoundingBoxes(mount, mount.boundingBox.copy().contract(check, check, check)).isEmpty();
            dx = x - d0;
            dy = y - d2 - 1.0E-6;
            dz = z - d3;
            mount.moveEntity(dx, dy, dz);
            final double movedY = dy;
            dx = x - mount.posX;
            dy = y - mount.posY;
            dz = z - mount.posZ;
            if (dy > -0.5 || dy < 0.5) {
                dy = 0.0;
            }
            distSq = dx * dx + dy * dy + dz * dz;
            boolean clientServerConflict = false;
            if (distSq > 10.0) {
                clientServerConflict = true;
                FMLLog.warning(mount.getCommandSenderName() + " (mount of " + super.playerEntity.getCommandSenderName() + ") moved wrongly! " + dx + ", " + dy + ", " + dz, new Object[0]);
            }
            mount.setPositionAndRotation(x, y, z, yaw, pitch);
            super.playerEntity.setPositionAndRotation(x, y, z, yaw, pitch);
            final boolean noCollideAfterMove = world.getCollidingBoundingBoxes(mount, mount.boundingBox.copy().contract(check, check, check)).isEmpty();
            if (noCollideBeforeMove && (clientServerConflict || !noCollideAfterMove)) {
                mount.setPositionAndRotation(d0, d2, d3, yaw, pitch);
                super.playerEntity.setPositionAndRotation(d0, d2, d3, yaw, pitch);
                final LOTRPacketMountControlServerEnforce pktClient2 = new LOTRPacketMountControlServerEnforce(mount);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)pktClient2, super.playerEntity);
                return;
            }
            final AxisAlignedBB flyCheckBox = mount.boundingBox.copy().expand(check, check, check).addCoord(0.0, -0.55, 0.0);
            if (!server.isFlightAllowed() && !world.checkBlockCollision(flyCheckBox)) {
                if (movedY >= -0.03125) {
                    ++this.floatingMountTick;
                    if (this.floatingMountTick > 80) {
                        FMLLog.warning(super.playerEntity.getCommandSenderName() + " was kicked for floating too long on mount " + mount.getCommandSenderName() + "!", new Object[0]);
                        this.kickPlayerFromServer("Flying is not enabled on this server");
                        return;
                    }
                }
            }
            else {
                this.floatingMountTick = 0;
            }
            server.getConfigurationManager().serverUpdateMountedMovingPlayer(super.playerEntity);
            super.playerEntity.addMovementStat(((Entity)super.playerEntity).posX - d0, ((Entity)super.playerEntity).posY - d2, ((Entity)super.playerEntity).posZ - d3);
        }
    }
    
    public void processUseEntity(final C02PacketUseEntity packet) {
        final WorldServer world = this.theServer.worldServerForDimension(((Entity)super.playerEntity).dimension);
        final Entity target = packet.func_149564_a((World)world);
        super.playerEntity.func_143004_u();
        if (target != null) {
            final ItemStack itemstack = super.playerEntity.getHeldItem();
            double reach = LOTRWeaponStats.getMeleeReachDistance((EntityPlayer)super.playerEntity);
            reach += LOTRWeaponStats.getMeleeExtraLookWidth();
            reach += target.getCollisionBorderSize();
            final int attackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
            if (super.playerEntity.getDistanceSqToEntity(target) < reach * reach) {
                if (packet.func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
                    super.playerEntity.interactWith(target);
                }
                else if (packet.func_149565_c() == C02PacketUseEntity.Action.ATTACK && (this.lastAttackTime <= 0 || !(target instanceof EntityLivingBase))) {
                    if (target instanceof EntityItem || target instanceof EntityXPOrb || target instanceof EntityArrow || target == super.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.theServer.logWarning("Player " + super.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
                        return;
                    }
                    super.playerEntity.attackTargetEntityWithCurrentItem(target);
                    this.lastAttackTime = attackTime;
                }
            }
        }
    }
    
    public void updateAttackTime() {
        if (this.lastAttackTime > 0) {
            --this.lastAttackTime;
        }
    }
    
    public void processPlayerDigging(final C07PacketPlayerDigging packet) {
        this.setBlockReach();
        super.processPlayerDigging(packet);
    }
    
    public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement packet) {
        this.setBlockReach();
        super.processPlayerBlockPlacement(packet);
    }
    
    private void setBlockReach() {
        if (this.defaultReach == -1.0) {
            this.defaultReach = super.playerEntity.theItemInWorldManager.getBlockReachDistance();
        }
        double reach = this.defaultReach;
        reach *= LOTRWeaponStats.getMeleeReachFactor(super.playerEntity.getHeldItem());
        super.playerEntity.theItemInWorldManager.setBlockReachDistance(reach);
    }
}
