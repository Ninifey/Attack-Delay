// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.tileentity.TileEntity;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.tileentity.LOTRTileEntityAlloyForge;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDwarfWarrior;
import lotr.common.entity.npc.LOTREntityDwarfAxeThrower;
import net.minecraft.init.Blocks;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityDwarfCommander;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;

public class LOTRWorldGenDwarvenTower extends LOTRWorldGenStructureBase2
{
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block barsBlock;
    protected Block gateBlock;
    protected Block tableBlock;
    protected Block forgeBlock;
    protected Block glowBrickBlock;
    protected int glowBrickMeta;
    protected Block plateBlock;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRChestContents chestContents;
    protected boolean ruined;
    
    public LOTRWorldGenDwarvenTower(final boolean flag) {
        super(flag);
        this.ruined = false;
        this.brickBlock = LOTRMod.brick;
        this.brickMeta = 6;
        this.brickSlabBlock = LOTRMod.slabSingle;
        this.brickSlabMeta = 7;
        this.brickStairBlock = LOTRMod.stairsDwarvenBrick;
        this.brickWallBlock = LOTRMod.wall;
        this.brickWallMeta = 7;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 0;
        this.barsBlock = LOTRMod.dwarfBars;
        this.gateBlock = LOTRMod.gateDwarven;
        this.tableBlock = LOTRMod.dwarvenTable;
        this.forgeBlock = LOTRMod.dwarvenForge;
        this.glowBrickBlock = LOTRMod.brick3;
        this.glowBrickMeta = 12;
        this.bannerType = LOTRItemBanner.BannerType.DWARF;
        this.chestContents = LOTRChestContents.DWARVEN_TOWER;
    }
    
