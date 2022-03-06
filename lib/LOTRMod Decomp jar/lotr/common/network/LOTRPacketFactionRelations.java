// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import lotr.common.fac.LOTRFaction;
import java.util.HashMap;
import java.util.Iterator;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFactionRelations;
import java.util.Map;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFactionRelations implements IMessage
{
    private Type packetType;
    private Map<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> fullMap;
    private LOTRFactionRelations.FactionPair singleKey;
    private LOTRFactionRelations.Relation singleRelation;
    
    public static LOTRPacketFactionRelations fullMap(final Map<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> map) {
        final LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
        pkt.packetType = Type.FULL_MAP;
        pkt.fullMap = map;
        return pkt;
    }
    
    public static LOTRPacketFactionRelations reset() {
        final LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
        pkt.packetType = Type.RESET;
        return pkt;
    }
    
    public static LOTRPacketFactionRelations oneEntry(final LOTRFactionRelations.FactionPair pair, final LOTRFactionRelations.Relation rel) {
        final LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
        pkt.packetType = Type.ONE_ENTRY;
        pkt.singleKey = pair;
        pkt.singleRelation = rel;
        return pkt;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.packetType.ordinal());
        if (this.packetType == Type.FULL_MAP) {
            for (final Map.Entry<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> e : this.fullMap.entrySet()) {
                final LOTRFactionRelations.FactionPair key = e.getKey();
                final LOTRFactionRelations.Relation rel = e.getValue();
                data.writeByte(key.getLeft().ordinal());
                data.writeByte(key.getRight().ordinal());
                data.writeByte(rel.ordinal());
            }
            data.writeByte(-1);
        }
        else if (this.packetType != Type.RESET) {
            if (this.packetType == Type.ONE_ENTRY) {
                data.writeByte(this.singleKey.getLeft().ordinal());
                data.writeByte(this.singleKey.getRight().ordinal());
                data.writeByte(this.singleRelation.ordinal());
            }
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final int typeID = data.readByte();
        this.packetType = Type.forID(typeID);
        if (this.packetType == Type.FULL_MAP) {
            this.fullMap = new HashMap<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation>();
            int fac1ID = 0;
            while ((fac1ID = data.readByte()) >= 0) {
                final int fac2ID = data.readByte();
                final int relID = data.readByte();
                final LOTRFaction f1 = LOTRFaction.forID(fac1ID);
                final LOTRFaction f2 = LOTRFaction.forID(fac2ID);
                final LOTRFactionRelations.FactionPair key = new LOTRFactionRelations.FactionPair(f1, f2);
                final LOTRFactionRelations.Relation rel = LOTRFactionRelations.Relation.forID(relID);
                this.fullMap.put(key, rel);
            }
        }
        else if (this.packetType != Type.RESET) {
            if (this.packetType == Type.ONE_ENTRY) {
                final int fac1ID = data.readByte();
                final int fac2ID = data.readByte();
                final int relID = data.readByte();
                final LOTRFaction f1 = LOTRFaction.forID(fac1ID);
                final LOTRFaction f2 = LOTRFaction.forID(fac2ID);
                this.singleKey = new LOTRFactionRelations.FactionPair(f1, f2);
                this.singleRelation = LOTRFactionRelations.Relation.forID(relID);
            }
        }
    }
    
    public enum Type
    {
        FULL_MAP, 
        RESET, 
        ONE_ENTRY;
        
        public static Type forID(final int id) {
            for (final Type t : values()) {
                if (t.ordinal() == id) {
                    return t;
                }
            }
            return null;
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFactionRelations, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFactionRelations packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final Type t = packet.packetType;
                if (t == Type.FULL_MAP) {
                    LOTRFactionRelations.resetAllRelations();
                    for (final Map.Entry<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> e : packet.fullMap.entrySet()) {
                        final LOTRFactionRelations.FactionPair key = e.getKey();
                        final LOTRFactionRelations.Relation rel = e.getValue();
                        LOTRFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
                    }
                }
                else if (t == Type.RESET) {
                    LOTRFactionRelations.resetAllRelations();
                }
                else if (t == Type.ONE_ENTRY) {
                    final LOTRFactionRelations.FactionPair key2 = packet.singleKey;
                    final LOTRFactionRelations.Relation rel2 = packet.singleRelation;
                    LOTRFactionRelations.overrideRelations(key2.getLeft(), key2.getRight(), rel2);
                }
            }
            return null;
        }
    }
}
