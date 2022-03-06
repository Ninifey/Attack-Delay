// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.FMLLog;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRShields;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketShield implements IMessage
{
    private UUID player;
    private LOTRShields shield;
    
    public LOTRPacketShield() {
    }
    
    public LOTRPacketShield(final UUID uuid) {
        this.player = uuid;
        final LOTRPlayerData pd = LOTRLevelData.getData(this.player);
        this.shield = pd.getShield();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.player.getMostSignificantBits());
        data.writeLong(this.player.getLeastSignificantBits());
        final boolean hasShield = this.shield != null;
        data.writeBoolean(hasShield);
        if (hasShield) {
            data.writeByte(this.shield.shieldID);
            data.writeByte(this.shield.shieldType.ordinal());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.player = new UUID(data.readLong(), data.readLong());
        final boolean hasShield = data.readBoolean();
        if (hasShield) {
            final int shieldID = data.readByte();
            final int shieldTypeID = data.readByte();
            if (shieldTypeID < 0 || shieldTypeID >= LOTRShields.ShieldType.values().length) {
                FMLLog.severe("Failed to update LOTR shield on client side: There is no shieldtype with ID " + shieldTypeID, new Object[0]);
            }
            else {
                final LOTRShields.ShieldType shieldType = LOTRShields.ShieldType.values()[shieldTypeID];
                if (shieldID < 0 || shieldID >= shieldType.list.size()) {
                    FMLLog.severe("Failed to update LOTR shield on client side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID, new Object[0]);
                }
                else {
                    this.shield = shieldType.list.get(shieldID);
                }
            }
        }
        else {
            this.shield = null;
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketShield, IMessage>
    {
        public IMessage onMessage(final LOTRPacketShield packet, final MessageContext context) {
            final LOTRPlayerData pd = LOTRLevelData.getData(packet.player);
            pd.setShield(packet.shield);
            return null;
        }
    }
}
