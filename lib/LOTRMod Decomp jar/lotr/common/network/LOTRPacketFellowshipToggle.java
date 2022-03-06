// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowshipClient;

public class LOTRPacketFellowshipToggle extends LOTRPacketFellowshipDo
{
    private ToggleFunction function;
    
    public LOTRPacketFellowshipToggle() {
    }
    
    public LOTRPacketFellowshipToggle(final LOTRFellowshipClient fs, final ToggleFunction f) {
        super(fs);
        this.function = f;
    }
    
    @Override
    public void toBytes(final ByteBuf data) {
        super.toBytes(data);
        data.writeByte(this.function.ordinal());
    }
    
    @Override
    public void fromBytes(final ByteBuf data) {
        super.fromBytes(data);
        this.function = ToggleFunction.values()[data.readByte()];
    }
    
    public enum ToggleFunction
    {
        PVP, 
        HIRED_FF, 
        MAP_SHOW;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipToggle, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipToggle packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            if (fellowship != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                if (packet.function == ToggleFunction.PVP) {
                    final boolean current = fellowship.getPreventPVP();
                    playerData.setFellowshipPreventPVP(fellowship, !current);
                }
                else if (packet.function == ToggleFunction.HIRED_FF) {
                    final boolean current = fellowship.getPreventHiredFriendlyFire();
                    playerData.setFellowshipPreventHiredFF(fellowship, !current);
                }
                else if (packet.function == ToggleFunction.MAP_SHOW) {
                    final boolean current = fellowship.getShowMapLocations();
                    playerData.setFellowshipShowMapLocations(fellowship, !current);
                }
            }
            return null;
        }
    }
}