    protected LOTREntityNPC getCommanderNPC(final World world) {
        return new LOTREntityDwarfCommander(world);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        final int randomWood = random.nextInt(4);
        if (randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 1;
        }
        else if (randomWood == 1) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 13;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 5;
        }
        else if (randomWood == 2) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 4;
        }
        else if (randomWood == 3) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 3;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 3;
        }
        if (random.nextBoolean()) {
            this.plateBlock = LOTRMod.ceramicPlateBlock;
        }
        else {
            this.plateBlock = LOTRMod.woodPlateBlock;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        final int sections = 5 + random.nextInt(3);
        if (super.restrictions) {
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.stone && block != Blocks.snow) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.placeBrick(world, random, i2, j2, k2);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                boolean flag = true;
                if (this.ruined) {
                    flag = (random.nextInt(12) != 0);
                }
                if (flag) {
                    this.setBlockAndMetadata(world, i2, 0, k2, this.plankBlock, this.plankMeta);
                }
            }
        }
        for (int l = 0; l <= sections; ++l) {
            final int sectionBase = l * 5;
            for (int i3 = -4; i3 <= 4; ++i3) {
                for (int j3 = sectionBase + 1; j3 <= sectionBase + 5; ++j3) {
                    for (int k3 = -4; k3 <= 4; ++k3) {
                        this.setAir(world, i3, j3, k3);
                        this.setAir(world, i3, j3, k3);
                    }
                }
            }
            for (int j2 = sectionBase + 1; j2 <= sectionBase + 5; ++j2) {
                for (int i4 = -5; i4 <= 5; ++i4) {
                    for (final int k4 : new int[] { -5, 5 }) {
                        boolean flag2 = true;
                        if (this.ruined) {
                            flag2 = (random.nextInt(20) != 0);
                        }
                        if (flag2) {
                            this.placeBrick(world, random, i4, j2, k4);
                        }
                    }
                }
                for (int k5 = -4; k5 <= 4; ++k5) {
                    for (final int i5 : new int[] { -5, 5 }) {
                        boolean flag2 = true;
                        if (this.ruined) {
                            flag2 = (random.nextInt(20) != 0);
                        }
                        if (flag2) {
                            this.placeBrick(world, random, i5, j2, k5);
                        }
                    }
                }
            }
            this.placePillar(world, random, -4, sectionBase + 1, -4);
            this.placePillar(world, random, -4, sectionBase + 2, -4);
            this.setBlockAndMetadata(world, -4, sectionBase + 3, -4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, -4, sectionBase + 4, -4);
            this.placePillar(world, random, -4, sectionBase + 1, 4);
            this.placePillar(world, random, -4, sectionBase + 2, 4);
            this.setBlockAndMetadata(world, -4, sectionBase + 3, 4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, -4, sectionBase + 4, 4);
            this.placePillar(world, random, 4, sectionBase + 1, -4);
            this.placePillar(world, random, 4, sectionBase + 2, -4);
            this.setBlockAndMetadata(world, 4, sectionBase + 3, -4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, 4, sectionBase + 4, -4);
            this.placePillar(world, random, 4, sectionBase + 1, 4);
            this.placePillar(world, random, 4, sectionBase + 2, 4);
            this.setBlockAndMetadata(world, 4, sectionBase + 3, 4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, 4, sectionBase + 4, 4);
            for (int i3 = -4; i3 <= 4; ++i3) {
                for (int k5 = -4; k5 <= 4; ++k5) {
                    boolean flag3 = true;
                    if (this.ruined) {
                        flag3 = (random.nextInt(12) != 0);
                    }
                    if (flag3) {
                        this.setBlockAndMetadata(world, i3, sectionBase + 5, k5, this.plankBlock, this.plankMeta);
                    }
                }
            }
            for (int k6 = -2; k6 <= 2; ++k6) {
                for (int j3 = sectionBase + 1; j3 <= sectionBase + 4; ++j3) {
                    if (Math.abs(k6) < 2 && (j3 == sectionBase + 2 || j3 == sectionBase + 3)) {
                        this.setBlockAndMetadata(world, -5, j3, k6, this.barsBlock, 0);
                        this.setBlockAndMetadata(world, 5, j3, k6, this.barsBlock, 0);
                    }
                    else {
                        this.placePillar(world, random, -5, j3, k6);
                        this.placePillar(world, random, 5, j3, k6);
                    }
                }
            }
            final int randomFeature = random.nextInt(5);
            if (l % 2 == 0) {
                for (int k5 = -1; k5 <= 4; ++k5) {
                    for (int i6 = 1; i6 <= 2; ++i6) {
                        this.setAir(world, i6, sectionBase + 5, k5);
                        final int k7 = k5 + 1;
                        for (int j4 = sectionBase + 1; j4 <= sectionBase + k7; ++j4) {
                            this.placeBrick(world, random, i6, j4, k5);
                        }
                        if (k7 < 5) {
                            this.placeBrickStair(world, random, i6, sectionBase + k7 + 1, k5, 2);
                        }
                    }
                }
                this.placeRandomFeature(world, random, -2, sectionBase + 1, 4, randomFeature, false);
                this.placeRandomFeature(world, random, -1, sectionBase + 1, 4, randomFeature, false);
                this.setBlockAndMetadata(world, 0, sectionBase + 1, 4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, -3, sectionBase + 1, 4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 0, sectionBase + 2, 4, this.plankSlabBlock, this.plankSlabMeta);
                this.setBlockAndMetadata(world, -3, sectionBase + 2, 4, this.plankSlabBlock, this.plankSlabMeta);
            }
            else {
                for (int k5 = -4; k5 <= 1; ++k5) {
                    for (int i6 = -2; i6 <= -1; ++i6) {
                        this.setAir(world, i6, sectionBase + 5, k5);
                        final int k7 = 5 - (k5 + 4);
                        for (int j4 = sectionBase + 1; j4 <= sectionBase + k7; ++j4) {
                            this.placeBrick(world, random, i6, j4, k5);
                        }
                        if (k7 < 5) {
                            this.placeBrickStair(world, random, i6, sectionBase + k7 + 1, k5, 3);
                        }
                    }
                }
                this.placeRandomFeature(world, random, 2, sectionBase + 1, -4, randomFeature, true);
                this.placeRandomFeature(world, random, 1, sectionBase + 1, -4, randomFeature, true);
                this.setBlockAndMetadata(world, 0, sectionBase + 1, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 3, sectionBase + 1, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 0, sectionBase + 2, -4, this.plankSlabBlock, this.plankSlabMeta);
                this.setBlockAndMetadata(world, 3, sectionBase + 2, -4, this.plankSlabBlock, this.plankSlabMeta);
            }
            if (!this.ruined) {
                final LOTREntityDwarf dwarf = (random.nextInt(3) == 0) ? new LOTREntityDwarfAxeThrower(world) : new LOTREntityDwarfWarrior(world);
                this.spawnNPCAndSetHome(dwarf, world, 0, sectionBase + 1, 0, 12);
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int j5 = 1; j5 <= 2; ++j5) {
                for (int k6 = -4; k6 <= 4; ++k6) {
                    this.setAir(world, i2, (sections + 1) * 5 + j5, k6);
                }
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            this.placeBrickWall(world, random, i2, (sections + 1) * 5 + 1, -5);
            this.placeBrickWall(world, random, i2, (sections + 1) * 5 + 1, 5);
        }
        for (int k8 = -4; k8 <= 4; ++k8) {
            this.placeBrickWall(world, random, -5, (sections + 1) * 5 + 1, k8);
            this.placeBrickWall(world, random, 5, (sections + 1) * 5 + 1, k8);
        }
        this.generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, -5);
        this.generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, 6);
        this.generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, -5);
        this.generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, 6);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.placePillar(world, random, i2, 0, -5);
            for (int j5 = 1; j5 <= 4; ++j5) {
                this.setBlockAndMetadata(world, i2, j5, -5, this.gateBlock, 2);
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            for (int j6 = 4; !this.isOpaque(world, i4, j6, -6) && this.getY(j6) >= 0; --j6) {
                if (j6 == 3) {
                    this.setBlockAndMetadata(world, i4, j6, -6, this.glowBrickBlock, this.glowBrickMeta);
                }
                else {
                    this.placePillar(world, random, i4, j6, -6);
                }
                this.setGrassToDirt(world, i4, j6 - 1, -6);
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.placeBrickSlab(world, random, i2, 5, -6, false);
        }
        if (this.bannerType != null) {
            this.placeWallBanner(world, -2, 7, -5, this.bannerType, 2);
            this.placeWallBanner(world, 0, 8, -5, this.bannerType, 2);
            this.placeWallBanner(world, 2, 7, -5, this.bannerType, 2);
        }
        final LOTREntityNPC commander = this.getCommanderNPC(world);
        if (commander != null) {
            this.spawnNPCAndSetHome(commander, world, 0, (sections + 1) * 5 + 1, 0, 16);
            if (sections % 2 == 0) {
                this.setBlockAndMetadata(world, -3, (sections + 1) * 5 + 1, -3, LOTRMod.commandTable, 0);
            }
            else {
                this.setBlockAndMetadata(world, 3, (sections + 1) * 5 + 1, 3, LOTRMod.commandTable, 0);
            }
        }
        this.placePillar(world, random, -4, (sections + 1) * 5 + 1, 0);
        this.placePillar(world, random, -4, (sections + 1) * 5 + 2, 0);
        this.placePillar(world, random, 4, (sections + 1) * 5 + 1, 0);
        this.placePillar(world, random, 4, (sections + 1) * 5 + 2, 0);
        if (this.bannerType != null) {
            this.placeBrick(world, random, -4, (sections + 1) * 5 + 1, 0);
            this.placeBanner(world, -4, (sections + 1) * 5 + 1, 0, this.bannerType, 1);
            this.placeBrick(world, random, 4, (sections + 1) * 5 + 1, 0);
            this.placeBanner(world, 4, (sections + 1) * 5 + 1, 0, this.bannerType, 3);
        }
        if (!this.ruined) {
            final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
            respawner.setSpawnClasses(LOTREntityDwarfWarrior.class, LOTREntityDwarfAxeThrower.class);
            respawner.setCheckRanges(12, -8, 42, 16);
            respawner.setSpawnRanges(4, 1, 41, 12);
            this.placeNPCRespawner(respawner, world, 0, 0, 0);
        }
        return true;
    }
    
    protected void placeBrick(final World world, final Random random, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
    }
    
    protected void placeBrickSlab(final World world, final Random random, final int i, final int j, final int k, final boolean flip) {
        this.setBlockAndMetadata(world, i, j, k, this.brickSlabBlock, this.brickSlabMeta | (flip ? 8 : 0));
    }
    
    protected void placeBrickStair(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
    }
    
    protected void placeBrickWall(final World world, final Random random, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, this.brickWallBlock, this.brickWallMeta);
    }
    
    protected void placePillar(final World world, final Random random, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, this.pillarBlock, this.pillarMeta);
    }
    
    private void generateCornerPillars(final World world, final Random random, final int i, final int j, final int k) {
        for (int i2 = i - 1; i2 <= i; ++i2) {
            for (int k2 = k - 1; k2 <= k; ++k2) {
                for (int j2 = j; (j2 == 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    if (j2 == j - 2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, this.glowBrickBlock, this.glowBrickMeta);
                    }
                    else {
                        this.placePillar(world, random, i2, j2, k2);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                }
            }
        }
    }
    
    private void placeRandomFeature(final World world, final Random random, final int i, final int j, final int k, final int randomFeature, final boolean flip) {
        if (randomFeature == 0) {
            this.setBlockAndMetadata(world, i, j, k, this.tableBlock, 0);
        }
        else if (randomFeature == 1) {
            this.setBlockAndMetadata(world, i, j, k, this.forgeBlock, flip ? 3 : 2);
            final TileEntity tileentity = this.getTileEntity(world, i, j, k);
            if (tileentity instanceof LOTRTileEntityAlloyForge) {
                ((LOTRTileEntityAlloyForge)tileentity).setInventorySlotContents(12, new ItemStack(Items.coal, 1 + random.nextInt(4)));
            }
        }
        else if (randomFeature == 2) {
            this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 0x8);
            this.placeChest(world, random, i, j + 1, k, flip ? 3 : 2, this.chestContents);
        }
        else if (randomFeature == 3) {
            this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 0x8);
            if (!this.ruined) {
                this.placePlateWithCertainty(world, random, i, j + 1, k, this.plateBlock, LOTRFoods.DWARF);
            }
        }
        else if (randomFeature == 4) {
            this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 0x8);
            if (!this.ruined) {
                this.placeBarrel(world, random, i, j + 1, k, flip ? 3 : 2, LOTRFoods.DWARF_DRINK);
            }
        }
    }
}
