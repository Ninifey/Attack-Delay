// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import lotr.common.world.LOTRWorldProviderUtumno;

public abstract class LOTRBlockUtumnoSlabBase extends LOTRBlockSlabBase implements LOTRWorldProviderUtumno.UtumnoBlock
{
    public LOTRBlockUtumnoSlabBase(final boolean flag, final int n) {
        super(flag, Material.rock, n);
        this.setHardness(1.5f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
}
