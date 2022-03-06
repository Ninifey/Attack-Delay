// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.sound;

import net.minecraft.block.Block;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import lotr.common.world.biome.LOTRBiomeGenFarHaradCoast;
import lotr.common.world.biome.LOTRBiomeGenLindonCoast;
import lotr.common.world.biome.LOTRBiomeGenBeach;
import lotr.common.world.biome.LOTRBiomeGenOcean;
import java.util.Collection;
import java.util.HashSet;
import lotr.common.world.biome.LOTRBiomeGenUtumno;
import lotr.common.world.biome.LOTRBiomeGenOldForest;
import lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted;
import lotr.common.world.biome.LOTRBiomeGenDeadMarshes;
import lotr.common.world.biome.LOTRBiomeGenDolGuldur;
import lotr.common.world.biome.LOTRBiomeGenBarrowDowns;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.PositionedSound;
import lotr.common.LOTRConfig;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.audio.ISound;
import java.util.List;

public class LOTRAmbience
{
    private int tallGrassCount;
    private int leafCount;
    private int ticksSinceWight;
    private List<ISound> playingWindSounds;
    private static final int maxWindSounds = 4;
    private List<ISound> playingSeaSounds;
    private static final int maxSeaSounds = 3;
    private ISound playingJazzMusic;
    private int jazzPlayerID;
    private static final ResourceLocation jazzMusicPath;
    private static final String jazzMusicTitle = "The Galadhon Groovers - Funky Villagers";
    
