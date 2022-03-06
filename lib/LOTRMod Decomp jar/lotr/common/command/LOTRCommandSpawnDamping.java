// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.command.ICommand;
import lotr.common.LOTRSpawnDamping;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class LOTRCommandSpawnDamping extends CommandBase
{
    public String getCommandName() {
        return "spawnDamping";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.lotr.spawnDamping.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1) {
            final String option = args[0];
            if (option.equals("reset")) {
                LOTRSpawnDamping.resetAll();
                func_152373_a(sender, (ICommand)this, "commands.lotr.spawnDamping.reset", new Object[0]);
                return;
            }
            if (args.length >= 2) {
                final String type = args[1];
                if (!type.equals(LOTRSpawnDamping.TYPE_NPC) && EnumCreatureType.valueOf(type) == null) {
                    throw new WrongUsageException("commands.lotr.spawnDamping.noType", new Object[] { type });
                }
                if (option.equals("set") && args.length >= 3) {
                    final float damping = (float)parseDoubleBounded(sender, args[2], 0.0, 1.0);
                    LOTRSpawnDamping.setSpawnDamping(type, damping);
                    func_152373_a(sender, (ICommand)this, "commands.lotr.spawnDamping.set", new Object[] { type, damping });
                    return;
                }
                if (option.equals("calc")) {
                    final World world = sender.getEntityWorld();
                    final int dim = world.provider.dimensionId;
                    final String dimName = world.provider.getDimensionName();
                    final float damping2 = LOTRSpawnDamping.getSpawnDamping(type);
                    final int players = world.playerEntities.size();
                    final int expectedChunks = 196;
                    final int baseCap = LOTRSpawnDamping.getBaseSpawnCapForInfo(type, world);
                    final int cap = LOTRSpawnDamping.getSpawnCap(type, baseCap, players);
                    final int capXPlayers = cap * players;
                    final IChatComponent msg = (IChatComponent)new ChatComponentTranslation("commands.lotr.spawnDamping.calc", new Object[] { dim, dimName, type, damping2, players, expectedChunks, cap, baseCap, capXPlayers });
                    msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
                    sender.addChatMessage(msg);
                    return;
                }
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[] { "set", "calc", "reset" });
        }
        if (args.length == 2 && (args[0].equals("set") || args[0].equals("calc"))) {
            final List<String> types = new ArrayList<String>();
            for (final EnumCreatureType type : EnumCreatureType.values()) {
                types.add(type.name());
            }
            types.add(LOTRSpawnDamping.TYPE_NPC);
            return getListOfStringsMatchingLastWord(args, (String[])types.toArray(new String[0]));
        }
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
