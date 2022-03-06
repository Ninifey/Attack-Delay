// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingStables extends LOTRWorldGenEasterlingStructure
{
    public LOTRWorldGenEasterlingStables(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -9; i2 <= 9; ++i2) {
                for (int k2 = -1; k2 <= 13; ++k2) {
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
        for (int i3 = -8; i3 <= 8; ++i3) {
            for (int k3 = 0; k3 <= 12; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = IntMath.mod(k3, 4);
                for (int j2 = 1; j2 <= 6; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 == 0 && (k3 == 0 || k3 == 12)) {
                    for (int j2 = 5; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 4 && k4 == 0) {
                    for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 8 && k4 == 0) {
                    for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
            }
        }
        for (int k5 = 1; k5 <= 11; ++k5) {
            this.setBlockAndMetadata(world, 0, 5, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 0, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int i3 = -8; i3 <= 8; ++i3) {
            for (int k3 = 0; k3 <= 12; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = IntMath.mod(k3, 4);
                if (i4 >= 5 && i4 <= 7) {
                    if (k3 == 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plankStairBlock, 3);
                        this.setBlockAndMetadata(world, i3, 2, k3, super.plankStairBlock, 2);
                    }
                    else if (k3 == 12) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plankStairBlock, 2);
                        this.setBlockAndMetadata(world, i3, 2, k3, super.plankStairBlock, 3);
                    }
                    else if (k4 == 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plankBlock, super.plankMeta);
                        this.setBlockAndMetadata(world, i3, 2, k3, super.plankBlock, super.plankMeta);
                    }
                    else {
                        final int randomGround = random.nextInt(2);
                        if (randomGround == 0) {
                            this.setBlockAndMetadata(world, i3, 0, k3, Blocks.dirt, 1);
                        }
                        else if (randomGround == 1) {
                            this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 1);
                        }
                    }
                }
                if (i4 >= 1 && i4 <= 3 && k3 == 12) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.plankStairBlock, 2);
                    this.setBlockAndMetadata(world, i3, 2, k3, super.plankStairBlock, 3);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.fenceBlock, super.fenceMeta);
                }
                if (i4 == 4 && k4 != 0) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.fenceGateBlock, (i3 > 0) ? 1 : 3);
                }
                if (i4 == 8 && k4 != 0) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.plankStairBlock, (i3 > 0) ? 1 : 0);
                    this.setBlockAndMetadata(world, i3, 2, k3, super.plankStairBlock, (i3 <= 0) ? 1 : 0);
                }
                if (i4 == 6 && k4 == 2) {
                    final LOTREntityHorse horse = new LOTREntityHorse(world);
                    this.spawnNPCAndSetHome((EntityCreature)horse, world, i3, 1, k3, 0);
                    horse.setHorseType(0);
                    horse.saddleMountForWorldGen();
                    horse.detachHome();
                }
                if (i4 == 4) {
                    if (k4 == 1) {
                        this.setBlockAndMetadata(world, i3, 3, k3, Blocks.torch, 3);
                    }
                    else if (k4 == 3) {
                        this.setBlockAndMetadata(world, i3, 3, k3, Blocks.torch, 4);
                    }
                }
                if (i4 == 0 && k4 == 2) {
                    this.setBlockAndMetadata(world, i3, 4, k3, LOTRMod.chandelier, 0);
                }
            }
        }
        for (int k5 = 0; k5 <= 12; ++k5) {
            final int k6 = IntMath.mod(k5, 4);
            this.setBlockAndMetadata(world, -8, 4, k5, super.roofStairBlock, 1);
            for (int i2 = -7; i2 <= -5; ++i2) {
                this.setBlockAndMetadata(world, i2, 4, k5, super.roofBlock, super.roofMeta);
            }
            if (k6 != 0) {
                this.setBlockAndMetadata(world, -4, 4, k5, super.roofStairBlock, 4);
            }
            this.setBlockAndMetadata(world, -5, 5, k5, super.roofStairBlock, 1);
            for (int i2 = -4; i2 <= -2; ++i2) {
                this.setBlockAndMetadata(world, i2, 5, k5, super.roofBlock, super.roofMeta);
            }
            this.setBlockAndMetadata(world, -1, 5, k5, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 6, k5, super.roofStairBlock, 1);
            for (int i2 = -1; i2 <= 1; ++i2) {
                this.setBlockAndMetadata(world, i2, 6, k5, super.roofBlock, super.roofMeta);
            }
            this.setBlockAndMetadata(world, 2, 6, k5, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 1, 5, k5, super.roofStairBlock, 5);
            for (int i2 = 2; i2 <= 4; ++i2) {
                this.setBlockAndMetadata(world, i2, 5, k5, super.roofBlock, super.roofMeta);
            }
            this.setBlockAndMetadata(world, 5, 5, k5, super.roofStairBlock, 0);
            if (k6 != 0) {
                this.setBlockAndMetadata(world, 4, 4, k5, super.roofStairBlock, 5);
            }
            for (int i2 = 5; i2 <= 7; ++i2) {
                this.setBlockAndMetadata(world, i2, 4, k5, super.roofBlock, super.roofMeta);
            }
            this.setBlockAndMetadata(world, 8, 4, k5, super.roofStairBlock, 0);
        }
        for (final int k2 : new int[] { -1, 13 }) {
            this.setBlockAndMetadata(world, -8, 3, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, -4, 4, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 0, 5, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 4, 4, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 8, 3, k2, super.fenceBlock, super.fenceMeta);
        }
        for (int k5 = 0; k5 <= 12; ++k5) {
            if (IntMath.mod(k5, 4) == 0) {
                this.setBlockAndMetadata(world, -9, 3, k5, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, 9, 3, k5, super.fenceBlock, super.fenceMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 4, -1, super.plankBlock, super.plankMeta);
        this.spawnItemFrame(world, 0, 4, -1, 2, new ItemStack(Items.saddle));
        this.spawnItemFrame(world, 0, 4, -1, 1, new ItemStack(Items.saddle));
        this.spawnItemFrame(world, 0, 4, -1, 3, new ItemStack(Items.saddle));
        this.placeChest(world, random, -3, 1, 4, 4, super.chestContents);
        this.placeChest(world, random, 3, 1, 4, 5, super.chestContents);
        this.setBlockAndMetadata(world, 0, 1, 4, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 1, 5, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 0, 1, 6, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 0, 1, 7, super.plankStairBlock, 3);
        for (int i3 = -2; i3 <= 2; ++i3) {
            final int h = 3 - Math.abs(i3);
            for (int j3 = 1; j3 < 1 + h; ++j3) {
                this.setBlockAndMetadata(world, i3, j3, 11, Blocks.hay_block, 0);
            }
            final int h2 = h - 1;
            if (h2 >= 1) {
                for (int j4 = 1; j4 < 1 + h2; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, 10, Blocks.hay_block, 0);
                }
            }
        }
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 0, 1, 3, 8);
        }
        return true;
    }
}
