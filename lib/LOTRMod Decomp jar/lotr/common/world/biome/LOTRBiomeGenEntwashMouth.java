// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenEntwashMouth extends LOTRBiomeGenGondor
{
    public LOTRBiomeGenEntwashMouth(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 2;
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 2;
        super.decorator.flowersPerChunk = 3;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.enableFern = true;
        super.decorator.waterlilyPerChunk = 2;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 4;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 100);
        super.decorator.addTree(LOTRTreeType.OAK_SWAMP, 600);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
        this.registerSwampFlowers();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 500);
        super.decorator.clearVillages();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterEntwashMouth;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return null;
    }
}
