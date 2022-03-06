// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.world.biome.BiomeGenBase$SpawnListEntry;
import java.util.Iterator;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityList;
import lotr.common.entity.LOTREntities;
import net.minecraft.util.MathHelper;
import lotr.common.world.spawning.LOTRSpawnEntry;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.entity.npc.LOTREntityOrc;
import java.util.ArrayList;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenOrcDungeon extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenOrcDungeon(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, final int j, int k) {
        final int xSize = random.nextInt(3) + 2;
        final int ySize = 3;
        final int zSize = random.nextInt(3) + 2;
        int height = 0;
        if (!super.restrictions && super.usingPlayer != null) {
            final int rotation = this.usingPlayerRotation();
            switch (rotation) {
                case 0: {
                    k += zSize + 2;
                    break;
                }
                case 1: {
                    i -= xSize + 2;
                    break;
                }
                case 2: {
                    k -= zSize + 2;
                    break;
                }
                case 3: {
                    i += xSize + 2;
                    break;
                }
            }
        }
        if (super.restrictions) {
            for (int i2 = i - xSize - 1; i2 <= i + xSize + 1; ++i2) {
                for (int j2 = j - 1; j2 <= j + ySize + 1; ++j2) {
                    for (int k2 = k - zSize - 1; k2 <= k + zSize + 1; ++k2) {
                        final Material material = world.getBlock(i2, j2, k2).getMaterial();
                        if (j2 == j - 1 && !material.isSolid()) {
                            return false;
                        }
                        if (j2 == j + ySize + 1 && !material.isSolid()) {
                            return false;
                        }
                        if ((i2 == i - xSize - 1 || i2 == i + xSize + 1 || k2 == k - zSize - 1 || k2 == k + zSize + 1) && j2 == j && world.isAirBlock(i2, j2, k2) && world.isAirBlock(i2, j2 + 1, k2)) {
                            ++height;
                        }
                    }
                }
            }
        }
        else {
            height = 3;
        }
        if (height >= 1 && height <= 5) {
            for (int i2 = i - xSize - 1; i2 <= i + xSize + 1; ++i2) {
                for (int j2 = j + ySize; j2 >= j - 1; --j2) {
                    for (int k2 = k - zSize - 1; k2 <= k + zSize + 1; ++k2) {
                        if (i2 != i - xSize - 1 && j2 != j - 1 && k2 != k - zSize - 1 && i2 != i + xSize + 1 && j2 != j + ySize + 1 && k2 != k + zSize + 1) {
                            this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                        }
                        else if (!super.restrictions || world.getBlock(i2, j2, k2).getMaterial().isSolid()) {
                            if (random.nextInt(4) != 0) {
                                this.func_150516_a(world, i2, j2, k2, Blocks.stonebrick, 1 + random.nextInt(2));
                            }
                            else {
                                this.func_150516_a(world, i2, j2, k2, Blocks.stonebrick, 0);
                            }
                        }
                    }
                }
            }
            for (int chestAttempts = 0; chestAttempts < 2; ++chestAttempts) {
                for (int thisChestAttempts = 0; thisChestAttempts < 3; ++thisChestAttempts) {
                    final int i3 = i + random.nextInt(xSize * 2 + 1) - xSize;
                    final int k3 = k + random.nextInt(zSize * 2 + 1) - zSize;
                    if (world.isAirBlock(i3, j, k3)) {
                        boolean flag = false;
                        if (world.getBlock(i3 - 1, j, k3).getMaterial().isSolid()) {
                            flag = true;
                        }
                        if (world.getBlock(i3 + 1, j, k3).getMaterial().isSolid()) {
                            flag = true;
                        }
                        if (world.getBlock(i3, j, k3 - 1).getMaterial().isSolid()) {
                            flag = true;
                        }
                        if (world.getBlock(i3, j, k3 + 1).getMaterial().isSolid()) {
                            flag = true;
                        }
                        if (flag) {
                            this.func_150516_a(world, i3, j, k3, LOTRMod.chestStone, 0);
                            LOTRChestContents.fillChest(world, random, i3, j, k3, LOTRChestContents.ORC_DUNGEON);
                            break;
                        }
                    }
                }
            }
            final Class<? extends LOTREntityOrc> backupClass = LOTREntityGundabadOrc.class;
            final List<Class<? extends LOTREntityOrc>> biomeClasses = new ArrayList<Class<? extends LOTREntityOrc>>();
            final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
            if (biome instanceof LOTRBiome) {
                final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.provider.worldChunkMgr).getBiomeVariantAt(i, k);
                final LOTRBiomeSpawnList biomeSpawns = ((LOTRBiome)biome).getNPCSpawnList(world, random, i, j, k, variant);
                for (final LOTRSpawnEntry spawnEntry : biomeSpawns.getAllSpawnEntries(world)) {
                    final Class spawnClass = ((BiomeGenBase$SpawnListEntry)spawnEntry).entityClass;
                    if (LOTREntityOrc.class.isAssignableFrom(spawnClass)) {
                        biomeClasses.add(spawnClass);
                    }
                }
            }
            int orcs = MathHelper.getRandomIntegerInRange(random, 3, 6);
            while (orcs > 0) {
                Class<? extends LOTREntityOrc> orcClass = backupClass;
                if (!biomeClasses.isEmpty()) {
                    orcClass = biomeClasses.get(random.nextInt(biomeClasses.size()));
                }
                final LOTREntityOrc orc = (LOTREntityOrc)EntityList.createEntityByName(LOTREntities.getStringFromClass(orcClass), world);
                if (orc.isOrcBombardier()) {
                    continue;
                }
                orc.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 0.5, 0.0f, 0.0f);
                orc.setHomeArea(i, j + 1, k, 4);
                orc.onSpawnWithEgg(null);
                orc.isNPCPersistent = true;
                world.spawnEntityInWorld((Entity)orc);
                --orcs;
            }
        Label_1251:
            for (int pillars = random.nextInt(6), l = 0; l < pillars; ++l) {
                final int i4 = i + random.nextInt(xSize * 2 + 1) - xSize;
                final int k4 = k + random.nextInt(zSize * 2 + 1) - zSize;
                if (i4 != i || k4 != k) {
                    for (int j3 = j + ySize; j3 >= j; --j3) {
                        if (!world.isAirBlock(i4, j3, k4)) {
                            continue Label_1251;
                        }
                    }
                    for (int j3 = j + ySize; j3 >= j; --j3) {
                        if (random.nextInt(4) != 0) {
                            this.func_150516_a(world, i4, j3, k4, Blocks.stonebrick, 1 + random.nextInt(2));
                        }
                        else {
                            this.func_150516_a(world, i4, j3, k4, Blocks.stonebrick, 0);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
