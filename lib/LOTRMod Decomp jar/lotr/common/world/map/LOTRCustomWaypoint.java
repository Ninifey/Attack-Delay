// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.network.LOTRPacketCWPSharedHideClient;
import lotr.common.network.LOTRPacketCWPSharedUnlockClient;
import lotr.common.network.LOTRPacketShareCWPClient;
import lotr.common.network.LOTRPacketRenameCWPClient;
import lotr.common.network.LOTRPacketDeleteCWPClient;
import lotr.common.network.LOTRPacketCreateCWPClient;
import lotr.common.network.LOTRPacketFellowship;
import net.minecraft.server.MinecraftServer;
import java.util.Set;
import java.util.Collection;
import lotr.common.fellowship.LOTRFellowshipData;
import java.util.HashSet;
import lotr.common.fellowship.LOTRFellowship;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiome;
import java.util.Iterator;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.util.StatCollector;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import java.util.ArrayList;
import lotr.common.LOTRPlayerData;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;
import java.util.List;

public class LOTRCustomWaypoint implements LOTRAbstractWaypoint
{
    private String customName;
    private int mapX;
    private int mapY;
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private int ID;
    private List<UUID> sharedFellowshipIDs;
    private UUID sharingPlayer;
    private String sharingPlayerName;
    private boolean sharedUnlocked;
    private static final int SHARED_UNLOCK_RANGE = 1000;
    private boolean sharedHidden;
    
    public static LOTRCustomWaypoint createForPlayer(final String name, final EntityPlayer entityplayer) {
        final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
        final int cwpID = playerData.getNextCwpID();
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int j = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        final int mapX = LOTRWaypoint.worldToMapX(i);
        final int mapY = LOTRWaypoint.worldToMapZ(k);
        final LOTRCustomWaypoint cwp = new LOTRCustomWaypoint(name, mapX, mapY, i, j, k, cwpID);
        playerData.addCustomWaypoint(cwp);
        playerData.incrementNextCwpID();
        return cwp;
    }
    
    public LOTRCustomWaypoint(final String name, final int i, final int j, final int posX, final int posY, final int posZ, final int id) {
        this.sharedFellowshipIDs = new ArrayList<UUID>();
        this.customName = name;
        this.mapX = i;
        this.mapY = j;
        this.xCoord = posX;
        this.yCoord = posY;
        this.zCoord = posZ;
        this.ID = id;
    }
    
    @Override
    public int getX() {
        return this.mapX;
    }
    
    @Override
    public int getY() {
        return this.mapY;
    }
    
    @Override
    public int getXCoord() {
        return this.xCoord;
    }
    
    @Override
    public int getYCoord(final World world, final int i, final int k) {
        final int j = this.yCoord;
        if (j < 0) {
            this.yCoord = LOTRMod.getTrueTopBlock(world, i, k);
        }
        else if (!this.isSafeBlock(world, i, j, k)) {
            final Block below = world.getBlock(i, j - 1, k);
            final Block block = world.getBlock(i, j, k);
            final Block above = world.getBlock(i, j + 1, k);
            final boolean belowSafe = below.getMaterial().blocksMovement();
            final boolean blockSafe = !block.isNormalCube((IBlockAccess)world, i, j, k);
            final boolean aboveSafe = !above.isNormalCube((IBlockAccess)world, i, j + 1, k);
            boolean foundSafe = false;
            if (!belowSafe) {
                int j2;
                for (int start = j2 = j - 1; j2 >= 1; --j2) {
                    if (this.isSafeBlock(world, i, j2, k)) {
                        this.yCoord = j2;
                        foundSafe = true;
                        break;
                    }
                }
            }
            if (!foundSafe && (!blockSafe || !aboveSafe)) {
                int j2;
                for (int start = j2 = (aboveSafe ? (j + 1) : (j + 2)); j2 <= world.getHeight() - 1; ++j2) {
                    if (this.isSafeBlock(world, i, j2, k)) {
                        this.yCoord = j2;
                        foundSafe = true;
                        break;
                    }
                }
            }
            if (!foundSafe) {
                this.yCoord = LOTRMod.getTrueTopBlock(world, i, k);
            }
        }
        return this.yCoord;
    }
    
