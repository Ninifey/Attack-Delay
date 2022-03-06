// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import lotr.common.world.LOTRWorldProviderUtumno;

public class LOTRBlockUtumnoPillar extends LOTRBlockPillarBase implements LOTRWorldProviderUtumno.UtumnoBlock
{
    public LOTRBlockUtumnoPillar() {
        this.setPillarNames("fire", "ice", "obsidian");
        this.setHardness(1.5f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
}
