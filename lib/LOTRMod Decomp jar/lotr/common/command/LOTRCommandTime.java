// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommand;
import lotr.common.LOTRTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandTime extends CommandBase
{
    public String getCommandName() {
        return "lotr_time";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.time.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 2) {
            if (args[0].equals("set")) {
                long time = 0L;
                if (args[1].equals("day")) {
                    time = Math.round(LOTRTime.DAY_LENGTH * 0.03);
                }
                else if (args[1].equals("night")) {
                    time = Math.round(LOTRTime.DAY_LENGTH * 0.6);
                }
                else {
                    time = parseIntWithMin(sender, args[1], 0);
                }
                LOTRTime.setWorldTime(time);
                func_152373_a(sender, (ICommand)this, "commands.lotr.time.set", new Object[] { time });
                return;
            }
            if (args[0].equals("add")) {
                final int time2 = parseIntWithMin(sender, args[1], 0);
                LOTRTime.addWorldTime(time2);
                func_152373_a(sender, (ICommand)this, "commands.lotr.time.add", new Object[] { time2 });
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "set", "add" });
        }
        if (args.length == 2 && args[0].equals("set")) {
            return getListOfStringsMatchingLastWord(args, new String[] { "day", "night" });
        }
        return null;
    }
}
