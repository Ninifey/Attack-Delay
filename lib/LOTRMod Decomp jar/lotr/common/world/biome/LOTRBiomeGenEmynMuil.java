// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenGrassPatch;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEmynMuil extends LOTRBiome
{
    private WorldGenerator boulderGenSmall;
    private WorldGenerator boulderGenLarge;
    private WorldGenerator clayBoulderGenSmall;
    private WorldGenerator clayBoulderGenLarge;
    private WorldGenerator grassPatchGen;
    
    public LOTRBiomeGenEmynMuil(final int i, final boolean major) {
        super(i, major);
        this.boulderGenSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
        this.boulderGenLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 5, 8).setHeightCheck(6);
        this.clayBoulderGenSmall = new LOTRWorldGenBoulder(Blocks.hardened_clay, 0, 1, 4);
        this.clayBoulderGenLarge = new LOTRWorldGenBoulder(Blocks.hardened_clay, 0, 5, 10).setHeightCheck(6);
        this.grassPatchGen = new LOTRWorldGenGrassPatch();
        super.topBlock = Blocks.stone;
        super.fillerBlock = Blocks.stone;
        super.spawnableCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 1);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
        factionList2.add(lists2);
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 2;
        this.registerMountainsFlowers();
        super.biomeColors.setGrass(9539937);
        super.biomeColors.setSky(10000788);
        super.decorator.generateOrcDungeon = true;
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterEmynMuil;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.EMYN_MUIL;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.BROWN_LANDS.getSubregion("emynMuil");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 20; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            if (random.nextInt(5) == 0) {
                this.clayBoulderGenSmall.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
            else {
                this.boulderGenSmall.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        for (int l = 0; l < 20; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            if (random.nextInt(5) == 0) {
                this.clayBoulderGenLarge.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
            else {
                this.boulderGenLarge.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        for (int l = 0; l < 10; ++l) {
            Block block = Blocks.stone;
            if (random.nextInt(5) == 0) {
                block = Blocks.hardened_clay;
            }
            for (int l2 = 0; l2 < 10; ++l2) {
                final int i3 = i + random.nextInt(16) + 8;
                final int k3 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i3, k3);
                if (world.getBlock(i3, j1 - 1, k3) == block) {
                    for (int height = j1 + random.nextInt(4), j2 = j1; j2 < height; ++j2) {
                        if (LOTRMod.isOpaque(world, i3, j2, k3)) {
                            break;
                        }
                        world.setBlock(i3, j2, k3, block, 0, 3);
                    }
                }
            }
        }
        for (int l = 0; l < 3; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            this.grassPatchGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
        }
    }
}
