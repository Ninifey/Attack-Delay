// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fac;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.LOTRNPCSelectForInfluence;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;
import java.util.Arrays;
import lotr.common.LOTRMod;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievementRank;
import java.util.Iterator;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.EnumSet;
import lotr.common.LOTRAchievement;
import lotr.common.item.LOTRItemBanner;
import java.util.List;
import java.util.Set;
import java.awt.Color;
import lotr.common.LOTRDimension;
import java.util.Random;

public enum LOTRFaction
{
    HOBBIT(5885518, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(830, 745, 100), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN)), 
    RANGER_NORTH(3823170, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1070, 760, 150), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN)), 
    BLUE_MOUNTAINS(6132172, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(650, 600, 125), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_DWARF)), 
    HIGH_ELF(13035007, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(570, 770, 200), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_ELF)), 
    GUNDABAD(9858132, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1160, 670, 150), EnumSet.of(FactionType.TYPE_ORC)), 
    ANGMAR(7836023, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1080, 600, 125), EnumSet.of(FactionType.TYPE_ORC, FactionType.TYPE_TROLL)), 
    WOOD_ELF(3774030, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1400, 640, 75), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_ELF)), 
    DOL_GULDUR(3488580, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1380, 870, 100), EnumSet.of(FactionType.TYPE_ORC)), 
    DALE(13535071, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1530, 670, 100), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN)), 
    DWARF(4940162, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1650, 650, 125), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_DWARF)), 
    GALADHRIM(15716696, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1230, 900, 75), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_ELF)), 
    DUNLAND(11048079, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1090, 1030, 125), EnumSet.of(FactionType.TYPE_MAN)), 
    URUK_HAI(3356723, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1110, 1070, 50), EnumSet.of(FactionType.TYPE_ORC)), 
    FANGORN(4831058, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1200, 1000, 75), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_TREE)), 
    ROHAN(3508007, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1230, 1090, 150), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN)), 
    GONDOR(16382457, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1170, 1300, 300), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN)), 
    MORDOR(3481375, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1620, 1290, 225), EnumSet.of(FactionType.TYPE_ORC)), 
    DORWINION(7155816, LOTRDimension.DimensionRegion.EAST, new LOTRMapRegion(1750, 900, 100), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN, FactionType.TYPE_ELF)), 
    RHUN(12882471, LOTRDimension.DimensionRegion.EAST, new LOTRMapRegion(1890, 980, 200), EnumSet.of(FactionType.TYPE_MAN)), 
    NEAR_HARAD(11868955, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1400, 1730, 375), EnumSet.of(FactionType.TYPE_MAN)), 
    MOREDAIN(14266458, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1400, 2360, 450), EnumSet.of(FactionType.TYPE_MAN)), 
    TAUREDAIN(3040066, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1250, 2870, 400), EnumSet.of(FactionType.TYPE_FREE, FactionType.TYPE_MAN)), 
    HALF_TROLL(10388339, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1900, 2500, 200), EnumSet.of(FactionType.TYPE_MAN, FactionType.TYPE_TROLL)), 
    DARK_HUORN(0, (LOTRDimension)null, (LOTRDimension.DimensionRegion)null, true, true, -1, (LOTRMapRegion)null, (EnumSet<FactionType>)null), 
    UTUMNO(3343616, LOTRDimension.UTUMNO, -66666, EnumSet.of(FactionType.TYPE_ORC)), 
    HOSTILE(true, -1), 
    UNALIGNED(false, 0);
    
    private static Random factionRand;
    public LOTRDimension factionDimension;
    public LOTRDimension.DimensionRegion factionRegion;
    private String factionName;
    private Color factionColor;
    private Set<FactionType> factionTypes;
    public List<LOTRItemBanner.BannerType> factionBanners;
    public boolean allowPlayer;
    public boolean allowEntityRegistry;
    public boolean hasFixedAlignment;
    public int fixedAlignment;
    private List<LOTRFactionRank> ranksSortedDescending;
    private LOTRFactionRank pledgeRank;
    private LOTRAchievement.Category achieveCategory;
    public LOTRMapRegion factionMapInfo;
    private List<LOTRControlZone> controlZones;
    public boolean isolationist;
    public boolean approvesWarCrimes;
    public static final int CONTROL_ZONE_EXTRA_RANGE = 50;
    
    private LOTRFaction(final int color, final LOTRDimension.DimensionRegion region, final LOTRMapRegion mapInfo, final EnumSet<FactionType> types) {
        this(color, LOTRDimension.MIDDLE_EARTH, region, mapInfo, types);
    }
    
    private LOTRFaction(final int color, final LOTRDimension dim, final LOTRDimension.DimensionRegion region, final LOTRMapRegion mapInfo, final EnumSet<FactionType> types) {
        this(color, dim, region, true, true, Integer.MIN_VALUE, mapInfo, types);
    }
    
    private LOTRFaction(final int color, final LOTRDimension dim, final int alignment, final EnumSet<FactionType> types) {
        this(color, dim, dim.dimensionRegions.get(0), true, true, alignment, null, types);
    }
    
    private LOTRFaction(final boolean registry, final int alignment) {
        this(0, null, null, false, registry, alignment, null, null);
    }
    
    private LOTRFaction(final int color, final LOTRDimension dim, final LOTRDimension.DimensionRegion region, final boolean player, final boolean registry, final int alignment, final LOTRMapRegion mapInfo, final EnumSet<FactionType> types) {
        this.factionTypes = new HashSet<FactionType>();
        this.factionBanners = new ArrayList<LOTRItemBanner.BannerType>();
        this.ranksSortedDescending = new ArrayList<LOTRFactionRank>();
        this.controlZones = new ArrayList<LOTRControlZone>();
        this.isolationist = false;
        this.approvesWarCrimes = true;
        this.allowPlayer = player;
        this.allowEntityRegistry = registry;
        this.factionColor = new Color(color);
        this.factionDimension = dim;
        if (this.factionDimension != null) {
            this.factionDimension.factionList.add(this);
        }
        this.factionRegion = region;
        if (this.factionRegion != null) {
            this.factionRegion.factionList.add(this);
            if (this.factionRegion.getDimension() != this.factionDimension) {
                throw new IllegalArgumentException("Faction dimension region must agree with faction dimension!");
            }
        }
        if (alignment != Integer.MIN_VALUE) {
            this.setFixedAlignment(alignment);
        }
        if (mapInfo != null) {
            this.factionMapInfo = mapInfo;
        }
        if (types != null) {
            this.factionTypes.addAll(types);
        }
    }
    
    private void setFixedAlignment(final int alignment) {
        this.hasFixedAlignment = true;
        this.fixedAlignment = alignment;
    }
    
    private void setAchieveCategory(final LOTRAchievement.Category cat) {
        this.achieveCategory = cat;
    }
    
    public LOTRAchievement.Category getAchieveCategory() {
        return this.achieveCategory;
    }
    
    private LOTRFactionRank addRank(final float alignment, final String name) {
        return this.addRank(alignment, name, false);
    }
    
    private LOTRFactionRank addRank(final float alignment, final String name, final boolean gendered) {
        final LOTRFactionRank rank = new LOTRFactionRank(this, alignment, name, gendered);
        this.ranksSortedDescending.add(rank);
        Collections.sort(this.ranksSortedDescending);
        return rank;
    }
    
    public void setPledgeRank(final LOTRFactionRank rank) {
        if (rank.fac != this) {
            throw new IllegalArgumentException("Incompatible faction!");
        }
        if (this.pledgeRank == null) {
            this.pledgeRank = rank;
            return;
        }
        throw new IllegalArgumentException("Faction already has a pledge rank!");
    }
    
    public LOTRFactionRank getPledgeRank() {
        if (this.pledgeRank != null) {
            return this.pledgeRank;
        }
        return null;
    }
    
    public float getPledgeAlignment() {
        if (this.pledgeRank != null) {
            return this.pledgeRank.alignment;
        }
        return 0.0f;
    }
    
    public void checkAlignmentAchievements(final EntityPlayer entityplayer, final float alignment) {
        final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
        for (final LOTRFactionRank rank : this.ranksSortedDescending) {
            final LOTRAchievementRank rankAch = rank.getRankAchievement();
            if (rankAch != null && rankAch.isPlayerRequiredRank(entityplayer)) {
                playerData.addAchievement(rankAch);
            }
        }
    }
    
    private void addControlZone(final LOTRControlZone zone) {
        this.controlZones.add(zone);
    }
    
    public List<LOTRControlZone> getControlZones() {
        return this.controlZones;
    }
    
    public static void initAllProperties() {
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.RANGER_NORTH, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.BLUE_MOUNTAINS, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.HIGH_ELF, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.DALE, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.DWARF, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.HIGH_ELF, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.DWARF, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.ANGMAR, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.DORWINION, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.DWARF, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DWARF, LOTRFaction.DUNLAND, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GALADHRIM, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DUNLAND, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.URUK_HAI, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.FANGORN, LOTRFaction.TAUREDAIN, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ROHAN, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MORDOR, LOTRFaction.RHUN, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MORDOR, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MORDOR, LOTRFaction.MOREDAIN, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MORDOR, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.ALLY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.NEAR_HARAD, LOTRFaction.MOREDAIN, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.NEAR_HARAD, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.FRIEND);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HOBBIT, LOTRFaction.DARK_HUORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.DUNLAND, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.RHUN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.MOREDAIN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.RANGER_NORTH, LOTRFaction.DARK_HUORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.BLUE_MOUNTAINS, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.RHUN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.HIGH_ELF, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.WOOD_ELF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.DALE, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.DWARF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GUNDABAD, LOTRFaction.DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.WOOD_ELF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.DALE, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.DWARF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ANGMAR, LOTRFaction.DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.RHUN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.WOOD_ELF, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.DALE, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.DWARF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.GALADHRIM, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DOL_GULDUR, LOTRFaction.DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.RHUN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DALE, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DWARF, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DWARF, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DWARF, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GALADHRIM, LOTRFaction.URUK_HAI, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GALADHRIM, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GALADHRIM, LOTRFaction.RHUN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GALADHRIM, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GALADHRIM, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DUNLAND, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DUNLAND, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.URUK_HAI, LOTRFaction.FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.URUK_HAI, LOTRFaction.ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.URUK_HAI, LOTRFaction.GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.URUK_HAI, LOTRFaction.DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.FANGORN, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.FANGORN, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ROHAN, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ROHAN, LOTRFaction.RHUN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ROHAN, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ROHAN, LOTRFaction.MOREDAIN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.ROHAN, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GONDOR, LOTRFaction.MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GONDOR, LOTRFaction.RHUN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GONDOR, LOTRFaction.NEAR_HARAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GONDOR, LOTRFaction.MOREDAIN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.GONDOR, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MORDOR, LOTRFaction.DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MORDOR, LOTRFaction.TAUREDAIN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.DORWINION, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.NEAR_HARAD, LOTRFaction.TAUREDAIN, LOTRFactionRelations.Relation.ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.MOREDAIN, LOTRFaction.TAUREDAIN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        LOTRFactionRelations.setDefaultRelations(LOTRFaction.TAUREDAIN, LOTRFaction.HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
        for (final LOTRFaction f : values()) {
            if (f.allowPlayer && f != LOTRFaction.UTUMNO) {
                LOTRFactionRelations.setDefaultRelations(f, LOTRFaction.UTUMNO, LOTRFactionRelations.Relation.MORTAL_ENEMY);
            }
        }
        LOTRFaction.HOBBIT.approvesWarCrimes = false;
        LOTRFaction.HOBBIT.isolationist = true;
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.BYWATER, 40));
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.BUCKLEBURY, 15));
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.HAYSEND, 10));
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.MICHEL_DELVING, 35));
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.GREENHOLM, 10));
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.LONGBOTTOM, 30));
        LOTRFaction.HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.BREE, 15));
        LOTRFaction.RANGER_NORTH.approvesWarCrimes = false;
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.BYWATER, 110));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.SARN_FORD, 60));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.LAST_BRIDGE, 110));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.BREE, 100));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.ANNUMINAS, 50));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.FORNOST, 50));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GRAM, 100));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.CARN_DUM, 60));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.GREENWAY_CROSSROADS, 60));
        LOTRFaction.RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.THARBAD, 50));
        LOTRFaction.BLUE_MOUNTAINS.approvesWarCrimes = false;
        LOTRFaction.BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(LOTRWaypoint.BELEGOST, 40));
        LOTRFaction.BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(LOTRWaypoint.NOGROD, 40));
        LOTRFaction.BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(LOTRWaypoint.THORIN_HALLS, 50));
        LOTRFaction.BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(695, 820, 80));
        LOTRFaction.HIGH_ELF.approvesWarCrimes = false;
        LOTRFaction.HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.MITHLOND_SOUTH, 60));
        LOTRFaction.HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.FORLOND, 80));
        LOTRFaction.HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.HARLOND, 80));
        LOTRFaction.HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.FORD_BRUINEN, 50));
        LOTRFaction.GUNDABAD.approvesWarCrimes = true;
        LOTRFaction.GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GUNDABAD, 200));
        LOTRFaction.GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GRAM, 200));
        LOTRFaction.GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.GOBLIN_TOWN, 150));
        LOTRFaction.GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_CARADHRAS, 100));
        LOTRFaction.ANGMAR.approvesWarCrimes = true;
        LOTRFaction.ANGMAR.addControlZone(new LOTRControlZone(LOTRWaypoint.CARN_DUM, 75));
        LOTRFaction.ANGMAR.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GRAM, 125));
        LOTRFaction.ANGMAR.addControlZone(new LOTRControlZone(LOTRWaypoint.THE_TROLLSHAWS, 50));
        LOTRFaction.WOOD_ELF.approvesWarCrimes = false;
        LOTRFaction.WOOD_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.ENCHANTED_RIVER, 75));
        LOTRFaction.WOOD_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.FOREST_GATE, 20));
        LOTRFaction.WOOD_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.DOL_GULDUR, 30));
        LOTRFaction.DOL_GULDUR.approvesWarCrimes = true;
        LOTRFaction.DOL_GULDUR.addControlZone(new LOTRControlZone(LOTRWaypoint.DOL_GULDUR, 125));
        LOTRFaction.DOL_GULDUR.addControlZone(new LOTRControlZone(LOTRWaypoint.ENCHANTED_RIVER, 75));
        LOTRFaction.DALE.approvesWarCrimes = false;
        LOTRFaction.DALE.addControlZone(new LOTRControlZone(LOTRWaypoint.DALE_CROSSROADS, 175));
        LOTRFaction.DWARF.approvesWarCrimes = false;
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.EREBOR, 75));
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.WEST_PEAK, 100));
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.EAST_PEAK, 75));
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.REDWATER_FORD, 75));
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_CARADHRAS, 100));
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GUNDABAD, 100));
        LOTRFaction.DWARF.addControlZone(new LOTRControlZone(LOTRWaypoint.DAINS_HALLS, 50));
        LOTRFaction.GALADHRIM.approvesWarCrimes = false;
        LOTRFaction.GALADHRIM.addControlZone(new LOTRControlZone(LOTRWaypoint.CARAS_GALADHON, 100));
        LOTRFaction.DUNLAND.approvesWarCrimes = true;
        LOTRFaction.DUNLAND.addControlZone(new LOTRControlZone(LOTRWaypoint.SOUTH_DUNLAND, 125));
        LOTRFaction.URUK_HAI.approvesWarCrimes = true;
        LOTRFaction.URUK_HAI.addControlZone(new LOTRControlZone(LOTRWaypoint.ISENGARD, 100));
        LOTRFaction.URUK_HAI.addControlZone(new LOTRControlZone(LOTRWaypoint.EDORAS, 50));
        LOTRFaction.FANGORN.approvesWarCrimes = false;
        LOTRFaction.FANGORN.isolationist = true;
        LOTRFaction.FANGORN.addControlZone(new LOTRControlZone(1180, 1005, 70));
        LOTRFaction.ROHAN.approvesWarCrimes = false;
        LOTRFaction.ROHAN.addControlZone(new LOTRControlZone(LOTRWaypoint.ENTWADE, 150));
        LOTRFaction.ROHAN.addControlZone(new LOTRControlZone(LOTRWaypoint.ISENGARD, 100));
        LOTRFaction.GONDOR.approvesWarCrimes = false;
        LOTRFaction.GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.MINAS_TIRITH, 200));
        LOTRFaction.GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.EDHELLOND, 125));
        LOTRFaction.GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.GREEN_HILLS, 100));
        LOTRFaction.GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_POROS, 150));
        LOTRFaction.GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_HARAD, 75));
        LOTRFaction.GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.UMBAR_CITY, 150));
        LOTRFaction.MORDOR.approvesWarCrimes = true;
        LOTRFaction.MORDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.BARAD_DUR, 500));
        LOTRFaction.DORWINION.approvesWarCrimes = false;
        LOTRFaction.DORWINION.addControlZone(new LOTRControlZone(LOTRWaypoint.DORWINION_COURT, 175));
        LOTRFaction.DORWINION.addControlZone(new LOTRControlZone(LOTRWaypoint.DALE_PORT, 30));
        LOTRFaction.RHUN.approvesWarCrimes = false;
        LOTRFaction.RHUN.addControlZone(new LOTRControlZone(LOTRWaypoint.RHUN_CAPITAL, 175));
        LOTRFaction.RHUN.addControlZone(new LOTRControlZone(LOTRWaypoint.MINAS_TIRITH, 100));
        LOTRFaction.RHUN.addControlZone(new LOTRControlZone(LOTRWaypoint.DALE_CITY, 50));
        LOTRFaction.NEAR_HARAD.approvesWarCrimes = false;
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.UMBAR_CITY, 200));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.FERTILE_VALLEY, 150));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.HARNEN_SEA_TOWN, 60));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.HARNEN_RIVER_TOWN, 60));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.DESERT_TOWN, 50));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.SOUTH_DESERT_TOWN, 50));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.GULF_CITY, 150));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_HARAD, 75));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_POROS, 50));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MINAS_TIRITH, 50));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(1210, 1340, 75));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.PELARGIR, 75));
        LOTRFaction.NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.LINHIR, 75));
        LOTRFaction.MOREDAIN.approvesWarCrimes = true;
        LOTRFaction.MOREDAIN.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_SOUTH, 350));
        LOTRFaction.MOREDAIN.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_WEST, 170));
        LOTRFaction.MOREDAIN.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_EAST, 200));
        LOTRFaction.MOREDAIN.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_NORTH, 75));
        LOTRFaction.TAUREDAIN.approvesWarCrimes = true;
        LOTRFaction.TAUREDAIN.addControlZone(new LOTRControlZone(LOTRWaypoint.JUNGLE_CITY_CAPITAL, 400));
        LOTRFaction.TAUREDAIN.addControlZone(new LOTRControlZone(LOTRWaypoint.OLD_JUNGLE_RUIN, 75));
        LOTRFaction.HALF_TROLL.approvesWarCrimes = true;
        LOTRFaction.HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.TROLL_ISLAND, 100));
        LOTRFaction.HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.BLOOD_RIVER, 200));
        LOTRFaction.HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.SHADOW_POINT, 100));
        LOTRFaction.HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_POROS, 40));
        LOTRFaction.HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.HARADUIN_BRIDGE, 100));
        LOTRFaction.UTUMNO.approvesWarCrimes = true;
        LOTRFaction.HOBBIT.setAchieveCategory(LOTRAchievement.Category.SHIRE);
        LOTRFaction.HOBBIT.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.HOBBIT.addRank(100.0f, "friend").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.HOBBIT.addRank(250.0f, "hayward").makeAchievement().makeTitle();
        LOTRFaction.HOBBIT.addRank(500.0f, "bounder").makeAchievement().makeTitle();
        LOTRFaction.HOBBIT.addRank(1000.0f, "shirriff").makeAchievement().makeTitle();
        LOTRFaction.HOBBIT.addRank(2000.0f, "chief").makeAchievement().makeTitle();
        LOTRFaction.HOBBIT.addRank(3000.0f, "thain").makeAchievement().makeTitle();
        LOTRFaction.RANGER_NORTH.setAchieveCategory(LOTRAchievement.Category.ERIADOR);
        LOTRFaction.RANGER_NORTH.addRank(10.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.RANGER_NORTH.addRank(50.0f, "warden").makeAchievement().makeTitle();
        LOTRFaction.RANGER_NORTH.addRank(100.0f, "ranger").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.RANGER_NORTH.addRank(200.0f, "ohtar").makeAchievement().makeTitle();
        LOTRFaction.RANGER_NORTH.addRank(500.0f, "roquen").makeAchievement().makeTitle();
        LOTRFaction.RANGER_NORTH.addRank(1000.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.RANGER_NORTH.addRank(2000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.setAchieveCategory(LOTRAchievement.Category.BLUE_MOUNTAINS);
        LOTRFaction.BLUE_MOUNTAINS.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.addRank(100.0f, "warden").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.BLUE_MOUNTAINS.addRank(200.0f, "axebearer").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.addRank(500.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.addRank(1000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.addRank(1500.0f, "noble").makeAchievement().makeTitle();
        LOTRFaction.BLUE_MOUNTAINS.addRank(3000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.setAchieveCategory(LOTRAchievement.Category.LINDON);
        LOTRFaction.HIGH_ELF.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.addRank(100.0f, "warrior").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.HIGH_ELF.addRank(200.0f, "herald").makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.addRank(500.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.addRank(1000.0f, "noble").makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.addRank(2000.0f, "commander").makeAchievement().makeTitle();
        LOTRFaction.HIGH_ELF.addRank(3000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.GUNDABAD.setAchieveCategory(LOTRAchievement.Category.ERIADOR);
        LOTRFaction.GUNDABAD.addRank(10.0f, "thrall").makeAchievement().makeTitle();
        LOTRFaction.GUNDABAD.addRank(50.0f, "snaga").makeAchievement().makeTitle();
        LOTRFaction.GUNDABAD.addRank(100.0f, "raider").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.GUNDABAD.addRank(200.0f, "ravager").makeAchievement().makeTitle();
        LOTRFaction.GUNDABAD.addRank(500.0f, "scourge").makeAchievement().makeTitle();
        LOTRFaction.GUNDABAD.addRank(1000.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.GUNDABAD.addRank(2000.0f, "chieftain").makeAchievement().makeTitle();
        LOTRFaction.ANGMAR.setAchieveCategory(LOTRAchievement.Category.ANGMAR);
        LOTRFaction.ANGMAR.addRank(10.0f, "thrall").makeAchievement().makeTitle();
        LOTRFaction.ANGMAR.addRank(50.0f, "servant").makeAchievement().makeTitle();
        LOTRFaction.ANGMAR.addRank(100.0f, "kinsman").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.ANGMAR.addRank(200.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.ANGMAR.addRank(500.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.ANGMAR.addRank(1000.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.ANGMAR.addRank(2000.0f, "chieftain").makeAchievement().makeTitle();
        LOTRFaction.WOOD_ELF.setAchieveCategory(LOTRAchievement.Category.MIRKWOOD);
        LOTRFaction.WOOD_ELF.addRank(50.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.WOOD_ELF.addRank(100.0f, "friend").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.WOOD_ELF.addRank(200.0f, "guard").makeAchievement().makeTitle();
        LOTRFaction.WOOD_ELF.addRank(500.0f, "herald").makeAchievement().makeTitle();
        LOTRFaction.WOOD_ELF.addRank(1000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.WOOD_ELF.addRank(2000.0f, "noble").makeAchievement().makeTitle();
        LOTRFaction.WOOD_ELF.addRank(3000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.DOL_GULDUR.setAchieveCategory(LOTRAchievement.Category.MIRKWOOD);
        LOTRFaction.DOL_GULDUR.addRank(10.0f, "thrall").makeAchievement().makeTitle();
        LOTRFaction.DOL_GULDUR.addRank(50.0f, "servant").makeAchievement().makeTitle();
        LOTRFaction.DOL_GULDUR.addRank(100.0f, "brigand").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.DOL_GULDUR.addRank(200.0f, "torchbearer").makeAchievement().makeTitle();
        LOTRFaction.DOL_GULDUR.addRank(500.0f, "despoiler").makeAchievement().makeTitle();
        LOTRFaction.DOL_GULDUR.addRank(1000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.DOL_GULDUR.addRank(2000.0f, "lieutenant").makeAchievement().makeTitle();
        LOTRFaction.DALE.setAchieveCategory(LOTRAchievement.Category.DALE);
        LOTRFaction.DALE.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.DALE.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.DALE.addRank(100.0f, "soldier").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.DALE.addRank(200.0f, "herald").makeAchievement().makeTitle();
        LOTRFaction.DALE.addRank(500.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.DALE.addRank(1000.0f, "marshal").makeAchievement().makeTitle();
        LOTRFaction.DALE.addRank(2000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.DWARF.setAchieveCategory(LOTRAchievement.Category.IRON_HILLS);
        LOTRFaction.DWARF.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.DWARF.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.DWARF.addRank(100.0f, "oathfriend").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.DWARF.addRank(200.0f, "axebearer").makeAchievement().makeTitle();
        LOTRFaction.DWARF.addRank(500.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.DWARF.addRank(1000.0f, "commander").makeAchievement().makeTitle();
        LOTRFaction.DWARF.addRank(1500.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.DWARF.addRank(3000.0f, "uzbad", true).makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.setAchieveCategory(LOTRAchievement.Category.LOTHLORIEN);
        LOTRFaction.GALADHRIM.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.addRank(100.0f, "warden").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.GALADHRIM.addRank(200.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.addRank(500.0f, "herald").makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.addRank(1000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.addRank(2000.0f, "noble").makeAchievement().makeTitle();
        LOTRFaction.GALADHRIM.addRank(3000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.DUNLAND.setAchieveCategory(LOTRAchievement.Category.DUNLAND);
        LOTRFaction.DUNLAND.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.DUNLAND.addRank(50.0f, "kinsman").makeAchievement().makeTitle();
        LOTRFaction.DUNLAND.addRank(100.0f, "warrior").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.DUNLAND.addRank(200.0f, "bearer").makeAchievement().makeTitle();
        LOTRFaction.DUNLAND.addRank(500.0f, "avenger").makeAchievement().makeTitle();
        LOTRFaction.DUNLAND.addRank(1000.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.DUNLAND.addRank(2000.0f, "chieftain").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.setAchieveCategory(LOTRAchievement.Category.ROHAN);
        LOTRFaction.URUK_HAI.addRank(10.0f, "thrall").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.addRank(50.0f, "snaga").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.addRank(100.0f, "soldier").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.URUK_HAI.addRank(200.0f, "treefeller").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.addRank(500.0f, "berserker").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.addRank(1000.0f, "corporal").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.addRank(1500.0f, "hand").makeAchievement().makeTitle();
        LOTRFaction.URUK_HAI.addRank(3000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.FANGORN.setAchieveCategory(LOTRAchievement.Category.FANGORN);
        LOTRFaction.FANGORN.addRank(10.0f, "newcomer").makeAchievement().makeTitle();
        LOTRFaction.FANGORN.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.FANGORN.addRank(100.0f, "treeherd").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.FANGORN.addRank(250.0f, "master").makeAchievement().makeTitle();
        LOTRFaction.FANGORN.addRank(500.0f, "elder").makeAchievement().makeTitle();
        LOTRFaction.ROHAN.setAchieveCategory(LOTRAchievement.Category.ROHAN);
        LOTRFaction.ROHAN.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.ROHAN.addRank(50.0f, "footman").makeAchievement().makeTitle();
        LOTRFaction.ROHAN.addRank(100.0f, "atarms").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.ROHAN.addRank(250.0f, "rider").makeAchievement().makeTitle();
        LOTRFaction.ROHAN.addRank(500.0f, "esquire").makeAchievement().makeTitle();
        LOTRFaction.ROHAN.addRank(1000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.ROHAN.addRank(2000.0f, "marshal").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.setAchieveCategory(LOTRAchievement.Category.GONDOR);
        LOTRFaction.GONDOR.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.addRank(100.0f, "atarms").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.GONDOR.addRank(200.0f, "soldier").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.addRank(500.0f, "knight").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.addRank(1000.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.addRank(1500.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.GONDOR.addRank(3000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.MORDOR.setAchieveCategory(LOTRAchievement.Category.MORDOR);
        LOTRFaction.MORDOR.addRank(10.0f, "thrall").makeAchievement().makeTitle();
        LOTRFaction.MORDOR.addRank(50.0f, "snaga").makeAchievement().makeTitle();
        LOTRFaction.MORDOR.addRank(100.0f, "brigand").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.MORDOR.addRank(200.0f, "slavedriver").makeAchievement().makeTitle();
        LOTRFaction.MORDOR.addRank(500.0f, "despoiler").makeAchievement().makeTitle();
        LOTRFaction.MORDOR.addRank(1000.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.MORDOR.addRank(1500.0f, "lieutenant").makeAchievement().makeTitle();
        LOTRFaction.MORDOR.addRank(3000.0f, "commander").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.setAchieveCategory(LOTRAchievement.Category.DORWINION);
        LOTRFaction.DORWINION.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.addRank(50.0f, "vinehand").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.addRank(100.0f, "merchant").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.DORWINION.addRank(200.0f, "guard").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.addRank(500.0f, "captain").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.addRank(1000.0f, "master").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.addRank(1500.0f, "chief").makeAchievement().makeTitle();
        LOTRFaction.DORWINION.addRank(3000.0f, "lord", true).makeAchievement().makeTitle();
        LOTRFaction.RHUN.setAchieveCategory(LOTRAchievement.Category.RHUN);
        LOTRFaction.RHUN.addRank(10.0f, "bondsman").makeAchievement().makeTitle();
        LOTRFaction.RHUN.addRank(50.0f, "levyman").makeAchievement().makeTitle();
        LOTRFaction.RHUN.addRank(100.0f, "clansman").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.RHUN.addRank(200.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.RHUN.addRank(500.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.RHUN.addRank(1000.0f, "golden").makeAchievement().makeTitle();
        LOTRFaction.RHUN.addRank(1500.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.RHUN.addRank(3000.0f, "chieftain").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.setAchieveCategory(LOTRAchievement.Category.NEAR_HARAD);
        LOTRFaction.NEAR_HARAD.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.addRank(100.0f, "kinsman").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.NEAR_HARAD.addRank(200.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.addRank(500.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.addRank(1000.0f, "serpentguard").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.addRank(1500.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.NEAR_HARAD.addRank(3000.0f, "prince", true).makeAchievement().makeTitle();
        LOTRFaction.MOREDAIN.setAchieveCategory(LOTRAchievement.Category.FAR_HARAD_SAVANNAH);
        LOTRFaction.MOREDAIN.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.MOREDAIN.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.MOREDAIN.addRank(100.0f, "kinsman").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.MOREDAIN.addRank(250.0f, "hunter").makeAchievement().makeTitle();
        LOTRFaction.MOREDAIN.addRank(500.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.MOREDAIN.addRank(1000.0f, "chief").makeAchievement().makeTitle();
        LOTRFaction.MOREDAIN.addRank(3000.0f, "greatchief").makeAchievement().makeTitle();
        LOTRFaction.TAUREDAIN.setAchieveCategory(LOTRAchievement.Category.FAR_HARAD_JUNGLE);
        LOTRFaction.TAUREDAIN.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.TAUREDAIN.addRank(50.0f, "friend").makeAchievement().makeTitle();
        LOTRFaction.TAUREDAIN.addRank(100.0f, "forestman").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.TAUREDAIN.addRank(200.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.TAUREDAIN.addRank(500.0f, "champion").makeAchievement().makeTitle();
        LOTRFaction.TAUREDAIN.addRank(1000.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.TAUREDAIN.addRank(3000.0f, "splendour").makeAchievement().makeTitle();
        LOTRFaction.HALF_TROLL.setAchieveCategory(LOTRAchievement.Category.PERTOROGWAITH);
        LOTRFaction.HALF_TROLL.addRank(10.0f, "guest").makeAchievement().makeTitle();
        LOTRFaction.HALF_TROLL.addRank(50.0f, "scavenger").makeAchievement().makeTitle();
        LOTRFaction.HALF_TROLL.addRank(100.0f, "kin").makeAchievement().makeTitle().setPledgeRank();
        LOTRFaction.HALF_TROLL.addRank(200.0f, "warrior").makeAchievement().makeTitle();
        LOTRFaction.HALF_TROLL.addRank(500.0f, "raider").makeAchievement().makeTitle();
        LOTRFaction.HALF_TROLL.addRank(1000.0f, "warlord").makeAchievement().makeTitle();
        LOTRFaction.HALF_TROLL.addRank(2000.0f, "chieftain").makeAchievement().makeTitle();
    }
    
    public String codeName() {
        return this.name();
    }
    
    public String untranslatedFactionName() {
        return "lotr.faction." + this.codeName() + ".name";
    }
    
    public String factionName() {
        if (LOTRMod.isAprilFools()) {
            final String[] names = { "Britain Stronger in Europe", "Vote Leave" };
            int i = this.ordinal();
            i = (int)(i + ((long)i ^ 0xF385L) + 28703L * ((long)(i * i) ^ 0x30C087L));
            LOTRFaction.factionRand.setSeed(i);
            final List<String> list = Arrays.asList(names);
            Collections.shuffle(list, LOTRFaction.factionRand);
            return list.get(0);
        }
        return StatCollector.translateToLocal(this.untranslatedFactionName());
    }
    
    public String factionEntityName() {
        return StatCollector.translateToLocal("lotr.faction." + this.codeName() + ".entity");
    }
    
    public String factionSubtitle() {
        return StatCollector.translateToLocal("lotr.faction." + this.codeName() + ".subtitle");
    }
    
    public LOTRFactionRank getRank(final EntityPlayer entityplayer) {
        return this.getRank(LOTRLevelData.getData(entityplayer));
    }
    
    public LOTRFactionRank getRank(final LOTRPlayerData pd) {
        final float alignment = pd.getAlignment(this);
        return this.getRank(alignment);
    }
    
    public LOTRFactionRank getRank(final float alignment) {
        for (final LOTRFactionRank rank : this.ranksSortedDescending) {
            if (!rank.isDummyRank() && alignment >= rank.alignment) {
                return rank;
            }
        }
        if (alignment >= 0.0f) {
            return LOTRFactionRank.RANK_NEUTRAL;
        }
        return LOTRFactionRank.RANK_ENEMY;
    }
    
    public LOTRFactionRank getRankAbove(final LOTRFactionRank curRank) {
        return this.getRankNAbove(curRank, 1);
    }
    
    public LOTRFactionRank getRankBelow(final LOTRFactionRank curRank) {
        return this.getRankNAbove(curRank, -1);
    }
    
    public LOTRFactionRank getRankNAbove(final LOTRFactionRank curRank, final int n) {
        if (this.ranksSortedDescending.isEmpty() || curRank == null) {
            return LOTRFactionRank.RANK_NEUTRAL;
        }
        int index = -1;
        if (curRank.isDummyRank()) {
            index = this.ranksSortedDescending.size();
        }
        else if (this.ranksSortedDescending.contains(curRank)) {
            index = this.ranksSortedDescending.indexOf(curRank);
        }
        if (index < 0) {
            return LOTRFactionRank.RANK_NEUTRAL;
        }
        index -= n;
        if (index < 0) {
            return this.ranksSortedDescending.get(0);
        }
        if (index > this.ranksSortedDescending.size() - 1) {
            return LOTRFactionRank.RANK_NEUTRAL;
        }
        return this.ranksSortedDescending.get(index);
    }
    
    public LOTRFactionRank getFirstRank() {
        if (this.ranksSortedDescending.isEmpty()) {
            return LOTRFactionRank.RANK_NEUTRAL;
        }
        return this.ranksSortedDescending.get(this.ranksSortedDescending.size() - 1);
    }
    
    public int getFactionColor() {
        return this.factionColor.getRGB();
    }
    
    public float[] getFactionColorComponents() {
        return this.factionColor.getColorComponents(null);
    }
    
    public boolean isPlayableAlignmentFaction() {
        return this.allowPlayer && !this.hasFixedAlignment;
    }
    
    public boolean isGoodRelation(final LOTRFaction other) {
        final LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
        return rel == LOTRFactionRelations.Relation.ALLY || rel == LOTRFactionRelations.Relation.FRIEND;
    }
    
    public boolean isAlly(final LOTRFaction other) {
        final LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
        return rel == LOTRFactionRelations.Relation.ALLY;
    }
    
    public boolean isNeutral(final LOTRFaction other) {
        return LOTRFactionRelations.getRelations(this, other) == LOTRFactionRelations.Relation.NEUTRAL;
    }
    
    public boolean isBadRelation(final LOTRFaction other) {
        final LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
        return rel == LOTRFactionRelations.Relation.ENEMY || rel == LOTRFactionRelations.Relation.MORTAL_ENEMY;
    }
    
    public boolean isMortalEnemy(final LOTRFaction other) {
        final LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
        return rel == LOTRFactionRelations.Relation.MORTAL_ENEMY;
    }
    
    public List<LOTRFaction> getOthersOfRelation(final LOTRFactionRelations.Relation rel) {
        final List<LOTRFaction> list = new ArrayList<LOTRFaction>();
        for (final LOTRFaction f : values()) {
            if (f != this && f.isPlayableAlignmentFaction() && LOTRFactionRelations.getRelations(this, f) == rel) {
                list.add(f);
            }
        }
        return list;
    }
    
    public List<LOTRFaction> getBonusesForKilling() {
        final List<LOTRFaction> list = new ArrayList<LOTRFaction>();
        for (final LOTRFaction f : values()) {
            if (f != this && this.isBadRelation(f)) {
                list.add(f);
            }
        }
        return list;
    }
    
    public List<LOTRFaction> getPenaltiesForKilling() {
        final List<LOTRFaction> list = new ArrayList<LOTRFaction>();
        list.add(this);
        for (final LOTRFaction f : values()) {
            if (f != this && this.isGoodRelation(f)) {
                list.add(f);
            }
        }
        return list;
    }
    
    public List<LOTRFaction> getConquestBoostRelations() {
        final List<LOTRFaction> list = new ArrayList<LOTRFaction>();
        for (final LOTRFaction f : values()) {
            if (f != this && f.isPlayableAlignmentFaction() && LOTRFactionRelations.getRelations(this, f) == LOTRFactionRelations.Relation.ALLY) {
                list.add(f);
            }
        }
        return list;
    }
    
    public static boolean controlZonesEnabled(final World world) {
        return LOTRLevelData.enableAlignmentZones() && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
    }
    
    public boolean inControlZone(final EntityPlayer entityplayer) {
        return this.inControlZone(((Entity)entityplayer).worldObj, ((Entity)entityplayer).posX, ((Entity)entityplayer).boundingBox.minY, ((Entity)entityplayer).posZ);
    }
    
    public boolean inControlZone(final World world, final double d, final double d1, final double d2) {
        if (this.inDefinedControlZone(world, d, d1, d2)) {
            return true;
        }
        final double nearbyRange = 24.0;
        final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(d, d1, d2, d, d1, d2).expand(nearbyRange, nearbyRange, nearbyRange);
        final List nearbyNPCs = world.selectEntitiesWithinAABB((Class)EntityLivingBase.class, aabb, (IEntitySelector)new LOTRNPCSelectForInfluence(this));
        return !nearbyNPCs.isEmpty();
    }
    
    public boolean inDefinedControlZone(final EntityPlayer entityplayer) {
        return this.inDefinedControlZone(entityplayer, 0);
    }
    
    public boolean inDefinedControlZone(final EntityPlayer entityplayer, final int extraMapRange) {
        return this.inDefinedControlZone(((Entity)entityplayer).worldObj, ((Entity)entityplayer).posX, ((Entity)entityplayer).boundingBox.minY, ((Entity)entityplayer).posZ, extraMapRange);
    }
    
    public boolean inDefinedControlZone(final World world, final double d, final double d1, final double d2) {
        return this.inDefinedControlZone(world, d, d1, d2, 0);
    }
    
    public boolean inDefinedControlZone(final World world, final double d, final double d1, final double d2, final int extraMapRange) {
        if (world.provider instanceof LOTRWorldProvider && ((LOTRWorldProvider)world.provider).getLOTRDimension() == this.factionDimension) {
            if (!controlZonesEnabled(world)) {
                return true;
            }
            for (final LOTRControlZone zone : this.controlZones) {
                if (zone.inZone(d, d1, d2, extraMapRange)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int getControlZoneReducedRange() {
        return this.isolationist ? 0 : 50;
    }
    
    public float getControlZoneAlignmentMultiplier(final EntityPlayer entityplayer) {
        if (this.inControlZone(entityplayer)) {
            return 1.0f;
        }
        final int reducedRange = this.getControlZoneReducedRange();
        final double dist = this.distanceToNearestControlZoneInRange(((Entity)entityplayer).posX, ((Entity)entityplayer).boundingBox.minY, ((Entity)entityplayer).posZ, reducedRange);
        if (dist >= 0.0) {
            final double mapDist = LOTRWaypoint.worldToMapR(dist);
            final float frac = (float)mapDist / reducedRange;
            float mplier = 1.0f - frac;
            mplier = MathHelper.clamp_float(mplier, 0.0f, 1.0f);
            return mplier;
        }
        return 0.0f;
    }
    
    public double distanceToNearestControlZoneInRange(final double d, final double d1, final double d2, final int mapRange) {
        double closestDist = -1.0;
        final int coordRange = LOTRWaypoint.mapToWorldR(mapRange);
        for (final LOTRControlZone zone : this.controlZones) {
            final double dx = d - zone.xCoord;
            final double dz = d2 - zone.zCoord;
            final double dSq = dx * dx + dz * dz;
            final double dToEdge = Math.sqrt(dSq) - zone.radiusCoord;
            if (dToEdge <= coordRange && (closestDist < 0.0 || dToEdge < closestDist)) {
                closestDist = dToEdge;
            }
        }
        return closestDist;
    }
    
    public int[] calculateFullControlZoneWorldBorders() {
        int xMin = 0;
        int xMax = 0;
        int zMin = 0;
        int zMax = 0;
        boolean first = true;
        for (final LOTRControlZone zone : this.controlZones) {
            final int cxMin = zone.xCoord - zone.radiusCoord;
            final int cxMax = zone.xCoord + zone.radiusCoord;
            final int czMin = zone.zCoord - zone.radiusCoord;
            final int czMax = zone.zCoord + zone.radiusCoord;
            if (first) {
                xMin = cxMin;
                xMax = cxMax;
                zMin = czMin;
                zMax = czMax;
                first = false;
            }
            else {
                xMin = Math.min(xMin, cxMin);
                xMax = Math.max(xMax, cxMax);
                zMin = Math.min(zMin, czMin);
                zMax = Math.max(zMax, czMax);
            }
        }
        return new int[] { xMin, xMax, zMin, zMax };
    }
    
    public boolean sharesControlZoneWith(final LOTRFaction other) {
        return this.sharesControlZoneWith(other, 0);
    }
    
    public boolean sharesControlZoneWith(final LOTRFaction other, final int extraMapRadius) {
        for (final LOTRControlZone zone : this.controlZones) {
            for (final LOTRControlZone otherZone : other.controlZones) {
                if (zone.intersectsWith(otherZone, extraMapRadius)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static LOTRFaction forName(final String name) {
        for (final LOTRFaction f : values()) {
            if (f.codeName().equals(name)) {
                return f;
            }
        }
        return null;
    }
    
    public static LOTRFaction forID(final int ID) {
        for (final LOTRFaction f : values()) {
            if (f.ordinal() == ID) {
                return f;
            }
        }
        return null;
    }
    
    public static List<LOTRFaction> getPlayableAlignmentFactions() {
        final List<LOTRFaction> factions = new ArrayList<LOTRFaction>();
        for (final LOTRFaction f : values()) {
            if (f.isPlayableAlignmentFaction()) {
                factions.add(f);
            }
        }
        return factions;
    }
    
    public static List<String> getPlayableAlignmentFactionNames() {
        final List<LOTRFaction> factions = getPlayableAlignmentFactions();
        final List<String> names = new ArrayList<String>();
        for (final LOTRFaction f : factions) {
            names.add(f.codeName());
        }
        return names;
    }
    
    public static List<LOTRFaction> getAllRegional(final LOTRDimension.DimensionRegion region) {
        final List<LOTRFaction> factions = new ArrayList<LOTRFaction>();
        for (final LOTRFaction f : values()) {
            if (f.factionRegion == region) {
                factions.add(f);
            }
        }
        return factions;
    }
    
    public static List<LOTRFaction> getAllRhun() {
        return getAllRegional(LOTRDimension.DimensionRegion.EAST);
    }
    
    public static List<LOTRFaction> getAllHarad() {
        return getAllRegional(LOTRDimension.DimensionRegion.SOUTH);
    }
    
    public static List<LOTRFaction> getAllOfType(final FactionType... types) {
        return getAllOfType(false, types);
    }
    
    public static List<LOTRFaction> getAllOfType(final boolean includeUnplayable, final FactionType... types) {
        final List<LOTRFaction> factions = new ArrayList<LOTRFaction>();
        for (final LOTRFaction f : values()) {
            Label_0119: {
                if (!includeUnplayable) {
                    if (!f.allowPlayer) {
                        break Label_0119;
                    }
                    if (f.hasFixedAlignment) {
                        break Label_0119;
                    }
                }
                boolean match = false;
                for (final FactionType t : types) {
                    if (f.isOfType(t)) {
                        match = true;
                        break;
                    }
                }
                if (match) {
                    factions.add(f);
                }
            }
        }
        return factions;
    }
    
    public boolean isOfType(final FactionType type) {
        return this.factionTypes.contains(type);
    }
    
    static {
        LOTRFaction.factionRand = new Random();
    }
    
    public enum FactionType
    {
        TYPE_FREE, 
        TYPE_ELF, 
        TYPE_MAN, 
        TYPE_DWARF, 
        TYPE_ORC, 
        TYPE_TROLL, 
        TYPE_TREE;
    }
}
