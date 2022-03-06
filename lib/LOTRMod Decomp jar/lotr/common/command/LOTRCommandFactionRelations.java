// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.command.ICommand;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandFactionRelations extends CommandBase
{
    public String getCommandName() {
        return "facRelations";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.facRelations.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1) {
            final String function = args[0];
            if (function.equals("set")) {
                if (args.length < 4) {
                    throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
                }
                final LOTRFaction fac1 = LOTRFaction.forName(args[1]);
                if (fac1 == null) {
                    throw new WrongUsageException("commands.lotr.alignment.noFaction", new Object[] { args[1] });
                }
                final LOTRFaction fac2 = LOTRFaction.forName(args[2]);
                if (fac2 == null) {
                    throw new WrongUsageException("commands.lotr.alignment.noFaction", new Object[] { args[2] });
                }
                final LOTRFactionRelations.Relation relation = LOTRFactionRelations.Relation.forName(args[3]);
                if (relation == null) {
                    throw new WrongUsageException("commands.lotr.facRelations.noRelation", new Object[] { args[3] });
                }
                try {
                    LOTRFactionRelations.overrideRelations(fac1, fac2, relation);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.facRelations.set", new Object[] { fac1.factionName(), fac2.factionName(), relation.getDisplayName() });
                    return;
                }
                catch (IllegalArgumentException e) {
                    throw new WrongUsageException("commands.lotr.facRelations.error", new Object[] { e.getMessage() });
                }
            }
            if (function.equals("reset")) {
                LOTRFactionRelations.resetAllRelations();
                func_152373_a(sender, (ICommand)this, "commands.lotr.facRelations.reset", new Object[0]);
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "set", "reset" });
        }
        if (args.length == 2 || args.length == 3) {
            final List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
            return getListOfStringsMatchingLastWord(args, (String[])list.toArray(new String[0]));
        }
        if (args.length == 4) {
            final List<String> list = LOTRFactionRelations.Relation.listRelationNames();
            return getListOfStringsMatchingLastWord(args, (String[])list.toArray(new String[0]));
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
