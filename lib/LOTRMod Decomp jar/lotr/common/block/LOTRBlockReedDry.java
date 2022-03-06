// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.World;

public class LOTRBlockReedDry extends LOTRBlockReed
{
    @Override
    protected boolean canReedGrow(final World world, final int i, final int j, final int k) {
        return false;
    }
}
