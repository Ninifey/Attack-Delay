// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityMoredain;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class LOTRWorldGenMoredainHutVillage extends LOTRWorldGenMoredainHut
{
    private Block bedBlock;
    
    public LOTRWorldGenMoredainHutVillage(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 4;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        if (random.nextBoolean()) {
            this.bedBlock = LOTRMod.strawBed;
        }
        else {
            this.bedBlock = LOTRMod.lionBed;
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                this.layFoundation(world, i2, k2);
                for (int j2 = 1; j2 <= 6; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 <= 2 && k3 <= 2) {
                    this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.redClay, 0);
                    if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.thatchFloor, 0);
                    }
                }
                if (i3 == 3 || k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.clayBlock, super.clayMeta);
                }
                if ((i3 == 3 && k3 <= 2) || (k3 == 3 && i3 <= 2)) {
                    for (int j3 = 2; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                    }
                }
                if (i3 == 3 && k3 == 3) {
                    for (int j3 = 2; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if ((i3 == 4 && k3 <= 2) || (k3 == 4 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if (i3 == 3 && k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if ((i3 == 4 && k3 == 0) || (k3 == 4 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.thatchBlock, super.thatchMeta);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if ((i3 == 3 && k3 <= 2) || (k3 == 3 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if ((i3 == 3 && k3 == 0) || (k3 == 3 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchBlock, super.thatchMeta);
                }
                if ((i3 == 2 && k3 <= 2) || (k3 == 2 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if (i3 + k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if (i3 + k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.thatchBlock, super.thatchMeta);
                }
                if (i3 <= 2 && k3 <= 2 && i3 + k3 >= 3) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchBlock, super.thatchMeta);
                }
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
            }
        }
        this.setAir(world, 0, 1, -3);
        this.setAir(world, 0, 2, -3);
        this.setBlockAndMetadata(world, 0, 3, -3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 4, -4, super.thatchBlock, super.thatchMeta);
        this.setBlockAndMetadata(world, 1, 4, -4, super.thatchBlock, super.thatchMeta);
        this.dropFence(world, -1, 3, -4);
        this.dropFence(world, 1, 3, -4);
        this.setBlockAndMetadata(world, -3, 2, 0, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, -3, 3, 0, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 2, 3, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 3, 3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 3, 2, 0, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 3, 3, 0, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 1, 2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -1, 1, 2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 2, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 1, 1, super.plankBlock, super.plankMeta);
        this.placeChest(world, random, 2, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.MOREDAIN_HUT);
        this.setBlockAndMetadata(world, 2, 1, -1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 2, 1, -2, LOTRMod.moredainTable, 0);
        this.setBlockAndMetadata(world, -1, 1, -1, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -2, 1, -1, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -1, 1, 1, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -2, 1, 1, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, -2, Blocks.torch, 3);
        final LOTREntityMoredain moredain = new LOTREntityMoredain(world);
        this.spawnNPCAndSetHome(moredain, world, 0, 1, 0, 8);
        return true;
    }
}
