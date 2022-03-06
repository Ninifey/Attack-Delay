// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenMarshLights;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;

public class LOTRBiomeGenDeadMarshes extends LOTRBiome
{
    public LOTRBiomeGenDeadMarshes(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.npcSpawnList.clear();
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.remains, 6, Blocks.dirt), 5.0f, 55, 65);
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariant(LOTRBiomeVariant.SWAMP_LOWLAND);
        super.decorator.sandPerChunk = 0;
        super.decorator.clayPerChunk = 0;
        super.decorator.quagmirePerChunk = 1;
        super.decorator.treesPerChunk = 0;
        super.decorator.logsPerChunk = 2;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 8;
        super.decorator.flowersPerChunk = 0;
        super.decorator.enableFern = true;
        super.decorator.enableSpecialGrasses = false;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 2;
        super.decorator.dryReedChance = 1.0f;
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
        this.flowers.clear();
        this.addFlower(LOTRMod.deadPlant, 0, 10);
        super.biomeColors.setGrass(8348751);
        super.biomeColors.setSky(5657394);
        super.biomeColors.setClouds(10525542);
        super.biomeColors.setFog(4210724);
        super.biomeColors.setWater(1316367);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDeadMarshes;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.NINDALF;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DEAD_MARSHES.getSubregion("deadMarshes");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 6; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            new WorldGenFlowers(LOTRMod.deadPlant).generate(world, random, i2, j1, k2);
        }
        for (int l = 0; l < 4; ++l) {
            int i2;
            int k2;
            int j1;
            for (i2 = i + random.nextInt(16) + 8, k2 = k + random.nextInt(16) + 8, j1 = 128; j1 > 0 && world.isAirBlock(i2, j1 - 1, k2); --j1) {}
            new LOTRWorldGenMarshLights().generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
}
