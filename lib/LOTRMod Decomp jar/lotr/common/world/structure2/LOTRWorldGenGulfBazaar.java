// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGulfFarmer;
import lotr.common.entity.npc.LOTREntityGulfBlacksmith;
import lotr.common.entity.npc.LOTREntityGulfHunter;
import lotr.common.entity.npc.LOTREntityGulfLumberman;
import lotr.common.entity.npc.LOTREntityGulfGoldsmith;
import lotr.common.entity.npc.LOTREntityGulfMiner;
import lotr.common.entity.npc.LOTREntityGulfBaker;
import lotr.common.entity.npc.LOTREntityGulfFishmonger;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import lotr.common.entity.npc.LOTREntityGulfBrewer;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityGulfButcher;
import lotr.common.entity.npc.LOTREntityGulfTrader;
import lotr.common.entity.npc.LOTREntityGulfMason;
import net.minecraft.item.ItemStack;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfBazaar extends LOTRWorldGenGulfStructure
{
    private static Class[] stalls;
    
    public LOTRWorldGenGulfBazaar(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -17; i2 <= 17; ++i2) {
                for (int k2 = -12; k2 <= 8; ++k2) {
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
        for (int i3 = -17; i3 <= 17; ++i3) {
            for (int k3 = -12; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 >= 5 && i4 <= 9 && k4 >= 10 && k4 <= 12) {
                    for (int j2 = 1; j2 <= 5; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if ((k3 >= -6 && k3 <= -5 && i4 <= 4) || (k3 == -5 && i4 >= 10 && i4 <= 12) || (k4 == 4 && i4 <= 14) || (k4 >= 2 && k4 <= 3 && i4 <= 15) || (k4 <= 1 && i4 <= 16) || (k3 == 5 && i4 <= 12) || (k3 == 6 && i4 <= 9) || (k3 == 7 && i4 <= 4)) {
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("gulf_bazaar");
        this.addBlockMetaAliasOption("BRICK", 6, super.brickBlock, super.brickMeta);
        this.addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK", 8, Blocks.sandstone, 0);
        this.addBlockAliasOption("BRICK_STAIR", 6, super.brickStairBlock);
        this.addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockAliasOption("BRICK_STAIR", 8, Blocks.sandstone_stairs);
        this.addBlockMetaAliasOption("BRICK_WALL", 6, super.brickWallBlock, super.brickWallMeta);
        this.addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wallStoneV, 4);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", super.beamBlock, super.beamMeta);
        this.associateBlockMetaAlias("BEAM2", super.beam2Block, super.beam2Meta);
        this.associateBlockMetaAlias("BEAM2|4", super.beam2Block, super.beam2Meta | 0x4);
        this.associateBlockMetaAlias("BEAM2|8", super.beam2Block, super.beam2Meta | 0x8);
        this.addBlockMetaAliasOption("GROUND", 10, (Block)Blocks.sand, 0);
        this.addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.dirtPath, 0);
        this.associateBlockMetaAlias("WOOL", Blocks.wool, 14);
        this.associateBlockMetaAlias("CARPET", Blocks.carpet, 14);
        this.associateBlockMetaAlias("WOOL2", Blocks.wool, 15);
        this.associateBlockMetaAlias("CARPET2", Blocks.carpet, 15);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeAnimalJar(world, -5, 4, -2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -7, 5, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeWallBanner(world, -3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, -8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, -6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        this.placeWallBanner(world, 7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, 6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, 8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        for (final int i5 : new int[] { -7, 7 }) {
            final int j2 = 1;
            final int k5 = -11;
            final LOTREntityGulfHaradWarrior guard = new LOTREntityGulfHaradWarrior(world);
            guard.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(guard, world, i5, j2, k5, 4);
        }
        final List<Class> stallClasses = new ArrayList<Class>(Arrays.asList((Class[])LOTRWorldGenGulfBazaar.stalls));
        while (stallClasses.size() > 5) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            final LOTRWorldGenStructureBase2 stall0 = stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall2 = stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall3 = stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall4 = stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall5 = stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            this.generateSubstructure(stall0, world, random, -9, 1, 0, 3);
            this.generateSubstructure(stall2, world, random, -5, 1, 5, 1);
            this.generateSubstructure(stall3, world, random, 0, 1, 6, 1);
            this.generateSubstructure(stall4, world, random, 8, 1, 2, 3);
            this.generateSubstructure(stall5, world, random, 11, 1, -2, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    static {
        LOTRWorldGenGulfBazaar.stalls = new Class[] { Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Miner.class, Goldsmith.class, Lumber.class, Hunter.class, Blacksmith.class, Farmer.class };
    }
    
    private static class Mason extends LOTRWorldGenStructureBase2
    {
        public Mason(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.brick3, 13);
            this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
            this.placeWeaponRack(world, 3, 2, 2, 3, new ItemStack(LOTRMod.pickaxeBronze));
            final LOTREntityGulfTrader trader = new LOTREntityGulfMason(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3)), true);
            this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3)), true);
            this.placeSkull(world, random, 2, 2, 3);
            final LOTREntityGulfTrader trader = new LOTREntityGulfButcher(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.barrel, 3);
            this.placeMug(world, random, 1, 2, 0, 0, LOTRFoods.GULF_HARAD_DRINK);
            this.placeMug(world, random, 0, 2, 2, 3, LOTRFoods.GULF_HARAD_DRINK);
            this.placeMug(world, random, 3, 2, 1, 1, LOTRFoods.GULF_HARAD_DRINK);
            this.placeFlowerPot(world, 2, 2, 3, this.getRandomFlower(world, random));
            final LOTREntityGulfTrader trader = new LOTREntityGulfBrewer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, (Block)Blocks.cauldron, 3);
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
            this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placeWeaponRack(world, 1, 2, 3, 0, new ItemStack((Item)Items.fishing_rod));
            final LOTREntityGulfTrader trader = new LOTREntityGulfFishmonger(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, Blocks.furnace, 2);
            this.setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
            this.setBlockAndMetadata(world, 3, 2, 2, LOTRMod.bananaCake, 0);
            this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.rollingPin));
            final LOTREntityGulfTrader trader = new LOTREntityGulfBaker(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.oreCopper, 0);
            this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.pickaxeBronze));
            final LOTREntityGulfTrader trader = new LOTREntityGulfMiner(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.birdCage, 3);
            this.setBlockAndMetadata(world, 2, 3, 2, LOTRMod.goldBars, 0);
            this.placeFlowerPot(world, 0, 2, 1, this.getRandomFlower(world, random));
            final LOTREntityGulfTrader trader = new LOTREntityGulfGoldsmith(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
            return true;
        }
    }
    
    private static class Lumber extends LOTRWorldGenStructureBase2
    {
        public Lumber(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
            this.placeFlowerPot(world, 0, 2, 2, new ItemStack(Blocks.sapling, 1, 4));
            this.placeFlowerPot(world, 3, 2, 1, new ItemStack(LOTRMod.sapling8, 1, 3));
            final LOTREntityGulfTrader trader = new LOTREntityGulfLumberman(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
            return true;
        }
    }
    
    private static class Hunter extends LOTRWorldGenStructureBase2
    {
        public Hunter(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
            this.placeSkull(world, random, 2, 3, 2);
            this.placeSkull(world, random, 3, 2, 2);
            this.spawnItemFrame(world, 2, 2, 2, 2, new ItemStack(LOTRMod.lionFur));
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
            this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.spearHarad));
            final LOTREntityGulfTrader trader = new LOTREntityGulfHunter(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, Blocks.anvil, 3);
            this.placeArmorStand(world, 1, 1, 2, 0, new ItemStack[] { null, new ItemStack(LOTRMod.bodyGulfHarad), null, null });
            this.placeWeaponRack(world, 0, 2, 2, 1, new LOTRWorldGenGulfBazaar(false).getRandomGulfWeapon(random));
            this.placeWeaponRack(world, 3, 2, 2, 3, new LOTRWorldGenGulfBazaar(false).getRandomGulfWeapon(random));
            final LOTREntityGulfBlacksmith trader = new LOTREntityGulfBlacksmith(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, (Block)Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, 1, 2, 3, Blocks.hay_block, 0);
            this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3)), true);
            this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
            final LOTREntityGulfFarmer trader = new LOTREntityGulfFarmer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
            return true;
        }
    }
}
