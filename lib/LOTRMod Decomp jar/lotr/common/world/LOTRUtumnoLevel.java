// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.LOTRMod;
import java.util.List;
import java.util.Collection;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import java.util.Random;
import net.minecraft.block.Block;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public enum LOTRUtumnoLevel
{
    ICE(13819887, 180, 240, 4, 4), 
    OBSIDIAN(2104109, 92, 180, 6, 5), 
    FIRE(6295040, 0, 92, 8, 7);
    
    public final int fogColor;
    public final int baseLevel;
    public final int topLevel;
    public final int corridorWidth;
    public final int corridorWidthStart;
    public final int corridorWidthEnd;
    public final int corridorHeight;
    public final int[] corridorBaseLevels;
    private static boolean initSpawnLists;
    private LOTRBiomeSpawnList npcSpawnList;
    public Block brickBlock;
    public int brickMeta;
    public Block brickStairBlock;
    public Block brickGlowBlock;
    public int brickGlowMeta;
    public Block tileBlock;
    public int tileMeta;
    public Block pillarBlock;
    public int pillarMeta;
    private static Random lightRand;
    private static NoiseGeneratorPerlin noiseGenXZ;
    private static NoiseGeneratorPerlin noiseGenY;
    private static NoiseGeneratorPerlin corridorNoiseY;
    private static NoiseGeneratorPerlin corridorNoiseX;
    private static NoiseGeneratorPerlin corridorNoiseZ;
    
    private LOTRUtumnoLevel(final int fog, final int base, final int top, final int cWidth, final int cHeight) {
        this.npcSpawnList = new LOTRBiomeSpawnList("UtumnoLevel_" + this.name());
        this.fogColor = fog;
        this.baseLevel = base;
        this.topLevel = top;
        this.corridorWidth = cWidth;
        this.corridorWidthStart = 8 - cWidth / 2;
        this.corridorWidthEnd = this.corridorWidthStart + cWidth;
        this.corridorHeight = cHeight;
        final List<Integer> baseLevels = new ArrayList<Integer>();
        int y = this.baseLevel;
        while (true) {
            y += this.corridorHeight * 2;
            if (y >= top - 5) {
                break;
            }
            baseLevels.add(y);
        }
        this.corridorBaseLevels = Ints.toArray((Collection)baseLevels);
    }
    
    public int getLowestCorridorFloor() {
        return this.corridorBaseLevels[0] - 1;
    }
    
    public int getHighestCorridorRoof() {
        return this.corridorBaseLevels[this.corridorBaseLevels.length - 1] + this.corridorHeight;
    }
    
    public LOTRBiomeSpawnList getNPCSpawnList() {
        return this.npcSpawnList;
    }
    
    public static LOTRUtumnoLevel forY(final int y) {
        for (final LOTRUtumnoLevel level : values()) {
            if (y >= level.baseLevel) {
                return level;
            }
        }
        return LOTRUtumnoLevel.FIRE;
    }
    
    public static void setupLevels() {
        if (LOTRUtumnoLevel.initSpawnLists) {
            return;
        }
        LOTRUtumnoLevel.ICE.brickBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.ICE.brickMeta = 2;
        LOTRUtumnoLevel.ICE.brickStairBlock = LOTRMod.stairsUtumnoBrickIce;
        LOTRUtumnoLevel.ICE.brickGlowBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.ICE.brickGlowMeta = 3;
        LOTRUtumnoLevel.ICE.tileBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.ICE.tileMeta = 6;
        LOTRUtumnoLevel.ICE.pillarBlock = LOTRMod.utumnoPillar;
        LOTRUtumnoLevel.ICE.pillarMeta = 1;
        LOTRUtumnoLevel.OBSIDIAN.brickBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.OBSIDIAN.brickMeta = 4;
        LOTRUtumnoLevel.OBSIDIAN.brickStairBlock = LOTRMod.stairsUtumnoBrickObsidian;
        LOTRUtumnoLevel.OBSIDIAN.brickGlowBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.OBSIDIAN.brickGlowMeta = 5;
        LOTRUtumnoLevel.OBSIDIAN.tileBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.OBSIDIAN.tileMeta = 7;
        LOTRUtumnoLevel.OBSIDIAN.pillarBlock = LOTRMod.utumnoPillar;
        LOTRUtumnoLevel.OBSIDIAN.pillarMeta = 2;
        LOTRUtumnoLevel.FIRE.brickBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.FIRE.brickMeta = 0;
        LOTRUtumnoLevel.FIRE.brickStairBlock = LOTRMod.stairsUtumnoBrickFire;
        LOTRUtumnoLevel.FIRE.brickGlowBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.FIRE.brickGlowMeta = 1;
        LOTRUtumnoLevel.FIRE.tileBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.FIRE.tileMeta = 8;
        LOTRUtumnoLevel.FIRE.pillarBlock = LOTRMod.utumnoPillar;
        LOTRUtumnoLevel.FIRE.pillarMeta = 0;
        LOTRUtumnoLevel.ICE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_ICE, 10));
        LOTRUtumnoLevel.OBSIDIAN.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_OBSIDIAN, 10));
        LOTRUtumnoLevel.FIRE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_FIRE, 10));
        LOTRUtumnoLevel.initSpawnLists = true;
    }
    
    public static void generateTerrain(final World world, final Random rand, final int chunkX, final int chunkZ, final Block[] blocks, final byte[] metadata) {
        final boolean hugeHoleChunk = rand.nextInt(16) == 0;
        final boolean hugeRavineChunk = rand.nextInt(16) == 0;
        long seed = world.getSeed();
        seed *= chunkX / 2 * 67839703L + chunkZ / 2 * 368093693L;
        LOTRUtumnoLevel.lightRand.setSeed(seed);
        final boolean chunkHasGlowing = LOTRUtumnoLevel.lightRand.nextInt(4) > 0;
        for (int i = 0; i < 16; ++i) {
            for (int k = 0; k < 16; ++k) {
                final int blockX = chunkX * 16 + i;
                final int blockZ = chunkZ * 16 + k;
                final double genNoiseXZHere = LOTRUtumnoLevel.noiseGenXZ.func_151601_a(blockX * 0.2, blockZ * 0.2);
                final double corridorNoiseYHere = LOTRUtumnoLevel.corridorNoiseY.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + LOTRUtumnoLevel.corridorNoiseY.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
                final double corridorNoiseXHere = LOTRUtumnoLevel.corridorNoiseX.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + LOTRUtumnoLevel.corridorNoiseX.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
                final double corridorNoiseZHere = LOTRUtumnoLevel.corridorNoiseZ.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + LOTRUtumnoLevel.corridorNoiseZ.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
                for (int j = 255; j >= 0; --j) {
                    final LOTRUtumnoLevel utumnoLevel = forY(j);
                    final int blockY = j;
                    final int blockIndex = (k * 16 + i) * 256 + j;
                    if (j <= 0 + rand.nextInt(5) || j >= 255 - rand.nextInt(3)) {
                        blocks[blockIndex] = Blocks.bedrock;
                    }
                    else {
                        final double genNoiseYHere = LOTRUtumnoLevel.noiseGenY.func_151601_a(blockY * 0.4, 0.0);
                        final double genNoise = (genNoiseXZHere + genNoiseYHere * 0.5) / 1.5;
                        if (genNoise > -0.2) {
                            blocks[blockIndex] = utumnoLevel.brickBlock;
                            metadata[blockIndex] = (byte)utumnoLevel.brickMeta;
                            if (chunkHasGlowing) {
                                boolean glowing = false;
                                if (utumnoLevel == LOTRUtumnoLevel.ICE && rand.nextInt(16) == 0) {
                                    glowing = true;
                                }
                                else if (utumnoLevel == LOTRUtumnoLevel.OBSIDIAN && rand.nextInt(12) == 0) {
                                    glowing = true;
                                }
                                else if (utumnoLevel == LOTRUtumnoLevel.FIRE && rand.nextInt(8) == 0) {
                                    glowing = true;
                                }
                                if (glowing) {
                                    blocks[blockIndex] = utumnoLevel.brickGlowBlock;
                                    metadata[blockIndex] = (byte)utumnoLevel.brickGlowMeta;
                                }
                            }
                        }
                        else if (utumnoLevel == LOTRUtumnoLevel.ICE) {
                            if (genNoise < -0.5) {
                                blocks[blockIndex] = Blocks.stone;
                                metadata[blockIndex] = 0;
                            }
                            else {
                                blocks[blockIndex] = Blocks.packed_ice;
                                metadata[blockIndex] = 0;
                            }
                        }
                        else if (utumnoLevel == LOTRUtumnoLevel.OBSIDIAN) {
                            if (genNoise < -0.5) {
                                blocks[blockIndex] = Blocks.stained_hardened_clay;
                                metadata[blockIndex] = 15;
                            }
                            else {
                                blocks[blockIndex] = Blocks.obsidian;
                                metadata[blockIndex] = 0;
                            }
                        }
                        else if (utumnoLevel == LOTRUtumnoLevel.FIRE) {
                            blocks[blockIndex] = Blocks.obsidian;
                            metadata[blockIndex] = 0;
                        }
                        final int levelFuzz = 2;
                        if (j <= utumnoLevel.getLowestCorridorFloor() - levelFuzz || j >= utumnoLevel.getHighestCorridorRoof() + levelFuzz) {
                            blocks[blockIndex] = utumnoLevel.brickBlock;
                            metadata[blockIndex] = (byte)utumnoLevel.brickMeta;
                        }
                        if (genNoise < 0.5) {
                            for (final int corridorBase : utumnoLevel.corridorBaseLevels) {
                                if (j == corridorBase - 1) {
                                    blocks[blockIndex] = utumnoLevel.tileBlock;
                                    metadata[blockIndex] = (byte)utumnoLevel.tileMeta;
                                }
                            }
                        }
                    }
                    int actingY = j;
                    actingY += (int)Math.round(corridorNoiseYHere * 1.15);
                    actingY = MathHelper.clamp_int(actingY, 0, 255);
                    int actingX = blockX;
                    int actingZ = blockZ;
                    actingX += (int)Math.round(corridorNoiseXHere * 1.7);
                    actingZ += (int)Math.round(corridorNoiseZHere * 1.7);
                    final int actingXInChunk = actingX & 0xF;
                    final int actingZInChunk = actingZ & 0xF;
                    final int actingChunkX = actingX / 16;
                    final int actingChunkZ = actingZ / 16;
                    final boolean carveHugeHole = hugeHoleChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
                    final boolean carveHugeRavine = hugeRavineChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
                    boolean carveCorridor = false;
                    for (final int corridorBase2 : utumnoLevel.corridorBaseLevels) {
                        carveCorridor = (actingY >= corridorBase2 && actingY < corridorBase2 + utumnoLevel.corridorHeight);
                        if (carveCorridor) {
                            break;
                        }
                    }
                    if (carveHugeHole && chunkX % 2 == 0 && chunkZ % 2 == 0) {
                        if (i >= utumnoLevel.corridorWidthStart + 1 && i <= utumnoLevel.corridorWidthEnd - 1 && k >= utumnoLevel.corridorWidthStart + 1 && k <= utumnoLevel.corridorWidthEnd - 1) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                        else if (i >= utumnoLevel.corridorWidthStart && i <= utumnoLevel.corridorWidthEnd && k >= utumnoLevel.corridorWidthStart && k <= utumnoLevel.corridorWidthEnd) {
                            blocks[blockIndex] = utumnoLevel.brickGlowBlock;
                            metadata[blockIndex] = (byte)utumnoLevel.brickGlowMeta;
                        }
                    }
                    if (chunkX % 2 == 0) {
                        if (carveCorridor && actingZInChunk >= utumnoLevel.corridorWidthStart && actingZInChunk <= utumnoLevel.corridorWidthEnd) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                        if (carveHugeRavine && actingXInChunk >= utumnoLevel.corridorWidthStart + 1 && actingXInChunk <= utumnoLevel.corridorWidthEnd - 1) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                    }
                    if (chunkZ % 2 == 0) {
                        if (carveCorridor && actingXInChunk >= utumnoLevel.corridorWidthStart && actingXInChunk <= utumnoLevel.corridorWidthEnd) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                        if (carveHugeRavine && actingZInChunk >= utumnoLevel.corridorWidthStart + 1 && actingZInChunk <= utumnoLevel.corridorWidthEnd - 1) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                    }
                }
            }
        }
    }
    
    static {
        LOTRUtumnoLevel.initSpawnLists = false;
        LOTRUtumnoLevel.lightRand = new Random();
        LOTRUtumnoLevel.noiseGenXZ = new NoiseGeneratorPerlin(new Random(5628506078940526L), 1);
        LOTRUtumnoLevel.noiseGenY = new NoiseGeneratorPerlin(new Random(1820268708369704034L), 1);
        LOTRUtumnoLevel.corridorNoiseY = new NoiseGeneratorPerlin(new Random(89230369345425L), 1);
        LOTRUtumnoLevel.corridorNoiseX = new NoiseGeneratorPerlin(new Random(824595069307073L), 1);
        LOTRUtumnoLevel.corridorNoiseZ = new NoiseGeneratorPerlin(new Random(759206035530266067L), 1);
    }
}
