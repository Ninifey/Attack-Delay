// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import lotr.common.world.biome.LOTRMusicRegion;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class LOTRTrackSorter
{
    public static List<LOTRMusicTrack> sortTracks(final List<LOTRMusicTrack> tracks, final Filter filter) {
        final List<LOTRMusicTrack> sorted = new ArrayList<LOTRMusicTrack>();
        for (final LOTRMusicTrack track : tracks) {
            if (filter.accept(track)) {
                sorted.add(track);
            }
        }
        return sorted;
    }
    
    public static Filter forRegionAndCategory(final LOTRMusicRegion reg, final LOTRMusicCategory cat) {
        return new Filter() {
            @Override
            public boolean accept(final LOTRMusicTrack track) {
                return track.getRegionInfo(reg).getCategories().contains(cat);
            }
        };
    }
    
    public static Filter forAny() {
        return new Filter() {
            @Override
            public boolean accept(final LOTRMusicTrack track) {
                return true;
            }
        };
    }
    
    public interface Filter
    {
        boolean accept(final LOTRMusicTrack p0);
    }
}
