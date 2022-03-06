// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;
import cpw.mods.fml.common.FMLLog;
import java.util.Collection;
import java.util.Arrays;
import net.minecraft.util.StatCollector;
import java.util.ArrayList;
import java.util.List;

public class LOTRRoads
{
    public static List<LOTRRoads> allRoads;
    private static RoadPointDatabase roadPointDatabase;
    public RoadPoint[] roadPoints;
    public List<RoadPoint> endpoints;
    private String roadName;
    
    private LOTRRoads(final String name, final RoadPoint... ends) {
        this.endpoints = new ArrayList<RoadPoint>();
        this.roadName = name;
        for (final RoadPoint e : ends) {
            this.endpoints.add(e);
        }
    }
    
    public String getDisplayName() {
        return StatCollector.translateToLocal("lotr.road." + this.roadName);
    }
    
    private static void registerRoad(final String name, final Object... waypoints) {
        final List<RoadPoint> points = new ArrayList<RoadPoint>();
        for (final Object obj : waypoints) {
            if (obj instanceof LOTRWaypoint) {
                final LOTRWaypoint wp = (LOTRWaypoint)obj;
                points.add(new RoadPoint(wp.getXCoord(), wp.getZCoord(), true));
            }
            else {
                if (!(obj instanceof int[])) {
                    throw new IllegalArgumentException("Wrong road parameter!");
                }
                final int[] coords = (int[])obj;
                if (coords.length != 2) {
                    throw new IllegalArgumentException("Coords length must be 2!");
                }
                points.add(new RoadPoint(LOTRWaypoint.mapToWorldX(coords[0]), LOTRWaypoint.mapToWorldZ(coords[1]), false));
            }
        }
        final RoadPoint[] array = points.toArray(new RoadPoint[0]);
        final LOTRRoads[] roads = getSplines(name, array);
        LOTRRoads.allRoads.addAll(Arrays.asList(roads));
    }
    
