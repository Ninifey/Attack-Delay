// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenYams extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 64; ++l) {
            final int i2 = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j + random.nextInt(4) - random.nextInt(4);
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            final Block block = LOTRMod.yamCrop;
            final int meta = 8;
            if (world.getBlock(i2, j2 - 1, k2).canSustainPlant((IBlockAccess)world, i2, j2 - 1, k2, ForgeDirection.UP, (IPlantable)Blocks.tallgrass) && world.isAirBlock(i2, j2, k2)) {
                world.setBlock(i2, j2, k2, block, meta, 2);
            }
        }
        return true;
    }
}
