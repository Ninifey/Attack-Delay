// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenShireMarshes extends LOTRBiomeGenShire
{
    public LOTRBiomeGenShireMarshes(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 1;
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 2;
        super.decorator.flowersPerChunk = 4;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 6;
        super.decorator.enableFern = true;
        super.decorator.waterlilyPerChunk = 4;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 4;
        super.decorator.addTree(LOTRTreeType.OAK_SWAMP, 2000);
        this.registerSwampFlowers();
        super.biomeColors.resetGrass();
        super.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.75f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}
