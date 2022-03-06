// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRTreeType;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRVanillaSaplings
{
    public static void growTree(final World world, final int i, final int j, final int k, final Random random) {
        final Block block = world.getBlock(i, j, k);
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        WorldGenerator treeGen = null;
        int trunkNeg = 0;
        int trunkPos = 0;
        int xOffset = 0;
        int zOffset = 0;
        if (meta == 0) {
            final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, block, meta);
            if (partyTree != null) {
                treeGen = (WorldGenerator)LOTRTreeType.OAK_PARTY.create(true, random);
                trunkPos = (trunkNeg = 1);
                xOffset = partyTree[0];
                zOffset = partyTree[1];
            }
            if (treeGen == null) {
                if (random.nextInt(10) == 0) {
                    treeGen = (WorldGenerator)LOTRTreeType.OAK_LARGE.create(true, random);
                }
                else {
                    treeGen = (WorldGenerator)LOTRTreeType.OAK.create(true, random);
                }
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
            }
        }
        if (meta == 1) {
            for (int i2 = 0; i2 >= -1; --i2) {
                for (int k2 = 0; k2 >= -1; --k2) {
                    if (isSameSapling(world, i + i2, j, k + k2, meta) && isSameSapling(world, i + i2 + 1, j, k + k2, meta) && isSameSapling(world, i + i2, j, k + k2 + 1, meta) && isSameSapling(world, i + i2 + 1, j, k + k2 + 1, meta)) {
                        if (random.nextBoolean()) {
                            treeGen = (WorldGenerator)LOTRTreeType.SPRUCE_MEGA.create(true, random);
                        }
                        else {
                            treeGen = (WorldGenerator)LOTRTreeType.SPRUCE_MEGA_THIN.create(true, random);
                        }
                        trunkNeg = 0;
                        trunkPos = 1;
                        xOffset = i2;
                        zOffset = k2;
                        break;
                    }
                }
                if (treeGen != null) {
                    break;
                }
            }
            if (treeGen == null) {
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
                treeGen = (WorldGenerator)LOTRTreeType.SPRUCE.create(true, random);
            }
        }
        if (meta == 2) {
            final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, block, meta);
            if (partyTree != null) {
                treeGen = (WorldGenerator)LOTRTreeType.BIRCH_PARTY.create(true, random);
                trunkPos = (trunkNeg = 1);
                xOffset = partyTree[0];
                zOffset = partyTree[1];
            }
            if (treeGen == null) {
                if (random.nextInt(10) == 0) {
                    treeGen = (WorldGenerator)LOTRTreeType.BIRCH_LARGE.create(true, random);
                }
                else {
                    treeGen = (WorldGenerator)LOTRTreeType.BIRCH.create(true, random);
                }
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
            }
        }
        if (meta == 3) {
            for (int i2 = 0; i2 >= -1; --i2) {
                for (int k2 = 0; k2 >= -1; --k2) {
                    if (isSameSapling(world, i + i2, j, k + k2, meta) && isSameSapling(world, i + i2 + 1, j, k + k2, meta) && isSameSapling(world, i + i2, j, k + k2 + 1, meta) && isSameSapling(world, i + i2 + 1, j, k + k2 + 1, meta)) {
                        treeGen = (WorldGenerator)LOTRTreeType.JUNGLE_LARGE.create(true, random);
                        trunkNeg = 0;
                        trunkPos = 1;
                        xOffset = i2;
                        zOffset = k2;
                        break;
                    }
                }
                if (treeGen != null) {
                    break;
                }
            }
            if (treeGen == null) {
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
                treeGen = (WorldGenerator)LOTRTreeType.JUNGLE.create(true, random);
            }
        }
        if (meta == 4) {
            treeGen = (WorldGenerator)LOTRTreeType.ACACIA.create(true, random);
        }
        if (meta == 5) {
            final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, block, meta);
            if (partyTree != null) {
                treeGen = (WorldGenerator)LOTRTreeType.DARK_OAK_PARTY.create(true, random);
                trunkPos = (trunkNeg = 1);
                xOffset = partyTree[0];
                zOffset = partyTree[1];
            }
            if (treeGen == null) {
                for (int i3 = 0; i3 >= -1; --i3) {
                    for (int k3 = 0; k3 >= -1; --k3) {
                        if (isSameSapling(world, i + i3, j, k + k3, meta) && isSameSapling(world, i + i3 + 1, j, k + k3, meta) && isSameSapling(world, i + i3, j, k + k3 + 1, meta) && isSameSapling(world, i + i3 + 1, j, k + k3 + 1, meta)) {
                            treeGen = (WorldGenerator)LOTRTreeType.DARK_OAK.create(true, random);
                            trunkNeg = 0;
                            trunkPos = 1;
                            xOffset = i3;
                            zOffset = k3;
                            break;
                        }
                    }
                    if (treeGen != null) {
                        break;
                    }
                }
            }
            if (treeGen == null) {
                return;
            }
        }
        for (int i2 = -trunkNeg; i2 <= trunkPos; ++i2) {
            for (int k2 = -trunkNeg; k2 <= trunkPos; ++k2) {
                world.setBlock(i + xOffset + i2, j, k + zOffset + k2, Blocks.air, 0, 4);
            }
        }
        if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
            for (int i2 = -trunkNeg; i2 <= trunkPos; ++i2) {
                for (int k2 = -trunkNeg; k2 <= trunkPos; ++k2) {
                    world.setBlock(i + xOffset + i2, j, k + zOffset + k2, Blocks.sapling, meta, 4);
                }
            }
        }
    }
    
    private static boolean isSameSapling(final World world, final int i, final int j, final int k, final int meta) {
        return LOTRBlockSaplingBase.isSameSapling(world, i, j, k, Blocks.sapling, meta);
    }
}
