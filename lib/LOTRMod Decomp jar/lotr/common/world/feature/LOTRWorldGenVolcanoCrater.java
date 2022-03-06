// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenVolcanoCrater extends WorldGenerator
{
    private int minWidth;
    private int maxWidth;
    private int heightCheck;
    
    public LOTRWorldGenVolcanoCrater() {
        this.minWidth = 5;
        this.maxWidth = 15;
        this.heightCheck = 8;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k) && world.getBlock(i, j - 1, k) != Blocks.stone) {
            return false;
        }
        final int craterWidth = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth);
        int highestHeight = j;
        int lowestHeight = j;
        for (int i2 = i - craterWidth; i2 <= i + craterWidth; ++i2) {
            for (int k2 = k - craterWidth; k2 <= k + craterWidth; ++k2) {
                final int heightValue = world.getHeightValue(i2, k2);
                final int j2 = heightValue - 1;
                if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i2, j2, k2) && world.getBlock(i2, j2, k2) != Blocks.stone) {
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
        for (int spheres = 1, l = 0; l < spheres; ++l) {
            final int posX = i;
            final int posZ = k;
            final int posY = world.getTopSolidOrLiquidBlock(posX, posZ);
            for (int sphereWidth = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth), i3 = posX - sphereWidth; i3 <= posX + sphereWidth; ++i3) {
                for (int k3 = posZ - sphereWidth; k3 <= posZ + sphereWidth; ++k3) {
                    final int i4 = i3 - posX;
                    final int k4 = k3 - posZ;
                    final int xzDistSq = i4 * i4 + k4 * k4;
                    if (xzDistSq < sphereWidth * sphereWidth || (xzDistSq < (sphereWidth + 1) * (sphereWidth + 1) && random.nextInt(3) == 0)) {
                        int j3;
                        for (int jTop = j3 = world.getTopSolidOrLiquidBlock(i3, k3); j3 > posY; --j3) {
                            this.func_150516_a(world, i3, j3, k3, Blocks.air, 0);
                        }
                        final int depthHere = (int)((sphereWidth - Math.sqrt(xzDistSq)) * 0.7) + random.nextInt(2);
                        for (int j4 = posY - depthHere - 1; j4 >= posY - (depthHere + this.heightCheck + 2 + random.nextInt(2)) && !world.getBlock(i3, j4, k3).isOpaqueCube(); --j4) {
                            this.func_150516_a(world, i3, j4, k3, Blocks.stone, 0);
                        }
                        for (int j4 = posY; j4 >= posY - depthHere; --j4) {
                            final int jDepth = posY - j4;
                            if (jDepth > 6) {
                                this.func_150516_a(world, i3, j4, k3, Blocks.lava, 0);
                                if (!world.getBlock(i3, j4 - 1, k3).isOpaqueCube()) {
                                    this.func_150516_a(world, i3, j4 - 1, k3, Blocks.obsidian, 0);
                                }
                            }
                            else {
                                this.func_150516_a(world, i3, j4, k3, Blocks.air, 0);
                            }
                            if (jDepth > 4) {
                                this.func_150516_a(world, i3, j4 - 1, k3, Blocks.obsidian, 0);
                            }
                            else if (jDepth > 2) {
                                if (random.nextInt(4) == 0) {
                                    this.func_150516_a(world, i3, j4 - 1, k3, Blocks.gravel, 0);
                                    this.func_150516_a(world, i3, j4 - 2, k3, Blocks.stone, 0);
                                }
                                else {
                                    this.func_150516_a(world, i3, j4 - 1, k3, LOTRMod.obsidianGravel, 0);
                                    this.func_150516_a(world, i3, j4 - 2, k3, Blocks.obsidian, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
