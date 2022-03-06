// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorSmithy extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = 1; k2 <= 11; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int k3 = 1; k3 <= 11; ++k3) {
            for (int i3 = -4; i3 <= 4; ++i3) {
                final boolean pillar = Math.abs(i3) == 4 && (k3 == 1 || k3 == 11);
                if (pillar) {
                    for (int j3 = 4; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.pillar2Block, super.pillar2Meta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
                else {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    if (Math.abs(i3) == 4 || k3 == 1 || k3 == 11) {
                        for (int j3 = 1; j3 <= 3; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                        this.setBlockAndMetadata(world, i3, 4, k3, super.brickWallBlock, super.brickWallMeta);
                    }
                    else {
                        for (int j3 = 1; j3 <= 5; ++j3) {
                            this.setAir(world, i3, j3, k3);
                        }
                    }
                }
            }
        }
        for (int k3 = 3; k3 <= 7; k3 += 4) {
            for (int i3 = -2; i3 <= 1; i3 += 3) {
                for (int k4 = k3; k4 <= k3 + 2; ++k4) {
                    for (int i4 = i3; i4 <= i3 + 1; ++i4) {
                        this.setBlockAndMetadata(world, i4, 0, k4, super.rockBlock, super.rockMeta);
                    }
                }
            }
        }
        for (int i5 = -1; i5 <= 1; ++i5) {
            for (int j4 = 1; j4 <= 3; ++j4) {
                this.setBlockAndMetadata(world, i5, j4, 1, super.rockBlock, super.rockMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 1, super.fenceGateBlock, 0);
        this.setAir(world, 0, 2, 1);
        for (int k3 = 2; k3 <= 10; ++k3) {
            this.setBlockAndMetadata(world, -4, 4, k3, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 4, 4, k3, super.brickWallBlock, super.brickWallMeta);
        }
        for (int i5 = -3; i5 <= 3; ++i5) {
            this.setBlockAndMetadata(world, i5, 4, 1, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i5, 4, 11, super.brickWallBlock, super.brickWallMeta);
        }
        for (int k3 = 2; k3 <= 10; ++k3) {
            for (int i3 = -3; i3 <= 3; ++i3) {
                this.setBlockAndMetadata(world, i3, 5, k3, super.brick2Block, super.brick2Meta);
            }
        }
        for (int k3 = 2; k3 <= 10; ++k3) {
            this.setBlockAndMetadata(world, -4, 5, k3, super.brick2StairBlock, 1);
            this.setBlockAndMetadata(world, 4, 5, k3, super.brick2StairBlock, 0);
        }
        for (int i5 = -4; i5 <= 4; ++i5) {
            this.setBlockAndMetadata(world, i5, 5, 1, super.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, i5, 5, 11, super.brick2StairBlock, 3);
        }
        this.setBlockAndMetadata(world, -3, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -3, 1, 3, super.tableBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 2, 4, super.brickWallBlock, super.brickWallMeta);
        for (int k3 = 2; k3 <= 4; ++k3) {
            this.setBlockAndMetadata(world, -3, 3, k3, super.brickStairBlock, 0);
        }
        for (int k3 = 2; k3 <= 6; k3 += 2) {
            this.setBlockAndMetadata(world, 3, 1, k3, Blocks.anvil, 0);
        }
        this.placeChest(world, random, 3, 1, 8, (Block)Blocks.chest, 5, LOTRChestContents.GONDOR_SMITHY);
        this.placeChest(world, random, 3, 1, 9, (Block)Blocks.chest, 5, LOTRChestContents.GONDOR_SMITHY);
        this.setBlockAndMetadata(world, -1, 2, 2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 2, 2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 2, 6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 2, 6, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 1, 8, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.setBlockAndMetadata(world, -1, 2, 8, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.setBlockAndMetadata(world, -3, 1, 9, Blocks.lava, 0);
        this.setBlockAndMetadata(world, -2, 1, 9, Blocks.lava, 0);
        this.setBlockAndMetadata(world, -3, 1, 10, Blocks.lava, 0);
        this.setBlockAndMetadata(world, -2, 1, 10, Blocks.lava, 0);
        this.setBlockAndMetadata(world, -3, 3, 8, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -2, 3, 8, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 3, 8, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 3, 9, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 3, 10, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 8, LOTRMod.alloyForge, 2);
        this.setBlockAndMetadata(world, -2, 1, 8, LOTRMod.alloyForge, 2);
        this.setBlockAndMetadata(world, -1, 1, 9, LOTRMod.alloyForge, 4);
        this.setBlockAndMetadata(world, -1, 1, 10, LOTRMod.alloyForge, 4);
        world.setBlockMetadata(-3, 1, 8, 2, 3);
        world.setBlockMetadata(-2, 1, 8, 2, 3);
        world.setBlockMetadata(-1, 1, 9, 5, 3);
        world.setBlockMetadata(-1, 1, 10, 5, 3);
        this.setBlockAndMetadata(world, -3, 2, 8, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -2, 2, 8, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -1, 2, 9, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -1, 2, 10, super.barsBlock, 0);
        for (int i5 = -1; i5 <= 1; ++i5) {
            for (int k5 = -1; k5 <= 1; ++k5) {
                if (i5 != 0 || k5 != 0) {
                    this.setBlockAndMetadata(world, -3 + i5, 4, 10 + k5, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    this.setBlockAndMetadata(world, -3 + i5, 5, 10 + k5, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    this.setBlockAndMetadata(world, -3 + i5, 6, 10 + k5, super.rockSlabBlock, super.rockSlabMeta);
                }
            }
        }
        this.setAir(world, -3, 5, 10);
        final LOTREntityGondorBlacksmith blacksmith = new LOTREntityGondorBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 6, 4);
        return true;
    }
}
