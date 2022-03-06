// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityGorcrow;

public class LOTRBiomeGenMirkwoodNorth extends LOTRBiomeGenMirkwood
{
    public LOTRBiomeGenMirkwoodNorth(final int i, final boolean major) {
        super(i, major);
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGorcrow.class, 4, 4, 4));
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 12;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 2;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 6;
        super.decorator.enableFern = true;
        super.decorator.addTree(LOTRTreeType.GREEN_OAK, 1000);
        super.decorator.addTree(LOTRTreeType.GREEN_OAK_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.RED_OAK, 15);
        super.decorator.addTree(LOTRTreeType.RED_OAK_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.OAK, 50);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.MIRK_OAK, 50);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 50);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 20);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 20);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.LARCH, 200);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 400);
        super.decorator.addTree(LOTRTreeType.ASPEN, 50);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MIRKWOOD.getSubregion("north");
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.2f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 4;
    }
}
