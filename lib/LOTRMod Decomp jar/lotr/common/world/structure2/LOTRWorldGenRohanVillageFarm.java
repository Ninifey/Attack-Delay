// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRohanFarmhand;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageFarm extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanVillageFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
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
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 4; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 <= 3 && k4 <= 4) {
                    this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 0);
                }
                if (i4 == 0 && k4 == 0) {
                    this.setBlockAndMetadata(world, i3, -1, k3, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i3, -2, k3);
                    this.setBlockAndMetadata(world, i3, 0, k3, Blocks.water, 0);
                    this.setBlockAndMetadata(world, i3, 1, k3, super.logBlock, super.logMeta);
                    this.setBlockAndMetadata(world, i3, 2, k3, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i3, 4, k3, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, i3, 5, k3, Blocks.pumpkin, 2);
                }
                else if (i4 == 3 && k4 == 4) {
                    for (int j2 = 1; j2 <= 2; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.logBlock, super.logMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 3, k3, Blocks.torch, 5);
                }
                else if (i4 == 3 && k4 <= 3) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                }
                else if (i4 <= 2 && i4 % 2 == 0) {
                    if (k4 <= 3) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.farmland, 7);
                        this.setBlockAndMetadata(world, i3, 1, k3, super.cropBlock, super.cropMeta);
                    }
                    if (k4 == 4) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                    }
                }
            }
        }
        for (int farmhands = 1 + random.nextInt(2), l = 0; l < farmhands; ++l) {
            final LOTREntityRohanFarmhand farmhand = new LOTREntityRohanFarmhand(world);
            this.spawnNPCAndSetHome(farmhand, world, random.nextBoolean() ? -1 : 1, 1, 0, 8);
            farmhand.seedsItem = super.seedItem;
        }
        return true;
    }
}
