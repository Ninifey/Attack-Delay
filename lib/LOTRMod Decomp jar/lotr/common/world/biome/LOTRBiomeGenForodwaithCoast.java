// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;

public class LOTRBiomeGenForodwaithCoast extends LOTRBiomeGenForodwaith
{
    public LOTRBiomeGenForodwaithCoast(final int i, final boolean major) {
        super(i, major);
        super.topBlock = Blocks.stone;
        super.topBlockMeta = 0;
        super.fillerBlock = super.topBlock;
        super.fillerBlockMeta = super.topBlockMeta;
        super.biomeTerrain.setXZScale(30.0);
        this.clearBiomeVariants();
        super.decorator.clearRandomStructures();
    }
}
