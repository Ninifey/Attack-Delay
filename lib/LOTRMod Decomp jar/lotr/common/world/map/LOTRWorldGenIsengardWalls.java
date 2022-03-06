// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import com.google.common.math.IntMath;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;

public class LOTRWorldGenIsengardWalls extends LOTRWorldGenStructureBase2
{
    public static LOTRWorldGenIsengardWalls INSTANCE;
    private int centreX;
    private int centreZ;
    private int radius;
    private int radiusSq;
    private double wallThick;
    private int wallTop;
    private int gateBottom;
    private int gateTop;
    
    private LOTRWorldGenIsengardWalls(final boolean flag) {
        super(flag);
        this.radius = 400;
        this.radiusSq = this.radius * this.radius;
        this.wallThick = 0.03;
        this.wallTop = 100;
        this.gateBottom = 80;
        this.gateTop = this.wallTop - 3;
        this.centreX = LOTRWaypoint.ISENGARD.getXCoord();
        this.centreZ = LOTRWaypoint.ISENGARD.getZCoord();
    }
    
    private double isPosInWall(final int i, final int k) {
        final int dx = i - this.centreX;
        final int dz = k - this.centreZ;
        final int distSq = dx * dx + dz * dz;
        final double circleDist = Math.abs(distSq / (double)this.radiusSq - 1.0);
        return circleDist;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (this.isPosInWall(i + 8, k + 8) < this.wallThick * 3.0) {
            for (int i2 = i; i2 <= i + 15; ++i2) {
                for (int k2 = k; k2 <= k + 15; ++k2) {
                    final double circleDist = this.isPosInWall(i2, k2);
                    if (circleDist < 0.03) {
                        final int dx = i2 - this.centreX;
                        final int dz = k2 - this.centreX;
                        final float roadNear = LOTRRoads.isRoadNear(i2, k2, 9);
                        final boolean gate = roadNear >= 0.0f;
                        boolean fences = false;
                        final boolean wallEdge = circleDist > 0.025;
                        final boolean ladder = wallEdge && Math.abs(dx) == 16 && dz < this.radius;
                        for (int j2 = this.wallTop; j2 > 0; --j2) {
                            if (fences) {
                                this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.fence, 3);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.brick2, 11);
                                if (wallEdge && j2 == this.wallTop && !ladder) {
                                    this.setBlockAndMetadata(world, i2, j2 + 1, k2, LOTRMod.brick2, 11);
                                    if (IntMath.mod(i2 + k2, 2) == 1) {
                                        this.setBlockAndMetadata(world, i2, j2 + 2, k2, LOTRMod.slabSingle5, 3);
                                    }
                                    else if (this.isTorchAt(i2, k2)) {
                                        this.setBlockAndMetadata(world, i2, j2 + 2, k2, LOTRMod.orcTorch, 0);
                                        this.setBlockAndMetadata(world, i2, j2 + 3, k2, LOTRMod.orcTorch, 1);
                                    }
                                }
                                if (ladder) {
                                    this.setBlockAndMetadata(world, i2, j2, k2 - 1, Blocks.ladder, 2);
                                }
                            }
                            final Block below = this.getBlock(world, i2, j2 - 1, k2);
                            this.setGrassToDirt(world, i2, j2 - 1, k2);
                            if (below == Blocks.grass || below == Blocks.dirt) {
                                break;
                            }
                            if (below == Blocks.stone) {
                                break;
                            }
                            if (gate) {
                                if (fences) {
                                    if (j2 == this.gateBottom) {
                                        break;
                                    }
                                }
                                else {
                                    final int lerpGateTop = this.gateBottom + Math.round((this.gateTop - this.gateBottom) * MathHelper.sqrt_float(1.0f - roadNear));
                                    if (j2 == lerpGateTop) {
                                        if (circleDist <= 0.025) {
                                            break;
                                        }
                                        fences = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean isTorchAt(final int i, final int k) {
        final int torchRange = 12;
        final int xmod = IntMath.mod(i, torchRange);
        final int zmod = IntMath.mod(k, torchRange);
        return IntMath.mod(xmod + zmod, torchRange) == 0;
    }
    
    @Override
    protected int getX(final int x, final int z) {
        return x;
    }
    
    @Override
    protected int getZ(final int x, final int z) {
        return z;
    }
    
    @Override
    protected int getY(final int y) {
        return y;
    }
    
    @Override
    protected int rotateMeta(final Block block, final int meta) {
        return meta;
    }
    
    static {
        LOTRWorldGenIsengardWalls.INSTANCE = new LOTRWorldGenIsengardWalls(false);
    }
}
