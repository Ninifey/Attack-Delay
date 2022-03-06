// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenRohanFortWall;
import lotr.common.world.structure2.LOTRWorldGenRohanFortCorner;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.world.structure2.LOTRWorldGenRohanFortress;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenRohanVillagePasture;
import lotr.common.world.structure2.LOTRWorldGenRohanHouse;
import lotr.common.world.structure2.LOTRWorldGenRohanBarn;
import lotr.common.world.structure2.LOTRWorldGenRohanStables;
import lotr.common.world.structure2.LOTRWorldGenRohanSmithy;
import lotr.common.world.structure2.LOTRWorldGenRohanGatehouse;
import lotr.common.world.structure2.LOTRWorldGenRohanVillagePalisade;
import lotr.common.world.structure2.LOTRWorldGenRohanMarketStall;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageSign;
import lotr.common.world.structure2.LOTRWorldGenRohanWell;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageGarden;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.world.structure2.LOTRWorldGenMeadHall;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenRohan extends LOTRVillageGen
{
    private static final int INNER_MAX = 20;
    private static final int OUTER_MIN = 50;
    private static final int OUTER_MAX = 56;
    private static final int PALISADE_GAP = 25;
    private static final int SPOKE_WIDTH = 2;
    private static final int PATH_FUZZ = 4;
    private static final int FORT_WALL_X = 51;
    private static final int FORT_STABLE_X = 35;
    private static final int FORT_STABLE_Z = 14;
    private static final int FORT_BACK_ROAD_Z = 20;
    
    public LOTRVillageGenRohan(final LOTRBiome biome, final float f) {
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
        FORT;
    }
    
    public class Instance extends AbstractInstance
    {
        public VillageType villageType;
        private String[] villageName;
        private boolean palisade;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
            this.villageName = LOTRNames.getRohanVillageName(random);
            if (random.nextInt(3) == 0) {
                this.villageType = VillageType.FORT;
            }
            else {
                this.villageType = VillageType.VILLAGE;
            }
            this.palisade = random.nextBoolean();
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
            else if (this.villageType == VillageType.FORT) {
                this.setupFort(random);
            }
        }
        
        private void setupVillage(final Random random) {
            this.addStructure(new LOTRWorldGenMeadHall(false), 0, 2, 0, true);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityRohanMan.class);
                    spawner.setCheckRanges(40, -12, 12, 40);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityRohirrimWarrior.class);
                    spawner.setCheckRanges(40, -12, 12, 16);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            final int houses = 20;
            final float frac = 1.0f / houses;
            float turn = 0.0f;
            while (turn < 1.0f) {
                turn += frac;
                final float turnR = (float)Math.toRadians(turn * 360.0f);
                final float sin = MathHelper.sin(turnR);
                final float cos = MathHelper.cos(turnR);
                int r = 0;
                final float turn2 = turn * 8.0f;
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
                if (this.palisade && sin < 0.0f && Math.abs(cos) <= 0.25f) {
                    continue;
                }
                if (random.nextBoolean()) {
                    final int l = 57;
                    final int i = Math.round(l * cos);
                    final int k = Math.round(l * sin);
                    this.addStructure(this.getRandomHouse(random), i, k, r);
                }
                else {
                    if (random.nextInt(3) == 0) {
                        continue;
                    }
                    final int l = 61;
                    final int i = Math.round(l * cos);
                    final int k = Math.round(l * sin);
                    this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                }
            }
            final int farmX = 25;
            for (int j = -1; j <= 1; ++j) {
                final int farmZ = j * 14;
                this.addStructure(this.getRandomFarm(random), -farmX, farmZ, 1);
                this.addStructure(this.getRandomFarm(random), farmX, farmZ, 3);
            }
            int gardenX = 14;
            for (int m = 0; m <= 2; ++m) {
                final int gardenZ = 24 + m * 8;
                this.addStructure(new LOTRWorldGenRohanVillageGarden(false), -gardenX, gardenZ, 3);
                this.addStructure(new LOTRWorldGenRohanVillageGarden(false), gardenX, gardenZ, 1);
            }
            final int gardenZ = 41;
            for (int i2 = -1; i2 <= 1; ++i2) {
                gardenX = i2 * 6;
                if (i2 != 0) {
                    this.addStructure(new LOTRWorldGenRohanVillageGarden(false), gardenX, gardenZ, 0);
                }
            }
            this.addStructure(new LOTRWorldGenRohanWell(false), 0, -23, 2, true);
            this.addStructure(new LOTRWorldGenRohanVillageSign(false).setSignText(this.villageName), 0, -11, 0, true);
            if (random.nextBoolean()) {
                final int marketX = 8;
                for (int k2 = 0; k2 <= 1; ++k2) {
                    final int marketZ = 25 + k2 * 10;
                    if (random.nextBoolean()) {
                        this.addStructure(LOTRWorldGenRohanMarketStall.getRandomStall(random, false), -marketX, -marketZ, 1);
                    }
                    if (random.nextBoolean()) {
                        this.addStructure(LOTRWorldGenRohanMarketStall.getRandomStall(random, false), marketX, -marketZ, 3);
                    }
                }
            }
            if (this.palisade) {
                final int rPalisade = 81;
                final int rSq = rPalisade * rPalisade;
                final int rMax = rPalisade + 1;
                final int rSqMax = rMax * rMax;
                for (int i3 = -rPalisade; i3 <= rPalisade; ++i3) {
                    for (int k3 = -rPalisade; k3 <= rPalisade; ++k3) {
                        if (Math.abs(i3) > 9 || k3 >= 0) {
                            final int dSq = i3 * i3 + k3 * k3;
                            if (dSq >= rSq && dSq < rSqMax) {
                                this.addStructure(new LOTRWorldGenRohanVillagePalisade(false), i3, k3, 0);
                            }
                        }
                    }
                }
                this.addStructure(new LOTRWorldGenRohanGatehouse(false), 0, -rPalisade - 2, 0);
            }
        }
        
        private LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (random.nextInt(4) == 0) {
                final int i = random.nextInt(3);
                if (i == 0) {
                    return new LOTRWorldGenRohanSmithy(false);
                }
                if (i == 1) {
                    return new LOTRWorldGenRohanStables(false);
                }
                if (i == 2) {
                    return new LOTRWorldGenRohanBarn(false);
                }
            }
            return new LOTRWorldGenRohanHouse(false);
        }
        
        private LOTRWorldGenStructureBase2 getRandomFarm(final Random random) {
            if (random.nextInt(3) == 0) {
                return new LOTRWorldGenRohanVillagePasture(false);
            }
            return new LOTRWorldGenRohanVillageFarm(false);
        }
        
        private void setupFort(final Random random) {
            this.addStructure(new LOTRWorldGenRohanFortress(false), 0, -13, 0, true);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClasses(LOTREntityRohirrimWarrior.class, LOTREntityRohirrimArcher.class);
                    spawner.setCheckRanges(40, -12, 12, 30);
                    spawner.setSpawnRanges(32, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenRohanGatehouse(false), 0, -53, 0, true);
            final int towerX = 46;
            for (final int i1 : new int[] { -towerX, towerX }) {
                this.addStructure(new LOTRWorldGenRohanWatchtower(false), i1, -towerX, 0, true);
                this.addStructure(new LOTRWorldGenRohanWatchtower(false), i1, towerX, 2, true);
            }
            for (final int i1 : new int[] { -35, 35 }) {
                this.addStructure(new LOTRWorldGenRohanStables(false), i1, -14, 0, true);
            }
            int farmZ = -20;
            for (int l = 0; l <= 1; ++l) {
                final int farmX = 30 - l * 12;
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), -farmX, farmZ, 2);
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), farmX, farmZ, 2);
            }
            farmZ = 26;
            for (int l = -2; l <= 2; ++l) {
                final int farmX = l * 12;
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), -farmX, farmZ, 0);
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), farmX, farmZ, 0);
            }
            for (final int i2 : new int[] { -51, 51 }) {
                for (final int k1 : new int[] { -51, 51 }) {
                    this.addStructure(new LOTRWorldGenRohanFortCorner(false), i2, k1, 0, true);
                }
            }
            for (int l = 0; l <= 4; ++l) {
                final int wallX = 13 + l * 8;
                final int wallZ = -51;
                this.addStructure(new LOTRWorldGenRohanFortWall(false, -3, 4), -wallX, wallZ, 0, true);
                this.addStructure(new LOTRWorldGenRohanFortWall(false, -4, 3), wallX, wallZ, 0, true);
            }
            for (int l = -5; l <= 5; ++l) {
                final int wallX = l * 9;
                final int wallZ = 51;
                this.addStructure(new LOTRWorldGenRohanFortWall(false), wallX, wallZ, 2, true);
                this.addStructure(new LOTRWorldGenRohanFortWall(false), -wallZ, wallX, 3, true);
                this.addStructure(new LOTRWorldGenRohanFortWall(false), wallZ, wallX, 1, true);
            }
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            final int i2 = Math.abs(i);
            final int k2 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                final int dSq = i * i + k * k;
                final int imn = 20 + random.nextInt(4);
                if (dSq < imn * imn) {
                    return LOTRRoadType.PATH;
                }
                final int omn = 50 - random.nextInt(4);
                final int omx = 56 + random.nextInt(4);
                if (dSq > omn * omn && dSq < omx * omx) {
                    return LOTRRoadType.PATH;
                }
                if (dSq < 2500) {
                    final int d1 = Math.abs(i2 - k2);
                    if (d1 <= 2 + random.nextInt(4)) {
                        return LOTRRoadType.PATH;
                    }
                }
                if (this.palisade && k < -56 && k > -81 && i2 <= 2 + random.nextInt(4)) {
                    return LOTRRoadType.PATH;
                }
            }
            if (this.villageType == VillageType.FORT) {
                if (k <= -14 && k >= -49 && i2 <= 2) {
                    return LOTRRoadType.ROHAN;
                }
                if (k <= -14 && k >= -17 && i2 <= 37) {
                    return LOTRRoadType.PATH;
                }
                if (k >= -14 && k <= 20 && i2 >= 19 && i2 <= 22) {
                    return LOTRRoadType.PATH;
                }
                if (k >= 20 && k <= 23 && i2 <= 37) {
                    return LOTRRoadType.PATH;
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
