// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSand extends WorldGenerator
{
    private Block sandBlock;
    private int radius;
    private int heightRadius;
    
    public LOTRWorldGenSand(final Block b, final int r, final int hr) {
        this.sandBlock = b;
        this.radius = r;
        this.heightRadius = hr;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        boolean mid = false;
        boolean adj = false;
        for (int waterCheck = 1, i2 = -waterCheck; i2 <= waterCheck; ++i2) {
            for (int k2 = -waterCheck; k2 <= waterCheck; ++k2) {
                final int i3 = i + i2;
                final int k3 = k + k2;
                if (world.getBlock(i3, j, k3).getMaterial() == Material.water) {
                    if (i2 == 0 && k2 == 0) {
                        mid = true;
                    }
                    else {
                        adj = true;
                    }
                }
            }
        }
        if (!mid || !adj) {
            return false;
        }
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        final int r = random.nextInt(this.radius - 2) + 2;
        final int hr = this.heightRadius;
        for (int i4 = i - r; i4 <= i + r; ++i4) {
            for (int k4 = k - r; k4 <= k + r; ++k4) {
                final int i5 = i4 - i;
                final int k5 = k4 - k;
                if (i5 * i5 + k5 * k5 <= r * r) {
                    for (int j2 = j - hr; j2 <= j + hr; ++j2) {
                        final Block block = world.getBlock(i4, j2, k4);
                        if ((block == biome.topBlock || block == biome.fillerBlock) && random.nextInt(3) != 0) {
                            world.setBlock(i4, j2, k4, this.sandBlock, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
