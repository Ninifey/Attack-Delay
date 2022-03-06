// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fac;

import com.mojang.authlib.GameProfile;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.server.MinecraftServer;
import java.util.Collection;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import java.io.File;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map;

public class LOTRFactionBounties
{
    public static Map<LOTRFaction, LOTRFactionBounties> factionBountyMap;
    public static boolean needsLoad;
    public static final int KILL_RECORD_TIME = 3456000;
    public static final int BOUNTY_KILLED_TIME = 864000;
    public final LOTRFaction theFaction;
    private Map<UUID, PlayerData> playerList;
    private boolean needsSave;
    
    public static LOTRFactionBounties forFaction(final LOTRFaction fac) {
        LOTRFactionBounties bounties = LOTRFactionBounties.factionBountyMap.get(fac);
        if (bounties == null) {
            bounties = loadFaction(fac);
            if (bounties == null) {
                bounties = new LOTRFactionBounties(fac);
            }
            LOTRFactionBounties.factionBountyMap.put(fac, bounties);
        }
        return bounties;
    }
    
    public static void updateAll() {
        for (final LOTRFactionBounties fb : LOTRFactionBounties.factionBountyMap.values()) {
            fb.update();
        }
    }
    
    public LOTRFactionBounties(final LOTRFaction f) {
        this.playerList = new HashMap<UUID, PlayerData>();
        this.needsSave = false;
        this.theFaction = f;
    }
    
    public PlayerData forPlayer(final EntityPlayer entityplayer) {
        return this.forPlayer(entityplayer.getUniqueID());
    }
    
    public PlayerData forPlayer(final UUID id) {
        PlayerData pd = this.playerList.get(id);
        if (pd == null) {
            pd = new PlayerData(this, id);
            this.playerList.put(id, pd);
        }
        return pd;
    }
    
    public void update() {
        for (final PlayerData pd : this.playerList.values()) {
            pd.update();
        }
    }
    
    public List<PlayerData> findBountyTargets(final int killAmount) {
        final List<PlayerData> players = new ArrayList<PlayerData>();
        for (final PlayerData pd : this.playerList.values()) {
            if (!pd.recentlyBountyKilled() && pd.getNumKills() >= killAmount) {
                players.add(pd);
            }
        }
        return players;
    }
    
    public void markDirty() {
        this.needsSave = true;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        final NBTTagList playerTags = new NBTTagList();
        for (final Map.Entry<UUID, PlayerData> e : this.playerList.entrySet()) {
            final UUID id = e.getKey();
            final PlayerData pd = e.getValue();
            if (pd.shouldSave()) {
                final NBTTagCompound playerData = new NBTTagCompound();
                playerData.setString("UUID", id.toString());
                pd.writeToNBT(playerData);
                playerTags.appendTag((NBTBase)playerData);
            }
        }
        nbt.setTag("PlayerList", (NBTBase)playerTags);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        this.playerList.clear();
        if (nbt.hasKey("PlayerList")) {
            final NBTTagList playerTags = nbt.getTagList("PlayerList", 10);
            for (int i = 0; i < playerTags.tagCount(); ++i) {
                final NBTTagCompound playerData = playerTags.getCompoundTagAt(i);
                final UUID id = UUID.fromString(playerData.getString("UUID"));
                if (id != null) {
                    final PlayerData pd = new PlayerData(this, id);
                    pd.readFromNBT(playerData);
                    this.playerList.put(id, pd);
                }
            }
        }
    }
    
    public static boolean anyDataNeedsSave() {
        for (final LOTRFactionBounties fb : LOTRFactionBounties.factionBountyMap.values()) {
            if (fb.needsSave) {
                return true;
            }
        }
        return false;
    }
    
