// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.FMLLog;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRShields;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSelectShield implements IMessage
{
    private LOTRShields shield;
    
    public LOTRPacketSelectShield() {
    }
    
    public LOTRPacketSelectShield(final LOTRShields s) {
        this.shield = s;
    }
    
    public void toBytes(final ByteBuf data) {
        if (this.shield == null) {
            data.writeByte(-1);
        }
        else {
            data.writeByte(this.shield.shieldID);
            data.writeByte(this.shield.shieldType.ordinal());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final int shieldID = data.readByte();
        if (shieldID == -1) {
            this.shield = null;
        }
        else {
            final int shieldTypeID = data.readByte();
            if (shieldTypeID < 0 || shieldTypeID >= LOTRShields.ShieldType.values().length) {
                FMLLog.severe("Failed to update LOTR shield on server side: There is no shieldtype with ID " + shieldTypeID, new Object[0]);
            }
            else {
                final LOTRShields.ShieldType shieldType = LOTRShields.ShieldType.values()[shieldTypeID];
                if (shieldID < 0 || shieldID >= shieldType.list.size()) {
                    FMLLog.severe("Failed to update LOTR shield on server side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID, new Object[0]);
                }
                else {
                    this.shield = shieldType.list.get(shieldID);
                }
            }
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketSelectShield, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSelectShield packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final LOTRShields shield = packet.shield;
            if (shield == null || shield.canPlayerWear(entityplayer)) {
                LOTRLevelData.getData(entityplayer).setShield(shield);
                LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, ((Entity)entityplayer).worldObj);
            }
            else {
                FMLLog.severe("Failed to update LOTR shield on server side: Player " + entityplayer.getCommandSenderName() + " cannot wear shield " + shield.name(), new Object[0]);
            }
            return null;
        }
    }
}
