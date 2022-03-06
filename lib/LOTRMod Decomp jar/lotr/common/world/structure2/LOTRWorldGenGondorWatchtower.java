// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import lotr.common.world.structure.LOTRChestContents;
import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWatchtower extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorWatchtower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 != 3 || k4 != 3) {
                    for (int j2 = 0; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if ((i4 == 3 && k4 == 2) || (k4 == 3 && i4 == 2)) {
                        for (int j2 = 1; j2 <= 9; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                        }
                    }
                    else if (i4 == 3 || k4 == 3) {
                        for (int j2 = 1; j2 <= 9; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickBlock, super.brickMeta);
                        for (int j2 = 1; j2 <= 5; ++j2) {
                            this.setAir(world, i3, j2, k3);
                        }
                        this.setBlockAndMetadata(world, i3, 6, k3, super.plankBlock, super.plankMeta);
                        for (int j2 = 7; j2 <= 9; ++j2) {
                            this.setAir(world, i3, j2, k3);
                        }
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.brickBlock, super.brickMeta);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 10, -4, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 10, 4, super.brickStairBlock, 7);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -4, 10, k5, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 10, k5, super.brickStairBlock, 4);
        }
        this.setBlockAndMetadata(world, -3, 10, -3, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 10, -3, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 4, 10, -3, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 10, -3, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 10, 3, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 10, 3, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 4, 10, 3, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 10, 3, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 0, -3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 1, -3, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -3, super.doorBlock, 8);
        for (int j3 = 1; j3 <= 2; ++j3) {
            this.setBlockAndMetadata(world, -1, j3, -3, LOTRMod.brick, 5);
            this.setBlockAndMetadata(world, 1, j3, -3, LOTRMod.brick, 5);
        }
        this.setBlockAndMetadata(world, -1, 3, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 3, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 6, -3, LOTRMod.brick, 5);
        this.setBlockAndMetadata(world, 0, 6, 3, LOTRMod.brick, 5);
        this.setBlockAndMetadata(world, -3, 6, 0, LOTRMod.brick, 5);
        this.setBlockAndMetadata(world, 3, 6, 0, LOTRMod.brick, 5);
        this.placeWallBanner(world, 0, 5, -3, super.bannerType, 2);
        for (int j3 = 1; j3 <= 9; ++j3) {
            this.setBlockAndMetadata(world, 0, j3, 2, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 10, 2, Blocks.trapdoor, 9);
        for (int k5 = -2; k5 <= 2; ++k5) {
            if (IntMath.mod(k5, 2) == 0) {
                this.placeChest(world, random, -2, 1, k5, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
                this.placeChest(world, random, 2, 1, k5, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
            }
            else {
                this.setBlockAndMetadata(world, -1, 1, k5, super.bedBlock, 3);
                this.setBlockAndMetadata(world, -2, 1, k5, super.bedBlock, 11);
                this.setBlockAndMetadata(world, 1, 1, k5, super.bedBlock, 1);
                this.setBlockAndMetadata(world, 2, 1, k5, super.bedBlock, 9);
            }
        }
        this.setBlockAndMetadata(world, -2, 3, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 3, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 5, 0, LOTRMod.chandelier, 1);
        this.placeChest(world, random, -2, 7, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        this.setBlockAndMetadata(world, -2, 7, 0, LOTRMod.armorStand, 3);
        this.setBlockAndMetadata(world, -2, 8, 0, LOTRMod.armorStand, 7);
        this.setBlockAndMetadata(world, -2, 7, 2, Blocks.anvil, 0);
        this.spawnItemFrame(world, -3, 8, -1, 1, this.getGondorFramedItem(random));
        this.spawnItemFrame(world, -3, 8, 1, 1, this.getGondorFramedItem(random));
        this.setBlockAndMetadata(world, 2, 7, -2, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 2, 7, -1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 7, 0, (Block)Blocks.stone_slab, 8);
        this.setBlockAndMetadata(world, 2, 7, 1, (Block)Blocks.stone_slab, 8);
        this.setBlockAndMetadata(world, 2, 7, 2, (Block)Blocks.stone_slab, 8);
        this.placeMug(world, random, 2, 8, 0, 1, LOTRFoods.GONDOR_DRINK);
        this.placePlateWithCertainty(world, random, 2, 8, 1, super.plateBlock, LOTRFoods.GONDOR);
        this.placeBarrel(world, random, 2, 8, 2, 5, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 0, 9, 0, LOTRMod.chandelier, 1);
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 == 4 && k4 == 4) {
                    this.setBlockAndMetadata(world, i3, 11, k3, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, 12, k3, super.brickBlock, super.brickMeta);
                }
                else if (i4 == 4 || k4 == 4) {
                    if (IntMath.mod(i3 + k3, 2) == 1) {
                        this.setBlockAndMetadata(world, i3, 11, k3, super.brickWallBlock, super.brickWallMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 11, k3, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i3, 12, k3, super.brickSlabBlock, super.brickSlabMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 11, 0, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, 0, 12, 0, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, 0, 13, 0, LOTRMod.brick, 5);
        this.placeBanner(world, 0, 14, 0, super.bannerType, 2);
        this.setBlockAndMetadata(world, 0, 11, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 11, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 11, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 11, 0, Blocks.torch, 1);
        final Class[] levyClasses = super.strFief.getLevyClasses();
        LOTREntityGondorMan soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(levyClasses[0], world);
        soldier.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(soldier, world, 0, 7, 0, 16);
        soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(levyClasses[0], world);
        soldier.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        for (int levymen = 1 + random.nextInt(3), l = 0; l < levymen; ++l) {
            Class entityClass = levyClasses[0];
            if (random.nextInt(3) == 0) {
                entityClass = levyClasses[1];
            }
            final LOTREntityGondorMan levyman = (LOTREntityGondorMan)LOTREntities.createEntityByClass(entityClass, world);
            levyman.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(levyman, world, 0, 11, 1, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(levyClasses[0], levyClasses[1]);
        respawner.setCheckRanges(16, -12, 8, 6);
        respawner.setSpawnRanges(3, -6, 6, 16);
        this.placeNPCRespawner(respawner, world, 0, 6, 0);
        return true;
    }
}