    public static void createRoads() {
        FMLLog.info("LOTRRoads: Creating roads", new Object[0]);
        final long time = System.nanoTime();
        LOTRRoads.allRoads.clear();
        LOTRRoads.roadPointDatabase = new RoadPointDatabase();
        registerRoad("EredLuin", LOTRWaypoint.NOGROD, LOTRWaypoint.BELEGOST);
        registerRoad("NogrodForlond", LOTRWaypoint.NOGROD, LOTRWaypoint.FORLOND);
        registerRoad("NogrodMithlond", LOTRWaypoint.NOGROD, new int[] { 654, 650 }, LOTRWaypoint.MITHLOND_NORTH);
        registerRoad("Mithlond", LOTRWaypoint.HARLOND, new int[] { 658, 755 }, LOTRWaypoint.MITHLOND_SOUTH, new int[] { 690, 711 }, new int[] { 681, 705 }, LOTRWaypoint.MITHLOND_NORTH, new int[] { 644, 733 }, new int[] { 603, 733 }, new int[] { 554, 715 }, LOTRWaypoint.FORLOND);
        registerRoad("WestEast", LOTRWaypoint.MITHLOND_SOUTH, LOTRWaypoint.TOWER_HILLS, LOTRWaypoint.GREENHOLM, LOTRWaypoint.MICHEL_DELVING, LOTRWaypoint.WAYMEET, LOTRWaypoint.BYWATER, LOTRWaypoint.FROGMORTON, LOTRWaypoint.WHITFURROWS, LOTRWaypoint.BRANDYWINE_BRIDGE, new int[] { 870, 718 }, new int[] { 902, 729 }, LOTRWaypoint.BREE, LOTRWaypoint.STADDLE, LOTRWaypoint.FORSAKEN_INN, new int[] { LOTRWaypoint.WEATHERTOP.getX(), LOTRWaypoint.WEATHERTOP.getY() + 2 }, LOTRWaypoint.LAST_BRIDGE, new int[] { 1132, 723 }, new int[] { 1178, 704 }, LOTRWaypoint.HIGH_PASS, LOTRWaypoint.OLD_FORD, LOTRWaypoint.RIVER_GATE, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.REDWATER_FORD, new int[] { 1785, 775 }, LOTRWaypoint.RHUN_NORTH_FORD, LOTRWaypoint.RHUN_NORTHEAST, LOTRWaypoint.RHUN_ROAD_WAY, LOTRWaypoint.BARAZ_DUM);
        registerRoad("BywaterRoad", LOTRWaypoint.BYWATER, LOTRWaypoint.HOBBITON);
        registerRoad("Overhill", LOTRWaypoint.HOBBITON, LOTRWaypoint.OVERHILL);
        registerRoad("BucklandRoad", LOTRWaypoint.HAY_GATE, LOTRWaypoint.BUCKLEBURY, LOTRWaypoint.HAYSEND);
        registerRoad("Chetroad", LOTRWaypoint.STADDLE, LOTRWaypoint.COMBE, LOTRWaypoint.ARCHET);
        registerRoad("ElfPath", LOTRWaypoint.FOREST_GATE, LOTRWaypoint.ENCHANTED_RIVER, LOTRWaypoint.THRANDUIL_HALLS);
        registerRoad("EreborRoad", LOTRWaypoint.LONG_LAKE, LOTRWaypoint.DALE_CITY, LOTRWaypoint.EREBOR);
        registerRoad("DalePortRoad", LOTRWaypoint.DALE_CITY, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.DALE_PORT);
        registerRoad("DaleSouthRoad", LOTRWaypoint.EAST_RHOVANION_ROAD, LOTRWaypoint.OLD_RHOVANION, LOTRWaypoint.RUNNING_FORD, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.WEST_PEAK);
        registerRoad("IronHills", LOTRWaypoint.WEST_PEAK, new int[] { 1652, 621 }, LOTRWaypoint.EAST_PEAK);
        registerRoad("DorwinionSouthRoad", LOTRWaypoint.DALE_PORT, LOTRWaypoint.DORWINION_CROSSROADS, LOTRWaypoint.DORWINION_COURT, LOTRWaypoint.DORWINION_FORD);
        registerRoad("DorwinionEastRoad", LOTRWaypoint.OLD_RHOVANION, LOTRWaypoint.DORWINION_CROSSROADS, LOTRWaypoint.DORWINION_PORT);
        registerRoad("RhunRoad", LOTRWaypoint.DORWINION_FORD, LOTRWaypoint.BORDER_TOWN, LOTRWaypoint.RHUN_SEA_CITY, LOTRWaypoint.RHUN_CAPITAL, new int[] { 1888, 958 }, LOTRWaypoint.RHUN_NORTH_CITY, LOTRWaypoint.BAZYLAN, LOTRWaypoint.RHUN_NORTHEAST);
        registerRoad("RhunEastRoad", LOTRWaypoint.RHUN_NORTH_CITY, LOTRWaypoint.RHUN_EAST_TOWN, LOTRWaypoint.RHUN_EAST_CITY);
        registerRoad("Nobottom", LOTRWaypoint.TIGHFIELD, LOTRWaypoint.LITTLE_DELVING, LOTRWaypoint.NOBOTTLE, LOTRWaypoint.NEEDLEHOLE);
        registerRoad("Oatbarton", LOTRWaypoint.OATBARTON, LOTRWaypoint.FROGMORTON);
        registerRoad("Stock", LOTRWaypoint.TUCKBOROUGH, LOTRWaypoint.STOCK);
        registerRoad("Deephallow", LOTRWaypoint.SCARY, LOTRWaypoint.WHITFURROWS, LOTRWaypoint.STOCK, LOTRWaypoint.DEEPHALLOW);
        registerRoad("Willowbottom", LOTRWaypoint.WILLOWBOTTOM, LOTRWaypoint.DEEPHALLOW);
        registerRoad("ArnorRoad", LOTRWaypoint.ANNUMINAS, LOTRWaypoint.FORNOST);
        registerRoad("Greenway", LOTRWaypoint.FORNOST, LOTRWaypoint.BREE, LOTRWaypoint.GREENWAY_CROSSROADS);
        registerRoad("ElvenWay", LOTRWaypoint.WEST_GATE, new int[] { 1133, 867 }, new int[] { 1124, 868 }, LOTRWaypoint.OST_IN_EDHIL, new int[] { 1073, 864 }, LOTRWaypoint.OLD_ELF_WAY, new int[] { 1002, 849 }, new int[] { 992, 860 }, LOTRWaypoint.THARBAD, new int[] { 959, 889 }, new int[] { 926, 913 }, new int[] { 902, 942 }, LOTRWaypoint.LOND_DAER);
        registerRoad("BruinenPath", LOTRWaypoint.FORD_BRUINEN, LOTRWaypoint.RIVENDELL);
        registerRoad("NimrodelRoad", LOTRWaypoint.DIMRILL_DALE, LOTRWaypoint.NIMRODEL);
        registerRoad("AnduinRoad", LOTRWaypoint.MORANNON, new int[] { 1428, 1066 }, LOTRWaypoint.EAST_RHOVANION_ROAD, LOTRWaypoint.ANDUIN_CROSSROADS, new int[] { 1325, 820 }, new int[] { 1318, 735 }, LOTRWaypoint.FOREST_GATE);
        registerRoad("DolGuldurRoad", LOTRWaypoint.ANDUIN_CROSSROADS, LOTRWaypoint.DOL_GULDUR);
        registerRoad("Framsburg", LOTRWaypoint.FOREST_GATE, new int[] { 1278, 605 }, LOTRWaypoint.FRAMSBURG, new int[] { 1260, 565 }, LOTRWaypoint.DAINS_HALLS);
        registerRoad("NorthSouth", LOTRWaypoint.LITTLE_DELVING, LOTRWaypoint.WAYMEET, LOTRWaypoint.LONGBOTTOM, LOTRWaypoint.SARN_FORD, LOTRWaypoint.GREENWAY_CROSSROADS, LOTRWaypoint.THARBAD, LOTRWaypoint.ENEDWAITH_ROAD, LOTRWaypoint.FORDS_OF_ISEN, LOTRWaypoint.HELMS_CROSSROADS, LOTRWaypoint.GRIMSLADE, LOTRWaypoint.EDORAS, LOTRWaypoint.ALDBURG, LOTRWaypoint.MERING_STREAM, LOTRWaypoint.AMON_DIN);
        registerRoad("TirithRoad", LOTRWaypoint.AMON_DIN, LOTRWaypoint.MINAS_TIRITH);
        registerRoad("OsgiliathRoad", LOTRWaypoint.MINAS_TIRITH, LOTRWaypoint.OSGILIATH_WEST);
        registerRoad("OsgiliathCrossing", LOTRWaypoint.OSGILIATH_WEST, LOTRWaypoint.OSGILIATH_EAST);
        registerRoad("OsgiliathMorgulRoad", LOTRWaypoint.OSGILIATH_EAST, LOTRWaypoint.CROSSROADS_ITHILIEN, LOTRWaypoint.MINAS_MORGUL);
        registerRoad("GondorSouthRoad", LOTRWaypoint.MINAS_TIRITH, LOTRWaypoint.CROSSINGS_ERUI, LOTRWaypoint.PELARGIR);
        registerRoad("IsengardRoad", LOTRWaypoint.FORDS_OF_ISEN, LOTRWaypoint.ISENGARD);
        registerRoad("HelmRoad", LOTRWaypoint.HELMS_CROSSROADS, LOTRWaypoint.HELMS_DEEP);
        registerRoad("WoldRoad", LOTRWaypoint.EDORAS, LOTRWaypoint.ENTWADE, new int[] { 1260, 1060 }, LOTRWaypoint.WOLD);
        registerRoad("LebenninRoad", LOTRWaypoint.ERECH, LOTRWaypoint.TARLANG, LOTRWaypoint.CALEMBEL, LOTRWaypoint.ETHRING, LOTRWaypoint.LINHIR, LOTRWaypoint.PELARGIR, LOTRWaypoint.CROSSINGS_OF_POROS);
        registerRoad("DolAmroth", LOTRWaypoint.ETHRING, LOTRWaypoint.TARNOST, LOTRWaypoint.EDHELLOND, new int[] { 1185, 1325 }, LOTRWaypoint.DOL_AMROTH);
        registerRoad("CairAndros", LOTRWaypoint.AMON_DIN, LOTRWaypoint.CAIR_ANDROS, LOTRWaypoint.NORTH_ITHILIEN);
        registerRoad("SauronRoad", LOTRWaypoint.MINAS_MORGUL, LOTRWaypoint.MOUNT_DOOM, LOTRWaypoint.BARAD_DUR, LOTRWaypoint.SEREGOST, new int[] { 1742, 1209 }, new int[] { 1809, 1172 }, LOTRWaypoint.EASTERN_GUARD, LOTRWaypoint.MORDOR_FORD, LOTRWaypoint.RHUN_SOUTH_PASS, new int[] { 1875, 1003 }, new int[] { 1867, 996 }, LOTRWaypoint.RHUN_CAPITAL);
        registerRoad("MorannonRoad", LOTRWaypoint.MORANNON, LOTRWaypoint.UDUN);
        registerRoad("MorannonRhunRoad", LOTRWaypoint.MORANNON, new int[] { 1520, 1130 }, new int[] { 1658, 1140 }, new int[] { 1780, 1115 }, LOTRWaypoint.MORDOR_FORD, LOTRWaypoint.RHUN_SOUTHEAST, LOTRWaypoint.KHAND_NORTH_ROAD, LOTRWaypoint.KHAND_FORD, LOTRWaypoint.HARNEN_BLACK_TOWN, LOTRWaypoint.CROSSINGS_OF_LITHNEN, LOTRWaypoint.HARNEN_ROAD_TOWN, LOTRWaypoint.HARNEN_RIVER_TOWN, LOTRWaypoint.HARNEN_SEA_TOWN, LOTRWaypoint.COAST_FORTRESS, LOTRWaypoint.GATE_FUINUR, LOTRWaypoint.UMBAR_CITY, LOTRWaypoint.GATE_HERUMOR);
        registerRoad("GorgorothRoad", LOTRWaypoint.UDUN, LOTRWaypoint.CARACH_ANGREN, LOTRWaypoint.BARAD_DUR, LOTRWaypoint.THAURBAND);
        registerRoad("HaradRoad", LOTRWaypoint.MORANNON, LOTRWaypoint.NORTH_ITHILIEN, LOTRWaypoint.CROSSROADS_ITHILIEN, LOTRWaypoint.CROSSINGS_OF_POROS, new int[] { 1429, 1394 }, new int[] { 1408, 1432 }, new int[] { 1428, 1470 }, new int[] { 1435, 1526 }, LOTRWaypoint.CROSSINGS_OF_HARAD, LOTRWaypoint.HARNEN_ROAD_TOWN, LOTRWaypoint.DESERT_TOWN);
        registerRoad("UmbarRoad", LOTRWaypoint.UMBAR_CITY, LOTRWaypoint.UMBAR_GATE, LOTRWaypoint.AIN_AL_HARAD, LOTRWaypoint.GARDENS_BERUTHIEL, LOTRWaypoint.FERTILE_VALLEY, LOTRWaypoint.SOUTH_DESERT_TOWN);
        registerRoad("GulfRoad", LOTRWaypoint.TOWN_BONES, new int[] { 1794, 2110 }, LOTRWaypoint.GULF_FORD, LOTRWaypoint.GULF_TRADE_TOWN, LOTRWaypoint.GULF_CITY, LOTRWaypoint.GULF_NORTH_TOWN, new int[] { 1702, 1940 }, LOTRWaypoint.GULF_OF_HARAD, new int[] { 1775, 2002 }, LOTRWaypoint.GULF_EAST_PORT);
        registerRoad("JungleNorthRoad", LOTRWaypoint.JUNGLE_CITY_TRADE, LOTRWaypoint.JUNGLE_CITY_OLD, LOTRWaypoint.JUNGLE_CITY_NORTH);
        registerRoad("JungleMangroveRoad", LOTRWaypoint.JUNGLE_CITY_NORTH, LOTRWaypoint.JUNGLE_CITY_EAST, LOTRWaypoint.HARADUIN_MOUTH);
        registerRoad("JungleDeepRoad", LOTRWaypoint.JUNGLE_CITY_NORTH, LOTRWaypoint.JUNGLE_CITY_CAPITAL, LOTRWaypoint.JUNGLE_CITY_CAVES, LOTRWaypoint.JUNGLE_CITY_DEEP);
        registerRoad("JungleWestEastRoad", LOTRWaypoint.JUNGLE_CITY_OLD, LOTRWaypoint.JUNGLE_CITY_STONE, LOTRWaypoint.JUNGLE_CITY_CAPITAL, LOTRWaypoint.JUNGLE_LAKES, LOTRWaypoint.JUNGLE_CITY_WATCH);
        registerRoad("JungleLakeRoad", LOTRWaypoint.JUNGLE_LAKES, LOTRWaypoint.JUNGLE_CITY_EAST, LOTRWaypoint.HARADUIN_BRIDGE, LOTRWaypoint.OLD_JUNGLE_RUIN);
        final long newTime = System.nanoTime();
        final int roads = LOTRRoads.allRoads.size();
        int points = 0;
        int dbEntries = 0;
        int dbPoints = 0;
        for (final LOTRRoads road : LOTRRoads.allRoads) {
            points += road.roadPoints.length;
        }
        for (final Map.Entry<Pair<Integer, Integer>, List<RoadPoint>> e : LOTRRoads.roadPointDatabase.pointMap.entrySet()) {
            ++dbEntries;
            dbPoints += e.getValue().size();
        }
        FMLLog.info("LOTRRoads: Created roads in " + (newTime - time) / 1.0E9 + "s", new Object[0]);
        FMLLog.info("LOTRRoads: roads=" + roads + ", points=" + points + ", dbEntries=" + dbEntries + ", dbPoints=" + dbPoints, new Object[0]);
    }
    
