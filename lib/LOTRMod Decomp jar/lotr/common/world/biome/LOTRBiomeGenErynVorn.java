// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenErynVorn extends LOTRBiomeGenEriador
{
    public LOTRBiomeGenErynVorn(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 8, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 10, 1, 4));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 10;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.PINE, 1000);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        this.registerForestFlowers();
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
