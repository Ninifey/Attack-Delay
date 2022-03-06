// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorHouse extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
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
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 2 && k4 <= 6) || (k4 <= 2 && i4 <= 6)) {
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        if (this.isRuined()) {
            this.loadStrScan("harnedor_house_ruined");
        }
        else {
            this.loadStrScan("harnedor_house");
        }
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        if (this.isRuined()) {
            this.setBlockAliasChance("PLANK2", 0.8f);
        }
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        if (!this.isRuined()) {
            this.setBlockAndMetadata(world, 0, 1, 3, super.bedBlock, 0);
            this.setBlockAndMetadata(world, 0, 1, 4, super.bedBlock, 8);
            this.placeWeaponRack(world, 0, 3, -4, 4, this.getRandomHarnedorWeapon(random));
            this.placeWeaponRack(world, 0, 3, 4, 6, this.getRandomHarnedorWeapon(random));
            this.placeChest(world, random, -4, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.HARNEDOR_HOUSE);
            this.placePlate(world, random, 4, 2, 0, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
            this.placePlate(world, random, -1, 2, 4, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
            this.placeMug(world, random, 1, 2, 4, 0, LOTRFoods.HARNEDOR_DRINK);
            for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
                final LOTREntityHarnedhrim haradrim = new LOTREntityHarnedhrim(world);
                this.spawnNPCAndSetHome(haradrim, world, 0, 1, -1, 16);
            }
        }
        return true;
    }
}
