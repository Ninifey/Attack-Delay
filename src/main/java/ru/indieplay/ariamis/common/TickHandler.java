package ru.indieplay.ariamis.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class TickHandler {
    public TickHandler() {
        FMLCommonHandler.instance().bus().register(this);
    }

    //FMLNetworkEvent.ClientConnectedToServerEvent
    @SubscribeEvent
    public void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
            PacketHandler.INSTANCE.sendToAll(new Packet(4));
        return;
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        final World world = ((Entity)player).worldObj;
        if (world == null || !world.isRemote) {
            return;
        }
        if (player != null && player instanceof EntityPlayerMP) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)player;
            if (event.phase == TickEvent.Phase.START && entityplayer.playerNetServerHandler != null && !(entityplayer.playerNetServerHandler instanceof Network)) {
                entityplayer.playerNetServerHandler = new Network(MinecraftServer.getServer(), entityplayer.playerNetServerHandler.netManager, entityplayer);
            }
            if (event.phase == TickEvent.Phase.END) {
                final NetHandlerPlayServer netHandler = entityplayer.playerNetServerHandler;
                if (netHandler instanceof Network) {
                    ((Network)netHandler).update();
                }
                final ItemStack heldItem = entityplayer.getHeldItem();
                if (heldItem != null && (heldItem.getItem() instanceof ItemWritableBook || heldItem.getItem() instanceof ItemEditableBook)) {
                    entityplayer.func_143004_u();
                }
            }
        }
    }

}
