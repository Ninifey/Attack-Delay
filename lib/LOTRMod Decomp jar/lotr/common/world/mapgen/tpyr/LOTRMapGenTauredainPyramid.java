// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.tpyr;

import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;
import java.util.Random;
import lotr.common.world.village.LOTRVillagePositionCache;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenTauredainClearing;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.LOTRDimension;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.gen.structure.MapGenStructure;

public class LOTRMapGenTauredainPyramid extends MapGenStructure
{
    private static List spawnBiomes;
    private int spawnChance;
    private static int minDist;
    private static int separation;
    
    public LOTRMapGenTauredainPyramid() {
        this.spawnChance = 10;
    }
    
    private static void setupSpawnBiomes() {
        if (LOTRMapGenTauredainPyramid.spawnBiomes == null) {
            LOTRMapGenTauredainPyramid.spawnBiomes = new ArrayList();
            for (final LOTRBiome biome : LOTRDimension.MIDDLE_EARTH.biomeList) {
                boolean flag = false;
                if (biome instanceof LOTRBiomeGenFarHaradJungle && !(biome instanceof LOTRBiomeGenTauredainClearing)) {
                    flag = true;
                }
                if (flag) {
                    LOTRMapGenTauredainPyramid.spawnBiomes.add(biome);
                }
            }
        }
    }
    
    protected boolean canSpawnStructureAtCoords(final int i, final int k) {
        final LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)((MapGenBase)this).worldObj.getWorldChunkManager();
        final LOTRVillagePositionCache cache = worldChunkMgr.getStructureCache(this);
        if (cache.isVillageAt(i, k)) {
            return true;
        }
        if (cache.isVillageNotAt(i, k)) {
            return false;
        }
        setupSpawnBiomes();
        int i2 = MathHelper.floor_double(i / (double)LOTRMapGenTauredainPyramid.separation);
        int k2 = MathHelper.floor_double(k / (double)LOTRMapGenTauredainPyramid.separation);
        final Random dRand = ((MapGenBase)this).worldObj.setRandomSeed(i2, k2, 190169976);
        i2 *= LOTRMapGenTauredainPyramid.separation;
        k2 *= LOTRMapGenTauredainPyramid.separation;
        i2 += dRand.nextInt(LOTRMapGenTauredainPyramid.separation - LOTRMapGenTauredainPyramid.minDist + 1);
        k2 += dRand.nextInt(LOTRMapGenTauredainPyramid.separation - LOTRMapGenTauredainPyramid.minDist + 1);
        if (i == i2 && k == k2) {
            final int i3 = i * 16 + 8;
            final int k3 = k * 16 + 8;
            if (((MapGenBase)this).worldObj.getWorldChunkManager().areBiomesViable(i3, k3, 0, LOTRMapGenTauredainPyramid.spawnBiomes) && ((MapGenBase)this).rand.nextInt(this.spawnChance) == 0) {
                cache.markResult(i, k, true);
                return true;
            }
        }
        cache.markResult(i, k, false);
        return false;
    }
    
    protected StructureStart getStructureStart(final int i, final int j) {
        return new LOTRStructureTPyrStart(((MapGenBase)this).worldObj, ((MapGenBase)this).rand, i, j);
    }
    
    public String func_143025_a() {
        return "LOTR.TPyr";
    }
    
    public static void register() {
        MapGenStructureIO.registerStructure((Class)LOTRStructureTPyrStart.class, "LOTR.TPyr");
        MapGenStructureIO.func_143031_a((Class)LOTRComponentTauredainPyramid.class, "LOTR.TPyr.Pyramid");
    }
    
    static {
        LOTRMapGenTauredainPyramid.minDist = 12;
        LOTRMapGenTauredainPyramid.separation = 24;
    }
}
