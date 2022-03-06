// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.FMLLog;
import java.util.HashSet;
import java.util.ArrayList;
import lotr.common.LOTRAchievement;
import lotr.common.network.LOTRPacketConquestGrid;
import lotr.common.fac.LOTRFactionRank;
import com.google.common.math.IntMath;
import net.minecraft.server.MinecraftServer;
import lotr.common.LOTRPlayerData;
import java.util.Iterator;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketConquestNotification;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import lotr.common.LOTRConfig;
import net.minecraft.world.World;
import java.io.File;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class LOTRConquestGrid
{
    private static final int MAP_GRID_POW2 = 3;
    private static final int MAP_GRID_SCALE;
    public static Map<GridCoordPair, LOTRConquestZone> zoneMap;
    private static final LOTRConquestZone dummyZone;
    public static boolean needsLoad;
    private static final Set<GridCoordPair> dirtyZones;
    public static final float MIN_CONQUEST = 0.0f;
    public static final float MAX_CONQUEST_SET = 100000.0f;
    private static Map<GridCoordPair, List<LOTRFaction>> cachedZoneFactions;
    
    private static int worldToGridX(final int i) {
        int mapX = i >> 7;
        mapX += 810;
        return mapX >> 3;
    }
    
    private static int worldToGridZ(final int k) {
        int mapZ = k >> 7;
        mapZ += 730;
        return mapZ >> 3;
    }
    
    private static int gridToMapCoord(final int i) {
        return i << 3;
    }
    
    public static LOTRConquestZone getZoneByWorldCoords(final int i, final int k) {
        final int x = worldToGridX(i);
        final int z = worldToGridZ(k);
        return getZoneByGridCoords(x, z);
    }
    
    public static LOTRConquestZone getZoneByEntityCoords(final Entity entity) {
        final int i = MathHelper.floor_double(entity.posX);
        final int k = MathHelper.floor_double(entity.posZ);
        return getZoneByWorldCoords(i, k);
    }
    
    public static LOTRConquestZone getZoneByGridCoords(final int i, final int k) {
        if (i < 0 || i >= MathHelper.ceiling_float_int(LOTRGenLayerWorld.imageWidth / (float)LOTRConquestGrid.MAP_GRID_SCALE)) {
            return LOTRConquestGrid.dummyZone;
        }
        if (k < 0 || k >= MathHelper.ceiling_float_int(LOTRGenLayerWorld.imageHeight / (float)LOTRConquestGrid.MAP_GRID_SCALE)) {
            return LOTRConquestGrid.dummyZone;
        }
        final GridCoordPair key = new GridCoordPair(i, k);
        LOTRConquestZone zone = LOTRConquestGrid.zoneMap.get(key);
        if (zone == null) {
            final File zoneDat = getZoneDat(key);
            zone = loadZoneFromFile(zoneDat);
            if (zone == null) {
                zone = new LOTRConquestZone(i, k);
            }
            LOTRConquestGrid.zoneMap.put(key, zone);
        }
        return zone;
    }
    
    public static int[] getMinCoordsOnMap(final LOTRConquestZone zone) {
        return new int[] { gridToMapCoord(zone.gridX), gridToMapCoord(zone.gridZ) };
    }
    
    public static int[] getMaxCoordsOnMap(final LOTRConquestZone zone) {
        return new int[] { gridToMapCoord(zone.gridX + 1), gridToMapCoord(zone.gridZ + 1) };
    }
    
    public static boolean conquestEnabled(final World world) {
        return LOTRConfig.enableConquest && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
    }
    
    public static float onConquestKill(final EntityPlayer entityplayer, final LOTRFaction pledgeFaction, final LOTRFaction enemyFaction, final float alignBonus) {
        final World world = ((Entity)entityplayer).worldObj;
        if (!world.isClient && conquestEnabled(world) && LOTRLevelData.getData(entityplayer).getEnableConquestKills() && ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            final LOTRConquestZone centralZone = getZoneByEntityCoords((Entity)entityplayer);
            final float conqAmount = alignBonus * LOTRLevelData.getConquestRate();
            final float conqGain = conqAmount * getConquestGainRate(entityplayer);
            final float conqCleanse = conqAmount;
            return doRadialConquest(world, centralZone, entityplayer, pledgeFaction, enemyFaction, conqGain, conqCleanse);
        }
        return 0.0f;
    }
    
    private static float getConquestGainRate(final EntityPlayer entityplayer) {
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        final BiomeGenBase bgb = ((Entity)entityplayer).worldObj.getBiomeGenForCoords(i, k);
        if (bgb instanceof LOTRBiome) {
            final LOTRBiome biome = (LOTRBiome)bgb;
            return biome.npcSpawnList.conquestGainRate;
        }
        return 1.0f;
    }
    
    public static float doRadialConquest(final World world, final LOTRConquestZone centralZone, final EntityPlayer killingPlayer, final LOTRFaction pledgeFaction, final LOTRFaction enemyFaction, final float conqGain, final float conqCleanse) {
        if (!centralZone.isDummyZone) {
            final int range = 3;
            final float radius = 3.5f;
            float centralConqBonus = 0.0f;
            for (int i1 = -3; i1 <= 3; ++i1) {
                for (int k1 = -3; k1 <= 3; ++k1) {
                    final int distSq = i1 * i1 + k1 * k1;
                    if (distSq <= 12.25f) {
                        final int zoneX = centralZone.gridX + i1;
                        final int zoneZ = centralZone.gridZ + k1;
                        final float dist = MathHelper.sqrt_float((float)distSq);
                        final float frac = 1.0f - dist / 3.5f;
                        final float conqGainHere = frac * conqGain;
                        final float conqCleanseHere = frac * conqCleanse;
                        final LOTRConquestZone zone = getZoneByGridCoords(zoneX, zoneZ);
                        if (!zone.isDummyZone) {
                            boolean doneEnemyCleansing = false;
                            if (enemyFaction != null) {
                                final float enemyConq = zone.getConquestStrength(enemyFaction, world);
                                if (enemyConq > 0.0f) {
                                    zone.addConquestStrength(enemyFaction, -conqCleanseHere, world);
                                    final float newEnemyConq = zone.getConquestStrength(enemyFaction, world);
                                    if (zone == centralZone) {
                                        centralConqBonus = newEnemyConq - enemyConq;
                                    }
                                    if (killingPlayer != null) {
                                        checkNotifyConquest(zone, killingPlayer, enemyFaction, newEnemyConq, enemyConq, true);
                                    }
                                    doneEnemyCleansing = true;
                                }
                            }
                            if (!doneEnemyCleansing && pledgeFaction != null) {
                                final float prevZoneConq = zone.getConquestStrength(pledgeFaction, world);
                                zone.addConquestStrength(pledgeFaction, conqGainHere, world);
                                final float newZoneConq = zone.getConquestStrength(pledgeFaction, world);
                                if (zone == centralZone) {
                                    centralConqBonus = newZoneConq - prevZoneConq;
                                }
                                if (killingPlayer != null) {
                                    checkNotifyConquest(zone, killingPlayer, pledgeFaction, newZoneConq, prevZoneConq, false);
                                }
                            }
                        }
                    }
                }
            }
            return centralConqBonus;
        }
        return 0.0f;
    }
    
    private static void checkNotifyConquest(final LOTRConquestZone zone, final EntityPlayer originPlayer, final LOTRFaction faction, final float newConq, final float prevConq, final boolean isCleansing) {
        final float notifInterval = 50.0f;
        final double notifRange = 200.0;
        if (MathHelper.floor_double((double)(newConq / 50.0f)) != MathHelper.floor_double((double)(prevConq / 50.0f)) || (newConq == 0.0f && prevConq != newConq)) {
            final World world = ((Entity)originPlayer).worldObj;
            final List playerEntities = world.playerEntities;
            for (final Object obj : playerEntities) {
                final EntityPlayerMP player = (EntityPlayerMP)obj;
                final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)player);
                if (player.getDistanceSqToEntity((Entity)originPlayer) <= 40000.0 && getZoneByEntityCoords((Entity)player) == zone) {
                    boolean playerApplicable = false;
                    if (isCleansing) {
                        final LOTRFaction pledgeFac = pd.getPledgeFaction();
                        playerApplicable = (pledgeFac != null && pledgeFac.isBadRelation(faction));
                    }
                    else {
                        playerApplicable = pd.isPledgedTo(faction);
                    }
                    if (!playerApplicable) {
                        continue;
                    }
                    final LOTRPacketConquestNotification pkt = new LOTRPacketConquestNotification(faction, newConq, isCleansing);
                    LOTRPacketHandler.networkWrapper.sendTo((IMessage)pkt, player);
                }
            }
        }
    }
    
    public static void updateZones(final World world) {
        if (conquestEnabled(world)) {
            final MinecraftServer srv = MinecraftServer.getServer();
            if (srv != null) {
                final int tick = srv.getTickCounter();
                final int interval = 6000;
                for (final LOTRConquestZone zone : LOTRConquestGrid.zoneMap.values()) {
                    if (!zone.isEmpty() && IntMath.mod(tick, interval) == IntMath.mod(zone.hashCode(), interval)) {
                        zone.checkForEmptiness(world);
                    }
                }
            }
        }
    }
    
    public static ConquestViewableQuery canPlayerViewConquest(final EntityPlayer entityplayer, final LOTRFaction fac) {
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        final LOTRFaction pledged = pd.getPledgeFaction();
        if (pledged == null) {
            return new ConquestViewableQuery(ConquestViewable.UNPLEDGED, null);
        }
        if (fac == pledged) {
            return ConquestViewableQuery.canView();
        }
        final float align = pd.getAlignment(pledged);
        final LOTRFactionRank pledgeRank = pledged.getPledgeRank();
        if (fac.isAlly(pledged) || fac.isBadRelation(pledged)) {
            return ConquestViewableQuery.canView();
        }
        final LOTRFactionRank higherRank = pledged.getRankNAbove(pledgeRank, 2);
        if (align >= higherRank.alignment) {
            return ConquestViewableQuery.canView();
        }
        return new ConquestViewableQuery(ConquestViewable.NEED_RANK, higherRank);
    }
    
    public static void sendConquestGridTo(final EntityPlayerMP entityplayer, final LOTRFaction fac) {
        final LOTRPacketConquestGrid pkt = new LOTRPacketConquestGrid(fac, LOTRConquestGrid.zoneMap.values(), ((Entity)entityplayer).worldObj);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)pkt, entityplayer);
        final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
        if (fac == pd.getPledgeFaction()) {
            pd.addAchievement(LOTRAchievement.factionConquest);
        }
    }
    
    public static ConquestEffective getConquestEffectIn(final World world, final LOTRConquestZone zone, final LOTRFaction theFaction) {
        if (!LOTRGenLayerWorld.loadedBiomeImage()) {
            new LOTRGenLayerWorld();
        }
        final GridCoordPair gridCoords = GridCoordPair.forZone(zone);
        List<LOTRFaction> cachedFacs = LOTRConquestGrid.cachedZoneFactions.get(gridCoords);
        if (cachedFacs == null) {
            cachedFacs = new ArrayList<LOTRFaction>();
            final List<LOTRBiome> includedBiomes = new ArrayList<LOTRBiome>();
            final int[] mapMin = getMinCoordsOnMap(zone);
            final int[] mapMax = getMaxCoordsOnMap(zone);
            final int mapXMin = mapMin[0];
            final int mapXMax = mapMax[0];
            final int mapZMin = mapMin[1];
            final int mapZMax = mapMax[1];
            for (int i = mapXMin; i < mapXMax; ++i) {
                for (int k = mapZMin; k < mapZMax; ++k) {
                    final LOTRBiome biome = LOTRGenLayerWorld.getBiomeOrOcean(i, k);
                    if (!includedBiomes.contains(biome)) {
                        includedBiomes.add(biome);
                    }
                }
            }
            for (final LOTRFaction fac : LOTRFaction.getPlayableAlignmentFactions()) {
                for (final LOTRBiome biome2 : includedBiomes) {
                    if (biome2.npcSpawnList.isFactionPresent(world, fac)) {
                        cachedFacs.add(fac);
                        break;
                    }
                }
            }
            LOTRConquestGrid.cachedZoneFactions.put(gridCoords, cachedFacs);
        }
        if (cachedFacs.contains(theFaction)) {
            return ConquestEffective.EFFECTIVE;
        }
        for (final LOTRFaction allyFac : theFaction.getConquestBoostRelations()) {
            if (cachedFacs.contains(allyFac)) {
                return ConquestEffective.ALLY_BOOST;
            }
        }
        return ConquestEffective.NO_EFFECT;
    }
    
    private static File getConquestDir() {
        final File dir = new File(LOTRLevelData.getOrCreateLOTRDir(), "conquest_zones");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    private static File getZoneDat(final LOTRConquestZone zone) {
        final GridCoordPair key = GridCoordPair.forZone(zone);
        return getZoneDat(key);
    }
    
    private static File getZoneDat(final GridCoordPair key) {
        return new File(getConquestDir(), key.gridX + "." + key.gridZ + ".dat");
    }
    
    public static void markZoneDirty(final LOTRConquestZone zone) {
        final GridCoordPair key = GridCoordPair.forZone(zone);
        if (LOTRConquestGrid.zoneMap.containsKey(key)) {
            LOTRConquestGrid.dirtyZones.add(key);
        }
    }
    
    public static boolean anyChangedZones() {
        return !LOTRConquestGrid.dirtyZones.isEmpty();
    }
    
    public static void saveChangedZones() {
        try {
            final Set<GridCoordPair> removes = new HashSet<GridCoordPair>();
            for (final GridCoordPair key : LOTRConquestGrid.dirtyZones) {
                final LOTRConquestZone zone = LOTRConquestGrid.zoneMap.get(key);
                if (zone != null) {
                    saveZoneToFile(zone);
                    if (!zone.isEmpty()) {
                        continue;
                    }
                    removes.add(key);
                }
            }
            LOTRConquestGrid.dirtyZones.clear();
            for (final GridCoordPair key : removes) {
                LOTRConquestGrid.zoneMap.remove(key);
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR conquest zones", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void loadAllZones() {
        try {
            LOTRConquestGrid.zoneMap.clear();
            LOTRConquestGrid.dirtyZones.clear();
            final File dir = getConquestDir();
            if (dir.exists()) {
                final File[] listFiles;
                final File[] subfiles = listFiles = dir.listFiles();
                for (final File zoneDat : listFiles) {
                    if (!zoneDat.isDirectory() && zoneDat.getName().endsWith(".dat")) {
                        final LOTRConquestZone zone = loadZoneFromFile(zoneDat);
                        if (zone != null) {
                            final GridCoordPair key = GridCoordPair.forZone(zone);
                            LOTRConquestGrid.zoneMap.put(key, zone);
                        }
                    }
                }
            }
            LOTRConquestGrid.needsLoad = false;
            FMLLog.info("LOTR: Loaded %s conquest zones", new Object[] { LOTRConquestGrid.zoneMap.size() });
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR conquest zones", new Object[0]);
            e.printStackTrace();
        }
    }
    
    private static LOTRConquestZone loadZoneFromFile(final File zoneDat) {
        try {
            final NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(zoneDat);
            if (nbt.hasNoTags()) {
                return null;
            }
            final LOTRConquestZone zone = LOTRConquestZone.readFromNBT(nbt);
            if (zone.isEmpty()) {
                return null;
            }
            return zone;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR conquest zone from %s", new Object[] { zoneDat.getName() });
            e.printStackTrace();
            return null;
        }
    }
    
    public static void saveZoneToFile(final LOTRConquestZone zone) {
        final File zoneDat = getZoneDat(zone);
        try {
            if (zone.isEmpty()) {
                zoneDat.delete();
            }
            else {
                final NBTTagCompound nbt = new NBTTagCompound();
                zone.writeToNBT(nbt);
                LOTRLevelData.saveNBTToFile(zoneDat, nbt);
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR conquest zone to %s", new Object[] { zoneDat.getName() });
            e.printStackTrace();
        }
    }
    
    static {
        MAP_GRID_SCALE = IntMath.pow(2, 3);
        LOTRConquestGrid.zoneMap = new HashMap<GridCoordPair, LOTRConquestZone>();
        dummyZone = new LOTRConquestZone(-999, -999).setDummyZone();
        LOTRConquestGrid.needsLoad = true;
        dirtyZones = new HashSet<GridCoordPair>();
        LOTRConquestGrid.cachedZoneFactions = new HashMap<GridCoordPair, List<LOTRFaction>>();
    }
    
    public enum ConquestViewable
    {
        UNPLEDGED, 
        CAN_VIEW, 
        NEED_RANK;
    }
    
    public static class ConquestViewableQuery
    {
        public final ConquestViewable result;
        public final LOTRFactionRank needRank;
        
        public ConquestViewableQuery(final ConquestViewable res, final LOTRFactionRank rank) {
            this.result = res;
            this.needRank = rank;
        }
        
        public static ConquestViewableQuery canView() {
            return new ConquestViewableQuery(ConquestViewable.CAN_VIEW, null);
        }
    }
    
    public enum ConquestEffective
    {
        EFFECTIVE, 
        ALLY_BOOST, 
        NO_EFFECT;
    }
    
    public static class GridCoordPair
    {
        public final int gridX;
        public final int gridZ;
        
        public GridCoordPair(final int i, final int k) {
            this.gridX = i;
            this.gridZ = k;
        }
        
        public static GridCoordPair forZone(final LOTRConquestZone zone) {
            return new GridCoordPair(zone.gridX, zone.gridZ);
        }
        
        @Override
        public int hashCode() {
            final int i = 1664525 * this.gridX + 1013904223;
            final int j = 1664525 * (this.gridZ ^ 0xDEADBEEF) + 1013904223;
            return i ^ j;
        }
        
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof GridCoordPair)) {
                return false;
            }
            final GridCoordPair otherPair = (GridCoordPair)other;
            return this.gridX == otherPair.gridX && this.gridZ == otherPair.gridZ;
        }
    }
}
