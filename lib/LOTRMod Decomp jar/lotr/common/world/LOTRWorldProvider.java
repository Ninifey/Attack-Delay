// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import lotr.client.render.LOTRCloudRenderer;
import lotr.client.render.LOTRSkyRenderer;
import lotr.common.LOTRConfig;
import lotr.compatibility.LOTRModChecker;
import com.google.common.math.IntMath;
import lotr.common.LOTRDate;
import lotr.common.LOTRTime;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import lotr.common.world.biome.LOTRBiomeGenTundra;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenOcean;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.StatCollector;
import lotr.common.LOTRDimension;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.IRenderHandler;
import net.minecraft.world.WorldProvider;

public abstract class LOTRWorldProvider extends WorldProvider
{
    public static int MOON_PHASES;
    @SideOnly(Side.CLIENT)
    private IRenderHandler lotrSkyRenderer;
    @SideOnly(Side.CLIENT)
    private IRenderHandler lotrCloudRenderer;
    private boolean spawnHostiles;
    private boolean spawnPeacefuls;
    private double cloudsR;
    private double cloudsG;
    private double cloudsB;
    private double fogR;
    private double fogG;
    private double fogB;
    
    public LOTRWorldProvider() {
        this.spawnHostiles = true;
        this.spawnPeacefuls = true;
    }
    
    public abstract LOTRDimension getLOTRDimension();
    
    public void registerWorldChunkManager() {
        super.worldChunkMgr = new LOTRWorldChunkManager(super.worldObj, this.getLOTRDimension());
        super.dimensionId = this.getLOTRDimension().dimensionID;
    }
    
    public String getWelcomeMessage() {
        return StatCollector.translateToLocalFormatted("lotr.dimension.enter", new Object[] { this.getLOTRDimension().getDimensionName() });
    }
    
    public String getDepartMessage() {
        return StatCollector.translateToLocalFormatted("lotr.dimension.exit", new Object[] { this.getLOTRDimension().getDimensionName() });
    }
    
    public String getSaveFolder() {
        return this.getLOTRDimension().dimensionName;
    }
    
    public String getDimensionName() {
        return this.getLOTRDimension().dimensionName;
    }
    
    public boolean canRespawnHere() {
        return true;
    }
    
    public BiomeGenBase getBiomeGenForCoords(final int i, final int k) {
        if (super.worldObj.blockExists(i, 0, k)) {
            final Chunk chunk = super.worldObj.getChunkFromBlockCoords(i, k);
            if (chunk != null) {
                final int chunkX = i & 0xF;
                final int chunkZ = k & 0xF;
                int biomeID = chunk.getBiomeArray()[chunkZ << 4 | chunkX] & 0xFF;
                if (biomeID == 255) {
                    final BiomeGenBase biomegenbase = super.worldChunkMgr.getBiomeGenAt((chunk.xPosition << 4) + chunkX, (chunk.zPosition << 4) + chunkZ);
                    biomeID = biomegenbase.biomeID;
                    chunk.getBiomeArray()[chunkZ << 4 | chunkX] = (byte)(biomeID & 0xFF);
                }
                final LOTRDimension dim = this.getLOTRDimension();
                return (dim.biomeList[biomeID] == null) ? dim.biomeList[0] : dim.biomeList[biomeID];
            }
        }
        return super.worldChunkMgr.getBiomeGenAt(i, k);
    }
    
