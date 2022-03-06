// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.entity.Entity;
import java.util.HashMap;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.network.LOTRPacketUpdatePlayerLocations;
import lotr.common.network.LOTRPacketShield;
import lotr.common.network.LOTRPacketAlignment;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import org.apache.commons.lang3.StringUtils;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.WorldServer;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.network.LOTRPacketEnableAlignmentZones;
import java.util.List;
import lotr.common.network.LOTRPacketFTCooldown;
import net.minecraft.server.MinecraftServer;
import lotr.common.network.LOTRPacketLogin;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketPortalPos;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTBase;
import lotr.common.world.spawning.LOTRTravellingTraderSpawner;
import lotr.common.world.spawning.LOTREventSpawner;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.FileInputStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.util.Map;
import net.minecraft.world.EnumDifficulty;

public class LOTRLevelData
{
    private static final int FAST_TRAVEL_COOLDOWN_DEFAULT = 36000;
    private static final int FAST_TRAVEL_COOLDOWN_MIN_DEFAULT = 3600;
    public static int madePortal;
    public static int madeMiddleEarthPortal;
    public static int overworldPortalX;
    public static int overworldPortalY;
    public static int overworldPortalZ;
    public static int middleEarthPortalX;
    public static int middleEarthPortalY;
    public static int middleEarthPortalZ;
    private static int structuresBanned;
    private static int ftCooldownMax;
    private static int ftCooldownMin;
    private static boolean gollumSpawned;
    private static boolean enableAlignmentZones;
    private static float conquestRate;
    public static boolean clientside_thisServer_feastMode;
    public static boolean clientside_thisServer_enchanting;
    public static boolean clientside_thisServer_enchantingLOTR;
    private static EnumDifficulty difficulty;
    private static boolean difficultyLock;
    private static Map<UUID, LOTRPlayerData> playerDataMap;
    public static boolean needsLoad;
    private static boolean needsSave;
    private static Random rand;
    
    public static void markDirty() {
        LOTRLevelData.needsSave = true;
    }
    
    public static boolean anyDataNeedsSave() {
        if (LOTRLevelData.needsSave) {
            return true;
        }
        if (LOTRSpawnDamping.needsSave) {
            return true;
        }
        for (final LOTRPlayerData pd : LOTRLevelData.playerDataMap.values()) {
            if (pd.needsSave()) {
                return true;
            }
        }
        return false;
    }
    
