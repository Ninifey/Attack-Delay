// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.animal.LOTREntityLion;
import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTicketBooth extends LOTRWorldGenEasterlingStructureTown
{
    public LOTRWorldGenTicketBooth(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3, 3);
        this.setupRandomBlocks(random);
        final int woolType = 14;
        final Block woodBlock = Blocks.planks;
        final int woodMeta = 0;
        final Block stairBlock = Blocks.oak_stairs;
        final Block seatBlock = Blocks.oak_stairs;
        final Block fillerBlock = this.getBiome(world, 0, 0).fillerBlock;
        for (int k2 = -2; k2 <= 15; ++k2) {
            for (int i2 = -2; i2 <= 9; ++i2) {
                if (i2 < 5 || k2 > 1) {
                    this.setBlockAndMetadata(world, i2, 0, k2, Blocks.cobblestone, 0);
                    for (int j2 = -1; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, fillerBlock, 0);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                    for (int j2 = 1; j2 <= 3; ++j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, woodBlock, woodMeta);
                    }
                    if (k2 > 2) {
                        for (int j2 = 4; j2 <= 5; ++j2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, woodBlock, woodMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i2, 2, k2, Blocks.stonebrick, 0);
                }
            }
        }
        for (int k2 = 3; k2 <= 14; ++k2) {
            for (int i2 = -1; i2 <= 8; ++i2) {
                for (int j2 = 1; j2 <= 4; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                if (k2 > 9 || IntMath.mod(k2, 2) != 1) {
                    this.setBlockAndMetadata(world, i2, 0, k2, Blocks.wool, woolType);
                }
                if (k2 <= 9 && IntMath.mod(k2, 2) == 1 && i2 != 3 && i2 != 4) {
                    this.setBlockAndMetadata(world, i2, 1, k2, seatBlock, 3);
                }
                if (i2 == 3 || i2 == 4) {
                    this.setBlockAndMetadata(world, i2, 0, k2, Blocks.cobblestone, 0);
                }
            }
        }
        for (int j3 = 0; j3 <= 4; ++j3) {
            for (int i2 = 2; i2 <= 5; ++i2) {
                if (j3 >= 1 && j3 <= 3 && i2 >= 3 && i2 <= 4) {
                    this.setBlockAndMetadata(world, i2, j3, 14, Blocks.stained_hardened_clay, 15);
                }
                else {
                    this.setBlockAndMetadata(world, i2, j3, 14, Blocks.hardened_clay, 0);
                }
            }
        }
        for (int k2 = -2; k2 <= 2; ++k2) {
            for (int j4 = 1; j4 <= 2; ++j4) {
                this.setAir(world, 3, j4, k2);
            }
        }
        for (int k2 = -1; k2 <= 0; ++k2) {
            for (int j4 = 1; j4 <= 2; ++j4) {
                for (int i3 = -1; i3 <= 1; ++i3) {
                    this.setAir(world, i3, j4, k2);
                    if (k2 == -1 && j4 == 2) {
                        this.setAir(world, i3, j4, k2 - 1);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 2, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 1, -2, Blocks.fence, 0);
        this.setBlockAndMetadata(world, -1, 2, -2, Blocks.glass_pane, 0);
        this.setBlockAndMetadata(world, 1, 2, -2, Blocks.glass_pane, 0);
        for (int k2 = 4; k2 <= 14; ++k2) {
            this.setBlockAndMetadata(world, -1, 4, k2, stairBlock, 4);
            this.setBlockAndMetadata(world, 8, 4, k2, stairBlock, 5);
        }
        for (int i4 = 0; i4 <= 7; ++i4) {
            this.setBlockAndMetadata(world, i4, 4, 3, stairBlock, 7);
            if (i4 <= 1 || i4 >= 6) {
                this.setBlockAndMetadata(world, i4, 4, 14, stairBlock, 6);
            }
        }
        for (int j3 = 0; j3 <= 4; ++j3) {
            Block block = woodBlock;
            int meta = woodMeta;
            if (j3 == 2) {
                block = Blocks.glowstone;
                meta = 0;
            }
            this.setBlockAndMetadata(world, -1, j3, 3, block, meta);
            this.setBlockAndMetadata(world, -1, j3, 14, block, meta);
            this.setBlockAndMetadata(world, 8, j3, 3, block, meta);
            this.setBlockAndMetadata(world, 8, j3, 14, block, meta);
        }
        for (int i4 = -2; i4 <= 4; ++i4) {
            if (i4 == 3) {
                this.setBlockAndMetadata(world, i4, 3, -3, woodBlock, woodMeta);
            }
            else {
                this.setBlockAndMetadata(world, i4, 3, -3, stairBlock, 2);
            }
        }
        for (int k2 = -2; k2 <= 3; ++k2) {
            this.setBlockAndMetadata(world, -3, 3, k2, stairBlock, 1);
        }
        for (int k2 = -2; k2 <= 0; ++k2) {
            this.setBlockAndMetadata(world, 5, 3, k2, stairBlock, 0);
        }
        this.generateSupports(world, 5, 3, 1, stairBlock, 2, woodBlock, woodMeta);
        for (int i4 = 6; i4 <= 9; ++i4) {
            this.setBlockAndMetadata(world, i4, 3, 1, stairBlock, 2);
        }
        for (int i4 = -2; i4 <= 9; ++i4) {
            this.setBlockAndMetadata(world, i4, 5, 2, stairBlock, 2);
            this.setBlockAndMetadata(world, i4, 5, 16, stairBlock, 3);
        }
        for (int k2 = 3; k2 <= 15; ++k2) {
            this.setBlockAndMetadata(world, -3, 5, k2, stairBlock, 1);
            this.setBlockAndMetadata(world, 10, 5, k2, stairBlock, 0);
        }
        this.setBlockAndMetadata(world, 10, 3, 2, stairBlock, 0);
        this.setBlockAndMetadata(world, 10, 3, 3, stairBlock, 0);
        this.generateSupports(world, -3, 3, -3, stairBlock, 2, woodBlock, woodMeta);
        this.generateSupports(world, 5, 3, -3, stairBlock, 2, woodBlock, woodMeta);
        this.generateSupports(world, 10, 3, 1, stairBlock, 2, woodBlock, woodMeta);
        this.generateSupports(world, 10, 3, 4, stairBlock, 3, woodBlock, woodMeta);
        this.generateSupports(world, -3, 3, 4, stairBlock, 3, woodBlock, woodMeta);
        this.setBlockAndMetadata(world, -3, 5, 2, stairBlock, 2);
        this.setBlockAndMetadata(world, 10, 5, 2, stairBlock, 2);
        this.generateSupports(world, -3, 5, 16, stairBlock, 3, woodBlock, woodMeta);
        this.generateSupports(world, 10, 5, 16, stairBlock, 3, woodBlock, woodMeta);
        this.setBlockAndMetadata(world, 3, 1, -2, Blocks.wooden_door, 1);
        this.setBlockAndMetadata(world, 3, 2, -2, Blocks.wooden_door, 8);
        for (int k2 = 5; k2 <= 12; ++k2) {
            if (IntMath.mod(k2, 3) != 1) {
                this.setBlockAndMetadata(world, -1, 2, k2, Blocks.torch, 2);
                this.setBlockAndMetadata(world, 8, 2, k2, Blocks.torch, 1);
            }
        }
        for (int i4 = 1; i4 <= 6; ++i4) {
            if (i4 <= 1 || i4 >= 6) {
                this.setBlockAndMetadata(world, i4, 2, 14, Blocks.torch, 4);
            }
            if (i4 <= 2 || i4 >= 5) {
                this.setBlockAndMetadata(world, i4, 2, 3, Blocks.torch, 3);
            }
        }
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 4, 2, -3, Blocks.torch, 4);
        this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, new String[] { "---------------", "Now showing:", "The Lion King", "---------------" });
        final LOTREntityLion lion = new LOTREntityLion(world);
        lion.setCustomNameTag("Ticket Lion");
        lion.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        lion.setHealth(lion.getMaxHealth());
        this.spawnNPCAndSetHome((EntityCreature)lion, world, 0, 1, -1, 4);
        this.setBlockAndMetadata(world, 0, 1, 2, (Block)Blocks.chest, 3);
        final TileEntityChest chest = (TileEntityChest)this.getTileEntity(world, 0, 1, 2);
        if (chest != null) {
            for (int lootAmount = 2 + random.nextInt(4), l = 0; l < lootAmount; ++l) {
                chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getBasicLoot(random));
            }
        }
        this.setBlockAndMetadata(world, 0, 2, 2, Blocks.trapdoor, 1);
        this.placeSign(world, 3, 2, 13, Blocks.wall_sign, 2, new String[] { "", "Showings", "postponed", "" });
        this.placeSign(world, 4, 2, 13, Blocks.wall_sign, 2, new String[] { "", "until further", "notice.", "" });
        return true;
    }
    
    private ItemStack getBasicLoot(final Random random) {
        final int i = random.nextInt(11);
        switch (i) {
            default: {
                return new ItemStack(Items.stick, 2 + random.nextInt(4));
            }
            case 1: {
                return new ItemStack(Items.paper, 1 + random.nextInt(3));
            }
            case 2: {
                return new ItemStack(Items.book, 1 + random.nextInt(2));
            }
            case 3: {
                return new ItemStack(Items.bread, 3 + random.nextInt(2));
            }
            case 4: {
                return new ItemStack(Items.compass);
            }
            case 5: {
                return new ItemStack(Items.gold_nugget, 2 + random.nextInt(6));
            }
            case 6: {
                return new ItemStack(Items.apple, 1 + random.nextInt(3));
            }
            case 7: {
                return new ItemStack(Items.string, 2 + random.nextInt(2));
            }
            case 8: {
                return new ItemStack(Items.bowl, 1 + random.nextInt(4));
            }
            case 9: {
                return new ItemStack(Items.cookie, 1 + random.nextInt(3));
            }
            case 10: {
                return new ItemStack(Items.coal, 1 + random.nextInt(2));
            }
        }
    }
    
    private void generateSupports(final World world, final int i, final int j, final int k, final Block stairBlock, final int stairMeta, final Block woodBlock, final int woodMeta) {
        this.setBlockAndMetadata(world, i, j, k, stairBlock, stairMeta);
        for (int j2 = -1; !this.isOpaque(world, i, j + j2, k) && this.getY(j + j2) >= 0; --j2) {
            Block block = Blocks.fence;
            int meta = 0;
            final Block below = world.getBlock(i, j + j2, k);
            if (below.getMaterial().isLiquid()) {
                block = Blocks.planks;
                meta = woodMeta;
            }
            this.setBlockAndMetadata(world, i, j + j2, k, block, meta);
        }
    }
    
    public static boolean generatesAt(final World world, final int i, final int k) {
        return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 1583, 2527);
    }
}
