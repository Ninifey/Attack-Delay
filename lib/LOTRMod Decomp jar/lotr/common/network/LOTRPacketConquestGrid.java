// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.Iterator;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.world.World;
import java.util.Collection;
import lotr.common.world.map.LOTRConquestZone;
import java.util.List;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketConquestGrid implements IMessage
{
    private LOTRFaction conqFac;
    private List<LOTRConquestZone> allZones;
    private long worldTime;
    private static final short END_OF_PACKET = -15000;
    
    public LOTRPacketConquestGrid() {
    }
    
    public LOTRPacketConquestGrid(final LOTRFaction fac, final Collection<LOTRConquestZone> grid, final World world) {
        this.conqFac = fac;
        this.allZones = new ArrayList<LOTRConquestZone>(grid);
        this.worldTime = world.getTotalWorldTime();
    }
    
    public void toBytes(final ByteBuf data) {
        final int facID = this.conqFac.ordinal();
        data.writeByte(facID);
        for (final LOTRConquestZone zone : this.allZones) {
            final float str = zone.getConquestStrength(this.conqFac, this.worldTime);
            if (str > 0.0f) {
                final float strRaw = zone.getConquestStrengthRaw(this.conqFac);
                data.writeShort(zone.gridX);
                data.writeShort(zone.gridZ);
                data.writeLong(zone.getLastChangeTime());
                data.writeFloat(strRaw);
            }
        }
        data.writeShort(-15000);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int facID = data.readByte();
        this.conqFac = LOTRFaction.forID(facID);
        this.allZones = new ArrayList<LOTRConquestZone>();
        int gridX = 0;
        int gridZ = 0;
        float str = 0.0f;
        while ((gridX = data.readShort()) != -15000) {
            gridZ = data.readShort();
            final long time = data.readLong();
            str = data.readFloat();
            final LOTRConquestZone zone = new LOTRConquestZone(gridX, gridZ);
            zone.setClientSide();
            zone.setLastChangeTime(time);
            zone.setConquestStrengthRaw(this.conqFac, str);
            this.allZones.add(zone);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketConquestGrid, IMessage>
    {
        public IMessage onMessage(final LOTRPacketConquestGrid packet, final MessageContext context) {
            LOTRMod.proxy.receiveConquestGrid(packet.conqFac, packet.allZones);
            return null;
        }
    }
}
