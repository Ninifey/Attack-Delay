// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDorwinionElf;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.block.Block;

public class LOTRWorldGenDorwinionElfHouse extends LOTRWorldGenDorwinionHouse
{
    private Block grapevineBlock;
    private Item wineItem;
    private Item grapeItem;
    
    public LOTRWorldGenDorwinionElfHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (random.nextBoolean()) {
            this.grapevineBlock = LOTRMod.grapevineRed;
            this.wineItem = LOTRMod.mugRedWine;
            this.grapeItem = LOTRMod.grapeRed;
        }
        else {
            this.grapevineBlock = LOTRMod.grapevineWhite;
            this.wineItem = LOTRMod.mugWhiteWine;
            this.grapeItem = LOTRMod.grapeWhite;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -4; i2 <= 8; ++i2) {
                for (int k2 = -1; k2 <= 20; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        boolean generateBackGate = true;
        for (int i3 = 1; i3 <= 3; ++i3) {
            final int k3 = 20;
            final int j3 = this.getTopBlock(world, i3, k3) - 1;
            if (j3 != 0) {
                generateBackGate = false;
            }
        }
        for (int i3 = -4; i3 <= 8; ++i3) {
            for (int k3 = 0; k3 <= 20; ++k3) {
                for (int j3 = 1; j3 <= 6; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int i3 = -3; i3 <= 7; ++i3) {
            for (int k3 = 0; k3 <= 8; ++k3) {
                if (i3 >= 3 && k3 <= 2) {
                    if (random.nextInt(3) == 0) {
                        final BiomeGenBase biome = this.getBiome(world, i3, k3);
                        final int j4 = 1;
                        biome.plantFlower(world, random, this.getX(i3, k3), this.getY(j4), this.getZ(i3, k3));
                    }
                }
                else if ((k3 == 0 && (i3 == -3 || i3 == 2)) || (k3 == 3 && (i3 == 2 || i3 == 7)) || (k3 == 8 && (i3 == -3 || i3 == 7))) {
                    for (int j3 = 0; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                else if (i3 == -3 || (i3 == 2 && k3 <= 3) || i3 == 7 || k3 == 0 || (k3 == 3 && i3 >= 2) || k3 == 8) {
                    for (int j3 = 0; j3 <= 1; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.wallBlock, super.wallMeta);
                    }
                    for (int j3 = 2; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                }
            }
        }
        for (int k2 = 1; k2 <= 7; ++k2) {
            final int k4 = IntMath.mod(k2, 3);
            if (k4 == 1) {
                this.setBlockAndMetadata(world, -4, 1, k2, super.brickStairBlock, 1);
                this.setGrassToDirt(world, -4, 0, k2);
            }
            else if (k4 == 2) {
                this.setAir(world, -3, 2, k2);
                this.setBlockAndMetadata(world, -3, 3, k2, super.brickStairBlock, 7);
                this.setBlockAndMetadata(world, -4, 1, k2, super.leafBlock, super.leafMeta);
            }
            else if (k4 == 0) {
                this.setAir(world, -3, 2, k2);
                this.setBlockAndMetadata(world, -3, 3, k2, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, -4, 1, k2, super.leafBlock, super.leafMeta);
            }
        }
        for (final int k5 : new int[] { 0, 8 }) {
            this.setAir(world, -1, 2, k5);
            this.setAir(world, 0, 2, k5);
            this.setBlockAndMetadata(world, -1, 3, k5, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 3, k5, super.brickStairBlock, 5);
        }
        for (final int k5 : new int[] { 3, 8 }) {
            this.setAir(world, 4, 2, k5);
            this.setAir(world, 5, 2, k5);
            this.setBlockAndMetadata(world, 4, 3, k5, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 3, k5, super.brickStairBlock, 5);
        }
        this.setBlockAndMetadata(world, 3, 1, 2, super.brickStairBlock, 2);
        this.setGrassToDirt(world, 3, 0, 2);
        this.setBlockAndMetadata(world, 4, 1, 2, super.leafBlock, super.leafMeta);
        this.setBlockAndMetadata(world, 5, 1, 2, super.leafBlock, super.leafMeta);
        this.setBlockAndMetadata(world, 6, 1, 2, super.brickStairBlock, 2);
        this.setGrassToDirt(world, 6, 0, 2);
        this.setBlockAndMetadata(world, 8, 1, 4, super.brickStairBlock, 0);
        this.setGrassToDirt(world, 8, 0, 4);
        this.setBlockAndMetadata(world, 8, 1, 5, super.leafBlock, super.leafMeta);
        this.setBlockAndMetadata(world, 8, 1, 6, super.leafBlock, super.leafMeta);
        this.setBlockAndMetadata(world, 8, 1, 7, super.brickStairBlock, 0);
        this.setGrassToDirt(world, 8, 0, 7);
        this.setAir(world, 7, 2, 5);
        this.setAir(world, 7, 2, 6);
        this.setBlockAndMetadata(world, 7, 3, 5, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 7, 3, 6, super.brickStairBlock, 6);
        for (final int i4 : new int[] { -1, 0 }) {
            this.setBlockAndMetadata(world, i4, 0, 0, super.floorBlock, super.floorMeta);
            this.setAir(world, i4, 1, 0);
        }
        for (int i3 = -3; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -1, super.brickStairBlock, 6);
        }
        for (int k2 = -1; k2 <= 2; ++k2) {
            this.setBlockAndMetadata(world, 3, 4, k2, super.brickStairBlock, 4);
            if (IntMath.mod(k2, 2) == 1) {
                this.setBlockAndMetadata(world, 3, 5, k2, super.brickSlabBlock, super.brickSlabMeta);
            }
        }
        for (int i3 = 4; i3 <= 8; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, 2, super.brickStairBlock, 6);
            if (IntMath.mod(i3, 2) == 0) {
                this.setBlockAndMetadata(world, i3, 5, 2, super.brickSlabBlock, super.brickSlabMeta);
            }
        }
        for (int k2 = 3; k2 <= 8; ++k2) {
            this.setBlockAndMetadata(world, 8, 4, k2, super.brickStairBlock, 4);
        }
        for (int i3 = 8; i3 >= -4; --i3) {
            this.setBlockAndMetadata(world, i3, 4, 9, super.brickStairBlock, 7);
            if (IntMath.mod(i3, 2) == 0) {
                this.setBlockAndMetadata(world, i3, 5, 9, super.brickSlabBlock, super.brickSlabMeta);
            }
        }
        for (int k2 = 8; k2 >= -1; --k2) {
            this.setBlockAndMetadata(world, -4, 4, k2, super.brickStairBlock, 5);
            if (IntMath.mod(k2, 2) == 1) {
                this.setBlockAndMetadata(world, -4, 5, k2, super.brickSlabBlock, super.brickSlabMeta);
            }
        }
        for (int k2 = 1; k2 <= 7; ++k2) {
            this.setBlockAndMetadata(world, -2, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            if (k2 <= 3) {
                this.setBlockAndMetadata(world, 1, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
            if (k2 >= 4) {
                this.setBlockAndMetadata(world, 2, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
        }
        for (int i3 = -2; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, 7, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            if (i3 <= 1) {
                this.setBlockAndMetadata(world, i3, 4, 3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
            if (i3 >= 2) {
                this.setBlockAndMetadata(world, i3, 4, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
        }
        for (int k2 = 1; k2 <= 6; ++k2) {
            this.setBlockAndMetadata(world, -2, 5, k2, super.plankStairBlock, 4);
            if (k2 <= 5) {
                this.setBlockAndMetadata(world, -1, 6, k2, super.plankStairBlock, 4);
            }
            if (k2 <= 4) {
                this.setBlockAndMetadata(world, 0, 6, k2, super.plankStairBlock, 5);
            }
            if (k2 <= 3) {
                this.setBlockAndMetadata(world, 1, 5, k2, super.plankStairBlock, 5);
            }
        }
        for (int i3 = -2; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 7, super.plankStairBlock, 6);
            if (i3 >= -1) {
                this.setBlockAndMetadata(world, i3, 6, 6, super.plankStairBlock, 6);
            }
            if (i3 >= 0) {
                this.setBlockAndMetadata(world, i3, 6, 5, super.plankStairBlock, 7);
            }
            if (i3 >= 1) {
                this.setBlockAndMetadata(world, i3, 5, 4, super.plankStairBlock, 7);
            }
        }
        this.setBlockAndMetadata(world, -2, 5, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -1, 5, 0, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 6, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 6, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 5, 0, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 5, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 7, 5, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 7, 5, 5, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 7, 6, 5, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 7, 6, 6, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 7, 5, 6, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 7, 5, 7, super.plankBlock, super.plankMeta);
        for (int k2 = -1; k2 <= 7; ++k2) {
            this.setBlockAndMetadata(world, -3, 5, k2, super.clayStairBlock, 1);
            if (k2 <= 6) {
                this.setBlockAndMetadata(world, -2, 6, k2, super.clayStairBlock, 1);
            }
            if (k2 <= 5) {
                this.setBlockAndMetadata(world, -1, 7, k2, super.clayStairBlock, 1);
            }
            if (k2 <= 4) {
                this.setBlockAndMetadata(world, 0, 7, k2, super.clayStairBlock, 0);
            }
            if (k2 <= 3) {
                this.setBlockAndMetadata(world, 1, 6, k2, super.clayStairBlock, 0);
            }
            if (k2 <= 2) {
                this.setBlockAndMetadata(world, 2, 5, k2, super.clayStairBlock, 0);
            }
        }
        for (int i3 = -3; i3 <= 8; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 8, super.clayStairBlock, 3);
            if (i3 >= -2) {
                this.setBlockAndMetadata(world, i3, 6, 7, super.clayStairBlock, 3);
            }
            if (i3 >= -1) {
                this.setBlockAndMetadata(world, i3, 7, 6, super.clayStairBlock, 3);
            }
            if (i3 >= 0) {
                this.setBlockAndMetadata(world, i3, 7, 5, super.clayStairBlock, 2);
            }
            if (i3 >= 1) {
                this.setBlockAndMetadata(world, i3, 6, 4, super.clayStairBlock, 2);
            }
            if (i3 >= 2) {
                this.setBlockAndMetadata(world, i3, 5, 3, super.clayStairBlock, 2);
            }
        }
        this.setBlockAndMetadata(world, -2, 5, -1, super.clayStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 6, -1, super.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 6, -1, super.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 5, -1, super.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 8, 5, 4, super.clayStairBlock, 7);
        this.setBlockAndMetadata(world, 8, 6, 5, super.clayStairBlock, 7);
        this.setBlockAndMetadata(world, 8, 6, 6, super.clayStairBlock, 6);
        this.setBlockAndMetadata(world, 8, 5, 7, super.clayStairBlock, 6);
        this.setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 3, 4, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -2, 3, 7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 6, 3, 7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 6, 3, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.crafting_table, 0);
        this.placeChest(world, random, -2, 1, 5, 5, LOTRChestContents.DORWINION_HOUSE);
        this.placeChest(world, random, -2, 1, 6, 5, LOTRChestContents.DORWINION_HOUSE);
        this.setBlockAndMetadata(world, -2, 1, 7, LOTRMod.dorwinionTable, 0);
        this.setBlockAndMetadata(world, -1, 1, 6, Blocks.bed, 0);
        this.setBlockAndMetadata(world, -1, 1, 7, Blocks.bed, 8);
        this.setBlockAndMetadata(world, 2, 1, 4, Blocks.furnace, 3);
        this.setBlockAndMetadata(world, 3, 1, 4, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 4, 1, 4, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 5, 1, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 6, 1, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 6, 1, 5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 6, 1, 6, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 6, 1, 7, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 5, 1, 7, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 4, 1, 7, super.plankStairBlock, 4);
        for (final int k5 : new int[] { 4, 7 }) {
            for (int i5 = 4; i5 <= 5; ++i5) {
                this.placePlate(world, random, i5, 2, k5, super.plateBlock, LOTRFoods.DORWINION);
            }
            this.placeBarrel(world, random, 6, 2, k5, 5, new ItemStack(this.wineItem));
        }
        this.placeMug(world, random, 6, 2, 5, 1, new ItemStack(this.wineItem), LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, 6, 2, 6, 1, new ItemStack(this.wineItem), LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, 2, 0, 8, super.floorBlock, super.floorMeta);
        this.setBlockAndMetadata(world, 2, 1, 8, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 2, 2, 8, super.doorBlock, 8);
        this.spawnItemFrame(world, 2, 3, 8, 2, new ItemStack(this.grapeItem));
        this.setBlockAndMetadata(world, 2, 3, 9, Blocks.torch, 3);
        for (int i3 = -3; i3 <= 7; ++i3) {
            for (int k3 = 9; k3 <= 19; ++k3) {
                if (i3 == -3 || i3 == 7 || k3 == 19) {
                    this.setGrassToDirt(world, i3, 0, k3);
                    this.setBlockAndMetadata(world, i3, 1, k3, super.wallBlock, super.wallMeta);
                    this.setBlockAndMetadata(world, i3, 2, k3, super.brickBlock, super.brickMeta);
                    if (IntMath.mod(i3 + k3, 2) == 0) {
                        this.setBlockAndMetadata(world, i3, 3, k3, super.brickSlabBlock, super.brickSlabMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 0);
                    if (IntMath.mod(i3, 2) == 1) {
                        if (k3 == 14) {
                            this.setBlockAndMetadata(world, i3, 0, k3, Blocks.water, 0);
                            this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                            this.setBlockAndMetadata(world, i3, 2, k3, Blocks.torch, 5);
                        }
                        else if (k3 >= 11 && k3 <= 17) {
                            this.setBlockAndMetadata(world, i3, 0, k3, Blocks.farmland, 7);
                            this.setBlockAndMetadata(world, i3, 1, k3, this.grapevineBlock, 7);
                            this.setBlockAndMetadata(world, i3, 2, k3, this.grapevineBlock, 7);
                        }
                    }
                }
            }
        }
        for (int i3 = 0; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, 19, super.brickBlock, super.brickMeta);
        }
        for (int i3 = 1; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, 19, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 4, 19, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 4, 4, 19, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 5, 19, super.brickSlabBlock, super.brickSlabMeta);
        this.setBlockAndMetadata(world, 3, 5, 19, super.brickSlabBlock, super.brickSlabMeta);
        for (final int i4 : new int[] { -3, 4 }) {
            this.setGrassToDirt(world, i4, 0, 20);
            this.setBlockAndMetadata(world, i4, 1, 20, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i4 + 1, 1, 20, super.leafBlock, super.leafMeta);
            this.setBlockAndMetadata(world, i4 + 2, 1, 20, super.leafBlock, super.leafMeta);
            this.setGrassToDirt(world, i4 + 3, 0, 20);
            this.setBlockAndMetadata(world, i4 + 3, 1, 20, super.brickStairBlock, 3);
        }
        if (generateBackGate) {
            for (int i3 = 1; i3 <= 3; ++i3) {
                this.setBlockAndMetadata(world, i3, 0, 19, LOTRMod.dirtPath, 0);
                for (int j2 = 1; j2 <= 3; ++j2) {
                    this.setBlockAndMetadata(world, i3, j2, 19, LOTRMod.gateWooden, 2);
                }
            }
        }
        else {
            for (int i3 = 1; i3 <= 3; ++i3) {
                this.setBlockAndMetadata(world, i3, 1, 20, super.leafBlock, super.leafMeta);
            }
        }
        final LOTREntityDorwinionElf dorwinionElf = new LOTREntityDorwinionElf(world);
        this.spawnNPCAndSetHome(dorwinionElf, world, 0, 1, 5, 16);
        return true;
    }
}
