// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiome;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBiomeFlowers extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final BiomeGenBase.FlowerEntry flower = ((LOTRBiome)world.getBiomeGenForCoords(i, k)).getRandomFlower(random);
        final Block block = flower.block;
        final int meta = flower.metadata;
        for (int l = 0; l < 64; ++l) {
            final int i2 = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j + random.nextInt(4) - random.nextInt(4);
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            if (world.isAirBlock(i2, j2, k2) && (!world.provider.hasNoSky || j2 < 255) && block.canBlockStay(world, i2, j2, k2)) {
                world.setBlock(i2, j2, k2, block, meta, 2);
            }
        }
        return true;
    }
}
