// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSkullPile extends WorldGenerator
{
    public LOTRWorldGenSkullPile() {
        super(false);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 4; ++l) {
            final int i2 = i - 4 + random.nextInt(9);
            final int k2 = k - 4 + random.nextInt(9);
            final int j2 = world.getHeightValue(i2, k2);
            if (world.getBlock(i2, j2 - 1, k2).isOpaqueCube() && world.getBlock(i2, j2, k2).isReplaceable((IBlockAccess)world, i2, j2, k2)) {
                world.setBlock(i2, j2, k2, Blocks.skull, 1, 2);
                final TileEntity tileentity = world.getTileEntity(i2, j2, k2);
                if (tileentity instanceof TileEntitySkull) {
                    final TileEntitySkull skull = (TileEntitySkull)tileentity;
                    skull.func_145903_a(random.nextInt(16));
                }
            }
        }
        return true;
    }
}
