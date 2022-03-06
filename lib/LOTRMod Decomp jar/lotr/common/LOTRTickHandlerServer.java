// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.block.Block;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.NetHandlerPlayServer;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import lotr.common.world.LOTRUtumnoLevel;
import net.minecraft.world.Teleporter;
import lotr.common.world.LOTRTeleporter;
import lotr.common.block.LOTRBlockPortal;
import net.minecraft.item.Item;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.storage.WorldInfo;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.nbt.NBTBase;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnerNPCs;
import lotr.common.item.LOTRItemStructureSpawner;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.world.World;
import lotr.common.world.LOTRWorldInfo;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.server.MinecraftServer;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fellowship.LOTRFellowshipData;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.HashMap;

public class LOTRTickHandlerServer
{
    public static HashMap playersInPortals;
    public static HashMap playersInElvenPortals;
    public static HashMap playersInMorgulPortals;
    private int fireworkDisplay;
    
    public LOTRTickHandlerServer() {
        FMLCommonHandler.instance().bus().register((Object)this);
    }
    
    @SubscribeEvent
    public void onWorldTick(final TickEvent.WorldTickEvent event) {
        final World world = event.world;
        if (world.isClient) {
            return;
        }
        if (event.phase == TickEvent.Phase.START && world == DimensionManager.getWorld(0)) {
            final World overworld = world;
            if (LOTRLevelData.needsLoad) {
                LOTRLevelData.load();
            }
            if (LOTRTime.needsLoad) {
                LOTRTime.load();
            }
            if (LOTRFellowshipData.needsLoad) {
                LOTRFellowshipData.loadAll();
            }
            if (LOTRFactionBounties.needsLoad) {
                LOTRFactionBounties.loadAll();
            }
            if (LOTRFactionRelations.needsLoad) {
                LOTRFactionRelations.load();
            }
            if (LOTRConquestGrid.needsLoad) {
                LOTRConquestGrid.loadAllZones();
            }
            for (final WorldServer dimWorld : MinecraftServer.getServer().worldServers) {
                if (((World)dimWorld).provider instanceof LOTRWorldProvider) {
                    final WorldInfo prevWorldInfo = dimWorld.getWorldInfo();
                    if (prevWorldInfo.getClass() != LOTRWorldInfo.class) {
                        final WorldInfo newWorldInfo = (WorldInfo)new LOTRWorldInfo(overworld.getWorldInfo());
                        newWorldInfo.setWorldName(prevWorldInfo.getWorldName());
                        LOTRReflection.setWorldInfo((World)dimWorld, newWorldInfo);
                        FMLLog.info("LOTR: Successfully replaced world info in %s", new Object[] { LOTRDimension.getCurrentDimension((World)dimWorld).dimensionName });
                    }
                }
            }
            LOTRBannerProtection.updateWarningCooldowns();
            LOTRInterModComms.update();
        }
        if (event.phase == TickEvent.Phase.END) {
            if (world == DimensionManager.getWorld(0)) {
                if (LOTRLevelData.anyDataNeedsSave()) {
                    LOTRLevelData.save();
                }
                if (LOTRFellowshipData.anyDataNeedsSave()) {
                    LOTRFellowshipData.saveAll();
                }
                LOTRFactionBounties.updateAll();
                if (LOTRFactionBounties.anyDataNeedsSave()) {
                    LOTRFactionBounties.saveAll();
                }
                if (LOTRFactionRelations.needsSave()) {
                    LOTRFactionRelations.save();
                }
                if (LOTRConquestGrid.anyChangedZones()) {
                    LOTRConquestGrid.saveChangedZones();
                }
                if (world.getTotalWorldTime() % 600L == 0L) {
                    LOTRLevelData.save();
                    LOTRLevelData.saveAndClearUnusedPlayerData();
                    LOTRFellowshipData.saveAll();
                    LOTRFellowshipData.saveAndClearUnusedFellowships();
                    LOTRFactionBounties.saveAll();
                    LOTRFactionRelations.save();
                }
                if (LOTRItemStructureSpawner.lastStructureSpawnTick > 0) {
                    --LOTRItemStructureSpawner.lastStructureSpawnTick;
                }
                LOTRTime.update();
                LOTRGreyWandererTracker.updateCooldowns();
            }
            if (world == DimensionManager.getWorld(LOTRDimension.MIDDLE_EARTH.dimensionID)) {
                LOTRDate.update(world);
                if (LOTRMod.canSpawnMobs(world)) {
                    LOTRSpawnerNPCs.performSpawning(world);
                    LOTREventSpawner.performSpawning(world);
                }
                LOTRConquestGrid.updateZones(world);
                if (!world.playerEntities.isEmpty()) {
                    if (LOTRMod.isNewYearsDay()) {
                        if (this.fireworkDisplay == 0 && world.rand.nextInt(4000) == 0) {
                            this.fireworkDisplay = 100 + world.rand.nextInt(300);
                        }
                        if (this.fireworkDisplay > 0) {
                            --this.fireworkDisplay;
                            if (world.rand.nextInt(50) == 0) {
                                final int launches = 1 + world.rand.nextInt(7 + world.playerEntities.size() / 2);
                                final int range = 64;
                                for (int l = 0; l < launches; ++l) {
                                    final EntityPlayer entityplayer = world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));
                                    final int i = MathHelper.floor_double(((Entity)entityplayer).posX) + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
                                    final int k = MathHelper.floor_double(((Entity)entityplayer).posZ) + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
                                    final int j = world.getHeightValue(i, k);
                                    if (world.getBlock(i, j - 1, k).isNormalCube()) {
                                        for (int fireworks = 1 + world.rand.nextInt(4), l2 = 0; l2 < fireworks; ++l2) {
                                            final int groupRange = 8;
                                            final int i2 = i - world.rand.nextInt(groupRange) + world.rand.nextInt(groupRange);
                                            final int k2 = k - world.rand.nextInt(groupRange) + world.rand.nextInt(groupRange);
                                            final int j2 = world.getHeightValue(i2, k2);
                                            if (world.getBlock(i2, j2 - 1, k2).isNormalCube()) {
                                                final ItemStack itemstack = new ItemStack(Items.fireworks);
                                                final NBTTagCompound itemData = new NBTTagCompound();
                                                final NBTTagCompound fireworkData = new NBTTagCompound();
                                                final NBTTagList explosionsList = new NBTTagList();
                                                for (int explosions = 1 + world.rand.nextInt(3), l3 = 0; l3 < explosions; ++l3) {
                                                    final NBTTagCompound explosionData = new NBTTagCompound();
                                                    if (world.rand.nextBoolean()) {
                                                        explosionData.setBoolean("Flicker", true);
                                                    }
                                                    if (world.rand.nextBoolean()) {
                                                        explosionData.setBoolean("Trail", true);
                                                    }
                                                    final int[] colors = new int[1 + world.rand.nextInt(3)];
                                                    for (int l4 = 0; l4 < colors.length; ++l4) {
                                                        colors[l4] = ItemDye.field_150922_c[world.rand.nextInt(ItemDye.field_150922_c.length)];
                                                    }
                                                    explosionData.setIntArray("Colors", colors);
                                                    int effectType = world.rand.nextInt(5);
                                                    if (effectType == 3) {
                                                        effectType = 0;
                                                    }
                                                    explosionData.setByte("Type", (byte)effectType);
                                                    explosionsList.appendTag((NBTBase)explosionData);
                                                }
                                                fireworkData.setTag("Explosions", (NBTBase)explosionsList);
                                                final int flight = 1 + world.rand.nextInt(3);
                                                fireworkData.setByte("Flight", (byte)flight);
                                                itemData.setTag("Fireworks", (NBTBase)fireworkData);
                                                itemstack.setTagCompound(itemData);
                                                final EntityFireworkRocket firework = new EntityFireworkRocket(world, i2 + 0.5, j2 + 0.5, k2 + 0.5, itemstack);
                                                world.spawnEntityInWorld((Entity)firework);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (world.getTotalWorldTime() % 20L == 0L) {
                        for (int m = 0; m < world.playerEntities.size(); ++m) {
                            final EntityPlayer entityplayer2 = world.playerEntities.get(m);
                            LOTRLevelData.sendPlayerLocationsToPlayer(entityplayer2, world);
                        }
                    }
                }
            }
            if (world == DimensionManager.getWorld(LOTRDimension.UTUMNO.dimensionID) && !world.playerEntities.isEmpty() && LOTRMod.canSpawnMobs(world)) {
                LOTRSpawnerNPCs.performSpawning(world);
            }
            if (world.provider instanceof LOTRWorldProvider && world.getTotalWorldTime() % 100L == 0L) {
                LOTRBiomeVariantStorage.performCleanup((WorldServer)world);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        final World world = ((Entity)player).worldObj;
        if (world == null || world.isClient) {
            return;
        }
        if (player != null && player instanceof EntityPlayerMP) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)player;
            if (event.phase == TickEvent.Phase.START && entityplayer.playerNetServerHandler != null && !(entityplayer.playerNetServerHandler instanceof LOTRNetHandlerPlayServer)) {
                entityplayer.playerNetServerHandler = new LOTRNetHandlerPlayServer(MinecraftServer.getServer(), entityplayer.playerNetServerHandler.netManager, entityplayer);
            }
            if (event.phase == TickEvent.Phase.END) {
                LOTRLevelData.getData((EntityPlayer)entityplayer).onUpdate(entityplayer, (WorldServer)world);
                final NetHandlerPlayServer netHandler = entityplayer.playerNetServerHandler;
                if (netHandler instanceof LOTRNetHandlerPlayServer) {
                    ((LOTRNetHandlerPlayServer)netHandler).update();
                }
                final ItemStack heldItem = entityplayer.getHeldItem();
                if (heldItem != null && (heldItem.getItem() instanceof ItemWritableBook || heldItem.getItem() instanceof ItemEditableBook)) {
                    entityplayer.func_143004_u();
                }
                if (((Entity)entityplayer).dimension == 0 && LOTRLevelData.madePortal == 0) {
                    final List items = world.getEntitiesWithinAABB((Class)EntityItem.class, ((Entity)entityplayer).boundingBox.expand(16.0, 16.0, 16.0));
                    for (final Object obj : items) {
                        final EntityItem item = (EntityItem)obj;
                        if (LOTRLevelData.madePortal == 0 && item.getEntityItem() != null && item.getEntityItem().getItem() == LOTRMod.goldRing && item.isBurning()) {
                            LOTRLevelData.setMadePortal(1);
                            LOTRLevelData.markOverworldPortalLocation(MathHelper.floor_double(((Entity)item).posX), MathHelper.floor_double(((Entity)item).posY), MathHelper.floor_double(((Entity)item).posZ));
                            item.setDead();
                            world.createExplosion((Entity)entityplayer, ((Entity)item).posX, ((Entity)item).posY + 3.0, ((Entity)item).posZ, 3.0f, true);
                            final Entity portal = new LOTREntityPortal(world);
                            portal.setLocationAndAngles(((Entity)item).posX, ((Entity)item).posY + 3.0, ((Entity)item).posZ, 0.0f, 0.0f);
                            world.spawnEntityInWorld(portal);
                        }
                    }
                }
                if (((Entity)entityplayer).dimension == 0 || ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                    final List items = world.getEntitiesWithinAABB((Class)EntityItem.class, ((Entity)entityplayer).boundingBox.expand(16.0, 16.0, 16.0));
                    for (final Object obj : items) {
                        final EntityItem item = (EntityItem)obj;
                        if (item.getEntityItem() == null) {
                            continue;
                        }
                        final int i = MathHelper.floor_double(((Entity)item).posX);
                        final int j = MathHelper.floor_double(((Entity)item).posY);
                        final int k = MathHelper.floor_double(((Entity)item).posZ);
                        final ItemStack itemstack = item.getEntityItem();
                        if ((LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.GALADHRIM) >= 1.0f || LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.HIGH_ELF) >= 1.0f) && (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.elanor) || itemstack.getItem() == Item.getItemFromBlock(LOTRMod.niphredil))) {
                            boolean foundPortalLocation = false;
                            int[] portalLocation = new int[3];
                            for (int i2 = i - 2; !foundPortalLocation && i2 <= i + 2; ++i2) {
                                for (int k2 = k - 2; !foundPortalLocation && k2 <= k + 2; ++k2) {
                                    if (((LOTRBlockPortal)LOTRMod.elvenPortal).isValidPortalLocation(world, i2, j, k2, false)) {
                                        foundPortalLocation = true;
                                        portalLocation = new int[] { i2, j, k2 };
                                    }
                                }
                            }
                            if (foundPortalLocation) {
                                item.setDead();
                                for (int i2 = -1; i2 <= 1; ++i2) {
                                    for (int k2 = -1; k2 <= 1; ++k2) {
                                        world.setBlock(portalLocation[0] + i2, portalLocation[1], portalLocation[2] + k2, LOTRMod.elvenPortal, 0, 2);
                                    }
                                }
                            }
                        }
                        if ((LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.MORDOR) < 1.0f && LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.ANGMAR) < 1.0f && LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.DOL_GULDUR) < 1.0f) || !LOTRMod.isOreNameEqual(itemstack, "bone")) {
                            continue;
                        }
                        boolean foundPortalLocation = false;
                        int[] portalLocation = new int[3];
                        for (int i2 = i - 2; !foundPortalLocation && i2 <= i + 2; ++i2) {
                            for (int k2 = k - 2; !foundPortalLocation && k2 <= k + 2; ++k2) {
                                if (((LOTRBlockPortal)LOTRMod.morgulPortal).isValidPortalLocation(world, i2, j, k2, false)) {
                                    foundPortalLocation = true;
                                    portalLocation = new int[] { i2, j, k2 };
                                }
                            }
                        }
                        if (!foundPortalLocation) {
                            continue;
                        }
                        item.setDead();
                        for (int i2 = -1; i2 <= 1; ++i2) {
                            for (int k2 = -1; k2 <= 1; ++k2) {
                                world.setBlock(portalLocation[0] + i2, portalLocation[1], portalLocation[2] + k2, LOTRMod.morgulPortal, 0, 2);
                            }
                        }
                    }
                }
                if ((((Entity)entityplayer).dimension == 0 || ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
                    final List portals = world.getEntitiesWithinAABB((Class)LOTREntityPortal.class, ((Entity)entityplayer).boundingBox.expand(8.0, 8.0, 8.0));
                    boolean inPortal = false;
                    for (int l = 0; l < portals.size(); ++l) {
                        final LOTREntityPortal portal2 = portals.get(l);
                        if (portal2.boundingBox.intersectsWith(((Entity)entityplayer).boundingBox)) {
                            inPortal = true;
                            break;
                        }
                    }
                    if (inPortal) {
                        int l = LOTRTickHandlerServer.playersInPortals.get(entityplayer);
                        ++l;
                        LOTRTickHandlerServer.playersInPortals.put(entityplayer, l);
                        if (l >= 100) {
                            int dimension = 0;
                            if (((Entity)entityplayer).dimension == 0) {
                                dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
                            }
                            else if (((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                                dimension = 0;
                            }
                            if (world instanceof WorldServer) {
                                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer, dimension, (Teleporter)new LOTRTeleporter(DimensionManager.getWorld(dimension), true));
                            }
                            LOTRTickHandlerServer.playersInPortals.remove(entityplayer);
                        }
                    }
                    else {
                        LOTRTickHandlerServer.playersInPortals.remove(entityplayer);
                    }
                }
                this.updatePlayerInPortal(entityplayer, LOTRTickHandlerServer.playersInElvenPortals, (LOTRBlockPortal)LOTRMod.elvenPortal);
                this.updatePlayerInPortal(entityplayer, LOTRTickHandlerServer.playersInMorgulPortals, (LOTRBlockPortal)LOTRMod.morgulPortal);
                if (((Entity)entityplayer).dimension == LOTRDimension.UTUMNO.dimensionID) {
                    final int m = MathHelper.floor_double(((Entity)entityplayer).posX);
                    final int j2 = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
                    final int k3 = MathHelper.floor_double(((Entity)entityplayer).posZ);
                    final byte range = 32;
                    for (int l2 = 0; l2 < 60; ++l2) {
                        final int i3 = m + world.rand.nextInt(range) - world.rand.nextInt(range);
                        final int j3 = j2 + world.rand.nextInt(range) - world.rand.nextInt(range);
                        final int k4 = k3 + world.rand.nextInt(range) - world.rand.nextInt(range);
                        if (LOTRUtumnoLevel.forY(j3) == LOTRUtumnoLevel.ICE) {
                            final Block block = world.getBlock(i3, j3, k4);
                            final int meta = world.getBlockMetadata(i3, j3, k4);
                            if (block.getMaterial() == Material.water && meta == 0) {
                                world.setBlock(i3, j3, k4, Blocks.ice, 0, 3);
                            }
                        }
                        if (LOTRUtumnoLevel.forY(j3) == LOTRUtumnoLevel.FIRE) {
                            final Block block = world.getBlock(i3, j3, k4);
                            final int meta = world.getBlockMetadata(i3, j3, k4);
                            if (block.getMaterial() == Material.water && meta == 0) {
                                world.setBlock(i3, j3, k4, Blocks.air, 0, 3);
                                LOTRWorldProviderUtumno.doEvaporateFX(world, i3, j3, k4);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void updatePlayerInPortal(final EntityPlayerMP entityplayer, final HashMap players, final LOTRBlockPortal portalBlock) {
        if ((((Entity)entityplayer).dimension == 0 || ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && players.containsKey(entityplayer)) {
            final boolean inPortal = ((Entity)entityplayer).worldObj.getBlock(MathHelper.floor_double(((Entity)entityplayer).posX), MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY), MathHelper.floor_double(((Entity)entityplayer).posZ)) == portalBlock;
            if (inPortal) {
                int i = players.get(entityplayer);
                ++i;
                players.put(entityplayer, i);
                if (i >= entityplayer.getMaxInPortalTime()) {
                    int dimension = 0;
                    if (((Entity)entityplayer).dimension == 0) {
                        dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
                    }
                    else if (((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                        dimension = 0;
                    }
                    final WorldServer newWorld = MinecraftServer.getServer().worldServerForDimension(dimension);
                    MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer, dimension, portalBlock.getPortalTeleporter(newWorld));
                    players.remove(entityplayer);
                }
            }
            else {
                players.remove(entityplayer);
            }
        }
    }
    
    static {
        LOTRTickHandlerServer.playersInPortals = new HashMap();
        LOTRTickHandlerServer.playersInElvenPortals = new HashMap();
        LOTRTickHandlerServer.playersInMorgulPortals = new HashMap();
    }
}
