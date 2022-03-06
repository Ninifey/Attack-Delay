// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fac;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.StatCollector;
import java.util.HashMap;
import net.minecraft.server.MinecraftServer;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Iterator;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRLevelData;
import java.io.File;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketFactionRelations;
import java.util.Map;

public class LOTRFactionRelations
{
    private static Map<FactionPair, Relation> defaultMap;
    private static Map<FactionPair, Relation> overrideMap;
    public static boolean needsLoad;
    private static boolean needsSave;
    
    private static Relation getFromDefaultMap(final FactionPair key) {
        if (LOTRFactionRelations.defaultMap.containsKey(key)) {
            return LOTRFactionRelations.defaultMap.get(key);
        }
        return Relation.NEUTRAL;
    }
    
    public static Relation getRelations(final LOTRFaction f1, final LOTRFaction f2) {
        if (f1 == LOTRFaction.UNALIGNED || f2 == LOTRFaction.UNALIGNED) {
            return Relation.NEUTRAL;
        }
        if (f1 == LOTRFaction.HOSTILE || f2 == LOTRFaction.HOSTILE) {
            return Relation.MORTAL_ENEMY;
        }
        if (f1 == f2) {
            return Relation.ALLY;
        }
        final FactionPair key = new FactionPair(f1, f2);
        if (LOTRFactionRelations.overrideMap.containsKey(key)) {
            return LOTRFactionRelations.overrideMap.get(key);
        }
        return getFromDefaultMap(key);
    }
    
    public static void setDefaultRelations(final LOTRFaction f1, final LOTRFaction f2, final Relation relation) {
        setRelations(f1, f2, relation, true);
    }
    
    public static void overrideRelations(final LOTRFaction f1, final LOTRFaction f2, final Relation relation) {
        setRelations(f1, f2, relation, false);
    }
    
    private static void setRelations(final LOTRFaction f1, final LOTRFaction f2, final Relation relation, final boolean isDefault) {
        if (f1 == LOTRFaction.UNALIGNED || f2 == LOTRFaction.UNALIGNED) {
            throw new IllegalArgumentException("Cannot alter UNALIGNED!");
        }
        if (f1 == LOTRFaction.HOSTILE || f2 == LOTRFaction.HOSTILE) {
            throw new IllegalArgumentException("Cannot alter HOSTILE!");
        }
        if (f1 == f2) {
            throw new IllegalArgumentException("Cannot alter a faction's relations with itself!");
        }
        final FactionPair key = new FactionPair(f1, f2);
        if (isDefault) {
            if (relation == Relation.NEUTRAL) {
                LOTRFactionRelations.defaultMap.remove(key);
            }
            else {
                LOTRFactionRelations.defaultMap.put(key, relation);
            }
        }
        else {
            final Relation defaultRelation = getFromDefaultMap(key);
            if (relation == defaultRelation) {
                LOTRFactionRelations.overrideMap.remove(key);
                markDirty();
            }
            else {
                LOTRFactionRelations.overrideMap.put(key, relation);
                markDirty();
            }
            final LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.oneEntry(key, relation);
            sendPacketToAll((IMessage)pkt);
        }
    }
    
    public static void resetAllRelations() {
        final boolean wasEmpty = LOTRFactionRelations.overrideMap.isEmpty();
        LOTRFactionRelations.overrideMap.clear();
        if (!wasEmpty) {
            markDirty();
            final LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.reset();
            sendPacketToAll((IMessage)pkt);
        }
    }
    
    public static void markDirty() {
        LOTRFactionRelations.needsSave = true;
    }
    
    public static boolean needsSave() {
        return LOTRFactionRelations.needsSave;
    }
    
    private static File getRelationsFile() {
        return new File(LOTRLevelData.getOrCreateLOTRDir(), "faction_relations.dat");
    }
    
    public static void save() {
        try {
            final File datFile = getRelationsFile();
            if (!datFile.exists()) {
                LOTRLevelData.saveNBTToFile(datFile, new NBTTagCompound());
            }
            final NBTTagCompound facData = new NBTTagCompound();
            final NBTTagList relationTags = new NBTTagList();
            for (final Map.Entry<FactionPair, Relation> e : LOTRFactionRelations.overrideMap.entrySet()) {
                final FactionPair pair = e.getKey();
                final Relation rel = e.getValue();
                final NBTTagCompound nbt = new NBTTagCompound();
                pair.writeToNBT(nbt);
                nbt.setString("Rel", rel.codeName());
                relationTags.appendTag((NBTBase)nbt);
            }
            facData.setTag("Overrides", (NBTBase)relationTags);
            LOTRLevelData.saveNBTToFile(datFile, facData);
            LOTRFactionRelations.needsSave = false;
        }
        catch (Exception e2) {
            FMLLog.severe("Error saving LOTR faction relations", new Object[0]);
            e2.printStackTrace();
        }
    }
    
