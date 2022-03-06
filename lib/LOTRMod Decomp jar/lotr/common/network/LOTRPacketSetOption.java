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
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSetOption implements IMessage
{
    private int option;
    
    public LOTRPacketSetOption() {
    }
    
    public LOTRPacketSetOption(final int i) {
        this.option = i;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.option);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.option = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketSetOption, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSetOption packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            if (packet.option == 0) {
                final boolean flag = pd.getFriendlyFire();
                pd.setFriendlyFire(!flag);
            }
            else if (packet.option == 1) {
                final boolean flag = pd.getEnableHiredDeathMessages();
                pd.setEnableHiredDeathMessages(!flag);
            }
            else if (packet.option == 2) {
                final boolean flag = pd.getHideAlignment();
                pd.setHideAlignment(!flag);
            }
            else if (packet.option == 3) {
                final boolean flag = pd.getHideMapLocation();
                pd.setHideMapLocation(!flag);
            }
            else if (packet.option == 4) {
                final boolean flag = pd.getFemRankOverride();
                pd.setFemRankOverride(!flag);
            }
            else if (packet.option == 5) {
                final boolean flag = pd.getEnableConquestKills();
                pd.setEnableConquestKills(!flag);
            }
            return null;
        }
    }
}
