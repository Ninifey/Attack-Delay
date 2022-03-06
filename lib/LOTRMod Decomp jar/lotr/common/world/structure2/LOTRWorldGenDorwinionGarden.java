// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionGarden extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenDorwinionGarden(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int maxEdgeHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -7; k2 <= 7; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                    if ((Math.abs(i2) == 7 || Math.abs(k2) == 7) && j2 > maxEdgeHeight) {
                        maxEdgeHeight = j2;
                    }
                }
            }
            super.originY = this.getY(maxEdgeHeight);
        }
        final int r = 25;
        final int h = 4;
        final int gardenR = 6;
        final int leafR = 7;
        for (int i3 = -r; i3 <= r; ++i3) {
            for (int k3 = -r; k3 <= r; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                boolean within = false;
                for (int j3 = 0; j3 >= -h; --j3) {
                    final int j4 = j3 + r - 2;
                    final int d = i4 * i4 + j4 * j4 + k4 * k4;
                    if (d < r * r) {
                        final boolean grass = !this.isOpaque(world, i3, j3 + 1, k3);
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                        within = true;
                    }
                }
                if (within) {
                    for (int j3 = -h - 1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    final int dh = i4 * i4 + k4 * k4;
                    if (dh < gardenR * gardenR) {
                        this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.brick5, 2);
                    }
                    else if (dh < leafR * leafR) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.leaves6, 6);
                    }
                    else if (random.nextInt(5) == 0) {
                        final BiomeGenBase biome = this.getBiome(world, i3, k3);
                        final int j5 = this.getTopBlock(world, i3, k3);
                        biome.plantFlower(world, random, this.getX(i3, k3), this.getY(j5), this.getZ(i3, k3));
                    }
                    if (i4 == 6 && k4 == 6) {
                        this.setGrassToDirt(world, i3, 0, k3);
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.brick5, 2);
                        this.setBlockAndMetadata(world, i3, 2, k3, LOTRMod.brick5, 2);
                        this.setBlockAndMetadata(world, i3, 3, k3, LOTRMod.wall3, 10);
                        this.setBlockAndMetadata(world, i3, 4, k3, Blocks.torch, 5);
                    }
                    if ((i4 == gardenR && k4 <= 1) || (k4 == gardenR && i4 <= 1)) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.stained_hardened_clay, 0);
                        this.setAir(world, i3, 1, k3);
                    }
                    if ((i4 == gardenR - 1 && k4 == 2) || (k4 == gardenR - 1 && i4 == 2)) {
                        this.setGrassToDirt(world, i3, 0, k3);
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.brick5, 2);
                        this.setBlockAndMetadata(world, i3, 2, k3, LOTRMod.slabSingle9, 7);
                    }
                    if ((i4 == 3 && k4 <= 2) || (k4 == 3 && i4 <= 2)) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.stained_hardened_clay, 0);
                    }
                    if (i4 == 2 && k4 == 2) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.slabSingle9, 7);
                    }
                    if ((i4 == 2 && k4 <= 1) || (k4 == 2 && i4 <= 1)) {
                        for (int j6 = -1; j6 <= 1; ++j6) {
                            this.setBlockAndMetadata(world, i3, j6, k3, LOTRMod.brick5, 2);
                        }
                    }
                    if (i4 <= 1 && k4 <= 1) {
                        this.setBlockAndMetadata(world, i3, -2, k3, LOTRMod.brick5, 2);
                        for (int j6 = -1; j6 <= 1; ++j6) {
                            this.setBlockAndMetadata(world, i3, j6, k3, Blocks.water, 0);
                        }
                    }
                }
            }
        }
        return true;
    }
}
