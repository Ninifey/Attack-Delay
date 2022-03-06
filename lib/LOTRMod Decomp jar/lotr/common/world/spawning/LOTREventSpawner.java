// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.entity.IEntityLivingData;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.entity.EntityList;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.LOTREntities;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.ChunkPosition;
import java.util.Random;
import lotr.common.entity.LOTREntityInvasionSpawner;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.Entity;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import java.util.Iterator;
import lotr.common.LOTRGreyWandererTracker;
import net.minecraft.world.EnumDifficulty;
import lotr.common.LOTRConfig;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNPC;
import java.util.List;
import net.minecraft.world.ChunkCoordIntPair;
import java.util.Set;

public class LOTREventSpawner
{
    private static Set<ChunkCoordIntPair> eligibleSpawnChunks;
    private static final int playerRange = 32;
    private static final int expectedChunks = 16;
    public static List<LOTRTravellingTraderSpawner> travellingTraders;
    private static Set<Class> traderClasses;
    
    public static void createTraderSpawner(final Class entityClass) {
        if (!LOTREventSpawner.traderClasses.contains(entityClass)) {
            LOTREventSpawner.traderClasses.add(entityClass);
            LOTREventSpawner.travellingTraders.add(new LOTRTravellingTraderSpawner(entityClass));
        }
    }
    
    public static void performSpawning(final World world) {
        for (final LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
            trader.performSpawning(world);
        }
        if (world.getTotalWorldTime() % 20L == 0L) {
            LOTRSpawnerNPCs.getSpawnableChunksWithPlayerInRange(world, LOTREventSpawner.eligibleSpawnChunks, 32);
            final List<ChunkCoordIntPair> shuffled = LOTRSpawnerNPCs.shuffle(LOTREventSpawner.eligibleSpawnChunks);
            if (LOTRConfig.enableBandits && world.difficultySetting != EnumDifficulty.PEACEFUL) {
                spawnBandits(world, shuffled);
            }
            if (LOTRConfig.enableInvasions && world.difficultySetting != EnumDifficulty.PEACEFUL) {
                spawnInvasions(world, shuffled);
            }
        }
        LOTRGollumSpawner.performSpawning(world);
        LOTRGreyWandererTracker.performSpawning(world);
    }
    
