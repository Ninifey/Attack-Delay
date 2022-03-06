// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;

public class LOTRWorldGenRuinedRohanWatchtower extends LOTRWorldGenStructureBase
{
    private Block plankBlock;
    private int plankMeta;
    private Block woodBlock;
    private int woodMeta;
    private Block stairBlock;
    
    public LOTRWorldGenRuinedRohanWatchtower(final boolean flag) {
        super(flag);
        this.plankBlock = LOTRMod.planks;
        this.plankMeta = 3;
        this.woodBlock = LOTRMod.wood;
        this.woodMeta = 3;
        this.stairBlock = LOTRMod.stairsCharred;
    }
    
    public boolean generate(final World world, final Random random, final int i, int j, final int k) {
        if (super.restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || world.getBiomeGenForCoords(i, k) != LOTRBiome.rohanUrukHighlands)) {
            return false;
        }
        final int height = 5 + random.nextInt(4);
        j += height;
        if (super.restrictions) {
            for (int i2 = i - 4; i2 <= i + 4; ++i2) {
                for (int j2 = j - 3; j2 <= j + 4; ++j2) {
                    for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                        if (!world.isAirBlock(i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        this.generateBasicStructure(world, random, i, j, k);
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                return this.generateFacingSouth(world, random, i, j, k);
            }
            case 1: {
                return this.generateFacingWest(world, random, i, j, k);
            }
            case 2: {
                return this.generateFacingNorth(world, random, i, j, k);
            }
            case 3: {
                return this.generateFacingEast(world, random, i, j, k);
            }
            default: {
                return true;
            }
        }
    }
    
    private void generateBasicStructure(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i - 3, j2, k - 3) && j2 >= 0; --j2) {
            this.func_150516_a(world, i - 3, j2, k - 3, this.plankBlock, this.plankMeta);
        }
        for (int j2 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i - 3, j2, k + 3) && j2 >= 0; --j2) {
            this.func_150516_a(world, i - 3, j2, k + 3, this.plankBlock, this.plankMeta);
        }
        for (int j2 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i + 3, j2, k - 3) && j2 >= 0; --j2) {
            this.func_150516_a(world, i + 3, j2, k - 3, this.plankBlock, this.plankMeta);
        }
        for (int j2 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i + 3, j2, k + 3) && j2 >= 0; --j2) {
            this.func_150516_a(world, i + 3, j2, k + 3, this.plankBlock, this.plankMeta);
        }
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                if (random.nextInt(4) != 0) {
                    this.func_150516_a(world, i2, j, k2, this.plankBlock, this.plankMeta);
                }
            }
        }
        for (int i2 = i - 2 + random.nextInt(3); i2 <= i + 2 - random.nextInt(3); ++i2) {
            this.func_150516_a(world, i2, j, k - 3, this.woodBlock, this.woodMeta | 0x4);
        }
        for (int i2 = i - 2 + random.nextInt(3); i2 <= i + 2 - random.nextInt(3); ++i2) {
            this.func_150516_a(world, i2, j, k + 3, this.woodBlock, this.woodMeta | 0x4);
        }
        for (int i2 = i - 2 + random.nextInt(3); i2 <= i + 2 - random.nextInt(3); ++i2) {
            this.func_150516_a(world, i2, j, k - 4, this.stairBlock, 6);
        }
        for (int i2 = i - 2 + random.nextInt(3); i2 <= i + 2 - random.nextInt(3); ++i2) {
            this.func_150516_a(world, i2, j, k + 4, this.stairBlock, 7);
        }
        for (int k3 = k - 2 + random.nextInt(3); k3 <= k + 2 - random.nextInt(3); ++k3) {
            this.func_150516_a(world, i - 3, j, k3, this.woodBlock, this.woodMeta | 0x8);
        }
        for (int k3 = k - 2 + random.nextInt(3); k3 <= k + 2 - random.nextInt(3); ++k3) {
            this.func_150516_a(world, i + 3, j, k3, this.woodBlock, this.woodMeta | 0x8);
        }
        for (int k3 = k - 2 + random.nextInt(3); k3 <= k + 2 - random.nextInt(3); ++k3) {
            this.func_150516_a(world, i - 4, j, k3, this.stairBlock, 4);
        }
        for (int k3 = k - 2 + random.nextInt(3); k3 <= k + 2 - random.nextInt(3); ++k3) {
            this.func_150516_a(world, i + 4, j, k3, this.stairBlock, 5);
        }
    }
    
    private boolean generateFacingSouth(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i, j2, k + 3) && j2 >= 0; --j2) {
            this.func_150516_a(world, i, j2, k + 3, this.plankBlock, this.plankMeta);
        }
        for (int k2 = k - 2; k2 <= k + 2; ++k2) {
            final int k3 = Math.abs(k - k2);
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i - 3, j3, k2) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((k3 == 2 && j4 % 4 == 1) || (k3 == 1 && j4 % 2 == 0) || (k3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i - 3, j3, k2, this.woodBlock, this.woodMeta);
                }
            }
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i + 3, j3, k2) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((k3 == 2 && j4 % 4 == 1) || (k3 == 1 && j4 % 2 == 0) || (k3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i + 3, j3, k2, this.woodBlock, this.woodMeta);
                }
            }
        }
        return true;
    }
    
    private boolean generateFacingWest(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i - 3, j2, k) && j2 >= 0; --j2) {
            this.func_150516_a(world, i - 3, j2, k, this.plankBlock, this.plankMeta);
        }
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            final int i3 = Math.abs(i - i2);
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i2, j3, k - 3) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((i3 == 2 && j4 % 4 == 1) || (i3 == 1 && j4 % 2 == 0) || (i3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i2, j3, k - 3, this.woodBlock, this.woodMeta);
                }
            }
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i2, j3, k + 3) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((i3 == 2 && j4 % 4 == 1) || (i3 == 1 && j4 % 2 == 0) || (i3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i2, j3, k + 3, this.woodBlock, this.woodMeta);
                }
            }
        }
        return true;
    }
    
    private boolean generateFacingNorth(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i, j2, k - 3) && j2 >= 0; --j2) {
            this.func_150516_a(world, i, j2, k - 3, this.plankBlock, this.plankMeta);
        }
        for (int k2 = k - 2; k2 <= k + 2; ++k2) {
            final int k3 = Math.abs(k - k2);
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i - 3, j3, k2) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((k3 == 2 && j4 % 4 == 1) || (k3 == 1 && j4 % 2 == 0) || (k3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i - 3, j3, k2, this.woodBlock, this.woodMeta);
                }
            }
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i + 3, j3, k2) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((k3 == 2 && j4 % 4 == 1) || (k3 == 1 && j4 % 2 == 0) || (k3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i + 3, j3, k2, this.woodBlock, this.woodMeta);
                }
            }
        }
        return true;
    }
    
    private boolean generateFacingEast(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i + 3, j2, k) && j2 >= 0; --j2) {
            this.func_150516_a(world, i + 3, j2, k, this.plankBlock, this.plankMeta);
        }
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            final int i3 = Math.abs(i - i2);
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i2, j3, k - 3) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((i3 == 2 && j4 % 4 == 1) || (i3 == 1 && j4 % 2 == 0) || (i3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i2, j3, k - 3, this.woodBlock, this.woodMeta);
                }
            }
            for (int j3 = j - 1; !LOTRMod.isOpaque(world, i2, j3, k + 3) && j3 >= 0; --j3) {
                final int j4 = j - j3;
                if ((i3 == 2 && j4 % 4 == 1) || (i3 == 1 && j4 % 2 == 0) || (i3 == 0 && j4 % 4 == 3 && random.nextInt(3) == 0)) {
                    this.func_150516_a(world, i2, j3, k + 3, this.woodBlock, this.woodMeta);
                }
            }
        }
        return true;
    }
}
