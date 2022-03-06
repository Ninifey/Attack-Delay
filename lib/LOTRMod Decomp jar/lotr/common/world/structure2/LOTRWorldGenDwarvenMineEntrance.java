// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenDwarvenMineEntrance extends LOTRWorldGenStructureBase2
{
    private Block plankBlock;
    private int plankMeta;
    private Block plankSlabBlock;
    private int plankSlabMeta;
    private Block logBlock;
    private int logMeta;
    private Block fenceBlock;
    private int fenceMeta;
    public boolean isRuined;
    
    public LOTRWorldGenDwarvenMineEntrance(final boolean flag) {
        super(flag);
        this.isRuined = false;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.plankBlock = Blocks.planks;
        this.plankMeta = 1;
        this.plankSlabBlock = (Block)Blocks.wooden_slab;
        this.plankSlabMeta = 1;
        this.logBlock = Blocks.log;
        this.logMeta = 1;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 1;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, (super.usingPlayer != null) ? 5 : 0);
        this.setupRandomBlocks(random);
        int coordDepth = 40;
        if (super.usingPlayer != null) {
            coordDepth = Math.max(this.getY(-30), 5);
        }
        final int relDepth = coordDepth - super.originY;
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j2 = 1; j2 <= 5; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                if (!this.isRuined) {
                    this.setBlockAndMetadata(world, i2, 0, k2, this.plankBlock, this.plankMeta);
                    if ((i3 == 4 && k3 >= 2) || (k3 == 4 && i3 >= 2)) {
                        this.setBlockAndMetadata(world, i2, 1, k2, this.fenceBlock, this.fenceMeta);
                    }
                    if ((i3 == 4 && k3 == 3) || (k3 == 4 && i3 == 3)) {
                        for (int j2 = 2; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, this.fenceBlock, this.fenceMeta);
                        }
                    }
                    if (i3 == 4 || k3 == 4) {
                        this.setBlockAndMetadata(world, i2, 4, k2, this.fenceBlock, this.fenceMeta);
                    }
                    if (i3 == 0 || k3 == 0) {
                        this.setBlockAndMetadata(world, i2, 4, k2, this.fenceBlock, this.fenceMeta);
                    }
                    if (i3 == 0 && k3 == 0) {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, this.fenceBlock, this.fenceMeta);
                        }
                    }
                    if (i3 == 4 || k3 == 4 || i3 == 0 || k3 == 0 || i3 + k3 <= 2) {
                        this.setBlockAndMetadata(world, i2, 5, k2, this.plankSlabBlock, this.plankSlabMeta);
                    }
                }
                else if (i3 == 4 || k3 == 4) {
                    this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.pillar, 0);
                }
                else {
                    this.setAir(world, i2, 0, k2);
                }
                if (i3 == 4 && k3 == 4) {
                    for (int j2 = 1; j2 <= 3; ++j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.pillar, 0);
                    }
                    if (!this.isRuined) {
                        this.setBlockAndMetadata(world, i2, 4, k2, LOTRMod.brick3, 12);
                        this.setBlockAndMetadata(world, i2, 5, k2, LOTRMod.pillar, 0);
                    }
                }
            }
        }
        for (int j3 = -1; j3 > relDepth && this.getY(j3) >= 0; --j3) {
            for (int i4 = -4; i4 <= 4; ++i4) {
                for (int k4 = -4; k4 <= 4; ++k4) {
                    final int i5 = Math.abs(i4);
                    final int k5 = Math.abs(k4);
                    if (i5 == 4 || k5 == 4) {
                        if (this.isRuined && random.nextInt(20) == 0) {
                            this.setAir(world, i4, j3, k4);
                        }
                        else if (this.isRuined && random.nextInt(4) == 0) {
                            this.setBlockAndMetadata(world, i4, j3, k4, LOTRMod.brick4, 5);
                        }
                        else {
                            this.setBlockAndMetadata(world, i4, j3, k4, LOTRMod.brick, 6);
                        }
                    }
                    else {
                        this.setAir(world, i4, j3, k4);
                    }
                }
            }
            this.setBlockAndMetadata(world, -3, j3, -3, LOTRMod.pillar, 0);
            this.setBlockAndMetadata(world, -3, j3, 3, LOTRMod.pillar, 0);
            this.setBlockAndMetadata(world, 3, j3, -3, LOTRMod.pillar, 0);
            this.setBlockAndMetadata(world, 3, j3, 3, LOTRMod.pillar, 0);
            if (!this.isRuined && IntMath.mod(j3, 6) == 3) {
                this.setBlockAndMetadata(world, -3, j3, -3, LOTRMod.brick3, 12);
                this.setBlockAndMetadata(world, -3, j3, 3, LOTRMod.brick3, 12);
                this.setBlockAndMetadata(world, 3, j3, -3, LOTRMod.brick3, 12);
                this.setBlockAndMetadata(world, 3, j3, 3, LOTRMod.brick3, 12);
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                if (!this.isOpaque(world, i2, relDepth, k2)) {
                    this.setBlockAndMetadata(world, i2, relDepth, k2, Blocks.stone, 0);
                }
            }
        }
        if (!this.isRuined) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    this.setBlockAndMetadata(world, i2, relDepth, k2, LOTRMod.pillar, 0);
                }
            }
        }
        else {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    int h = 0;
                    if (random.nextInt(5) == 0) {
                        h += 1 + random.nextInt(1);
                    }
                    for (int j4 = 0; j4 <= h; ++j4) {
                        if (random.nextBoolean()) {
                            this.setBlockAndMetadata(world, i2, relDepth + h, k2, LOTRMod.pillar, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, relDepth + h, k2, Blocks.stone, 0);
                        }
                    }
                }
            }
        }
        if (!this.isRuined) {
            for (int j3 = 1; j3 > relDepth && this.getY(j3) >= 0; --j3) {
                this.setBlockAndMetadata(world, 0, j3, 0, this.logBlock, this.logMeta);
                this.setBlockAndMetadata(world, 0, j3, -1, Blocks.ladder, 2);
                this.setBlockAndMetadata(world, 0, j3, 1, Blocks.ladder, 3);
                this.setBlockAndMetadata(world, -1, j3, 0, Blocks.ladder, 5);
                this.setBlockAndMetadata(world, 1, j3, 0, Blocks.ladder, 4);
            }
        }
        for (int j3 = relDepth + 1; j3 <= relDepth + 3; ++j3) {
            for (int i4 = -1; i4 <= 1; ++i4) {
                this.setAir(world, i4, j3, -4);
                this.setAir(world, i4, j3, 4);
            }
            for (int k2 = -1; k2 <= 1; ++k2) {
                this.setAir(world, -4, j3, k2);
                this.setAir(world, 4, j3, k2);
            }
        }
        for (int k6 = -1; k6 <= 1; ++k6) {
            this.setBlockAndMetadata(world, -4, relDepth + 1, k6, LOTRMod.slabSingle, 15);
            this.setBlockAndMetadata(world, 4, relDepth + 1, k6, LOTRMod.slabSingle, 15);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, relDepth + 1, -4, LOTRMod.slabSingle, 15);
        }
        if (!this.isRuined || random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, -4, relDepth + 1, 0, LOTRMod.dwarvenForge, 4);
        }
        if (!this.isRuined || random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, 4, relDepth + 1, 0, LOTRMod.dwarvenForge, 5);
        }
        if (!this.isRuined || random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, 0, relDepth + 1, -4, LOTRMod.dwarvenForge, 3);
        }
        return true;
    }
}
