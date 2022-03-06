// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.Direction;
import com.google.common.math.IntMath;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenRhudaurCastle extends LOTRWorldGenStructureBase2
{
    private Block brickBlock;
    private int brickMeta;
    private Block brickSlabBlock;
    private int brickSlabMeta;
    private Block brickCrackedBlock;
    private int brickCrackedMeta;
    private Block brickCrackedSlabBlock;
    private int brickCrackedSlabMeta;
    
    public LOTRWorldGenRhudaurCastle(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        if (random.nextBoolean()) {
            this.brickBlock = Blocks.stonebrick;
            this.brickMeta = 0;
            this.brickSlabBlock = (Block)Blocks.stone_slab;
            this.brickSlabMeta = 5;
            this.brickCrackedBlock = Blocks.stonebrick;
            this.brickCrackedMeta = 2;
            this.brickCrackedSlabBlock = LOTRMod.slabSingleV;
            this.brickCrackedSlabMeta = 1;
        }
        else {
            this.brickBlock = LOTRMod.brick2;
            this.brickMeta = 0;
            this.brickSlabBlock = LOTRMod.slabSingle3;
            this.brickSlabMeta = 3;
            this.brickCrackedBlock = LOTRMod.brick2;
            this.brickCrackedMeta = 1;
            this.brickCrackedSlabBlock = LOTRMod.slabSingle3;
            this.brickCrackedSlabMeta = 4;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        final int width = MathHelper.getRandomIntegerInRange(random, 6, 15);
        final int height = MathHelper.getRandomIntegerInRange(random, 3, 8);
        for (int i2 = -width; i2 <= width; ++i2) {
            for (int k2 = -width; k2 <= width; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == width || k3 == width) {
                    final float f = MathHelper.randomFloatClamp(random, 0.7f, 1.0f);
                    int h = Math.round(height * f);
                    int b = 1;
                    if (k2 == -width && i3 <= 1) {
                        b = 4;
                    }
                    if (IntMath.mod(i3 + k3, 2) == IntMath.mod(width, 2)) {
                        ++h;
                    }
                    final int top = this.getTopBlock(world, i2, k2) - 1;
                    boolean foundSurface = false;
                    int j2;
                    for (j2 = top; j2 >= top - 16; --j2) {
                        if (this.isSurface(world, i2, j2, k2)) {
                            foundSurface = true;
                            break;
                        }
                    }
                    if (foundSurface) {
                        for (int j3 = b; j3 <= h; ++j3) {
                            final int j4 = j2 + j3;
                            final boolean low = j3 < (int)(height * 0.5f) && j3 < h;
                            if (!low || random.nextInt(40) != 0) {
                                final boolean slab = (low && random.nextInt(20) == 0) || (j3 == h && random.nextInt(5) == 0);
                                final boolean cracked = random.nextInt(4) == 0;
                                if (cracked) {
                                    if (slab) {
                                        this.setBlockAndMetadata(world, i2, j4, k2, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta);
                                    }
                                    else {
                                        this.setBlockAndMetadata(world, i2, j4, k2, this.brickCrackedBlock, this.brickCrackedMeta);
                                    }
                                }
                                else if (slab) {
                                    this.setBlockAndMetadata(world, i2, j4, k2, this.brickSlabBlock, this.brickSlabMeta);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i2, j4, k2, this.brickBlock, this.brickMeta);
                                }
                                if (j3 == 1) {
                                    this.setGrassToDirt(world, i2, j4 - 1, k2);
                                }
                            }
                        }
                    }
                }
                else {
                    if (random.nextInt(16) == 0) {
                        final int j5 = this.getTopBlock(world, i2, k2) - 1;
                        if (this.isSurface(world, i2, j5, k2)) {
                            if (random.nextInt(3) == 0) {
                                this.setBlockAndMetadata(world, i2, j5, k2, Blocks.gravel, 0);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, j5, k2, Blocks.cobblestone, 0);
                            }
                        }
                    }
                    if (random.nextInt(50) == 0) {
                        final int j5 = this.getTopBlock(world, i2, k2) - 1;
                        if (this.isSurface(world, i2, j5, k2)) {
                            if (random.nextInt(3) == 0) {
                                this.setBlockAndMetadata(world, i2, j5 + 1, k2, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, j5 + 1, k2, this.brickSlabBlock, this.brickSlabMeta);
                            }
                            this.setGrassToDirt(world, i2, j5, k2);
                        }
                    }
                }
            }
        }
        int chestX = width - 1;
        int chestZ = width - 1;
        if (random.nextBoolean()) {
            chestX *= -1;
        }
        if (random.nextBoolean()) {
            chestZ *= -1;
        }
        final int chestY = this.getTopBlock(world, chestX, chestZ);
        if (this.isSurface(world, chestX, chestY - 1, chestZ)) {
            final int chestMeta = Direction.directionToFacing[random.nextInt(4)];
            this.setBlockAndMetadata(world, chestX, chestY, chestZ, LOTRMod.chestStone, chestMeta);
            this.fillChest(world, random, chestX, chestY, chestZ, LOTRChestContents.RUINED_HOUSE, 5);
            this.fillChest(world, random, chestX, chestY, chestZ, LOTRChestContents.ORC_DUNGEON, 4);
            this.fillChest(world, random, chestX, chestY, chestZ, LOTRChestContents.DUNEDAIN_TOWER, 4);
        }
        return true;
    }
}
