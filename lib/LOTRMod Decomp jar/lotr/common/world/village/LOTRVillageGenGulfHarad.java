// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenGulfWarCamp;
import lotr.common.world.structure2.LOTRWorldGenGulfTownWall;
import lotr.common.world.structure2.LOTRWorldGenGulfTower;
import lotr.common.world.structure2.LOTRWorldGenGulfAltar;
import lotr.common.world.structure2.LOTRWorldGenGulfBazaar;
import lotr.common.world.structure2.LOTRWorldGenGulfVillageLight;
import lotr.common.world.structure2.LOTRWorldGenGulfPyramid;
import lotr.common.world.structure2.LOTRWorldGenGulfPasture;
import lotr.common.world.structure2.LOTRWorldGenGulfFarm;
import lotr.common.world.structure2.LOTRWorldGenGulfHouse;
import lotr.common.world.structure2.LOTRWorldGenGulfSmithy;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenGulfVillageSign;
import lotr.common.world.structure2.LOTRWorldGenGulfTavern;
import lotr.common.world.structure2.LOTRWorldGenGulfTotem;
import lotr.common.entity.npc.LOTREntityGulfHaradArcher;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenGulfHarad extends LOTRVillageGen
{
    public LOTRVillageGenGulfHarad(final LOTRBiome biome, final float f) {
        super(biome);
        super.gridScale = 14;
        super.gridRandomDisplace = 1;
        super.spawnChance = f;
        super.villageChunkSize = 6;
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
        private int numOuterHouses;
        private static final int pathInner = 16;
        private static final int roadWidth = 5;
        private static final int pathFuzz = 3;
        private static final int rHouses = 24;
        private static final int rFarms = 52;
        private static final int rEdge = 68;
        private static final int rTownInner = 24;
        private static final int rTownRoadEnd = 74;
        private static final int rTownWall = 98;
        private static final int townRoadWidth = 7;
        private boolean townWall;
        int rTownTower;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
            this.townWall = true;
            this.rTownTower = 90;
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
            this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 5, 8);
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
                    spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
                    spawner.setCheckRanges(64, -12, 12, 24);
                    spawner.setSpawnRanges(32, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(64);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
                    spawner.setCheckRanges(64, -12, 12, 12);
                    spawner.setSpawnRanges(32, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(64);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenGulfTotem(false), 0, -2, 0, true);
            this.addStructure(new LOTRWorldGenGulfTavern(false), 0, 24, 0, true);
            final int rSignsInner = 11;
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), -rSignsInner, 0, 1, true);
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), rSignsInner, 0, 3, true);
            for (int h = 0; h < this.numOuterHouses; ++h) {
                final float turn = h / (float)(this.numOuterHouses - 1);
                final float turnMin = 0.15f;
                final float turnMax = 1.0f - turnMin;
                final float turnInRange = turnMin + (turnMax - turnMin) * turn;
                final float turnCorrected = (turnInRange + 0.25f) % 1.0f;
                final float turnR = (float)Math.toRadians(turnCorrected * 360.0f);
                final float sin = MathHelper.sin(turnR);
                final float cos = MathHelper.cos(turnR);
                int r = 0;
                final float turn2 = turnCorrected * 8.0f;
                if (turn2 >= 1.0f && turn2 < 3.0f) {
                    r = 0;
                }
                else if (turn2 >= 3.0f && turn2 < 5.0f) {
                    r = 1;
                }
                else if (turn2 >= 5.0f && turn2 < 7.0f) {
                    r = 2;
                }
                else if (turn2 >= 7.0f || turn2 < 1.0f) {
                    r = 3;
                }
                final int l = 24;
                final int i = Math.round(l * cos);
                final int k = Math.round(l * sin);
                this.addStructure(this.getRandomHouse(random), i, k, r);
            }
            final int numFarms = this.numOuterHouses * 2;
            final float frac = 1.0f / numFarms;
            float turn3 = 0.0f;
            while (turn3 < 1.0f) {
                turn3 += frac;
                final float turnR2 = (float)Math.toRadians(turn3 * 360.0f);
                final float sin2 = MathHelper.sin(turnR2);
                final float cos2 = MathHelper.cos(turnR2);
                int r2 = 0;
                final float turn4 = turn3 * 8.0f;
                if (turn4 >= 1.0f && turn4 < 3.0f) {
                    r2 = 0;
                }
                else if (turn4 >= 3.0f && turn4 < 5.0f) {
                    r2 = 1;
                }
                else if (turn4 >= 5.0f && turn4 < 7.0f) {
                    r2 = 2;
                }
                else if (turn4 >= 7.0f || turn4 < 1.0f) {
                    r2 = 3;
                }
                final int j = 52;
                final int m = Math.round(j * cos2);
                final int k2 = Math.round(j * sin2);
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), m, k2, r2);
                }
                else {
                    this.addStructure(this.getRandomFarm(random), m, k2, r2);
                }
            }
        }
        
        private LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (random.nextInt(5) == 0) {
                return new LOTRWorldGenGulfSmithy(false);
            }
            return new LOTRWorldGenGulfHouse(false);
        }
        
        private LOTRWorldGenStructureBase2 getRandomFarm(final Random random) {
            if (random.nextBoolean()) {
                return new LOTRWorldGenGulfFarm(false);
            }
            return new LOTRWorldGenGulfPasture(false);
        }
        
        private void setupTown(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
                    spawner.setCheckRanges(80, -12, 12, 100);
                    spawner.setSpawnRanges(40, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (final int i1 : new int[] { -40, 40 }) {
                for (final int k1 : new int[] { -40, 40 }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            spawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
                            spawner.setCheckRanges(64, -12, 12, 20);
                            spawner.setSpawnRanges(20, -6, 6, 64);
                            spawner.setBlockEnemySpawnRange(64);
                        }
                    }, i1, k1, 0);
                }
            }
            this.addStructure(new LOTRWorldGenGulfPyramid(false), 0, -11, 0, true);
            final int lightR = 15;
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), -lightR, -lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), lightR, -lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), -lightR, lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), lightR, lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfBazaar(false), -74, 0, 1, true);
            this.addStructure(new LOTRWorldGenGulfAltar(false), 74, 0, 3, true);
            this.addStructure(new LOTRWorldGenGulfTotem(false), 0, 79, 0, true);
            for (int l = 0; l <= 2; ++l) {
                final int j = 5;
                final int m = 32 + l * 20;
                this.addStructure(new LOTRWorldGenGulfHouse(false), -j, -m, 1, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), j, -m, 3, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), -j, m, 1, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), j, m, 3, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), m, -j, 2, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), m, j, 0, true);
                if (l == 0) {
                    this.addStructure(new LOTRWorldGenGulfSmithy(false), -m - 6, -j, 2, true);
                    this.addStructure(new LOTRWorldGenGulfTavern(false), -m - 6, j, 0, true);
                }
            }
            final int xzTownTower = (int)(this.rTownTower / Math.sqrt(2.0));
            this.addStructure(new LOTRWorldGenGulfTower(false), -xzTownTower, -xzTownTower + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), xzTownTower, -xzTownTower + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), -xzTownTower, xzTownTower - 4, 0, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), xzTownTower, xzTownTower - 4, 0, true);
            int turn = 0;
            final int numTurns = 24;
            while (turn <= numTurns) {
                if (++turn % 3 == 0) {
                    continue;
                }
                final float turnF = turn / (float)numTurns;
                final float turnR = (float)Math.toRadians(turnF * 360.0f);
                final float sin = MathHelper.sin(turnR);
                final float cos = MathHelper.cos(turnR);
                int r = 0;
                final float turn2 = turnF * 8.0f;
                if (turn2 >= 1.0f && turn2 < 3.0f) {
                    r = 0;
                }
                else if (turn2 >= 3.0f && turn2 < 5.0f) {
                    r = 1;
                }
                else if (turn2 >= 5.0f && turn2 < 7.0f) {
                    r = 2;
                }
                else if (turn2 >= 7.0f || turn2 < 1.0f) {
                    r = 3;
                }
                final int l2 = this.rTownTower - 6;
                final int i2 = Math.round(l2 * cos);
                final int k2 = Math.round(l2 * sin);
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), i2, k2, r);
                }
                else {
                    this.addStructure(this.getRandomFarm(random), i2, k2, r);
                }
            }
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), -5, -96, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), 5, -96, 0, true);
            if (this.townWall) {
                final int rSq = 9604;
                final int rMax = 99;
                final int rSqMax = rMax * rMax;
                for (int i3 = -98; i3 <= 98; ++i3) {
                    for (int k3 = -98; k3 <= 98; ++k3) {
                        final int i4 = Math.abs(i3);
                        if (i4 > 6 || k3 >= 0) {
                            final int dSq = i3 * i3 + k3 * k3;
                            if (dSq >= rSq && dSq < rSqMax) {
                                final LOTRWorldGenGulfTownWall wall = new LOTRWorldGenGulfTownWall(false);
                                if (i4 == 7 && k3 < 0) {
                                    wall.setTall();
                                }
                                this.addStructure(wall, i3, k3, 0);
                            }
                        }
                    }
                }
            }
        }
        
        private void setupFort(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
                    spawner.setCheckRanges(40, -12, 12, 16);
                    spawner.setSpawnRanges(24, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenGulfWarCamp(false), 0, -15, 0, true);
            final int towerX = 36;
            this.addStructure(new LOTRWorldGenGulfTower(false), -towerX, -towerX + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), towerX, -towerX + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), -towerX, towerX - 4, 0, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), towerX, towerX - 4, 0, true);
            for (int l = -1; l <= 1; ++l) {
                final int i = l * 16;
                final int k = 28;
                this.addStructure(this.getRandomFarm(random), i, k, 0);
                this.addStructure(this.getRandomFarm(random), -k, i, 1);
                this.addStructure(this.getRandomFarm(random), k, i, 3);
            }
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            final int i2 = Math.abs(i);
            final int k2 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                final int dSq = i * i + k * k;
                final int imn = 16 - random.nextInt(3);
                final int imx = 21 + random.nextInt(3);
                if (dSq > imn * imn && dSq < imx * imx) {
                    return LOTRRoadType.PATH;
                }
            }
            if (this.villageType == VillageType.TOWN) {
                final int dSq = i * i + k * k;
                if (dSq < 576) {
                    return LOTRRoadType.GULF_HARAD;
                }
                if ((k2 <= 3 && i2 <= 74) || (i2 <= 3 && k <= 74)) {
                    return LOTRRoadType.GULF_HARAD;
                }
            }
            return null;
        }
        
        @Override
        public boolean isVillageSurface(final World world, final int i, final int j, final int k) {
            if (this.villageType == VillageType.TOWN) {
                final Block block = world.getBlock(i, j, k);
                final int meta = world.getBlockMetadata(i, j, k);
                if ((block == LOTRMod.brick3 && meta == 13) || (block == LOTRMod.brick3 && meta == 14)) {
                    return true;
                }
            }
            return false;
        }
    }
}
