package ru.indieplay.ariamis;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.config.Property;
import ru.indieplay.ariamis.common.Packet;
import ru.indieplay.ariamis.common.PacketHandler;

import java.util.List;

public class ReloadConfig implements ICommand {
    @Override
    public String getCommandName() {
        return "ad";
    }

    @Override
    public String getCommandUsage(ICommandSender ic) {
        String name = ic.getCommandSenderName();
        return null;
    }

    @Override
    public List getCommandAliases() {
        return null;
    }


    /*
        перезагрузка конфига
        /ad (reload|r)

        добавление или изменение параметра атаки для оружия, после использования нужно выполнить /add r
        /ad (add|a) (s|speed|r|reach) 0.1

        вывести текущие значения параметров
        /ad i
     */
    @Override
    public void processCommand(ICommandSender p, String[] str) {
        if (str.length == 0) return;
            if (str[0].equals("reload") || str[0].equals("r")) {
                Delay.loadConfig();
                PacketHandler.INSTANCE.sendToAll(new Packet(4));
            }
            if ((str[0].equals("add") || str[0].equals("a") ) && str.length>2 && p instanceof EntityPlayer && ((EntityPlayer) p).getHeldItem() != null) {

                    ItemStack is = ((EntityPlayer) p).getHeldItem();
                    if (is.getItem() != null) {
                        String clasz = is.getItem().getClass().getName();
                        Double val =  Double.parseDouble(str[2]);
                        String cat = "";
                        if(val.isNaN() || val.isInfinite())return;
                        else if(str[1].equals("speed") || str[1].equals("s")) cat = "general";
                        else if(str[1].equals("reach") || str[1].equals("r")) cat = "range";
                        else return;

                        Property prop = Delay.config.get(cat, clasz,0);
                        prop.set(val);
                        Delay.config.save();
                        p.addChatMessage(new ChatComponentText(String.format("Value of %s %s set to %f successfully",cat,clasz,val)));
                        Delay.loadConfig();
                    }

            }
            if (str[0].equals("info") || str[0].equals("i")) {
                if (p instanceof EntityPlayer && ((EntityPlayer) p).getHeldItem() != null) {
                    ItemStack is = ((EntityPlayer) p).getHeldItem();
                    if (is.getItem() != null) {
                        String clasz = is.getItem().getClass().getName();
                        if (Delay.config.hasKey("general", clasz)) {
                            p.addChatMessage(new ChatComponentText(String.format("Item: %s \nThe value of attack speed for this item is: %f \nThe value of reach speed for this item is: %f but unused. \n", clasz, Delay.config.get("general", clasz, 1.0).getDouble(), Delay.config.get("range", clasz, 1.0).getDouble())));
                        }
                    }
                }
            }
            if(str[0].equals("help") || str[0].equals("?") || str[0].equals("h")){
                String[] header = new String[6];
                header[0] = "This is help page for Ariamis mod named as AttackDelay.";
                header[1] = "This page can be called by followed commands \n /ad h \n /ad help \n /ad ? \n .";
                header[2] = "You can modify speed of weapon reloading (swing animation) by followed commands \n /ad a s {0.2 - 10} \n /ad add s {0.2 - 10}  \n /ad add speed {0.2 - 10} \n.";
                header[3] = "You can't modify range of weapon attack by followed commands \n /ad a r {0.2 - 10} \n /ad add r {0.2 - 10}  \n /ad add range {0.2 - 10} \n.";
                header[4] = "Followed commands will show the item name and saved configuration for item \n /ad i \n /ad info  \n.";
                header[5] = "You must reload config after settings by one of followed commands \n /ad r \n /ad reload  \n.";
                int size=0;
                for(String line: header){
                    String[] s=line.split("\n");
                    for(String l: s){
                        p.addChatMessage(new ChatComponentText(l));
                    }
                }
            }
            if (str[0].equals("reset")) {

            }
        p.addChatMessage(new ChatComponentText("debug: command " + String.join("|", str)));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p) {
        return p instanceof EntityPlayer; //&& Minecraft.getMinecraft().theWorld.getPlayerEntityByName( p.getCommandSenderName()).capabilities.isCreativeMode;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