    private static File getBountiesDir() {
        final File dir = new File(LOTRLevelData.getOrCreateLOTRDir(), "factionbounties");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    private static File getFactionFile(final LOTRFaction f) {
        return new File(getBountiesDir(), f.codeName() + ".dat");
    }
    
    public static void saveAll() {
        try {
            for (final LOTRFactionBounties fb : LOTRFactionBounties.factionBountyMap.values()) {
                if (fb.needsSave) {
                    saveFaction(fb);
                    fb.needsSave = false;
                }
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR faction bounty data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void loadAll() {
        try {
            LOTRFactionBounties.factionBountyMap.clear();
            LOTRFactionBounties.needsLoad = false;
            saveAll();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR faction bounty data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    private static LOTRFactionBounties loadFaction(final LOTRFaction fac) {
        final File file = getFactionFile(fac);
        try {
            final NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(file);
            if (nbt.hasNoTags()) {
                return null;
            }
            final LOTRFactionBounties fb = new LOTRFactionBounties(fac);
            fb.readFromNBT(nbt);
            return fb;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR faction bounty data for %s", new Object[] { fac.codeName() });
            e.printStackTrace();
            return null;
        }
    }
    
    private static void saveFaction(final LOTRFactionBounties fb) {
        try {
            final NBTTagCompound nbt = new NBTTagCompound();
            fb.writeToNBT(nbt);
            LOTRLevelData.saveNBTToFile(getFactionFile(fb.theFaction), nbt);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR faction bounty data for %s", new Object[] { fb.theFaction.codeName() });
            e.printStackTrace();
        }
    }
    
    static {
        LOTRFactionBounties.factionBountyMap = new HashMap<LOTRFaction, LOTRFactionBounties>();
        LOTRFactionBounties.needsLoad = true;
    }
    
    public static class PlayerData
    {
        public final LOTRFactionBounties bountyList;
        public final UUID playerID;
        private String username;
        private List<KillRecord> killRecords;
        private int recentBountyKilled;
        
        public PlayerData(final LOTRFactionBounties b, final UUID id) {
            this.killRecords = new ArrayList<KillRecord>();
            this.bountyList = b;
            this.playerID = id;
        }
        
        public void recordNewKill() {
            this.killRecords.add(new KillRecord());
            this.markDirty();
        }
        
        public int getNumKills() {
            return this.killRecords.size();
        }
        
        public void recordBountyKilled() {
            this.recentBountyKilled = 864000;
            this.markDirty();
        }
        
        public boolean recentlyBountyKilled() {
            return this.recentBountyKilled > 0;
        }
        
        public void update() {
            boolean minorChanges = false;
            if (this.recentBountyKilled > 0) {
                --this.recentBountyKilled;
                minorChanges = true;
            }
            final List<KillRecord> toRemove = new ArrayList<KillRecord>();
            for (final KillRecord kr : this.killRecords) {
                kr.timeElapsed--;
                if (kr.timeElapsed <= 0) {
                    toRemove.add(kr);
                }
                minorChanges = true;
            }
            if (!toRemove.isEmpty()) {
                this.killRecords.removeAll(toRemove);
                minorChanges = true;
            }
            if (minorChanges && MinecraftServer.getServer().getTickCounter() % 600 == 0) {
                this.markDirty();
            }
        }
        
        private void markDirty() {
            this.bountyList.markDirty();
        }
        
        public boolean shouldSave() {
            return !this.killRecords.isEmpty() || this.recentBountyKilled > 0;
        }
        
        public void writeToNBT(final NBTTagCompound nbt) {
            final NBTTagList recordTags = new NBTTagList();
            for (final KillRecord kr : this.killRecords) {
                final NBTTagCompound killData = new NBTTagCompound();
                kr.writeToNBT(killData);
                recordTags.appendTag((NBTBase)killData);
            }
            nbt.setTag("KillRecords", (NBTBase)recordTags);
            nbt.setInteger("RecentBountyKilled", this.recentBountyKilled);
        }
        
        public void readFromNBT(final NBTTagCompound nbt) {
            this.killRecords.clear();
            if (nbt.hasKey("KillRecords")) {
                final NBTTagList recordTags = nbt.getTagList("KillRecords", 10);
                for (int i = 0; i < recordTags.tagCount(); ++i) {
                    final NBTTagCompound killData = recordTags.getCompoundTagAt(i);
                    final KillRecord kr = new KillRecord();
                    kr.readFromNBT(killData);
                    this.killRecords.add(kr);
                }
            }
            this.recentBountyKilled = nbt.getInteger("RecentBountyKilled");
        }
        
        public String findUsername() {
            if (this.username == null) {
                GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(this.playerID);
                if (profile == null || StringUtils.isBlank((CharSequence)profile.getName())) {
                    final String name = UsernameCache.getLastKnownUsername(this.playerID);
                    if (name != null) {
                        profile = new GameProfile(this.playerID, name);
                    }
                    else {
                        profile = new GameProfile(this.playerID, "");
                        MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
                    }
                }
                this.username = profile.getName();
            }
            return this.username;
        }
        
        private static class KillRecord
        {
            private int timeElapsed;
            
            public KillRecord() {
                this.timeElapsed = 3456000;
            }
            
            public void writeToNBT(final NBTTagCompound nbt) {
                nbt.setInteger("Time", this.timeElapsed);
            }
            
            public void readFromNBT(final NBTTagCompound nbt) {
                this.timeElapsed = nbt.getInteger("Time");
            }
        }
    }
}
