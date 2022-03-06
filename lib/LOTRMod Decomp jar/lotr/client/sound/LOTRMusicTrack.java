// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundList;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.audio.ISound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lotr.common.world.biome.LOTRMusicRegion;
import java.util.Map;
import net.minecraft.client.audio.PositionedSound;

public class LOTRMusicTrack extends PositionedSound
{
    private final String filename;
    private String title;
    private Map<LOTRMusicRegion, LOTRTrackRegionInfo> regions;
    private List<String> authors;
    
    public LOTRMusicTrack(final String s) {
        super(getMusicResource(s));
        this.regions = new HashMap<LOTRMusicRegion, LOTRTrackRegionInfo>();
        this.authors = new ArrayList<String>();
        super.field_147662_b = 1.0f;
        super.field_147663_c = 1.0f;
        super.field_147660_d = 0.0f;
        super.field_147661_e = 0.0f;
        super.field_147658_f = 0.0f;
        super.field_147659_g = false;
        super.field_147665_h = 0;
        super.field_147666_i = ISound.AttenuationType.NONE;
        this.filename = s;
    }
    
    private static ResourceLocation getMusicResource(final String s) {
        final ResourceLocation res = new ResourceLocation("lotrmusic", s);
        return res;
    }
    
    public void loadTrack(final InputStream in) throws IOException {
        this.loadSoundResource();
        LOTRMusic.addTrackToRegions(this);
    }
    
    private void loadSoundResource() {
        final ResourceLocation resource = this.func_147650_b();
        final SoundList soundList = new SoundList();
        soundList.func_148572_a(true);
        soundList.func_148571_a(SoundCategory.MUSIC);
        final SoundList.SoundEntry soundEntry = new SoundList.SoundEntry();
        soundEntry.func_148561_a(this.filename);
        soundEntry.func_148553_a(this.func_147653_e());
        soundEntry.func_148559_b(this.func_147655_f());
        soundEntry.func_148554_a(1);
        soundEntry.func_148562_a(SoundList.SoundEntry.Type.SOUND_EVENT);
        soundEntry.func_148557_a(true);
        soundList.func_148570_a().add(soundEntry);
        final SoundRegistry sndRegistry = LOTRMusic.Reflect.getSoundRegistry();
        SoundEventAccessorComposite soundAccessorComp;
        if (sndRegistry.containsKey((Object)resource) && !soundList.func_148574_b()) {
            soundAccessorComp = (SoundEventAccessorComposite)sndRegistry.getObject((Object)resource);
        }
        else {
            soundAccessorComp = new SoundEventAccessorComposite(resource, 1.0, 1.0, soundList.func_148573_c());
            sndRegistry.func_148762_a(soundAccessorComp);
        }
        final SoundPoolEntry soundPoolEntry = new SoundPoolEntry(resource, (double)soundEntry.func_148560_c(), (double)soundEntry.func_148558_b(), soundEntry.func_148552_f());
        final ISoundEventAccessor soundAccessor = (ISoundEventAccessor)new TrackSoundAccessor(soundPoolEntry, soundEntry.func_148555_d());
        soundAccessorComp.func_148727_a(soundAccessor);
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        return this.filename;
    }
    
    public void setTitle(final String s) {
        this.title = s;
    }
    
    public Set<LOTRMusicRegion> getAllRegions() {
        return this.regions.keySet();
    }
    
    public LOTRTrackRegionInfo createRegionInfo(final LOTRMusicRegion reg) {
        LOTRTrackRegionInfo info = this.regions.get(reg);
        if (info == null) {
            info = new LOTRTrackRegionInfo(reg);
            this.regions.put(reg, info);
        }
        return info;
    }
    
    public LOTRTrackRegionInfo getRegionInfo(final LOTRMusicRegion reg) {
        if (this.regions.containsKey(reg)) {
            return this.regions.get(reg);
        }
        return null;
    }
    
    public void addAuthor(final String s) {
        this.authors.add(s);
    }
    
    public List<String> getAuthors() {
        return this.authors;
    }
    
    public String[] getTrackInfo() {
        final List<String> list = new ArrayList<String>();
        list.add("Title: " + this.getTitle());
        list.add("Filename: " + this.getFilename());
        list.add("Regions:");
        for (final LOTRMusicRegion reg : this.getAllRegions()) {
            final LOTRTrackRegionInfo info = this.getRegionInfo(reg);
            list.add(">" + reg.regionName);
            list.add(">Weight: " + info.getWeight());
            final List<String> subs = info.getSubregions();
            if (!subs.isEmpty()) {
                list.add(">Subregions:");
                for (final String s : subs) {
                    list.add(">>" + s);
                }
            }
            final List<LOTRMusicCategory> categories = info.getCategories();
            if (!categories.isEmpty()) {
                list.add(">Categories:");
                for (final LOTRMusicCategory cat : categories) {
                    list.add(">>" + cat.categoryName);
                }
            }
        }
        list.add("Authors:");
        for (final String auth : this.getAuthors()) {
            list.add(">" + auth);
        }
        return list.toArray(new String[0]);
    }
    
    private static class TrackSoundAccessor implements ISoundEventAccessor
    {
        private final SoundPoolEntry soundEntry;
        private final int weight;
        
        private TrackSoundAccessor(final SoundPoolEntry e, final int i) {
            this.soundEntry = e;
            this.weight = i;
        }
        
        public int func_148721_a() {
            return this.weight;
        }
        
        public SoundPoolEntry func_148720_g() {
            return new SoundPoolEntry(this.soundEntry);
        }
    }
}
