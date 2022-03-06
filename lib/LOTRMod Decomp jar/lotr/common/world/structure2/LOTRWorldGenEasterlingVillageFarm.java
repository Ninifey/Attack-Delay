// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityAnimal;
import lotr.common.entity.npc.LOTREntityEasterlingFarmer;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterlingFarmhand;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenEasterlingVillageFarm extends LOTRWorldGenEasterlingStructure
{
    public LOTRWorldGenEasterlingVillageFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
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
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                }
            }
        }
        if (super.restrictions) {
            int highestHeight = 0;
            for (int i3 = -6; i3 <= 6; ++i3) {
                for (int k3 = -6; k3 <= 6; ++k3) {
                    final int i4 = Math.abs(i3);
                    final int k4 = Math.abs(k3);
                    if ((i4 == 6 && k4 == 0) || (k4 == 6 && i4 == 0)) {
                        final int j3 = this.getTopBlock(world, i3, k3) - 1;
                        if (this.isSurface(world, i3, j3, k3) && j3 > highestHeight) {
                            highestHeight = j3;
                        }
                    }
                }
            }
            super.originY = this.getY(highestHeight);
        }
        for (int i5 = -5; i5 <= 5; ++i5) {
            for (int k5 = -5; k5 <= 5; ++k5) {
                final int i6 = Math.abs(i5);
                final int k6 = Math.abs(k5);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i5, j2, k5)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i5, j2, k5, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i5, j2 - 1, k5);
                }
                for (int j2 = 1; j2 <= 10; ++j2) {
                    this.setAir(world, i5, j2, k5);
                }
                if (i6 == 5 && k6 == 5) {
                    this.setBlockAndMetadata(world, i5, 1, k5, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i5, 2, k5, super.brickSlabBlock, super.brickSlabMeta);
                }
                else if (i6 == 5 || k6 == 5) {
                    this.setBlockAndMetadata(world, i5, 1, k5, super.fenceBlock, super.fenceMeta);
                    if (i6 == 2 || k6 == 2) {
                        this.setBlockAndMetadata(world, i5, 1, k5, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i5, 2, k5, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i5, 3, k5, super.brickWallBlock, super.brickWallMeta);
                        this.setBlockAndMetadata(world, i5, 4, k5, Blocks.torch, 5);
                    }
                    if (i6 == 0 || k6 == 0) {
                        this.setAir(world, i5, 1, k5);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i5, 0, k5, (Block)Blocks.grass, 0);
                }
            }
        }
        return true;
    }
    
    public static class Crops extends LOTRWorldGenEasterlingVillageFarm
    {
        public Crops(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
                return false;
            }
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int i3 = Math.abs(i2);
                    final int k3 = Math.abs(k2);
                    if (i3 <= 2 && k3 <= 2) {
                        if (i3 == 0 && k3 == 0) {
                            this.setBlockAndMetadata(world, i2, 0, k2, Blocks.water, 0);
                            this.setBlockAndMetadata(world, i2, 1, k2, super.brickBlock, super.brickMeta);
                            this.setBlockAndMetadata(world, i2, 2, k2, Blocks.hay_block, 0);
                            this.setBlockAndMetadata(world, i2, 3, k2, super.fenceBlock, super.fenceMeta);
                            this.setBlockAndMetadata(world, i2, 4, k2, Blocks.hay_block, 0);
                            this.setBlockAndMetadata(world, i2, 5, k2, Blocks.pumpkin, 2);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, 0, k2, Blocks.farmland, 7);
                            this.setBlockAndMetadata(world, i2, 1, k2, super.cropBlock, super.cropMeta);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.dirtPath, 0);
                    }
                }
            }
            this.setBlockAndMetadata(world, 0, 1, -5, super.fenceGateBlock, 0);
            this.setBlockAndMetadata(world, 0, 1, 5, super.fenceGateBlock, 2);
            this.setBlockAndMetadata(world, -5, 1, 0, super.fenceGateBlock, 1);
            this.setBlockAndMetadata(world, 5, 1, 0, super.fenceGateBlock, 3);
            for (int farmhands = 1 + random.nextInt(2), l = 0; l < farmhands; ++l) {
                final LOTREntityEasterlingFarmhand farmhand = new LOTREntityEasterlingFarmhand(world);
                this.spawnNPCAndSetHome(farmhand, world, 0, 1, -1, 8);
                farmhand.seedsItem = super.seedItem;
            }
            if (random.nextInt(3) == 0) {
                final LOTREntityEasterlingFarmer farmer = new LOTREntityEasterlingFarmer(world);
                this.spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
            }
            return true;
        }
    }
    
    public static class Animals extends LOTRWorldGenEasterlingVillageFarm
    {
        public Animals(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
                return false;
            }
            for (int i2 = -1; i2 <= 1; ++i2) {
                this.setBlockAndMetadata(world, i2, 1, -5, super.fenceGateBlock, 0);
                this.setBlockAndMetadata(world, i2, 1, 5, super.fenceGateBlock, 2);
            }
            for (int k2 = -1; k2 <= 1; ++k2) {
                this.setBlockAndMetadata(world, -5, 1, k2, super.fenceGateBlock, 1);
                this.setBlockAndMetadata(world, 5, 1, k2, super.fenceGateBlock, 3);
            }
            for (int i2 = -1; i2 <= 1; ++i2) {
                for (int k3 = -1; k3 <= 1; ++k3) {
                    if (random.nextInt(3) == 0) {
                        final int j2 = 1;
                        int j3 = 1;
                        if (i2 == 0 && k3 == 0 && random.nextBoolean()) {
                            ++j3;
                        }
                        for (int j4 = j2; j4 <= j3; ++j4) {
                            this.setBlockAndMetadata(world, i2, j4, k3, Blocks.hay_block, 0);
                        }
                    }
                }
            }
            for (int animals = 4 + random.nextInt(5), l = 0; l < animals; ++l) {
                final EntityCreature animal = (EntityCreature)getRandomAnimal(world, random);
                final int i3 = 3 * (random.nextBoolean() ? 1 : -1);
                final int k4 = 3 * (random.nextBoolean() ? 1 : -1);
                this.spawnNPCAndSetHome(animal, world, i3, 1, k4, 0);
                animal.detachHome();
            }
            return true;
        }
        
        private static EntityAnimal getRandomAnimal(final World world, final Random random) {
            final int animal = random.nextInt(4);
            if (animal == 0) {
                return (EntityAnimal)new EntityCow(world);
            }
            if (animal == 1) {
                return (EntityAnimal)new EntityPig(world);
            }
            if (animal == 2) {
                return (EntityAnimal)new EntitySheep(world);
            }
            if (animal == 3) {
                return (EntityAnimal)new EntityChicken(world);
            }
            return null;
        }
    }
    
    public static class Tree extends LOTRWorldGenEasterlingVillageFarm
    {
        public Tree(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
                return false;
            }
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
                    final int i3 = Math.abs(i2);
                    final int k3 = Math.abs(k2);
                    if (i3 == 5 && k3 == 5) {
                        this.setBlockAndMetadata(world, i2, 2, k2, super.brickWallBlock, super.brickWallMeta);
                        this.setBlockAndMetadata(world, i2, 3, k2, LOTRMod.leaves6, 6);
                    }
                }
            }
            for (int l = 0; l < 16; ++l) {
                final LOTRTreeType tree = getRandomTree(random);
                final WorldGenerator treeGen = (WorldGenerator)tree.create(super.notifyChanges, random);
                if (treeGen != null) {
                    final int i4 = 0;
                    final int j2 = 1;
                    final int k4 = 0;
                    if (treeGen.generate(world, random, this.getX(i4, k4), this.getY(j2), this.getZ(i4, k4))) {
                        break;
                    }
                }
            }
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int j3 = 1;
                    if (!this.isOpaque(world, i2, j3, k2) && random.nextInt(8) == 0) {
                        this.plantFlower(world, random, i2, j3, k2);
                    }
                }
            }
            return true;
        }
        
        public static LOTRTreeType getRandomTree(final Random random) {
            final List<LOTRTreeType> treeList = new ArrayList<LOTRTreeType>();
            treeList.add(LOTRTreeType.BEECH);
            treeList.add(LOTRTreeType.BEECH_LARGE);
            treeList.add(LOTRTreeType.MAPLE);
            treeList.add(LOTRTreeType.MAPLE_LARGE);
            treeList.add(LOTRTreeType.CYPRESS);
            treeList.add(LOTRTreeType.ALMOND);
            treeList.add(LOTRTreeType.OLIVE);
            treeList.add(LOTRTreeType.DATE_PALM);
            treeList.add(LOTRTreeType.POMEGRANATE);
            return treeList.get(random.nextInt(treeList.size()));
        }
    }
}
