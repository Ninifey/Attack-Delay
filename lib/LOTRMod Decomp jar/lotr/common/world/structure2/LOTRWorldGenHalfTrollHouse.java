// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.world.structure.LOTRChestContents;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHalfTrollHouse extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenHalfTrollHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int radius = 5;
        final int height = 6 + random.nextInt(4);
        this.setOriginAndRotation(world, i, j, k, rotation, radius + 1);
        if (super.restrictions) {
            for (int i2 = -radius; i2 <= radius; ++i2) {
                for (int k2 = -radius; k2 <= radius; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -radius; i2 <= radius; ++i2) {
            for (int k2 = -radius; k2 <= radius; ++k2) {
                for (int j2 = 0; j2 <= height; ++j2) {
                    final double f = (i2 * i2 + k2 * k2) / 2.0 - (8 - j2);
                    if (f < 8.0) {
                        if (j2 == 0) {
                            for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                                this.setBlockAndMetadata(world, i2, j3, k2, Blocks.hardened_clay, 0);
                                this.setGrassToDirt(world, i2, j3 - 1, k2);
                            }
                        }
                        if (f > 0.0) {
                            if (j2 <= 1 || j2 == height - 1) {
                                this.setBlockAndMetadata(world, i2, j2, k2, Blocks.stained_hardened_clay, 12);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, j2, k2, Blocks.hardened_clay, 0);
                            }
                        }
                        else if (j2 == 0) {
                            this.setBlockAndMetadata(world, i2, j2, k2, Blocks.cobblestone, 0);
                        }
                        else {
                            this.setAir(world, i2, j2, k2);
                        }
                    }
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -radius; k2 <= -radius + 1; ++k2) {
                this.setBlockAndMetadata(world, i2, 0, k2, Blocks.cobblestone, 0);
                for (int j2 = 1; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
            this.setBlockAndMetadata(world, i2, 4, -radius, LOTRMod.woodSlabSingle, 3);
        }
        this.setBlockAndMetadata(world, -2, 2, -radius, LOTRMod.fence, 3);
        this.setBlockAndMetadata(world, -2, 3, -radius, LOTRMod.woodSlabSingle, 3);
        this.setBlockAndMetadata(world, 2, 2, -radius, LOTRMod.fence, 3);
        this.setBlockAndMetadata(world, 2, 3, -radius, LOTRMod.woodSlabSingle, 3);
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 2 || k3 == 2 || (i3 == 0 && k3 == 0)) {
                    for (int j4 = -4; j4 <= 0; ++j4) {
                        this.setBlockAndMetadata(world, i2, j4, k2, Blocks.stained_hardened_clay, 12);
                    }
                }
                else if (i3 == 1 || k3 == 1) {
                    this.setBlockAndMetadata(world, i2, -4, k2, LOTRMod.hearth, 0);
                    this.setBlockAndMetadata(world, i2, -3, k2, (Block)Blocks.fire, 0);
                    this.setBlockAndMetadata(world, i2, -2, k2, Blocks.air, 0);
                    this.setBlockAndMetadata(world, i2, -1, k2, Blocks.air, 0);
                    this.setBlockAndMetadata(world, i2, 0, k2, Blocks.iron_bars, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, Blocks.cobblestone, 0);
        for (int l = 0; l < 8; ++l) {
            final int i4 = (2 + (l + 1) / 2 % 2) * IntMath.pow(-1, l / 4);
            final int k4 = (2 + (l + 3) / 2 % 2) * IntMath.pow(-1, (l + 2) / 4);
            this.setBlockAndMetadata(world, i4, 1, k4, Blocks.cobblestone, 0);
            this.setBlockAndMetadata(world, i4, 2, k4, LOTRMod.fence, 3);
            this.setBlockAndMetadata(world, i4, 3, k4, LOTRMod.fence, 3);
        }
        this.setBlockAndMetadata(world, -4, 3, 0, LOTRMod.fence, 3);
        this.setAir(world, -5, 3, 0);
        this.setBlockAndMetadata(world, 4, 3, 0, LOTRMod.fence, 3);
        this.setAir(world, 5, 3, 0);
        this.setBlockAndMetadata(world, 0, 3, 4, LOTRMod.fence, 3);
        this.setAir(world, 0, 3, 5);
        for (int i2 = -3; i2 <= 3; i2 += 6) {
            this.setBlockAndMetadata(world, i2, 1, -1, (Block)Blocks.stone_slab, 11);
            this.setBlockAndMetadata(world, i2, 1, 1, (Block)Blocks.stone_slab, 11);
            this.placeChest(world, random, i2, 1, 0, LOTRMod.chestBasket, 0, LOTRChestContents.HALF_TROLL_HOUSE);
        }
        this.setBlockAndMetadata(world, -1, 1, 3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, (Block)Blocks.stone_slab, 11);
        this.setBlockAndMetadata(world, 1, 1, 3, LOTRMod.halfTrollTable, 0);
        final LOTREntityHalfTroll halfTroll = new LOTREntityHalfTroll(world);
        this.spawnNPCAndSetHome(halfTroll, world, 0, 1, 0, 16);
        return true;
    }
}
