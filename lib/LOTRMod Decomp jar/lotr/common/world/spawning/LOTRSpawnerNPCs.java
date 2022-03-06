// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import net.minecraft.world.biome.BiomeGenBase$SpawnListEntry;
import java.util.HashMap;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.event.ForgeEventFactory;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.block.material.Material;
import lotr.common.LOTRSpawnDamping;
import lotr.common.LOTRConfig;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import net.minecraft.util.AxisAlignedBB;
import java.util.HashSet;
import java.util.ArrayList;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import java.util.Map;
import net.minecraft.world.ChunkCoordIntPair;
import java.util.Set;

public class LOTRSpawnerNPCs
{
    private static final int chunkRange = 7;
    public static final int expectedChunks = 196;
    private static Set<ChunkCoordIntPair> eligibleSpawnChunks;
    private static Map<Integer, Integer> ticksSinceCycle;
    
    public static ChunkPosition getRandomSpawningPointInChunk(final World world, final ChunkCoordIntPair chunkCoords) {
        final int i = chunkCoords.chunkXPos;
        final int k = chunkCoords.chunkZPos;
        return getRandomSpawningPointInChunk(world, i, k);
    }
    
    public static ChunkPosition getRandomSpawningPointInChunk(final World world, final int i, final int k) {
        if (!world.getChunkProvider().chunkExists(i, k)) {
            return null;
        }
        final Chunk chunk = world.getChunkFromChunkCoords(i, k);
        final int i2 = i * 16 + world.rand.nextInt(16);
        final int k2 = k * 16 + world.rand.nextInt(16);
        final int j = world.rand.nextInt((chunk == null) ? world.getActualHeight() : (chunk.getTopFilledSegment() + 16 - 1));
        return new ChunkPosition(i2, j, k2);
    }
    
