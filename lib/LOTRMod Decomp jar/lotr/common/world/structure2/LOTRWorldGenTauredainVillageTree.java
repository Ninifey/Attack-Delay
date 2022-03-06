// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainVillageTree extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainVillageTree(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 4;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                this.layFoundation(world, i2, k2);
                for (int j2 = 1; j2 <= 12; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 3 || k3 == 3) {
                    if (i3 == 3 && k3 == 3) {
                        this.setBlockAndMetadata(world, i2, 1, k2, super.woodBlock, super.woodMeta);
                        this.setBlockAndMetadata(world, i2, 2, k2, super.woodBlock, super.woodMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 1, k2, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i2, 2, k2, super.brickSlabBlock, super.brickSlabMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.mudGrass, 0);
                    if (random.nextInt(2) == 0) {
                        if (random.nextBoolean()) {
                            this.setBlockAndMetadata(world, i2, 2, k2, (Block)Blocks.tallgrass, 1);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, 2, k2, (Block)Blocks.tallgrass, 2);
                        }
                    }
                }
            }
        }
        final int treeX = 0;
        final int treeY = 2;
        final int treeZ = 0;
        this.setAir(world, treeX, treeY, treeZ);
        for (int attempts = 0; attempts < 20; ++attempts) {
            LOTRTreeType treeType = null;
            final int randomTree = random.nextInt(4);
            if (randomTree == 0 || randomTree == 1) {
                treeType = LOTRTreeType.JUNGLE;
            }
            if (randomTree == 2) {
                treeType = LOTRTreeType.MANGO;
            }
            if (randomTree == 3) {
                treeType = LOTRTreeType.BANANA;
            }
            if (treeType.create(super.notifyChanges, random).generate(world, random, this.getX(treeX, treeZ), this.getY(treeY), this.getZ(treeX, treeZ))) {
                break;
            }
        }
        return true;
    }
}
