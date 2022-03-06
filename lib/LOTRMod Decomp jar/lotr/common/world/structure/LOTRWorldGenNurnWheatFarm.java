// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNurnWheatFarm extends LOTRWorldGenNurnFarmBase
{
    public LOTRWorldGenNurnWheatFarm(final boolean flag) {
        super(flag);
    }
    
    @Override
    public void generateCrops(final World world, final Random random, final int i, final int j, final int k) {
        for (int i2 = i - 4; i2 <= i + 4; ++i2) {
            for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                if (Math.abs(i2 - i) == 4 && Math.abs(k2 - k) == 4) {
                    this.func_150516_a(world, i2, j + 1, k2, LOTRMod.brick, 0);
                    this.func_150516_a(world, i2, j + 2, k2, LOTRMod.brick, 0);
                    this.func_150516_a(world, i2, j + 3, k2, LOTRMod.fence, 3);
                    this.func_150516_a(world, i2, j + 4, k2, Blocks.wool, 12);
                    this.placeSkull(world, random, i2, j + 5, k2);
                }
                else if (Math.abs(i2 - i) <= 1 && Math.abs(k2 - k) <= 1) {
                    this.func_150516_a(world, i2, j + 1, k2, LOTRMod.brick, 0);
                    if (Math.abs(i2 - i) != 0 && Math.abs(k2 - k) != 0) {
                        this.placeOrcTorch(world, i2, j + 2, k2);
                    }
                }
                else if (i2 == i || k2 == k) {
                    if (Math.abs(i2 - i) <= 3 && Math.abs(k2 - k) <= 3) {
                        this.func_150516_a(world, i2, j, k2, Blocks.water, 0);
                    }
                }
                else {
                    this.func_150516_a(world, i2, j, k2, Blocks.farmland, 7);
                    this.func_150516_a(world, i2, j + 1, k2, Blocks.wheat, 7);
                }
            }
        }
        this.func_150516_a(world, i, j + 1, k, LOTRMod.morgulTable, 0);
    }
}
