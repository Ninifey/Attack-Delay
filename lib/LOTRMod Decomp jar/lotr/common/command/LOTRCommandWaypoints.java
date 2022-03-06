// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandException;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommand;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandWaypoints extends CommandBase
{
    public String getCommandName() {
        return "lotrWaypoints";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.lotrWaypoints.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 2) {
            final String regionName = args[1];
            EntityPlayerMP entityplayer;
            if (args.length >= 3) {
                entityplayer = getPlayer(sender, args[2]);
            }
            else {
                entityplayer = getCommandSenderAsPlayer(sender);
            }
            final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            if (args[0].equalsIgnoreCase("unlock")) {
                if (regionName.equalsIgnoreCase("all")) {
                    for (final LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
                        playerData.unlockFTRegion(region);
                    }
                    func_152373_a(sender, (ICommand)this, "commands.lotr.lotrWaypoints.unlockAll", new Object[] { entityplayer.getCommandSenderName() });
                    return;
                }
                final LOTRWaypoint.Region region2 = this.findRegionByName(regionName);
                if (playerData.isFTRegionUnlocked(region2)) {
                    throw new WrongUsageException("commands.lotr.lotrWaypoints.unlock.fail", new Object[] { entityplayer.getCommandSenderName(), region2.name() });
                }
                playerData.unlockFTRegion(region2);
                func_152373_a(sender, (ICommand)this, "commands.lotr.lotrWaypoints.unlock", new Object[] { entityplayer.getCommandSenderName(), region2.name() });
                return;
            }
            else if (args[0].equalsIgnoreCase("lock")) {
                if (regionName.equalsIgnoreCase("all")) {
                    for (final LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
                        playerData.lockFTRegion(region);
                    }
                    func_152373_a(sender, (ICommand)this, "commands.lotr.lotrWaypoints.lockAll", new Object[] { entityplayer.getCommandSenderName() });
                    return;
                }
                final LOTRWaypoint.Region region2 = this.findRegionByName(regionName);
                if (!playerData.isFTRegionUnlocked(region2)) {
                    throw new WrongUsageException("commands.lotr.lotrWaypoints.lock.fail", new Object[] { entityplayer.getCommandSenderName(), region2.name() });
                }
                playerData.lockFTRegion(region2);
                func_152373_a(sender, (ICommand)this, "commands.lotr.lotrWaypoints.lock", new Object[] { entityplayer.getCommandSenderName(), region2.name() });
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    private LOTRWaypoint.Region findRegionByName(final String name) {
        final LOTRWaypoint.Region region = LOTRWaypoint.regionForName(name);
        if (region == null) {
            throw new CommandException("commands.lotr.lotrWaypoints.unknown", new Object[] { name });
        }
        return region;
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "unlock", "lock" });
        }
        if (args.length == 2) {
            final List<String> names = new ArrayList<String>();
            for (final LOTRWaypoint.Region r : LOTRWaypoint.Region.values()) {
                names.add(r.name());
            }
            names.add("all");
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
