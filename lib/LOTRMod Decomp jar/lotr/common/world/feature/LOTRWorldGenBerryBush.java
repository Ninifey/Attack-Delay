// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.block.LOTRBlockBerryBush;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBerryBush extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final Block bush = LOTRMod.berryBush;
        final LOTRBlockBerryBush.BushType bushType = LOTRBlockBerryBush.BushType.randomType(random);
        int bushMeta = bushType.bushMeta;
        bushMeta = LOTRBlockBerryBush.setHasBerries(bushMeta, true);
        if (bushType.poisonous && random.nextInt(2) != 0) {
            return false;
        }
        for (int l = 0; l < 12; ++l) {
            final int i2 = i - random.nextInt(4) + random.nextInt(4);
            final int j2 = j - random.nextInt(2) + random.nextInt(2);
            final int k2 = k - random.nextInt(4) + random.nextInt(4);
            final Block below = world.getBlock(i2, j2 - 1, k2);
            final Block block = world.getBlock(i2, j2, k2);
            if (below.canSustainPlant((IBlockAccess)world, i2, j2 - 1, k2, ForgeDirection.UP, (IPlantable)Blocks.sapling) && !block.getMaterial().isLiquid() && block.isReplaceable((IBlockAccess)world, i2, j2, k2)) {
                world.setBlock(i2, j2, k2, bush, bushMeta, 2);
            }
        }
        return true;
    }
}
