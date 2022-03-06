// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import lotr.common.fellowship.LOTRFellowshipClient;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraft.util.EnumChatFormatting;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.NBTTagCompound;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import com.mojang.authlib.GameProfile;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.server.MinecraftServer;
import java.util.Iterator;
import lotr.common.LOTRLevelData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.LOTRPlayerData;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import lotr.common.LOTRTitle;
import java.util.Map;
import java.util.List;
import net.minecraft.item.ItemStack;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFellowship implements IMessage
{
    private UUID fellowshipID;
    private boolean isInvite;
    private String fellowshipName;
    private ItemStack fellowshipIcon;
    private boolean isOwned;
    private boolean isAdminned;
    private List<String> playerNames;
    private Map<String, LOTRTitle.PlayerTitle> titleMap;
    private Set<String> adminNames;
    private boolean preventPVP;
    private boolean preventHiredFF;
    private boolean showMapLocations;
    
    public LOTRPacketFellowship() {
        this.playerNames = new ArrayList<String>();
        this.titleMap = new HashMap<String, LOTRTitle.PlayerTitle>();
        this.adminNames = new HashSet<String>();
    }
    
    public LOTRPacketFellowship(final LOTRPlayerData playerData, final LOTRFellowship fs, final boolean invite) {
        this.playerNames = new ArrayList<String>();
        this.titleMap = new HashMap<String, LOTRTitle.PlayerTitle>();
        this.adminNames = new HashSet<String>();
        this.fellowshipID = fs.getFellowshipID();
        this.isInvite = invite;
        this.fellowshipName = fs.getName();
        this.fellowshipIcon = fs.getIcon();
        final UUID thisPlayer = playerData.getPlayerUUID();
        this.isOwned = fs.isOwner(thisPlayer);
        this.isAdminned = fs.isAdmin(thisPlayer);
        final List<UUID> playerIDs = fs.getAllPlayerUUIDs();
        for (final UUID player : playerIDs) {
            final String username = getPlayerUsername(player);
            this.playerNames.add(username);
            final LOTRPlayerData data = LOTRLevelData.getData(player);
            final LOTRTitle.PlayerTitle title = data.getPlayerTitle();
            if (title != null) {
                this.titleMap.put(username, title);
            }
            if (fs.isAdmin(player)) {
                this.adminNames.add(username);
            }
        }
        this.preventPVP = fs.getPreventPVP();
        this.preventHiredFF = fs.getPreventHiredFriendlyFire();
        this.showMapLocations = fs.getShowMapLocations();
    }
    
    public static String getPlayerUsername(final UUID player) {
        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
        if (profile == null || StringUtils.isBlank((CharSequence)profile.getName())) {
            final String name = UsernameCache.getLastKnownUsername(player);
            if (name != null) {
                profile = new GameProfile(player, name);
            }
            else {
                profile = new GameProfile(player, "");
                MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
            }
        }
        final String username = profile.getName();
        return username;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
        data.writeBoolean(this.isInvite);
        final byte[] fsNameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
        data.writeByte(fsNameBytes.length);
        data.writeBytes(fsNameBytes);
        final NBTTagCompound iconData = new NBTTagCompound();
        if (this.fellowshipIcon != null) {
            this.fellowshipIcon.writeToNBT(iconData);
        }
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(iconData);
        }
        catch (IOException e) {
            FMLLog.severe("LOTR: Error writing fellowship data", new Object[0]);
            e.printStackTrace();
        }
        data.writeBoolean(this.isOwned);
        data.writeBoolean(this.isAdminned);
        for (final String username : this.playerNames) {
            final byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
            data.writeByte(usernameBytes.length);
            data.writeBytes(usernameBytes);
            final LOTRTitle.PlayerTitle title = this.titleMap.get(username);
            if (title != null) {
                data.writeShort(title.getTitle().titleID);
                data.writeByte((int)title.getColor().getFormattingCode());
            }
            else {
                data.writeShort(-1);
            }
            final boolean admin = this.adminNames.contains(username);
            data.writeBoolean(admin);
        }
        data.writeByte(-1);
        data.writeBoolean(this.preventPVP);
        data.writeBoolean(this.preventHiredFF);
        data.writeBoolean(this.showMapLocations);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
        this.isInvite = data.readBoolean();
        final int fsNameLength = data.readByte();
        final ByteBuf fsNameBytes = data.readBytes(fsNameLength);
        this.fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
        NBTTagCompound iconData = new NBTTagCompound();
        try {
            iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("LOTR: Error reading fellowship data", new Object[0]);
            e.printStackTrace();
        }
        this.fellowshipIcon = ItemStack.loadItemStackFromNBT(iconData);
        this.isOwned = data.readBoolean();
        this.isAdminned = data.readBoolean();
        int usernameLength = 0;
        while ((usernameLength = data.readByte()) >= 0) {
            final ByteBuf usernameBytes = data.readBytes(usernameLength);
            final String username = usernameBytes.toString(Charsets.UTF_8);
            this.playerNames.add(username);
            final int titleID = data.readShort();
            if (titleID >= 0) {
                final int colorID = data.readByte();
                final LOTRTitle title = LOTRTitle.forID(titleID);
                final EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorID);
                if (title != null && color != null) {
                    final LOTRTitle.PlayerTitle playerTitle = new LOTRTitle.PlayerTitle(title, color);
                    this.titleMap.put(username, playerTitle);
                }
            }
            final boolean admin = data.readBoolean();
            if (admin) {
                this.adminNames.add(username);
            }
        }
        this.preventPVP = data.readBoolean();
        this.preventHiredFF = data.readBoolean();
        this.showMapLocations = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowship, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowship packet, final MessageContext context) {
            final LOTRFellowshipClient fellowship = new LOTRFellowshipClient(packet.fellowshipID, packet.fellowshipName, packet.isOwned, packet.isAdminned, packet.playerNames);
            fellowship.setTitles(packet.titleMap);
            fellowship.setAdmins(packet.adminNames);
            fellowship.setIcon(packet.fellowshipIcon);
            fellowship.setPreventPVP(packet.preventPVP);
            fellowship.setPreventHiredFriendlyFire(packet.preventHiredFF);
            fellowship.setShowMapLocations(packet.showMapLocations);
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            if (packet.isInvite) {
                LOTRLevelData.getData(entityplayer).addOrUpdateClientFellowshipInvite(fellowship);
            }
            else {
                LOTRLevelData.getData(entityplayer).addOrUpdateClientFellowship(fellowship);
            }
            return null;
        }
    }
}
