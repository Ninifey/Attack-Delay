// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

public class LOTRWorldGenRuinedHouse extends LOTRWorldGenStructureBase2
{
    protected Block woodBlock;
    protected int woodMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block stairBlock;
    protected Block stoneBlock;
    protected int stoneMeta;
    protected Block stoneVariantBlock;
    protected int stoneVariantMeta;
    
    public LOTRWorldGenRuinedHouse(final boolean flag) {
        super(flag);
        this.woodBlock = Blocks.log;
        this.woodMeta = 0;
        this.plankBlock = Blocks.planks;
        this.plankMeta = 0;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.stairBlock = Blocks.oak_stairs;
        this.stoneBlock = Blocks.cobblestone;
        this.stoneMeta = 0;
        this.stoneVariantBlock = Blocks.mossy_cobblestone;
        this.stoneVariantMeta = 0;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int width = 4 + random.nextInt(3);
        this.setOriginAndRotation(world, i, j, k, rotation, width + 1);
        if (super.restrictions) {
            int minHeight = 1;
            int maxHeight = 1;
            for (int i2 = -width; i2 <= width; ++i2) {
                for (int k2 = -width; k2 <= width; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
                        return false;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                }
            }
            if (Math.abs(maxHeight - minHeight) > 5) {
                return false;
            }
        }
        for (int i3 = -width; i3 <= width; ++i3) {
            for (int k3 = -width; k3 <= width; ++k3) {
                for (int j3 = 0; j3 <= 5; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                for (int j3 = 0; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.placeRandomGroundBlock(world, random, i3, j3, k3);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int k4 = -width; k4 <= width; ++k4) {
            this.placeRandomWallOrStone(world, random, -width, 1, -k4);
            this.placeRandomWallOrStone(world, random, width, 1, k4);
            this.placeRandomWall(world, random, -width, 2, k4, true);
            this.placeRandomWall(world, random, width, 2, k4, true);
            this.placeRandomWall(world, random, -width, 3, k4, true);
            this.placeRandomWall(world, random, width, 3, k4, true);
        }
        for (int i3 = -width; i3 <= width; ++i3) {
            this.placeRandomWallOrStone(world, random, i3, 1, width);
            if (random.nextInt(3) == 0) {
                this.placeRandomWallOrStone(world, random, i3, 2, width - 1);
            }
            this.placeRandomWall(world, random, i3, 2, width, false);
            this.placeRandomWall(world, random, i3, 3, width, false);
        }
        for (int i3 = -width + 1; i3 <= -1 && random.nextInt(4) != 0; ++i3) {
            this.placeRandomWallOrStone(world, random, i3, 1, -width);
        }
        for (int i3 = width - 1; i3 >= 1 && random.nextInt(4) != 0; --i3) {
            this.placeRandomWallOrStone(world, random, i3, 1, -width);
        }
        this.setBlockAndMetadata(world, -width + 1, 2, -width, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, width - 1, 2, -width, this.fenceBlock, this.fenceMeta);
        this.placeWoodPillar(world, random, -width, 1, -width);
        this.placeWoodPillar(world, random, width, 1, -width);
        this.placeWoodPillar(world, random, -width, 1, width);
        this.placeWoodPillar(world, random, width, 1, width);
        if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, width - 1, 1, -width + 1, this.stoneBlock, this.stoneMeta);
            this.setBlockAndMetadata(world, width - 1, 1, -width + 2, Blocks.furnace, 0);
        }
        else {
            this.setBlockAndMetadata(world, -width + 1, 1, -width + 1, this.stoneBlock, this.stoneMeta);
            this.setBlockAndMetadata(world, -width + 1, 1, -width + 2, Blocks.furnace, 0);
        }
        if (random.nextBoolean()) {
            this.placeChest(world, random, width - 1, 1, width - 2, 0, LOTRChestContents.RUINED_HOUSE);
        }
        else {
            this.placeChest(world, random, -width + 1, 1, width - 2, 0, LOTRChestContents.RUINED_HOUSE);
        }
        return true;
    }
    
    private void placeRandomGroundBlock(final World world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(4);
        if (l == 0) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.dirt, 1);
        }
        else if (l == 1) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.gravel, 0);
        }
        else if (l == 2) {
            this.setBlockAndMetadata(world, i, j, k, this.stoneBlock, this.stoneMeta);
        }
        else if (l == 3) {
            this.setBlockAndMetadata(world, i, j, k, this.stoneVariantBlock, this.stoneVariantMeta);
        }
    }
    
    private void placeRandomWallOrStone(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(12) == 0) {
            return;
        }
        if (this.isAir(world, i, j - 1, k)) {
            return;
        }
        final int l = random.nextInt(4);
        if (l == 0) {
            this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
        }
        else if (l == 1) {
            this.setBlockAndMetadata(world, i, j, k, this.plankBlock, this.plankMeta);
        }
        else if (l == 2) {
            this.setBlockAndMetadata(world, i, j, k, this.stoneBlock, this.stoneMeta);
        }
        else if (l == 3) {
            this.setBlockAndMetadata(world, i, j, k, this.stoneVariantBlock, this.stoneVariantMeta);
        }
    }
    
    private void placeRandomWall(final World world, final Random random, final int i, final int j, final int k, final boolean northToSouth) {
        if (random.nextInt(12) == 0) {
            return;
        }
        if (this.isAir(world, i, j - 1, k)) {
            return;
        }
        final int l = random.nextInt(4);
        if (l == 0) {
            this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
        }
        else if (l == 1) {
            this.setBlockAndMetadata(world, i, j, k, this.plankBlock, this.plankMeta);
        }
        else if (l == 2) {
            this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta | (northToSouth ? 8 : 4));
        }
        else if (l == 3) {
            final int upsideDown = random.nextBoolean() ? 4 : 0;
            if (northToSouth) {
                this.setBlockAndMetadata(world, i, j, k, this.stairBlock, random.nextInt(2) | upsideDown);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.stairBlock, 2 + random.nextInt(2) | upsideDown);
            }
        }
    }
    
    private void placeWoodPillar(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j; j2 <= j + 4; ++j2) {
            this.setBlockAndMetadata(world, i, j2, k, this.woodBlock, this.woodMeta);
            if (random.nextInt(4) == 0 && j2 >= j + 2) {
                break;
            }
        }
    }
}
