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
import lotr.common.LOTRAchievement;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAchievement implements IMessage
{
    private LOTRAchievement achievement;
    private boolean display;
    
    public LOTRPacketAchievement() {
    }
    
    public LOTRPacketAchievement(final LOTRAchievement ach, final boolean disp) {
        this.achievement = ach;
        this.display = disp;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.achievement.category.ordinal());
        data.writeShort(this.achievement.ID);
        data.writeBoolean(this.display);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int catID = data.readByte();
        final int achID = data.readShort();
        final LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
        this.achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
        this.display = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAchievement, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAchievement packet, final MessageContext context) {
            final LOTRAchievement achievement = packet.achievement;
            if (achievement != null) {
                if (!LOTRMod.proxy.isSingleplayer()) {
                    final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                    LOTRLevelData.getData(entityplayer).addAchievement(achievement);
                }
                if (packet.display) {
                    LOTRMod.proxy.queueAchievement(achievement);
                }
            }
            return null;
        }
    }
}
