package ru.indieplay.ariamis.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import ru.indieplay.ariamis.Delay;

import java.util.Map;


public class PacketDamage implements IMessage, IMessageHandler<PacketDamage, IMessage> {
    int s;
    String name;
    public PacketDamage() {

    }
    public PacketDamage(int s, String name) {
        this.s=s;
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        s = buf.readInt();

        int strlen = buf.readInt();
        char[] symbols = new char[strlen];
        int i=0;
        while(i<strlen){
            symbols[i++] = buf.readChar();
        }
        name = String.copyValueOf(symbols);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(s);
        char[] str= name.toCharArray();
        buf.writeInt(str.length);
        for(char c: str){
            buf.writeChar(c);
        }
    }

    // クライアントサイドのプログラムが通信を受けた時に情報に基づき、演奏音を鳴らす。
    @Override
    public IMessage onMessage(PacketDamage message, MessageContext ctx) {
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                break;
            case SERVER:
                final EntityPlayerMP thePlayer = ctx.getServerHandler().playerEntity;
                if(thePlayer != null) {
                    Entity entity = thePlayer.worldObj.getEntityByID(message.s);
                    if (entity != null)
                        thePlayer.attackTargetEntityWithCurrentItem(entity);
                }
                break;
        }

        return null;
    }
}
