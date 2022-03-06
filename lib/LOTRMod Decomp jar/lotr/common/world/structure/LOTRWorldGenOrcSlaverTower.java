// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMordorOrcSlaver;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenOrcSlaverTower extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenOrcSlaverTower(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenNurn))) {
            return false;
        }
        final int height = 5 + random.nextInt(4);
        j += height;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                ++k;
                break;
            }
            case 1: {
                --i;
                break;
            }
            case 2: {
                --k;
                break;
            }
            case 3: {
                ++i;
                break;
            }
        }
        if (super.restrictions) {
            for (int i2 = i - 3; i2 <= i + 3; ++i2) {
                for (int k2 = k - 3; k2 <= k + 3; ++k2) {
                    final int j2 = world.getHeightValue(i2, k2) - 1;
                    final Block l = world.getBlock(i2, j2, k2);
                    if (l != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i - 3; i2 <= i + 3; ++i2) {
            for (int k2 = k - 3; k2 <= k + 3; ++k2) {
                this.func_150516_a(world, i2, j, k2, LOTRMod.planks, 3);
                this.func_150516_a(world, i2, j + 6, k2, LOTRMod.planks, 3);
                if (Math.abs(i2 - i) == 3 || Math.abs(k2 - k) == 3) {
                    this.func_150516_a(world, i2, j + 1, k2, LOTRMod.fence, 3);
                    this.func_150516_a(world, i2, j + 5, k2, LOTRMod.fence, 3);
                    this.func_150516_a(world, i2, j + 7, k2, LOTRMod.fence, 3);
                }
            }
        }
        for (int i2 = i - 3; i2 <= i + 3; i2 += 6) {
            for (int k2 = k - 3; k2 <= k + 3; k2 += 6) {
                for (int j2 = j + 5; (j2 >= j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i2, j2, k2, LOTRMod.wood, 3);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int j3 = j + 2; j3 <= j + 4; ++j3) {
            this.func_150516_a(world, i - 2, j3, k - 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i - 2, j3, k + 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 2, j3, k - 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 2, j3, k + 3, LOTRMod.fence, 3);
            this.func_150516_a(world, i - 3, j3, k - 2, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 3, j3, k - 2, LOTRMod.fence, 3);
            this.func_150516_a(world, i - 3, j3, k + 2, LOTRMod.fence, 3);
            this.func_150516_a(world, i + 3, j3, k + 2, LOTRMod.fence, 3);
        }
        for (int j3 = j + 11; (j3 >= j || !LOTRMod.isOpaque(world, i, j3, k)) && j3 >= 0; --j3) {
            this.func_150516_a(world, i, j3, k, LOTRMod.wood, 3);
            this.setGrassToDirt(world, i, j3 - 1, k);
            if (j3 <= j + 6) {
                this.func_150516_a(world, i, j3, k - 1, Blocks.ladder, 2);
            }
        }
        this.func_150516_a(world, i, j + 1, k - 1, Blocks.trapdoor, 0);
        this.func_150516_a(world, i, j + 7, k - 1, Blocks.trapdoor, 0);
        this.placeOrcTorch(world, i - 3, j + 8, k - 3);
        this.placeOrcTorch(world, i - 3, j + 8, k + 3);
        this.placeOrcTorch(world, i + 3, j + 8, k - 3);
        this.placeOrcTorch(world, i + 3, j + 8, k + 3);
        this.func_150516_a(world, i, j + 12, k, LOTRMod.fence, 3);
        this.func_150516_a(world, i, j + 13, k, LOTRMod.fence, 3);
        this.func_150516_a(world, i, j + 12, k - 1, LOTRMod.fence, 3);
        this.func_150516_a(world, i, j + 12, k + 1, LOTRMod.fence, 3);
        this.func_150516_a(world, i - 1, j + 12, k, LOTRMod.fence, 3);
        this.func_150516_a(world, i + 1, j + 12, k, LOTRMod.fence, 3);
        this.placeOrcTorch(world, i, j + 14, k);
        this.placeOrcTorch(world, i, j + 13, k - 1);
        this.placeOrcTorch(world, i, j + 13, k + 1);
        this.placeOrcTorch(world, i - 1, j + 13, k);
        this.placeOrcTorch(world, i + 1, j + 13, k);
        final LOTREntityMordorOrcSlaver slaver = new LOTREntityMordorOrcSlaver(world);
        slaver.setLocationAndAngles(i + 1.5, (double)(j + 7), k + 1.5, 0.0f, 0.0f);
        slaver.onSpawnWithEgg(null);
        world.spawnEntityInWorld((Entity)slaver);
        slaver.setHomeArea(i, j + 6, k, 12);
        for (int orcs = 2 + random.nextInt(3), m = 0; m < orcs; ++m) {
            final LOTREntityOrc orc = new LOTREntityMordorOrc(world);
            orc.setLocationAndAngles(i + 1.5, (double)(j + 1), k + 1.5, 0.0f, 0.0f);
            orc.onSpawnWithEgg(null);
            orc.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)orc);
            orc.setHomeArea(i, j + 1, k, 8);
        }
        return true;
    }
}
