// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntitySwan;

public class LOTRBiomeGenSwanfleet extends LOTRBiomeGenEriador
{
    public LOTRBiomeGenSwanfleet(final int i, final boolean major) {
        super(i, major);
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntitySwan.class, 20, 4, 8));
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 1;
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 4;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 8;
        super.decorator.enableFern = true;
        super.decorator.waterlilyPerChunk = 4;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 3;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 500);
        super.decorator.addTree(LOTRTreeType.OAK_SWAMP, 500);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
        super.decorator.addTree(LOTRTreeType.BIRCH, 200);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 100);
        this.registerSwampFlowers();
        super.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.clearInvasions();
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterSwanfleet;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ERIADOR.getSubregion("swanfleet");
    }
}
