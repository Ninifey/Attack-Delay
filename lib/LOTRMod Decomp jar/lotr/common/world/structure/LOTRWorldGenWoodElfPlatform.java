// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.entity.LOTREntityNPCRespawner;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenWoodElfPlatform extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenWoodElfPlatform(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, final int j, int k) {
        int rotation = -1;
        if (super.restrictions) {
            rotation = random.nextInt(4);
            switch (rotation) {
                case 0: {
                    k -= 3;
                    break;
                }
                case 1: {
                    i += 3;
                    break;
                }
                case 2: {
                    k += 3;
                    break;
                }
                case 3: {
                    i -= 3;
                    break;
                }
            }
        }
        else if (super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        boolean flag = false;
        switch (rotation) {
            case 0: {
                flag = this.generateFacingSouth(world, random, i, j, k);
                break;
            }
            case 1: {
                flag = this.generateFacingWest(world, random, i, j, k);
                break;
            }
            case 2: {
                flag = this.generateFacingNorth(world, random, i, j, k);
                break;
            }
            case 3: {
                flag = this.generateFacingEast(world, random, i, j, k);
                break;
            }
        }
        if (flag) {
            final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
            respawner.setSpawnClass(LOTREntityWoodElf.class);
            respawner.setCheckRanges(8, -8, 8, 2);
            respawner.setSpawnRanges(3, -2, 2, 8);
            this.placeNPCRespawner(respawner, world, i, j + 1, k);
        }
        return false;
    }
    
    private boolean generateFacingSouth(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    if (!world.getBlock(i2, j2, k + 1).isWood((IBlockAccess)world, i2, j2, k + 1)) {
                        return false;
                    }
                    for (int k2 = k; k2 >= k - 3; --k2) {
                        if (!world.isAirBlock(i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        else {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    for (int k2 = k; k2 >= k - 3; --k2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int i2 = i - 1; i2 <= i + 1; ++i2) {
            for (int k3 = k; k3 >= k - 2; --k3) {
                this.func_150516_a(world, i2, j, k3, LOTRMod.planks2, 13);
            }
        }
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            this.func_150516_a(world, i2, j, k - 3, LOTRMod.stairsGreenOak, 6);
            this.func_150516_a(world, i2, j + 1, k - 3, LOTRMod.fence2, 13);
        }
        for (int k4 = k; k4 >= k - 2; --k4) {
            this.func_150516_a(world, i - 2, j, k4, LOTRMod.stairsGreenOak, 4);
            this.func_150516_a(world, i + 2, j, k4, LOTRMod.stairsGreenOak, 5);
            this.func_150516_a(world, i - 2, j + 1, k4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i + 2, j + 1, k4, LOTRMod.fence2, 13);
        }
        this.func_150516_a(world, i - 2, j + 2, k, LOTRMod.fence2, 13);
        this.func_150516_a(world, i - 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i + 2, j + 2, k, LOTRMod.fence2, 13);
        this.func_150516_a(world, i + 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i - 2, j + 2, k - 3, LOTRMod.fence2, 13);
        this.func_150516_a(world, i - 2, j + 3, k - 3, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i + 2, j + 2, k - 3, LOTRMod.fence2, 13);
        this.func_150516_a(world, i + 2, j + 3, k - 3, LOTRMod.woodElvenTorch, 5);
        for (int j3 = j; j3 >= 0 && LOTRMod.isOpaque(world, i, j3, k + 1) && (j3 >= j || !LOTRMod.isOpaque(world, i, j3, k)); --j3) {
            this.func_150516_a(world, i, j3, k, Blocks.ladder, 2);
        }
        return true;
    }
    
    private boolean generateFacingWest(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    if (!world.getBlock(i - 1, j2, k2).isWood((IBlockAccess)world, i - 1, j2, k2)) {
                        return false;
                    }
                    for (int i2 = i; i2 <= i + 3; ++i2) {
                        if (!world.isAirBlock(i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        else {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    for (int i2 = i; i2 <= i + 3; ++i2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int k2 = k - 1; k2 <= k + 1; ++k2) {
            for (int i3 = i; i3 <= i + 2; ++i3) {
                this.func_150516_a(world, i3, j, k2, LOTRMod.planks2, 13);
            }
        }
        for (int k2 = k - 2; k2 <= k + 2; ++k2) {
            this.func_150516_a(world, i + 3, j, k2, LOTRMod.stairsGreenOak, 5);
            this.func_150516_a(world, i + 3, j + 1, k2, LOTRMod.fence2, 13);
        }
        for (int i4 = i; i4 <= i + 2; ++i4) {
            this.func_150516_a(world, i4, j, k - 2, LOTRMod.stairsGreenOak, 6);
            this.func_150516_a(world, i4, j, k + 2, LOTRMod.stairsGreenOak, 7);
            this.func_150516_a(world, i4, j + 1, k - 2, LOTRMod.fence2, 13);
            this.func_150516_a(world, i4, j + 1, k + 2, LOTRMod.fence2, 13);
        }
        this.func_150516_a(world, i, j + 2, k - 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i + 3, j + 2, k - 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i + 3, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i + 3, j + 2, k + 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i + 3, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
        for (int j3 = j; j3 >= 0 && LOTRMod.isOpaque(world, i - 1, j3, k) && (j3 >= j || !LOTRMod.isOpaque(world, i, j3, k)); --j3) {
            this.func_150516_a(world, i, j3, k, Blocks.ladder, 5);
        }
        return true;
    }
    
    private boolean generateFacingNorth(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    if (!world.getBlock(i2, j2, k - 1).isWood((IBlockAccess)world, i2, j2, k - 1)) {
                        return false;
                    }
                    for (int k2 = k; k2 <= k + 3; ++k2) {
                        if (!world.isAirBlock(i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        else {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    for (int k2 = k; k2 <= k + 3; ++k2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int i2 = i - 1; i2 <= i + 1; ++i2) {
            for (int k3 = k; k3 <= k + 2; ++k3) {
                this.func_150516_a(world, i2, j, k3, LOTRMod.planks2, 13);
            }
        }
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            this.func_150516_a(world, i2, j, k + 3, LOTRMod.stairsGreenOak, 7);
            this.func_150516_a(world, i2, j + 1, k + 3, LOTRMod.fence2, 13);
        }
        for (int k4 = k; k4 <= k + 2; ++k4) {
            this.func_150516_a(world, i - 2, j, k4, LOTRMod.stairsGreenOak, 4);
            this.func_150516_a(world, i + 2, j, k4, LOTRMod.stairsGreenOak, 5);
            this.func_150516_a(world, i - 2, j + 1, k4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i + 2, j + 1, k4, LOTRMod.fence2, 13);
        }
        this.func_150516_a(world, i - 2, j + 2, k, LOTRMod.fence2, 13);
        this.func_150516_a(world, i - 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i + 2, j + 2, k, LOTRMod.fence2, 13);
        this.func_150516_a(world, i + 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i - 2, j + 2, k + 3, LOTRMod.fence2, 13);
        this.func_150516_a(world, i - 2, j + 3, k + 3, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i + 2, j + 2, k + 3, LOTRMod.fence2, 13);
        this.func_150516_a(world, i + 2, j + 3, k + 3, LOTRMod.woodElvenTorch, 5);
        for (int j3 = j; j3 >= 0 && LOTRMod.isOpaque(world, i, j3, k - 1) && (j3 >= j || !LOTRMod.isOpaque(world, i, j3, k)); --j3) {
            this.func_150516_a(world, i, j3, k, Blocks.ladder, 3);
        }
        return true;
    }
    
    private boolean generateFacingEast(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    if (!world.getBlock(i + 1, j2, k2).isWood((IBlockAccess)world, i + 1, j2, k2)) {
                        return false;
                    }
                    for (int i2 = i; i2 >= i - 3; --i2) {
                        if (!world.isAirBlock(i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        else {
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                for (int j2 = j; j2 <= j + 4; ++j2) {
                    for (int i2 = i; i2 >= i - 3; --i2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
            }
        }
        for (int k2 = k - 1; k2 <= k + 1; ++k2) {
            for (int i3 = i; i3 >= i - 2; --i3) {
                this.func_150516_a(world, i3, j, k2, LOTRMod.planks2, 13);
            }
        }
        for (int k2 = k - 2; k2 <= k + 2; ++k2) {
            this.func_150516_a(world, i - 3, j, k2, LOTRMod.stairsGreenOak, 4);
            this.func_150516_a(world, i - 3, j + 1, k2, LOTRMod.fence2, 13);
        }
        for (int i4 = i; i4 >= i - 2; --i4) {
            this.func_150516_a(world, i4, j, k - 2, LOTRMod.stairsGreenOak, 6);
            this.func_150516_a(world, i4, j, k + 2, LOTRMod.stairsGreenOak, 7);
            this.func_150516_a(world, i4, j + 1, k - 2, LOTRMod.fence2, 13);
            this.func_150516_a(world, i4, j + 1, k + 2, LOTRMod.fence2, 13);
        }
        this.func_150516_a(world, i, j + 2, k - 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i - 3, j + 2, k - 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i - 3, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
        this.func_150516_a(world, i - 3, j + 2, k + 2, LOTRMod.fence2, 13);
        this.func_150516_a(world, i - 3, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
        for (int j3 = j; j3 >= 0 && LOTRMod.isOpaque(world, i + 1, j3, k) && (j3 >= j || !LOTRMod.isOpaque(world, i, j3, k)); --j3) {
            this.func_150516_a(world, i, j3, k, Blocks.ladder, 4);
        }
        return true;
    }
}
