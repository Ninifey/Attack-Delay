// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenEasterlingFortress;
import lotr.common.world.structure2.LOTRWorldGenEasterlingLamp;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTownWall;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTower;
import lotr.common.world.structure2.LOTRWorldGenEasterlingGatehouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingMarketStall;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTownHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTavernTown;
import lotr.common.world.structure2.LOTRWorldGenEasterlingStatue;
import lotr.common.world.structure2.LOTRWorldGenEasterlingGarden;
import lotr.common.world.structure2.LOTRWorldGenEasterlingVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenEasterlingSmithy;
import lotr.common.world.structure2.LOTRWorldGenEasterlingStables;
import lotr.common.world.structure2.LOTRWorldGenEasterlingHouse;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTavern;
import lotr.common.world.structure2.LOTRWorldGenEasterlingLargeTownHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingVillageSign;
import lotr.common.world.structure2.LOTRWorldGenEasterlingWell;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenRhun extends LOTRVillageGen
{
    private boolean enableTowns;
    private static final int VILLAGE_CENTRE_SIZE = 15;
    private static final int VILLAGE_PATH_LENGTH = 64;
    private static final int VILLAGE_PATH_WIDTH = 3;
    private static final int PATH_FUZZ = 4;
    private static final int TOWN_ROAD_WIDTH = 6;
    private static final int TOWN_INNER_ROAD = 12;
    private static final int TOWN_OUTER_ROAD = 60;
    private static final int TOWN_HOUSE_GAP = 2;
    private static final int TOWN_EDGE = 86;
    private static final int FORT_ROAD_WIDTH = 4;
    private static final int FORT_EDGE = 54;
    private static final int FORT_INNER_ROAD = 20;
    private static final int FORT_OUTER_ROAD = 48;
    
    public LOTRVillageGenRhun(final LOTRBiome biome, final float f, final boolean flag) {
        super(biome);
        super.gridScale = 14;
        super.gridRandomDisplace = 1;
        super.spawnChance = f;
        super.villageChunkSize = 6;
        this.enableTowns = flag;
    }
    
    @Override
    protected AbstractInstance createVillageInstance(final World world, final int i, final int k, final Random random) {
        return new Instance(world, i, k, random);
    }
    
    public enum VillageType
    {
        VILLAGE, 
        TOWN, 
        FORT;
    }
    
    public class Instance extends AbstractInstance
    {
        public VillageType villageType;
        private String[] villageName;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
            this.villageName = LOTRNames.getRhunVillageName(random);
            if (random.nextInt(4) == 0) {
                this.villageType = VillageType.FORT;
            }
            else if (LOTRVillageGenRhun.this.enableTowns && random.nextInt(4) == 0) {
                this.villageType = VillageType.TOWN;
            }
            else {
                this.villageType = VillageType.VILLAGE;
            }
        }
        
        @Override
        public boolean isFlat() {
            final VillageType villageType = this.villageType;
            final VillageType villageType2 = this.villageType;
            return villageType == VillageType.TOWN;
        }
        
        @Override
        protected void addVillageStructures(final Random random) {
            if (this.villageType == VillageType.VILLAGE) {
                this.setupVillage(random);
            }
            else if (this.villageType == VillageType.TOWN) {
                this.setupTown(random);
            }
            else if (this.villageType == VillageType.FORT) {
                this.setupFort(random);
            }
        }
        
        private void setupVillage(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityEasterling.class);
                    spawner.setCheckRanges(40, -12, 12, 40);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
                    spawner.setCheckRanges(40, -12, 12, 16);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            final int pathEnd = 68;
            final int pathSide = 7;
            final int centreSide = 19;
            this.addStructure(new LOTRWorldGenEasterlingWell(false), 0, -2, 0, true);
            final int signX = 12;
            this.addStructure(new LOTRWorldGenEasterlingVillageSign(false).setSignText(this.villageName), -signX, 0, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingVillageSign(false).setSignText(this.villageName), signX, 0, 3, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), 0, -centreSide, 2, true);
            if (random.nextBoolean()) {
                this.addStructure(new LOTRWorldGenEasterlingTavern(false), -pathEnd, 0, 1, true);
                this.addStructure(this.getOtherVillageStructure(random), pathEnd, 0, 3, true);
            }
            else {
                this.addStructure(this.getOtherVillageStructure(random), -pathEnd, 0, 1, true);
                this.addStructure(new LOTRWorldGenEasterlingTavern(false), pathEnd, 0, 3, true);
            }
            for (int rowHouses = 3, l = -rowHouses; l <= rowHouses; ++l) {
                final int i1 = l * 18;
                int k1 = pathSide;
                if (Math.abs(i1) <= 15) {
                    k1 += 15 - pathSide;
                }
                if (Math.abs(l) >= 1) {
                    this.addStructure(this.getRandomHouse(random), i1, -k1, 2);
                }
                this.addStructure(this.getRandomHouse(random), i1, k1, 0);
                final int k2 = k1 + 20;
                if (l != 0) {
                    if (random.nextInt(3) == 0) {
                        this.addStructure(this.getRandomVillageFarm(random), i1, -k2, 2);
                    }
                    else {
                        this.addStructure(new LOTRWorldGenHayBales(false), i1, -k2, 2);
                    }
                }
                if (random.nextInt(3) == 0) {
                    this.addStructure(this.getRandomVillageFarm(random), i1, k2, 0);
                }
                else {
                    this.addStructure(new LOTRWorldGenHayBales(false), i1, k2, 0);
                }
            }
        }
        
        private LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            return new LOTRWorldGenEasterlingHouse(false);
        }
        
        private LOTRWorldGenStructureBase2 getOtherVillageStructure(final Random random) {
            if (random.nextBoolean()) {
                return new LOTRWorldGenEasterlingStables(false);
            }
            return new LOTRWorldGenEasterlingSmithy(false);
        }
        
        private LOTRWorldGenStructureBase2 getRandomVillageFarm(final Random random) {
            if (random.nextBoolean()) {
                return new LOTRWorldGenEasterlingVillageFarm.Animals(false);
            }
            return new LOTRWorldGenEasterlingVillageFarm.Crops(false);
        }
        
        private void setupTown(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityEasterling.class);
                    spawner.setCheckRanges(80, -12, 12, 100);
                    spawner.setSpawnRanges(60, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            final int spawnerX = 60;
            for (final int i1 : new int[] { -spawnerX, spawnerX }) {
                for (final int k1 : new int[] { -spawnerX, spawnerX }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
                            spawner.setCheckRanges(50, -12, 12, 16);
                            spawner.setSpawnRanges(20, -6, 6, 64);
                            spawner.setBlockEnemySpawnRange(60);
                        }
                    }, i1, k1, 0);
                }
            }
            if (random.nextBoolean()) {
                this.addStructure(new LOTRWorldGenEasterlingGarden(false), 0, 10, 2, true);
            }
            else {
                this.addStructure(new LOTRWorldGenEasterlingStatue(false), 0, 6, 2, true);
            }
            final int mansionX = 12;
            final int mansionZ = 20;
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionX, -mansionZ, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionX, -mansionZ, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionX, mansionZ, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionX, mansionZ, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionZ, -mansionX, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionZ, mansionX, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionZ, -mansionX, 3, true);
            this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionZ, mansionX, 3, true);
            for (int l = 0; l <= 3; ++l) {
                final int houseX = 10 + 14 * l;
                final int houseZ1 = 58;
                final int houseZ2 = 68;
                if (l <= 2) {
                    if (l >= 1 && l <= 2) {
                        if (l == 1) {
                            this.addStructure(new LOTRWorldGenEasterlingTavernTown(false), -houseX - 7, -houseZ1, 0, true);
                        }
                    }
                    else {
                        this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, -houseZ1, 0, true);
                    }
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, -houseZ1, 0, true);
                    if (l >= 1) {
                        this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, houseZ1, 2, true);
                        this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, houseZ1, 2, true);
                    }
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ1, -houseX, 3, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ1, houseX, 3, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ1, -houseX, 1, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ1, houseX, 1, true);
                }
                if (l == 1) {
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseX, -houseZ2, 2, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseX, -houseZ2, 2, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseX, houseZ2, 0, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseX, houseZ2, 0, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ2, -houseX, 1, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ2, houseX, 1, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ2, -houseX, 3, true);
                    this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ2, houseX, 3, true);
                }
                else {
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, -houseZ2, 2, true);
                    this.addStructure((l == 3) ? new LOTRWorldGenEasterlingSmithy(false) : new LOTRWorldGenEasterlingTownHouse(false), houseX, -houseZ2, 2, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, houseZ2, 0, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, houseZ2, 0, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ2, -houseX, 1, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ2, houseX, 1, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ2, -houseX, 3, true);
                    this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ2, houseX, 3, true);
                }
            }
            int marketX = 4;
            for (int j = 0; j <= 2; ++j) {
                final int marketZ = 56 - j * 7;
                this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), -marketX, marketZ, 1, true);
                this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), marketX, marketZ, 3, true);
            }
            marketX = 14;
            final int marketZ = 59;
            this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), -marketX, marketZ, 2, true);
            this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), marketX, marketZ, 2, true);
            final int gardenX = 58;
            this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -gardenX + 5, -gardenX, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), gardenX - 5, -gardenX, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -gardenX + 5, gardenX, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), gardenX - 5, gardenX, 2, true);
            final int wellX = 69;
            final int wellZ = 63;
            this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellX, -wellZ, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellZ, -wellX, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), wellX, -wellZ, 3, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), wellZ, -wellX, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellX, wellZ, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellZ, wellX, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), wellX, wellZ, 3, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), wellZ, wellX, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingGatehouse(false).setSignText(this.villageName), 0, 94, 2, true);
            final int towerX = 90;
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
            final int wallZ = towerX;
            this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), 0, -wallZ, 0);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), wallZ, 0, 1);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), -wallZ, 0, 3);
            for (int m = 0; m <= 9; ++m) {
                final int wallX = 11 + m * 8;
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallX, -wallZ, 0);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallX, -wallZ, 0);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallZ, wallX, 1);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallZ, -wallX, 1);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallX, wallZ, 2);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallX, wallZ, 2);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallZ, -wallX, 3);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallZ, wallX, 3);
            }
            final int lampX = 7;
            final int lampZ = 96;
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampZ, 2, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampZ, 2, false);
        }
        
        private void setupFort(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityEasterling.class);
                    spawner.setCheckRanges(50, -12, 12, 16);
                    spawner.setSpawnRanges(30, -6, 6, 40);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (final int i1 : new int[] { -48, 48 }) {
                for (final int k1 : new int[] { -48, 48 }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
                            spawner.setCheckRanges(32, -12, 12, 16);
                            spawner.setSpawnRanges(20, -6, 6, 40);
                            spawner.setBlockEnemySpawnRange(40);
                        }
                    }, i1, k1, 0);
                }
            }
            this.addStructure(new LOTRWorldGenEasterlingFortress(false), 0, 13, 2, true);
            final int stableX = 26;
            final int stableZ = 0;
            this.addStructure(new LOTRWorldGenEasterlingStables(false), -stableX, stableZ, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingStables(false), stableX, stableZ, 3, true);
            final int wellX = stableX;
            final int wellZ = 18;
            this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellX, wellZ, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingWell(false), wellX, wellZ, 3, true);
            final int farmZ = 27;
            for (int l = -3; l <= 3; ++l) {
                final int farmX = l * 10;
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), farmX, -farmZ - 5, 2);
                }
                else {
                    this.addStructure(this.getRandomVillageFarm(random), farmX, -farmZ, 2);
                }
            }
            final int statueX = 6;
            final int statueZ = 36;
            this.addStructure(new LOTRWorldGenEasterlingStatue(false), -statueX, statueZ, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingStatue(false), statueX, statueZ, 3, true);
            this.addStructure(new LOTRWorldGenEasterlingGatehouse(false).disableSigns(), 0, 62, 2, true);
            final int towerX = 58;
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
            this.addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
            final int wallZ = towerX;
            this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), 0, -wallZ, 0);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), wallZ, 0, 1);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), -wallZ, 0, 3);
            for (int j = 0; j <= 5; ++j) {
                final int wallX = 11 + j * 8;
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallX, -wallZ, 0);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallX, -wallZ, 0);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallZ, wallX, 1);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallZ, -wallX, 1);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallX, wallZ, 2);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallX, wallZ, 2);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallZ, -wallX, 3);
                this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallZ, wallX, 3);
            }
            int lampX = 17;
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, -lampX, 2, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, -lampX, 2, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampX, 0, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampX, 0, false);
            lampX = 45;
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, -lampX, 2, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, -lampX, 2, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampX, 0, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampX, 0, false);
            lampX = 7;
            final int lampZ = 64;
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampZ, 2, false);
            this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampZ, 2, false);
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            final int i2 = Math.abs(i);
            final int k2 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                final int dSq = i * i + k * k;
                final int imn = 15 + random.nextInt(4);
                if (dSq < imn * imn) {
                    return LOTRRoadType.PATH;
                }
                if (i2 <= 64 && k2 <= 3 + random.nextInt(2)) {
                    return LOTRRoadType.PATH;
                }
            }
            if (this.villageType == VillageType.TOWN) {
                final int innerOut = 18;
                if (i2 <= innerOut && k2 <= innerOut && (i2 >= 12 || k2 >= 12)) {
                    return LOTRRoadType.RHUN;
                }
                if ((i2 <= 3 && k2 >= innerOut && k2 <= 86) || (k2 <= 3 && i2 >= innerOut && i2 <= 86)) {
                    return LOTRRoadType.RHUN;
                }
                final int outerOut = 66;
                if (i2 <= outerOut && k2 <= outerOut && (i2 >= 60 || k2 >= 60)) {
                    return LOTRRoadType.RHUN;
                }
            }
            if (this.villageType == VillageType.FORT) {
                final int innerOut = 24;
                if (i2 <= innerOut && k2 <= innerOut && (i2 >= 20 || k2 >= 20)) {
                    return LOTRRoadType.RHUN;
                }
                if (k >= 14 && k <= 54 && i2 <= 2) {
                    return LOTRRoadType.RHUN;
                }
                final int outerOut = 52;
                if (i2 <= outerOut && k2 <= outerOut && (i2 >= 48 || k2 >= 48)) {
                    return LOTRRoadType.RHUN;
                }
            }
            return null;
        }
        
        @Override
        public boolean isVillageSurface(final World world, final int i, final int j, final int k) {
            return false;
        }
    }
}
