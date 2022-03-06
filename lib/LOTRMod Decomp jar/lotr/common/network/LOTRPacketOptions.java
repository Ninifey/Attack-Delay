// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketOptions implements IMessage
{
    private int option;
    private boolean enable;
    
    public LOTRPacketOptions() {
    }
    
    public LOTRPacketOptions(final int i, final boolean flag) {
        this.option = i;
        this.enable = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.option);
        data.writeBoolean(this.enable);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.option = data.readByte();
        this.enable = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketOptions, IMessage>
    {
        public IMessage onMessage(final LOTRPacketOptions packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final int option = packet.option;
                final boolean enable = packet.enable;
                if (option == 0) {
                    LOTRLevelData.getData(entityplayer).setFriendlyFire(enable);
                }
                else if (option == 1) {
                    LOTRLevelData.getData(entityplayer).setEnableHiredDeathMessages(enable);
                }
                else if (option == 3) {
                    LOTRLevelData.getData(entityplayer).setHideMapLocation(enable);
                }
                else if (option == 4) {
                    LOTRLevelData.getData(entityplayer).setFemRankOverride(enable);
                }
                else if (option == 5) {
                    LOTRLevelData.getData(entityplayer).setEnableConquestKills(enable);
                }
            }
            return null;
        }
    }
}
