// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockSapling extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling() {
        this.setSaplingNames("shirePine", "mallorn", "mirkOak", "mirkOakRed");
    }
    
    @Override
    public void growTree(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        WorldGenerator treeGen = null;
        int trunkNeg = 0;
        int trunkPos = 0;
        int xOffset = 0;
        int zOffset = 0;
        boolean cross = false;
        if (meta == 0) {
            treeGen = (WorldGenerator)LOTRTreeType.SHIRE_PINE.create(true, random);
        }
        if (meta == 1) {
            Label_0226: {
                if (world.getBlock(i, j - 1, k) == LOTRMod.quenditeGrass) {
                    for (int i2 = -4; i2 <= 4; ++i2) {
                        for (int k2 = -4; k2 <= 4; ++k2) {
                            boolean canGenerate = true;
                        Label_0181:
                            for (int i3 = -2; i3 <= 2; ++i3) {
                                for (int k3 = -2; k3 <= 2; ++k3) {
                                    final int i4 = i + i2 + i3;
                                    final int k4 = k + k2 + k3;
                                    if (!this.isSameSapling(world, i4, j, k4, meta) || world.getBlock(i4, j - 1, k4) != LOTRMod.quenditeGrass) {
                                        canGenerate = false;
                                        break Label_0181;
                                    }
                                }
                            }
                            if (canGenerate) {
                                treeGen = (WorldGenerator)LOTRTreeType.MALLORN_EXTREME_SAPLING.create(true, random);
                                trunkPos = (trunkNeg = 2);
                                xOffset = i2;
                                zOffset = k2;
                                break Label_0226;
                            }
                        }
                    }
                }
            }
            if (treeGen == null) {
                final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, (Block)this, meta);
                if (partyTree != null) {
                    treeGen = (WorldGenerator)LOTRTreeType.MALLORN_PARTY.create(true, random);
                    trunkPos = (trunkNeg = 1);
                    xOffset = partyTree[0];
                    zOffset = partyTree[1];
                }
            }
            if (treeGen == null) {
                final int[] boughTree = LOTRBlockSaplingBase.findCrossShape(world, i, j, k, (Block)this, meta);
                if (boughTree != null) {
                    treeGen = (WorldGenerator)LOTRTreeType.MALLORN_BOUGHS.create(true, random);
                    trunkPos = (trunkNeg = 1);
                    xOffset = boughTree[0];
                    zOffset = boughTree[1];
                    cross = true;
                }
            }
            if (treeGen == null) {
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
                treeGen = (WorldGenerator)LOTRTreeType.MALLORN.create(true, random);
            }
        }
        if (meta == 2) {
            final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, (Block)this, meta);
            if (partyTree != null) {
                treeGen = (WorldGenerator)LOTRTreeType.MIRK_OAK_LARGE.create(true, random);
                trunkPos = (trunkNeg = 1);
                xOffset = partyTree[0];
                zOffset = partyTree[1];
            }
            if (treeGen == null) {
                treeGen = (WorldGenerator)LOTRTreeType.MIRK_OAK.create(true, random);
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
            }
        }
        if (meta == 3) {
            final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, (Block)this, meta);
            if (partyTree != null) {
                treeGen = (WorldGenerator)LOTRTreeType.RED_OAK_LARGE.create(true, random);
                trunkPos = (trunkNeg = 1);
                xOffset = partyTree[0];
                zOffset = partyTree[1];
            }
            if (treeGen == null) {
                treeGen = (WorldGenerator)LOTRTreeType.RED_OAK.create(true, random);
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
            }
        }
        for (int i2 = -trunkNeg; i2 <= trunkPos; ++i2) {
            for (int k2 = -trunkNeg; k2 <= trunkPos; ++k2) {
                if (!cross || Math.abs(i2) == 0 || Math.abs(k2) == 0) {
                    world.setBlock(i + xOffset + i2, j, k + zOffset + k2, Blocks.air, 0, 4);
                }
            }
        }
        if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
            for (int i2 = -trunkNeg; i2 <= trunkPos; ++i2) {
                for (int k2 = -trunkNeg; k2 <= trunkPos; ++k2) {
                    if (!cross || Math.abs(i2) == 0 || Math.abs(k2) == 0) {
                        world.setBlock(i + xOffset + i2, j, k + zOffset + k2, (Block)this, meta, 4);
                    }
                }
            }
        }
    }
    
    public boolean canReplace(final World world, final int i, final int j, final int k, final int side, final ItemStack item) {
        return super.canReplace(world, i, j, k, side, item) || (item.getItemDamage() == 1 && world.getBlock(i, j - 1, k) == LOTRMod.quenditeGrass);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        if (super.canBlockStay(world, i, j, k)) {
            return true;
        }
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        return meta == 1 && world.getBlock(i, j - 1, k) == LOTRMod.quenditeGrass;
    }
}
