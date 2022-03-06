// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import net.minecraft.world.biome.BiomeGenBase$SpawnListEntry;
import net.minecraft.entity.EntityLivingBase;
import java.util.Collection;
import java.util.Map;
import lotr.common.world.map.LOTRConquestZone;
import lotr.common.util.LOTRLog;
import cpw.mods.fml.common.FMLLog;
import java.util.HashMap;
import lotr.common.world.map.LOTRConquestGrid;
import java.util.Random;
import java.util.Iterator;
import net.minecraft.world.World;
import java.util.ArrayList;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.fac.LOTRFaction;
import java.util.List;

public class LOTRBiomeSpawnList
{
    private final String biomeIdentifier;
    private List<FactionContainer> factionContainers;
    private List<LOTRFaction> presentFactions;
    public float conquestGainRate;
    
    public LOTRBiomeSpawnList(final LOTRBiome biome) {
        this(biome.getClass().getName());
    }
    
    public LOTRBiomeSpawnList(final String s) {
        this.factionContainers = new ArrayList<FactionContainer>();
        this.presentFactions = new ArrayList<LOTRFaction>();
        this.conquestGainRate = 1.0f;
        this.biomeIdentifier = s;
    }
    
    public FactionContainer newFactionList(final int w) {
        return this.newFactionList(w, 1.0f);
    }
    
    public FactionContainer newFactionList(final int w, final float conq) {
        final FactionContainer cont = new FactionContainer(this, w);
        cont.conquestSensitivity = conq;
        this.factionContainers.add(cont);
        return cont;
    }
    
    public static SpawnListContainer entry(final LOTRSpawnList list) {
        return entry(list, 1);
    }
    
    public static SpawnListContainer entry(final LOTRSpawnList list, final int weight) {
        final SpawnListContainer container = new SpawnListContainer(list, weight);
        return container;
    }
    
    public void clear() {
        this.factionContainers.clear();
        this.presentFactions.clear();
        this.conquestGainRate = 1.0f;
    }
    
    private void determineFactions(final World world) {
        if (this.presentFactions.isEmpty() && !this.factionContainers.isEmpty()) {
            for (final FactionContainer facContainer : this.factionContainers) {
                facContainer.determineFaction(world);
                final LOTRFaction fac = facContainer.theFaction;
                if (!this.presentFactions.contains(fac)) {
                    this.presentFactions.add(fac);
                }
            }
        }
    }
    
    public boolean isFactionPresent(final World world, final LOTRFaction fac) {
        this.determineFactions(world);
        return this.presentFactions.contains(fac);
    }
    
