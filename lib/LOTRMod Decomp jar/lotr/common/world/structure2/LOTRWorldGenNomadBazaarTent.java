// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNomadArmourer;
import lotr.common.entity.npc.LOTREntityNomadMiner;
import lotr.common.entity.npc.LOTREntityNomadBrewer;
import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityNomadTrader;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNomadMason;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.LOTRMod;
import lotr.common.LOTRFoods;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNomadBazaarTent extends LOTRWorldGenNomadStructure
{
    private static Class[] stalls;
    
    public LOTRWorldGenNomadBazaarTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -14; i2 <= 14; ++i2) {
                for (int k2 = -6; k2 <= 8; ++k2) {
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
        for (int i3 = -14; i3 <= 14; ++i3) {
            for (int k3 = -6; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (!this.isSurface(world, i3, 0, k3)) {
                    this.laySandBase(world, i3, 0, k3);
                }
                for (int j2 = 1; j2 <= 8; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
            }
        }
        this.loadStrScan("nomad_bazaar");
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.beamBlock, super.beamMeta);
        this.associateBlockMetaAlias("TENT", super.tentBlock, super.tentMeta);
        this.associateBlockMetaAlias("TENT2", super.tent2Block, super.tent2Meta);
        this.associateBlockMetaAlias("CARPET", super.carpetBlock, super.carpetMeta);
        this.associateBlockMetaAlias("CARPET2", super.carpet2Block, super.carpet2Meta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeSkull(world, random, -8, 2, -4);
        this.placeBarrel(world, random, 7, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
        this.placeBarrel(world, random, 8, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
        this.placeAnimalJar(world, -7, 2, -4, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
        this.placeAnimalJar(world, 9, 1, 5, LOTRMod.birdCageWood, 0, null);
        this.placeAnimalJar(world, 4, 3, 2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -4, 4, 5, LOTRMod.birdCage, 2, new LOTREntityBird(world));
        this.placeAnimalJar(world, -4, 5, -1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, 0, 5, 5, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        final List<Class> stallClasses = new ArrayList<Class>(Arrays.asList((Class[])LOTRWorldGenNomadBazaarTent.stalls));
        while (stallClasses.size() > 3) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            final LOTRWorldGenStructureBase2 stall0 = stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall2 = stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            final LOTRWorldGenStructureBase2 stall3 = stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(super.notifyChanges);
            this.generateSubstructure(stall0, world, random, -4, 1, 6, 0);
            this.generateSubstructure(stall2, world, random, 0, 1, 6, 0);
            this.generateSubstructure(stall3, world, random, 4, 1, 6, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    static {
        LOTRWorldGenNomadBazaarTent.stalls = new Class[] { Mason.class, Brewer.class, Miner.class, Armourer.class };
    }
    
    private static class Mason extends LOTRWorldGenStructureBase2
    {
        public Mason(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, -1, 3, 1, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, -1, 1, 0, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, -1, 2, 0, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, 0, 2, 1, LOTRMod.slabSingle4, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.slabSingle4, 0);
            this.placeWeaponRack(world, 1, 3, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
            final LOTREntityNomadTrader trader = new LOTREntityNomadMason(world);
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
            this.setBlockAndMetadata(world, 0, 1, 1, (Block)Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
            this.placeMug(world, random, -1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
            this.placeMug(world, random, 1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
            final LOTREntityNomadTrader trader = new LOTREntityNomadBrewer(world);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 1, 2, 1, Blocks.lapis_ore, 0);
            this.setBlockAndMetadata(world, 1, 1, 0, Blocks.lapis_ore, 0);
            this.placeWeaponRack(world, 0, 2, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
            final LOTREntityNomadTrader trader = new LOTREntityNomadMiner(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
    
    private static class Armourer extends LOTRWorldGenStructureBase2
    {
        public Armourer(final boolean flag) {
            super(flag);
        }
        
        @Override
        public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.anvil, 1);
            this.placeArmorStand(world, 0, 1, 1, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetMoredainLion), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsNomad), new ItemStack(LOTRMod.bootsNomad) });
            this.placeWeaponRack(world, -1, 2, -2, 2, new LOTRWorldGenNomadBazaarTent(false).getRandomNomadWeapon(random));
            final LOTREntityNomadArmourer trader = new LOTREntityNomadArmourer(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }
}
