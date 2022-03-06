// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.world.World;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRDimension;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;

public class LOTRTeleporterUtumno extends Teleporter
{
    private WorldServer worldObj;
    private int targetX;
    private int targetZ;
    
    private LOTRTeleporterUtumno(final WorldServer world) {
        super(world);
        this.worldObj = world;
    }
    
    public static LOTRTeleporterUtumno newTeleporter(final int dimension) {
        WorldServer world = DimensionManager.getWorld(dimension);
        if (world == null) {
            DimensionManager.initDimension(dimension);
            world = DimensionManager.getWorld(dimension);
        }
        return new LOTRTeleporterUtumno(world);
    }
    
    public void setTargetCoords(final int x, final int z) {
        this.targetX = x;
        this.targetZ = z;
    }
    
    public void placeInPortal(final Entity entity, final double d, final double d1, final double d2, final float f) {
        int i = 0;
        int j = 256;
        int k = 0;
        Label_0310: {
            if (((World)this.worldObj).provider.dimensionId == LOTRDimension.UTUMNO.dimensionID) {
                for (int l = 0; l < 10000; ++l) {
                    final int x = this.targetX;
                    final int z = this.targetZ;
                    final int y = LOTRUtumnoLevel.ICE.corridorBaseLevels[LOTRUtumnoLevel.ICE.corridorBaseLevels.length - 1];
                    final int targetFuzz = 32;
                    final int x2 = MathHelper.getRandomIntegerInRange(((World)this.worldObj).rand, x - targetFuzz, x + targetFuzz);
                    final int z2 = MathHelper.getRandomIntegerInRange(((World)this.worldObj).rand, z - targetFuzz, z + targetFuzz);
                    for (int yFuzz = 3, j2 = -yFuzz; j2 <= yFuzz; ++j2) {
                        final int y2 = y + j2;
                        if (this.worldObj.getBlock(x2, y2 - 1, z2).isOpaqueCube() && this.worldObj.isAirBlock(x2, y2, z2) && this.worldObj.isAirBlock(x2, y2, z2)) {
                            i = x2;
                            j = y2;
                            k = z2;
                            break Label_0310;
                        }
                    }
                }
            }
            else {
                final double randomDistance = MathHelper.getRandomDoubleInRange(((World)this.worldObj).rand, 40.0, 80.0);
                final float angle = ((World)this.worldObj).rand.nextFloat() * 3.1415927f * 2.0f;
                i = LOTRFixedStructures.UTUMNO_ENTRANCE.xCoord + (int)(randomDistance * MathHelper.sin(angle));
                k = LOTRFixedStructures.UTUMNO_ENTRANCE.zCoord + (int)(randomDistance * MathHelper.cos(angle));
                j = this.worldObj.getTopSolidOrLiquidBlock(i, k);
            }
        }
        entity.setLocationAndAngles(i + 0.5, j + 1.0, k + 0.5, entity.rotationYaw, 0.0f);
        entity.fallDistance = 0.0f;
    }
}