    public static boolean isRoadAt(final int x, final int z) {
        return isRoadNear(x, z, 4) >= 0.0f;
    }
    
    public static float isRoadNear(final int x, final int z, final int width) {
        final double widthSq = width * width;
        float leastSqRatio = -1.0f;
        final List<RoadPoint> points = LOTRRoads.roadPointDatabase.getPointsForCoords(x, z);
        for (final RoadPoint point : points) {
            final double dx = point.x - x;
            final double dz = point.z - z;
            final double distSq = dx * dx + dz * dz;
            if (distSq < widthSq) {
                final float f = (float)(distSq / widthSq);
                if (leastSqRatio == -1.0f) {
                    leastSqRatio = f;
                }
                else {
                    if (f >= leastSqRatio) {
                        continue;
                    }
                    leastSqRatio = f;
                }
            }
        }
        return leastSqRatio;
    }
    
    static {
        LOTRRoads.allRoads = new ArrayList<LOTRRoads>();
        LOTRRoads.roadPointDatabase = new RoadPointDatabase();
    }
    
    private static class RoadPointDatabase
    {
        private Map<Pair<Integer, Integer>, List<RoadPoint>> pointMap;
        private static final int COORD_LOOKUP_SIZE = 1000;
        
        private RoadPointDatabase() {
            this.pointMap = new HashMap<Pair<Integer, Integer>, List<RoadPoint>>();
        }
        
