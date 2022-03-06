// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import lotr.common.LOTRDimension;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;

public class LOTRTeleporter extends Teleporter
{
    private WorldServer world;
    private boolean makeRingPortal;
    
    public LOTRTeleporter(final WorldServer worldserver, final boolean flag) {
        super(worldserver);
        this.world = worldserver;
        this.makeRingPortal = flag;
    }
    
    public void placeInPortal(final Entity entity, final double d, final double d1, final double d2, final float f) {
        int i;
        int k;
        int j;
        if (((World)this.world).provider.dimensionId == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            i = 0;
            k = 0;
            j = LOTRMod.getTrueTopBlock((World)this.world, i, k);
        }
        else {
            i = LOTRLevelData.overworldPortalX;
            k = LOTRLevelData.overworldPortalZ;
            j = LOTRLevelData.overworldPortalY;
        }
        entity.setLocationAndAngles(i + 0.5, j + 1.0, k + 0.5, entity.rotationYaw, 0.0f);
        if (((World)this.world).provider.dimensionId == LOTRDimension.MIDDLE_EARTH.dimensionID && LOTRLevelData.madeMiddleEarthPortal == 0) {
            LOTRLevelData.setMadeMiddleEarthPortal(1);
            if (this.makeRingPortal) {
                if (((World)this.world).provider instanceof LOTRWorldProviderMiddleEarth) {
                    ((LOTRWorldProviderMiddleEarth)((World)this.world).provider).setRingPortalLocation(i, j, k);
                }
                final Entity portal = new LOTREntityPortal((World)this.world);
                portal.setLocationAndAngles(i + 0.5, j + 3.5, k + 0.5, 0.0f, 0.0f);
                this.world.spawnEntityInWorld(portal);
            }
        }
    }
}
