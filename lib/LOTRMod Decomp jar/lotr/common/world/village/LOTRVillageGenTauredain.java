// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenTauredainWatchtower;
import lotr.common.world.structure2.LOTRWorldGenTauredainSmithy;
import lotr.common.world.structure2.LOTRWorldGenTauredainHouseSimple;
import lotr.common.world.structure2.LOTRWorldGenTauredainHouseLarge;
import lotr.common.world.structure2.LOTRWorldGenTauredainHouseStilts;
import lotr.common.world.structure2.LOTRWorldGenTauredainVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenTauredainVillageTree;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.world.structure2.LOTRWorldGenTauredainChieftainPyramid;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;

public class LOTRVillageGenTauredain extends LOTRVillageGen
{
    public LOTRVillageGenTauredain(final LOTRBiome biome, final float f) {
        super(biome);
        super.gridScale = 10;
        super.gridRandomDisplace = 1;
        super.spawnChance = f;
        super.villageChunkSize = 3;
    }
    
    @Override
    protected AbstractInstance createVillageInstance(final World world, final int i, final int k, final Random random) {
        return new Instance(world, i, k, random);
    }
    
    public class Instance extends AbstractInstance
    {
        public Instance(final World world, final int i, final int k, final Random random) {
            super(world, i, k, random);
        }
        
        @Override
        protected void setupVillageProperties(final Random random) {
        }
        
        @Override
        public boolean isFlat() {
            return false;
        }
        
        @Override
        protected void addVillageStructures(final Random random) {
            final int smithyPos = random.nextInt(4);
            this.addStructure(new LOTRWorldGenTauredainChieftainPyramid(false), 0, -11, 0, true);
            this.addStructure(new LOTRWorldGenTauredainVillageTree(false), 0, -16, 2);
            this.addStructure(new LOTRWorldGenTauredainVillageFarm(false), -16, -19, 2);
            this.addStructure(new LOTRWorldGenTauredainVillageFarm(false), 16, -19, 2);
            this.addStructure(new LOTRWorldGenTauredainHouseStilts(false), 0, 15, 0);
            this.addStructure(new LOTRWorldGenTauredainVillageFarm(false), -16, 19, 0);
            this.addStructure(new LOTRWorldGenTauredainVillageFarm(false), 16, 19, 0);
            this.addStructure(new LOTRWorldGenTauredainHouseLarge(false), -20, 0, 1);
            this.addStructure(new LOTRWorldGenTauredainHouseLarge(false), 20, 1, 3);
            this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), -15, -36, 0);
            this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 15, -36, 0);
            if (smithyPos == 0) {
                this.addStructure(new LOTRWorldGenTauredainSmithy(false), -22, -13, 1);
            }
            else {
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, -22, 3);
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, -12, 3);
            }
            if (smithyPos == 1) {
                this.addStructure(new LOTRWorldGenTauredainSmithy(false), -22, 14, 1);
            }
            else {
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, 13, 3);
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, 23, 3);
            }
            if (smithyPos == 2) {
                this.addStructure(new LOTRWorldGenTauredainSmithy(false), 22, -13, 3);
            }
            else {
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, -22, 1);
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, -12, 1);
            }
            if (smithyPos == 3) {
                this.addStructure(new LOTRWorldGenTauredainSmithy(false), 22, 14, 3);
            }
            else {
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, 13, 1);
                this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, 23, 1);
            }
            this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), -15, 36, 2);
            this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 0, 37, 2);
            this.addStructure(new LOTRWorldGenTauredainHouseSimple(false), 15, 36, 2);
            this.addStructure(new LOTRWorldGenTauredainWatchtower(false), -26, -36, 0);
            this.addStructure(new LOTRWorldGenTauredainWatchtower(false), 26, -36, 0);
            this.addStructure(new LOTRWorldGenTauredainWatchtower(false), -26, 37, 2);
            this.addStructure(new LOTRWorldGenTauredainWatchtower(false), 26, 37, 2);
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
