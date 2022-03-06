// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.util.Iterator;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import java.io.OutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.FileOutputStream;
import net.minecraft.nbt.NBTTagCompound;
import java.io.File;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.EnumCreatureType;
import java.util.Map;

public class LOTRSpawnDamping
{
    public static Map<String, Float> spawnDamping;
    public static String TYPE_NPC;
    public static boolean needsSave;
    
    public static int getCreatureSpawnCap(final EnumCreatureType type, final World world) {
        return getSpawnCap(type.name(), type.getMaxNumberOfCreature(), world);
    }
    
    public static int getNPCSpawnCap(final World world) {
        return getSpawnCap(LOTRSpawnDamping.TYPE_NPC, LOTRDimension.getCurrentDimension(world).spawnCap, world);
    }
    
    public static int getSpawnCap(final String type, final int baseCap, final World world) {
        final int players = world.playerEntities.size();
        return getSpawnCap(type, baseCap, players);
    }
    
    public static int getSpawnCap(final String type, final int baseCap, final int players) {
        final float damp = getSpawnDamping(type);
        float dampFraction = (players - 1) * damp;
        dampFraction = MathHelper.clamp_float(dampFraction, 0.0f, 1.0f);
        final float stationaryPointValue = 0.5f + damp / 2.0f;
        if (dampFraction > stationaryPointValue) {
            dampFraction = stationaryPointValue;
        }
        int capPerPlayer = Math.round(baseCap * (1.0f - dampFraction));
        capPerPlayer = Math.max(capPerPlayer, 1);
        return capPerPlayer;
    }
    
    public static float getSpawnDamping(final String type) {
        float f = 0.0f;
        if (LOTRSpawnDamping.spawnDamping.containsKey(type)) {
            f = LOTRSpawnDamping.spawnDamping.get(type);
        }
        return f;
    }
    
    public static void setSpawnDamping(final EnumCreatureType type, final float damping) {
        setSpawnDamping(type.name(), damping);
    }
    
    public static void setNPCSpawnDamping(final float damping) {
        setSpawnDamping(LOTRSpawnDamping.TYPE_NPC, damping);
    }
    
    public static void setSpawnDamping(final String type, final float damping) {
        LOTRSpawnDamping.spawnDamping.put(type, damping);
        markDirty();
    }
    
    public static int getBaseSpawnCapForInfo(final String type, final World world) {
        if (type.equals(LOTRSpawnDamping.TYPE_NPC)) {
            return LOTRDimension.getCurrentDimension(world).spawnCap;
        }
        final EnumCreatureType creatureType = EnumCreatureType.valueOf(type);
        if (creatureType != null) {
            return creatureType.getMaxNumberOfCreature();
        }
        return -1;
    }
    
    private static void markDirty() {
        LOTRSpawnDamping.needsSave = true;
    }
    
    private static File getDataFile() {
        return new File(LOTRLevelData.getOrCreateLOTRDir(), "spawn_damping.dat");
    }
    
    public static void saveAll() {
        try {
            final File datFile = getDataFile();
            if (!datFile.exists()) {
                CompressedStreamTools.writeCompressed(new NBTTagCompound(), (OutputStream)new FileOutputStream(datFile));
            }
            final NBTTagCompound spawnData = new NBTTagCompound();
            final NBTTagList typeTags = new NBTTagList();
            for (final Map.Entry<String, Float> e : LOTRSpawnDamping.spawnDamping.entrySet()) {
                final String type = e.getKey();
                final float damping = e.getValue();
                final NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("Type", type);
                nbt.setFloat("Damp", damping);
                typeTags.appendTag((NBTBase)nbt);
            }
            spawnData.setTag("Damping", (NBTBase)typeTags);
            LOTRLevelData.saveNBTToFile(datFile, spawnData);
            LOTRSpawnDamping.needsSave = false;
        }
        catch (Exception e2) {
            FMLLog.severe("Error saving LOTR spawn damping", new Object[0]);
            e2.printStackTrace();
        }
    }
    
    public static void loadAll() {
        try {
            final File datFile = getDataFile();
            final NBTTagCompound spawnData = LOTRLevelData.loadNBTFromFile(datFile);
            LOTRSpawnDamping.spawnDamping.clear();
            if (spawnData.hasKey("Damping")) {
                final NBTTagList typeTags = spawnData.getTagList("Damping", 10);
                for (int i = 0; i < typeTags.tagCount(); ++i) {
                    final NBTTagCompound nbt = typeTags.getCompoundTagAt(i);
                    final String type = nbt.getString("Type");
                    final float damping = nbt.getFloat("Damp");
                    if (!StringUtils.isBlank((CharSequence)type)) {
                        LOTRSpawnDamping.spawnDamping.put(type, damping);
                    }
                }
            }
            LOTRSpawnDamping.needsSave = true;
            saveAll();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR spawn damping", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void resetAll() {
        LOTRSpawnDamping.spawnDamping.clear();
        markDirty();
    }
    
    static {
        LOTRSpawnDamping.spawnDamping = new HashMap<String, Float>();
        LOTRSpawnDamping.TYPE_NPC = "lotr_npc";
        LOTRSpawnDamping.needsSave = true;
    }
}
