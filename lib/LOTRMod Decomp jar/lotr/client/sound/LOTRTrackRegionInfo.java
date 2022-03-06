// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRTrackRegionInfo
{
    private LOTRMusicRegion region;
    private List<String> subregions;
    private static final double defaultWeight = 1.0;
    private double weight;
    private List<LOTRMusicCategory> categories;
    
    public LOTRTrackRegionInfo(final LOTRMusicRegion r) {
        this.subregions = new ArrayList<String>();
        this.categories = new ArrayList<LOTRMusicCategory>();
        this.region = r;
        this.weight = 1.0;
    }
    
    public List<String> getSubregions() {
        return this.subregions;
    }
    
    public void addSubregion(final String sub) {
        if (!this.subregions.contains(sub)) {
            this.subregions.add(sub);
        }
    }
    
    public void addAllSubregions() {
        final List<String> allSubs = this.region.getAllSubregions();
        if (!allSubs.isEmpty()) {
            for (final String sub : allSubs) {
                this.addSubregion(sub);
            }
        }
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public void setWeight(final double d) {
        this.weight = d;
    }
    
    public List<LOTRMusicCategory> getCategories() {
        return this.categories;
    }
    
    public void addCategory(final LOTRMusicCategory cat) {
        if (!this.categories.contains(cat)) {
            this.categories.add(cat);
        }
    }
    
    public void addAllCategories() {
        for (final LOTRMusicCategory cat : LOTRMusicCategory.values()) {
            this.addCategory(cat);
        }
    }
}
