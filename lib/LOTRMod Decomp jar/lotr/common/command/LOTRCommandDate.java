// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.util.IChatComponent;
import net.minecraft.command.ICommand;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRDate;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandDate extends CommandBase
{
    public String getCommandName() {
        return "lotrDate";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.lotrDate.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1 && args[0].equals("get")) {
            final int date = LOTRDate.ShireReckoning.currentDay;
            final String dateName = LOTRDate.ShireReckoning.getShireDate().getDateName(false);
            final IChatComponent message = (IChatComponent)new ChatComponentTranslation("commands.lotr.lotrDate.get", new Object[] { date, dateName });
            sender.addChatMessage(message);
            return;
        }
        if (args.length < 2) {
            throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
        }
        int newDate = LOTRDate.ShireReckoning.currentDay;
        if (args[0].equals("set")) {
            newDate = parseInt(sender, args[1]);
        }
        else if (args[0].equals("add")) {
            final int date2 = parseInt(sender, args[1]);
            newDate += date2;
        }
        if (Math.abs(newDate) > 1000000) {
            throw new WrongUsageException("commands.lotr.lotrDate.outOfBounds", new Object[0]);
        }
        LOTRDate.setDate(newDate);
        final String dateName = LOTRDate.ShireReckoning.getShireDate().getDateName(false);
        func_152373_a(sender, (ICommand)this, "commands.lotr.lotrDate.set", new Object[] { newDate, dateName });
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "get", "set", "add" });
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