    public boolean canBlockFreeze(final int i, final int j, final int k, final boolean isBlockUpdate) {
        final BiomeGenBase biome = super.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenOcean) {
            return LOTRBiomeGenOcean.isFrozen(i, k) && this.canFreeze_ignoreTemp(i, j, k, isBlockUpdate);
        }
        if (biome instanceof LOTRBiome) {
            return super.worldObj.canBlockFreezeBody(i, j, k, isBlockUpdate);
        }
        return super.worldObj.canBlockFreezeBody(i, j, k, isBlockUpdate);
    }
    
    public boolean canSnowAt(final int i, final int j, final int k, final boolean checkLight) {
        final BiomeGenBase biome = super.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenOcean) {
            return LOTRBiomeGenOcean.isFrozen(i, k) && this.canSnow_ignoreTemp(i, j, k, checkLight);
        }
        if (biome instanceof LOTRBiomeGenTundra) {
            final boolean flag = super.worldObj.canSnowAtBody(i, j, k, checkLight);
            return flag && LOTRBiomeGenTundra.isTundraSnowy(i, k);
        }
        if (biome instanceof LOTRBiome) {
            return j >= ((LOTRBiome)biome).getSnowHeight() && super.worldObj.canSnowAtBody(i, j, k, checkLight);
        }
        return super.worldObj.canSnowAtBody(i, j, k, checkLight);
    }
    
    private boolean canFreeze_ignoreTemp(final int i, final int j, final int k, final boolean isBlockUpdate) {
        if (j >= 0 && j < super.worldObj.getHeight() && super.worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10) {
            final Block block = super.worldObj.getBlock(i, j, k);
            if ((block == Blocks.water || block == Blocks.flowing_water) && super.worldObj.getBlockMetadata(i, j, k) == 0) {
                if (!isBlockUpdate) {
                    return true;
                }
                boolean surroundWater = true;
                if (surroundWater && super.worldObj.getBlock(i - 1, j, k).getMaterial() != Material.water) {
                    surroundWater = false;
                }
                if (surroundWater && super.worldObj.getBlock(i + 1, j, k).getMaterial() != Material.water) {
                    surroundWater = false;
                }
                if (surroundWater && super.worldObj.getBlock(i, j, k - 1).getMaterial() != Material.water) {
                    surroundWater = false;
                }
                if (surroundWater && super.worldObj.getBlock(i, j, k + 1).getMaterial() != Material.water) {
                    surroundWater = false;
                }
                if (!surroundWater) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean canSnow_ignoreTemp(final int i, final int j, final int k, final boolean checkLight) {
        if (!checkLight) {
            return true;
        }
        if (j >= 0 && j < super.worldObj.getHeight() && super.worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10) {
            final Block block = super.worldObj.getBlock(i, j, k);
            if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(super.worldObj, i, j, k)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean shouldMapSpin(final String entity, final double x, final double y, final double z) {
        return false;
    }
    
    public void resetRainAndThunder() {
        super.resetRainAndThunder();
        if (LOTRMod.doDayCycle(super.worldObj)) {
            LOTRTime.advanceToMorning();
        }
    }
    
    public float calculateCelestialAngle(final long time, final float partialTick) {
        float daytime = ((int)(time % LOTRTime.DAY_LENGTH) + partialTick) / LOTRTime.DAY_LENGTH - 0.25f;
        if (daytime < 0.0f) {
            ++daytime;
        }
        if (daytime > 1.0f) {
            --daytime;
        }
        float angle = 1.0f - (float)((Math.cos(daytime * 3.141592653589793) + 1.0) / 2.0);
        angle = daytime + (angle - daytime) / 3.0f;
        return angle;
    }
    
    public int getMoonPhase(final long time) {
        return getLOTRMoonPhase();
    }
    
    public static int getLOTRMoonPhase() {
        final int day = LOTRDate.ShireReckoning.currentDay;
        return IntMath.mod(day, LOTRWorldProvider.MOON_PHASES);
    }
    
    public static boolean isLunarEclipse() {
        final int day = LOTRDate.ShireReckoning.currentDay;
        return getLOTRMoonPhase() == 0 && IntMath.mod(day / LOTRWorldProvider.MOON_PHASES, 4) == 3;
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        if (!LOTRModChecker.hasShaders() && LOTRConfig.enableLOTRSky) {
            if (this.lotrSkyRenderer == null) {
                this.lotrSkyRenderer = new LOTRSkyRenderer(this);
            }
            return this.lotrSkyRenderer;
        }
        return super.getSkyRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer() {
        if (!LOTRModChecker.hasShaders() && LOTRConfig.cloudRange > 0) {
            if (this.lotrCloudRenderer == null) {
                this.lotrCloudRenderer = new LOTRCloudRenderer();
            }
            return this.lotrCloudRenderer;
        }
        return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 192.0f;
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 drawClouds(final float f) {
        final Minecraft mc = Minecraft.getMinecraft();
        final int i = (int)((Entity)mc.renderViewEntity).posX;
        final int k = (int)((Entity)mc.renderViewEntity).posZ;
        final Vec3 clouds = super.drawClouds(f);
        final double cloudsR = 0.0;
        this.cloudsB = cloudsR;
        this.cloudsG = cloudsR;
        this.cloudsR = cloudsR;
        final GameSettings settings = mc.gameSettings;
        final int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        int l = 0;
        for (int i2 = -distance; i2 <= distance; ++i2) {
            for (int k2 = -distance; k2 <= distance; ++k2) {
                final Vec3 tempClouds = Vec3.createVectorHelper(clouds.xCoord, clouds.yCoord, clouds.zCoord);
                final BiomeGenBase biome = super.worldObj.getBiomeGenForCoords(i + i2, k + k2);
                if (biome instanceof LOTRBiome) {
                    ((LOTRBiome)biome).getCloudColor(tempClouds);
                }
                this.cloudsR += tempClouds.xCoord;
                this.cloudsG += tempClouds.yCoord;
                this.cloudsB += tempClouds.zCoord;
                ++l;
            }
        }
        this.cloudsR /= l;
        this.cloudsG /= l;
        this.cloudsB /= l;
        return Vec3.createVectorHelper(this.cloudsR, this.cloudsG, this.cloudsB);
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(final float f, final float f1) {
        final Minecraft mc = Minecraft.getMinecraft();
        final int i = (int)((Entity)mc.renderViewEntity).posX;
        final int k = (int)((Entity)mc.renderViewEntity).posZ;
        final Vec3 fog = super.getFogColor(f, f1);
        final double fogR = 0.0;
        this.fogB = fogR;
        this.fogG = fogR;
        this.fogR = fogR;
        final GameSettings settings = mc.gameSettings;
        final int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        int l = 0;
        for (int i2 = -distance; i2 <= distance; ++i2) {
            for (int k2 = -distance; k2 <= distance; ++k2) {
                final Vec3 tempFog = Vec3.createVectorHelper(fog.xCoord, fog.yCoord, fog.zCoord);
                final BiomeGenBase biome = super.worldObj.getBiomeGenForCoords(i + i2, k + k2);
                if (biome instanceof LOTRBiome) {
                    ((LOTRBiome)biome).getFogColor(tempFog);
                }
                this.fogR += tempFog.xCoord;
                this.fogG += tempFog.yCoord;
                this.fogB += tempFog.zCoord;
                ++l;
            }
        }
        this.fogR /= l;
        this.fogG /= l;
        this.fogB /= l;
        return Vec3.createVectorHelper(this.fogR, this.fogG, this.fogB);
    }
    
    public float[] modifyFogIntensity(final float farPlane, final int fogMode) {
        final Minecraft mc = Minecraft.getMinecraft();
        final int i = (int)((Entity)mc.renderViewEntity).posX;
        final int k = (int)((Entity)mc.renderViewEntity).posZ;
        float fogStart = 0.0f;
        float fogEnd = 0.0f;
        final GameSettings settings = mc.gameSettings;
        final int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        int l = 0;
        for (int i2 = -distance; i2 <= distance; ++i2) {
            for (int k2 = -distance; k2 <= distance; ++k2) {
                float thisFogStart = 0.0f;
                float thisFogEnd = 0.0f;
                final boolean foggy = this.doesXZShowFog(i + i2, k + k2);
                if (foggy) {
                    thisFogStart = farPlane * 0.05f;
                    thisFogEnd = Math.min(farPlane, 192.0f) * 0.5f;
                }
                else if (fogMode < 0) {
                    thisFogStart = 0.0f;
                    thisFogEnd = farPlane;
                }
                else {
                    thisFogStart = farPlane * 0.75f;
                    thisFogEnd = farPlane;
                }
                fogStart += thisFogStart;
                fogEnd += thisFogEnd;
                ++l;
            }
        }
        fogStart /= l;
        fogEnd /= l;
        return new float[] { fogStart, fogEnd };
    }
    
    public float[] handleFinalFogColors(final EntityLivingBase viewer, final double tick, final float[] rgb) {
        return rgb;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(final int i, final int k) {
        final BiomeGenBase biome = super.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome) {
            return ((LOTRBiome)biome).hasFog();
        }
        return super.doesXZShowFog(i, k);
    }
    
    static {
        LOTRWorldProvider.MOON_PHASES = 8;
    }
}
