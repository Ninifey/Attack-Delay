// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.util.ChunkCoordinates;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;

public class LOTRTeleporterMorgulPortal extends Teleporter
{
    private final WorldServer theWorld;
    private final LongHashMap portalPositions;
    private final List portalTimes;
    private Random rand;
    
    public LOTRTeleporterMorgulPortal(final WorldServer world) {
        super(world);
        this.portalPositions = new LongHashMap();
        this.portalTimes = new ArrayList();
        this.rand = new Random();
        this.theWorld = world;
    }
    
    public void placeInPortal(final Entity entity, final double d, final double d1, final double d2, final float f) {
        if (!this.placeInExistingPortal(entity, d, d1, d2, f)) {
            this.makePortal(entity);
            this.placeInExistingPortal(entity, d, d1, d2, f);
        }
    }
    
    public boolean placeInExistingPortal(final Entity entity, final double d, final double d1, final double d2, final float f) {
        final short range = 128;
        double distanceToPortal = -1.0;
        int i = 0;
        int j = 0;
        int k = 0;
        final int posX = MathHelper.floor_double(entity.posX);
        final int posZ = MathHelper.floor_double(entity.posZ);
        final long chunkLocation = ChunkCoordIntPair.chunkXZ2Int(posX, posZ);
        boolean isChunkLocationInPortalPositions = true;
        if (this.portalPositions.containsItem(chunkLocation)) {
            final Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition)this.portalPositions.getValueByKey(chunkLocation);
            distanceToPortal = 0.0;
            i = ((ChunkCoordinates)portalposition).posX;
            j = ((ChunkCoordinates)portalposition).posY;
            k = ((ChunkCoordinates)portalposition).posZ;
            portalposition.lastUpdateTime = this.theWorld.getTotalWorldTime();
            isChunkLocationInPortalPositions = false;
        }
        else {
            for (int i2 = posX - range; i2 <= posX + range; ++i2) {
                final double xDistance = i2 + 0.5 - entity.posX;
                for (int k2 = posZ - range; k2 <= posZ + range; ++k2) {
                    final double zDistance = k2 + 0.5 - entity.posZ;
                    for (int j2 = this.theWorld.getActualHeight() - 1; j2 >= 0; --j2) {
                        boolean portalHere = true;
                        for (int i3 = i2 - 1; i3 <= i2 + 1; ++i3) {
                            for (int k3 = k2 - 1; k3 <= k2 + 1; ++k3) {
                                if (this.theWorld.getBlock(i3, j2, k3) != LOTRMod.morgulPortal) {
                                    portalHere = false;
                                }
                            }
                        }
                        if (portalHere) {
                            final double yDistance = j2 + 0.5 - entity.posY;
                            final double distanceSq = xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;
                            if (distanceToPortal < 0.0 || distanceSq < distanceToPortal) {
                                distanceToPortal = distanceSq;
                                i = i2;
                                j = j2;
                                k = k2;
                            }
                        }
                    }
                }
            }
        }
        if (distanceToPortal >= 0.0) {
            if (isChunkLocationInPortalPositions) {
                this.portalPositions.add(chunkLocation, (Object)new Teleporter.PortalPosition((Teleporter)this, i, j, k, this.theWorld.getTotalWorldTime()));
                this.portalTimes.add(chunkLocation);
            }
            final int side = this.rand.nextInt(4);
            switch (side) {
                case 0: {
                    entity.setLocationAndAngles(i - 2 + 0.5, (double)(j + 1), k - 1 + 0.25 + this.rand.nextFloat() * 2.0f, entity.rotationYaw, entity.rotationPitch);
                    break;
                }
                case 1: {
                    entity.setLocationAndAngles(i + 2 + 0.5, (double)(j + 1), k - 1 + 0.25 + this.rand.nextFloat() * 2.0f, entity.rotationYaw, entity.rotationPitch);
                    break;
                }
                case 2: {
                    entity.setLocationAndAngles(i - 1 + 0.25 + this.rand.nextFloat() * 2.0f, (double)(j + 1), k - 2 + 0.25, entity.rotationYaw, entity.rotationPitch);
                    break;
                }
                case 3: {
                    entity.setLocationAndAngles(i - 1 + 0.25 + this.rand.nextFloat() * 2.0f, (double)(j + 1), k + 2 + 0.25, entity.rotationYaw, entity.rotationPitch);
                    break;
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean makePortal(final Entity entity) {
        final byte range = 16;
        double distanceToPortal = -1.0;
        final int i = MathHelper.floor_double(entity.posX);
        final int j = MathHelper.floor_double(entity.posY);
        final int k = MathHelper.floor_double(entity.posZ);
        int posX = i;
        int posY = j;
        int posZ = k;
        for (int i2 = i - range; i2 <= i + range; ++i2) {
            final double xDistance = i2 + 0.5 - entity.posX;
            for (int k2 = k - range; k2 <= k + range; ++k2) {
                final double zDistance = k2 + 0.5 - entity.posZ;
            Label_0335:
                for (int j2 = this.theWorld.getActualHeight() - 1; j2 >= 0; --j2) {
                    if (this.theWorld.isAirBlock(i2, j2, k2)) {
                        while (j2 > 0 && this.theWorld.isAirBlock(i2, j2 - 1, k2)) {
                            --j2;
                        }
                        for (int i3 = i2 - 2; i3 <= i2 + 2; ++i3) {
                            for (int k3 = k2 - 2; k3 <= k2 + 2; ++k3) {
                                for (int j3 = -1; j3 <= 3; ++j3) {
                                    final int j4 = j2 + j3;
                                    if (j3 < 0 && !LOTRMod.isOpaque((World)this.theWorld, i3, j4, k3)) {
                                        continue Label_0335;
                                    }
                                    if (j3 >= 0 && !this.theWorld.isAirBlock(i3, j4, k3)) {
                                        continue Label_0335;
                                    }
                                }
                            }
                        }
                        final double yDistance = j2 + 0.5 - entity.posY;
                        final double distanceSq = xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;
                        if (distanceToPortal < 0.0 || distanceSq < distanceToPortal) {
                            distanceToPortal = distanceSq;
                            posX = i2;
                            posY = j2;
                            posZ = k2;
                        }
                    }
                }
            }
        }
        final int actualPosX = posX;
        int actualPosY = posY;
        final int actualPosZ = posZ;
        boolean generateRockBelow = false;
        if (distanceToPortal < 0.0) {
            if (posY < 70) {
                posY = 70;
            }
            if (posY > this.theWorld.getActualHeight() - 10) {
                posY = this.theWorld.getActualHeight() - 10;
            }
            actualPosY = posY;
            generateRockBelow = true;
        }
        for (int i4 = -2; i4 <= 2; ++i4) {
            for (int k4 = -2; k4 <= 2; ++k4) {
                for (int j2 = generateRockBelow ? -1 : 0; j2 <= 3; ++j2) {
                    final int i3 = actualPosX + i4;
                    final int j5 = actualPosY + j2;
                    final int k5 = actualPosZ + k4;
                    if (j2 > 0) {
                        if (Math.abs(i4) == 2 && Math.abs(k4) == 2) {
                            this.theWorld.setBlock(i3, j5, k5, LOTRMod.guldurilBrick, 0, 2);
                        }
                        else {
                            this.theWorld.setBlock(i3, j5, k5, Blocks.air, 0, 2);
                        }
                    }
                    else if (j2 < 0 && generateRockBelow) {
                        this.theWorld.setBlock(i3, j5, k5, LOTRMod.rock, 0, 2);
                    }
                    else {
                        final boolean isFrame = i4 == -2 || i4 == 2 || k4 == -2 || k4 == 2;
                        this.theWorld.setBlock(i3, j5, k5, isFrame ? LOTRMod.rock : LOTRMod.morgulPortal, 0, 2);
                    }
                }
            }
        }
        for (int i4 = -3; i4 <= 3; ++i4) {
            for (int k4 = -3; k4 <= 3; ++k4) {
                for (int j2 = generateRockBelow ? -2 : -1; j2 <= 4; ++j2) {
                    final int i3 = actualPosX + i4;
                    final int j5 = actualPosY + j2;
                    final int k5 = actualPosZ + k4;
                    this.theWorld.notifyBlocksOfNeighborChange(i3, j5, k5, this.theWorld.getBlock(i3, j5, k5));
                }
            }
        }
        return true;
    }
    
    public void removeStalePortalLocations(final long time) {
        if (time % 100L == 0L) {
            final Iterator iterator = this.portalTimes.iterator();
            final long j = time - 600L;
            while (iterator.hasNext()) {
                final Long olong = iterator.next();
                final Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition)this.portalPositions.getValueByKey((long)olong);
                if (portalposition == null || portalposition.lastUpdateTime < j) {
                    iterator.remove();
                    this.portalPositions.remove((long)olong);
                }
            }
        }
    }
}
