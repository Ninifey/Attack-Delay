// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.Iterator;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRLevelData;
import java.util.HashMap;
import lotr.common.fac.LOTRFaction;
import java.util.Map;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAlignment implements IMessage
{
    private UUID player;
    private Map<LOTRFaction, Float> alignmentMap;
    private boolean hideAlignment;
    
    public LOTRPacketAlignment() {
        this.alignmentMap = new HashMap<LOTRFaction, Float>();
    }
    
    public LOTRPacketAlignment(final UUID uuid) {
        this.alignmentMap = new HashMap<LOTRFaction, Float>();
        this.player = uuid;
        final LOTRPlayerData pd = LOTRLevelData.getData(this.player);
        for (final LOTRFaction f : LOTRFaction.values()) {
            final float al = pd.getAlignment(f);
            this.alignmentMap.put(f, al);
        }
        this.hideAlignment = pd.getHideAlignment();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.player.getMostSignificantBits());
        data.writeLong(this.player.getLeastSignificantBits());
        for (final Map.Entry<LOTRFaction, Float> entry : this.alignmentMap.entrySet()) {
            final LOTRFaction f = entry.getKey();
            final float alignment = entry.getValue();
            data.writeByte(f.ordinal());
            data.writeFloat(alignment);
        }
        data.writeByte(-1);
        data.writeBoolean(this.hideAlignment);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.player = new UUID(data.readLong(), data.readLong());
        int factionID = 0;
        while ((factionID = data.readByte()) >= 0) {
            final LOTRFaction f = LOTRFaction.forID(factionID);
            final float alignment = data.readFloat();
            this.alignmentMap.put(f, alignment);
        }
        this.hideAlignment = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAlignment, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAlignment packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final LOTRPlayerData pd = LOTRLevelData.getData(packet.player);
                for (final Map.Entry<LOTRFaction, Float> entry : packet.alignmentMap.entrySet()) {
                    final LOTRFaction f = entry.getKey();
                    final float alignment = entry.getValue();
                    pd.setAlignment(f, alignment);
                }
                pd.setHideAlignment(packet.hideAlignment);
            }
            return null;
        }
    }
}
