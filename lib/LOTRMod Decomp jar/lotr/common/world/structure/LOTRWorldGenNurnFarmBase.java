// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenNurnFarmBase extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenNurnFarmBase(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenNurn))) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 8;
                break;
            }
            case 1: {
                i -= 8;
                break;
            }
            case 2: {
                k -= 8;
                break;
            }
            case 3: {
                i += 8;
                break;
            }
        }
        if (super.restrictions) {
            for (int i2 = i - 8; i2 <= i + 8; ++i2) {
                for (int k2 = k - 8; k2 <= k + 8; ++k2) {
                    final int j2 = world.getHeightValue(i2, k2) - 1;
                    if (Math.abs(j2 - j) > 4) {
                        return false;
                    }
                    final Block l = world.getBlock(i2, j2, k2);
                    if (l != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i - 7; i2 <= i + 7; ++i2) {
            for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                    this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                }
                for (int j2 = j; (j2 == j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i2, j2, k2, LOTRMod.brick, 0);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                if (Math.abs(i2 - i) == 7 || Math.abs(k2 - k) == 7) {
                    this.func_150516_a(world, i2, j + 1, k2, LOTRMod.brick, 0);
                    this.func_150516_a(world, i2, j + 2, k2, LOTRMod.wall, 1);
                }
                else if (Math.abs(i2 - i) <= 4 && Math.abs(k2 - k) <= 4) {
                    this.func_150516_a(world, i2, j + 1, k2, LOTRMod.slabSingle, 1);
                }
                else {
                    this.func_150516_a(world, i2, j + 1, k2, LOTRMod.slabSingle, 1);
                }
                if (Math.abs(i2 - i) == 7 && Math.abs(k2 - k) == 7) {
                    this.placeOrcTorch(world, i2, j + 3, k2);
                }
            }
        }
        if (rotation == 0) {
            this.func_150516_a(world, i, j + 1, k - 7, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i, j + 2, k - 7, Blocks.air, 0);
            this.func_150516_a(world, i - 1, j + 3, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 3, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 1, j + 4, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i, j + 4, k - 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 4, k - 7, LOTRMod.wall, 1);
        }
        else if (rotation == 1) {
            this.func_150516_a(world, i + 7, j + 1, k, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i + 7, j + 2, k, Blocks.air, 0);
            this.func_150516_a(world, i + 7, j + 3, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 3, k + 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 4, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 4, k, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 7, j + 4, k + 1, LOTRMod.wall, 1);
        }
        else if (rotation == 2) {
            this.func_150516_a(world, i, j + 1, k + 7, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i, j + 2, k + 7, Blocks.air, 0);
            this.func_150516_a(world, i - 1, j + 3, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 3, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 1, j + 4, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i, j + 4, k + 7, LOTRMod.wall, 1);
            this.func_150516_a(world, i + 1, j + 4, k + 7, LOTRMod.wall, 1);
        }
        else if (rotation == 3) {
            this.func_150516_a(world, i - 7, j + 1, k, LOTRMod.slabSingle, 1);
            this.func_150516_a(world, i - 7, j + 2, k, Blocks.air, 0);
            this.func_150516_a(world, i - 7, j + 3, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 3, k + 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 4, k - 1, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 4, k, LOTRMod.wall, 1);
            this.func_150516_a(world, i - 7, j + 4, k + 1, LOTRMod.wall, 1);
        }
        this.generateCrops(world, random, i, j, k);
        for (int slaves = 2 + random.nextInt(4), m = 0; m < slaves; ++m) {
            final LOTREntityNurnSlave slave = new LOTREntityNurnSlave(world);
            slave.setLocationAndAngles(i + 0.5, (double)(j + 2), k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
            slave.onSpawnWithEgg(null);
            slave.setHomeArea(i, j, k, 8);
            slave.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)slave);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityNurnSlave.class);
        respawner.setCheckRanges(12, -8, 8, 8);
        respawner.setSpawnRanges(6, -2, 2, 8);
        this.placeNPCRespawner(respawner, world, i, j, k);
        return true;
    }
    
    public abstract void generateCrops(final World p0, final Random p1, final int p2, final int p3, final int p4);
}
