// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenAnduinMouth extends LOTRBiomeGenLebennin
{
    public LOTRBiomeGenAnduinMouth(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 2;
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 5;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.enableFern = true;
        super.decorator.waterlilyPerChunk = 5;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 4;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.OAK, 200);
        super.decorator.addTree(LOTRTreeType.OAK_SWAMP, 500);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
        super.decorator.addTree(LOTRTreeType.BIRCH, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 100);
        this.registerSwampFlowers();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 500);
        super.decorator.clearVillages();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterAnduinMouth;
    }
}
