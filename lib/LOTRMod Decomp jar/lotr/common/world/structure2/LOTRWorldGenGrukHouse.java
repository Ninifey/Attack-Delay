// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import net.minecraft.block.Block;
import lotr.common.item.LOTRItemMug;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGrukHouse extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenGrukHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        if (super.restrictions) {
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -8; k2 <= 8; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -8; k2 <= 8; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, Blocks.cobblestone, 0);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                if (i3 == 5 && k3 == 8) {
                    for (int j3 = 1; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, LOTRMod.woodBeamV1, 1);
                    }
                }
                else if (i3 == 5 || k3 == 8) {
                    for (int j3 = 1; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, Blocks.planks, 1);
                    }
                }
                else {
                    for (int j3 = 1; j3 <= 10; ++j3) {
                        this.setAir(world, i2, j3, k2);
                    }
                }
            }
        }
        for (int k4 = -9; k4 <= 9; ++k4) {
            for (int l = 0; l <= 5; ++l) {
                this.setBlockAndMetadata(world, -6 + l, 5 + l, k4, LOTRMod.stairsReed, 1);
                this.setBlockAndMetadata(world, 6 - l, 5 + l, k4, LOTRMod.stairsReed, 0);
                this.setBlockAndMetadata(world, -6 + l, 4 + l, k4, LOTRMod.stairsReed, 4);
                this.setBlockAndMetadata(world, 6 - l, 4 + l, k4, LOTRMod.stairsReed, 5);
            }
            this.setBlockAndMetadata(world, 0, 10, k4, LOTRMod.thatch, 1);
            this.setBlockAndMetadata(world, 0, 11, k4, LOTRMod.slabSingleThatch, 1);
        }
        for (int m = 0; m <= 5; ++m) {
            for (int i4 = -5 + m; i4 <= 5 - m; ++i4) {
                this.setBlockAndMetadata(world, i4, 5 + m, -8, Blocks.planks, 1);
                this.setBlockAndMetadata(world, i4, 5 + m, 8, Blocks.planks, 1);
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            this.setBlockAndMetadata(world, i2, 5, -8, LOTRMod.woodBeamV1, 5);
            this.setBlockAndMetadata(world, i2, 5, 8, LOTRMod.woodBeamV1, 5);
            this.setBlockAndMetadata(world, i2, 5, -7, Blocks.fence, 0);
            this.setBlockAndMetadata(world, i2, 5, 7, Blocks.fence, 0);
        }
        for (int k4 = -7; k4 <= 7; ++k4) {
            this.setBlockAndMetadata(world, -5, 5, k4, LOTRMod.woodBeamV1, 9);
            this.setBlockAndMetadata(world, 5, 5, k4, LOTRMod.woodBeamV1, 9);
            this.setBlockAndMetadata(world, -4, 5, k4, Blocks.fence, 0);
            this.setBlockAndMetadata(world, 4, 5, k4, Blocks.fence, 0);
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            final int i5 = Math.abs(i2);
            if (i5 == 2 || i5 == 3) {
                this.setBlockAndMetadata(world, i2, 2, -8, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, i2, 3, -8, LOTRMod.reedBars, 0);
            }
        }
        for (int k4 = -7; k4 <= 7; ++k4) {
            final int k5 = Math.abs(k4);
            if (k5 == 0 || k5 == 1 || k5 == 5 || k5 == 6) {
                this.setBlockAndMetadata(world, -5, 2, k4, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, -5, 3, k4, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, 5, 2, k4, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, 5, 3, k4, LOTRMod.reedBars, 0);
            }
            else if (k5 == 3) {
                for (int j2 = 0; j2 <= 4; ++j2) {
                    this.setBlockAndMetadata(world, -5, j2, k4, LOTRMod.woodBeamV1, 1);
                    this.setBlockAndMetadata(world, 5, j2, k4, LOTRMod.woodBeamV1, 1);
                }
                this.setBlockAndMetadata(world, -3, 1, k4, Blocks.fence, 1);
                this.setBlockAndMetadata(world, -3, 2, k4, Blocks.torch, 5);
                this.setBlockAndMetadata(world, 3, 1, k4, Blocks.fence, 1);
                this.setBlockAndMetadata(world, 3, 2, k4, Blocks.torch, 5);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, -8, Blocks.cobblestone, 0);
        this.setBlockAndMetadata(world, 0, 1, -8, LOTRMod.doorPine, 1);
        this.setBlockAndMetadata(world, 0, 2, -8, LOTRMod.doorPine, 8);
        this.setBlockAndMetadata(world, 0, 4, -9, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 3, -7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 1, 7, Blocks.fence, 1);
        this.setBlockAndMetadata(world, 0, 2, 7, Blocks.torch, 5);
        for (int k4 = -7; k4 <= 7; ++k4) {
            for (final int i6 : new int[] { -4, 4 }) {
                this.setBlockAndMetadata(world, i6, 1, k4, LOTRMod.planks2, 4);
                if (random.nextBoolean()) {
                    this.placeMug(world, random, i6, 2, k4, random.nextInt(4), this.getRandomDrink(random), new LOTRItemMug.Vessel[] { LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.HORN, LOTRItemMug.Vessel.HORN_GOLD });
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            if (i2 != 0) {
                this.placeBarrel(world, random, i2, 1, 7, 2, this.getRandomDrink(random));
                this.placeBarrel(world, random, i2, 2, 7, 2, this.getRandomDrink(random));
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, 7, Blocks.wool, 14);
            this.setBlockAndMetadata(world, i2, 5, 7, Blocks.wool, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -6; k2 <= -3; ++k2) {
                this.setBlockAndMetadata(world, i2, 1, k2, Blocks.carpet, 14);
            }
            for (int k2 = -2; k2 <= 1; ++k2) {
                this.setBlockAndMetadata(world, i2, 1, k2, Blocks.carpet, 0);
            }
        }
        for (final int i7 : new int[] { -8, 8 }) {
            for (int i8 = i7 - 2; i8 <= i7 + 2; ++i8) {
                for (int k6 = -20; k6 <= -16; ++k6) {
                    for (int j4 = 4; (j4 >= 0 || !this.isOpaque(world, i8, j4, k6)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i8, j4, k6, Blocks.cobblestone, 0);
                        this.setGrassToDirt(world, i8, j4 - 1, k6);
                    }
                    for (int j4 = 5; j4 <= 10; ++j4) {
                        this.setAir(world, i8, j4, k6);
                    }
                    if (Math.abs(i8 - i7) <= 1 && Math.abs(k6 + 18) <= 1) {
                        this.setBlockAndMetadata(world, i8, 4, k6, LOTRMod.hearth, 0);
                        this.setBlockAndMetadata(world, i8, 5, k6, (Block)Blocks.fire, 0);
                    }
                }
            }
        }
        for (int i2 = -12; i2 <= 12; ++i2) {
            for (int k2 = -20; k2 <= 0; ++k2) {
                final int dx = i2 - 0;
                final int dz = k2 + 8;
                final int dSq = dx * dx + dz * dz;
                if (dSq <= 144 && random.nextInt(6) != 0) {
                    final int j5 = this.getTopBlock(world, i2, k2) - 1;
                    final BiomeGenBase biome = this.getBiome(world, i2, k2);
                    final Block below = this.getBlock(world, i2, j5, k2);
                    if (below == biome.topBlock || below == biome.fillerBlock) {
                        final LOTRRoadType.RoadBlock roadblock = LOTRRoadType.PATH.getBlock(random, LOTRBiome.tundra, true, false);
                        this.setBlockAndMetadata(world, i2, j5, k2, roadblock.block, roadblock.meta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 3, -9, Blocks.wall_sign, 2);
        TileEntity te = this.getTileEntity(world, 0, 3, -9);
        if (te instanceof TileEntitySign) {
            final TileEntitySign sign = (TileEntitySign)te;
            sign.field_145915_a[1] = "Kvas";
            sign.field_145915_a[2] = "chlebov\u00c3½";
        }
        this.setBlockAndMetadata(world, 0, 3, 7, Blocks.wall_sign, 2);
        te = this.getTileEntity(world, 0, 3, 7);
        if (te instanceof TileEntitySign) {
            final TileEntitySign sign = (TileEntitySign)te;
            sign.field_145915_a[1] = ":^)";
        }
        this.setBlockAndMetadata(world, 0, 8, -7, Blocks.wall_sign, 3);
        te = this.getTileEntity(world, 0, 8, -7);
        if (te instanceof TileEntitySign) {
            final TileEntitySign sign = (TileEntitySign)te;
            sign.field_145915_a[1] = "Textures?";
        }
        this.spawnItemFrame(world, -1, 7, -8, 0, new ItemStack(LOTRMod.rollingPin));
        this.spawnItemFrame(world, 1, 7, -8, 0, new ItemStack(Items.book));
        final EntityOcelot bazyl = new EntityOcelot(world);
        bazyl.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        bazyl.setHealth(bazyl.getMaxHealth());
        bazyl.setTamed(true);
        bazyl.func_152115_b("6c94c61a-aebb-4b77-9699-4d5236d0e78a");
        bazyl.setTameSkin(1);
        bazyl.setCustomNameTag("Bazyl");
        this.spawnNPCAndSetHome((EntityCreature)bazyl, world, -1, 1, 0, 16);
        final EntityWolf wiktor = new EntityWolf(world);
        wiktor.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        wiktor.setHealth(wiktor.getMaxHealth());
        wiktor.setTamed(true);
        wiktor.func_152115_b("6c94c61a-aebb-4b77-9699-4d5236d0e78a");
        wiktor.setCustomNameTag("Wiktor");
        this.spawnNPCAndSetHome((EntityCreature)wiktor, world, 1, 1, 0, 16);
        return true;
    }
    
    private ItemStack getRandomDrink(final Random random) {
        if (random.nextBoolean()) {
            return new ItemStack(LOTRMod.mugPlumKvass);
        }
        return new ItemStack(LOTRMod.mugVodka);
    }
    
    public static boolean generatesAt(final World world, final int i, final int k) {
        return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 989, 528);
    }
}
