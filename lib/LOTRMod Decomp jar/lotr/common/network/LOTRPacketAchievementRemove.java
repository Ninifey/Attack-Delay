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

public class LOTRPacketAchievementRemove implements IMessage
{
    private LOTRAchievement achievement;
    
    public LOTRPacketAchievementRemove() {
    }
    
    public LOTRPacketAchievementRemove(final LOTRAchievement ach) {
        this.achievement = ach;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.achievement.category.ordinal());
        data.writeShort(this.achievement.ID);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int catID = data.readByte();
        final int achID = data.readShort();
        final LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
        this.achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAchievementRemove, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAchievementRemove packet, final MessageContext context) {
            final LOTRAchievement achievement = packet.achievement;
            if (achievement != null && !LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                LOTRLevelData.getData(entityplayer).removeAchievement(achievement);
            }
            return null;
        }
    }
}
