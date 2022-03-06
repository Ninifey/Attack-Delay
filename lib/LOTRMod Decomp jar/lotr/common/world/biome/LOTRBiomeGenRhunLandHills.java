// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenRhunLandHills extends LOTRBiomeGenRhunLand
{
    private static NoiseGeneratorPerlin noiseStone;
    private static NoiseGeneratorPerlin noiseSand;
    
    public LOTRBiomeGenRhunLandHills(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.gold_ore, 4), 2.0f, 0, 48);
        super.decorator.resetTreeCluster();
        super.decorator.flowersPerChunk = 2;
        super.decorator.doubleFlowersPerChunk = 0;
        super.decorator.grassPerChunk = 5;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.clearVillages();
        super.biomeColors.resetGrass();
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenRhunLandHills.noiseStone.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = LOTRBiomeGenRhunLandHills.noiseStone.func_151601_a(i * 0.4, k * 0.4);
        final double d3 = LOTRBiomeGenRhunLandHills.noiseSand.func_151601_a(i * 0.09, k * 0.09);
        final double d4 = LOTRBiomeGenRhunLandHills.noiseSand.func_151601_a(i * 0.4, k * 0.4);
        if (d3 + d4 > 1.1) {
            super.topBlock = (Block)Blocks.sand;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.4) {
            super.topBlock = Blocks.stone;
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
        return 0.25f;
    }
    
    static {
        LOTRBiomeGenRhunLandHills.noiseStone = new NoiseGeneratorPerlin(new Random(528592609698295062L), 1);
        LOTRBiomeGenRhunLandHills.noiseSand = new NoiseGeneratorPerlin(new Random(23849150950915615L), 1);
    }
}
