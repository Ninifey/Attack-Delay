// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenElvenForge extends LOTRWorldGenStructureBase2
{
    protected Block brickBlock;
    protected int brickMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block slabBlock;
    protected int slabMeta;
    protected Block carvedBrickBlock;
    protected int carvedBrickMeta;
    protected Block wallBlock;
    protected int wallMeta;
    protected Block stairBlock;
    protected Block torchBlock;
    protected Block tableBlock;
    protected Block barsBlock;
    protected Block woodBarsBlock;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofStairBlock;
    protected Block chestBlock;
    protected boolean ruined;
    
    public LOTRWorldGenElvenForge(final boolean flag) {
        super(flag);
        this.ruined = false;
        this.chestBlock = (Block)Blocks.chest;
    }
    
    protected abstract LOTREntityElf getElf(final World p0);
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                this.layFoundation(world, i3, k3, random);
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.layFoundation(world, i3, -5, random);
            this.layFoundation(world, i3, 5, random);
        }
        for (int k4 = -2; k4 <= 2; ++k4) {
            this.layFoundation(world, -5, k4, random);
            this.layFoundation(world, 5, k4, random);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.layFoundation(world, i3, -6, random);
        }
        this.placeStairs(world, -1, 1, -6, 1, random);
        this.placeStairs(world, 0, 1, -6, 2, random);
        this.placeStairs(world, 1, 1, -6, 0, random);
        for (int l = 0; l <= 3; ++l) {
            final int width = 4 - l;
            final int j3 = 7 + l;
            for (int i4 = -width; i4 <= width; ++i4) {
                this.placeRoofStairs(world, i4, j3, -width, 2, random);
                this.placeRoofStairs(world, i4, j3, width, 3, random);
                this.placeRoofStairs(world, i4, j3 - 1, -width, 7, random);
                this.placeRoofStairs(world, i4, j3 - 1, width, 6, random);
            }
            for (int k2 = -width + 1; k2 <= width - 1; ++k2) {
                this.placeRoofStairs(world, -width, j3, k2, 1, random);
                this.placeRoofStairs(world, width, j3, k2, 0, random);
                this.placeRoofStairs(world, -width, j3 - 1, k2, 4, random);
                this.placeRoofStairs(world, width, j3 - 1, k2, 5, random);
            }
            if (l < 3) {
                final int width2 = 2 - l;
                for (int i5 = -width2; i5 <= width2; ++i5) {
                    this.placeRoofStairs(world, i5, j3, -width - 1, 2, random);
                    this.placeRoofStairs(world, i5, j3, width + 1, 3, random);
                    this.placeRoof(world, i5, j3, -width, random);
                    this.placeRoof(world, i5, j3, width, random);
                }
                for (int k5 = -width2; k5 <= width2; ++k5) {
                    this.placeRoofStairs(world, -width - 1, j3, k5, 1, random);
                    this.placeRoofStairs(world, width + 1, j3, k5, 0, random);
                    this.placeRoof(world, -width, j3, k5, random);
                    this.placeRoof(world, width, j3, k5, random);
                }
                if (width2 > 0) {
                    for (int l2 = 0; l2 <= 1; ++l2) {
                        for (int l3 = 0; l3 <= 1; ++l3) {
                            final int l4 = IntMath.pow(-1, l3);
                            this.placeRoofStairs(world, -width2, j3, l4 * (width + l2), 1, random);
                            this.placeRoofStairs(world, width2, j3, l4 * (width + l2), 0, random);
                            this.placeRoofStairs(world, l4 * (width + l2), j3, -width2, 2, random);
                            this.placeRoofStairs(world, l4 * (width + l2), j3, width2, 3, random);
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 10, -1, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 0, 10, 1, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, -1, 10, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 1, 10, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        this.placeRoofStairs(world, 0, 11, -1, 2, random);
        this.placeRoofStairs(world, 0, 11, 1, 3, random);
        this.placeRoofStairs(world, -1, 11, 0, 1, random);
        this.placeRoofStairs(world, 1, 11, 0, 0, random);
        this.buildPillar(world, -5, -2, random);
        this.buildPillar(world, -5, 2, random);
        this.buildPillar(world, 5, -2, random);
        this.buildPillar(world, 5, 2, random);
        this.buildPillar(world, -2, -5, random);
        this.buildPillar(world, 2, -5, random);
        this.buildPillar(world, -2, 5, random);
        this.buildPillar(world, 2, 5, random);
        this.buildPillar(world, -4, -4, random);
        this.buildPillar(world, -4, 4, random);
        this.buildPillar(world, 4, -4, random);
        this.buildPillar(world, 4, 4, random);
        this.buildWall(world, 2, -4, random);
        this.buildWall(world, 3, -4, random);
        this.buildWall(world, 4, -3, random);
        this.buildWall(world, 4, -2, random);
        this.buildWall(world, 5, -1, random);
        this.buildWall(world, 5, 0, random);
        this.buildWall(world, 5, 1, random);
        this.buildWall(world, 4, 2, random);
        this.buildWall(world, 4, 3, random);
        this.buildWall(world, 3, 4, random);
        this.buildWall(world, 2, 4, random);
        this.buildWall(world, 1, 5, random);
        this.buildWall(world, 0, 5, random);
        this.buildWall(world, -1, 5, random);
        this.buildWall(world, -2, 4, random);
        this.buildWall(world, -3, 4, random);
        this.buildWall(world, -4, 3, random);
        this.buildWall(world, -4, 2, random);
        this.buildWall(world, -5, 1, random);
        this.buildWall(world, -5, 0, random);
        this.buildWall(world, -5, -1, random);
        this.buildWall(world, -4, -2, random);
        this.buildWall(world, -4, -3, random);
        this.buildWall(world, -3, -4, random);
        this.buildWall(world, -2, -4, random);
        this.placeStairs(world, -1, 2, -5, 0, random);
        this.placeStairs(world, 1, 2, -5, 1, random);
        if (!this.ruined) {
            this.setBlockAndMetadata(world, -1, 5, -5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 0, 5, -5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 1, 5, -5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, -4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 3, 5, -4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, -3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, -2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, -1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, 0, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, 1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, 2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, 3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 3, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 1, 5, 5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 0, 5, 5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -1, 5, 5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -2, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -3, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, 3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, 2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -5, 5, 1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -5, 5, 0, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -5, 5, -1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, -2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, -3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -3, 5, -4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -2, 5, -4, this.woodBarsBlock, 0);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.placePillar(world, i3, 6, -5, random);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                final int i6 = Math.abs(i3);
                final int k6 = Math.abs(k3);
                if ((i6 <= 2 && k6 <= 2) || i6 == 0 || k6 == 0) {
                    this.placePillar(world, i3, 1, k3, random);
                }
            }
        }
        if (!this.ruined) {
            for (int i3 = -4; i3 <= 4; i3 += 8) {
                this.setBlockAndMetadata(world, i3, 2, -1, Blocks.anvil, 0);
                this.setBlockAndMetadata(world, i3, 2, 1, this.tableBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, -4, 2, 0, LOTRMod.elvenForge, 4);
        this.setBlockAndMetadata(world, 4, 2, 0, LOTRMod.elvenForge, 5);
        if (!this.ruined) {
            this.placeChest(world, random, -1, 2, 4, this.chestBlock, 2, LOTRChestContents.ELVEN_FORGE);
            this.setBlockAndMetadata(world, 0, 2, 4, Blocks.crafting_table, 0);
            this.placeChest(world, random, 1, 2, 4, this.chestBlock, 2, LOTRChestContents.ELVEN_FORGE);
        }
        this.setBlockAndMetadata(world, 0, 1, -2, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 0, 1, 2, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, -2, 1, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 2, 1, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                final int i6 = Math.abs(i3);
                final int k6 = Math.abs(k3);
                if (i6 == 1 && k6 == 1) {
                    this.placePillar(world, i3, 2, k3, random);
                    this.placePillar(world, i3, 3, k3, random);
                    this.placeSlab(world, i3, 4, k3, false, random);
                }
                if (i6 + k6 == 1) {
                    for (int j2 = 2; j2 <= 9; ++j2) {
                        this.placeBrick(world, i3, j2, k3, random);
                    }
                }
            }
        }
        if (!this.ruined) {
            this.setBlockAndMetadata(world, 0, 2, 0, Blocks.lava, 0);
        }
        this.setBlockAndMetadata(world, 0, 2, -1, LOTRMod.elvenForge, 2);
        this.setBlockAndMetadata(world, 0, 3, -1, this.barsBlock, 0);
        if (!this.ruined) {
            this.setBlockAndMetadata(world, -1, 5, -1, this.getTorchBlock(random), 1);
            this.setBlockAndMetadata(world, 1, 5, -1, this.getTorchBlock(random), 2);
            this.setBlockAndMetadata(world, -1, 5, 1, this.getTorchBlock(random), 1);
            this.setBlockAndMetadata(world, 1, 5, 1, this.getTorchBlock(random), 2);
        }
        final LOTREntityElf elf = this.getElf(world);
        if (elf != null) {
            this.spawnNPCAndSetHome(elf, world, 0, 2, -2, 8);
        }
        return true;
    }
    
    private void layFoundation(final World world, final int i, final int k, final Random random) {
        for (int j = 0; !this.isOpaque(world, i, j, k) && this.getY(j) >= 0; --j) {
            this.placeBrick(world, i, j, k, random);
            this.setGrassToDirt(world, i, j - 1, k);
        }
        this.placeBrick(world, i, 1, k, random);
        for (int j = 2; j <= 6; ++j) {
            this.setAir(world, i, j, k);
        }
    }
    
    private void buildPillar(final World world, final int i, final int k, final Random random) {
        for (int j = 1; j <= 6; ++j) {
            this.placePillar(world, i, j, k, random);
        }
    }
    
    private void buildWall(final World world, final int i, final int k, final Random random) {
        this.placePillar(world, i, 2, k, random);
        this.placeWall(world, i, 3, k, random);
        this.placePillar(world, i, 6, k, random);
    }
    
    protected void placeBrick(final World world, final int i, final int j, final int k, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
    }
    
    protected void placePillar(final World world, final int i, final int j, final int k, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.pillarBlock, this.pillarMeta);
    }
    
    protected void placeSlab(final World world, final int i, final int j, final int k, final boolean flag, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.slabBlock, flag ? (this.slabMeta | 0x8) : this.slabMeta);
    }
    
    protected void placeWall(final World world, final int i, final int j, final int k, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.wallBlock, this.wallMeta);
    }
    
    protected void placeStairs(final World world, final int i, final int j, final int k, final int meta, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.stairBlock, meta);
    }
    
    protected void placeRoof(final World world, final int i, final int j, final int k, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.roofBlock, this.roofMeta);
    }
    
    protected void placeRoofStairs(final World world, final int i, final int j, final int k, final int meta, final Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.roofStairBlock, meta);
    }
    
    protected Block getTorchBlock(final Random random) {
        return this.torchBlock;
    }
}
