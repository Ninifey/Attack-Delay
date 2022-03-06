// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

import lotr.common.network.LOTRPacketBiomeVariantsUnwatch;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBiomeVariantsWatch;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import java.util.HashMap;
import net.minecraft.world.World;
import net.minecraft.world.ChunkCoordIntPair;
import lotr.common.LOTRDimension;
import java.util.Map;

public class LOTRBiomeVariantStorage
{
    private static Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMap;
    private static Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMapClient;
    
    private static Map<ChunkCoordIntPair, byte[]> getDimensionChunkMap(final World world) {
        Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> sourcemap;
        if (!world.isClient) {
            sourcemap = LOTRBiomeVariantStorage.chunkVariantMap;
        }
        else {
            sourcemap = LOTRBiomeVariantStorage.chunkVariantMapClient;
        }
        final LOTRDimension dim = LOTRDimension.getCurrentDimension(world);
        Map map = sourcemap.get(dim);
        if (map == null) {
            map = new HashMap();
            sourcemap.put(dim, map);
        }
        return (Map<ChunkCoordIntPair, byte[]>)map;
    }
    
    private static ChunkCoordIntPair getChunkKey(final Chunk chunk) {
        return new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
    }
    
    public static byte[] getChunkBiomeVariants(final World world, final Chunk chunk) {
        return getChunkBiomeVariants(world, getChunkKey(chunk));
    }
    
    public static byte[] getChunkBiomeVariants(final World world, final ChunkCoordIntPair chunk) {
        return getDimensionChunkMap(world).get(chunk);
    }
    
    public static void setChunkBiomeVariants(final World world, final Chunk chunk, final byte[] variants) {
        setChunkBiomeVariants(world, getChunkKey(chunk), variants);
    }
    
    public static void setChunkBiomeVariants(final World world, final ChunkCoordIntPair chunk, final byte[] variants) {
        getDimensionChunkMap(world).put(chunk, variants);
    }
    
    public static void clearChunkBiomeVariants(final World world, final Chunk chunk) {
        clearChunkBiomeVariants(world, getChunkKey(chunk));
    }
    
    public static void clearChunkBiomeVariants(final World world, final ChunkCoordIntPair chunk) {
        getDimensionChunkMap(world).remove(chunk);
    }
    
    public static void loadChunkVariants(final World world, final Chunk chunk, final NBTTagCompound data) {
        if (getChunkBiomeVariants(world, chunk) == null) {
            byte[] variants;
            if (data.hasKey("LOTRBiomeVariants")) {
                variants = data.getByteArray("LOTRBiomeVariants");
            }
            else {
                variants = new byte[256];
            }
            setChunkBiomeVariants(world, chunk, variants);
        }
    }
    
    public static void saveChunkVariants(final World world, final Chunk chunk, final NBTTagCompound data) {
        final byte[] variants = getChunkBiomeVariants(world, chunk);
        if (variants != null) {
            data.setByteArray("LOTRBiomeVariants", variants);
        }
    }
    
    public static void clearAllVariants(final World world) {
        getDimensionChunkMap(world).clear();
        FMLLog.info("Unloading LOTR biome variants in %s", new Object[] { LOTRDimension.getCurrentDimension(world).dimensionName });
    }
    
    public static void performCleanup(final WorldServer world) {
        final Map<ChunkCoordIntPair, byte[]> dimensionMap = getDimensionChunkMap((World)world);
        final int loaded = dimensionMap.size();
        final long l = System.nanoTime();
        final List<ChunkCoordIntPair> removalChunks = new ArrayList<ChunkCoordIntPair>();
        for (final ChunkCoordIntPair chunk : dimensionMap.keySet()) {
            if (!world.theChunkProviderServer.chunkExists(chunk.chunkXPos, chunk.chunkZPos)) {
                removalChunks.add(chunk);
            }
        }
        int removed = 0;
        for (final ChunkCoordIntPair chunk2 : removalChunks) {
            dimensionMap.remove(chunk2);
            ++removed;
        }
    }
    
    public static void sendChunkVariantsToPlayer(final World world, final Chunk chunk, final EntityPlayerMP entityplayer) {
        final byte[] variants = getChunkBiomeVariants(world, chunk);
        if (variants != null) {
            final LOTRPacketBiomeVariantsWatch packet = new LOTRPacketBiomeVariantsWatch(chunk.xPosition, chunk.zPosition, variants);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
        }
        else {
            final String dimName = world.provider.getDimensionName();
            final int posX = chunk.xPosition << 4;
            final int posZ = chunk.zPosition << 4;
            final String playerName = entityplayer.getCommandSenderName();
            FMLLog.severe("Could not find LOTR biome variants for %s chunk at %d, %d; requested by %s", new Object[] { dimName, posX, posZ, playerName });
        }
    }
    
    public static void sendUnwatchToPlayer(final World world, final Chunk chunk, final EntityPlayerMP entityplayer) {
        final LOTRPacketBiomeVariantsUnwatch packet = new LOTRPacketBiomeVariantsUnwatch(chunk.xPosition, chunk.zPosition);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    public static int getSize(final World world) {
        final Map<ChunkCoordIntPair, byte[]> map = getDimensionChunkMap(world);
        return map.size();
    }
    
    static {
        LOTRBiomeVariantStorage.chunkVariantMap = new HashMap<LOTRDimension, Map<ChunkCoordIntPair, byte[]>>();
        LOTRBiomeVariantStorage.chunkVariantMapClient = new HashMap<LOTRDimension, Map<ChunkCoordIntPair, byte[]>>();
    }
}
