// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityMoredain;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityMoredainWarrior;
import net.minecraft.init.Blocks;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutHunter extends LOTRWorldGenMoredainHut
{
    public LOTRWorldGenMoredainHutHunter(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 3;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                this.layFoundation(world, i2, k2);
                for (int j2 = 1; j2 <= 5; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 <= 1 && k3 <= 1) {
                    this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.redClay, 0);
                    if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.thatchFloor, 0);
                    }
                }
                if ((i3 == 2 && k3 <= 1) || (k3 == 2 && i3 <= 1)) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.clayBlock, super.clayMeta);
                    for (int j3 = 2; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.plankBlock, super.plankMeta);
                    }
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 1; j3 <= 2; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 3, k2, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 3, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if ((i3 == 2 && k3 == 0) || (k3 == 2 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.thatchBlock, super.thatchMeta);
                }
                if (i3 + k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if (i3 + k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
            }
        }
        this.setAir(world, 0, 1, -2);
        this.setAir(world, 0, 2, -2);
        this.dropFence(world, -1, 2, -3);
        this.dropFence(world, 1, 2, -3);
        this.setBlockAndMetadata(world, -1, 3, -3, super.thatchSlabBlock, super.thatchSlabMeta);
        this.setBlockAndMetadata(world, 0, 3, -3, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 3, -3, super.thatchSlabBlock, super.thatchSlabMeta);
        this.setBlockAndMetadata(world, -1, 1, 0, LOTRMod.strawBed, 0);
        this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.strawBed, 8);
        this.placeChest(world, random, 1, 1, 1, LOTRMod.chestBasket, 2, LOTRChestContents.MOREDAIN_HUT);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 3);
        final LOTREntityMoredain moredain = new LOTREntityMoredainWarrior(world);
        moredain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(moredain, world, 0, 1, 0, 8);
        return true;
    }
}
