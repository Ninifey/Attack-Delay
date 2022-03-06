// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import java.util.List;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.ArrayList;
import lotr.common.item.LOTRItemBanner;
import lotr.common.entity.npc.LOTREntityTauredainPyramidWraith;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.IInventory;
import lotr.common.util.LOTRMazeGenerator;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainPyramid extends LOTRWorldGenStructureBase2
{
    public static int RADIUS;
    private boolean isGolden;
    private boolean carson;
    
    public LOTRWorldGenTauredainPyramid(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, int j, final int k, final int rotation) {
        final int depth = 20;
        j -= depth - 1;
        this.setOriginAndRotation(world, i, j, k, rotation, (super.usingPlayer != null) ? (LOTRWorldGenTauredainPyramid.RADIUS - depth) : 0);
        this.isGolden = (random.nextInt(20) == 0);
        this.carson = (random.nextInt(200) == 0);
        final int maze1R = 19;
        final int maze1W = maze1R * 2 + 1;
        final LOTRMazeGenerator maze1 = new LOTRMazeGenerator(maze1W, maze1W);
        maze1.setStart(maze1R + 0, maze1R + 4);
        for (int maze1CentreW = 3, i2 = -maze1CentreW - 1; i2 <= maze1CentreW + 1; ++i2) {
            for (int k2 = -maze1CentreW - 1; k2 <= maze1CentreW + 1; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 > maze1CentreW || k3 > maze1CentreW) {
                    maze1.exclude(maze1R + i2, maze1R + k2);
                }
                else {
                    maze1.clear(maze1R + i2, maze1R + k2);
                }
            }
        }
        maze1.generate(random);
        maze1.selectOuterEndpoint(random);
        final int[] maze1End = maze1.getEnd();
        final int maze2R = 25;
        final int maze2W = maze2R * 2 + 1;
        final LOTRMazeGenerator maze2 = new LOTRMazeGenerator(maze2W, maze2W);
        maze2.setStart(maze1End[0] + (maze2.xSize - maze1.xSize), maze1End[1] + (maze2.zSize - maze1.zSize));
        maze2.generate(random);
        maze2.selectOuterEndpoint(random);
        final int maze3R = 13;
        final int maze3W = maze3R * 2 + 1;
        final LOTRMazeGenerator maze3 = new LOTRMazeGenerator(maze3W, maze3W);
        maze3.setStart(maze3R + 0, maze3R + 2);
        for (int maze3CentreW = 1, i4 = -maze3CentreW - 1; i4 <= maze3CentreW + 1; ++i4) {
            for (int k4 = -maze3CentreW - 1; k4 <= maze3CentreW + 1; ++k4) {
                final int i5 = Math.abs(i4);
                final int k5 = Math.abs(k4);
                if (i5 > maze3CentreW || k5 > maze3CentreW) {
                    maze3.exclude(maze3R + i4, maze3R + 4 + k4);
                }
                else {
                    maze3.clear(maze3R + i4, maze3R + 4 + k4);
                }
            }
        }
        maze3.generate(random);
        maze3.selectOuterEndpoint(random);
        final IInventory[] chests = new IInventory[4];
        for (int l = 0; l < chests.length; ++l) {
            chests[l] = (IInventory)new InventoryBasic("drops", false, 27);
            final LOTRChestContents itemPool = LOTRChestContents.TAUREDAIN_PYRAMID;
            int amount = LOTRChestContents.getRandomItemAmount(itemPool, random);
            if (this.isGolden) {
                amount *= 3;
            }
            LOTRChestContents.fillInventory(chests[l], random, itemPool, amount);
        }
        if (this.carson) {
            for (final IInventory chest : chests) {
                for (int m = 0; m < chest.getSizeInventory(); ++m) {
                    chest.setInventorySlotContents(m, new ItemStack(Items.wheat, 64));
                }
            }
        }
        if (super.restrictions) {
            for (int i6 = -LOTRWorldGenTauredainPyramid.RADIUS; i6 <= LOTRWorldGenTauredainPyramid.RADIUS; ++i6) {
                for (int k6 = -LOTRWorldGenTauredainPyramid.RADIUS; k6 <= LOTRWorldGenTauredainPyramid.RADIUS; ++k6) {
                    final int j2 = this.getTopBlock(world, i6, k6);
                    final Block block = this.getBlock(world, i6, j2 - 1, k6);
                    if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone && block != LOTRMod.mudGrass && block != LOTRMod.mud) {
                        return false;
                    }
                }
            }
        }
        for (int i6 = -LOTRWorldGenTauredainPyramid.RADIUS; i6 <= LOTRWorldGenTauredainPyramid.RADIUS; ++i6) {
            for (int k6 = -LOTRWorldGenTauredainPyramid.RADIUS; k6 <= LOTRWorldGenTauredainPyramid.RADIUS; ++k6) {
                for (int j2 = 0; (this.getY(j2) >= super.originY || !this.isOpaque(world, i6, j2, k6)) && this.getY(j2) >= 0; --j2) {
                    this.placeRandomBrick(world, random, i6, j2, k6);
                    this.setGrassToDirt(world, i6, j2 - 1, k6);
                }
            }
        }
        final int steps = (LOTRWorldGenTauredainPyramid.RADIUS - 10) / 2;
        final int topRadius = LOTRWorldGenTauredainPyramid.RADIUS - steps * 2;
        final int topHeight = steps * 2;
        for (int step = 0; step < steps; ++step) {
            for (int j3 = step * 2; j3 <= step * 2 + 1; ++j3) {
                for (int r = LOTRWorldGenTauredainPyramid.RADIUS - step * 2, i7 = -r; i7 <= r; ++i7) {
                    for (int k7 = -r; k7 <= r; ++k7) {
                        this.placeRandomBrick(world, random, i7, j3, k7);
                        if ((Math.abs(i7) == r - 1 || Math.abs(k7) == r - 1) && random.nextInt(3) == 0) {
                            this.placeRandomBrick(world, random, i7, j3 + 1, k7);
                        }
                    }
                }
            }
        }
        for (int i8 = -2; i8 <= 2; ++i8) {
            for (int k8 = -2; k8 <= 2; ++k8) {
                this.setBlockAndMetadata(world, i8, topHeight, k8, LOTRMod.brick4, 3);
                for (int j4 = topHeight + 1; j4 <= topHeight + 6; ++j4) {
                    if (Math.abs(i8) == 2 && Math.abs(k8) == 2) {
                        this.setBlockAndMetadata(world, i8, j4, k8, LOTRMod.pillar2, 12);
                    }
                    else {
                        this.setBlockAndMetadata(world, i8, j4, k8, LOTRMod.brick4, 4);
                    }
                }
                this.setBlockAndMetadata(world, i8, topHeight + 7, k8, LOTRMod.brick4, 3);
            }
        }
        for (final int i7 : new int[] { -10, 10 }) {
            for (final int k9 : new int[] { -10, 10 }) {
                this.setBlockAndMetadata(world, i7, topHeight, k9, LOTRMod.brick4, 3);
                for (int j5 = topHeight + 1; j5 <= topHeight + 3; ++j5) {
                    this.setBlockAndMetadata(world, i7, j5, k9, LOTRMod.pillar2, 12);
                }
                this.setBlockAndMetadata(world, i7, topHeight + 4, k9, LOTRMod.brick4, 3);
            }
        }
        this.generateMaze(world, random, 0, topHeight - 13, 0, maze1, 5, 1, false);
        int stepX = 0;
        int stepY = topHeight - 1;
        int stepZ = 3;
        while (stepY >= topHeight - 13) {
            int newX = stepX;
            int newY = stepY;
            int newZ = stepZ;
            if (stepX == -3 && stepZ == -3) {
                this.placeRandomBrick(world, random, stepX, stepY, stepZ);
                ++newZ;
            }
            else if (stepX == -3 && stepZ == 3) {
                this.placeRandomBrick(world, random, stepX, stepY, stepZ);
                ++newX;
            }
            else if (stepX == 3 && stepZ == 3) {
                this.placeRandomBrick(world, random, stepX, stepY, stepZ);
                --newZ;
            }
            else if (stepX == 3 && stepZ == -3) {
                this.placeRandomBrick(world, random, stepX, stepY, stepZ);
                --newX;
            }
            else if (stepZ == -3) {
                this.placeRandomStairs(world, random, stepX, stepY, stepZ, 1);
                --newX;
                --newY;
            }
            else if (stepZ == 3) {
                this.placeRandomStairs(world, random, stepX, stepY, stepZ, 0);
                ++newX;
                --newY;
            }
            else if (stepX == -3) {
                this.placeRandomStairs(world, random, stepX, stepY, stepZ, 3);
                ++newZ;
                --newY;
            }
            else if (stepX == 3) {
                this.placeRandomStairs(world, random, stepX, stepY, stepZ, 2);
                --newZ;
                --newY;
            }
            for (int j6 = 1; j6 <= 3; ++j6) {
                this.setAir(world, stepX, stepY + j6, stepZ);
            }
            stepX = newX;
            stepY = newY;
            stepZ = newZ;
        }
        this.setAir(world, stepX, stepY + 3, stepZ);
        for (int j7 = topHeight - 18 + 2; j7 < topHeight - 13; ++j7) {
            this.setAir(world, maze1End[0] - (maze1.xSize - 1) / 2, j7, maze1End[1] - (maze1.zSize - 1) / 2);
        }
        this.generateMaze(world, random, 0, topHeight - 18, 0, maze2, 2, 1, false);
        final int[] maze2End = maze2.getEnd();
        for (int j8 = topHeight - 22; j8 < topHeight - 18; ++j8) {
            this.setAir(world, maze2End[0] - (maze2.xSize - 1) / 2, j8, maze2End[1] - (maze2.zSize - 1) / 2);
        }
        final int chamberRMin = 22;
        for (int chamberRMax = 26, i9 = -chamberRMax - 1; i9 <= chamberRMax + 1; ++i9) {
            for (int k9 = -chamberRMax - 1; k9 <= chamberRMax + 1; ++k9) {
                final int i10 = Math.abs(i9);
                final int k10 = Math.abs(k9);
                if (i10 == chamberRMax + 1 || k10 == chamberRMax + 1) {
                    this.setBlockAndMetadata(world, i9, topHeight - 25, k9, LOTRMod.brick4, 4);
                    this.setBlockAndMetadata(world, i9, topHeight - 24, k9, LOTRMod.brick4, 3);
                }
                if (i10 <= chamberRMax && k10 <= chamberRMax && (i10 >= chamberRMin || k10 >= chamberRMin)) {
                    for (int j9 = topHeight - 26; j9 < topHeight - 22; ++j9) {
                        this.setAir(world, i9, j9, k9);
                    }
                    if (i10 == chamberRMax && k10 == chamberRMax) {
                        this.setBlockAndMetadata(world, i9, topHeight - 26, k9, LOTRMod.hearth, 0);
                        this.setBlockAndMetadata(world, i9, topHeight - 25, k9, (Block)Blocks.fire, 0);
                    }
                    else if (i10 >= chamberRMax - 1 && k10 >= chamberRMax - 1) {
                        this.setBlockAndMetadata(world, i9, topHeight - 26, k9, LOTRMod.brick4, 3);
                    }
                    else if (i10 >= chamberRMax - 2 && k10 >= chamberRMax - 2) {
                        this.setBlockAndMetadata(world, i9, topHeight - 26, k9, LOTRMod.slabSingle8, 4);
                    }
                    if ((i10 == chamberRMax && k10 % 6 == 0 && k10 < chamberRMax - 4) || (k10 == chamberRMax && i10 % 6 == 0 && i10 < chamberRMax - 4)) {
                        Block pillarBlock = LOTRMod.pillar;
                        int pillarMeta = 14;
                        if (this.isGolden) {
                            pillarBlock = LOTRMod.pillar2;
                            pillarMeta = 11;
                        }
                        for (int j10 = topHeight - 26; j10 < topHeight - 22; ++j10) {
                            this.setBlockAndMetadata(world, i9, j10, k9, pillarBlock, pillarMeta);
                        }
                    }
                }
            }
        }
        this.generateMaze(world, random, 0, topHeight - 35, 0, maze3, 4, 3, true);
        final int[] maze3End = maze3.getEnd();
        int maze3EndX = maze3End[0] - (maze3.xSize - 1) / 2;
        int maze3EndZ = maze3End[1] - (maze3.zSize - 1) / 2;
        maze3EndX *= 3;
        maze3EndZ *= 3;
        for (int step2 = 0; step2 <= 9; ++step2) {
            for (int i11 = -1; i11 <= 1; ++i11) {
                final int j11 = topHeight - 36 + step2;
                final int k11 = 13 + step2;
                if (step2 > 0) {
                    this.placeRandomStairs(world, random, i11, j11, k11, 2);
                }
                for (int j12 = 1; j12 <= 4; ++j12) {
                    this.setAir(world, i11, j11 + j12, k11);
                }
                if (step2 < 9) {
                    this.placeRandomStairs(world, random, i11, j11 + 5, k11, 7);
                }
                if (step2 <= 3) {
                    for (int j12 = 1; j12 < step2; ++j12) {
                        this.placeRandomBrick(world, random, i11, j11 - step2 + j12, k11);
                    }
                }
            }
        }
        final int roomBottom = topHeight - 49;
        final int roomFloor = topHeight - 47;
        final int roomTop = topHeight - 36;
        final int roomPillarEdge = 32;
        for (int i12 = -37; i12 <= 37; ++i12) {
            for (int k12 = -37; k12 <= 37; ++k12) {
                final int i13 = Math.abs(i12);
                final int k13 = Math.abs(k12);
                int actingRoomTop = roomTop;
                if (i13 != roomPillarEdge && k13 != roomPillarEdge) {
                    actingRoomTop -= random.nextInt(2);
                }
                for (int j13 = roomFloor + 1; j13 < actingRoomTop; ++j13) {
                    this.setAir(world, i12, j13, k12);
                }
                if (i13 > roomPillarEdge || k13 > roomPillarEdge) {
                    for (int j13 = roomBottom + 1; j13 <= roomFloor + 1; ++j13) {
                        this.placeRandomBrick(world, random, i12, j13, k12);
                    }
                }
                else if (i13 == roomPillarEdge || k13 == roomPillarEdge) {
                    for (int j13 = roomBottom + 1; j13 <= roomFloor + 1; ++j13) {
                        this.setBlockAndMetadata(world, i12, j13, k12, LOTRMod.brick4, 4);
                    }
                    this.placeRandomBrick(world, random, i12, actingRoomTop - 1, k12);
                    if (this.isGolden) {
                        this.setBlockAndMetadata(world, i12, actingRoomTop - 2, k12, LOTRMod.pillar2, 11);
                    }
                    else {
                        this.setBlockAndMetadata(world, i12, actingRoomTop - 2, k12, LOTRMod.pillar, 14);
                    }
                    final int i14 = IntMath.mod(i12, 4);
                    final int k14 = IntMath.mod(k12, 4);
                    if ((i13 == roomPillarEdge && k14 == 0) || (k13 == roomPillarEdge && i14 == 0)) {
                        for (int j14 = roomFloor + 2; j14 <= actingRoomTop - 2; ++j14) {
                            if (this.isGolden) {
                                this.setBlockAndMetadata(world, i12, j14, k12, LOTRMod.pillar2, 11);
                            }
                            else {
                                this.setBlockAndMetadata(world, i12, j14, k12, LOTRMod.pillar, 14);
                            }
                        }
                    }
                    if (i13 == roomPillarEdge) {
                        if (k14 == 1) {
                            this.placeRandomStairs(world, random, i12, actingRoomTop - 3, k12, 7);
                        }
                        else if (k14 == 3) {
                            this.placeRandomStairs(world, random, i12, actingRoomTop - 3, k12, 6);
                        }
                    }
                    else if (k13 == roomPillarEdge) {
                        if (i14 == 1) {
                            this.placeRandomStairs(world, random, i12, actingRoomTop - 3, k12, 4);
                        }
                        else if (i14 == 3) {
                            this.placeRandomStairs(world, random, i12, actingRoomTop - 3, k12, 5);
                        }
                    }
                }
                else if (i13 <= 10 && k13 <= 10) {
                    final int max = Math.max(i13, k13);
                    final int height = (10 - Math.max(max, 3)) / 2;
                    for (int j14 = roomBottom + 1; j14 <= roomFloor; ++j14) {
                        this.placeRandomBrick(world, random, i12, j14, k12);
                    }
                    final int lvlMin = roomFloor + 1;
                    final int lvlMax = lvlMin + height;
                    for (int j15 = lvlMin; j15 <= lvlMax; ++j15) {
                        this.placeRandomBrick(world, random, i12, j15, k12);
                    }
                    if (max > 3 && max % 2 == 0) {
                        this.setBlockAndMetadata(world, i12, lvlMax, k12, LOTRMod.brick4, 4);
                        if (i13 == k13) {
                            this.setBlockAndMetadata(world, i12, lvlMax, k12, LOTRMod.pillar2, 11);
                            this.setBlockAndMetadata(world, i12, lvlMax + 1, k12, LOTRMod.pillar2, 11);
                            this.setBlockAndMetadata(world, i12, lvlMax + 2, k12, LOTRMod.tauredainDoubleTorch, 0);
                            this.setBlockAndMetadata(world, i12, lvlMax + 3, k12, LOTRMod.tauredainDoubleTorch, 1);
                        }
                    }
                    if (max > 3 && (i13 <= 1 || k13 <= 1)) {
                        if (max % 2 == 0) {
                            this.setBlockAndMetadata(world, i12, lvlMax, k12, LOTRMod.slabSingle8, 3);
                        }
                        else {
                            this.setBlockAndMetadata(world, i12, lvlMax, k12, LOTRMod.brick4, 3);
                        }
                    }
                }
                else {
                    for (int j13 = roomBottom + 1; j13 <= roomFloor; ++j13) {
                        this.setBlockAndMetadata(world, i12, j13, k12, Blocks.lava, 0);
                    }
                    if (random.nextInt(300) == 0) {
                        this.setBlockAndMetadata(world, i12, actingRoomTop, k12, (Block)Blocks.flowing_lava, 0);
                    }
                    if (i13 == roomPillarEdge - 1 || k13 == roomPillarEdge - 1) {
                        if (random.nextInt(4) > 0) {
                            this.setBlockAndMetadata(world, i12, roomFloor, k12, Blocks.obsidian, 0);
                        }
                    }
                    else if (i13 == roomPillarEdge - 2 || k13 == roomPillarEdge - 2) {
                        if (random.nextInt(2) == 0) {
                            this.setBlockAndMetadata(world, i12, roomFloor, k12, Blocks.obsidian, 0);
                        }
                    }
                    else if (i13 == roomPillarEdge - 3 || k13 == roomPillarEdge - 3) {
                        if (random.nextInt(4) == 0) {
                            this.setBlockAndMetadata(world, i12, roomFloor, k12, Blocks.obsidian, 0);
                        }
                    }
                    else {
                        if (random.nextInt(16) == 0) {
                            this.placeRandomBrick(world, random, i12, roomFloor, k12);
                        }
                        if (random.nextInt(200) == 0) {
                            Block pillarBlock2 = LOTRMod.pillar;
                            int pillarMeta2 = 14;
                            if (this.isGolden) {
                                pillarBlock2 = LOTRMod.pillar2;
                                pillarMeta2 = 11;
                            }
                            if (random.nextBoolean()) {
                                pillarBlock2 = LOTRMod.pillar2;
                                pillarMeta2 = 12;
                            }
                            for (int j14 = roomBottom + 1; j14 < actingRoomTop; ++j14) {
                                this.setBlockAndMetadata(world, i12, j14, k12, pillarBlock2, pillarMeta2);
                            }
                        }
                    }
                }
            }
        }
        this.placePyramidBanner(world, 0, roomFloor + 6, 0);
        this.placeSpawnerChest(world, random, -1, roomFloor + 5, 0, LOTRMod.spawnerChestStone, 5, LOTREntityTauredainPyramidWraith.class, null);
        this.putInventoryInChest(world, -1, roomFloor + 5, 0, chests[0]);
        this.placeSpawnerChest(world, random, 1, roomFloor + 5, 0, LOTRMod.spawnerChestStone, 4, LOTREntityTauredainPyramidWraith.class, null);
        this.putInventoryInChest(world, 1, roomFloor + 5, 0, chests[1]);
        this.placeSpawnerChest(world, random, 0, roomFloor + 5, -1, LOTRMod.spawnerChestStone, 2, LOTREntityTauredainPyramidWraith.class, null);
        this.putInventoryInChest(world, 0, roomFloor + 5, -1, chests[2]);
        this.placeSpawnerChest(world, random, 0, roomFloor + 5, 1, LOTRMod.spawnerChestStone, 3, LOTREntityTauredainPyramidWraith.class, null);
        this.putInventoryInChest(world, 0, roomFloor + 5, 1, chests[3]);
        stepX = 1;
        stepY = topHeight - 36;
        stepZ = 0;
        for (int i12 = -1; i12 <= 1; ++i12) {
            for (int k12 = -1; k12 <= 1; ++k12) {
                this.setAir(world, maze3EndX + i12, stepY, maze3EndZ + k12);
                this.setAir(world, maze3EndX + i12, stepY - 1, maze3EndZ + k12);
            }
        }
        this.placeRandomBrick(world, random, maze3EndX + 1, stepY, maze3EndZ + 1);
        while (stepY > roomFloor + 1) {
            int newX2 = stepX;
            int newY2 = stepY;
            int newZ2 = stepZ;
            final int stepPlaceX = stepX + maze3EndX;
            final int stepPlaceZ = stepZ + maze3EndZ;
            if (stepX == -1 && stepZ == -1) {
                this.placeRandomBrick(world, random, stepPlaceX, stepY, stepPlaceZ);
                ++newZ2;
            }
            else if (stepX == -1 && stepZ == 1) {
                this.placeRandomBrick(world, random, stepPlaceX, stepY, stepPlaceZ);
                ++newX2;
            }
            else if (stepX == 1 && stepZ == 1) {
                this.placeRandomBrick(world, random, stepPlaceX, stepY, stepPlaceZ);
                --newZ2;
            }
            else if (stepX == 1 && stepZ == -1) {
                this.placeRandomBrick(world, random, stepPlaceX, stepY, stepPlaceZ);
                --newX2;
            }
            else if (stepZ == -1) {
                this.placeRandomStairs(world, random, stepPlaceX, stepY, stepPlaceZ, 1);
                this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, stepPlaceZ, 4);
                --newX2;
                --newY2;
            }
            else if (stepZ == 1) {
                this.placeRandomStairs(world, random, stepPlaceX, stepY, stepPlaceZ, 0);
                this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, stepPlaceZ, 5);
                ++newX2;
                --newY2;
            }
            else if (stepX == -1) {
                this.placeRandomStairs(world, random, stepPlaceX, stepY, stepPlaceZ, 3);
                this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, stepPlaceZ, 6);
                ++newZ2;
                --newY2;
            }
            else if (stepX == 1) {
                this.placeRandomStairs(world, random, stepPlaceX, stepY, stepPlaceZ, 2);
                this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, stepPlaceZ, 7);
                --newZ2;
                --newY2;
            }
            stepX = newX2;
            stepY = newY2;
            stepZ = newZ2;
        }
        for (int j16 = roomFloor + 1; j16 <= topHeight - 32; ++j16) {
            this.setBlockAndMetadata(world, maze3EndX, j16, maze3EndZ, LOTRMod.pillar2, 12);
        }
        this.setBlockAndMetadata(world, maze3EndX + 1, topHeight - 33, maze3EndZ, Blocks.torch, 2);
        this.setBlockAndMetadata(world, maze3EndX - 1, topHeight - 33, maze3EndZ, Blocks.torch, 1);
        this.setBlockAndMetadata(world, maze3EndX, topHeight - 33, maze3EndZ + 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, maze3EndX, topHeight - 33, maze3EndZ - 1, Blocks.torch, 4);
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (this.isGolden) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 3);
            return;
        }
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 1);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 2);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 0);
        }
    }
    
    private void placeRandomWall(final World world, final Random random, final int i, final int j, final int k) {
        if (this.isGolden) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 3);
            return;
        }
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 1);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 2);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 0);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (this.isGolden) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrickGold, meta);
            return;
        }
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrickMossy, meta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrickCracked, meta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrick, meta);
        }
    }
    
    private void placePyramidBanner(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j - 1, k, Blocks.gold_block, 0);
        for (int j2 = 0; j2 <= 3; ++j2) {
            this.setAir(world, i, j + j2, k);
        }
        this.placeBanner(world, i, j, k, LOTRItemBanner.BannerType.TAUREDAIN, 0, true, 64);
    }
    
    private void generateMaze(final World world, final Random random, int i, final int j, int k, final LOTRMazeGenerator maze, final int height, final int scale, final boolean traps) {
        final int xr = (maze.xSize - 1) / 2;
        final int zr = (maze.zSize - 1) / 2;
        i -= xr;
        k -= zr;
        final int scaleR = (scale - 1) / 2;
        for (int pass = 0; pass <= 1; ++pass) {
            for (int i2 = 0; i2 < maze.xSize; ++i2) {
                for (int k2 = 0; k2 < maze.zSize; ++k2) {
                    if (pass == 0) {
                        final boolean path = maze.isPath(i2, k2);
                        if (path) {
                            for (int i3 = 0; i3 < scale; ++i3) {
                                for (int k3 = 0; k3 < scale; ++k3) {
                                    for (int j2 = 0; j2 < height; ++j2) {
                                        this.setAir(world, (i + i2) * scale - scaleR + i3, j + j2, (k + k2) * scale - scaleR + k3);
                                    }
                                }
                            }
                        }
                    }
                    if (pass == 1) {
                        if (maze.isDeadEnd(i2, k2) && random.nextInt(3) == 0) {
                            this.setBlockAndMetadata(world, (i + i2) * scale - scaleR, j + 1, (k + k2) * scale - scaleR, Blocks.torch, 0);
                        }
                        if (traps && !maze.isPath(i2, k2) && random.nextInt(4) == 0) {
                            final List<ForgeDirection> validDirs = new ArrayList<ForgeDirection>();
                            if (i2 - 1 >= 0 && maze.isPath(i2 - 1, k2)) {
                                validDirs.add(ForgeDirection.WEST);
                            }
                            if (i2 + 1 < maze.xSize && maze.isPath(i2 + 1, k2)) {
                                validDirs.add(ForgeDirection.EAST);
                            }
                            if (k2 - 1 >= 0 && maze.isPath(i2, k2 - 1)) {
                                validDirs.add(ForgeDirection.NORTH);
                            }
                            if (k2 + 1 < maze.zSize && maze.isPath(i2, k2 + 1)) {
                                validDirs.add(ForgeDirection.SOUTH);
                            }
                            if (!validDirs.isEmpty()) {
                                final ForgeDirection dir = validDirs.get(random.nextInt(validDirs.size()));
                                this.placeDartTrap(world, random, (i + i2) * scale + dir.offsetX * scaleR, j + 0, (k + k2) * scale + dir.offsetZ * scaleR, dir.ordinal());
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void placeDartTrap(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        Block dartTrapBlock = LOTRMod.tauredainDartTrap;
        if (this.isGolden) {
            dartTrapBlock = LOTRMod.tauredainDartTrapGold;
        }
        this.setBlockAndMetadata(world, i, j, k, dartTrapBlock, meta);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntityDartTrap) {
            final LOTRTileEntityDartTrap trap = (LOTRTileEntityDartTrap)tileentity;
            for (int l = 0; l < trap.getSizeInventory(); ++l) {
                if (random.nextBoolean()) {
                    final int darts = MathHelper.getRandomIntegerInRange(random, 2, 6);
                    if (random.nextBoolean()) {
                        trap.setInventorySlotContents(l, new ItemStack(LOTRMod.tauredainDartPoisoned, darts));
                    }
                    else {
                        trap.setInventorySlotContents(l, new ItemStack(LOTRMod.tauredainDart, darts));
                    }
                }
            }
        }
    }
    
    static {
        LOTRWorldGenTauredainPyramid.RADIUS = 60;
    }
}
