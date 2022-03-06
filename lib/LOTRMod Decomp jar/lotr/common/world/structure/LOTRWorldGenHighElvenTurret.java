// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenTurret extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenHighElvenTurret(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions) {
            final Block block = world.getBlock(i, j - 1, k);
            if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
                return false;
            }
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 6;
                break;
            }
            case 1: {
                i -= 6;
                break;
            }
            case 2: {
                k -= 6;
                break;
            }
            case 3: {
                i += 6;
                break;
            }
        }
        for (int i2 = i - 4; i2 <= i + 4; ++i2) {
            for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                for (int j2 = j; (j2 == j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i2, j2, k2, LOTRMod.brick3, 2);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = j + 1; j2 <= j + 7; ++j2) {
                    if (Math.abs(i2 - i) == 4 || Math.abs(k2 - k) == 4) {
                        this.func_150516_a(world, i2, j2, k2, LOTRMod.brick3, 2);
                    }
                    else {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int i2 = i - 3; i2 <= i + 3; ++i2) {
            for (int k2 = k - 3; k2 <= k + 3; ++k2) {
                if (Math.abs(i2 - i) % 2 == Math.abs(k2 - k) % 2) {
                    this.func_150516_a(world, i2, j, k2, LOTRMod.pillar, 10);
                }
                else {
                    this.func_150516_a(world, i2, j, k2, (Block)Blocks.double_stone_slab, 0);
                }
            }
        }
        for (int j3 = j + 1; j3 <= j + 7; ++j3) {
            this.func_150516_a(world, i - 3, j3, k - 3, LOTRMod.pillar, 10);
            this.func_150516_a(world, i - 3, j3, k + 3, LOTRMod.pillar, 10);
            this.func_150516_a(world, i + 3, j3, k - 3, LOTRMod.pillar, 10);
            this.func_150516_a(world, i + 3, j3, k + 3, LOTRMod.pillar, 10);
        }
        for (int i2 = i - 4; i2 <= i + 4; ++i2) {
            this.func_150516_a(world, i2, j + 7, k - 4, LOTRMod.stairsHighElvenBrick, 2);
            this.func_150516_a(world, i2, j + 7, k + 4, LOTRMod.stairsHighElvenBrick, 3);
        }
        for (int k3 = k - 3; k3 <= k + 3; ++k3) {
            this.func_150516_a(world, i - 4, j + 7, k3, LOTRMod.stairsHighElvenBrick, 0);
            this.func_150516_a(world, i + 4, j + 7, k3, LOTRMod.stairsHighElvenBrick, 1);
        }
        for (int i2 = i - 3; i2 <= i + 3; ++i2) {
            for (int k2 = k - 3; k2 <= k + 3; ++k2) {
                for (int j2 = j + 7; j2 <= j + 15; ++j2) {
                    if (Math.abs(i2 - i) == 3 || Math.abs(k2 - k) == 3) {
                        if (j2 - j < 10 || j2 - j > 14 || Math.abs(i2 - i) < 3 || Math.abs(k2 - k) < 3) {
                            this.func_150516_a(world, i2, j2, k2, LOTRMod.brick3, 2);
                        }
                    }
                    else {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int i2 = i - 4; i2 <= i + 4; ++i2) {
            for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                for (int j2 = j + 16; j2 <= j + 18; ++j2) {
                    if (j2 - j == 16 || Math.abs(i2 - i) == 4 || Math.abs(k2 - k) == 4) {
                        if (j2 - j == 18 && (Math.abs(i2 - i) % 2 == 1 || Math.abs(k2 - k) % 2 == 1)) {
                            this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                        }
                        else {
                            this.func_150516_a(world, i2, j2, k2, LOTRMod.brick3, 2);
                        }
                    }
                    else {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int i2 = i - 4; i2 <= i + 4; ++i2) {
            this.func_150516_a(world, i2, j + 16, k - 4, LOTRMod.stairsHighElvenBrick, 6);
            this.func_150516_a(world, i2, j + 16, k + 4, LOTRMod.stairsHighElvenBrick, 7);
        }
        for (int k3 = k - 3; k3 <= k + 3; ++k3) {
            this.func_150516_a(world, i - 4, j + 16, k3, LOTRMod.stairsHighElvenBrick, 4);
            this.func_150516_a(world, i + 4, j + 16, k3, LOTRMod.stairsHighElvenBrick, 5);
        }
        if (rotation == 0) {
            for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                this.func_150516_a(world, i2, j, k - 5, (Block)Blocks.double_stone_slab, 0);
                this.func_150516_a(world, i2, j, k - 4, (Block)Blocks.double_stone_slab, 0);
            }
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i - 1, j3, k - 5, LOTRMod.brick3, 2);
                this.func_150516_a(world, i, j3, k - 5, Blocks.air, 0);
                this.func_150516_a(world, i, j3, k - 4, Blocks.air, 0);
                this.func_150516_a(world, i + 1, j3, k - 5, LOTRMod.brick3, 2);
            }
            this.func_150516_a(world, i - 1, j + 3, k - 5, LOTRMod.stairsHighElvenBrick, 0);
            this.func_150516_a(world, i, j + 3, k - 5, LOTRMod.brick3, 2);
            this.func_150516_a(world, i + 1, j + 3, k - 5, LOTRMod.stairsHighElvenBrick, 1);
            for (int i2 = i + 1; i2 <= i + 2; ++i2) {
                for (int j4 = j + 1; j4 <= j + 7; ++j4) {
                    this.func_150516_a(world, i2, j4, k + 3, LOTRMod.brick3, 2);
                }
                for (int j4 = j + 1; j4 <= j + 16; ++j4) {
                    this.func_150516_a(world, i2, j4, k + 2, Blocks.ladder, 2);
                }
            }
        }
        else if (rotation == 1) {
            for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                this.func_150516_a(world, i + 5, j, k3, (Block)Blocks.double_stone_slab, 0);
                this.func_150516_a(world, i + 4, j, k3, (Block)Blocks.double_stone_slab, 0);
            }
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i + 5, j3, k - 1, LOTRMod.brick3, 2);
                this.func_150516_a(world, i + 5, j3, k, Blocks.air, 0);
                this.func_150516_a(world, i + 4, j3, k, Blocks.air, 0);
                this.func_150516_a(world, i + 5, j3, k + 1, LOTRMod.brick3, 2);
            }
            this.func_150516_a(world, i + 5, j + 3, k - 1, LOTRMod.stairsHighElvenBrick, 2);
            this.func_150516_a(world, i + 5, j + 3, k, LOTRMod.brick3, 2);
            this.func_150516_a(world, i + 5, j + 3, k + 1, LOTRMod.stairsHighElvenBrick, 3);
            for (int k3 = k - 1; k3 >= k - 2; --k3) {
                for (int j4 = j + 1; j4 <= j + 7; ++j4) {
                    this.func_150516_a(world, i - 3, j4, k3, LOTRMod.brick3, 2);
                }
                for (int j4 = j + 1; j4 <= j + 16; ++j4) {
                    this.func_150516_a(world, i - 2, j4, k3, Blocks.ladder, 5);
                }
            }
        }
        else if (rotation == 2) {
            for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                this.func_150516_a(world, i2, j, k + 5, (Block)Blocks.double_stone_slab, 0);
                this.func_150516_a(world, i2, j, k + 4, (Block)Blocks.double_stone_slab, 0);
            }
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i - 1, j3, k + 5, LOTRMod.brick3, 2);
                this.func_150516_a(world, i, j3, k + 5, Blocks.air, 0);
                this.func_150516_a(world, i, j3, k + 4, Blocks.air, 0);
                this.func_150516_a(world, i + 1, j3, k + 5, LOTRMod.brick3, 2);
            }
            this.func_150516_a(world, i - 1, j + 3, k + 5, LOTRMod.stairsHighElvenBrick, 0);
            this.func_150516_a(world, i, j + 3, k + 5, LOTRMod.brick3, 2);
            this.func_150516_a(world, i + 1, j + 3, k + 5, LOTRMod.stairsHighElvenBrick, 1);
            for (int i2 = i - 1; i2 >= i - 2; --i2) {
                for (int j4 = j + 1; j4 <= j + 7; ++j4) {
                    this.func_150516_a(world, i2, j4, k - 3, LOTRMod.brick3, 2);
                }
                for (int j4 = j + 1; j4 <= j + 16; ++j4) {
                    this.func_150516_a(world, i2, j4, k - 2, Blocks.ladder, 3);
                }
            }
        }
        else if (rotation == 3) {
            for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                this.func_150516_a(world, i - 5, j, k3, (Block)Blocks.double_stone_slab, 0);
                this.func_150516_a(world, i - 4, j, k3, (Block)Blocks.double_stone_slab, 0);
            }
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i - 5, j3, k - 1, LOTRMod.brick3, 2);
                this.func_150516_a(world, i - 5, j3, k, Blocks.air, 0);
                this.func_150516_a(world, i - 4, j3, k, Blocks.air, 0);
                this.func_150516_a(world, i - 5, j3, k + 1, LOTRMod.brick3, 2);
            }
            this.func_150516_a(world, i - 5, j + 3, k - 1, LOTRMod.stairsHighElvenBrick, 2);
            this.func_150516_a(world, i - 5, j + 3, k, LOTRMod.brick3, 2);
            this.func_150516_a(world, i - 5, j + 3, k + 1, LOTRMod.stairsHighElvenBrick, 3);
            for (int k3 = k + 1; k3 <= k + 2; ++k3) {
                for (int j4 = j + 1; j4 <= j + 7; ++j4) {
                    this.func_150516_a(world, i + 3, j4, k3, LOTRMod.brick3, 2);
                }
                for (int j4 = j + 1; j4 <= j + 16; ++j4) {
                    this.func_150516_a(world, i + 2, j4, k3, Blocks.ladder, 4);
                }
            }
        }
        this.func_150516_a(world, i - 3, j + 3, k, LOTRMod.highElvenTorch, 1);
        this.func_150516_a(world, i + 3, j + 3, k, LOTRMod.highElvenTorch, 2);
        this.func_150516_a(world, i, j + 3, k - 3, LOTRMod.highElvenTorch, 3);
        this.func_150516_a(world, i, j + 3, k + 3, LOTRMod.highElvenTorch, 4);
        this.func_150516_a(world, i - 3, j + 18, k, LOTRMod.highElvenTorch, 1);
        this.func_150516_a(world, i + 3, j + 18, k, LOTRMod.highElvenTorch, 2);
        this.func_150516_a(world, i, j + 18, k - 3, LOTRMod.highElvenTorch, 3);
        this.func_150516_a(world, i, j + 18, k + 3, LOTRMod.highElvenTorch, 4);
        return true;
    }
}
