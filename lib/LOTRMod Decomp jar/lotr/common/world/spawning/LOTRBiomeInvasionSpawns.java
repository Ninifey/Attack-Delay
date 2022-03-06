// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotr.common.world.biome.LOTRBiome;

public class LOTRBiomeInvasionSpawns
{
    private LOTRBiome theBiome;
    private Map<LOTREventSpawner.EventChance, List<LOTRInvasions>> invasionsByChance;
    private List<LOTRInvasions> registeredInvasions;
    
    public LOTRBiomeInvasionSpawns(final LOTRBiome biome) {
        this.invasionsByChance = new HashMap<LOTREventSpawner.EventChance, List<LOTRInvasions>>();
        this.registeredInvasions = new ArrayList<LOTRInvasions>();
        this.theBiome = biome;
    }
    
    public void addInvasion(final LOTRInvasions invasion, final LOTREventSpawner.EventChance chance) {
        final List<LOTRInvasions> chanceList = this.getInvasionsForChance(chance);
        if (chanceList.contains(invasion) || this.registeredInvasions.contains(invasion)) {
            FMLLog.warning("LOTR biome %s already has invasion %s registered", new Object[] { this.theBiome.biomeName, invasion.codeName() });
        }
        else {
            chanceList.add(invasion);
            this.registeredInvasions.add(invasion);
        }
    }
    
    public void clearInvasions() {
        this.invasionsByChance.clear();
        this.registeredInvasions.clear();
    }
    
    public List<LOTRInvasions> getInvasionsForChance(final LOTREventSpawner.EventChance chance) {
        List<LOTRInvasions> chanceList = this.invasionsByChance.get(chance);
        if (chanceList == null) {
            chanceList = new ArrayList<LOTRInvasions>();
        }
        this.invasionsByChance.put(chance, chanceList);
        return chanceList;
    }
}
