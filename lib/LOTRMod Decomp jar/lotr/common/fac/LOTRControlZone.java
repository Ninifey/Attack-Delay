// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fac;

import lotr.common.world.map.LOTRWaypoint;

public class LOTRControlZone
{
    public final int mapX;
    public final int mapY;
    public final int radius;
    public final int xCoord;
    public final int zCoord;
    public final int radiusCoord;
    public final long radiusCoordSq;
    
    public LOTRControlZone(final int x, final int y, final int r) {
        this.mapX = x;
        this.mapY = y;
        this.radius = r;
        this.xCoord = LOTRWaypoint.mapToWorldX(this.mapX);
        this.zCoord = LOTRWaypoint.mapToWorldZ(this.mapY);
        this.radiusCoord = LOTRWaypoint.mapToWorldR(this.radius);
        this.radiusCoordSq = this.radiusCoord * (long)this.radiusCoord;
    }
    
    public LOTRControlZone(final LOTRWaypoint wp, final int r) {
        this(wp.getX(), wp.getY(), r);
    }
    
    public boolean inZone(final double x, final double y, final double z, final int extraMapRange) {
        final double dx = x - this.xCoord;
        final double dz = z - this.zCoord;
        final double distSq = dx * dx + dz * dz;
        if (extraMapRange == 0) {
            return distSq <= this.radiusCoordSq;
        }
        final int checkRadius = LOTRWaypoint.mapToWorldR(this.radius + extraMapRange);
        final long checkRadiusSq = checkRadius * (long)checkRadius;
        return distSq <= checkRadiusSq;
    }
    
    public boolean intersectsWith(final LOTRControlZone other, final int extraMapRadius) {
        final double dx = other.xCoord - this.xCoord;
        final double dz = other.zCoord - this.zCoord;
        final double distSq = dx * dx + dz * dz;
        final double r12 = this.radiusCoord + other.radiusCoord + LOTRWaypoint.mapToWorldR(extraMapRadius * 2);
        final double r12Sq = r12 * r12;
        return distSq <= r12Sq;
    }
}