    private static void spawnInvasions(final World world, final List<ChunkCoordIntPair> spawnChunks) {
        final Random rand = world.rand;
    Label_0012:
        while (true) {
            for (final ChunkCoordIntPair chunkCoords : spawnChunks) {
                final ChunkPosition chunkposition = LOTRSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
                if (chunkposition != null) {
                    final int i = chunkposition.chunkPosX;
                    final int k = chunkposition.chunkPosZ;
                    final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                    if (!(biome instanceof LOTRBiome)) {
                        continue;
                    }
                    final LOTRBiomeInvasionSpawns invasionSpawns = ((LOTRBiome)biome).invasionSpawns;
                    for (final EventChance invChance : EventChance.values()) {
                        final List<LOTRInvasions> invList = invasionSpawns.getInvasionsForChance(invChance);
                        if (!invList.isEmpty()) {
                            final LOTRInvasions invasionType = invList.get(rand.nextInt(invList.size()));
                            double chance = invChance.chancesPerSecondPerChunk[16];
                            if (!world.isDaytime() && LOTRWorldProvider.isLunarEclipse()) {
                                chance *= 5.0;
                            }
                            if (rand.nextDouble() < chance) {
                                final int range = 48;
                                final AxisAlignedBB playerCheckBox = AxisAlignedBB.getBoundingBox((double)(i - range), 0.0, (double)(k - range), (double)(i + range), (double)world.getHeight(), (double)(k + range));
                                final List nearbyPlayers = world.selectEntitiesWithinAABB((Class)EntityPlayer.class, playerCheckBox, (IEntitySelector)new IEntitySelector() {
                                    public boolean isEntityApplicable(final Entity entity) {
                                        if (entity instanceof EntityPlayer) {
                                            final EntityPlayer entityplayer = (EntityPlayer)entity;
                                            if (entityplayer.isEntityAlive() && !entityplayer.capabilities.isCreativeMode) {
                                                return LOTRLevelData.getData(entityplayer).getAlignment(invasionType.invasionFaction) < 0.0f;
                                            }
                                        }
                                        return false;
                                    }
                                });
                                if (!nearbyPlayers.isEmpty()) {
                                    for (int attempts = 0; attempts < 16; ++attempts) {
                                        final int i2 = i + MathHelper.getRandomIntegerInRange(rand, -32, 32);
                                        final int k2 = k + MathHelper.getRandomIntegerInRange(rand, -32, 32);
                                        int j1 = world.getHeightValue(i2, k2);
                                        if (j1 > 60) {
                                            final Block block = world.getBlock(i2, j1 - 1, k2);
                                            if ((block == biome.topBlock || block == biome.fillerBlock) && !world.getBlock(i2, j1, k2).isNormalCube() && !world.getBlock(i2, j1 + 1, k2).isNormalCube()) {
                                                j1 += 3 + rand.nextInt(3);
                                                final LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
                                                invasion.setInvasionType(invasionType);
                                                invasion.setLocationAndAngles(i2 + 0.5, (double)j1, k2 + 0.5, 0.0f, 0.0f);
                                                if (invasion.canInvasionSpawnHere()) {
                                                    world.spawnEntityInWorld((Entity)invasion);
                                                    invasion.selectAppropriateBonusFactions();
                                                    invasion.announceInvasionToEnemies();
                                                    continue Label_0012;
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
            break;
        }
    }
    
    private static void spawnBandits(final World world, final List<ChunkCoordIntPair> spawnChunks) {
        final Random rand = world.rand;
        for (final ChunkCoordIntPair chunkCoords : spawnChunks) {
            final ChunkPosition chunkposition = LOTRSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
            if (chunkposition != null) {
                final int i = chunkposition.chunkPosX;
                final int k = chunkposition.chunkPosZ;
                final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                if (!(biome instanceof LOTRBiome)) {
                    continue;
                }
                final LOTRBiome lotrbiome = (LOTRBiome)biome;
                final Class<? extends LOTREntityBandit> banditClass = lotrbiome.getBanditEntityClass();
                final double chance = lotrbiome.getBanditChance().chancesPerSecondPerChunk[16];
                if (chance <= 0.0 || world.rand.nextDouble() >= chance) {
                    continue;
                }
                final int range = 48;
                final AxisAlignedBB playerCheckBox = AxisAlignedBB.getBoundingBox((double)(i - range), 0.0, (double)(k - range), (double)(i + range), (double)world.getHeight(), (double)(k + range));
                final List nearbyPlayers = world.selectEntitiesWithinAABB((Class)EntityPlayer.class, playerCheckBox, LOTRMod.selectNonCreativePlayers());
                if (nearbyPlayers.isEmpty()) {
                    continue;
                }
                int banditsSpawned = 0;
                final int maxBandits = MathHelper.getRandomIntegerInRange(world.rand, 1, 4);
                for (int attempts = 0; attempts < 32; ++attempts) {
                    final int i2 = i + MathHelper.getRandomIntegerInRange(rand, -32, 32);
                    final int k2 = k + MathHelper.getRandomIntegerInRange(rand, -32, 32);
                    final int j1 = world.getHeightValue(i2, k2);
                    if (j1 > 60) {
                        final Block block = world.getBlock(i2, j1 - 1, k2);
                        if ((block == biome.topBlock || block == biome.fillerBlock) && !world.getBlock(i2, j1, k2).isNormalCube() && !world.getBlock(i2, j1 + 1, k2).isNormalCube()) {
                            final String entityName = LOTREntities.getStringFromClass(banditClass);
                            final LOTREntityBandit bandit = (LOTREntityBandit)EntityList.createEntityByName(entityName, world);
                            if (bandit != null) {
                                bandit.setLocationAndAngles(i2 + 0.5, (double)j1, k2 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
                                final Event.Result canSpawn = ForgeEventFactory.canEntitySpawn((EntityLiving)bandit, world, (float)((Entity)bandit).posX, (float)((Entity)bandit).posY, (float)((Entity)bandit).posZ);
                                if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && bandit.getCanSpawnHere())) {
                                    bandit.onSpawnWithEgg(null);
                                    world.spawnEntityInWorld((Entity)bandit);
                                    bandit.isNPCPersistent = false;
                                    if (++banditsSpawned >= maxBandits) {
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
    
    static {
        LOTREventSpawner.eligibleSpawnChunks = new HashSet<ChunkCoordIntPair>();
        LOTREventSpawner.travellingTraders = new ArrayList<LOTRTravellingTraderSpawner>();
        LOTREventSpawner.traderClasses = new HashSet<Class>();
    }
    
    public enum EventChance
    {
        NEVER(0.0f, 0), 
        RARE(0.1f, 3600), 
        UNCOMMON(0.3f, 3600), 
        COMMON(0.9f, 3600), 
        BANDIT_RARE(0.1f, 3600), 
        BANDIT_UNCOMMON(0.3f, 3600), 
        BANDIT_COMMON(0.8f, 3600);
        
        public final double chancePerSecond;
        public final double[] chancesPerSecondPerChunk;
        
        private EventChance(final float prob, final int s) {
            this.chancePerSecond = getChance(prob, s);
            this.chancesPerSecondPerChunk = new double[64];
            for (int i = 0; i < this.chancesPerSecondPerChunk.length; ++i) {
                this.chancesPerSecondPerChunk[i] = getChance(this.chancePerSecond, i);
            }
        }
        
        public static double getChance(final double prob, final int trials) {
            if (prob == 0.0 || trials == 0) {
                return 0.0;
            }
            double d = prob;
            d = 1.0 - d;
            d = Math.pow(d, 1.0 / trials);
            d = 1.0 - d;
            return d;
        }
    }
}
