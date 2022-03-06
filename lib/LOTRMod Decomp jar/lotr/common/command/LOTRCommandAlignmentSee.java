// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import lotr.common.LOTRPlayerData;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.WrongUsageException;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketAlignmentSee;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandAlignmentSee extends CommandBase
{
    public String getCommandName() {
        return "alignmentsee";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.alignmentsee.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1) {
            final String username = args[0];
            GameProfile profile = null;
            final EntityPlayerMP entityplayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(username);
            if (entityplayer != null) {
                profile = entityplayer.getGameProfile();
            }
            else {
                profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
            }
            if (profile == null || profile.getId() == null) {
                throw new PlayerNotFoundException("commands.lotr.alignmentsee.noPlayer", new Object[] { username });
            }
            if (sender instanceof EntityPlayerMP) {
                final LOTRPlayerData playerData = LOTRLevelData.getData(profile.getId());
                final LOTRPacketAlignmentSee packet = new LOTRPacketAlignmentSee(username, playerData);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)sender);
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return i == 0;
    }
}
