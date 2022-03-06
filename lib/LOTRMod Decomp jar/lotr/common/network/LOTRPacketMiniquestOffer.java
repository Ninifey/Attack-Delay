// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMiniquestOffer implements IMessage
{
    private int entityID;
    private NBTTagCompound miniquestData;
    
    public LOTRPacketMiniquestOffer() {
    }
    
    public LOTRPacketMiniquestOffer(final int id, final NBTTagCompound nbt) {
        this.entityID = id;
        this.miniquestData = nbt;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.miniquestData);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing miniquest data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        try {
            this.miniquestData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading miniquest data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static void sendClosePacket(final EntityPlayer entityplayer, final LOTREntityNPC npc, final boolean accept) {
        if (entityplayer == null) {
            FMLLog.warning("LOTR Warning: Tried to send miniquest offer close packet, but player == null", new Object[0]);
            return;
        }
        final LOTRPacketMiniquestOfferClose packet = new LOTRPacketMiniquestOfferClose(npc.getEntityId(), accept);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMiniquestOffer, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMiniquestOffer packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                final LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
                if (quest != null) {
                    LOTRMod.proxy.displayMiniquestOffer(quest, npc);
                }
                else {
                    LOTRPacketMiniquestOffer.sendClosePacket(entityplayer, npc, false);
                }
            }
            return null;
        }
    }
}
