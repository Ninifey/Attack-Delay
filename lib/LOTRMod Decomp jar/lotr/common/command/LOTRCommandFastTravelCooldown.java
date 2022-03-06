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

public class LOTRCommandFastTravelCooldown extends CommandBase
{
    public static int MAX_COOLDOWN;
    
    public String getCommandName() {
        return "fastTravelCooldown";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.fastTravelCooldown.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        String function = null;
        int cooldown = -1;
        if (args.length == 1) {
            function = "max";
            cooldown = parseIntBounded(sender, args[0], 0, LOTRCommandFastTravelCooldown.MAX_COOLDOWN);
        }
        else if (args.length >= 2) {
            function = args[0];
            cooldown = parseIntBounded(sender, args[1], 0, LOTRCommandFastTravelCooldown.MAX_COOLDOWN);
        }
        if (function != null && cooldown >= 0) {
            int max = LOTRLevelData.getFTCooldownMax();
            int min = LOTRLevelData.getFTCooldownMin();
            if (function.equals("max")) {
                boolean updatedMin = false;
                max = cooldown;
                if (max < min) {
                    min = max;
                    updatedMin = true;
                }
                LOTRLevelData.setFTCooldown(max, min);
                func_152373_a(sender, (ICommand)this, "commands.lotr.fastTravelCooldown.setMax", new Object[] { max, LOTRLevelData.getHMSTime(max) });
                if (updatedMin) {
                    func_152373_a(sender, (ICommand)this, "commands.lotr.fastTravelCooldown.updateMin", new Object[] { min });
                }
                return;
            }
            if (function.equals("min")) {
                boolean updatedMax = false;
                min = cooldown;
                if (min > max) {
                    max = min;
                    updatedMax = true;
                }
                LOTRLevelData.setFTCooldown(max, min);
                func_152373_a(sender, (ICommand)this, "commands.lotr.fastTravelCooldown.setMin", new Object[] { min, LOTRLevelData.getHMSTime(min) });
                if (updatedMax) {
                    func_152373_a(sender, (ICommand)this, "commands.lotr.fastTravelCooldown.updateMax", new Object[] { max });
                }
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "max", "min" });
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
    
    static {
        LOTRCommandFastTravelCooldown.MAX_COOLDOWN = 1728000;
    }
}
