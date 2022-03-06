// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.util.IChatComponent;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.UUID;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import java.util.Arrays;
import java.util.List;
import net.minecraft.command.CommandBase;

public class LOTRCommandFellowshipMessage extends CommandBase
{
    public String getCommandName() {
        return "fmsg";
    }
    
    public List getCommandAliases() {
        return Arrays.asList("fchat");
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return sender instanceof EntityPlayer || super.canCommandSenderUseCommand(sender);
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.fmsg.usage";
    }
    
    public void processCommand(final ICommandSender sender, String[] args) {
        final EntityPlayerMP entityplayer = getCommandSenderAsPlayer(sender);
        final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
        if (args.length >= 1) {
            if (args[0].equals("bind") && args.length >= 2) {
                args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, false);
                final String fsName = args[1];
                final LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
                if (fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.getUniqueID())) {
                    playerData.setChatBoundFellowship(fellowship);
                    final IChatComponent notif = (IChatComponent)new ChatComponentTranslation("commands.lotr.fmsg.bind", new Object[] { fellowship.getName() });
                    notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
                    notif.getChatStyle().setItalic(Boolean.valueOf(true));
                    sender.addChatMessage(notif);
                    return;
                }
                throw new WrongUsageException("commands.lotr.fmsg.notFound", new Object[] { fsName });
            }
            else {
                if (args[0].equals("unbind")) {
                    final LOTRFellowship preBoundFellowship = playerData.getChatBoundFellowship();
                    playerData.setChatBoundFellowshipID(null);
                    if (preBoundFellowship == null || preBoundFellowship.containsPlayer(entityplayer.getUniqueID())) {}
                    final IChatComponent notif2 = (IChatComponent)new ChatComponentTranslation("commands.lotr.fmsg.unbind", new Object[] { preBoundFellowship.getName() });
                    notif2.getChatStyle().setColor(EnumChatFormatting.GRAY);
                    notif2.getChatStyle().setItalic(Boolean.valueOf(true));
                    sender.addChatMessage(notif2);
                    return;
                }
                LOTRFellowship fellowship2 = null;
                int msgStartIndex = 0;
                if (args[0].startsWith("\"")) {
                    args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, false);
                    final String fsName2 = args[0];
                    fellowship2 = playerData.getFellowshipByName(fsName2);
                    if (fellowship2 == null) {
                        throw new WrongUsageException("commands.lotr.fmsg.notFound", new Object[] { fsName2 });
                    }
                    msgStartIndex = 1;
                }
                if (fellowship2 == null) {
                    fellowship2 = playerData.getChatBoundFellowship();
                    if (fellowship2 == null) {
                        throw new WrongUsageException("commands.lotr.fmsg.boundNone", new Object[0]);
                    }
                    if (fellowship2.isDisbanded() || !fellowship2.containsPlayer(entityplayer.getUniqueID())) {
                        throw new WrongUsageException("commands.lotr.fmsg.boundNotMember", new Object[] { fellowship2.getName() });
                    }
                }
                if (fellowship2 != null) {
                    final IChatComponent message = func_147176_a(sender, args, msgStartIndex, false);
                    fellowship2.sendFellowshipMessage(entityplayer, message.getUnformattedText());
                    return;
                }
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, String[] args) {
        final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)getCommandSenderAsPlayer(sender));
        final String[] argsOriginal = Arrays.copyOf(args, args.length);
        if (args.length >= 2 && args[0].equals("bind")) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, true);
            return LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 1, playerData, false);
        }
        if (args.length >= 1) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, true);
            final List matches = new ArrayList();
            if (args.length == 1 && !argsOriginal[0].startsWith("\"")) {
                matches.addAll(getListOfStringsMatchingLastWord(args, new String[] { "bind", "unbind" }));
            }
            matches.addAll(LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 0, playerData, false));
            return matches;
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
