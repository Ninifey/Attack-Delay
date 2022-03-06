// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import net.minecraft.entity.Entity;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import lotr.common.world.biome.LOTRBiome;
import lotr.client.LOTRClientProxy;
import lotr.common.world.biome.LOTRMusicRegion;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRConfig;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.Minecraft;
import java.util.Random;

public class LOTRMusicTicker
{
    public static LOTRMusicTrack currentTrack;
    private static boolean wasPlayingMenu;
    private static final int firstTiming = 100;
    private static int timing;
    private static final int nullTrackResetTiming = 400;
    
    public static void update(final Random rand) {
        final Minecraft mc = Minecraft.getMinecraft();
        final boolean noMusic = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC) <= 0.0f;
        final boolean menu = LOTRMusic.isMenuMusic();
        if (LOTRMusicTicker.wasPlayingMenu != menu) {
            if (LOTRMusicTicker.currentTrack != null) {
                mc.getSoundHandler().func_147683_b((ISound)LOTRMusicTicker.currentTrack);
                LOTRMusicTicker.currentTrack = null;
            }
            LOTRMusicTicker.wasPlayingMenu = menu;
            LOTRMusicTicker.timing = 100;
        }
        if (LOTRMusicTicker.currentTrack != null) {
            if (noMusic) {
                mc.getSoundHandler().func_147683_b((ISound)LOTRMusicTicker.currentTrack);
            }
            if (!mc.getSoundHandler().func_147692_c((ISound)LOTRMusicTicker.currentTrack)) {
                LOTRMusicTicker.currentTrack = null;
                resetTiming(rand);
            }
        }
        if (!noMusic) {
            boolean update = false;
            update = (menu || (LOTRMusic.isLOTRDimension() && !Minecraft.getMinecraft().func_147113_T()));
            if (update) {
                --LOTRMusicTicker.timing;
                if (LOTRMusicTicker.currentTrack == null && LOTRMusicTicker.timing <= 0) {
                    LOTRMusicTicker.currentTrack = getNewTrack(mc, rand);
                    if (LOTRMusicTicker.currentTrack != null) {
                        LOTRMusicTicker.wasPlayingMenu = menu;
                        mc.getSoundHandler().playSound((ISound)LOTRMusicTicker.currentTrack);
                        LOTRMusicTicker.timing = Integer.MAX_VALUE;
                    }
                    else {
                        LOTRMusicTicker.timing = 400;
                    }
                }
            }
        }
    }
    
    private static void resetTiming(final Random rand) {
        if (LOTRMusic.isMenuMusic()) {
            LOTRMusicTicker.timing = MathHelper.getRandomIntegerInRange(rand, LOTRConfig.musicIntervalMenuMin * 20, LOTRConfig.musicIntervalMenuMax * 20);
        }
        else {
            LOTRMusicTicker.timing = MathHelper.getRandomIntegerInRange(rand, LOTRConfig.musicIntervalMin * 20, LOTRConfig.musicIntervalMax * 20);
        }
    }
    
    private static LOTRMusicTrack getNewTrack(final Minecraft mc, final Random rand) {
        final LOTRMusicRegion.Sub regionSub = getCurrentRegion(mc, rand);
        final LOTRMusicCategory category = getCurrentCategory(mc, rand);
        if (regionSub != null) {
            final LOTRMusicRegion region = regionSub.region;
            final String sub = regionSub.subregion;
            LOTRTrackSorter.Filter filter;
            if (category != null) {
                filter = LOTRTrackSorter.forRegionAndCategory(region, category);
            }
            else {
                filter = LOTRTrackSorter.forAny();
            }
            final LOTRRegionTrackPool trackPool = LOTRMusic.getTracksForRegion(region, sub);
            return trackPool.getRandomTrack(rand, filter);
        }
        return null;
    }
    
    private static LOTRMusicRegion.Sub getCurrentRegion(final Minecraft mc, final Random rand) {
        final World world = (World)mc.theWorld;
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        if (LOTRMusic.isMenuMusic()) {
            return LOTRMusicRegion.MENU.getWithoutSub();
        }
        if (LOTRMusic.isLOTRDimension()) {
            final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
            final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
            if (LOTRClientProxy.doesClientChunkExist(world, i, k)) {
                final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                if (biome instanceof LOTRBiome) {
                    final LOTRBiome lotrbiome = (LOTRBiome)biome;
                    final LOTRMusicRegion.Sub region = lotrbiome.getBiomeMusic();
                    return region;
                }
            }
        }
        return null;
    }
    
    private static LOTRMusicCategory getCurrentCategory(final Minecraft mc, final Random rand) {
        final World world = (World)mc.theWorld;
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        if (world == null || entityplayer == null) {
            return null;
        }
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int j = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        if (LOTRMusicCategory.isCave(world, i, j, k)) {
            return LOTRMusicCategory.CAVE;
        }
        if (LOTRMusicCategory.isDay(world)) {
            return LOTRMusicCategory.DAY;
        }
        return LOTRMusicCategory.NIGHT;
    }
    
    static {
        LOTRMusicTicker.wasPlayingMenu = true;
        LOTRMusicTicker.timing = 100;
    }
}
