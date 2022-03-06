// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import java.util.Arrays;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBuf;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;
import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketUpdatePlayerLocations implements IMessage
{
    private List<PlayerLocationInfo> playerLocations;
    private static Map<UUID, byte[]> cachedProfileBytes;
    
    public LOTRPacketUpdatePlayerLocations() {
        this.playerLocations = new ArrayList<PlayerLocationInfo>();
    }
    
    public void addPlayerLocation(final GameProfile profile, final double x, final double z) {
        if (profile.isComplete()) {
            this.playerLocations.add(new PlayerLocationInfo(profile, x, z));
        }
    }
    
    public void toBytes(final ByteBuf data) {
        final int players = this.playerLocations.size();
        data.writeShort(players);
        for (final PlayerLocationInfo player : this.playerLocations) {
            final GameProfile profile = player.playerProfile;
            final UUID playerID = profile.getId();
            byte[] profileBytes = null;
            if (LOTRPacketUpdatePlayerLocations.cachedProfileBytes.containsKey(playerID)) {
                profileBytes = LOTRPacketUpdatePlayerLocations.cachedProfileBytes.get(playerID);
            }
            else {
                final ByteBuf tempBuf = Unpooled.buffer();
                try {
                    final NBTTagCompound profileData = new NBTTagCompound();
                    NBTUtil.func_152460_a(profileData, profile);
                    new PacketBuffer(tempBuf).writeNBTTagCompoundToBuffer(profileData);
                    final byte[] tempBytes = tempBuf.array();
                    profileBytes = Arrays.copyOf(tempBytes, tempBytes.length);
                    LOTRPacketUpdatePlayerLocations.cachedProfileBytes.put(playerID, profileBytes);
                }
                catch (IOException e) {
                    FMLLog.severe("Error writing player profile data", new Object[0]);
                    e.printStackTrace();
                }
            }
            if (profileBytes == null) {
                data.writeShort(-1);
            }
            else {
                final byte[] copied = Arrays.copyOf(profileBytes, profileBytes.length);
                data.writeShort(copied.length);
                data.writeBytes(copied);
                data.writeDouble(player.posX);
                data.writeDouble(player.posZ);
            }
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.playerLocations.clear();
        for (int players = data.readShort(), l = 0; l < players; ++l) {
            final int len = data.readShort();
            if (len >= 0) {
                final ByteBuf profileBytes = data.readBytes(len);
                GameProfile profile = null;
                try {
                    final NBTTagCompound profileData = new PacketBuffer(profileBytes).readNBTTagCompoundFromBuffer();
                    profile = NBTUtil.func_152459_a(profileData);
                }
                catch (IOException e) {
                    FMLLog.severe("Error reading player profile data", new Object[0]);
                    e.printStackTrace();
                }
                final double posX = data.readDouble();
                final double posZ = data.readDouble();
                if (profile != null) {
                    final PlayerLocationInfo playerInfo = new PlayerLocationInfo(profile, posX, posZ);
                    this.playerLocations.add(playerInfo);
                }
            }
        }
    }
    
    static {
        LOTRPacketUpdatePlayerLocations.cachedProfileBytes = new HashMap<UUID, byte[]>();
    }
    
    private static class PlayerLocationInfo
    {
        public final GameProfile playerProfile;
        public final double posX;
        public final double posZ;
        
        public PlayerLocationInfo(final GameProfile profile, final double x, final double z) {
            this.playerProfile = profile;
            this.posX = x;
            this.posZ = z;
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketUpdatePlayerLocations, IMessage>
    {
        public IMessage onMessage(final LOTRPacketUpdatePlayerLocations packet, final MessageContext context) {
            LOTRMod.proxy.clearMapPlayerLocations();
            for (final PlayerLocationInfo info : packet.playerLocations) {
                LOTRMod.proxy.addMapPlayerLocation(info.playerProfile, info.posX, info.posZ);
            }
            return null;
        }
    }
}
