// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenHobbitWindmill extends LOTRWorldGenStructureBase2
{
    private Block plankBlock;
    private int plankMeta;
    private Block woodBlock;
    private int woodMeta;
    private Block doorBlock;
    
    public LOTRWorldGenHobbitWindmill(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        if (random.nextBoolean()) {
            this.woodBlock = Blocks.log;
            this.woodMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.doorBlock = Blocks.wooden_door;
        }
        else {
            this.woodBlock = LOTRMod.wood;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 0;
            this.doorBlock = LOTRMod.doorShirePine;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 < 3 || k3 <= 3) {
                    if (k3 < 3 || i3 <= 3) {
                        Block fillBlock = Blocks.air;
                        int fillMeta = 0;
                        if (i3 == 3 && k3 == 3) {
                            fillBlock = this.plankBlock;
                            fillMeta = this.plankMeta;
                        }
                        else if ((i3 == 4 && k3 == 2) || (i3 == 2 && k3 == 4)) {
                            fillBlock = this.woodBlock;
                            fillMeta = this.woodMeta;
                        }
                        else if (i3 == 4 || k3 == 4) {
                            fillBlock = this.plankBlock;
                            fillMeta = this.plankMeta;
                        }
                        else {
                            fillBlock = Blocks.air;
                        }
                        for (int j3 = 4; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                            if (fillBlock == Blocks.air) {
                                if (j3 == 4 || j3 <= 0) {
                                    this.setBlockAndMetadata(world, i2, j3, k2, this.plankBlock, this.plankMeta);
                                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                                }
                                else {
                                    this.setAir(world, i2, j3, k2);
                                }
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, j3, k2, fillBlock, fillMeta);
                                this.setGrassToDirt(world, i2, j3 - 1, k2);
                            }
                        }
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 != 3 || k3 != 3) {
                    Block fillBlock = Blocks.air;
                    int fillMeta = 0;
                    if (i3 == 3 && k3 == 3) {
                        fillBlock = this.plankBlock;
                        fillMeta = this.plankMeta;
                    }
                    else if ((i3 == 3 && k3 == 2) || (i3 == 2 && k3 == 3)) {
                        fillBlock = this.woodBlock;
                        fillMeta = this.woodMeta;
                    }
                    else if (i3 == 3 || k3 == 3) {
                        fillBlock = this.plankBlock;
                        fillMeta = this.plankMeta;
                    }
                    else {
                        fillBlock = Blocks.air;
                    }
                    for (int j3 = 5; j3 <= 8; ++j3) {
                        if (fillBlock == Blocks.air) {
                            this.setAir(world, i2, j3, k2);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, j3, k2, fillBlock, fillMeta);
                        }
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j4 = 9; j4 <= 12; ++j4) {
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, j4, k2, this.woodBlock, this.woodMeta);
                    }
                    else if (i3 == 2 || k3 == 2) {
                        this.setBlockAndMetadata(world, i2, j4, k2, this.plankBlock, this.plankMeta);
                    }
                    else {
                        this.setAir(world, i2, j4, k2);
                    }
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int j2 = 11; j2 <= 12; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, this.plankBlock, this.plankMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.chandelier, 2);
        final int originX = 0;
        final int originY = 13;
        final int originZ = 0;
        for (int radius = 4, i4 = originX - radius; i4 <= originX + radius; ++i4) {
            for (int j5 = originY - radius; j5 <= originY + radius; ++j5) {
                for (int k4 = originZ - radius; k4 <= originZ + radius; ++k4) {
                    final int i5 = i4 - originX;
                    final int j6 = j5 - originY;
                    final int k5 = k4 - originZ;
                    final int dist = i5 * i5 + j6 * j6 + k5 * k5;
                    if (dist < radius * radius && j5 >= originY) {
                        this.setBlockAndMetadata(world, i4, j5, k4, LOTRMod.clayTileDyed, 13);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -3, 6, 0, LOTRMod.glassPane, 0);
        this.setBlockAndMetadata(world, 3, 6, 0, LOTRMod.glassPane, 0);
        this.setBlockAndMetadata(world, 0, 6, -3, LOTRMod.glassPane, 0);
        this.setBlockAndMetadata(world, 0, 6, 3, LOTRMod.glassPane, 0);
        this.placeFenceTorch(world, -2, 2, -3);
        this.placeFenceTorch(world, -2, 2, 3);
        this.placeFenceTorch(world, 2, 2, -3);
        this.placeFenceTorch(world, 2, 2, 3);
        this.placeFenceTorch(world, -3, 2, -2);
        this.placeFenceTorch(world, 3, 2, -2);
        this.placeFenceTorch(world, -3, 2, 2);
        this.placeFenceTorch(world, 3, 2, 2);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        this.setBlockAndMetadata(world, -3, 1, -1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, 0, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -2, 1, 0, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 2, 1, Blocks.hay_block, 0);
        for (int j4 = 1; j4 <= 4; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 0, j4, 1, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 1, 5, -2, Blocks.bed, 1);
        this.setBlockAndMetadata(world, 2, 5, -2, Blocks.bed, 9);
        this.setBlockAndMetadata(world, -2, 5, -2, Blocks.bookshelf, 0);
        this.setBlockAndMetadata(world, -1, 5, -2, Blocks.bookshelf, 0);
        this.setBlockAndMetadata(world, -2, 6, -2, Blocks.bookshelf, 0);
        this.setBlockAndMetadata(world, -1, 6, -2, Blocks.bookshelf, 0);
        this.setBlockAndMetadata(world, -2, 5, -1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -2, 5, 1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 5, 2, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 6, 1, LOTRWorldGenHobbitStructure.getRandomCakeBlock(random), 0);
        this.placeBarrel(world, random, -2, 6, 2, 4, LOTRFoods.HOBBIT_DRINK);
        this.setBlockAndMetadata(world, 2, 5, 1, LOTRMod.hobbitOven, 5);
        this.setBlockAndMetadata(world, 2, 5, 2, LOTRMod.hobbitOven, 5);
        this.placeChest(world, random, -2, 5, 0, 4, LOTRChestContents.HOBBIT_HOLE_STUDY);
        this.placeChest(world, random, 2, 5, 0, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
        if (random.nextInt(20) == 0) {
            final TileEntity te = this.getTileEntity(world, 2, 5, 0);
            if (te instanceof IInventory) {
                final IInventory chest = (IInventory)te;
                final ItemStack hooch = new ItemStack(LOTRMod.mugLemonLiqueur);
                LOTRItemMug.setStrengthMeta(hooch, 1);
                LOTRItemMug.setVessel(hooch, LOTRItemMug.Vessel.MUG, true);
                hooch.setStackDisplayName("Bad Windmill Hooch");
                final NBTTagList loreTags = hooch.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
                loreTags.appendTag((NBTBase)new NBTTagString("Really nothing compared to the Spoons Hooch."));
                hooch.getTagCompound().getCompoundTag("display").setTag("Lore", (NBTBase)loreTags);
                final int slot = random.nextInt(chest.getSizeInventory());
                chest.setInventorySlotContents(slot, hooch);
            }
        }
        this.setBlockAndMetadata(world, 0, 10, -3, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 10, -4, Blocks.wool, 15);
        for (int j4 = 7; j4 <= 13; ++j4) {
            for (int i6 = -3; i6 <= 3; ++i6) {
                final int j7 = Math.abs(j4 - 10);
                final int i5 = Math.abs(i6);
                if (j7 == i5 && j7 != 0) {
                    this.setBlockAndMetadata(world, i6, j4, -4, Blocks.wool, 0);
                }
            }
        }
        final LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
        this.spawnNPCAndSetHome(hobbit, world, 0, 1, 0, 8);
        return true;
    }
    
    private void placeFenceTorch(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i, j, k, Blocks.fence, 0);
        this.setBlockAndMetadata(world, i, j + 1, k, Blocks.torch, 5);
    }
}
