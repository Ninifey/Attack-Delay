// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.LOTREntityNomadWarrior;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNomadChieftain;
import net.minecraft.init.Blocks;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenNomadChieftainTent extends LOTRWorldGenNomadStructure
{
    public LOTRWorldGenNomadChieftainTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = LOTRMod.lionBed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -12; i2 <= 12; ++i2) {
                for (int k2 = -8; k2 <= 8; ++k2) {
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
        for (int i3 = -12; i3 <= 12; ++i3) {
            for (int k3 = -8; k3 <= 8; ++k3) {
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
        this.loadStrScan("nomad_tent_chieftain");
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("TENT", super.tentBlock, super.tentMeta);
        this.associateBlockMetaAlias("TENT2", super.tent2Block, super.tent2Meta);
        this.associateBlockMetaAlias("CARPET", super.carpetBlock, super.carpetMeta);
        this.associateBlockMetaAlias("CARPET2", super.carpet2Block, super.carpet2Meta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, -6, 1, 4, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -6, 1, 5, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -5, 1, 4, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -5, 1, 5, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 5, 1, 4, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 5, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 6, 1, 4, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 6, 1, 5, super.bedBlock, 8);
        this.placeChest(world, random, -11, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.NOMAD_TENT);
        this.placeChest(world, random, 11, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.NOMAD_TENT);
        this.placeWeaponRack(world, -5, 3, -5, 4, this.getRandomUmbarWeapon(random));
        this.placeWeaponRack(world, 5, 3, -5, 4, this.getRandomUmbarWeapon(random));
        this.placeMug(world, random, -4, 2, -5, 2, LOTRFoods.NOMAD_DRINK);
        this.placePlateWithCertainty(world, random, -6, 2, -5, LOTRMod.ceramicPlateBlock, LOTRFoods.NOMAD);
        this.placePlateWithCertainty(world, random, 6, 2, -5, LOTRMod.ceramicPlateBlock, LOTRFoods.NOMAD);
        this.placeMug(world, random, 4, 2, -5, 2, LOTRFoods.NOMAD_DRINK);
        this.placeWallBanner(world, 0, 3, 7, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, -5, 4, 6, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        this.placeWallBanner(world, 5, 4, 6, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        this.placeWallBanner(world, -12, 4, 0, LOTRItemBanner.BannerType.HARAD_NOMAD, 1);
        this.placeWallBanner(world, 12, 4, 0, LOTRItemBanner.BannerType.HARAD_NOMAD, 3);
        this.placeWallBanner(world, 0, 5, -8, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        this.setBlockAndMetadata(world, -1, 4, -9, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 1, 4, -9, Blocks.skull, 2);
        final LOTREntityNomadChieftain chief = new LOTREntityNomadChieftain(world);
        this.spawnNPCAndSetHome(chief, world, 0, 1, 0, 8);
        for (int warriors = 2 + random.nextInt(2), l = 0; l < warriors; ++l) {
            final LOTREntityNomad warrior = new LOTREntityNomadWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, random.nextBoolean() ? -6 : 6, 1, 0, 8);
        }
        for (final int i5 : new int[] { -5, 5 }) {
            final int j3 = 1;
            final int k5 = -8;
            if (this.isOpaque(world, i5, j3 - 1, k5) && this.isAir(world, i5, j3, k5)) {
                this.setBlockAndMetadata(world, i5, j3, k5, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, i5, j3 + 1, k5, super.fenceBlock, super.fenceMeta);
                final LOTREntityCamel camel = new LOTREntityCamel(world);
                this.spawnNPCAndSetHome((EntityCreature)camel, world, i5, j3, k5, 0);
                camel.saddleMountForWorldGen();
                camel.detachHome();
                camel.setNomadChestAndCarpet();
                this.leashEntityTo((EntityCreature)camel, world, i5, j3, k5);
            }
        }
        return true;
    }
}