    private boolean isSafeBlock(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        final Block block = world.getBlock(i, j, k);
        final Block above = world.getBlock(i, j + 1, k);
        return below.getMaterial().blocksMovement() && !block.isNormalCube((IBlockAccess)world, i, j, k) && !above.isNormalCube((IBlockAccess)world, i, j + 1, k) && !block.getMaterial().isLiquid() && block.getMaterial() != Material.fire && !above.getMaterial().isLiquid() && above.getMaterial() != Material.fire;
    }
    
    @Override
    public int getYCoordDisplay() {
        return this.yCoord;
    }
    
    @Override
    public int getZCoord() {
        return this.zCoord;
    }
    
    @Override
    public String getCodeName() {
        return this.customName;
    }
    
    @Override
    public String getDisplayName() {
        if (this.isShared()) {
            return StatCollector.translateToLocalFormatted("lotr.waypoint.shared", new Object[] { this.customName });
        }
        return StatCollector.translateToLocalFormatted("lotr.waypoint.custom", new Object[] { this.customName });
    }
    
    @Override
    public String getLoreText(final EntityPlayer entityplayer) {
        final boolean ownShared = !this.isShared() && !this.sharedFellowshipIDs.isEmpty();
        final boolean shared = this.isShared() && this.sharingPlayerName != null;
        if (ownShared || shared) {
            final int numShared = this.sharedFellowshipIDs.size();
            final int maxShow = 3;
            int numShown = 0;
            final List<String> fsNames = new ArrayList<String>();
            for (int i = 0; i < 3 && i < this.sharedFellowshipIDs.size(); ++i) {
                final UUID fsID = this.sharedFellowshipIDs.get(i);
                final LOTRFellowshipClient fs = LOTRLevelData.getData(entityplayer).getClientFellowshipByID(fsID);
                if (fs != null) {
                    fsNames.add(fs.getName());
                    ++numShown;
                }
            }
            String sharedFsNames = "";
            for (final String s : fsNames) {
                sharedFsNames = sharedFsNames + "\n" + s;
            }
            if (numShared > numShown) {
                final int numMore = numShared - numShown;
                sharedFsNames = sharedFsNames + "\n" + StatCollector.translateToLocalFormatted("lotr.waypoint.custom.andMore", new Object[] { numMore });
            }
            if (ownShared) {
                return StatCollector.translateToLocalFormatted("lotr.waypoint.custom.info", new Object[] { sharedFsNames });
            }
            if (shared) {
                return StatCollector.translateToLocalFormatted("lotr.waypoint.shared.info", new Object[] { this.sharingPlayerName, sharedFsNames });
            }
        }
        return null;
    }
    
    @Override
    public boolean hasPlayerUnlocked(final EntityPlayer entityplayer) {
        return !this.isShared() || this.isSharedUnlocked();
    }
    
    @Override
    public boolean isUnlockable(final EntityPlayer entityplayer) {
        return true;
    }
    
    @Override
    public boolean isHidden() {
        return false;
    }
    
    @Override
    public int getID() {
        return this.ID;
    }
    
    private LOTRWaypoint.Region getWorldPosRegion(final World world) {
        final BiomeGenBase biome = world.getBiomeGenForCoords(this.xCoord, this.zCoord);
        if (biome instanceof LOTRBiome) {
            return ((LOTRBiome)biome).getBiomeWaypoints();
        }
        return null;
    }
    
    public void rename(final String newName) {
        this.customName = newName;
    }
    
    public static String validateCustomName(String name) {
        name = StringUtils.trim(name);
        if (!StringUtils.isBlank((CharSequence)name)) {
            return name;
        }
        return null;
    }
    
    public List<UUID> getSharedFellowshipIDs() {
        return this.sharedFellowshipIDs;
    }
    
    public void addSharedFellowship(final LOTRFellowship fs) {
        this.addSharedFellowship(fs.getFellowshipID());
    }
    
    public void addSharedFellowship(final UUID fsID) {
        if (!this.sharedFellowshipIDs.contains(fsID)) {
            this.sharedFellowshipIDs.add(fsID);
        }
    }
    
