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
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityGorcrow;

public class LOTRBiomeGenLongMarshes extends LOTRBiomeGenWilderlandNorth
{
    public LOTRBiomeGenLongMarshes(final int i, final boolean major) {
        super(i, major);
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGorcrow.class, 8, 4, 4));
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 2;
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 2;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.enableFern = true;
        super.decorator.waterlilyPerChunk = 4;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 6;
        super.decorator.addTree(LOTRTreeType.OAK_SWAMP, 1000);
        super.decorator.addTree(LOTRTreeType.GREEN_OAK, 200);
        this.registerSwampFlowers();
        super.biomeColors.setSky(13230818);
        super.biomeColors.setFog(12112325);
        super.biomeColors.setFoggy(true);
        super.biomeColors.setWater(8167049);
        super.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLongMarshes;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHOVANION.getSubregion("longMarshes");
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
