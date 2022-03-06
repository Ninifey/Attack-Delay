// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;

public class LOTRBlockMordorPlant extends LOTRBlockFlower
{
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
    }
}
