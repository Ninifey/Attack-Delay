// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorArcher;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTurret extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenGondorTurret(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.slabDouble, 2);
                for (int j2 = -1; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.slabDouble, 2);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int j3 = 1; j3 <= 4; ++j3) {
            for (int i3 = -1; i3 <= 1; ++i3) {
                for (int k3 = -1; k3 <= 1; ++k3) {
                    if (Math.abs(i3) == 1 && Math.abs(k3) == 1) {
                        this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.brick, 5);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.rock, 1);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 1, -2, LOTRMod.slabDouble, 2);
        this.setBlockAndMetadata(world, -2, 1, 2, LOTRMod.slabDouble, 2);
        this.setBlockAndMetadata(world, 2, 1, -2, LOTRMod.slabDouble, 2);
        this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.slabDouble, 2);
        for (int j3 = 2; j3 <= 4; ++j3) {
            this.setBlockAndMetadata(world, -2, j3, -2, LOTRMod.wall, 2);
            this.setBlockAndMetadata(world, -2, j3, 2, LOTRMod.wall, 2);
            this.setBlockAndMetadata(world, 2, j3, -2, LOTRMod.wall, 2);
            this.setBlockAndMetadata(world, 2, j3, 2, LOTRMod.wall, 2);
        }
        this.setBlockAndMetadata(world, -2, 5, -2, Blocks.log, 0);
        this.setBlockAndMetadata(world, -2, 5, 2, Blocks.log, 0);
        this.setBlockAndMetadata(world, 2, 5, -2, Blocks.log, 0);
        this.setBlockAndMetadata(world, 2, 5, 2, Blocks.log, 0);
        for (int k4 = -1; k4 <= 1; ++k4) {
            this.setBlockAndMetadata(world, -2, 1, k4, LOTRMod.stairsGondorBrick, 1);
            this.setBlockAndMetadata(world, 2, 1, k4, LOTRMod.stairsGondorBrick, 0);
            this.setBlockAndMetadata(world, -2, 3, k4, LOTRMod.slabSingle, 10);
            this.setBlockAndMetadata(world, -2, 4, k4, Blocks.log, 0);
            this.setBlockAndMetadata(world, -2, 5, k4, Blocks.log, 0);
            this.setBlockAndMetadata(world, 2, 3, k4, LOTRMod.slabSingle, 10);
            this.setBlockAndMetadata(world, 2, 4, k4, Blocks.log, 0);
            this.setBlockAndMetadata(world, 2, 5, k4, Blocks.log, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, 2, LOTRMod.stairsGondorBrick, 3);
            this.setBlockAndMetadata(world, i2, 3, -2, LOTRMod.slabSingle, 10);
            this.setBlockAndMetadata(world, i2, 4, -2, Blocks.log, 0);
            this.setBlockAndMetadata(world, i2, 5, -2, Blocks.log, 0);
            this.setBlockAndMetadata(world, i2, 3, 2, LOTRMod.slabSingle, 10);
            this.setBlockAndMetadata(world, i2, 4, 2, Blocks.log, 0);
            this.setBlockAndMetadata(world, i2, 5, 2, Blocks.log, 0);
        }
        for (int j3 = 1; j3 <= 4; ++j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 1, -1, LOTRMod.doorLebethron, 1);
        this.setBlockAndMetadata(world, 0, 2, -1, LOTRMod.doorLebethron, 8);
        this.setBlockAndMetadata(world, 0, 5, 0, Blocks.trapdoor, 0);
        this.setBlockAndMetadata(world, 0, 5, 1, LOTRMod.slabSingle, 2);
        this.placeChest(world, random, 1, 5, 1, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                if (Math.abs(i2) == 2 || Math.abs(k2) == 2) {
                    this.setBlockAndMetadata(world, i2, 6, k2, LOTRMod.wall, 2);
                    if (Math.abs(i2) == 2 && Math.abs(k2) == 2) {
                        this.setBlockAndMetadata(world, i2, 7, k2, Blocks.torch, 5);
                    }
                }
            }
        }
        for (int soldiers = 1 + random.nextInt(2), l = 0; l < soldiers; ++l) {
            final LOTREntityGondorSoldier soldier = random.nextBoolean() ? new LOTREntityGondorSoldier(world) : new LOTREntityGondorArcher(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 6, 0, 8);
        }
        return true;
    }
}
