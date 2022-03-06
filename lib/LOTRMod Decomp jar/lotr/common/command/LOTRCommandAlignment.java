// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import net.minecraft.server.MinecraftServer;
import java.util.Map;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.List;
import java.util.HashMap;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import lotr.common.fac.LOTRFaction;
import java.util.ArrayList;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandAlignment extends CommandBase
{
    public String getCommandName() {
        return "alignment";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.alignment.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 2) {
            List<LOTRFaction> factions = new ArrayList<LOTRFaction>();
            if (args[1].equalsIgnoreCase("all")) {
                factions = LOTRFaction.getPlayableAlignmentFactions();
            }
            else {
                final LOTRFaction faction = LOTRFaction.forName(args[1]);
                if (faction == null) {
                    throw new WrongUsageException("commands.lotr.alignment.noFaction", new Object[] { args[1] });
                }
                factions.add(faction);
            }
            if (args[0].equals("set")) {
                final float alignment = (float)parseDoubleBounded(sender, args[2], -10000.0, 10000.0);
                EntityPlayerMP entityplayer;
                if (args.length >= 4) {
                    entityplayer = getPlayer(sender, args[3]);
                }
                else {
                    entityplayer = getCommandSenderAsPlayer(sender);
                    if (entityplayer == null) {
                        throw new PlayerNotFoundException();
                    }
                }
                for (final LOTRFaction f : factions) {
                    LOTRLevelData.getData((EntityPlayer)entityplayer).setAlignmentFromCommand(f, alignment);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.alignment.set", new Object[] { entityplayer.getCommandSenderName(), f.factionName(), alignment });
                }
                return;
            }
            if (args[0].equals("add")) {
                final float alignment = (float)parseDouble(sender, args[2]);
                EntityPlayerMP entityplayer;
                if (args.length >= 4) {
                    entityplayer = getPlayer(sender, args[3]);
                }
                else {
                    entityplayer = getCommandSenderAsPlayer(sender);
                    if (entityplayer == null) {
                        throw new PlayerNotFoundException();
                    }
                }
                final Map<LOTRFaction, Float> newAlignments = new HashMap<LOTRFaction, Float>();
                for (final LOTRFaction f2 : factions) {
                    final float newAlignment = LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(f2) + alignment;
                    if (newAlignment < -10000.0f) {
                        throw new WrongUsageException("commands.lotr.alignment.tooLow", new Object[] { -10000.0f });
                    }
                    if (newAlignment > 10000.0f) {
                        throw new WrongUsageException("commands.lotr.alignment.tooHigh", new Object[] { 10000.0f });
                    }
                    newAlignments.put(f2, newAlignment);
                }
                for (final LOTRFaction f2 : factions) {
                    final float newAlignment = newAlignments.get(f2);
                    LOTRLevelData.getData((EntityPlayer)entityplayer).addAlignmentFromCommand(f2, alignment);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.alignment.add", new Object[] { alignment, entityplayer.getCommandSenderName(), f2.factionName() });
                }
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "set", "add" });
        }
        if (args.length == 2) {
            final List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
            list.add("all");
            return getListOfStringsMatchingLastWord(args, (String[])list.toArray(new String[0]));
        }
        if (args.length == 4) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return i == 3;
    }
}
