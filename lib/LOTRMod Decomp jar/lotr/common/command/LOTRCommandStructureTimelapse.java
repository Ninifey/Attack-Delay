// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.command.ICommand;
import lotr.common.LOTRConfig;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandStructureTimelapse extends CommandBase
{
    public String getCommandName() {
        return "strTimelapse";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.strTimelapse.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length != 1) {
            throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
        }
        if (args[0].equals("on")) {
            LOTRConfig.setStructureTimelapse(true);
            func_152373_a(sender, (ICommand)this, "commands.lotr.strTimelapse.on", new Object[0]);
            func_152373_a(sender, (ICommand)this, "commands.lotr.strTimelapse.warn", new Object[0]);
            return;
        }
        if (args[0].equals("off")) {
            LOTRConfig.setStructureTimelapse(false);
            func_152373_a(sender, (ICommand)this, "commands.lotr.strTimelapse.off", new Object[0]);
            return;
        }
        final int interval = parseIntWithMin(sender, args[0], 0);
        LOTRConfig.setStructureTimelapseInterval(interval);
        func_152373_a(sender, (ICommand)this, "commands.lotr.strTimelapse.interval", new Object[] { interval });
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "on", "off" });
        }
        return null;
    }
}
