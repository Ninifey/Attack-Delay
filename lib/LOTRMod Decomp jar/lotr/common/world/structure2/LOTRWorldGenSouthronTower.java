// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTower extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                for (int j2 = 1; j2 <= 15; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("southron_tower");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.associateBlockAlias("GATE_METAL", super.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeChest(world, random, -1, 1, -1, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER);
        this.placeMug(world, random, -1, 2, 1, 0, LOTRFoods.SOUTHRON_DRINK);
        this.placeBarrel(world, random, 1, 2, 1, 2, LOTRFoods.SOUTHRON_DRINK);
        this.placeWeaponRack(world, -1, 8, 0, 5, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, 1, 8, 0, 7, this.getRandomHaradWeapon(random));
        this.placeWallBanner(world, 0, 14, -3, super.bannerType, 2);
        this.placeWallBanner(world, -3, 14, 0, super.bannerType, 3);
        this.placeWallBanner(world, 0, 14, 3, super.bannerType, 0);
        this.placeWallBanner(world, 3, 14, 0, super.bannerType, 1);
        for (int warriors = 2, l = 0; l < warriors; ++l) {
            final LOTREntityNearHaradrimBase warrior = this.createWarrior(world, random);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 15, 0, 8);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        this.setSpawnClasses(respawner);
        respawner.setCheckRanges(8, -4, 20, 8);
        respawner.setSpawnRanges(2, -1, 16, 8);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    protected LOTREntityNearHaradrimBase createWarrior(final World world, final Random random) {
        return (random.nextInt(3) == 0) ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
    }
    
    protected void setSpawnClasses(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
    }
}
