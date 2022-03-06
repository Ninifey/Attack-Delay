// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import lotr.common.LOTRReflection;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.util.HashMap;
import java.io.BufferedReader;
import net.minecraft.util.ResourceLocation;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.zip.ZipEntry;
import java.util.Collection;
import java.util.Arrays;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import com.google.gson.JsonParser;
import java.io.Reader;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import org.apache.commons.io.input.BOMInputStream;
import net.minecraft.client.resources.AbstractResourcePack;
import lotr.common.world.biome.LOTRBiome;
import java.io.IOException;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.FileResourcePack;
import lotr.common.LOTRDimension;
import java.util.Iterator;
import lotr.common.util.LOTRLog;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import lotr.common.world.LOTRWorldProvider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.audio.SoundCategory;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import java.util.Random;
import lotr.common.world.biome.LOTRMusicRegion;
import java.util.Map;
import java.util.List;
import java.io.File;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class LOTRMusic implements IResourceManagerReloadListener
{
    private static File musicDir;
    private static final String jsonFilename = "music.json";
    public static final String musicResourcePath = "lotrmusic";
    public static final LOTRMusicResourceManager trackResourceManager;
    private static List<LOTRMusicTrack> allTracks;
    private static Map<LOTRMusicRegion.Sub, LOTRRegionTrackPool> regionTracks;
    private static boolean initSubregions;
    private static Random musicRand;
    
    public LOTRMusic() {
        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener((IResourceManagerReloadListener)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onResourceManagerReload(final IResourceManager resourcemanager) {
        loadMusicPacks(Minecraft.getMinecraft().mcDataDir, (SimpleReloadableResourceManager)resourcemanager);
    }
    
    public void update() {
        LOTRMusicTicker.update(LOTRMusic.musicRand);
    }
    
    @SubscribeEvent
    public void onPlaySound(final PlaySoundEvent17 event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (!LOTRMusic.allTracks.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof LOTRMusicTrack)) {
            if (isLOTRDimension()) {
                event.result = null;
                return;
            }
            if (isMenuMusic() && !getTracksForRegion(LOTRMusicRegion.MENU, null).isEmpty()) {
                event.result = null;
            }
        }
    }
    
    public static boolean isLOTRDimension() {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        return entityplayer != null && world != null && world.provider instanceof LOTRWorldProvider;
    }
    
    public static boolean isMenuMusic() {
        final Minecraft mc = Minecraft.getMinecraft();
        return mc.func_147109_W() == MusicTicker.MusicType.MENU;
    }
    
    public static LOTRRegionTrackPool getTracksForRegion(final LOTRMusicRegion region, final String sub) {
        if (region.hasSubregion(sub) || (region.hasNoSubregions() && sub == null)) {
            final LOTRMusicRegion.Sub key = region.getSubregion(sub);
            LOTRRegionTrackPool regionPool = LOTRMusic.regionTracks.get(key);
            if (regionPool == null) {
                regionPool = new LOTRRegionTrackPool(region, sub);
                LOTRMusic.regionTracks.put(key, regionPool);
            }
            return regionPool;
        }
        LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
        return null;
    }
    
    public static void addTrackToRegions(final LOTRMusicTrack track) {
        LOTRMusic.allTracks.add(track);
        for (final LOTRMusicRegion region : track.getAllRegions()) {
            if (region.hasNoSubregions()) {
                getTracksForRegion(region, null).addTrack(track);
            }
            else {
                for (final String sub : track.getRegionInfo(region).getSubregions()) {
                    getTracksForRegion(region, sub).addTrack(track);
                }
            }
        }
    }
    
    private static void loadMusicPacks(final File mcDir, final SimpleReloadableResourceManager resourceMgr) {
        LOTRMusic.musicDir = new File(mcDir, "lotrmusic");
        if (!LOTRMusic.musicDir.exists()) {
            LOTRMusic.musicDir.mkdirs();
        }
        LOTRMusic.allTracks.clear();
        LOTRMusic.regionTracks.clear();
        if (!LOTRMusic.initSubregions) {
            for (final LOTRDimension dim : LOTRDimension.values()) {
                for (final LOTRBiome biome : dim.biomeList) {
                    if (biome != null) {
                        biome.getBiomeMusic();
                    }
                }
            }
            LOTRMusic.initSubregions = true;
        }
        for (final File file : LOTRMusic.musicDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".zip")) {
                try {
                    final AbstractResourcePack resourcePack = (AbstractResourcePack)new FileResourcePack(file);
                    resourceMgr.reloadResourcePack((IResourcePack)resourcePack);
                    final ZipFile zipFile = new ZipFile(file);
                    loadMusicPack(zipFile, resourceMgr);
                }
                catch (Exception e) {
                    LOTRLog.logger.warn("LOTRMusic: Failed to load music pack " + file.getName() + "!");
                    e.printStackTrace();
                }
            }
        }
        try {
            generateReadme();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    private static void loadMusicPack(final ZipFile zip, final SimpleReloadableResourceManager resourceMgr) throws IOException {
        final ZipEntry entry = zip.getEntry("music.json");
        if (entry != null) {
            final InputStream stream = zip.getInputStream(entry);
            final JsonReader reader = new JsonReader((Reader)new InputStreamReader((InputStream)new BOMInputStream(stream), Charsets.UTF_8.name()));
            final JsonParser parser = new JsonParser();
            final List<LOTRMusicTrack> packTracks = new ArrayList<LOTRMusicTrack>();
            final JsonObject root = parser.parse(reader).getAsJsonObject();
            final JsonArray rootArray = root.get("tracks").getAsJsonArray();
            for (final JsonElement e : rootArray) {
                final JsonObject trackData = e.getAsJsonObject();
                final String filename = trackData.get("file").getAsString();
                final ZipEntry trackEntry = zip.getEntry("assets/lotrmusic/" + filename);
                if (trackEntry == null) {
                    LOTRLog.logger.warn("LOTRMusic: Track " + filename + " in pack " + zip.getName() + " does not exist!");
                }
                else {
                    final InputStream trackStream = zip.getInputStream(trackEntry);
                    final LOTRMusicTrack track = new LOTRMusicTrack(filename);
                    if (trackData.has("title")) {
                        final String title = trackData.get("title").getAsString();
                        track.setTitle(title);
                    }
                    final JsonArray regions = trackData.get("regions").getAsJsonArray();
                    for (final JsonElement r : regions) {
                        final JsonObject regionData = r.getAsJsonObject();
                        final String regionName = regionData.get("name").getAsString();
                        boolean allRegions = false;
                        LOTRMusicRegion region;
                        if (regionName.equalsIgnoreCase("all")) {
                            region = null;
                            allRegions = true;
                        }
                        else {
                            region = LOTRMusicRegion.forName(regionName);
                            if (region == null) {
                                LOTRLog.logger.warn("LOTRMusic: No region named " + regionName + "!");
                                continue;
                            }
                        }
                        final List<String> subregionNames = new ArrayList<String>();
                        if (region != null && regionData.has("sub")) {
                            final JsonArray subList = regionData.get("sub").getAsJsonArray();
                            for (final JsonElement s : subList) {
                                final String sub = s.getAsString();
                                if (region.hasSubregion(sub)) {
                                    subregionNames.add(sub);
                                }
                                else {
                                    LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
                                }
                            }
                        }
                        final List<LOTRMusicCategory> regionCategories = new ArrayList<LOTRMusicCategory>();
                        if (region != null && regionData.has("categories")) {
                            final JsonArray catList = regionData.get("categories").getAsJsonArray();
                            for (final JsonElement cat : catList) {
                                final String categoryName = cat.getAsString();
                                final LOTRMusicCategory category = LOTRMusicCategory.forName(categoryName);
                                if (category != null) {
                                    regionCategories.add(category);
                                }
                                else {
                                    LOTRLog.logger.warn("LOTRMusic: No category named " + categoryName + "!");
                                }
                            }
                        }
                        double weight = -1.0;
                        if (regionData.has("weight")) {
                            weight = regionData.get("weight").getAsDouble();
                        }
                        final List<LOTRMusicRegion> regionsAdd = new ArrayList<LOTRMusicRegion>();
                        if (allRegions) {
                            regionsAdd.addAll(Arrays.asList(LOTRMusicRegion.values()));
                        }
                        else {
                            regionsAdd.add(region);
                        }
                        for (final LOTRMusicRegion reg : regionsAdd) {
                            final LOTRTrackRegionInfo regInfo = track.createRegionInfo(reg);
                            if (weight >= 0.0) {
                                regInfo.setWeight(weight);
                            }
                            if (subregionNames.isEmpty()) {
                                regInfo.addAllSubregions();
                            }
                            else {
                                for (final String sub2 : subregionNames) {
                                    regInfo.addSubregion(sub2);
                                }
                            }
                            if (regionCategories.isEmpty()) {
                                regInfo.addAllCategories();
                            }
                            else {
                                for (final LOTRMusicCategory cat2 : regionCategories) {
                                    regInfo.addCategory(cat2);
                                }
                            }
                        }
                    }
                    if (trackData.has("authors")) {
                        final JsonArray authorList = trackData.get("authors").getAsJsonArray();
                        for (final JsonElement a : authorList) {
                            final String author = a.getAsString();
                            track.addAuthor(author);
                        }
                    }
                    track.loadTrack(trackStream);
                    packTracks.add(track);
                }
            }
            reader.close();
            LOTRLog.logger.info("LOTRMusic: Successfully loaded music pack " + zip.getName() + " with " + packTracks.size() + " tracks");
        }
    }
    
    private static void generateReadme() throws IOException {
        final File readme = new File(LOTRMusic.musicDir, "readme.txt");
        readme.createNewFile();
        final PrintStream writer = new PrintStream(new FileOutputStream(readme));
        final ResourceLocation template = new ResourceLocation("lotr:music/readme.txt");
        final InputStream templateIn = Minecraft.getMinecraft().getResourceManager().getResource(template).getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(templateIn), Charsets.UTF_8.name()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("#REGIONS#")) {
                writer.println("all");
                for (final LOTRMusicRegion region : LOTRMusicRegion.values()) {
                    String regionString = "";
                    regionString += region.regionName;
                    final List<String> subregions = region.getAllSubregions();
                    if (!subregions.isEmpty()) {
                        String subs = "";
                        for (final String s : subregions) {
                            if (subs.length() > 0) {
                                subs += ", ";
                            }
                            subs += s;
                        }
                        regionString = regionString + ": {" + subs + "}";
                    }
                    writer.println(regionString);
                }
            }
            else if (line.equals("#CATEGORIES#")) {
                for (final LOTRMusicCategory category : LOTRMusicCategory.values()) {
                    final String catString = category.categoryName;
                    writer.println(catString);
                }
            }
            else {
                writer.println(line);
            }
        }
        writer.close();
        reader.close();
    }
    
    static {
        trackResourceManager = new LOTRMusicResourceManager();
        LOTRMusic.allTracks = new ArrayList<LOTRMusicTrack>();
        LOTRMusic.regionTracks = new HashMap<LOTRMusicRegion.Sub, LOTRRegionTrackPool>();
        LOTRMusic.initSubregions = false;
        LOTRMusic.musicRand = new Random();
    }
    
    public static class Reflect
    {
        public static void putDomainResourceManager(final String domain, final IResourceManager manager) {
            final SimpleReloadableResourceManager masterManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
            try {
                final Map map = (Map)ObfuscationReflectionHelper.getPrivateValue((Class)SimpleReloadableResourceManager.class, (Object)masterManager, new String[] { "domainResourceManagers", "field_110548_a" });
                map.put(domain, manager);
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
            }
        }
        
        public static SoundRegistry getSoundRegistry() {
            final SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
            try {
                return (SoundRegistry)ObfuscationReflectionHelper.getPrivateValue((Class)SoundHandler.class, (Object)handler, new String[] { "sndRegistry", "field_147697_e" });
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
    }
}
