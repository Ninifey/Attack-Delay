// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.command.ICommand;
import lotr.common.LOTRLevelData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandAdminHideMap extends CommandBase
{
    public String getCommandName() {
        return "opHideMap";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.opHideMap.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            throw new WrongUsageException("commands.lotr.opHideMap.notOp", new Object[0]);
        }
        final EntityPlayer player = (EntityPlayer)sender;
        if (!MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile())) {
            throw new WrongUsageException("commands.lotr.opHideMap.notOp", new Object[0]);
        }
        if (player.capabilities.isCreativeMode) {
            LOTRLevelData.getData(player).setAdminHideMap(true);
            func_152373_a(sender, (ICommand)this, "commands.lotr.opHideMap.hiding", new Object[0]);
            return;
        }
        throw new WrongUsageException("commands.lotr.opHideMap.notCreative", new Object[0]);
    }
    
    public static void notifyUnhidden(final EntityPlayer entityplayer) {
        entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.lotr.opHideMap.unhide", new Object[0]));
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
