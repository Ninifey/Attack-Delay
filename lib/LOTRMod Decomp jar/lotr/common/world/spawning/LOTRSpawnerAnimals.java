// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import java.util.HashMap;
import java.util.HashSet;
import lotr.common.entity.animal.LOTRAnimalSpawnConditions;
import net.minecraft.util.MathHelper;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.ChunkPosition;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.SpawnerAnimals;
import lotr.common.LOTRSpawnDamping;
import lotr.common.LOTRConfig;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import java.util.Map;
import net.minecraft.world.ChunkCoordIntPair;
import java.util.Set;

public class LOTRSpawnerAnimals
{
    private static Set<ChunkCoordIntPair> eligibleSpawnChunks;
    private static Map<Integer, Integer> ticksSinceCycle;
    private static Map<Integer, DimInfo> dimInfos;
    private static final int FAIL_THRESHOLD = 10;
    private static final int FAIL_BLOCKINGS = 100;
    
    private static TypeInfo forDimAndType(final World world, final EnumCreatureType type) {
        final int dimID = world.provider.dimensionId;
        DimInfo dimInfo = LOTRSpawnerAnimals.dimInfos.get(dimID);
        if (dimInfo == null) {
            dimInfo = new DimInfo();
            LOTRSpawnerAnimals.dimInfos.put(dimID, dimInfo);
        }
        TypeInfo typeInfo = dimInfo.types.get(type);
        if (typeInfo == null) {
            typeInfo = new TypeInfo();
            dimInfo.types.put(type, typeInfo);
        }
        return typeInfo;
    }
    
