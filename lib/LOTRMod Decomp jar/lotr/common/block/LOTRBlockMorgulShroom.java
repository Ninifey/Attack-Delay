// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRBlockMorgulShroom extends LOTRBlockMordorPlant
{
    public LOTRBlockMorgulShroom() {
        final float f = 0.2f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
        this.setTickRandomly(true);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (random.nextInt(10) == 0) {
            boolean nearbyWater = false;
            for (int i2 = i - 2; i2 <= i + 2 && !nearbyWater; ++i2) {
                for (int j2 = j - 2; j2 <= j + 2 && !nearbyWater; ++j2) {
                    for (int k2 = k - 2; k2 <= k + 2 && !nearbyWater; ++k2) {
                        if (world.getBlock(i2, j - 1, k2).getMaterial() == Material.water) {
                            nearbyWater = true;
                        }
                    }
                }
            }
            if (nearbyWater) {
                final int i2 = i - 1 + random.nextInt(3);
                final int j2 = j - 1 + random.nextInt(3);
                final int k2 = k - 1 + random.nextInt(3);
                if (world.isAirBlock(i2, j2, k2) && this.canBlockStay(world, i2, j2, k2)) {
                    world.setBlock(i2, j2, k2, (Block)this);
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }
}
