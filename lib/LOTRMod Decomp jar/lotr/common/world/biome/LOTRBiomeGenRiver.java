// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.spawning.LOTREventSpawner;

public class LOTRBiomeGenRiver extends LOTRBiome
{
    public LOTRBiomeGenRiver(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.npcSpawnList.clear();
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return null;
    }
    
    @Override
    public boolean isRiver() {
        return true;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
}
