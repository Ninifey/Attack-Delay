// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import java.util.Iterator;
import java.util.List;
import cpw.mods.fml.common.FMLLog;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;
import lotr.common.LOTRDimension;

public class LOTRGenLayerRemoveMapRivers extends LOTRGenLayer
{
    private static int MAX_PIXEL_RANGE;
    private LOTRDimension dimension;
    
    public LOTRGenLayerRemoveMapRivers(final long l, final LOTRGenLayer biomes, final LOTRDimension dim) {
        super(l);
        super.lotrParent = biomes;
        this.dimension = dim;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int maxRange = LOTRGenLayerRemoveMapRivers.MAX_PIXEL_RANGE;
        final int[] biomes = super.lotrParent.getInts(world, i - maxRange, k - maxRange, xSize + maxRange * 2, zSize + maxRange * 2);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int biomeID = biomes[i2 + maxRange + (k2 + maxRange) * (xSize + maxRange * 2)];
                if (biomeID == LOTRBiome.river.biomeID) {
                    int replaceID = -1;
                    for (int range = 1; range <= maxRange; ++range) {
                        final Map<Integer, Integer> viableBiomes = new HashMap<Integer, Integer>();
                        final Map<Integer, Integer> viableBiomesWateryAdjacent = new HashMap<Integer, Integer>();
                        for (int k3 = k2 - range; k3 <= k2 + range; ++k3) {
                            for (int i3 = i2 - range; i3 <= i2 + range; ++i3) {
                                if (Math.abs(i3 - i2) == range || Math.abs(k3 - k2) == range) {
                                    final int subIndex = i3 + maxRange + (k3 + maxRange) * (xSize + maxRange * 2);
                                    final int subBiomeID = biomes[subIndex];
                                    final LOTRBiome subBiome = this.dimension.biomeList[subBiomeID];
                                    if (subBiome != LOTRBiome.river) {
                                        final boolean wateryAdjacent = subBiome.isWateryBiome() && range == 1;
                                        final Map<Integer, Integer> srcMap = wateryAdjacent ? viableBiomesWateryAdjacent : viableBiomes;
                                        int count = 0;
                                        if (srcMap.containsKey(subBiomeID)) {
                                            count = srcMap.get(subBiomeID);
                                        }
                                        ++count;
                                        srcMap.put(subBiomeID, count);
                                    }
                                }
                            }
                        }
                        Map<Integer, Integer> priorityMap = viableBiomes;
                        if (!viableBiomesWateryAdjacent.isEmpty()) {
                            priorityMap = viableBiomesWateryAdjacent;
                        }
                        if (!priorityMap.isEmpty()) {
                            final List<Integer> maxCountBiomes = new ArrayList<Integer>();
                            int maxCount = 0;
                            for (final Map.Entry<Integer, Integer> e : priorityMap.entrySet()) {
                                final int id = e.getKey();
                                final int count2 = e.getValue();
                                if (count2 > maxCount) {
                                    maxCount = count2;
                                }
                            }
                            for (final Map.Entry<Integer, Integer> e : priorityMap.entrySet()) {
                                final int id = e.getKey();
                                final int count2 = e.getValue();
                                if (count2 == maxCount) {
                                    maxCountBiomes.add(id);
                                }
                            }
                            replaceID = maxCountBiomes.get(this.nextInt(maxCountBiomes.size()));
                            break;
                        }
                    }
                    if (replaceID == -1) {
                        FMLLog.warning("WARNING! LOTR map generation failed to replace map river at %d, %d", new Object[] { i, k });
                        ints[i2 + k2 * xSize] = 0;
                    }
                    else {
                        ints[i2 + k2 * xSize] = replaceID;
                    }
                }
                else {
                    ints[i2 + k2 * xSize] = biomeID;
                }
            }
        }
        return ints;
    }
    
    static {
        LOTRGenLayerRemoveMapRivers.MAX_PIXEL_RANGE = 4;
    }
}
