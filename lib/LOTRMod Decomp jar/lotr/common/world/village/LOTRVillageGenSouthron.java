// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenSouthronFortCorner;
import lotr.common.world.structure2.LOTRWorldGenSouthronFortWall;
import lotr.common.world.structure2.LOTRWorldGenSouthronFortGate;
import lotr.common.world.structure2.LOTRWorldGenSouthronTraining;
import lotr.common.world.structure2.LOTRWorldGenSouthronBarracks;
import lotr.common.world.structure2.LOTRWorldGenSouthronTower;
import lotr.common.world.structure2.LOTRWorldGenSouthronFortress;
import lotr.common.entity.npc.LOTREntitySouthronChampion;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownCorner;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownWall;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownGate;
import lotr.common.world.structure2.LOTRWorldGenSouthronStatue;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownTree;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownFlowers;
import lotr.common.world.structure2.LOTRWorldGenSouthronLamp;
import lotr.common.world.structure2.LOTRWorldGenSouthronBazaar;
import com.google.common.math.IntMath;
import lotr.common.world.structure2.LOTRWorldGenSouthronPasture;
import lotr.common.world.structure2.LOTRWorldGenSouthronFarm;
import lotr.common.world.structure2.LOTRWorldGenSouthronHouse;
import lotr.common.world.structure2.LOTRWorldGenSouthronStables;
import lotr.common.world.structure2.LOTRWorldGenSouthronSmithy;
import lotr.common.world.structure2.LOTRWorldGenSouthronTavern;
import lotr.common.world.structure2.LOTRWorldGenSouthronMansion;
import lotr.common.world.structure2.LOTRWorldGenSouthronVillageSign;
import lotr.common.world.structure2.LOTRWorldGenSouthronWell;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNearHaradrim;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenSouthronVillagePost;
import lotr.common.world.structure2.LOTRWorldGenSouthronVillageFence;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenSouthron extends LOTRVillageGen
{
    public LOTRVillageGenSouthron(final LOTRBiome biome, final float f) {
        super(biome);
        super.gridScale = 14;
        super.gridRandomDisplace = 1;
        super.spawnChance = f;
        super.villageChunkSize = 5;
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
        public String[] villageName;
        private static final int roadWidth = 5;
        private static final int pathFuzz = 3;
        private static final int rInnerSquare = 14;
        private static final int rOuterSquare = 45;
        private static final int tavernEdge = 7;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
            if (random.nextInt(4) == 0) {
                this.villageType = VillageType.FORT;
            }
            else if (random.nextInt(3) == 0) {
                this.villageType = VillageType.TOWN;
            }
            else {
                this.villageType = VillageType.VILLAGE;
            }
            this.villageName = LOTRNames.getHaradVillageName(random);
        }
        
        @Override
        public boolean isFlat() {
            return false;
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
                    Instance.this.setCivilianSpawnClass(spawner);
                    spawner.setCheckRanges(64, -12, 12, 24);
                    spawner.setSpawnRanges(32, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(64);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    Instance.this.setWarriorSpawnClasses(spawner);
                    spawner.setCheckRanges(64, -12, 12, 12);
                    spawner.setSpawnRanges(32, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(64);
                }
            }, 0, 0, 0);
            this.addStructure(this.getWell(random), 0, -2, 0, true);
            this.addStructure(this.getSignpost(random).setSignText(this.villageName), 0, -8, 0, true);
            final int rSquareEdge = 17;
            this.addStructure(this.getTavern(random), 0, rSquareEdge, 0, true);
            this.addStructure(this.getMansion(random), -3, -rSquareEdge, 2, true);
            this.addStructure(this.getMansion(random), -rSquareEdge, 3, 1, true);
            this.addStructure(this.getMansion(random), rSquareEdge, -3, 3, true);
            final int backFenceX = 0;
            final int backFenceZ = rSquareEdge + 19;
            final int backFenceWidth = 12;
            final int sideFenceX = 13;
            final int sideFenceZ = rSquareEdge + 11;
            final int sideFenceWidth = 8;
            final int frontPostZ = sideFenceZ - sideFenceWidth - 1;
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceX, -backFenceZ, 0);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceX, -sideFenceZ, 1);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceX, -sideFenceZ, 3);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), -sideFenceX, -frontPostZ, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), sideFenceX, -frontPostZ, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), -sideFenceX, -backFenceZ, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), sideFenceX, -backFenceZ, 0);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), -backFenceZ, backFenceX, 1);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceZ, sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), -sideFenceZ, -sideFenceX, 2);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), -frontPostZ, sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), -frontPostZ, -sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), -backFenceZ, sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), -backFenceZ, -sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceZ, backFenceX, 3);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), sideFenceZ, -sideFenceX, 2);
            this.addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceZ, sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), frontPostZ, -sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), frontPostZ, sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), backFenceZ, -sideFenceX, 0);
            this.addStructure(new LOTRWorldGenSouthronVillagePost(false), backFenceZ, sideFenceX, 0);
            final int farmRange = 3;
            final int farmStep = 14;
            final int farmX = 55;
            for (int l = -farmRange; l <= farmRange; ++l) {
                final int k = l * farmStep;
                int i = -farmX;
                int r = 1;
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                }
                else {
                    this.addStructure(this.getRandomFarm(random), i, k, r);
                }
                i = farmX;
                r = 3;
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                }
                else {
                    this.addStructure(this.getRandomFarm(random), i, k, r);
                }
            }
            final int houseRange = 3;
            final int houseStep = 17;
            final int houseZ = 55;
            for (int j = -houseRange; j <= houseRange; ++j) {
                final int m = j * houseStep;
                int k2 = -houseZ;
                int r2 = 2;
                this.addStructure(this.getRandomHouse(random), m, k2, r2);
                k2 = houseZ;
                r2 = 0;
                if (Math.abs(m) >= 7) {
                    this.addStructure(this.getRandomHouse(random), m, k2, r2);
                }
            }
        }
        
        protected void setCivilianSpawnClass(final LOTREntityNPCRespawner spawner) {
            spawner.setSpawnClass(LOTREntityNearHaradrim.class);
        }
        
        protected void setWarriorSpawnClasses(final LOTREntityNPCRespawner spawner) {
            spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
        }
        
        protected LOTRWorldGenStructureBase2 getWell(final Random random) {
            return new LOTRWorldGenSouthronWell(false);
        }
        
        protected LOTRWorldGenSouthronVillageSign getSignpost(final Random random) {
            return new LOTRWorldGenSouthronVillageSign(false);
        }
        
        protected LOTRWorldGenStructureBase2 getMansion(final Random random) {
            return new LOTRWorldGenSouthronMansion(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTavern(final Random random) {
            return new LOTRWorldGenSouthronTavern(false);
        }
        
        protected LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (random.nextInt(6) == 0) {
                return new LOTRWorldGenSouthronSmithy(false);
            }
            if (random.nextInt(6) == 0) {
                return new LOTRWorldGenSouthronStables(false);
            }
            return new LOTRWorldGenSouthronHouse(false);
        }
        
        protected LOTRWorldGenStructureBase2 getRandomFarm(final Random random) {
            if (random.nextBoolean()) {
                return new LOTRWorldGenSouthronFarm(false);
            }
            return new LOTRWorldGenSouthronPasture(false);
        }
        
        private void setupTown(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    Instance.this.setCivilianSpawnClass(spawner);
                    spawner.setCheckRanges(80, -12, 12, 100);
                    spawner.setSpawnRanges(40, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (final int i1 : new int[] { -30, 30 }) {
                for (final int k1 : new int[] { -30, 30 }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            Instance.this.setWarriorSpawnClasses(spawner);
                            spawner.setCheckRanges(40, -12, 12, 16);
                            spawner.setSpawnRanges(20, -6, 6, 64);
                            spawner.setBlockEnemySpawnRange(60);
                        }
                    }, i1, k1, 0);
                }
            }
            this.addStructure(this.getBazaar(random), 1, -2, 0, true);
            this.addStructure(this.getLamp(random), 15, -2, 0, true);
            this.addStructure(this.getLamp(random), -13, -2, 0, true);
            this.addStructure(this.getLamp(random), 15, 18, 0, true);
            this.addStructure(this.getLamp(random), -13, 18, 0, true);
            this.addStructure(this.getWell(random), -16, 12, 1, true);
            this.addStructure(this.getWell(random), -16, 4, 1, true);
            this.addStructure(this.getFlowers(random), 18, 13, 3, true);
            this.addStructure(this.getFlowers(random), 18, 3, 3, true);
            for (int l = 0; l <= 3; ++l) {
                int j = -41 + l * 19;
                int m = -7;
                int r = 2;
                this.addStructure(this.getMansion(random), j, m, r, true);
                this.addStructure(this.getLamp(random), j + 6, m - 1, r, true);
                j = 24 - l * 19;
                m = 23;
                r = 0;
                this.addStructure(this.getMansion(random), j, m, r, true);
                this.addStructure(this.getLamp(random), j - 6, m + 1, r, true);
            }
            this.addStructure(this.getSmithy(random), -25, 9, 1, true);
            this.addStructure(this.getHouse(random), -25, 18, 1, true);
            this.addStructure(this.getHouse(random), -25, -2, 1, true);
            this.addStructure(this.getTree(random), -45, 8, 1, true);
            this.addStructure(this.getHouse(random), -50, 18, 3, true);
            this.addStructure(this.getHouse(random), -50, -2, 3, true);
            this.addStructure(this.getWell(random), -51, -14, 2, true);
            this.addStructure(this.getTree(random), -46, -29, 2, true);
            this.addStructure(this.getFlowers(random), -42, -32, 3, true);
            this.addStructure(this.getTree(random), -50, 30, 0, true);
            for (int l = -3; l <= 3; ++l) {
                final int j = -56;
                final int m = -2 + l * 10;
                final int r = 1;
                this.addStructure(this.getHouse(random), j, m, r, true);
            }
            this.addStructure(this.getStatue(random), 26, 8, 3, true);
            this.addStructure(this.getHouse(random), 26, 18, 3, true);
            this.addStructure(this.getHouse(random), 26, -2, 3, true);
            for (int l = -3; l <= 2; ++l) {
                final int j = 52;
                final int m = 8 + l * 10;
                final int r = 1;
                this.addStructure(this.getHouse(random), j, m, r, true);
            }
            this.addStructure(this.getSmithy(random), 41, -33, 3, true);
            for (int l = -2; l <= 2; ++l) {
                final int j = 65;
                final int m = 3 + l * 14;
                final int r = 2;
                this.addStructure(this.getHouse(random), j, m, r, true);
            }
            this.addStructure(this.getWell(random), 57, -19, 2, true);
            this.addStructure(this.getLamp(random), 57, -16, 2, true);
            this.addStructure(this.getLamp(random), 57, -8, 2, true);
            this.addStructure(this.getTree(random), 57, 1, 2, true);
            this.addStructure(this.getLamp(random), 57, 4, 2, true);
            this.addStructure(this.getLamp(random), 57, 12, 2, true);
            this.addStructure(this.getTree(random), 57, 21, 2, true);
            this.addStructure(this.getLamp(random), 57, 24, 2, true);
            this.addStructure(this.getLamp(random), 57, 32, 2, true);
            for (int l = 0; l <= 3; ++l) {
                final int j = 41 + l * 8;
                final int m = 34;
                final int r = 0;
                this.addStructure(this.getFlowers(random), j, m, r, true);
            }
            this.addStructure(this.getTree(random), 34, 25, 0, true);
            this.addStructure(this.getStables(random), -20, -30, 1, true);
            this.addStructure(this.getTavern(random), 17, -32, 1, true);
            this.addStructure(this.getLamp(random), 19, -28, 1, true);
            this.addStructure(this.getLamp(random), 19, -36, 1, true);
            this.addStructure(this.getLamp(random), -16, -32, 3, true);
            this.addStructure(this.getFlowers(random), 25, -32, 3, true);
            this.addStructure(this.getTree(random), 34, -29, 2, true);
            this.addStructure(this.getLamp(random), 34, -26, 2, true);
            this.addStructure(this.getLamp(random), 34, -18, 2, true);
            this.addStructure(this.getTree(random), 34, -9, 2, true);
            this.addStructure(this.getTownGate(random).setSignText(this.villageName), 34, -47, 0, true);
            this.addStructure(this.getTownWallCorner(random), 73, -47, 0, true);
            this.addStructure(this.getTownWallCorner(random), -77, -43, 3, true);
            this.addStructure(this.getTownWallCorner(random), -73, 47, 2, true);
            this.addStructure(this.getTownWallCorner(random), 77, 43, 1, true);
            for (int l = 0; l <= 6; ++l) {
                final int j = 68 - l * 4;
                final int m = -44;
                final int r = 0;
                if (l % 2 == 0) {
                    this.addStructure(this.getTownWallShort(random), j, m, r, true);
                }
                else {
                    this.addStructure(this.getTownWallLong(random), j, m, r, true);
                }
            }
            this.addStructure(this.getTownWallExtra(random), 24, -44, 0, true);
            for (int l = 0; l <= 22; ++l) {
                final int j = 20 - l * 4;
                final int m = -44;
                final int r = 0;
                if (l % 2 == 0) {
                    this.addStructure(this.getTownWallShort(random), j, m, r, true);
                }
                else {
                    this.addStructure(this.getTownWallLong(random), j, m, r, true);
                }
            }
            this.addStructure(this.getTownWallSideMid(random), 74, 0, 1, true);
            this.addStructure(this.getTownWallSideMid(random), -74, 0, 3, true);
            for (int l = 1; l <= 9; ++l) {
                final int j = 74;
                final int m = 2 + l * 4;
                if (l % 2 == 1) {
                    this.addStructure(this.getTownWallShort(random), j, m, 1, true);
                    this.addStructure(this.getTownWallShort(random), j, -m, 1, true);
                    this.addStructure(this.getTownWallShort(random), -j, m, 3, true);
                    this.addStructure(this.getTownWallShort(random), -j, -m, 3, true);
                }
                else {
                    this.addStructure(this.getTownWallLong(random), j, m, 1, true);
                    this.addStructure(this.getTownWallLong(random), j, -m, 1, true);
                    this.addStructure(this.getTownWallLong(random), -j, m, 3, true);
                    this.addStructure(this.getTownWallLong(random), -j, -m, 3, true);
                }
            }
            for (int l = -17; l <= 17; ++l) {
                final int j = 0 + l * 4;
                final int m = 44;
                final int r = 2;
                if (IntMath.mod(l, 2) == 1) {
                    this.addStructure(this.getTownWallShort(random), j, m, r, true);
                }
                else {
                    this.addStructure(this.getTownWallLong(random), j, m, r, true);
                }
            }
        }
        
        protected LOTRWorldGenStructureBase2 getHouse(final Random random) {
            return new LOTRWorldGenSouthronHouse(false);
        }
        
        protected LOTRWorldGenStructureBase2 getSmithy(final Random random) {
            return new LOTRWorldGenSouthronSmithy(false);
        }
        
        protected LOTRWorldGenStructureBase2 getBazaar(final Random random) {
            return new LOTRWorldGenSouthronBazaar(false);
        }
        
        protected LOTRWorldGenStructureBase2 getLamp(final Random random) {
            return new LOTRWorldGenSouthronLamp(false);
        }
        
        protected LOTRWorldGenStructureBase2 getFlowers(final Random random) {
            return new LOTRWorldGenSouthronTownFlowers(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTree(final Random random) {
            return new LOTRWorldGenSouthronTownTree(false);
        }
        
        protected LOTRWorldGenStructureBase2 getStatue(final Random random) {
            return new LOTRWorldGenSouthronStatue(false);
        }
        
        protected LOTRWorldGenSouthronTownGate getTownGate(final Random random) {
            return new LOTRWorldGenSouthronTownGate(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTownWallShort(final Random random) {
            return new LOTRWorldGenSouthronTownWall.Short(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTownWallLong(final Random random) {
            return new LOTRWorldGenSouthronTownWall.Long(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTownWallSideMid(final Random random) {
            return new LOTRWorldGenSouthronTownWall.SideMid(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTownWallExtra(final Random random) {
            return new LOTRWorldGenSouthronTownWall.Extra(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTownWallCorner(final Random random) {
            return new LOTRWorldGenSouthronTownCorner(false);
        }
        
        private void setupFort(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    Instance.this.setCivilianSpawnClass(spawner);
                    spawner.setCheckRanges(60, -12, 12, 16);
                    spawner.setSpawnRanges(24, -6, 6, 40);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (final int i1 : new int[] { -25, 25 }) {
                for (final int k1 : new int[] { -25, 25 }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            Instance.this.setWarriorSpawnClasses(spawner);
                            spawner.setCheckRanges(35, -12, 12, 16);
                            spawner.setSpawnRanges(15, -6, 6, 40);
                            spawner.setBlockEnemySpawnRange(35);
                        }
                    }, i1, k1, 0);
                }
            }
            this.placeChampionRespawner();
            this.addStructure(this.getFortress(random), 0, -15, 0, true);
            this.addStructure(this.getBarracks(random), -33, -8, 0, true);
            this.addStructure(this.getBarracks(random), 32, -8, 0, true);
            this.addStructure(this.getTower(random), -43, -36, 2, true);
            this.addStructure(this.getTower(random), 43, -36, 2, true);
            this.addStructure(this.getTower(random), -43, 36, 0, true);
            this.addStructure(this.getTower(random), 43, 36, 0, true);
            for (int l = 0; l <= 2; ++l) {
                final int j = 10 + l * 11;
                final int m = -28;
                final int r = 2;
                this.addStructure(this.getRandomFarm(random), j, m, r);
                this.addStructure(this.getRandomFarm(random), -j, m, r);
            }
            this.addStructure(this.getTraining(random), 0, 27, 0, true);
            this.addStructure(this.getStables(random), -29, 33, 3, true);
            this.addStructure(this.getStables(random), 29, 37, 1, true);
            this.addStructure(this.getFortGate(random), 0, -47, 0, true);
            for (int l = 0; l <= 9; ++l) {
                final int j = 8 + l * 4;
                final int m = -46;
                final int r = 0;
                if (l % 2 == 0) {
                    this.addStructure(this.getFortWallLong(random), -j, m, r, true);
                    this.addStructure(this.getFortWallLong(random), j, m, r, true);
                }
                else {
                    this.addStructure(this.getFortWallLong(random), -j, m, r, true);
                    this.addStructure(this.getFortWallLong(random), j, m, r, true);
                }
            }
            for (int l = -11; l <= 11; ++l) {
                final int j = l * 4;
                final int m = 46;
                final int r = 2;
                if (l % 2 == 0) {
                    this.addStructure(this.getFortWallLong(random), j, m, r, true);
                }
                else {
                    this.addStructure(this.getFortWallLong(random), j, m, r, true);
                }
            }
            for (int l = -10; l <= 10; ++l) {
                final int j = -50;
                final int m = l * 4;
                int r = 3;
                if (l % 2 == 0) {
                    this.addStructure(this.getFortWallLong(random), j, m, r, true);
                }
                else {
                    this.addStructure(this.getFortWallLong(random), j, m, r, true);
                }
                r = 1;
                if (l % 2 == 0) {
                    this.addStructure(this.getFortWallLong(random), -j, m, r, true);
                }
                else {
                    this.addStructure(this.getFortWallLong(random), -j, m, r, true);
                }
            }
            this.addStructure(this.getFortCorner(random), -50, -46, 0, true);
            this.addStructure(this.getFortCorner(random), 50, -46, 1, true);
            this.addStructure(this.getFortCorner(random), -50, 46, 3, true);
            this.addStructure(this.getFortCorner(random), 50, 46, 2, true);
        }
        
        protected void placeChampionRespawner() {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntitySouthronChampion.class);
                    spawner.setCheckRanges(60, -12, 12, 4);
                    spawner.setSpawnRanges(24, -6, 6, 32);
                }
            }, 0, 0, 0);
        }
        
        protected LOTRWorldGenStructureBase2 getFortress(final Random random) {
            return new LOTRWorldGenSouthronFortress(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTower(final Random random) {
            return new LOTRWorldGenSouthronTower(false);
        }
        
        protected LOTRWorldGenStructureBase2 getStables(final Random random) {
            return new LOTRWorldGenSouthronStables(false);
        }
        
        protected LOTRWorldGenStructureBase2 getBarracks(final Random random) {
            return new LOTRWorldGenSouthronBarracks(false);
        }
        
        protected LOTRWorldGenStructureBase2 getTraining(final Random random) {
            return new LOTRWorldGenSouthronTraining(false);
        }
        
        protected LOTRWorldGenStructureBase2 getFortGate(final Random random) {
            return new LOTRWorldGenSouthronFortGate(false);
        }
        
        protected LOTRWorldGenStructureBase2 getFortWallShort(final Random random) {
            return new LOTRWorldGenSouthronFortWall.Short(false);
        }
        
        protected LOTRWorldGenStructureBase2 getFortWallLong(final Random random) {
            return new LOTRWorldGenSouthronFortWall.Long(false);
        }
        
        protected LOTRWorldGenStructureBase2 getFortCorner(final Random random) {
            return new LOTRWorldGenSouthronFortCorner(false);
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            final int i2 = Math.abs(i);
            final int k2 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                final int dSq = i * i + k * k;
                int imn = 2;
                int imx = 14 + random.nextInt(3);
                int kmn = 2;
                int kmx = 14 + random.nextInt(3);
                if (i2 <= imx && k2 <= kmx && (i2 > imn || k2 > kmn)) {
                    return LOTRRoadType.PATH;
                }
                imn = 45 - random.nextInt(3);
                imx = 50 + random.nextInt(3);
                kmn = 45 - random.nextInt(3);
                kmx = 50 + random.nextInt(3);
                if (i2 <= imx && k2 <= kmx && (i2 > imn || k2 > kmn) && (k < 0 || i2 > 7)) {
                    return LOTRRoadType.PATH;
                }
                if (k < 0) {
                    imn = 14;
                    imx = 45;
                    if (i2 + k2 >= imn + imn && i2 + k2 <= imx + imx) {
                        final int d1 = Math.abs(i2 - k2);
                        if (d1 <= (int)(2.5f + random.nextInt(3) * 2.0f)) {
                            return LOTRRoadType.PATH;
                        }
                    }
                }
                if (k > 0) {
                    imn = 10;
                    imx = imn + 5 + random.nextInt(3);
                    imn -= random.nextInt(3);
                    kmn = 14;
                    kmx = 45;
                    if (k2 >= kmn && k2 <= kmx && i2 >= imn && i2 <= imx) {
                        return LOTRRoadType.PATH;
                    }
                }
            }
            if (this.villageType == VillageType.TOWN && i2 <= 72 && k2 <= 42) {
                return LOTRRoadType.HARAD_TOWN;
            }
            if (this.villageType == VillageType.FORT) {
                if (i2 <= 3 && k >= -45 && k <= -15) {
                    return LOTRRoadType.PATH;
                }
                if (i2 <= 36 && k >= -27 && k <= -20) {
                    return LOTRRoadType.PATH;
                }
                if (i2 >= 29 && i2 <= 36 && k >= -27 && k <= 39 && (k < -7 || k > 7)) {
                    return LOTRRoadType.PATH;
                }
                if (i2 <= 36 && k >= 20 && k <= 27) {
                    return LOTRRoadType.PATH;
                }
            }
            return null;
        }
        
        @Override
        public boolean isVillageSurface(final World world, final int i, final int j, final int k) {
            if (this.villageType == VillageType.TOWN) {
                final Block block = world.getBlock(i, j, k);
                final int meta = world.getBlockMetadata(i, j, k);
                if ((block == LOTRMod.brick && meta == 15) || (block == LOTRMod.brick3 && meta == 11) || (block == LOTRMod.pillar && meta == 5)) {
                    return true;
                }
            }
            return false;
        }
    }
}
