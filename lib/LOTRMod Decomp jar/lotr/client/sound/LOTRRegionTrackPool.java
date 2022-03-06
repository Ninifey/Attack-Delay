// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRRegionTrackPool
{
    private final LOTRMusicRegion region;
    private final String subregion;
    private List<LOTRMusicTrack> trackList;
    
    public LOTRRegionTrackPool(final LOTRMusicRegion r, final String s) {
        this.trackList = new ArrayList<LOTRMusicTrack>();
        this.region = r;
        this.subregion = s;
    }
    
    public void addTrack(final LOTRMusicTrack track) {
        this.trackList.add(track);
    }
    
    public boolean isEmpty() {
        return this.trackList.isEmpty();
    }
    
    public LOTRMusicTrack getRandomTrack(final Random rand, final LOTRTrackSorter.Filter filter) {
        final List<LOTRMusicTrack> sortedTracks = LOTRTrackSorter.sortTracks(this.trackList, filter);
        double totalWeight = 0.0;
        for (final LOTRMusicTrack track : sortedTracks) {
            final double weight = track.getRegionInfo(this.region).getWeight();
            totalWeight += weight;
        }
        double randWeight = rand.nextDouble();
        randWeight *= totalWeight;
        final Iterator<LOTRMusicTrack> it = sortedTracks.iterator();
        LOTRMusicTrack track2 = null;
        while (it.hasNext()) {
            track2 = it.next();
            final double weight2 = track2.getRegionInfo(this.region).getWeight();
            randWeight -= weight2;
            if (randWeight < 0.0) {
                return track2;
            }
        }
        return track2;
    }
}