    public static int performSpawning(final WorldServer world, final boolean hostiles, final boolean peacefuls, final boolean rareTick) {
        final int interval = rareTick ? 0 : LOTRConfig.mobSpawnInterval;
        if (interval > 0) {
            int ticks = 0;
            final int dimID = ((World)world).provider.dimensionId;
            if (LOTRSpawnerAnimals.ticksSinceCycle.containsKey(dimID)) {
                ticks = LOTRSpawnerAnimals.ticksSinceCycle.get(dimID);
            }
            --ticks;
            LOTRSpawnerAnimals.ticksSinceCycle.put(dimID, ticks);
            if (ticks > 0) {
                return 0;
            }
            ticks = interval;
            LOTRSpawnerAnimals.ticksSinceCycle.put(dimID, ticks);
        }
        if (!hostiles && !peacefuls) {
            return 0;
        }
        int totalSpawned = 0;
        LOTRSpawnerNPCs.getSpawnableChunks((World)world, LOTRSpawnerAnimals.eligibleSpawnChunks);
        final ChunkCoordinates spawnPoint = world.getSpawnPoint();
        for (final EnumCreatureType creatureType : EnumCreatureType.values()) {
            final TypeInfo typeInfo = forDimAndType((World)world, creatureType);
            boolean canSpawnType = true;
            if (creatureType.getPeacefulCreature()) {
                canSpawnType = peacefuls;
            }
            else {
                canSpawnType = hostiles;
            }
            if (creatureType.getAnimal()) {
                canSpawnType = rareTick;
            }
            Label_0953: {
                if (canSpawnType) {
                    int count = world.countEntities(creatureType, true);
                    final int maxCount = LOTRSpawnDamping.getCreatureSpawnCap(creatureType, (World)world) * LOTRSpawnerAnimals.eligibleSpawnChunks.size() / 196;
                    if (count <= maxCount) {
                    Label_0313_Outer:
                        for (int cycles = Math.max(1, interval), c = 0; c < cycles; ++c) {
                            if (typeInfo.blockedCycles > 0) {
                                final TypeInfo typeInfo2 = typeInfo;
                                --typeInfo2.blockedCycles;
                            }
                            else {
                                int newlySpawned = 0;
                                final List<ChunkCoordIntPair> shuffled = LOTRSpawnerNPCs.shuffle(LOTRSpawnerAnimals.eligibleSpawnChunks);
                            Label_0313:
                                while (true) {
                                    for (final ChunkCoordIntPair chunkCoords : shuffled) {
                                        final ChunkPosition chunkposition = LOTRSpawnerNPCs.getRandomSpawningPointInChunk((World)world, chunkCoords);
                                        if (chunkposition != null) {
                                            final int i = chunkposition.chunkPosX;
                                            final int j = chunkposition.chunkPosY;
                                            final int k = chunkposition.chunkPosZ;
                                            if (world.spawnRandomCreature(creatureType, i, j, k) == null) {
                                                continue Label_0313_Outer;
                                            }
                                            if (world.getBlock(i, j, k).isNormalCube() || world.getBlock(i, j, k).getMaterial() != creatureType.getCreatureMaterial()) {
                                                continue Label_0313_Outer;
                                            }
                                            for (int groupsSpawned = 0; groupsSpawned < 3; ++groupsSpawned) {
                                                int i2 = i;
                                                int j2 = j;
                                                int k2 = k;
                                                final int range = 6;
                                                BiomeGenBase.SpawnListEntry spawnEntry = null;
                                                IEntityLivingData entityData = null;
                                                for (int attempts = 0; attempts < 4; ++attempts) {
                                                    i2 += ((World)world).rand.nextInt(range) - ((World)world).rand.nextInt(range);
                                                    j2 += ((World)world).rand.nextInt(1) - ((World)world).rand.nextInt(1);
                                                    k2 += ((World)world).rand.nextInt(range) - ((World)world).rand.nextInt(range);
                                                    if (world.blockExists(i2, j2, k2) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(creatureType, (World)world, i2, j2, k2)) {
                                                        final float f = i2 + 0.5f;
                                                        final float f2 = (float)j2;
                                                        final float f3 = k2 + 0.5f;
                                                        if (world.getClosestPlayer((double)f, (double)f2, (double)f3, 24.0) == null) {
                                                            final float f4 = f - spawnPoint.posX;
                                                            final float f5 = f2 - spawnPoint.posY;
                                                            final float f6 = f3 - spawnPoint.posZ;
                                                            final float distSq = f4 * f4 + f5 * f5 + f6 * f6;
                                                            if (distSq >= 576.0f) {
                                                                if (spawnEntry == null) {
                                                                    spawnEntry = world.spawnRandomCreature(creatureType, i2, j2, k2);
                                                                    if (spawnEntry == null) {
                                                                        continue Label_0313;
                                                                    }
                                                                }
                                                                EntityLiving entity;
                                                                try {
                                                                    entity = spawnEntry.entityClass.getConstructor(World.class).newInstance(world);
                                                                }
                                                                catch (Exception e) {
                                                                    e.printStackTrace();
                                                                    return totalSpawned;
                                                                }
                                                                entity.setLocationAndAngles((double)f, (double)f2, (double)f3, ((World)world).rand.nextFloat() * 360.0f, 0.0f);
                                                                final Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, (World)world, f, f2, f3);
                                                                if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && entity.getCanSpawnHere())) {
                                                                    ++totalSpawned;
                                                                    world.spawnEntityInWorld((Entity)entity);
                                                                    if (!ForgeEventFactory.doSpecialSpawn(entity, (World)world, f, f2, f3)) {
                                                                        entityData = entity.onSpawnWithEgg(entityData);
                                                                    }
                                                                    ++newlySpawned;
                                                                    ++count;
                                                                    if (c > 0 && count > maxCount) {
                                                                        break Label_0953;
                                                                    }
                                                                    if (groupsSpawned >= ForgeEventFactory.getMaxSpawnPackSize(entity)) {
                                                                        continue Label_0313;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                                if (newlySpawned == 0) {
                                    final TypeInfo typeInfo3 = typeInfo;
                                    ++typeInfo3.failedCycles;
                                    if (typeInfo.failedCycles >= 10) {
                                        typeInfo.failedCycles = 0;
                                        typeInfo.blockedCycles = 100;
                                    }
                                }
                                else if (typeInfo.failedCycles > 0) {
                                    final TypeInfo typeInfo4 = typeInfo;
                                    --typeInfo4.failedCycles;
                                }
                            }
                        }
                    }
                }
            }
        }
        return totalSpawned;
    }
    
    public static void worldGenSpawnAnimals(final World world, final LOTRBiome biome, final LOTRBiomeVariant variant, final int i, final int k, final Random rand) {
        final int spawnRange = 16;
        final int spawnFuzz = 5;
        final List spawnList = biome.getSpawnableList(EnumCreatureType.creature);
        if (!spawnList.isEmpty()) {
            while (rand.nextFloat() < biome.getSpawningChance()) {
                final BiomeGenBase.SpawnListEntry spawnEntry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(world.rand, (Collection)spawnList);
                final int count = MathHelper.getRandomIntegerInRange(rand, spawnEntry.minGroupCount, spawnEntry.maxGroupCount);
                IEntityLivingData entityData = null;
                final int packX = i + rand.nextInt(spawnRange);
                final int packZ = k + rand.nextInt(spawnRange);
                int i2 = packX;
                int k2 = packZ;
                for (int l = 0; l < count; ++l) {
                    final int attempts = 4;
                    boolean spawned = false;
                    for (int a = 0; !spawned && a < attempts; ++a) {
                        final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
                        if (SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, world, i2, j1, k2)) {
                            final float f = i2 + 0.5f;
                            final float f2 = (float)j1;
                            final float f3 = k2 + 0.5f;
                            EntityLiving entity;
                            try {
                                entity = spawnEntry.entityClass.getConstructor(World.class).newInstance(world);
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                                continue;
                            }
                            boolean canSpawn = true;
                            if (entity instanceof LOTRAnimalSpawnConditions) {
                                final LOTRAnimalSpawnConditions conditions = (LOTRAnimalSpawnConditions)entity;
                                if (!conditions.canWorldGenSpawnAt(i2, j1, k2, biome, variant)) {
                                    canSpawn = false;
                                }
                            }
                            if (canSpawn) {
                                entity.setLocationAndAngles((double)f, (double)f2, (double)f3, rand.nextFloat() * 360.0f, 0.0f);
                                world.spawnEntityInWorld((Entity)entity);
                                entityData = entity.onSpawnWithEgg(entityData);
                                spawned = true;
                            }
                        }
                        for (i2 += rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz), k2 += rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz); i2 < i || i2 >= i + spawnRange || k2 < k || k2 >= k + spawnRange; i2 = packX + rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz), k2 = packZ + rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz)) {}
                    }
                }
            }
        }
    }
    
    static {
        LOTRSpawnerAnimals.eligibleSpawnChunks = new HashSet<ChunkCoordIntPair>();
        LOTRSpawnerAnimals.ticksSinceCycle = new HashMap<Integer, Integer>();
        LOTRSpawnerAnimals.dimInfos = new HashMap<Integer, DimInfo>();
    }
    
    private static class DimInfo
    {
        public Map<EnumCreatureType, TypeInfo> types;
        
        private DimInfo() {
            this.types = new HashMap<EnumCreatureType, TypeInfo>();
        }
    }
    
    private static class TypeInfo
    {
        public int failedCycles;
        public int blockedCycles;
    }
}
