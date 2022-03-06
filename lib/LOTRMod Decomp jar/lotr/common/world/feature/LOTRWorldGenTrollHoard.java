// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import lotr.common.world.biome.LOTRBiomeGenEttenmoors;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenTrollHoard extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = world.getTopSolidOrLiquidBlock(i, k);
        final int maxCaveHeight = height - 4;
        final int chests = 2 + random.nextInt(5);
        int chestsGenerated = 0;
        for (int l = 0; l < 64; ++l) {
            final int i2 = i + MathHelper.getRandomIntegerInRange(random, -3, 3);
            final int j2 = j + MathHelper.getRandomIntegerInRange(random, -3, 3);
            final int k2 = k + MathHelper.getRandomIntegerInRange(random, -3, 3);
            if (j2 <= maxCaveHeight && world.isAirBlock(i2, j2, k2) && world.getBlock(i2, j2 - 1, k2) == Blocks.stone) {
                Block treasureBlock;
                if (random.nextInt(5) == 0) {
                    treasureBlock = LOTRMod.treasureCopper;
                }
                else if (random.nextBoolean()) {
                    treasureBlock = LOTRMod.treasureSilver;
                }
                else {
                    treasureBlock = LOTRMod.treasureGold;
                }
                for (int top = j2 + random.nextInt(3), j3 = j2; j3 <= top; ++j3) {
                    int treasureMeta = 7;
                    if (j3 == top) {
                        treasureMeta = random.nextInt(7);
                    }
                    if (!world.isAirBlock(i2, j3, k2)) {
                        break;
                    }
                    this.func_150516_a(world, i2, j3, k2, treasureBlock, treasureMeta);
                }
            }
        }
        for (int l = 0; l < 48; ++l) {
            final int i2 = i + MathHelper.getRandomIntegerInRange(random, -8, 8);
            final int j2 = j + MathHelper.getRandomIntegerInRange(random, -2, 2);
            final int k2 = k + MathHelper.getRandomIntegerInRange(random, -8, 8);
            if (j2 <= maxCaveHeight && world.isAirBlock(i2, j2, k2) && world.getBlock(i2, j2 - 1, k2) == Blocks.stone) {
                this.func_150516_a(world, i2, j2, k2, (Block)Blocks.chest, 0);
                LOTRChestContents.fillChest(world, random, i2, j2, k2, LOTRChestContents.TROLL_HOARD);
                if (world.getBiomeGenForCoords(i2, k2) instanceof LOTRBiomeGenEttenmoors && random.nextInt(5) == 0) {
                    LOTRChestContents.fillChest(world, random, i2, j2, k2, LOTRChestContents.TROLL_HOARD_ETTENMOORS);
                }
                if (++chestsGenerated >= chests) {
                    break;
                }
            }
        }
        return chestsGenerated > 0;
    }
}
