// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class LOTRBiomeGenNearHaradRed extends LOTRBiomeGenNearHarad
{
    public LOTRBiomeGenNearHaradRed(final int i, final boolean major) {
        super(i, major);
        this.setDisableRain();
        super.topBlock = (Block)Blocks.sand;
        super.topBlockMeta = 1;
        super.fillerBlock = (Block)Blocks.sand;
        super.fillerBlockMeta = 1;
        super.decorator.clearRandomStructures();
        super.decorator.clearVillages();
    }
}
