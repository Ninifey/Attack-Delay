// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import lotr.common.world.structure2.LOTRWorldGenHaradRuinedFort;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityCamel;

public class LOTRBiomeGenNearHaradRiverbank extends LOTRBiomeGenNearHaradFertile
{
    public LOTRBiomeGenNearHaradRiverbank(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCamel.class, 20, 4, 4));
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 30).setSpawnChance(1000);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10).setSpawnChance(1000);
        factionList.add(lists);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        super.variantChance = 0.3f;
        super.decorator.treesPerChunk = 0;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 3;
        super.decorator.flowersPerChunk = 1;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
        super.decorator.clearVillages();
        super.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.25f));
        this.clearTravellingTraders();
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.HARAD_DESERT;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNearHarad;
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
