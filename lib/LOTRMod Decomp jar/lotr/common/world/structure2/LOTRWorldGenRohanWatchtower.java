// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.LOTRFoods;
import com.google.common.math.IntMath;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanWatchtower extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanWatchtower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        final int height = 7 + random.nextInt(3);
        super.originY += height;
        if (super.restrictions) {
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (final int i3 : new int[] { -3, 3 }) {
            for (final int k3 : new int[] { -3, 3 }) {
                for (int j3 = 3; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                this.setBlockAndMetadata(world, i2, 0, k2, super.plankBlock, super.plankMeta);
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                this.setBlockAndMetadata(world, i2, 4, k2, super.plankBlock, super.plankMeta);
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 0, -3, super.logBlock, super.logMeta | 0x4);
            this.setBlockAndMetadata(world, i2, 0, 3, super.logBlock, super.logMeta | 0x4);
            this.setBlockAndMetadata(world, i2, 4, -3, super.logBlock, super.logMeta | 0x4);
            this.setBlockAndMetadata(world, i2, 4, 3, super.logBlock, super.logMeta | 0x4);
            this.setBlockAndMetadata(world, i2, 0, -4, super.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i2, 0, 4, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i2, 1, -4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i2, 1, 4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i2, 3, -3, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i2, 3, 3, super.fenceBlock, super.fenceMeta);
        }
        for (int k4 = -2; k4 <= 2; ++k4) {
            this.setBlockAndMetadata(world, -3, 0, k4, super.logBlock, super.logMeta | 0x8);
            this.setBlockAndMetadata(world, 3, 0, k4, super.logBlock, super.logMeta | 0x8);
            this.setBlockAndMetadata(world, -3, 4, k4, super.logBlock, super.logMeta | 0x8);
            this.setBlockAndMetadata(world, 3, 4, k4, super.logBlock, super.logMeta | 0x8);
            this.setBlockAndMetadata(world, -4, 0, k4, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 0, k4, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -4, 1, k4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 4, 1, k4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, -3, 3, k4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 3, 3, k4, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, -2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -3, 1, 2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 3, 1, -2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 3, 1, 2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 1, -3, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, -3, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 1, 3, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, 3, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -3, 2, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 2, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 2, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 2, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 2, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 4, 0, super.logBlock, super.logMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 4, 0, super.logBlock, super.logMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 4, -1, super.logBlock, super.logMeta | 0x4);
        this.setBlockAndMetadata(world, 0, 4, 1, super.logBlock, super.logMeta | 0x4);
        for (int i2 = -4; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, -4, super.plankStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 4, 4, super.plankStairBlock, 3);
        }
        for (int k4 = -4; k4 <= 4; ++k4) {
            this.setBlockAndMetadata(world, -4, 4, k4, super.plankStairBlock, 1);
            this.setBlockAndMetadata(world, 4, 4, k4, super.plankStairBlock, 0);
        }
        for (int j4 = 0; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 3, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 0, j4, 2, Blocks.ladder, 2);
        }
        for (int j4 = -1; !this.isOpaque(world, 0, j4, 3) && this.getY(j4) >= 0; --j4) {
            this.setBlockAndMetadata(world, 0, j4, 3, super.plank2Block, super.plank2Meta);
            this.setGrassToDirt(world, 0, j4 - 1, 3);
            if (!this.isOpaque(world, 0, j4, 2)) {
                this.setBlockAndMetadata(world, 0, j4, 2, Blocks.ladder, 2);
            }
        }
        this.placeChest(world, random, -2, 1, 2, 2, LOTRChestContents.ROHAN_WATCHTOWER);
        this.setBlockAndMetadata(world, 2, 1, 2, super.tableBlock, 0);
        for (int k4 = -2; k4 <= 2; ++k4) {
            final int k5 = Math.abs(k4);
            for (final int i4 : new int[] { -3, 3 }) {
                for (int j5 = -1; !this.isOpaque(world, i4, j5, k4) && this.getY(j5) >= 0; --j5) {
                    if ((k5 == 2 && IntMath.mod(j5, 4) == 1) || (k5 == 1 && IntMath.mod(j5, 2) == 0) || (k5 == 0 && IntMath.mod(j5, 4) == 3)) {
                        this.setBlockAndMetadata(world, i4, j5, k4, super.logBlock, super.logMeta);
                        if (k5 == 0) {
                            this.setBlockAndMetadata(world, i4 - 1 * Integer.signum(i4), j5, k4, Blocks.torch, (i4 > 0) ? 1 : 2);
                        }
                    }
                }
            }
        }
        int belowTop = this.getBelowTop(world, 2, -1, 2);
        this.placeChest(world, random, 2, belowTop, 2, 5, LOTRChestContents.ROHAN_WATCHTOWER);
        belowTop = this.getBelowTop(world, 2, -1, 0);
        this.setBlockAndMetadata(world, 2, belowTop, 0, super.plankBlock, super.plankMeta);
        this.setGrassToDirt(world, 2, belowTop - 1, 0);
        this.placeBarrel(world, random, 2, belowTop + 1, 0, 5, LOTRFoods.ROHAN_DRINK);
        belowTop = this.getBelowTop(world, -2, -1, 1);
        this.setBlockAndMetadata(world, -2, belowTop, 1, Blocks.hay_block, 0);
        this.setGrassToDirt(world, -2, belowTop - 1, 1);
        belowTop = this.getBelowTop(world, -2, -1, 2);
        this.setBlockAndMetadata(world, -2, belowTop, 2, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -2, belowTop + 1, 2, Blocks.hay_block, 0);
        this.setGrassToDirt(world, -2, belowTop - 1, 2);
        belowTop = this.getBelowTop(world, -1, -1, 2);
        this.setBlockAndMetadata(world, -1, belowTop, 2, Blocks.hay_block, 0);
        this.setGrassToDirt(world, -1, belowTop - 1, 2);
        for (int soldiers = 1 + random.nextInt(3), l = 0; l < soldiers; ++l) {
            final LOTREntityRohirrimWarrior rohirrim = (random.nextInt(3) == 0) ? new LOTREntityRohirrimArcher(world) : new LOTREntityRohirrimWarrior(world);
            rohirrim.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(rohirrim, world, 0, 1, 0, 4);
        }
        return true;
    }
    
    private int getBelowTop(final World world, final int i, int j, final int k) {
        while (!this.isOpaque(world, i, j, k) && this.getY(j) >= 0) {
            --j;
        }
        return j + 1;
    }
}
