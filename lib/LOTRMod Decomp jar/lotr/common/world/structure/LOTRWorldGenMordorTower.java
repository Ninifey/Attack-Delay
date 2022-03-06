// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMordorOrcMercenaryCaptain;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMordorTower extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenMordorTower(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenMordor)) {
            return false;
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
        final int equipmentSection = 1 + random.nextInt(sections);
        if (super.restrictions) {
            for (int i2 = i - 7; i2 <= i + 7; ++i2) {
                for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                    final int j2 = world.getHeightValue(i2, k2) - 1;
                    final Block block = world.getBlock(i2, j2, k2);
                    final int meta = world.getBlockMetadata(i2, j2, k2);
                    if (block != LOTRMod.mordorDirt && block != LOTRMod.mordorGravel && (block != LOTRMod.rock || meta != 0) && block != Blocks.grass && block != Blocks.dirt) {
                        return false;
                    }
                }
            }
        }
        for (int k3 = k - 2; k3 <= k + 2; ++k3) {
            for (int j3 = j; !LOTRMod.isOpaque(world, i - 6, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i - 6, j3, k3, LOTRMod.brick, 0);
            }
            for (int j3 = j; !LOTRMod.isOpaque(world, i + 6, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i + 6, j3, k3, LOTRMod.brick, 0);
            }
        }
        for (int k3 = k - 4; k3 <= k + 4; ++k3) {
            for (int j3 = j; !LOTRMod.isOpaque(world, i - 5, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i - 5, j3, k3, LOTRMod.brick, 0);
            }
            for (int j3 = j; !LOTRMod.isOpaque(world, i + 5, j3, k3) && j3 >= 0; --j3) {
                this.func_150516_a(world, i + 5, j3, k3, LOTRMod.brick, 0);
            }
        }
        for (int k3 = k - 5; k3 <= k + 5; ++k3) {
            for (int i3 = i - 4; i3 <= i - 3; ++i3) {
                for (int j2 = j; !LOTRMod.isOpaque(world, i3, j2, k3) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i3, j2, k3, LOTRMod.brick, 0);
                }
            }
            for (int i3 = i + 3; i3 <= i + 4; ++i3) {
                for (int j2 = j; !LOTRMod.isOpaque(world, i3, j2, k3) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i3, j2, k3, LOTRMod.brick, 0);
                }
            }
        }
        for (int k3 = k - 6; k3 <= k + 6; ++k3) {
            for (int i3 = i - 2; i3 <= i + 2; ++i3) {
                for (int j2 = j; !LOTRMod.isOpaque(world, i3, j2, k3) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i3, j2, k3, LOTRMod.brick, 0);
                }
            }
        }
        for (int l = 0; l <= sections; ++l) {
            this.generateTowerSection(world, random, i, j, k, l, false, l == equipmentSection);
        }
        this.generateTowerSection(world, random, i, j, k, sections + 1, true, false);
        final LOTREntityMordorOrcMercenaryCaptain trader = new LOTREntityMordorOrcMercenaryCaptain(world);
        trader.setLocationAndAngles(i + 0.5, (double)(j + (sections + 1) * 8 + 1), k - 4 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
        trader.onSpawnWithEgg(null);
        trader.setHomeArea(i, j + (sections + 1) * 8, k, 24);
        world.spawnEntityInWorld((Entity)trader);
        switch (rotation) {
            case 0: {
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    this.func_150516_a(world, i3, j, k - 6, LOTRMod.slabDouble, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i3, j2, k - 6, LOTRMod.gateOrc, 3);
                    }
                }
                this.placeWallBanner(world, i, j + 7, k - 6, 2, LOTRItemBanner.BannerType.MORDOR);
                break;
            }
            case 1: {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    this.func_150516_a(world, i + 6, j, k2, LOTRMod.slabDouble, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i + 6, j2, k2, LOTRMod.gateOrc, 4);
                    }
                }
                this.placeWallBanner(world, i + 6, j + 7, k, 3, LOTRItemBanner.BannerType.MORDOR);
                break;
            }
            case 2: {
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    this.func_150516_a(world, i3, j, k + 6, LOTRMod.slabDouble, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i3, j2, k + 6, LOTRMod.gateOrc, 2);
                    }
                }
                this.placeWallBanner(world, i, j + 7, k + 6, 0, LOTRItemBanner.BannerType.MORDOR);
                break;
            }
            case 3: {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    this.func_150516_a(world, i - 6, j, k2, LOTRMod.slabDouble, 0);
                    for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                        this.func_150516_a(world, i - 6, j2, k2, LOTRMod.gateOrc, 5);
                    }
                }
                this.placeWallBanner(world, i - 6, j + 7, k, 1, LOTRItemBanner.BannerType.MORDOR);
                break;
            }
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityMordorOrc.class);
        respawner.setCheckRanges(12, -8, 50, 20);
        respawner.setSpawnRanges(5, 1, 40, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
        return true;
    }
    
    private void generateTowerSection(final World world, final Random random, final int i, int j, final int k, final int section, final boolean isTop, final boolean isEquipmentSection) {
        j += section * 8;
        for (int j2 = (section == 0) ? j : (j + 1); j2 <= (isTop ? (j + 10) : (j + 8)); ++j2) {
            Block fillBlock = Blocks.air;
            int fillMeta = 0;
            if (j2 == j) {
                fillBlock = LOTRMod.slabDouble;
                fillMeta = 0;
            }
            else if (j2 == j + 8 && !isTop) {
                fillBlock = LOTRMod.slabSingle;
                fillMeta = 8;
            }
            else {
                fillBlock = Blocks.air;
                fillMeta = 0;
            }
            for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                this.func_150516_a(world, i - 5, j2, k2, fillBlock, fillMeta);
                this.func_150516_a(world, i + 5, j2, k2, fillBlock, fillMeta);
            }
            for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                for (int i2 = i - 4; i2 <= i - 3; ++i2) {
                    this.func_150516_a(world, i2, j2, k2, fillBlock, fillMeta);
                }
                for (int i2 = i + 3; i2 <= i + 4; ++i2) {
                    this.func_150516_a(world, i2, j2, k2, fillBlock, fillMeta);
                }
            }
            for (int k2 = k - 5; k2 <= k + 5; ++k2) {
                for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                    this.func_150516_a(world, i2, j2, k2, fillBlock, fillMeta);
                }
            }
        }
        for (int j2 = j + 1; j2 <= (isTop ? (j + 1) : (j + 8)); ++j2) {
            for (int k3 = k - 2; k3 <= k + 2; ++k3) {
                this.func_150516_a(world, i - 6, j2, k3, LOTRMod.brick, 0);
                this.func_150516_a(world, i + 6, j2, k3, LOTRMod.brick, 0);
            }
            for (int i3 = i - 2; i3 <= i + 2; ++i3) {
                this.func_150516_a(world, i3, j2, k - 6, LOTRMod.brick, 0);
                this.func_150516_a(world, i3, j2, k + 6, LOTRMod.brick, 0);
            }
            this.func_150516_a(world, i - 5, j2, k - 4, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 5, j2, k - 3, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 5, j2, k + 3, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 5, j2, k + 4, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 4, j2, k - 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 4, j2, k + 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 3, j2, k - 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 3, j2, k + 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 3, j2, k - 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 3, j2, k + 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 4, j2, k - 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 4, j2, k + 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 5, j2, k - 4, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 5, j2, k - 3, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 5, j2, k + 3, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 5, j2, k + 4, LOTRMod.brick, 0);
        }
        this.placeOrcTorch(world, i - 5, j + 1, k - 2);
        this.placeOrcTorch(world, i - 5, j + 1, k + 2);
        this.placeOrcTorch(world, i + 5, j + 1, k - 2);
        this.placeOrcTorch(world, i + 5, j + 1, k + 2);
        this.placeOrcTorch(world, i - 2, j + 1, k - 5);
        this.placeOrcTorch(world, i + 2, j + 1, k - 5);
        this.placeOrcTorch(world, i - 2, j + 1, k + 5);
        this.placeOrcTorch(world, i + 2, j + 1, k + 5);
        if (!isTop) {
            for (int j2 = j + 2; j2 <= j + 4; ++j2) {
                for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                    this.func_150516_a(world, i - 6, j2, k3, LOTRMod.orcSteelBars, 0);
                    this.func_150516_a(world, i + 6, j2, k3, LOTRMod.orcSteelBars, 0);
                }
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    this.func_150516_a(world, i3, j2, k - 6, LOTRMod.orcSteelBars, 0);
                    this.func_150516_a(world, i3, j2, k + 6, LOTRMod.orcSteelBars, 0);
                }
            }
            for (int i4 = i - 2; i4 <= i + 2; ++i4) {
                for (int k3 = k - 2; k3 <= k + 2; ++k3) {
                    this.func_150516_a(world, i4, j + 8, k3, Blocks.air, 0);
                }
            }
            this.func_150516_a(world, i - 2, j + 1, k + 1, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i - 2, j + 1, k + 2, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i - 1, j + 2, k + 2, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i + 1, j + 3, k + 2, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i + 2, j + 3, k + 2, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i + 2, j + 4, k + 1, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i + 2, j + 4, k, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i + 2, j + 5, k - 1, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i + 2, j + 5, k - 2, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i + 1, j + 6, k - 2, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i, j + 6, k - 2, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i - 1, j + 7, k - 2, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i - 2, j + 7, k - 2, LOTRMod.slabSingle, 8);
            this.func_150516_a(world, i - 2, j + 8, k - 1, LOTRMod.slabSingle, 0);
            this.func_150516_a(world, i - 2, j + 8, k, LOTRMod.slabSingle, 8);
        }
        for (int i4 = i - 1; i4 <= i + 1; ++i4) {
            for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                for (int j3 = j + 1; j3 <= (isTop ? (j + 3) : (j + 8)); ++j3) {
                    this.func_150516_a(world, i4, j3, k3, LOTRMod.brick, 0);
                }
            }
        }
        if (isEquipmentSection) {
            final int l = random.nextInt(4);
            switch (l) {
                case 0: {
                    for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                        this.func_150516_a(world, i3, j + 1, k - 5, LOTRMod.orcBomb, 0);
                        this.func_150516_a(world, i3, j + 1, k + 5, LOTRMod.slabSingle, 9);
                        this.placeBarrel(world, random, i3, j + 2, k + 5, 2, LOTRFoods.ORC_DRINK);
                    }
                    break;
                }
                case 1: {
                    for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                        this.func_150516_a(world, i + 5, j + 1, k3, LOTRMod.orcBomb, 0);
                        this.func_150516_a(world, i - 5, j + 1, k3, LOTRMod.slabSingle, 9);
                        this.placeBarrel(world, random, i - 5, j + 2, k3, 5, LOTRFoods.ORC_DRINK);
                    }
                    break;
                }
                case 2: {
                    for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                        this.func_150516_a(world, i3, j + 1, k + 5, LOTRMod.orcBomb, 0);
                        this.func_150516_a(world, i3, j + 1, k - 5, LOTRMod.slabSingle, 9);
                        this.placeBarrel(world, random, i3, j + 2, k - 5, 3, LOTRFoods.ORC_DRINK);
                    }
                    break;
                }
                case 3: {
                    for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                        this.func_150516_a(world, i - 5, j + 1, k3, LOTRMod.orcBomb, 0);
                        this.func_150516_a(world, i + 5, j + 1, k3, LOTRMod.slabSingle, 9);
                        this.placeBarrel(world, random, i + 5, j + 2, k3, 4, LOTRFoods.ORC_DRINK);
                    }
                    break;
                }
            }
        }
        if (isTop) {
            for (int j2 = j + 1; j2 <= j + 8; ++j2) {
                for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                    this.func_150516_a(world, i - 7, j2, k3, LOTRMod.brick, 0);
                    this.func_150516_a(world, i + 7, j2, k3, LOTRMod.brick, 0);
                }
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    this.func_150516_a(world, i3, j2, k - 7, LOTRMod.brick, 0);
                    this.func_150516_a(world, i3, j2, k + 7, LOTRMod.brick, 0);
                }
            }
            for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                this.func_150516_a(world, i - 7, j, k4, LOTRMod.stairsMordorBrick, 4);
                this.func_150516_a(world, i - 6, j + 2, k4, LOTRMod.stairsMordorBrick, 1);
                this.func_150516_a(world, i - 7, j + 9, k4, LOTRMod.stairsMordorBrick, 0);
                this.func_150516_a(world, i - 6, j + 9, k4, LOTRMod.stairsMordorBrick, 5);
                this.func_150516_a(world, i - 6, j + 10, k4, LOTRMod.stairsMordorBrick, 0);
                this.func_150516_a(world, i + 7, j, k4, LOTRMod.stairsMordorBrick, 5);
                this.func_150516_a(world, i + 6, j + 2, k4, LOTRMod.stairsMordorBrick, 0);
                this.func_150516_a(world, i + 7, j + 9, k4, LOTRMod.stairsMordorBrick, 1);
                this.func_150516_a(world, i + 6, j + 9, k4, LOTRMod.stairsMordorBrick, 4);
                this.func_150516_a(world, i + 6, j + 10, k4, LOTRMod.stairsMordorBrick, 1);
            }
            for (int i4 = i - 1; i4 <= i + 1; ++i4) {
                this.func_150516_a(world, i4, j, k - 7, LOTRMod.stairsMordorBrick, 6);
                this.func_150516_a(world, i4, j + 2, k - 6, LOTRMod.stairsMordorBrick, 3);
                this.func_150516_a(world, i4, j + 9, k - 7, LOTRMod.stairsMordorBrick, 2);
                this.func_150516_a(world, i4, j + 9, k - 6, LOTRMod.stairsMordorBrick, 7);
                this.func_150516_a(world, i4, j + 10, k - 6, LOTRMod.stairsMordorBrick, 2);
                this.func_150516_a(world, i4, j, k + 7, LOTRMod.stairsMordorBrick, 7);
                this.func_150516_a(world, i4, j + 2, k + 6, LOTRMod.stairsMordorBrick, 2);
                this.func_150516_a(world, i4, j + 9, k + 7, LOTRMod.stairsMordorBrick, 3);
                this.func_150516_a(world, i4, j + 9, k + 6, LOTRMod.stairsMordorBrick, 6);
                this.func_150516_a(world, i4, j + 10, k + 6, LOTRMod.stairsMordorBrick, 3);
            }
            for (int j2 = j; j2 <= j + 4; ++j2) {
                this.func_150516_a(world, i - 5, j2, k - 5, LOTRMod.brick, 0);
                this.func_150516_a(world, i - 5, j2, k + 5, LOTRMod.brick, 0);
                this.func_150516_a(world, i + 5, j2, k - 5, LOTRMod.brick, 0);
                this.func_150516_a(world, i + 5, j2, k + 5, LOTRMod.brick, 0);
            }
            this.placeBanner(world, i - 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.MORDOR);
            this.placeBanner(world, i - 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.MORDOR);
            this.placeBanner(world, i + 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.MORDOR);
            this.placeBanner(world, i + 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.MORDOR);
            this.func_150516_a(world, i - 5, j + 2, k - 4, LOTRMod.stairsMordorBrick, 3);
            this.func_150516_a(world, i - 4, j + 2, k - 5, LOTRMod.stairsMordorBrick, 1);
            this.func_150516_a(world, i - 5, j + 2, k + 4, LOTRMod.stairsMordorBrick, 2);
            this.func_150516_a(world, i - 4, j + 2, k + 5, LOTRMod.stairsMordorBrick, 1);
            this.func_150516_a(world, i + 5, j + 2, k - 4, LOTRMod.stairsMordorBrick, 3);
            this.func_150516_a(world, i + 4, j + 2, k - 5, LOTRMod.stairsMordorBrick, 0);
            this.func_150516_a(world, i + 5, j + 2, k + 4, LOTRMod.stairsMordorBrick, 2);
            this.func_150516_a(world, i + 4, j + 2, k + 5, LOTRMod.stairsMordorBrick, 0);
        }
    }
}
