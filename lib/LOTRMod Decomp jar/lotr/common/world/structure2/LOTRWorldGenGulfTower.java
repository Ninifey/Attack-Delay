// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityGulfHaradArcher;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTower extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
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
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 1; j2 <= 16; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("gulf_tower");
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", super.woodBlock, super.woodMeta | 0x4);
        this.associateBlockMetaAlias("WOOD|8", super.woodBlock, super.woodMeta | 0x8);
        this.associateBlockMetaAlias("WOOD|12", super.woodBlock, super.woodMeta | 0xC);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("FLAG", super.flagBlock, super.flagMeta);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeChest(world, random, -2, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.GULF_HOUSE);
        this.placeSkull(world, random, 2, 2, 1);
        this.placeBarrel(world, random, -2, 2, -1, 4, LOTRFoods.GULF_HARAD_DRINK);
        this.placeMug(world, random, 2, 2, -1, 2, LOTRFoods.GULF_HARAD_DRINK);
        this.placePlate(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        this.placePlate(world, random, -2, 2, 1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        this.placeWallBanner(world, 0, 8, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 0, 8, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        for (int warriors = 1 + random.nextInt(2), l = 0; l < warriors; ++l) {
            final LOTREntityGulfHaradrim warrior = (random.nextInt(3) == 0) ? new LOTREntityGulfHaradArcher(world) : new LOTREntityGulfHaradWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 14, 0, 8);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
        respawner.setCheckRanges(6, -20, 4, 4);
        respawner.setSpawnRanges(1, -2, 1, 8);
        this.placeNPCRespawner(respawner, world, 0, 14, 0);
        return true;
    }
}
