// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiomeGenBeach;
import lotr.common.world.biome.LOTRBiomeGenFarHaradVolcano;
import lotr.common.world.biome.LOTRBiomeGenFarHaradCoast;
import lotr.common.world.biome.LOTRBiomeGenForodwaith;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenLindon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRDimension;

public class LOTRGenLayerBeach extends LOTRGenLayer
{
    private LOTRDimension dimension;
    private BiomeGenBase targetBiome;
    
    public LOTRGenLayerBeach(final long l, final LOTRGenLayer layer, final LOTRDimension dim, final BiomeGenBase target) {
        super(l);
        super.lotrParent = layer;
        this.dimension = dim;
        this.targetBiome = target;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] biomes = super.lotrParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int biomeID = biomes[i2 + 1 + (k2 + 1) * (xSize + 2)];
                final LOTRBiome biome = this.dimension.biomeList[biomeID];
                int newBiomeID = biomeID;
                if (biomeID != this.targetBiome.biomeID && !biome.isWateryBiome()) {
                    final int biome2 = biomes[i2 + 1 + (k2 + 1 - 1) * (xSize + 2)];
                    final int biome3 = biomes[i2 + 1 + 1 + (k2 + 1) * (xSize + 2)];
                    final int biome4 = biomes[i2 + 1 - 1 + (k2 + 1) * (xSize + 2)];
                    final int biome5 = biomes[i2 + 1 + (k2 + 1 + 1) * (xSize + 2)];
                    if (biome2 == this.targetBiome.biomeID || biome3 == this.targetBiome.biomeID || biome4 == this.targetBiome.biomeID || biome5 == this.targetBiome.biomeID) {
                        if (biome instanceof LOTRBiomeGenLindon) {
                            if (this.nextInt(3) == 0) {
                                newBiomeID = LOTRBiome.lindonCoast.biomeID;
                            }
                            else {
                                newBiomeID = LOTRBiome.beachWhite.biomeID;
                            }
                        }
                        else if (biome instanceof LOTRBiomeGenForodwaith) {
                            newBiomeID = LOTRBiome.forodwaithCoast.biomeID;
                        }
                        else if (biome instanceof LOTRBiomeGenFarHaradCoast) {
                            newBiomeID = biomeID;
                        }
                        else if (biome instanceof LOTRBiomeGenFarHaradVolcano) {
                            newBiomeID = biomeID;
                        }
                        else if (!(biome instanceof LOTRBiomeGenBeach)) {
                            if (biome.decorator.whiteSand) {
                                newBiomeID = LOTRBiome.beachWhite.biomeID;
                            }
                            else if (this.nextInt(20) == 0) {
                                newBiomeID = LOTRBiome.beachGravel.biomeID;
                            }
                            else {
                                newBiomeID = LOTRBiome.beach.biomeID;
                            }
                        }
                    }
                }
                ints[i2 + k2 * xSize] = newBiomeID;
            }
        }
        return ints;
    }
}
