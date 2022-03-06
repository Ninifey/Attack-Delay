// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBrokenPledge implements IMessage
{
    private int cooldown;
    private int cooldownStart;
    private LOTRFaction brokenFac;
    
    public LOTRPacketBrokenPledge() {
    }
    
    public LOTRPacketBrokenPledge(final int cd, final int start, final LOTRFaction f) {
        this.cooldown = cd;
        this.cooldownStart = start;
        this.brokenFac = f;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cooldown);
        data.writeInt(this.cooldownStart);
        if (this.brokenFac == null) {
            data.writeByte(-1);
        }
        else {
            data.writeByte(this.brokenFac.ordinal());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cooldown = data.readInt();
        this.cooldownStart = data.readInt();
        final int facID = data.readByte();
        if (facID >= 0) {
            this.brokenFac = LOTRFaction.forID(facID);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBrokenPledge, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBrokenPledge packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            pd.setPledgeBreakCooldown(packet.cooldown);
            pd.setPledgeBreakCooldownStart(packet.cooldownStart);
            pd.setBrokenPledgeFaction(packet.brokenFac);
            return null;
        }
    }
}