        public List<RoadPoint> getPointsForCoords(final int x, final int z) {
            final int x2 = x / 1000;
            final int z2 = z / 1000;
            return this.getRoadList(x2, z2, false);
        }
        
        public void add(final RoadPoint point) {
            final int x = (int)Math.round(point.x / 1000.0);
            final int z = (int)Math.round(point.z / 1000.0);
            for (int overlap = 1, i = -overlap; i <= overlap; ++i) {
                for (int k = -overlap; k <= overlap; ++k) {
                    final int xKey = x + i;
                    final int zKey = z + k;
                    this.getRoadList(xKey, zKey, true).add(point);
                }
            }
        }
        
        private List<RoadPoint> getRoadList(final int xKey, final int zKey, final boolean addToMap) {
            final Pair<Integer, Integer> key = (Pair<Integer, Integer>)Pair.of((Object)xKey, (Object)zKey);
            List<RoadPoint> list = this.pointMap.get(key);
            if (list == null) {
                list = new ArrayList<RoadPoint>();
                if (addToMap) {
                    this.pointMap.put(key, list);
                }
            }
            return list;
        }
    }
    
    public static class RoadPoint
    {
        public final double x;
        public final double z;
        public final boolean isWaypoint;
        
        public RoadPoint(final double i, final double j, final boolean flag) {
            this.x = i;
            this.z = j;
            this.isWaypoint = flag;
        }
    }
    
