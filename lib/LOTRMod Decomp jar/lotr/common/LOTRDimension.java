// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.world.LOTRWorldProviderMiddleEarth;
import net.minecraft.world.WorldProvider;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraft.util.StatCollector;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.EnumSet;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import java.util.Map;
import lotr.common.world.biome.LOTRBiome;

public enum LOTRDimension
{
    MIDDLE_EARTH("MiddleEarth", 100, (Class)LOTRWorldProviderMiddleEarth.class, true, 100, EnumSet.of(DimensionRegion.WEST, DimensionRegion.EAST, DimensionRegion.SOUTH)), 
    UTUMNO("Utumno", 101, (Class)LOTRWorldProviderUtumno.class, false, 500, EnumSet.of(DimensionRegion.REG_UTUMNO));
    
    public String dimensionName;
    private int defaultID;
    public int dimensionID;
    private Class providerClass;
    private boolean loadSpawn;
    public LOTRBiome[] biomeList;
    public Map<Integer, Integer> colorsToBiomeIDs;
    public List<LOTRBiome> majorBiomes;
    public List<LOTRAchievement.Category> achievementCategories;
    public List<LOTRAchievement> allAchievements;
    public List<LOTRFaction> factionList;
    public List<DimensionRegion> dimensionRegions;
    public int spawnCap;
    
    private LOTRDimension(final String s, final int i, final Class c, final boolean flag, final int spawns, final EnumSet<DimensionRegion> regions) {
        this.biomeList = new LOTRBiome[256];
        this.colorsToBiomeIDs = new HashMap<Integer, Integer>();
        this.majorBiomes = new ArrayList<LOTRBiome>();
        this.achievementCategories = new ArrayList<LOTRAchievement.Category>();
        this.allAchievements = new ArrayList<LOTRAchievement>();
        this.factionList = new ArrayList<LOTRFaction>();
        this.dimensionRegions = new ArrayList<DimensionRegion>();
        this.dimensionName = s;
        this.defaultID = i;
        this.providerClass = c;
        this.loadSpawn = flag;
        this.spawnCap = spawns;
        this.dimensionRegions.addAll(regions);
        for (final DimensionRegion r : this.dimensionRegions) {
            r.setDimension(this);
        }
    }
    
    public String getDimensionName() {
        return StatCollector.translateToLocal("lotr.dimension." + this.dimensionName);
    }
    
    public static void configureDimensions(final Configuration config, final String category) {
        for (final LOTRDimension dim : values()) {
            dim.dimensionID = config.get(category, "Dimension ID: " + dim.dimensionName, dim.defaultID).getInt();
        }
    }
    
    public static void registerDimensions() {
        for (final LOTRDimension dim : values()) {
            DimensionManager.registerProviderType(dim.dimensionID, dim.providerClass, dim.loadSpawn);
            DimensionManager.registerDimension(dim.dimensionID, dim.dimensionID);
        }
    }
    
    public static LOTRDimension getCurrentDimension(final World world) {
        if (world != null) {
            final WorldProvider provider = world.provider;
            if (provider instanceof LOTRWorldProvider) {
                return ((LOTRWorldProvider)provider).getLOTRDimension();
            }
        }
        return LOTRDimension.MIDDLE_EARTH;
    }
    
    public static LOTRDimension forName(final String s) {
        for (final LOTRDimension dim : values()) {
            if (dim.dimensionName.equals(s)) {
                return dim;
            }
        }
        return null;
    }
    
    public enum DimensionRegion
    {
        WEST("west"), 
        EAST("east"), 
        SOUTH("south"), 
        REG_UTUMNO("utumno");
        
        private String regionName;
        private LOTRDimension dimension;
        public List<LOTRFaction> factionList;
        
        private DimensionRegion(final String s) {
            this.factionList = new ArrayList<LOTRFaction>();
            this.regionName = s;
        }
        
        public void setDimension(final LOTRDimension dim) {
            this.dimension = dim;
        }
        
        public LOTRDimension getDimension() {
            return this.dimension;
        }
        
        public String codeName() {
            return this.regionName;
        }
        
        public String getRegionName() {
            return StatCollector.translateToLocal("lotr.dimension." + this.dimension.dimensionName + "." + this.codeName());
        }
        
        public static DimensionRegion forName(final String regionName) {
            for (final DimensionRegion r : values()) {
                if (r.codeName().equals(regionName)) {
                    return r;
                }
            }
            return null;
        }
        
        public static DimensionRegion forID(final int ID) {
            for (final DimensionRegion r : values()) {
                if (r.ordinal() == ID) {
                    return r;
                }
            }
            return null;
        }
    }
}