    public LOTRAmbience() {
        this.playingWindSounds = new ArrayList<ISound>();
        this.playingSeaSounds = new ArrayList<ISound>();
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onPlaySound(final PlaySoundEvent17 event) {
        final String name = event.name;
        final ISound sound = event.sound;
        if (LOTRConfig.newRain && sound instanceof PositionedSound) {
            final PositionedSound ps = (PositionedSound)sound;
            if (name.equals("ambient.weather.rain")) {
                event.result = (ISound)new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.rain"), ps.func_147653_e(), ps.func_147655_f(), ps.func_147649_g(), ps.func_147654_h(), ps.func_147651_i());
            }
            else if (name.equals("ambient.weather.thunder")) {
                event.result = (ISound)new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.thunder"), ps.func_147653_e(), ps.func_147655_f(), ps.func_147649_g(), ps.func_147654_h(), ps.func_147651_i());
            }
        }
        if (this.playingJazzMusic != null && event.category == SoundCategory.MUSIC) {
            event.result = null;
        }
    }
    
    public void updateAmbience(final World world, final EntityPlayer entityplayer) {
        world.theProfiler.startSection("lotrAmbience");
        final Minecraft mc = Minecraft.getMinecraft();
        final double x = ((Entity)entityplayer).posX;
        final double y = ((Entity)entityplayer).boundingBox.minY;
        final double z = ((Entity)entityplayer).posZ;
        final int i = MathHelper.floor_double(x);
        final int j = MathHelper.floor_double(y);
        final int k = MathHelper.floor_double(z);
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        final Random rand = world.rand;
        if (this.ticksSinceWight > 0) {
            --this.ticksSinceWight;
        }
        else {
            final boolean wights = (LOTRTickHandlerClient.anyWightsViewed && rand.nextInt(20) == 0) || (biome instanceof LOTRBiomeGenBarrowDowns && rand.nextInt(3000) == 0);
            if (wights) {
                world.playSound(x, y, z, "lotr:wight.ambience", 1.0f, 0.8f + rand.nextFloat() * 0.4f, false);
                this.ticksSinceWight = 300;
            }
        }
        boolean spookyBiomeNoise = false;
        float spookyPitch = 1.0f;
        if (biome instanceof LOTRBiomeGenDolGuldur) {
            spookyBiomeNoise = (rand.nextInt(1000) == 0);
            spookyPitch = 0.85f;
        }
        else if (biome instanceof LOTRBiomeGenDeadMarshes) {
            spookyBiomeNoise = (rand.nextInt(2400) == 0);
        }
        else if (biome instanceof LOTRBiomeGenMirkwoodCorrupted) {
            spookyBiomeNoise = (rand.nextInt(3000) == 0);
        }
        else if (biome instanceof LOTRBiomeGenOldForest) {
            spookyBiomeNoise = (rand.nextInt(6000) == 0);
        }
        else if (biome instanceof LOTRBiomeGenUtumno) {
            spookyBiomeNoise = (rand.nextInt(1000) == 0);
            spookyPitch = 0.75f;
        }
        if (spookyBiomeNoise) {
            world.playSound(x, y, z, "lotr:wight.ambience", 1.0f, (0.8f + rand.nextFloat() * 0.4f) * spookyPitch, false);
        }
        if (biome instanceof LOTRBiomeGenUtumno && world.rand.nextInt(500) == 0) {
            world.playSound(x, y, z, "ambient.cave.cave", 1.0f, 0.8f + rand.nextFloat() * 0.2f, false);
        }
        if (this.playingWindSounds.size() < 4) {
            final int xzRange = 16;
            int minWindHeight = 100;
            int fullWindHeight = 180;
            if (rand.nextInt(20) == 0) {
                minWindHeight -= 10;
                if (rand.nextInt(10) == 0) {
                    minWindHeight -= 10;
                }
            }
            if (world.isRaining()) {
                minWindHeight = 80;
                fullWindHeight = 120;
                if (rand.nextInt(20) == 0) {
                    minWindHeight -= 20;
                }
            }
            for (int l = 0; l < 2; ++l) {
                final int i2 = i + MathHelper.getRandomIntegerInRange(rand, -xzRange, xzRange);
                final int k2 = k + MathHelper.getRandomIntegerInRange(rand, -xzRange, xzRange);
                final int j2 = j + MathHelper.getRandomIntegerInRange(rand, -16, 16);
                if (j2 >= minWindHeight && world.canBlockSeeTheSky(i2, j2, k2)) {
                    float windiness = (j2 - minWindHeight) / (float)(fullWindHeight - minWindHeight);
                    windiness = MathHelper.clamp_float(windiness, 0.0f, 1.0f);
                    if (windiness >= rand.nextFloat()) {
                        final float x2 = i2 + 0.5f;
                        final float y2 = j2 + 0.5f;
                        final float z2 = k2 + 0.5f;
                        final float vol = 1.0f * Math.max(0.25f, windiness);
                        final float pitch = 0.8f + rand.nextFloat() * 0.4f;
                        final ISound wind = (ISound)new AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.weather.wind"), vol, pitch, x2, y2, z2).calcAmbientVolume(entityplayer, xzRange);
                        mc.getSoundHandler().playSound(wind);
                        this.playingWindSounds.add(wind);
                        break;
                    }
                }
            }
        }
        else {
            final Set<ISound> removes = new HashSet<ISound>();
            for (final ISound wind2 : this.playingWindSounds) {
                if (!mc.getSoundHandler().func_147692_c(wind2)) {
                    removes.add(wind2);
                }
            }
            this.playingWindSounds.removeAll(removes);
        }
        if (this.playingSeaSounds.size() < 3) {
            if (biome instanceof LOTRBiomeGenOcean || biome instanceof LOTRBiomeGenBeach || biome instanceof LOTRBiomeGenLindonCoast || biome instanceof LOTRBiomeGenFarHaradCoast) {
                final int xzRange = 64;
                final float[] array;
                final float[] rangeChecks = array = new float[] { 0.25f, 0.5f, 0.75f, 1.0f };
            Label_1402:
                for (final float fr : array) {
                    final int range = (int)(xzRange * fr);
                    int m = 0;
                    while (m < 8) {
                        final int i3 = i + MathHelper.getRandomIntegerInRange(rand, -range, range);
                        final int k3 = k + MathHelper.getRandomIntegerInRange(rand, -range, range);
                        final int j3 = j + MathHelper.getRandomIntegerInRange(rand, -16, 8);
                        final Block block = world.getBlock(i3, j3, k3);
                        if (block.getMaterial() == Material.water && j3 >= world.getTopSolidOrLiquidBlock(i3, k3)) {
                            final float x3 = i3 + 0.5f;
                            final float y3 = j3 + 0.5f;
                            final float z3 = k3 + 0.5f;
                            final float vol2 = 1.0f;
                            final float pitch2 = 0.8f + rand.nextFloat() * 0.4f;
                            final ISound sea = (ISound)new AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.terrain.sea"), vol2, pitch2, x3, y3, z3).calcAmbientVolume(entityplayer, xzRange);
                            mc.getSoundHandler().playSound(sea);
                            this.playingSeaSounds.add(sea);
                            final int j4 = world.getHeightValue(i3, k3) - 1;
                            if (world.getBlock(i3, j4, k3).getMaterial() == Material.water) {
                                final double dx = i3 + 0.5 - ((Entity)entityplayer).posX;
                                final double dz = k3 + 0.5 - ((Entity)entityplayer).posZ;
                                final float angle = (float)Math.atan2(dz, dx);
                                final float cos = MathHelper.cos(angle);
                                final float sin = MathHelper.sin(angle);
                                final float angle2 = angle + (float)Math.toRadians(-90.0);
                                final float cos2 = MathHelper.cos(angle2);
                                final float sin2 = MathHelper.sin(angle2);
                                final float waveSpeed = MathHelper.randomFloatClamp(rand, 0.3f, 0.5f);
                                for (int waveR = 40 + rand.nextInt(100), w = -waveR; w <= waveR; ++w) {
                                    final float f = w / 8.0f;
                                    double d0 = i3 + 0.5;
                                    final double d2 = j4 + 1.0 + MathHelper.randomFloatClamp(rand, 0.02f, 0.1f);
                                    double d3 = k3 + 0.5;
                                    d0 += f * cos2;
                                    d3 += f * sin2;
                                    if (world.getBlock(MathHelper.floor_double(d0), MathHelper.floor_double(d2) - 1, MathHelper.floor_double(d3)).getMaterial() == Material.water) {
                                        final double d4 = waveSpeed * -cos;
                                        final double d5 = 0.0;
                                        final double d6 = waveSpeed * -sin;
                                        LOTRMod.proxy.spawnParticle("wave", d0, d2, d3, d4, d5, d6);
                                    }
                                }
                                break Label_1402;
                            }
                            break Label_1402;
                        }
                        else {
                            ++m;
                        }
                    }
                }
            }
        }
        else {
            final Set<ISound> removes = new HashSet<ISound>();
            for (final ISound sea2 : this.playingSeaSounds) {
                if (!mc.getSoundHandler().func_147692_c(sea2)) {
                    removes.add(sea2);
                }
            }
            this.playingSeaSounds.removeAll(removes);
        }
        if (this.playingJazzMusic == null) {
            if (((Entity)entityplayer).ticksExisted % 20 == 0) {
                final double range2 = 16.0;
                final List elves = world.getEntitiesWithinAABB((Class)LOTREntityElf.class, ((Entity)entityplayer).boundingBox.expand(range2, range2, range2));
                LOTREntityElf playingElf = null;
                for (final Object obj : elves) {
                    final LOTREntityElf elf = (LOTREntityElf)obj;
                    if (elf.isEntityAlive() && elf.isJazz() && elf.isSolo()) {
                        playingElf = elf;
                        break;
                    }
                }
                if (playingElf != null) {
                    mc.getSoundHandler().func_147690_c();
                    this.jazzPlayerID = playingElf.getEntityId();
                    final ISound music = this.getJazzMusic((Entity)playingElf);
                    mc.getSoundHandler().playSound(music);
                    this.playingJazzMusic = music;
                    mc.ingameGUI.setRecordPlayingMessage("The Galadhon Groovers - Funky Villagers");
                }
            }
        }
        else {
            final Entity player = world.getEntityByID(this.jazzPlayerID);
            if (player == null || !player.isEntityAlive()) {
                mc.getSoundHandler().func_147683_b(this.playingJazzMusic);
                this.playingJazzMusic = null;
            }
            if (!mc.getSoundHandler().func_147692_c(this.playingJazzMusic)) {
                this.playingJazzMusic = null;
            }
        }
        world.theProfiler.endSection();
    }
    
    private ISound getJazzMusic(final Entity entity) {
        return (ISound)PositionedSoundRecord.func_147675_a(LOTRAmbience.jazzMusicPath, (float)entity.posX, (float)entity.posY, (float)entity.posZ);
    }
    
    static {
        jazzMusicPath = new ResourceLocation("lotr:music.jazzelf");
    }
    
    private static class AmbientSoundNoAttentuation extends PositionedSoundRecord
    {
        public AmbientSoundNoAttentuation(final ResourceLocation sound, final float vol, final float pitch, final float x, final float y, final float z) {
            super(sound, vol, pitch, x, y, z);
            ((PositionedSound)this).field_147666_i = ISound.AttenuationType.NONE;
        }
        
        public AmbientSoundNoAttentuation calcAmbientVolume(final EntityPlayer entityplayer, final int maxRange) {
            float distFr = (float)entityplayer.getDistance((double)((PositionedSound)this).field_147660_d, (double)((PositionedSound)this).field_147661_e, (double)((PositionedSound)this).field_147658_f);
            distFr /= maxRange;
            distFr = Math.min(distFr, 1.0f);
            distFr = 1.0f - distFr;
            distFr *= 1.5f;
            distFr = MathHelper.clamp_float(distFr, 0.1f, 1.0f);
            ((PositionedSound)this).field_147662_b *= distFr;
            return this;
        }
    }
}
