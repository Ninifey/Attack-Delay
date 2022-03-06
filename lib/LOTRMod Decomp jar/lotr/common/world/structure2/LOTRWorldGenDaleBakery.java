// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDaleBaker;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleBakery extends LOTRWorldGenDaleStructure
{
    public LOTRWorldGenDaleBakery(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -4; k2 <= 15; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = 0; k3 <= 13; ++k3) {
                for (int j3 = 0; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 7; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (Math.abs(i3) == 5 || k3 == 0 || k3 == 13) {
                    for (int j3 = 1; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                }
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k3 = -1; k3 <= 14; ++k3) {
                if ((Math.abs(i3) == 6 && (k3 == -1 || k3 == 14)) || (Math.abs(i3) == 1 && k3 == -1)) {
                    for (int j3 = 4; (j3 >= 1 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            if (Math.abs(i3) == 1) {
                for (int k3 = -2, j3 = 2; (j3 >= 1 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.fenceBlock, super.fenceMeta);
                }
            }
            else if (i3 == 0) {
                for (int k3 = -1, j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.floorBlock, super.floorMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -1, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i3, 4, 14, super.brickBlock, super.brickMeta);
        }
        for (int k4 = 0; k4 <= 13; ++k4) {
            this.setBlockAndMetadata(world, -6, 4, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 6, 4, k4, super.brickBlock, super.brickMeta);
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i3, 4, 15, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        }
        for (int k4 = -2; k4 <= 15; ++k4) {
            this.setBlockAndMetadata(world, -7, 4, k4, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 7, 4, k4, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        }
        for (final int i4 : new int[] { -5, 2 }) {
            this.setBlockAndMetadata(world, i4, 2, -1, Blocks.trapdoor, 12);
            this.setBlockAndMetadata(world, i4, 3, -1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 4, -2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i4 + 1, 2, 0, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4 + 1, 3, -1, super.plankSlabBlock, super.plankSlabMeta);
            this.setBlockAndMetadata(world, i4 + 2, 2, 0, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4 + 2, 3, -1, super.plankSlabBlock, super.plankSlabMeta);
            this.setBlockAndMetadata(world, i4 + 3, 2, -1, Blocks.trapdoor, 12);
            this.setBlockAndMetadata(world, i4 + 3, 3, -1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4 + 3, 4, -2, super.brickBlock, super.brickMeta);
        }
        for (final int i4 : new int[] { -5, 2 }) {
            this.setBlockAndMetadata(world, i4, 2, 14, Blocks.trapdoor, 13);
            this.setBlockAndMetadata(world, i4, 3, 14, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 4, 15, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i4 + 1, 2, 13, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4 + 1, 3, 14, super.plankSlabBlock, super.plankSlabMeta);
            this.setBlockAndMetadata(world, i4 + 2, 2, 13, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4 + 2, 3, 14, super.plankSlabBlock, super.plankSlabMeta);
            this.setBlockAndMetadata(world, i4 + 3, 2, 14, Blocks.trapdoor, 13);
            this.setBlockAndMetadata(world, i4 + 3, 3, 14, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4 + 3, 4, 15, super.brickBlock, super.brickMeta);
        }
        for (int k4 = 0; k4 <= 13; ++k4) {
            if (k4 == 0 || k4 == 3 || k4 == 6 || k4 == 8 || k4 == 13) {
                this.setBlockAndMetadata(world, -6, 2, k4, Blocks.trapdoor, 15);
                this.setBlockAndMetadata(world, -6, 3, k4, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, -7, 4, k4, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, 6, 2, k4, Blocks.trapdoor, 14);
                this.setBlockAndMetadata(world, 6, 3, k4, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 7, 4, k4, super.brickBlock, super.brickMeta);
            }
            if (k4 == 1 || k4 == 2 || k4 == 4 || k4 == 5 || (k4 >= 9 && k4 <= 12)) {
                this.setBlockAndMetadata(world, -5, 2, k4, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, -6, 3, k4, super.plankSlabBlock, super.plankSlabMeta);
                this.setBlockAndMetadata(world, 5, 2, k4, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, 6, 3, k4, super.plankSlabBlock, super.plankSlabMeta);
            }
        }
        for (int k4 = -2; k4 <= -1; ++k4) {
            this.setBlockAndMetadata(world, -1, 3, k4, Blocks.wool, 14);
            this.setBlockAndMetadata(world, 0, 3, k4, Blocks.wool, 0);
            this.setBlockAndMetadata(world, 1, 3, k4, Blocks.wool, 14);
        }
        this.setBlockAndMetadata(world, 0, 1, -1, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, -1, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 0, 0, super.floorBlock, super.floorMeta);
        this.setAir(world, 0, 1, 0);
        this.setAir(world, 0, 2, 0);
        for (int k4 = 0; k4 <= 13; ++k4) {
            for (int i5 = -5; i5 <= 5; ++i5) {
                final int i6 = Math.abs(i5);
                if (i6 >= 1 && i6 <= 3 && ((k4 >= 2 && k4 <= 6) || (k4 >= 8 && k4 <= 11))) {
                    this.setBlockAndMetadata(world, i5, 4, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                else {
                    this.setBlockAndMetadata(world, i5, 4, k4, super.plankBlock, super.plankMeta);
                }
            }
            for (int i5 = -6; i5 <= 6; ++i5) {
                this.setBlockAndMetadata(world, i5, 5, k4, super.roofBlock, super.roofMeta);
            }
            for (int i5 = -5; i5 <= 5; ++i5) {
                this.setBlockAndMetadata(world, i5, 6, k4, super.roofBlock, super.roofMeta);
            }
        }
        for (final int k2 : new int[] { -1, 14 }) {
            for (int i7 = -6; i7 <= 6; ++i7) {
                this.setBlockAndMetadata(world, i7, 5, k2, super.plankBlock, super.plankMeta);
            }
            for (int i7 = -5; i7 <= 5; ++i7) {
                this.setBlockAndMetadata(world, i7, 6, k2, super.plankBlock, super.plankMeta);
            }
        }
        for (final int k2 : new int[] { -2, 15 }) {
            this.setBlockAndMetadata(world, -6, 5, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -5, 6, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 6, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 5, k2, super.roofStairBlock, 5);
            for (final int i8 : new int[] { -3, 0, 3 }) {
                for (int j4 = 5; j4 <= 6; ++j4) {
                    this.setBlockAndMetadata(world, i8, j4, k2, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int k4 = -2; k4 <= 15; ++k4) {
            this.setBlockAndMetadata(world, -7, 5, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -6, 6, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -5, 7, k4, super.roofStairBlock, 1);
            for (int i5 = -4; i5 <= 4; ++i5) {
                this.setBlockAndMetadata(world, i5, 7, k4, super.roofBlock, super.roofMeta);
            }
            for (int i5 = -2; i5 <= 2; ++i5) {
                this.setBlockAndMetadata(world, i5, 8, k4, super.roofSlabBlock, super.roofSlabMeta);
            }
            this.setBlockAndMetadata(world, 5, 7, k4, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 6, 6, k4, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 7, 5, k4, super.roofStairBlock, 0);
        }
        for (int j5 = 1; j5 <= 9; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, 11, Blocks.brick_block, 0);
            this.setBlockAndMetadata(world, -1, j5, 12, Blocks.brick_block, 0);
            this.setAir(world, 0, j5, 12);
            this.setBlockAndMetadata(world, 1, j5, 12, Blocks.brick_block, 0);
            this.setBlockAndMetadata(world, 0, j5, 13, Blocks.brick_block, 0);
        }
        for (final int j6 : new int[] { 1, 8 }) {
            this.setBlockAndMetadata(world, 0, j6, 12, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, 0, j6 + 1, 12, (Block)Blocks.fire, 0);
        }
        this.setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 3, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 3, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -4, 3, 10, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 3, 10, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 3, 12, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, 12, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 4, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -2, 3, 4, LOTRMod.chandelier, 3);
        this.setBlockAndMetadata(world, 2, 4, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 2, 3, 4, LOTRMod.chandelier, 3);
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 7, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i3, 3, 7, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 1, 7, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 1, 7, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 1, 7, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 3, 1, 7, super.fenceGateBlock, 0);
        this.setBlockAndMetadata(world, -1, 1, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 1, 11, Blocks.brick_stairs, 2);
        this.setBlockAndMetadata(world, 1, 1, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -1, 2, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 2, 11, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 1, 2, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -1, 3, 11, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 1, 3, 11, Blocks.brick_block, 0);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = 10; k3 <= 12; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, Blocks.brick_block, 0);
            }
        }
        this.setBlockAndMetadata(world, -2, 1, 1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 1, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -4, 1, 1, super.plankBlock, super.plankMeta);
        for (int k4 = 2; k4 <= 5; ++k4) {
            this.setBlockAndMetadata(world, -4, 1, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        }
        this.setBlockAndMetadata(world, -4, 1, 6, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 6, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 1, 6, super.plankBlock, super.plankMeta);
        for (int k4 = 1; k4 <= 6; ++k4) {
            this.placeRandomCake(world, random, -4, 2, k4);
        }
        for (int i3 = -3; i3 <= -2; ++i3) {
            this.placeRandomCake(world, random, i3, 2, 1);
            this.placeRandomCake(world, random, i3, 2, 6);
        }
        this.setBlockAndMetadata(world, 2, 1, 3, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 2, 1, 4, super.plankStairBlock, 6);
        this.placeRandomCake(world, random, 2, 2, 3);
        this.placeRandomCake(world, random, 2, 2, 4);
        this.setBlockAndMetadata(world, 4, 1, 1, super.plankBlock, super.plankMeta);
        for (int k4 = 2; k4 <= 5; ++k4) {
            this.setBlockAndMetadata(world, 4, 1, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        }
        this.setBlockAndMetadata(world, 4, 1, 6, super.plankBlock, super.plankMeta);
        for (int k4 = 1; k4 <= 6; ++k4) {
            this.placeRandomCake(world, random, 4, 2, k4);
        }
        for (int i3 = -4; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 8, super.plankBlock, super.plankMeta);
            for (int k3 = 9; k3 <= 11; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
            this.setBlockAndMetadata(world, i3, 1, 12, super.plankBlock, super.plankMeta);
            for (int k3 = 8; k3 <= 12; ++k3) {
                this.placeRandomCake(world, random, i3, 2, k3);
            }
        }
        this.setBlockAndMetadata(world, 4, 1, 8, super.plankBlock, super.plankMeta);
        this.placeRandomCake(world, random, 4, 2, 8);
        this.setBlockAndMetadata(world, 4, 1, 9, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 4, 1, 10, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 4, 1, 11, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 4, 1, 12, super.plankBlock, super.plankMeta);
        this.placeRandomCake(world, random, 4, 2, 12);
        this.setBlockAndMetadata(world, 3, 1, 12, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeRandomCake(world, random, 3, 2, 12);
        this.setBlockAndMetadata(world, 2, 1, 12, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 2, 2, 12, 2, LOTRFoods.DALE_DRINK);
        this.spawnItemFrame(world, 5, 3, 9, 3, new ItemStack(Items.clock));
        final LOTREntityDaleBaker baker = new LOTREntityDaleBaker(world);
        this.spawnNPCAndSetHome(baker, world, 0, 1, 8, 8);
        this.setBlockAndMetadata(world, 0, 5, -3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 6, -3, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 6, -4, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 5, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -4, super.plankBlock, super.plankMeta);
        final String[] bakeryName = LOTRNames.getDaleBakeryName(random, baker.getNPCName());
        baker.setSpecificLocationName(bakeryName[0] + " " + bakeryName[1]);
        this.setBlockAndMetadata(world, -1, 4, -4, Blocks.wall_sign, 5);
        this.setBlockAndMetadata(world, 1, 4, -4, Blocks.wall_sign, 4);
        for (final int i9 : new int[] { -1, 1 }) {
            final TileEntity te = this.getTileEntity(world, i9, 4, -4);
            if (te instanceof TileEntitySign) {
                final TileEntitySign sign = (TileEntitySign)te;
                sign.field_145915_a[1] = bakeryName[0];
                sign.field_145915_a[2] = bakeryName[1];
            }
        }
        return true;
    }
    
    private void placeRandomCake(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            Block cakeBlock = null;
            if (random.nextBoolean()) {
                cakeBlock = LOTRMod.dalishPastry;
            }
            else {
                final int randomCake = random.nextInt(4);
                if (randomCake == 0) {
                    cakeBlock = Blocks.cake;
                }
                else if (randomCake == 1) {
                    cakeBlock = LOTRMod.appleCrumble;
                }
                else if (randomCake == 2) {
                    cakeBlock = LOTRMod.berryPie;
                }
                else if (randomCake == 3) {
                    cakeBlock = LOTRMod.marzipanBlock;
                }
            }
            this.setBlockAndMetadata(world, i, j, k, cakeBlock, 0);
        }
    }
}
