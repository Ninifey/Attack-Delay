// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenNearHaradHills extends LOTRBiomeGenNearHarad
{
    private static NoiseGeneratorPerlin noiseSandstone;
    private static NoiseGeneratorPerlin noiseStone;
    
    public LOTRBiomeGenNearHaradHills(final int i, final boolean major) {
        super(i, major);
        super.enableRain = true;
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenNearHaradHills.noiseSandstone.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = LOTRBiomeGenNearHaradHills.noiseSandstone.func_151601_a(i * 0.4, k * 0.4);
        final double d3 = LOTRBiomeGenNearHaradHills.noiseStone.func_151601_a(i * 0.09, k * 0.09);
        final double d4 = LOTRBiomeGenNearHaradHills.noiseStone.func_151601_a(i * 0.4, k * 0.4);
        if (d3 + d4 > 0.6) {
            super.topBlock = Blocks.stone;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.2) {
            super.topBlock = Blocks.sandstone;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.01f;
    }
    
    static {
        LOTRBiomeGenNearHaradHills.noiseSandstone = new NoiseGeneratorPerlin(new Random(8906820602062L), 1);
        LOTRBiomeGenNearHaradHills.noiseStone = new NoiseGeneratorPerlin(new Random(583062262026L), 1);
    }
}
