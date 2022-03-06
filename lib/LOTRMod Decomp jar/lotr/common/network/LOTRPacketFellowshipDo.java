// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.fellowship.LOTRFellowship;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowshipClient;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public abstract class LOTRPacketFellowshipDo implements IMessage
{
    private UUID fellowshipID;
    
    public LOTRPacketFellowshipDo() {
    }
    
    public LOTRPacketFellowshipDo(final LOTRFellowshipClient fsClient) {
        this.fellowshipID = fsClient.getFellowshipID();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
    }
    
    public void fromBytes(final ByteBuf data) {
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
    }
    
    protected LOTRFellowship getFellowship() {
        return LOTRFellowshipData.getFellowship(this.fellowshipID);
    }
}
