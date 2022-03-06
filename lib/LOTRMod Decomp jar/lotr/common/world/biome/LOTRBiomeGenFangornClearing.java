// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenFangornClearing extends LOTRBiomeGenFangorn
{
    public LOTRBiomeGenFangornClearing(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
        factionList.add(lists);
        this.clearBiomeVariants();
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 4;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 8;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
}
