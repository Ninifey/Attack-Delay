// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDunlendingCampfire extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenDunlendingCampfire(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 5;
                break;
            }
            case 1: {
                i -= 5;
                break;
            }
            case 2: {
                k -= 5;
                break;
            }
            case 3: {
                i += 5;
                break;
            }
        }
        if (super.restrictions) {
            for (int i2 = i - 5; i2 <= i + 5; ++i2) {
                for (int k2 = k - 5; k2 <= k + 5; ++k2) {
                    for (int j2 = j + 1; j2 <= j + 2; ++j2) {
                        if (LOTRMod.isOpaque(world, i2, j2, k2)) {
                            return false;
                        }
                    }
                    int j2 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
                    if (Math.abs(j2 - j) > 2) {
                        return false;
                    }
                    final Block l = world.getBlock(i2, j2, k2);
                    if (l != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i - 5; i2 <= i + 5; ++i2) {
            for (int k2 = k - 5; k2 <= k + 5; ++k2) {
                if (!super.restrictions) {
                    for (int j2 = j + 1; j2 <= j + 2; ++j2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
                for (int j2 = j; j2 >= j - 2; --j2) {
                    if (LOTRMod.isOpaque(world, i2, j2 + 1, k2)) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.dirt, 0);
                    }
                    else {
                        this.func_150516_a(world, i2, j2, k2, (Block)Blocks.grass, 0);
                    }
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int i2 = i - 1; i2 <= i + 1; ++i2) {
            for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                this.func_150516_a(world, i2, j, k2, Blocks.gravel, 0);
            }
        }
        this.func_150516_a(world, i, j, k, LOTRMod.hearth, 0);
        this.func_150516_a(world, i, j + 1, k, (Block)Blocks.fire, 0);
        this.placeSkullPillar(world, random, i - 2, j + 1, k - 2);
        this.placeSkullPillar(world, random, i + 2, j + 1, k - 2);
        this.placeSkullPillar(world, random, i - 2, j + 1, k + 2);
        this.placeSkullPillar(world, random, i + 2, j + 1, k + 2);
        if (random.nextBoolean()) {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                this.func_150516_a(world, i2, j + 1, k + 4, Blocks.log, 4);
                this.setGrassToDirt(world, i2, j, k - 4);
            }
        }
        if (random.nextBoolean()) {
            for (int k3 = k - 2; k3 <= k + 2; ++k3) {
                this.func_150516_a(world, i - 4, j + 1, k3, Blocks.log, 8);
                this.setGrassToDirt(world, i - 4, j, k3);
            }
        }
        if (random.nextBoolean()) {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                this.func_150516_a(world, i2, j + 1, k - 4, Blocks.log, 4);
                this.setGrassToDirt(world, i2, j, k - 4);
            }
        }
        if (random.nextBoolean()) {
            for (int k3 = k - 2; k3 <= k + 2; ++k3) {
                this.func_150516_a(world, i + 4, j + 1, k3, Blocks.log, 8);
                this.setGrassToDirt(world, i + 4, j, k3);
            }
        }
        if (random.nextBoolean()) {
            int chestX = i;
            int chestZ = k;
            int chestMeta = 0;
            final int m = random.nextInt(4);
            switch (m) {
                case 0: {
                    chestX = i - 3 + random.nextInt(6);
                    chestZ = k + 3;
                    chestMeta = 3;
                    break;
                }
                case 1: {
                    chestX = i - 3;
                    chestZ = k - 3 + random.nextInt(6);
                    chestMeta = 4;
                    break;
                }
                case 2: {
                    chestX = i - 3 + random.nextInt(6);
                    chestZ = k - 3;
                    chestMeta = 2;
                    break;
                }
                case 3: {
                    chestX = i + 3;
                    chestZ = k - 3 + random.nextInt(6);
                    chestMeta = 5;
                    break;
                }
            }
            this.func_150516_a(world, chestX, j + 1, chestZ, LOTRMod.chestBasket, chestMeta);
            LOTRChestContents.fillChest(world, random, chestX, j + 1, chestZ, LOTRChestContents.DUNLENDING_CAMPFIRE);
        }
        return true;
    }
    
    private void placeSkullPillar(final World world, final Random random, final int i, final int j, final int k) {
        this.func_150516_a(world, i, j, k, Blocks.cobblestone_wall, 0);
        this.placeSkull(world, random, i, j + 1, k);
    }
}