    private static class BezierCurves
    {
        private static int roadLengthFactor;
        
        private static LOTRRoads[] getSplines(final String name, final RoadPoint[] waypoints) {
            if (waypoints.length == 2) {
                final RoadPoint p1 = waypoints[0];
                final RoadPoint p2 = waypoints[1];
                final LOTRRoads road = new LOTRRoads(name, new RoadPoint[] { p1, p2 }, null);
                final double dx = p2.x - p1.x;
                final double dz = p2.z - p1.z;
                final int roadLength = (int)Math.round(Math.sqrt(dx * dx + dz * dz));
                final int points = roadLength * BezierCurves.roadLengthFactor;
                road.roadPoints = new RoadPoint[points];
                for (int l = 0; l < points; ++l) {
                    final double t = l / (double)points;
                    final RoadPoint point = bezier(p1, p1, p2, p2, t);
                    road.roadPoints[l] = point;
                    LOTRRoads.roadPointDatabase.add(point);
                }
                return new LOTRRoads[] { road };
            }
            final int length = waypoints.length;
            final double[] x = new double[length];
            final double[] z = new double[length];
            for (int i = 0; i < length; ++i) {
                x[i] = waypoints[i].x;
                z[i] = waypoints[i].z;
            }
            final double[][] controlX = getControlPoints(x);
            final double[][] controlZ = getControlPoints(z);
            final int controlPoints = controlX[0].length;
            final RoadPoint[] controlPoints2 = new RoadPoint[controlPoints];
            final RoadPoint[] controlPoints3 = new RoadPoint[controlPoints];
            for (int j = 0; j < controlPoints; ++j) {
                final RoadPoint p3 = new RoadPoint(controlX[0][j], controlZ[0][j], false);
                final RoadPoint p4 = new RoadPoint(controlX[1][j], controlZ[1][j], false);
                controlPoints2[j] = p3;
                controlPoints3[j] = p4;
            }
            final LOTRRoads[] roads = new LOTRRoads[length - 1];
            for (int k = 0; k < roads.length; ++k) {
                final RoadPoint p5 = waypoints[k];
                final RoadPoint p6 = waypoints[k + 1];
                final RoadPoint cp1 = controlPoints2[k];
                final RoadPoint cp2 = controlPoints3[k];
                final LOTRRoads road2 = new LOTRRoads(name, new RoadPoint[] { p5, p6 }, null);
                roads[k] = road2;
                final double dx2 = p6.x - p5.x;
                final double dz2 = p6.z - p5.z;
                final int roadLength2 = (int)Math.round(Math.sqrt(dx2 * dx2 + dz2 * dz2));
                final int points2 = roadLength2 * BezierCurves.roadLengthFactor;
                road2.roadPoints = new RoadPoint[points2];
                for (int m = 0; m < points2; ++m) {
                    final double t2 = m / (double)points2;
                    final RoadPoint point2 = bezier(p5, cp1, cp2, p6, t2);
                    road2.roadPoints[m] = point2;
                    LOTRRoads.roadPointDatabase.add(point2);
                }
            }
            return roads;
        }
        
