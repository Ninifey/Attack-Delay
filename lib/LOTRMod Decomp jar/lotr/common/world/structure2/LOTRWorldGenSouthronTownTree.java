// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownTree extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronTownTree(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; j3 <= 12; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, super.stoneBlock, super.stoneMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                if (i3 == 2 || k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.stoneBlock, super.stoneMeta);
                    if ((i3 + k3) % 2 == 0) {
                        this.setBlockAndMetadata(world, i2, 2, k2, super.brickSlabBlock, super.brickSlabMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i2, 1, k2, (Block)Blocks.grass, 0);
                }
            }
        }
        for (int l = 0; l < 16; ++l) {
            final int i4 = 0;
            final int j2 = 2;
            final int k4 = 0;
            final LOTRTreeType tree = this.getRandomTree(random);
            final WorldGenerator treeGen = (WorldGenerator)tree.create(super.notifyChanges, random);
            if (treeGen != null && treeGen.generate(world, random, this.getX(i4, k4), this.getY(j2), this.getZ(i4, k4))) {
                break;
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                if ((i2 != 0 || k2 != 0) && this.isAir(world, i2, 2, k2)) {
                    this.plantTallGrass(world, random, i2, 2, k2);
                }
            }
        }
        return true;
    }
    
    private LOTRTreeType getRandomTree(final Random random) {
        final List<LOTRTreeType> treeList = new ArrayList<LOTRTreeType>();
        treeList.add(LOTRTreeType.CEDAR);
        treeList.add(LOTRTreeType.CYPRESS);
        treeList.add(LOTRTreeType.PALM);
        treeList.add(LOTRTreeType.DATE_PALM);
        treeList.add(LOTRTreeType.OLIVE);
        return treeList.get(random.nextInt(treeList.size()));
    }
}
