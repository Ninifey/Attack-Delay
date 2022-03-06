// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import com.google.common.math.IntMath;
import java.awt.image.RenderedImage;
import java.io.IOException;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.WorldType;
import net.minecraft.world.World;
import java.util.Enumeration;
import cpw.mods.fml.common.ModContainer;
import java.awt.image.BufferedImage;
import lotr.common.world.biome.LOTRBiome;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import lotr.common.LOTRDimension;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRMod;

public class LOTRGenLayerWorld extends LOTRGenLayer
{
    public static final int scalePower = 7;
    private static byte[] biomeImageData;
    public static final int originX = 810;
    public static final int originZ = 730;
    public static final int scale;
    public static int imageWidth;
    public static int imageHeight;
    
    public LOTRGenLayerWorld() {
        super(0L);
        if (!loadedBiomeImage()) {
            try {
                BufferedImage biomeImage = null;
                final String imageName = "assets/lotr/map/map.png";
                final ModContainer mc = LOTRMod.getModContainer();
                if (mc.getSource().isFile()) {
                    final ZipFile zip = new ZipFile(mc.getSource());
                    final Enumeration entries = zip.entries();
                    while (entries.hasMoreElements()) {
                        final ZipEntry entry = entries.nextElement();
                        if (entry.getName().equals(imageName)) {
                            biomeImage = ImageIO.read(zip.getInputStream(entry));
                        }
                    }
                    zip.close();
                }
                else {
                    final File file = new File(LOTRMod.class.getResource("/" + imageName).toURI());
                    biomeImage = ImageIO.read(new FileInputStream(file));
                }
                if (biomeImage == null) {
                    throw new RuntimeException("Could not load LOTR biome map image");
                }
                LOTRGenLayerWorld.imageWidth = biomeImage.getWidth();
                LOTRGenLayerWorld.imageHeight = biomeImage.getHeight();
                final int[] colors = biomeImage.getRGB(0, 0, LOTRGenLayerWorld.imageWidth, LOTRGenLayerWorld.imageHeight, null, 0, LOTRGenLayerWorld.imageWidth);
                LOTRGenLayerWorld.biomeImageData = new byte[LOTRGenLayerWorld.imageWidth * LOTRGenLayerWorld.imageHeight];
                for (int i = 0; i < colors.length; ++i) {
                    final int color = colors[i];
                    final Integer biomeID = LOTRDimension.MIDDLE_EARTH.colorsToBiomeIDs.get(color);
                    if (biomeID != null) {
                        LOTRGenLayerWorld.biomeImageData[i] = (byte)(int)biomeID;
                    }
                    else {
                        FMLLog.log(Level.ERROR, "Found unknown biome on map " + Integer.toHexString(color), new Object[0]);
                        LOTRGenLayerWorld.biomeImageData[i] = (byte)LOTRBiome.ocean.biomeID;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] intArray = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                final int i3 = i + i2 + 810;
                final int k3 = k + k2 + 730;
                if (i3 < 0 || i3 >= LOTRGenLayerWorld.imageWidth || k3 < 0 || k3 >= LOTRGenLayerWorld.imageHeight) {
                    intArray[i2 + k2 * xSize] = LOTRBiome.ocean.biomeID;
                }
                else {
                    intArray[i2 + k2 * xSize] = getBiomeImageID(i3, k3);
                }
            }
        }
        return intArray;
    }
    
    public static int getBiomeImageID(final int x, final int z) {
        final int index = z * LOTRGenLayerWorld.imageWidth + x;
        return LOTRGenLayerWorld.biomeImageData[index] & 0xFF;
    }
    
    public static LOTRBiome getBiomeOrOcean(final int mapX, final int mapZ) {
        int biomeID;
        if (mapX >= 0 && mapX < LOTRGenLayerWorld.imageWidth && mapZ >= 0 && mapZ < LOTRGenLayerWorld.imageHeight) {
            biomeID = getBiomeImageID(mapX, mapZ);
        }
        else {
            biomeID = LOTRBiome.ocean.biomeID;
        }
        return LOTRDimension.MIDDLE_EARTH.biomeList[biomeID];
    }
    
    public static boolean loadedBiomeImage() {
        return LOTRGenLayerWorld.biomeImageData != null;
    }
    
    public static LOTRGenLayer[] createWorld(final LOTRDimension dim, final WorldType worldType) {
        if (dim == LOTRDimension.UTUMNO) {
            final LOTRGenLayer biomes = new LOTRGenLayerBiome(LOTRBiome.utumno);
            final LOTRGenLayer variants = new LOTRGenLayerBiomeVariants(300L);
            return new LOTRGenLayer[] { biomes, variants, variants, variants, variants };
        }
        LOTRGenLayer rivers = new LOTRGenLayerRiverInit(100L);
        rivers = LOTRGenLayerZoom.magnify(1000L, rivers, 10);
        rivers = new LOTRGenLayerRiver(1L, rivers);
        rivers = new LOTRGenLayerSmooth(1000L, rivers);
        rivers = LOTRGenLayerZoom.magnify(1000L, rivers, 1);
        LOTRGenLayer biomeSubtypes = new LOTRGenLayerBiomeSubtypesInit(3000L);
        biomeSubtypes = LOTRGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
        LOTRGenLayer biomes2 = new LOTRGenLayerWorld();
        if (worldType == LOTRMod.worldTypeMiddleEarthClassic) {
            LOTRGenLayer oceans = new LOTRGenLayerClassicOcean(2012L);
            oceans = LOTRGenLayerZoom.magnify(200L, oceans, 3);
            oceans = new LOTRGenLayerClassicRemoveOcean(400L, oceans);
            biomes2 = new LOTRGenLayerClassicBiomes(2013L, oceans, dim);
            biomes2 = LOTRGenLayerZoom.magnify(300L, biomes2, 2);
        }
        LOTRGenLayer mapRivers = new LOTRGenLayerExtractMapRivers(5000L, biomes2);
        biomes2 = new LOTRGenLayerRemoveMapRivers(1000L, biomes2, dim);
        biomes2 = new LOTRGenLayerBiomeSubtypes(1000L, biomes2, biomeSubtypes);
        biomes2 = new LOTRGenLayerNearHaradRiverbanks(200L, biomes2, mapRivers, dim);
        biomes2 = new LOTRGenLayerNearHaradOasis(500L, biomes2, dim);
        biomes2 = LOTRGenLayerZoom.magnify(1000L, biomes2, 1);
        biomes2 = new LOTRGenLayerBeach(1000L, biomes2, dim, LOTRBiome.ocean);
        biomes2 = LOTRGenLayerZoom.magnify(1000L, biomes2, 2);
        biomes2 = new LOTRGenLayerOasisLake(600L, biomes2, dim);
        biomes2 = LOTRGenLayerZoom.magnify(1000L, biomes2, 2);
        biomes2 = new LOTRGenLayerSmooth(1000L, biomes2);
        LOTRGenLayer variants2 = new LOTRGenLayerBiomeVariants(200L);
        variants2 = LOTRGenLayerZoom.magnify(200L, variants2, 8);
        LOTRGenLayer variantsSmall = new LOTRGenLayerBiomeVariants(300L);
        variantsSmall = LOTRGenLayerZoom.magnify(300L, variantsSmall, 6);
        LOTRGenLayer lakes = new LOTRGenLayerBiomeVariantsLake(100L, null, 0).setLakeFlags(1);
        for (int i = 1; i <= 5; ++i) {
            lakes = new LOTRGenLayerZoom(200L + i, lakes);
            if (i <= 2) {
                lakes = new LOTRGenLayerBiomeVariantsLake(300L + i, lakes, i).setLakeFlags(1);
            }
            if (i == 3) {
                lakes = new LOTRGenLayerBiomeVariantsLake(500L, lakes, i).setLakeFlags(2, 4);
            }
        }
        for (int i = 0; i < 4; ++i) {
            mapRivers = new LOTRGenLayerMapRiverZoom(4000L + i, mapRivers);
        }
        mapRivers = new LOTRGenLayerNarrowRivers(3000L, mapRivers, 6);
        mapRivers = LOTRGenLayerZoom.magnify(4000L, mapRivers, 1);
        rivers = new LOTRGenLayerIncludeMapRivers(5000L, rivers, mapRivers);
        return new LOTRGenLayer[] { biomes2, variants2, variantsSmall, lakes, rivers };
    }
    
    public static void printRiverlessMap(final World world, final File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        final LOTRDimension dim = LOTRDimension.MIDDLE_EARTH;
        LOTRGenLayer biomeSubtypes = new LOTRGenLayerBiomeSubtypesInit(3000L);
        biomeSubtypes = LOTRGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
        LOTRGenLayer biomes = new LOTRGenLayerWorld();
        biomes = new LOTRGenLayerRemoveMapRivers(1000L, biomes, dim);
        biomes = new LOTRGenLayerBiomeSubtypes(1000L, biomes, biomeSubtypes);
        final BufferedImage buf = new BufferedImage(LOTRGenLayerWorld.imageWidth, LOTRGenLayerWorld.imageHeight, 2);
        for (int i = 0; i < LOTRGenLayerWorld.imageWidth; ++i) {
            for (int k = 0; k < LOTRGenLayerWorld.imageHeight; ++k) {
                final int[] b = biomes.getInts(world, i - 810, k - 730, 1, 1);
                final LOTRBiome biome = dim.biomeList[b[0]];
                buf.setRGB(i, k, biome.color | 0xFF000000);
                LOTRIntCache.get(world).resetIntCache();
            }
        }
        try {
            ImageIO.write(buf, "png", file);
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    static {
        scale = IntMath.pow(2, 7);
    }
}
