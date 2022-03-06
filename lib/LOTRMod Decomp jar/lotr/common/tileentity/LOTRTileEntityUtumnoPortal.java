// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.world.Teleporter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.world.LOTRTeleporterUtumno;
import lotr.common.LOTRDimension;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRGuiMessageTypes;
import lotr.common.LOTRLevelData;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityUtumnoPortal extends TileEntity
{
    public static final int WIDTH = 3;
    public static final int HEIGHT = 30;
    public static final int PORTAL_ABOVE = 2;
    public static final int PORTAL_BELOW = 2;
    public static final int TARGET_COORDINATE_RANGE = 1000000;
    public static final int TARGET_FUZZ_RANGE = 32;
    private int targetX;
    private int targetZ;
    private int targetResetTick;
    private static final int targetResetTick_max = 1200;
    
    public void updateEntity() {
        if (super.worldObj.getBlock(super.xCoord, super.yCoord - 1, super.zCoord) == this.getBlockType()) {
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
        }
        if (!super.worldObj.isClient) {
            if (this.targetResetTick > 0) {
                --this.targetResetTick;
            }
            else {
                this.targetX = MathHelper.getRandomIntegerInRange(super.worldObj.rand, -1000000, 1000000);
                this.targetZ = MathHelper.getRandomIntegerInRange(super.worldObj.rand, -1000000, 1000000);
                this.targetResetTick = 1200;
            }
        }
        if (!super.worldObj.isClient) {
            final List players = super.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)(super.xCoord - 8), (double)super.yCoord, (double)(super.zCoord - 8), (double)(super.xCoord + 9), (double)(super.yCoord + 60), (double)(super.zCoord + 9)));
            for (final Object obj : players) {
                final EntityPlayer entityplayer = (EntityPlayer)obj;
                LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.UTUMNO_WARN);
            }
        }
        if (!super.worldObj.isClient && super.worldObj.rand.nextInt(2000) == 0) {
            String s = "ambient.cave.cave";
            if (super.worldObj.rand.nextBoolean()) {
                s = "lotr:wight.ambience";
            }
            final float volume = 6.0f;
            super.worldObj.playSoundEffect(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5, s, volume, 0.8f + super.worldObj.rand.nextFloat() * 0.2f);
        }
    }
    
    public void transferEntity(final Entity entity) {
        entity.fallDistance = 0.0f;
        if (!super.worldObj.isClient) {
            final LOTRTileEntityUtumnoPortal actingPortal = this.findActingTargetingPortal();
            final int dimension = LOTRDimension.UTUMNO.dimensionID;
            final LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
            teleporter.setTargetCoords(actingPortal.targetX, actingPortal.targetZ);
            if (entity instanceof EntityPlayerMP) {
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)entity, dimension, (Teleporter)teleporter);
            }
            else {
                LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
            }
            entity.fallDistance = 0.0f;
            actingPortal.targetResetTick = 1200;
        }
    }
    
    private LOTRTileEntityUtumnoPortal findActingTargetingPortal() {
        int i;
        for (int range = i = 8; i >= -range; --i) {
            for (int k = range; k >= -range; --k) {
                final int i2 = super.xCoord + i;
                final int k2 = super.zCoord + k;
                final int j1 = super.yCoord;
                if (super.worldObj.getBlock(i2, j1, k2) == this.getBlockType()) {
                    final TileEntity te = super.worldObj.getTileEntity(i2, j1, k2);
                    if (te instanceof LOTRTileEntityUtumnoPortal) {
                        return (LOTRTileEntityUtumnoPortal)te;
                    }
                }
            }
        }
        return this;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("TargetX", this.targetX);
        nbt.setInteger("TargetZ", this.targetZ);
        nbt.setInteger("TargetReset", this.targetResetTick);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.targetX = nbt.getInteger("TargetX");
        this.targetZ = nbt.getInteger("TargetZ");
        this.targetResetTick = nbt.getInteger("TargetReset");
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 2), (double)super.yCoord, (double)(super.zCoord - 2), (double)(super.xCoord + 3), (double)(super.yCoord + 30), (double)(super.zCoord + 3));
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        final double d = 256.0;
        return d * d;
    }
}
