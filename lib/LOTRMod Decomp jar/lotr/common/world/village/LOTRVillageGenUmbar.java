// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.structure2.LOTRWorldGenUmbarFortCorner;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortWall;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortGate;
import lotr.common.world.structure2.LOTRWorldGenUmbarTraining;
import lotr.common.world.structure2.LOTRWorldGenUmbarBarracks;
import lotr.common.world.structure2.LOTRWorldGenUmbarTower;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortress;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownCorner;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownWall;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownGate;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownGate;
import lotr.common.world.structure2.LOTRWorldGenUmbarStatue;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownTree;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownFlowers;
import lotr.common.world.structure2.LOTRWorldGenUmbarLamp;
import lotr.common.world.structure2.LOTRWorldGenUmbarBazaar;
import lotr.common.world.structure2.LOTRWorldGenUmbarPasture;
import lotr.common.world.structure2.LOTRWorldGenUmbarFarm;
import lotr.common.world.structure2.LOTRWorldGenUmbarHouse;
import lotr.common.world.structure2.LOTRWorldGenUmbarStables;
import lotr.common.world.structure2.LOTRWorldGenUmbarSmithy;
import lotr.common.world.structure2.LOTRWorldGenUmbarTavern;
import lotr.common.world.structure2.LOTRWorldGenUmbarMansion;
import lotr.common.world.structure2.LOTRWorldGenUmbarVillageSign;
import lotr.common.world.structure2.LOTRWorldGenSouthronVillageSign;
import lotr.common.world.structure2.LOTRWorldGenUmbarWell;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import lotr.common.entity.npc.LOTREntityUmbarian;
import lotr.common.entity.LOTREntityNPCRespawner;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenUmbar extends LOTRVillageGenSouthron
{
    public LOTRVillageGenUmbar(final LOTRBiome biome, final float f) {
        super(biome, f);
    }
    
    @Override
    protected AbstractInstance createVillageInstance(final World world, final int i, final int k, final Random random) {
        return new InstanceUmbar(world, i, k, random);
    }
    
    public class InstanceUmbar extends Instance
    {
        public InstanceUmbar(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setCivilianSpawnClass(final LOTREntityNPCRespawner spawner) {
            spawner.setSpawnClass(LOTREntityUmbarian.class);
        }
        
        @Override
        protected void setWarriorSpawnClasses(final LOTREntityNPCRespawner spawner) {
            spawner.setSpawnClasses(LOTREntityUmbarWarrior.class, LOTREntityUmbarArcher.class);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getWell(final Random random) {
            return new LOTRWorldGenUmbarWell(false);
        }
        
        @Override
        protected LOTRWorldGenSouthronVillageSign getSignpost(final Random random) {
            return new LOTRWorldGenUmbarVillageSign(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getMansion(final Random random) {
            return new LOTRWorldGenUmbarMansion(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTavern(final Random random) {
            return new LOTRWorldGenUmbarTavern(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (random.nextInt(6) == 0) {
                return new LOTRWorldGenUmbarSmithy(false);
            }
            if (random.nextInt(6) == 0) {
                return new LOTRWorldGenUmbarStables(false);
            }
            return new LOTRWorldGenUmbarHouse(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getRandomFarm(final Random random) {
            if (random.nextBoolean()) {
                return new LOTRWorldGenUmbarFarm(false);
            }
            return new LOTRWorldGenUmbarPasture(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getHouse(final Random random) {
            return new LOTRWorldGenUmbarHouse(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getSmithy(final Random random) {
            return new LOTRWorldGenUmbarSmithy(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getBazaar(final Random random) {
            return new LOTRWorldGenUmbarBazaar(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getLamp(final Random random) {
            return new LOTRWorldGenUmbarLamp(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getFlowers(final Random random) {
            return new LOTRWorldGenUmbarTownFlowers(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTree(final Random random) {
            return new LOTRWorldGenUmbarTownTree(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getStatue(final Random random) {
            return new LOTRWorldGenUmbarStatue(false);
        }
        
        @Override
        protected LOTRWorldGenSouthronTownGate getTownGate(final Random random) {
            return new LOTRWorldGenUmbarTownGate(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTownWallShort(final Random random) {
            return new LOTRWorldGenUmbarTownWall.Short(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTownWallLong(final Random random) {
            return new LOTRWorldGenUmbarTownWall.Long(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTownWallSideMid(final Random random) {
            return new LOTRWorldGenUmbarTownWall.SideMid(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTownWallExtra(final Random random) {
            return new LOTRWorldGenUmbarTownWall.Extra(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTownWallCorner(final Random random) {
            return new LOTRWorldGenUmbarTownCorner(false);
        }
        
        @Override
        protected void placeChampionRespawner() {
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getFortress(final Random random) {
            return new LOTRWorldGenUmbarFortress(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTower(final Random random) {
            return new LOTRWorldGenUmbarTower(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getStables(final Random random) {
            return new LOTRWorldGenUmbarStables(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getBarracks(final Random random) {
            return new LOTRWorldGenUmbarBarracks(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getTraining(final Random random) {
            return new LOTRWorldGenUmbarTraining(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getFortGate(final Random random) {
            return new LOTRWorldGenUmbarFortGate(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getFortWallShort(final Random random) {
            return new LOTRWorldGenUmbarFortWall.Short(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getFortWallLong(final Random random) {
            return new LOTRWorldGenUmbarFortWall.Long(false);
        }
        
        @Override
        protected LOTRWorldGenStructureBase2 getFortCorner(final Random random) {
            return new LOTRWorldGenUmbarFortCorner(false);
        }
    }
}
