package ru.indieplay.ariamis.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import ru.indieplay.ariamis.Delay;

import java.util.Map;


public class Packet implements IMessage, IMessageHandler<Packet, IMessage> {
    double[] delays;
    String[] delay_names;

    double[] ranges;
    String[] range_names;


    int base;
    public Packet() {

    }
    public Packet(int s) {
        System.out.println("The packet of data created");
        int size= Delay.config.getCategory("general").size();
        delays = new double[size];
        delay_names = new String[size];
        base = Delay.config.get("base", "base", "20").getInt();
        int i=0;
        for (Map.Entry<String, Property> entry : Delay.config.getCategory("general").getValues().entrySet()){
            Property p = entry.getValue();
            delays[i] = p.getDouble();
            delay_names[i] = p.getName();
            i++;
        }
        size= Delay.config.getCategory("range").size();
        ranges = new double[size];
        range_names = new String[size];

        i = 0;
        for (Map.Entry<String, Property> entry : Delay.config.getCategory("range").getValues().entrySet()){
            Property p = entry.getValue();
            ranges[i] = p.getDouble();
            range_names[i] = p.getName();
            i++;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        base = buf.readInt();

        int size = buf.readInt();
        delays=new double[size];
        delay_names=new String[size];
        while(size-- > 0){
            delays[size] = buf.readDouble();
            int strlen = buf.readInt();
            char[] symbols = new char[strlen];
            int i=0;
            while(i<strlen){
                symbols[i++] = buf.readChar();
            }
            delay_names[size] = String.copyValueOf(symbols);
        }

        size = buf.readInt();
        ranges=new double[size];
        range_names=new String[size];
        while(size-- > 0){
            ranges[size] = buf.readDouble();
            int strlen = buf.readInt();
            char[] symbols = new char[strlen];
            int i=0;
            while(i<strlen){
                symbols[i++] = buf.readChar();
            }
            range_names[size] = String.copyValueOf(symbols);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(base);
        writeArray(buf, delays, delay_names);
        writeArray(buf, ranges, range_names);

    }

    private void writeArray(ByteBuf buf, double[] ranges, String[] range_names) {
        buf.writeInt(ranges.length);
        for (int i = 0; i< ranges.length; i++){
            buf.writeDouble(ranges[i]);
            char[] str= range_names[i].toCharArray();
            buf.writeInt(str.length);
            for(char c: str){
                buf.writeChar(c);
            }

        }
    }

    // クライアントサイドのプログラムが通信を受けた時に情報に基づき、演奏音を鳴らす。
    @Override
    public IMessage onMessage(Packet message, MessageContext ctx) {
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                Delay.config.removeCategory(Delay.config.getCategory("general"));
                Delay.config.removeCategory(Delay.config.getCategory("range"));

                for (int i=0; i<message.delays.length;i++) {
                    ConfigCategory shit = Delay.config.getCategory("general");
                    Property p = Delay.config.getCategory("base").get("base");
                    p.set(message.base);
                    Property pee = new Property(message.delay_names[i], Double.toString(message.delays[i]), Property.Type.DOUBLE);
                    shit.put(pee.getName(), pee);
                }
                for (int i=0; i<message.ranges.length;i++) {
                    ConfigCategory shit = Delay.config.getCategory("range");
                    Property pee = new Property(message.range_names[i], Double.toString(message.ranges[i]), Property.Type.DOUBLE);
                    shit.put(pee.getName(), pee);
                }

                Delay.config.save();
                Delay.loadConfig();

                System.out.println("Accepted config successfully");
                break;
            case SERVER:
                PacketHandler.INSTANCE.sendToAll(message);
                break;
        }

        return null;
    }
}
