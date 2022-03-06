// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.List;

public enum LOTRMusicRegion
{
    MENU("menu"), 
    SEA("sea"), 
    SHIRE("shire"), 
    OLD_FOREST("oldForest"), 
    LINDON("lindon"), 
    BARROW_DOWNS("barrowDowns"), 
    BREE("bree"), 
    ERIADOR("eriador"), 
    RIVENDELL("rivendell"), 
    ANGMAR("angmar"), 
    EREGION("eregion"), 
    ENEDWAITH("enedwaith"), 
    DUNLAND("dunland"), 
    PUKEL("pukel"), 
    MISTY_MOUNTAINS("mistyMountains"), 
    FORODWAITH("forodwaith"), 
    GREY_MOUNTAINS("greyMountains"), 
    RHOVANION("rhovanion"), 
    MIRKWOOD("mirkwood"), 
    WOODLAND_REALM("woodlandRealm"), 
    DALE("dale"), 
    DWARVEN("dwarven"), 
    LOTHLORIEN("lothlorien"), 
    FANGORN("fangorn"), 
    ROHAN("rohan"), 
    ISENGARD("isengard"), 
    GONDOR("gondor"), 
    BROWN_LANDS("brownLands"), 
    DEAD_MARSHES("deadMarshes"), 
    MORDOR("mordor"), 
    DORWINION("dorwinion"), 
    RHUN("rhun"), 
    NEAR_HARAD("nearHarad"), 
    FAR_HARAD("farHarad"), 
    FAR_HARAD_JUNGLE("farHaradJungle"), 
    PERTOROGWAITH("pertorogwaith"), 
    UTUMNO("utumno");
    
    public static final String allRegionCode = "all";
    public final String regionName;
    private List<String> subregions;
    
    private LOTRMusicRegion(final String s) {
        this.subregions = new ArrayList<String>();
        this.regionName = s;
    }
    
    public Sub getSubregion(final String s) {
        if (s != null && !this.subregions.contains(s)) {
            this.subregions.add(s);
        }
        return new Sub(this, s);
    }
    
    public Sub getWithoutSub() {
        return new Sub(this, null);
    }
    
    public boolean hasSubregion(final String s) {
        return this.subregions.contains(s);
    }
    
    public List<String> getAllSubregions() {
        return this.subregions;
    }
    
    public boolean hasNoSubregions() {
        return this.subregions.isEmpty();
    }
    
    public static LOTRMusicRegion forName(final String s) {
        for (final LOTRMusicRegion r : values()) {
            if (s.equalsIgnoreCase(r.regionName)) {
                return r;
            }
        }
        return null;
    }
    
    public static class Sub extends Pair<LOTRMusicRegion, String>
    {
        public final LOTRMusicRegion region;
        public final String subregion;
        
        public Sub(final LOTRMusicRegion r, final String s) {
            this.region = r;
            this.subregion = s;
        }
        
        public String setValue(final String value) {
            throw new IllegalArgumentException("Value is final");
        }
        
        public LOTRMusicRegion getLeft() {
            return this.region;
        }
        
        public String getRight() {
            return this.subregion;
        }
    }
}
