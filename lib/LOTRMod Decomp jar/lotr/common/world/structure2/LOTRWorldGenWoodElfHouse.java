// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWoodElfHouse extends LOTRWorldGenStructureBase2
{
    private WorldGenerator treeGen;
    protected Block plank1Block;
    protected int plank1Meta;
    protected Block wood1Block;
    protected int wood1Meta;
    protected Block fence1Block;
    protected int fence1Meta;
    protected Block doorBlock;
    protected Block plank2Block;
    protected int plank2Meta;
    protected Block fence2Block;
    protected int fence2Meta;
    protected Block stair2Block;
    protected Block barsBlock;
    protected Block plateBlock;
    
    public LOTRWorldGenWoodElfHouse(final boolean flag) {
        super(flag);
        this.treeGen = (WorldGenerator)new LOTRWorldGenMirkOak(true, 3, 4, 0, false).setGreenOak().disableRestrictions().disableRoots();
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 3) {
                        return false;
                    }
                }
            }
        }
        final int randomWood1 = random.nextInt(2);
        if (randomWood1 == 0) {
            this.plank1Block = LOTRMod.planks2;
            this.plank1Meta = 13;
            this.wood1Block = LOTRMod.wood7;
            this.wood1Meta = 1;
            this.fence1Block = LOTRMod.fence2;
            this.fence1Meta = 13;
            this.doorBlock = LOTRMod.doorGreenOak;
        }
        else {
            this.plank1Block = LOTRMod.planks;
            this.plank1Meta = 9;
            this.wood1Block = LOTRMod.wood2;
            this.wood1Meta = 1;
            this.fence1Block = LOTRMod.fence;
            this.fence1Meta = 9;
            this.doorBlock = LOTRMod.doorBeech;
        }
        final int randomWood2 = random.nextInt(2);
        if (randomWood2 == 0) {
            this.plank2Block = LOTRMod.planks2;
            this.plank2Meta = 13;
            this.fence2Block = LOTRMod.fence2;
            this.fence2Meta = 13;
            this.stair2Block = LOTRMod.stairsGreenOak;
        }
        else {
            this.plank2Block = LOTRMod.planks;
            this.plank2Meta = 9;
            this.fence2Block = LOTRMod.fence;
            this.fence2Meta = 9;
            this.stair2Block = LOTRMod.stairsBeech;
        }
        this.barsBlock = LOTRMod.woodElfWoodBars;
        this.plateBlock = LOTRMod.woodPlateBlock;
        for (int i2 = -6; i2 <= 6; ++i2) {
            for (int k2 = -6; k2 <= 6; ++k2) {
                for (int j2 = 1; j2 <= 7; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    if (this.getBlock(world, i2, j2 + 1, k2).isOpaqueCube()) {
                        this.setBlockAndMetadata(world, i2, j2, k2, Blocks.dirt, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j2, k2, (Block)Blocks.grass, 0);
                    }
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.brick3, 5);
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; j3 <= 3; ++j3) {
                    if (i3 == 4 && k3 == 4) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.wood1Block, this.wood1Meta);
                    }
                    else if (i3 == 4 || k3 == 4) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.plank1Block, this.plank1Meta);
                    }
                    else {
                        this.setAir(world, i2, j3, k2);
                    }
                }
                if (i3 == 4 || k3 == 4) {
                    this.setBlockAndMetadata(world, i2, 4, k2, this.plank2Block, this.plank2Meta);
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, -5, this.stair2Block, 6);
            this.setBlockAndMetadata(world, i2, 4, 5, this.stair2Block, 7);
            for (final int k4 : new int[] { -5, 5 }) {
                if (!this.getBlock(world, i2, 1, k4).isOpaqueCube()) {
                    this.setBlockAndMetadata(world, i2, 1, k4, LOTRMod.leaves7, 5);
                }
            }
        }
        for (int k5 = -4; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, -5, 4, k5, this.stair2Block, 5);
            this.setBlockAndMetadata(world, 5, 4, k5, this.stair2Block, 4);
            for (final int i4 : new int[] { -5, 5 }) {
                if (!this.getBlock(world, i4, 1, k5).isOpaqueCube()) {
                    this.setBlockAndMetadata(world, i4, 1, k5, LOTRMod.leaves7, 5);
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, -3, this.stair2Block, 7);
            this.setBlockAndMetadata(world, i2, 4, 3, this.stair2Block, 6);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -3, 4, k5, this.stair2Block, 4);
            this.setBlockAndMetadata(world, 3, 4, k5, this.stair2Block, 5);
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 5 || k3 == 5) {
                    this.setBlockAndMetadata(world, i2, 5, k2, this.fence1Block, this.fence1Meta);
                }
                if (i3 == 5 && k3 == 5) {
                    this.setBlockAndMetadata(world, i2, 6, k2, LOTRMod.woodElvenTorch, 5);
                }
                if ((i3 == 5 && k3 == 0) || (k3 == 5 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 6, k2, this.fence1Block, this.fence1Meta);
                    this.setBlockAndMetadata(world, i2, 7, k2, LOTRMod.woodElvenTorch, 5);
                }
            }
        }
        this.setBlockAndMetadata(world, -3, 2, -1, LOTRMod.woodElvenTorch, 2);
        this.setBlockAndMetadata(world, -3, 2, 1, LOTRMod.woodElvenTorch, 2);
        this.setBlockAndMetadata(world, 3, 2, -1, LOTRMod.woodElvenTorch, 1);
        this.setBlockAndMetadata(world, 3, 2, 1, LOTRMod.woodElvenTorch, 1);
        this.setBlockAndMetadata(world, -1, 2, -3, LOTRMod.woodElvenTorch, 3);
        this.setBlockAndMetadata(world, 1, 2, -3, LOTRMod.woodElvenTorch, 3);
        this.setBlockAndMetadata(world, -1, 2, 3, LOTRMod.woodElvenTorch, 4);
        this.setBlockAndMetadata(world, 1, 2, 3, LOTRMod.woodElvenTorch, 4);
        final int[] carpets = { 12, 13, 14, 15 };
        final int carpetType = carpets[random.nextInt(carpets.length)];
        for (int i5 = -4; i5 <= 4; ++i5) {
            for (int k6 = -4; k6 <= 4; ++k6) {
                final int i6 = Math.abs(i5);
                final int k7 = Math.abs(k6);
                this.setBlockAndMetadata(world, i5, -5, k6, LOTRMod.brick3, 5);
                for (int j4 = -4; j4 <= -1; ++j4) {
                    if (i6 == 4 || k7 == 4) {
                        if (j4 >= -3 && j4 <= -2) {
                            this.setBlockAndMetadata(world, i5, j4, k6, Blocks.stonebrick, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i5, j4, k6, this.plank1Block, this.plank1Meta);
                        }
                    }
                    else {
                        this.setAir(world, i5, j4, k6);
                    }
                }
                if (i6 <= 2 && k7 <= 2) {
                    this.setBlockAndMetadata(world, i5, -4, k6, Blocks.carpet, carpetType);
                }
            }
        }
        for (int j2 = -3; j2 <= -2; ++j2) {
            this.setBlockAndMetadata(world, -2, j2, -4, this.wood1Block, this.wood1Meta);
            this.setBlockAndMetadata(world, -1, j2, -4, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, 0, j2, -4, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, 1, j2, -4, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, 1, j2, -4, this.wood1Block, this.wood1Meta);
        }
        for (int k8 = 2; k8 <= 3; ++k8) {
            for (int i7 = -2; i7 <= 2; ++i7) {
                this.setAir(world, i7, 0, k8);
                final int stairHeight = i7 + 2;
                for (int j5 = -4; j5 < -4 + stairHeight; ++j5) {
                    this.setBlockAndMetadata(world, i7, j5, k8, LOTRMod.brick3, 5);
                }
                this.setBlockAndMetadata(world, i7, -4 + stairHeight, k8, LOTRMod.stairsWoodElvenBrick, 1);
            }
            for (int j6 = -4; j6 <= -1; ++j6) {
                this.setBlockAndMetadata(world, 3, j6, k8, LOTRMod.brick3, 5);
            }
        }
        this.setBlockAndMetadata(world, -3, -2, -3, LOTRMod.woodElvenTorch, 3);
        this.setBlockAndMetadata(world, 3, -2, -3, LOTRMod.woodElvenTorch, 3);
        this.setBlockAndMetadata(world, -3, -2, 3, LOTRMod.woodElvenTorch, 4);
        this.setBlockAndMetadata(world, 3, -2, 1, LOTRMod.woodElvenTorch, 4);
        this.setBlockAndMetadata(world, 3, -4, 0, LOTRMod.woodElvenBed, 0);
        this.setBlockAndMetadata(world, 3, -4, 1, LOTRMod.woodElvenBed, 8);
        for (int i5 = -3; i5 <= 3; ++i5) {
            this.setBlockAndMetadata(world, i5, -1, -3, this.barsBlock, 0);
        }
        for (int k8 = -2; k8 <= 3; ++k8) {
            this.setBlockAndMetadata(world, -3, -1, k8, this.barsBlock, 0);
        }
        for (int k8 = -2; k8 <= 1; ++k8) {
            this.setBlockAndMetadata(world, 3, -1, k8, this.barsBlock, 0);
        }
        for (int j2 = 1; j2 <= 3; ++j2) {
            for (int i7 = -1; i7 <= 1; ++i7) {
                this.setBlockAndMetadata(world, i7, j2, -4, this.wood1Block, this.wood1Meta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, -2, LOTRMod.brick2, 14);
        this.setBlockAndMetadata(world, -2, 0, 0, LOTRMod.brick2, 14);
        this.setBlockAndMetadata(world, 2, 0, 0, LOTRMod.brick2, 14);
        this.setBlockAndMetadata(world, 3, 0, 3, LOTRMod.brick2, 14);
        this.setAir(world, 0, 1, -5);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        this.setBlockAndMetadata(world, -1, 2, -5, LOTRMod.woodElvenTorch, 4);
        this.setBlockAndMetadata(world, 1, 2, -5, LOTRMod.woodElvenTorch, 4);
        this.setBlockAndMetadata(world, -3, 2, -4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -2, 2, -4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 2, 2, -4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 3, 2, -4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 3, 1, -3, this.plank2Block, this.plank2Meta);
        this.setBlockAndMetadata(world, 2, 1, -3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, -2, LOTRMod.woodElvenTable, 0);
        this.setBlockAndMetadata(world, -3, 1, -3, this.plank2Block, this.plank2Meta);
        this.placeMug(world, random, -3, 2, -3, random.nextInt(4), LOTRFoods.WOOD_ELF_DRINK);
        this.setBlockAndMetadata(world, -2, 1, -3, this.plank2Block, this.plank2Meta);
        this.placePlate(world, random, -2, 2, -3, this.plateBlock, LOTRFoods.ELF);
        this.placeChest(world, random, -3, 1, -2, 0, LOTRChestContents.WOOD_ELF_HOUSE);
        this.placeWoodElfItemFrame(world, -4, 2, 0, 1, random);
        this.placeWoodElfItemFrame(world, 4, 2, 0, 3, random);
        for (int j2 = 1; j2 <= 4; ++j2) {
            this.setBlockAndMetadata(world, 3, j2, 3, Blocks.ladder, 2);
        }
        for (int j2 = -4; j2 <= 4; ++j2) {
            this.setBlockAndMetadata(world, 0, j2, 0, LOTRMod.wood7, 1);
        }
        this.treeGen.generate(world, random, this.getX(0, 0), this.getY(5), this.getZ(0, 0));
        final LOTREntityWoodElf elf = new LOTREntityWoodElf(world);
        this.spawnNPCAndSetHome(elf, world, 1, 1, 1, 8);
        return true;
    }
    
    private void placeWoodElfItemFrame(final World world, final int i, final int j, final int k, final int direction, final Random random) {
        ItemStack item = null;
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                item = new ItemStack(LOTRMod.mirkwoodBow);
                break;
            }
            case 1: {
                item = new ItemStack(Items.arrow);
                break;
            }
            case 2: {
                item = new ItemStack(LOTRMod.sapling7, 1, 1);
                break;
            }
            case 3: {
                item = new ItemStack((Block)Blocks.red_flower);
                break;
            }
            case 4: {
                item = new ItemStack((Block)Blocks.yellow_flower);
                break;
            }
            case 5: {
                item = new ItemStack(Items.book);
                break;
            }
        }
        this.spawnItemFrame(world, i, j, k, direction, item);
    }
}
