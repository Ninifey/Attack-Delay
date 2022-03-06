// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityAngmarOrcMercenaryCaptain;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.LOTRBiomeGenAngmar;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarTower extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenAngmarTower(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions) {
            if (!(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenAngmar)) {
                return false;
            }
            final Block l = world.getBlock(i, j - 1, k);
            if (l != Blocks.grass && l != Blocks.dirt && l != Blocks.stone) {
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
                k += 7;
                break;
            }
            case 1: {
                i -= 7;
                break;
            }
            case 2: {
                k -= 7;
                break;
            }
            case 3: {
                i += 7;
                break;
            }
        }
        final int sections = 2 + random.nextInt(3);
        if (super.restrictions) {
            for (int i2 = i - 7; i2 <= i + 7; ++i2) {
                for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                    final int j2 = world.getHeightValue(i2, k2) - 1;
                    final Block block = world.getBlock(i2, j2, k2);
                    if (block != Blocks.grass && block != Blocks.stone && block != Blocks.dirt && !block.isWood((IBlockAccess)world, i2, j2, k2) && !block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int k3 = k - 2; k3 <= k + 2; ++k3) {
            for (int j3 = j; !LOTRMod.isOpaque(world, i - 6, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i - 6, j3, k3, LOTRMod.brick2, 0);
                this.setGrassToDirt(world, i - 6, j3 - 1, k3);
            }
            for (int j3 = j; !LOTRMod.isOpaque(world, i + 6, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i + 6, j3, k3, LOTRMod.brick2, 0);
                this.setGrassToDirt(world, i + 6, j3 - 1, k3);
            }
        }
        for (int k3 = k - 4; k3 <= k + 4; ++k3) {
            for (int j3 = j; !LOTRMod.isOpaque(world, i - 5, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i - 5, j3, k3, LOTRMod.brick2, 0);
                this.setGrassToDirt(world, i - 5, j3 - 1, k3);
            }
            for (int j3 = j; !LOTRMod.isOpaque(world, i + 5, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i + 5, j3, k3, LOTRMod.brick2, 0);
                this.setGrassToDirt(world, i + 5, j3 - 1, k3);
            }
        }
        for (int k3 = k - 5; k3 <= k + 5; ++k3) {
            for (int i3 = i - 4; i3 <= i - 3; ++i3) {
                for (int j2 = j; !LOTRMod.isOpaque(world, i3, j2, k3) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i3, j2, k3, LOTRMod.brick2, 0);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
            }
            for (int i3 = i + 3; i3 <= i + 4; ++i3) {
                for (int j2 = j; !LOTRMod.isOpaque(world, i3, j2, k3) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i3, j2, k3, LOTRMod.brick2, 0);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
            }
        }
        for (int k3 = k - 6; k3 <= k + 6; ++k3) {
            for (int i3 = i - 2; i3 <= i + 2; ++i3) {
                for (int j2 = j; !LOTRMod.isOpaque(world, i3, j2, k3) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i3, j2, k3, LOTRMod.brick2, 0);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
            }
        }
        for (int m = 0; m <= sections; ++m) {
            this.generateTowerSection(world, random, i, j, k, m, false);
        }
        this.generateTowerSection(world, random, i, j, k, sections + 1, true);
        final LOTREntityAngmarOrcMercenaryCaptain trader = new LOTREntityAngmarOrcMercenaryCaptain(world);
        trader.setLocationAndAngles(i - 2 + 0.5, (double)(j + (sections + 1) * 8 + 1), k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
        trader.onSpawnWithEgg(null);
        trader.setHomeArea(i, j + (sections + 1) * 8, k, 24);
        world.spawnEntityInWorld((Entity)trader);
        switch (rotation) {
            case 0: {
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    this.func_150516_a(world, i3, j, k - 6, Blocks.stonebrick, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i3, j2, k - 6, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i, j + 7, k - 6, LOTRMod.brick2, 0);
                this.placeWallBanner(world, i, j + 7, k - 6, 2, LOTRItemBanner.BannerType.ANGMAR);
                break;
            }
            case 1: {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    this.func_150516_a(world, i + 6, j, k2, Blocks.stonebrick, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i + 6, j2, k2, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i + 6, j + 7, k, LOTRMod.brick2, 0);
                this.placeWallBanner(world, i + 6, j + 7, k, 3, LOTRItemBanner.BannerType.ANGMAR);
                break;
            }
            case 2: {
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    this.func_150516_a(world, i3, j, k + 6, Blocks.stonebrick, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i3, j2, k + 6, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i, j + 7, k + 6, LOTRMod.brick2, 0);
                this.placeWallBanner(world, i, j + 7, k + 6, 0, LOTRItemBanner.BannerType.ANGMAR);
                break;
            }
            case 3: {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    this.func_150516_a(world, i - 6, j, k2, Blocks.stonebrick, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i - 6, j2, k2, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i - 6, j + 7, k, LOTRMod.brick2, 0);
                this.placeWallBanner(world, i - 6, j + 7, k, 1, LOTRItemBanner.BannerType.ANGMAR);
                break;
            }
        }
        final int radius = 6;
        for (int l2 = 0; l2 < 16; ++l2) {
            final int i4 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            final int k4 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            final int j4 = world.getHeightValue(i4, k4);
            final Block id = world.getBlock(i4, j4 - 1, k4);
            if (id == Blocks.grass || id == Blocks.dirt || id == Blocks.stone) {
                final int randomFeature = random.nextInt(4);
                boolean flag = true;
                if (randomFeature == 0) {
                    if (!LOTRMod.isOpaque(world, i4, j4, k4)) {
                        if (random.nextInt(3) == 0) {
                            this.func_150516_a(world, i4, j4, k4, LOTRMod.slabSingle3, 4);
                        }
                        else {
                            this.func_150516_a(world, i4, j4, k4, LOTRMod.slabSingle3, 3);
                        }
                    }
                }
                else {
                    for (int j5 = j4; j5 < j4 + randomFeature && flag; flag = !LOTRMod.isOpaque(world, i4, j5, k4), ++j5) {}
                    if (flag) {
                        for (int j5 = j4; j5 < j4 + randomFeature; ++j5) {
                            if (random.nextBoolean()) {
                                this.func_150516_a(world, i4, j5, k4, LOTRMod.brick2, 0);
                            }
                            else {
                                this.func_150516_a(world, i4, j5, k4, LOTRMod.brick2, 1);
                            }
                        }
                    }
                }
                if (world.getBlock(i4, j4 - 1, k4) == Blocks.dirt) {
                    this.func_150516_a(world, i4, j4 - 1, k4, Blocks.dirt, 0);
                }
            }
        }
        return true;
    }
    
    private void generateTowerSection(final World world, final Random random, final int i, int j, final int k, final int section, final boolean isTop) {
        j += section * 8;
        for (int j2 = (section == 0) ? j : (j + 1); j2 <= (isTop ? (j + 10) : (j + 8)); ++j2) {
            Block fillBlock = Blocks.air;
            int fillMeta = 0;
            if (j2 == j) {
                fillBlock = Blocks.stonebrick;
                fillMeta = 0;
            }
            else {
                fillBlock = Blocks.air;
                fillMeta = 0;
            }
            final boolean hasCeiling = j2 == j + 8 && !isTop;
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                this.func_150516_a(world, i - 5, j2, k2, fillBlock, fillMeta);
                this.func_150516_a(world, i + 5, j2, k2, fillBlock, fillMeta);
                if (hasCeiling && random.nextInt(20) != 0) {
                    this.func_150516_a(world, i - 5, j2, k2, LOTRMod.slabSingle3, 11);
                }
                if (hasCeiling && random.nextInt(20) != 0) {
                    this.func_150516_a(world, i + 5, j2, k2, LOTRMod.slabSingle3, 11);
                }
            }
            for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                for (int i2 = i - 4; i2 <= i - 3; ++i2) {
                    this.func_150516_a(world, i2, j2, k2, fillBlock, fillMeta);
                    if (hasCeiling && random.nextInt(20) != 0) {
                        this.func_150516_a(world, i2, j2, k2, LOTRMod.slabSingle3, 11);
                    }
                }
                for (int i2 = i + 3; i2 <= i + 4; ++i2) {
                    this.func_150516_a(world, i2, j2, k2, fillBlock, fillMeta);
                    if (hasCeiling && random.nextInt(20) != 0) {
                        this.func_150516_a(world, i2, j2, k2, LOTRMod.slabSingle3, 11);
                    }
                }
            }
            for (int k2 = k - 5; k2 <= k + 5; ++k2) {
                for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                    this.func_150516_a(world, i2, j2, k2, fillBlock, fillMeta);
                    if (hasCeiling && random.nextInt(20) != 0) {
                        this.func_150516_a(world, i2, j2, k2, LOTRMod.slabSingle3, 11);
                    }
                }
            }
        }
        for (int j2 = j + 1; j2 <= (isTop ? (j + 1) : (j + 8)); ++j2) {
            for (int k3 = k - 2; k3 <= k + 2; ++k3) {
                this.placeRandomBrick(world, random, i - 6, j2, k3);
                this.placeRandomBrick(world, random, i + 6, j2, k3);
            }
            for (int i3 = i - 2; i3 <= i + 2; ++i3) {
                this.placeRandomBrick(world, random, i3, j2, k - 6);
                this.placeRandomBrick(world, random, i3, j2, k + 6);
            }
            this.placeRandomBrick(world, random, i - 5, j2, k - 4);
            this.placeRandomBrick(world, random, i - 5, j2, k - 3);
            this.placeRandomBrick(world, random, i - 5, j2, k + 3);
            this.placeRandomBrick(world, random, i - 5, j2, k + 4);
            this.placeRandomBrick(world, random, i - 4, j2, k - 5);
            this.placeRandomBrick(world, random, i - 4, j2, k + 5);
            this.placeRandomBrick(world, random, i - 3, j2, k - 5);
            this.placeRandomBrick(world, random, i - 3, j2, k + 5);
            this.placeRandomBrick(world, random, i + 3, j2, k - 5);
            this.placeRandomBrick(world, random, i + 3, j2, k + 5);
            this.placeRandomBrick(world, random, i + 4, j2, k - 5);
            this.placeRandomBrick(world, random, i + 4, j2, k + 5);
            this.placeRandomBrick(world, random, i + 5, j2, k - 4);
            this.placeRandomBrick(world, random, i + 5, j2, k - 3);
            this.placeRandomBrick(world, random, i + 5, j2, k + 3);
            this.placeRandomBrick(world, random, i + 5, j2, k + 4);
        }
        if (!isTop) {
            for (int j2 = j + 2; j2 <= j + 4; ++j2) {
                for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                    if (random.nextInt(3) != 0) {
                        this.func_150516_a(world, i - 6, j2, k3, LOTRMod.orcSteelBars, 0);
                    }
                    else {
                        this.func_150516_a(world, i - 6, j2, k3, Blocks.air, 0);
                    }
                    if (random.nextInt(3) != 0) {
                        this.func_150516_a(world, i + 6, j2, k3, LOTRMod.orcSteelBars, 0);
                    }
                    else {
                        this.func_150516_a(world, i + 6, j2, k3, Blocks.air, 0);
                    }
                }
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    if (random.nextInt(3) != 0) {
                        this.func_150516_a(world, i3, j2, k - 6, LOTRMod.orcSteelBars, 0);
                    }
                    else {
                        this.func_150516_a(world, i3, j2, k - 6, Blocks.air, 0);
                    }
                    if (random.nextInt(3) != 0) {
                        this.func_150516_a(world, i3, j2, k + 6, LOTRMod.orcSteelBars, 0);
                    }
                    else {
                        this.func_150516_a(world, i3, j2, k + 6, Blocks.air, 0);
                    }
                }
            }
            for (int i4 = i - 2; i4 <= i + 2; ++i4) {
                for (int k3 = k - 2; k3 <= k + 2; ++k3) {
                    this.func_150516_a(world, i4, j + 8, k3, Blocks.air, 0);
                }
            }
            this.func_150516_a(world, i - 2, j + 1, k + 1, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i - 2, j + 1, k + 2, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i - 1, j + 2, k + 2, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i + 1, j + 3, k + 2, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i + 2, j + 3, k + 2, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i + 2, j + 4, k + 1, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i + 2, j + 4, k, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i + 2, j + 5, k - 1, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i + 2, j + 5, k - 2, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i + 1, j + 6, k - 2, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i, j + 6, k - 2, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i - 1, j + 7, k - 2, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i - 2, j + 7, k - 2, LOTRMod.slabSingle3, 11);
            this.func_150516_a(world, i - 2, j + 8, k - 1, LOTRMod.slabSingle3, 3);
            this.func_150516_a(world, i - 2, j + 8, k, LOTRMod.slabSingle3, 11);
        }
        for (int i4 = i - 1; i4 <= i + 1; ++i4) {
            for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                for (int j3 = j + 1; j3 <= (isTop ? (j + 3) : (j + 8)); ++j3) {
                    this.placeRandomBrick(world, random, i4, j3, k3);
                }
            }
        }
        if (isTop) {
            for (int top = 4 + random.nextInt(5), j4 = j + 1; j4 <= j + top; ++j4) {
                for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                    this.placeRandomBrick(world, random, i - 7, j4, k4);
                    this.placeRandomBrick(world, random, i + 7, j4, k4);
                }
                for (int i5 = i - 1; i5 <= i + 1; ++i5) {
                    this.placeRandomBrick(world, random, i5, j4, k - 7);
                    this.placeRandomBrick(world, random, i5, j4, k + 7);
                }
            }
            for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                this.placeRandomStairs(world, random, i - 7, j, k3, 4);
                this.placeRandomStairs(world, random, i - 6, j + 2, k3, 1);
                this.placeRandomStairs(world, random, i + 7, j, k3, 5);
                this.placeRandomStairs(world, random, i + 6, j + 2, k3, 0);
            }
            for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                this.placeRandomStairs(world, random, i3, j, k - 7, 6);
                this.placeRandomStairs(world, random, i3, j + 2, k - 6, 3);
                this.placeRandomStairs(world, random, i3, j, k + 7, 7);
                this.placeRandomStairs(world, random, i3, j + 2, k + 6, 2);
            }
            for (int j4 = j; j4 <= j + 4; ++j4) {
                this.func_150516_a(world, i - 5, j4, k - 5, LOTRMod.brick2, 0);
                this.func_150516_a(world, i - 5, j4, k + 5, LOTRMod.brick2, 0);
                this.func_150516_a(world, i + 5, j4, k - 5, LOTRMod.brick2, 0);
                this.func_150516_a(world, i + 5, j4, k + 5, LOTRMod.brick2, 0);
            }
            this.placeBanner(world, i - 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.ANGMAR);
            this.placeBanner(world, i - 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.ANGMAR);
            this.placeBanner(world, i + 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.ANGMAR);
            this.placeBanner(world, i + 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.ANGMAR);
            this.placeRandomStairs(world, random, i - 5, j + 2, k - 4, 3);
            this.placeRandomStairs(world, random, i - 4, j + 2, k - 5, 1);
            this.placeRandomStairs(world, random, i - 5, j + 2, k + 4, 2);
            this.placeRandomStairs(world, random, i - 4, j + 2, k + 5, 1);
            this.placeRandomStairs(world, random, i + 5, j + 2, k - 4, 3);
            this.placeRandomStairs(world, random, i + 4, j + 2, k - 5, 0);
            this.placeRandomStairs(world, random, i + 5, j + 2, k + 4, 2);
            this.placeRandomStairs(world, random, i + 4, j + 2, k + 5, 0);
        }
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(20) == 0) {
            return;
        }
        if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 1);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 0);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(10) == 0) {
            return;
        }
        if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrickCracked, meta);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrick, meta);
        }
    }
}
