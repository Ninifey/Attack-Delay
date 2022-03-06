// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.world.ChunkPosition;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.BlockFalling;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.Chunk;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import lotr.common.world.mapgen.LOTRMapGenCavesUtumno;
import lotr.common.world.mapgen.LOTRMapGenCaves;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRChunkProviderUtumno implements IChunkProvider
{
    private World worldObj;
    private Random rand;
    private BiomeGenBase[] biomesForGeneration;
    private LOTRBiomeVariant[] variantsForGeneration;
    private LOTRMapGenCaves caveGenerator;
    
    public LOTRChunkProviderUtumno(final World world, final long l) {
        this.caveGenerator = new LOTRMapGenCavesUtumno();
        this.worldObj = world;
        this.rand = new Random(l);
        LOTRUtumnoLevel.setupLevels();
    }
    
    private void generateTerrain(final int chunkX, final int chunkZ, final Block[] blocks, final byte[] metadata) {
        Arrays.fill(blocks, Blocks.air);
        LOTRUtumnoLevel.generateTerrain(this.worldObj, this.rand, chunkX, chunkZ, blocks, metadata);
    }
    
    public Chunk loadChunk(final int i, final int j) {
        return this.provideChunk(i, j);
    }
    
    public Chunk provideChunk(final int i, final int k) {
        this.rand.setSeed(i * 341873128712L + k * 132897987541L);
        final LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.getWorldChunkManager();
        final Block[] blocks = new Block[65536];
        final byte[] metadata = new byte[65536];
        this.generateTerrain(i, k, blocks, metadata);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, i * 16, k * 16, 16, 16);
        this.variantsForGeneration = chunkManager.getBiomeVariants(this.variantsForGeneration, i * 16, k * 16, 16, 16);
        this.caveGenerator.func_151539_a((IChunkProvider)this, this.worldObj, i, k, blocks);
        final Chunk chunk = new Chunk(this.worldObj, i, k);
        final ExtendedBlockStorage[] blockStorage = chunk.getBlockStorageArray();
        for (int i2 = 0; i2 < 16; ++i2) {
            for (int k2 = 0; k2 < 16; ++k2) {
                for (int j1 = 0; j1 < 256; ++j1) {
                    final int blockIndex = i2 << 12 | k2 << 8 | j1;
                    final Block block = blocks[blockIndex];
                    if (block != null) {
                        if (block != Blocks.air) {
                            final byte meta = metadata[blockIndex];
                            final int j2 = j1 >> 4;
                            if (blockStorage[j2] == null) {
                                blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                            }
                            blockStorage[j2].func_150818_a(i2, j1 & 0xF, k2, block);
                            blockStorage[j2].setExtBlockMetadata(i2, j1 & 0xF, k2, meta & 0xF);
                        }
                    }
                }
            }
        }
        final byte[] biomes = chunk.getBiomeArray();
        for (int l = 0; l < biomes.length; ++l) {
            biomes[l] = (byte)this.biomesForGeneration[l].biomeID;
        }
        final byte[] variants = new byte[256];
        for (int m = 0; m < variants.length; ++m) {
            variants[m] = (byte)this.variantsForGeneration[m].variantID;
        }
        LOTRBiomeVariantStorage.setChunkBiomeVariants(this.worldObj, chunk, variants);
        chunk.resetRelightChecks();
        return chunk;
    }
    
    public boolean chunkExists(final int i, final int j) {
        return true;
    }
    
    public void populate(final IChunkProvider ichunkprovider, final int chunkX, final int chunkZ) {
        BlockFalling.fallInstantly = true;
        final int i = chunkX * 16;
        final int k = chunkZ * 16;
        final BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(i + 16, k + 16);
        if (biomegenbase instanceof LOTRBiome) {
            final LOTRBiome biome = (LOTRBiome)biomegenbase;
            this.rand.setSeed(this.worldObj.getSeed());
            final long l1 = this.rand.nextLong() / 2L * 2L + 1L;
            final long l2 = this.rand.nextLong() / 2L * 2L + 1L;
            this.rand.setSeed(chunkX * l1 + chunkZ * l2 ^ this.worldObj.getSeed());
            biome.decorate(this.worldObj, this.rand, i, k);
            BlockFalling.fallInstantly = false;
        }
    }
    
    public boolean saveChunks(final boolean flag, final IProgressUpdate update) {
        return true;
    }
    
    public void saveExtraData() {
    }
    
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    public boolean canSave() {
        return true;
    }
    
    public String makeString() {
        return "UtumnoLevelSource";
    }
    
    public List getPossibleCreatures(final EnumCreatureType creatureType, final int i, final int j, final int k) {
        final BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        return (biome == null) ? null : biome.getSpawnableList(creatureType);
    }
    
    public ChunkPosition func_147416_a(final World world, final String type, final int i, final int j, final int k) {
        return null;
    }
    
    public int getLoadedChunkCount() {
        return 0;
    }
    
    public void recreateStructures(final int i, final int j) {
    }
}