    public void removeSharedFellowship(final LOTRFellowship fs) {
        this.removeSharedFellowship(fs.getFellowshipID());
    }
    
    public void removeSharedFellowship(final UUID fsID) {
        if (this.sharedFellowshipIDs.contains(fsID)) {
            this.sharedFellowshipIDs.remove(fsID);
        }
    }
    
    public boolean hasSharedFellowship(final LOTRFellowship fs) {
        return this.hasSharedFellowship(fs.getFellowshipID());
    }
    
    public boolean hasSharedFellowship(final UUID fsID) {
        return this.sharedFellowshipIDs.contains(fsID);
    }
    
    public void validateFellowshipIDs(final LOTRPlayerData ownerData) {
        final UUID ownerUUID = ownerData.getPlayerUUID();
        final Set<UUID> removeIDs = new HashSet<UUID>();
        for (final UUID fsID : this.sharedFellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null || fs.isDisbanded() || !fs.containsPlayer(ownerUUID)) {
                removeIDs.add(fsID);
            }
        }
        this.sharedFellowshipIDs.removeAll(removeIDs);
    }
    
    public void setSharedFellowshipIDs(final List<UUID> fsIDs) {
        this.sharedFellowshipIDs = fsIDs;
    }
    
    public void setSharingPlayerID(final UUID id) {
        final UUID prev = this.sharingPlayer;
        this.sharingPlayer = id;
        if (MinecraftServer.getServer() != null && (prev == null || !prev.equals(this.sharingPlayer))) {
            this.sharingPlayerName = LOTRPacketFellowship.getPlayerUsername(this.sharingPlayer);
        }
    }
    
    public UUID getSharingPlayerID() {
        return this.sharingPlayer;
    }
    
    public boolean isShared() {
        return this.sharingPlayer != null;
    }
    
    public void setSharingPlayerName(final String s) {
        this.sharingPlayerName = s;
    }
    
    public String getSharingPlayerName() {
        return this.sharingPlayerName;
    }
    
    public LOTRCustomWaypoint createCopyOfShared(final UUID sharer) {
        final LOTRCustomWaypoint copy = new LOTRCustomWaypoint(this.customName, this.mapX, this.mapY, this.xCoord, this.yCoord, this.zCoord, this.ID);
        copy.setSharingPlayerID(sharer);
        copy.setSharedFellowshipIDs(new ArrayList<UUID>(this.sharedFellowshipIDs));
        return copy;
    }
    
    public boolean isSharedUnlocked() {
        return this.sharedUnlocked;
    }
    
    public void setSharedUnlocked() {
        this.sharedUnlocked = true;
    }
    
    public boolean canUnlockShared(final EntityPlayer entityplayer) {
        if (this.yCoord >= 0) {
            final double distSq = entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5);
            final double unlockRangeSq = 1000000.0;
            return distSq <= unlockRangeSq;
        }
        return false;
    }
    
    public boolean isSharedHidden() {
        return this.sharedHidden;
    }
    
    public void setSharedHidden(final boolean flag) {
        this.sharedHidden = flag;
    }
    
    public List<UUID> getPlayersInAllSharedFellowships() {
        final List<UUID> allPlayers = new ArrayList<UUID>();
        for (final UUID fsID : this.sharedFellowshipIDs) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs != null && !fs.isDisbanded()) {
                final List<UUID> fsPlayers = fs.getAllPlayerUUIDs();
                for (final UUID player : fsPlayers) {
                    if (!player.equals(this.sharingPlayer) && !allPlayers.contains(player)) {
                        allPlayers.add(player);
                    }
                }
            }
        }
        return allPlayers;
    }
    
    public LOTRPacketCreateCWPClient getClientPacket() {
        return new LOTRPacketCreateCWPClient(this.mapX, this.mapY, this.xCoord, this.yCoord, this.zCoord, this.ID, this.customName, this.sharedFellowshipIDs);
    }
    
    public LOTRPacketDeleteCWPClient getClientDeletePacket() {
        return new LOTRPacketDeleteCWPClient(this.ID);
    }
    
    public LOTRPacketRenameCWPClient getClientRenamePacket() {
        return new LOTRPacketRenameCWPClient(this.ID, this.customName);
    }
    
    public LOTRPacketShareCWPClient getClientAddFellowshipPacket(final UUID fsID) {
        return new LOTRPacketShareCWPClient(this.ID, fsID, true);
    }
    
    public LOTRPacketShareCWPClient getClientRemoveFellowshipPacket(final UUID fsID) {
        return new LOTRPacketShareCWPClient(this.ID, fsID, false);
    }
    
    public LOTRPacketCreateCWPClient getClientPacketShared() {
        return new LOTRPacketCreateCWPClient(this.mapX, this.mapY, this.xCoord, this.yCoord, this.zCoord, this.ID, this.customName, this.sharedFellowshipIDs).setSharingPlayer(this.sharingPlayer, this.sharingPlayerName, this.sharedUnlocked, this.sharedHidden);
    }
    
    public LOTRPacketDeleteCWPClient getClientDeletePacketShared() {
        return new LOTRPacketDeleteCWPClient(this.ID).setSharingPlayer(this.sharingPlayer);
    }
    
    public LOTRPacketRenameCWPClient getClientRenamePacketShared() {
        return new LOTRPacketRenameCWPClient(this.ID, this.customName).setSharingPlayer(this.sharingPlayer);
    }
    
    public LOTRPacketCWPSharedUnlockClient getClientSharedUnlockPacket() {
        return new LOTRPacketCWPSharedUnlockClient(this.ID, this.sharingPlayer);
    }
    
    public LOTRPacketCWPSharedHideClient getClientSharedHidePacket(final boolean hide) {
        return new LOTRPacketCWPSharedHideClient(this.ID, this.sharingPlayer, hide);
    }
    
    public void writeToNBT(final NBTTagCompound nbt, final LOTRPlayerData pd) {
        nbt.setString("Name", this.customName);
        nbt.setInteger("X", this.mapX);
        nbt.setInteger("Y", this.mapY);
        nbt.setInteger("XCoord", this.xCoord);
        nbt.setInteger("YCoord", this.yCoord);
        nbt.setInteger("ZCoord", this.zCoord);
        nbt.setInteger("ID", this.ID);
        this.validateFellowshipIDs(pd);
        if (!this.sharedFellowshipIDs.isEmpty()) {
            final NBTTagList sharedFellowshipTags = new NBTTagList();
            for (final UUID fsID : this.sharedFellowshipIDs) {
                final NBTTagString tag = new NBTTagString(fsID.toString());
                sharedFellowshipTags.appendTag((NBTBase)tag);
            }
            nbt.setTag("SharedFellowships", (NBTBase)sharedFellowshipTags);
        }
    }
    
    public static LOTRCustomWaypoint readFromNBT(final NBTTagCompound nbt, final LOTRPlayerData pd) {
        final String name = nbt.getString("Name");
        final int x = nbt.getInteger("X");
        final int y = nbt.getInteger("Y");
        final int xCoord = nbt.getInteger("XCoord");
        final int zCoord = nbt.getInteger("ZCoord");
        int yCoord;
        if (nbt.hasKey("YCoord")) {
            yCoord = nbt.getInteger("YCoord");
        }
        else {
            yCoord = -1;
        }
        final int ID = nbt.getInteger("ID");
        final LOTRCustomWaypoint cwp = new LOTRCustomWaypoint(name, x, y, xCoord, yCoord, zCoord, ID);
        cwp.sharedFellowshipIDs.clear();
        if (nbt.hasKey("SharedFellowships")) {
            final NBTTagList sharedFellowshipTags = nbt.getTagList("SharedFellowships", 8);
            for (int i = 0; i < sharedFellowshipTags.tagCount(); ++i) {
                final UUID fsID = UUID.fromString(sharedFellowshipTags.getStringTagAt(i));
                if (fsID != null) {
                    cwp.sharedFellowshipIDs.add(fsID);
                }
            }
        }
        cwp.validateFellowshipIDs(pd);
        return cwp;
    }
}
