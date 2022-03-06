// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import lotr.common.entity.LOTREntities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockAnimalJar;
import net.minecraft.entity.EntityList;
import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityAnimalJar extends TileEntity
{
    private static final int PACKET_ALL = 0;
    private static final int PACKET_ROTATE = 1;
    private NBTTagCompound jarEntityData;
    private Entity jarEntity;
    public int ticksExisted;
    private float targetYaw;
    private boolean hasTargetYaw;
    
    public LOTRTileEntityAnimalJar() {
        this.ticksExisted = -1;
        this.hasTargetYaw = false;
    }
    
    public void updateEntity() {
        final Random rand = super.worldObj.rand;
        super.updateEntity();
        if (this.ticksExisted < 0) {
            this.ticksExisted = rand.nextInt(100);
        }
        ++this.ticksExisted;
        this.getOrCreateJarEntity();
        if (this.jarEntity != null) {
            this.jarEntity.ticksExisted = this.ticksExisted;
            final Entity jarEntity = this.jarEntity;
            final Entity jarEntity2 = this.jarEntity;
            final double posX = this.jarEntity.posX;
            jarEntity2.prevPosX = posX;
            jarEntity.lastTickPosX = posX;
            final Entity jarEntity3 = this.jarEntity;
            final Entity jarEntity4 = this.jarEntity;
            final double posY = this.jarEntity.posY;
            jarEntity4.prevPosY = posY;
            jarEntity3.lastTickPosY = posY;
            final Entity jarEntity5 = this.jarEntity;
            final Entity jarEntity6 = this.jarEntity;
            final double posZ = this.jarEntity.posZ;
            jarEntity6.prevPosZ = posZ;
            jarEntity5.lastTickPosZ = posZ;
            this.jarEntity.prevRotationYaw = this.jarEntity.rotationYaw;
            if (!super.worldObj.isClient) {
                if (this.jarEntity instanceof EntityLiving) {
                    final EntityLiving entityLiving;
                    final EntityLiving jarLiving = entityLiving = (EntityLiving)this.jarEntity;
                    ++entityLiving.livingSoundTime;
                    if (rand.nextInt(1000) < jarLiving.livingSoundTime) {
                        jarLiving.livingSoundTime = -jarLiving.getTalkInterval();
                        jarLiving.playLivingSound();
                    }
                    if (rand.nextInt(200) == 0) {
                        this.targetYaw = rand.nextFloat() * 360.0f;
                        this.sendJarPacket(1);
                        this.jarEntity.rotationYaw = this.targetYaw;
                    }
                }
            }
            else if (this.hasTargetYaw) {
                float delta = this.targetYaw - this.jarEntity.rotationYaw;
                delta = MathHelper.wrapAngleTo180_float(delta);
                delta *= 0.1f;
                final Entity jarEntity7 = this.jarEntity;
                jarEntity7.rotationYaw += delta;
                if (Math.abs(this.jarEntity.rotationYaw - this.targetYaw) <= 0.01f) {
                    this.hasTargetYaw = false;
                }
            }
        }
    }
    
    public void setEntityData(final NBTTagCompound nbt) {
        this.jarEntityData = nbt;
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public void clearEntityData() {
        this.jarEntity = null;
        this.setEntityData(null);
    }
    
    public NBTTagCompound getEntityData() {
        return this.jarEntityData;
    }
    
    public Entity getOrCreateJarEntity() {
        if (this.jarEntityData == null || this.jarEntityData.hasNoTags()) {
            return null;
        }
        if (this.jarEntity == null) {
            this.jarEntity = EntityList.createEntityFromNBT(this.jarEntityData, super.worldObj);
            this.jarEntity.ticksExisted = this.ticksExisted;
            final float[] coords = this.getInitialEntityCoords(this.jarEntity);
            this.jarEntity.setLocationAndAngles((double)coords[0], (double)coords[1], (double)coords[2], this.jarEntity.rotationYaw, this.jarEntity.rotationPitch);
        }
        return this.jarEntity;
    }
    
    private float[] getInitialEntityCoords(final Entity entity) {
        final float[] xyz = { super.xCoord + 0.5f, super.yCoord + this.getEntityHeight() - entity.height / 2.0f, super.zCoord + 0.5f };
        return xyz;
    }
    
    private float getEntityHeight() {
        final Block block = this.getBlockType();
        if (block instanceof LOTRBlockAnimalJar) {
            return ((LOTRBlockAnimalJar)block).getJarEntityHeight();
        }
        return 0.5f;
    }
    
    public float getEntityBobbing(final float f) {
        return MathHelper.sin((this.ticksExisted + f) * 0.2f) * 0.05f;
    }
    
    public boolean isEntityWatching() {
        this.getOrCreateJarEntity();
        return this.jarEntity instanceof LOTREntityButterfly;
    }
    
    public int getLightValue() {
        this.getOrCreateJarEntity();
        if (this.jarEntity instanceof LOTREntityButterfly) {
            final LOTREntityButterfly butterfly = (LOTREntityButterfly)this.jarEntity;
            if (butterfly.getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
                return 7;
            }
        }
        return -1;
    }
    
    public void setWorldObj(final World world) {
        super.setWorldObj(world);
        if (this.jarEntity != null) {
            this.jarEntity = null;
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.getOrCreateJarEntity();
        if (this.jarEntity != null && this.jarEntityData != null) {
            this.jarEntity.writeToNBTOptional(this.jarEntityData);
        }
        if (this.jarEntityData != null) {
            nbt.setTag("JarEntityData", (NBTBase)this.jarEntityData);
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("JarEntityData")) {
            this.jarEntityData = nbt.getCompoundTag("JarEntityData");
        }
        else if (nbt.hasKey("ButterflyData")) {
            (this.jarEntityData = nbt.getCompoundTag("ButterflyData")).setString("id", LOTREntities.getStringFromClass(LOTREntityButterfly.class));
        }
        if (this.jarEntity != null) {
            this.jarEntity.readFromNBT(this.jarEntityData);
        }
    }
    
    public Packet getDescriptionPacket() {
        return this.getJarPacket(0);
    }
    
    private Packet getJarPacket(final int type) {
        this.getOrCreateJarEntity();
        final NBTTagCompound data = new NBTTagCompound();
        data.setByte("JarPacketType", (byte)type);
        if (type == 0) {
            this.writeToNBT(data);
        }
        else if (type == 1) {
            data.setFloat("TargetYaw", this.targetYaw);
        }
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    private void sendJarPacket(final int type) {
        final Packet packet = this.getJarPacket(type);
        final int i = MathHelper.floor_double((double)super.xCoord) >> 4;
        final int k = MathHelper.floor_double((double)super.zCoord) >> 4;
        final PlayerManager playermanager = ((WorldServer)super.worldObj).getPlayerManager();
        final List players = super.worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, i, k)) {
                entityplayer.playerNetServerHandler.sendPacket(packet);
            }
        }
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity packet) {
        this.getOrCreateJarEntity();
        final NBTTagCompound data = packet.func_148857_g();
        int type = 0;
        if (data.hasKey("JarPacketType")) {
            type = data.getByte("JarPacketType");
        }
        if (type == 0) {
            this.readFromNBT(packet.func_148857_g());
        }
        else if (type == 1) {
            this.targetYaw = data.getFloat("TargetYaw");
            this.hasTargetYaw = true;
        }
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
    }
}
