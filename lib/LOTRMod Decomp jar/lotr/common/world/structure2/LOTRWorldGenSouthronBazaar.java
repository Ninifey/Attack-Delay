// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntitySouthronButcher;
import lotr.common.entity.npc.LOTREntitySouthronFlorist;
import lotr.common.entity.npc.LOTREntitySouthronMiner;
import lotr.common.entity.npc.LOTREntitySouthronBrewer;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.common.entity.npc.LOTREntitySouthronFarmer;
import lotr.common.entity.npc.LOTREntitySouthronGoldsmith;
import lotr.common.entity.npc.LOTREntitySouthronBaker;
import lotr.common.entity.npc.LOTREntitySouthronFishmonger;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.entity.npc.LOTREntitySouthronMason;
import lotr.common.entity.npc.LOTREntitySouthronTrader;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntitySouthronLumberman;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronBazaar extends LOTRWorldGenSouthronStructure
{
    private static Class[] stalls;
    
    public LOTRWorldGenSouthronBazaar(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -13; i2 <= 13; ++i2) {
                for (int k2 = -9; k2 <= 9; ++k2) {
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
                    if (maxHeight - minHeight > 12) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -13; i3 <= 13; ++i3) {
            for (int k3 = -9; k3 <= 9; ++k3) {
                for (int j3 = 1; j3 <= 8; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.stoneBlock, super.stoneMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        this.loadStrScan("southron_bazaar");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", super.brick2Block, super.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", super.brick2SlabBlock, super.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", super.brick2SlabBlock, super.brick2SlabMeta | 0x8);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeArmorStand(world, -4, 1, -2, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetNearHaradWarlord), null, null, null });
        this.placeAnimalJar(world, -3, 1, -7, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
        this.placeAnimalJar(world, 11, 1, -1, LOTRMod.birdCageWood, 0, null);
        this.placeAnimalJar(world, 3, 1, 7, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -9, 3, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, 4, 3, 3, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        final List<Class> stallClasses = new ArrayList<Class>(Arrays.asList((Class[])this.getStallClasses()));
        while (stallClasses.size() > 6) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            final LOTRWorldGenStructureBase2 stall0 = stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall2 = stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall3 = stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall4 = stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall5 = stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall6 = stallClasses.get(5).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            this.generateSubstructure(stall0, world, random, -8, 1, -4, 2);
            this.generateSubstructure(stall2, world, random, 0, 1, -4, 2);
            this.generateSubstructure(stall3, world, random, 8, 1, -4, 2);
            this.generateSubstructure(stall4, world, random, -8, 1, 4, 0);
            this.generateSubstructure(stall5, world, random, 0, 1, 4, 0);
            this.generateSubstructure(stall6, world, random, 8, 1, 4, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    protected Class[] getStallClasses() {
        return LOTRWorldGenSouthronBazaar.stalls;
    }
    
    static {
        LOTRWorldGenSouthronBazaar.stalls = new Class[] { Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class };
    }
    
    private static class Lumber extends LOTRWorldGenStructureBase2
    {
        public Lumber(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.wood4, 10);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.wood4, 2);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.wood4, 2);
            this.placeFlowerPot(world, -2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
            this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling8, 1, 3));
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronLumberman(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Mason extends LOTRWorldGenStructureBase2
    {
        public Mason(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.slabSingle4, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick3, 13);
            this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(LOTRMod.pickaxeBronze));
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronMason(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Fish extends LOTRWorldGenStructureBase2
    {
        public Fish(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, (Block)Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
            this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronFishmonger(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Baker extends LOTRWorldGenStructureBase2
    {
        public Baker(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.planks2, 2);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.planks2, 2);
            this.placePlate_item(world, random, -1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
            this.placePlate_item(world, random, 1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3)), true);
            this.placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, this.getRandomFlower(world, random));
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronBaker(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Goldsmith extends LOTRWorldGenStructureBase2
    {
        public Goldsmith(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, -1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, 1, 1, -1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, LOTRMod.birdCage, 2);
            this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, LOTRMod.birdCage, 3);
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronGoldsmith(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Farmer extends LOTRWorldGenStructureBase2
    {
        public Farmer(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, (Block)Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -1, 1, -1, Blocks.hay_block, 0);
            this.placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.lettuce, 1 + random.nextInt(3), 1), true);
            final LOTREntitySouthronFarmer trader = new LOTREntitySouthronFarmer(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Blacksmith extends LOTRWorldGenStructureBase2
    {
        public Blacksmith(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
            this.placeArmorStand(world, 1, 1, 1, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetNearHarad), new ItemStack(LOTRMod.bodyNearHarad), null, null });
            this.placeWeaponRack(world, -2, 2, 0, 1, new LOTRWorldGenSouthronBazaar(false).getRandomHaradWeapon(random));
            this.placeWeaponRack(world, 2, 2, 0, 3, new LOTRWorldGenSouthronBazaar(false).getRandomHaradWeapon(random));
            final LOTREntityNearHaradBlacksmith trader = new LOTREntityNearHaradBlacksmith(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Brewer extends LOTRWorldGenStructureBase2
    {
        public Brewer(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
            this.placeMug(world, random, -2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
            this.placeMug(world, random, 2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronBrewer(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Miner extends LOTRWorldGenStructureBase2
    {
        public Miner(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreCopper, 0);
            this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(LOTRMod.pickaxeBronze));
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronMiner(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Florist extends LOTRWorldGenStructureBase2
    {
        public Florist(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeFlowerPot(world, -2, 2, 0, this.getRandomFlower(world, random));
            this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, -1, 0, 1, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.doubleFlower, 3);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.doubleFlower, 11);
            this.setBlockAndMetadata(world, 1, 1, 1, (Block)Blocks.grass, 0);
            this.plantFlower(world, random, 1, 2, 1);
            this.setBlockAndMetadata(world, 1, 1, 0, Blocks.trapdoor, 12);
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.trapdoor, 15);
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronFlorist(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Butcher extends LOTRWorldGenStructureBase2
    {
        public Butcher(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
            this.placeKebabStand(world, random, 0, 2, 1, LOTRMod.kebabStand, 3);
            this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 1), true);
            final LOTREntitySouthronTrader trader = new LOTREntitySouthronButcher(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
}
