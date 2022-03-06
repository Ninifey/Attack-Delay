// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import lotr.common.LOTRMod;
import lotr.common.LOTRConfig;
import net.minecraft.world.World;

public enum LOTRFixedStructures
{
    SPAWN(809.5, 729.5), 
    UTUMNO_ENTRANCE(1139.0, 394.0), 
    MORDOR_CHERRY_TREE(1630.0, 1170.0);
    
    public int xCoord;
    public int zCoord;
    public static long nanoTimeElapsed;
    
    private LOTRFixedStructures(final double x, final double z) {
        this.xCoord = LOTRWaypoint.mapToWorldX(x);
        this.zCoord = LOTRWaypoint.mapToWorldZ(z);
    }
    
    public boolean isAt(final int x, final int z) {
        return this.xCoord == x && this.zCoord == z;
    }
    
    public static boolean generatesAt(final int i, final int k, final int x, final int z) {
        return i >> 4 == x >> 4 && k >> 4 == z >> 4;
    }
    
    public static boolean generatesAtMapImageCoords(final int i, final int k, int x, int z) {
        x = LOTRWaypoint.mapToWorldX(x);
        z = LOTRWaypoint.mapToWorldZ(z);
        return generatesAt(i, k, x, z);
    }
    
    public static boolean[] _mountainNear_structureNear(final World world, final int x, final int z) {
        final long l = System.nanoTime();
        boolean mountainNear = false;
        boolean structureNear = false;
        if (hasMapFeatures(world)) {
            if (LOTRMountains.mountainAt(x, z)) {
                mountainNear = true;
            }
            structureNear = structureNear(world, x, z, 256);
            if (!structureNear) {
                for (final LOTRWaypoint wp : LOTRWaypoint.values()) {
                    final double dx = x - wp.getXCoord();
                    final double dz = z - wp.getZCoord();
                    final double distSq = dx * dx + dz * dz;
                    final double range = 256.0;
                    final double rangeSq = range * range;
                    if (distSq < rangeSq) {
                        structureNear = true;
                        break;
                    }
                }
            }
            if (!structureNear && LOTRRoads.isRoadNear(x, z, 32) >= 0.0f) {
                structureNear = true;
            }
        }
        LOTRFixedStructures.nanoTimeElapsed += System.nanoTime() - l;
        return new boolean[] { mountainNear, structureNear };
    }
    
    public static boolean structureNear(final World world, final int x, final int z, final int range) {
        for (final LOTRFixedStructures str : values()) {
            final double dx = x - str.xCoord;
            final double dz = z - str.zCoord;
            final double distSq = dx * dx + dz * dz;
            final double rangeSq = range * range;
            if (distSq < rangeSq) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasMapFeatures(final World world) {
        return LOTRConfig.generateMapFeatures && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
    }
}
