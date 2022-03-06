// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import lotr.common.tileentity.LOTRTileEntityDwarvenForge;
import net.minecraft.entity.EntityCreature;
import java.util.Random;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.entity.npc.LOTREntityDwarfSmith;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

public class LOTRWorldGenDwarfSmithy extends LOTRWorldGenStructureBase2
{
    protected Block baseBrickBlock;
    protected int baseBrickMeta;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block carvedBrickBlock;
    protected int carvedBrickMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block gateBlock;
    protected Block tableBlock;
    protected Block barsBlock;
    
    public LOTRWorldGenDwarfSmithy(final boolean flag) {
        super(flag);
        this.baseBrickBlock = Blocks.stonebrick;
        this.baseBrickMeta = 0;
        this.brickBlock = LOTRMod.brick;
        this.brickMeta = 6;
        this.brickSlabBlock = LOTRMod.slabSingle;
        this.brickSlabMeta = 7;
        this.brickStairBlock = LOTRMod.stairsDwarvenBrick;
        this.carvedBrickBlock = LOTRMod.brick2;
        this.carvedBrickMeta = 12;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 0;
        this.tableBlock = LOTRMod.dwarvenTable;
        this.barsBlock = LOTRMod.dwarfBars;
    }
    
    protected LOTREntityDwarf createSmith(final World world) {
        return new LOTREntityDwarfSmith(world);
    }
    
    protected LOTRChestContents getChestContents() {
        return LOTRChestContents.DWARF_SMITHY;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        final int randomWood = random.nextInt(4);
        if (randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.gateBlock = Blocks.fence_gate;
        }
        else if (randomWood == 1) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 13;
            this.gateBlock = LOTRMod.fenceGateLarch;
        }
        else if (randomWood == 2) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.gateBlock = LOTRMod.fenceGatePine;
        }
        else if (randomWood == 3) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 3;
            this.gateBlock = LOTRMod.fenceGateFir;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
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
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 + k4 <= 6) {
                    this.layFoundation(world, i3, k3);
                    for (int j2 = 1; j2 <= 5; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    if (i4 == 4 || k4 == 4) {
                        this.setBlockAndMetadata(world, i3, 1, k3, this.baseBrickBlock, this.baseBrickMeta);
                        this.setBlockAndMetadata(world, i3, 2, k3, this.plankBlock, this.plankMeta);
                        this.setBlockAndMetadata(world, i3, 3, k3, this.brickBlock, this.brickMeta);
                    }
                    if (i4 == 3 && k4 == 3) {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, this.pillarBlock, this.pillarMeta);
                        }
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -3, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 3, 3, this.brickStairBlock, 6);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -3, 3, k5, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 3, 3, k5, this.brickStairBlock, 5);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, this.brickBlock, this.brickMeta);
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -4, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 4, 4, this.brickStairBlock, 3);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -4, 4, k5, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 4, 4, k5, this.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -4, 4, 2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 4, 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 4, 3, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -2, 4, 3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 4, 4, 2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 3, 4, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 4, 3, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 2, 4, 3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -4, 4, -2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 4, -2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 4, -3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -2, 4, -3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 4, 4, -2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 3, 4, -2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 4, -3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 2, 4, -3, this.brickStairBlock, 0);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 2; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3 - 0);
                final int k4 = Math.abs(k3 - 3);
                if (i4 == 1 && k4 == 1) {
                    this.setBlockAndMetadata(world, i3, 5, k3, this.brickSlabBlock, this.brickSlabMeta);
                }
                else if (i4 == 1 || k4 == 1) {
                    this.setBlockAndMetadata(world, i3, 5, k3, this.brickBlock, this.brickMeta);
                }
                else if (i4 == 0 && k4 == 0) {
                    this.setAir(world, i3, 3, k3);
                    this.setAir(world, i3, 4, k3);
                }
            }
            this.setBlockAndMetadata(world, i3, 4, 4, this.brickBlock, this.brickMeta);
            for (int j3 = 1; j3 <= 2; ++j3) {
                this.setBlockAndMetadata(world, i3, j3, 4, this.brickBlock, this.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 6, 2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 6, 3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 6, 3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 6, 4, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 1, -4, this.gateBlock, 0);
        this.setAir(world, 0, 2, -4);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 1, -1, Blocks.anvil, 1);
        for (final int i5 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, i5, 1, -1, Blocks.anvil, 0);
            this.setBlockAndMetadata(world, i5, 1, 0, this.tableBlock, 0);
            this.setBlockAndMetadata(world, i5, 1, 2, Blocks.crafting_table, 0);
        }
        this.setBlockAndMetadata(world, -3, 1, -2, LOTRMod.unsmeltery, 4);
        this.setBlockAndMetadata(world, 3, 1, -2, LOTRMod.unsmeltery, 5);
        this.placeChest(world, random, -3, 1, 1, 4, this.getChestContents());
        this.placeChest(world, random, 3, 1, 1, 5, this.getChestContents());
        this.placeDwarfForge(world, random, 0, 1, 2, 2);
        this.placeDwarfForge(world, random, -1, 1, 3, 5);
        this.placeDwarfForge(world, random, 1, 1, 3, 4);
        for (final int i5 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, i5, 1, 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i5, 2, 2, this.carvedBrickBlock, this.carvedBrickMeta);
            this.setBlockAndMetadata(world, i5, 3, 2, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i5, 2, 3, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i5, 3, 3, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 2, 2, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 3, 2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 1, 3, Blocks.lava, 0);
        final LOTREntityDwarf smith = this.createSmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, 1, 0, 8);
        return true;
    }
    
    protected void layFoundation(final World world, final int i, final int k) {
        for (int j = 0; (j == 0 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
            this.setBlockAndMetadata(world, i, j, k, this.baseBrickBlock, this.baseBrickMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
    
    protected void placeDwarfForge(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.dwarvenForge, meta);
        final TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if (tileentity instanceof LOTRTileEntityDwarvenForge) {
            final LOTRTileEntityDwarvenForge forge = (LOTRTileEntityDwarvenForge)tileentity;
            final int fuelAmount = MathHelper.getRandomIntegerInRange(random, 0, 4);
            if (fuelAmount > 0) {
                final ItemStack fuel = new ItemStack(Items.coal, fuelAmount, 0);
                forge.setInventorySlotContents(forge.fuelSlot, fuel);
            }
        }
    }
}
