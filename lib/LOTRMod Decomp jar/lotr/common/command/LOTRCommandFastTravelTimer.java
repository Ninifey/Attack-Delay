// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import net.minecraft.server.MinecraftServer;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandFastTravelTimer extends CommandBase
{
    public String getCommandName() {
        return "fastTravelTimer";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.fastTravelTimer.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1) {
            final int timer = parseIntBounded(sender, args[0], 0, LOTRCommandFastTravelCooldown.MAX_COOLDOWN);
            EntityPlayerMP entityplayer;
            if (args.length >= 2) {
                entityplayer = getPlayer(sender, args[1]);
            }
            else {
                entityplayer = getCommandSenderAsPlayer(sender);
                if (entityplayer == null) {
                    throw new PlayerNotFoundException();
                }
            }
            LOTRLevelData.getData((EntityPlayer)entityplayer).setFTTimer(timer);
            func_152373_a(sender, (ICommand)this, "commands.lotr.fastTravelTimer.set", new Object[] { entityplayer.getCommandSenderName(), timer, LOTRLevelData.getHMSTime(timer) });
            return;
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return i == 1;
    }
}
