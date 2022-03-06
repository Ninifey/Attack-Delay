// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityMoredainVillageTrader;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityMoredainHutmaker;
import lotr.common.entity.npc.LOTREntityMoredainHuntsman;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutTrader extends LOTRWorldGenMoredainHut
{
    public LOTRWorldGenMoredainHutTrader(final boolean flag) {
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
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                this.layFoundation(world, i2, k2);
                for (int j2 = 1; j2 <= 6; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 3 || k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.clayBlock, super.clayMeta);
                }
                if ((i3 == 3 && k3 <= 2) || (k3 == 3 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 2, k2, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i2, 3, k2, super.stainedClayBlock, super.stainedClayMeta);
                    this.setBlockAndMetadata(world, i2, 4, k2, super.stainedClayBlock, super.stainedClayMeta);
                }
                if (i3 == 3 && k3 == 3) {
                    for (int j3 = 2; j3 <= 3; ++j3) {
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
                    this.setBlockAndMetadata(world, i2, 5, k2, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                    if (i2 != 0 || k2 != -4) {
                        this.setBlockAndMetadata(world, i2, 3, k2, super.fenceBlock, super.fenceMeta);
                        this.dropFence(world, i2, 1, k2);
                    }
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
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        this.setAir(world, 0, 1, -3);
        this.setAir(world, 0, 2, -3);
        this.setBlockAndMetadata(world, 0, 3, -3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.layFoundation(world, -2, -4);
        this.layFoundation(world, 2, -4);
        this.setBlockAndMetadata(world, -2, 1, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 1, -2, LOTRMod.chestBasket, 4);
        this.setBlockAndMetadata(world, -2, 1, -1, super.stainedClayBlock, super.stainedClayMeta);
        this.setBlockAndMetadata(world, -2, 1, 0, super.stainedClayBlock, super.stainedClayMeta);
        this.setBlockAndMetadata(world, -2, 1, 1, LOTRMod.chestBasket, 4);
        this.setBlockAndMetadata(world, 2, 1, -2, LOTRMod.chestBasket, 5);
        this.setBlockAndMetadata(world, 2, 1, -1, super.stainedClayBlock, super.stainedClayMeta);
        this.setBlockAndMetadata(world, 2, 1, 0, super.stainedClayBlock, super.stainedClayMeta);
        this.setBlockAndMetadata(world, 2, 1, 1, LOTRMod.chestBasket, 5);
        for (final int f : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, 2 * f, 1, 2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 1 * f, 1, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 0 * f, 1, 2, LOTRMod.moredainTable, 0);
            this.setBlockAndMetadata(world, 2 * f, 2, 2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 1 * f, 2, 2, LOTRMod.chestBasket, 2);
            this.setBlockAndMetadata(world, 0 * f, 2, 2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, 2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 1 * f, 3, 2, super.plankSlabBlock, super.plankSlabMeta);
            this.setBlockAndMetadata(world, 0 * f, 3, 2, super.plankSlabBlock, super.plankSlabMeta);
        }
        this.setBlockAndMetadata(world, -2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, -2, Blocks.torch, 3);
        LOTREntityMoredainVillageTrader trader;
        if (random.nextBoolean()) {
            trader = new LOTREntityMoredainHuntsman(world);
        }
        else {
            trader = new LOTREntityMoredainHutmaker(world);
        }
        this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
        return true;
    }
}
