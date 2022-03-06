// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.command.CommandException;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Collection;
import lotr.common.LOTRAchievement;
import java.util.ArrayList;
import net.minecraft.command.ICommand;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandAchievement extends CommandBase
{
    public String getCommandName() {
        return "lotrAchievement";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.lotrAchievement.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 2) {
            final String achName = args[1];
            EntityPlayerMP entityplayer;
            if (args.length >= 3) {
                entityplayer = getPlayer(sender, args[2]);
            }
            else {
                entityplayer = getCommandSenderAsPlayer(sender);
            }
            final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            if (args[0].equalsIgnoreCase("give")) {
                final LOTRAchievement ach = this.findAchievementByName(achName);
                if (playerData.hasAchievement(ach)) {
                    throw new WrongUsageException("commands.lotr.lotrAchievement.give.fail", new Object[] { entityplayer.getCommandSenderName(), ach.getTitle((EntityPlayer)entityplayer) });
                }
                playerData.addAchievement(ach);
                func_152373_a(sender, (ICommand)this, "commands.lotr.lotrAchievement.give", new Object[] { entityplayer.getCommandSenderName(), ach.getTitle((EntityPlayer)entityplayer) });
                return;
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                if (achName.equalsIgnoreCase("all")) {
                    final List<LOTRAchievement> allAchievements = new ArrayList<LOTRAchievement>(playerData.getAchievements());
                    for (final LOTRAchievement ach2 : allAchievements) {
                        playerData.removeAchievement(ach2);
                    }
                    func_152373_a(sender, (ICommand)this, "commands.lotr.lotrAchievement.removeAll", new Object[] { entityplayer.getCommandSenderName() });
                    return;
                }
                final LOTRAchievement ach = this.findAchievementByName(achName);
                if (!playerData.hasAchievement(ach)) {
                    throw new WrongUsageException("commands.lotr.lotrAchievement.remove.fail", new Object[] { entityplayer.getCommandSenderName(), ach.getTitle((EntityPlayer)entityplayer) });
                }
                playerData.removeAchievement(ach);
                func_152373_a(sender, (ICommand)this, "commands.lotr.lotrAchievement.remove", new Object[] { entityplayer.getCommandSenderName(), ach.getTitle((EntityPlayer)entityplayer) });
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    private LOTRAchievement findAchievementByName(final String name) {
        final LOTRAchievement ach = LOTRAchievement.findByName(name);
        if (ach == null) {
            throw new CommandException("commands.lotr.lotrAchievement.unknown", new Object[] { name });
        }
        return ach;
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "give", "remove" });
        }
        if (args.length == 2) {
            final List<LOTRAchievement> achievements = LOTRAchievement.getAllAchievements();
            final List<String> names = new ArrayList<String>();
            for (final LOTRAchievement a : achievements) {
                names.add(a.getCodeName());
            }
            if (args[0].equals("remove")) {
                names.add("all");
            }
            return getListOfStringsMatchingLastWord(args, (String[])names.toArray(new String[0]));
        }
        if (args.length == 3) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return i == 2;
    }
}
