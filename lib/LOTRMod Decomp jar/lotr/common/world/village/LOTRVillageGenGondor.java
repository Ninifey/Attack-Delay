// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothWatchtower;
import lotr.common.world.structure2.LOTRWorldGenLamedonWatchtower;
import lotr.common.world.structure2.LOTRWorldGenBlackrootWatchtower;
import lotr.common.world.structure2.LOTRWorldGenPinnathGelinWatchtower;
import lotr.common.world.structure2.LOTRWorldGenPelargirWatchtower;
import lotr.common.world.structure2.LOTRWorldGenLebenninWatchtower;
import lotr.common.world.structure2.LOTRWorldGenLossarnachWatchtower;
import lotr.common.world.structure2.LOTRWorldGenGondorWatchtower;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothStables;
import lotr.common.world.structure2.LOTRWorldGenLamedonFortress;
import lotr.common.world.structure2.LOTRWorldGenBlackrootFortress;
import lotr.common.world.structure2.LOTRWorldGenPinnathGelinFortress;
import lotr.common.world.structure2.LOTRWorldGenPelargirFortress;
import lotr.common.world.structure2.LOTRWorldGenLebenninFortress;
import lotr.common.world.structure2.LOTRWorldGenLossarnachFortress;
import lotr.common.world.structure2.LOTRWorldGenGondorFortress;
import lotr.common.world.structure2.LOTRWorldGenGondorFortWallCorner;
import lotr.common.world.structure2.LOTRWorldGenGondorFortWall;
import lotr.common.world.structure2.LOTRWorldGenGondorFortGate;
import lotr.common.world.structure2.LOTRWorldGenGondorTownWall;
import lotr.common.world.structure2.LOTRWorldGenGondorGatehouse;
import com.google.common.math.IntMath;
import lotr.common.world.structure2.LOTRWorldGenGondorBath;
import lotr.common.world.structure2.LOTRWorldGenGondorTownBench;
import lotr.common.world.structure2.LOTRWorldGenGondorTownTrees;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenGondorStoneHouse;
import lotr.common.world.structure2.LOTRWorldGenGondorLampPost;
import lotr.common.world.structure2.LOTRWorldGenGondorTownGarden;
import lotr.common.world.structure2.LOTRWorldGenGondorVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenGondorHouse;
import lotr.common.world.structure2.LOTRWorldGenGondorBarn;
import lotr.common.world.structure2.LOTRWorldGenGondorSmithy;
import lotr.common.world.structure2.LOTRWorldGenGondorStables;
import lotr.common.world.structure2.LOTRWorldGenGondorVillageSign;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure2.LOTRWorldGenGondorMarketStall;
import lotr.common.world.structure2.LOTRWorldGenGondorTavern;
import lotr.common.world.structure2.LOTRWorldGenGondorCottage;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenGondorWell;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;

public class LOTRVillageGenGondor extends LOTRVillageGen
{
    private static final int INNER_MAX = 20;
    private static final int OUTER_MIN = 53;
    private static final int OUTER_MAX = 60;
    private static final int SPOKE_WIDTH = 2;
    private static final int PATH_FUZZ = 4;
    private static final int FORT_WALL_Z = 37;
    private static final int FORT_WALL_CENTRE_X = 11;
    private static final int FORT_WALL_CORNER = 30;
    private static final int TOWER_X = 23;
    private static final int TOWER_Z = 33;
    private static final int SIGN_R = 50;
    private static final int SIGN_DISP = 7;
    private LOTRWorldGenGondorStructure.GondorFiefdom villageFief;
    
