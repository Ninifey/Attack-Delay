// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.world.WorldServer;
import lotr.common.world.LOTRWorldInfo;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.world.World;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLLog;
import java.io.OutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.FileOutputStream;
import net.minecraft.nbt.NBTTagCompound;
import java.io.File;

public class LOTRTime
{
    public static int DAY_LENGTH;
    private static long totalTime;
    private static long worldTime;
    public static boolean needsLoad;
    
    private static File getTimeDat() {
        return new File(LOTRLevelData.getOrCreateLOTRDir(), "LOTRTime.dat");
    }
    
    public static void save() {
        try {
            final File time_dat = getTimeDat();
            if (!time_dat.exists()) {
                CompressedStreamTools.writeCompressed(new NBTTagCompound(), (OutputStream)new FileOutputStream(time_dat));
            }
            final NBTTagCompound timeData = new NBTTagCompound();
            timeData.setLong("LOTRTotalTime", LOTRTime.totalTime);
            timeData.setLong("LOTRWorldTime", LOTRTime.worldTime);
            LOTRLevelData.saveNBTToFile(time_dat, timeData);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR time data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void load() {
        try {
            final NBTTagCompound timeData = LOTRLevelData.loadNBTFromFile(getTimeDat());
            LOTRTime.totalTime = timeData.getLong("LOTRTotalTime");
            LOTRTime.worldTime = timeData.getLong("LOTRWorldTime");
            LOTRTime.needsLoad = false;
            save();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR time data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void setWorldTime(final long time) {
        LOTRTime.worldTime = time;
    }
    
    public static void addWorldTime(final long time) {
        LOTRTime.worldTime += time;
    }
    
    public static void advanceToMorning() {
        final long l = LOTRTime.worldTime + LOTRTime.DAY_LENGTH;
        setWorldTime(l - l % LOTRTime.DAY_LENGTH);
    }
    
    public static void update() {
        final MinecraftServer server = MinecraftServer.getServer();
        final WorldServer overworld = server.worldServerForDimension(0);
        if (LOTRMod.doDayCycle((World)overworld)) {
            ++LOTRTime.worldTime;
        }
        ++LOTRTime.totalTime;
        for (final WorldServer world : server.worldServers) {
            if (((World)world).provider instanceof LOTRWorldProvider) {
                final LOTRWorldInfo worldinfo = (LOTRWorldInfo)world.getWorldInfo();
                worldinfo.lotr_setTotalTime(LOTRTime.totalTime);
                worldinfo.lotr_setWorldTime(LOTRTime.worldTime);
            }
        }
    }
    
    static {
        LOTRTime.DAY_LENGTH = 48000;
        LOTRTime.needsLoad = true;
    }
}
