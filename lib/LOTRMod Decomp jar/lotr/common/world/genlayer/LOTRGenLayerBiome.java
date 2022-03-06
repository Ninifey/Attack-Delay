// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import java.util.Arrays;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRGenLayerBiome extends LOTRGenLayer
{
    private BiomeGenBase theBiome;
    
    public LOTRGenLayerBiome(final BiomeGenBase biome) {
        super(0L);
        this.theBiome = biome;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        Arrays.fill(ints, this.theBiome.biomeID);
        return ints;
    }
}