    public LOTRVillageGenGondor(final LOTRBiome biome, final LOTRWorldGenGondorStructure.GondorFiefdom fief, final float f) {
        super(biome);
        super.gridScale = 16;
        super.gridRandomDisplace = 2;
        super.spawnChance = f;
        super.villageChunkSize = 5;
        this.villageFief = fief;
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
            this.villageName = LOTRNames.getGondorVillageName(random);
            if (random.nextInt(4) == 0) {
                this.villageType = VillageType.FORT;
            }
            else if (random.nextInt(4) == 0) {
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
        protected void addStructure(final LOTRWorldGenStructureBase2 structure, final int x, final int z, final int r, final boolean force) {
            super.addStructure(structure, x, z, r, force);
            if (structure instanceof LOTRWorldGenGondorStructure) {
                ((LOTRWorldGenGondorStructure)structure).strFief = LOTRVillageGenGondor.this.villageFief;
            }
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
                this.setupFortVillage(random);
            }
        }
        
        private void setupVillage(final Random random) {
            this.addStructure(new LOTRWorldGenGondorWell(false), 0, -4, 0, true);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGondorMan.class);
                    spawner.setCheckRanges(40, -12, 12, 40);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTRVillageGenGondor.this.villageFief.getLevyClasses()[0]);
                    spawner.setCheckRanges(40, -12, 12, 16);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenGondorCottage(false), -21, 0, 1);
            this.addStructure(new LOTRWorldGenGondorCottage(false), 0, -21, 2);
            this.addStructure(new LOTRWorldGenGondorCottage(false), 21, 0, 3);
            this.addStructure(new LOTRWorldGenGondorTavern(false), 0, 21, 0);
            if (random.nextBoolean()) {
                if (random.nextInt(3) == 0) {
                    this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), -9, -12, 1);
                }
                if (random.nextInt(3) == 0) {
                    this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), 9, -12, 3);
                }
                if (random.nextInt(3) == 0) {
                    this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), -9, 12, 1);
                }
                if (random.nextInt(3) == 0) {
                    this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), 9, 12, 3);
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
                if (random.nextBoolean()) {
                    final int l = 61;
                    final int i = Math.round(l * cos);
                    final int k = Math.round(l * sin);
                    this.addStructure(this.getRandomHouse(random), i, k, r);
                }
                else {
                    if (random.nextInt(3) == 0) {
                        continue;
                    }
                    final int l = 65;
                    final int i = Math.round(l * cos);
                    final int k = Math.round(l * sin);
                    this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                }
            }
            final int signPos = Math.round(50.0f * MathHelper.cos((float)Math.toRadians(45.0)));
            final int signDisp = Math.round(7.0f * MathHelper.cos((float)Math.toRadians(45.0)));
            this.addStructure(new LOTRWorldGenGondorVillageSign(false).setSignText(this.villageName), -signPos, -signPos + signDisp, 1);
            this.addStructure(new LOTRWorldGenGondorVillageSign(false).setSignText(this.villageName), signPos, -signPos + signDisp, 3);
            this.addStructure(new LOTRWorldGenGondorVillageSign(false).setSignText(this.villageName), -signPos, signPos - signDisp, 1);
            this.addStructure(new LOTRWorldGenGondorVillageSign(false).setSignText(this.villageName), signPos, signPos - signDisp, 3);
            final int farmX = 38;
            final int farmZ = 17;
            final int farmSize = 6;
            if (random.nextBoolean()) {
                this.addStructure(this.getRandomFarm(random), -farmX + farmSize, -farmZ, 1);
            }
            if (random.nextBoolean()) {
                this.addStructure(this.getRandomFarm(random), -farmZ + farmSize, -farmX, 1);
            }
            if (random.nextBoolean()) {
                this.addStructure(this.getRandomFarm(random), farmX - farmSize, -farmZ, 3);
            }
            if (random.nextBoolean()) {
                this.addStructure(this.getRandomFarm(random), farmZ - farmSize, -farmX, 3);
            }
            if (random.nextBoolean()) {
                this.addStructure(this.getRandomFarm(random), -farmX + farmSize, farmZ, 1);
            }
            if (random.nextBoolean()) {
                this.addStructure(this.getRandomFarm(random), farmX - farmSize, farmZ, 3);
            }
        }
        
        private LOTRWorldGenStructureBase2 getRandomHouse(final Random random) {
            if (random.nextInt(5) == 0) {
                final int i = random.nextInt(3);
                if (i == 0) {
                    return new LOTRWorldGenGondorStables(false);
                }
                if (i == 1) {
                    return new LOTRWorldGenGondorSmithy(false);
                }
                if (i == 2) {
                    return new LOTRWorldGenGondorBarn(false);
                }
            }
            return new LOTRWorldGenGondorHouse(false);
        }
        
        private LOTRWorldGenStructureBase2 getRandomFarm(final Random random) {
            if (!random.nextBoolean()) {
                return new LOTRWorldGenGondorVillageFarm.Tree(false);
            }
            if (random.nextBoolean()) {
                return new LOTRWorldGenGondorVillageFarm.Animals(false);
            }
            return new LOTRWorldGenGondorVillageFarm.Crops(false);
        }
        
        private void setupTown(final Random random) {
            final boolean outerTavern = random.nextBoolean();
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGondorMan.class);
                    spawner.setCheckRanges(80, -12, 12, 100);
                    spawner.setSpawnRanges(60, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (final int i1 : new int[] { -40, 40 }) {
                for (final int k1 : new int[] { -40, 40 }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            spawner.setSpawnClasses(LOTRVillageGenGondor.this.villageFief.getLevyClasses()[0], LOTRVillageGenGondor.this.villageFief.getLevyClasses()[1]);
                            spawner.setCheckRanges(40, -12, 12, 16);
                            spawner.setSpawnRanges(20, -6, 6, 64);
                            spawner.setBlockEnemySpawnRange(60);
                        }
                    }, i1, k1, 0);
                }
            }
            this.addStructure(new LOTRWorldGenGondorWell(false), 0, -4, 0, true);
            final int stallPos = 12;
            for (int k2 = -1; k2 <= 1; ++k2) {
                final int k3 = k2 * stallPos;
                if (random.nextInt(3) != 0) {
                    this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), -stallPos + 3, k3, 1, true);
                }
                if (random.nextInt(3) != 0) {
                    this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), stallPos - 3, k3, 3, true);
                }
            }
            if (random.nextInt(3) != 0) {
                this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), 0, stallPos - 3, 0, true);
            }
            if (random.nextInt(3) != 0) {
                this.addStructure(LOTRWorldGenGondorMarketStall.getRandomStall(random, false), 0, -stallPos + 3, 2, true);
            }
            final int flowerX = 12;
            final int flowerZ = 18;
            for (final int i2 : new int[] { -flowerX, flowerX }) {
                this.addStructure(new LOTRWorldGenGondorTownGarden(false), i2, flowerZ, 0, true);
                this.addStructure(new LOTRWorldGenGondorTownGarden(false), i2, -flowerZ, 2, true);
                this.addStructure(new LOTRWorldGenGondorTownGarden(false), -flowerZ, i2, 1, true);
                this.addStructure(new LOTRWorldGenGondorTownGarden(false), flowerZ, i2, 3, true);
            }
            final int lampZ = 21;
            for (final int i3 : new int[] { -1, 1 }) {
                final int lampX = i3 * 6;
                this.addStructure(new LOTRWorldGenGondorLampPost(false), lampX, lampZ, 0, true);
                this.addStructure(new LOTRWorldGenGondorLampPost(false), lampX, -lampZ, 2, true);
                if (i3 != -1) {
                    this.addStructure(new LOTRWorldGenGondorLampPost(false), -lampZ, lampX, 1, true);
                }
                this.addStructure(new LOTRWorldGenGondorLampPost(false), lampZ, lampX, 3, true);
            }
            int houseX = 24;
            for (int k4 = -1; k4 <= 1; ++k4) {
                final int houseZ = k4 * 12;
                if (k4 == 1) {
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), -houseX, houseZ, 1, true);
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseX, houseZ, 3, true);
                }
                if (k4 != 0) {
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseZ, houseX, 0, true);
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseZ, -houseX, 2, true);
                }
            }
            this.addStructure(new LOTRWorldGenGondorSmithy(false), 0, -26, 2, true);
            this.addStructure(new LOTRWorldGenGondorObelisk(false), 0, 27, 0, true);
            this.addStructure(new LOTRWorldGenGondorTavern(false), -houseX, -5, 1, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -47, -13, 2, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -47, 1, 0, true);
            for (final int i4 : new int[] { -43, -51 }) {
                this.addStructure(new LOTRWorldGenGondorTownBench(false), i4, -9, 2, true);
                this.addStructure(new LOTRWorldGenGondorTownBench(false), i4, -3, 0, true);
            }
            this.addStructure(new LOTRWorldGenGondorBath(false), houseX + 2, -6, 3, true);
            this.addStructure(new LOTRWorldGenGondorTownGarden(false), 51, -13, 2, true);
            this.addStructure(new LOTRWorldGenGondorTownGarden(false), 51, 1, 0, true);
            this.addStructure(new LOTRWorldGenGondorTownGarden(false), 52, -6, 3, true);
            int wellX = 22;
            int wellZ = 31;
            for (final int i5 : new int[] { -wellX, wellX }) {
                this.addStructure(new LOTRWorldGenGondorWell(false), i5, -wellZ, 2, true);
                this.addStructure(new LOTRWorldGenGondorWell(false), i5, wellZ, 0, true);
                this.addStructure(new LOTRWorldGenGondorWell(false), -wellZ, i5, 1, true);
                this.addStructure(new LOTRWorldGenGondorWell(false), wellZ, i5, 3, true);
            }
            houseX = 54;
            for (int k1 = -2; k1 <= 2; ++k1) {
                final int houseZ2 = k1 * 12;
                if (k1 <= -2 || k1 >= 1) {
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), -houseX, houseZ2, 3, true);
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseX, houseZ2, 1, true);
                }
                this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseZ2, houseX, 2, true);
                this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseZ2, -houseX, 0, true);
            }
            int treeX = 47;
            int treeZ = 35;
            for (final int i6 : new int[] { -treeX, treeX }) {
                this.addStructure(new LOTRWorldGenGondorTownTrees(false), i6, -treeZ, 0, true);
                this.addStructure(new LOTRWorldGenGondorTownTrees(false), i6, treeZ, 2, true);
                this.addStructure(new LOTRWorldGenGondorTownTrees(false), -treeZ, i6, 3, true);
                this.addStructure(new LOTRWorldGenGondorTownTrees(false), treeZ, i6, 1, true);
            }
            houseX = 64;
            final int lampX2 = 59;
            for (int k5 = -4; k5 <= 4; ++k5) {
                final int houseZ3 = k5 * 12;
                final boolean treepiece = IntMath.mod(k5, 2) == 1;
                if (treepiece) {
                    this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), -houseX - 2, houseZ3, 1, true);
                    this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), houseX + 2, houseZ3, 3, true);
                }
                else {
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), -houseX, houseZ3, 1, true);
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseX, houseZ3, 3, true);
                }
                if (treepiece) {
                    this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), houseZ3, -houseX - 2, 2, true);
                }
                else {
                    this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseZ3, -houseX, 2, true);
                }
                if (Math.abs(k5) >= 2 && (!outerTavern || k5 <= 2)) {
                    if (treepiece) {
                        this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), houseZ3, houseX + 2, 0, true);
                    }
                    else {
                        this.addStructure(new LOTRWorldGenGondorStoneHouse(false), houseZ3, houseX, 0, true);
                    }
                }
                this.addStructure(new LOTRWorldGenGondorLampPost(false), -lampX2, houseZ3, 1, true);
                this.addStructure(new LOTRWorldGenGondorLampPost(false), lampX2, houseZ3, 3, true);
                this.addStructure(new LOTRWorldGenGondorLampPost(false), houseZ3, lampX2, 0, true);
                this.addStructure(new LOTRWorldGenGondorLampPost(false), houseZ3, -lampX2, 2, true);
            }
            if (outerTavern) {
                this.addStructure(new LOTRWorldGenGondorTavern(false), 44, houseX, 0, true);
            }
            final int gardenX = 42;
            final int gardenZ = 48;
            this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), -gardenX, -gardenZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), -gardenX, gardenZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), gardenX, -gardenZ, 3, true);
            this.addStructure(new LOTRWorldGenGondorVillageFarm.Tree(false), gardenX, gardenZ, 3, true);
            final int obeliskX = 62;
            final int obeliskZ = 66;
            this.addStructure(new LOTRWorldGenGondorObelisk(false), -obeliskX, -obeliskZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorObelisk(false), -obeliskX, obeliskZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorObelisk(false), obeliskX, -obeliskZ, 3, true);
            this.addStructure(new LOTRWorldGenGondorObelisk(false), obeliskX, obeliskZ, 3, true);
            wellX = 64;
            wellZ = 57;
            this.addStructure(new LOTRWorldGenGondorWell(false), -wellX, -wellZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), -wellX, wellZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), wellX, -wellZ, 3, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), wellX, wellZ, 3, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), -wellZ, -wellX, 2, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), wellZ, -wellX, 2, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), -wellZ, wellX, 0, true);
            this.addStructure(new LOTRWorldGenGondorWell(false), wellZ, wellX, 0, true);
            treeX = 75;
            treeZ = 61;
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -treeX, -treeZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -treeX, treeZ, 1, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), treeX, -treeZ, 3, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), treeX, treeZ, 3, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -treeZ, -treeX, 2, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), treeZ, -treeX, 2, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -treeZ, treeX, 0, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), treeZ, treeX, 0, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), -14, 71, 1, true);
            this.addStructure(new LOTRWorldGenGondorTownTrees(false), 14, 71, 3, true);
            for (final int k6 : new int[] { 67, 75 }) {
                this.addStructure(new LOTRWorldGenGondorTownBench(false), -10, k6, 1, true);
                this.addStructure(new LOTRWorldGenGondorTownBench(false), 10, k6, 3, true);
            }
            this.addStructure(new LOTRWorldGenGondorGatehouse(false).setSignText(this.villageName), 0, 84, 2, true);
            this.addStructure(new LOTRWorldGenGondorLampPost(false), -4, 73, 0, true);
            this.addStructure(new LOTRWorldGenGondorLampPost(false), 4, 73, 0, true);
            final int towerX = 78;
            final int towerZ = 74;
            for (final int i7 : new int[] { -towerX, towerX }) {
                this.addStructure(this.getVillageWatchtower(), i7, -towerZ, 2, true);
                this.addStructure(this.getVillageWatchtower(), i7, towerZ, 0, true);
            }
            final int wallZ = 82;
            final int wallEndX = 76;
            for (int l = 0; l <= 3; ++l) {
                final int wallX = 12 + l * 16;
                this.addStructure(LOTRWorldGenGondorTownWall.Left(false), -wallX, wallZ, 2, true);
                this.addStructure(LOTRWorldGenGondorTownWall.Right(false), wallX, wallZ, 2, true);
            }
            this.addStructure(LOTRWorldGenGondorTownWall.LeftEndShort(false), -wallEndX, wallZ, 2, true);
            this.addStructure(LOTRWorldGenGondorTownWall.RightEndShort(false), wallEndX, wallZ, 2, true);
            this.addStructure(LOTRWorldGenGondorTownWall.Centre(false), -wallZ, 0, 3, true);
            this.addStructure(LOTRWorldGenGondorTownWall.Centre(false), wallZ, 0, 1, true);
            this.addStructure(LOTRWorldGenGondorTownWall.Centre(false), 0, -wallZ, 0, true);
            for (int l = 0; l <= 3; ++l) {
                final int wallX = 12 + l * 16;
                this.addStructure(LOTRWorldGenGondorTownWall.Left(false), -wallZ, -wallX, 3, true);
                this.addStructure(LOTRWorldGenGondorTownWall.Right(false), -wallZ, wallX, 3, true);
                this.addStructure(LOTRWorldGenGondorTownWall.Left(false), wallZ, wallX, 1, true);
                this.addStructure(LOTRWorldGenGondorTownWall.Right(false), wallZ, -wallX, 1, true);
                this.addStructure(LOTRWorldGenGondorTownWall.Left(false), wallX, -wallZ, 0, true);
                this.addStructure(LOTRWorldGenGondorTownWall.Right(false), -wallX, -wallZ, 0, true);
            }
            this.addStructure(LOTRWorldGenGondorTownWall.LeftEnd(false), -wallZ, -wallEndX, 3, true);
            this.addStructure(LOTRWorldGenGondorTownWall.RightEnd(false), -wallZ, wallEndX, 3, true);
            this.addStructure(LOTRWorldGenGondorTownWall.LeftEnd(false), wallZ, wallEndX, 1, true);
            this.addStructure(LOTRWorldGenGondorTownWall.RightEnd(false), wallZ, -wallEndX, 1, true);
            this.addStructure(LOTRWorldGenGondorTownWall.LeftEndShort(false), wallEndX, -wallZ, 0, true);
            this.addStructure(LOTRWorldGenGondorTownWall.RightEndShort(false), -wallEndX, -wallZ, 0, true);
        }
        
        private void setupFortVillage(final Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                @Override
                public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGondorMan.class);
                    spawner.setCheckRanges(50, -12, 12, 16);
                    spawner.setSpawnRanges(30, -6, 6, 40);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (final int i1 : new int[] { -20, 20 }) {
                for (final int k1 : new int[] { -20, 20 }) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                        @Override
                        public void setupRespawner(final LOTREntityNPCRespawner spawner) {
                            spawner.setSpawnClasses(LOTRVillageGenGondor.this.villageFief.getSoldierClasses()[0], LOTRVillageGenGondor.this.villageFief.getSoldierClasses()[1]);
                            spawner.setCheckRanges(20, -12, 12, 16);
                            spawner.setSpawnRanges(20, -6, 6, 40);
                            spawner.setBlockEnemySpawnRange(40);
                        }
                    }, i1, k1, 0);
                }
            }
            this.addStructure(this.getVillageFortress(), 0, 12, 2, true);
            this.addStructure(new LOTRWorldGenGondorFortGate(false), 0, -37, 0, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Right(false), -11, -37, 0, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Left(false), 11, -37, 0, true);
            this.addStructure(this.getVillageWatchtower(), -23, -33, 2, true);
            this.addStructure(this.getVillageWatchtower(), 23, -33, 2, true);
            this.addStructure(new LOTRWorldGenGondorFortGate(false), -37, 0, 3, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Left(false), -37, -11, 3, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Right(false), -37, 11, 3, true);
            this.addStructure(this.getVillageWatchtower(), -33, -23, 1, true);
            this.addStructure(this.getVillageWatchtower(), -33, 23, 1, true);
            this.addStructure(new LOTRWorldGenGondorFortGate(false), 0, 37, 2, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Left(false), -11, 37, 2, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Right(false), 11, 37, 2, true);
            this.addStructure(this.getVillageWatchtower(), -23, 33, 0, true);
            this.addStructure(this.getVillageWatchtower(), 23, 33, 0, true);
            this.addStructure(new LOTRWorldGenGondorFortGate(false), 37, 0, 1, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Right(false), 37, -11, 1, true);
            this.addStructure(new LOTRWorldGenGondorFortWall.Left(false), 37, 11, 1, true);
            this.addStructure(this.getVillageWatchtower(), 33, -23, 3, true);
            this.addStructure(this.getVillageWatchtower(), 33, 23, 3, true);
            this.addStructure(new LOTRWorldGenGondorFortWallCorner(false), -30, -30, 3);
            this.addStructure(new LOTRWorldGenGondorFortWallCorner(false), -30, 30, 0);
            this.addStructure(new LOTRWorldGenGondorFortWallCorner(false), 30, 30, 1);
            this.addStructure(new LOTRWorldGenGondorFortWallCorner(false), 30, -30, 2);
            this.addStructure(new LOTRWorldGenGondorStables(false), -24, 2, 0);
            this.addStructure(new LOTRWorldGenGondorStables(false), -24, -2, 2);
            this.addStructure(new LOTRWorldGenGondorSmithy(false), 24, 1, 0);
            this.addStructure(new LOTRWorldGenGondorSmithy(false), 24, -1, 2);
            this.addStructure(new LOTRWorldGenGondorStoneHouse(false), -3, -25, 1);
            this.addStructure(new LOTRWorldGenGondorStoneHouse(false), 3, -25, 3);
            this.addStructure(new LOTRWorldGenGondorVillageFarm.Crops(false), -18, -21, 1);
            this.addStructure(new LOTRWorldGenGondorVillageFarm.Crops(false), 18, -21, 3);
            this.addStructure(new LOTRWorldGenGondorWell(false), -12, 27, 1);
            this.addStructure(new LOTRWorldGenGondorWell(false), 12, 27, 3);
        }
        
        private LOTRWorldGenStructureBase2 getVillageFortress() {
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR) {
                return new LOTRWorldGenGondorFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH) {
                return new LOTRWorldGenLossarnachFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN) {
                return new LOTRWorldGenLebenninFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR) {
                return new LOTRWorldGenPelargirFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN) {
                return new LOTRWorldGenPinnathGelinFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE) {
                return new LOTRWorldGenBlackrootFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON) {
                return new LOTRWorldGenLamedonFortress(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH) {
                return new LOTRWorldGenDolAmrothStables(false);
            }
            return null;
        }
        
        private LOTRWorldGenStructureBase2 getVillageWatchtower() {
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR) {
                return new LOTRWorldGenGondorWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH) {
                return new LOTRWorldGenLossarnachWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN) {
                return new LOTRWorldGenLebenninWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR) {
                return new LOTRWorldGenPelargirWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN) {
                return new LOTRWorldGenPinnathGelinWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE) {
                return new LOTRWorldGenBlackrootWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON) {
                return new LOTRWorldGenLamedonWatchtower(false);
            }
            if (LOTRVillageGenGondor.this.villageFief == LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH) {
                return new LOTRWorldGenDolAmrothWatchtower(false);
            }
            return null;
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
                final int omn = 53 - random.nextInt(4);
                final int omx = 60 + random.nextInt(4);
                if (dSq > omn * omn && dSq < omx * omx) {
                    return LOTRRoadType.PATH;
                }
                if (dSq < 2809) {
                    final int d1 = Math.abs(i2 - k2);
                    if (d1 <= 2 + random.nextInt(4)) {
                        return LOTRRoadType.PATH;
                    }
                }
            }
            if (this.villageType == VillageType.TOWN && i2 <= 80 && k2 <= 80) {
                return LOTRRoadType.COBBLESTONE;
            }
            if (this.villageType == VillageType.FORT) {
                if (i2 <= 1 && (k >= 13 || k <= -12) && k2 <= 36) {
                    return LOTRVillageGenGondor.this.villageBiome.getRoadBlock();
                }
                if (k2 <= 1 && i2 >= 12 && i2 <= 36) {
                    return LOTRVillageGenGondor.this.villageBiome.getRoadBlock();
                }
                if (k >= 26 && k <= 28 && i2 <= 12) {
                    return LOTRVillageGenGondor.this.villageBiome.getRoadBlock();
                }
            }
            return null;
        }
        
        @Override
        public boolean isVillageSurface(final World world, final int i, final int j, final int k) {
            final Block block = world.getBlock(i, j, k);
            return this.villageType == VillageType.TOWN && block == Blocks.cobblestone;
        }
    }
}