    public static void load() {
        try {
            final NBTTagCompound facData = LOTRLevelData.loadNBTFromFile(getRelationsFile());
            LOTRFactionRelations.overrideMap.clear();
            final NBTTagList relationTags = facData.getTagList("Overrides", 10);
            for (int i = 0; i < relationTags.tagCount(); ++i) {
                final NBTTagCompound nbt = relationTags.getCompoundTagAt(i);
                final FactionPair pair = FactionPair.readFromNBT(nbt);
                final Relation rel = Relation.forName(nbt.getString("Rel"));
                if (pair != null && rel != null) {
                    LOTRFactionRelations.overrideMap.put(pair, rel);
                }
            }
            LOTRFactionRelations.needsLoad = false;
            save();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR faction relations", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void sendAllRelationsTo(final EntityPlayerMP entityplayer) {
        final LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.fullMap(LOTRFactionRelations.overrideMap);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)pkt, entityplayer);
    }
    
    private static void sendPacketToAll(final IMessage packet) {
        final MinecraftServer srv = MinecraftServer.getServer();
        if (srv != null) {
            for (final Object obj : srv.getConfigurationManager().playerEntityList) {
                final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
                LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
            }
        }
    }
    
    static {
        LOTRFactionRelations.defaultMap = new HashMap<FactionPair, Relation>();
        LOTRFactionRelations.overrideMap = new HashMap<FactionPair, Relation>();
        LOTRFactionRelations.needsLoad = true;
        LOTRFactionRelations.needsSave = false;
    }
    
    public enum Relation
    {
        ALLY, 
        FRIEND, 
        NEUTRAL, 
        ENEMY, 
        MORTAL_ENEMY;
        
        public String codeName() {
            return this.name();
        }
        
        public String getDisplayName() {
            return StatCollector.translateToLocal("lotr.faction.rel." + this.codeName());
        }
        
        public static List<String> listRelationNames() {
            final List<String> names = new ArrayList<String>();
            for (final Relation rel : values()) {
                names.add(rel.codeName());
            }
            return names;
        }
        
        public static Relation forID(final int id) {
            for (final Relation rel : values()) {
                if (rel.ordinal() == id) {
                    return rel;
                }
            }
            return null;
        }
        
        public static Relation forName(final String name) {
            for (final Relation rel : values()) {
                if (rel.codeName().equals(name)) {
                    return rel;
                }
            }
            return null;
        }
    }
    
    public static class FactionPair
    {
        private final LOTRFaction fac1;
        private final LOTRFaction fac2;
        
        public FactionPair(final LOTRFaction f1, final LOTRFaction f2) {
            this.fac1 = f1;
            this.fac2 = f2;
        }
        
        public LOTRFaction getLeft() {
            return this.fac1;
        }
        
        public LOTRFaction getRight() {
            return this.fac2;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof FactionPair) {
                final FactionPair pair = (FactionPair)obj;
                if (this.fac1 == pair.fac1 && this.fac2 == pair.fac2) {
                    return true;
                }
                if (this.fac1 == pair.fac2 && this.fac2 == pair.fac1) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            final int f1 = this.fac1.ordinal();
            final int f2 = this.fac2.ordinal();
            final int lower = Math.min(f1, f2);
            final int upper = Math.max(f1, f2);
            return upper << 16 | lower;
        }
        
        public void writeToNBT(final NBTTagCompound nbt) {
            nbt.setString("FacPair1", this.fac1.codeName());
            nbt.setString("FacPair2", this.fac2.codeName());
        }
        
        public static FactionPair readFromNBT(final NBTTagCompound nbt) {
            final LOTRFaction f1 = LOTRFaction.forName(nbt.getString("FacPair1"));
            final LOTRFaction f2 = LOTRFaction.forName(nbt.getString("FacPair2"));
            if (f1 != null && f2 != null) {
                return new FactionPair(f1, f2);
            }
            return null;
        }
    }
}
