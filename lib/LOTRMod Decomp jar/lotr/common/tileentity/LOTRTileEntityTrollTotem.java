// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import lotr.common.LOTRMod;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityTrollTotem extends TileEntity
{
    private int prevJawRotation;
    private int jawRotation;
    private boolean prevCanSummon;
    private boolean clientCanSummon;
    
    public void updateEntity() {
        if (!super.worldObj.isClient) {
            final boolean flag = this.canSummon();
            if (flag != this.prevCanSummon) {
                super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            }
            this.prevCanSummon = flag;
        }
        else {
            this.prevJawRotation = this.jawRotation;
            if (this.jawRotation < 60 && this.canSummon()) {
                ++this.jawRotation;
            }
            else if (this.jawRotation > 0 && !this.canSummon()) {
                --this.jawRotation;
            }
        }
    }
    
    public float getJawRotation(final float f) {
        final float rotation = this.prevJawRotation + (this.jawRotation - this.prevJawRotation) * f;
        return rotation / 60.0f * -35.0f;
    }
    
    public boolean canSummon() {
        if (super.worldObj.isClient) {
            return this.clientCanSummon;
        }
        if (super.worldObj.isDaytime()) {
            return false;
        }
        if (!super.worldObj.canBlockSeeTheSky(super.xCoord, super.yCoord, super.zCoord)) {
            return false;
        }
        if (this.getBlockType() == LOTRMod.trollTotem && (this.getBlockMetadata() & 0x3) == 0x0) {
            final int rotation = (this.getBlockMetadata() & 0xC) >> 2;
            if (super.worldObj.getBlock(super.xCoord, super.yCoord - 1, super.zCoord) == LOTRMod.trollTotem && super.worldObj.getBlockMetadata(super.xCoord, super.yCoord - 1, super.zCoord) == (0x1 | rotation << 2) && super.worldObj.getBlock(super.xCoord, super.yCoord - 2, super.zCoord) == LOTRMod.trollTotem && super.worldObj.getBlockMetadata(super.xCoord, super.yCoord - 2, super.zCoord) == (0x2 | rotation << 2)) {
                return true;
            }
        }
        return false;
    }
    
    public void summon() {
        if (!super.worldObj.isClient) {
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord - 1, super.zCoord);
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord - 2, super.zCoord);
            final LOTREntityMountainTrollChieftain troll = new LOTREntityMountainTrollChieftain(super.worldObj);
            troll.setLocationAndAngles(super.xCoord + 0.5, (double)(super.yCoord - 2), super.zCoord + 0.5, super.worldObj.rand.nextFloat() * 360.0f, 0.0f);
            troll.onSpawnWithEgg(null);
            super.worldObj.spawnEntityInWorld((Entity)troll);
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        data.setBoolean("CanSummon", this.canSummon());
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.clientCanSummon = data.getBoolean("CanSummon");
    }
}
