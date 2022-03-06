// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure.LOTRWorldGenHobbitPicnicBench;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import lotr.common.world.structure2.LOTRWorldGenHobbitHouse;
import lotr.common.world.structure2.LOTRWorldGenHobbitHole;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenWhiteDowns extends LOTRBiomeGenShire
{
    private WorldGenerator chalkBoulder;
    
    public LOTRBiomeGenWhiteDowns(final int i, final boolean major) {
        super(i, major);
        this.chalkBoulder = new LOTRWorldGenBoulder(LOTRMod.rock, 5, 1, 3);
        super.fillerBlock = LOTRMod.rock;
        super.fillerBlockMeta = 5;
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10).setSpawnChance(100);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 1).setConquestOnly();
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 3).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 3).setConquestThreshold(100.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists4[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 5);
        factionList4.add(lists4);
        super.npcSpawnList.conquestGainRate = 0.2f;
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        super.biomeColors.resetGrass();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitHole(false), 150);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitHouse(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 600);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenHobbitPicnicBench(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1500);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 1500);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterWhiteDowns;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.SHIRE;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SHIRE.getSubregion("whiteDowns");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(80) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.chalkBoulder.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 5;
    }
}
