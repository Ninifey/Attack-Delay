// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.NBTBase;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.LOTRPlayerData;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.command.ICommand;
import lotr.common.LOTRLevelData;
import net.minecraft.command.WrongUsageException;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.PlayerNotFoundException;
import java.util.UUID;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandFellowship extends CommandBase
{
    public String getCommandName() {
        return "fellowship";
    }
    
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.fellowship.usage";
    }
    
    private UUID getPlayerIDByName(final ICommandSender sender, final String username) {
        try {
            final EntityPlayerMP entityplayer = getPlayer(sender, username);
            if (entityplayer != null) {
                return entityplayer.getUniqueID();
            }
        }
        catch (PlayerNotFoundException ex) {}
        final GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
        if (profile != null) {
            return profile.getId();
        }
        return null;
    }
    
    public static String[] fixArgsForFellowship(final String[] args, final int startIndex, final boolean autocompleting) {
        if (args[startIndex].startsWith("\"")) {
            int endIndex = startIndex;
            boolean foundEnd = false;
            while (!foundEnd) {
                if (args[endIndex].endsWith("\"")) {
                    foundEnd = true;
                }
                else if (endIndex >= args.length - 1) {
                    if (autocompleting) {
                        break;
                    }
                    throw new WrongUsageException("commands.lotr.fellowship.edit.nameError", new Object[0]);
                }
                else {
                    ++endIndex;
                }
            }
            String fsName = "";
            for (int i = startIndex; i <= endIndex; ++i) {
                if (i > startIndex) {
                    fsName += " ";
                }
                fsName += args[i];
            }
            if (!autocompleting || foundEnd) {
                fsName = fsName.replace("\"", "");
            }
            final int diff = endIndex - startIndex;
            final String[] argsNew = new String[args.length - diff];
            for (int j = 0; j < argsNew.length; ++j) {
                if (j < startIndex) {
                    argsNew[j] = args[j];
                }
                else if (j == startIndex) {
                    argsNew[j] = fsName;
                }
                else {
                    argsNew[j] = args[j + diff];
                }
            }
            return argsNew;
        }
        if (!autocompleting) {
            throw new WrongUsageException("commands.lotr.fellowship.edit.nameError", new Object[0]);
        }
        return args;
    }
    
    public void processCommand(final ICommandSender sender, String[] args) {
        if (args.length < 3 || !args[0].equals("create")) {
            if (args[0].equals("option")) {
                args = fixArgsForFellowship(args, 2, false);
                if (args.length < 4) {
                    throw new PlayerNotFoundException();
                }
                final String ownerName = args[1];
                final String fsName = args[2];
                if (fsName == null) {
                    throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", new Object[] { ownerName, fsName });
                }
                final String option = args[3];
                final UUID ownerID = this.getPlayerIDByName(sender, ownerName);
                if (ownerID != null) {
                    final LOTRPlayerData ownerData = LOTRLevelData.getData(ownerID);
                    final LOTRFellowship fellowship = ownerData.getFellowshipByName(fsName);
                    if (fellowship == null || !fellowship.isOwner(ownerID)) {
                        throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", new Object[] { ownerName, fsName });
                    }
                    if (option.equals("disband")) {
                        ownerData.disbandFellowship(fellowship);
                        func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.disband", new Object[] { ownerName, fsName });
                        return;
                    }
                    if (option.equals("rename")) {
                        String newName = "";
                        final int startIndex = 4;
                        if (args[startIndex].startsWith("\"")) {
                            int endIndex = startIndex;
                            while (!args[endIndex].endsWith("\"")) {
                                if (++endIndex >= args.length) {
                                    throw new WrongUsageException("commands.lotr.fellowship.rename.error", new Object[0]);
                                }
                            }
                            for (int i = startIndex; i <= endIndex; ++i) {
                                if (i > startIndex) {
                                    newName += " ";
                                }
                                newName += args[i];
                            }
                            newName = newName.replace("\"", "");
                        }
                        if (!StringUtils.isBlank((CharSequence)newName)) {
                            ownerData.renameFellowship(fellowship, newName);
                            func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.rename", new Object[] { ownerName, fsName, newName });
                            return;
                        }
                        throw new WrongUsageException("commands.lotr.fellowship.rename.error", new Object[0]);
                    }
                    else if (option.equals("icon")) {
                        final String iconData = func_147178_a(sender, args, 4).getUnformattedText();
                        if (iconData.equals("clear")) {
                            ownerData.setFellowshipIcon(fellowship, null);
                            func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.icon", new Object[] { ownerName, fsName, "[none]" });
                            return;
                        }
                        ItemStack itemstack = null;
                        try {
                            final NBTBase nbt = JsonToNBT.func_150315_a(iconData);
                            if (!(nbt instanceof NBTTagCompound)) {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.icon.tagError", new Object[] { "Not a valid tag" });
                                return;
                            }
                            final NBTTagCompound compound = (NBTTagCompound)nbt;
                            itemstack = ItemStack.loadItemStackFromNBT(compound);
                        }
                        catch (NBTException nbtexception) {
                            func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.icon.tagError", new Object[] { nbtexception.getMessage() });
                            return;
                        }
                        if (itemstack != null) {
                            ownerData.setFellowshipIcon(fellowship, itemstack);
                            func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.icon", new Object[] { ownerName, fsName, itemstack.getDisplayName() });
                            return;
                        }
                        func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.icon.tagError", new Object[] { "No item" });
                        return;
                    }
                    else if (option.equals("pvp") || option.equals("hired-ff")) {
                        final String setting = args[4];
                        boolean prevent;
                        if (setting.equals("prevent")) {
                            prevent = true;
                        }
                        else {
                            if (!setting.equals("allow")) {
                                throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
                            }
                            prevent = false;
                        }
                        if (option.equals("pvp")) {
                            ownerData.setFellowshipPreventPVP(fellowship, prevent);
                            if (prevent) {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.pvp.prevent", new Object[] { ownerName, fsName });
                            }
                            else {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.pvp.allow", new Object[] { ownerName, fsName });
                            }
                            return;
                        }
                        if (option.equals("hired-ff")) {
                            ownerData.setFellowshipPreventHiredFF(fellowship, prevent);
                            if (prevent) {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.hiredFF.prevent", new Object[] { ownerName, fsName });
                            }
                            else {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.hiredFF.allow", new Object[] { ownerName, fsName });
                            }
                            return;
                        }
                    }
                    else {
                        if (option.equals("map-show")) {
                            final String setting = args[4];
                            boolean show;
                            if (setting.equals("on")) {
                                show = true;
                            }
                            else {
                                if (!setting.equals("off")) {
                                    throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
                                }
                                show = false;
                            }
                            ownerData.setFellowshipShowMapLocations(fellowship, show);
                            if (show) {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.mapShow.on", new Object[] { ownerName, fsName });
                            }
                            else {
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.mapShow.off", new Object[] { ownerName, fsName });
                            }
                            return;
                        }
                        if (args.length < 3) {
                            throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
                        }
                        final String playerName = args[4];
                        final UUID playerID = this.getPlayerIDByName(sender, playerName);
                        if (playerID == null) {
                            throw new PlayerNotFoundException();
                        }
                        final LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
                        if (option.equals("invite")) {
                            if (!fellowship.containsPlayer(playerID)) {
                                ownerData.invitePlayerToFellowship(fellowship, playerID);
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.invite", new Object[] { ownerName, fsName, playerName });
                                return;
                            }
                            throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", new Object[] { ownerName, fsName, playerName });
                        }
                        else if (option.equals("add")) {
                            if (!fellowship.containsPlayer(playerID)) {
                                ownerData.invitePlayerToFellowship(fellowship, playerID);
                                playerData.acceptFellowshipInvite(fellowship);
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.add", new Object[] { ownerName, fsName, playerName });
                                return;
                            }
                            throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", new Object[] { ownerName, fsName, playerName });
                        }
                        else if (option.equals("remove")) {
                            if (fellowship.hasMember(playerID)) {
                                ownerData.removePlayerFromFellowship(fellowship, playerID);
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.remove", new Object[] { ownerName, fsName, playerName });
                                return;
                            }
                            throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[] { ownerName, fsName, playerName });
                        }
                        else if (option.equals("transfer")) {
                            if (fellowship.hasMember(playerID)) {
                                ownerData.transferFellowship(fellowship, playerID);
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.transfer", new Object[] { ownerName, fsName, playerName });
                                return;
                            }
                            throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[] { ownerName, fsName, playerName });
                        }
                        else if (option.equals("op")) {
                            if (!fellowship.hasMember(playerID)) {
                                throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[] { ownerName, fsName, playerName });
                            }
                            if (!fellowship.isAdmin(playerID)) {
                                ownerData.setFellowshipAdmin(fellowship, playerID, true);
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.op", new Object[] { ownerName, fsName, playerName });
                                return;
                            }
                            throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyOp", new Object[] { ownerName, fsName, playerName });
                        }
                        else if (option.equals("deop")) {
                            if (!fellowship.hasMember(playerID)) {
                                throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[] { ownerName, fsName, playerName });
                            }
                            if (fellowship.isAdmin(playerID)) {
                                ownerData.setFellowshipAdmin(fellowship, playerID, false);
                                func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.deop", new Object[] { ownerName, fsName, playerName });
                                return;
                            }
                            throw new WrongUsageException("commands.lotr.fellowship.edit.notOp", new Object[] { ownerName, fsName, playerName });
                        }
                    }
                }
            }
            throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
        }
        args = fixArgsForFellowship(args, 2, false);
        final String playerName2 = args[1];
        final String fsName = args[2];
        if (fsName == null) {
            throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", new Object[] { playerName2, fsName });
        }
        final UUID playerID2 = this.getPlayerIDByName(sender, playerName2);
        if (playerID2 == null) {
            throw new PlayerNotFoundException();
        }
        final LOTRPlayerData playerData2 = LOTRLevelData.getData(playerID2);
        final LOTRFellowship fellowship2 = playerData2.getFellowshipByName(fsName);
        if (fellowship2 == null) {
            playerData2.createFellowship(fsName, false);
            func_152373_a(sender, (ICommand)this, "commands.lotr.fellowship.create", new Object[] { playerName2, fsName });
            return;
        }
        throw new WrongUsageException("commands.lotr.fellowship.create.exists", new Object[] { playerName2, fsName });
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "create", "option" });
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        if (args.length > 2) {
            final String function = args[0];
            if (function.equals("create")) {
                return null;
            }
            if (function.equals("option")) {
                final String[] argsOriginal = Arrays.copyOf(args, args.length);
                args = fixArgsForFellowship(args, 2, true);
                final String ownerName = args[1];
                final UUID ownerID = this.getPlayerIDByName(sender, ownerName);
                if (ownerID != null) {
                    final LOTRPlayerData playerData = LOTRLevelData.getData(ownerID);
                    final String fsName = args[2];
                    if (args.length == 3) {
                        return listFellowshipsMatchingLastWord(args, argsOriginal, 2, playerData, true);
                    }
                    if (fsName != null) {
                        final LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
                        if (fellowship != null) {
                            if (args.length == 4) {
                                return getListOfStringsMatchingLastWord(args, new String[] { "invite", "add", "remove", "transfer", "op", "deop", "disband", "rename", "icon", "pvp", "hired-ff", "map-show" });
                            }
                            final String option = args[3];
                            if (option.equals("invite") || option.equals("add")) {
                                final List<String> notInFellowshipNames = new ArrayList<String>();
                                final GameProfile[] func_152600_g;
                                final GameProfile[] players = func_152600_g = MinecraftServer.getServer().getConfigurationManager().func_152600_g();
                                for (final GameProfile playerProfile : func_152600_g) {
                                    final UUID playerID = playerProfile.getId();
                                    if (!fellowship.containsPlayer(playerID)) {
                                        notInFellowshipNames.add(playerProfile.getName());
                                    }
                                }
                                return getListOfStringsMatchingLastWord(args, (String[])notInFellowshipNames.toArray(new String[0]));
                            }
                            if (option.equals("remove") || option.equals("transfer")) {
                                final List<String> memberNames = new ArrayList<String>();
                                for (final UUID playerID2 : fellowship.getMemberUUIDs()) {
                                    final GameProfile playerProfile2 = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID2);
                                    if (playerProfile2 != null && playerProfile2.getName() != null) {
                                        memberNames.add(playerProfile2.getName());
                                    }
                                }
                                return getListOfStringsMatchingLastWord(args, (String[])memberNames.toArray(new String[0]));
                            }
                            if (option.equals("op")) {
                                final List<String> notAdminNames = new ArrayList<String>();
                                for (final UUID playerID2 : fellowship.getMemberUUIDs()) {
                                    if (!fellowship.isAdmin(playerID2)) {
                                        final GameProfile playerProfile2 = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID2);
                                        if (playerProfile2 == null || playerProfile2.getName() == null) {
                                            continue;
                                        }
                                        notAdminNames.add(playerProfile2.getName());
                                    }
                                }
                                return getListOfStringsMatchingLastWord(args, (String[])notAdminNames.toArray(new String[0]));
                            }
                            if (option.equals("deop")) {
                                final List<String> adminNames = new ArrayList<String>();
                                for (final UUID playerID2 : fellowship.getMemberUUIDs()) {
                                    if (fellowship.isAdmin(playerID2)) {
                                        final GameProfile playerProfile2 = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID2);
                                        if (playerProfile2 == null || playerProfile2.getName() == null) {
                                            continue;
                                        }
                                        adminNames.add(playerProfile2.getName());
                                    }
                                }
                                return getListOfStringsMatchingLastWord(args, (String[])adminNames.toArray(new String[0]));
                            }
                            if (option.equals("pvp") || option.equals("hired-ff")) {
                                return getListOfStringsMatchingLastWord(args, new String[] { "prevent", "allow" });
                            }
                            if (option.equals("map-show")) {
                                return getListOfStringsMatchingLastWord(args, new String[] { "on", "off" });
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static List<String> listFellowshipsMatchingLastWord(final String[] argsFixed, final String[] argsOriginal, final int fsNameIndex, final LOTRPlayerData playerData, final boolean leadingOnly) {
        final String fsName = argsFixed[fsNameIndex];
        final List<String> allFellowshipNames = leadingOnly ? playerData.listAllLeadingFellowshipNames() : playerData.listAllFellowshipNames();
        final List<String> autocompletes = new ArrayList<String>();
        for (final String nextFsName : allFellowshipNames) {
            String autocompFsName = "\"" + nextFsName + "\"";
            if (autocompFsName.toLowerCase().startsWith(fsName.toLowerCase())) {
                if (argsOriginal.length > argsFixed.length) {
                    for (int diff = argsOriginal.length - argsFixed.length, j = 0; j < diff; ++j) {
                        autocompFsName = autocompFsName.substring(autocompFsName.indexOf(" ") + 1);
                    }
                }
                if (autocompFsName.indexOf(" ") >= 0) {
                    autocompFsName = autocompFsName.substring(0, autocompFsName.indexOf(" "));
                }
                autocompletes.add(autocompFsName);
            }
        }
        return (List<String>)getListOfStringsMatchingLastWord(argsOriginal, (String[])autocompletes.toArray(new String[0]));
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        if (args.length >= 5 && args[0].equals("option")) {
            final String option = args[3];
            if (option.equals("invite") || option.equals("add") || option.equals("remove") || option.equals("transfer")) {
                return i == 4;
            }
        }
        return false;
    }
}
