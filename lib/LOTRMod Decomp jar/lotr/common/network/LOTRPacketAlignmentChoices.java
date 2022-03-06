// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.Iterator;
import io.netty.buffer.ByteBuf;
import java.util.HashSet;
import lotr.common.fac.LOTRFaction;
import java.util.Set;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAlignmentChoices implements IMessage
{
    private Set<LOTRFaction> setZeroFacs;
    
    public LOTRPacketAlignmentChoices() {
        this.setZeroFacs = new HashSet<LOTRFaction>();
    }
    
    public LOTRPacketAlignmentChoices(final Set<LOTRFaction> facs) {
        this.setZeroFacs = new HashSet<LOTRFaction>();
        this.setZeroFacs = facs;
    }
    
    public void toBytes(final ByteBuf data) {
        for (final LOTRFaction fac : this.setZeroFacs) {
            data.writeByte(fac.ordinal());
        }
        data.writeByte(-1);
    }
    
    public void fromBytes(final ByteBuf data) {
        int facID = 0;
        while ((facID = data.readByte()) >= 0) {
            final LOTRFaction fac = LOTRFaction.forID(facID);
            if (fac != null) {
                this.setZeroFacs.add(fac);
            }
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAlignmentChoices, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAlignmentChoices packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            playerData.chooseUnwantedAlignments(entityplayer, packet.setZeroFacs);
            return null;
        }
    }
}
