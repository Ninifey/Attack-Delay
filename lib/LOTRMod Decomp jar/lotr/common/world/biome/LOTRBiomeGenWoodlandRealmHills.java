// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenWoodlandRealmHills extends LOTRBiomeGenWoodlandRealm
{
    public LOTRBiomeGenWoodlandRealmHills(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.decorator.treesPerChunk = 4;
        super.decorator.grassPerChunk = 10;
        super.decorator.addTree(LOTRTreeType.GREEN_OAK_EXTREME, 500);
    }
}
