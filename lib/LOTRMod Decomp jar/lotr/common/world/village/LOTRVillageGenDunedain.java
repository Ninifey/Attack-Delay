// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenRangerHouse;
import lotr.common.world.structure2.LOTRWorldGenRangerLodge;
import lotr.common.world.structure2.LOTRWorldGenRangerStables;
import lotr.common.world.structure2.LOTRWorldGenRangerSmithy;
import lotr.common.world.structure2.LOTRWorldGenRangerVillagePalisade;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenRangerVillageLight;
import lotr.common.world.structure2.LOTRWorldGenRangerWell;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTREntityDunedain;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenDunedain extends LOTRVillageGen
{
    public LOTRVillageGenDunedain(final LOTRBiome biome, final float f) {
        super(biome);
        super.gridScale = 12;
        super.gridRandomDisplace = 1;
        super.spawnChance = f;
        super.villageChunkSize = 4;
    }
    
    @Override
    protected AbstractInstance createVillageInstance(final World world, final int i, final int k, final Random random) {
        return new Instance(world, i, k, random);
    }
    
    public enum VillageType
    {
        VILLAGE;
    }
    
    public class Instance extends AbstractInstance
    {
        public VillageType villageType;
        private int innerSize;
        private static final int innerSizeMin = 12;
        private static final int innerSizeMax = 20;
        private static final int roadWidth = 2;
        private static final int pathFuzz = 3;
        private static final int outerGap = 12;
        private boolean palisade;
        private static final int palisadeGap = 16;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
            this.villageType = VillageType.VILLAGE;
            this.innerSize = MathHelper.getRandomIntegerInRange(random, 12, 20);
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
        }
        
        private void setupVillage(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityDunedain.class);
                    spawner.setCheckRanges(40, -12, 12, 30);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityRangerNorth.class);
                    spawner.setCheckRanges(40, -12, 12, 12);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenRangerWell(false), 0, -2, 0, true);
            final int lampX = 8;
            for (final int i : new int[] { -lampX, lampX }) {
                for (final int k : new int[] { -lampX, lampX }) {
                    this.addStructure(new LOTRWorldGenRangerVillageLight(false), i, k, 0);
                }
            }
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
                if (this.palisade && sin < 0.0f && Math.abs(cos) <= 0.5f) {
                    continue;
                }
                if (random.nextInt(3) != 0) {
                    int l = this.innerSize + 3;
                    if (random.nextInt(3) == 0) {
                        l += 12;
                    }
                    final int j = Math.round(l * cos);
                    final int m = Math.round(l * sin);
                    this.addStructure(this.getRandomHouse(random), j, m, r);
                }
                else {
                    if (random.nextInt(4) != 0) {
                        continue;
                    }
                    int l = this.innerSize + 5;
                    if (random.nextInt(3) == 0) {
                        l += 12;
                    }
                    final int j = Math.round(l * cos);
                    final int m = Math.round(l * sin);
                    this.addStructure(new LOTRWorldGenHayBales(false), j, m, r);
                }
            }
            if (this.palisade) {
                final int rPalisade = this.innerSize + 12 + 16;
                final int rSq = rPalisade * rPalisade;
                final int rMax = rPalisade + 1;
                final int rSqMax = rMax * rMax;
                for (int i2 = -rPalisade; i2 <= rPalisade; ++i2) {
                    for (int k2 = -rPalisade; k2 <= rPalisade; ++k2) {
                        if (Math.abs(i2) > 5 || k2 >= 0) {
                            final int dSq = i2 * i2 + k2 * k2;
                            if (dSq >= rSq && dSq < rSqMax) {
                                this.addStructure(new LOTRWorldGenRangerVillagePalisade(false), i2, k2, 0);
                            }
                        }
                    }
                }
            }
        }
        
        private LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (random.nextInt(3) == 0) {
                final int i = random.nextInt(3);
                if (i == 0) {
                    return new LOTRWorldGenRangerSmithy(false);
                }
                if (i == 1) {
                    return new LOTRWorldGenRangerStables(false);
                }
                if (i == 2) {
                    return new LOTRWorldGenRangerLodge(false);
                }
            }
            return new LOTRWorldGenRangerHouse(false);
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            final int i2 = Math.abs(i);
            final int k2 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                final int dSq = i * i + k * k;
                if (i2 <= 2 && k2 <= 2) {
                    return null;
                }
                final int imn = this.innerSize + random.nextInt(3);
                if (dSq < imn * imn) {
                    return LOTRRoadType.PATH;
                }
                if (this.palisade && k < 0 && k > -(this.innerSize + 12 + 16) && i2 <= 2 + random.nextInt(3)) {
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
