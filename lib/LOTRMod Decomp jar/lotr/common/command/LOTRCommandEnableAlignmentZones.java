// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommand;
import lotr.common.LOTRLevelData;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandEnableAlignmentZones extends CommandBase
{
    public String getCommandName() {
        return "alignmentZones";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.alignmentZones.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1) {
            final String flag = args[0];
            if (flag.equals("enable")) {
                LOTRLevelData.setEnableAlignmentZones(true);
                func_152373_a(sender, (ICommand)this, "commands.lotr.alignmentZones.enable", new Object[0]);
                return;
            }
            if (flag.equals("disable")) {
                LOTRLevelData.setEnableAlignmentZones(false);
                func_152373_a(sender, (ICommand)this, "commands.lotr.alignmentZones.disable", new Object[0]);
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "enable", "disable" });
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
