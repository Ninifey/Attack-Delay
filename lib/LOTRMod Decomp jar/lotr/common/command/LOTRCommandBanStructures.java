// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import net.minecraft.server.MinecraftServer;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.command.ICommand;
import net.minecraft.command.WrongUsageException;
import lotr.common.LOTRLevelData;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandBanStructures extends CommandBase
{
    public String getCommandName() {
        return "banStructures";
    }
    
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.banStructures.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length == 0) {
            if (LOTRLevelData.structuresBanned()) {
                throw new WrongUsageException("commands.lotr.banStructures.alreadyBanned", new Object[0]);
            }
            LOTRLevelData.setStructuresBanned(true);
            func_152373_a(sender, (ICommand)this, "commands.lotr.banStructures.ban", new Object[0]);
        }
        else {
            LOTRLevelData.setPlayerBannedForStructures(args[0], true);
            func_152373_a(sender, (ICommand)this, "commands.lotr.banStructures.banPlayer", new Object[] { args[0] });
            final EntityPlayerMP entityplayer = getPlayer(sender, args[0]);
            if (entityplayer != null) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.banStructures", new Object[0]));
            }
        }
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }
}
