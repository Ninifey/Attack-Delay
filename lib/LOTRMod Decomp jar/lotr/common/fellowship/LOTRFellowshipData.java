// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fellowship;

import java.util.HashMap;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import java.io.File;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map;

public class LOTRFellowshipData
{
    private static Map<UUID, LOTRFellowship> fellowshipMap;
    public static boolean needsLoad;
    private static boolean doFullClearing;
    
    public static boolean anyDataNeedsSave() {
        for (final LOTRFellowship fs : LOTRFellowshipData.fellowshipMap.values()) {
            if (fs.needsSave()) {
                return true;
            }
        }
        return false;
    }
    
    private static File getFellowshipDir() {
        final File fsDir = new File(LOTRLevelData.getOrCreateLOTRDir(), "fellowships");
        if (!fsDir.exists()) {
            fsDir.mkdirs();
        }
        return fsDir;
    }
    
    private static File getFellowshipDat(final UUID fsID) {
        return new File(getFellowshipDir(), fsID.toString() + ".dat");
    }
    
    public static void saveAll() {
        try {
            for (final LOTRFellowship fs : LOTRFellowshipData.fellowshipMap.values()) {
                if (fs.needsSave()) {
                    saveFellowship(fs);
                }
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR fellowship data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void loadAll() {
        try {
            destroyAllFellowshipData();
            LOTRFellowshipData.needsLoad = false;
            saveAll();
            FMLLog.info("LOTR: Loaded %s fellowships", new Object[] { LOTRFellowshipData.fellowshipMap.size() });
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR fellowship data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void addFellowship(final LOTRFellowship fs) {
        if (!LOTRFellowshipData.fellowshipMap.containsKey(fs.getFellowshipID())) {
            LOTRFellowshipData.fellowshipMap.put(fs.getFellowshipID(), fs);
        }
    }
    
    public static LOTRFellowship getFellowship(final UUID fsID) {
        LOTRFellowship fs = LOTRFellowshipData.fellowshipMap.get(fsID);
        if (fs == null) {
            fs = loadFellowship(fsID);
            if (fs != null) {
                LOTRFellowshipData.fellowshipMap.put(fsID, fs);
            }
        }
        return fs;
    }
    
    private static LOTRFellowship loadFellowship(final UUID fsID) {
        final File fsDat = getFellowshipDat(fsID);
        try {
            final NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(fsDat);
            if (nbt.hasNoTags()) {
                return null;
            }
            final LOTRFellowship fs = new LOTRFellowship(fsID);
            fs.load(nbt);
            if (fs.isDisbanded()) {
                return null;
            }
            return fs;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR fellowship data for %s", new Object[] { fsDat.getName() });
            e.printStackTrace();
            return null;
        }
    }
    
    public static void saveFellowship(final LOTRFellowship fs) {
        try {
            final NBTTagCompound nbt = new NBTTagCompound();
            fs.save(nbt);
            LOTRLevelData.saveNBTToFile(getFellowshipDat(fs.getFellowshipID()), nbt);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR fellowship data for %s", new Object[] { fs.getFellowshipID() });
            e.printStackTrace();
        }
    }
    
    private static void saveAndClearFellowship(final LOTRFellowship fs) {
        if (LOTRFellowshipData.fellowshipMap.containsValue(fs)) {
            saveFellowship(fs);
            LOTRFellowshipData.fellowshipMap.remove(fs.getFellowshipID());
        }
        else {
            FMLLog.severe("Attempted to clear LOTR fellowship data for %s; no data found", new Object[] { fs.getFellowshipID() });
        }
    }
    
    public static void saveAndClearUnusedFellowships() {
        if (LOTRFellowshipData.doFullClearing) {
            final List<LOTRFellowship> clearing = new ArrayList<LOTRFellowship>();
            for (final LOTRFellowship fs : LOTRFellowshipData.fellowshipMap.values()) {
                boolean foundMember = false;
                for (final Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                    final EntityPlayer entityplayer = (EntityPlayer)player;
                    if (fs.containsPlayer(entityplayer.getUniqueID())) {
                        foundMember = true;
                        break;
                    }
                }
                if (!foundMember) {
                    clearing.add(fs);
                }
            }
            for (final LOTRFellowship fs : clearing) {
                saveAndClearFellowship(fs);
            }
        }
    }
    
    public static void destroyAllFellowshipData() {
        LOTRFellowshipData.fellowshipMap.clear();
    }
    
    static {
        LOTRFellowshipData.fellowshipMap = new HashMap<UUID, LOTRFellowship>();
        LOTRFellowshipData.needsLoad = true;
        LOTRFellowshipData.doFullClearing = false;
    }
}
