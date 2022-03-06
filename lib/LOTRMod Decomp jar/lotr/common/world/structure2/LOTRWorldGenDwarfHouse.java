// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.util.MathHelper;
import com.google.common.math.IntMath;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.world.World;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;

public class LOTRWorldGenDwarfHouse extends LOTRWorldGenStructureBase2
{
    protected Block stoneBlock;
    protected int stoneMeta;
    protected Block fillerBlock;
    protected int fillerMeta;
    protected Block topBlock;
    protected int topMeta;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickStairBlock;
    protected Block brick2Block;
    protected int brick2Meta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block chandelierBlock;
    protected int chandelierMeta;
    protected Block tableBlock;
    protected Block barsBlock;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block plateBlock;
    protected LOTRChestContents larderContents;
    protected LOTRChestContents personalContents;
    protected LOTRFoods plateFoods;
    protected LOTRFoods drinkFoods;
    
    public LOTRWorldGenDwarfHouse(final boolean flag) {
        super(flag);
    }
    
    protected LOTREntityDwarf createDwarf(final World world) {
        return new LOTREntityDwarf(world);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        this.stoneBlock = Blocks.stone;
        this.stoneMeta = 0;
        this.fillerBlock = Blocks.dirt;
        this.fillerMeta = 0;
        this.topBlock = (Block)Blocks.grass;
        this.topMeta = 0;
        this.brickBlock = LOTRMod.brick;
        this.brickMeta = 6;
        this.brickStairBlock = LOTRMod.stairsDwarvenBrick;
        this.brick2Block = Blocks.stonebrick;
        this.brick2Meta = 0;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 0;
        this.chandelierBlock = LOTRMod.chandelier;
        this.chandelierMeta = 8;
        this.tableBlock = LOTRMod.dwarvenTable;
        this.barsBlock = LOTRMod.dwarfBars;
        final int randomWood = random.nextInt(4);
        if (randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 1;
            this.plankStairBlock = Blocks.spruce_stairs;
        }
        else if (randomWood == 1) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 13;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 5;
            this.plankStairBlock = LOTRMod.stairsLarch;
        }
        else if (randomWood == 2) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsPine;
        }
        else if (randomWood == 3) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 3;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsFir;
        }
        this.carpetBlock = Blocks.carpet;
        final int randomCarpet = random.nextInt(3);
        if (randomCarpet == 0) {
            this.carpetMeta = 7;
        }
        else if (randomCarpet == 1) {
            this.carpetMeta = 12;
        }
        else if (randomCarpet == 2) {
            this.carpetMeta = 15;
        }
        if (random.nextBoolean()) {
            this.plateBlock = LOTRMod.ceramicPlateBlock;
        }
        else {
            this.plateBlock = LOTRMod.woodPlateBlock;
        }
        this.larderContents = LOTRChestContents.DWARF_HOUSE_LARDER;
        this.personalContents = LOTRChestContents.DWARVEN_TOWER;
        this.plateFoods = LOTRFoods.DWARF;
        this.drinkFoods = LOTRFoods.DWARF_DRINK;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (super.restrictions && super.usingPlayer == null) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            final int xzRange = 5;
            final int yRange = 4;
            for (int i2 = -xzRange; i2 <= xzRange; ++i2) {
                for (int j2 = -yRange; j2 <= yRange; ++j2) {
                    for (int k2 = -xzRange; k2 <= xzRange; ++k2) {
                        if (this.isAir(world, i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        else {
            this.setOriginAndRotation(world, i, j, k, rotation, 8);
        }
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i3 = -1; i3 <= 1; ++i3) {
                for (int j3 = 1; j3 <= 2; ++j3) {
                    boolean foundAir = false;
                    for (int k3 = -8; k3 >= -14; --k3) {
                        if (this.isAir(world, i3, j3, k3)) {
                            foundAir = true;
                            break;
                        }
                    }
                    if (!foundAir) {
                        return false;
                    }
                }
            }
            for (int i3 = -1; i3 <= 1; ++i3) {
                for (int j3 = 1; j3 <= 2; ++j3) {
                    for (int k4 = -8; k4 >= -14 && !this.isAir(world, i3, j3, k4); --k4) {
                        this.setAir(world, i3, j3, k4);
                        if (j3 == 1) {
                            this.setBlockAndMetadata(world, i3, j3 - 1, k4, this.stoneBlock, this.stoneMeta);
                        }
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k5 = -7; k5 <= 7; ++k5) {
                final int i4 = Math.abs(i3);
                final int k6 = Math.abs(k5);
                final int dist = (int)Math.round(Math.sqrt(i4 * i4 + k6 * k6));
                int top = 13 - dist;
                int j4;
                for (top = (j4 = Math.min(top, 7)); (j4 >= -5 || !this.isOpaque(world, i3, j4, k5)) && this.getY(j4) >= 0; --j4) {
                    if (!this.isOpaque(world, i3, j4, k5)) {
                        Block block = null;
                        int meta = -1;
                        if (j4 >= top - 4) {
                            if (this.isOpaque(world, i3, j4 + 1, k5)) {
                                block = this.fillerBlock;
                                meta = this.fillerMeta;
                            }
                            else {
                                block = this.topBlock;
                                meta = this.topMeta;
                            }
                        }
                        else {
                            block = this.stoneBlock;
                            meta = this.stoneMeta;
                        }
                        if (block != null) {
                            this.setBlockAndMetadata(world, i3, j4, k5, block, meta);
                            this.setGrassToDirt(world, i3, j4 - 1, k5);
                        }
                    }
                }
            }
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            int i5 = 5 - j5;
            if (j5 >= 3) {
                --i5;
            }
            for (int i2 = -i5; i2 <= i5; ++i2) {
                this.setBlockAndMetadata(world, i2, j5, -7, this.stoneBlock, this.stoneMeta);
            }
        }
        for (int i3 = -11; i3 <= 11; ++i3) {
            for (int k5 = -11; k5 <= 11; ++k5) {
                final int i4 = Math.abs(i3);
                final int k6 = Math.abs(k5);
                if (i4 > 7 || k6 > 7) {
                    final int i6 = Math.min(i4, k6);
                    final int k7 = Math.max(i4, k6);
                    int diff = k7 - 8;
                    for (final int limit : new int[] { 4, 7, 9 }) {
                        if (i6 >= limit) {
                            diff += i6 - limit;
                        }
                    }
                    int j6;
                    for (int top2 = j6 = 0 - (i6 + diff) / 2; !this.isOpaque(world, i3, j6, k5) && this.getY(j6) >= 0; --j6) {
                        Block block2 = null;
                        int meta2 = -1;
                        if (j6 >= top2 - 4) {
                            if (this.isOpaque(world, i3, j6 + 1, k5)) {
                                block2 = this.fillerBlock;
                                meta2 = this.fillerMeta;
                            }
                            else {
                                block2 = this.topBlock;
                                meta2 = this.topMeta;
                            }
                        }
                        else {
                            block2 = this.stoneBlock;
                            meta2 = this.stoneMeta;
                        }
                        if (block2 != null) {
                            this.setBlockAndMetadata(world, i3, j6, k5, block2, meta2);
                            this.setGrassToDirt(world, i3, j6 - 1, k5);
                        }
                    }
                }
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k5 = -6; k5 <= 6; ++k5) {
                for (int j7 = -4; j7 <= 4; ++j7) {
                    if (Math.abs(i3) == 6 || Math.abs(k5) == 6) {
                        if (j7 == 2) {
                            this.setBlockAndMetadata(world, i3, j7, k5, this.plankBlock, this.plankMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j7, k5, this.brick2Block, this.brick2Meta);
                        }
                    }
                    else if (j7 == 0 || Math.abs(j7) == 4) {
                        this.setBlockAndMetadata(world, i3, j7, k5, this.brick2Block, this.brick2Meta);
                    }
                    else {
                        this.setAir(world, i3, j7, k5);
                    }
                }
            }
        }
        for (int j5 = -3; j5 <= 3; ++j5) {
            if (j5 != 0) {
                this.setBlockAndMetadata(world, -5, j5, -5, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, -5, j5, 5, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, 5, j5, -5, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, 5, j5, 5, this.pillarBlock, this.pillarMeta);
            }
        }
        this.setBlockAndMetadata(world, -4, 2, -5, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -5, 2, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 2, 5, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -5, 2, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 4, 2, -5, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 5, 2, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 4, 2, 5, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 5, 2, 4, Blocks.torch, 4);
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -5, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 3, 5, this.brickStairBlock, 6);
        }
        for (int k8 = -4; k8 <= 4; ++k8) {
            this.setBlockAndMetadata(world, -5, 3, k8, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 3, k8, this.brickStairBlock, 5);
        }
        for (int j5 = 1; j5 <= 2; ++j5) {
            this.setBlockAndMetadata(world, -1, j5, -6, this.pillarBlock, this.pillarMeta);
            this.setAir(world, 0, j5, -6);
            this.setBlockAndMetadata(world, 1, j5, -6, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, -1, j5, -7, this.stoneBlock, this.stoneMeta);
            this.setAir(world, 0, j5, -7);
            this.setBlockAndMetadata(world, 1, j5, -7, this.stoneBlock, this.stoneMeta);
        }
        this.placeIthildinDoor(world, 0, 1, -7, LOTRMod.dwarvenDoorIthildin, 3, LOTRBlockGateDwarvenIthildin.DoorSize._1x2);
        for (int k8 = -4; k8 <= -3; ++k8) {
            for (int i7 = -3; i7 <= 3; ++i7) {
                this.setBlockAndMetadata(world, i7, 1, k8, this.carpetBlock, this.carpetMeta);
            }
        }
        for (int k8 = -1; k8 <= 3; ++k8) {
            for (int i7 = -1; i7 <= 1; ++i7) {
                if (Math.abs(i7) == 1 && (k8 == -1 || k8 == 3)) {
                    this.setBlockAndMetadata(world, i7, 1, k8, this.plankBlock, this.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i7, 1, k8, this.plankSlabBlock, this.plankSlabMeta | 0x8);
                }
                if (random.nextInt(3) == 0) {
                    this.placeMug(world, random, i7, 2, k8, random.nextInt(4), this.drinkFoods);
                }
                else {
                    this.placePlate(world, random, i7, 2, k8, this.plateBlock, this.plateFoods);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 0, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, 0, 3, 2, this.chandelierBlock, this.chandelierMeta);
        for (int k8 = 0; k8 <= 2; ++k8) {
            this.setBlockAndMetadata(world, -3, 1, k8, this.plankStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 1, k8, this.plankStairBlock, 1);
        }
        for (int k8 = 4; k8 <= 6; ++k8) {
            for (int j3 = 1; j3 <= 4; ++j3) {
                for (int i2 = -2; i2 <= 2; ++i2) {
                    this.setBlockAndMetadata(world, i2, j3, k8, this.brickBlock, this.brickMeta);
                }
            }
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, 4, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 2, j5, 4, this.pillarBlock, this.pillarMeta);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, 4, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i3, 3, 4, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i3, 1, 5, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, i3, 2, 5, (Block)Blocks.fire, 0);
            this.setAir(world, i3, 3, 5);
        }
        for (int k8 = -2; k8 <= 1; ++k8) {
            this.setAir(world, -5, 0, k8);
            this.setAir(world, 5, 0, k8);
            final int height = 1 - k8;
            for (int j7 = -3; j7 < -3 + height; ++j7) {
                this.setBlockAndMetadata(world, -5, j7, k8, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 5, j7, k8, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, -5, -3 + height, k8, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, 5, -3 + height, k8, this.brickStairBlock, 3);
        }
        for (int k8 = -5; k8 <= 5; ++k8) {
            for (int j3 = -3; j3 <= -1; ++j3) {
                for (int i2 = -1; i2 <= 1; ++i2) {
                    this.setBlockAndMetadata(world, i2, j3, k8, this.plankBlock, this.plankMeta);
                }
            }
        }
        for (int j5 = -3; j5 <= -1; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, -5, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, -2, j5, 5, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, -5, -2, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, -2, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, -2, -4, Blocks.torch, 3);
        for (int k8 = -4; k8 <= 4; ++k8) {
            if (IntMath.mod(k8, 2) == 1) {
                this.setBlockAndMetadata(world, -2, -3, k8, this.plankSlabBlock, this.plankSlabMeta | 0x8);
                if (random.nextBoolean()) {
                    this.placePlateWithCertainty(world, random, -2, -2, k8, this.plateBlock, this.plateFoods);
                }
                else {
                    this.placeMug(world, random, -2, -2, k8, 1, this.drinkFoods);
                }
            }
            else {
                this.setBlockAndMetadata(world, -2, -3, k8, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, -2, -1, k8, this.brickStairBlock, 5);
        }
        for (int i3 = -4; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, -3, -5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i3, -2, -6, this.plankBlock, this.plankMeta);
            this.placeBarrel(world, random, i3, -2, -5, 3, this.drinkFoods);
            this.setBlockAndMetadata(world, i3, -1, -5, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, -3, 5, Blocks.furnace, 2);
            this.setBlockAndMetadata(world, i3, -2, 6, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i3, -1, 5, this.brickStairBlock, 6);
        }
        for (int k8 = -4; k8 <= -3; ++k8) {
            this.setBlockAndMetadata(world, -5, -3, k8, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -6, -2, k8, this.plankBlock, this.plankMeta);
            this.placeChest(world, random, -5, -2, k8, 4, this.larderContents);
            this.setBlockAndMetadata(world, -5, -1, k8, this.brickStairBlock, 4);
        }
        this.setBlockAndMetadata(world, -2, -3, 2, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -2, -3, 0, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -2, -3, -2, this.tableBlock, 0);
        for (int j5 = -3; j5 <= -1; ++j5) {
            this.setBlockAndMetadata(world, 2, j5, -5, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 2, j5, 5, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 5, -2, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, -2, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 5, -2, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, -2, -4, Blocks.torch, 3);
        for (int k8 = -4; k8 <= 4; ++k8) {
            this.setBlockAndMetadata(world, 2, -3, k8, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 2, -1, k8, this.brickStairBlock, 4);
        }
        for (int i3 = 3; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, -3, -5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i3, -2, -6, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i3, -1, -5, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, -3, 5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i3, -2, 6, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i3, -1, 5, this.brickStairBlock, 6);
            for (int k5 = -2; k5 <= 0; ++k5) {
                this.setBlockAndMetadata(world, i3, -3, k5, this.carpetBlock, this.carpetMeta);
            }
            this.setBlockAndMetadata(world, i3, -3, -3, LOTRMod.dwarvenBed, 2);
            this.setBlockAndMetadata(world, i3, -3, -4, LOTRMod.dwarvenBed, 10);
            this.placeChest(world, random, i3, -2, -5, 3, this.personalContents, MathHelper.getRandomIntegerInRange(random, 2, 4));
        }
        for (int k8 = -4; k8 <= -3; ++k8) {
            this.setBlockAndMetadata(world, 5, -3, k8, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 6, -2, k8, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 5, -1, k8, this.brickStairBlock, 5);
        }
        for (int k8 = -2; k8 <= 2; ++k8) {
            if (k8 == 0) {
                final ItemStack item = this.getRandomWeaponItem(random);
                this.placeWeaponRack(world, 2, -2, k8, 5, item);
            }
            else if (IntMath.mod(k8, 2) == 0) {
                final ItemStack item = random.nextBoolean() ? this.getRandomWeaponItem(random) : this.getRandomOtherItem(random);
                this.spawnItemFrame(world, 1, -2, k8, 1, item);
            }
        }
        final LOTREntityDwarf dwarfMale = this.createDwarf(world);
        dwarfMale.familyInfo.setMale(true);
        dwarfMale.familyInfo.setName(LOTRNames.getDwarfName(random, dwarfMale.familyInfo.isMale()));
        this.spawnNPCAndSetHome(dwarfMale, world, 0, 2, 0, 8);
        final LOTREntityDwarf dwarfFemale = this.createDwarf(world);
        dwarfFemale.familyInfo.setMale(false);
        dwarfFemale.familyInfo.setName(LOTRNames.getDwarfName(random, dwarfFemale.familyInfo.isMale()));
        this.spawnNPCAndSetHome(dwarfFemale, world, 0, 2, 0, 8);
        final int maxChildren = dwarfMale.familyInfo.getRandomMaxChildren();
        dwarfMale.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.dwarvenRing));
        dwarfMale.familyInfo.spouseUniqueID = dwarfFemale.getUniqueID();
        dwarfMale.familyInfo.setMaxBreedingDelay();
        dwarfMale.familyInfo.maxChildren = maxChildren;
        dwarfFemale.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.dwarvenRing));
        dwarfFemale.familyInfo.spouseUniqueID = dwarfMale.getUniqueID();
        dwarfFemale.familyInfo.setMaxBreedingDelay();
        dwarfFemale.familyInfo.maxChildren = maxChildren;
        return true;
    }
    
    protected ItemStack getRandomWeaponItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.swordDwarven), new ItemStack(LOTRMod.daggerDwarven), new ItemStack(LOTRMod.hammerDwarven), new ItemStack(LOTRMod.battleaxeDwarven), new ItemStack(LOTRMod.pickaxeDwarven), new ItemStack(LOTRMod.mattockDwarven), new ItemStack(LOTRMod.throwingAxeDwarven), new ItemStack(LOTRMod.pikeDwarven), new ItemStack(LOTRMod.swordDale), new ItemStack(LOTRMod.daggerDale), new ItemStack(LOTRMod.pikeDale), new ItemStack(LOTRMod.spearDale), new ItemStack(LOTRMod.battleaxeDale) };
        return items[random.nextInt(items.length)].copy();
    }
    
    protected ItemStack getRandomOtherItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.helmetDwarven), new ItemStack(LOTRMod.bodyDwarven), new ItemStack(LOTRMod.legsDwarven), new ItemStack(LOTRMod.bootsDwarven), new ItemStack(LOTRMod.helmetDale), new ItemStack(LOTRMod.bodyDale), new ItemStack(LOTRMod.legsDale), new ItemStack(LOTRMod.bootsDale), new ItemStack(LOTRMod.dwarfSteel), new ItemStack(LOTRMod.bronze), new ItemStack(Items.iron_ingot), new ItemStack(LOTRMod.silver), new ItemStack(LOTRMod.silverNugget), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_nugget) };
        return items[random.nextInt(items.length)].copy();
    }
}