    public LOTRSpawnEntry.Instance getRandomSpawnEntry(final Random rand, final World world, final int i, final int j, final int k) {
        this.determineFactions(world);
        final LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(i, k);
        int totalWeight = 0;
        final Map<FactionContainer, Integer> cachedFacWeights = new HashMap<FactionContainer, Integer>();
        final Map<FactionContainer, Float> cachedConqStrengths = new HashMap<FactionContainer, Float>();
        for (final FactionContainer cont : this.factionContainers) {
            if (!cont.isEmpty()) {
                final float conq = cont.getEffectiveConquestStrength(world, zone);
                final int weight = cont.getFactionWeight(conq);
                if (weight <= 0) {
                    continue;
                }
                totalWeight += weight;
                cachedFacWeights.put(cont, weight);
                cachedConqStrengths.put(cont, conq);
            }
        }
        if (totalWeight > 0) {
            FactionContainer chosenFacContainer = null;
            boolean isConquestSpawn = false;
            int w = rand.nextInt(totalWeight);
            for (final FactionContainer cont2 : this.factionContainers) {
                if (!cont2.isEmpty() && cachedFacWeights.containsKey(cont2)) {
                    final int facWeight = cachedFacWeights.get(cont2);
                    w -= facWeight;
                    if (w >= 0) {
                        continue;
                    }
                    chosenFacContainer = cont2;
                    if (facWeight > cont2.baseWeight) {
                        isConquestSpawn = (rand.nextFloat() < (facWeight - cont2.baseWeight) / (float)facWeight);
                        break;
                    }
                    break;
                }
            }
            if (chosenFacContainer != null) {
                final float conq2 = cachedConqStrengths.get(chosenFacContainer);
                final SpawnListContainer spawnList = chosenFacContainer.getRandomSpawnList(rand, conq2);
                if (spawnList == null || spawnList.spawnList == null) {
                    System.out.println("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
                    FMLLog.severe("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName(), new Object[0]);
                    LOTRLog.logger.warn("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
                }
                final LOTRSpawnEntry entry = spawnList.spawnList.getRandomSpawnEntry(rand);
                final int chance = spawnList.spawnChance;
                return new LOTRSpawnEntry.Instance(entry, chance, isConquestSpawn);
            }
        }
        return null;
    }
    
    public List<LOTRSpawnEntry> getAllSpawnEntries(final World world) {
        this.determineFactions(world);
        final List<LOTRSpawnEntry> spawns = new ArrayList<LOTRSpawnEntry>();
        for (final FactionContainer facCont : this.factionContainers) {
            if (!facCont.isEmpty()) {
                for (final SpawnListContainer listCont : facCont.spawnLists) {
                    final LOTRSpawnList list = listCont.spawnList;
                    spawns.addAll(list.getReadOnlyList());
                }
            }
        }
        return spawns;
    }
    
    public boolean containsEntityClassByDefault(final Class<? extends EntityLivingBase> desiredClass, final World world) {
        this.determineFactions(world);
        for (final FactionContainer facCont : this.factionContainers) {
            if (!facCont.isEmpty() && !facCont.isConquestFaction()) {
                for (final SpawnListContainer listCont : facCont.spawnLists) {
                    final LOTRSpawnList list = listCont.spawnList;
                    for (final LOTRSpawnEntry e : list.getReadOnlyList()) {
                        final Class spawnClass = ((BiomeGenBase$SpawnListEntry)e).entityClass;
                        if (desiredClass.isAssignableFrom(spawnClass)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static class FactionContainer
    {
        private final LOTRBiomeSpawnList parent;
        private LOTRFaction theFaction;
        private final List<SpawnListContainer> spawnLists;
        private final int baseWeight;
        private float conquestSensitivity;
        
        public FactionContainer(final LOTRBiomeSpawnList biomeList, final int w) {
            this.spawnLists = new ArrayList<SpawnListContainer>();
            this.conquestSensitivity = 1.0f;
            this.parent = biomeList;
            this.baseWeight = w;
        }
        
        public void add(final SpawnListContainer... lists) {
            for (final SpawnListContainer cont : lists) {
                this.spawnLists.add(cont);
            }
        }
        
        public boolean isEmpty() {
            return this.spawnLists.isEmpty();
        }
        
        public boolean isConquestFaction() {
            return this.baseWeight <= 0;
        }
        
        public void determineFaction(final World world) {
            if (this.theFaction == null) {
                for (final SpawnListContainer cont : this.spawnLists) {
                    final LOTRSpawnList list = cont.spawnList;
                    final LOTRFaction fac = list.getListCommonFaction(world);
                    if (this.theFaction == null) {
                        this.theFaction = fac;
                    }
                    else {
                        if (fac != this.theFaction) {
                            throw new IllegalArgumentException("Faction containers must include spawn lists of only one faction! Mismatched faction " + fac.codeName() + " in biome " + this.parent.biomeIdentifier);
                        }
                        continue;
                    }
                }
            }
        }
        
        public float getEffectiveConquestStrength(final World world, final LOTRConquestZone zone) {
            if (LOTRConquestGrid.conquestEnabled(world) && !zone.isEmpty()) {
                float conqStr = zone.getConquestStrength(this.theFaction, world);
                for (final LOTRFaction allyFac : this.theFaction.getConquestBoostRelations()) {
                    if (!this.parent.isFactionPresent(world, allyFac)) {
                        conqStr += zone.getConquestStrength(allyFac, world) * 0.333f;
                    }
                }
                return conqStr;
            }
            return 0.0f;
        }
        
        public int getFactionWeight(final float conq) {
            if (conq > 0.0f) {
                final float conqFactor = conq * 0.2f * this.conquestSensitivity;
                return this.baseWeight + Math.round(conqFactor);
            }
            return this.baseWeight;
        }
        
        public SpawnListContainer getRandomSpawnList(final Random rand, final float conq) {
            int totalWeight = 0;
            for (final SpawnListContainer cont : this.spawnLists) {
                if (cont.canSpawnAtConquestLevel(conq)) {
                    totalWeight += cont.weight;
                }
            }
            if (totalWeight > 0) {
                SpawnListContainer chosenList = null;
                int w = rand.nextInt(totalWeight);
                for (final SpawnListContainer cont2 : this.spawnLists) {
                    if (cont2.canSpawnAtConquestLevel(conq)) {
                        w -= cont2.weight;
                        if (w < 0) {
                            chosenList = cont2;
                            break;
                        }
                        continue;
                    }
                }
                return chosenList;
            }
            return null;
        }
    }
    
    public static class SpawnListContainer
    {
        private final LOTRSpawnList spawnList;
        private final int weight;
        private int spawnChance;
        private float conquestThreshold;
        
        public SpawnListContainer(final LOTRSpawnList list, final int w) {
            this.spawnChance = 0;
            this.conquestThreshold = -1.0f;
            this.spawnList = list;
            this.weight = w;
        }
        
        public SpawnListContainer setSpawnChance(final int i) {
            this.spawnChance = i;
            return this;
        }
        
        public SpawnListContainer setConquestOnly() {
            return this.setConquestThreshold(0.0f);
        }
        
        public SpawnListContainer setConquestThreshold(final float f) {
            this.conquestThreshold = f;
            return this;
        }
        
        public boolean canSpawnAtConquestLevel(final float conq) {
            return conq > this.conquestThreshold;
        }
        
        public boolean isConquestOnly() {
            return this.conquestThreshold >= 0.0f;
        }
    }
}
