// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWebOfUngoliant extends WorldGenerator
{
    private int attempts;
    
    public LOTRWorldGenWebOfUngoliant(final boolean flag, final int i) {
        super(flag);
        this.attempts = i;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < this.attempts; ++l) {
            final int i2 = i - random.nextInt(8) + random.nextInt(8);
            final int j2 = j - random.nextInt(6) + random.nextInt(6);
            final int k2 = k - random.nextInt(8) + random.nextInt(8);
            if (world.isAirBlock(i2, j2, k2)) {
                boolean flag = false;
                if (this.isSuitableBlock(world, i2 - 1, j2, k2)) {
                    flag = true;
                }
                if (this.isSuitableBlock(world, i2 + 1, j2, k2)) {
                    flag = true;
                }
                if (this.isSuitableBlock(world, i2, j2 - 1, k2)) {
                    flag = true;
                }
                if (this.isSuitableBlock(world, i2, j2 + 1, k2)) {
                    flag = true;
                }
                if (this.isSuitableBlock(world, i2, j2, k2 - 1)) {
                    flag = true;
                }
                if (this.isSuitableBlock(world, i2, j2, k2 + 1)) {
                    flag = true;
                }
                if (flag) {
                    this.func_150516_a(world, i2, j2, k2, LOTRMod.webUngoliant, 0);
                }
            }
        }
        return true;
    }
    
    private boolean isSuitableBlock(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j, k).isNormalCube();
    }
}