        private static RoadPoint lerp(final RoadPoint a, final RoadPoint b, final double t) {
            final double x = a.x + (b.x - a.x) * t;
            final double z = a.z + (b.z - a.z) * t;
            return new RoadPoint(x, z, false);
        }
        
        private static RoadPoint bezier(final RoadPoint a, final RoadPoint b, final RoadPoint c, final RoadPoint d, final double t) {
            final RoadPoint ab = lerp(a, b, t);
            final RoadPoint bc = lerp(b, c, t);
            final RoadPoint cd = lerp(c, d, t);
            final RoadPoint abbc = lerp(ab, bc, t);
            final RoadPoint bccd = lerp(bc, cd, t);
            return lerp(abbc, bccd, t);
        }
        
        private static double[][] getControlPoints(final double[] src) {
            final int length = src.length - 1;
            final double[] p1 = new double[length];
            final double[] p2 = new double[length];
            final double[] a = new double[length];
            final double[] b = new double[length];
            final double[] c = new double[length];
            final double[] r = new double[length];
            a[0] = 0.0;
            b[0] = 2.0;
            c[0] = 1.0;
            r[0] = src[0] + 2.0 * src[1];
            for (int i = 1; i < length - 1; ++i) {
                a[i] = 1.0;
                b[i] = 4.0;
                c[i] = 1.0;
                r[i] = 4.0 * src[i] + 2.0 * src[i + 1];
            }
            a[length - 1] = 2.0;
            b[length - 1] = 7.0;
            c[length - 1] = 0.0;
            r[length - 1] = 8.0 * src[length - 1] + src[length];
            for (int i = 1; i < length; ++i) {
                final double m = a[i] / b[i - 1];
                b[i] -= m * c[i - 1];
                r[i] -= m * r[i - 1];
            }
            p1[length - 1] = r[length - 1] / b[length - 1];
            for (int i = length - 2; i >= 0; --i) {
                final double p3 = (r[i] - c[i] * p1[i + 1]) / b[i];
                p1[i] = p3;
            }
            for (int i = 0; i < length - 1; ++i) {
                p2[i] = 2.0 * src[i + 1] - p1[i + 1];
            }
            p2[length - 1] = 0.5 * (src[length] + p1[length - 1]);
            return new double[][] { p1, p2 };
        }
        
        static {
            BezierCurves.roadLengthFactor = 1;
        }
    }
}
