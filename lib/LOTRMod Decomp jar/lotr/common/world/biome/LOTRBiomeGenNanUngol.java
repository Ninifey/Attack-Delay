// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenMordorSpiderPit;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenNanUngol extends LOTRBiomeGenMordor
{
    public LOTRBiomeGenNanUngol(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 2);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_SPIDERS, 100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        factionList2.add(lists2);
        super.npcSpawnList.conquestGainRate = 0.5f;
        this.clearBiomeVariants();
        super.decorator.generateCobwebs = false;
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenMordorSpiderPit(false), 40);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNanUngol;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.NAN_UNGOL;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("nanUngol");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            final int k2 = k + random.nextInt(16) + 8;
            new LOTRWorldGenWebOfUngoliant(false, 64).generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.75f;
    }
}
