// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.StructureStart;
import lotr.common.world.village.LOTRVillagePositionCache;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenErebor;
import lotr.common.world.biome.LOTRBiomeGenGreyMountains;
import lotr.common.world.biome.LOTRBiomeGenBlueMountainsFoothills;
import lotr.common.world.biome.LOTRBiomeGenBlueMountains;
import lotr.common.world.biome.LOTRBiomeGenIronHills;
import lotr.common.LOTRDimension;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.gen.structure.MapGenStructure;

public class LOTRMapGenDwarvenMine extends MapGenStructure
{
    private static List spawnBiomes;
    private int spawnChance;
    private static List spawnBiomesRuined;
    private int spawnChanceRuined;
    
    public LOTRMapGenDwarvenMine() {
        this.spawnChance = 150;
        this.spawnChanceRuined = 500;
    }
    
    private static void setupSpawnBiomes() {
        if (LOTRMapGenDwarvenMine.spawnBiomes == null) {
            LOTRMapGenDwarvenMine.spawnBiomes = new ArrayList();
            LOTRMapGenDwarvenMine.spawnBiomesRuined = new ArrayList();
            for (final LOTRBiome biome : LOTRDimension.MIDDLE_EARTH.biomeList) {
                boolean mine = false;
                boolean ruined = false;
                if (biome instanceof LOTRBiomeGenIronHills) {
                    mine = true;
                }
                if (biome instanceof LOTRBiomeGenBlueMountains && !(biome instanceof LOTRBiomeGenBlueMountainsFoothills)) {
                    mine = true;
                }
                if (biome instanceof LOTRBiomeGenGreyMountains) {
                    ruined = true;
                }
                if (biome instanceof LOTRBiomeGenErebor) {
                    mine = true;
                }
                if (mine) {
                    LOTRMapGenDwarvenMine.spawnBiomes.add(biome);
                }
                if (ruined) {
                    LOTRMapGenDwarvenMine.spawnBiomesRuined.add(biome);
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
        final int i2 = i * 16 + 8;
        final int k2 = k * 16 + 8;
        setupSpawnBiomes();
        if (((MapGenBase)this).worldObj.getWorldChunkManager().areBiomesViable(i2, k2, 0, LOTRMapGenDwarvenMine.spawnBiomes)) {
            if (((MapGenBase)this).rand.nextInt(this.spawnChance) == 0) {
                cache.markResult(i, k, true);
                return true;
            }
        }
        else if (((MapGenBase)this).worldObj.getWorldChunkManager().areBiomesViable(i2, k2, 0, LOTRMapGenDwarvenMine.spawnBiomesRuined) && ((MapGenBase)this).rand.nextInt(this.spawnChanceRuined) == 0) {
            cache.markResult(i, k, true);
            return true;
        }
        cache.markResult(i, k, false);
        return false;
    }
    
    protected StructureStart getStructureStart(final int i, final int k) {
        final int i2 = i * 16 + 8;
        final int k2 = k * 16 + 8;
        final BiomeGenBase biome = ((MapGenBase)this).worldObj.getWorldChunkManager().getBiomeGenAt(i2, k2);
        final boolean ruined = LOTRMapGenDwarvenMine.spawnBiomesRuined.contains(biome);
        return new LOTRStructureDwarvenMineStart(((MapGenBase)this).worldObj, ((MapGenBase)this).rand, i, k, ruined);
    }
    
    public String func_143025_a() {
        return "LOTR.DwarvenMine";
    }
    
    public static void register() {
        MapGenStructureIO.registerStructure((Class)LOTRStructureDwarvenMineStart.class, "LOTR.DwarvenMine");
        MapGenStructureIO.func_143031_a((Class)LOTRComponentDwarvenMineEntrance.class, "LOTR.DwarvenMine.Entrance");
        MapGenStructureIO.func_143031_a((Class)LOTRComponentDwarvenMineCorridor.class, "LOTR.DwarvenMine.Corridor");
        MapGenStructureIO.func_143031_a((Class)LOTRComponentDwarvenMineCrossing.class, "LOTR.DwarvenMine.Crossing");
        MapGenStructureIO.func_143031_a((Class)LOTRComponentDwarvenMineStairs.class, "LOTR.DwarvenMine.Stairs");
    }
}
