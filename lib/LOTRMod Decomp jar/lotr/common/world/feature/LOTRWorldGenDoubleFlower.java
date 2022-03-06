// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.BlockDoublePlant;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenDoubleFlower extends WorldGenerator
{
    private int flowerType;
    
    public void setFlowerType(final int i) {
        this.flowerType = i;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        boolean flag = false;
        for (int l = 0; l < 64; ++l) {
            final int i2 = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j + random.nextInt(4) - random.nextInt(4);
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            if (world.isAirBlock(i2, j2, k2) && (!world.provider.hasNoSky || j2 < 254) && LOTRMod.doubleFlower.canPlaceBlockAt(world, i2, j2, k2)) {
                ((BlockDoublePlant)LOTRMod.doubleFlower).func_149889_c(world, i2, j2, k2, this.flowerType, 2);
                flag = true;
            }
        }
        return flag;
    }
}