    public static void getSpawnableChunks(final World world, final Set<ChunkCoordIntPair> set) {
        set.clear();
        for (int l = 0; l < world.playerEntities.size(); ++l) {
            final EntityPlayer entityplayer = world.playerEntities.get(l);
            final int i = MathHelper.floor_double(((Entity)entityplayer).posX / 16.0);
            final int k = MathHelper.floor_double(((Entity)entityplayer).posZ / 16.0);
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -7; k2 <= 7; ++k2) {
                    final ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i + i2, k + k2);
                    set.add(chunkcoordintpair);
                }
            }
        }
    }
    
    public static void getSpawnableChunksWithPlayerInRange(final World world, final Set<ChunkCoordIntPair> set, final int range) {
        getSpawnableChunks(world, set);
        final List<EntityPlayer> validPlayers = new ArrayList<EntityPlayer>();
        for (final Object obj : world.playerEntities) {
            final EntityPlayer entityplayer = (EntityPlayer)obj;
            if (!entityplayer.capabilities.isCreativeMode) {
                validPlayers.add(entityplayer);
            }
        }
        final int height = world.getHeight();
        final Set<ChunkCoordIntPair> removes = new HashSet<ChunkCoordIntPair>();
        for (final ChunkCoordIntPair chunkCoords : set) {
            final int i = chunkCoords.getCenterXPos();
            final int k = chunkCoords.getCenterZPosition();
            final AxisAlignedBB playerCheckBox = AxisAlignedBB.getBoundingBox((double)(i - range), 0.0, (double)(k - range), (double)(i + range), (double)height, (double)(k + range));
            boolean anyPlayers = false;
            for (final EntityPlayer entityplayer2 : validPlayers) {
                if (((Entity)entityplayer2).boundingBox.intersectsWith(playerCheckBox)) {
                    anyPlayers = true;
                    break;
                }
            }
            if (!anyPlayers) {
                removes.add(chunkCoords);
            }
        }
        set.removeAll(removes);
    }
    
    public static List<ChunkCoordIntPair> shuffle(final Set<ChunkCoordIntPair> set) {
        final List<ChunkCoordIntPair> list = new ArrayList<ChunkCoordIntPair>(set);
        Collections.shuffle(list);
        return list;
    }
    
    public static void performSpawning(final World world) {
        final int interval = LOTRConfig.mobSpawnInterval;
        if (interval > 0) {
            int ticks = 0;
            final int dimID = world.provider.dimensionId;
            if (LOTRSpawnerNPCs.ticksSinceCycle.containsKey(dimID)) {
                ticks = LOTRSpawnerNPCs.ticksSinceCycle.get(dimID);
            }
            --ticks;
            LOTRSpawnerNPCs.ticksSinceCycle.put(dimID, ticks);
            if (ticks > 0) {
                return;
            }
            ticks = interval;
            LOTRSpawnerNPCs.ticksSinceCycle.put(dimID, ticks);
        }
        getSpawnableChunks(world, LOTRSpawnerNPCs.eligibleSpawnChunks);
        final ChunkCoordinates spawnPoint = world.getSpawnPoint();
        int totalSpawnCount = countNPCs(world);
        final int maxSpawnCount = LOTRSpawnDamping.getNPCSpawnCap(world) * LOTRSpawnerNPCs.eligibleSpawnChunks.size() / 196;
        Label_0867: {
            if (totalSpawnCount <= maxSpawnCount) {
                for (int cycles = Math.max(1, interval), c = 0; c < cycles; ++c) {
                    final List<ChunkCoordIntPair> shuffled = shuffle(LOTRSpawnerNPCs.eligibleSpawnChunks);
                    for (final ChunkCoordIntPair chunkCoords : shuffled) {
                        final ChunkPosition chunkposition = getRandomSpawningPointInChunk(world, chunkCoords);
                        if (chunkposition != null) {
                            final int i = chunkposition.chunkPosX;
                            final int j = chunkposition.chunkPosY;
                            final int k = chunkposition.chunkPosZ;
                            if (world.getBlock(i, j, k).isNormalCube() || world.getBlock(i, j, k).getMaterial() != Material.air) {
                                continue;
                            }
                            for (int groups = 3, l = 0; l < groups; ++l) {
                                int i2 = i;
                                int j2 = j;
                                int k2 = k;
                                final int range = 5;
                                final int yRange = 0;
                                final int rangeP1 = range + 1;
                                final int yRangeP1 = yRange + 1;
                                final LOTRSpawnEntry.Instance spawnEntryInstance = getRandomSpawnListEntry(world, i2, j2, k2);
                                if (spawnEntryInstance != null) {
                                    final LOTRSpawnEntry spawnEntry = spawnEntryInstance.spawnEntry;
                                    final boolean isConquestSpawn = spawnEntryInstance.isConquestSpawn;
                                    final int spawnCount = MathHelper.getRandomIntegerInRange(world.rand, ((BiomeGenBase$SpawnListEntry)spawnEntry).minGroupCount, ((BiomeGenBase$SpawnListEntry)spawnEntry).maxGroupCount);
                                    final int chance = spawnEntryInstance.spawnChance;
                                    if (chance == 0 || world.rand.nextInt(chance) == 0) {
                                        IEntityLivingData entityData = null;
                                        int spawned = 0;
                                        for (int attempts = spawnCount * 8, a = 0; a < attempts; ++a) {
                                            i2 += world.rand.nextInt(rangeP1) - world.rand.nextInt(rangeP1);
                                            j2 += world.rand.nextInt(yRangeP1) - world.rand.nextInt(yRangeP1);
                                            k2 += world.rand.nextInt(rangeP1) - world.rand.nextInt(rangeP1);
                                            if (world.blockExists(i2, j2, k2) && canNPCSpawnAtLocation(world, i2, j2, k2)) {
                                                final float f = i2 + 0.5f;
                                                final float f2 = (float)j2;
                                                final float f3 = k2 + 0.5f;
                                                if (world.getClosestPlayer((double)f, (double)f2, (double)f3, 24.0) == null) {
                                                    final float f4 = f - spawnPoint.posX;
                                                    final float f5 = f2 - spawnPoint.posY;
                                                    final float f6 = f3 - spawnPoint.posZ;
                                                    final float distSq = f4 * f4 + f5 * f5 + f6 * f6;
                                                    if (distSq >= 576.0f) {
                                                        EntityLiving entity;
                                                        try {
                                                            entity = ((BiomeGenBase$SpawnListEntry)spawnEntry).entityClass.getConstructor(World.class).newInstance(world);
                                                        }
                                                        catch (Exception e) {
                                                            e.printStackTrace();
                                                            return;
                                                        }
                                                        entity.setLocationAndAngles((double)f, (double)f2, (double)f3, world.rand.nextFloat() * 360.0f, 0.0f);
                                                        if (entity instanceof LOTREntityNPC && isConquestSpawn) {
                                                            final LOTREntityNPC npc = (LOTREntityNPC)entity;
                                                            npc.setConquestSpawning(true);
                                                        }
                                                        final Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, world, f, f2, f3);
                                                        if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && entity.getCanSpawnHere())) {
                                                            world.spawnEntityInWorld((Entity)entity);
                                                            if (entity instanceof LOTREntityNPC) {
                                                                final LOTREntityNPC npc2 = (LOTREntityNPC)entity;
                                                                npc2.setConquestSpawning(npc2.isNPCPersistent = false);
                                                            }
                                                            if (!ForgeEventFactory.doSpecialSpawn(entity, world, f, f2, f3)) {
                                                                entityData = entity.onSpawnWithEgg(entityData);
                                                            }
                                                            totalSpawnCount += ((entity instanceof LOTREntityNPC) ? ((LOTREntityNPC)entity).getSpawnCountValue() : 1);
                                                            if (c > 0 && totalSpawnCount > maxSpawnCount) {
                                                                break Label_0867;
                                                            }
                                                            if (++spawned >= spawnCount) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static int countNPCs(final World world) {
        int count = 0;
        for (int i = 0; i < world.loadedEntityList.size(); ++i) {
            final Entity entity = world.loadedEntityList.get(i);
            if (entity instanceof LOTREntityNPC) {
                final int spawnCountValue = ((LOTREntityNPC)entity).getSpawnCountValue();
                count += spawnCountValue;
            }
        }
        return count;
    }
    
    private static boolean canNPCSpawnAtLocation(final World world, final int i, final int j, final int k) {
        if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)world, i, j - 1, k)) {
            return false;
        }
        final Block block = world.getBlock(i, j - 1, k);
        final int l1 = world.getBlockMetadata(i, j - 1, k);
        final boolean spawnBlock = block.canCreatureSpawn(EnumCreatureType.monster, (IBlockAccess)world, i, j - 1, k);
        return spawnBlock && block != Blocks.bedrock && !world.getBlock(i, j, k).isNormalCube() && !world.getBlock(i, j, k).getMaterial().isLiquid() && !world.getBlock(i, j + 1, k).isNormalCube();
    }
    
    private static LOTRSpawnEntry.Instance getRandomSpawnListEntry(final World world, final int i, final int j, final int k) {
        LOTRBiomeSpawnList spawnlist = null;
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome && world.provider instanceof LOTRWorldProvider) {
            final LOTRBiome lotrbiome = (LOTRBiome)biome;
            final LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)world.provider.worldChunkMgr;
            final LOTRBiomeVariant variant = worldChunkMgr.getBiomeVariantAt(i, k);
            spawnlist = lotrbiome.getNPCSpawnList(world, world.rand, i, j, k, variant);
        }
        if (spawnlist != null) {
            return spawnlist.getRandomSpawnEntry(world.rand, world, i, j, k);
        }
        return null;
    }
    
    static {
        LOTRSpawnerNPCs.eligibleSpawnChunks = new HashSet<ChunkCoordIntPair>();
        LOTRSpawnerNPCs.ticksSinceCycle = new HashMap<Integer, Integer>();
    }
}
