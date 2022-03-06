// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.LOTRBiomeGenShire;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class LOTRWorldGenHobbitPicnicBench extends LOTRWorldGenStructureBase
{
    private Block baseBlock;
    private int baseMeta;
    private Block stairBlock;
    private Block halfBlock;
    private int halfMeta;
    private Block plateBlock;
    
    public LOTRWorldGenHobbitPicnicBench(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions && !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenShire)) {
            return false;
        }
        final int randomWood = random.nextInt(4);
        switch (randomWood) {
            case 0: {
                this.baseBlock = Blocks.planks;
                this.baseMeta = 0;
                this.stairBlock = Blocks.oak_stairs;
                this.halfBlock = (Block)Blocks.wooden_slab;
                this.halfMeta = 0;
                break;
            }
            case 1: {
                this.baseBlock = Blocks.planks;
                this.baseMeta = 1;
                this.stairBlock = Blocks.spruce_stairs;
                this.halfBlock = (Block)Blocks.wooden_slab;
                this.halfMeta = 1;
                break;
            }
            case 2: {
                this.baseBlock = Blocks.planks;
                this.baseMeta = 2;
                this.stairBlock = Blocks.birch_stairs;
                this.halfBlock = (Block)Blocks.wooden_slab;
                this.halfMeta = 2;
                break;
            }
            case 3: {
                this.baseBlock = LOTRMod.planks;
                this.baseMeta = 0;
                this.stairBlock = LOTRMod.stairsShirePine;
                this.halfBlock = LOTRMod.woodSlabSingle;
                this.halfMeta = 0;
                break;
            }
        }
        this.plateBlock = (random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock);
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
                return false;
            }
        }
    }
    
    private boolean generateFacingSouth(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int k2 = k; k2 <= k + 5; ++k2) {
                for (int i2 = i + 2; i2 >= i - 3; --i2) {
                    if (world.getBlock(i2, j - 1, k2) != Blocks.grass || !world.isAirBlock(i2, j, k2) || !world.isAirBlock(i2, j + 1, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int k2 = k; k2 <= k + 5; ++k2) {
            for (int i2 = i + 2; i2 >= i - 3; --i2) {
                this.func_150516_a(world, i2, j, k2, Blocks.air, 0);
                this.func_150516_a(world, i2, j + 1, k2, Blocks.air, 0);
            }
        }
        for (int k2 = k; k2 <= k + 5; ++k2) {
            for (int i2 = i; i2 >= i - 1; --i2) {
                if (k2 == k || k2 == k + 5) {
                    this.func_150516_a(world, i2, j, k2, this.baseBlock, this.baseMeta);
                }
                else {
                    this.func_150516_a(world, i2, j, k2, this.halfBlock, this.halfMeta | 0x8);
                }
                this.placePlate(world, random, i2, j + 1, k2, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.func_150516_a(world, i - 3, j, k2, this.stairBlock, 1);
            this.func_150516_a(world, i + 2, j, k2, this.stairBlock, 0);
        }
        for (int hobbits = 2 + random.nextInt(3), i2 = 0; i2 < hobbits; ++i2) {
            final LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            final int hobbitX = i + 1 - random.nextInt(2) * 3;
            final int hobbitY = j;
            final int hobbitZ = k + random.nextInt(6);
            hobbit.setLocationAndAngles(hobbitX + 0.5, (double)hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)hobbit);
        }
        return true;
    }
    
    private boolean generateFacingWest(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int i2 = i; i2 >= i - 5; --i2) {
                for (int k2 = k + 2; k2 >= k - 3; --k2) {
                    if (world.getBlock(i2, j - 1, k2) != Blocks.grass || !world.isAirBlock(i2, j, k2) || !world.isAirBlock(i2, j + 1, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i; i2 >= i - 5; --i2) {
            for (int k2 = k + 2; k2 >= k - 3; --k2) {
                this.func_150516_a(world, i2, j, k2, Blocks.air, 0);
                this.func_150516_a(world, i2, j + 1, k2, Blocks.air, 0);
            }
        }
        for (int i2 = i; i2 >= i - 5; --i2) {
            for (int k2 = k; k2 >= k - 1; --k2) {
                if (i2 == i || i2 == i - 5) {
                    this.func_150516_a(world, i2, j, k2, this.baseBlock, this.baseMeta);
                }
                else {
                    this.func_150516_a(world, i2, j, k2, this.halfBlock, this.halfMeta | 0x8);
                }
                this.placePlate(world, random, i2, j + 1, k2, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.func_150516_a(world, i2, j, k - 3, this.stairBlock, 3);
            this.func_150516_a(world, i2, j, k + 2, this.stairBlock, 2);
        }
        for (int hobbits = 2 + random.nextInt(3), i3 = 0; i3 < hobbits; ++i3) {
            final LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            final int hobbitX = i - random.nextInt(6);
            final int hobbitY = j;
            final int hobbitZ = k + 1 - random.nextInt(2) * 3;
            hobbit.setLocationAndAngles(hobbitX + 0.5, (double)hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)hobbit);
        }
        return true;
    }
    
    private boolean generateFacingNorth(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int k2 = k; k2 >= k - 5; --k2) {
                for (int i2 = i - 2; i2 <= i + 3; ++i2) {
                    if (world.getBlock(i2, j - 1, k2) != Blocks.grass || !world.isAirBlock(i2, j, k2) || !world.isAirBlock(i2, j + 1, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int k2 = k; k2 >= k - 5; --k2) {
            for (int i2 = i - 2; i2 <= i + 3; ++i2) {
                this.func_150516_a(world, i2, j, k2, Blocks.air, 0);
                this.func_150516_a(world, i2, j + 1, k2, Blocks.air, 0);
            }
        }
        for (int k2 = k; k2 >= k - 5; --k2) {
            for (int i2 = i; i2 <= i + 1; ++i2) {
                if (k2 == k || k2 == k - 5) {
                    this.func_150516_a(world, i2, j, k2, this.baseBlock, this.baseMeta);
                }
                else {
                    this.func_150516_a(world, i2, j, k2, this.halfBlock, this.halfMeta | 0x8);
                }
                this.placePlate(world, random, i2, j + 1, k2, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.func_150516_a(world, i - 2, j, k2, this.stairBlock, 1);
            this.func_150516_a(world, i + 3, j, k2, this.stairBlock, 0);
        }
        for (int hobbits = 2 + random.nextInt(3), i2 = 0; i2 < hobbits; ++i2) {
            final LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            final int hobbitX = i - 1 + random.nextInt(2) * 3;
            final int hobbitY = j;
            final int hobbitZ = k - random.nextInt(6);
            hobbit.setLocationAndAngles(hobbitX + 0.5, (double)hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)hobbit);
        }
        return true;
    }
    
    private boolean generateFacingEast(final World world, final Random random, final int i, final int j, final int k) {
        if (super.restrictions) {
            for (int i2 = i; i2 <= i + 5; ++i2) {
                for (int k2 = k - 2; k2 <= k + 3; ++k2) {
                    if (world.getBlock(i2, j - 1, k2) != Blocks.grass || !world.isAirBlock(i2, j, k2) || !world.isAirBlock(i2, j + 1, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i; i2 <= i + 5; ++i2) {
            for (int k2 = k - 2; k2 <= k + 3; ++k2) {
                this.func_150516_a(world, i2, j, k2, Blocks.air, 0);
                this.func_150516_a(world, i2, j + 1, k2, Blocks.air, 0);
            }
        }
        for (int i2 = i; i2 <= i + 5; ++i2) {
            for (int k2 = k; k2 <= k + 1; ++k2) {
                if (i2 == i || i2 == i + 5) {
                    this.func_150516_a(world, i2, j, k2, this.baseBlock, this.baseMeta);
                }
                else {
                    this.func_150516_a(world, i2, j, k2, this.halfBlock, this.halfMeta | 0x8);
                }
                this.placePlate(world, random, i2, j + 1, k2, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.func_150516_a(world, i2, j, k - 2, this.stairBlock, 3);
            this.func_150516_a(world, i2, j, k + 3, this.stairBlock, 2);
        }
        for (int hobbits = 2 + random.nextInt(3), i3 = 0; i3 < hobbits; ++i3) {
            final LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            final int hobbitX = i + random.nextInt(6);
            final int hobbitY = j;
            final int hobbitZ = k - 1 + random.nextInt(2) * 3;
            hobbit.setLocationAndAngles(hobbitX + 0.5, (double)hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)hobbit);
        }
        return true;
    }
}
