// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import lotr.common.world.map.LOTRWorldGenUtumnoEntrance;
import lotr.common.world.map.LOTRFixedStructures;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenForodwaith extends LOTRBiome
{
    private WorldGenerator boulderGen;
    private LOTRWorldGenStalactites stalactiteIceGen;
    
    public LOTRBiomeGenForodwaith(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        this.stalactiteIceGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteIce);
        this.setEnableSnow();
        super.topBlock = Blocks.snow;
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableCaveCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 10).setSpawnChance(100000);
        factionList.add(lists);
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(Blocks.packed_ice, 16), 40.0f, 32, 256);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 0;
        super.decorator.generateWater = false;
        super.biomeColors.setSky(10069160);
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 4000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 4000);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterForodwaith;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FORODWAITH;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FORODWAITH.getSubregion("forodwaith");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (LOTRFixedStructures.UTUMNO_ENTRANCE.isAt(i, k)) {
            new LOTRWorldGenUtumnoEntrance().generate(world, random, i, world.getHeightValue(i, k), k);
        }
        if (random.nextInt(32) == 0) {
            for (int boulders = 1 + random.nextInt(5), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        for (int j = 0; j < 2; ++j) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j2 = random.nextInt(60);
            final int k2 = k + random.nextInt(16) + 8;
            this.stalactiteIceGen.generate(world, random, i3, j2, k2);
        }
        if (random.nextInt(20000) == 0) {
            final LOTRWorldGenMirkOak tree = ((LOTRWorldGenMirkOak)LOTRTreeType.RED_OAK_WEIRWOOD.create(false, random)).disableRestrictions();
            final int i3 = i + random.nextInt(16) + 8;
            final int k3 = k + random.nextInt(16) + 8;
            final int j3 = world.getHeightValue(i3, k3);
            tree.generate(world, random, i3, j3, k3);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.0f;
    }
}
