// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.block.Block;
import lotr.common.world.biome.LOTRBiome;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRRoadGenerator
{
    public static final int ROAD_DEPTH = 4;
    
    public static boolean generateRoad(final World world, final Random rand, final int i, final int k, final LOTRBiome biome, final Block[] blocks, final byte[] metadata, final double[] heightNoise) {
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        final LOTRRoadType roadType = biome.getRoadBlock();
        final LOTRRoadType.BridgeType bridgeType = biome.getBridgeBlock();
        if (LOTRRoads.isRoadAt(i, k)) {
            int roadTop = 0;
            int bridgeBase = 0;
            boolean bridge = false;
            boolean bridgeSlab = false;
            for (int j = ySize - 1; j > 0; --j) {
                final int index = xzIndex * ySize + j;
                final Block block = blocks[index];
                if (block.isOpaqueCube()) {
                    roadTop = j;
                    bridge = false;
                    break;
                }
                if (block.getMaterial().isLiquid()) {
                    roadTop = (bridgeBase = j + 1);
                    final int maxBridgeTop = j + 6;
                    float bridgeHeight = 0.0f;
                    for (int j2 = j - 1; j2 > 0; --j2) {
                        final int indexLower = xzIndex * ySize + j2;
                        final Block blockLower = blocks[indexLower];
                        if (!blockLower.getMaterial().isLiquid()) {
                            break;
                        }
                        bridgeHeight += 0.5f;
                    }
                    final int bridgeHeightInt = (int)Math.floor(bridgeHeight);
                    roadTop += bridgeHeightInt;
                    roadTop = Math.min(roadTop, maxBridgeTop);
                    if (roadTop >= maxBridgeTop) {
                        bridgeSlab = true;
                    }
                    else {
                        final float bridgeHeightR = bridgeHeight - bridgeHeightInt;
                        if (bridgeHeightR < 0.5f) {
                            bridgeSlab = true;
                        }
                    }
                    bridge = true;
                    break;
                }
            }
            if (bridge) {
                final LOTRRoadType.RoadBlock bridgeBlock = bridgeType.getBlock(rand, false);
                final LOTRRoadType.RoadBlock bridgeBlockSlab = bridgeType.getBlock(rand, true);
                final LOTRRoadType.RoadBlock bridgeEdge = bridgeType.getEdge(rand);
                final LOTRRoadType.RoadBlock bridgeFence = bridgeType.getFence(rand);
                final boolean fence = isFenceAt(i, k);
                final int index2 = xzIndex * ySize + roadTop;
                if (fence) {
                    final boolean pillar = isPillarAt(i, k);
                    if (pillar) {
                        for (int l = roadTop + 4; l > 0; --l) {
                            final int pillarIndex = xzIndex * ySize + l;
                            final Block block2 = blocks[pillarIndex];
                            if (block2.isOpaqueCube()) {
                                break;
                            }
                            if (l >= roadTop + 4) {
                                blocks[pillarIndex] = bridgeFence.block;
                                metadata[pillarIndex] = (byte)bridgeFence.meta;
                            }
                            else if (l >= roadTop + 3) {
                                blocks[pillarIndex] = bridgeBlock.block;
                                metadata[pillarIndex] = (byte)bridgeBlock.meta;
                            }
                            else {
                                blocks[pillarIndex] = bridgeEdge.block;
                                metadata[pillarIndex] = (byte)bridgeEdge.meta;
                            }
                        }
                    }
                    else {
                        blocks[index2] = bridgeEdge.block;
                        metadata[index2] = (byte)bridgeEdge.meta;
                        final int indexUpper = index2 + 1;
                        blocks[indexUpper] = bridgeFence.block;
                        metadata[indexUpper] = (byte)bridgeFence.meta;
                        if (roadTop > bridgeBase) {
                            final int indexLower2 = index2 - 1;
                            blocks[indexLower2] = bridgeEdge.block;
                            metadata[indexLower2] = (byte)bridgeEdge.meta;
                        }
                        final int support = bridgeBase + 2;
                        if (roadTop - 1 > support) {
                            final int indexSupport = xzIndex * ySize + support;
                            blocks[indexSupport] = bridgeFence.block;
                            metadata[indexSupport] = (byte)bridgeFence.meta;
                        }
                    }
                }
                else {
                    if (bridgeSlab) {
                        blocks[index2] = bridgeBlockSlab.block;
                        metadata[index2] = (byte)bridgeBlockSlab.meta;
                    }
                    else {
                        blocks[index2] = bridgeBlock.block;
                        metadata[index2] = (byte)bridgeBlock.meta;
                    }
                    if (roadTop > bridgeBase) {
                        final int indexLower = index2 - 1;
                        blocks[indexLower] = bridgeBlock.block;
                        metadata[indexLower] = (byte)bridgeBlock.meta;
                    }
                }
            }
            else {
                for (int j = roadTop; j > roadTop - 4 && j > 0; --j) {
                    final int index = xzIndex * ySize + j;
                    final float repair = roadType.getRepair();
                    if (rand.nextFloat() < repair) {
                        final boolean isTop = j == roadTop;
                        boolean isSlab = false;
                        if (isTop && j >= 63) {
                            final double avgNoise = (heightNoise[index] + heightNoise[index + 1]) / 2.0;
                            isSlab = (avgNoise < 0.0);
                        }
                        final LOTRRoadType.RoadBlock roadblock = roadType.getBlock(rand, biome, isTop, isSlab);
                        blocks[index] = roadblock.block;
                        metadata[index] = (byte)roadblock.meta;
                    }
                }
            }
            return true;
        }
        if (roadType.hasFlowers()) {
            int roadTop = 0;
            for (int m = ySize - 1; m > 0; --m) {
                final int index3 = xzIndex * ySize + m;
                final Block block3 = blocks[index3];
                if (block3.isOpaqueCube()) {
                    roadTop = m;
                    break;
                }
            }
            boolean adjRoad = false;
        Label_0967:
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    if (i2 != 0 || k2 != 0) {
                        if (LOTRRoads.isRoadAt(i + i2, k + k2)) {
                            adjRoad = true;
                            break Label_0967;
                        }
                    }
                }
            }
            if (adjRoad) {
                final int index3 = xzIndex * ySize + roadTop + 1;
                final BiomeGenBase.FlowerEntry flower = biome.getRandomFlower(rand);
                blocks[index3] = flower.block;
                metadata[index3] = (byte)flower.metadata;
            }
            else {
                adjRoad = false;
            Label_1092:
                for (int i2 = -3; i2 <= 3; ++i2) {
                    for (int k2 = -3; k2 <= 3; ++k2) {
                        if (Math.abs(i2) > 2 || Math.abs(k2) > 2) {
                            if (LOTRRoads.isRoadAt(i + i2, k + k2)) {
                                adjRoad = true;
                                break Label_1092;
                            }
                        }
                    }
                }
                if (adjRoad) {
                    final int index3 = xzIndex * ySize + roadTop + 1;
                    blocks[index3] = (Block)Blocks.leaves;
                    metadata[index3] = 4;
                }
            }
            return true;
        }
        return false;
    }
    
    private static boolean isFenceAt(final int i, final int k) {
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                if (i2 != 0 || k2 != 0) {
                    if (!LOTRRoads.isRoadAt(i + i2, k + k2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean isPillarAt(final int i, final int k) {
        final int pRange = 8;
        final int xmod = IntMath.mod(i, pRange);
        final int zmod = IntMath.mod(k, pRange);
        return IntMath.mod(xmod + zmod, pRange) == 0 && !isBridgeEdgePillar(i + 1, k - 1) && !isBridgeEdgePillar(i + 1, k + 1);
    }
    
    private static boolean isBridgeEdgePillar(final int i, final int k) {
        return LOTRRoads.isRoadAt(i, k) && isFenceAt(i, k) && isPillarAt(i, k);
    }
}
