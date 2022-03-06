// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;

public class LOTRBiomeGenForodwaithGlacier extends LOTRBiomeGenForodwaithMountains
{
    public LOTRBiomeGenForodwaithGlacier(final int i, final boolean major) {
        super(i, major);
        super.topBlock = Blocks.ice;
        super.fillerBlock = Blocks.ice;
    }
}
