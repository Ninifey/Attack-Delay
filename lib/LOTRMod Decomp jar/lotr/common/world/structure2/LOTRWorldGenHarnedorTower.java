// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorTower extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
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
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                for (int j3 = 6; j3 <= 16; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("harnedor_tower");
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeSkull(world, random, -3, 5, -3);
        this.placeSkull(world, random, 3, 6, -3);
        this.placeSkull(world, random, 3, 6, 3);
        this.placeSkull(world, random, -3, 7, -2);
        this.placeSkull(world, random, -3, 7, 2);
        this.placeSkull(world, random, 0, 8, 3);
        this.placeSkull(world, random, -3, 10, 3);
        this.placeSkull(world, random, -3, 12, -3);
        this.placeSkull(world, random, 3, 13, 2);
        this.placeChest(world, random, -2, 11, 2, LOTRMod.chestBasket, 2, LOTRChestContents.HARNEDOR_HOUSE);
        for (int warriors = 1 + random.nextInt(2), l = 0; l < warriors; ++l) {
            final LOTREntityHarnedhrim warrior = (random.nextInt(3) == 0) ? new LOTREntityHarnedorArcher(world) : new LOTREntityHarnedorWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 13, 0, 8);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
        respawner.setCheckRanges(6, -16, 4, 4);
        respawner.setSpawnRanges(2, -1, 1, 8);
        this.placeNPCRespawner(respawner, world, 0, 13, 0);
        return true;
    }
}
