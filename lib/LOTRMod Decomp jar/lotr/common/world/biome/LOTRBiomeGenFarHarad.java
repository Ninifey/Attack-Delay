// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.LOTRWorldChunkManager;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.entity.animal.LOTREntityCrocodile;
import lotr.common.entity.animal.LOTREntityDikDik;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.entity.animal.LOTREntityGiraffe;
import lotr.common.entity.animal.LOTREntityLioness;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityLion;

public abstract class LOTRBiomeGenFarHarad extends LOTRBiome
{
    public LOTRBiomeGenFarHarad(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityLion.class, 4, 2, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityLioness.class, 4, 2, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGiraffe.class, 4, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityZebra.class, 8, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityRhino.class, 8, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGemsbok.class, 8, 4, 8));
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 5, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 8, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDikDik.class, 8, 4, 6));
        super.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCrocodile.class, 10, 4, 4));
        super.npcSpawnList.clear();
        super.decorator.biomeGemFactor = 0.75f;
        super.decorator.treesPerChunk = 0;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 12;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.ACACIA, 1000);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 300);
        super.decorator.addTree(LOTRTreeType.BAOBAB, 20);
        super.decorator.addTree(LOTRTreeType.MANGO, 1);
        this.registerHaradFlowers();
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FAR_HARAD;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
        if (variant == LOTRBiomeVariant.RIVER && random.nextInt(3) == 0) {
            final WorldGenerator bananaTree = (WorldGenerator)LOTRTreeType.BANANA.create(false, random);
            for (int bananas = 3 + random.nextInt(8), l = 0; l < bananas; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getTopSolidOrLiquidBlock(i2, k2);
                bananaTree.generate(world, random, i2, j1, k2);
            }
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        final LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
        if (random.nextInt(5) == 0) {
            doubleFlowerGen.setFlowerType(3);
        }
        else {
            doubleFlowerGen.setFlowerType(2);
        }
        return doubleFlowerGen;
    }
}
