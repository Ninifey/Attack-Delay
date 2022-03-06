// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.command.ICommand;
import net.minecraft.command.WrongUsageException;
import lotr.common.LOTRLevelData;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandAllowStructures extends CommandBase
{
    public String getCommandName() {
        return "allowStructures";
    }
    
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.allowStructures.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length == 0) {
            if (!LOTRLevelData.structuresBanned()) {
                throw new WrongUsageException("commands.lotr.allowStructures.alreadyAllowed", new Object[0]);
            }
            LOTRLevelData.setStructuresBanned(false);
            func_152373_a(sender, (ICommand)this, "commands.lotr.allowStructures.allow", new Object[0]);
        }
        else {
            LOTRLevelData.setPlayerBannedForStructures(args[0], false);
            func_152373_a(sender, (ICommand)this, "commands.lotr.allowStructures.allowPlayer", new Object[] { args[0] });
            final EntityPlayerMP entityplayer = getPlayer(sender, args[0]);
            if (entityplayer != null) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.allowStructures", new Object[0]));
            }
        }
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            final List<String> bannedNames = new ArrayList<String>();
            bannedNames.addAll(LOTRLevelData.getBannedStructurePlayersUsernames());
            return getListOfStringsMatchingLastWord(args, (String[])bannedNames.toArray(new String[0]));
        }
        return null;
    }
}
