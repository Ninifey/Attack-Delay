// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBiomeGenGreyMountainsFoothills extends LOTRBiomeGenGreyMountains
{
    public LOTRBiomeGenGreyMountainsFoothills(final int i, final boolean major) {
        super(i, major);
        super.decorator.biomeGemFactor = 0.75f;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
    }
}
