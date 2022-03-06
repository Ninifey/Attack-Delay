// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNomadTentLarge extends LOTRWorldGenNomadStructure
{
    public LOTRWorldGenNomadTentLarge(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -10; i2 <= 10; ++i2) {
                for (int k2 = -7; k2 <= 7; ++k2) {
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
        for (int i3 = -10; i3 <= 10; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
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
        this.loadStrScan("nomad_tent_large");
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
        this.setBlockAndMetadata(world, -3, 1, 4, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 5, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -4, 1, 4, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 1, 5, super.bedBlock, 8);
        this.placeWeaponRack(world, 0, 3, 6, 6, this.getRandomNomadWeapon(random));
        this.placeChest(world, random, -4, 1, -5, LOTRMod.chestBasket, 3, LOTRChestContents.NOMAD_TENT);
        this.placeWallBanner(world, 0, 5, 7, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        for (int nomads = 1 + random.nextInt(3), l = 0; l < nomads; ++l) {
            final LOTREntityNomad nomad = new LOTREntityNomad(world);
            this.spawnNPCAndSetHome(nomad, world, 0, 1, 0, 16);
        }
        return true;
    }
}
