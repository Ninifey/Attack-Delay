// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.spawning;

import net.minecraft.world.biome.BiomeGenBase;

public class LOTRSpawnEntry extends BiomeGenBase.SpawnListEntry
{
    public LOTRSpawnEntry(final Class c, final int weight, final int min, final int max) {
        super(c, weight, min, max);
    }
    
    public static class Instance
    {
        public final LOTRSpawnEntry spawnEntry;
        public final int spawnChance;
        public final boolean isConquestSpawn;
        
        public Instance(final LOTRSpawnEntry entry, final int chance, final boolean conquest) {
            this.spawnEntry = entry;
            this.spawnChance = chance;
            this.isConquestSpawn = conquest;
        }
    }
}
