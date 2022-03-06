// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenFarHaradArid extends LOTRBiomeGenFarHaradSavannah
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseSand;
    
    public LOTRBiomeGenFarHaradArid(final int i, final boolean major) {
        super(i, major);
        super.decorator.flowersPerChunk = 1;
        super.decorator.doubleFlowersPerChunk = 1;
        super.spawnableLOTRAmbientList.clear();
        super.biomeColors.setGrass(14073692);
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenFarHaradArid.noiseDirt.func_151601_a(i * 0.07, k * 0.07);
        final double d2 = LOTRBiomeGenFarHaradArid.noiseDirt.func_151601_a(i * 0.15, k * 0.15);
        final double d3 = LOTRBiomeGenFarHaradArid.noiseSand.func_151601_a(i * 0.07, k * 0.07);
        final double d4 = LOTRBiomeGenFarHaradArid.noiseSand.func_151601_a(i * 0.15, k * 0.15);
        if (d3 + d4 > 0.6) {
            super.topBlock = (Block)Blocks.sand;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.1) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        return random.nextBoolean() ? new GrassBlockAndMeta(LOTRMod.aridGrass, 0) : super.getRandomGrass(random);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return super.getChanceToSpawnAnimals() * 0.5f;
    }
    
    static {
        LOTRBiomeGenFarHaradArid.noiseDirt = new NoiseGeneratorPerlin(new Random(35952060662L), 1);
        LOTRBiomeGenFarHaradArid.noiseSand = new NoiseGeneratorPerlin(new Random(5925366672L), 1);
    }
}
