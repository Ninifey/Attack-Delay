// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenNearHaradTent;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTower;
import lotr.common.world.structure2.LOTRWorldGenHarnedorFort;
import lotr.common.world.structure2.LOTRWorldGenHarnedorHouse;
import lotr.common.world.structure2.LOTRWorldGenHarnedorStables;
import lotr.common.world.structure2.LOTRWorldGenHarnedorSmithy;
import lotr.common.world.structure2.LOTRWorldGenHarnedorHouseRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPalisade;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPalisadeRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorVillageSign;
import lotr.common.world.structure2.LOTRWorldGenHarnedorFarm;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPasture;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTavern;
import lotr.common.world.structure2.LOTRWorldGenHarnedorMarket;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTavernRuined;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenHarnedor extends LOTRVillageGen
{
    private boolean isRuinedVillage;
    
    public LOTRVillageGenHarnedor(final LOTRBiome biome, final float f) {
        super(biome);
        super.gridScale = 12;
        super.gridRandomDisplace = 1;
        super.spawnChance = f;
        super.villageChunkSize = 4;
    }
    
    public LOTRVillageGenHarnedor setRuined() {
        this.isRuinedVillage = true;
        return this;
    }
    
    @Override
    protected AbstractInstance createVillageInstance(final World world, final int i, final int k, final Random random) {
        return new Instance(world, i, k, random);
    }
    
    public enum VillageType
    {
        VILLAGE, 
        FORTRESS;
    }
    
    public class Instance extends AbstractInstance
    {
        public VillageType villageType;
        public String[] villageName;
        private int numOuterHouses;
        private static final int pathInner = 17;
        private static final int roadWidth = 5;
        private static final int pathFuzz = 3;
        private static final int rHouses = 25;
        private static final int rFarms = 45;
        private boolean palisade;
        private static final int rPalisade = 61;
        private static final int rTowers = 24;
        private static final int rFortPalisade = 42;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
            if (random.nextInt(4) == 0) {
                this.villageType = VillageType.FORTRESS;
            }
            else {
                this.villageType = VillageType.VILLAGE;
            }
            this.villageName = LOTRNames.getHaradVillageName(random);
            this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 5, 8);
            this.palisade = (random.nextInt(3) != 0);
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
            else {
                this.setupFortress(random);
            }
        }
        
        private void setupVillage(final Random random) {
            if (!LOTRVillageGenHarnedor.this.isRuinedVillage) {
                this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                    @Override
                    public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClass(LOTREntityHarnedhrim.class);
                        spawner.setCheckRanges(64, -12, 12, 24);
                        spawner.setSpawnRanges(32, -6, 6, 32);
                        spawner.setBlockEnemySpawnRange(64);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                    @Override
                    public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
                        spawner.setCheckRanges(64, -12, 12, 12);
                        spawner.setSpawnRanges(32, -6, 6, 32);
                        spawner.setBlockEnemySpawnRange(64);
                    }
                }, 0, 0, 0);
            }
            if (LOTRVillageGenHarnedor.this.isRuinedVillage) {
                this.addStructure(new LOTRWorldGenHarnedorTavernRuined(false), 3, -7, 0, true);
            }
            else if (random.nextBoolean()) {
                this.addStructure(new LOTRWorldGenHarnedorMarket(false), 0, -8, 0, true);
            }
            else {
                this.addStructure(new LOTRWorldGenHarnedorTavern(false), 3, -7, 0, true);
            }
            float frac = 1.0f / this.numOuterHouses;
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
                final int l = 25;
                final int i = Math.round(l * cos);
                final int k = Math.round(l * sin);
                if (this.palisade && k < 0 && Math.abs(i) < 10) {
                    continue;
                }
                this.addStructure(this.getRandomHouse(random), i, k, r);
            }
            if (!LOTRVillageGenHarnedor.this.isRuinedVillage) {
                final int numFarms = this.numOuterHouses * 2;
                frac = 1.0f / numFarms;
                turn = 0.0f;
                while (turn < 1.0f) {
                    turn += frac;
                    final float turnR2 = (float)Math.toRadians(turn * 360.0f);
                    final float sin2 = MathHelper.sin(turnR2);
                    final float cos2 = MathHelper.cos(turnR2);
                    int r2 = 0;
                    final float turn3 = turn * 8.0f;
                    if (turn3 >= 1.0f && turn3 < 3.0f) {
                        r2 = 0;
                    }
                    else if (turn3 >= 3.0f && turn3 < 5.0f) {
                        r2 = 1;
                    }
                    else if (turn3 >= 5.0f && turn3 < 7.0f) {
                        r2 = 2;
                    }
                    else if (turn3 >= 7.0f || turn3 < 1.0f) {
                        r2 = 3;
                    }
                    final int j = 45;
                    final int m = Math.round(j * cos2);
                    final int k2 = Math.round(j * sin2);
                    if (this.palisade && k2 < 0 && Math.abs(m) < 10) {
                        continue;
                    }
                    if (random.nextInt(3) == 0) {
                        this.addStructure(new LOTRWorldGenHayBales(false), m, k2, r2);
                    }
                    else if (random.nextInt(3) == 0) {
                        this.addStructure(new LOTRWorldGenHarnedorPasture(false), m, k2, r2);
                    }
                    else {
                        this.addStructure(new LOTRWorldGenHarnedorFarm(false), m, k2, r2);
                    }
                }
            }
            if (!LOTRVillageGenHarnedor.this.isRuinedVillage) {
                if (this.palisade) {
                    this.addStructure(new LOTRWorldGenHarnedorVillageSign(false).setSignText(this.villageName), 5 * (random.nextBoolean() ? 1 : -1), -56, 0, true);
                }
                else {
                    this.addStructure(new LOTRWorldGenHarnedorVillageSign(false).setSignText(this.villageName), 0, -16, 0, true);
                }
            }
            if (this.palisade) {
                final int rSq = 3721;
                final int rMax = 62;
                final int rSqMax = rMax * rMax;
                for (int i2 = -61; i2 <= 61; ++i2) {
                    for (int k3 = -61; k3 <= 61; ++k3) {
                        final int i3 = Math.abs(i2);
                        if (i3 > 4 || k3 >= 0) {
                            final int dSq = i2 * i2 + k3 * k3;
                            if (dSq >= rSq && dSq < rSqMax) {
                                LOTRWorldGenHarnedorPalisade palisade;
                                if (LOTRVillageGenHarnedor.this.isRuinedVillage) {
                                    if (random.nextBoolean()) {
                                        continue;
                                    }
                                    palisade = new LOTRWorldGenHarnedorPalisadeRuined(false);
                                }
                                else {
                                    palisade = new LOTRWorldGenHarnedorPalisade(false);
                                }
                                if (i3 == 5 && k3 < 0) {
                                    palisade.setTall();
                                }
                                this.addStructure(palisade, i2, k3, 0);
                            }
                        }
                    }
                }
            }
        }
        
        private LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (LOTRVillageGenHarnedor.this.isRuinedVillage) {
                return new LOTRWorldGenHarnedorHouseRuined(false);
            }
            if (random.nextInt(5) == 0) {
                return new LOTRWorldGenHarnedorSmithy(false);
            }
            if (random.nextInt(4) == 0) {
                return new LOTRWorldGenHarnedorStables(false);
            }
            return new LOTRWorldGenHarnedorHouse(false);
        }
        
        private void setupFortress(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityHarnedhrim.class);
                    spawner.setCheckRanges(64, -12, 12, 16);
                    spawner.setSpawnRanges(24, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(50);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenHarnedorFort(false), 0, -12, 0, true);
            this.addStructure(new LOTRWorldGenHarnedorTower(false), -24, -24, 0, true);
            this.addStructure(new LOTRWorldGenHarnedorTower(false), 24, -24, 0, true);
            this.addStructure(new LOTRWorldGenHarnedorTower(false), -24, 24, 2, true);
            this.addStructure(new LOTRWorldGenHarnedorTower(false), 24, 24, 2, true);
            for (int l = -1; l <= 1; ++l) {
                final int k = l * 10;
                final int i = 24;
                this.addStructure(new LOTRWorldGenNearHaradTent(false), -i, k, 1, true);
                this.addStructure(new LOTRWorldGenNearHaradTent(false), i, k, 3, true);
            }
            final int rSq = 1764;
            final int rMax = 43;
            final int rSqMax = rMax * rMax;
            for (int j = -42; j <= 42; ++j) {
                for (int m = -42; m <= 42; ++m) {
                    final int i2 = Math.abs(j);
                    if (i2 > 4 || m >= 0) {
                        final int dSq = j * j + m * m;
                        if (dSq >= rSq && dSq < rSqMax) {
                            final LOTRWorldGenHarnedorPalisade palisade = new LOTRWorldGenHarnedorPalisade(false);
                            if (i2 == 5 && m < 0) {
                                palisade.setTall();
                            }
                            this.addStructure(palisade, j, m, 0);
                        }
                    }
                }
            }
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            final int i2 = Math.abs(i);
            final int k2 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                if (LOTRVillageGenHarnedor.this.isRuinedVillage && random.nextInt(4) == 0) {
                    return null;
                }
                final int dSq = i * i + k * k;
                final int imn = 17 - random.nextInt(3);
                final int imx = 22 + random.nextInt(3);
                if (dSq > imn * imn && dSq < imx * imx) {
                    return LOTRRoadType.PATH;
                }
                if (this.palisade && k <= -imx && k >= -66 && i2 < 2 + random.nextInt(3)) {
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
