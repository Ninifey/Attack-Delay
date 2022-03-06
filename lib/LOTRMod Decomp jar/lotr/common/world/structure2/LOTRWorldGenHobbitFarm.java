// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHobbit;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import net.minecraft.entity.EnumCreatureType;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.npc.LOTREntityHobbitFarmhand;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHobbitFarmer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Items;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.block.Block;

public class LOTRWorldGenHobbitFarm extends LOTRWorldGenStructureBase2
{
    private Block wood1Block;
    private int wood1Meta;
    private Block wood1SlabBlock;
    private int wood1SlabMeta;
    private Block wood1Stair;
    private Block beam1Block;
    private int beam1Meta;
    private Block wood2Block;
    private int wood2Meta;
    private Block cropBlock;
    private int cropMeta;
    private Item seedItem;
    
    public LOTRWorldGenHobbitFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        final int randomWood = random.nextInt(4);
        switch (randomWood) {
            case 0: {
                this.wood1Block = Blocks.planks;
                this.wood1Meta = 0;
                this.wood1SlabBlock = (Block)Blocks.wooden_slab;
                this.wood1SlabMeta = 0;
                this.wood1Stair = Blocks.oak_stairs;
                this.beam1Block = LOTRMod.woodBeamV1;
                this.beam1Meta = 0;
                break;
            }
            case 1: {
                this.wood1Block = Blocks.planks;
                this.wood1Meta = 2;
                this.wood1SlabBlock = (Block)Blocks.wooden_slab;
                this.wood1SlabMeta = 2;
                this.wood1Stair = Blocks.birch_stairs;
                this.beam1Block = LOTRMod.woodBeamV1;
                this.beam1Meta = 2;
                break;
            }
            case 2: {
                this.wood1Block = LOTRMod.planks;
                this.wood1Meta = 0;
                this.wood1SlabBlock = LOTRMod.woodSlabSingle;
                this.wood1SlabMeta = 0;
                this.wood1Stair = LOTRMod.stairsShirePine;
                this.beam1Block = LOTRMod.woodBeam1;
                this.beam1Meta = 0;
                break;
            }
            case 3: {
                this.wood1Block = LOTRMod.planks;
                this.wood1Meta = 4;
                this.wood1SlabBlock = LOTRMod.woodSlabSingle;
                this.wood1SlabMeta = 4;
                this.wood1Stair = LOTRMod.stairsApple;
                this.beam1Block = LOTRMod.woodBeamFruit;
                this.beam1Meta = 0;
                break;
            }
        }
        final int randomWood2 = random.nextInt(2);
        switch (randomWood2) {
            case 0: {
                this.wood2Block = Blocks.planks;
                this.wood2Meta = 1;
                break;
            }
            case 1: {
                this.wood2Block = LOTRMod.planks;
                this.wood2Meta = 6;
                break;
            }
        }
        final int randomCrop = random.nextInt(8);
        switch (randomCrop) {
            case 0: {
                this.cropBlock = Blocks.wheat;
                this.cropMeta = 7;
                this.seedItem = Items.wheat_seeds;
                break;
            }
            case 1: {
                this.cropBlock = Blocks.carrots;
                this.cropMeta = 7;
                this.seedItem = Items.carrot;
                break;
            }
            case 2: {
                this.cropBlock = Blocks.potatoes;
                this.cropMeta = 7;
                this.seedItem = Items.potato;
                break;
            }
            case 3: {
                this.cropBlock = LOTRMod.lettuceCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.lettuce;
                break;
            }
            case 4: {
                this.cropBlock = LOTRMod.pipeweedCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.pipeweedSeeds;
                break;
            }
            case 5: {
                this.cropBlock = LOTRMod.cornStalk;
                this.cropMeta = 0;
                this.seedItem = Item.getItemFromBlock(LOTRMod.cornStalk);
                break;
            }
            case 6: {
                this.cropBlock = LOTRMod.leekCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.leek;
                break;
            }
            case 7: {
                this.cropBlock = LOTRMod.turnipCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.turnip;
                break;
            }
        }
        if (super.restrictions) {
            int minHeight = 1;
            int maxHeight = 1;
            for (int i2 = -5; i2 <= 10; ++i2) {
                for (int k2 = -7; k2 <= 8; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
                        return false;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                }
            }
            if (Math.abs(maxHeight - minHeight) > 6) {
                return false;
            }
        }
        for (int i3 = -5; i3 <= 10; ++i3) {
            for (int k3 = -7; k3 <= 8; ++k3) {
                for (int j3 = 1; j3 <= 10; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                this.setGrassToDirt(world, i3, -1, k3);
                for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int k4 = -5; k4 <= 6; ++k4) {
            for (int i4 = -5; i4 <= 4; ++i4) {
                if (k4 == -5 || k4 == 6 || i4 == -5 || i4 == 4) {
                    for (int j3 = 1; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i4, j3, k4, this.wood2Block, this.wood2Meta);
                        this.setGrassToDirt(world, i4, j3 - 1, k4);
                    }
                }
            }
        }
        for (int stair = 0; stair <= 4; ++stair) {
            final int j4 = 5 + stair;
            for (int i2 = -5 + stair; i2 <= 4 - stair; ++i2) {
                for (final int k5 : new int[] { -5, 6 }) {
                    this.setBlockAndMetadata(world, i2, j4, k5, this.wood2Block, this.wood2Meta);
                }
            }
            for (int k6 = -6; k6 <= 7; ++k6) {
                this.setBlockAndMetadata(world, -6 + stair, j4, k6, LOTRMod.stairsThatch, 1);
                this.setBlockAndMetadata(world, 5 - stair, j4, k6, LOTRMod.stairsThatch, 0);
            }
        }
        for (int k4 = -4; k4 <= 5; ++k4) {
            for (int i4 = -4; i4 <= 3; ++i4) {
                this.setBlockAndMetadata(world, i4, 5, k4, this.wood1Block, this.wood1Meta);
            }
        }
        for (int j5 = 1; j5 <= 5; ++j5) {
            for (final int k7 : new int[] { -5, 6 }) {
                this.setBlockAndMetadata(world, -5, j5, k7, this.beam1Block, this.beam1Meta);
                this.setBlockAndMetadata(world, -2, j5, k7, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, 1, j5, k7, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, 4, j5, k7, this.beam1Block, this.beam1Meta);
            }
            for (final int i5 : new int[] { -5, 4 }) {
                this.setBlockAndMetadata(world, i5, j5, -1, this.beam1Block, this.beam1Meta);
                this.setBlockAndMetadata(world, i5, j5, 2, this.beam1Block, this.beam1Meta);
            }
        }
        for (int k4 = 0; k4 <= 1; ++k4) {
            for (final int i5 : new int[] { -5, 4 }) {
                this.setBlockAndMetadata(world, i5, 2, k4, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, i5, 4, k4, this.wood1Block, this.wood1Meta);
            }
        }
        for (final int k2 : new int[] { -5, 6 }) {
            for (int i5 = -1; i5 <= 0; ++i5) {
                this.setBlockAndMetadata(world, i5, 3, k2, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, i5, 5, k2, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, i5, 7, k2, LOTRMod.glassPane, 0);
            }
            for (int i5 = -2; i5 <= 1; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, k2, (Block)Blocks.grass, 0);
                for (int j6 = 1; j6 <= 3; ++j6) {
                    this.setBlockAndMetadata(world, i5, j6, k2, LOTRMod.gateWooden, 2);
                }
            }
        }
        for (int i3 = -1; i3 <= 0; ++i3) {
            for (int k3 = -6; k3 <= 7; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, LOTRMod.slabSingleThatch, 0);
            }
        }
        for (int i3 = -3; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -6, this.wood1Stair, 6);
            this.setBlockAndMetadata(world, i3, 5, 7, this.wood1Stair, 7);
        }
        this.setBlockAndMetadata(world, -5, 5, -6, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, -4, 5, -6, this.wood1Stair, 4);
        this.setBlockAndMetadata(world, 3, 5, -6, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 4, 5, -6, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, -5, 5, 7, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, -4, 5, 7, this.wood1Stair, 4);
        this.setBlockAndMetadata(world, 3, 5, 7, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 4, 5, 7, this.wood1Block, this.wood1Meta);
        for (final int i6 : new int[] { -4, 3 }) {
            for (final int k8 : new int[] { -1, 2 }) {
                this.setBlockAndMetadata(world, i6, 1, k8, Blocks.crafting_table, 0);
                this.setBlockAndMetadata(world, i6, 2, k8, Blocks.fence, 0);
                this.setBlockAndMetadata(world, i6, 3, k8, Blocks.fence, 0);
                this.setBlockAndMetadata(world, i6, 4, k8, Blocks.torch, 5);
            }
        }
        this.setBlockAndMetadata(world, -4, 1, -4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 2, -4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, -4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 1, -3, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 2, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 1, 4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 2, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 2, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 1, 4, Blocks.hay_block, 0);
        for (int j5 = 1; j5 <= 4; ++j5) {
            this.setBlockAndMetadata(world, 2, j5, -3, Blocks.fence, 0);
        }
        this.setBlockAndMetadata(world, 1, 1, -4, this.wood1Stair, 1);
        this.setBlockAndMetadata(world, 2, 1, -4, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 2, 2, -4, this.wood1Stair, 1);
        this.setBlockAndMetadata(world, 3, 1, -4, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 3, 2, -4, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 3, 2, -3, this.wood1Stair, 7);
        this.setBlockAndMetadata(world, 3, 3, -3, this.wood1Stair, 2);
        this.setBlockAndMetadata(world, 3, 3, -2, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 2, 3, -2, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 2, 4, -2, this.wood1Stair, 0);
        this.setBlockAndMetadata(world, 1, 4, -2, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 1, 5, -2, this.wood1Stair, 0);
        this.setAir(world, 3, 5, -4);
        this.setAir(world, 3, 5, -3);
        this.setAir(world, 3, 5, -2);
        this.setAir(world, 2, 5, -2);
        for (int i3 = 0; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -3, Blocks.fence, 0);
            this.setBlockAndMetadata(world, i3, 6, -1, Blocks.fence, 0);
        }
        this.setBlockAndMetadata(world, 2, 6, -4, Blocks.fence, 0);
        this.setBlockAndMetadata(world, 0, 6, -2, Blocks.fence_gate, 3);
        for (int k4 = -4; k4 <= 5; ++k4) {
            this.setBlockAndMetadata(world, -4, 6, k4, this.wood2Block, this.wood2Meta);
        }
        for (int k4 = -1; k4 <= 5; ++k4) {
            this.setBlockAndMetadata(world, 3, 6, k4, this.wood2Block, this.wood2Meta);
        }
        this.setBlockAndMetadata(world, -2, 7, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 7, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 7, 5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 7, 5, Blocks.torch, 4);
        final int carpet = random.nextInt(16);
        this.setBlockAndMetadata(world, -1, 6, 2, Blocks.carpet, carpet);
        this.setBlockAndMetadata(world, 0, 6, 2, Blocks.carpet, carpet);
        this.setBlockAndMetadata(world, -1, 6, 3, Blocks.carpet, carpet);
        this.setBlockAndMetadata(world, 0, 6, 3, Blocks.carpet, carpet);
        for (int k3 = 4; k3 <= 5; ++k3) {
            for (int j3 = 6; j3 <= 7; ++j3) {
                this.setBlockAndMetadata(world, -3, j3, k3, Blocks.bookshelf, 0);
                this.setBlockAndMetadata(world, 2, j3, k3, Blocks.bookshelf, 0);
            }
        }
        this.setBlockAndMetadata(world, -3, 6, 0, this.wood2Block, this.wood2Meta);
        this.setBlockAndMetadata(world, -3, 7, 0, LOTRWorldGenHobbitStructure.getRandomCakeBlock(random), 0);
        this.setBlockAndMetadata(world, -3, 6, 1, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -3, 6, 2, LOTRMod.hobbitOven, 4);
        this.setBlockAndMetadata(world, -3, 6, 3, Blocks.crafting_table, 0);
        this.placeChest(world, random, 2, 6, 1, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.setBlockAndMetadata(world, 2, 6, 2, Blocks.bed, 0);
        this.setBlockAndMetadata(world, 2, 6, 3, Blocks.bed, 8);
        for (int i4 = 5; i4 <= 10; ++i4) {
            this.setBlockAndMetadata(world, i4, 1, -5, Blocks.fence, 0);
            this.setBlockAndMetadata(world, i4, 1, 6, Blocks.fence, 0);
        }
        for (int k3 = -4; k3 <= 5; ++k3) {
            this.setBlockAndMetadata(world, 10, 1, k3, Blocks.fence, 0);
        }
        this.setBlockAndMetadata(world, 7, 1, -5, Blocks.fence_gate, 0);
        this.setBlockAndMetadata(world, 5, 2, -5, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, -5, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, -1, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, 2, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 5, 2, 6, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, 6, Blocks.torch, 5);
        for (int i4 = 5; i4 <= 9; ++i4) {
            this.setBlockAndMetadata(world, i4, 0, -4, Blocks.gravel, 0);
            this.setBlockAndMetadata(world, i4, 0, 5, Blocks.gravel, 0);
        }
        for (int k3 = -3; k3 <= 4; ++k3) {
            this.setBlockAndMetadata(world, 4, 0, k3, Blocks.stonebrick, 0);
            this.setBlockAndMetadata(world, 5, 0, k3, Blocks.water, 0);
            this.setBlockAndMetadata(world, 5, 1, k3, (Block)Blocks.stone_slab, 5);
            this.setBlockAndMetadata(world, 9, 0, k3, Blocks.gravel, 0);
            for (int i2 = 6; i2 <= 8; ++i2) {
                this.setBlockAndMetadata(world, i2, 0, k3, Blocks.farmland, 7);
                this.setBlockAndMetadata(world, i2, 1, k3, this.cropBlock, this.cropMeta);
            }
        }
        this.setBlockAndMetadata(world, 10, 2, 0, Blocks.fence, 0);
        this.setBlockAndMetadata(world, 10, 3, 0, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 10, 4, 0, Blocks.pumpkin, 1);
        final LOTREntityHobbit farmer = new LOTREntityHobbitFarmer(world);
        this.spawnNPCAndSetHome(farmer, world, 0, 6, 0, 16);
        for (int farmhands = 1 + random.nextInt(3), l = 0; l < farmhands; ++l) {
            final LOTREntityHobbitFarmhand farmhand = new LOTREntityHobbitFarmhand(world);
            farmhand.seedsItem = this.seedItem;
            this.spawnNPCAndSetHome(farmhand, world, 7, 1, 0, 8);
        }
        for (int animals = 3 + random.nextInt(6), m = 0; m < animals; ++m) {
            final Class animalClass = ((BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(world.rand, (Collection)LOTRBiome.shire.getSpawnableList(EnumCreatureType.creature))).entityClass;
            EntityCreature animal = null;
            try {
                animal = animalClass.getConstructor(World.class).newInstance(world);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                continue;
            }
            this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 8);
        }
        return true;
    }
}
