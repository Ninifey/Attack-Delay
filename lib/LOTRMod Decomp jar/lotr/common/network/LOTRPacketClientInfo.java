// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.HashMap;
import java.util.Iterator;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRDimension;
import java.util.Map;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketClientInfo implements IMessage
{
    private LOTRFaction viewingFaction;
    private Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap;
    private boolean showWP;
    private boolean showCWP;
    private boolean showHiddenSWP;
    
    public LOTRPacketClientInfo() {
    }
    
    public LOTRPacketClientInfo(final LOTRFaction f, final Map<LOTRDimension.DimensionRegion, LOTRFaction> crMap, final boolean w, final boolean cw, final boolean h) {
        this.viewingFaction = f;
        this.changedRegionMap = crMap;
        this.showWP = w;
        this.showCWP = cw;
        this.showHiddenSWP = h;
    }
    
    public void toBytes(final ByteBuf data) {
        if (this.viewingFaction == null) {
            data.writeByte(-1);
        }
        else {
            data.writeByte(this.viewingFaction.ordinal());
        }
        final int changedRegionsSize = (this.changedRegionMap != null) ? this.changedRegionMap.size() : 0;
        data.writeByte(changedRegionsSize);
        if (changedRegionsSize > 0) {
            for (final Map.Entry<LOTRDimension.DimensionRegion, LOTRFaction> e : this.changedRegionMap.entrySet()) {
                final LOTRDimension.DimensionRegion reg = e.getKey();
                final LOTRFaction fac = e.getValue();
                data.writeByte(reg.ordinal());
                data.writeByte(fac.ordinal());
            }
        }
        data.writeBoolean(this.showWP);
        data.writeBoolean(this.showCWP);
        data.writeBoolean(this.showHiddenSWP);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int factionID = data.readByte();
        if (factionID >= 0) {
            this.viewingFaction = LOTRFaction.forID(factionID);
        }
        final int changedRegionsSize = data.readByte();
        if (changedRegionsSize > 0) {
            this.changedRegionMap = new HashMap<LOTRDimension.DimensionRegion, LOTRFaction>();
            for (int l = 0; l < changedRegionsSize; ++l) {
                final LOTRDimension.DimensionRegion reg = LOTRDimension.DimensionRegion.forID(data.readByte());
                final LOTRFaction fac = LOTRFaction.forID(data.readByte());
                this.changedRegionMap.put(reg, fac);
            }
        }
        this.showWP = data.readBoolean();
        this.showCWP = data.readBoolean();
        this.showHiddenSWP = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketClientInfo, IMessage>
    {
        public IMessage onMessage(final LOTRPacketClientInfo packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            if (packet.viewingFaction != null) {
                final LOTRFaction prevFac = pd.getViewingFaction();
                final LOTRFaction newFac = packet.viewingFaction;
                pd.setViewingFaction(newFac);
                if (prevFac != newFac && prevFac.factionRegion == newFac.factionRegion) {
                    pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignment());
                }
                if (prevFac.factionRegion != newFac.factionRegion) {
                    pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignmentRegion());
                }
            }
            final Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap = packet.changedRegionMap;
            if (changedRegionMap != null) {
                for (final LOTRDimension.DimensionRegion reg : changedRegionMap.keySet()) {
                    final LOTRFaction fac = changedRegionMap.get(reg);
                    pd.setRegionLastViewedFaction(reg, fac);
                }
            }
            pd.setShowWaypoints(packet.showWP);
            pd.setShowCustomWaypoints(packet.showCWP);
            pd.setShowHiddenSharedWaypoints(packet.showHiddenSWP);
            return null;
        }
    }
}
