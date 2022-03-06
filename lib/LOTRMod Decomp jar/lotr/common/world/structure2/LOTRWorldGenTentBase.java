// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;

public abstract class LOTRWorldGenTentBase extends LOTRWorldGenStructureBase2
{
    protected Block tentBlock;
    protected int tentMeta;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block tableBlock;
    protected LOTRChestContents chestContents;
    protected boolean hasOrcForge;
    protected boolean hasOrcTorches;
    
    public LOTRWorldGenTentBase(final boolean flag) {
        super(flag);
        this.hasOrcForge = false;
        this.hasOrcTorches = false;
    }
    
    protected boolean isOrcTent() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    final BiomeGenBase biome = this.getBiome(world, i2, k2);
                    if (biome instanceof LOTRBiomeGenMordor) {
                        final int randomGround = random.nextInt(3);
                        if (randomGround == 0) {
                            this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.rock, 0);
                        }
                        else if (randomGround == 1) {
                            this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.mordorDirt, 0);
                        }
                        else if (randomGround == 2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.mordorGravel, 0);
                        }
                    }
                    else {
                        final int randomGround = random.nextInt(3);
                        if (randomGround == 0) {
                            if (j2 == 0) {
                                this.setBiomeTop(world, i2, j2, k2);
                            }
                            else {
                                this.setBiomeFiller(world, i2, j2, k2);
                            }
                        }
                        else if (randomGround == 1) {
                            this.setBlockAndMetadata(world, i2, j2, k2, Blocks.gravel, 0);
                        }
                        else if (randomGround == 2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, Blocks.cobblestone, 0);
                        }
                    }
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = 1; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int k3 = -3; k3 <= 3; ++k3) {
            for (final int i3 : new int[] { -2, 2 }) {
                for (int j3 = 1; j3 <= 2; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, this.tentBlock, this.tentMeta);
                }
                this.setGrassToDirt(world, i3, 0, k3);
            }
            this.setBlockAndMetadata(world, -1, 3, k3, this.tentBlock, this.tentMeta);
            this.setBlockAndMetadata(world, 1, 3, k3, this.tentBlock, this.tentMeta);
            this.setBlockAndMetadata(world, 0, 4, k3, this.tentBlock, this.tentMeta);
        }
        for (int j4 = 1; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, j4, 3, this.fenceBlock, this.fenceMeta);
        }
        if (this.hasOrcTorches) {
            this.placeOrcTorch(world, -1, 1, -3);
            this.placeOrcTorch(world, 1, 1, -3);
            this.placeOrcTorch(world, -1, 1, 3);
            this.placeOrcTorch(world, 1, 1, 3);
        }
        else {
            this.setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
        }
        if (random.nextBoolean()) {
            if (this.hasOrcForge) {
                this.setBlockAndMetadata(world, -1, 1, 0, LOTRMod.orcForge, 4);
                this.setGrassToDirt(world, -1, 0, 0);
                this.setBlockAndMetadata(world, -1, 1, -1, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, -1, 1, 1, this.fenceBlock, this.fenceMeta);
            }
            else {
                this.placeChest(world, random, -1, 1, 0, 4, this.chestContents);
                this.setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
                this.setGrassToDirt(world, -1, 0, -1);
                this.setBlockAndMetadata(world, -1, 1, 1, this.tableBlock, 0);
                this.setGrassToDirt(world, -1, 0, 1);
            }
        }
        else if (this.hasOrcForge) {
            this.setBlockAndMetadata(world, 1, 1, 0, LOTRMod.orcForge, 5);
            this.setGrassToDirt(world, 1, 0, 0);
            this.setBlockAndMetadata(world, 1, 1, -1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 1, 1, 1, this.fenceBlock, this.fenceMeta);
        }
        else {
            this.placeChest(world, random, 1, 1, 0, 5, this.chestContents);
            this.setBlockAndMetadata(world, 1, 1, -1, Blocks.crafting_table, 0);
            this.setGrassToDirt(world, 1, 0, -1);
            this.setBlockAndMetadata(world, 1, 1, 1, this.tableBlock, 0);
            this.setGrassToDirt(world, 1, 0, 1);
        }
        return true;
    }
}
