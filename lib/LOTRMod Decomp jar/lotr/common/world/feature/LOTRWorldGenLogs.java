// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenLogs extends WorldGenerator
{
    public LOTRWorldGenLogs() {
        super(false);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        if (!this.isSuitablePositionForLog(world, i, j, k)) {
            return false;
        }
        final int logType = random.nextInt(5);
        if (logType == 0) {
            for (int length = 2 + random.nextInt(6), i2 = i; i2 < i + length && this.isSuitablePositionForLog(world, i2, j, k); ++i2) {
                this.func_150516_a(world, i2, j, k, LOTRMod.rottenLog, 4);
                world.getBlock(i2, j - 1, k).onPlantGrow(world, i2, j - 1, k, i2, j, k);
            }
            return true;
        }
        if (logType == 1) {
            for (int length = 2 + random.nextInt(6), k2 = k; k2 < k + length && this.isSuitablePositionForLog(world, i, j, k2); ++k2) {
                this.func_150516_a(world, i, j, k2, LOTRMod.rottenLog, 8);
                world.getBlock(i, j - 1, k2).onPlantGrow(world, i, j - 1, k2, i, j, k2);
            }
            return true;
        }
        this.func_150516_a(world, i, j, k, LOTRMod.rottenLog, 0);
        world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
        return true;
    }
    
    private boolean isSuitablePositionForLog(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling) && world.getBlock(i, j, k).isReplaceable((IBlockAccess)world, i, j, k);
    }
}
