// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

import org.apache.commons.lang3.ArrayUtils;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import java.util.Random;
import java.util.ArrayList;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.feature.LOTRTreeType;
import java.util.List;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeVariant
{
    private static LOTRBiomeVariant[] allVariants;
    public static LOTRBiomeVariant STANDARD;
    public static LOTRBiomeVariant FLOWERS;
    public static LOTRBiomeVariant FOREST;
    public static LOTRBiomeVariant FOREST_LIGHT;
    public static LOTRBiomeVariant STEPPE;
    public static LOTRBiomeVariant STEPPE_BARREN;
    public static LOTRBiomeVariant HILLS;
    public static LOTRBiomeVariant HILLS_FOREST;
    public static LOTRBiomeVariant MOUNTAIN;
    public static LOTRBiomeVariant CLEARING;
    public static LOTRBiomeVariant DENSEFOREST_OAK;
    public static LOTRBiomeVariant DENSEFOREST_SPRUCE;
    public static LOTRBiomeVariant DENSEFOREST_OAK_SPRUCE;
    public static LOTRBiomeVariant DEADFOREST_OAK;
    public static LOTRBiomeVariant DEADFOREST_SPRUCE;
    public static LOTRBiomeVariant DEADFOREST_OAK_SPRUCE;
    public static LOTRBiomeVariant SHRUBLAND_OAK;
    public static LOTRBiomeVariant DENSEFOREST_BIRCH;
    public static LOTRBiomeVariant SWAMP_LOWLAND;
    public static LOTRBiomeVariant SWAMP_UPLAND;
    public static LOTRBiomeVariant SAVANNAH_BAOBAB;
    public static LOTRBiomeVariant LAKE;
    public static LOTRBiomeVariant DENSEFOREST_LEBETHRON;
    public static LOTRBiomeVariant BOULDERS_RED;
    public static LOTRBiomeVariant BOULDERS_ROHAN;
    public static LOTRBiomeVariant JUNGLE_DENSE;
    public static LOTRBiomeVariant VINEYARD;
    public static LOTRBiomeVariant FOREST_ASPEN;
    public static LOTRBiomeVariant FOREST_BIRCH;
    public static LOTRBiomeVariant FOREST_BEECH;
    public static LOTRBiomeVariant FOREST_MAPLE;
    public static LOTRBiomeVariant FOREST_LARCH;
    public static LOTRBiomeVariant FOREST_PINE;
    public static LOTRBiomeVariant ORCHARD_SHIRE;
    public static LOTRBiomeVariant ORCHARD_APPLE_PEAR;
    public static LOTRBiomeVariant ORCHARD_ORANGE;
    public static LOTRBiomeVariant ORCHARD_LEMON;
    public static LOTRBiomeVariant ORCHARD_LIME;
    public static LOTRBiomeVariant ORCHARD_ALMOND;
    public static LOTRBiomeVariant ORCHARD_OLIVE;
    public static LOTRBiomeVariant ORCHARD_PLUM;
    public static LOTRBiomeVariant RIVER;
    public static LOTRBiomeVariant SCRUBLAND;
    public static LOTRBiomeVariant HILLS_SCRUBLAND;
    public static LOTRBiomeVariant WASTELAND;
    public static LOTRBiomeVariant ORCHARD_DATE;
    public static LOTRBiomeVariant DENSEFOREST_DARK_OAK;
    public static LOTRBiomeVariant ORCHARD_POMEGRANATE;
    public static LOTRBiomeVariant DUNES;
    public static LOTRBiomeVariant SCRUBLAND_SAND;
    public static LOTRBiomeVariant HILLS_SCRUBLAND_SAND;
    public static LOTRBiomeVariant WASTELAND_SAND;
    public static LOTRBiomeVariant[] SET_NORMAL;
    public static LOTRBiomeVariant[] SET_NORMAL_OAK;
    public static LOTRBiomeVariant[] SET_NORMAL_SPRUCE;
    public static LOTRBiomeVariant[] SET_NORMAL_OAK_SPRUCE;
    public static LOTRBiomeVariant[] SET_NORMAL_NOSTEPPE;
    public static LOTRBiomeVariant[] SET_NORMAL_OAK_NOSTEPPE;
    public static LOTRBiomeVariant[] SET_FOREST;
    public static LOTRBiomeVariant[] SET_MOUNTAINS;
    public static LOTRBiomeVariant[] SET_SWAMP;
    public static NoiseGeneratorPerlin marshNoise;
    public static NoiseGeneratorPerlin podzolNoise;
    public final int variantID;
    public final String variantName;
    public final VariantScale variantScale;
    public float tempBoost;
    public float rainBoost;
    public boolean absoluteHeight;
    public float absoluteHeightLevel;
    private float heightBoost;
    public float hillFactor;
    public float treeFactor;
    public float grassFactor;
    public float flowerFactor;
    public boolean hasMarsh;
    public boolean disableStructures;
    public boolean disableVillages;
    public List<LOTRTreeType.WeightedTreeType> treeTypes;
    public float variantTreeChance;
    public WorldGenerator boulderGen;
    public int boulderChance;
    public int boulderMax;
    
    public LOTRBiomeVariant(final int i, final String s, final VariantScale scale) {
        this.tempBoost = 0.0f;
        this.rainBoost = 0.0f;
        this.absoluteHeight = false;
        this.absoluteHeightLevel = 0.0f;
        this.heightBoost = 0.0f;
        this.hillFactor = 1.0f;
        this.treeFactor = 1.0f;
        this.grassFactor = 1.0f;
        this.flowerFactor = 1.0f;
        this.hasMarsh = false;
        this.disableStructures = false;
        this.disableVillages = false;
        this.treeTypes = new ArrayList<LOTRTreeType.WeightedTreeType>();
        this.variantTreeChance = 0.0f;
        this.boulderChance = 0;
        this.boulderMax = 1;
        if (LOTRBiomeVariant.allVariants[i] != null) {
            throw new IllegalArgumentException("LOTR Biome variant already exists at index " + i);
        }
        this.variantID = i;
        LOTRBiomeVariant.allVariants[i] = this;
        this.variantName = s;
        this.variantScale = scale;
    }
    
    public static LOTRBiomeVariant getVariantForID(final int i) {
        final LOTRBiomeVariant variant = LOTRBiomeVariant.allVariants[i];
        if (variant == null) {
            return LOTRBiomeVariant.STANDARD;
        }
        return variant;
    }
    
    protected LOTRBiomeVariant setTemperatureRainfall(final float temp, final float rain) {
        this.tempBoost = temp;
        this.rainBoost = rain;
        return this;
    }
    
    protected LOTRBiomeVariant setHeight(final float height, final float hills) {
        this.heightBoost = height;
        this.hillFactor = hills;
        return this;
    }
    
    protected LOTRBiomeVariant setAbsoluteHeight(final float height, final float hills) {
        this.absoluteHeight = true;
        this.absoluteHeightLevel = height;
        float f = height;
        f -= 2.0f;
        f += 0.2f;
        this.heightBoost = f;
        this.hillFactor = hills;
        return this;
    }
    
    public float getHeightBoostAt(final int i, final int k) {
        return this.heightBoost;
    }
    
    protected LOTRBiomeVariant setTrees(final float f) {
        this.treeFactor = f;
        return this;
    }
    
    protected LOTRBiomeVariant setGrass(final float f) {
        this.grassFactor = f;
        return this;
    }
    
    protected LOTRBiomeVariant setFlowers(final float f) {
        this.flowerFactor = f;
        return this;
    }
    
    protected LOTRBiomeVariant addTreeTypes(final float f, final Object... trees) {
        this.variantTreeChance = f;
        for (int i = 0; i < trees.length / 2; ++i) {
            final Object obj1 = trees[i * 2];
            final Object obj2 = trees[i * 2 + 1];
            this.treeTypes.add(new LOTRTreeType.WeightedTreeType((LOTRTreeType)obj1, (int)obj2));
        }
        return this;
    }
    
    public LOTRTreeType getRandomTree(final Random random) {
        final WeightedRandom.Item item = WeightedRandom.getRandomItem(random, (Collection)this.treeTypes);
        return ((LOTRTreeType.WeightedTreeType)item).treeType;
    }
    
    protected LOTRBiomeVariant setMarsh() {
        this.hasMarsh = true;
        return this;
    }
    
    protected LOTRBiomeVariant disableVillages() {
        this.disableVillages = true;
        return this;
    }
    
    protected LOTRBiomeVariant disableStructuresVillages() {
        this.disableStructures = true;
        this.disableVillages = true;
        return this;
    }
    
    protected LOTRBiomeVariant setBoulders(final WorldGenerator boulder, final int chance, final int num) {
        if (num < 1) {
            throw new IllegalArgumentException("n must be > 1");
        }
        this.boulderGen = boulder;
        this.boulderChance = chance;
        this.boulderMax = num;
        return this;
    }
    
    public void generateVariantTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int height, final LOTRBiome biome) {
    }
    
    public void decorateVariant(final World world, final Random random, final int i, final int k, final LOTRBiome biome) {
    }
    
    static {
        LOTRBiomeVariant.allVariants = new LOTRBiomeVariant[256];
        LOTRBiomeVariant.STANDARD = new LOTRBiomeVariant(0, "standard", VariantScale.ALL);
        LOTRBiomeVariant.FLOWERS = new LOTRBiomeVariant(1, "flowers", VariantScale.SMALL).setFlowers(10.0f);
        LOTRBiomeVariant.FOREST = new LOTRBiomeVariantForest(2, "forest");
        LOTRBiomeVariant.FOREST_LIGHT = new LOTRBiomeVariant(3, "forest_light", VariantScale.ALL).setTemperatureRainfall(0.0f, 0.2f).setTrees(3.0f).setGrass(2.0f);
        LOTRBiomeVariant.STEPPE = new LOTRBiomeVariant(4, "steppe", VariantScale.LARGE).setTemperatureRainfall(0.0f, -0.1f).setHeight(0.0f, 0.1f).setTrees(0.01f).setGrass(3.0f);
        LOTRBiomeVariant.STEPPE_BARREN = new LOTRBiomeVariant(5, "steppe_barren", VariantScale.LARGE).setTemperatureRainfall(0.1f, -0.2f).setHeight(0.0f, 0.1f).setTrees(0.01f).setGrass(0.2f);
        LOTRBiomeVariant.HILLS = new LOTRBiomeVariant(6, "hills", VariantScale.ALL).setTemperatureRainfall(-0.1f, -0.1f).setHeight(0.5f, 1.5f).setGrass(0.5f);
        LOTRBiomeVariant.HILLS_FOREST = new LOTRBiomeVariant(7, "hills_forest", VariantScale.ALL).setTemperatureRainfall(-0.1f, 0.0f).setHeight(0.5f, 1.5f).setTrees(3.0f);
        LOTRBiomeVariant.MOUNTAIN = new LOTRBiomeVariant(8, "mountain", VariantScale.ALL).setTemperatureRainfall(-0.1f, -0.2f).setHeight(1.2f, 3.0f);
        LOTRBiomeVariant.CLEARING = new LOTRBiomeVariant(9, "clearing", VariantScale.SMALL).setHeight(0.0f, 0.5f).setTrees(0.0f).setGrass(2.0f).setFlowers(3.0f);
        LOTRBiomeVariant.DENSEFOREST_OAK = new LOTRBiomeVariantDenseForest(10, "denseForest_oak").addTreeTypes(0.5f, LOTRTreeType.OAK_LARGE, 600, LOTRTreeType.OAK_PARTY, 100);
        LOTRBiomeVariant.DENSEFOREST_SPRUCE = new LOTRBiomeVariantDenseForest(11, "denseForest_spruce").addTreeTypes(0.5f, LOTRTreeType.SPRUCE_MEGA, 100);
        LOTRBiomeVariant.DENSEFOREST_OAK_SPRUCE = new LOTRBiomeVariantDenseForest(12, "denseForest_oak_spruce").addTreeTypes(0.5f, LOTRTreeType.OAK_LARGE, 600, LOTRTreeType.OAK_PARTY, 200, LOTRTreeType.SPRUCE_MEGA, 200);
        LOTRBiomeVariant.DEADFOREST_OAK = new LOTRBiomeVariantDeadForest(13, "deadForest_oak").addTreeTypes(0.5f, LOTRTreeType.OAK_DEAD, 100);
        LOTRBiomeVariant.DEADFOREST_SPRUCE = new LOTRBiomeVariantDeadForest(14, "deadForest_spruce").addTreeTypes(0.5f, LOTRTreeType.SPRUCE_DEAD, 100);
        LOTRBiomeVariant.DEADFOREST_OAK_SPRUCE = new LOTRBiomeVariantDeadForest(15, "deadForest_oak_spruce").addTreeTypes(0.5f, LOTRTreeType.OAK_DEAD, 100, LOTRTreeType.SPRUCE_DEAD, 100);
        LOTRBiomeVariant.SHRUBLAND_OAK = new LOTRBiomeVariant(16, "shrubland_oak", VariantScale.ALL).setTemperatureRainfall(0.0f, 0.3f).setTrees(6.0f).addTreeTypes(0.7f, LOTRTreeType.OAK_SHRUB, 100);
        LOTRBiomeVariant.DENSEFOREST_BIRCH = new LOTRBiomeVariantDenseForest(17, "denseForest_birch").addTreeTypes(0.5f, LOTRTreeType.BIRCH_LARGE, 600, LOTRTreeType.BIRCH_PARTY, 100);
        LOTRBiomeVariant.SWAMP_LOWLAND = new LOTRBiomeVariant(18, "swampLowland", VariantScale.SMALL).setHeight(-0.12f, 0.2f).setTrees(0.5f).setGrass(5.0f).setMarsh();
        LOTRBiomeVariant.SWAMP_UPLAND = new LOTRBiomeVariant(19, "swampUpland", VariantScale.SMALL).setHeight(0.12f, 1.0f).setTrees(6.0f).setGrass(5.0f);
        LOTRBiomeVariant.SAVANNAH_BAOBAB = new LOTRBiomeVariant(20, "savannahBaobab", VariantScale.LARGE).setHeight(0.0f, 0.5f).setTemperatureRainfall(0.0f, 0.2f).setTrees(1.5f).setGrass(0.5f).addTreeTypes(0.6f, LOTRTreeType.BAOBAB, 100);
        LOTRBiomeVariant.LAKE = new LOTRBiomeVariant(21, "lake", VariantScale.NONE).setAbsoluteHeight(-0.5f, 0.05f);
        LOTRBiomeVariant.DENSEFOREST_LEBETHRON = new LOTRBiomeVariantDenseForest(22, "denseForest_lebethron").addTreeTypes(0.5f, LOTRTreeType.LEBETHRON_LARGE, 600, LOTRTreeType.LEBETHRON_PARTY, 100);
        LOTRBiomeVariant.BOULDERS_RED = new LOTRBiomeVariant(23, "boulders_red", VariantScale.LARGE).setBoulders(new LOTRWorldGenBoulder(LOTRMod.redSandstone, 1, 1, 3), 2, 4);
        LOTRBiomeVariant.BOULDERS_ROHAN = new LOTRBiomeVariant(24, "boulders_rohan", VariantScale.LARGE).setBoulders(new LOTRWorldGenBoulder(LOTRMod.rock, 2, 1, 3), 2, 4);
        LOTRBiomeVariant.JUNGLE_DENSE = new LOTRBiomeVariant(25, "jungle_dense", VariantScale.LARGE).setTemperatureRainfall(0.1f, 0.1f).setTrees(2.0f).addTreeTypes(0.6f, LOTRTreeType.JUNGLE_FANGORN, 1000, LOTRTreeType.MAHOGANY_FANGORN, 500);
        LOTRBiomeVariant.VINEYARD = new LOTRBiomeVariant(26, "vineyard", VariantScale.SMALL).setHeight(0.0f, 0.5f).setTrees(0.0f).setGrass(0.5f).setFlowers(0.0f).disableStructuresVillages();
        LOTRBiomeVariant.FOREST_ASPEN = new LOTRBiomeVariantForest(27, "forest_aspen").addTreeTypes(0.8f, LOTRTreeType.ASPEN, 1000, LOTRTreeType.ASPEN_LARGE, 50);
        LOTRBiomeVariant.FOREST_BIRCH = new LOTRBiomeVariantForest(28, "forest_birch").addTreeTypes(0.8f, LOTRTreeType.BIRCH, 1000, LOTRTreeType.BIRCH_LARGE, 150);
        LOTRBiomeVariant.FOREST_BEECH = new LOTRBiomeVariantForest(29, "forest_beech").addTreeTypes(0.8f, LOTRTreeType.BEECH, 1000, LOTRTreeType.BEECH_LARGE, 150);
        LOTRBiomeVariant.FOREST_MAPLE = new LOTRBiomeVariantForest(30, "forest_maple").addTreeTypes(0.8f, LOTRTreeType.MAPLE, 1000, LOTRTreeType.MAPLE_LARGE, 150);
        LOTRBiomeVariant.FOREST_LARCH = new LOTRBiomeVariantForest(31, "forest_larch").addTreeTypes(0.8f, LOTRTreeType.LARCH, 1000);
        LOTRBiomeVariant.FOREST_PINE = new LOTRBiomeVariantForest(32, "forest_pine").addTreeTypes(0.8f, LOTRTreeType.PINE, 1000);
        LOTRBiomeVariant.ORCHARD_SHIRE = new LOTRBiomeVariantOrchard(33, "orchard_shire").addTreeTypes(1.0f, LOTRTreeType.APPLE, 100, LOTRTreeType.PEAR, 100, LOTRTreeType.CHERRY, 10);
        LOTRBiomeVariant.ORCHARD_APPLE_PEAR = new LOTRBiomeVariantOrchard(34, "orchard_apple_pear").addTreeTypes(1.0f, LOTRTreeType.APPLE, 100, LOTRTreeType.PEAR, 100);
        LOTRBiomeVariant.ORCHARD_ORANGE = new LOTRBiomeVariantOrchard(35, "orchard_orange").addTreeTypes(1.0f, LOTRTreeType.ORANGE, 100);
        LOTRBiomeVariant.ORCHARD_LEMON = new LOTRBiomeVariantOrchard(36, "orchard_lemon").addTreeTypes(1.0f, LOTRTreeType.LEMON, 100);
        LOTRBiomeVariant.ORCHARD_LIME = new LOTRBiomeVariantOrchard(37, "orchard_lime").addTreeTypes(1.0f, LOTRTreeType.LIME, 100);
        LOTRBiomeVariant.ORCHARD_ALMOND = new LOTRBiomeVariantOrchard(38, "orchard_almond").addTreeTypes(1.0f, LOTRTreeType.ALMOND, 100);
        LOTRBiomeVariant.ORCHARD_OLIVE = new LOTRBiomeVariantOrchard(39, "orchard_olive").addTreeTypes(1.0f, LOTRTreeType.OLIVE, 100);
        LOTRBiomeVariant.ORCHARD_PLUM = new LOTRBiomeVariantOrchard(40, "orchard_plum").addTreeTypes(1.0f, LOTRTreeType.PLUM, 100);
        LOTRBiomeVariant.RIVER = new LOTRBiomeVariant(41, "river", VariantScale.NONE).setAbsoluteHeight(-0.5f, 0.05f).setTemperatureRainfall(0.0f, 0.3f);
        LOTRBiomeVariant.SCRUBLAND = new LOTRBiomeVariantScrubland(42, "scrubland", Blocks.stone).setHeight(0.0f, 0.8f);
        LOTRBiomeVariant.HILLS_SCRUBLAND = new LOTRBiomeVariantScrubland(43, "hills_scrubland", Blocks.stone).setHeight(0.5f, 2.0f);
        LOTRBiomeVariant.WASTELAND = new LOTRBiomeVariantWasteland(44, "wasteland", Blocks.stone).setHeight(0.0f, 0.5f);
        LOTRBiomeVariant.ORCHARD_DATE = new LOTRBiomeVariantOrchard(45, "orchard_date").addTreeTypes(1.0f, LOTRTreeType.DATE_PALM, 100);
        LOTRBiomeVariant.DENSEFOREST_DARK_OAK = new LOTRBiomeVariantDenseForest(46, "denseForest_darkOak").addTreeTypes(0.5f, LOTRTreeType.DARK_OAK, 600, LOTRTreeType.DARK_OAK_PARTY, 100);
        LOTRBiomeVariant.ORCHARD_POMEGRANATE = new LOTRBiomeVariantOrchard(47, "orchard_pomegranate").addTreeTypes(1.0f, LOTRTreeType.POMEGRANATE, 100);
        LOTRBiomeVariant.DUNES = new LOTRBiomeVariantDunes(48, "dunes");
        LOTRBiomeVariant.SCRUBLAND_SAND = new LOTRBiomeVariantScrubland(49, "scrubland_sand", Blocks.sandstone).setHeight(0.0f, 0.8f);
        LOTRBiomeVariant.HILLS_SCRUBLAND_SAND = new LOTRBiomeVariantScrubland(50, "hills_scrubland_sand", Blocks.sandstone).setHeight(0.5f, 2.0f);
        LOTRBiomeVariant.WASTELAND_SAND = new LOTRBiomeVariantWasteland(51, "wasteland_sand", Blocks.sandstone).setHeight(0.0f, 0.5f);
        LOTRBiomeVariant.SET_NORMAL = new LOTRBiomeVariant[] { LOTRBiomeVariant.FLOWERS, LOTRBiomeVariant.FOREST, LOTRBiomeVariant.FOREST_LIGHT, LOTRBiomeVariant.STEPPE, LOTRBiomeVariant.STEPPE_BARREN, LOTRBiomeVariant.HILLS, LOTRBiomeVariant.HILLS_FOREST };
        LOTRBiomeVariant.SET_NORMAL_OAK = (LOTRBiomeVariant[])ArrayUtils.addAll((Object[])LOTRBiomeVariant.SET_NORMAL, (Object[])new LOTRBiomeVariant[] { LOTRBiomeVariant.DENSEFOREST_OAK, LOTRBiomeVariant.DEADFOREST_OAK, LOTRBiomeVariant.SHRUBLAND_OAK });
        LOTRBiomeVariant.SET_NORMAL_SPRUCE = (LOTRBiomeVariant[])ArrayUtils.addAll((Object[])LOTRBiomeVariant.SET_NORMAL, (Object[])new LOTRBiomeVariant[] { LOTRBiomeVariant.DENSEFOREST_SPRUCE, LOTRBiomeVariant.DEADFOREST_SPRUCE });
        LOTRBiomeVariant.SET_NORMAL_OAK_SPRUCE = (LOTRBiomeVariant[])ArrayUtils.addAll((Object[])LOTRBiomeVariant.SET_NORMAL, (Object[])new LOTRBiomeVariant[] { LOTRBiomeVariant.DENSEFOREST_OAK, LOTRBiomeVariant.DEADFOREST_OAK, LOTRBiomeVariant.SHRUBLAND_OAK, LOTRBiomeVariant.DENSEFOREST_SPRUCE, LOTRBiomeVariant.DEADFOREST_SPRUCE, LOTRBiomeVariant.DENSEFOREST_OAK_SPRUCE, LOTRBiomeVariant.DEADFOREST_OAK_SPRUCE });
        LOTRBiomeVariant.SET_NORMAL_NOSTEPPE = (LOTRBiomeVariant[])ArrayUtils.removeElements((Object[])LOTRBiomeVariant.SET_NORMAL, (Object[])new LOTRBiomeVariant[] { LOTRBiomeVariant.STEPPE, LOTRBiomeVariant.STEPPE_BARREN });
        LOTRBiomeVariant.SET_NORMAL_OAK_NOSTEPPE = (LOTRBiomeVariant[])ArrayUtils.removeElements((Object[])LOTRBiomeVariant.SET_NORMAL_OAK, (Object[])new LOTRBiomeVariant[] { LOTRBiomeVariant.STEPPE, LOTRBiomeVariant.STEPPE_BARREN });
        LOTRBiomeVariant.SET_FOREST = new LOTRBiomeVariant[] { LOTRBiomeVariant.FLOWERS, LOTRBiomeVariant.HILLS, LOTRBiomeVariant.CLEARING };
        LOTRBiomeVariant.SET_MOUNTAINS = new LOTRBiomeVariant[] { LOTRBiomeVariant.FOREST, LOTRBiomeVariant.FOREST_LIGHT };
        LOTRBiomeVariant.SET_SWAMP = new LOTRBiomeVariant[] { LOTRBiomeVariant.SWAMP_LOWLAND, LOTRBiomeVariant.SWAMP_LOWLAND, LOTRBiomeVariant.SWAMP_LOWLAND, LOTRBiomeVariant.SWAMP_UPLAND };
        LOTRBiomeVariant.marshNoise = new NoiseGeneratorPerlin(new Random(444L), 1);
        LOTRBiomeVariant.podzolNoise = new NoiseGeneratorPerlin(new Random(58052L), 1);
    }
    
    public enum VariantScale
    {
        LARGE, 
        SMALL, 
        ALL, 
        NONE;
    }
}
