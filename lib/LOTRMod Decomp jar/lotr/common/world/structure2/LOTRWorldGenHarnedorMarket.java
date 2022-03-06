// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHarnedorFarmer;
import lotr.common.entity.npc.LOTREntityHarnedorBlacksmith;
import lotr.common.entity.npc.LOTREntityHarnedorHunter;
import lotr.common.entity.npc.LOTREntityHarnedorMason;
import lotr.common.entity.npc.LOTREntityHarnedorMiner;
import lotr.common.entity.npc.LOTREntityHarnedorLumberman;
import lotr.common.entity.npc.LOTREntityHarnedorBaker;
import lotr.common.entity.npc.LOTREntityHarnedorButcher;
import lotr.common.entity.npc.LOTREntityHarnedorFishmonger;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import lotr.common.entity.npc.LOTREntityHarnedorTrader;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorBrewer;
import lotr.common.LOTRFoods;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemBanner;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorMarket extends LOTRWorldGenHarnedorStructure
{
    private static Class[] stalls;
    
    public LOTRWorldGenHarnedorMarket(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -9; i2 <= 9; ++i2) {
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
        for (int i3 = -8; i3 <= 8; ++i3) {
            for (int k3 = -8; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 6 && k4 <= 6) || (i4 == 7 && k4 <= 4) || (k4 == 7 && i4 <= 4) || (i4 == 8 && k4 <= 1) || (k4 == 8 && i4 <= 1)) {
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.plank2Block, super.plank2Meta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
            }
        }
        this.loadStrScan("harnedor_market");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|12", super.woodBlock, super.woodMeta | 0xC);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeWallBanner(world, 0, 5, -2, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 0, 5, 2, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.placeWallBanner(world, -2, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
        this.placeWallBanner(world, 2, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
        this.spawnItemFrame(world, 2, 2, -3, 3, this.getHarnedorFramedItem(random));
        this.spawnItemFrame(world, -2, 2, 3, 1, this.getHarnedorFramedItem(random));
        this.placeWeaponRack(world, -3, 2, 1, 6, this.getRandomHarnedorWeapon(random));
        this.placeArmorStand(world, 2, 1, -2, 2, new ItemStack[] { new ItemStack(LOTRMod.helmetHarnedor), null, null, null });
        this.placeFlowerPot(world, -2, 2, 2, this.getRandomFlower(world, random));
        this.placeAnimalJar(world, 2, 1, 1, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
        this.placeAnimalJar(world, -3, 1, -1, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -2, 3, -2, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, 6, 3, 1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeSkull(world, random, 2, 4, -5);
        final List<Class> stallClasses = new ArrayList<Class>(Arrays.asList((Class[])LOTRWorldGenHarnedorMarket.stalls));
        while (stallClasses.size() > 4) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            final LOTRWorldGenStructureBase2 stall0 = stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall2 = stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall3 = stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall4 = stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            this.generateSubstructure(stall0, world, random, 2, 1, 2, 0);
            this.generateSubstructure(stall2, world, random, 2, 1, -2, 1);
            this.generateSubstructure(stall3, world, random, -2, 1, -2, 2);
            this.generateSubstructure(stall4, world, random, -2, 1, 2, 3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final int maxSteps = 12;
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int step = 0; step < 12; ++step) {
                final int j2 = 0 - step;
                final int k5 = -9 - step;
                if (this.isOpaque(world, i2, j2, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i2, j2, k5, super.plank2StairBlock, 2);
                this.setGrassToDirt(world, i2, j2 - 1, k5);
                for (int j3 = j2 - 1; !this.isOpaque(world, i2, j3, k5) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k5, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i2, j3 - 1, k5);
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int step = 0; step < 12; ++step) {
                final int j2 = 0 - step;
                final int k5 = 9 + step;
                if (this.isOpaque(world, i2, j2, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i2, j2, k5, super.plank2StairBlock, 3);
                this.setGrassToDirt(world, i2, j2 - 1, k5);
                for (int j3 = j2 - 1; !this.isOpaque(world, i2, j3, k5) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k5, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i2, j3 - 1, k5);
                }
            }
        }
        for (int k6 = -1; k6 <= 1; ++k6) {
            for (int step = 0; step < 12; ++step) {
                final int j2 = 0 - step;
                final int i5 = -9 - step;
                if (this.isOpaque(world, i5, j2, k6)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j2, k6, super.plank2StairBlock, 1);
                this.setGrassToDirt(world, i5, j2 - 1, k6);
                for (int j3 = j2 - 1; !this.isOpaque(world, i5, j3, k6) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i5, j3, k6, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i5, j3 - 1, k6);
                }
            }
        }
        for (int k6 = -1; k6 <= 1; ++k6) {
            for (int step = 0; step < 12; ++step) {
                final int j2 = 0 - step;
                final int i5 = 9 + step;
                if (this.isOpaque(world, i5, j2, k6)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j2, k6, super.plank2StairBlock, 0);
                this.setGrassToDirt(world, i5, j2 - 1, k6);
                for (int j3 = j2 - 1; !this.isOpaque(world, i5, j3, k6) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i5, j3, k6, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i5, j3 - 1, k6);
                }
            }
        }
        return true;
    }
    
    static {
        LOTRWorldGenHarnedorMarket.stalls = new Class[] { Brewer.class, Fish.class, Butcher.class, Baker.class, Lumber.class, Miner.class, Mason.class, Hunter.class, Blacksmith.class, Farmer.class };
    }
    
    private static class Brewer extends LOTRWorldGenStructureBase2
    {
        public Brewer(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeMug(world, random, 3, 2, 0, 0, LOTRFoods.HARNEDOR_DRINK);
            this.placeMug(world, random, 0, 2, 2, 1, LOTRFoods.HARNEDOR_DRINK);
            this.setBlockAndMetadata(world, 0, 2, 4, LOTRMod.barrel, 4);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.barrel, 2);
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorBrewer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
            this.placePlate_item(world, random, 0, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placeFlowerPot(world, 0, 2, 4, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.placePlate_item(world, random, 3, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 2, 1, 4, (Block)Blocks.cauldron, 3);
            this.placeWeaponRack(world, 4, 2, 2, 6, new ItemStack((Item)Items.fishing_rod));
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorFishmonger(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.kebab, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 4, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.kebab, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 3, 1, 3, Blocks.furnace, 2);
            this.placeKebabStand(world, random, 3, 2, 3, LOTRMod.kebabStand, 2);
            this.setBlockAndMetadata(world, 2, 3, 3, LOTRMod.kebabBlock, 0);
            this.setBlockAndMetadata(world, 2, 4, 3, LOTRMod.fence2, 2);
            this.setBlockAndMetadata(world, 2, 5, 3, LOTRMod.fence2, 2);
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorButcher(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 0, 2, 4, LOTRMod.lemonCake, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.marzipanBlock, 0);
            this.placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(LOTRMod.rollingPin));
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorBaker(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
            this.placeFlowerPot(world, 0, 2, 2, new ItemStack(LOTRMod.sapling8, 1, 3));
            this.placeFlowerPot(world, 0, 2, 4, new ItemStack(LOTRMod.sapling7, 1, 3));
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.wood6, 3);
            this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.wood6, 11);
            this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.woodBeam8, 11);
            this.placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(LOTRMod.axeBronze));
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorLumberman(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placeWeaponRack(world, 2, 2, 0, 2, new ItemStack(LOTRMod.pickaxeBronze));
            this.placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(LOTRMod.shovelBronze));
            this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.oreTin, 0);
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorMiner(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
            this.placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(LOTRMod.pickaxeBronze));
            this.setBlockAndMetadata(world, 4, 1, 2, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, 2, 1, 3, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.redSandstone, 0);
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorMason(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.placePlate_item(world, random, 3, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.deerRaw, 1 + random.nextInt(3), 0), true);
            this.spawnItemFrame(world, 4, 2, 3, 2, new ItemStack(LOTRMod.fur));
            this.spawnItemFrame(world, 3, 2, 4, 3, new ItemStack(Items.leather));
            final LOTREntityHarnedorTrader trader = new LOTREntityHarnedorHunter(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.placeWeaponRack(world, 3, 2, 0, 2, new LOTRWorldGenHarnedorMarket(false).getRandomHarnedorWeapon(random));
            this.placeWeaponRack(world, 0, 2, 4, 3, new LOTRWorldGenHarnedorMarket(false).getRandomHarnedorWeapon(random));
            this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, 3, 1, 3, Blocks.anvil, 1);
            this.placeArmorStand(world, 4, 1, 2, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), null, null });
            this.placeArmorStand(world, 2, 1, 4, 1, null);
            final LOTREntityHarnedorBlacksmith trader = new LOTREntityHarnedorBlacksmith(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 1, 2, LOTRMod.berryBush, 9);
            this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.berryBush, 9);
            this.placePlate_item(world, random, 3, 2, 0, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
            this.placePlate_item(world, random, 0, 2, 4, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
            final LOTREntityHarnedorFarmer trader = new LOTREntityHarnedorFarmer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
        
        private ItemStack getRandomFarmFood(final Random random) {
            final ItemStack[] items = { new ItemStack(LOTRMod.orange), new ItemStack(LOTRMod.lemon), new ItemStack(LOTRMod.lime), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip) };
            final ItemStack ret = items[random.nextInt(items.length)].copy();
            ret.stackSize = 1 + random.nextInt(3);
            return ret;
        }
    }
}
