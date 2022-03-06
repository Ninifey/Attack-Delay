// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBoulder extends WorldGenerator
{
    private Block id;
    private int meta;
    private int minWidth;
    private int maxWidth;
    private int heightCheck;
    
    public LOTRWorldGenBoulder(final Block i, final int j, final int k, final int l) {
        super(false);
        this.heightCheck = 3;
        this.id = i;
        this.meta = j;
        this.minWidth = k;
        this.maxWidth = l;
    }
    
    public LOTRWorldGenBoulder setHeightCheck(final int i) {
        this.heightCheck = i;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k)) {
            return false;
        }
        final int boulderWidth = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth);
        int highestHeight = j;
        int lowestHeight = j;
        for (int i2 = i - boulderWidth; i2 <= i + boulderWidth; ++i2) {
            for (int k2 = k - boulderWidth; k2 <= k + boulderWidth; ++k2) {
                final int heightValue = world.getHeightValue(i2, k2);
                if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i2, heightValue - 1, k2)) {
                    return false;
                }
                if (heightValue > highestHeight) {
                    highestHeight = heightValue;
                }
                if (heightValue < lowestHeight) {
                    lowestHeight = heightValue;
                }
            }
        }
        if (highestHeight - lowestHeight > this.heightCheck) {
            return false;
        }
        for (int spheres = 1 + random.nextInt(boulderWidth + 1), l = 0; l < spheres; ++l) {
            final int posX = i + MathHelper.getRandomIntegerInRange(random, -boulderWidth, boulderWidth);
            final int posZ = k + MathHelper.getRandomIntegerInRange(random, -boulderWidth, boulderWidth);
            final int posY = world.getTopSolidOrLiquidBlock(posX, posZ);
            for (int sphereWidth = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth), i3 = posX - sphereWidth; i3 <= posX + sphereWidth; ++i3) {
                for (int j2 = posY - sphereWidth; j2 <= posY + sphereWidth; ++j2) {
                    for (int k3 = posZ - sphereWidth; k3 <= posZ + sphereWidth; ++k3) {
                        final int i4 = i3 - posX;
                        final int j3 = j2 - posY;
                        final int k4 = k3 - posZ;
                        final int dist = i4 * i4 + j3 * j3 + k4 * k4;
                        if (dist < sphereWidth * sphereWidth || (dist < (sphereWidth + 1) * (sphereWidth + 1) && random.nextInt(3) == 0)) {
                            int j4;
                            for (j4 = j2; j4 >= 0 && !world.getBlock(i3, j4 - 1, k3).isOpaqueCube(); --j4) {}
                            this.func_150516_a(world, i3, j4, k3, this.id, this.meta);
                            world.getBlock(i3, j4 - 1, k3).onPlantGrow(world, i3, j4 - 1, k3, i3, j4 - 1, k3);
                        }
                    }
                }
            }
        }
        return true;
    }
}
