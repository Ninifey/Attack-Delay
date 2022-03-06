// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenCampBase extends LOTRWorldGenStructureBase2
{
    protected Block tableBlock;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    protected Block farmBaseBlock;
    protected int farmBaseMeta;
    protected Block farmCropBlock;
    protected int farmCropMeta;
    protected boolean hasOrcTorches;
    protected boolean hasSkulls;
    
    public LOTRWorldGenCampBase(final boolean flag) {
        super(flag);
        this.hasOrcTorches = false;
        this.hasSkulls = false;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        this.tableBlock = Blocks.crafting_table;
        this.brickBlock = Blocks.cobblestone;
        this.brickMeta = 0;
        this.brickSlabBlock = (Block)Blocks.stone_slab;
        this.brickSlabMeta = 3;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.fenceGateBlock = Blocks.fence_gate;
        this.farmBaseBlock = Blocks.farmland;
        this.farmBaseMeta = 7;
        this.farmCropBlock = Blocks.wheat;
        this.farmCropMeta = 7;
    }
    
    protected abstract LOTRWorldGenStructureBase2 createTent(final boolean p0, final Random p1);
    
    protected abstract LOTREntityNPC getCampCaptain(final World p0, final Random p1);
    
    protected void placeNPCRespawner(final World world, final Random random, final int i, final int j, final int k) {
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (super.restrictions) {
            if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k)) {
                return false;
            }
            if (world.getBlock(i, j, k).getMaterial().isLiquid()) {
                return false;
            }
        }
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int groundRange = 12, i2 = -groundRange; i2 <= groundRange; ++i2) {
            for (int k2 = -groundRange; k2 <= groundRange; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 * i3 + k3 * k3 < groundRange * groundRange) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    if (this.getBlock(world, i2, j2 - 1, k2) == Blocks.grass && random.nextInt(5) != 0) {
                        this.setBlockAndMetadata(world, i2, j2 - 1, k2, Blocks.dirt, 1);
                    }
                }
            }
        }
        int highestHeight = 0;
        for (int i4 = -1; i4 <= 1; ++i4) {
            for (int k4 = -1; k4 <= 1; ++k4) {
                final int j3 = this.getTopBlock(world, i4, k4);
                if (j3 > highestHeight) {
                    highestHeight = j3;
                }
            }
        }
        super.originY = this.getY(highestHeight);
        this.generateCentrepiece(world, random, 0, 0, 0);
        final LOTREntityNPC captain = this.getCampCaptain(world, random);
        if (captain != null) {
            captain.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 24);
        }
        for (int l = 0; l < 4; ++l) {
            final int tentX = MathHelper.getRandomIntegerInRange(random, -3, 3);
            final int tentZ = MathHelper.getRandomIntegerInRange(random, 6, 12);
            int i5 = 0;
            int k5 = 0;
            final int rot = l;
            if (rot == 0) {
                i5 = tentX;
                k5 = tentZ;
            }
            else if (rot == 1) {
                i5 = tentZ;
                k5 = -tentX;
            }
            else if (rot == 2) {
                i5 = -tentX;
                k5 = -tentZ;
            }
            else if (rot == 3) {
                i5 = -tentZ;
                k5 = tentX;
            }
            final int j4 = this.getTopBlock(world, i5, k5);
            this.generateSubstructure(this.createTent(super.notifyChanges, random), world, random, i5, j4, k5, rot);
        }
        if (this.hasOrcTorches) {
            for (final int i5 : new int[] { -2, 2 }) {
                for (final int k6 : new int[] { -2, 2 }) {
                    final int j5 = this.getTopBlock(world, i5, k6);
                    this.placeOrcTorch(world, i5, j5, k6);
                }
            }
        }
        if (this.generateFarm()) {
            int[] farmCoords = null;
            final int farmRange = 12;
            final int minFarmRange = 5;
        Label_0751:
            for (int m = 0; m < 32; ++m) {
                final int i6 = MathHelper.getRandomIntegerInRange(random, -farmRange, farmRange);
                final int k7 = MathHelper.getRandomIntegerInRange(random, -farmRange, farmRange);
                final int dSq = i6 * i6 + k7 * k7;
                if (dSq > minFarmRange * minFarmRange) {
                    for (int i7 = i6 - 2; i7 <= i6 + 2; ++i7) {
                        for (int k8 = k7 - 2; k8 <= k7 + 2; ++k8) {
                            final int j6 = this.getTopBlock(world, i7, k8) - 1;
                            if (!this.isSurface(world, i7, j6, k8)) {
                                continue Label_0751;
                            }
                            if (!this.isAir(world, i7, j6 + 1, k8) && !this.isReplaceable(world, i7, j6 + 1, k8)) {
                                continue Label_0751;
                            }
                        }
                    }
                    farmCoords = new int[] { i6, k7 };
                    break;
                }
            }
            if (farmCoords != null) {
                final int i5 = farmCoords[0];
                final int k5 = farmCoords[1];
                int highestFarmHeight = this.getTopBlock(world, i5, k5);
                for (int i8 = i5 - 2; i8 <= i5 + 2; ++i8) {
                    for (int k9 = k5 - 2; k9 <= k5 + 2; ++k9) {
                        final int j7 = this.getTopBlock(world, i8, k9);
                        if (j7 > highestFarmHeight) {
                            highestFarmHeight = j7;
                        }
                    }
                }
                for (int i8 = i5 - 2; i8 <= i5 + 2; ++i8) {
                    for (int k9 = k5 - 2; k9 <= k5 + 2; ++k9) {
                        for (int j7 = highestFarmHeight - 2; !this.isOpaque(world, i8, j7, k9) && this.getY(j7) >= 0; --j7) {
                            this.setBiomeFiller(world, i8, j7, k9);
                            this.setGrassToDirt(world, i8, j7 - 1, k9);
                        }
                        if (Math.abs(i8 - i5) == 2 || Math.abs(k9 - k5) == 2) {
                            this.setBlockAndMetadata(world, i8, highestFarmHeight, k9, this.fenceBlock, this.fenceMeta);
                            this.setBiomeTop(world, i8, highestFarmHeight - 1, k9);
                            this.setGrassToDirt(world, i8, highestFarmHeight - 2, k9);
                        }
                        else if (i8 == i5 && k9 == k5) {
                            this.setBlockAndMetadata(world, i8, highestFarmHeight - 1, k9, Blocks.water, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i8, highestFarmHeight, k9, this.farmCropBlock, this.farmCropMeta);
                            this.setBlockAndMetadata(world, i8, highestFarmHeight - 1, k9, this.farmBaseBlock, this.farmBaseMeta);
                            this.setGrassToDirt(world, i8, highestFarmHeight - 2, k9);
                        }
                    }
                }
                final int gate = random.nextInt(4);
                if (gate == 0) {
                    this.setBlockAndMetadata(world, i5, highestFarmHeight, k5 + 2, this.fenceGateBlock, 0);
                }
                else if (gate == 1) {
                    this.setBlockAndMetadata(world, i5 - 2, highestFarmHeight, k5, this.fenceGateBlock, 1);
                }
                else if (gate == 2) {
                    this.setBlockAndMetadata(world, i5, highestFarmHeight, k5 - 2, this.fenceGateBlock, 2);
                }
                else if (gate == 3) {
                    this.setBlockAndMetadata(world, i5 + 2, highestFarmHeight, k5, this.fenceGateBlock, 3);
                }
                final int scarecrowX = i5 + (random.nextBoolean() ? -2 : 2);
                final int scarecrowZ = k5 + (random.nextBoolean() ? -2 : 2);
                this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 1, scarecrowZ, this.fenceBlock, this.fenceMeta);
                if (this.hasOrcTorches) {
                    this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 2, scarecrowZ, Blocks.wool, 12);
                    this.placeSkull(world, random, scarecrowX, highestFarmHeight + 3, scarecrowZ);
                }
                else {
                    this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 2, scarecrowZ, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 3, scarecrowZ, Blocks.pumpkin, random.nextInt(4));
                }
            }
        }
        if (this.hasSkulls) {
            for (int l = 0; l < 6; ++l) {
                final int range = 8;
                final int i9 = MathHelper.getRandomIntegerInRange(random, -range, range);
                final int k10 = MathHelper.getRandomIntegerInRange(random, -range, range);
                if (i9 * i9 + k10 * k10 > 20) {
                    final int j8 = this.getTopBlock(world, i9, k10);
                    if (this.isSurface(world, i9, j8 - 1, k10) && this.isReplaceable(world, i9, j8, k10) && this.isAir(world, i9, j8 + 1, k10)) {
                        this.setBlockAndMetadata(world, i9, j8, k10, this.fenceBlock, this.fenceMeta);
                        this.placeSkull(world, random, i9, j8 + 1, k10);
                    }
                }
            }
            for (int l = 0; l < 6; ++l) {
                final int range = 12;
                final int i9 = MathHelper.getRandomIntegerInRange(random, -range, range);
                final int k10 = MathHelper.getRandomIntegerInRange(random, -range, range);
                if (i9 * i9 + k10 * k10 > 20) {
                    final int j8 = this.getTopBlock(world, i9, k10);
                    if (this.isSurface(world, i9, j8 - 1, k10) && this.isReplaceable(world, i9, j8, k10) && this.isAir(world, i9, j8 + 1, k10)) {
                        this.placeSkull(world, random, i9, j8, k10);
                    }
                }
            }
        }
        this.placeNPCRespawner(world, random, 0, 0, 0);
        return true;
    }
    
    protected boolean generateFarm() {
        return true;
    }
    
    protected void generateCentrepiece(final World world, final Random random, final int i, final int j, final int k) {
        for (int i2 = i - 1; i2 <= i + 1; ++i2) {
            for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                for (int j2 = j - 1; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                this.setBlockAndMetadata(world, i2, j, k2, this.brickSlabBlock, this.brickSlabMeta);
                this.setGrassToDirt(world, i2, j - 1, k2);
            }
        }
        this.setBlockAndMetadata(world, i, j, k, this.tableBlock, 0);
    }
}
