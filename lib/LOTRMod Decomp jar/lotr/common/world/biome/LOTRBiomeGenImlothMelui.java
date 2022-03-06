// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class LOTRBiomeGenImlothMelui extends LOTRBiomeGenLossarnach
{
    public LOTRBiomeGenImlothMelui(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 20;
        super.decorator.doubleFlowersPerChunk = 12;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 3;
        this.registerPlainsFlowers();
        this.addFlower((Block)Blocks.red_flower, 0, 80);
        super.decorator.addTree(LOTRTreeType.MAPLE, 500);
        super.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.BEECH, 500);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 100);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterImlothMelui;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 1; ++l) {
            final WorldGenerator shrub = (WorldGenerator)LOTRTreeType.OAK_SHRUB.create(false, random);
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            shrub.generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(8) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j2 = random.nextInt(128);
            final int k3 = k + random.nextInt(16) + 8;
            new WorldGenFlowers(LOTRMod.athelas).generate(world, random, i3, j2, k3);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        if (random.nextInt(5) > 0) {
            final WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
            doubleFlowerGen.func_150548_a(4);
            return (WorldGenerator)doubleFlowerGen;
        }
        return super.getRandomWorldGenForDoubleFlower(random);
    }
}
