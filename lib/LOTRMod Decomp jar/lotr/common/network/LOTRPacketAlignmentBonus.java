// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.Iterator;
import com.google.common.base.Charsets;
import java.util.Map;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAlignmentBonus implements IMessage
{
    private LOTRFaction mainFaction;
    private float prevMainAlignment;
    private LOTRAlignmentBonusMap factionBonusMap;
    private float conquestBonus;
    private double posX;
    private double posY;
    private double posZ;
    private String name;
    private boolean needsTranslation;
    private boolean isKill;
    
    public LOTRPacketAlignmentBonus() {
        this.factionBonusMap = new LOTRAlignmentBonusMap();
    }
    
    public LOTRPacketAlignmentBonus(final LOTRFaction f, final float pre, final LOTRAlignmentBonusMap fMap, final float conqBonus, final double x, final double y, final double z, final LOTRAlignmentValues.AlignmentBonus source) {
        this.factionBonusMap = new LOTRAlignmentBonusMap();
        this.mainFaction = f;
        this.prevMainAlignment = pre;
        this.factionBonusMap = fMap;
        this.conquestBonus = conqBonus;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.name = source.name;
        this.needsTranslation = source.needsTranslation;
        this.isKill = source.isKill;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.mainFaction.ordinal());
        data.writeFloat(this.prevMainAlignment);
        if (!this.factionBonusMap.isEmpty()) {
            for (final Map.Entry<LOTRFaction, Float> e : this.factionBonusMap.entrySet()) {
                final LOTRFaction faction = e.getKey();
                final float bonus = e.getValue();
                data.writeByte(faction.ordinal());
                data.writeFloat(bonus);
            }
            data.writeByte(-1);
        }
        else {
            data.writeByte(-1);
        }
        data.writeFloat(this.conquestBonus);
        data.writeDouble(this.posX);
        data.writeDouble(this.posY);
        data.writeDouble(this.posZ);
        final byte[] nameData = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameData.length);
        data.writeBytes(nameData);
        data.writeBoolean(this.needsTranslation);
        data.writeBoolean(this.isKill);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.mainFaction = LOTRFaction.forID(data.readByte());
        this.prevMainAlignment = data.readFloat();
        int factionID = 0;
        while ((factionID = data.readByte()) >= 0) {
            final LOTRFaction faction = LOTRFaction.forID(factionID);
            final float bonus = data.readFloat();
            this.factionBonusMap.put(faction, bonus);
        }
        this.conquestBonus = data.readFloat();
        this.posX = data.readDouble();
        this.posY = data.readDouble();
        this.posZ = data.readDouble();
        final int length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
        this.needsTranslation = data.readBoolean();
        this.isKill = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAlignmentBonus, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAlignmentBonus packet, final MessageContext context) {
            String name = packet.name;
            if (packet.needsTranslation) {
                name = StatCollector.translateToLocal(name);
            }
            LOTRMod.proxy.spawnAlignmentBonus(packet.mainFaction, packet.prevMainAlignment, packet.factionBonusMap, name, packet.isKill, packet.conquestBonus, packet.posX, packet.posY, packet.posZ);
            return null;
        }
    }
}
