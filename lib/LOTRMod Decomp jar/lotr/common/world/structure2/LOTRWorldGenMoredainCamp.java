// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSavannah;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainCamp extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenMoredainCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        if (super.restrictions) {
            boolean suitableSpawn = false;
            final BiomeGenBase biome = world.getBiomeGenForCoords(super.originX, super.originZ);
            if (biome instanceof LOTRBiomeGenFarHaradSavannah) {
                suitableSpawn = !LOTRBiomeGenFarHaradSavannah.isBiomePopulated(super.originX, super.originY, super.originZ);
            }
            if (!suitableSpawn) {
                return false;
            }
            int minHeight = 0;
            int maxHeight = 0;
            for (int range = 3, i2 = -range; i2 <= range; ++i2) {
                for (int k2 = -range; k2 <= range; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand && block != Blocks.stone) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.hardened_clay, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.moredainTable, 0);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.slabSingle7, 0);
                }
            }
        }
        for (int huts = MathHelper.getRandomIntegerInRange(random, 2, 4), l = 0; l < huts; ++l) {
            final LOTRWorldGenStructureBase2 structure = new LOTRWorldGenMoredainHutHunter(super.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        return true;
    }
    
    private boolean attemptHutSpawn(final LOTRWorldGenStructureBase2 structure, final World world, final Random random) {
        structure.restrictions = super.restrictions;
        structure.usingPlayer = super.usingPlayer;
        for (int l = 0; l < 16; ++l) {
            int x = MathHelper.getRandomIntegerInRange(random, 4, 8);
            int z = MathHelper.getRandomIntegerInRange(random, 4, 8);
            x *= (random.nextBoolean() ? -1 : 1);
            z *= (random.nextBoolean() ? -1 : 1);
            final int spawnX = this.getX(x, z);
            final int spawnZ = this.getZ(x, z);
            final int spawnY = this.getY(this.getTopBlock(world, x, z));
            final int rotation = random.nextInt(4);
            if (structure.generateWithSetRotation(world, random, spawnX, spawnY, spawnZ, rotation)) {
                return true;
            }
        }
        return false;
    }
}
