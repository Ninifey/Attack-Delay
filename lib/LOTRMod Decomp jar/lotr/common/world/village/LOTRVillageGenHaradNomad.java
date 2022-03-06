// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenNomadTent;
import lotr.common.world.structure2.LOTRWorldGenNomadBazaarTent;
import lotr.common.world.structure2.LOTRWorldGenNomadChieftainTent;
import lotr.common.world.structure2.LOTRWorldGenNomadWell;
import lotr.common.world.structure2.LOTRWorldGenNomadTentLarge;
import lotr.common.entity.npc.LOTREntityNomadArcher;
import lotr.common.entity.npc.LOTREntityNomadWarrior;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenHaradNomad extends LOTRVillageGen
{
    public LOTRVillageGenHaradNomad(final LOTRBiome biome, final float f) {
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
        SMALL, 
        BIG;
    }
    
    public class Instance extends AbstractInstance
    {
        public VillageType villageType;
        private int numOuterHouses;
        
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
            if (random.nextInt(3) == 0) {
                this.villageType = VillageType.BIG;
                this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 8, 14);
            }
            else {
                this.villageType = VillageType.SMALL;
                this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 4, 7);
            }
        }
        
        @Override
        public boolean isFlat() {
            return false;
        }
        
        @Override
        protected void addVillageStructures(final Random random) {
            this.setupVillage(random);
        }
        
        private void setupVillage(final Random random) {
            if (this.villageType == VillageType.SMALL) {
                this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                    @Override
                    public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClass(LOTREntityNomad.class);
                        spawner.setCheckRanges(64, -12, 12, 24);
                        spawner.setSpawnRanges(32, -6, 6, 32);
                        spawner.setBlockEnemySpawnRange(40);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                    @Override
                    public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClasses(LOTREntityNomadWarrior.class, LOTREntityNomadArcher.class);
                        spawner.setCheckRanges(64, -12, 12, 12);
                        spawner.setSpawnRanges(32, -6, 6, 32);
                        spawner.setBlockEnemySpawnRange(40);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNomadTentLarge(false), 0, -8, 0, true);
            }
            else if (this.villageType == VillageType.BIG) {
                this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                    @Override
                    public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClass(LOTREntityNomad.class);
                        spawner.setCheckRanges(80, -12, 12, 50);
                        spawner.setSpawnRanges(40, -8, 8, 40);
                        spawner.setBlockEnemySpawnRange(60);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                    @Override
                    public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClasses(LOTREntityNomadWarrior.class, LOTREntityNomadArcher.class);
                        spawner.setCheckRanges(80, -12, 12, 24);
                        spawner.setSpawnRanges(40, -8, 8, 40);
                        spawner.setBlockEnemySpawnRange(60);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNomadWell(false), 0, 0, 0, true);
                this.addStructure(new LOTRWorldGenNomadChieftainTent(false), 0, 14, 0, true);
                this.addStructure(new LOTRWorldGenNomadBazaarTent(false), 0, -14, 2, true);
                this.addStructure(new LOTRWorldGenNomadTentLarge(false), -14, 0, 1, true);
                this.addStructure(new LOTRWorldGenNomadTentLarge(false), 14, 0, 3, true);
            }
            int minOuterSize = 0;
            if (this.villageType == VillageType.SMALL) {
                minOuterSize = MathHelper.getRandomIntegerInRange(random, 15, 25);
            }
            else if (this.villageType == VillageType.BIG) {
                minOuterSize = MathHelper.getRandomIntegerInRange(random, 35, 45);
            }
            final float frac = 1.0f / this.numOuterHouses;
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
                final int l = minOuterSize + random.nextInt(5);
                final int i = Math.round(l * cos);
                final int k = Math.round(l * sin);
                this.addStructure(new LOTRWorldGenNomadTent(false), i, k, r);
            }
        }
        
        @Override
        protected LOTRRoadType getPath(final Random random, final int i, final int k) {
            return null;
        }
        
        @Override
        public boolean isVillageSurface(final World world, final int i, final int j, final int k) {
            return false;
        }
    }
}