    public static File getOrCreateLOTRDir() {
        final File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    
    private static File getLOTRDat() {
        return new File(getOrCreateLOTRDir(), "LOTR.dat");
    }
    
    private static File getLOTRPlayerDat(final UUID player) {
        final File playerDir = new File(getOrCreateLOTRDir(), "players");
        if (!playerDir.exists()) {
            playerDir.mkdirs();
        }
        return new File(playerDir, player.toString() + ".dat");
    }
    
    public static NBTTagCompound loadNBTFromFile(final File file) throws FileNotFoundException, IOException {
        if (file.exists()) {
            return CompressedStreamTools.readCompressed((InputStream)new FileInputStream(file));
        }
        return new NBTTagCompound();
    }
    
    public static void saveNBTToFile(final File file, final NBTTagCompound nbt) throws FileNotFoundException, IOException {
        CompressedStreamTools.writeCompressed(nbt, (OutputStream)new FileOutputStream(file));
    }
    
    public static void save() {
        try {
            if (LOTRLevelData.needsSave) {
                final File LOTR_dat = getLOTRDat();
                if (!LOTR_dat.exists()) {
                    saveNBTToFile(LOTR_dat, new NBTTagCompound());
                }
                final NBTTagCompound levelData = new NBTTagCompound();
                levelData.setInteger("MadePortal", LOTRLevelData.madePortal);
                levelData.setInteger("MadeMiddlePortal", LOTRLevelData.madeMiddleEarthPortal);
                levelData.setInteger("OverworldX", LOTRLevelData.overworldPortalX);
                levelData.setInteger("OverworldY", LOTRLevelData.overworldPortalY);
                levelData.setInteger("OverworldZ", LOTRLevelData.overworldPortalZ);
                levelData.setInteger("MiddleEarthX", LOTRLevelData.middleEarthPortalX);
                levelData.setInteger("MiddleEarthY", LOTRLevelData.middleEarthPortalY);
                levelData.setInteger("MiddleEarthZ", LOTRLevelData.middleEarthPortalZ);
                levelData.setInteger("StructuresBanned", LOTRLevelData.structuresBanned);
                levelData.setInteger("FastTravel", LOTRLevelData.ftCooldownMax);
                levelData.setInteger("FastTravelMin", LOTRLevelData.ftCooldownMin);
                levelData.setBoolean("GollumSpawned", LOTRLevelData.gollumSpawned);
                levelData.setBoolean("AlignmentZones", LOTRLevelData.enableAlignmentZones);
                levelData.setFloat("ConqRate", LOTRLevelData.conquestRate);
                if (LOTRLevelData.difficulty != null) {
                    levelData.setInteger("SavedDifficulty", LOTRLevelData.difficulty.getDifficultyId());
                }
                levelData.setBoolean("DifficultyLock", LOTRLevelData.difficultyLock);
                final NBTTagCompound travellingTraderData = new NBTTagCompound();
                for (final LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
                    final NBTTagCompound nbt = new NBTTagCompound();
                    trader.writeToNBT(nbt);
                    travellingTraderData.setTag(trader.entityClassName, (NBTBase)nbt);
                }
                levelData.setTag("TravellingTraders", (NBTBase)travellingTraderData);
                LOTRGreyWandererTracker.save(levelData);
                LOTRDate.saveDates(levelData);
                saveNBTToFile(LOTR_dat, levelData);
                LOTRLevelData.needsSave = false;
            }
            int i = 0;
            int j = 0;
            for (final Map.Entry<UUID, LOTRPlayerData> e : LOTRLevelData.playerDataMap.entrySet()) {
                final UUID player = e.getKey();
                final LOTRPlayerData pd = e.getValue();
                if (pd.needsSave()) {
                    saveData(player);
                    ++i;
                }
                ++j;
            }
            if (LOTRSpawnDamping.needsSave) {
                LOTRSpawnDamping.saveAll();
            }
        }
        catch (Exception e2) {
            FMLLog.severe("Error saving LOTR data", new Object[0]);
            e2.printStackTrace();
        }
    }
    
    public static void load() {
        try {
            NBTTagCompound levelData = loadNBTFromFile(getLOTRDat());
            final File oldLOTRDat = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR.dat");
            if (oldLOTRDat.exists()) {
                levelData = loadNBTFromFile(oldLOTRDat);
                oldLOTRDat.delete();
                if (levelData.hasKey("PlayerData")) {
                    final NBTTagList playerDataTags = levelData.getTagList("PlayerData", 10);
                    for (int i = 0; i < playerDataTags.tagCount(); ++i) {
                        final NBTTagCompound nbt = playerDataTags.getCompoundTagAt(i);
                        final UUID player = UUID.fromString(nbt.getString("PlayerUUID"));
                        saveNBTToFile(getLOTRPlayerDat(player), nbt);
                    }
                }
            }
            LOTRLevelData.madePortal = levelData.getInteger("MadePortal");
            LOTRLevelData.madeMiddleEarthPortal = levelData.getInteger("MadeMiddlePortal");
            LOTRLevelData.overworldPortalX = levelData.getInteger("OverworldX");
            LOTRLevelData.overworldPortalY = levelData.getInteger("OverworldY");
            LOTRLevelData.overworldPortalZ = levelData.getInteger("OverworldZ");
            LOTRLevelData.middleEarthPortalX = levelData.getInteger("MiddleEarthX");
            LOTRLevelData.middleEarthPortalY = levelData.getInteger("MiddleEarthY");
            LOTRLevelData.middleEarthPortalZ = levelData.getInteger("MiddleEarthZ");
            LOTRLevelData.structuresBanned = levelData.getInteger("StructuresBanned");
            if (levelData.hasKey("FastTravel")) {
                LOTRLevelData.ftCooldownMax = levelData.getInteger("FastTravel");
            }
            else {
                LOTRLevelData.ftCooldownMax = 36000;
            }
            if (levelData.hasKey("FastTravelMin")) {
                LOTRLevelData.ftCooldownMin = levelData.getInteger("FastTravelMin");
            }
            else {
                LOTRLevelData.ftCooldownMin = 3600;
            }
            LOTRLevelData.gollumSpawned = levelData.getBoolean("GollumSpawned");
            if (levelData.hasKey("AlignmentZones")) {
                LOTRLevelData.enableAlignmentZones = levelData.getBoolean("AlignmentZones");
            }
            else {
                LOTRLevelData.enableAlignmentZones = true;
            }
            if (levelData.hasKey("ConqRate")) {
                LOTRLevelData.conquestRate = levelData.getFloat("ConqRate");
            }
            else {
                LOTRLevelData.conquestRate = 1.0f;
            }
            if (levelData.hasKey("SavedDifficulty")) {
                final int id = levelData.getInteger("SavedDifficulty");
                final EnumDifficulty d = LOTRLevelData.difficulty = EnumDifficulty.getDifficultyEnum(id);
                LOTRMod.proxy.setClientDifficulty(LOTRLevelData.difficulty);
            }
            else {
                LOTRLevelData.difficulty = null;
            }
            LOTRLevelData.difficultyLock = levelData.getBoolean("DifficultyLock");
            final NBTTagCompound travellingTraderData = levelData.getCompoundTag("TravellingTraders");
            for (final LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
                final NBTTagCompound nbt2 = travellingTraderData.getCompoundTag(trader.entityClassName);
                trader.readFromNBT(nbt2);
            }
            LOTRGreyWandererTracker.load(levelData);
            destroyAllPlayerData();
            LOTRDate.loadDates(levelData);
            LOTRSpawnDamping.loadAll();
            LOTRLevelData.needsLoad = false;
            LOTRLevelData.needsSave = true;
            save();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void setMadePortal(final int i) {
        LOTRLevelData.madePortal = i;
        markDirty();
    }
    
    public static void setMadeMiddleEarthPortal(final int i) {
        LOTRLevelData.madeMiddleEarthPortal = i;
        markDirty();
    }
    
    public static void markOverworldPortalLocation(final int i, final int j, final int k) {
        LOTRLevelData.overworldPortalX = i;
        LOTRLevelData.overworldPortalY = j;
        LOTRLevelData.overworldPortalZ = k;
        markDirty();
    }
    
    public static void markMiddleEarthPortalLocation(final int i, final int j, final int k) {
        final LOTRPacketPortalPos packet = new LOTRPacketPortalPos(i, j, k);
        LOTRPacketHandler.networkWrapper.sendToAll((IMessage)packet);
        markDirty();
    }
    
    public static void sendLoginPacket(final EntityPlayerMP entityplayer) {
        final LOTRPacketLogin packet = new LOTRPacketLogin();
        packet.ringPortalX = LOTRLevelData.middleEarthPortalX;
        packet.ringPortalY = LOTRLevelData.middleEarthPortalY;
        packet.ringPortalZ = LOTRLevelData.middleEarthPortalZ;
        packet.ftCooldownMax = LOTRLevelData.ftCooldownMax;
        packet.ftCooldownMin = LOTRLevelData.ftCooldownMin;
        packet.difficulty = LOTRLevelData.difficulty;
        packet.difficultyLocked = LOTRLevelData.difficultyLock;
        packet.alignmentZones = LOTRLevelData.enableAlignmentZones;
        packet.feastMode = LOTRConfig.canAlwaysEat;
        packet.enchanting = LOTRConfig.enchantingVanilla;
        packet.enchantingLOTR = LOTRConfig.enchantingLOTR;
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public static int getFTCooldownMax() {
        return LOTRLevelData.ftCooldownMax;
    }
    
    public static int getFTCooldownMin() {
        return LOTRLevelData.ftCooldownMin;
    }
    
    public static void setFTCooldown(int max, int min) {
        max = Math.max(0, max);
        min = Math.max(0, min);
        if (min > max) {
            min = max;
        }
        LOTRLevelData.ftCooldownMax = max;
        LOTRLevelData.ftCooldownMin = min;
        markDirty();
        if (!LOTRMod.proxy.isClient()) {
            final List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for (int i = 0; i < players.size(); ++i) {
                final EntityPlayerMP entityplayer = players.get(i);
                final LOTRPacketFTCooldown packet = new LOTRPacketFTCooldown(LOTRLevelData.ftCooldownMax, LOTRLevelData.ftCooldownMin);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
            }
        }
    }
    
    public static boolean enableAlignmentZones() {
        return LOTRLevelData.enableAlignmentZones;
    }
    
    public static void setEnableAlignmentZones(final boolean flag) {
        LOTRLevelData.enableAlignmentZones = flag;
        markDirty();
        if (!LOTRMod.proxy.isClient()) {
            final List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for (int i = 0; i < players.size(); ++i) {
                final EntityPlayerMP entityplayer = players.get(i);
                final LOTRPacketEnableAlignmentZones packet = new LOTRPacketEnableAlignmentZones(LOTRLevelData.enableAlignmentZones);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
            }
        }
    }
    
    public static float getConquestRate() {
        return LOTRLevelData.conquestRate;
    }
    
    public static void setConquestRate(final float f) {
        LOTRLevelData.conquestRate = f;
        markDirty();
    }
    
    public static void sendPlayerData(final EntityPlayerMP entityplayer) {
        try {
            final LOTRPlayerData pd = getData((EntityPlayer)entityplayer);
            pd.sendPlayerData(entityplayer);
        }
        catch (Exception e) {
            FMLLog.severe("Failed to send player data to player " + entityplayer.getCommandSenderName(), new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static LOTRPlayerData getData(final EntityPlayer entityplayer) {
        return getData(entityplayer.getUniqueID());
    }
    
    public static LOTRPlayerData getData(final UUID player) {
        LOTRPlayerData pd = LOTRLevelData.playerDataMap.get(player);
        if (pd == null) {
            pd = loadData(player);
            if (pd == null) {
                pd = new LOTRPlayerData(player);
            }
            LOTRLevelData.playerDataMap.put(player, pd);
        }
        return pd;
    }
    
    private static LOTRPlayerData loadData(final UUID player) {
        try {
            final NBTTagCompound nbt = loadNBTFromFile(getLOTRPlayerDat(player));
            final LOTRPlayerData pd = new LOTRPlayerData(player);
            pd.load(nbt);
            return pd;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR player data for %s", new Object[] { player });
            e.printStackTrace();
            return null;
        }
    }
    
    public static void saveData(final UUID player) {
        try {
            final NBTTagCompound nbt = new NBTTagCompound();
            final LOTRPlayerData pd = LOTRLevelData.playerDataMap.get(player);
            pd.save(nbt);
            saveNBTToFile(getLOTRPlayerDat(player), nbt);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR player data for %s", new Object[] { player });
            e.printStackTrace();
        }
    }
    
    private static boolean saveAndClearData(final UUID player) {
        final LOTRPlayerData pd = LOTRLevelData.playerDataMap.get(player);
        if (pd != null) {
            boolean saved = false;
            if (pd.needsSave()) {
                saveData(player);
                saved = true;
            }
            LOTRLevelData.playerDataMap.remove(player);
            return saved;
        }
        FMLLog.severe("Attempted to clear LOTR player data for %s; no data found", new Object[] { player });
        return false;
    }
    
    public static void saveAndClearUnusedPlayerData() {
        final List<UUID> clearing = new ArrayList<UUID>();
        for (final UUID player : LOTRLevelData.playerDataMap.keySet()) {
            boolean foundPlayer = false;
            for (final WorldServer world : MinecraftServer.getServer().worldServers) {
                if (world.func_152378_a(player) != null) {
                    foundPlayer = true;
                    break;
                }
            }
            if (!foundPlayer) {
                clearing.add(player);
            }
        }
        final int numCleared = clearing.size();
        final int sizeBefore = LOTRLevelData.playerDataMap.size();
        int numSaved = 0;
        for (final UUID player2 : clearing) {
            final boolean saved = saveAndClearData(player2);
            if (saved) {
                ++numSaved;
            }
        }
        final int sizeNow = LOTRLevelData.playerDataMap.size();
    }
    
    public static void destroyAllPlayerData() {
        LOTRLevelData.playerDataMap.clear();
    }
    
    public static boolean structuresBanned() {
        return LOTRLevelData.structuresBanned == 1;
    }
    
    public static void setStructuresBanned(final boolean banned) {
        LOTRLevelData.structuresBanned = (banned ? 1 : 0);
        markDirty();
    }
    
    public static void setPlayerBannedForStructures(final String username, final boolean flag) {
        final UUID uuid = UUID.fromString(PreYggdrasilConverter.func_152719_a(username));
        if (uuid != null) {
            getData(uuid).setStructuresBanned(flag);
        }
    }
    
    public static boolean isPlayerBannedForStructures(final EntityPlayer entityplayer) {
        return getData(entityplayer).getStructuresBanned();
    }
    
    public static Set<String> getBannedStructurePlayersUsernames() {
        final Set<String> players = new HashSet<String>();
        for (final UUID uuid : LOTRLevelData.playerDataMap.keySet()) {
            if (getData(uuid).getStructuresBanned()) {
                final GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
                if (StringUtils.isBlank((CharSequence)profile.getName())) {
                    MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
                }
                final String username = profile.getName();
                if (StringUtils.isBlank((CharSequence)username)) {
                    continue;
                }
                players.add(username);
            }
        }
        return players;
    }
    
    public static void sendAlignmentToAllPlayersInWorld(final EntityPlayer entityplayer, final World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            final EntityPlayer worldPlayer = world.playerEntities.get(i);
            final LOTRPacketAlignment packet = new LOTRPacketAlignment(entityplayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)worldPlayer);
        }
    }
    
    public static void sendAllAlignmentsInWorldToPlayer(final EntityPlayer entityplayer, final World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            final EntityPlayer worldPlayer = world.playerEntities.get(i);
            final LOTRPacketAlignment packet = new LOTRPacketAlignment(worldPlayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public static void selectDefaultShieldForPlayer(final EntityPlayer entityplayer) {
        if (getData(entityplayer).getShield() == null) {
            for (final LOTRShields shield : LOTRShields.values()) {
                if (shield.canPlayerWear(entityplayer)) {
                    getData(entityplayer).setShield(shield);
                    return;
                }
            }
        }
    }
    
    public static void sendShieldToAllPlayersInWorld(final EntityPlayer entityplayer, final World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            final EntityPlayer worldPlayer = world.playerEntities.get(i);
            final LOTRPacketShield packet = new LOTRPacketShield(entityplayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)worldPlayer);
        }
    }
    
    public static void sendAllShieldsInWorldToPlayer(final EntityPlayer entityplayer, final World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            final EntityPlayer worldPlayer = world.playerEntities.get(i);
            final LOTRPacketShield packet = new LOTRPacketShield(worldPlayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }
    
    public static void sendPlayerLocationsToPlayer(final EntityPlayer entityplayer, final World world) {
        final LOTRPacketUpdatePlayerLocations packetLocations = new LOTRPacketUpdatePlayerLocations();
        final boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
        final boolean creative = entityplayer.capabilities.isCreativeMode;
        final LOTRPlayerData playerData = getData(entityplayer);
        final List<LOTRFellowship> fellowshipsMapShow = new ArrayList<LOTRFellowship>();
        for (final UUID fsID : playerData.getFellowshipIDs()) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null && !fs.isDisbanded() && fs.getShowMapLocations()) {
                fellowshipsMapShow.add(fs);
            }
        }
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            final EntityPlayer worldPlayer = world.playerEntities.get(i);
            if (worldPlayer != entityplayer) {
                boolean show = !getData(worldPlayer).getHideMapLocation();
                if (LOTRConfig.forceMapLocations == 1) {
                    show = false;
                }
                else if (LOTRConfig.forceMapLocations == 2) {
                    show = true;
                }
                else if (!show) {
                    if (isOp && creative) {
                        show = true;
                    }
                    else if (!isOp && getData(worldPlayer).getAdminHideMap()) {
                        show = false;
                    }
                    else if (!playerData.isSiegeActive()) {
                        for (final LOTRFellowship fs2 : fellowshipsMapShow) {
                            if (fs2.containsPlayer(worldPlayer.getUniqueID())) {
                                show = true;
                                break;
                            }
                        }
                    }
                }
                if (show) {
                    packetLocations.addPlayerLocation(worldPlayer.getGameProfile(), ((Entity)worldPlayer).posX, ((Entity)worldPlayer).posZ);
                }
            }
        }
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packetLocations, (EntityPlayerMP)entityplayer);
    }
    
    public static boolean gollumSpawned() {
        return LOTRLevelData.gollumSpawned;
    }
    
    public static void setGollumSpawned(final boolean flag) {
        LOTRLevelData.gollumSpawned = flag;
        markDirty();
    }
    
    public static EnumDifficulty getSavedDifficulty() {
        return LOTRLevelData.difficulty;
    }
    
    public static void setSavedDifficulty(final EnumDifficulty d) {
        LOTRLevelData.difficulty = d;
        markDirty();
    }
    
    public static boolean isDifficultyLocked() {
        return LOTRLevelData.difficultyLock;
    }
    
    public static void setDifficultyLocked(final boolean flag) {
        LOTRLevelData.difficultyLock = flag;
        markDirty();
    }
    
    public static String getHMSTime(final int i) {
        final int hours = i / 72000;
        final int minutes = i % 72000 / 1200;
        final int seconds = i % 72000 % 1200 / 20;
        return hours + "h " + minutes + "m " + seconds + "s";
    }
    
    static {
        LOTRLevelData.conquestRate = 1.0f;
        LOTRLevelData.difficultyLock = false;
        LOTRLevelData.playerDataMap = new HashMap<UUID, LOTRPlayerData>();
        LOTRLevelData.needsLoad = true;
        LOTRLevelData.needsSave = false;
        LOTRLevelData.rand = new Random();
    }
}
