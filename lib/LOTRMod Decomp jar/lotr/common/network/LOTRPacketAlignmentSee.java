// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import lotr.common.LOTRPlayerData;
import java.util.HashMap;
import lotr.common.fac.LOTRFaction;
import java.util.Map;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAlignmentSee implements IMessage
{
    private String username;
    private Map<LOTRFaction, Float> alignmentMap;
    
    public LOTRPacketAlignmentSee() {
        this.alignmentMap = new HashMap<LOTRFaction, Float>();
    }
    
    public LOTRPacketAlignmentSee(final String name, final LOTRPlayerData pd) {
        this.alignmentMap = new HashMap<LOTRFaction, Float>();
        this.username = name;
        for (final LOTRFaction f : LOTRFaction.getPlayableAlignmentFactions()) {
            final float al = pd.getAlignment(f);
            this.alignmentMap.put(f, al);
        }
    }
    
    public void toBytes(final ByteBuf data) {
        final byte[] nameBytes = this.username.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
        for (final Map.Entry<LOTRFaction, Float> entry : this.alignmentMap.entrySet()) {
            final LOTRFaction f = entry.getKey();
            final float alignment = entry.getValue();
            data.writeByte(f.ordinal());
            data.writeFloat(alignment);
        }
        data.writeByte(-1);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int length = data.readByte();
        final ByteBuf nameBytes = data.readBytes(length);
        this.username = nameBytes.toString(Charsets.UTF_8);
        int factionID = 0;
        while ((factionID = data.readByte()) >= 0) {
            final LOTRFaction f = LOTRFaction.forID(factionID);
            final float alignment = data.readFloat();
            this.alignmentMap.put(f, alignment);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAlignmentSee, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAlignmentSee packet, final MessageContext context) {
            LOTRMod.proxy.displayAlignmentSee(packet.username, packet.alignmentMap);
            return null;
        }
    }
}
